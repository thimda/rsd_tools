package nc.lfw.jsp.editor;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.jsp.swt.DWidget;
import nc.lfw.jsp.swt.DesignPanel;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UIWidget;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWProjectTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class GuideEditor extends EditorPart {

	private IEditorSite site;
	private IEditorInput input;
	private boolean isDirty = false;
	private DesignPanel designPanel= null;
	
	public void doSave(IProgressMonitor monitor) {
		setFocus();
		isDirty = false;
		this.firePropertyChange(PROP_DIRTY);
		UIElement element = designPanel.getUiElement();
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		String folderPath = LFWPersTool.getCurrentFolderPath();
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
		String pmPath = null;
		if(pmTreeItem != null)
			pmPath = pmTreeItem.getFile().getAbsolutePath();
		if(widget != null){
			UIMeta uimeta = null;
			if(element instanceof UIWidget)
				uimeta = ((UIWidget)element).getUimeta();
			else
				uimeta = (UIMeta) element;
			NCConnector.saveUIMeta(uimeta, pmPath, folderPath);
		}
		else{
			//补充dialog信息
			fillDialog((UIMeta) element);
			NCConnector.saveUIMeta((UIMeta) element, pmPath, folderPath);
		}
	}

	private void fillDialog(UIMeta meta) {
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		LfwWidget[] widgets = pm.getWidgets();
		for (int i = 0; i < widgets.length; i++) {
			LfwWidget w = widgets[i];
			if(w.isDialog()){
				UIWidget widget = new UIWidget();
				widget.setId(w.getId());
				meta.setDialog(w.getId(), widget);
			}
		}
		
	}

	
	public void doSaveAs() {
		isDirty = false;
	}

	public Object getAdapter(Class type) {
		if (type == org.eclipse.ui.views.properties.IPropertySheetPage.class) {
			PropertySheetPage page = new PropertySheetPage();
			page.setRootEntry(new GuidePropertySheetEntry());
			return page;
		}
		return super.getAdapter(type);
	}
	
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.site = site;
		this.input = input;
	}

	
	public boolean isDirty() {
		return isDirty;
	}
	
	public void setDirty() {
		this.isDirty = true;
		this.firePropertyChange(PROP_DIRTY);
	}

	
	public boolean isSaveAsAllowed() {
		return false;
	}

	public static GuideEditor getActiveEditor(){
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if(page != null){
			editor = page.getActiveEditor();
		}
		if(editor != null && editor instanceof GuideEditor){
			return (GuideEditor)editor;
		}else {
			return null;
		}
		
	}
	
	private LfwWidget widget = null;
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		SashForm form = new SashForm(parent, SWT.HORIZONTAL | SWT.FLAT | SWT.SMOOTH);
		//GuideEditorInput input = (GuideEditorInput) getEditorInput();
		//String folderPath = input.getFolderPath();
		UIMeta uimeta = null;
		//pagemeta = input.getPageMeta();
		widget = LFWPersTool.getCurrentWidget();
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if(pmTreeItem != null)
			pm = pmTreeItem.getPm();
		String folderPath = LFWPersTool.getCurrentFolderPath();
		if(widget != null){
			uimeta = NCConnector.getUIMeta(folderPath, pm, widget.getId());
			UIWidget uiwidget = new UIWidget();
			uiwidget.setUimeta(uimeta);
			uiwidget.setId(widget.getId());
			designPanel = new DWidget(form, SWT.NONE, uiwidget, widget);
			if(pm != null)
				designPanel.setPagemeta(LFWPersTool.getCurrentPageMeta());
		}
		else{
//			LfwWidget widget = null;
			
//			if(pm.getWidget("main") != null){
//				LfwWidget mainwidget = pm.getWidget("main");
//				if(mainwidget.getFrom() != null && mainwidget.getFrom().equals("NC"))
//					widget = mainwidget;
//			}
			uimeta = NCConnector.getUIMeta(folderPath, pm, null);
			if(pm == null)
				return;
			designPanel = new DesignPanel(form, SWT.NONE, uimeta, pm);
		}
		//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViews();
		Canvas viewP = new Canvas(form, SWT.BORDER);
		viewP.setLayout(new FillLayout());
		
		GuidePaletteView view = new GuidePaletteView(widget != null);
		//new Button(form, SWT.NONE).setText("dddd");
		view.createPartControl(viewP);
		form.setWeights(new int[]{500, 100});
	}

	
	public void setFocus() {
		TreeItem currTreeItem = LFWPersTool.getCurrentTreeItem();
		if(currTreeItem != null){
			setTitle(currTreeItem.getText() + "[UI设计]");
		}
		try{
			IEditorInput input = getEditorInput();
			GuideEditorInput guideInput = (GuideEditorInput) input;
			String folderPath = guideInput.getFolderPath();
			String oripmPath = guideInput.getPmPath();
			LFWDirtoryTreeItem nodeTreeItem = null;
			LFWPageMetaTreeItem pmTreeItem = null;
			Tree tree = LFWPersTool.getTree();
			TreeItem[] treeItems = tree.getItems();
			IProject project = LFWPersTool.getCurrentProject();
			LFWProjectTreeItem projectTreeItem = null;
			LFWBusinessCompnentTreeItem busiCompTreeItem = null;
			for (int i = 0; i < treeItems.length; i++) {
				LFWProjectTreeItem projectItem = (LFWProjectTreeItem) treeItems[i];
				if(projectItem.getProjectModel().getJavaProject().equals(project)){
					projectTreeItem = projectItem;
					break;
				}
			}
			
			
			//如果是组件项目,首先查找组件
			if(LFWPersTool.isBCPProject(project)){
				TreeItem currComp = LFWPersTool.getCurrentBusiCompTreeItem();
				TreeItem[] compTreeItems = projectTreeItem.getItems();
				for (int i = 0; i < compTreeItems.length; i++) {
					TreeItem treeItem = compTreeItems[i];
					if(treeItem.getText().equals(currComp.getText())){
						busiCompTreeItem = (LFWBusinessCompnentTreeItem) treeItem;
						break;
					}
				}
				//查找pages目录
				TreeItem[] nodesTreeItems = busiCompTreeItem.getItems();
				for (int i = 0; i < nodesTreeItems.length; i++) {
					TreeItem treeItem = nodesTreeItems[i];
					if(treeItem.getText().equals("Pages")){
						nodeTreeItem = (LFWDirtoryTreeItem) treeItem;
						break;
					}
				}
			}
			
			else{
				//查找pages目录
				TreeItem[] nodesTreeItems = projectTreeItem.getItems();
				for (int i = 0; i < nodesTreeItems.length; i++) {
					TreeItem treeItem = nodesTreeItems[i];
					if(treeItem.getText().equals("Pages")){
						nodeTreeItem = (LFWDirtoryTreeItem) treeItem;
						break;
					}
				}
			}
			
			TreeItem[] pmItems = nodeTreeItem.getItems();
			for (int i = 0; i < pmItems.length; i++) {
				TreeItem treeIetm = pmItems[i];
				pmTreeItem = getPMTreeItem(treeIetm, oripmPath);
				if(pmTreeItem != null)
					break;
			}
			if(null != oripmPath && oripmPath.equals(folderPath)){
				tree.setSelection(pmTreeItem);
				return;
			}
			else{
				if(pmTreeItem != null){
					boolean isNotOpen = false;
					TreeItem[] widgetTreeItems = pmTreeItem.getItems();
					if(widgetTreeItems.length == 0)
						isNotOpen = true;
					for (int i = 0; i < widgetTreeItems.length; i++) {
						if(!(widgetTreeItems[i] instanceof LFWPageMetaTreeItem)){
							isNotOpen = false;
							break;
						}
						isNotOpen = true;
					}
					if(isNotOpen){
						TreeItem[] items = new TreeItem[1];
						tree.setSelection(pmTreeItem);
						LFWExplorerTreeView.getLFWExploerTreeView(null).refreshDirtoryTreeItem();
						widgetTreeItems = pmTreeItem.getItems();
					}
					
					for (int j = 0; j < widgetTreeItems.length; j++) {
						if(widgetTreeItems[j] instanceof LFWWidgetTreeItem){
							LFWWidgetTreeItem widgetTree = (LFWWidgetTreeItem) widgetTreeItems[j];
							String widgetPath = widgetTree.getFile().getAbsolutePath();
							if(folderPath != null && folderPath.equals(widgetPath)){
								tree.setSelection(widgetTree);
								break;
							}
						}
					}
				}
			}
		}catch(Throwable e){
			MainPlugin.getDefault().logError(e.getMessage());
		}
	}

	private LFWPageMetaTreeItem getPMTreeItem(TreeItem treeItem, String folderPath){
		LFWPageMetaTreeItem pmTreeItem = null;
		if(treeItem instanceof LFWPageMetaTreeItem) {
			LFWPageMetaTreeItem dirTreeItem = (LFWPageMetaTreeItem) treeItem;
			String pmPath = dirTreeItem.getFile().getAbsolutePath();
			//PageMeta pageMeta = dirTreeItem.getPm();
			if(folderPath!= null && folderPath.equals(pmPath)){
				return dirTreeItem;
			}
		}
		else{
			TreeItem[] childItems = treeItem.getItems();
			for (int i = 0; i < childItems.length; i++) {
				if(childItems[i] instanceof LFWPageMetaTreeItem){
					pmTreeItem =  getPMTreeItem(childItems[i] , folderPath);
					if(pmTreeItem != null)
						return pmTreeItem;
				}
			}
		}
		return null;
	}
	
	
	public IEditorInput getEditorInput() {
		return input;
	}

	
	public IEditorSite getEditorSite() {
		return site;
	}

	
	public IWorkbenchPartSite getSite() {
		return site;
	}

}
