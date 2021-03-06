package nc.lfw.editor.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.parts.LFWElementPart;
import nc.uap.lfw.perspective.listener.DeleteJsListenerAction;
import nc.uap.lfw.perspective.listener.EventEditorControl;
import nc.uap.lfw.perspective.listener.EventEditorControlComp;
import nc.uap.lfw.perspective.listener.EventEditorHandler;
import nc.uap.lfw.perspective.listener.JsListenerLabel;
import nc.uap.lfw.perspective.listener.LFWGraphicalEditorWithFlyoutPalette;
import nc.uap.lfw.perspective.listener.ListenerEditorHandler;
import nc.uap.lfw.perspective.listener.ListenerElementObj;
import nc.uap.lfw.perspective.listener.NewJsListenerAction;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.views.ILFWViewPage;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWProjectTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWVirtualDirTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;

/**
 * LFW工具基本编辑器（包含对Listener的编辑功能以及扩展属性视图）
 * @author guoweic
 *
 */
public abstract class LFWBaseEditor extends LFWGraphicalEditorWithFlyoutPalette {
	
	/**
	 * JS编辑器使用的input
	 */
	private IEditorInput fileEditorInput;
	
	private IProgressMonitor monitor;

	// 当前选中的内容
	private ISelection currentSelection;
	/**
	 * 编辑器是否为脏的标识
	 */
	private boolean dirty = false;
	
	private LFWWidgetTreeItem widgetTreeItem = null;
	
	public LFWWidgetTreeItem getWidgetTreeItem() {
		if(widgetTreeItem == null){
			widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		}
		return widgetTreeItem;
	}

	public void setWidgetTreeItem(LFWWidgetTreeItem widgetTreeItem) {
		this.widgetTreeItem = widgetTreeItem;
	}

	public void setJsListenerObj(ListenerElementObj jsListenerObj) {
		this.jsListenerObj = jsListenerObj;
	}

	public LfwBaseGraph getGraph() {
		return null;
	}
	
	/**
	 * 关闭编辑器时执行
	 */
	 public void dispose() {
		 super.dispose();
		 if(getEditorInput() instanceof WidgetEditorInput){
		      LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		      if(view != null){
		    	 boolean isdirty =  isDirty();
		    	 if(isdirty){
		    		deleteNode();
		    	 }
		      }
		 }
		 
	}

	 public void  deleteNode(){
	 }
	 
	public void setCurrentSelection(ISelection currentSelection) {
		this.currentSelection = currentSelection;
	}

	/**
	 * 获取当前选中内容
	 * @return
	 */
	public ISelection getCurrentSelection() {
		return currentSelection;
	}
	
	private LFWAbstractViewPage viewPage;

	public LFWAbstractViewPage getViewPage() {
		return viewPage;
	}
	
	
	protected PaletteRoot getPaletteRoot() {
		return null;
	}
	
	

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirtyTrue() {
		if (!isDirty()) {
			setDirty(true);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
	
	public void setDirtyFalse(){
		setDirty(false);
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
	
	/**
	 * 当前打开的Event编辑器控件
	 */
	private List<EventEditorControl> eventCtrlList = new ArrayList<EventEditorControl>();
	
	public void addEventCtrl(EventEditorControl eventCtrl) {
		eventCtrlList.add(eventCtrl);
	}
	
	public void removeEventCtrl(EventEditorControl eventCtrl) {
		eventCtrlList.remove(eventCtrl);
	}

	public List<EventEditorControl> getEventCtrlList() {
		return eventCtrlList;
	}
	
	/**
	 * 事件编辑器工具类
	 */
	private ListenerEditorHandler listenerHandler;

	public ListenerEditorHandler getListenerHandler() {
		return listenerHandler;
	}
	//新版
	private EventEditorHandler eventHandler;
	
	public EventEditorHandler getEventHandler() {
		return eventHandler;
	}
	
	public void setEventHandler(EventEditorHandler eventHandler){
		this.eventHandler = eventHandler;
	}

	public void doSave(IProgressMonitor monitor) {
		setFocus();
		this.monitor = monitor;
		//TODO 其他保存操作
		// 保存Event代码
		saveEventScript();
	}
	
	/**
	 * 保存Event代码
	 */
	public void saveEventScript() {
		for (int i = 0, n = eventCtrlList.size(); i < n; i++) {
			EventEditorControl eventCtrl = eventCtrlList.get(i);
			
			if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
				//新版
				eventHandler.saveEventScript((EventEditorControlComp)eventCtrl);
			}else{
				listenerHandler.saveEventScript(eventCtrl);
			}
		}
		setDirty(false);
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
	
	private CTabFolder tabFolder;

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}
	
	public void createPartControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new FillLayout());

		tabFolder = new CTabFolder(comp, SWT.BOTTOM);

		CTabItem item1 = new CTabItem(tabFolder, SWT.NONE);
		item1.setText("编辑器");
		
		super.createPartControl(tabFolder);
		item1.setControl(getSplitter());
		
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			//新版
			eventHandler = new EventEditorHandler();
		}else{
			listenerHandler = new ListenerEditorHandler();
		}

		tabFolder.setSelection(0);
		
	}
	
	public void createPartControl(Composite parent, boolean useSuper) {
		if (useSuper)
			super.createPartControl(tabFolder);
		else
			this.createPartControl(parent);
		
	}
	
	public void refreshTreeItem(WebElement web){
		IEditorInput input = getEditorInput();
		if(input instanceof ElementEditorInput){
			//WebElement webElement = eleEditorInput.getCloneElement();
			Tree tree = LFWPersTool.getTree();
			TreeItem treeItem = tree.getSelection()[0];
			treeItem.setData(web);
		}
	}
	
	protected TreeItem getSelectedTreeItem(String modelRootName){
		TreeItem ti = null;
		IEditorInput input = getEditorInput();
		if (input instanceof LfwBaseEditorInput) {
			LfwBaseEditorInput editorInput = (LfwBaseEditorInput)input;
			//LFW项目浏览器工程树(包含所有工程节点)
			Tree tree = LFWAMCPersTool.getTree();
			//工程节点集合
			TreeItem[] treeItems = tree.getItems();
			//当前工程
			IProject project = editorInput.getProject();
			//当前工程节点
			LFWProjectTreeItem projectTreeItem = null;
			for (int i = 0; i < treeItems.length; i++) {
				if(treeItems[i] instanceof LFWProjectTreeItem){
					LFWProjectTreeItem projTreeItem = (LFWProjectTreeItem) treeItems[i];
					if(projTreeItem.getProjectModel().getJavaProject().equals(project)){
						projectTreeItem = projTreeItem;
						break;
					}
				}
			}
			//模块目录节点
			LFWDirtoryTreeItem dirTreeItem = null;
			//如果是组件项目,首先查找组件
			if(LFWPersTool.isBCPProject(project)){
				//当前组件节点
				TreeItem currCompTreeItem = editorInput.getBusiCompTreeItem();
				if(currCompTreeItem != null){
					//组件项目，组件节点
					LFWBusinessCompnentTreeItem busiCompTreeItem = null;
					//当前工程组件节点集合
					TreeItem[] compTreeItems = projectTreeItem.getItems();
					for (int i = 0; i < compTreeItems.length; i++) {
						if(compTreeItems[i].getText().equals(currCompTreeItem.getText())){
							busiCompTreeItem = (LFWBusinessCompnentTreeItem)compTreeItems[i];
							break;
						}
					}
					if(busiCompTreeItem != null){//模块查找目录
						//组件节点下一级子节点集合
						TreeItem[] nodeTreeItems = busiCompTreeItem.getItems();
						for (int i = 0; i < nodeTreeItems.length; i++) {
							if(nodeTreeItems[i].getText().equals(modelRootName)){
								//模块目录节点
								dirTreeItem = (LFWDirtoryTreeItem)nodeTreeItems[i];
								break;
							}
						}
					}
				}
			}
			if(projectTreeItem == null){
				throw new LfwRuntimeException("当前工程节点为空!");
			}
			if(dirTreeItem == null){//模块查找目录
				//工程节点下一级子节点集合
				TreeItem[] nodeTreeItems = projectTreeItem.getItems();
				for (int i = 0; i < nodeTreeItems.length; i++) {
					if(nodeTreeItems[i].getText().equals(modelRootName)){
						//模块目录节点
						dirTreeItem = (LFWDirtoryTreeItem) nodeTreeItems[i];
						break;
					}
				}
			}
			if(dirTreeItem == null){
				throw new LfwRuntimeException("模块目录节点为空!");
			}
			ti = getSelectedTreeItem(dirTreeItem, editorInput);
		}
		return ti;
	}
	
	protected TreeItem getSelectedTreeItem(LFWDirtoryTreeItem dirTreeItem,
			LfwBaseEditorInput editorInput) {
		TreeItem ti = null;
		if(editorInput instanceof ElementEditorInput){
			LfwWidget widget = ((ElementEditorInput)editorInput).getWidget();
			if(widget != null){
				if(widget.getRefId().startsWith("..") || widget.getExtendAttribute(LfwWidget.POOLWIDGET) != null){//public view
					//public view节点集合
					TreeItem[] treeItems = dirTreeItem.getItems();
					if(treeItems != null && treeItems.length > 0){
						for(TreeItem item : treeItems){
							if(item instanceof LFWWidgetTreeItem){
								if(widget.getId().equals(((LFWWidgetTreeItem)item).getWidget().getId())){
									ti = item;
									break;
								}
							}
						}
					}
				}else{
					PageMeta pm = widget.getPagemeta();
					//Window节点集合
					ti = getSelectedTreeItem(pm.getId(), widget.getId(), dirTreeItem);
				}
			}
		}
		return ti;
	}
	
	private TreeItem getSelectedTreeItem(String pageMetaId, String id, TreeItem parent){
		if(pageMetaId != null && id != null && parent != null){
			TreeItem[] children = parent.getItems();
			if(children != null && children.length > 0){
				for(TreeItem child : children){
					if(child instanceof LFWPageMetaTreeItem){
						if(pageMetaId.equals(((LFWPageMetaTreeItem) child).getId())){
							TreeItem[] views = child.getItems();
							if(views != null && views.length > 0){
								for(TreeItem view : views){
									if(view instanceof LFWWidgetTreeItem){
										if(id.equals(((LFWWidgetTreeItem) view).getId())){
											return view;
										}
									}
								}
							}
						}
					}else if(child instanceof LFWVirtualDirTreeItem){
						TreeItem item = getSelectedTreeItem(pageMetaId, id, child);
						if(item != null){
							return item;
						}
					}
				}
			}
		}
		return null;
	}
	
	public void setFocus() {
		super.setFocus();
		IEditorInput input = getEditorInput();
		if(input instanceof LfwBaseEditorInput){
			LfwBaseEditorInput  editorInput = (LfwBaseEditorInput)input;
			TreeItem ti = editorInput.getCurrentTreeItem();
			if(ti != null && !ti.isDisposed()){
				LFWPersTool.getTree().setSelection(ti);
			}
		}
		if (input instanceof ElementEditorInput) {
			ElementEditorInput editorInput = (ElementEditorInput)input;
			try{
				TreeItem selectedTI = editorInput.getCurrentTreeItem();
				if(selectedTI == null || selectedTI.isDisposed()){
					TreeItem widgetTI = null;
					if(editorInput.getWidget().getRefId().startsWith("..") || editorInput.getWidget().getExtendAttribute(LfwWidget.POOLWIDGET) != null){
						widgetTI = getSelectedTreeItem(WEBProjConstants.PUBLIC_VIEW);
					}else{
						widgetTI = getSelectedTreeItem(WEBProjConstants.WINDOW);
					}
					setWidgetTreeItem((LFWWidgetTreeItem)widgetTI);
					
					LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(WEBProjConstants.COMPONENTS);
					WebElement element = editorInput.getCloneElement();
					TreeItem[] childTreeItems = lfwSeparaTreeItem.getItems();
					for (int i = 0; i < childTreeItems.length; i++) {
						LFWBasicTreeItem webT = (LFWBasicTreeItem) childTreeItems[i];
						if(webT.getData() instanceof WebComponent){
							WebComponent comp = (WebComponent) webT.getData();
							if(element.getId().equals(comp.getId())){
								selectedTI = webT;
								break;
							}
						}
					}
				}
				if(selectedTI != null && !selectedTI.isDisposed()){
					LFWAMCPersTool.getTree().setSelection(selectedTI);
					editorInput.setCurrentTreeItem(selectedTI);
				}else{
					MessageDialog.openError(null, "提示", "当前编辑器已失效,请关闭并重新打开!");
					return;
				}
			}catch(Exception e){
				MainPlugin.getDefault().logError(e);
			}
		}
		LFWPersTool.showView(LFWTool.ID_LFW_VIEW_SHEET);
		LFWPersTool.showView(IPageLayout.ID_PROP_SHEET);
	}

	/**
	 * 得到控件项
	 * @return
	 */
	public LFWSeparateTreeItem getWebSeparateTreeItem(String type){
		LFWSeparateTreeItem lfwSeparaTreeItem = null;
		LFWWidgetTreeItem widgetTreeItem = getWidgetTreeItem();		
		if(widgetTreeItem != null){
			
			if(LFWTool.NEW_VERSION.equals(widgetTreeItem.getLfwVersion())){
				if(WEBProjConstants.COMPONENTS.equals(type)){
					type = LFWTool.getWEBProjConstantValue("COMPONENTS", LFWTool.NEW_VERSION);
				}else if(WEBProjConstants.DATASET.equals(type)){
					type = LFWTool.getWEBProjConstantValue("DATASET", LFWTool.NEW_VERSION);
				}else if(WEBProjConstants.REFNODE.equals(type)){
					type = LFWTool.getWEBProjConstantValue("REFNODE", LFWTool.NEW_VERSION);
				}else if(WEBProjConstants.COMBODATA.equals(type)){
					type = LFWTool.getWEBProjConstantValue("COMBODATA", LFWTool.NEW_VERSION);
				}
			}
			
			TreeItem[] separasTreeItems = widgetTreeItem.getItems();
			jump:for (int i = 0; i < separasTreeItems.length; i++) {
				TreeItem item = separasTreeItems[i];
				if(item instanceof LFWSeparateTreeItem){
					LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
					if(seitem.getText().equals(type)){
						lfwSeparaTreeItem = seitem;
						break;
					}
					if(LFWTool.NEW_VERSION.equals(widgetTreeItem.getLfwVersion())){
						if(seitem.getText().equals(LFWTool.getWEBProjConstantValue("CONTEXT", LFWTool.NEW_VERSION))){
							TreeItem[] sepTreeItems = seitem.getItems();
							for(int j=0; j<sepTreeItems.length; j++){
								if(sepTreeItems[j] instanceof LFWSeparateTreeItem){
									LFWSeparateTreeItem sepTreeItem = (LFWSeparateTreeItem)sepTreeItems[j];
									if(sepTreeItem.getText().equals(type)){
										lfwSeparaTreeItem = sepTreeItem;
										break jump;
									}
								}
							}
						}
					}
				}
			}
		}
		return lfwSeparaTreeItem;
	}
	
	/**
	 * 绘制Listener图形
	 * @author guoweic
	 * @param listenerMap
	public void addListenerCellsToEidtor(Map listenerMap, DatasetGraph graph) {
		Iterator it = listenerMap.keySet().iterator();
		int count = 1;
		while (it.hasNext()) {
			String id = (String) it.next();
			JsListener listener = (JsListener) listenerMap.get(id);
			String type = listener.getJsClazz();
			// TODO guoweic
			ListenerElementObj jsListenerObj = new ListenerElementObj();
			jsListenerObj.setId(id);
			jsListenerObj.setType(type);
			jsListenerObj.setJsListener(listener);
			// 设置出现位置
			jsListenerObj.setSize(new Dimension(100,100));
			int pointy = (count++) * 150 - 50;
			Point point = new Point(400, pointy);
			jsListenerObj.setLocation(point);
			graph.addJsListener(jsListenerObj);
		}
	}
	 */
	
	private ListenerElementObj jsListenerObj;
	
	// 默认的Listener显示位置
	private int listenerPointX;
	// 默认的Listener显示位置
	private int listenerPointY;
	
	/**
	 * 绘制Listener图形
	 * @author guoweic
	 * @param listenerMap
	 */
	public void addListenerCellToEditor(Map<String, JsListenerConf> listenerMap, LfwBaseGraph graph) {
		jsListenerObj = new ListenerElementObj();
		jsListenerObj.setEditor(this);
		jsListenerObj.setListenerMap(listenerMap);

		// 设置初始大小
		jsListenerObj.setSize(new Dimension(150, 150));
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			jsListenerObj.setSize(new Dimension(0, 0));
		}
		// 设置显示位置
		//setListenerCellPosition(graph);
		repaintListenerPositon();
		graph.addCell(jsListenerObj);
		graph.addJsListener(jsListenerObj);
		
	}
	
	/**
	 * 设置JsListener图标显示位置
	 * @param graph
	 */
	public void setListenerCellPosition(LfwBaseGraph graph) {
		// 设置显示位置
		int pointX = listenerPointX == 0 ? 100 : listenerPointX;
		//int pointY = ((graph.elementsCount + 1) / 2) * 250 + 150;
		int pointY = listenerPointY == 0 ? ((graph.elementsCount + 1) / 2) * 200 + 150 : listenerPointY;
		jsListenerObj.setSize(new Dimension(150, 150));
		Point point = new Point(pointX, pointY);
		jsListenerObj.setLocation(point);
	}
	
	/**
	 * 重画Listener位置
	 */
	@SuppressWarnings("static-access")
	public void repaintListenerPositon(){
		LfwElementObjWithGraph topElement = getTopElement();
		LfwElementObjWithGraph leftElement = getLeftElement();
		int paddingTop = getPaddingTop();
		int paddingLeft = getPaddingLeft();
		int pointX = 0;
		int pointY = 0;
		LFWElementPart part = new LFWElementPart(null);
		if(topElement != null){
			LFWBaseRectangleFigure topFigure = (LFWBaseRectangleFigure) part.getFigureByModel(topElement);
			pointY = topFigure.getLocation().y + topFigure.getSize().height + paddingTop;
		}else 
			pointX = paddingTop;
		if(leftElement != null){
			LFWBaseRectangleFigure leftFigure = (LFWBaseRectangleFigure) part.getFigureByModel(leftElement);
			pointX = leftFigure.getLocation().x + leftFigure.getSize().width + paddingLeft;
		}
		else 
			pointX = paddingLeft;
		Point point = new Point(pointX, pointY);
		jsListenerObj.setLocation(point);
	}

	protected abstract LfwElementObjWithGraph getTopElement();
	
	protected abstract LfwElementObjWithGraph getLeftElement();
	
	protected int getPaddingTop(){
		return 80;
	}
	
	protected int getPaddingLeft(){
		return 100;
	}
	
	public void executComand(Command cmd){
		super.getCommandStack().execute(cmd);
	}
	
	/**
	 * 获取当前的LFWBaseEditor
	 * @return
	 */
	public static LFWBaseEditor getActiveEditor() {
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if (page != null) {
			editor = page.getActiveEditor();
		}
		if (editor != null && editor instanceof LFWBaseEditor) {
			return (LFWBaseEditor) editor;
		} else {
			return null;
		}
	}
	
	/**
	 * 保存Listener
	 * @param jsListenerId
	 * @param jsEventHandler
	 * @param jsListener
	 */
	public void saveListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf jsListener) {
		saveJsListener(jsListenerId, jsEventHandler, jsListener);
		jsListenerObj.getFigure().refreshListeners();
	}
	
	/**
	 * 保存jsListener
	 * @param jsListenerId
	 * @param jsEventHandler 若为新建，则此项为null
	 * @param jsListener 若为修改，则此项为null
	 */
	public abstract void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf jsListener);
	
	/**
	 * 保存事件到Listener中
	 * @param jsListener
	 * @param jsEventHandler
	 */
	protected void doSaveListenerEvent(JsListenerConf jsListener, EventHandlerConf jsEventHandler) {
		if ((jsEventHandler.getScript() != null && jsEventHandler.getScript().trim().length() > 0) 
				|| jsEventHandler.isOnserver() 
				|| jsEventHandler.getSubmitRule() != null
				|| jsEventHandler.getExtendParamList().size() > 0)
			jsListener.addEventHandler(jsEventHandler);
		else
			jsListener.removeEventHandler(jsEventHandler.getName());
		List<EventHandlerConf> eventList = jsListener.getJsEventHandlerList();
		for (int i = 0, n = eventList.size(); i < n; i++) {
			if (eventList.get(i).getName().equals(jsEventHandler.getName()))
				eventList.get(i).setScript(jsEventHandler.getScript());
		}
	}
	
	/**
	 * 删除jsListener
	 * @param jsListenerId
	 */
	public abstract void removeJsListener(JsListenerConf jsListenerId);
	
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class cls) {
		if (cls == ILFWViewPage.class) {
			this.viewPage = createViewPage();
			return this.viewPage;
		}
		return super.getAdapter(cls);
	}
	
	/**
	 * 新建并返回当前LFWAbstractViewPage实例
	 * @return
	 */
	public abstract LFWAbstractViewPage createViewPage();
	
	/**
	 * 构造右键菜单
	 * @return
	 */
	protected MenuManager getMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				manager.removeAll();
				if (null != getCurrentSelection()) {
					StructuredSelection ss = (StructuredSelection) getCurrentSelection();
					Object sel = ss.getFirstElement();
					if (sel instanceof AbstractGraphicalEditPart) {
						AbstractGraphicalEditPart lfwEle = (AbstractGraphicalEditPart) sel;
						Object model = lfwEle.getModel();
						if (model instanceof ListenerElementObj) {  // Listener菜单
							ListenerElementObj listenerObj = (ListenerElementObj) model;
							// 增加 新建Listener菜单项
							NewJsListenerAction jsListenerAction = new NewJsListenerAction(listenerObj);
//							// 设置目标对象类型
//							jsListenerAction.setTargetType(JsListenerConstant.TARGET_DATASET);
							// 设置可接受的JsListener类型
							jsListenerAction.setAcceptListeners(getEditorAcceptListeners());
							manager.add(jsListenerAction);
							// 获取当先选中的子项
							Label label = listenerObj.getFigure().getCurrentLabel();
							if (null != label) {
								if (label instanceof JsListenerLabel) {
									JsListenerConf jsListener = (JsListenerConf) ((JsListenerLabel) label).getEditableObj();
									// 增加 编辑选中子项菜单
									//manager.add(new EditJsListenerAction(label, listenerObj, jsListener.getId()));
									// 增加 删除选中子项菜单
									manager.add(new DeleteJsListenerAction(label, listenerObj, jsListener.getId()));
								}
							}
						}
					}
				}
				// 增加编辑器自定义菜单
				editMenuManager(manager);
			}
		});
		return menuManager;
	}
	
	/**
	 * 增加编辑器自定义菜单
	 * @param manager
	 */
	protected abstract void editMenuManager(IMenuManager manager);
	
	/**
	 * 获取编辑器可生成的JsListener类型
	 * @return
	 */
	public abstract List<Class<? extends JsListenerConf>> getEditorAcceptListeners();

	public IProgressMonitor getMonitor() {
		return monitor;
	}

	/**
	 * 得到编辑时临时文件的input
	 * @return
	 */
	public IEditorInput getFileEditorInput() {
		return fileEditorInput;
	}

	/**
	 * 设置编辑时临时文件的input
	 * @param fileEditorInput
	 */
	public void setFileEditorInput(IEditorInput fileEditorInput) {
		this.fileEditorInput = fileEditorInput;
	}

	public ListenerElementObj getJsListenerObj() {
		return jsListenerObj;
	}

	public void setListenerPointX(int listenerPointX) {
		this.listenerPointX = listenerPointX;
	}

	public void setListenerPointY(int listenerPointY) {
		this.listenerPointY = listenerPointY;
	}
	
	public List<JsEventDesc> getAcceptEventDescs(){
		return null;
	}
	
}
