package nc.uap.lfw.label;

import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.comp.LabelComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.factory.ElementEidtPartFactory;
import nc.uap.lfw.palette.PaletteFactory;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.views.LFWViewPage;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
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
 * Label Editor
 * @author zhangxya
 *
 */
public class LabelEditor extends LFWBaseEditor {
	public LabelGraph getGraph() {
		return graph;
	}

	public void setGraph(LabelGraph graph) {
		this.graph = graph;
	}


	private LabelGraph graph = new LabelGraph();
	

	public LabelEditor() {
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
		if(super.isDirty())
			return true;
		return getCommandStack().isDirty();
	}

	
	public void  deleteNode(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		IEditorInput input = getEditorInput();
		LabelEditorInput labelEditorInput = (LabelEditorInput)input;
		LabelComp label = (LabelComp)labelEditorInput.getCloneElement();
		widget = LFWPersTool.getCurrentWidget();
		WebComponent[] webcomps = widget.getViewComponents().getComponents();
		boolean isExits = false;
		for (int i = 0; i < webcomps.length; i++) {
			WebComponent web = webcomps[i];
			if(web instanceof LabelComp){
				if(web.getId().equals(label.getId())){
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
		LabelEditorInput labelEditorInput = (LabelEditorInput)input;
		LabelComp labelComp = (LabelComp) labelEditorInput.getCloneElement();
		TreeItem[] childTreeItems = lfwSeparaTreeItem.getItems();
		for (int i = 0; i < childTreeItems.length; i++) {
			LFWBasicTreeItem webT = (LFWBasicTreeItem) childTreeItems[i];
			if(webT.getData() instanceof LabelComp){
				LabelComp lab = (LabelComp) webT.getData();
				if(labelComp.getId().equals(lab.getId())){
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
		super.doSave(monitor);
		String msg = graph.getCells().get(0).validate();
		if(msg != null){
			String message = "标签输入信息有错误，是否还要保存：\r\n\r\n\r\n"+msg;
			if(!MessageDialog.openConfirm(getSite().getShell(), "提示", message))
				return;
		}
		save();
	}
	
	//保存
	public boolean save(){
		IEditorInput input = getEditorInput();
		LabelEditorInput labelEditorInput = (LabelEditorInput)input;
		LabelComp label = (LabelComp) labelEditorInput.getCloneElement();
		//LFWWebComponentTreeItem buttontreeItem = buttonEditorInput.getLfwWebComponentTreeItem();
		LabelElementObj labelobj = (LabelElementObj) this.graph.getCells().get(0);
		//dsobj.setDs(ds);
		LabelComp labelnew = labelobj.getLabelComp();
		//buttontreeItem.setData(buttonnew);
		//buttonEditorInput.setButton(buttonnew);
		labelnew.setId(label.getId());
		LabelComp clone = (LabelComp)labelnew.clone();
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		//LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(widgetTreeItem != null){
			LfwWidget lfwwidget = widgetTreeItem.getWidget();
			if(lfwwidget != null){
			Map<String, WebComponent> map = lfwwidget.getViewComponents().getComponentsMap();
			boolean flag = false;
			for (Iterator<String> itwd = map.keySet().iterator(); itwd.hasNext();) {
				String buttonId = (String) itwd.next();
				if(map.get(buttonId) instanceof LabelComp){
					LabelComp  newlabel  = (LabelComp)map.get(buttonId);
					if(clone.getId().equals(newlabel.getId())){
						map.put(clone.getId(), clone);
						flag = true;
						break;
					}
				}
			}
			if(!flag)
				map.put(clone.getId(), clone);
			}
			
//			String projectPath = LFWPersTool.getProjectPath();
//			LFWDirtoryTreeItem pagemetaTreeItem = (LFWDirtoryTreeItem)widgeTreeItem.getParentItem();
//			String pagemetaNode = pagemetaTreeItem.getFile().getPath();
//			String filePath = pagemetaNode + "/" + widgeTreeItem.getText().substring(5).trim();
//			String fileName = "widget.wd";
//			DataProviderForDesign dataProvider = new DataProviderForDesign();
//			dataProvider.saveWidgettoXml(filePath, fileName, projectPath, lfwwidget);
			LFWPersTool.saveWidget(lfwwidget);
			getCommandStack().markSaveLocation();
			//更新左边的树节点
			refreshTreeItem(clone);
			return true;
		}
		return false;
	}

	
	private LFWWidgetTreeItem widgeTreeItem = null;
	private LfwWidget widget = null;
	private LabelElementObj labelElement;
	
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		LabelEditorInput labelEditor = (LabelEditorInput)input;
		//LFWWebComponentTreeItem lfwWebComponentTreeItem = buttonEditor.getLfwWebComponentTreeItem();
		widgeTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		widget = widgeTreeItem.getWidget();
		labelElement = new LabelElementObj();
		LabelComp labelcomp = (LabelComp)labelEditor.getCloneElement();
		labelElement.setLabelComp(labelcomp);
		labelElement.setLocation(new Point(100, 100));
		graph.addCell(labelElement);
		// 绘制Listener图形
		Map<String, JsListenerConf> listenerMap = labelcomp.getListenerMap();
		addListenerCellToEditor(listenerMap, graph);
	}

	
	public static LabelEditor getActiveEditor(){
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if(page != null){
			editor = page.getActiveEditor();
		}
		if(editor != null && editor instanceof LabelEditor){
			return (LabelEditor)editor;
		}else {
			return null;
		}
		
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
	
	
	
	public LFWAbstractViewPage createViewPage() {
		// TODO Auto-generated method stub
		return new LFWViewPage();
	}

	
	public void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf listener) {
		if(widget != null){
			IEditorInput input = getEditorInput();
			LabelEditorInput labelEditorInput = (LabelEditorInput)input;
			LabelComp label =  (LabelComp) labelEditorInput.getCloneElement();
			Map<String, WebComponent> compMap = widget.getViewComponents().getComponentsMap();
			LabelComp addListenerlabel = null;
			for (Iterator<String> itwd = compMap.keySet().iterator(); itwd.hasNext();) {
				WebComponent webcomp = compMap.get(itwd.next());
				if(webcomp instanceof LabelComp && webcomp.getId().equals(label.getId())){
					addListenerlabel = (LabelComp) webcomp;
					break;
				}
			}
			if(addListenerlabel == null)
				addListenerlabel = label;
			if (null != jsEventHandler) {
				if (addListenerlabel.getListenerMap().containsKey(jsListenerId)) {
					JsListenerConf jsListener = addListenerlabel.getListenerMap().get(jsListenerId);
					doSaveListenerEvent(jsListener, jsEventHandler);
				}
			} else {
				addListenerlabel.addListener(listener);
			}
			setDirtyTrue();
		}
	}

	
	protected void editMenuManager(IMenuManager manager) {
		// TODO Auto-generated method stub
		
	}

	
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		if (null != labelElement)
			return labelElement.getWebElement().getAcceptListeners();
		return null;
	}

	
	public void removeJsListener(JsListenerConf jsListener) {
		if(widget != null){
			IEditorInput input = getEditorInput();
			LabelEditorInput labelEditorInput = (LabelEditorInput)input;
			LabelComp label = (LabelComp) labelEditorInput.getCloneElement();
			Map<String, WebComponent> compdMap = widget.getViewComponents().getComponentsMap();
			LabelComp  addListenerlabel = null;
			for (Iterator<String> itwd = compdMap.keySet().iterator(); itwd.hasNext();) {
				WebComponent webcomp = compdMap.get(itwd.next());
				if(webcomp instanceof LabelComp && webcomp.getId().equals(label.getId())){
					addListenerlabel = (LabelComp) webcomp;
					break;
				}
			}
			String jsListenerId = jsListener.getId();
			addListenerlabel.removeListener(jsListenerId);
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
		return labelElement.getLabelComp().getAcceptEventDescs();
	}

	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}

	public LabelElementObj getLabelElement() {
		return labelElement;
	}

	public void setLabelElement(LabelElementObj labelElement) {
		this.labelElement = labelElement;
	}
	
}

