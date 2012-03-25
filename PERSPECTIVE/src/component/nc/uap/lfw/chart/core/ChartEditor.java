package nc.uap.lfw.chart.core;

import java.util.EventObject;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.chart.model.Bar2DChartModelEleObj;
import nc.uap.lfw.chart.model.Bar3DChartModelEleObj;
import nc.uap.lfw.chart.model.BaseChartModelEleObj;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.Bar2DChartModel;
import nc.uap.lfw.core.comp.Bar3DChartModel;
import nc.uap.lfw.core.comp.BaseChartModel;
import nc.uap.lfw.core.comp.ChartComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.palette.PaletteFactory;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.views.LFWViewPage;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

public class ChartEditor  extends LFWBaseEditor {
	private PaletteRoot paleteRoot = null;
	private ChartGraph graph = new ChartGraph();
	private LFWWidgetTreeItem widgeTreeItem = null;
	private LfwWidget widget = null;
	private ChartCompEleObj chartElement;
	private KeyHandler shareKeyHandler = null;

	
	public ChartGraph getGraph() {
		return graph;
	}

	public void setGraph(ChartGraph graph) {
		this.graph = graph;
	}

	public ChartEditor() {
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
		ChartEditorInput chartEditorInput = (ChartEditorInput)input;
		ChartComp chart = (ChartComp)chartEditorInput.getCloneElement();
		WebComponent[] webcomps = widget.getViewComponents().getComponents();
		boolean isExits = false;
		for (int i = 0; i < webcomps.length; i++) {
			WebComponent web = webcomps[i];
			if(web instanceof ChartComp){
				if(web.getId().equals(chart.getId())){
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
	
	public void setFocus() {
		super.setFocus();
		Tree tree = LFWPersTool.getTree();
		LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(WEBProjConstants.COMPONENTS);
		IEditorInput input = getEditorInput();
		ChartEditorInput chartEditorInput = (ChartEditorInput)input;
		ChartComp chartComp = (ChartComp) chartEditorInput.getCloneElement();
		TreeItem[] childTreeItems = lfwSeparaTreeItem.getItems();
		for (int i = 0; i < childTreeItems.length; i++) {
			LFWBasicTreeItem webT = (LFWBasicTreeItem) childTreeItems[i];
			if(webT.getData() instanceof ChartComp){
				ChartComp gr = (ChartComp) webT.getData();
				if(chartComp.getId().equals(gr.getId())){
					tree.setSelection(webT);
					break;
				}
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
			String message = "图表输入信息有错误，是否还要保存：\r\n\r\n\r\n"+msg;
			if(!MessageDialog.openConfirm(getSite().getShell(), "提示", message))
				return;
		}
		save();
	}
	
	//保存
	public boolean save(){
		IEditorInput input = getEditorInput();
		ChartEditorInput chartEditorInput = (ChartEditorInput)input;
		ChartComp chart =  (ChartComp) chartEditorInput.getCloneElement();
		ChartCompEleObj chartobj = (ChartCompEleObj) this.graph.getCells().get(0);
		ChartComp chartnew = chartobj.getChartComp();
		chartnew.setId(chart.getId());
		ChartComp clone = (ChartComp)chartnew.clone();
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		if(widgetTreeItem != null){
			LfwWidget lfwwidget = widgetTreeItem.getWidget();;
			if(lfwwidget != null){
				Map<String, WebComponent> map = lfwwidget.getViewComponents().getComponentsMap();
				map.put(clone.getId(),clone);
				LFWPersTool.saveWidget(lfwwidget);
				getCommandStack().markSaveLocation();
				//更新左边的树节点
				refreshTreeItem(clone);
				return true;
			}
		}
		return false;
	}

	
	
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		ChartEditorInput chartEditorInput = (ChartEditorInput)input;
		widgeTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		widget = widgeTreeItem.getWidget();
		chartElement = new ChartCompEleObj();
		ChartComp chartcomp = (ChartComp) chartEditorInput.getCloneElement();
		chartElement.setChartComp(chartcomp);
		chartElement.setLocation(new Point(100, 100));
		graph.addCell(chartElement);
		
		BaseChartModel chartModel = null;//chartcomp.getCharModel();
		if(chartModel != null){
			String datasetId = chartModel.getDataset();
			Dataset ds = widget.getViewModels().getDataset(datasetId);
			BaseChartModelEleObj basetChartObj = null;
			if(chartModel instanceof Bar2DChartModel)
				basetChartObj = new Bar2DChartModelEleObj();
			else if(chartModel instanceof Bar3DChartModel)
				basetChartObj = new Bar3DChartModelEleObj();
			else
				basetChartObj = new BaseChartModelEleObj();
			basetChartObj.setLocation(new Point(350,100));
			basetChartObj.setSize(new Dimension(100,100));
			basetChartObj.setDs(ds);
			basetChartObj.setId(chartModel.getId());
			graph.addCell(basetChartObj);
		}
	}

	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return true;
	}
	
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
		getGraphicalViewer().setEditPartFactory(new ChartEditPartFactory(this));
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
	
	protected PaletteRoot getPaletteRoot() {
		if(paleteRoot == null){
			paleteRoot = ChartPaletteFactory.createChartPalette();
		}
		return paleteRoot;
	}

	
	public LFWAbstractViewPage createViewPage() {
		// TODO Auto-generated method stub
		return new LFWViewPage();
	}

	
	public void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf listener) {
	
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
	public List<JsEventDesc> getAcceptEventDescs() {
		return chartElement.getChartComp().getAcceptEventDescs();
	}

	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}

	public ChartCompEleObj getChartElement() {
		return chartElement;
	}

	public void setChartElement(ChartCompEleObj chartElement) {
		this.chartElement = chartElement;
	}
	
}

