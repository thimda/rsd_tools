/**
 * 
 */
package nc.uap.lfw.editor.common.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.application.LFWApplicationTreeItem;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.event.PageEvent;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.PageListener;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.core.uimodel.conf.Model;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.webcomponent.LFWVirtualDirTreeItem;

import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * @author chouhl
 * 2011-11-4
 */
public class LFWSaveElementTool {
	
	/**
	 * 生成Application对象
	 * @param id
	 * @param caption
	 * @param controllerClazz
	 * @param sourcePackage
	 * @return
	 */
	public static Application createNewApplicationConf(String id, String caption, String controllerClazz, String sourcePackage){
		Application app = new Application();
		app.setId(id);
		app.setCaption(caption);
		app.setControllerClazz(controllerClazz);
		app.setSourcePackage(sourcePackage);
		//真实路径
		String appPath = LFWAMCPersTool.getApplicationPath();
		String folderPath = LFWAMCPersTool.getCurrentFolderPath();
		String realPath = id;
		if(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWVirtualDirTreeItem){
			if(appPath.length() < folderPath.length()){
				realPath = folderPath.substring(folderPath.indexOf(appPath) + appPath.length() + 1) + "/" + id;
			}
		}
		app.setRealPath(realPath);
		return app;
	}

	/**
	 * 创建Application(生成XML，创建Class)
	 * @param appConf
	 */
	public static void createApplication(Application appConf){
		String folderPath = LFWAMCPersTool.getCurrentFolderPath();
		String filePath = folderPath + File.separator + appConf.getId();
		int index = appConf.getControllerClazz().lastIndexOf(".");
		String packageName = null;
		if(index > 0){
			packageName = appConf.getControllerClazz().substring(0, index);
		}else{
			packageName = "";
		}
		String projectPath = LFWAMCPersTool.getLFWProjectPath();
		String className = appConf.getControllerClazz().substring(index + 1);
		String classFilePath = projectPath + File.separator + appConf.getSourcePackage() + packageName.replaceAll("\\.", "/");
		String classFileName = className + ".java";
		
		LFWAMCConnector.createApplication(packageName, className, classFilePath, classFileName, filePath, WEBProjConstants.AMC_APPLICATION_FILENAME, projectPath, appConf);
	}
	
	/**
	 * 保存application到XML文件
	 * @param application
	 */
	public static void saveApplication(Application appConf) {
		String folderPath = LFWAMCPersTool.getCurrentFolderPath();
		String projectPath = LFWAMCPersTool.getProjectPath();
		LFWAMCConnector.updateApplication(folderPath, WEBProjConstants.AMC_APPLICATION_FILENAME, projectPath, appConf);
	}
	
	/**
	 * 保存application到XML文件
	 * @param appConf
	 * @param folderPath
	 */
	public static void saveApplication(Application appConf, String folderPath){
		String projectPath = LFWAMCPersTool.getProjectPath();
		LFWAMCConnector.updateApplication(folderPath, WEBProjConstants.AMC_APPLICATION_FILENAME, projectPath, appConf);
	}
	
	/**
	 * 创建Model
	 * @param model
	 */
	public static void createModel(Model model){
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String projectPath = LFWPersTool.getProjectPath();
		String filePath = folderPath + "/" + model.getId();
		LFWAMCConnector.createModel(filePath, WEBProjConstants.AMC_MODEL_FILENAME, projectPath, model);
	}
	
	public static final String DEFAULT_PROCESSOR_CLASS_NAME = "nc.uap.lfw.core.event.AppRequestProcessor";
	/**
	 * 创建WindowConf
	 * @param id
	 * @param caption
	 * @param controllerClazz
	 * @param sourcePackage
	 * @return
	 */
	public static PageMeta createNewWindowConf(String id, String caption, String controllerClazz, String sourcePackage){
		//新建Window
		PageMeta winConf = new PageMeta();
		winConf.setId(id);
		winConf.setCaption(caption);
		winConf.setProcessorClazz(DEFAULT_PROCESSOR_CLASS_NAME);
		winConf.setControllerClazz(controllerClazz);
		winConf.setSourcePackage(sourcePackage);
		winConf.setWindowType("win");
		//真实路径
		String windowPath = LFWAMCPersTool.getWindowPath();
		String folderPath = LFWAMCPersTool.getCurrentFolderPath();
		String realPath = id;
		//在Application中创建window，默认节点生成在window根目录下.
		if(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWVirtualDirTreeItem){
			if(windowPath.length() < folderPath.length()){
				realPath = folderPath.substring(folderPath.indexOf(windowPath) + windowPath.length() + 1) + "/" + id;
			}
		}
		winConf.setRealPath(realPath);
//		//页面状态
//		PageStates pageStates = new PageStates();
//		PageState pageStateCard = new PageState();
//		pageStateCard.setKey("1");
//		pageStateCard.setName("卡片显示");
//		PageState pageStateList = new PageState();
//		pageStateList.setKey("2");
//		pageStateList.setName("列表显示");
//		pageStates.addPageState(pageStateCard);
//		pageStates.addPageState(pageStateList);
//		winConf.setPageStates(pageStates);
//		winConf.setPageState("1");
		// 增加默认事件
		EventConf eventConf = new EventConf();
		eventConf.setEventStatus(EventConf.ADD_STATUS);
		eventConf.setJsEventClaszz(PageListener.class.getName());
		eventConf.setMethodName("sysWindowClosed");
		eventConf.setName(PageListener.ON_CLOSED);
		List<LfwParameter> paramList = new ArrayList<LfwParameter>();
		LfwParameter param = new LfwParameter();
		param.setName("event");
		param.setDesc(PageEvent.class.getName());
		paramList.add(param);
		eventConf.setParamList(paramList);
		eventConf.setSubmitRule(new EventSubmitRule());
		winConf.addEventConf(eventConf);
		return winConf;
	}
	
	/**
	 * 创建Window(生成XML，创建Class)
	 * @param pagemeta
	 */
	public static void createPagemeta(PageMeta winConf, boolean isFlowlayout) {
		String folderPath = LFWAMCPersTool.getCurrentFolderPath();
		if(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWApplicationTreeItem){
			TreeItem currItem = LFWAMCPersTool.getWindowDirectoryTreeItem(LFWAMCPersTool.getCurrentProject(), LFWAMCPersTool.getCurrentTreeItem());
			folderPath = LFWAMCPersTool.getCurrentFolderPath(currItem);
		}
		String filePath = folderPath + File.separator + winConf.getId();
		int index = winConf.getControllerClazz().lastIndexOf(".");
		String packageName = null;
		if(index > 0){
			packageName = winConf.getControllerClazz().substring(0, index);
		}else{
			packageName = "";
		}
		String projectPath = LFWAMCPersTool.getLFWProjectPath();
		String className = winConf.getControllerClazz().substring(index + 1);
		String classFilePath = projectPath + File.separator + winConf.getSourcePackage() + packageName.replaceAll("\\.", "/");
		String classFileName = className + ".java";

		UIMeta meta = new UIMeta();
//		String folderPath = LFWPersTool.getCurrentFolderPath();
//		String filePath = folderPath + File.separator + widget.getId();
		String fp = filePath.replaceAll("\\\\", "/");
		String id = fp.substring(fp.lastIndexOf("/") + 1) + "_um";
		meta.setAttribute(UIMeta.ID, id);
		meta.setFlowmode(isFlowlayout);
		
		LFWAMCConnector.createWindow(packageName, className, classFilePath, classFileName, filePath, WEBProjConstants.AMC_WINDOW_FILENAME, projectPath, winConf, meta);
	}
	
	/**
	 * 保存Pagemeta到XML文件
	 * @param winConf
	 */
	public static void savePagemeta(PageMeta winConf) {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String projectPath = LFWPersTool.getProjectPath();
		LFWAMCConnector.updateWindowToXml(folderPath, WEBProjConstants.AMC_WINDOW_FILENAME, projectPath, winConf);
	}
	
	/**
	 * 更新PageMeta(XML文件、Class文件)
	 * @param pageMeta
	 */
	public static void updateWindow(PageMeta pageMeta){
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String projectPath = LFWPersTool.getProjectPath();
		LFWAMCConnector.updateWindow(folderPath, WEBProjConstants.AMC_WINDOW_FILENAME, projectPath, pageMeta);
	}
	
	/**
	 * 创建View(生成XML，创建Class)
	 * @param widget
	 */
	public static void createView(LfwWidget viewConf, UIMeta uimeta) {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String filePath = folderPath + File.separator + viewConf.getId();
		String ctrlClazz = viewConf.getControllerClazz();
		int index = ctrlClazz == null ? -1 : ctrlClazz.lastIndexOf(".");
		String packageName = null;
		if(index > 0){
			packageName = viewConf.getControllerClazz().substring(0, index);
		}else{
			packageName = "";
		}
		
		String projectPath = LFWAMCPersTool.getLFWProjectPath();
		String className = null;
		String classFilePath = null;
		String classFileName = null;
		if(ctrlClazz != null && !ctrlClazz.equals("")){
			className = viewConf.getControllerClazz().substring(index + 1);
			classFilePath = projectPath + File.separator + viewConf.getSourcePackage() + packageName.replaceAll("\\.", "/");
			classFileName = className + ".java";
		}
		LFWAMCConnector.createView(packageName, className, classFilePath, classFileName, filePath, WEBProjConstants.AMC_VIEW_FILENAME, projectPath, viewConf, uimeta);
	}
	
	/**
	 * 更新View(XML文件、Class文件)
	 * @param viewConf
	 */
	public static void updateView(LfwWidget viewConf){
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String projectPath = LFWPersTool.getProjectPath();
		LFWAMCConnector.updateView(folderPath, WEBProjConstants.AMC_VIEW_FILENAME, projectPath, viewConf);
	}
	
	/**
	 * 创建UIMeta（XML文件）
	 * @param filePath
	 */
	public static void createUIMeta(String filePath){
		String fp = filePath.replaceAll("\\\\", "/");
		String id = fp.substring(fp.lastIndexOf("/") + 1) + "_um";
		UIMeta meta = new UIMeta();
//		meta.setAttribute(UIMeta.ISCHART, 0);
//		meta.setAttribute(UIMeta.ISJQUERY, 0);
//		meta.setAttribute(UIMeta.ISEXCEL, 0);
//		meta.setAttribute(UIMeta.JSEDITOR, 0);
		meta.setAttribute(UIMeta.ID, id);
		meta.setFlowmode(true);
		LFWAMCConnector.createUIMeta(filePath, WEBProjConstants.AMC_UIMETA_FILENAME, meta);
	}
	
}
