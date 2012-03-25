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
 *LFW��ĿBuilder 
 *
 *dingrf
 */
public class PortalBuilder extends IncrementalProjectBuilder
{
	/** NC_HOME Ŀ¼*/
	private String NC_HOME = ProjCoreUtility.getNcHomeFolderPath().toString();

	/** hotwebs Ŀ¼*/
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
				//�Ƿ�Ϊ�Ѳ������portalspec�µ��ļ�
				if (isDeployedPortalFile(moduleName, filePath, starts)){
					//Ŀ��·��
					String targetPath = PORTALHOME + "/" + moduleName + "/portalspec" 
						+ filePath.substring(0,filePath.lastIndexOf("/")).replace(starts + moduleName + "/portalspec", "");
					if (delta.getKind() != IResourceDelta.REMOVED){
						compilePortalFile(targetPath, candidate, monitor);
					}
					else{
						removePortalFile(targetPath, candidate, monitor);
					}
					
					//���²������ı���ļ�
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
	 * �ж��Ƿ�Ϊportalspec���ļ������仯
	 * 
	 * @param file  �����ı���ļ����ļ���
	 */
	private boolean isDeployedPortalFile(String moduleName, String filePath, String starts){
		//portalspec��webĿ¼�����˴���
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
 	 * ����portalspecĿ¼���ļ�
 	 */
	private void compilePortalFile(String targetPath, IFile file, IProgressMonitor monitor){
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
	 * ɾ��portalspecĿ¼���ļ�
	 */
	private void removePortalFile(String targetPath, IFile file, IProgressMonitor monitor)
	{
		File f = new File(targetPath + "/" + file.getName());
		f.delete();
	}
	
	/**
	 * ����PortalSpec
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