package nc.uap.portal.project;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.internal.util.ProjCoreUtility;
import nc.uap.portal.core.PortalProjConnector;

import org.eclipse.core.resources.IFile;
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
 *dingrf
 */
public class PortalBuilder extends IncrementalProjectBuilder
{
	/** NC_HOME 目录*/
	private String NC_HOME = ProjCoreUtility.getNcHomeFolderPath().toString();

	/** hotwebs 目录*/
	private String PORTALHOME = NC_HOME + "/portalhome";
	
	public PortalBuilder(){
		WEBProjPlugin.getDefault().logInfo("PortalBuilder started");
	}

	@Override
	@SuppressWarnings("unchecked")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException
	{
		IProject project = getProject();
		if (!ProjCoreUtility.isModuleProject(project))
			return null;
		IResourceDelta delta = null;
		if (kind != FULL_BUILD)
			delta = getDelta(project);
		if (delta == null || kind == FULL_BUILD){}
		else{
			delta.accept(new DeltaVisitor(monitor));
		}
		return null;
	}

	class DeltaVisitor implements IResourceDeltaVisitor{
		
		private IProgressMonitor monitor;

		public DeltaVisitor(IProgressMonitor monitor)
		{
			WEBProjPlugin.getDefault().logInfo("DeltaVisitor started");
			this.monitor = monitor;
		}

		public boolean visit(IResourceDelta delta)
		{
			IResource resource = delta.getResource();
			if (resource instanceof IFile){
				IFile candidate = (IFile) resource;
				String filePath =  candidate.getProjectRelativePath().toString();
				boolean isBcp = false;
				String moduleName = getProjectModuleName(getProject(), filePath, isBcp);
				String starts = "src/portalspec/";
				if (isBcp)
					starts = moduleName + "/src/portalspec/";
				//是否为已部署过的portalspec下的文件
				if (isDeployedPortalFile(moduleName, filePath, starts)){
					//目标路径
					String targetPath = PORTALHOME + "/" + moduleName + "/portalspec" 
						+ filePath.substring(0,filePath.lastIndexOf("/")).replace(starts + moduleName + "/portalspec", "");
					if (delta.getKind() != IResourceDelta.REMOVED){
						compilePortalFile(targetPath, candidate, monitor);
					}
					else{
						removePortalFile(targetPath, candidate, monitor);
					}
					
					//重新部署发生改变的文件
					deployPortalSpec(candidate, moduleName, starts);
					
					return false;
				}
			}
			return true;
		}

		private String getProjectModuleName(IProject project, String filePath, boolean isBcp) {
			String moduleName = LfwCommonTool.getProjectModuleName(project);
			if (filePath.startsWith("src/portalspec/"+moduleName+"/portalspec/")){
				return moduleName; 
			}
			else if (LfwCommonTool.isBCPProject(project)){
				String[] bcpNames = LfwCommonTool.getBCPNames(project);
				if(bcpNames != null){
					for (int i = 0; i < bcpNames.length; i++) {
						if (filePath.startsWith(bcpNames[i] + "/src/portalspec/" + bcpNames[i] + "/portalspec/")){
							isBcp = true;
							return bcpNames[i];
						}
					}
				}
			}
			return moduleName; 
		}
	}

	/**
	 * 判断是否为portalspec下文件发生变化
	 * 
	 * @param file  发生改变的文件或文件夹
	 */
	private boolean isDeployedPortalFile(String moduleName, String filePath, String starts){
		//portalspec中web目录不做此处理
		if (filePath.startsWith(starts + moduleName + "/portalspec/") && 
				!filePath.startsWith(starts + moduleName + "/portalspec/web")){
			File dir = new File(PORTALHOME + "/" + moduleName + "/portalspec");
			if (dir.exists())
				return true;
			else
				return false;
		}
		return false;
	}

 	/**
 	 * 拷贝portalspec目录下文件
 	 */
	private void compilePortalFile(String targetPath, IFile file, IProgressMonitor monitor){
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
	 * 删除portalspec目录下文件
	 */
	private void removePortalFile(String targetPath, IFile file, IProgressMonitor monitor)
	{
		File f = new File(targetPath + "/" + file.getName());
		f.delete();
	}
	
	/**
	 * 部署PortalSpec
	 * 
	 * @param filePath
	 * @param moduleName
	 */
	private void deployPortalSpec(IFile candidate, String moduleName, String starts){
		String filePath = candidate.getProjectRelativePath().toString();
		if (filePath.equals(starts + moduleName+"/portalspec/portal.xml")){
			PortalProjConnector.deployPortal(moduleName);
		}
		else if (filePath.equals(starts + moduleName+"/portalspec/portlet.xml")){
			PortalProjConnector.deployPortletApp(moduleName);
		}
		else if (filePath.equals(starts + moduleName+"/portalspec/display.xml")){
			PortalProjConnector.deployDisplay(moduleName);
		}
		else if (filePath.equals(starts + moduleName+"/portalspec/plugin.xml")){
			PortalProjConnector.deployPtPlugin(moduleName);
		}
		else if (filePath.endsWith(".pml")){
			PortalProjConnector.deployPage(moduleName, candidate.getName().split("\\.")[0]);
		}
		else if (filePath.startsWith(starts + moduleName+"/portalspec/manager/")){
			PortalProjConnector.deployManagerApps(moduleName);
		}
		else if (filePath.startsWith(starts + moduleName+"/portalspec/ftl/portaldefine/skin")){
			PortalProjConnector.deploySkin(moduleName);
		}
	}
}