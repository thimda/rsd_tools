package nc.uap.lfw.perspective.project;

import java.io.File;

/**
 * Ê÷ÐÍ½Úµã
 * @author zhangxya
 *
 */
public interface ILFWTreeNode {
	public static final String PARENT_NODE_FOLDER = "PARENT_NODE_FOLDER";
	public static final String PARENT_PUB_REF_FOLDER = "PARENT_PUB_REF_FOLDER";
	public static final String NODE_FOLDER = "NODE_FOLDER";
	public static final String PUB_REF_FOLDER = "PUB_REF_FOLDER";

	public static final String PARENT_PUB_DS_FOLDER = "PARENT_PUB_DS_FOLDER";
	
	public static final String PARENT_PUB_WIDGET_FOLDER = "PARENT_PUB_WIdget_FOLDER";
	
	public static final String PAGEFLOW_FOLDER = "PAGEFLOW_FOLDER";
	
	public static final String PORTAL_DEFINE = "PORTAL_DEFINE";
	
	public static String PORTALDEFINE="PORTALDEFINE";
	public static String PORTALPAGE="PORTALPAGE";
	public static String PORTLET="PORTLET";
	public static String PORTLETS="PORTLETS";
	public static String MANAGERAPPS="MANAGERAPPS";
	public static String THEMES = "THEMES";
	public static String PLUGIN = "PLUGIN";
	public static String EVENTS = "EVENTS";
	public static String PORTAL = "PORTAL";
	public static String PORTALMANPAGE="PORTALMANPAGE";
	public static String PORTALUSERPAGE="PORTALUSERPAGE";
	public static String MANAEXTENPOINT="MANAEXTENPOINT";
	public static String PORTALMANAGE="PORTALMANAGE";
	public static String CMSMANAGE="CMSMANAGE";
	public static String PORTLETTHEMEEDIT="PORTLETTHEMEEDIT";
	public static String LAYOUTTHEME="LAYOUTTHEME";
	public static String POOLDSFOLDER = "POOLDSFOLDER";
	public static String POOLWIDGETFOLDER = "poolWidgetFolder";
	public static String POOLREFNODEFOLDER = "POOLREFNODEFOLDER";
	
	public static final String APPLICATION_FOLDER = "APPLICATION_FOLDER";
	public static final String APPLICATION = "APPLICATION";
	public static final String MODEL_FOLDER = "MODEL_FOLDER";
	public static final String MODEL = "MODEL";
	public static final String WINDOW_FOLDER = "WINDOW_FOLDER";
	public static final String WINDOW = "WINDOW";
	public static final String VIEW = "VIEW";
	public static final String PUBLIC_VIEW_FOLDER = "PUBLIC_VIEW_FOLDER";
	public static final String PUBLIC_VIEW = "PUBLIC_VIEW";
	
	public File getFile();
	public void deleteNode();
	public String getIPathStr();
}
