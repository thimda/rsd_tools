package nc.uap.portal.core;

import nc.uap.lfw.common.ClassTool;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.portal.service.itf.IPortalDesignDataProvider;

import org.eclipse.core.resources.IProject;

/**
 * 与service交互
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
	 * 部署portal.xml
	 * 
	 * @param projectModuleName
	 */
	public static void deployPortal(String projectModuleName){
		getProvider().deployPortal(projectModuleName);
	}
	
	/**
	 * 部署display
	 * 
	 * @param projectModuleName
	 */
	public static void deployDisplay(String projectModuleName) {
		getProvider().deployDisplay(projectModuleName);
	}

	/**
	 * 部署主题
	 * 
	 */
//	public static void deployLookAndFeel() {
//		getProvider().deployLookAndFeel();
//	}

	/**
	 * 部署功能管理 
	 * 
	 * @param projectModuleName
	 */
	public static void deployManagerApps(String projectModuleName) {
		getProvider().deployManagerApps(projectModuleName);
	}

	/**
	 * 部署page页
	 * 
	 * @param projectModuleName
	 * @param pageName
	 */
	public static void deployPage(String projectModuleName, String pageName) {
		getProvider().deployPage(projectModuleName,pageName);
	}

	/**
	 * 部署portletapp
	 * 
	 * @param projectModuleName
	 */
	public static void deployPortletApp(String projectModuleName) {
		getProvider().deployPortletApp(projectModuleName);
	}

	/**
	 * 部署插件
	 * 
	 * @param projectModuleName
	 */
	public static void deployPtPlugin(String projectModuleName) {
		getProvider().deployPtPlugin(projectModuleName);
	}

	/**
	 * 部署皮肤
	 * 
	 * @param projectModuleName
	 */
	public static void deploySkin(String projectModuleName) {
		getProvider().deploySkin(projectModuleName);
	}
	
	
}
