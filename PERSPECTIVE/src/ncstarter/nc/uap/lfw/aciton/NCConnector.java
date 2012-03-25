package nc.uap.lfw.aciton;

import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.ClassTool;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.design.itf.BCPManifest;
import nc.uap.lfw.design.itf.FuncNodeVO;
import nc.uap.lfw.design.itf.ILfwDesignDataProvider;
import nc.uap.lfw.design.itf.TemplateVO;
import nc.uap.lfw.design.itf.TypeNodeVO;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.jsp.uimeta.UIMeta;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;

public class NCConnector {
	public static List<FuncNodeVO> getFuncRegisterVOs() {
		return getProvider().getFuncRegisterVOs();
	}
	
	
	public static List<TemplateVO> getTemplateVOs() {
		return getProvider().getTemplateVOs();
	}
	/**
	 * 根据funcode获取已有模板
	 * @param funcnode
	 * @return
	 */
	public static List<TemplateVO> getTemplateByFuncnode(String funcnode) {
		return getProvider().getTemplateByFuncnode(funcnode);
	}
	
	public static List<TemplateVO> getAllTemplateByFuncnode(String funcnode) {
		return getProvider().getAllTemplateByFuncnode(funcnode);
	}
	
	public static String getBillType(String funcnode) {
		return getProvider().getBillType(funcnode);
	}
	/**
	 * 根据funcode获取已有查询模板
	 * @param funcnode
	 * @return
	 */
	public static List<TemplateVO> getQeuryTemplateByFuncnode(String funcnode) {
		return getProvider().getQeuryTemplateByFuncnode(funcnode);
	}
	
	
	//getQueryTemplateByFuncnode
	
	public static TypeNodeVO[] getAllMainOrgType(){
		return getProvider().getAllMainOrgType();
	}
	
	public static void updateSysTemplate(FuncNodeVO funnodevo, TemplateVO[] templateVOs) {
		try {
			getProvider().updateSysTemplate(funnodevo, templateVOs);
			LFWPersTool.refreshCurrentPorject();
		} catch (LfwBusinessException e) {
			MainPlugin.getDefault().logError(e.getMessage(), e);
			MessageDialog.openError(null, "错误提示", e.getMessage());
		}
	}
	
	public static void delSysTemplateRelated(String funnode){
		getProvider().delSysTemplateRelated(funnode);
		LFWPersTool.refreshCurrentPorject();
	}
	
	public static TypeNodeVO getOrgTypeByPK(String id) {
		return getProvider().getOrgTypeByPK(id);
	}
	
	public static String[] registerBillAction(String billType, String[] actions){
		return getProvider().registerBillAction(billType, actions);
	}
	
	public static void registerButtonAction(List<MenuItem> menuItems, String funnode){
		getProvider().registerMenuItems(menuItems, funnode);
	}
	
	public static UIMeta getUIMeta(String folderPath, PageMeta pm,  String widgetID) {
		return getProvider().getUIMeta(folderPath, pm, widgetID);
	}
	
	public static void saveUIMeta(UIMeta meta, String pmPath, String folderPath){
		String filePath = folderPath + "/uimeta.um";
		LFWPersTool.checkOutFile(filePath);
		getProvider().saveUIMeta(meta, pmPath, folderPath);
		LFWPersTool.refreshCurrentPorject();
	}
	
	public static String getVersion(){
		return getProvider().getVersion();
	}
	
	public static  Map<String, String>[] getPageNames(String[] projPaths){
		return getProvider().getPageNames(projPaths);
	}
//	
//	public static PageFlow[][] getPageFlows(String[] projPaths){
//		return getProvider().getPageFlowDef(projPaths);
//	}
	
//	public static void pageFlowToXML(String projectPath, PageFlow pageflow){
//		getProvider().pageFlowToXMl(projectPath, pageflow);
//		LFWPersTool.refreshCurrentPorject();
//	}
	
	public static void refreshNode(){
		getProvider().refresh();
	}
	
	public static void deleteFunNode(String funnode){
		getProvider().deleteFunNode(funnode);
		LFWPersTool.refreshCurrentPorject();
	}
	
	public static BCPManifest getBCPManifest(String filePath){
		return getProvider().getMenifest(filePath);
	}
	
	public static LfwWidget getMdDsFromComponent(LfwWidget widget, String componetId){
		return getProvider().getMdDsFromComponent(widget, componetId);
	}
	
	public static void saveWidgettoXml(String filePath, String fileName, String projectPath, LfwWidget widget, String rootPath) {
		String path = filePath + "/" + fileName;
		LFWPersTool.checkOutFile(path);
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			LFWAMCConnector.updateViewToXml(filePath, fileName, projectPath, widget);
		}else{
			getProvider().saveWidgettoXml(filePath, fileName, projectPath, widget, rootPath);
		}
		LFWPersTool.refreshCurrentPorject();
	}
	
	private static ILfwDesignDataProvider getProvider() {
		IProject[] projects = LFWPersTool.getLFwProjects();
		for (int i = 0; i < projects.length; i++) {
			try {
				Class<?> c = ClassTool.loadClass("nc.uap.lfw.design.impl.LfwDesignDataProvider", projects[i], ILfwDesignDataProvider.class.getClassLoader());
				Thread.currentThread().setContextClassLoader(c.getClassLoader());
				return (ILfwDesignDataProvider) c.newInstance();
			} 
			catch (Exception e) {
				MainPlugin.getDefault().logError(e);
			}
		}
		return null;
	}
	
}