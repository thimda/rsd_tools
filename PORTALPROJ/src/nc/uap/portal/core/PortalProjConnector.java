package nc.uap.portal.core;

import nc.uap.lfw.common.ClassTool;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.portal.service.itf.IPortalDesignDataProvider;

import org.eclipse.core.resources.IProject;

/**
 * ��service����
 * 
 * @author dingrf
 *
 */
public class PortalProjConnector {

	@SuppressWarnings("unchecked")
	private static IPortalDesignDataProvider getProvider() {
		IProject[] projects = LfwCommonTool.getLfwProjects();
		for (int i = 0; i < projects.length; i++) {
			try {
				Class c = ClassTool.loadClass("nc.uap.portal.service.impl.PortalDesignDataProviderImpl", projects[i], IPortalDesignDataProvider.class.getClassLoader());
				Thread.currentThread().setContextClassLoader(c.getClassLoader());
				System.out.println(c.getClassLoader());
				System.out.println(IPortalDesignDataProvider.class.getClassLoader());
				return (IPortalDesignDataProvider) c.newInstance();
			} 
			catch (Exception e) {
				WEBProjPlugin.getDefault().logError(e.getMessage(),e);
			}
		}
		return null;
	}

	/**
	 * ����portal.xml
	 * 
	 * @param projectModuleName
	 */
	public static void deployPortal(String projectModuleName){
		getProvider().deployPortal(projectModuleName);
	}
	
	/**
	 * ����display
	 * 
	 * @param projectModuleName
	 */
	public static void deployDisplay(String projectModuleName) {
		getProvider().deployDisplay(projectModuleName);
	}

	/**
	 * ��������
	 * 
	 */
//	public static void deployLookAndFeel() {
//		getProvider().deployLookAndFeel();
//	}

	/**
	 * �����ܹ��� 
	 * 
	 * @param projectModuleName
	 */
	public static void deployManagerApps(String projectModuleName) {
		getProvider().deployManagerApps(projectModuleName);
	}

	/**
	 * ����pageҳ
	 * 
	 * @param projectModuleName
	 * @param pageName
	 */
	public static void deployPage(String projectModuleName, String pageName) {
		getProvider().deployPage(projectModuleName,pageName);
	}

	/**
	 * ����portletapp
	 * 
	 * @param projectModuleName
	 */
	public static void deployPortletApp(String projectModuleName) {
		getProvider().deployPortletApp(projectModuleName);
	}

	/**
	 * ������
	 * 
	 * @param projectModuleName
	 */
	public static void deployPtPlugin(String projectModuleName) {
		getProvider().deployPtPlugin(projectModuleName);
	}

	/**
	 * ����Ƥ��
	 * 
	 * @param projectModuleName
	 */
	public static void deploySkin(String projectModuleName) {
		getProvider().deploySkin(projectModuleName);
	}
	
	
}
