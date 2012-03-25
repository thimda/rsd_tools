package nc.uap.portal.wizards;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.internal.util.ProjCoreUtility;
import nc.uap.lfw.wizards.NewWebModuleProjectWizard;
import nc.uap.portal.core.PortalProjConstants;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * 新建Portal（LFW工程）
 * 
 * @author dingrf
 *
 */
public class NewLFWPortalModuleProjectWizard extends NewWebModuleProjectWizard {

	/**portal工程的默认依赖模块*/
	private final String DEFAULT_DEPENDS = "pserver"; 
	
	public NewLFWPortalModuleProjectWizard(){
		setWindowTitle(PortalProjConstants.KEY_NEW_PORTALPRJ_MAINPAGE_TITLE);
	}
	

	protected void addPathPage() {
		fNewModuleWebPage = new PortalModuleWebContextPage(WEBProjConstants.KEY_NEWPRJ_MAINPAGE_TITLE);
		addPage(fNewModuleWebPage);
	}
	
	@Override
	public boolean performFinish() {
		boolean finishFlag =  super.performFinish();
		if(finishFlag){
			return this.doPortalOperate();
		}
		return true;
	}
	
	/**
	 * portal本身的操作
	 * @return
	 */
	private boolean doPortalOperate(){
		IProject project = super.getProject();
		if(project == null)
			return false;
	
		IJavaProject javaProject = JavaCore.create(project);
		try {
			List<IClasspathEntry> list = new ArrayList<IClasspathEntry>();
			list.add(ProjCoreUtility.createSourceEntry(project, "src/portalspec"));
			list.addAll(Arrays.asList(javaProject.getRawClasspath()));
			javaProject.setRawClasspath(list.toArray(new IClasspathEntry[0]), null);
		} catch (CoreException e) {
			PortalProjPlugin.getDefault().logError(e.getMessage(),e);
			return false;
		}

		try {
			String moduleName = LfwCommonTool.getProjectModuleName(project);
			String localFilePath = project.getLocation().toString() + "/src/portalspec/" + 
				moduleName + "/portalspec";
			writePortal(localFilePath,moduleName);
			
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		} catch (Exception e) {
			PortalProjPlugin.getDefault().logError(e.getMessage(),e);
			return false;
		}
		return true;
	}
	
	protected void performBasicOperation() throws InvocationTargetException,InterruptedException {
		super.
		getContainer().run(false, true, new PortalProjectCreationOperation(fProjectProvider));
	}
	
 	/**
 	 * 生成portal文件
 	 * @param filePath
 	 * @param module
 	 * @return
 	 * @throws Exception 
 	 * @throws UnsupportedEncodingException 
 	 */
 	private void writePortal(String filePath, String module) throws UnsupportedEncodingException, Exception
 	{
 		StringBuffer buffer = new StringBuffer();
 		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
 		buffer.append("\n<portal>");
 		buffer.append("\n<module>"+module+"</module>");
 		buffer.append("\n<depends>"+DEFAULT_DEPENDS+"</depends>");
 		buffer.append("\n</portal>");
 		//ByteArrayInputStream stream = new ByteArrayInputStream(buffer.toString().getBytes());
 		File f = new File(filePath);
 		if (!f.exists()){
 			f.mkdirs();
 		}
 		FileUtilities.saveFile(filePath + "/portal.xml", buffer.toString().getBytes("UTF-8"));
 	}
}
