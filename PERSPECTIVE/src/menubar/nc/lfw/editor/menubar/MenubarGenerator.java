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
 * �˵��Զ�������
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
//	//�Ƿ�֧�ֶ�״̬��ʾ
//	private boolean isSupportMultiVisible;
	private List<String> slaveDsIds = new ArrayList<String>();
//	private Map<String, String> tabidsDsmap = new HashMap<String, String>();
//	private String pageId;
//	private String aggVO;
	
	// �в�����ť�Ƿ񵥶��ŵ�toolbar��
//	private boolean lineItemsInToolbar = false;
	
//	private static final String CARD_TYPE = "card";
//	private static final String LIST_TYPE = "list";
	
	// �в����еġ�ҳǩ������
//	private static final String TAB_ID = "bodyTab_card";
	
//	private static final String LIST_SINGLE_TAB_ID = "headTab_list";
	
//	private static final String LIST_TAB_ID = "bodyTab_list";
	// ����Ƭ���֡�ID����
//	private static final String CARD_LAYOUT_ID = "manageCard";
//	// �в���Toolbar��ID
//	private static final String LINE_TOOLBAR_ID = "bodyToolbar";
	// �в���Menubar��ID
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
	 * �Զ�����Menubar
	 * @param pageType ҳ������
	 * @param billType ��������
	 */
	public void generateMenubar(int pageType, String billType) {
//		LfwWidget mainWidget =  getMainWidget();
		//list����ͷ
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
	 * ���ɿ�Ƭ�Ͳ˵�
	 * 
	 * @param pageType ҳ������
	 * @param isSignal �Ƿ��ǵ���ͷ���壩
	 * @param billType ��������
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
//		if (!isSignal) {  // ������
//			if (this.lineItemsInToolbar) {  // �в�����ť����һ��Menubar��
////				createLineToolbar(CARD_TYPE);
//				createLineMenubar(CARD_TYPE);
//			}
//			else {  // �в�����Ϊһ���Ӳ˵�
//				creatLineMenuItem(menubar, CARD_TYPE);
//			}
//		}
//		if (billType != null && !"".equals(billType)) {  // �ǹ�����
//			createWorkflowMenuItem(menubar, billType, CARD_TYPE);
//			createQueryPfInfoMenuItem(menubar, billType, CARD_TYPE);
//		}
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST 
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {  // ������
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
	 * �����б��Ͳ˵�
	 * 
	 * @param pageType ҳ������
	 * @param isSignal �Ƿ��ǵ���ͷ���壩
	 * @param isWorkflow �Ƿ�������
	 * @param billType ��������
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
////		if (!isSignal) {  // ������
////			if (this.lineItemsInToolbar) {  // �в�����ť����һ��Menubar��
//////				createLineToolbar(CARD_TYPE);
////				createLineMenubar(LIST_TYPE);
////			}
////			else {  // �в�����Ϊһ���Ӳ˵�
////				if(pageType == ExtAttrConstants.PAGETYPE_LIST_SINGAL){
////					creatLineMenuItem(menubar, LIST_SINGLE_TAB_ID);
////				}
////				else
////					creatLineMenuItem(menubar, LIST_TYPE);
////			}
////		}
//		//list����
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
//		if (billType != null) {  // �ǹ�����
//			createWorkflowMenuItem(menubar, billType, LIST_TYPE);
//		}
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST 
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {  // ������
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
					} else {  // �����ֶ�
						
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
	 * ���ɡ��½�����ť
	 * @param menubar
	 * @param type
	 * @param pageType
	 * @return
	 */
//	private MenuItem createAddMenuItem(MenubarComp menubar, String type, int pageType) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("add");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
//		paramMap.put("�Ƿ��е���", false);
//		// �жϿ�Ƭ���Ȼ��б�����
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST) {
////			paramMap.put("�л���Ƭ", "manageCard");
////			paramMap.put("�л���Ƭ�����", "0");
////			paramMap.put("ҳ��״̬", "1");
////		} else if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {
////			paramMap.put("�л���Ƭ", "manageCard");
////			paramMap.put("�л���Ƭ�����", "1");
////			paramMap.put("ҳ��״̬", "1");
////		}
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_add");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
//			defaultItem.setId("list_add");
//		}
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("N");
//		menuItem.setDisplayHotKey("Ctrl+N");
//		return menuItem;
//	}
	
	
	/**
	 * ���ɿ�����ť
	 * @param menubar
	 * @param type
	 * @param pageType
	 * @return
	 */
//	private MenuItem createCopyMenuItem(MenubarComp menubar, String type, int pageType) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("copy");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
//		paramMap.put("�Ƿ��е���", false);
//		// �жϿ�Ƭ���Ȼ��б�����
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST) {
////			paramMap.put("�л���Ƭ", "manageCard");
////			paramMap.put("�л���Ƭ�����", "0");
////			paramMap.put("ҳ��״̬", "1");
////		} else if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {
////			paramMap.put("�л���Ƭ", "manageCard");
////			paramMap.put("�л���Ƭ�����", "1");
////			paramMap.put("ҳ��״̬", "1");
////		}
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_copy");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
//			defaultItem.setId("list_copy");
//		}
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("N");
//		menuItem.setDisplayHotKey("Ctrl+C");
//		return menuItem;
//	}
//	
//	/**
//	 * ���ɡ��޸ġ���ť
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createEditMenuItem(MenubarComp menubar, String type, int pageType) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("edit");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
//		// �жϿ�Ƭ���Ȼ��б�����
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST) {
////			paramMap.put("�л���Ƭ", "manageCard");
////			paramMap.put("�л���Ƭ�����", "0");
////			paramMap.put("ҳ��״̬", "1");
////		} else if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST) {
////			paramMap.put("�л���Ƭ", "manageCard");
////			paramMap.put("�л���Ƭ�����", "1");
////			paramMap.put("ҳ��״̬", "1");
////		}
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_edit");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ�ɾ������ť
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createDeleteMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("delete");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_delete");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ����桱��ť
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createSaveMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("save");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_save");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ�ȡ������ť
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createCancelMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("cancel");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_cancel");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ���ӡ����ť
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createPrintMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("print");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
//		if(slaveDsIds != null && slaveDsIds.size() > 0)
//			paramMap.put("�����ݼ�", slaveDsIds.get(0));
//		else 
//			paramMap.put("�����ݼ�", null);
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_print");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ���ѯ����ť
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createQueryMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("query");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		String page = pageId + ".qry";
//		paramMap.put("��ѯҳ��", page);
//		paramMap.put("Ŀ�길Ƭ��", mainWidgetId);
//		paramMap.put("Ŀ�길���ݼ�", masterDsId);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			defaultItem.setId("card_query");
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ������������ݡ���ť
//	 * @param menubar
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createQueryPfInfoMenuItem(MenubarComp menubar, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("querypfinfo");
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("�����ݼ�", masterDsId);
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
//	 * ���ɡ��б���ʾ����ť
//	 * @param menubar
//	 * @return
//	 */
//	private MenuItem createListShowMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("list");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("��Ƭ����ID", CARD_LAYOUT_ID);
//		paramMap.put("�л�״̬", "2");
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("X");
//		menuItem.setDisplayHotKey("Ctrl+X");
//		return menuItem;
//	}
//	
//	/**
//	 * ���ɡ���Ƭ��ʾ����ť
//	 * @param menubar
//	 * @return
//	 */
//	private MenuItem createCardShowMenuItem(MenubarComp menubar, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("card");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("��Ƭ����ID", CARD_LAYOUT_ID);
//		paramMap.put("�л�״̬", "1");
//		
//		MenuItem menuItem = createNewMenuItem(menubar, null, defaultItem, paramMap, type);
//		menuItem.setHotKey("Z");
//		menuItem.setDisplayHotKey("Ctrl+Z");
//		return menuItem;
//	}
//	
//	/**
//	 * ���ɡ����̡��Ӳ˵�
//	 * @param menubar
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createWorkflowMenuItem(MenubarComp menubar, String billType, String type) {
//		MenuItem item = new MenuItem();
//		item.setId(menubar.getId() + "workflowMenuItem");
//		item.setText("����");
//		//TODO ����
//		
//		// ��������
//		createSubmitMenuItem(item, billType, type);
//		createReCallMenuItem(item, billType, type);
//		createApproveMenuItem(item, billType, type);
//		createUnApproveMenuItem(item, billType, type);
//		menubar.addMenuItem(item);
//		
//		return item;
//	}

	/**
	 * ���ɡ��ύ����ť
	 * @param menubar
	 * @param billType
	 * @param type
	 * @return
	 */
//	private MenuItem createSubmitMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("submit");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", masterDsId);
////		paramMap.put("��������", pageId);
//		paramMap.put("��������", billType);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ���ˡ���ť
//	 * @param menubar
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createApproveMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("approve");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", masterDsId);
////		paramMap.put("��������", pageId);
//		paramMap.put("��������", billType);
//		
//		paramMap.put("�ۺ�VO", aggVO);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * �����ջذ�ť
//	 * @param item
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createReCallMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("recall");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", masterDsId);
////		paramMap.put("��������", pageId);
//		paramMap.put("��������", billType);
//		
//		paramMap.put("�ۺ�VO", aggVO);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
//	 * ���ɡ����󡱰�ť
//	 * @param menubar
//	 * @param billType
//	 * @param type
//	 * @return
//	 */
//	private MenuItem createUnApproveMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("unapprove");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", masterDsId);
////		paramMap.put("��������", pageId);
//		paramMap.put("��������", billType);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
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
	 * ���ɡ�����״̬����ť
	 * @param menubar
	 * @param billType
	 * @param type
	 * @return
	 */
//	private MenuItem createApproveStateMenuItem(MenuItem item, String billType, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("approvestate");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", masterDsId);
//		paramMap.put("��������", pageId);
////		paramMap.put("��������", pageId);
//		paramMap.put("��������", billType);
//		
//		if (type.equals(CARD_TYPE)) {  // ��Ƭ����
//			
//		} else if (type.equals(LIST_TYPE)) {  // �б�����
//			
//		}
//		
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//	
	
	/**
	 * ���ɡ��в������Ӳ˵�
	 * @param menubar
	 * @param type
	 * @return
	 */
//	private MenuItem creatLineMenuItem(MenubarComp menubar, String type) {
//		MenuItem item = new MenuItem();
//		item.setId(menubar.getId() + "lineMenuItem");
//		item.setText("�в���");
//		//TODO ����
//		
//		// �����ݼ�ID����
//		String dsIds = "";
//		for (String slaveDsId : slaveDsIds) {
//			dsIds += slaveDsId + ",";
//		}
//		if (dsIds.length() > 0)
//			dsIds = dsIds.substring(0, dsIds.length() - 1);
//
//		// ��������
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
	 * ���ɡ������ӡ���ť
	 * @param item
	 * @param dsIds
	 * @return
	 */
//	private MenuItem createAddLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("add_line");
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		//ҳǩ���ݼ�
//		paramMap.put("ҳǩ���ݼ�", tabidsDsmap);
//		//paramMap.put("���ݼ�", dsIds);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("ҳǩ", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("ҳǩ", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("ҳǩ", LIST_SINGLE_TAB_ID);
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * ���ɡ��в��롱��ť
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createInsertLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("insert_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		//paramMap.put("ҳǩ", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("ҳǩ", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("ҳǩ", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("ҳǩ", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * ���ɡ���ɾ������ť
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createDeleteLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("delete_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		//paramMap.put("ҳǩ", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("ҳǩ", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("ҳǩ", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("ҳǩ", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * ���ɡ��и��ơ���ť
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createCopyLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("copy_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		//paramMap.put("ҳǩ", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("ҳǩ", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("ҳǩ", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("ҳǩ", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
//
//	/**
//	 * ���ɡ���ճ������ť
//	 * @param dsIds
//	 * @return
//	 */
//	private MenuItem createPasteLineMenuItem(MenuItem item, String dsIds, String type) {
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("paste_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		//paramMap.put("ҳǩ", TAB_ID);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("ҳǩ", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("ҳǩ", LIST_TAB_ID);
//		else if(type.equals(LIST_SINGLE_TAB_ID))
//			paramMap.put("ҳǩ", LIST_SINGLE_TAB_ID);
//
//		MenuItem menuItem = createNewMenuItem(null, item, defaultItem, paramMap, type);
//		return menuItem;
//	}
	
	/**
	 * �����µ�MenuItem
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
//		// ����ID
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
//	 * �����в���Toolbar
//	 * @return
//	 */
//	private ToolBarComp createLineToolbar(String type) {
//		ToolBarComp toolbar = new ToolBarComp();
//		toolbar.setId(LINE_TOOLBAR_ID);
//		toolbar.setTransparent(true);
//		LfwWidget widget = getMainWidget();
//		
//		// �����ݼ�ID����
//		String dsIds = "";
//		for (String slaveDsId : slaveDsIds) {
//			dsIds += slaveDsId + ",";
//		}
//		if (dsIds.length() > 0)
//			dsIds = dsIds.substring(0, dsIds.length() - 1);
//
//		// ��������
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
//	 * ���ɡ������ӡ���ť
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
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * ���ɡ��в��롱��ť
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createInsertLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/insert_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("insert_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * ���ɡ���ɾ������ť
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createDeleteLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/delete_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("delete_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * ���ɡ��и��ơ���ť
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createCopyLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/copy_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("copy_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//
//	/**
//	 * ���ɡ���ճ������ť
//	 * @param dsIds
//	 * @return
//	 */
//	private ToolBarItem createPasteLineToolItem(ToolBarComp toolbar, String dsIds, String type) {
//		String refImg = "/lfw/images/bodytoolbar/paste_line.gif";
//		
//		DefaultItem defaultItem = DefaultMenuItemCreator.getDefaultMenuItem("paste_line");
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		ToolBarItem toolItem = createNewToolbarItem(toolbar, defaultItem, paramMap, refImg, type);
//		return toolItem;
//	}
//	
//	/**
//	 * �����µ�ToolBarItem
//	 * @param toolbar
//	 * @param defaultItem
//	 * @param paramMap
//	 * @param refImg ͼƬ·��
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
	 * �����в���Menubar
	 * @return
	 */
//	private MenubarComp createLineMenubar(String type) {
//		MenubarComp menubar = new MenubarComp();
//		menubar.setId(LINE_MENUBAR_ID);
//		
//		// �����ݼ�ID����
//		String dsIds = "";
//		for (String slaveDsId : slaveDsIds) {
//			dsIds += slaveDsId + ",";
//		}
//		if (dsIds.length() > 0)
//			dsIds = dsIds.substring(0, dsIds.length() - 1);
//
//		// ��������
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
	 * ���ɡ������ӡ���ť
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
//		paramMap.put("Ƭ��", mainWidgetId);
//		//paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ���ݼ�", tabidsDsmap);
//		if(type.equals(CARD_TYPE))
//			paramMap.put("ҳǩ", TAB_ID);
//		else if(type.equals(LIST_TYPE))
//			paramMap.put("ҳǩ", LIST_TAB_ID);
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * ���ɡ��в��롱��ť
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
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * ���ɡ���ɾ������ť
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
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * ���ɡ��и��ơ���ť
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
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
//
//	/**
//	 * ���ɡ���ճ������ť
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
//		paramMap.put("Ƭ��", mainWidgetId);
//		paramMap.put("���ݼ�", dsIds);
//		paramMap.put("ҳǩ", TAB_ID);
//
//		MenuItem item = createNewMenuItem(menubar, defaultItem, paramMap, imgIcon, imgIconOn, imgIconDisable, type);
//		return item;
//	}
	
	/**
	 * �����µ�MenuItem����ΪͼƬ�����������֣�
	 * @param menubar
	 * @param defaultItem
	 * @param paramMap
	 * @param imgIcon ͼƬ
	 * @param imgIconOn ��긲��ʱ��ͼƬ
	 * @param imgIconDisable ����ʱ��ͼƬ
	 * @param type
	 * @return
	 */
//	private MenuItem createNewMenuItem(MenubarComp menubar, DefaultItem defaultItem, Map<String, Object> paramMap, String imgIcon, String imgIconOn, String imgIconDisable, String type) {
//		
//		MenuItem menuItem = defaultItem.generateImgMenuItem(imgIcon, imgIconOn, imgIconDisable);
//		
//		// ����ID
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
	 * ����Listener
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
//		// �����ύ����
//		EventSubmitRule sr = defaultItem.generateSubmitRule(mainWidgetId, masterDsId, slaveDsIds, tabidsDsmap);
//		event.setSubmitRule(sr);
//		
//		
//		// ����Java�ļ�
//		String javaData = LFWConnector.generatorClass(listener.getServerClazz(), superClazz, paramMap);
//		String folderPath = LFWPersTool.getProjectPath() + "/" +  sourceFolder + "/" + listener.getServerClazz().substring(0, listener.getServerClazz().lastIndexOf(genClazz) - 1).replace('.', '/');
//		FileUtil.saveToFile(folderPath, genClazz + ".java", javaData);
//		
//		return listener;
//	}
	
	/**
	 * ��ȡ��ǰPagemeta����Widget������Dataset����Widget��
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
	 * ��ȡ��Dataset��ID
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
