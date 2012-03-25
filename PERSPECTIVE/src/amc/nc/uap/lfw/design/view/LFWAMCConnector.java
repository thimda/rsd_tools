/**
 * 
 */
package nc.uap.lfw.design.view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.WidgetEditorInput;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.ClassTool;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.AMCServiceObj;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.core.uimodel.conf.Model;
import nc.uap.lfw.design.itf.ILfwAMCDesignDataProvider;
import nc.uap.lfw.design.itf.MdComponnetVO;
import nc.uap.lfw.editor.common.editor.LFWBrowserEditor;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.editor.common.tools.ViewEventTool;
import nc.uap.lfw.editor.publicview.PublicViewBrowserEditor;
import nc.uap.lfw.editor.publicview.PublicViewEditorInput;
import nc.uap.lfw.editor.view.ViewBrowserEditor;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.project.ILFWTreeNode;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorInput;

/**
 * 
 * UAPWEB服务连接类
 * @author chouhl
 *
 */
public class LFWAMCConnector extends LFWConnector {
	
	/**
	 * 获取上下文
	 * @return
	 */
	private static String getContextPath(){
		String ctxPath = LfwCommonTool.getLfwProjectCtx(LFWAMCPersTool.getCurrentProject());
		if(!ctxPath.startsWith("/")){
			ctxPath = "/" + ctxPath;
		}
		return ctxPath;
	}
	
	/**
	 * 检查文件
	 * @param filePath
	 * @param fileName
	 */
	private static void checkFile(String filePath, String fileName){
		String path = filePath + "/" + fileName;
		MainPlugin.getDefault().logInfo("文件路径：" + path);
		LFWAMCPersTool.checkOutFile(path);
	}
	
	/************************OperateWebElementXML************************/
	/**
	 * 更新Application（XML文件）
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param app
	 */
	public static void updateApplicationToXml(String filePath, String fileName, String projectPath, Application app){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.UpdateApplicationXml);
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		amcServiceObj.setCurrentProjPath(projectPath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		amcServiceObj.setAppConf(app);
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 更新Application（类文件、XML文件）
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param app
	 */
	public static void updateApplication(String filePath, String fileName, String projectPath, Application app){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		//操作类型
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.UpdateAppXmlAndController);
		//Application
		amcServiceObj.setAppConf(app);
		//当前工程路径
		amcServiceObj.setCurrentProjPath(projectPath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		//XML文件
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		//事件
		EventConf[] events = app.getEventConfs();
		if(events != null && events.length > 0){
			Map<String, List<EventConf>> eventsMap = new HashMap<String, List<EventConf>>();
			for(EventConf event : events){
				if(event.getClassFilePath() != null && event.getClassFileName() != null){
					List<EventConf> list = eventsMap.get(event.getClassFilePath() + File.separator + event.getClassFileName());
					if(list == null){
						list = new ArrayList<EventConf>();
					}
					list.add(event);
					eventsMap.put(event.getClassFilePath() + File.separator + event.getClassFileName(), list);
				}
			}
			String key = null;
			Iterator<String> keys = eventsMap.keySet().iterator();
			while(keys.hasNext()){
				key = keys.next();
				MainPlugin.getDefault().logInfo("类文件路径：" + key);
				LFWAMCPersTool.checkOutFile(key);
			}
			amcServiceObj.setEventsMap(eventsMap);
		}
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		if(events != null && events.length > 0){
			for(EventConf event : events){
				if(event.getEventStatus() == EventConf.ADD_STATUS){
					event.setEventStatus(EventConf.NORMAL_STATUS);
				}else if(event.getEventStatus() == EventConf.DEL_STATUS){
					app.removeEventConf(event.getName(), event.getMethodName());
				}
			}
		}
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 创建application（生成XML文件和Controller类文件）
	 * @param packageName
	 * @param className
	 * @param classFilePath
	 * @param classFileName
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param appConf
	 */
	public static void createApplication(String packageName, String className, String classFilePath, String classFileName, String filePath, String fileName, String projectPath, Application appConf){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		//操作类型
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.CreateApplicationXml);
		//ApplicationConf
		amcServiceObj.setAppConf(appConf);
		//当前工程路径
		amcServiceObj.setCurrentProjPath(projectPath);
		//XML文件
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		//Controller类文件
		checkFile(classFilePath, classFileName);
		amcServiceObj.setPackageName(packageName);
		amcServiceObj.setClassName(className);
		amcServiceObj.setClassFilePath(classFilePath);
		amcServiceObj.setClassFileName(classFileName);
		//上下文
		String ctxPath = getContextPath();
		amcServiceObj.setCtxPath(ctxPath);
		//UAPWEB服务
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		//刷新当前工程
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 创建model（生成XML文件）
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param model
	 */
	public static void createModel(String filePath, String fileName, String projectPath, Model model){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.CreateModelXml);
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		amcServiceObj.setCurrentProjPath(projectPath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		amcServiceObj.setModel(model);
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 创建window（生成XML文件和Controller类文件）
	 * @param packageName
	 * @param className
	 * @param classFilePath
	 * @param classFileName
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param pageMeta
	 */
	public static void createWindow(String packageName, String className, String classFilePath, String classFileName, String filePath, String fileName, String projectPath, PageMeta pageMeta, UIMeta uimeta){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		//操作类型
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.CreateWindowXml);
		//PageMeta
		amcServiceObj.setPageMeta(pageMeta);
		//当前工程路径
		amcServiceObj.setCurrentProjPath(projectPath);
		//XML文件
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		//Controller类文件
		checkFile(classFilePath, classFileName);
		amcServiceObj.setPackageName(packageName);
		amcServiceObj.setClassName(className);
		amcServiceObj.setClassFilePath(classFilePath);
		amcServiceObj.setClassFileName(classFileName);
		//上下文
		String ctxPath = getContextPath();
		amcServiceObj.setCtxPath(ctxPath);
//		//流式布局
//		amcServiceObj.setFlowlayout(isFlowlayout);
		amcServiceObj.setUimeta(uimeta);
		//UAPWEB服务
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		//刷新当前工程
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 更新Window（XML文件）
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param winConf
	 */
	public static void updateWindowToXml(String filePath, String fileName, String projectPath, PageMeta winConf){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.UpdateWindowXml);
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		amcServiceObj.setCurrentProjPath(projectPath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		amcServiceObj.setPageMeta(winConf);
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		LFWPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 更新Window（类文件、XML文件）
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param app
	 */
	public static void updateWindow(String filePath, String fileName, String projectPath, PageMeta winConf){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		//操作类型
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.UpdateWinXmlAndController);
		//Window
		amcServiceObj.setPageMeta(winConf);
		//当前工程路径
		amcServiceObj.setCurrentProjPath(projectPath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		//XML文件
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		//事件
		EventConf[] events = winConf.getEventConfs();
		if(events != null && events.length > 0){
			Map<String, List<EventConf>> eventsMap = new HashMap<String, List<EventConf>>();
			for(EventConf event : events){
				if(event.getClassFilePath() != null && event.getClassFileName() != null){
					List<EventConf> list = eventsMap.get(event.getClassFilePath() + File.separator + event.getClassFileName());
					if(list == null){
						list = new ArrayList<EventConf>();
					}
					list.add(event);
					eventsMap.put(event.getClassFilePath() + File.separator + event.getClassFileName(), list);
				}
			}
			String key = null;
			Iterator<String> keys = eventsMap.keySet().iterator();
			while(keys.hasNext()){
				key = keys.next();
				MainPlugin.getDefault().logInfo("类文件路径：" + key);
				LFWAMCPersTool.checkOutFile(key);
			}
			amcServiceObj.setEventsMap(eventsMap);
		}
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		if(events != null && events.length > 0){
			for(EventConf event : events){
				if(event.getEventStatus() == EventConf.ADD_STATUS){
					event.setEventStatus(EventConf.NORMAL_STATUS);
				}else if(event.getEventStatus() == EventConf.DEL_STATUS){
					winConf.removeEventConf(event.getName(), event.getMethodName());
				}
			}
		}
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 创建View（生成XML文件和Controller类文件）
	 * @param packageName
	 * @param className
	 * @param classFilePath
	 * @param classFileName
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param viewConf
	 * @param isFlowlayout
	 */
	public static void createView(String packageName, String className, String classFilePath, String classFileName, String filePath, String fileName, String projectPath, LfwWidget viewConf, UIMeta uimeta){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.CreateViewXml);

		amcServiceObj.setLfwWidget(viewConf);
		
		amcServiceObj.setCurrentProjPath(projectPath);
		
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		
		if(classFileName != null)
			checkFile(classFilePath, classFileName);
		amcServiceObj.setPackageName(packageName);
		amcServiceObj.setClassName(className);
		amcServiceObj.setClassFilePath(classFilePath);
		amcServiceObj.setClassFileName(classFileName);
		
//		amcServiceObj.setFlowlayout(isFlowlayout);
//		amcServiceObj.setCreateUIMeta(createUIMeta);
		
		amcServiceObj.setUimeta(uimeta);
		
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 更新View（类文件、XML文件）
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param app
	 */
	public static void updateView(String filePath, String fileName, String projectPath, LfwWidget viewConf){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		//操作类型
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.UpdateViewXmlAndController);
		//View
		amcServiceObj.setLfwWidget(viewConf);
		//当前工程路径
		amcServiceObj.setCurrentProjPath(projectPath);
		//XML文件
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		//事件
		EventConf[] events = ViewEventTool.getAllEvents(viewConf, null);
		if(events != null && events.length > 0){
			Map<String, List<EventConf>> eventsMap = new HashMap<String, List<EventConf>>();
			for(EventConf event : events){
				if(event.getClassFilePath() != null && event.getClassFileName() != null){
					List<EventConf> list = eventsMap.get(event.getClassFilePath() + File.separator + event.getClassFileName());
					if(list == null){
						list = new ArrayList<EventConf>();
					}
					list.add(event);
					eventsMap.put(event.getClassFilePath() + File.separator + event.getClassFileName(), list);
				}
			}
			String key = null;
			Iterator<String> keys = eventsMap.keySet().iterator();
			while(keys.hasNext()){
				key = keys.next();
				MainPlugin.getDefault().logInfo("类文件路径：" + key);
				LFWAMCPersTool.checkOutFile(key);
			}
			amcServiceObj.setEventsMap(eventsMap);
		}
		//sessionId
		LFWBrowserEditor editorPart = null;
		List<LFWBrowserEditor> editors = LFWTool.getAllWebEditors();
		if(editors != null && editors.size() > 0){
			for(LFWBrowserEditor editor : editors){
				if(editor instanceof ViewBrowserEditor){
					IEditorInput input = ((ViewBrowserEditor)editor).getEditorInput();
					if(input instanceof WidgetEditorInput){
						if(viewConf.getPagemeta() != null && viewConf.getPagemeta().getId().equals(((WidgetEditorInput)input).getWidget().getPagemeta().getId())){
							if(viewConf.getId().equals(((WidgetEditorInput)input).getWidget().getId())){
								amcServiceObj.setSessionId(((ViewBrowserEditor)editor).getSessionId());
								editorPart = editor;
								break;
							}
						}
					}
				}else if(editor instanceof PublicViewBrowserEditor){
					IEditorInput input = ((PublicViewBrowserEditor)editor).getEditorInput();
					if(input instanceof PublicViewEditorInput){
						if(viewConf.getId().equals(((PublicViewEditorInput)input).getWidget().getId())){
							amcServiceObj.setSessionId(((PublicViewBrowserEditor)editor).getSessionId());
							editorPart = editor;
							break;
						}
					}
				}
			}
		}
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		if(events != null && events.length > 0){
			for(EventConf event : events){
				if(event.getEventStatus() == EventConf.ADD_STATUS){
					event.setEventStatus(EventConf.NORMAL_STATUS);
				}else if(event.getEventStatus() == EventConf.DEL_STATUS){
					ViewEventTool.removeEvent(viewConf, event.getName(), event.getMethodName());
				}
			}
		}
		if(editorPart != null){
			if(editorPart instanceof ViewBrowserEditor){
				((ViewBrowserEditor)editorPart).execute("refreshDs();");
			}else if(editorPart instanceof PublicViewBrowserEditor){
				((PublicViewBrowserEditor)editorPart).execute("refreshDs();");
			}
		}
		LFWAMCPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 更新View（XML文件）
	 * @param filePath
	 * @param fileName
	 * @param projectPath
	 * @param viewConf
	 */
	public static void updateViewToXml(String filePath, String fileName, String projectPath, LfwWidget viewConf){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.UpdateWidgetXml);
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		amcServiceObj.setCurrentProjPath(projectPath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		amcServiceObj.setLfwWidget(viewConf);
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
		LFWPersTool.refreshCurrentPorject();
	}
	
	/**
	 * 创建UIMeta（XML文件）
	 * @param folderPath
	 */
	public static void createUIMeta(String filePath, String fileName, UIMeta meta){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.CreateUIMeta);
		checkFile(filePath, fileName);
		amcServiceObj.setFilePath(filePath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		amcServiceObj.setUimeta(meta);
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
	}	
	
	/**
	 * 保存session中PageMeta和UIMeta到XML文件中
	 * @param sessionId
	 * @param pageMetaId
	 * @param nodePath
	 */
	public static void savePageMetaAndUIMetaFromSessionCache(String sessionId, String pageMetaId, String nodePath){
		checkFile(nodePath, WEBProjConstants.AMC_WINDOW_FILENAME);
		checkFile(nodePath, WEBProjConstants.AMC_UIMETA_FILENAME);
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.SavePageMetaAndUIMetaFromSessionCache);
		amcServiceObj.setSessionId(sessionId);
		PageMeta pageMeta = new PageMeta();
		pageMeta.setId(pageMetaId);
		amcServiceObj.setPageMeta(pageMeta);
		amcServiceObj.setAmcNodePath(nodePath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
	}
	
	/**
	 * 保存session中View和UIMeta到XML文件中
	 * @param sessionId
	 * @param pageMetaId
	 * @param nodePath
	 */
	public static void saveWidgetAndUIMetaFromSessionCache(String sessionId, String pageMetaId, String widgetId, String nodePath){
		checkFile(nodePath, WEBProjConstants.AMC_VIEW_FILENAME);
		checkFile(nodePath, WEBProjConstants.AMC_UIMETA_FILENAME);
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElementXMLType(AMCServiceObj.SaveWidgetAndUIMetaFromSessionCache);
		amcServiceObj.setSessionId(sessionId);
		PageMeta pageMeta = new PageMeta();
		pageMeta.setId(pageMetaId);
		amcServiceObj.setPageMeta(pageMeta);
		LfwWidget widget = new LfwWidget();
		widget.setId(widgetId);
		amcServiceObj.setLfwWidget(widget);
		amcServiceObj.setAmcNodePath(nodePath);
		//上下文
		amcServiceObj.setRootPath(getContextPath());
		getAMCDesignDataProvider().operateWebElementXML(amcServiceObj);
	}
	
	/************************OperateVO************************/
	/**
	 * 获取在Model节点中已存在的组件
	 * @return
	 */
	public static List<MdComponnetVO> getExistComponents(){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateVO(AMCServiceObj.GetExistComponentIds);
		String projPath = LFWAMCPersTool.getProjectPath();
		amcServiceObj.setCurrentProjPath(projPath);
		amcServiceObj.setItemType(ILFWTreeNode.MODEL);
		amcServiceObj.setAmcNodePath(WEBProjConstants.AMC_MODEL_PATH);
		amcServiceObj.setSuffix(".mod");
		amcServiceObj.setTagName(WEBProjConstants.MODEL_SUB);
		return getAMCDesignDataProvider().operateVO(amcServiceObj).getComponentVOList();
	}
	
	/************************OperateWebElement************************/
	/**
	 * 获取树节点名称
	 * @param projPaths
	 * @param itemType
	 * @param nodePath
	 * @return
	 */
	public static Map<String, String>[] getTreeNodeNames(String[] projPaths, String itemType, String nodePath){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetAMCNames);
		amcServiceObj.setProjPaths(projPaths);
		amcServiceObj.setItemType(itemType);
		if(ILFWTreeNode.APPLICATION.equals(itemType)){
			if(nodePath == null){
				nodePath = WEBProjConstants.AMC_APPLICATION_PATH;
			}
			amcServiceObj.setAmcNodePath(nodePath);
			amcServiceObj.setSuffix(".app");
			amcServiceObj.setTagName(WEBProjConstants.APPLICATION_SUB);
		}else if(ILFWTreeNode.MODEL.equals(itemType)){
			if(nodePath == null){
				nodePath = WEBProjConstants.AMC_MODEL_PATH;
			}
			amcServiceObj.setAmcNodePath(nodePath);
			amcServiceObj.setSuffix(".mod");
			amcServiceObj.setTagName(WEBProjConstants.MODEL_SUB);
		}else if(ILFWTreeNode.WINDOW.equals(itemType)){
			if(nodePath == null){
				nodePath = WEBProjConstants.AMC_WINDOW_PATH;
			}
			amcServiceObj.setAmcNodePath(nodePath);
			amcServiceObj.setSuffix(".pm");
			amcServiceObj.setTagName(WEBProjConstants.WINDOW_SUB);
		}else if(ILFWTreeNode.PUBLIC_VIEW.equals(itemType)){
			if(nodePath == null){
				nodePath = WEBProjConstants.AMC_PUBLIC_VIEW_PATH;
			}
			amcServiceObj.setAmcNodePath(nodePath);
			amcServiceObj.setSuffix(".wd");
			amcServiceObj.setTagName(WEBProjConstants.PUBLIC_VIEW_TAGNAME);
		}
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getWebElementNames();
	}
	
	/**
	 * 获取Application下包含的所有Window
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static List<PageMeta> getAppWindowList(String filePath, String fileName){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetAppWindowList);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getPmList();
	}
	
	/**
	 * 获取Application（读取XML文件）
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static Application getApplication(String filePath, String fileName){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetApplication);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getAppConf();
	}
	
	/**
	 * 获取Window
	 * @param paramMap
	 * @param userinfoMap
	 * @return
	 */
	public static PageMeta getWindow(Map<String, Object> paramMap, Map<String, String> userInfoMap){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetWindowWithWidget);
		amcServiceObj.setParamMap(paramMap);
		amcServiceObj.setUserInfoMap(userInfoMap);
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getPageMeta();
	}
	
	/**
	 * 获取Window（读取XML文件）
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static PageMeta getWindow(String filePath, String fileName){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetWindow);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getPageMeta();
	}
	
	/**
	 * 获取View（读取XML文件）
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static LfwWidget getView(String filePath, String fileName){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetView);
		amcServiceObj.setFilePath(filePath);
		amcServiceObj.setFileName(fileName);
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getLfwWidget();
	}
	
	/**
	 * 获取UIMeta（读取XML文件）
	 * @param filePath
	 * @return
	 */
	public static UIMeta getUIMeta(String filePath){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetUIMeta);
		amcServiceObj.setFilePath(filePath);
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getUimeta();
	}
	
	/**
	 * 获取同一上下文的所有Window
	 * @return
	 */
	public static List<PageMeta> getCacheWindows(){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetCacheWindows);
		String ctxPath = getContextPath();
		amcServiceObj.setCtxPath(ctxPath);
		return getAMCDesignDataProvider().operateWebElement(amcServiceObj).getPmList();
	}
	
	/**
	 * 更新Session中的View
	 * @param viewConf
	 */
	public static void updateViewSessionCache(LfwWidget viewConf, UIMeta uimeta, Map<String, UIElement> elementMap){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		//操作类型
		amcServiceObj.setOperateWebElement(AMCServiceObj.UpdateSessionCacheForView);
		//View
		amcServiceObj.setLfwWidget(viewConf);
		//UIMeta
		amcServiceObj.setUimeta(uimeta);
		//UIElement
		amcServiceObj.setElementMap(elementMap);
		//事件
		EventConf[] events = ViewEventTool.getAllEvents(viewConf, null);
		if(events != null && events.length > 0){
			Map<String, List<EventConf>> eventsMap = new HashMap<String, List<EventConf>>();
			for(EventConf event : events){
				if(event.getClassFilePath() != null && event.getClassFileName() != null){
					List<EventConf> list = eventsMap.get(event.getClassFilePath() + File.separator + event.getClassFileName());
					if(list == null){
						list = new ArrayList<EventConf>();
					}
					list.add(event);
					eventsMap.put(event.getClassFilePath() + File.separator + event.getClassFileName(), list);
				}
			}
			String key = null;
			Iterator<String> keys = eventsMap.keySet().iterator();
			while(keys.hasNext()){
				key = keys.next();
				MainPlugin.getDefault().logInfo("类文件路径：" + key);
				LFWAMCPersTool.checkOutFile(key);
			}
			amcServiceObj.setEventsMap(eventsMap);
		}
		if(uimeta != null){
			events = ViewEventTool.getAllEvents(null, uimeta);
			if(events != null && events.length > 0){
				Map<String, List<EventConf>> eventsMap = amcServiceObj.getEventsMap();
				if(eventsMap == null){
					eventsMap = new HashMap<String, List<EventConf>>();
				}
				for(EventConf event : events){
					if(event.getClassFilePath() != null && event.getClassFileName() != null){
						List<EventConf> list = eventsMap.get(event.getClassFilePath() + File.separator + event.getClassFileName());
						if(list == null){
							list = new ArrayList<EventConf>();
						}
						list.add(event);
						eventsMap.put(event.getClassFilePath() + File.separator + event.getClassFileName(), list);
					}
				}
				String key = null;
				Iterator<String> keys = eventsMap.keySet().iterator();
				while(keys.hasNext()){
					key = keys.next();
					MainPlugin.getDefault().logInfo("类文件路径：" + key);
					LFWAMCPersTool.checkOutFile(key);
				}
				amcServiceObj.setEventsMap(eventsMap);
			}
		}
		//sessionId
		List<LFWBrowserEditor> editors = LFWTool.getAllWebEditors();
		if(editors != null && editors.size() > 0){
			for(LFWBrowserEditor editor : editors){
				if(editor instanceof ViewBrowserEditor){
					IEditorInput input = ((ViewBrowserEditor)editor).getEditorInput();
					if(input instanceof WidgetEditorInput){
						if(viewConf.getPagemeta() != null && viewConf.getPagemeta().getId().equals(((WidgetEditorInput)input).getWidget().getPagemeta().getId())){
							if(viewConf.getId().equals(((WidgetEditorInput)input).getWidget().getId())){
								amcServiceObj.setSessionId(((ViewBrowserEditor)editor).getSessionId());
								break;
							}
						}
					}
				}else if(editor instanceof PublicViewBrowserEditor){
					IEditorInput input = ((PublicViewBrowserEditor)editor).getEditorInput();
					if(input instanceof PublicViewEditorInput){
						if(viewConf.getId().equals(((PublicViewEditorInput)input).getWidget().getId())){
							amcServiceObj.setSessionId(((PublicViewBrowserEditor)editor).getSessionId());
							break;
						}
					}
				}
			}
		}
		getAMCDesignDataProvider().operateWebElement(amcServiceObj);
	}
	
	/**
	 * 获取同一上下文的所有公共View
	 * @param ctx
	 * @return
	 */
	public static Map<String, Map<String, LfwWidget>>  getAllPublicViews(){
		String ctx = getContextPath();
		Map<String, Map<String, LfwWidget>> cacheMap = LFWConnector.getAllPoolWidgets(ctx);
		if(cacheMap != null){
			Set<String> keys = cacheMap.keySet();
			List<String> list = new ArrayList<String>();
			for(String key : keys){
				if(ctx != null && !ctx.equals(key)){
					list.add(key);
				}
			}
			for(String key : list){
				cacheMap.remove(key);
			}
		}
		return cacheMap;
	}
	
	/**
	 * 清除session
	 * @param sessionId
	 */
	public static void clearSessionCache(String sessionId){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.ClearSession);
		amcServiceObj.setSessionId(sessionId);
		getAMCDesignDataProvider().operateWebElement(amcServiceObj);
	}
	
	/**
	 * 从session中获取PageMeta
	 * @param sessionId
	 * @param pageMetaId
	 * @return
	 */
	public static PageMeta getPageMetaFromSessionCache(String sessionId, String pageMetaId){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetPageMetaFromSessionCache);
		amcServiceObj.setSessionId(sessionId);
		PageMeta pm = new PageMeta();
		pm.setId(pageMetaId);
		amcServiceObj.setPageMeta(pm);
		getAMCDesignDataProvider().operateWebElement(amcServiceObj);
		return amcServiceObj.getPageMeta();
	}
	
	/**
	 * 设置PageMeta到session中
	 * @param sessionId
	 * @param pageMeta
	 */
	public static void setPageMetaToSessionCache(String sessionId, PageMeta pageMeta){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.SetPageMetaToSessionCache);
		amcServiceObj.setSessionId(sessionId);
		amcServiceObj.setPageMeta(pageMeta);
		getAMCDesignDataProvider().operateWebElement(amcServiceObj);
	}
	
	public static final String KEY_PAGEMETA = "key_pagemeta";
	public static final String KEY_UIMETA = "key_pageuimeta";
	/**
	 * 从session中获取元素
	 * @param pageMeta
	 * @param uiMeta
	 */
	public static Map<String, Object> getElementFromSessionCache(String pageMetaId, String sessionId){
		AMCServiceObj amcServiceObj = new AMCServiceObj();
		amcServiceObj.setOperateWebElement(AMCServiceObj.GetElementFromSessionCache);
		PageMeta pm = new PageMeta();
		pm.setId(pageMetaId);
		amcServiceObj.setPageMeta(pm);
		amcServiceObj.setSessionId(sessionId);
		amcServiceObj = getAMCDesignDataProvider().operateWebElement(amcServiceObj);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(KEY_PAGEMETA, amcServiceObj.getPageMeta());
		map.put(KEY_UIMETA, amcServiceObj.getUimeta());
		return map;
	}
	
	/**
	 * 连接UAPWEB服务
	 * @return
	 */
	private static ILfwAMCDesignDataProvider getAMCDesignDataProvider() {
		IProject[] projects = LFWAMCPersTool.getLFwProjects();
		for (int i = 0; i < projects.length; i++) {
			try{
				Class<?> c = ClassTool.loadClass("nc.uap.lfw.design.impl.LfwAMCDesignDataProviderImpl", projects[i], ILfwAMCDesignDataProvider.class.getClassLoader());
				Thread.currentThread().setContextClassLoader(c.getClassLoader());
				ILfwAMCDesignDataProvider provider = (ILfwAMCDesignDataProvider) c.newInstance();
				return provider;
			}catch(Exception e){
				MainPlugin.getDefault().logError(e);
			}
		}
		return null;
	}
		
}
