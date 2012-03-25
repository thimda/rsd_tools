package nc.lfw.editor.menubar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwBaseGraph;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.menubar.action.AddMenuItemAction;
import nc.lfw.editor.menubar.action.DefaultMenuItemAction;
import nc.lfw.editor.menubar.action.DelMenuItemAction;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenuItemObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.lfw.editor.menubar.graph.MenuElementPart;
import nc.lfw.editor.menubar.page.MenubarViewPage;
import nc.lfw.editor.menubar.palette.MenubarPalette;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.palette.PaletteFactory;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWMenubarCompTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

public class MenubarEditor extends LFWBaseEditor {
	private MenubarGraph graph = new MenubarGraph();
	private MenubarElementObj menubarObj;
	/**
	 * �����޸ĵ���ʱmenubar����
	 */
	private MenubarComp menubarTemp;
//	private PageMeta pagemetaWithWidgets;
	
	/**
	 * �Ӳ˵����󼯺�
	 */
	private Map<String, List<MenuElementObj>> childrenElementMap = new HashMap<String, List<MenuElementObj>>();
	
	/**
	 * ��ʱ����½����Ӳ˵�
	 */
	private Map<String, MenuElementObj> tempChildrenElementMap = new HashMap<String, MenuElementObj>();
	
	/**
	 * ����������
	 */
	private Map<String, MenubarConnector> connectorMap = new HashMap<String, MenubarConnector>();
	
	/**
	 * ��ǰListenerͼ���������MenuItem����
	 */
	private MenuItem currentListenerMenuItem;

	public MenubarEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}

	
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}

	protected void setInput(IEditorInput input) {
		super.setInput(input);
		MenubarEditorInput menubarEditorInput = (MenubarEditorInput) input;
//		LFWMenubarCompTreeItem menubarTreeItem = menubarEditor.getMenubarTreeItem();
//		menubarTemp = (MenubarComp) ((MenubarComp) menubarTreeItem.getData()).clone();
		menubarTemp = (MenubarComp) menubarEditorInput.getCloneElement();
		MenubarElementObj menubarElement = new MenubarElementObj();
		menubarElement.setMenubar(menubarTemp);
		graph.addCell(menubarElement);
		this.menubarObj = menubarElement;
		
		// ��ʾMenubar
		int pointX = 100;
		int pointY = 100;
		// ������ʾλ��
		menubarObj.setSize(new Dimension(150, 150));
		Point point = new Point(pointX, pointY);
		menubarObj.setLocation(point);
		
		// ��ʾ�Ӳ˵�
		List<MenuItem> menuList = menubarElement.getMenubar().getMenuList();
		if (null != menuList)
			showChildMenus(menuList, menubarElement, null, 1);
		
		// ��ʾ�˵�֮������ӹ�ϵ
		List<MenuItemObj> menubarChildrenList = menubarObj.getChildrenList();
		if (null != menubarChildrenList)
			showRelations(menubarChildrenList, menubarObj, null);
		
		// ����Listener��ʾλ��
		setListenerPointX(100);
		setListenerPointY(500);
		addListenerCellToEditor(new HashMap<String, JsListenerConf>(), graph);
//		getJsListenerObj().setSize(new Dimension(150, 150));
//		this.pagemetaWithWidgets = ((LfwBaseEditorInput)input).getPageMeta();
		
	}
	
	/**
	 * ��ʾ�����Ӳ˵���
	 * @param menuList �Ӳ˵��б�
	 * @param menubarObj ���˵���ʾ����
	 * @param menuObj �Ӳ˵���ʾ����
	 * @param level ����
	 */
	private void showChildMenus(List<MenuItem> menuList, MenubarElementObj menubarObj, MenuElementObj menuObj, int level) {
		for (int i = 0, n = menuList.size(); i < n; i++) {
			MenuItem item = menuList.get(i);
			MenuItemObj itemObj = new MenuItemObj();
			itemObj.setMenuItem(item);
			List<MenuItem> childList = item.getChildList();
			if (null != childList && childList.size() > 0) {
				for (int j = 0; j < childList.size(); j++) {
					MenuItem childMenu = childList.get(j);
					// �����Ӳ˵�����
					MenuElementObj subMenuObj = new MenuElementObj();
					subMenuObj.setMenuItem(childMenu);
					// ���ü���
					subMenuObj.setLevel(level);
					
					itemObj.setChild(subMenuObj);
					if (null != menubarObj) {
						subMenuObj.setParentElementObj(menubarObj);
					}else if (null != menuObj){
						subMenuObj.setParentElementObj(menuObj);
					}
//					// �����Ӳ˵�����
//					MenuItemObj itemObj = new MenuItemObj();
//					itemObj.setMenuItem(childMenu);
//					itemObj.setChild(subMenuObj);
//					
//					// �����Ӳ˵�����
//					if (null != menubarObj) {
//						menubarObj.addChild(itemObj);
//						subMenuObj.setParentElementObj(menubarObj);
//					} else if (null != menuObj) {
//						menuObj.addChild(itemObj);
//						subMenuObj.setParentElementObj(menuObj);
//					}
					
					// ��ʾ�Ӳ˵�
					showChildMenuItem(subMenuObj, level);
					
					graph.addMenu(subMenuObj);
					
					// ������ʾ�Ӳ˵����Ӳ˵�
					List<MenuItem> childMenuList = childMenu.getChildList();
					showChildMenus(childMenuList, null, subMenuObj, level + 1);
				}
			}
			if (null != menubarObj) {
				menubarObj.addChild(itemObj);
			}else if (null != menuObj){
				menuObj.addChild(itemObj);
			}
		}
	}
	
	/**
	 * ��ʾ�Ӳ˵�
	 * @param itemObj
	 * @param level
	 */
	public void showChildMenuItem(MenuElementObj itemObj, int level) {
//		graph.addCell(itemObj);
		
		// ��ʾ�Ӳ˵�
		int pointX = 100 + level * 200;
//		LfwElementObjWithGraph parentObj = itemObj.getParentElementObj();
		
		if (!childrenElementMap.containsKey(String.valueOf(level)))
			childrenElementMap.put(String.valueOf(level), new ArrayList<MenuElementObj>());
		childrenElementMap.get(String.valueOf(level)).add(itemObj);
		
//		int levelY = 1;
//		if (null != parentObj) {
//			if (parentObj instanceof MenubarElementObj)
//				levelY = ((MenubarElementObj)parentObj).getChildrenList().size();
//			else if (parentObj instanceof MenuElementObj)
//				levelY = ((MenuElementObj)parentObj).getChildrenList().size();
//		} else {
//			levelY = childrenElementMap.get(String.valueOf(level)).size();
//		}
		int	itemIndex = childrenElementMap.get(String.valueOf(level)).size() - 1;
		int pointY = 100 + (itemIndex) * 150;
		// ������ʾλ��
		itemObj.setSize(new Dimension(100, 100));
		Point point = new Point(pointX, pointY);
		itemObj.setLocation(point);
		
		
	}
	
	/**
	 * ��ʾ�˵�֮������ӹ�ϵ
	 * 
	 * @param menubar
	 */
	private void showRelations(List<MenuItemObj> childrenList, MenubarElementObj menubarObj, MenuElementObj menuObj) {
		for (int i = 0, n = childrenList.size(); i < n; i++) {
			MenuElementObj subMenuObj = childrenList.get(i).getChild();
			if(subMenuObj == null){
				continue;
			}
			// ����������
			MenubarConnector con = null;
			if (null != menubarObj) {
				con = new MenubarConnector(menubarObj, subMenuObj);
			} else if (null != menuObj) {
				con = new MenubarConnector(menuObj, subMenuObj);
			}
			con.setId(subMenuObj.getMenuItem().getId() + MenubarConnector.ID_SUFFIX);
			con.connect();
			// ���������Ӳ˵���������
			showRelations(subMenuObj.getChildrenList(), null, subMenuObj);
			
			addConnector(con);
		}
	}
	
	public LfwBaseGraph getGraph() {
		return graph;
	}

	private KeyHandler shareKeyHandler = null;

	private KeyHandler getShareKeyHandler() {
		if (shareKeyHandler == null) {
			shareKeyHandler = new KeyHandler();
			shareKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0), 
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
		}
		return shareKeyHandler;
	}

	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScalableFreeformRootEditPart rootEditpart = new ScalableFreeformRootEditPart();
		getGraphicalViewer().setRootEditPart(rootEditpart);
		getGraphicalViewer().setEditPartFactory(new MenubarEditPartFactory(this));
		getGraphicalViewer().setKeyHandler(getShareKeyHandler());
		getGraphicalViewer().setContextMenu(getMenuManager());
	}

	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getGraphicalViewer().setContents(this.graph);
		getGraphicalViewer().addDropTargetListener(new nc.uap.lfw.perspective.editor
				.DiagramTemplateTransferDropTargetListener(getGraphicalViewer()));
		LFWPersTool.showView(IPageLayout.ID_PROP_SHEET);
	}

	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}

		};
	}

	private PaletteRoot paleteRoot = null;

	protected PaletteRoot getPaletteRoot() {
		if (paleteRoot == null) {
			paleteRoot = MenubarPalette.createPaletteRoot();
		}
		return paleteRoot;
	}

	public static MenubarEditor getActiveMenubarEditor() {
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if (page != null) {
			editor = page.getActiveEditor();

		}
		if (editor != null && editor instanceof MenubarEditor) {
			return (MenubarEditor) editor;
		} else {
			return null;
		}

	}

	
	public void setFocus() {
		super.setFocus();
		Tree tree = LFWPersTool.getTree();
		LFWSeparateTreeItem lfwSeparaTreeItem = null;
		IEditorInput input = getEditorInput();
		MenubarEditorInput menubarEditorInput = (MenubarEditorInput)input;
		MenubarComp menubarnComp = (MenubarComp) menubarEditorInput.getCloneElement();
		LFWWidgetTreeItem widgetTreeItem = getWidgetTreeItem();
		if(widgetTreeItem != null){
			if(LFWTool.NEW_VERSION.equals(widgetTreeItem.getLfwVersion())){
				lfwSeparaTreeItem = getWebSeparateTreeItem(WEBProjConstants.COMPONENTS);
			}else{
				TreeItem[] separasTreeItems = widgetTreeItem.getItems();
				for (int i = 0; i < separasTreeItems.length; i++) {
					TreeItem item = separasTreeItems[i];
					if(item instanceof LFWSeparateTreeItem){
						LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
						if(seitem.getText().equals(WEBProjConstants.WIDGET_MENUBAR)){
							lfwSeparaTreeItem = seitem;
							break;
						}
					}
				}
			}
			TreeItem[] childTreeItems = lfwSeparaTreeItem.getItems();
			for (int i = 0; i < childTreeItems.length; i++) {
				if(childTreeItems[i] instanceof LFWMenubarCompTreeItem){
					LFWMenubarCompTreeItem webT = (LFWMenubarCompTreeItem) childTreeItems[i];
					if(webT.getData() instanceof MenubarComp){
						MenubarComp gr = (MenubarComp) webT.getData();
						if(menubarnComp.getId().equals(gr.getId())){
							tree.setSelection(webT);
							break;
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * ����Pagemeta���ļ���
	 * 
	 * @param widget
	 */
	public void savePagemeta(PageMeta pagemeta) {
		// ���޸Ĺ���menubar�������pagemeta��
		pagemeta.getViewMenus().addMenuBar(menubarTemp);
		// ��ȡ��Ŀ·��
//		String projectPath = LFWPersTool.getProjectPath();
////		LFWMenubarCompTreeItem menubarTreeItem = input.getMenubarTreeItem();
////		LFWDirtoryTreeItem pagemetaTreeItem = LFWPersTool.getPagemetaTreeItem(menubarTreeItem);
//		LFWDirtoryTreeItem pagemetaTreeItem = LFWPersTool.getPagemetaTreeItem(LFWPersTool.getCurrentTreeItem());
//		String pagemetaNodePath = pagemetaTreeItem.getFile().getPath();
//		DataProviderForDesign dataProvider = new DataProviderForDesign();
//		// ����Widget��pagemeta.pm��
//		dataProvider.savePagemetaToXml(pagemetaNodePath, "pagemeta.pm",
//				projectPath, pagemeta);
		LFWPersTool.savePagemeta(pagemeta);
	}

	
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		
		IEditorInput input = getEditorInput();
		MenubarEditorInput menubarEditorInput = (MenubarEditorInput)input;
		if(menubarEditorInput.getWidget() != null)
			saveWidget();
		else
			savePagemeta(LFWPersTool.getCurrentPageMeta());
	}

	public void saveWidget() {
		// ���޸Ĺ���menubar�������pagemeta��
		MenubarComp clone = (MenubarComp)menubarTemp.clone();
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		widget.getViewMenus().addMenuBar(clone);
		// ��ȡ��Ŀ·��
//		String projectPath = LFWPersTool.getProjectPath();
//		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
//		//LFWDirtoryTreeItem pagemetaTreeItem = LFWPersTool.getPagemetaTreeItem(LFWPersTool.getCurrentTreeItem());
//		String filePath = widgetTreeItem.getFile().getPath();
//		DataProviderForDesign dataProvider = new DataProviderForDesign();
//		// ����Widget��pagemeta.pm��
//		dataProvider.saveWidgettoXml(filePath, "widget.wd", projectPath, widget);
		LFWPersTool.saveWidget(widget);
	}
	
	public LFWAbstractViewPage createViewPage() {
		return new MenubarViewPage();
	}

	
	public void saveJsListener(String jsListenerId,
			EventHandlerConf jsEventHandler, JsListenerConf listener) {
		MenuItem item = getCurrentListenerMenuItem();
		if(item != null){
			if (null != jsEventHandler) {
				if (item.getListenerMap().containsKey(jsListenerId)) {
					JsListenerConf jsListener = item.getListenerMap().get(jsListenerId);
					doSaveListenerEvent(jsListener, jsEventHandler);
				}
			} else {
				item.addListener(listener);
			}
			setDirtyTrue();
		}
		else{
			IEditorInput input = getEditorInput();
			MenubarEditorInput menubarEditorInput = (MenubarEditorInput)input;
			MenubarComp menubarnComp = (MenubarComp) menubarEditorInput.getCloneElement();
			if (null != jsEventHandler) {
				if (menubarnComp.getListenerMap().containsKey(jsListenerId)) {
					JsListenerConf jsListener = menubarnComp.getListenerMap().get(jsListenerId);
					doSaveListenerEvent(jsListener, jsEventHandler);
			} else {
				menubarnComp.addListener(listener);
			}
			setDirtyTrue();
			}
		}
	}

	
	protected void editMenuManager(IMenuManager manager) {
		if (null != getCurrentSelection()) {
			StructuredSelection ss = (StructuredSelection) getCurrentSelection();
			Object sel = ss.getFirstElement();
			if (sel instanceof MenuElementPart) {
				MenuElementPart lfwEle = (MenuElementPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof MenubarElementObj) {
					MenubarElementObj menubarObj = (MenubarElementObj) model;
					// ���� ������Ĭ�ϲ˵�� �˵���
					DefaultMenuItemAction defaultMenuItemAction = new DefaultMenuItemAction(menubarObj);
					
					// ����Menubar��ʼ��������
//					MenubarEditorInput input = (MenubarEditorInput) getEditorInput();
//					LFWMenubarCompTreeItem menubarTreeItem = input.getMenubarTreeItem();
					manager.add(defaultMenuItemAction);
					// ���� �������Զ���˵�� �˵���
					AddMenuItemAction addMenuItemAction = new AddMenuItemAction(menubarObj);
					
					manager.add(addMenuItemAction);
					
					// ��ȡ����ѡ�е�����
					Label label = menubarObj.getFigure().getCurrentLabel();
					if (null != label) {
						
						if (label instanceof MenuItemLabel) {
							DelMenuItemAction delMenuItemAction = new DelMenuItemAction((MenuItemLabel)label,
									menubarObj, ((MenuItemLabel)label).getText());
							manager.add(delMenuItemAction);
						}
					}
				}
				if (model instanceof MenuElementObj) {
					MenuElementObj menuObj = (MenuElementObj) model;
					
					// ���� ������Ĭ�ϲ˵�� �˵���
					DefaultMenuItemAction defaultMenuItemAction = new DefaultMenuItemAction(menuObj);
					
					// ���� �������Զ���˵�� �˵���
					AddMenuItemAction addMenuItemAction = new AddMenuItemAction(menuObj);
					
					manager.add(defaultMenuItemAction);
					manager.add(addMenuItemAction);
					
					// ��ȡ����ѡ�е�����
					Label label = menuObj.getFigure().getCurrentLabel();
					if (null != label) {
						DelMenuItemAction delMenuItemAction = new DelMenuItemAction((MenuItemLabel)label,
								menuObj, ((MenuItemLabel)label).getText());
						manager.add(delMenuItemAction);
					}
				}
			} else {
				return;
			}
		} else {
			return;
		}

	}
	
	/**
	 * ˢ������ҳ��˵��������ʾͼ��
	 */
	public void refreshAllElementObj() {
		if (null != menubarObj)
			menubarObj.getFigure().refresh();
		for (String key : childrenElementMap.keySet()) {
			List<MenuElementObj> childElementObjList = childrenElementMap.get(key);
			for (MenuElementObj menuElementObj : childElementObjList) {
				menuElementObj.getFigure().refresh();
			}
		}
	}

	
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		MenuItem item = menubarObj.getMenubar().getMenuList().get(0);
		if (null != menubarObj && null != item)
			return item.getAcceptListeners();
		return null;
	}

	
	public void removeJsListener(JsListenerConf jsListenerId) {
		// TODO Auto-generated method stub

	}

	public MenubarElementObj getMenubarObj() {
		return menubarObj;
	}

	
	public void addTempChildMenuElement(MenuElementObj menuObj) {
		tempChildrenElementMap.put(menuObj.getId(), menuObj);
	}

	public Map<String, MenuElementObj> getTempChildrenElementMap() {
		return tempChildrenElementMap;
	}

//	public PageMeta getPagemetaWithWidgets() {
//		return pagemetaWithWidgets;
//	}

	/**
	 * ��ȡ����������
	 * @param key
	 * @return
	 */
	public MenubarConnector getConnector(String key) {
		if (connectorMap.containsKey(key))
			return connectorMap.get(key);
		return null;
	}

	/**
	 * ����������
	 * @param connector
	 */
	public void addConnector(MenubarConnector connector) {
		connectorMap.put(((MenuElementObj)connector.getTarget()).getMenuItem().getId(), connector);
	}

	/**
	 * ɾ��������
	 * @param key
	 */
	public void removeConnector(String key) {
		if (connectorMap.containsKey(key))
			connectorMap.remove(key);
	}

	/**
	 * ��ȡ��ǰListenerͼ���������MenuItem����
	 * @return
	 */
	public MenuItem getCurrentListenerMenuItem() {
		return currentListenerMenuItem;
	}

	/**
	 * ���õ�ǰListenerͼ���������MenuItem����
	 * @param currentListenerMenuItem
	 */
	public void setCurrentListenerMenuItem(MenuItem currentListenerMenuItem) {
		this.currentListenerMenuItem = currentListenerMenuItem;
	}


	public String getPath() {
		LFWDirtoryTreeItem pagemetaTreeItem = LFWPersTool.getPagemetaTreeItem(LFWPersTool.getCurrentTreeItem());
		String pagemetaNodePath = pagemetaTreeItem.getFile().getPath();
		return pagemetaNodePath + "/pagemeta.pm";
	}


	public MenubarComp getMenubarTemp() {
		return menubarTemp;
	}


	protected LfwElementObjWithGraph getLeftElement() {
		return null;
	}


	protected LfwElementObjWithGraph getTopElement() {
		return getMenubarObj();
	}
	
	@Override
	public List<JsEventDesc> getAcceptEventDescs() {
		if(menubarTemp != null){
			return menubarTemp.getAcceptEventDescs();
		}else{
			return menubarObj.getMenubar().getAcceptEventDescs();
		}
	}

	public void setMenubarObj(MenubarElementObj menubarObj) {
		this.menubarObj = menubarObj;
	}
	
	public LfwWidget getWidget() {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		if(widgetTreeItem != null){
			return widgetTreeItem.getWidget();
		}
		return null;
	}

	public void setWidget(LfwWidget widget) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		if(widgetTreeItem != null){
			widgetTreeItem.setWidget(widget);
		}
	}

	public void setMenubarTemp(MenubarComp menubarTemp) {
		this.menubarTemp = menubarTemp;
	}

}
