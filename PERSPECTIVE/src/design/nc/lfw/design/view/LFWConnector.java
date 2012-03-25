package nc.lfw.design.view;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.ClassTool;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.MdDataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.design.itf.ILfwDesignDataProvider;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.tools.LFWTool;

import org.eclipse.core.resources.IProject;

public class LFWConnector {

	public static Map<String, IRefNode> getRefNode(String projectPath){
		return getProvider().getRefNode(projectPath);
	}
	
	//String ctx =ResourcesPlugin.getWorkspace().getRoot().getc.getWorkspace().getRoot().getProjects() 
	public static Map<String,Dataset> getDataset(String projectPath){
		return getProvider().getDataset(projectPath);
	}
	
	public static Map<String, Map<String, LfwWidget>>  getAllPoolWidgets(String ctx){
		return getProvider().getAllPoolWidgets(ctx);
	}
	
	public static Map<String, Map<String, Dataset>>  getAllPoolDatasets(String ctx){
		return getProvider().getAllPoolDatasets(ctx);
	}
	
	
	public static Map<String, Map<String, IRefNode>> getAllPoolRefNodes(String ctx){
		return getProvider().getAllRefNodesFromPool(ctx);
	}
	
	public static PageMeta getPageMeta(Map<String, Object> paramMap, Map<String, String> userInfoMap){
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			return LFWAMCConnector.getWindow(paramMap, userInfoMap);
		}else{
			return getProvider().getPageMeta(paramMap, userInfoMap);
		}
	}
	
	public static void saveDatasettoXml(String rootPath, String filePath, String fileName, Dataset ds) {
		getProvider().saveDatasettoXml(rootPath, filePath, fileName, ds);
		LFWPersTool.refreshCurrentPorject();
	}
	
	public static void removeDsFromPool(String rootPath, Dataset ds) {
		getProvider().removeDsFromPool(rootPath, ds);
		LFWPersTool.refreshCurrentPorject();
	}
	
	
	public static void removeWidgetFromPool(String rootPath, LfwWidget widget){
		getProvider().removeWidgetFromPool(rootPath, widget);
		LFWPersTool.refreshCurrentPorject();
	}
	

	public static void removeRefNodeFromPool(String rootPath, IRefNode refNode){
		getProvider().removeRefnodeFromPool(rootPath, refNode);
		LFWPersTool.refreshCurrentPorject();
	}
	
	public static void saveRefNodetoXml(String rootPath, String filePath, String fileName, IRefNode refnode){
		getProvider().saveRefNodetoXml(rootPath, filePath, fileName, refnode);
		LFWPersTool.refreshCurrentPorject();
	}
	
	public static String[][] getAllAccount(){
		return getProvider().getAllAccounts();
	}
	
	public static void savePagemetaToXml(String filePath, String fileName, String projectPath, PageMeta meta) {
		//checkoutÎÄ¼þ
		String path = filePath + "/" + fileName;
		LFWPersTool.checkOutFile(path);
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			LFWAMCConnector.updateWindowToXml(filePath, fileName, projectPath, meta);
		}else{
			getProvider().savePagemetaToXml(filePath, fileName, projectPath, meta);
		}
		LFWPersTool.refreshCurrentPorject();
	}
	
	public static String[] getAllNcRefNode() {
		return getProvider().getAllNcRefNode();
	}
	
	public static List getAllModulse() {
		return getProvider().getAllModulse();
	}
	
	public static List getAllComponentByModuleId(String moduleid) {
		return getProvider().getAllComponentByModuleId(moduleid);
	}
	
	public static List getAllComponents(){
		return getProvider().getAllComponents();
	}
	
	public static List getAllClasses(){
		return getProvider().getAllClasses();
	}
	
	public static List getAllClassByComponentId(String componentID) {
		return getProvider().getAllClassByComponentId(componentID);
	}
	
	public static MdDataset getMdDataset(MdDataset ds) {
		return getProvider().getMdDataset(ds);
	}
	
	public static String getAggVO(String fullClassName){
		return getProvider().getAggVO(fullClassName);
	}
	
	public static List  getNCRefMdDataset(MdDataset mdds) {
		return getProvider().getNCRefMdDataset(mdds);
	}
	
	public static List  getNCFieldRelations(MdDataset mdds) {
		return getProvider().getNCFieldRelations(mdds);
	}
	
	public static List  getAllNCRefNode(MdDataset mdds) {
		return getProvider().getAllNCRefNode(mdds);
	}
	
	public static List  getAllNcComboData(MdDataset mdds) {
		return getProvider().getAllNcComboData(mdds);
	}

	public static String generatorClass(String fullPath, String extendClass,Map<String, Object> param){
		return getProvider().generatorClass(fullPath, extendClass, param);
	}
	
	public static String generateRefNodeClass(String refType, String modelClass, String tableName, String refPk, String refCode, String refName, String visibleFields, String childfield, String childfield2){
		return getProvider().generateRefNodeClass(refType, modelClass, tableName, refPk, refCode, refName, visibleFields, childfield, childfield2);
	}
	public static String generatorVO(String fullPath, String tableName, String primaryKey, Dataset ds){
		return getProvider().generatorVO(fullPath, tableName, primaryKey, ds);
	}
	
	private static ILfwDesignDataProvider getProvider() {
		IProject[] projects = LFWPersTool.getLFwProjects();
		for (int i = 0; i < projects.length; i++) {
			try {
				Class<?> c = ClassTool.loadClass("nc.uap.lfw.design.impl.LfwDesignDataProvider", projects[i], ILfwDesignDataProvider.class.getClassLoader());
				Thread.currentThread().setContextClassLoader(c.getClassLoader());
				ILfwDesignDataProvider provider = (ILfwDesignDataProvider) c.newInstance();
				return provider;
			} 
			catch (Exception e) {
				MainPlugin.getDefault().logError(e);
			}
		}
		return null;
	}
	
}
