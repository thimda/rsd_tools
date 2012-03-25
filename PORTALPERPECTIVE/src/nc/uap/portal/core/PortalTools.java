package nc.uap.portal.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.perspective.PortalPlugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * pottal������
 */

public class PortalTools {

	/**HOTWEBS·��**/
	private static final String HOTWEBS = "/hotwebs";
	
	/**Portalģ�鶨���ļ�**/
	private static final String PORTAL_DEF = "/WEB-INF/portal.xml";
	
	/**
	 * ����portal����
	 * @return
	 */
	public static IProject[] getPortalProjects(){
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<IProject> lfwProjects = new ArrayList<IProject>();
		for (int i = 0; i < projects.length; i++) {
			try {
				if(projects[i].hasNature(WEBProjConstants.MODULE_NATURE))
					lfwProjects.add(projects[i]);
			} 
			catch (CoreException e) {
				PortalPlugin.getDefault().logError(e);
			}
		}
		return lfwProjects.toArray(new IProject[lfwProjects.size()]);
	}
	
	/**
	 * ��ȡuapportal����
	 * @return
	 */
	public static IProject getPortalProject(){
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			if(projects[i].getName().equals("uapportal"))
				return projects[i];
		}
		return null;
	}
	
	/**
	 * ������в�����BCP������̵�portal����
	 * @return
	 */
	public static IProject[] getPortalNotBCPJavaProjects(){
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<IProject> portalProjects = new ArrayList<IProject>();
		for (int i = 0; i < projects.length; i++) {
			try {
				if(projects[i].isOpen() && projects[i].hasNature(WEBProjConstants.PORTAL_MODULE_NATURE) && !projects[i].hasNature(WEBProjConstants.BCPMODULE_NATURE))
					portalProjects.add(projects[i]);
			} 
			catch (CoreException e) {
				PortalPlugin.getDefault().logError(e);
				
			}
		}
		return portalProjects.toArray(new IProject[portalProjects.size()]);
	}

	/**
	 * ��������������
	 * @return
	 */
	public static IProject[] getPortalBcpProject(){
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<IProject> bcpProjects = new ArrayList<IProject>();
		for (int i = 0; i < projects.length; i++) {
			try {
				if(projects[i].isOpen() && projects[i].hasNature(WEBProjConstants.PORTAL_MODULE_NATURE) && projects[i].hasNature(WEBProjConstants.BCPMODULE_NATURE))
					bcpProjects.add(projects[i]);
			} 
			catch (CoreException e) {
				PortalPlugin.getDefault().logError(e);
				
			}
		}
		return bcpProjects.toArray(new IProject[bcpProjects.size()]);
	}

	
	/**
	 * �������portalģ��
	 * @return
	 */
	public static  List<String> getPortalModules(){
		List<String> portalModules = new ArrayList<String>();
		IPath ncHome = new Path("NC_HOME");
		String dir = ncHome.toString() + HOTWEBS ;
		File moduleDir = new File(dir);
		/**
		 * ���ģ���б�
		 */
		File[] modules = moduleDir.listFiles();
		/**
		 * ����Ƿ�Portalģ��
		 */
		for (int i = 0; i < modules.length; i++) {
			File module = modules[i];
			if (module.isDirectory()) {
				String name = module.getName();
				String portalFlag = module.getPath()+PORTAL_DEF;
				if(new File(portalFlag).exists())
 					portalModules.add(name);
			}
		}
		
		/**
		 * temp 
		 */
		if(portalModules.isEmpty()){
			portalModules.add("pbase");
			portalModules.add("pmng");
			portalModules.add("pserver");
		}
		return portalModules;
	}
	
	/**
	 * ��portletApp�и���id����portlet
	 * 
	 * @param portletApp
	 * @param portletId
	 * @return
	 */
	public static PortletDefinition getPortletById(PortletApplicationDefinition portletApp,String portletId){
		for (PortletDefinition p : portletApp.getPortlets()){
			if (p.getPortletName().equals(portletId)){
				return p;
			}
		}
		return null;
	}
}
