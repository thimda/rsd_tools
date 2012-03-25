package nc.uap.lfw.excel.core;

import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.contextmenubar.ContextMenuElementObj;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.excel.DatasetToExcelConnection;
import nc.uap.lfw.excel.ExcelElementObj;
import nc.uap.lfw.excel.ExcelGraph;
import nc.uap.lfw.factory.ElementEidtPartFactory;
import nc.uap.lfw.palette.PaletteFactory;
import nc.uap.lfw.perspective.model.RefDatasetElementObj;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;
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

public class ExcelEditor extends LFWBaseEditor {
	private ExcelGraph graph = new ExcelGraph();

	public ExcelGraph getGraph() {
		return graph;
	}


	public void setGraph(ExcelGraph graph) {
		this.graph = graph;
	}


	public ExcelEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}
	
	
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}
	public boolean isDirty() {
		if (super.isDirty())
			return true;
		return getCommandStack().isDirty();
	}

	
	public void  deleteNode(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		IEditorInput input = getEditorInput();
		ExcelEditorInput ExcelEditorInput = (ExcelEditorInput)input;
		ExcelComp Excel = (ExcelComp)ExcelEditorInput.getCloneElement();
		
		WebComponent[] webcomps = widget.getViewComponents().getComponents();
		boolean isExits = false;
		for (int i = 0; i < webcomps.length; i++) {
			WebComponent web = webcomps[i];
			if(web instanceof ExcelComp){
				if(web.getId().equals(Excel.getId())){
					isExits = true;
					break;
				}
			}
		}
		if(!isExits){
			 try {
				view.deleteNewNode();
			} catch (Exception e) {
				MainPlugin.getDefault().logError(e.getMessage(), e);
			}
		 }
	}
	
	
	public void commandStackChanged(EventObject arg0) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(arg0);
	}
	public void doSave(IProgressMonitor monitor) {
		// 保存Event代码
		super.doSave(monitor);
		String msg = graph.getCells().get(0).validate();
		if(msg != null){
			String message = "表格输入信息有错误，是否还要保存：\r\n\r\n\r\n"+msg;
			if(!MessageDialog.openConfirm(getSite().getShell(), "提示", message))
				return;
		}
		//TODO 其他保存操作
		save();
	}
	
	/**
	 * 聚焦编辑器
	 */
	public void setFocus() {
		super.setFocus();
		Tree tree = LFWPersTool.getTree();
		LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(WEBProjConstants.COMPONENTS);
		IEditorInput input = getEditorInput();
		ExcelEditorInput ExcelEditorInput = (ExcelEditorInput)input;
		ExcelComp ExcelComp = (ExcelComp) ExcelEditorInput.getCloneElement();
		TreeItem[] childTreeItems = lfwSeparaTreeItem.getItems();
		for (int i = 0; i < childTreeItems.length; i++) {
			LFWBasicTreeItem webT = (LFWBasicTreeItem) childTreeItems[i];
			if(webT.getData() instanceof ExcelComp){
				ExcelComp gr = (ExcelComp) webT.getData();
				if(ExcelComp.getId().equals(gr.getId())){
					tree.setSelection(webT);
					break;
				}
			}
		}

	}
	
	/**
	 * 更新左边树的显示信息
	 * @param Excel
	 */
	public void refreshTreeItemText(ExcelComp Excel){
		TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
		if(treeItem instanceof LFWWebComponentTreeItem){
			LFWWebComponentTreeItem ExcelTreeItem = (LFWWebComponentTreeItem) treeItem;
			ExcelTreeItem.setText(WEBProjConstants.COMPONENT_EXCEL + Excel.getId()+ "[" + Excel.getCaption() + "]");
		}
	}
	
	//保存
	public boolean save(){
		IEditorInput input = getEditorInput();
		ExcelEditorInput ExcelEditorInput = (ExcelEditorInput)input;
		ExcelComp Excel = (ExcelComp) ExcelEditorInput.getCloneElement();
		ExcelElementObj Excelobj = (ExcelElementObj) this.graph.getCells().get(0);
		ExcelComp excelnew = Excelobj.getExcelComp();
		excelnew.setId(Excel.getId());
		excelnew.setListenerMap(Excel.getListenerMap());
		if(excelnew.getFrom() != null)
			excelnew.setConfType(WebElement.CONF_REF);
		ContextMenuComp contextMenuComp = null;
		if(graph.getContextMenu() != null && graph.getContextMenu().size() > 0){
			ContextMenuElementObj contextMenuEle = graph.getContextMenu().get(0);
			if(contextMenuEle != null)
				contextMenuComp = contextMenuEle.getMenubar();
			if(contextMenuComp != null)
				excelnew.setContextMenu(contextMenuComp.getId());
		}
		ExcelComp clone = (ExcelComp)excelnew.clone();
		if(widget != null){
			Map<String, WebComponent> Excelmap = widget.getViewComponents().getComponentsMap();
//			boolean flag = false;
//			for (Iterator<String> itwd = Excelmap.keySet().iterator(); itwd.hasNext();) {
//				String ExcelId = (String) itwd.next();
//				if(Excelmap.get(ExcelId) instanceof ExcelComp){
//					ExcelComp  newExcel  = (ExcelComp)Excelmap.get(ExcelId);
//					if(Excel.getId().equals(newExcel.getId())){
//						Excelmap.put(Excel.getId(), Excelnew);
//						flag = true;
//						break;
//					}
//				}
//			}
//			if(!flag)
			Excelmap.put(clone.getId(), clone);
			LFWPersTool.saveWidget(widget);
			getCommandStack().markSaveLocation();
			//更新左边树节点
			refreshTreeItem(clone);
			return true;
		}
		return false;

	}

	
	private LFWWidgetTreeItem widgeTreeItem = null;
	private LfwWidget widget = null;
	private ExcelElementObj excelElement;
	
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		ExcelEditorInput ExcelEditor = (ExcelEditorInput)input;
		widgeTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		widget = widgeTreeItem.getWidget();
		excelElement = new ExcelElementObj();
		ExcelComp ExcelComp = (ExcelComp) ExcelEditor.getCloneElement();
		excelElement.setExcelComp(ExcelComp);
		excelElement.setLocation(new Point(100, 100));
		graph.addCell(excelElement);
		if(graph.getJsListeners() == null || graph.getJsListeners().size() == 0){
			// 绘制Listener图形
			Map<String, JsListenerConf> listenerMap = ExcelComp.getListenerMap();
			addListenerCellToEditor(listenerMap, graph);
		}
		String dsId = ExcelComp.getDataset();
		if(dsId != null){
			RefDatasetElementObj dsobj = null;
			Dataset ds = widget.getViewModels().getDataset(dsId);
			if(ds != null){
				dsobj = new RefDatasetElementObj();
				dsobj.setLocation(new Point(400,100));
				dsobj.setSize(new Dimension(150,150));
				dsobj.setDs(ds);
				graph.addCell(dsobj);
			}
			if(dsobj != null){
				DatasetToExcelConnection conn = new DatasetToExcelConnection(excelElement, dsobj);
				conn.connect();
			}
		}
		//contextMenu
		String contextMenuId = ExcelComp.getContextMenu();
		if(contextMenuId != null && !contextMenuId.equals("")){
			ContextMenuComp contextMenuComp = widget.getViewMenus().getContextMenu(contextMenuId);
			ContextMenuElementObj contextMenuElement = new ContextMenuElementObj();
			contextMenuElement.setMenubar(contextMenuComp);
			contextMenuElement.setLocation(new Point(400, 300));
			contextMenuElement.setSize(new Dimension(150, 150));
			graph.addCell(contextMenuElement);
		}
	}

	
	public static ExcelEditor getActiveEditor(){
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if(page != null){
			editor = page.getActiveEditor();

		}
		if(editor != null && editor instanceof ExcelEditor){
			return (ExcelEditor)editor;
		}else {
			return null;
		}
		
	}
	
	
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return true;
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
		getGraphicalViewer().setEditPartFactory(new ElementEidtPartFactory(this));
		getGraphicalViewer().setKeyHandler(getShareKeyHandler());
		getGraphicalViewer().setContextMenu(getMenuManager());
	}
	
	
	
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
	    getGraphicalViewer().setContents(this.graph);
	    getGraphicalViewer().addDropTargetListener(new nc.uap.lfw.perspective.editor.DiagramTemplateTransferDropTargetListener(getGraphicalViewer()));
        LFWPersTool.showView(IPageLayout.ID_PROP_SHEET);
	}
	
	
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()){
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
			
		};
	}
	
	private PaletteRoot paleteRoot = null;
	protected PaletteRoot getPaletteRoot() {
		if(paleteRoot == null){
			paleteRoot = PaletteFactory.createExcelPalette();
		}
		return paleteRoot;
	}

	
	public ExcelViewPage createViewPage() {
		return new ExcelViewPage();
	}

	
	public void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf listener) {
		if(widget == null)
			widget = LFWPersTool.getCurrentWidgetTreeItem().getWidget();
		if(widget != null){
			IEditorInput input = getEditorInput();
			ExcelEditorInput ExcelEditorInput = (ExcelEditorInput)input;
			ExcelComp Excel =  (ExcelComp) ExcelEditorInput.getCloneElement();
			Map<String, WebComponent> ExcelMap = widget.getViewComponents().getComponentsMap();
			ExcelComp addListenerExcel = null;
			for (Iterator<String> itwd = ExcelMap.keySet().iterator(); itwd.hasNext();) {
				WebComponent webcomp = ExcelMap.get(itwd.next());
				if(webcomp instanceof ExcelComp && webcomp.getId().equals(Excel.getId())){
					addListenerExcel = (ExcelComp) webcomp;
					break;
				}
			}
			if(addListenerExcel == null)
				addListenerExcel = Excel;
			if (null != jsEventHandler) {
				if (addListenerExcel.getListenerMap().containsKey(jsListenerId)) {
					JsListenerConf jsListener = addListenerExcel.getListenerMap().get(jsListenerId);
					doSaveListenerEvent(jsListener, jsEventHandler);
				}
			} else {
				addListenerExcel.addListener(listener);
			}
			setDirtyTrue();
		}
	}

	
	protected void editMenuManager(IMenuManager manager) {
		// TODO Auto-generated method stub
		
	}

	
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		if (null != excelElement)
			return excelElement.getWebElement().getAcceptListeners();
		return null;
	}

	
	public void removeJsListener(JsListenerConf jsListener) {
		if(widget != null){
			IEditorInput input = getEditorInput();
			ExcelEditorInput ExcelEditorInput = (ExcelEditorInput)input;
			ExcelComp Excel = (ExcelComp) ExcelEditorInput.getCloneElement();
			Map<String, WebComponent> compdMap = widget.getViewComponents().getComponentsMap();
			ExcelComp  addListenerExcel = null;
			for (Iterator<String> itwd = compdMap.keySet().iterator(); itwd.hasNext();) {
				WebComponent webcomp = compdMap.get(itwd.next());
				if(webcomp instanceof ExcelComp && webcomp.getId().equals(Excel.getId())){
					addListenerExcel = (ExcelComp) webcomp;
					break;
				}
			}
			String jsListenerId = jsListener.getId();
			if(jsListener.getFrom() != null){
				jsListener.setConfType(WebElement.CONF_DEL);
				//widget.getViewComponents().getComponent(Excel.getId());
				addListenerExcel.addListener(jsListener);
			}else
				addListenerExcel.removeListener(jsListenerId);
			compdMap.put(addListenerExcel.getId(), addListenerExcel);
			save();
		}
		
	}

	@Override
	protected LfwElementObjWithGraph getLeftElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LfwElementObjWithGraph getTopElement() {
		// TODO Auto-generated method stub
		return graph.getCells().get(0);
	}
	
	@Override
	public List<JsEventDesc> getAcceptEventDescs() {
		return excelElement.getExcelComp().getAcceptEventDescs();
	}

	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}

	public ExcelElementObj getExcelElement() {
		return excelElement;
	}

	public void setExcelElement(ExcelElementObj excelElement) {
		this.excelElement = excelElement;
	}
	
}

