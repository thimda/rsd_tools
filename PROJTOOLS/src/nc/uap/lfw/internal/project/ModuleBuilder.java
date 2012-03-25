/*
 * Created on 2005-8-16
 * @author 何冠宇
 */
package nc.uap.lfw.internal.project;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.internal.util.PartMergeUtil;
import nc.uap.lfw.internal.util.ProjCoreUtility;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 *LFW项目Builder 
 *
 */
public class ModuleBuilder extends IncrementalProjectBuilder
{
	/** NC_HOME 目录*/
	private String NC_HOME = ProjCoreUtility.getNcHomeFolderPath().toString();

	/** hotwebs 目录*/
	private String HOTWEBS = NC_HOME + "/hotwebs";
	
	public ModuleBuilder(){
		WEBProjPlugin.getDefault().logInfo("ModuleBuilder started");
	}

	@Override
	@SuppressWarnings("unchecked")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException{
		IProject project = getProject();
		if (!ProjCoreUtility.isModuleProject(project))
			return null;
		IResourceDelta delta = null;
		if (kind != FULL_BUILD)
			delta = getDelta(project);
		if (delta == null || kind == FULL_BUILD){
			if(ProjCoreUtility.isPortalProject(project)){
				String moduleName = LfwCommonTool.getProjectModuleName(project);
				String fromPath = project.getLocation().toString() + "/src/portalspec/" + moduleName + "/portalspec/web";
				File fromDir = new File(fromPath);
				if (fromDir.exists()){
					//将所有portal项目的web下目录拷贝到hotwebs/portal/apps下
					String toPath = HOTWEBS + "/portal/apps/" + moduleName;
					File toDir = new File(toPath);
					if (!toDir.exists())
						toDir.mkdirs();
					try {
						FileUtilities.copyFileFromDir(toPath, fromPath);
					} catch (Exception e) {
						WEBProjPlugin.getDefault().logError(e);
					}
				}
			}
		}
		else{
			delta.accept(new DeltaVisitor(monitor));
		}
		return null;
	}

	class DeltaVisitor implements IResourceDeltaVisitor{
		
		private IProgressMonitor monitor;

		public DeltaVisitor(IProgressMonitor monitor){
			WEBProjPlugin.getDefault().logInfo("DeltaVisitor started");
			this.monitor = monitor;
		}

		public boolean visit(IResourceDelta delta){
			IResource resource = delta.getResource();
			if (resource instanceof IFile){
				IFile candidate = (IFile) resource;
				//web目录下文件改变
				if (isWebFolder(candidate)){
					/*目标路径*/
					String targetPath = HOTWEBS;
					/* WEB-INF 相对路径*/
					String webinfPath = "/web/WEB-INF";
					/* 文件在web中的相对 路径 */
					String fileFullPath = candidate.getFullPath().toString();
					String filePath =  fileFullPath.substring(fileFullPath.indexOf("/web/")+ "/web".length() , fileFullPath.lastIndexOf("/"));
					
					/*是组件工程*/
					if (!"web".equals(candidate.getProjectRelativePath().segment(0))){
						webinfPath = candidate.getProjectRelativePath().segment(0) + webinfPath;
					}
					
					/* 在web-inf中查找*.part */
					String partName = getPartName(webinfPath);
					if (partName == null){
						/* 在.module_prj 中找到 module.webContext */
						String ctx = LfwCommonTool.getLfwProjectCtx(getProject());
						if (ctx == null || ctx.equals("")){
							WEBProjPlugin.getDefault().logError("没有找到web上下文,项目："+getProject().getLocation().toString());
							return true;
						}
						targetPath =  targetPath  + "/" + ctx + filePath;
					}
					else{
						targetPath = targetPath + partName + filePath; 
					}
					WEBProjPlugin.getDefault().logInfo("Web 下 File changed:" + candidate.getName() + ",type:" + delta.getKind());
					
					/*删除webtemp*/
					if (filePath.startsWith("/html/nodes/")){
						String[] folders = filePath.replace("/html/nodes/", "").split("/");
						String page = "";
						for (int i = 0 ; i< folders.length ; i ++){
							page += "/" + folders[i]; 
							String tempPath = targetPath.replace(filePath, "/webtemp/html/nodes"+page);
							deleteTempFile(tempPath);
						}
					}

					if (delta.getKind() != IResourceDelta.REMOVED){
						compileWebFile(targetPath, candidate, monitor);
						
						if (candidate.getName().endsWith(".part")){
							/* 修改part文件时，进行web.xml合并 */
							File fromFile = new File(candidate.getLocation().toString());
							PartMergeUtil.mergeWebXml(getProject(), fromFile);
						}
						else if (candidate.getName().equals("web.xml")){
							PartMergeUtil.mergeParts(getProject());
						}
						return false;
					}
					else
					{
						removeWebFile(targetPath, candidate, monitor);
						/* 删除part文件时，在web.xml中剔除相关配置*/
						if (candidate.getName().endsWith(".part")){
							PartMergeUtil.deletePart(getProject(), candidate.getName().replace(".part", ""));
						}
						return false;
					}
				}
			}
			return true;
		}
	}

	/**
	 * 判断是否为web目录下文件改变
	 * 
	 * @param file  发生改变的文件
	 */
	private boolean isWebFolder(IFile file){
		if ("web".equals(file.getProjectRelativePath().segment(0))){
			return true;
		} 
		else{
			try {
				if ( (getProject().hasNature(WEBProjConstants.MODULE_NATURE)) && ("web".equals(file.getProjectRelativePath().segment(1)))){
					String[] names = LfwCommonTool.getBCPNames(getProject());
					for (int i = 0 ; i < names.length ; i++){
						if (names[i].equals(file.getProjectRelativePath().segment(0))){
							return true;
						}
					}
				}
			} 
			catch (Exception e) {
				WEBProjPlugin.getDefault().logError(e);
			}
		}
		return false;
	}

	/**
	 * 取WEB-INF下的*.part名称
	 */
 	private String getPartName(String webinfPath){
		IFolder webinfFolder = getProject().getFolder(webinfPath);
		File webinfFile = new File(webinfFolder.getLocation().toString());
		if(!webinfFile.exists()){
			WEBProjPlugin.getDefault().logError(webinfFolder.getLocation().toString() + "不存在，无法完成同步");
			return null;
		}
			
		File[] files = webinfFile.listFiles();
		for (int i=0 ; i < files.length ; i++){
			if (files[i].isFile()){
				if (files[i].getName().endsWith(".part")){
					return "/" + files[i].getName().substring(0,files[i].getName().length() - 5);
				}
			} 
		}
		return null;
 	}
	
 	/**
 	 * 拷贝web目录下文件
 	 */
	private void compileWebFile(String targetPath, IFile file, IProgressMonitor monitor){
		try{
			String filePath = targetPath; 
			File f = new File(filePath);
			if (!f.exists()){
				f.mkdirs();
			}
			WEBProjPlugin.getDefault().logInfo("拷贝到的目的文件路径" + filePath);
			FileUtilities.copyFile(file.getLocation().toString(), filePath + "/" + file.getName());
		}
		catch (IOException e)
		{
			WEBProjPlugin.getDefault().logError(e);
		}
	}
	
	/**
	 * 删除web目录下文件
	 */
	private void removeWebFile(String targetPath, IFile file, IProgressMonitor monitor)
	{
		File f = new File(targetPath + "/" + file.getName());
		f.delete();
	}
	
	/**
	 * 删除webtemp下的jsp文件
	 * 
	 * @param path
	 */
	private void deleteTempFile(String path){
		File dir = new File(path);
		if (dir.exists() && dir.isDirectory()){
			File[] childFiles = dir.listFiles();
            int count = childFiles == null ? 0 : childFiles.length;
            for (int i = 0; i < count; i++) {
            	if (childFiles[i].isFile())
            		childFiles[i].delete();
            }
		}
		
	}
	
}