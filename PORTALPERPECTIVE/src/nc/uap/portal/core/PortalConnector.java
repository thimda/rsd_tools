package nc.uap.portal.core;

import java.io.File;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.ClassTool;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.deploy.vo.PortalDeployDefinition;
import nc.uap.portal.deploy.vo.PortalModule;
import nc.uap.portal.om.Display;
import nc.uap.portal.om.LookAndFeel;
import nc.uap.portal.om.ManagerApps;
import nc.uap.portal.om.Page;
import nc.uap.portal.om.PortletDisplay;
import nc.uap.portal.om.PortletDisplayCategory;
import nc.uap.portal.om.Skin;
import nc.uap.portal.om.SkinDescription;
import nc.uap.portal.perspective.PortalPlugin;
import nc.uap.portal.plugins.model.PtPlugin;
import nc.uap.portal.service.itf.IPortalDesignDataProvider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * service��������
 * 
 * @author dingrf
 *
 */
public class PortalConnector {

	@SuppressWarnings("unchecked")
	private static IPortalDesignDataProvider getProvider() {
		IProject[] projects = LFWPersTool.getLFwProjects();
		//IProject[] bcpProjects = LFWPersTool.getBcpProject();
		for (int i = 0; i < projects.length; i++) {
			try {
				Class c = ClassTool.loadClass("nc.uap.portal.service.impl.PortalDesignDataProviderImpl", projects[i], IPortalDesignDataProvider.class.getClassLoader());
				Thread.currentThread().setContextClassLoader(c.getClassLoader());
				System.out.println(c.getClassLoader());
				System.out.println(IPortalDesignDataProvider.class.getClassLoader());
				return (IPortalDesignDataProvider) c.newInstance();
			} 
			catch (Exception e) {
				PortalPlugin.getDefault().logError(e.getMessage(),e);
			}
		}
		return null;
	}
	
	/**
	 * ����ļ�
	 * @param filePath
	 * @param fileName
	 */
	public static void checkFile(String filePath, String fileName){
		String path = filePath + "/" + fileName;
		MainPlugin.getDefault().logInfo("�ļ�·����" + path);
		LFWAMCPersTool.checkOutFile(path);
	}
	
	/**
	 * ��ȡ�ļ�·��
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	private static String getPortalSpecPath(String projectPath,String projectModuleName){
		return projectPath +"/src/portalspec/" + projectModuleName +  "/portalspec/";
	}
	
	/**
	 * ��ȡ��Ŀ�����е�Portlet
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	public static List<PortletDefinition> getAllPortlet(String projectPath,String projectModuleName){
		return getProvider().getAllPortlets(projectPath,projectModuleName);
	}
	
	/**
	 * ��ȡ��Ŀ�е�����ManagerApps
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	public static List<ManagerApps> getAllManagerApps(String projectPath,String projectModuleName){
		return getProvider().getManagerApps(projectPath,projectModuleName);
	}

	/**
	 * ��ȡ��Ŀ�е�ManagerApps
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param managerId
	 * @return
	 */
	public static ManagerApps getManagerApps(String projectPath,String projectModuleName,String managerId){
		return getProvider().getManagerApps(projectPath,projectModuleName,managerId);
	}

	/**
	 * ɾ��ManagerApps
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param id
	 */
	public static void deleteManagerApps(String projectPath,String projectModuleName,String id){
		getProvider().deleteManagerApps(projectPath, projectModuleName, id);
		refreshCurrentPorject();
	}
	
	/**
	 * ����ManagerApps
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param managerApps
	 */
	public static void saveManagerAppsToXml(String projectPath,String projectModuleName,ManagerApps managerApps){
//		getProvider().saveManagerApps(projectPath, projectModuleName, managerApps);
//		refreshCurrentPorject();
	}

	/**
	 * ȡportalModule
	 * 
	 * @param projectPath
	 * @return
	 */
	public static PortalDeployDefinition getPortalDefinition(String projectPath){
		return getProvider().getPortalModule(projectPath);
	}
	
	/**ȡportalModule
	 * 
	 * @param file
	 * @return
	 */
	public static PortalModule getPortalModule(File file){
		return getProvider().getPortalModule(file);
	}

	/**
	 * ��ȡ��Ŀ�е� PortletApplicationDefinition
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	public static PortletApplicationDefinition getPortletApp(String projectPath,String projectModuleName){
		return getProvider().getPortletApp(projectPath,projectModuleName);
	}

	/**
	 * ͨ���ַ����õ� PortletApplicationDefinition
	 * @param projectPath
	 * @param projectModuleName
	 * @param portletAppText
	 * @return
	 */
	public static PortletApplicationDefinition getPortletApp(String projectPath,String projectModuleName,String portletAppText){
		return getProvider().getPortletApp(projectPath,projectModuleName,portletAppText);
	}
	
	/**
	 * ����PortletApplicationDefinition
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param portletApp
	 */
	public static void savePortletAppToXml(String projectPath,String projectModuleName, PortletApplicationDefinition portletApp){
		String filePath = getPortalSpecPath(projectPath, projectModuleName);
		String fileName = "portlet.xml";
		checkFile(filePath, fileName);
		getProvider().savePortletAppToXml(projectPath,projectModuleName, portletApp);
		refreshCurrentPorject();
	}

	/**
	 *����portlet 
	 * 
	 */
	public static void savePortletToXml(String projectPath,String projectModuleName, PortletDefinition portlet,String categoryId){
		PortletApplicationDefinition  portletApp = PortalConnector.getPortletApp(projectPath,projectModuleName);
		if (portletApp==null){
			portletApp = new PortletApplicationDefinition(); 
		}
		PortletDefinition pd = null;
		if(portletApp.getPortlet(portlet.getPortletName())==null){
			portletApp.getPortlets().add(portlet);
		}else{
			pd = portletApp.getPortlet(portlet.getPortletName()); 
			portletApp.getPortlets().remove(pd);
			portletApp.getPortlets().add(portlet);
		}
		savePortletAppToXml(projectPath,projectModuleName, portletApp);
		
		//дportlet����
		if (categoryId != null){
			Display  display = PortalConnector.getDisplay(projectPath, projectModuleName);
			for (PortletDisplayCategory pdc : display.getCategory()){
				if (pdc.getId().equals(categoryId)){
					Boolean isEdit = false;
					for (PortletDisplay portletDisplay : pdc.getPortletDisplayList()){
						if (portletDisplay.getId().equals(portlet.getPortletName())){
							portletDisplay.setTitle(portlet.getDisplayName().get(0).getDisplayName());
							isEdit = true;
							break;
						}
					}
					if (!isEdit){
						PortletDisplay portletDisplay = new PortletDisplay();
						portletDisplay.setId(portlet.getPortletName());
						portletDisplay.setDynamic(true);
						portletDisplay.setTitle(portlet.getDisplayName().size()>0?portlet.getDisplayName().get(0).getDisplayName():"");
						pdc.addPortletDisplayList(portletDisplay);
					}
					break;
				}
			}
			saveDisplayToXml(projectPath,projectModuleName, display);
		}

		refreshCurrentPorject();
	}
	
	/**
	 * ��ȡ��Ŀ�е� PortalModule ����
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	public static PortalModule getPortal(String projectPath,String projectModuleName){
		PortalModule portalModule = getProvider().getPortal(projectPath, projectModuleName);
		return portalModule;
	}

	/**
	 * ������ĿPortalModule
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param portalModule
	 */
	public static void savePortalToXml(String projectPath,String projectModuleName,PortalModule portalModule){
		String filePath = getPortalSpecPath(projectPath, projectModuleName);
		String fileName = "portal.xml";
		checkFile(filePath, fileName);
		getProvider().savePortalToXml(projectPath, projectModuleName, portalModule);
		refreshCurrentPorject();
	}
	
	/**
	 * ��ȡ��Ŀ���
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	public static PtPlugin getPtPlugin(String projectPath,String projectModuleName){
		PtPlugin p = getProvider().getPtPlugin(projectPath, projectModuleName);
		return p;
		//return getProvider().getPtPlugin(projectPath, projectModuleName);
	}
	
	/**
	 * ������Ŀ���
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param ptPlugin
	 */
	public static void savePtPluginToXml(String projectPath,String projectModuleName, PtPlugin ptPlugin){
		String filePath = getPortalSpecPath(projectPath, projectModuleName);
		String fileName = "plugin.xml";
		checkFile(filePath, fileName);
		getProvider().savePtPluginToXml(projectPath,projectModuleName, ptPlugin);
		refreshCurrentPorject();
	}
	
	/**
	 * ��ȡ��Ŀportlet����,������������Ŀ
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	public static List<Display> getAllDisplays(String projectPath,String projectModuleName){
		return getProvider().getAllDisplays(projectPath, projectModuleName);
	}
	
	/**
	 * ��ȡ��Ŀportlet����
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @return
	 */
	public static Display getDisplay(String projectPath,String projectModuleName){
		Display d = getProvider().getDisplay(projectPath, projectModuleName);
		return d;
	}
	
	/**
	 * ������Ŀportlet����
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param display
	 */
	public static void saveDisplayToXml(String projectPath,String projectModuleName, Display display){
		String filePath = getPortalSpecPath(projectPath, projectModuleName);
		String fileName = "display.pml";
		checkFile(filePath, fileName);
		getProvider().saveDisplayToXml(projectPath,projectModuleName, display);
		refreshCurrentPorject();
	}

    /**
     * ��ȡ��ĿPage�����б�
     * 
     * @param projectPath
     * @param projectModuleName
     * @return
     */
	public static Page[] getAllPages(String projectPath,String projectModuleName){
		return getProvider().getAllPages(projectPath,projectModuleName);
	}

	/**
	 * ��ȡ��ĿPage����
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param pageName
	 * @return
	 */
	public static Page getPage(String projectPath,String projectModuleName,String pageName){
		return getProvider().getPage(projectPath, projectModuleName, pageName);
	}
	
	/**
	 * Page����ת����XML�ַ���
	 * 
	 * @param page
	 * @return
	 */
	public static String  pageToString(Page page){
		return getProvider().pageToString(page);
	}

	/**
	 * XML�ַ���ת����Page����
	 * 
	 * @param xml
	 * @return
	 */
	public static Page stringToPage(String xml){
		return getProvider().stringToPage(xml);
	}

	/**
	 * ����Page����
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param fileName
	 * @param xml
	 */
	public static void savePageToXml(String projectPath,String projectModuleName,String fileName,String xml){
		String filePath = getPortalSpecPath(projectPath, projectModuleName) + "pml/";
		if(fileName.endsWith(".pml")){
			checkFile(filePath, fileName);
		}else{
			checkFile(filePath, fileName + ".pml");
		}
		getProvider().savePageToXml(projectPath, projectModuleName, fileName,xml);
		refreshCurrentPorject();
	}

	/**
	 * ����Page����
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param page
	 */
	public static void savePageToXml(String projectPath,String projectModuleName,Page page){
		String filePath = getPortalSpecPath(projectPath, projectModuleName) + "pml/";
		String fileName = page.getPagename() + ".pml";
		checkFile(filePath, fileName);
		getProvider().savePageToXml(projectPath, projectModuleName,page);
		refreshCurrentPorject();
	}
	
	/**
	 * ɾ����Ŀ�е�Page
	 * 
	 * @param projectPath
	 * @param projectModuleName
	 * @param pageName
	 */
	public static void deletePage(String projectPath,String projectModuleName,String pageName){
		getProvider().deletePage(projectPath, projectModuleName,pageName);
		refreshCurrentPorject();
	}
	
    /**
     * ��ȡ��ĿLookAndFeel����
     * 
     * @param projectPath
     * @param projectModuleName
     * @return
     */
    public static LookAndFeel getLookAndFeel(String projectPath,String projectModuleName){
    	return getProvider().getLookAndFeel(projectPath,projectModuleName);
    }
    
    /**
     * ����LookAndFeel
     * 
     * @param projectPath
     * @param projectModuleName
     * @return
     */
    public static void saveLookAndFeelToXml(String projectPath,String projectModuleName,LookAndFeel lookAndFeel){
    	String filePath = projectPath + "/web/WEB-INF/conf/";
    	String fileName = "look-and-feel.xml";
    	checkFile(filePath, fileName);
    	getProvider().saveLookAndFeelToXml(projectPath, projectModuleName, lookAndFeel);
    }
    
    /**
     * ��ȡ��ʽ�б�
     * 
     * @param projectPath
     * @param projectModuleName
     * @param type
     * @return
     */
    public static List<Skin> getSkins(String projectPath,String projectModuleName,String type){
    	return getProvider().getSkins(projectPath, projectModuleName, type);
    }

    /**
     * ��ȡ��ʽ�б�,��������ģ��
     * 
     * @param projectPath
     * @param projectModuleName
     * @param type
     * @return
     */
    public static List<Skin> getAllSkins(String projectPath,String projectModuleName,String type){
    	return getProvider().getAllSkins(projectPath, projectModuleName, type);
    }
    
    /**
     * ��ȡ��ʽ�б�
     * 
     * @param projectPath
     * @param projectModuleName
     * @param type
     * @param themeId
     * @return
     */
    public static SkinDescription getSkinDescription(String projectPath,String projectModuleName,String type,String themeId){
    	return getProvider().getSkinDescription(projectPath, projectModuleName, type,themeId);
    }

    /**
     * ������ʽ�����ļ�
     * 
     * @param projectPath
     * @param projectModuleName
     * @param type
     * @param themeId
     * @param skinDescription
     */
    public static void saveSkinDescription(String projectPath,String projectModuleName,String type,String themeId,SkinDescription skinDescription){
    	String filePath = getPortalSpecPath(projectPath,projectModuleName) + "/ftl/portaldefine/skin/" + themeId + "/" + type + "/";
    	String fileName = "description.xml";
    	checkFile(filePath, fileName);
    	getProvider().saveSkinDescription(projectPath, projectModuleName, type,themeId,skinDescription);
    }

    /**
     * ������ʽ�ļ�
     * 
     * @param projectPath
     * @param projectModuleName
     * @param type
     * @param themeId
     * @param fileName
     * @param fileText
     */
    public static void createSkinFile(String projectPath,String projectModuleName,String type,String themeId,String fileName,String fileText){
    	getProvider().createSkinFile(projectPath, projectModuleName, type, themeId, fileName, fileText);
    	refreshCurrentPorject();
    } 

    /**
     * ɾ����ʽ�ļ�
     * 
     * @param projectPath
     * @param projectModuleName
     * @param type
     * @param themeId
     * @param fileName
     */
    public static void deleteSkinFile(String projectPath,String projectModuleName,String type,String themeId,String fileName){
    	getProvider().deleteSkinFile(projectPath, projectModuleName, type, themeId, fileName);
    	refreshCurrentPorject();
    } 
    
    /**
     * ������ʽ�ļ���
     * 
     * @param projectPath
     * @param projectModuleName
     * @param themeId
     */
    public static void createThemeFolder(String projectPath,String projectModuleName,String themeId){
    	getProvider().createThemeFolder(projectPath, projectModuleName, themeId);
    	refreshCurrentPorject();
    }
    
    /**
     * ɾ����ʽ�ļ���
     * 
     * @param projectPath
     * @param projectModuleName
     * @param themeId
     */
    public static void deleteThemeFolder(String projectPath,String projectModuleName,String themeId){
    	getProvider().deleteThemeFolder(projectPath, projectModuleName, themeId);
    	refreshCurrentPorject();
    }
    
    /**
     * ��ȡ����ҳ��Map 
     * 
     * @param projPaths
     * @return
     */
	public static  Map<String, String>[] getPageNames(String[] projPaths){
		return getProvider().getPageNames(projPaths);
	}

	/**
	 * ˢ�µ�ǰporject
	 * @throws CoreException 
	 */
	private static void refreshCurrentPorject(){
		IProject project = LFWPersTool.getCurrentProject();
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			PortalPlugin.getDefault().logError(e.getMessage(),e);
		} 
	}
}
