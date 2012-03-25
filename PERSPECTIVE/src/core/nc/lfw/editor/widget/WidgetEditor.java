package nc.lfw.editor.widget;

import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.WidgetEditorInput;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.editor.widget.plug.DelPluginDescAction;
import nc.lfw.editor.widget.plug.DelPlugoutDescAction;
import nc.lfw.editor.widget.plug.NewPluginDescAction;
import nc.lfw.editor.widget.plug.NewPlugoutDescAction;
import nc.lfw.editor.widget.plug.PluginDescElementObj;
import nc.lfw.editor.widget.plug.PluginDescElementPart;
import nc.lfw.editor.widget.plug.PlugoutDescElementObj;
import nc.lfw.editor.widget.plug.PlugoutDescElementPart;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PluginDesc;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.editor.view.ViewViewPage;
import nc.uap.lfw.palette.ChildConnection;
import nc.uap.lfw.palette.PaletteFactory;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWVirtualDirTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

/**
 * Widget��݋��
 * @author guoweic
 *
 */
public class WidgetEditor extends LFWBaseEditor {

	private WidgetGraph graph = new WidgetGraph();

	public WidgetGraph getGraph() {
		return graph;
	}

	public void setGraph(WidgetGraph graph) {
		this.graph = graph;
	}
	
//	@Override
//	public void commandStackChanged(EventObject arg0) {
//		firePropertyChange(IEditorPart.PROP_DIRTY);
//		super.commandStackChanged(arg0);
//	}
//
//	public boolean isDirty() {
//		if(super.isDirty())
//			return true;
//		return getCommandStack().isDirty();
//	}
	
	public WidgetEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}
	
	TableViewer tv = null;

	public TableViewer getTv() {
		return tv;
	}

	
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());

	}
	
	/**
	 * ��ȡ��ǰ��WidgetEditor�����������и÷�����
	 * @return
	 */
	public static WidgetEditor getActiveWidgetEditor() {
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if (page != null) {
			editor = page.getActiveEditor();
		}
		if (editor != null && editor instanceof WidgetEditor) {
			return (WidgetEditor) editor;
		} else {
			return null;
		}
	}

	
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	private WidgetElementObj wdElementObj = null;

	protected void setInput(IEditorInput input) {
		super.setInput(input);
		WidgetEditorInput wdEditorInput = (WidgetEditorInput) input;
//		LFWWidgetTreeItem widgetTreeItem = wdEditorInput.getTreeItem();
//		LfwWidget widget = widgetTreeItem.getWidget();
		LfwWidget widget = (LfwWidget) wdEditorInput.getCloneElement();
		
		//������ ��Ϊ������ȡwidget�󣬶�����pluginDesc plugoutDesc����
//		TreeItem ti = LFWAMCPersTool.getCurrentTreeItem();
//		if(ti instanceof LFWBasicTreeItem){
//			LFWBasicTreeItem bTreeItem = (LFWBasicTreeItem)ti;
//			if(LFWTool.NEW_VERSION.equals(bTreeItem.getLfwVersion())){
//				widget = LFWAMCConnector.getView(LFWAMCPersTool.getCurrentFolderPath(), WEBProjConstants.AMC_VIEW_FILENAME);
//			}
//		}
		
		WidgetElementObj wdObj = new WidgetElementObj();

		wdObj.setWidget(widget);
		
		// ��ʾWidget
		int pointX = 250;
		int pointY = 100;
		// ������ʾλ��
		wdObj.setSize(new Dimension(100, 100));
		Point point = new Point(pointX, pointY);
		wdObj.setLocation(point);
		graph.addWidgetCell(wdObj);
		
		graph.setWidget(widget);
		
		this.wdElementObj = wdObj;
		
		// ��ʾListener
		addListenerCellToEditor(widget.getListenerMap(), graph);
		
		//��ʾplugoutDesc��pluginDesc
		if (widget.getPlugoutDescs() != null){
			for (PlugoutDesc p: widget.getPlugoutDescs()){
				PlugoutDescElementObj plugoutObj = new PlugoutDescElementObj();
				plugoutObj.setPlugout(p);
				Point outpoint = new Point();
				int count =wdObj.getPlugoutCells().size();
				outpoint.x = wdObj.getLocation().x + wdObj.getSize().width + 50;
				outpoint.y = wdObj.getLocation().y + count * 40;
				ChildConnection conn = new ChildConnection(wdObj,plugoutObj);
				conn.connect();
				plugoutObj.setConn(conn);
				plugoutObj.setLocation(outpoint);
				plugoutObj.setSize(new Dimension(100, 30));
				plugoutObj.setWidgetObj(wdObj);
				wdObj.addPlugoutCell(plugoutObj);
			}
		}
		if (widget.getPluginDescs() != null){
			for (PluginDesc p: widget.getPluginDescs()){
				PluginDescElementObj pluginObj = new PluginDescElementObj();
				pluginObj.setPlugin(p);
				Point outpoint = new Point();
				int count =wdObj.getPluginCells().size();
				outpoint.x = wdObj.getLocation().x +  -100 - 50;
				outpoint.y = wdObj.getLocation().y + count * 40;
				ChildConnection conn = new ChildConnection(wdObj,pluginObj);
				conn.connect();
				pluginObj.setConn(conn);
				pluginObj.setLocation(outpoint);
				pluginObj.setSize(new Dimension(100, 30));
				pluginObj.setWidgetObj(wdObj);
				wdObj.addPluginCell(pluginObj);
			}
		}
	}
	
	/**
	 * ����Widget���ļ���
	 * @param widget
	 */
	private void saveWidget(LfwWidget widget) {
//		WidgetEditorInput input = (WidgetEditorInput) getEditorInput();
		// ��ȡ��Ŀ·��
		String projectPath = LFWPersTool.getProjectPath();
//		LFWWidgetTreeItem widgetTreeItem = input.getTreeItem();
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		if (null == widgetTreeItem)
			widgetTreeItem = LFWPersTool.getWidgetTreeItemById(widget.getId());
//		String pagemetaNodePath = ((LFWNodeTreeItem)widgetTreeItem.getParentItem()).getFile().getPath();
		String pagemetaNodePath = null;
		if(widgetTreeItem != null){
			pagemetaNodePath = ((LFWBasicTreeItem)widgetTreeItem.getParentItem()).getFile().getPath();
			
		}else{
			pagemetaNodePath = ((LFWBasicTreeItem)LFWPersTool.getCurrentTreeItem()).getFile().getPath();
		}
		// ƴ��widget.wd�ļ�ȫ·��
		String filePath = pagemetaNodePath + "\\" + widget.getId();
		// ����Widget��widget.wd��
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			LFWSaveElementTool.updateView(widget);
		}else{
			NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
		}
		widgetTreeItem.setWidget(widget);
	}

	
	public void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf listener) {
		LfwWidget widget = graph.getWidget();
		if (null != jsEventHandler) {
			if (widget.getListenerMap().containsKey(jsListenerId)) {
				JsListenerConf jsListener = widget.getListenerMap().get(jsListenerId);
				doSaveListenerEvent(jsListener, jsEventHandler);
//				if ((jsEventHandler.getScript() != null && jsEventHandler.getScript().trim().length() > 0) || jsEventHandler.isOnserver() || jsEventHandler.getSubmitRule() != null)
//					jsListener.addEventHandler(jsEventHandler);
//				else
//					jsListener.removeEventHandler(jsEventHandler.getName());
//				List<EventHandlerConf> eventList = jsListener.getJsEventHandlerList();
//				for (int i = 0, n = eventList.size(); i < n; i++) {
//					if (eventList.get(i).getName().equals(jsEventHandler.getName()))
//						eventList.get(i).setScript(jsEventHandler.getScript());
//				}
			}
		} else {
			widget.addListener(listener);
		}
		setDirtyTrue();
	}
	
	
	public void removeJsListener(JsListenerConf jsListener) {
		String jsListenerId = jsListener.getId();
		LfwWidget widget = graph.getWidget();
		if (widget.getListenerMap().containsKey(jsListenerId)) {
			widget.getListenerMap().remove(jsListenerId);
		}
		setDirtyTrue();
	}
	
	public WidgetElementObj getModel() {
		return this.wdElementObj;
	}
	
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getGraphicalViewer().setContents(this.graph);
		getGraphicalViewer().addDropTargetListener(
				new nc.uap.lfw.perspective.editor.DiagramTemplateTransferDropTargetListener(
						getGraphicalViewer()));
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
		getGraphicalViewer().setEditPartFactory(new WidgetEditPartFactory(this));
		getGraphicalViewer().setKeyHandler(getShareKeyHandler());
		getGraphicalViewer().setContextMenu(getMenuManager());
		
	}
	
	/**
	 * ���ӱ༭���Զ���˵�
	 * @param manager
	 */
	protected void editMenuManager(IMenuManager manager) {
		if (null != getCurrentSelection()) {
			StructuredSelection ss = (StructuredSelection) getCurrentSelection();
			Object sel = ss.getFirstElement();
			if (sel instanceof WidgetElementPart) {
				WidgetElementPart lfwEle = (WidgetElementPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof WidgetElementObj) {
					//����plugoutDesc
					WidgetElementObj widgetObj = (WidgetElementObj) model;
					manager.add(new NewPluginDescAction(widgetObj));
					manager.add(new NewPlugoutDescAction(widgetObj));
					
					
//					WidgetElementObj widgetObj = (WidgetElementObj) model;
//					// ���� �½�Signal�˵���
//					manager.add(new NewSignalAction(widgetObj));
//					// ���� �½�Slot�˵���
//					manager.add(new NewSlotAction(widgetObj));
//					// ��ȡ����ѡ�е�����
//					Label label = widgetObj.getFigure().getCurrentLabel();
//					if (null != label) {
//						if (label instanceof SignalLabel) {
//							LfwSignal signal = (LfwSignal) ((SignalLabel) label).getEditableObj();
//							// ���� �༭ѡ������˵�
//							manager.add(new EditWidgetItemAction(label, widgetObj, signal.getId()));
//							// ���� ɾ��ѡ������˵�
////							manager.add(new DeleteWidgetItemAction(label, widgetObj, signal.getId()));
//						} else if (label instanceof SlotLabel) {
//							LfwSlot slot = (LfwSlot) ((SlotLabel) label).getEditableObj();
//							// ���� �༭ѡ������˵�
//							manager.add(new EditWidgetItemAction(label, widgetObj, slot.getId()));
//							// ���� ɾ��ѡ������˵�
////							manager.add(new DeleteWidgetItemAction(label, widgetObj, slot.getId()));
//						}
//					}
				}
			}
			else if (sel instanceof PluginDescElementPart) {
				PluginDescElementPart lfwEle = (PluginDescElementPart) sel;
				Object model = lfwEle.getModel();
				PluginDescElementObj pluginObj = (PluginDescElementObj) model;
				manager.add(new DelPluginDescAction(pluginObj));
			}
			else if (sel instanceof PlugoutDescElementPart) {
				PlugoutDescElementPart lfwEle = (PlugoutDescElementPart) sel;
				Object model = lfwEle.getModel();
				PlugoutDescElementObj plugoutObj = (PlugoutDescElementObj) model;
				manager.add(new DelPlugoutDescAction(plugoutObj));
			}
			else {
				return;
			}
		} else {
			return;
		}
	}

	private PaletteRoot paleteRoot = null;

	protected PaletteRoot getPaletteRoot() {
		if (paleteRoot == null) {
			paleteRoot = PaletteFactory.createWidgetPalette();
		}
		return paleteRoot;
	}
	
	
	public void doSave(IProgressMonitor monitor) {
		// ����Event����
		super.doSave(monitor);
//		saveEventScript();
		String msg = graph.getCells().get(0).validate();
		if(msg != null){
			String message = "Ƭ��������Ϣ�д����Ƿ�Ҫ���棺\r\n\r\n\r\n"+msg;
			if(!MessageDialog.openConfirm(getSite().getShell(), "��ʾ", message))
				return;
		}
		LfwWidget widget = graph.getWidget();
		saveWidget(widget);
	}

	
	public LFWAbstractViewPage createViewPage() {
		TreeItem treeItem = LFWAMCPersTool.getCurrentTreeItem();
		if(treeItem instanceof LFWWidgetTreeItem){
			LFWWidgetTreeItem widgetTreeItem = (LFWWidgetTreeItem)treeItem;
			if(LFWTool.OLD_VERSION.equals(widgetTreeItem.getLfwVersion())){
				return new WidgetViewPage();
			}else{
				return new ViewViewPage();
			}
		}else{
			return new WidgetViewPage();
		}
	}

	
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		if (null != wdElementObj)
			return wdElementObj.getWebElement().getAcceptListeners();
		return null;
	}

	public String getPath() {
		return null;
	}

	protected LfwElementObjWithGraph getLeftElement() {
		return null;
	}

	protected LfwElementObjWithGraph getTopElement() {
		return graph.getWidgetCells().get(0);
	}
	
	@Override
	public List<JsEventDesc> getAcceptEventDescs() {
		return graph.getWidget().getAcceptEventDescs();
	}

	@Override
	public void setFocus() {
		super.setFocus();
		IEditorInput input = getEditorInput();
		if (input instanceof WidgetEditorInput) {
			WidgetEditorInput editorInput = (WidgetEditorInput)input;
			try{
				TreeItem selectedTI = editorInput.getCurrentTreeItem();
				if(selectedTI == null || selectedTI.isDisposed()){
					if(editorInput.getWidget().getRefId().startsWith("..") || editorInput.getWidget().getExtendAttribute(LfwWidget.POOLWIDGET) != null){
						selectedTI = getSelectedTreeItem(WEBProjConstants.PUBLIC_VIEW);
					}else{
						selectedTI = getSelectedTreeItem(WEBProjConstants.WINDOW);
					}
				}
				if(selectedTI != null && !selectedTI.isDisposed()){
					LFWAMCPersTool.getTree().setSelection(selectedTI);
					editorInput.setCurrentTreeItem(selectedTI);
				}else{
					MessageDialog.openError(null, "��ʾ", "��ǰ�༭����ʧЧ,��رղ����´�!");
				}
			}catch(Exception e){
				MainPlugin.getDefault().logError(e);
			}
		}
	}
	
	@Override
	protected TreeItem getSelectedTreeItem(LFWDirtoryTreeItem dirTreeItem,
			LfwBaseEditorInput editorInput) {
		TreeItem ti = null;
		if(editorInput instanceof WidgetEditorInput){
			LfwWidget widget = ((WidgetEditorInput)editorInput).getWidget();
			if(widget != null){
				if(widget.getRefId().startsWith("..") || widget.getExtendAttribute(LfwWidget.POOLWIDGET) != null){//public view
					//public view�ڵ㼯��
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
					//Window�ڵ㼯��
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
	
}
