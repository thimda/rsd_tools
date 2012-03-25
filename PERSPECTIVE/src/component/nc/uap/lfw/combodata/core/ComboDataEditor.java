package nc.uap.lfw.combodata.core;

import java.util.EventObject;
import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.combodata.ComboDataElementObj;
import nc.uap.lfw.combodata.ComboDataGraph;
import nc.uap.lfw.combodata.ComboDataViewPage;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.combodata.DynamicComboDataConf;
import nc.uap.lfw.core.combodata.StaticComboData;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.factory.ElementEidtPartFactory;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWComboTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
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


/**
 * 下拉数据编辑器
 * @author zhangxya
 *
 */
public class ComboDataEditor  extends LFWBaseEditor {
	private ComboDataGraph graph = new ComboDataGraph();
	public ComboDataEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}
	public boolean isDirty() {
		if(super.isDirty())
			return true;
		return getCommandStack().isDirty();
	}

	public void setFocus() {
		super.setFocus();
		Tree tree = LFWPersTool.getTree();
		LFWSeparateTreeItem lfwSeparaTreeItem = null;
		IEditorInput input = getEditorInput();
		ComboDataEidtorInput comboEditorInput = (ComboDataEidtorInput)input;
		ComboData comboComp = (ComboData) comboEditorInput.getCloneElement();
		
		lfwSeparaTreeItem = getWebSeparateTreeItem(WEBProjConstants.COMBODATA);
//		LFWWidgetTreeItem widgetTreeItem = getWidgetTreeItem();
//		TreeItem[] separasTreeItems = widgetTreeItem.getItems();
//		for (int i = 0; i < separasTreeItems.length; i++) {
//			TreeItem item = separasTreeItems[i];
//			if(item instanceof LFWSeparateTreeItem){
//				LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
//				if(seitem.getText().equals(WEBProjConstants.COMBODATA)){
//					lfwSeparaTreeItem = seitem;
//					break;
//				}
//				
//			}
//		}
		TreeItem[] childTreeItems = lfwSeparaTreeItem.getItems();
		for (int i = 0; i < childTreeItems.length; i++) {
			LFWComboTreeItem webT = (LFWComboTreeItem) childTreeItems[i];
			if(webT.getData() instanceof ComboData){
				ComboData gr = (ComboData) webT.getData();
				if(comboComp.getId().equals(gr.getId())){
					tree.setSelection(webT);
					break;
				}
			}
		}

	}
	
	
	public void refreshTreeItemText(ComboData combo){
		TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
		if(treeItem instanceof LFWComboTreeItem){
			LFWComboTreeItem comboTreeItem = (LFWComboTreeItem) treeItem;
			comboTreeItem.setText(WEBProjConstants.COMBODATA + combo.getId()+ "[" + combo.getCaption() + "]");
		}
	}
	
	public void  deleteNode(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		IEditorInput input = getEditorInput();
		ComboDataEidtorInput comboEditor = (ComboDataEidtorInput)input;
		ComboData combo = (ComboData)comboEditor.getCloneElement();
		String id = null;
		if(combo instanceof StaticComboData){
			StaticComboData staticCombo = (StaticComboData) combo;
			id = staticCombo.getId();
		}else if(combo instanceof DynamicComboDataConf){
			DynamicComboDataConf dynaCombo = (DynamicComboDataConf) combo;
			id = dynaCombo.getId();
		}
		ComboData comboData = widget.getViewModels().getComboData(id);	
		if(comboData == null){
			 try {
				view.deleteNewNode();
			} catch (Exception e) {
				MainPlugin.getDefault().logError(e.getMessage(), e);
			}
		 }
	 }
	
	@Override
	public void commandStackChanged(EventObject arg0) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(arg0);
	}
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		//TODO 其他保存操作
		String msg = graph.getCells().get(0).validate();
		if(msg != null){
			String message = "下拉框输入信息有错误，是否还要保存：\r\n\r\n\r\n"+msg;
			if(!MessageDialog.openConfirm(getSite().getShell(), "提示", message))
				return;
		}
		save();
	}
	

	//保存
	public boolean save(){
		IEditorInput input = getEditorInput();
		ComboDataEidtorInput comboEditorInput = (ComboDataEidtorInput)input;
		ComboData combo = (ComboData) comboEditorInput.getCloneElement();
		ComboDataElementObj comboElement = (ComboDataElementObj) this.graph.getCells().get(0);
		ComboData combonew = comboElement.getCombodata();
		combonew.setId(combo.getId());
		comboElement.setCombodata(combonew);
		if(widget != null){
			ComboData comDataOld = widget.getViewModels().getComboData(comboElement.getOrginalId());
			//原来没有此combo
			if(comDataOld != null)
				widget.getViewModels().removeComboData(comboElement.getOrginalId());
			widget.getViewModels().addComboData(combonew);
			//重新设置原来的id
			comboElement.setOrginalId(combonew.getId());
//			String projectPath = LFWPersTool.getProjectPath();
//			LFWDirtoryTreeItem pagemetaTreeItem = (LFWDirtoryTreeItem)widgeTreeItem.getParentItem();
//			String pagemetaNode = pagemetaTreeItem.getFile().getPath();
//			String filePath = pagemetaNode + "/" + widgeTreeItem.getText().substring(5).trim();
//			String fileName = "widget.wd";
//			DataProviderForDesign dataProvider = new DataProviderForDesign();
//			dataProvider.saveWidgettoXml(filePath, fileName, projectPath, widget);
			LFWPersTool.saveWidget(widget);
			getCommandStack().markSaveLocation();
			//更新左边的树节点
			refreshTreeItem(combonew);
			return true;
		}
		return false;
	}

	
	private LFWWidgetTreeItem widgeTreeItem = null;
	private LfwWidget widget = null;
	private ComboDataElementObj comboElement;
	
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		ComboDataEidtorInput comboEditor = (ComboDataEidtorInput)input;
		widgeTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		widget = widgeTreeItem.getWidget();
		ComboData combodata = (ComboData)comboEditor.getCloneElement();
		comboElement = new ComboDataElementObj(combodata.getId());
		comboElement.setCombodata(combodata);
		comboElement.setLocation(new Point(100, 100));
		graph.addCell(comboElement);
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
	
	public static ComboDataEditor getActiveEditor(){
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if(page != null){
			editor = page.getActiveEditor();

		}
		if(editor != null && editor instanceof ComboDataEditor){
			return (ComboDataEditor)editor;
		}else {
			return null;
		}
		
	}
	
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScalableFreeformRootEditPart rootEditpart = new ScalableFreeformRootEditPart();
		getGraphicalViewer().setRootEditPart(rootEditpart);
		getGraphicalViewer().setEditPartFactory(new ElementEidtPartFactory(this));
		getGraphicalViewer().setKeyHandler(getShareKeyHandler());
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
	
	@Override
	public LFWAbstractViewPage createViewPage() {
		// TODO Auto-generated method stub
		return new ComboDataViewPage();
	}

	@Override
	public void saveJsListener(String jsListenerId,
			EventHandlerConf jsEventHandler, JsListenerConf jsListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void editMenuManager(IMenuManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeJsListener(JsListenerConf jsListenerId) {
		// TODO Auto-generated method stub
		
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
	
}

