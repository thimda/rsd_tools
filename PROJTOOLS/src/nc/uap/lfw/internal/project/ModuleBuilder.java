/*
 * Created on 2005-8-16
 * @author �ι���
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
 *LFW��ĿBuilder 
 *
 */
public class ModuleBuilder extends IncrementalProjectBuilder
{
	/** NC_HOME Ŀ¼*/
	private String NC_HOME = ProjCoreUtility.getNcHomeFolderPath().toString();

	/** hotwebs Ŀ¼*/
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
					//������portal��Ŀ��web��Ŀ¼������hotwebs/portal/apps��
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
				//webĿ¼���ļ��ı�
				if (isWebFolder(candidate)){
					/*Ŀ��·��*/
					String targetPath = HOTWEBS;
					/* WEB-INF ���·��*/
					String webinfPath = "/web/WEB-INF";
					/* �ļ���web�е���� ·�� */
					String fileFullPath = candidate.getFullPath().toString();
					String filePath =  fileFullPath.substring(fileFullPath.indexOf("/web/")+ "/web".length() , fileFullPath.lastIndexOf("/"));
					
					/*���������*/
					if (!"web".equals(candidate.getProjectRelativePath().segment(0))){
						webinfPath = candidate.getProjectRelativePath().segment(0) + webinfPath;
					}
					
					/* ��web-inf�в���*.part */
					String partName = getPartName(webinfPath);
					if (partName == null){
						/* ��.module_prj ���ҵ� module.webContext */
						String ctx = LfwCommonTool.getLfwProjectCtx(getProject());
						if (ctx == null || ctx.equals("")){
							WEBProjPlugin.getDefault().logError("û���ҵ�web������,��Ŀ��"+getProject().getLocation().toString());
							return true;
						}
						targetPath =  targetPath  + "/" + ctx + filePath;
					}
					else{
						targetPath = targetPath + partName + filePath; 
					}
					WEBProjPlugin.getDefault().logInfo("Web �� File changed:" + candidate.getName() + ",type:" + delta.getKind());
					
					/*ɾ��webtemp*/
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
							/* �޸�part�ļ�ʱ������web.xml�ϲ� */
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
						/* ɾ��part�ļ�ʱ����web.xml���޳��������*/
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
	 * �ж��Ƿ�ΪwebĿ¼���ļ��ı�
	 * 
	 * @param file  �����ı���ļ�
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
	 * ȡWEB-INF�µ�*.part����
	 */
 	private String getPartName(String webinfPath){
		IFolder webinfFolder = getProject().getFolder(webinfPath);
		File webinfFile = new File(webinfFolder.getLocation().toString());
		if(!webinfFile.exists()){
			WEBProjPlugin.getDefault().logError(webinfFolder.getLocation().toString() + "�����ڣ��޷����ͬ��");
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
 	 * ����webĿ¼���ļ�
 	 */
	private void compileWebFile(String targetPath, IFile file, IProgressMonitor monitor){
		try{
			String filePath = targetPath; 
			File f = new File(filePath);
			if (!f.exists()){
				f.mkdirs();
			}
			WEBProjPlugin.getDefault().logInfo("��������Ŀ���ļ�·��" + filePath);
			FileUtilities.copyFile(file.getLocation().toString(), filePath + "/" + file.getName());
		}
		catch (IOException e)
		{
			WEBProjPlugin.getDefault().logError(e);
		}
	}
	
	/**
	 * ɾ��webĿ¼���ļ�
	 */
	private void removeWebFile(String targetPath, IFile file, IProgressMonitor monitor)
	{
		File f = new File(targetPath + "/" + file.getName());
		f.delete();
	}
	
	/**
	 * ɾ��webtemp�µ�jsp�ļ�
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