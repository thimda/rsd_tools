package nc.uap.lfw.perspective.project;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.contextmenubar.ContextMenuEditor;
import nc.lfw.editor.contextmenubar.ContextMenuEditorInput;
import nc.lfw.editor.datasets.core.DatasetsEditor;
import nc.lfw.editor.datasets.core.DatasetsEditorInput;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.MenubarEditorInput;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.application.LFWApplicationTreeItem;
import nc.uap.lfw.button.ButtonEditor;
import nc.uap.lfw.button.ButtonEditorInput;
import nc.uap.lfw.chart.core.ChartEditor;
import nc.uap.lfw.chart.core.ChartEditorInput;
import nc.uap.lfw.combodata.core.ComboDataEditor;
import nc.uap.lfw.combodata.core.ComboDataEidtorInput;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.LFWAMCTreeItem;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.combodata.StaticComboData;
import nc.uap.lfw.core.common.WebConstant;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.comp.ChartComp;
import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.comp.IFrameComp;
import nc.uap.lfw.core.comp.ImageButtonComp;
import nc.uap.lfw.core.comp.ImageComp;
import nc.uap.lfw.core.comp.LabelComp;
import nc.uap.lfw.core.comp.LinkComp;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.ProgressBarComp;
import nc.uap.lfw.core.comp.SelfDefComp;
import nc.uap.lfw.core.comp.ToolBarComp;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.IRefDataset;
import nc.uap.lfw.core.data.RefDataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.ViewMenus;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.page.config.RefNodeConf;
import nc.uap.lfw.core.refnode.BaseRefNode;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.core.refnode.RefNodeRelation;
import nc.uap.lfw.core.refnode.RefNodeRelations;
import nc.uap.lfw.core.refnode.SelfDefRefNode;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.excel.core.ExcelEditor;
import nc.uap.lfw.excel.core.ExcelEditorInput;
import nc.uap.lfw.form.core.FormEditor;
import nc.uap.lfw.form.core.FormEditorInput;
import nc.uap.lfw.grid.core.GridEditor;
import nc.uap.lfw.grid.core.GridEditorInput;
import nc.uap.lfw.iframe.IFrameEditor;
import nc.uap.lfw.iframe.IFrameEditorInput;
import nc.uap.lfw.image.ImageEditor;
import nc.uap.lfw.image.ImageEditorInput;
import nc.uap.lfw.label.LabelEditor;
import nc.uap.lfw.label.LabelEditorInput;
import nc.uap.lfw.linkcomp.LinkCompEditor;
import nc.uap.lfw.linkcomp.LinkCompEditroInput;
import nc.uap.lfw.model.LFWModelTreeItem;
import nc.uap.lfw.perspective.action.RefreshLFWExplorerTreeAction;
import nc.uap.lfw.perspective.editor.DataSetEitorInput;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWComboTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWContextMenuTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDSTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWMenubarCompTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWProjectTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWRefNodeTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWVirtualDirTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;
import nc.uap.lfw.progressbar.ProgressBarEditor;
import nc.uap.lfw.progressbar.ProgressBarEditorInput;
import nc.uap.lfw.refnode.core.RefNodeEditor;
import nc.uap.lfw.refnode.core.RefNodeEditorInput;
import nc.uap.lfw.refnoderel.LFWRefNodeRelTreeItem;
import nc.uap.lfw.refnoderel.RefnoderelEditor;
import nc.uap.lfw.refnoderel.RefnoderelEditorInput;
import nc.uap.lfw.selfdefcomp.core.SelfDefCompEditor;
import nc.uap.lfw.selfdefcomp.core.SelfDefCompEditorInput;
import nc.uap.lfw.textcomp.TextCompEditor;
import nc.uap.lfw.textcomp.TextCompEditorInput;
import nc.uap.lfw.toolbar.ToolBarEditor;
import nc.uap.lfw.toolbar.ToolBarEditorInput;
import nc.uap.lfw.tree.core.TreeEditor;
import nc.uap.lfw.tree.core.TreeEditorInput;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * lfw�������ͼ
 * 
 * @author zhangxya
 * 
 */
public class LFWExplorerTreeView extends ViewPart {
	protected TreeViewer treeView = null;
	protected Map<String, String> editoridMap = new HashMap<String, String>();

	// �̳�LFWExplorerTreeView��viewid
	public static String EXTEND_VIEWID;

	public void createPartControl(Composite parent) {
		ViewForm vf = new ViewForm(parent, SWT.NONE);
		vf.setLayout(new FillLayout());
		ToolBar tb = new ToolBar(vf, SWT.FLAT);

		RefreshLFWExplorerTreeAction refresh = new RefreshLFWExplorerTreeAction();
		ToolBarManager tbm = new ToolBarManager(tb);
		tbm.add(refresh);
		dealOthersToolBar(tbm);
		tbm.update(true);

		treeView = new TreeViewer(vf, SWT.SINGLE | SWT.H_SCROLL | SWT.H_SCROLL);
		Tree tree = treeView.getTree();
		LFWPersTool.setTree(tree);
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				Tree tree = treeView.getTree();
				TreeItem[] tis = tree.getSelection();
				if (tis == null || tis.length == 0)
					return;
				TreeItem ti = tis[0];
				manager.removeAll();
				if (ti instanceof LFWBasicTreeItem) {
					((LFWBasicTreeItem) ti).addMenuListener(manager);
				} else
					othersTreeItemMenuListener(ti, manager);
				/* ��portal������ж�����Menu������չ */
				treeItemMenuListenerExtends(ti, manager);
			}
		});

		Menu menu = menuManager.createContextMenu(tree);
		tree.setMenu(menu);
		initTree();
		tree.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				Tree tree = treeView.getTree();
				TreeItem[] tis = tree.getSelection();
				if (tis == null || tis.length == 0)
					return;
				TreeItem ti = tis[0];
				if (ti instanceof LFWBasicTreeItem) {
					((LFWBasicTreeItem) ti).mouseDoubleClick();
				} else
					otherMouseDoubleClick(ti);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseUp(e);
			}
		});
		vf.setTopRight(tb);
		vf.setContent(treeView.getControl());
	}

	/**
	 * �����������˫���¼�
	 * 
	 * @param ti
	 */
	protected void otherMouseDoubleClick(TreeItem ti) {

	}

	/**
	 * �����������ڵ��Ҽ��˵�
	 * 
	 * @param ti
	 * @param manager
	 */
	protected void othersTreeItemMenuListener(TreeItem ti, IMenuManager manager) {

	}

	/**
	 * ����������չ���ڵ��menuListener
	 * 
	 */
	protected void treeItemMenuListenerExtends(TreeItem ti, IMenuManager manager) {

	}

	/**
	 * ����Explorer������
	 * 
	 * @param tbm
	 */
	protected void dealOthersToolBar(ToolBarManager tbm) {

	}

	/**
	 * ���¹������ݼ�
	 * 
	 * @param tis
	 */
	public void refreshPubTreeItem(TreeItem[] tis) {
		TreeItem ti = tis[0];
		IProject project = LFWPersTool.getCurrentProject();
		LFWDirtoryTreeItem direcTreeItem = (LFWDirtoryTreeItem) ti;
		LFWProjectTreeItem projectTreeItem = (LFWProjectTreeItem) direcTreeItem
				.getParentItem();
		File file = projectTreeItem.getFile();
		// ��������
		if (direcTreeItem.getType().equals(
				LFWDirtoryTreeItem.PARENT_PUB_REF_FOLDER)) {
			LFWExplorerTreeBuilder lfwExplorer = LFWExplorerTreeBuilder
					.getInstance();
			File dsRefNodeFile = new File(file, WEBProjConstants.PUBLIC_REFNODE);
			lfwExplorer.initRefNodeSubTree(direcTreeItem, dsRefNodeFile,
					project.getLocation().toString());
		} else if (direcTreeItem.getType().equals(
				LFWDirtoryTreeItem.PARENT_PUB_DS_FOLDER)) {
			LFWExplorerTreeBuilder lfwExplorer = LFWExplorerTreeBuilder
					.getInstance();
			File dsFile = new File(file, WEBProjConstants.PUBLIC_DATASET);
			lfwExplorer.initDsSubTree(direcTreeItem, dsFile, project
					.getLocation().toString());
		}
	}

	/**
	 * ˢ�½ڵ��ļ���������
	 * 
	 * @param tis
	 */
	public void refreshDirtoryTreeItem() {
		LFWPageMetaTreeItem ti = LFWPersTool.getCurrentPageMetaTreeItem();
		if(ti == null){
			ti = LFWAMCPersTool.getDefaultWindowTreeItem(null);
		}
		TreeItem[] treeItems = ti.getItems();
		for (int i = 0; i < treeItems.length; i++) {
			TreeItem treeItem = treeItems[i];
			treeItem.dispose();
		}
		ti.setPm(null);
		// �����ӽڵ�
		LFWExplorerTreeBuilder.getInstance().initSubNodeTree(ti,
				LFWPersTool.getCurrentProject());
		File file = ti.getFile();

		// ����pageMeta
		PageMeta pagemeta = ti.getPm();
		if (pagemeta == null)
			return;
		ti.setPm(pagemeta);
		WidgetConfig[] widgetlist = pagemeta.getWidgetConfs();
		if (widgetlist.length > 0) {
			String view = "[" + LFWTool.getViewText(ti) + "] ";
			for (int i = 0; i < widgetlist.length; i++) {
				LfwWidget widget = pagemeta.getWidget(widgetlist[i].getId());
				File widgetfile = new File(file.getPath() + "/"
						+ widget.getId());
				LFWWidgetTreeItem widgetTreeItem = new LFWWidgetTreeItem(ti, widgetfile, widget,
						view + widgetfile.getName());
				// TreeItem[] widgetTreeItems = new TreeItem[1];
				// widgetTreeItems[0] = widgetTreeItem;
				widgetTreeItem.setId(widget.getId());
				refreshWidgetTreeItem(widgetTreeItem);
			}
		}
		//�˵���������Ҫ��һ��ʵ��
		if (LFWTool.OLD_VERSION.equals(ti.getLfwVersion())) {
			initMenusManagerItem((LFWDirtoryTreeItem) ti, file, pagemeta);
		}

		// containers
//		ViewContainers viewContainers = pagemeta.getViewConinters();
//		Map<String, WebComponent> viewContainerMap = viewContainers
//				.getContainerMap();
//		if (viewContainerMap.size() > 0) {
//			LFWSeparateTreeItem containerD = createFolder(ti, file,
//					WEBProjConstants.CONTAINER);
//			for (Iterator<String> itcm = viewContainerMap.keySet().iterator(); itcm
//					.hasNext();) {
//				WebComponent webCompnent = viewContainerMap.get(itcm.next());
//				if (webCompnent instanceof TabLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD, LFWWebComponentTreeItem.TAB_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_TAB);
//				} else if (webCompnent instanceof CardLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.CARDLAYOUT_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_CARDLAYOUT);
//				} else if (webCompnent instanceof PanelLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.PANELLAYOUT_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_PANELLAYOUT);
//				} else if (webCompnent instanceof OutlookbarComp) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.OUTLOOKBAR_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_OUTLOOKBAR);
//				}
//			}
//		}
	}
	
	/**
	 * ˢ�½ڵ��ļ���������
	 * 
	 * @param tis
	 */
	public void refreshDirtoryTreeItem(LFWPageMetaTreeItem ti) {
		if(ti == null){
			ti = LFWPersTool.getCurrentPageMetaTreeItem();
		}
		if(ti == null){
			ti = LFWAMCPersTool.getDefaultWindowTreeItem(null);
		}
		TreeItem[] treeItems = ti.getItems();
		for (int i = 0; i < treeItems.length; i++) {
			TreeItem treeItem = treeItems[i];
			treeItem.dispose();
		}
		ti.setPm(null);
		// �����ӽڵ�
		LFWExplorerTreeBuilder.getInstance().initSubNodeTree(ti,
				LFWPersTool.getCurrentProject());
		File file = ti.getFile();

		// ����pageMeta
		PageMeta pagemeta = ti.getPm();
		if (pagemeta == null)
			return;
		ti.setPm(pagemeta);
		WidgetConfig[] widgetlist = pagemeta.getWidgetConfs();
		if (widgetlist.length > 0) {
			String view = "[" + LFWTool.getViewText(ti) + "] ";
			for (int i = 0; i < widgetlist.length; i++) {
				LfwWidget widget = pagemeta.getWidget(widgetlist[i].getId());
				File widgetfile = new File(file.getPath() + "/"
						+ widget.getId());
				LFWWidgetTreeItem widgetTreeItem = null;
				widgetTreeItem = new LFWWidgetTreeItem(ti, widgetfile, widget,
						view + widgetfile.getName());
				// TreeItem[] widgetTreeItems = new TreeItem[1];
				// widgetTreeItems[0] = widgetTreeItem;
				refreshWidgetTreeItem(widgetTreeItem);
			}
		}
		//�˵���������Ҫ��һ��ʵ��
		if (LFWTool.OLD_VERSION.equals(ti.getLfwVersion())) {
			initMenusManagerItem((LFWDirtoryTreeItem) ti, file, pagemeta);
		}

		// containers
//		ViewContainers viewContainers = pagemeta.getViewConinters();
//		Map<String, WebComponent> viewContainerMap = viewContainers
//				.getContainerMap();
//		if (viewContainerMap.size() > 0) {
//			LFWSeparateTreeItem containerD = createFolder(ti, file,
//					WEBProjConstants.CONTAINER);
//			for (Iterator<String> itcm = viewContainerMap.keySet().iterator(); itcm
//					.hasNext();) {
//				WebComponent webCompnent = viewContainerMap.get(itcm.next());
//				if (webCompnent instanceof TabLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD, LFWWebComponentTreeItem.TAB_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_TAB);
//				} else if (webCompnent instanceof CardLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.CARDLAYOUT_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_CARDLAYOUT);
//				} else if (webCompnent instanceof PanelLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.PANELLAYOUT_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_PANELLAYOUT);
//				} else if (webCompnent instanceof OutlookbarComp) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.OUTLOOKBAR_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_OUTLOOKBAR);
//				}
//			}
//		}
	}

	/**
	 * ���ز˵���������
	 * 
	 * @param ti
	 * @param file
	 * @param pagemeta
	 */
	public void initMenusManagerItem(LFWDirtoryTreeItem ti, File file,
			PageMeta pagemeta) {
		// ����Menu��
		ViewMenus viewMenus = pagemeta.getViewMenus();
		LFWSeparateTreeItem menusItem = createFolder(ti, file,
				WEBProjConstants.MENUS_MANAGER_CN);
		// Map<String, MenubarComp> menuBars = viewMenus.getMenuBars();
		MenubarComp[] menuBars = viewMenus.getMenuBars();
		for (int i = 0; i < menuBars.length; i++) {
			MenubarComp menubar = menuBars[i];
			new LFWMenubarCompTreeItem(menusItem, menubar);
		}
	};

	/**
	 * ˢ�²˵��������������������
	 * 
	 * @param treeView
	 * @param projectPath
	 * @param pagemeta
	 * @param id
	 *            �����Ĳ˵���ID
	 * @return ���������Ĳ˵���
	 */
	public LFWMenubarCompTreeItem refreshMenusManagerItem(TreeViewer treeView,
			String projectPath, PageMeta pagemeta, String id) {
		Tree tree = treeView.getTree();
		TreeItem[] tis = tree.getSelection();
		if (tis == null || tis.length == 0)
			return null;
		LFWSeparateTreeItem menusItem = (LFWSeparateTreeItem) tis[0];
		menusItem.removeAll();

		// ����Menu��
		ViewMenus viewMenus = pagemeta.getViewMenus();
		// �����ص������˵���
		LFWMenubarCompTreeItem newItem = null;
		MenubarComp[] menuBars = viewMenus.getMenuBars();
		for (int i = 0; i < menuBars.length; i++) {
			MenubarComp menubar = menuBars[i];
			LFWMenubarCompTreeItem menubarTreeItem = new LFWMenubarCompTreeItem(
					menusItem, menubar);
			// menubarTreeItem.setPagemeta(pagemeta);
			// menubarTreeItem.setProjectPath(projectPath);
			if (null != id && id.equals(menubar.getId())) {
				newItem = menubarTreeItem;
			}
		}
		return newItem;

	}

	public void refreshPubWidgetTreeItem(TreeItem ti) {
		LFWWidgetTreeItem widgetTreeItem = (LFWWidgetTreeItem) ti;
		File file = ((LFWWidgetTreeItem) ti).getFile();
		LfwWidget widget = widgetTreeItem.getWidget();
		detalWidgetTreeItem(ti, file, widget);
	}

	/**
	 * ˢ��Widget������
	 * 
	 * @param tis
	 */
	public void refreshWidgetTreeItem(TreeItem ti) {
		TreeItem treeItem = ti;
		File file = ((LFWWidgetTreeItem) treeItem).getFile();
		String pageIdKey = "";
		while (treeItem.getParentItem() != null
				&& (treeItem instanceof LFWWidgetTreeItem || treeItem instanceof LFWDirtoryTreeItem)) {
			LFWDirtoryTreeItem parent = (LFWDirtoryTreeItem) treeItem
					.getParentItem();
			if ((parent.getText().equals(WEBProjConstants.NODEGROUP_CN)
					|| parent.getText().equals(WEBProjConstants.PUBLIC_REFNODE) || parent
					.getText().equals(WEBProjConstants.WINDOW)))
				break;
			pageIdKey = parent.getFile().getName() + "/" + pageIdKey;
			treeItem = parent;

		}
		// ti = tis[0];
		if (pageIdKey.endsWith("/"))
			pageIdKey = pageIdKey.substring(0, pageIdKey.length() - 1);

		String filePath = LFWPersTool.getProjectPath();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(WebConstant.PROJECT_PATH_KEY, filePath);
		paramMap.put(WebConstant.PAGE_ID_KEY, pageIdKey);
		String widgetId = file.getName();

		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		LfwWidget widget = pm.getWidget(widgetId);
		if(widget == null){
			widget = ((LFWWidgetTreeItem) ti).getWidget();
			pm.addWidget(widget);
		}
		// // ����pageMeta
		// LfwWidget widget = dataProviderForDesign.getWidget(
		// paramMap, widgetId);
		((LFWWidgetTreeItem) ti).setWidget(widget);
		// ����widget
		LFWPersTool.setLfwWidget(widget);
		if(LFWTool.NEW_VERSION.equals(((LFWBasicTreeItem)ti).getLfwVersion()) && widget.getRefId() != null && widget.getRefId().startsWith("..")){
			((LFWWidgetTreeItem) ti).setType(ILFWTreeNode.POOLREFNODEFOLDER);
			return;
		}else if(LFWTool.NEW_VERSION.equals(((LFWBasicTreeItem)ti).getLfwVersion()) && widget.getRefId() == null){
			((LFWWidgetTreeItem) ti).setType(ILFWTreeNode.POOLREFNODEFOLDER);
			return;
		}
		// viewModels
		detalWidgetTreeItem(ti, file, widget);
	}

	/**
	 * ����widgetTreeItem
	 * 
	 * @param widgetTreeItem
	 */
	public void dealCurrentWidgetTreeItem(LFWWidgetTreeItem widgetTreeItem) {
		widgetTreeItem.removeAll();
		// widgetTreeItem.dispose();
		File file = widgetTreeItem.getFile();
		detalWidgetTreeItem(widgetTreeItem, file, widgetTreeItem.getWidget());
	}

	public void detalWidgetTreeItem(TreeItem ti, File file, LfwWidget widget) {
		ViewModels viewModels = widget.getViewModels();
		TreeItem parent = ti;
		if (LFWTool.NEW_VERSION.equals(((LFWBasicTreeItem) ti).getLfwVersion())) {
			LFWSeparateTreeItem contextD = createFolder(ti, file,
					WEBProjConstants.CONTEXT_EN);
			parent = contextD;
		}

		LFWSeparateTreeItem datasetD = createFolder(parent, file, LFWTool
				.getWEBProjConstantValue("DATASET", ((LFWBasicTreeItem) parent)
						.getLfwVersion()));
		// Map<String, Dataset> dsMap = viewModels.getDatasetMap();
		Dataset[] datasets = viewModels.getDatasets();

		// ����widget��ds����������
		LFWPersTool.setRefdatasets(datasets);
		for (int i = 0; i < datasets.length; i++) {
			for (int j = datasets.length - 1; j > i; j--) {
				if (datasets[i] instanceof IRefDataset
						&& !(datasets[j] instanceof IRefDataset)) {
					Dataset temp = datasets[i];
					datasets[i] = datasets[j];
					datasets[j] = temp;
				}
			}
		}

		for (int i = 0; i < datasets.length; i++) {
			new LFWDSTreeItem(datasetD, datasets[i], datasets[i].getId());
		}

		// //refnode
		LFWSeparateTreeItem refnodeD = createFolder(parent, file, LFWTool
				.getWEBProjConstantValue("REFNODE", ((LFWBasicTreeItem) parent)
						.getLfwVersion()));
		IRefNode[] refnodes = viewModels.getRefNodes();

		// ����refnodes
		LFWPersTool.setRefnodes(refnodes);
		for (int i = 0; i < refnodes.length; i++) {
			IRefNode refnode = refnodes[i];
			new LFWRefNodeTreeItem(refnodeD, refnode, LFWTool
					.getWEBProjConstantValue("COMPONENT_REFNODE",
							((LFWBasicTreeItem) ti).getLfwVersion()));
		}

		// refnodeRelation
		RefNodeRelations refnodeRelations = widget.getViewModels()
				.getRefNodeRelations();
		if (refnodeRelations != null) {
			Map<String, RefNodeRelation> refnodeRels = refnodeRelations
					.getRefnodeRelations();
			RefNodeRelation[] rels = refnodeRels.values().toArray(
					new RefNodeRelation[0]);
			for (int i = 0; i < rels.length; i++) {
				RefNodeRelation refnodeRel = rels[i];
				new LFWRefNodeRelTreeItem(refnodeD, refnodeRel, LFWTool
						.getWEBProjConstantValue("COMPONENT_REFNODERELATION",
								((LFWBasicTreeItem) ti).getLfwVersion()));
			}
		}
		// //combodata
		LFWSeparateTreeItem combodateD = createFolder(parent, file, LFWTool
				.getWEBProjConstantValue("COMBODATA", ((LFWBasicTreeItem) ti)
						.getLfwVersion()));
		ComboData[] combodata = viewModels.getComboDatas();
		// ����widget��combodata
		LFWPersTool.setRefcomdatas(combodata);
		for (int i = 0; i < combodata.length; i++) {
			ComboData compdata = combodata[i];
			new LFWComboTreeItem(combodateD, compdata, LFWTool
					.getWEBProjConstantValue("COMPONENT_COMBODATA",
							((LFWBasicTreeItem) ti).getLfwVersion()));
		}

		// containers
//		ViewContainers viewContainers = widget.getViewConinters();
//		Map<String, WebComponent> viewContainerMap = viewContainers
//				.getContainerMap();
//		if (viewContainerMap.size() > 0) {
//			LFWSeparateTreeItem containerD = createFolder(ti, file, LFWTool
//					.getWEBProjConstantValue("CONTAINER",
//							((LFWBasicTreeItem) ti).getLfwVersion()));
//			for (Iterator<String> itcm = viewContainerMap.keySet().iterator(); itcm
//					.hasNext();) {
//				WebComponent webCompnent = viewContainerMap.get(itcm.next());
//				if (webCompnent instanceof TabLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD, LFWWebComponentTreeItem.TAB_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_TAB);
//				} else if (webCompnent instanceof CardLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.CARDLAYOUT_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_CARDLAYOUT);
//				} else if (webCompnent instanceof PanelLayout) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.PANELLAYOUT_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_PANELLAYOUT);
//				} else if (webCompnent instanceof OutlookbarComp) {
//					LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
//							containerD,
//							LFWWebComponentTreeItem.OUTLOOKBAR_NAME,
//							webCompnent);
//					newComp.setType(LFWWebComponentTreeItem.PARAM_OUTLOOKBAR);
//				}
//			}
//		}
		//component���
		LFWSeparateTreeItem componentD = createFolder(ti, file, LFWTool
				.getWEBProjConstantValue("COMPONENTS", ((LFWBasicTreeItem) ti)
						.getLfwVersion()));
		WebComponent[] grids = widget.getViewComponents().getComponentByType(
				GridComp.class);
		for (int i = 0; i < grids.length; i++) {
			geneWebCompTreeItem(componentD, grids[i]);
		}
		WebComponent[] forms = widget.getViewComponents().getComponentByType(
				FormComp.class);
		for (int i = 0; i < forms.length; i++) {
			geneWebCompTreeItem(componentD, forms[i]);
		}
		WebComponent[] buttons = widget.getViewComponents().getComponentByType(
				ButtonComp.class);
		for (int i = 0; i < buttons.length; i++) {
			geneWebCompTreeItem(componentD, buttons[i]);
		}

		WebComponent[] menubarComps = widget.getViewComponents()
				.getComponentByType(MenubarComp.class);
		for (int i = 0; i < menubarComps.length; i++) {
			geneWebCompTreeItem(componentD, menubarComps[i]);
		}
		WebComponent[] trees = widget.getViewComponents().getComponentByType(
				TreeViewComp.class);
		for (int i = 0; i < trees.length; i++) {
			geneWebCompTreeItem(componentD, trees[i]);
		}
		WebComponent[] images = widget.getViewComponents().getComponentByType(
				ImageComp.class);
		for (int i = 0; i < images.length; i++) {
			geneWebCompTreeItem(componentD, images[i]);
		}
		WebComponent[] labels = widget.getViewComponents().getComponentByType(
				LabelComp.class);
		for (int i = 0; i < labels.length; i++) {
			geneWebCompTreeItem(componentD, labels[i]);
		}
		WebComponent[] textcomps = widget.getViewComponents()
				.getComponentByType(TextComp.class);
		for (int i = 0; i < textcomps.length; i++) {
			geneWebCompTreeItem(componentD, textcomps[i]);
		}

		WebComponent[] excelcomps = widget.getViewComponents()
				.getComponentByType(ExcelComp.class);
		for (int i = 0; i < excelcomps.length; i++) {
			geneWebCompTreeItem(componentD, excelcomps[i]);
		}
		// WebComponent[] textareas =
		// widget.getViewComponents().getComponentByType(TextAreaComp.class);
		// for (int i = 0; i < textareas.length; i++) {
		// geneWebCompTreeItem(componentD, textareas[i]);
		// }
		WebComponent[] iframes = widget.getViewComponents().getComponentByType(
				IFrameComp.class);
		for (int i = 0; i < iframes.length; i++) {
			geneWebCompTreeItem(componentD, iframes[i]);
		}
		WebComponent[] toolbars = widget.getViewComponents()
				.getComponentByType(ToolBarComp.class);
		for (int i = 0; i < toolbars.length; i++) {
			geneWebCompTreeItem(componentD, toolbars[i]);
		}
		WebComponent[] links = widget.getViewComponents().getComponentByType(
				LinkComp.class);
		for (int i = 0; i < links.length; i++) {
			geneWebCompTreeItem(componentD, links[i]);
		}

		WebComponent[] progressBars = widget.getViewComponents()
				.getComponentByType(ProgressBarComp.class);
		for (int i = 0; i < progressBars.length; i++) {
			geneWebCompTreeItem(componentD, progressBars[i]);
		}

		WebComponent[] chartcomps = widget.getViewComponents()
				.getComponentByType(ChartComp.class);
		for (int i = 0; i < chartcomps.length; i++) {
			geneWebCompTreeItem(componentD, chartcomps[i]);
		}

		WebComponent[] selfdefComps = widget.getViewComponents()
				.getComponentByType(SelfDefComp.class);
		for (int i = 0; i < selfdefComps.length; i++) {
			geneWebCompTreeItem(componentD, selfdefComps[i]);
		}

		if (LFWTool.OLD_VERSION.equals(((LFWBasicTreeItem) ti).getLfwVersion())) {
			//Menubar
			componentD = createFolder(ti, file, WEBProjConstants.WIDGET_MENUBAR);
		}
		ViewMenus viewMenus = widget.getViewMenus();
		// Map<String, MenubarComp> menuBars = viewMenus.getMenuBars();
		ContextMenuComp[] contextMenuss = viewMenus.getContextMenus();
		for (int i = 0; i < contextMenuss.length; i++) {
			ContextMenuComp contextMenu = contextMenuss[i];
			new LFWContextMenuTreeItem(
					componentD,
					LFWTool.getWEBProjConstantValue("CONTEXT_MENUCOMP_ELEMENT", ((LFWBasicTreeItem) ti).getLfwVersion()),
					contextMenu);
		}
		MenubarComp[] menubars = viewMenus.getMenuBars();
		for (int i = 0; i < menubars.length; i++) {
			MenubarComp menubar = menubars[i];
			new LFWMenubarCompTreeItem(
					componentD, 
					LFWTool.getWEBProjConstantValue("COMPONENT_MENUBAR", ((LFWBasicTreeItem) ti).getLfwVersion()),
					menubar);
		}
	}

	private void geneWebCompTreeItem(LFWSeparateTreeItem componentD,
			WebComponent webCompnent) {
		if (webCompnent instanceof GridComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_GRID", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_GRID);
		} else if (webCompnent instanceof ExcelComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_EXCEL", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_EXCEL);
		} else if (webCompnent instanceof FormComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_FORM", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_FORM);
		} else if (webCompnent instanceof ButtonComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_BUTTON", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_BUTTON);
		} else if (webCompnent instanceof MenubarComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_MENUBAR", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_MENUBAR);
		} else if (webCompnent instanceof TreeViewComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_TREE", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_TREE);
		} else if (webCompnent instanceof ImageComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_IMAGE", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_IMAGE);
		} else if (webCompnent instanceof LabelComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_LABEL", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_LABEL);
		} else if (webCompnent instanceof TextComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_TEXTCOMP", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_TEXTCOMP);
		}
		// else if(webCompnent instanceof TextAreaComp){
		// LFWWebComponentTreeItem newComp = new
		// LFWWebComponentTreeItem(componentD, COMPONENT_TEXTAREA ,webCompnent);
		// newComp.setType(LFWWebComponentTreeItem.PARAM_TEXTAREA);
		// }
		else if (webCompnent instanceof IFrameComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_IFRAMECOMP", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_IFRAME);
		} else if (webCompnent instanceof ToolBarComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_TOOLBAR", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_TOOLBAR);
		} else if (webCompnent instanceof LinkComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_LINKCOMP", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_LINK);
		} else if (webCompnent instanceof ProgressBarComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_PROGRESSBAR", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_PROGRESSBAR);
		} else if (webCompnent instanceof SelfDefComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_SELFDEFCOMP", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_SELFDEF);
		} else if (webCompnent instanceof ChartComp) {
			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(
					componentD, LFWTool.getWEBProjConstantValue("COMPONENT_CHART", componentD.getLfwVersion()), webCompnent);
			newComp.setType(LFWWebComponentTreeItem.PARAM_CHART);
		} else {
			new LFWWebComponentTreeItem(componentD, "", webCompnent);
		}
	}

	/**
	 * ��ds�༭��
	 * 
	 * @param ds
	 */
	public void openDsEdit(LFWDSTreeItem ds) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(ds);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pageMetaTreeItem = LFWPersTool
				.getPagemetaTreeItem(ds);
		PageMeta pagemeta = null;
		if (pageMetaTreeItem != null)
			pagemeta = pageMetaTreeItem.getPm();
		Dataset dsdata = (Dataset) ds.getData();
		DataSetEitorInput datasetEditorInput = new DataSetEitorInput(dsdata,
				lfwwidget, pagemeta);
		ds.setEditorInput(datasetEditorInput);
		String editorid = "nc.uap.lfw.perspective.editor.DataSetEditor";
		if (datasetEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		String editorinputid = null;
		if (pagemeta == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "dataset"
					+ dsdata.getId());
		else
			editorinputid = editoridMap.get(pagemeta.getId()
					+ lfwwidget.getId() + "dataset" + dsdata.getId());
		if (LFWPersTool.isBCPProject(LFWPersTool.getCurrentProject())) {
			LFWBusinessCompnentTreeItem busiComponent = LFWPersTool
					.getBusiCompTreeItem(ds);
			if (pagemeta == null)
				editorinputid = editoridMap.get(busiComponent.getText()
						+ lfwwidget.getId() + "dataset" + dsdata.getId());
			else
				editorinputid = editoridMap.get(busiComponent.getText()
						+ pagemeta.getId() + lfwwidget.getId() + "dataset"
						+ dsdata.getId());
		}
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof DataSetEitorInput) {
					DataSetEitorInput dsEditorInput = (DataSetEitorInput) editorInput;
					Dataset dsnew = (Dataset) dsEditorInput.getCloneElement();
					PageMeta pmnew = dsEditorInput.getPagemeta();
					LfwWidget widgetnew = dsEditorInput.getWidget();
					if (pmnew == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& dsnew.getId().equals(dsdata.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pagemeta.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& dsnew.getId().equals(dsdata.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(datasetEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (LFWPersTool.isBCPProject(LFWPersTool.getCurrentProject())) {
				LFWBusinessCompnentTreeItem busiComponent = LFWPersTool
						.getBusiCompTreeItem(ds);
				if (pagemeta == null)
					editoridMap.put(busiComponent.getText() + lfwwidget.getId()
							+ "dataset" + dsdata.getId(), busiComponent
							.getText()
							+ lfwwidget.getId() + "dataset" + dsdata.getId());

				else
					editoridMap.put(busiComponent.getText() + pagemeta.getId()
							+ lfwwidget.getId() + "dataset" + dsdata.getId(),
							busiComponent.getText() + pagemeta.getId()
									+ lfwwidget.getId() + "dataset"
									+ dsdata.getId());
			} else {
				if (pagemeta == null)
					editoridMap.put(lfwwidget.getId() + "dataset"
							+ dsdata.getId(), lfwwidget.getId() + "dataset"
							+ dsdata.getId());
				else
					editoridMap.put(pagemeta.getId() + lfwwidget.getId()
							+ "dataset" + dsdata.getId(), pagemeta.getId()
							+ lfwwidget.getId() + "dataset" + dsdata.getId());
			}
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(datasetEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * ��poolds
	 * 
	 * @param ds
	 */
	public void openPoolDsEdit(LFWDSTreeItem ds) {
		Dataset dsdata = (Dataset) ds.getData();
		DataSetEitorInput datasetEditorInput = new DataSetEitorInput(dsdata,
				null, null);
		String editorid = "nc.uap.lfw.perspective.editor.DataSetEditor";
		if (datasetEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		String editorinputid = editoridMap.get("pooldataset" + dsdata.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof DataSetEitorInput) {
					DataSetEitorInput dsEditorInput = (DataSetEitorInput) editorInput;
					Dataset dsnew = (Dataset) dsEditorInput.getCloneElement();
					if (dsnew.getId().equals(dsdata.getId())) {
						editor = parts[i];
						break;
					}
				}
			}
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(datasetEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			editoridMap.put("pooldataset" + dsdata.getId(), "pooldataset"
					+ dsdata.getId());
			try {
				workbenchPage.openEditor(datasetEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * ��grid�༭��
	 * 
	 * @param gridComponent
	 */
	public void openGridEditor(LFWWebComponentTreeItem gridComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(gridComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pageMetaTreeItem = LFWPersTool
				.getPagemetaTreeItem(gridComponent);
		PageMeta pagemeta = null;
		if (pageMetaTreeItem != null)
			pagemeta = pageMetaTreeItem.getPm();
		GridComp grid = (GridComp) gridComponent.getData();
		GridEditorInput gridEditorInput = new GridEditorInput(grid, lfwwidget,
				pagemeta);
		gridComponent.setEditorInput(gridEditorInput);
		String editorid = GridEditor.class.getName();
		if (gridEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		GridComp gridcomp = (GridComp) gridComponent.getData();
		String editorinputid = null;
		if (pagemeta == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "grid"
					+ gridcomp.getId());
		else
			editorinputid = editoridMap.get(pagemeta.getId()
					+ lfwwidget.getId() + "grid" + gridcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof GridEditorInput) {
					GridEditorInput gEditorInput = (GridEditorInput) editorInput;
					GridComp gridnew = (GridComp) gEditorInput
							.getCloneElement();
					PageMeta pmnew = gEditorInput.getPagemeta();
					LfwWidget widgetnew = gEditorInput.getWidget();
					if (pmnew == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& gridnew.getId().equals(gridcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pagemeta.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& gridnew.getId().equals(gridcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(gridEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pagemeta == null)
				editoridMap.put(lfwwidget.getId() + "grid" + gridcomp.getId(),
						lfwwidget.getId() + "grid" + gridcomp.getId());
			else
				editoridMap.put(pagemeta.getId() + lfwwidget.getId() + "grid"
						+ gridcomp.getId(), pagemeta.getId()
						+ lfwwidget.getId() + "grid" + gridcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(gridEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openExcelEditor(LFWWebComponentTreeItem excelComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(excelComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pageMetaTreeItem = LFWPersTool
				.getPagemetaTreeItem(excelComponent);
		PageMeta pagemeta = null;
		if (pageMetaTreeItem != null)
			pagemeta = pageMetaTreeItem.getPm();
		ExcelComp excel = (ExcelComp) excelComponent.getData();
		ExcelEditorInput excelEditorInput = new ExcelEditorInput(excel,
				lfwwidget, pagemeta);
		excelComponent.setEditorInput(excelEditorInput);
		String editorid = ExcelEditor.class.getName();
		if (excelEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		ExcelComp excelcomp = (ExcelComp) excelComponent.getData();
		String editorinputid = null;
		if (pagemeta == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "excel"
					+ excelcomp.getId());
		else
			editorinputid = editoridMap.get(pagemeta.getId()
					+ lfwwidget.getId() + "excel" + excelcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ExcelEditorInput) {
					ExcelEditorInput gEditorInput = (ExcelEditorInput) editorInput;
					ExcelComp gridnew = (ExcelComp) gEditorInput
							.getCloneElement();
					PageMeta pmnew = gEditorInput.getPagemeta();
					LfwWidget widgetnew = gEditorInput.getWidget();
					if (pmnew == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& gridnew.getId().equals(excelcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pagemeta.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& gridnew.getId().equals(excelcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(excelEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pagemeta == null)
				editoridMap.put(
						lfwwidget.getId() + "excel" + excelcomp.getId(),
						lfwwidget.getId() + "excel" + excelcomp.getId());
			else
				editoridMap.put(pagemeta.getId() + lfwwidget.getId() + "excel"
						+ excelcomp.getId(), pagemeta.getId()
						+ lfwwidget.getId() + "excel" + excelcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(excelEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * ��button�༭��
	 * 
	 * @param gridComponent
	 */
	public void openButtonEditor(LFWWebComponentTreeItem buttonComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(buttonComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		ButtonComp button = (ButtonComp) buttonComponent.getData();
		ButtonEditorInput buttonEditorInput = new ButtonEditorInput(button,
				lfwwidget, pm);
		buttonComponent.setEditorInput(buttonEditorInput);
		String editorid = ButtonEditor.class.getName();
		if (buttonEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		ButtonComp buttoncomp = (ButtonComp) buttonComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "button"
					+ buttoncomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId()
					+ "button" + buttoncomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ButtonEditorInput) {
					ButtonEditorInput bEditorInput = (ButtonEditorInput) editorInput;
					PageMeta pmnew = bEditorInput.getPagemeta();
					LfwWidget widgetnew = bEditorInput.getWidget();
					ButtonComp buttonnew = (ButtonComp) bEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& buttonnew.getId().equals(buttoncomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& buttonnew.getId().equals(buttoncomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(buttonEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "button"
						+ buttoncomp.getId(), lfwwidget.getId() + "button"
						+ buttoncomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "button"
						+ buttoncomp.getId(), pm.getId() + lfwwidget.getId()
						+ "button" + buttoncomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(buttonEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openChartEditor(LFWWebComponentTreeItem chartComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(chartComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		ChartComp chart = (ChartComp) chartComponent.getData();
		ChartEditorInput chartEditorInput = new ChartEditorInput(chart,
				lfwwidget, pm);
		chartComponent.setEditorInput(chartEditorInput);
		String editorid = ChartEditor.class.getName();
		if (chartEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		ChartComp chartcomp = (ChartComp) chartComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "chart" + chartcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId() + "chart"
					+ chartcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ChartEditorInput) {
					ChartEditorInput bEditorInput = (ChartEditorInput) editorInput;
					PageMeta pmnew = bEditorInput.getPagemeta();
					LfwWidget widgetnew = bEditorInput.getWidget();
					ChartComp chartnew = (ChartComp) bEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& chartnew.getId().equals(chartcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& chartnew.getId().equals(chartcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(chartEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(
						lfwwidget.getId() + "chart" + chartcomp.getId(),
						lfwwidget.getId() + "chart" + chartcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "chart"
						+ chartcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "chart" + chartcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(chartEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openSelfdefEditor(LFWWebComponentTreeItem selfdefComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(selfdefComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		SelfDefComp selfdef = (SelfDefComp) selfdefComponent.getData();
		SelfDefCompEditorInput selfEditorInput = new SelfDefCompEditorInput(
				selfdef, lfwwidget, pm);
		selfdefComponent.setEditorInput(selfEditorInput);
		String editorid = SelfDefCompEditor.class.getName();
		if (selfEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		SelfDefComp selfdefcomp = (SelfDefComp) selfdefComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "selfdef" + selfdefcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId() + "selfdef"
					+ selfdefcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof SelfDefCompEditorInput) {
					SelfDefCompEditorInput bEditorInput = (SelfDefCompEditorInput) editorInput;
					PageMeta pmnew = bEditorInput.getPagemeta();
					LfwWidget widgetnew = bEditorInput.getWidget();
					SelfDefComp selfnew = (SelfDefComp) bEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& selfnew.getId().equals(selfdefcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& selfnew.getId().equals(selfdefcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(selfEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "selfdef"
						+ selfdefcomp.getId(), lfwwidget.getId() + "selfdef"
						+ selfdefcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "selfdef"
						+ selfdefcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "selfdef" + selfdefcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(selfEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openProgressBarEditor(LFWWebComponentTreeItem buttonComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(buttonComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		ProgressBarComp button = (ProgressBarComp) buttonComponent.getData();
		ProgressBarEditorInput buttonEditorInput = new ProgressBarEditorInput(
				button, lfwwidget, pm);
		buttonComponent.setEditorInput(buttonEditorInput);
		String editorid = ProgressBarEditor.class.getName();
		if (buttonEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		ProgressBarComp buttoncomp = (ProgressBarComp) buttonComponent
				.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "progressbar"
					+ buttoncomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId() + "progressbar"
					+ buttoncomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ProgressBarEditorInput) {
					ProgressBarEditorInput bEditorInput = (ProgressBarEditorInput) editorInput;
					PageMeta pmnew = bEditorInput.getPagemeta();
					LfwWidget widgetnew = bEditorInput.getWidget();
					ProgressBarComp buttonnew = (ProgressBarComp) bEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& buttonnew.getId().equals(buttoncomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& buttonnew.getId().equals(buttoncomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(buttonEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "progressbar"
						+ buttoncomp.getId(), lfwwidget.getId() + "progressbar"
						+ buttoncomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "progressbar"
						+ buttoncomp.getId(), pm.getId() + lfwwidget.getId()
						+ "progressbar" + buttoncomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(buttonEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openRefNodeRelEditor(LFWRefNodeRelTreeItem refNodeRel) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(refNodeRel);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		RefNodeRelation rel = (RefNodeRelation) refNodeRel.getData();
		RefnoderelEditorInput refnodeRelEditorInput = new RefnoderelEditorInput(
				rel, lfwwidget, pm);
		refNodeRel.setEditorInput(refnodeRelEditorInput);
		String editorid = RefnoderelEditor.class.getName();
		if (refnodeRelEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		RefNodeRelation relcomp = (RefNodeRelation) refNodeRel.getData();
		String editorinputid = null;
		if (pm == null)
			editoridMap.get(lfwwidget.getId() + "refnodrel" + relcomp.getId());
		else
			editoridMap.get(pm.getId() + lfwwidget.getId() + "refnodrel"
					+ relcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof RefnoderelEditorInput) {
					RefnoderelEditorInput bEditorInput = (RefnoderelEditorInput) editorInput;
					PageMeta pmnew = bEditorInput.getPagemeta();
					LfwWidget widgetnew = bEditorInput.getWidget();
					RefNodeRelation relnew = (RefNodeRelation) bEditorInput
							.getRefnodeRel();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& relnew.getId().equals(relcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& relnew.getId().equals(relcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(refnodeRelEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "refnodrel"
						+ relcomp.getId(), lfwwidget.getId() + "refnodrel"
						+ relcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "refnodrel"
						+ relcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "refnodrel" + relcomp.getId());
			try {
				workbenchPage.openEditor(refnodeRelEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openImageEditor(LFWWebComponentTreeItem imageComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(imageComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		ImageComp image = (ImageComp) imageComponent.getData();
		ImageEditorInput imageEditorInput = new ImageEditorInput(image,
				lfwwidget, pm);
		imageComponent.setEditorInput(imageEditorInput);
		String editorid = ImageEditor.class.getName();
		if (imageEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		ImageComp buttoncomp = (ImageComp) imageComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "image" + buttoncomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId() + "image"
					+ buttoncomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ImageEditorInput) {
					ImageEditorInput bEditorInput = (ImageEditorInput) editorInput;
					PageMeta pmnew = bEditorInput.getPagemeta();
					LfwWidget widgetnew = bEditorInput.getWidget();
					ImageComp buttonnew = (ImageComp) bEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& buttonnew.getId().equals(buttoncomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& buttonnew.getId().equals(buttoncomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(imageEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "image"
						+ buttoncomp.getId(), lfwwidget.getId() + "image"
						+ buttoncomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "image"
						+ buttoncomp.getId(), pm.getId() + lfwwidget.getId()
						+ "image" + buttoncomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(imageEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * ��imageButton
	 * 
	 * @param imageComponent
	 */
	public void openImageButtonEditor(LFWWebComponentTreeItem imageComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(imageComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = pmTreeItem.getPm();
		ImageComp image = (ImageComp) imageComponent.getData();
		ImageEditorInput imageEditorInput = new ImageEditorInput(image,
				lfwwidget, pm);
		imageComponent.setEditorInput(imageEditorInput);
		String editorid = ImageEditor.class.getName();
		if (imageEditorInput == null || editorid == null)
			return;
		// buttonEditorInput.setProjectPath(getProjectPath(buttonComponent));
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		ImageComp buttoncomp = (ImageComp) imageComponent.getData();
		String editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId()
				+ "image" + buttoncomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ImageEditorInput) {
					ImageEditorInput bEditorInput = (ImageEditorInput) editorInput;
					ImageComp buttonnew = (ImageComp) bEditorInput
							.getCloneElement();
					if (buttonnew.getId().equals(buttoncomp.getId())) {
						editor = parts[i];
						break;
					}
				}
			}
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(imageEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			editoridMap.put(pm.getId() + lfwwidget.getId() + "image"
					+ buttoncomp.getId(), pm.getId() + lfwwidget.getId()
					+ "image" + buttoncomp.getId());
			try {
				workbenchPage.openEditor(imageEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openLabelEditor(LFWWebComponentTreeItem labelComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(labelComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		LabelComp label = (LabelComp) labelComponent.getData();
		LabelEditorInput labelEditorInput = new LabelEditorInput(label,
				lfwwidget, pm);
		labelComponent.setEditorInput(labelEditorInput);
		String editorid = LabelEditor.class.getName();
		if (labelEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		LabelComp labelcomp = (LabelComp) labelComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "label" + labelcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId() + "label"
					+ labelcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof LabelEditorInput) {
					LabelEditorInput lEditorInput = (LabelEditorInput) editorInput;
					PageMeta pmnew = lEditorInput.getPagemeta();
					LabelComp labelnew = (LabelComp) lEditorInput
							.getCloneElement();
					LfwWidget widgetnew = lEditorInput.getWidget();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& labelnew.getId().equals(labelcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& labelnew.getId().equals(labelcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(labelEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(
						lfwwidget.getId() + "label" + labelcomp.getId(),
						lfwwidget.getId() + "label" + labelcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "label"
						+ labelcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "label" + labelcomp.getId());
			try {
				workbenchPage.openEditor(labelEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openLinkEditor(LFWWebComponentTreeItem linkComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(linkComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		LinkComp link = (LinkComp) linkComponent.getData();
		LinkCompEditroInput linkcompEditorInput = new LinkCompEditroInput(link,
				lfwwidget, pm);
		linkComponent.setEditorInput(linkcompEditorInput);
		String editorid = LinkCompEditor.class.getName();
		if (linkcompEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		LinkComp linkcomp = (LinkComp) linkComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "linkcomp" + linkcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId() + "linkcomp"
					+ linkcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof LinkCompEditroInput) {
					LinkCompEditroInput lEditorInput = (LinkCompEditroInput) editorInput;
					PageMeta pmnew = lEditorInput.getPagemeta();
					LfwWidget widgetnew = lEditorInput.getWidget();
					LinkComp linknew = (LinkComp) lEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& linknew.getId().equals(linkcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& linknew.getId().equals(linkcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(linkcompEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "linkcomp"
						+ linkcomp.getId(), lfwwidget.getId() + "linkcomp"
						+ linkcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "linkcomp"
						+ linkcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "linkcomp" + linkcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(linkcompEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openIFrameEditor(LFWWebComponentTreeItem iframecomponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(iframecomponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		IFrameComp iFrame = (IFrameComp) iframecomponent.getData();
		IFrameEditorInput iframeEditorInput = new IFrameEditorInput(iFrame,
				lfwwidget, pm);
		iframecomponent.setEditorInput(iframeEditorInput);
		String editorid = IFrameEditor.class.getName();
		if (iframeEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		IFrameComp iframecomp = (IFrameComp) iframecomponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "iframe"
					+ iframecomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId()
					+ "iframe" + iframecomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof IFrameEditorInput) {
					IFrameEditorInput ifEditorInput = (IFrameEditorInput) editorInput;
					PageMeta pmnew = ifEditorInput.getPagemeta();
					LfwWidget widgetnew = ifEditorInput.getWidget();
					IFrameComp iframenew = (IFrameComp) ifEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& iframenew.getId().equals(iframenew.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& iframenew.getId().equals(iframenew.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(iframeEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "iframe"
						+ iframecomp.getId(), lfwwidget.getId() + "iframe"
						+ iframecomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "iframe"
						+ iframecomp.getId(), pm.getId() + lfwwidget.getId()
						+ "iframe" + iframecomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(iframeEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * ��form�༭��
	 * 
	 * @param formComponent
	 */
	public void openFormEditor(LFWWebComponentTreeItem formComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(formComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pageTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pageTreeItem != null)
			pm = pageTreeItem.getPm();
		FormComp formcomp = (FormComp) formComponent.getData();
		FormEditorInput formEditorInput = new FormEditorInput(formcomp,
				lfwwidget, pm);
		formComponent.setEditorInput(formEditorInput);
		String editorid = FormEditor.class.getName();
		if (formEditorInput == null || editorid == null)
			return;
		// formEditorInput.setProjectPath(getProjectPath(formComponent));
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "form"
					+ formcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId()
					+ "form" + formcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof FormEditorInput) {
					FormEditorInput fEditorInput = (FormEditorInput) editorInput;
					PageMeta pmnew = fEditorInput.getPagemeta();
					LfwWidget widgetnew = fEditorInput.getWidget();
					FormComp formnew = (FormComp) fEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& formnew.getId().equals(formcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& formnew.getId().equals(formcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(formEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "form" + formcomp.getId(),
						lfwwidget.getId() + "form" + formcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "form"
						+ formcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "form" + formcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(formEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openToolbarEditor(LFWWebComponentTreeItem toolbarComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(toolbarComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pageTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pageTreeItem != null)
			pm = pageTreeItem.getPm();
		ToolBarComp toolbar = (ToolBarComp) toolbarComponent.getData();
		ToolBarEditorInput toolbarEditorInput = new ToolBarEditorInput(toolbar,
				lfwwidget, pm);
		toolbarComponent.setEditorInput(toolbarEditorInput);
		String editorid = ToolBarEditor.class.getName();
		if (toolbarEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		String editorinputid = null;
		if (pm == null)
			editoridMap.get(lfwwidget.getId() + "toolbar" + toolbar.getId());
		else
			editoridMap.get(pm.getId() + lfwwidget.getId() + "toolbar"
					+ toolbar.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ToolBarEditorInput) {
					ToolBarEditorInput toolInput = (ToolBarEditorInput) editorInput;
					PageMeta pmnew = toolInput.getPagemeta();
					LfwWidget widgetnew = toolInput.getWidget();
					ToolBarComp toolbarnew = (ToolBarComp) toolInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& toolbarnew.getId().equals(toolbar.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& toolbarnew.getId().equals(toolbar.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(toolbarEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(
						lfwwidget.getId() + "toolbar" + toolbar.getId(),
						lfwwidget.getId() + "toolbar" + toolbar.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "toolbar"
						+ toolbar.getId(), pm.getId() + lfwwidget.getId()
						+ "toolbar" + toolbar.getId());
			try {
				workbenchPage.openEditor(toolbarEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * ���ı���༭��
	 * 
	 * @param textCompComponent
	 */
	public void openTextEditor(LFWWebComponentTreeItem textCompComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(textCompComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeTtem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeTtem != null)
			pm = pmTreeTtem.getPm();
		TextComp textComp = (TextComp) textCompComponent.getData();
		TextCompEditorInput textcompEditorInput = new TextCompEditorInput(
				textComp, lfwwidget, pm);
		textCompComponent.setEditorInput(textcompEditorInput);
		String editorid = TextCompEditor.class.getName();
		if (textcompEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		TextComp textcomp = (TextComp) textCompComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "textcomp" + textcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId() + "textcomp"
					+ textcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof TextCompEditorInput) {
					TextCompEditorInput textEditorInput = (TextCompEditorInput) editorInput;
					PageMeta pmnew = textEditorInput.getPagemeta();
					LfwWidget widgetnew = textEditorInput.getWidget();
					TextComp textnew = (TextComp) textEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& textnew.getId().equals(textcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pm == null) {
							if (widgetnew.getId().equals(lfwwidget.getId())
									&& textnew.getId().equals(textcomp.getId())) {
								editor = parts[i];
								break;
							}
						} else {
							if (pmnew.getId().equals(pm.getId())
									&& widgetnew.getId().equals(
											lfwwidget.getId())
									&& textnew.getId().equals(textcomp.getId())) {
								editor = parts[i];
								break;
							}
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(textcompEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "textcomp"
						+ textcomp.getId(), lfwwidget.getId() + "textcomp"
						+ textcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "textcomp"
						+ textcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "textcomp" + textcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(textcompEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * �����༭��
	 * 
	 * @param gridComponent
	 */
	public void openTreeEditor(LFWWebComponentTreeItem treeComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(treeComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		TreeViewComp tree = (TreeViewComp) treeComponent.getData();
		TreeEditorInput treeEditorInput = new TreeEditorInput(tree, lfwwidget,
				pm);
		treeComponent.setEditorInput(treeEditorInput);
		String editorid = TreeEditor.class.getName();
		if (treeEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		TreeViewComp treecomp = (TreeViewComp) treeComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "tree"
					+ treecomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId()
					+ "tree" + treecomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof TreeEditorInput) {
					TreeEditorInput treeviewEditorInput = (TreeEditorInput) editorInput;
					PageMeta pmnew = treeviewEditorInput.getPagemeta();
					LfwWidget widgetnew = treeviewEditorInput.getWidget();
					TreeViewComp treenew = (TreeViewComp) treeviewEditorInput
							.getCloneElement();
					if (pm == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& treenew.getId().equals(treecomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& treenew.getId().equals(treecomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(treeEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "tree" + treecomp.getId(),
						lfwwidget.getId() + "tree" + treecomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "tree"
						+ treecomp.getId(), pm.getId() + lfwwidget.getId()
						+ "tree" + treecomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(treeEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * ���������ݱ༭��
	 * 
	 * @param treeComponent
	 */
	public void openComboEditor(LFWComboTreeItem comboDataComponent) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(comboDataComponent);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		ComboData comb = (ComboData) comboDataComponent.getData();
		ComboDataEidtorInput comboDataEditorInput = new ComboDataEidtorInput(
				comb, lfwwidget, pm);
		comboDataComponent.setEditorInput(comboDataEditorInput);
		String editorid = ComboDataEditor.class.getName();
		if (comboDataEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		ComboData combcomp = (ComboData) comboDataComponent.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "combodata"
					+ combcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId()
					+ "combodata" + combcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof ComboDataEidtorInput) {
					ComboDataEidtorInput comboEditorInput = (ComboDataEidtorInput) editorInput;
					PageMeta pmnew = comboEditorInput.getPagemeta();
					LfwWidget widgetnew = comboEditorInput.getWidget();
					ComboData combonew = (ComboData) comboEditorInput
							.getCloneElement();
					if (pmnew == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& combonew.getId().equals(combcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& combonew.getId().equals(combcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(comboDataEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(lfwwidget.getId() + "combodata"
						+ combcomp.getId(), lfwwidget.getId() + "combodata"
						+ combcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "combodata"
						+ combcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "combodata" + combcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(comboDataEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * �򿪲��սڵ�
	 * 
	 * @param comboDataComponent
	 */
	public void openRefNodeEditor(LFWRefNodeTreeItem refnodeTreeItem) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(refnodeTreeItem);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		LFWPersTool.setLfwWidget(lfwwidget);
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		BaseRefNode refnode = (BaseRefNode) refnodeTreeItem.getData();
		RefNodeEditorInput refnodeEditorInput = new RefNodeEditorInput(refnode,
				lfwwidget, pm);
		refnodeTreeItem.setEditorInput(refnodeEditorInput);
		String editorid = RefNodeEditor.class.getName();
		if (refnodeEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		BaseRefNode refcomp = (BaseRefNode) refnodeTreeItem.getData();
		String editorinputid = null;
		if (pm == null)
			editorinputid = editoridMap.get(lfwwidget.getId() + "refnode"
					+ refcomp.getId());
		else
			editorinputid = editoridMap.get(pm.getId() + lfwwidget.getId()
					+ "refnode" + refcomp.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof RefNodeEditorInput) {
					RefNodeEditorInput refEditorInput = (RefNodeEditorInput) editorInput;
					PageMeta pmnew = refEditorInput.getPagemeta();
					LfwWidget widgetnew = refEditorInput.getWidget();
					BaseRefNode refnew = (BaseRefNode) refEditorInput.getCloneElement();
					if (pmnew == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& refnew.getId().equals(refcomp.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& refnew.getId().equals(refcomp.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(refnodeEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put(
						lfwwidget.getId() + "refnode" + refcomp.getId(),
						lfwwidget.getId() + "refnode" + refcomp.getId());
			else
				editoridMap.put(pm.getId() + lfwwidget.getId() + "refnode"
						+ refcomp.getId(), pm.getId() + lfwwidget.getId()
						+ "refnode" + refcomp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(refnodeEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * �򿪹������в��սڵ�
	 * 
	 * @param ds
	 */
	public void openPoolRefNodeEdit(LFWRefNodeTreeItem refnodeTreeItem) {
		RefNodeConf refnode = (RefNodeConf) refnodeTreeItem.getData();
		RefNodeEditorInput refnodeEditorInput = new RefNodeEditorInput(refnode,
				null, null);
		refnodeEditorInput.setFromPool(true);
		refnodeTreeItem.setEditorInput(refnodeEditorInput);
		String editorid = "nc.uap.lfw.refnode.core.RefNodeEditor";
		if (refnodeEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		String editorinputid = editoridMap.get("poolrefnode" + refnode.getId());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof RefNodeEditorInput) {
					RefNodeEditorInput refEditorInput = (RefNodeEditorInput) editorInput;
					BaseRefNode refnodenew = (BaseRefNode) refEditorInput
							.getCloneElement();
					if (refnodenew.getId().equals(refnode.getId())) {
						editor = parts[i];
						break;
					}
				}
			}
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(refnodeEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			editoridMap.put("poolrefnode" + refnode.getId(), "poolrefnode"
					+ refnode.getId());
			try {
				workbenchPage.openEditor(refnodeEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	// ��datasets�༭��
	public void openDatasetsEditor(LFWSeparateTreeItem treeItem) {
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(treeItem);
		LfwWidget lfwwidget = widgetTreeItem.getWidget();
		// getWidget(widgetTreeItem);
		LFWPersTool.setLfwWidget(lfwwidget);
		DatasetsEditorInput datasetsEditorInput = new DatasetsEditorInput(
				treeItem);
		String editorid = DatasetsEditor.class.getName();
		if (datasetsEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		File file = (File) treeItem.getData();
		String editorinputid = editoridMap.get(file.getPath() + "datasets"
				+ file.toString());
		if (editorinputid != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = (IEditorInput) parts[i]
						.getEditorInput();
				if (editorInput instanceof DatasetsEditorInput) {
					DatasetsEditorInput gEditorInput = (DatasetsEditorInput) editorInput;
					File filenew = (File) gEditorInput.getSeparateTreeItem()
							.getData();
					String name = (String) filenew.toString();
					if (file.toString().equals(name)) {
						editor = parts[i];
						break;
					}
				}
			}
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(datasetsEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			editoridMap.put(file.getPath() + "datasets" + file.toString(), file
					.getPath()
					+ "datasets" + file.toString());
			try {
				workbenchPage.openEditor(datasetsEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

	public void openMenubarEditor(LFWMenubarCompTreeItem treeItem) {
		MenubarComp menubar = null;
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool
				.getWidgetTreeItem(treeItem);
		LfwWidget lfwwidget = null;
		if (widgetTreeItem != null)
			lfwwidget = widgetTreeItem.getWidget();
		MenubarComp menubarOld = (MenubarComp) treeItem.getData();
		LFWPageMetaTreeItem pageMetaTreeItem = LFWPersTool
				.getPagemetaTreeItem(treeItem);
		PageMeta pm = null;
		if (pageMetaTreeItem != null)
			pm = pageMetaTreeItem.getPm();
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		if (treeItem.isFromWidget()) {
			menubar = widget.getViewMenus().getMenuBar(menubarOld.getId());
		} else {
			menubar = pm.getViewMenus().getMenuBar(menubarOld.getId());
		}
		MenubarEditorInput menubarEditorInput = new MenubarEditorInput(menubar,
				pm);
		if (treeItem.isFromWidget())
			menubarEditorInput.setWidget(widget);
		String editorid = MenubarEditor.class.getName();
		if (menubarEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		MenubarComp menubarComp = ((MenubarComp) treeItem.getData());
		String editorInputId = null;
		if (pm == null)
			editorInputId = editoridMap.get("menubar_" + widget.getId() + "_"
					+ menubarComp.getId());
		else
			editorInputId = editoridMap.get("menubar_" + pm.getId() + "_"
					+ menubarComp.getId());
		if (editorInputId != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = parts[i].getEditorInput();
				if (editorInput instanceof MenubarEditorInput) {
					MenubarEditorInput mbEditorInput = (MenubarEditorInput) editorInput;
					MenubarComp mbnew = (MenubarComp) mbEditorInput
							.getElement();
					PageMeta pmnew = mbEditorInput.getPagemeta();
					LfwWidget widgetnew = mbEditorInput.getWidget();
					if (pmnew == null) {
						if (widgetnew.getId().equals(lfwwidget.getId())
								&& menubarComp.getId().equals(mbnew.getId())) {
							editor = parts[i];
							break;
						}
					} else {
						if (pmnew.getId().equals(pm.getId())
								&& widgetnew.getId().equals(lfwwidget.getId())
								&& menubarComp.getId().equals(mbnew.getId())) {
							editor = parts[i];
							break;
						}
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(menubarEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put("menubar_" + widget.getId() + "_"
						+ menubarComp.getId(), "menubar_" + widget.getId()
						+ "_" + menubarComp.getId());
			else
				editoridMap.put("menubar_" + pm.getId() + "_"
						+ menubarComp.getId(), "menubar_" + pm.getId() + "_"
						+ menubarComp.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(menubarEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * ���Ҽ��˵�
	 * 
	 * @param treeItem
	 */
	public void openContextMenuEditor(LFWContextMenuTreeItem treeItem) {
		// MenubarEditorInput menubarEditorInput = new
		// MenubarEditorInput(treeItem);
		ContextMenuComp oldMenubar = (ContextMenuComp) treeItem.getData();
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool
				.getCurrentPageMetaTreeItem();
		PageMeta pm = null;
		if (pmTreeItem != null)
			pm = pmTreeItem.getPm();
		ContextMenuComp menubar = null;
		menubar = widget.getViewMenus().getContextMenu(oldMenubar.getId());
		if(menubar == null)
			menubar = pm.getViewMenus().getContextMenu(oldMenubar.getId());
		ContextMenuEditorInput menubarEditorInput = new ContextMenuEditorInput(
				menubar, widget, pm);
		String editorid = ContextMenuEditor.class.getName();
		if (menubarEditorInput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = getViewSite().getPage();
		IEditorPart editor = null;
		String editorInputId = null;
		if (pm == null){
			editorInputId = editoridMap.get("contextmenubar_" + widget.getId() + "_"
					+ menubar.getId());
		}
		else{
			editorInputId = editoridMap.get("contextmenubar_" + pm.getId() + "_"
					+ widget.getId() + "_" + menubar.getId());
		}
		if (editorInputId != null) {
			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
			for (int i = 0; i < parts.length; i++) {
				IEditorInput editorInput = parts[i].getEditorInput();
				if (editorInput instanceof ContextMenuEditorInput) {
					ContextMenuEditorInput mbEditorInput = (ContextMenuEditorInput) editorInput;
					ContextMenuComp mbnew = (ContextMenuComp) mbEditorInput
							.getElement();
					if (menubar.getId().equals(mbnew.getId())) {
						editor = parts[i];
						break;
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(menubarEditorInput, editorid);
				} catch (PartInitException e1) {
					MainPlugin.getDefault().logError(e1.getMessage(), e1);
				}
			}
		} else {
			if (pm == null)
				editoridMap.put("contextmenubar_" + widget.getId() + "_"
						+ menubar.getId(), "contextmenubar_"
						+ widget.getId() + "_" + menubar.getId());
			else
				editoridMap.put("contextmenubar_" + pm.getId() + "_"
						+ widget.getId() + "_" + menubar.getId(),
						"contextmenubar_" + pm.getId() + "_" + widget.getId()
								+ "_" + menubar.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(menubarEditorInput, editorid);
			} catch (PartInitException e1) {
				MainPlugin.getDefault().logError(e1.getMessage(), e1);
			}
		}

	}

//	// ��pageflow�༭��
//	@SuppressWarnings("deprecation")
//	public void openPageFlowEditor(LFWPageFlowTreeItem treeItem) {
//		PageFlow pageFlow = (PageFlow) treeItem.getData();
//		PageFlowEditorInput editorinput = new PageFlowEditorInput(pageFlow);
//		String editorid = PageFlowEditor.class.getName();
//		if (editorinput == null || editorid == null)
//			return;
//		IWorkbenchPage workbenchPage = getViewSite().getPage();
//		IEditorPart editor = null;
//		String editorinputid = editoridMap.get("pageflow" + pageFlow.getName());
//		if (editorinputid != null) {
//			IEditorPart[] parts = LFWTool.getEditors(workbenchPage);
//			for (int i = 0; i < parts.length; i++) {
//				IEditorInput editorInput = (IEditorInput) parts[i]
//						.getEditorInput();
//				if (editorInput instanceof PageFlowEditorInput) {
//					PageFlowEditorInput pfEditorInput = (PageFlowEditorInput) editorInput;
//					PageFlow pfnew = (PageFlow) pfEditorInput.getPageFlow();
//					String name = pfnew.getName();
//					if (pageFlow.getName().equals(name)) {
//						editor = parts[i];
//						break;
//					}
//				}
//			}
//			if (editor != null)
//				workbenchPage.bringToTop(editor);
//			else {
//				try {
//					workbenchPage.openEditor(editorinput, editorid);
//				} catch (PartInitException e1) {
//					MainPlugin.getDefault().logError(e1.getMessage(), e1);
//				}
//			}
//		} else {
//			editoridMap.put("pageflow" + pageFlow.getName(), "pageflow"
//					+ pageFlow.getName());
//			try {
//				workbenchPage.openEditor(editorinput, editorid);
//			} catch (PartInitException e1) {
//				MainPlugin.getDefault().logError(e1.getMessage(), e1);
//			}
//		}
//	}

	Map<String, LFWSeparateTreeItem> separateMap = new HashMap<String, LFWSeparateTreeItem>();

	// �����ļ���
	public LFWSeparateTreeItem createFolder(TreeItem tree, File file,
			String name) {
		File f = new File(file, name);
		LFWSeparateTreeItem dirtory = new LFWSeparateTreeItem(tree, f);
		return dirtory;
	}

	// ���ڵ�ʱ��򿪹�
	public boolean hasOpened(TreeItem treeItem) {
		if (treeItem instanceof LFWPageMetaTreeItem) {
			TreeItem[] pmItems = treeItem.getItems();
			if (pmItems.length > 0) {
				for (int i = 0; i < pmItems.length; i++) {
					if (pmItems[i] instanceof LFWPageMetaTreeItem)
						continue;
					else
						return true;
				}

				return false;
			} else
				return false;

		} else {
			TreeItem[] items = treeItem.getItems();
			if (items.length > 0)
				return true;
			else
				return false;
		}
	}

	public void setFocus() {
		treeView.getControl().setFocus();

	}

	/**
	 * ����Ŀ¼
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addDirTreeNode(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();// (File)
		// selTI.getData();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼��");
		File f = new File(parentFile, dirName);
		if (!f.exists()) {
			f.mkdirs();
		}
		TreeItem ti = new LFWDirtoryTreeItem(selTI, f);
		// ��������
		((LFWDirtoryTreeItem) ti).setType(LFWDirtoryTreeItem.NODE_FOLDER);
		//guoweic: �˵���ʾ������
		selTI.setExpanded(true);
		return ti;
	}

	public TreeItem addVirtualTreeNode(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();// (File)
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼!");
		List<String> allTreeItems = LFWPersTool.getAllVirtualTreeItems();
		if (allTreeItems.contains(dirName))
			throw new Exception("�Ѿ�����IDΪ" + dirName + "������Ŀ¼!");
		File f = new File(parentFile, dirName);
		if (!f.exists()) {
			f.mkdirs();
		}
		TreeItem ti = new LFWVirtualDirTreeItem(selTI, f);
		selTI.setExpanded(true);
		return ti;
	}

	public TreeItem addPageMetaTreeNode(String appId, String appName)
			throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();// (File)
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼!");
		if (appId != null
				&& (appId.equals("login") || appId.equals("main")
						|| appId.equals("quickdesk")
						|| appId.equals("reference") || appId
						.equals("userprefs")))
			throw new Exception("�Ѿ�����IDΪ" + appId + "�Ľڵ�!");
		String projectPath = LFWPersTool.getProjectPath();
		String[] projPaths = new String[1];
		projPaths[0] = projectPath;
		Map<String, String>[] appNamesMap = NCConnector.getPageNames(projPaths);
		if (appNamesMap != null && appNamesMap.length > 0) {
			Map<String, String> appNames = appNamesMap[0];
			if (appNames != null && appNames.size() > 0) {
				Iterator<String> itwd = appNames.keySet().iterator();
				while (itwd.hasNext()) {
					String applicationId = (String) itwd.next();
					if (applicationId.indexOf("/") != -1) {
						applicationId = applicationId.substring(applicationId
								.indexOf("/"));
					}
					if (applicationId.equals(appId)) {
						throw new Exception("�Ѿ�����IDΪ" + appId + "�Ľڵ�!");
					}
				}
			}
		}
		File f = new File(parentFile, appId);
		if (!f.exists()) {
			f.mkdirs();
		}
		TreeItem ti = new LFWPageMetaTreeItem(selTI, f, appName + "[" + appId
				+ "]");
		((LFWPageMetaTreeItem) ti).setId(appId);
		((LFWPageMetaTreeItem) ti).setItemName(appName);
		selTI.setExpanded(true);
		return ti;
	}

	/**
	 * ��Windows���ļ��У��ڵ�������Window�ڵ�
	 * 
	 * @param windowId
	 * @param windowName
	 * @param treeItem
	 * @return
	 * @author chouhl
	 * @throws Exception
	 */
	public TreeItem addWindowTreeNode(String windowId, String windowName,IProject project, TreeItem currentTreeItem) throws Exception {
		TreeItem selTI = LFWPersTool.getWindowDirectoryTreeItem(project, currentTreeItem);
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		File f = new File(parentFile, windowId);
		if (!f.exists()) {
			f.mkdirs();
		}
		LFWPageMetaTreeItem ti = new LFWPageMetaTreeItem(selTI, f, windowName + "[" + windowId + "]");
		ti.setId(windowId);
		ti.setItemName(windowName);
		selTI.setExpanded(true);
		return ti;
	}

	/**
	 * ���ӽڵ�
	 * @param amcId
	 * @param amcName
	 * @param itemType
	 * @return
	 * @throws Exception
	 */
	public TreeItem addAMCTreeNode(String amcId, String amcName, String itemType) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem selTI = tree.getSelection()[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		File f = new File(parentFile, amcId);
		if (!f.exists()) {
			f.mkdirs();
		}
		TreeItem ti = null;
		if (ILFWTreeNode.APPLICATION.equals(itemType)) {
			ti = new LFWApplicationTreeItem(selTI, f, amcName + "[" + amcId	+ "]");
		}else if (ILFWTreeNode.MODEL.equals(itemType)) {
			ti = new LFWModelTreeItem(selTI, f, amcName + "[" + amcId + "]");
		}else if(ILFWTreeNode.WINDOW.equals(itemType)){
			ti = new LFWPageMetaTreeItem(selTI, f, amcName + "[" + amcId + "]");
		}else {
			ti = new LFWAMCTreeItem(selTI, f, amcName + "[" + amcId + "]");
		}
		if(ti instanceof LFWBasicTreeItem){
			((LFWBasicTreeItem)ti).setId(amcId);
			((LFWBasicTreeItem)ti).setItemName(amcName);
		}
		selTI.setExpanded(true);
		return ti;
	}
	
	/**
	 * ����View�ڵ�
	 * @param viewId
	 * @param widget
	 * @return
	 */
	public TreeItem addViewTreeNode(LfwWidget widget){
		Tree tree = treeView.getTree();
		TreeItem selTI = tree.getSelection()[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		File f = new File(parentFile, widget.getId());
		if (!f.exists()) {
			f.mkdirs();
		}
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		pm.addWidget(widget);
		TreeItem ti = new LFWWidgetTreeItem(selTI, f, widget, "[" + LFWTool.getViewText(selTI) + "] " + widget.getId());
		refreshWidgetTreeItem(ti);
		if(ti instanceof LFWBasicTreeItem){
			((LFWBasicTreeItem)ti).setId(widget.getId());
		}
		selTI.setExpanded(true);
		return ti;
	}

	/**
	 * ����ds�ڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addDSTreeNode(String dirName,String caption) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼!");
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(selTI);
		LfwWidget widget = widgetTreeItem.getWidget();
		Dataset originalds = widget.getViewModels().getDataset(dirName);
		if (originalds != null)
			throw new Exception("��Ƭ�����Ѿ�����IDΪ" + dirName + "�����ݼ�!");
		Dataset ds = new Dataset();
		ds.setId(dirName);
		ds.setCaption(caption);
		TreeItem newdsTreeItem = new LFWDSTreeItem(selTI, ds, ds.getId());
		selTI.setExpanded(true);
		return newdsTreeItem;
	}

	/**
	 * ����contextMenubar
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addContextMenubarTreeNode(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼!");
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(selTI);
		LfwWidget widget = widgetTreeItem.getWidget();
		ContextMenuComp originalds = widget.getViewMenus().getContextMenu(
				dirName);
		if (originalds != null)
			throw new Exception("��Ƭ�����Ѿ�����IDΪ" + dirName + "���Ҽ��˵�!");
		ContextMenuComp contextMenubar = new ContextMenuComp();
		contextMenubar.setId(dirName);
		LFWContextMenuTreeItem contextMenubarTreeItem = new LFWContextMenuTreeItem(
				selTI, 
				LFWTool.getWEBProjConstantValue("CONTEXT_MENUCOMP_ELEMENT", ((LFWBasicTreeItem)selTI).getLfwVersion()),
				contextMenubar);
		selTI.setExpanded(true);
		return contextMenubarTreeItem;
	}

	/**
	 * ����refdsds�ڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addRefDSTreeNode(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼��");
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(selTI);
		LfwWidget widget = widgetTreeItem.getWidget();
		Dataset originalds = widget.getViewModels().getDataset(dirName);
		if (originalds != null)
			throw new Exception("��Ƭ�����Ѿ�����IDΪ" + dirName + "�����ݼ�!");
		RefDataset refds = new RefDataset();
		refds.setId(dirName);
		TreeItem newdsTreeItem = new LFWDSTreeItem(selTI, refds, refds.getId());
		selTI.setExpanded(true);
		return newdsTreeItem;
	}

	public TreeItem addPoolDSTreeNode(String dirName) throws Exception {
		List<String> allPooldsIds = LFWPersTool.getAllPoolDsId();
		if (allPooldsIds.contains(dirName))
			throw new Exception("�Ѿ�����IDΪ" + dirName + "�Ĺ������ݼ�!");
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		// if (selTIs == null || selTIs.length == 0)
		// throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		String fullName = dirName;
		Dataset ds = new Dataset();
		ds.setId(fullName);
		TreeItem newdsTreeItem = null;
		LFWDirtoryTreeItem rootTreeItem = getPubPoolDsTreeItem();
		if (fullName.indexOf(".") != -1) {
			String foldername = fullName.substring(0, fullName.indexOf("."));
			String leftfolder = fullName.substring(fullName.indexOf(".") + 1);
			while (leftfolder != null) {
				String newRootPath = rootTreeItem.getFile().getPath().replace(
						"�������ݼ�", "web/pagemeta/public/dspool");
				File newfile = new File(newRootPath, foldername);
				if (!newfile.exists() || !(newfile.isDirectory())) {
					newfile.mkdir();
				}
				LFWDirtoryTreeItem treeItem = null;
				TreeItem[] childTreeItems = rootTreeItem.getItems();
				for (int i = 0; i < childTreeItems.length; i++) {
					if (childTreeItems[i] instanceof LFWDirtoryTreeItem) {
						LFWDirtoryTreeItem child = (LFWDirtoryTreeItem) childTreeItems[i];
						if (child.getText().equals(foldername)) {
							treeItem = child;
							break;
						}
					}
				}
				if (treeItem == null) {
					treeItem = new LFWDirtoryTreeItem(rootTreeItem, newfile);
					treeItem.setType(LFWDirtoryTreeItem.POOLDSFOLDER);
				}
				rootTreeItem = treeItem;
				if (leftfolder.indexOf(".") != -1) {
					foldername = leftfolder.substring(0, leftfolder
							.indexOf("."));
					leftfolder = leftfolder
							.substring(leftfolder.indexOf(".") + 1);

				} else {
					newdsTreeItem = new LFWDSTreeItem(rootTreeItem, ds,
							leftfolder);
					leftfolder = null;
				}
			}
		} else
			newdsTreeItem = new LFWDSTreeItem(rootTreeItem, ds, ds.getId());
		selTI.setExpanded(true);
		return newdsTreeItem;
	}

	/**
	 * �õ��������ݼ�Item
	 * 
	 * @return
	 */
	private LFWDirtoryTreeItem getPubPoolDsTreeItem() {
		TreeItem[] treeItems = treeView.getTree().getItems();
		IProject project = LFWPersTool.getCurrentProject();
		LFWProjectTreeItem projectTreeItem = null;
		for (int i = 0; i < treeItems.length; i++) {
			LFWProjectTreeItem projectItem = (LFWProjectTreeItem) treeItems[i];
			if (projectItem.getProjectModel().getJavaProject().equals(project)) {
				projectTreeItem = projectItem;
				break;
			}
		}
		LFWDirtoryTreeItem nodeTreeItem = null;
		TreeItem[] nodesTreeItems = null;
		LFWBusinessCompnentTreeItem busiCompTreeItem = null;
		if (LFWPersTool.isBCPProject(project)) {
			TreeItem currComp = LFWPersTool.getCurrentBusiCompTreeItem();
			TreeItem[] compTreeItems = projectTreeItem.getItems();
			for (int i = 0; i < compTreeItems.length; i++) {
				TreeItem treeItem = compTreeItems[i];
				if (treeItem.getText().equals(currComp.getText())) {
					busiCompTreeItem = (LFWBusinessCompnentTreeItem) treeItem;
					break;
				}
			}
		}
		if (busiCompTreeItem != null)
			nodesTreeItems = busiCompTreeItem.getItems();
		else
			nodesTreeItems = projectTreeItem.getItems();
		for (int i = 0; i < nodesTreeItems.length; i++) {
			TreeItem treeItem = nodesTreeItems[i];
			if (treeItem.getText().equals(WEBProjConstants.PUBLIC_DATASET)) {
				nodeTreeItem = (LFWDirtoryTreeItem) treeItem;
				break;
			}
		}
		return nodeTreeItem;
	}

	/**
	 * ����Ƭ��
	 * 
	 * @return
	 */
//	private LFWDirtoryTreeItem getPubPoolWidgetTreeItem() {
//		TreeItem[] treeItems = treeView.getTree().getItems();
//		IProject project = LFWPersTool.getCurrentProject();
//		LFWProjectTreeItem projectTreeItem = null;
//		for (int i = 0; i < treeItems.length; i++) {
//			LFWProjectTreeItem projectItem = (LFWProjectTreeItem) treeItems[i];
//			if (projectItem.getProjectModel().getJavaProject().equals(project)) {
//				projectTreeItem = projectItem;
//				break;
//			}
//		}
//		LFWDirtoryTreeItem nodeTreeItem = null;
//		TreeItem[] nodesTreeItems = null;
//		LFWBusinessCompnentTreeItem busiCompTreeItem = null;
//		if (LFWPersTool.isBCPProject(project)) {
//			TreeItem currComp = LFWPersTool.getCurrentBusiCompTreeItem();
//			TreeItem[] compTreeItems = projectTreeItem.getItems();
//			for (int i = 0; i < compTreeItems.length; i++) {
//				TreeItem treeItem = compTreeItems[i];
//				if (treeItem.getText().equals(currComp.getText())) {
//					busiCompTreeItem = (LFWBusinessCompnentTreeItem) treeItem;
//					break;
//				}
//			}
//		}
//		if (busiCompTreeItem != null)
//			nodesTreeItems = busiCompTreeItem.getItems();
//		else
//			nodesTreeItems = projectTreeItem.getItems();
//		for (int i = 0; i < nodesTreeItems.length; i++) {
//			TreeItem treeItem = nodesTreeItems[i];
//			if (treeItem.getText().equals(WEBProjConstants.PUBLIC_WIDGET)) {
//				nodeTreeItem = (LFWDirtoryTreeItem) treeItem;
//				break;
//			}
//		}
//		return nodeTreeItem;
//	}

	private LFWDirtoryTreeItem getPubPoolRefNodeTreeItem() {
		TreeItem[] treeItems = treeView.getTree().getItems();
		IProject project = LFWPersTool.getCurrentProject();
		LFWProjectTreeItem projectTreeItem = null;
		for (int i = 0; i < treeItems.length; i++) {
			LFWProjectTreeItem projectItem = (LFWProjectTreeItem) treeItems[i];
			if (projectItem.getProjectModel().getJavaProject().equals(project)) {
				projectTreeItem = projectItem;
				break;
			}
		}
		LFWDirtoryTreeItem nodeTreeItem = null;
		TreeItem[] nodesTreeItems = null;
		LFWBusinessCompnentTreeItem busiCompTreeItem = null;
		if (LFWPersTool.isBCPProject(project)) {
			TreeItem currComp = LFWPersTool.getCurrentBusiCompTreeItem();
			TreeItem[] compTreeItems = projectTreeItem.getItems();
			for (int i = 0; i < compTreeItems.length; i++) {
				TreeItem treeItem = compTreeItems[i];
				if (treeItem.getText().equals(currComp.getText())) {
					busiCompTreeItem = (LFWBusinessCompnentTreeItem) treeItem;
					break;
				}
			}
		}
		if (busiCompTreeItem != null)
			nodesTreeItems = busiCompTreeItem.getItems();
		else
			nodesTreeItems = projectTreeItem.getItems();
		for (int i = 0; i < nodesTreeItems.length; i++) {
			TreeItem treeItem = nodesTreeItems[i];
			if (treeItem.getText().equals(WEBProjConstants.PUBLIC_REFNODE)) {
				nodeTreeItem = (LFWDirtoryTreeItem) treeItem;
				break;
			}
		}
		return nodeTreeItem;
	}

	/**
	 * ���ӹ������еĲ��սڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addPoolRefNode(String dirName) throws Exception {
		List<String> allPoolrefNodeIds = LFWPersTool.getAllPoolRefNodeId();
		if (allPoolrefNodeIds.contains(dirName))
			throw new Exception("�Ѿ�����IDΪ" + dirName + "�Ĺ�������!");
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		TreeItem selTI = selTIs[0];
		String fullName = dirName;
		BaseRefNode refnode = new BaseRefNode();
		refnode.setId(fullName);
		TreeItem newdrefnodeTreeItem = null;
		// ������սڵ�
		LFWDirtoryTreeItem rootTreeItem = getPubPoolRefNodeTreeItem();
		if (fullName.indexOf(".") != -1) {
			String foldername = fullName.substring(0, fullName.indexOf("."));
			String leftfolder = fullName.substring(fullName.indexOf(".") + 1);
			while (leftfolder != null) {
				String newRootPath = rootTreeItem.getFile().getPath().replace(
						"��������", "web/pagemeta/public/refnodes");
				File newfile = new File(newRootPath, foldername);
				if (!newfile.exists() || !(newfile.isDirectory())) {
					newfile.mkdir();
				}
				LFWDirtoryTreeItem treeItem = null;
				TreeItem[] childTreeItems = rootTreeItem.getItems();
				for (int i = 0; i < childTreeItems.length; i++) {
					if (childTreeItems[i] instanceof LFWDirtoryTreeItem) {
						LFWDirtoryTreeItem child = (LFWDirtoryTreeItem) childTreeItems[i];
						if (child.getText().equals(foldername)) {
							treeItem = child;
							break;
						}
					}
				}
				if (treeItem == null) {
					treeItem = new LFWDirtoryTreeItem(rootTreeItem, newfile);
					treeItem.setType(LFWDirtoryTreeItem.POOLREFNODEFOLDER);
				}
				rootTreeItem = treeItem;
				if (leftfolder.indexOf(".") != -1) {
					foldername = leftfolder.substring(0, leftfolder
							.indexOf("."));
					leftfolder = leftfolder
							.substring(leftfolder.indexOf(".") + 1);

				} else {
					newdrefnodeTreeItem = new LFWRefNodeTreeItem(rootTreeItem,
							refnode, WEBProjConstants.COMPONENT_REFNODE);
					// new LFWRefNodeTreeItem(rootTreeItem, refnode,
					// leftfolder);
					leftfolder = null;
				}
			}
		} else
			newdrefnodeTreeItem = new LFWRefNodeTreeItem(rootTreeItem, refnode,
					WEBProjConstants.COMPONENT_REFNODE);
		// newdrefnodeTreeItem = new LFWRefNodeTreeItem(rootTreeItem,refnode,
		// refnode.getId());
		selTI.setExpanded(true);
		return newdrefnodeTreeItem;

		// Tree tree = treeView.getTree();
		// TreeItem[] selTIs = tree.getSelection();
		// if (selTIs == null || selTIs.length == 0)
		// throw new Exception("��ѡ��һ���ڵ�!");
		// TreeItem selTI = selTIs[0];
		// TreeItem origiranlTI = selTI;
		// File parentFile = ((ILFWTreeNode) selTI).getFile();
		// if (parentFile.isFile())
		// throw new Exception("�������ļ����½�Ŀ¼!");
		// TreeItem[] treeItems = selTI.getItems();
		// List<String> refNames = new ArrayList<String>();
		// for (int i = 0; i < treeItems.length; i++) {
		// TreeItem treeItem = treeItems[i];
		// if(treeItem instanceof LFWRefNodeTreeItem){
		// RefNodeConf ref = (RefNodeConf) treeItem.getData();
		// refNames.add(ref.getId());
		// }
		// }
		//		
		// String fullName = dirName;
		//		
		// if(selTI instanceof LFWDirtoryTreeItem){
		// LFWDirtoryTreeItem dir = (LFWDirtoryTreeItem) selTI;
		// if(!dir.getType().equals(LFWDirtoryTreeItem.PARENT_PUB_REF_FOLDER)){
		// fullName = selTI.getText() + "." + dirName;
		// while (selTI.getParentItem() != null && (selTI instanceof
		// LFWDirtoryTreeItem)) {
		// LFWDirtoryTreeItem parent =
		// (LFWDirtoryTreeItem)selTI.getParentItem();
		// if ((parent.getText().equals(NODEGROUP_CN) ||
		// parent.getText().equals(PUBLIC_REFNODE)))
		// break;
		// fullName = parent.getFile().getName() + "." + fullName;
		// selTI = parent;
		// }
		// }
		// }
		// if(refNames.contains(fullName))
		// throw new Exception("��Ŀ¼���Ѿ�������IDΪ" + dirName + "�Ĳ���!");
		// RefNode refnode = new RefNode();
		// refnode.setId(fullName);
		// LFWRefNodeTreeItem newTreeItem = new LFWRefNodeTreeItem(origiranlTI,
		// refnode, "[����]");
		// selTI.setExpanded(true);
		// return newTreeItem;
	}

	/**
	 * ����View - Control �ڵ����ӽڵ�
	 * @param dirName
	 * @param webComponent
	 * @param type
	 * @return
	 */
	public TreeItem addControlChildTreeNode(String dirName, WebComponent webComponent,String constantName, String type) throws Exception{
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0){
			throw new Exception("��ѡ��һ���ڵ�!");
		}
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile()){
			throw new Exception("�������ļ����½�Ŀ¼!");
		}
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(selTI);
		LfwWidget widget = widgetTreeItem.getWidget();
		WebComponent oriWebCom = widget.getViewComponents().getComponent(dirName);
		if (oriWebCom != null){
			throw new Exception("��Ƭ�����Ѿ�����IDΪ" + dirName + "���!");
		}
		webComponent.setId(dirName);
		LFWWebComponentTreeItem webCompTreeItem = new LFWWebComponentTreeItem(
				selTI, 
				LFWTool.getWEBProjConstantValue(constantName, ((LFWBasicTreeItem)selTI).getLfwVersion()), 
				webComponent);
		webCompTreeItem.setType(type);
		selTI.setExpanded(true);
		return webCompTreeItem;
	}
	
	/**
	 * ���ӽڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addGridTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new GridComp(), "COMPONENT_GRID", LFWWebComponentTreeItem.PARAM_GRID);
	}

	/**
	 * �½�excel���ڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addExcelTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new ExcelComp(), "COMPONENT_EXCEL", LFWWebComponentTreeItem.PARAM_EXCEL);
	}

	public TreeItem addButtonTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new ButtonComp(), "COMPONENT_BUTTON", LFWWebComponentTreeItem.PARAM_BUTTON);
	}

	public TreeItem addChartTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new ChartComp(), "COMPONENT_CHART", LFWWebComponentTreeItem.PARAM_CHART);
	}

	public TreeItem addSelfdefTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new SelfDefComp(), "COMPONENT_SELFDEFCOMP", LFWWebComponentTreeItem.PARAM_SELFDEF);
	}

	public TreeItem addProgressBarTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new ProgressBarComp(), "COMPONENT_PROGRESSBAR", LFWWebComponentTreeItem.PARAM_PROGRESSBAR);
	}

	public TreeItem addLabelTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new LabelComp(), "COMPONENT_LABEL", LFWWebComponentTreeItem.PARAM_LABEL);
	}

	public TreeItem addLinkTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new LinkComp(), "COMPONENT_LINKCOMP", LFWWebComponentTreeItem.PARAM_LINK);
	}

	public TreeItem addImageTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new ImageComp(), "COMPONENT_IMAGE", LFWWebComponentTreeItem.PARAM_IMAGE);
	}

	public TreeItem addImageButtonTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new ImageButtonComp(), "COMPONENT_IMAGE", LFWWebComponentTreeItem.PARAM_IMAGEBUTTON);
	}

	public TreeItem addIFrameTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new IFrameComp(), "COMPONENT_IFRAMECOMP", LFWWebComponentTreeItem.PARAM_IFRAME);
	}

	/**
	 * ����Form�ڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addFormTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new FormComp(), "COMPONENT_FORM", LFWWebComponentTreeItem.PARAM_FORM);
	}

	public TreeItem addToolbarTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new ToolBarComp(), "COMPONENT_TOOLBAR", LFWWebComponentTreeItem.PARAM_TOOLBAR);
	}

	public TreeItem addTextCompTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new TextComp(), "COMPONENT_TEXTCOMP", LFWWebComponentTreeItem.PARAM_TEXTCOMP);
	}

	/**
	 * �½�treen�ڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addTreeNode(String dirName) throws Exception {
		return addControlChildTreeNode(dirName, new TreeViewComp(), "COMPONENT_TREE", LFWWebComponentTreeItem.PARAM_TREE);
	}

	/**
	 * �½��������ݽڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addComboNode(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼!");
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(selTI);
		LfwWidget widget = widgetTreeItem.getWidget();
		ComboData oriComboData = widget.getViewModels().getComboData(dirName);
		if (oriComboData != null)
			throw new Exception("��Ƭ�����Ѿ�����IDΪ" + dirName + "��������!");
		ComboData staticcombo = new StaticComboData();
		staticcombo.setId(dirName);
		LFWComboTreeItem newTreeItem = new LFWComboTreeItem(selTI, staticcombo,
				LFWTool.getWEBProjConstantValue("COMPONENT_COMBODATA", ((LFWBasicTreeItem)selTI).getLfwVersion()));
		selTI.setExpanded(true);
		return newTreeItem;
	}

	/**
	 * ���Ӳ��սڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addRefNode(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼!");
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(selTI);
		LfwWidget widget = widgetTreeItem.getWidget();
		IRefNode originalrefnode = widget.getViewModels().getRefNode(dirName);
		if (originalrefnode != null)
			throw new Exception("��Ƭ�����Ѿ���������Ϊ" + dirName + "�Ĳ���!");
		SelfDefRefNode refnode = new SelfDefRefNode();
		refnode.setId(dirName);
		// �½�ʱ��refnode����Ĭ��pagemeta
//		refnode.setPagemeta(WEBProjConstants.REFNODE_PAGEMETA);
		LFWRefNodeTreeItem newTreeItem = new LFWRefNodeTreeItem(selTI, refnode,
				LFWTool.getWEBProjConstantValue("COMPONENT_REFNODE", ((LFWBasicTreeItem)selTI).getLfwVersion()));
		selTI.setExpanded(true);
		return newTreeItem;
	}

	/**
	 * ���Ӳ��չ�ϵ
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addRefNodeRelation(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Ŀ¼!");
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(selTI);
		LfwWidget widget = widgetTreeItem.getWidget();
		if (widget.getViewModels().getRefNodeRelations() == null) {
			RefNodeRelations refs = new RefNodeRelations();
			widget.getViewModels().setRefnodeRelations(refs);
		}
		RefNodeRelation refnoderel = widget.getViewModels()
				.getRefNodeRelations().getRefNodeRelation(dirName);
		if (refnoderel != null) {
			throw new Exception("��Ƭ�����Ѿ���������Ϊ" + dirName + "�Ĳ���!");
		}
		RefNodeRelation refNodeRel = new RefNodeRelation();
		refNodeRel.setId(dirName);
		LFWRefNodeRelTreeItem newTreeItem = new LFWRefNodeRelTreeItem(selTI, refNodeRel, 
				LFWTool.getWEBProjConstantValue("COMPONENT_REFNODERELATION", ((LFWBasicTreeItem)selTI).getLfwVersion()));
		selTI.setExpanded(true);
		return newTreeItem;
	}

	/**
	 * ����widgets���ڵ�
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public TreeItem addWidgetsTreeNode(String dirName) throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();// (File)
		// selTI.getData();
		if (parentFile.isFile())
			throw new Exception("�������ļ����½�Widgets!");
		File f = new File(parentFile, dirName);
		if (!f.exists()) {
			f.mkdirs();
		}
		selTI.setExpanded(true);
		// return ti;
		return null;
	}

	public void deleteSelectedTreeNode() throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		if (selTI instanceof ILFWTreeNode) {
			Shell shell = new Shell(Display.getCurrent());
			if (!MessageDialog.openConfirm(shell, "ȷ��", "ȷʵҪɾ��"
					+ selTI.getText() + "��"))
				return;
			if (selTI instanceof LFWPageMetaTreeItem) {
				LFWPageMetaTreeItem pmTreeItem = (LFWPageMetaTreeItem) selTI;
				PageMeta pm = (PageMeta) pmTreeItem.getPm();
				if (pm == null)
					return;
//				if (pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE) != null
//						&& pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE)
//								.getValue() != null) {
//					String funnode = (String) pm.getExtendAttribute(
//							ExtAttrConstants.FUNC_CODE).getValue();
//					NCConnector.deleteFunNode(funnode);
//				}
			} else if (selTI instanceof LFWVirtualDirTreeItem) {
				TreeItem[] treeItems = selTI.getItems();
				if (treeItems != null && treeItems.length > 0) {
					MessageDialog.openError(null, "��ʾ", "������Ŀ¼�´���������Ϣ,����ɾ��!");
					return;
				}
			}
			((ILFWTreeNode) selTI).deleteNode();
		}
		closeOpenedEidtor(selTI);

	}

	/**
	 * ɾ��ѡ��ds
	 * 
	 * @throws Exception
	 */
	public void deleteSelectedDsNode() throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		if (selTI instanceof LFWDSTreeItem) {
			Shell shell = new Shell(Display.getCurrent());
			if (!MessageDialog.openConfirm(shell, "ȷ��", "ȷʵҪɾ��"
					+ selTI.getText() + "��"))
				return;
			// closeOpenedEidtor(selTI);
			((LFWDSTreeItem) selTI).deleteNode((LFWDSTreeItem) selTI);
		}
		closeOpenedEidtor(selTI);
	}

	protected void closeOpenedEidtor(TreeItem treeItem) {
		// ɾ���ڵ��رձ༭��
		IWorkbenchPage page = getViewSite().getPage();
		IEditorInput input = ((LFWBasicTreeItem) treeItem).getEditorInput();
		if (null != input) {
			IEditorPart editor = page.findEditor(input);
			if (null != editor) {
				page.closeEditor(editor, false);
			}
		}
	}

	/**
	 * ɾ������ʱ���ӵĽڵ�
	 * 
	 * @throws Exception
	 */
	public void deleteNewNode() throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("û�п���ɾ���Ľڵ�!");
		TreeItem selTI = selTIs[0];
		closeOpenedEidtor(selTI);
		selTI.dispose();
	}

	/**
	 * ɾ��ѡ��node
	 * 
	 * @throws Exception
	 */
	public void deleteSelectedWebComponentNode() throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ㡣");
		TreeItem selTI = selTIs[0];
		if (selTI instanceof LFWWebComponentTreeItem) {
			// if(lfwbasic.getType().equals(LFWWebComponentTreeItem.PARAM_GRID)){
			Shell shell = new Shell(Display.getCurrent());
			if (!MessageDialog.openConfirm(shell, "ȷ��", "ȷʵҪɾ��"
					+ selTI.getText() + "��"))
				return;
			// closeOpenedEidtor(selTI);
			((LFWWebComponentTreeItem) selTI)
					.deleteNode((LFWWebComponentTreeItem) selTI);
			// }
		}
		closeOpenedEidtor(selTI);

	}

	public void deleteSelectedRefNode() throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		if (selTI instanceof LFWRefNodeTreeItem) {
			// LFWRefNodeTreeItem refTreeItem = (LFWRefNodeTreeItem)selTI;
			Shell shell = new Shell(Display.getCurrent());
			if (!MessageDialog.openConfirm(shell, "ȷ��", "ȷʵҪɾ��"
					+ selTI.getText() + "��"))
				return;
			((LFWRefNodeTreeItem) selTI).deleteNode((LFWRefNodeTreeItem) selTI);
			// }
		}
		closeOpenedEidtor(selTI);
	}

	public void deleteSelectedRefNodeRel() throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		if (selTI instanceof LFWRefNodeRelTreeItem) {
			// LFWRefNodeTreeItem refTreeItem = (LFWRefNodeTreeItem)selTI;
			Shell shell = new Shell(Display.getCurrent());
			if (!MessageDialog.openConfirm(shell, "ȷ��", "ȷʵҪɾ��"
					+ selTI.getText() + "��"))
				return;
			((LFWRefNodeRelTreeItem) selTI)
					.deleteNode((LFWRefNodeRelTreeItem) selTI);
			// }
		}
		closeOpenedEidtor(selTI);
	}

	public void deleteSelectedComboNode() throws Exception {
		Tree tree = treeView.getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0)
			throw new Exception("��ѡ��һ���ڵ�!");
		TreeItem selTI = selTIs[0];
		if (selTI instanceof LFWComboTreeItem) {
			// LFWComboTreeItem comboTreeItem = (LFWComboTreeItem)selTI;
			Shell shell = new Shell(Display.getCurrent());
			if (!MessageDialog.openConfirm(shell, "ȷ��", "ȷʵҪɾ��"
					+ selTI.getText() + "��"))
				return;
			((LFWComboTreeItem) selTI).deleteNode((LFWComboTreeItem) selTI);
			// }
		}
		closeOpenedEidtor(selTI);
	}

	protected void initTree() {
		LFWExplorerTreeBuilder.getInstance().buildTree(treeView.getTree());
	}

	public void refreshTree() {
		Tree tree = treeView.getTree();
		tree.removeAll();
		initTree();
	}

	public Tree getExplorerTree() {
		return treeView.getTree();
	}

	private static final String LFW_LABEL = "LFWģ����ͼ";
	private static final String PORTAL_LABEL = "Portalģ����ͼ";
	
	public static LFWExplorerTreeView getLFWExploerTreeView(IWorkbenchPage page) {
		if (page == null) {
			if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage() != null) {
				page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage();
			}
		}
		if (page != null) {
			LFWExplorerTreeView view = (LFWExplorerTreeView) page
					.findView(LFWExplorerTreeView.class.getCanonicalName());
			// ȡ�̳��� LFWExplorerTreeView ��view
			if (view == null && LFWExplorerTreeView.EXTEND_VIEWID != null) {
				view = (LFWExplorerTreeView) page
						.findView(LFWExplorerTreeView.EXTEND_VIEWID);
			}
			if (view == null) {
				if(LFW_LABEL.equals(page.getLabel()) || PORTAL_LABEL.equals(page.getLabel())){
					try {
						view = (LFWExplorerTreeView) page
								.showView(LFWExplorerTreeView.class
										.getCanonicalName());
					} catch (PartInitException e) {
						// e.printStackTrace();
					}
				}
			}
			return view;
		} else
			return null;
	}

	public TreeViewer getTreeView() {
		return treeView;
	}

	public Map<String, String> getEditoridMap() {
		return editoridMap;
	}
}
