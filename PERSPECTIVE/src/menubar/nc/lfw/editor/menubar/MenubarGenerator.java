package nc.lfw.editor.menubar;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.IRefDataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

/**
 * 菜单自动生成器
 * @author guoweic
 *
 */
public class MenubarGenerator {

//	private String sourceFolder;
//	private String packageName;
//	private String classPrefix;
	private PageMeta pagemeta;
//	private String mainWidgetId;
	private String masterDsId;
//	//是否支持多状态显示
//	private boolean isSupportMultiVisible;
	private List<String> slaveDsIds = new ArrayList<String>();
//	private Map<String, String> tabidsDsmap = new HashMap<String, String>();
//	private String pageId;
//	private String aggVO;
	
	// 行操作按钮是否单独放到toolbar上
//	private boolean lineItemsInToolbar = false;
	
//	private static final String CARD_TYPE = "card";
//	private static final String LIST_TYPE = "list";
	
	// 行操作中的“页签”参数
//	private static final String TAB_ID = "bodyTab_card";
	
//	private static final String LIST_SINGLE_TAB_ID = "headTab_list";
	
//	private static final String LIST_TAB_ID = "bodyTab_list";
	// “卡片布局”ID参数
//	private static final String CARD_LAYOUT_ID = "manageCard";
//	// 行操作Toolbar的ID
//	private static final String LINE_TOOLBAR_ID = "bodyToolbar";
	// 行操作Menubar的ID
//	private static final String LINE_MENUBAR_ID = "bodyMenubar";
	
	public MenubarGenerator(){
		
	}
	
	public MenubarGenerator(String sourceFolder, String packageName, String classPrefix, PageMeta pagemeta, String aggVO, boolean lineItemsInToolbar) {
//		this.sourceFolder = sourceFolder;
//		this.packageName = packageName;
//		this.classPrefix = classPrefix;
		if (null != pagemeta)
			this.pagemeta = pagemeta;
		else
			this.pagemeta = LFWPersTool.getCurrentPageMeta();
		LfwWidget mainWidget = getMainWidget();
//		this.mainWidgetId = mainWidget.getId();
		this.masterDsId = getMasterDataset(mainWidget);
		Dataset[] dsArray = mainWidget.getViewModels().getDatasets();
		for (Dataset ds : dsArray) {
			if(!(ds instanceof IRefDataset)){
				if (!ds.getId().equals(masterDsId))
					slaveDsIds.add(ds.getId());
				}
		}
//		this.pageId = LFWPersTool.getCurrentPageMetaTreeItem().getPageId();
//		this.aggVO = aggVO;
//		this.lineItemsInToolbar = lineItemsInToolbar;
//		this.isSupportMultiVisible = isSupportMultiVisible;
	}
	
	/**
	 * 自动生成Menubar
	 * @param pageType 页面类型
	 * @param billType 单据类型
	 */
	public void generateMenubar(int pageType, String billType) {
//		LfwWidget mainWidget =  getMainWidget();
		//list单表头
//		if(pageType == ExtAttrConstants.PAGETYPE_LIST_SINGAL){
//			if(mainWidget.getExtendAttribute(ExtAttrConstants.TABIDDATASET) != null){
//				Map<String, Dataset> tabDsmap = (Map<String, Dataset>) mainWidget.getExtendAttributeValue(ExtAttrConstants.TABIDDATASET);
//				if(tabDsmap != null){
//					for (Iterator<String> it = tabDsmap.keySet().iterator(); it.hasNext();) {
//						String id = it.next();
//						Dataset ds = tabDsmap.get(id);
//						if(id.startsWith("0_")){
//							String newid = id.substring(2);
//							tabidsDsmap.put(newid, ds.getId());
//						}
//					}
//				}
//			}
//		}
//		else if(pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST || 
//				pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST || 
//				pageType == ExtAttrConstants.PAGETYPE_CARD_MASCHI || 
//				pageType == ExtAttrConstants.PAGETYPE_LIST_MASCHI){
//			if(mainWidget.getExtendAttribute(ExtAttrConstants.TABIDDATASET) != null){
//				Map<String, Dataset> tabDsmap = (Map<String, Dataset>) mainWidget.getExtendAttributeValue(ExtAttrConstants.TABIDDATASET);
//				if(tabDsmap != null){
//					for (Iterator<String> it = tabDsmap.keySet().iterator(); it.hasNext();) {
//						String id = it.next();
//						Dataset ds = tabDsmap.get(id);
//						if(id.startsWith("1_")){
//							String newid = id.substring(2);
//							tabidsDsmap.put(newid, ds.getId());
//						}
//					}
//				}
//			}
//		}
//		
//		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
//				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST) {
//			createCardMenubar(pageType, true, billType);
//			createListMenubar(pageType, true, billType);
//		} else if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST 
//				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {
//			createCardMenubar(pageType, false, billType);
//			createListMenubar(pageType, false, billType);
//			
//		} 
		
//		else if(pageType == ExtAttrConstants.PAGETYPE_CARD_MASCHI || 
//				pageType == ExtAttrConstants.PAGETYPE_CARD_SINGAL){
//			createCardMenubar(pageType, true, billType);
//		}
//		
//		else if(pageType == ExtAttrConstants.PAGETYPE_CARD_MASCHI)
//			createCardMenubar(pageType, false, billType);
//		else if(pageType == ExtAttrConstants.PAGETYPE_CARD_SINGAL)
//			createCardMenubar(pageType, true, billType);
//			
//		else if(pageType == ExtAttrConstants.PAGETYPE_LIST_MASCHI || pageType == ExtAttrConstants.PAGETYPE_LIST_SINGAL){
//			createListMenubar(pageType, false, billType);
//		}
	}

	/**
	 * 生成卡片型菜单
	 * 
	 * @param pageType 页面类型
	 * @param isSignal 是否是单表头（体）
	 * @param billType 单据类型
	 * @return
	 */
//	private MenubarComp createCardMenubar(int pageType, boolean isSignal, String billType) {
//		MenubarComp menubar = new MenubarComp();
//		menubar.setId("menu_card");
//		createAddMenuItem(menubar, CARD_TYPE, pageType);
//		createCopyMenuItem(menubar, CARD_TYPE, pageType);
//		createEditMenuItem(menubar, CARD_TYPE, pageType);
//		createDeleteMenuItem(menubar, CARD_TYPE);
//		createSaveMenuItem(menubar, CARD_TYPE);
//		createCancelMenuItem(menubar, CARD_TYPE);
//		createPrintMenuItem(menubar, CARD_TYPE);
//		createQueryMenuItem(menubar, CARD_TYPE);
//		if (!isSignal) {  // 是主子
//			if (this.lineItemsInToolbar) {  // 行操作按钮放在一个Menubar上
////				createLineToolbar(CARD_TYPE);
//				createLineMenubar(CARD_TYPE);
//			}
//			else {  // 行操作作为一个子菜单
//				creatLineMenuItem(menubar, CARD_TYPE);
//			}
//		}
//		if (billType != null && !"".equals(billType)) {  // 是工作流
//			createWorkflowMenuItem(menubar, billType, CARD_TYPE);
//			createQueryPfInfoMenuItem(menubar, billType, CARD_TYPE);
//		}
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST 
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {  // 管理型
////			createListShowMenuItem(menubar, CARD_TYPE);
////		}
//		
//		pagemeta.getViewMenus().addMenuBar(menubar);
////		List<MenuItem> menuItemList = menubar.getMenuList();
////		if(isSupportMultiVisible && menuItemList != null){
////			for (MenuItem menuItem2 : menuItemList) {
////				menuItem2.setOperatorVisibleStatusArray(menuItem2.getOperatorStatusArray());
////				menuItem2.setBusinessVisibleStatusArray(menuItem2.getBusinessStatusArray());
////			}
////		}
//		return menubar;
//	}
	
	/**
	 * 
	 * 生成列表型菜单
	 * 
	 * @param pageType 页面类型
	 * @param isSignal 是否是单表头（体）
	 * @param isWorkflow 是否是流程
	 * @param billType 单据类型
	 * @return
	 */
//	private MenubarComp createListMenubar(int pageType, boolean isSignal, String billType) {
////		if(pageType == ExtAttrConstants.PAGETYPE_LIST_SINGAL){
////			this.lineItemsInToolbar = false;
////		}
//		MenubarComp menubar = new MenubarComp();
//		menubar.setId("menu_list");
//		createAddMenuItem(menubar, LIST_TYPE, pageType);
//		createCopyMenuItem(menubar, LIST_TYPE, pageType);
//		createEditMenuItem(menubar, LIST_TYPE, pageType);
//		createDeleteMenuItem(menubar, LIST_TYPE);
//		createQueryMenuItem(menubar, LIST_TYPE);
////		if(pageType == ExtAttrConstants.PAGETYPE_LIST_MASCHI || pageType == ExtAttrConstants.PAGETYPE_LIST_SINGAL){
////			createSaveMenuItem(menubar, LIST_TYPE);
////		}
////		if (!isSignal) {  // 是主子
////			if (this.lineItemsInToolbar) {  // 行操作按钮放在一个Menubar上
//////				createLineToolbar(CARD_TYPE);
////				createLineMenubar(LIST_TYPE);
////			}
////			else {  // 行操作作为一个子菜单
////				if(pageType == ExtAttrConstants.PAGETYPE_LIST_SINGAL){
////					creatLineMenuItem(menubar, LIST_SINGLE_TAB_ID);
////				}
////				else
////					creatLineMenuItem(menubar, LIST_TYPE);
////			}
////		}
//		//list主子
////		if(pageType == ExtAttrConstants.PAGETYPE_LIST_MASCHI){
////			if (this.lineItemsInToolbar) { 
////				createLineMenubar(LIST_TYPE);
////			}
////			else
////				creatLineMenuItem(menubar, LIST_TYPE);
////		}
////		if(pageType == ExtAttrConstants.PAGETYPE_LIST_SINGAL){
////			creatLineMenuItem(menubar, LIST_SINGLE_TAB_ID);
////		}
//		
//		if (billType != null) {  // 是工作流
//			createWorkflowMenuItem(menubar, billType, LIST_TYPE);
//		}
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST 
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {  // 管理型
////			createCardShowMenuItem(menubar, LIST_TYPE);
////		}
//
//		pagemeta.getViewMenus().addMenuBar(menubar);
////		List<MenuItem> menuItemList = menubar.getMenuList();
////		if(isSupportMultiVisible && menuItemList != null){
////			for (MenuItem menuItem2 : menuItemList) {
////				menuItem2.setOperatorVisibleStatusArray(menuItem2.getOperatorStatusArray());
////				menuItem2.setBusinessVisibleStatusArray(menuItem2.getBusinessStatusArray());
////			}
////		}
//		return menubar;
//	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	private Map<String, Object> createMenuItemParamMap(String clazz) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Class c = Class.forName(clazz);
			Method[] ms = c.getDeclaredMethods();
			for (int i = 0; i < ms.length; i++) {
				Method m = ms[i];
				UifMethodAnnotation annotation = m.getAnnotation(UifMethodAnnotation.class);
				if (annotation != null) {
					String name = annotation.name();
					if (annotation.isWidget()) {
						paramMap.put(name, mainWidgetId);
					} else if (annotation.isDataset()) {
						if (annotation.isMultiSel()) {
							String ids = "";
							for (String slaveDsId : slaveDsIds) {
								ids += slaveDsId + ",";
							}
							if (ids.length() > 0)
								ids = ids.substring(0, ids.length() - 1);
							paramMap.put(name, masterDsId);
						} else {
							paramMap.put(name, masterDsId);
						}
					}
//					else if (annotation.isCardLayout()) {
//						createCardText(name, annotation.isMust());
//						annotationCardName = name;
//					} else if (annotation.isTab()) {
//						createTabText(name, annotation.isMust());
//						annotationTabName = name;
//					} 
					else if (annotation.isBoolean()) {
						paramMap.put(name, annotation.booleanValue());
					} else {  // 其他字段
						
					}
				}
			}
			return paramMap;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	 */
	
	/**
	 * 生成“新建”按钮
	 * @param menubar
	 * @param type
	 * @param pageType
	 * @return
	 */
//	private MenuItem createAddMenuItem(MenubarComp menubar, String type, int pageType) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("add");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		paramMap.put("是否有导航", false);
//		// 判断卡片优先或列表优先
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST) {
////			paramMap.put("切换卡片", "manageCard");
////			paramMap.put("切换卡片项序号", "0");
////			paramMap.put("页面状态", "1");
////		} else if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {
////			paramMap.put("切换卡片", "manageCard");
////			paramMap.put("切换卡片项序号", "1");
////			paramMap.put("页面状态", "1");
////		}
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_add");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_add");
//		}
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("N");
//		menuItem.setDisplayHotKey("Ctrl+N");
//		return menuItem;
//	}
	
	
	/**
	 * 生成拷贝按钮
	 * @param menubar
	 * @param type
	 * @param pageType
	 * @return
	 */
//	private MenuItem createCopyMenuItem(MenubarComp menubar, String type, int pageType) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("copy");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		paramMap.put("是否有导航", false);
//		// 判断卡片优先或列表优先
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST) {
////			paramMap.put("切换卡片", "manageCard");
////			paramMap.put("切换卡片项序号", "0");
////			paramMap.put("页面状态", "1");
////		} else if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {
////			paramMap.put("切换卡片", "manageCard");
////			paramMap.put("切换卡片项序号", "1");
////			paramMap.put("页面状态", "1");
////		}
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_copy");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_copy");
//		}
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("N");
//		menuItem.setDisplayHotKey("Ctrl+C");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“修改”按钮
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createEditMenuItem(MenubarComp menubar, String type, int pageType) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("edit");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		// 判断卡片优先或列表优先
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST) {
////			paramMap.put("切换卡片", "manageCard");
////			paramMap.put("切换卡片项序号", "0");
////			paramMap.put("页面状态", "1");
////		} else if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {
////			paramMap.put("切换卡片", "manageCard");
////			paramMap.put("切换卡片项序号", "1");
////			paramMap.put("页面状态", "1");
////		}
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_edit");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_edit");
//		}
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("E");
//		menuItem.setDisplayHotKey("Ctrl+E");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“删除”按钮
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createDeleteMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("delete");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_delete");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_delete");
//		}
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("Del");
//		menuItem.setDisplayHotKey("Ctrl+Del");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“保存”按钮
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createSaveMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("save");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_save");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_save");
//		}
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("S");
//		menuItem.setDisplayHotKey("Ctrl+S");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“取消”按钮
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createCancelMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("cancel");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_cancel");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_cancel");
//		}
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("Q");
//		menuItem.setDisplayHotKey("Ctrl+Q");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“打印”按钮
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createPrintMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("print");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		if(slaveDsIds != null && slaveDsIds.size() > 0)
//			paramMap.put("子数据集", slaveDsIds.get(0));
//		else 
//			paramMap.put("子数据集", null);
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_print");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_print");
//		}
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("P");
//		menuItem.setDisplayHotKey("Ctrl+P");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“查询”按钮
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createQueryMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("query");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		String page = pageId + ".qry";
//		paramMap.put("查询页面", page);
//		paramMap.put("目标父片段", mainWidgetId);
//		paramMap.put("目标父数据集", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			defaultItem.setId("card_query");
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			defaultItem.setId("list_query");
//		}
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("Shift+F");
//		menuItem.setDisplayHotKey("Shift+F");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“联查审批单据”按钮
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createQueryPfInfoMenuItem(MenubarComp menubar, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("querypfinfo");
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("主数据集", masterDsId);
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("Q");
//		menuItem.setDisplayHotKey("Ctrl+Q");
//		((SubmitMenuItem)defaultItem).setBillType(billType);
//		((SubmitMenuItem)defaultItem).setSourceFolder(sourceFolder);
//		((SubmitMenuItem)defaultItem).setPackageName(packageName);
//		defaultItem.afterAdd();
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“列表显示”按钮
//	 * @param menubar
//	 * @return
//	 */
//	private MenuItem createListShowMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("list");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("卡片布局ID", CARD_LAYOUT_ID);
//		paramMap.put("切换状态", "2");
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("X");
//		menuItem.setDisplayHotKey("Ctrl+X");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“卡片显示”按钮
//	 * @param menubar
//	 * @return
//	 */
//	private MenuItem createCardShowMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("card");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("卡片布局ID", CARD_LAYOUT_ID);
//		paramMap.put("切换状态", "1");
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("Z");
//		menuItem.setDisplayHotKey("Ctrl+Z");
//		return menuItem;
//	}
//	
//	/**
//	 * 生成“流程”子菜单
//	 * @param menubar
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createWorkflowMenuItem(MenubarComp menubar, String billType, String type) {
//		MenuItem item = new MenuItem();
//		item.setId(menubar.getId() + "workflowMenuItem");
//		item.setText("流程");
//		//TODO 多语
//		
//		// 增加子项
//		createSubmitMenuItem(item, billType, type);
//		createReCallMenuItem(item, billType, type);
//		createApproveMenuItem(item, billType, type);
//		createUnApproveMenuItem(item, billType, type);
//		menubar.addMenuItem(item);
//		
//		return item;
//	}

	/**
	 * 生成“提交”按钮
	 * @param menubar
	 * @param billType
	 * @param type
	 * @return
	 */
//	private MenuItem createSubmitMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("submit");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", masterDsId);
////		paramMap.put("单据类型", pageId);
//		paramMap.put("单据类型", billType);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			
//		}
//		
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		menuItem.setHotKey("W");
//		menuItem.setDisplayHotKey("Ctrl+W");
//		
//		((SubmitMenuItem)defaultItem).setBillType(billType);
//		((SubmitMenuItem)defaultItem).setSourceFolder(sourceFolder);
//		((SubmitMenuItem)defaultItem).setPackageName(packageName);
//		defaultItem.afterAdd();
//		
//		return menuItem;
//	}
//
//	/**
//	 * 生成“审核”按钮
//	 * @param menubar
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createApproveMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("approve");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", masterDsId);
////		paramMap.put("单据类型", pageId);
//		paramMap.put("单据类型", billType);
//		
//		paramMap.put("聚合VO", aggVO);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			
//		}
//		
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//
//		((ApproveMenuItem)defaultItem).setBillType(billType);
//		((ApproveMenuItem)defaultItem).setSourceFolder(sourceFolder);
//		((ApproveMenuItem)defaultItem).setPackageName(packageName);
//		defaultItem.afterAdd();
//		
//		menuItem.setHotKey("U");
//		menuItem.setDisplayHotKey("Ctrl+U");
//		return menuItem;
//	}
//	
//	
//	/**
//	 * 生成收回按钮
//	 * @param item
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createReCallMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("recall");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", masterDsId);
////		paramMap.put("单据类型", pageId);
//		paramMap.put("单据类型", billType);
//		
//		paramMap.put("聚合VO", aggVO);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			
//		}
//		
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//
//		((ApproveMenuItem)defaultItem).setBillType(billType);
//		((ApproveMenuItem)defaultItem).setSourceFolder(sourceFolder);
//		((ApproveMenuItem)defaultItem).setPackageName(packageName);
//		defaultItem.afterAdd();
//		
//		menuItem.setHotKey("U");
//		menuItem.setDisplayHotKey("Ctrl+U");
//		return menuItem;
//	}
//
//
//	/**
//	 * 生成“弃审”按钮
//	 * @param menubar
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createUnApproveMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("unapprove");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", masterDsId);
////		paramMap.put("单据类型", pageId);
//		paramMap.put("单据类型", billType);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			
//		}
//		
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//
//		((UnApproveMenuItem)defaultItem).setBillType(billType);
//		((UnApproveMenuItem)defaultItem).setSourceFolder(sourceFolder);
//		((UnApproveMenuItem)defaultItem).setPackageName(packageName);
//		defaultItem.afterAdd();
//		menuItem.setHotKey("U");
//		menuItem.setModifiers(8);
//		menuItem.setDisplayHotKey("Alt+U");
//		return menuItem;
//	}

	/**
	 * 生成“审批状态”按钮
	 * @param menubar
	 * @param billType
	 * @param type
	 * @return
	 */
//	private MenuItem createApproveStateMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("approvestate");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", masterDsId);
//		paramMap.put("单据类型", pageId);
////		paramMap.put("单据类型", pageId);
//		paramMap.put("单据类型", billType);
//		
//		if (type.equals(CARD_TYPE)) {  // 卡片类型
//			
//		} else if (type.equals(LIST_TYPE)) {  // 列表类型
//			
//		}
//		
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//	
	
	/**
	 * 生成“行操作”子菜单
	 * @param menubar
	 * @param type
	 * @return
	 */
//	private MenuItem creatLineMenuItem(MenubarComp menubar, String type) {
//		MenuItem item = new MenuItem();
//		item.setId(menubar.getId() + "lineMenuItem");
//		item.setText("行操作");
//		//TODO 多语
//		
//		// 子数据集ID参数
//		String dsIds = "";
//		for (String slaveDsId : slaveDsIds) {
//			dsIds += slaveDsId + ",";
//		}
//		if (dsIds.length() > 0)
//			dsIds = dsIds.substring(0, dsIds.length() - 1);
//
//		// 增加子项
//		createAddLineMenuItem(item, dsIds, type);
//		createInsertLineMenuItem(item, dsIds, type);
//		createDeleteLineMenuItem(item, dsIds, type);
//		createCopyLineMenuItem(item, dsIds, type);
//		createPasteLineMenuItem(item, dsIds, type);
//		
//		menubar.addMenuItem(item);
//		
//		return item;
//		
//	}

	/**
	 * 生成“行增加”按钮
	 * @param item
	 * @param dsIds
	 * @return
	 */
//	private MenuItem createAddLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("add_line");
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		//页签数据集
//		paramMap.put("页签数据集", tabidsDsmap);
//		//paramMap.put("数据集", dsIds);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("页签", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("页签", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("页签", LIST_SINGLE_TAB_ID);
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * 生成“行插入”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createInsertLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("insert_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		//paramMap.put("页签", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("页签", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("页签", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("页签", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * 生成“行删除”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createDeleteLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("delete_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		//paramMap.put("页签", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("页签", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("页签", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("页签", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * 生成“行复制”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createCopyLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("copy_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		//paramMap.put("页签", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("页签", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("页签", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("页签", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * 生成“行粘贴”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createPasteLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("paste_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		//paramMap.put("页签", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("页签", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("页签", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("页签", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
	
	/**
	 * 创建新的MenuItem
	 * @param menubar
	 * @param parentItem
	 * @param defaultItem
	 * @param paramMap
	 * @param type
	 * @return
	 */
//	private MenuItem createNewMenuItem(MenubarComp menubar, MenuItem parentItem, DefaultItem defaultItem, Map<String, Object> paramMap, String type) {
//		
//		MenuItem menuItem = defaultItem.generateMenuItem();
//		// 设置ID
//		String id = menuItem.getId();
//		//menuItem.setId(type + "_" + id);
//		String listenerId = "menu_item_" + id + "_listener";
//		String genClazz = classPrefix + defaultItem.getSuperClazz().substring(defaultItem.getSuperClazz().lastIndexOf('.') + 1);
//		
//		JsListenerConf listener = generateListener(genClazz, defaultItem, listenerId, paramMap, type);
//		menuItem.addListener(listener);
//		
//		if (parentItem != null)
//			parentItem.addMenuItem(menuItem);
//		else
//			menubar.addMenuItem(menuItem);
//		
//		return menuItem;
//	}
	
	
//	/**
//	 * 构造行操作Toolbar
//	 * @return
//	 */
//	private ToolBarComp createLineToolbar(String type) {
//		ToolBarComp toolbar = new ToolBarComp();
//		toolbar.setId(LINE_TOOLBAR_ID);
//		toolbar.setTransparent(true);
//		LfwWidget widget = getMainWidget();
//		
//		// 子数据集ID参数
//		String dsIds = "";
//		for (String slaveDsId : slaveDsIds) {
//			dsIds += slaveDsId + ",";
//		}
//		if (dsIds.length() > 0)
//			dsIds = dsIds.substring(0, dsIds.length() - 1);
//
//		// 增加子项
//		createPasteLineToolItem(toolbar, dsIds, type);
//		createCopyLineToolItem(toolbar, dsIds, type);
//		createDeleteLineToolItem(toolbar, dsIds, type);
//		createInsertLineToolItem(toolbar, dsIds, type);
//		createAddLineToolItem(toolbar, dsIds, type);
//		
//		widget.getViewComponents().addComponent(toolbar);
//		
//		return toolbar;
//	}
//
//	/**
//	 * 生成“行增加”按钮
//	 * @param item
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createAddLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/add_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("add_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * 生成“行插入”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createInsertLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/insert_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("insert_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * 生成“行删除”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createDeleteLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/delete_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("delete_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * 生成“行复制”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createCopyLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/copy_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("copy_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * 生成“行粘贴”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createPasteLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/paste_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("paste_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//	
//	/**
//	 * 创建新的ToolBarItem
//	 * @param toolbar
//	 * @param defaultItem
//	 * @param paramMap
//	 * @param refImg 图片路径
//	 * @param type
//	 * @return
//	 */
//	private ToolBarItem createNewToolbarItem(ToolBarComp toolbar, DefaultItem defaultItem, Map<String, Object> paramMap, String refImg, String type) {
//
//		String id = defaultItem.getId();
//		
//		ToolBarItem toolItem = new ToolBarItem();
//		toolItem.setId(id);
//		toolItem.setAlign("right");
//		toolItem.setRefImg(refImg);
//		toolItem.setTip(defaultItem.getText());
//		
//		String listenerId = "tool_item_" + id + "_listener";
//		String genClazz = classPrefix + defaultItem.getSuperClazz().substring(defaultItem.getSuperClazz().lastIndexOf('.') + 1);
//		
//		JsListenerConf listener = generateListener(genClazz, defaultItem, listenerId, paramMap, type);
//		toolItem.addListener(listener);
//		
//		toolbar.addElement(toolItem);
//		
//		return toolItem;
//	}
	
	/**
	 * 构造行操作Menubar
	 * @return
	 */
//	private MenubarComp createLineMenubar(String type) {
//		MenubarComp menubar = new MenubarComp();
//		menubar.setId(LINE_MENUBAR_ID);
//		
//		// 子数据集ID参数
//		String dsIds = "";
//		for (String slaveDsId : slaveDsIds) {
//			dsIds += slaveDsId + ",";
//		}
//		if (dsIds.length() > 0)
//			dsIds = dsIds.substring(0, dsIds.length() - 1);
//
//		// 增加子项
//		createAddLineMenuItem(menubar, dsIds, type);
//		createInsertLineMenuItem(menubar, dsIds, type);
//		createDeleteLineMenuItem(menubar, dsIds, type);
//		createCopyLineMenuItem(menubar, dsIds, type);
//		createPasteLineMenuItem(menubar, dsIds, type);
//		
//		this.pagemeta.getViewMenus().addMenuBar(menubar);
//		
//		return menubar;
//	}
	
	

	/**
	 * 生成“行增加”按钮
	 * @param item
	 * @param dsIds
	 * @return
	 */
//	private MenuItem createAddLineMenuItem(MenubarComp menubar, String dsIds, String type) {
//		
//		String imgIcon = "/themes/${t}/images/menubar/add.gif";
//		String imgIconOn = "/themes/${t}/images/menubar/add_on.gif";
//		String imgIconDisable = "/themes/${t}/images/menubar/add_disable.gif";
////		
////		String imgIcon = "/lfw/images/bodymenubar/add.gif";
////		String imgIconOn = "/lfw/images/bodymenubar/add_on.gif";
////		String imgIconDisable = "/lfw/images/bodymenubar/add_disable.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("add_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		//paramMap.put("数据集", dsIds);
//		paramMap.put("页签数据集", tabidsDsmap);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("页签", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("页签", LIST_TAB_ID);
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * 生成“行插入”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createInsertLineMenuItem(MenubarComp menubar, String dsIds, String type) {
//		
//		String imgIcon = "/themes/${t}/images/menubar/insert.gif";
//		String imgIconOn = "/themes/${t}/images/menubar/insert_on.gif";
//		String imgIconDisable = "/themes/${t}/images/menubar/insert_disable.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("insert_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * 生成“行删除”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createDeleteLineMenuItem(MenubarComp menubar, String dsIds, String type) {
//		String imgIcon = "/themes/${t}/images/menubar/delete.gif";
//		String imgIconOn = "/themes/${t}/images/menubar/delete_on.gif";
//		String imgIconDisable = "/themes/${t}/images/menubar/delete_disable.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("delete_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * 生成“行复制”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createCopyLineMenuItem(MenubarComp menubar, String dsIds, String type) {
//		String imgIcon = "/themes/${t}/images/menubar/copy.gif";
//		String imgIconOn = "/themes/${t}/images/menubar/copy_on.gif";
//		String imgIconDisable = "/themes/${t}/images/menubar/copy_disable.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("copy_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * 生成“行粘贴”按钮
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createPasteLineMenuItem(MenubarComp menubar, String dsIds, String type) {
//		String imgIcon = "/themes/${t}/images/menubar/paste.gif";
//		String imgIconOn = "/themes/${t}/images/menubar/paste_on.gif";
//		String imgIconDisable = "/themes/${t}/images/menubar/paste_disable.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("paste_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("片段", mainWidgetId);
//		paramMap.put("数据集", dsIds);
//		paramMap.put("页签", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
	
	/**
	 * 创建新的MenuItem（仅为图片，不包含文字）
	 * @param menubar
	 * @param defaultItem
	 * @param paramMap
	 * @param imgIcon 图片
	 * @param imgIconOn 鼠标覆盖时的图片
	 * @param imgIconDisable 禁用时的图片
	 * @param type
	 * @return
	 */
//	private MenuItem createNewMenuItem(MenubarComp menubar, DefaultItem defaultItem, Map<String, Object> paramMap, String imgIcon, String imgIconOn, String imgIconDisable, String type) {
//		
//		MenuItem menuItem = defaultItem.generateImgMenuItem(imgIcon, imgIconOn, imgIconDisable);
//		
//		// 设置ID
//		String id = menuItem.getId();
//		
//		String listenerId = "menu_item_" + id + "_listener";
//		String genClazz = classPrefix + defaultItem.getSuperClazz().substring(defaultItem.getSuperClazz().lastIndexOf('.') + 1);
//		
//		JsListenerConf listener = generateListener(genClazz, defaultItem, listenerId, paramMap, type);
//		menuItem.addListener(listener);
//		
//		menubar.addMenuItem(menuItem);
//		
//		return menuItem;
//	}
	
	/**
	 * 生成Listener
	 * 
	 * @param genClazz
	 * @param defaultItem
	 * @param listenerId
	 * @param paramMap
	 * @param type
	 * @return
	 */
//	private JsListenerConf generateListener(String genClazz,DefaultItem defaultItem, String listenerId, Map<String, Object> paramMap, String type) {
//		
//		String superClazz = defaultItem.getSuperClazz();
//		JsListenerConf listener = new MouseListener();
////		if (CARD_TYPE.equals(type))
////			listener.setServerClazz(packageName + ".card." + genClazz);
////		else if (LIST_TYPE.equals(type))
////			listener.setServerClazz(packageName + ".list." + genClazz);
//
//		listener.setServerClazz(packageName + "." + genClazz);
//		listener.setId(listenerId);
//		
//		EventHandlerConf event = new EventHandlerConf();
//		event.setAsync(true);
//		event.setName("onclick");
//		event.setOnserver(true);
//		
//		listener.addEventHandler(event);
//		
//		LfwParameter param = new LfwParameter();
//		param.setName("mouseEvent");
//		event.addParam(param);
//		
//		// 生成提交规则
//		EventSubmitRule sr = defaultItem.generateSubmitRule(mainWidgetId, masterDsId, slaveDsIds, tabidsDsmap);
//		event.setSubmitRule(sr);
//		
//		
//		// 保存Java文件
//		String javaData = LFWConnector.generatorClass(listener.getServerClazz(), superClazz, paramMap);
//		String folderPath = LFWPersTool.getProjectPath() + "/" +  sourceFolder + "/" + listener.getServerClazz().substring(0, listener.getServerClazz().lastIndexOf(genClazz) - 1).replace('.', '/');
//		FileUtil.saveToFile(folderPath, genClazz + ".java", javaData);
//		
//		return listener;
//	}
	
	/**
	 * 获取当前Pagemeta的主Widget（含有Dataset最多的Widget）
	 * @return
	 */
	private LfwWidget getMainWidget() {
		LfwWidget[] widgets = pagemeta.getWidgets();
		if (widgets.length > 0) {
			LfwWidget widget = widgets[0];
			for (int i = 1, n = widgets.length; i < n; i++) {
				if (widget.getViewModels().getDatasets().length == 0 
						|| widgets[i].getViewModels().getDatasets().length > widget.getViewModels().getDatasets().length) {
					widget = widgets[i];
				}
			}
			return widget;
		}
		return null;
	}
	
	/**
	 * 获取主Dataset的ID
	 * @param widget
	 * @return
	 */
	private String getMasterDataset(LfwWidget widget) {
		if (null != widget.getViewModels().getDsrelations()) {
			DatasetRelation[] relationList = widget.getViewModels().getDsrelations().getDsRelations();
			if (relationList.length > 0) {
				DatasetRelation relation = relationList[0];
				String masterDataset = relation.getMasterDataset();
				return masterDataset;
			}
		}
		Dataset[] dss = widget.getViewModels().getDatasets();
		for (int i = 0; i < dss.length; i++) {
			if(dss[i] instanceof IRefDataset)
				continue;
			return dss[i].getId();
		}
		return "";
	}
	
}
