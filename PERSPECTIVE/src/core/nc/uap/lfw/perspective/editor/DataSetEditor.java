package nc.uap.lfw.perspective.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.Connection;
import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.FieldRelations;
import nc.uap.lfw.core.data.IRefDataset;
import nc.uap.lfw.core.data.MdDataset;
import nc.uap.lfw.core.data.PubDataset;
import nc.uap.lfw.core.data.RefMdDataset;
import nc.uap.lfw.core.data.RefPubDataset;
import nc.uap.lfw.core.event.conf.DatasetListener;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.dataset.ImportDsFromDBAction;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.factory.ElementEidtPartFactory;
import nc.uap.lfw.palette.ChildConnection;
import nc.uap.lfw.palette.PaletteFactory;
import nc.uap.lfw.parts.LFWElementPart;
import nc.uap.lfw.perspective.action.GeneratorRefNodeAction;
import nc.uap.lfw.perspective.action.GeneratorVObyDsAction;
import nc.uap.lfw.perspective.action.NewDsFormMDAction;
import nc.uap.lfw.perspective.action.NewDsFromPoolAction;
import nc.uap.lfw.perspective.listener.ListenerElementObj;
import nc.uap.lfw.perspective.model.DatasetElementObj;
import nc.uap.lfw.perspective.model.DatasetGraph;
import nc.uap.lfw.perspective.model.RefDatasetElementObj;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.views.LFWViewPage;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWComboTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDSTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWProjectTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWRefNodeTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.commands.Command;
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
 * dataset�༭��
 * @author zhangxya 
 *
 */
public class DataSetEditor extends LFWBaseEditor {
	
	private DatasetGraph graph = new DatasetGraph();
	private TableViewer tv = null;
	private	Dataset ds = null;
	private DatasetElementObj dsElementObj = null;
	private boolean isPoolds = false;
	private PaletteRoot paleteRoot = null;

	public boolean isPoolds() {
		return isPoolds;
	}

	public void setPoolds(boolean isPoolds) {
		this.isPoolds = isPoolds;
	}

	public DatasetGraph getGraph() {
		return graph;
	}

	public void setGraph(DatasetGraph graph) {
		this.graph = graph;
	}

	public DataSetEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}
	public TableViewer getTv() {
		return tv;
	}
	
	public void executComand(Command cmd){
		super.getCommandStack().execute(cmd);
	}


	
	public boolean isDirty() {
		if (super.isDirty())
			return true;
		return getCommandStack().isDirty();
	}
	
	
	public void commandStackChanged(EventObject arg0) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(arg0);
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}
	
	/**
	 * ��һ������ʱ��û�б��棬��ɾ���ڵ�
	 */
	public void  deleteNode(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		IEditorInput input = getEditorInput();
		DataSetEitorInput dsEditor = (DataSetEitorInput)input;
		Dataset ds = (Dataset)dsEditor.getCloneElement();
		widget = LFWPersTool.getCurrentWidget();
		if(widget != null){
			if(widget.getViewModels().getDataset(ds.getId()) == null){
				try {
					view.deleteNewNode();
				} catch (Exception e) {
					MainPlugin.getDefault().logError(e.getMessage(), e);
				}
			 }
		}else{
			
		}
	 }
	
	public void refreshTreeItemText(Dataset ds){
		//IEditorInput input = getEditorInput();
		TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
		if(treeItem instanceof LFWDSTreeItem){
			LFWDSTreeItem dsTreeItem = (LFWDSTreeItem) treeItem;
			dsTreeItem.setText(ds.getId() + "[" + ds.getCaption() + "]");
		}
//		if(input instanceof ElementEditorInput){
//			//WebElement webElement = eleEditorInput.getCloneElement();
//			Tree tree = LFWPersTool.getTree();
//			TreeItem treeItem = tree.getSelection()[0];
//			if(treeItem instanceof LFWDSTreeItem){
//				LFWDSTreeItem dsTreeItem = (LFWDSTreeItem) treeItem;
//				dsTreeItem.setText(ds.getId() + "[" + ds.getCaption() + "]");
//			}
//		}
	}
	
	public void setFocus() {
		super.setFocus();
		Tree tree = LFWPersTool.getTree();
		IEditorInput input = getEditorInput();
		DataSetEitorInput dsEditorInput = (DataSetEitorInput)input;
		Dataset ds = (Dataset) dsEditorInput.getCloneElement();
		LFWWidgetTreeItem widgetTreeItem = getWidgetTreeItem();
		//widget�µ����ݼ�
		if(widgetTreeItem != null){
			LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(WEBProjConstants.DATASET);
			TreeItem[] childTreeItems = lfwSeparaTreeItem.getItems();
			for (int i = 0; i < childTreeItems.length; i++) {
				LFWDSTreeItem dsT = (LFWDSTreeItem) childTreeItems[i];
				if(dsT.getText().indexOf("[") != -1){
					if(dsT.getText().substring(0,dsT.getText().indexOf("[")).equals(ds.getId())){
						tree.setSelection(dsT);
						break;
					}
				}
				else{
					if(dsT.getText().equals(ds.getId())){
						tree.setSelection(dsT);
						break;
					}
				}
			}
		}
		else{
			//�������ݼ�
			TreeItem[] treeItems = tree.getItems();
			IProject project = LFWPersTool.getCurrentProject();
			LFWProjectTreeItem projectTreeItem = null;
			for (int i = 0; i < treeItems.length; i++) {
				LFWProjectTreeItem projectItem = (LFWProjectTreeItem) treeItems[i];
				if(projectItem.getProjectModel().getJavaProject().equals(project)){
					projectTreeItem = projectItem;
					break;
				}
			}
			LFWDirtoryTreeItem pubDsNode = null;
			
			LFWDirtoryTreeItem nodeTreeItem = null;
			LFWBusinessCompnentTreeItem busiCompTreeItem = null;
			if(LFWPersTool.isBCPProject(project)){
				TreeItem currComp = LFWPersTool.getCurrentBusiCompTreeItem();
				if(currComp != null){
					TreeItem[] compTreeItems = projectTreeItem.getItems();
					for (int i = 0; i < compTreeItems.length; i++) {
						TreeItem treeItem = compTreeItems[i];
						if(treeItem.getText().equals(currComp.getText())){
							busiCompTreeItem = (LFWBusinessCompnentTreeItem) treeItem;
							break;
						}
					}
				}
			}
			TreeItem[] nodesTreeItems = null;
			if(busiCompTreeItem != null)
				nodesTreeItems = busiCompTreeItem.getItems();
			else
				nodesTreeItems = projectTreeItem.getItems();
			for (int i = 0; i < nodesTreeItems.length; i++) {
				TreeItem treeItem = nodesTreeItems[i];
				if(treeItem.getText().equals(LFWTool.getWEBProjConstantValue("PUBLIC_DATASET", ((LFWBasicTreeItem)treeItem).getLfwVersion()))){
					nodeTreeItem = (LFWDirtoryTreeItem) treeItem;
					pubDsNode = (LFWDirtoryTreeItem) treeItem;
					break;
				}
			}
			LFWDSTreeItem selDsTreeItem = null;
			String dsId = ds.getId();
			if(dsId.indexOf(".") != -1){
				String firstFolder = dsId.substring(0, dsId.indexOf("."));
				String leftfolder = dsId.substring(dsId.indexOf(".") + 1);
				
				while(nodeTreeItem != null){
					TreeItem[] childTreeItems = nodeTreeItem.getItems();
					for (int i = 0; i < childTreeItems.length; i++) {
						if(nodeTreeItem == null)
							break;
						TreeItem childTreeItem = childTreeItems[i];
						if(childTreeItem instanceof LFWDirtoryTreeItem && childTreeItem.getText().equals(firstFolder)){
							if(leftfolder.indexOf(".") != -1){
								firstFolder = leftfolder.substring(0, leftfolder.indexOf("."));
								leftfolder = leftfolder.substring(leftfolder.indexOf(".")+ 1);
							}
							nodeTreeItem = (LFWDirtoryTreeItem) childTreeItem;
							break;
						}
						else {
							TreeItem[] dsTreeItems = nodeTreeItem.getItems();
							for (int j = 0; j < dsTreeItems.length; j++) {
								TreeItem dsTreeItem = dsTreeItems[j];
								if(dsTreeItem instanceof LFWDSTreeItem){
									if(dsTreeItem.getText().equals(leftfolder)){
										selDsTreeItem = (LFWDSTreeItem) dsTreeItem;
										nodeTreeItem = null;
										break;
									}
								}
							}
						}
					}
				}
			}
			else{
				TreeItem[] dsTreeItems = nodeTreeItem.getItems();
				for (int j = 0; j < dsTreeItems.length; j++) {
					TreeItem dsTreeItem = dsTreeItems[j];
					if(dsTreeItem instanceof LFWDSTreeItem){
						if(dsTreeItem.getText().equals(dsId)){
							selDsTreeItem = (LFWDSTreeItem) dsTreeItem;
							break;
						}
					}
				}
			}
			if(selDsTreeItem != null){
				tree.setSelection(selDsTreeItem);
			}
			else
				tree.setSelection(pubDsNode);
		}
	}
		
	 public String getPath(){
		if(widgeTreeItem != null){
			File file = widgeTreeItem.getFile();
			String path = file.getPath() + "/widget.wd";
			return path;
		}
		else
			return null;
	}
	
	//����
	public boolean save(){
		//����ļ�û��checkout,����checkout�ļ�
//		if(!isDirty())
//			return true;
		DatasetElementObj dsobj = (DatasetElementObj) this.graph.getCells().get(0);
		//���ds��Id�����˸ı䣬��Ҫ�ı���Ӧ������
		Dataset dsnewds = dsobj.getDs();
		IEditorInput input = getEditorInput();
		DataSetEitorInput dsEditorInput = (DataSetEitorInput)input;
		ds = (Dataset) dsEditorInput.getCloneElement();
		dsnewds.setListenerMap(ds.getListenerMap());
		RefPubDataset refpubds  = null;
		RefMdDataset refmdds = null;
		if(ds instanceof IRefDataset){
			if(dsnewds instanceof PubDataset){
				refpubds = new RefPubDataset();
				refpubds.setFieldSet(dsnewds.getFieldSet());
				refpubds.setRefId(((PubDataset) dsnewds).getRefId());
				refpubds.setId(ds.getId());
				dsobj.setDs(refpubds);
			}
			if(dsnewds instanceof MdDataset){
				refmdds = new RefMdDataset();
				refmdds.setFieldSet(dsnewds.getFieldSet());
				refmdds.setObjMeta(((MdDataset) dsnewds).getObjMeta());
				refmdds.setId(ds.getId());
				dsobj.setDs(refmdds);
			}
		}else{
			dsobj.setDs(dsnewds);
		}
		if(dsnewds.getFrom() != null)
			dsnewds.setConfType(WebElement.CONF_REF);
		if(widget == null)
			widget = LFWPersTool.getCurrentWidget();
		if(widget != null){
			Dataset oldds = widget.getViewModels().getDataset(dsobj.getOrginaldsId());
			if(oldds != null){
				widget.getViewModels().removeDataset(dsobj.getOrginaldsId());
				RemoveOriginalCombdatas(dsobj.getOrginaldsId());
				RemoveOriginalRefNodes(dsobj.getOrginaldsId());
			}
			if(ds instanceof IRefDataset){
				if(dsnewds instanceof PubDataset){
					widget.getViewModels().addDataset(refpubds);
				}else if(dsnewds instanceof MdDataset){
					widget.getViewModels().addDataset(refmdds); 
				}
			}
			else
				widget.getViewModels().addDataset(dsnewds);
			Dataset currentDS = widget.getViewModels().getDataset(ds.getId());
			boolean isAddMethod = false;
			if(currentDS != null && !currentDS.isLazyLoad()){
				EventConf[] ecs = currentDS.getEventConfs();
				isAddMethod = true;
				if(ecs != null && ecs.length > 0){
					for(EventConf ec : ecs){
						if(ec.getName().equals(DatasetListener.ON_DATA_LOAD)){
							isAddMethod = false;
							break;
						}
					}
				}
			}
			if(isAddMethod){
				if(widget != null && widget.getControllerClazz() != null && widget.getControllerClazz().trim().length() > 0){
					EventConf eventConf = null;
					List<EventConf> ecList = LFWTool.getAcceptEventList(getAcceptEventDescs());
					for(EventConf ec : ecList){
						if(DatasetListener.ON_DATA_LOAD.equals(ec.getName())){
							eventConf = (EventConf)ec.clone();
							break;
						}
					}
					eventConf.setMethodName(DatasetListener.ON_DATA_LOAD + "_" + ds.getId());
					String projectPath = LFWAMCPersTool.getLFWProjectPath();
					int index = widget.getControllerClazz().lastIndexOf(".");
					String packageName = null;
					if(index > 0){
						packageName = widget.getControllerClazz().substring(0, index);
					}else{
						packageName = "";
					}
					String className = widget.getControllerClazz().substring(index + 1);
					String classFilePath = projectPath + File.separator + widget.getSourcePackage() + packageName.replaceAll("\\.", "/");
					String classFileName = className + ".java";
					eventConf.setClassFilePath(classFilePath);
					eventConf.setClassFileName(classFileName);
					eventConf.setEventStatus(EventConf.ADD_STATUS);
					currentDS.addEventConf(eventConf);
					LFWSaveElementTool.updateView(widget);
				}
			}else{
				LFWPersTool.saveWidget(widget);
			}
			getCommandStack().markSaveLocation();
			//������ߵ����ڵ�
			refreshTreeItem(dsnewds);
			if(dsobj.getExtendFlag() != null && dsobj.getExtendFlag()){
				addExtendedCombdatas();
				addExtendedRefDs();
				addExtendedRefNode();
			}
			return true;
		}
		//�������е�ds����
		else{
			String projectPath = LFWPersTool.getProjectPath();
			TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
			String directory = treeItem.getText();
			TreeItem parent = (TreeItem)treeItem.getParentItem();
			while (parent != null && parent instanceof LFWDirtoryTreeItem) {
				if (parent.getText().equals(LFWTool.getWEBProjConstantValue("PUBLIC_DATASET", LFWTool.OLD_VERSION)))
					break;
				directory = ((LFWDirtoryTreeItem)parent).getFile().getName() + "/" + directory;
				//treeItem = parent;
				parent = (TreeItem)parent.getParentItem();
			}
			
			if(LFWPersTool.isBCPProject(LFWPersTool.getCurrentProject())){
				LFWBusinessCompnentTreeItem busiCompnent = LFWPersTool.getCurrentBusiCompTreeItem();
				projectPath += "/" + busiCompnent.getText().substring(busiCompnent.getText().indexOf(LFWTool.getWEBProjConstantValue("BUSINESSCOMPONENT", LFWTool.OLD_VERSION)) + 
						LFWTool.getWEBProjConstantValue("BUSINESSCOMPONENT", LFWTool.OLD_VERSION).length() + 1, busiCompnent.getText().length() -1 );
			}
			
			String filePath = projectPath + "/web/pagemeta/public/dspool";
			if(directory.lastIndexOf("/") != -1){
				String folder = directory.substring(0, directory.lastIndexOf("/"));
				filePath = projectPath + "/web/pagemeta/public/dspool/" + folder;
			}
			String fileName = treeItem.getText() + ".xml";
			LFWPersTool.getCurrentProject().getLocation();
			String rootPath = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
			LFWConnector.saveDatasettoXml("/" + rootPath, filePath, fileName, dsnewds);
			refreshTreeItem(dsnewds);
			getCommandStack().markSaveLocation();
			return true;
		}
		
	}
	
	/**
	 *  ɾ��ԭ����combodata
	 * @param id
	 */
	public void RemoveOriginalCombdatas(String id){
		//�����������ݼ�
		LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(LFWTool.getWEBProjConstantValue("COMBODATA", getWidgetTreeItem().getLfwVersion()));
		if(lfwSeparaTreeItem != null){
			TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
			for (int i = 0; i < treeItems.length; i++) {
				ComboData combo = (ComboData) treeItems[i].getData();
				if(combo.getId().startsWith("combo_" + id))
					treeItems[i].dispose();
			}
		}
	}
	
	
	/**
	 * ɾ��ԭ����refnodes
	 * @param id
	 */
	public void RemoveOriginalRefNodes(String id){
		LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(LFWTool.getWEBProjConstantValue("REFNODE", getWidgetTreeItem().getLfwVersion()));
		if(lfwSeparaTreeItem != null){
			TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
			for (int i = 0; i < treeItems.length; i++) {
				if(treeItems[i].getData() instanceof IRefNode){
					IRefNode refnode = (IRefNode) treeItems[i].getData();
					if(refnode.getId().startsWith("refnode_" + id))
						treeItems[i].dispose();
				}
			}
		}
	}
	
	
	/**
	 * ��NC����ds��ʱ�򣬿��������µ�����ds�������򣬲��սڵ�����
	 * @param widget
	 */
	public void addExtendedCombdatas(){
		//�����������ݼ�
		//Map<String, ComboData>  combodataMap = widget.getViewModels().getComboDataMap();
		if(widget != null){
			ComboData[] comboDatas = widget.getViewModels().getComboDatas();
			LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(LFWTool.getWEBProjConstantValue("COMBODATA", getWidgetTreeItem().getLfwVersion()));
			if(lfwSeparaTreeItem != null){
				TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
				List<String> list = new ArrayList<String>();
				if(treeItems != null && treeItems.length > 0){
					for (int i = 0; i < treeItems.length; i++) {
						ComboData combo = (ComboData) treeItems[i].getData();
						list.add(combo.getId());
					}
				}
				if(comboDatas != null && comboDatas.length > 0){
					for (int i = 0; i < comboDatas.length; i++) {
						if(!list.contains(comboDatas[i].getId())){
							ComboData comboData  = comboDatas[i];
							new LFWComboTreeItem(lfwSeparaTreeItem, comboData, LFWTool.getWEBProjConstantValue("COMPONENT_COMBODATA", getWidgetTreeItem().getLfwVersion()));
						}
					}
				}
			}
		}
	}
	
	//����refds
	public void addExtendedRefDs(){
		LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(LFWTool.getWEBProjConstantValue("DATASET", getWidgetTreeItem().getLfwVersion()));
		TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < treeItems.length; i++) {
			Dataset dataset = (Dataset) treeItems[i].getData();
			list.add(dataset.getId());
		}
		Dataset[] datas = widget.getViewModels().getDatasets();
		for (int i = 0; i < datas.length; i++) {
			if(!list.contains(datas[i].getId())){
				Dataset dsData  = datas[i];
				new LFWDSTreeItem(lfwSeparaTreeItem, dsData, dsData.getId());
			}
			else{
				for (int j = 0; j < treeItems.length; j++) {
					Dataset ds = (Dataset) treeItems[j].getData();
					if(datas[i].getId().equals(ds.getId())){
						treeItems[j].setData(datas[i]);
						break;
					}
				}
			}
		}
	}
	
	//���սڵ�
	public void addExtendedRefNode(){
		LFWSeparateTreeItem lfwSeparaTreeItem = getWebSeparateTreeItem(LFWTool.getWEBProjConstantValue("REFNODE", getWidgetTreeItem().getLfwVersion()));
		TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < treeItems.length; i++) {
			if(treeItems[i].getData() instanceof IRefNode){
				IRefNode refnode  = (IRefNode) treeItems[i].getData();
				list.add(refnode.getId());
			}
		}
		
		IRefNode[] refnodes = widget.getViewModels().getRefNodes();
		for (int i = 0; i < refnodes.length; i++) {
			if(!list.contains(refnodes[i].getId())){
				IRefNode ref  = refnodes[i];
				new LFWRefNodeTreeItem(lfwSeparaTreeItem, ref, LFWTool.getWEBProjConstantValue("COMPONENT_REFNODE", getWidgetTreeItem().getLfwVersion()));
			}
		}
	}

	//�ı�dsId��Ӧ������
//	private void doOtherChange(String originalId, String newId){
//		Map<String, WebComponent>  webComponents = widget.getViewComponents().getComponentsMap();
//		for (Iterator<String> itwd = webComponents.keySet().iterator(); itwd.hasNext();) {
//			String webcomId = itwd.next();
//			//grid�ı�
//			if(webComponents.get(webcomId) instanceof GridComp){
//				GridComp oldgrid  = (GridComp)webComponents.get(webcomId);
//				if(oldgrid.getDataset() != null && oldgrid.getDataset().equals(originalId)){
//					oldgrid.setDataset(newId);
//				}
//			}
//			//form�ı�
//			else if(webComponents.get(webcomId) instanceof FormComp){
//				FormComp oldform  = (FormComp)webComponents.get(webcomId);
//				if(oldform.getDataset() != null && oldform.getDataset().equals(originalId)){
//					oldform.setDataset(newId);
//				}
//			}
//			//tree�ı�
//			else if(webComponents.get(webcomId) instanceof TreeViewComp){
//				TreeViewComp oldtree  = (TreeViewComp)webComponents.get(webcomId);
//				TreeLevel toptreeLevel = oldtree.getTopLevel();
//				if(toptreeLevel != null){
//					//toplevel�ı�
//					if(toptreeLevel.getDataset().equals(originalId)){
//						toptreeLevel.setDataset(newId);
//					}
//					//��treeleve�ı�
//					while(toptreeLevel.getChildTreeLevel() != null){
//						TreeLevel childlevel = toptreeLevel.getChildTreeLevel();
//						if(childlevel.getDataset().equals(originalId))
//							childlevel.setDataset(newId);
//						toptreeLevel = childlevel;
//					}
//				}
//			}
//		}
//		//���սڵ��writeds��readds�ı�
//		RefNode[] refnodes = widget.getViewModels().getRefNodes();
//		for (int i = 0; i < refnodes.length; i++) {
//			//String refnodeid = (String) itrf.next();
//			 RefNode refnode = refnodes[i];
//			 if(refnode.getWriteDs() != null && refnode.getWriteDs().equals(originalId))
//				 refnode.setWriteDs(newId);
//			 if(refnode.getWriteDs() != null && refnode.getReadDs().equals(originalId))
//				 refnode.setReadDs(newId);
//		}
//		 //dsrelation�ı�
//		DatasetRelations dsRels = widget.getViewModels().getDsrelations();
//		if(dsRels != null && dsRels.getRelationList().size() > 0){
//			List<DatasetRelation> dsrellist = dsRels.getRelationList();
//			int size = dsrellist.size();
//			for (int i = 0; i < size; i++) {
//				DatasetRelation dsrelation =  dsrellist.get(i);
//				if(dsrelation.getMasterDataset() != null && dsrelation.getMasterDataset().equals(originalId))
//					dsrelation.setMasterDataset(newId);
//				if(dsrelation.getDetailDataset() != null && dsrelation.getDetailDataset().equals(originalId))
//					dsrelation.setDetailDataset(newId);
//			}
//		}
//		//fieldrealition�ı�
//		Dataset[] datasets = widget.getViewModels().getDatasets();
//		for (int i = 0; i < datasets.length; i++) {
//			Dataset ds = datasets[i];
//			FieldRelation[] frs = ds.getFieldRelations().getFieldRelations();
//			if(frs != null){
//				for (int j = 0; j < frs.length; j++) {
//					FieldRelation parent = frs[j];
//					if(parent.getRefDataset() != null && parent.getRefDataset().equals(originalId))
//						parent.setRefDataset(newId);
//					doChildRelationChange(parent, originalId, newId);
//				}
//			}
//		}
//	}
	
	public void doChildRelationChange(FieldRelation parent, String originalId, String newId){
		List<FieldRelation> childlist = parent.getChildRelationList();
		if(childlist != null && childlist.size() > 0){
			int size = childlist.size();
			for (int j = 0; j < size; j++) {
				FieldRelation child =  childlist.get(j);
				if(child.getRefDataset() != null && child.getRefDataset().equals(originalId)){
					child.setRefDataset(newId);
					doChildRelationChange(child, originalId, newId);
				}
			}
		}
	}
	
	public static DataSetEditor getActiveEditor(){
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if(page != null){
			editor = page.getActiveEditor();
		}
		if(editor != null && editor instanceof DataSetEditor){
			return (DataSetEditor)editor;
		}else {
			return null;
		}
		
	}
	
	Object data = null;

	public void setData(Dataset ds){
		this.ds = ds;
		
	}
	public Dataset getData(){
		return ds;
	}
	
	private LFWWidgetTreeItem widgeTreeItem = null;
	private LfwWidget widget = null;
	
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		DataSetEitorInput dsEditor = (DataSetEitorInput)input;
		Dataset ds = (Dataset)dsEditor.getCloneElement();
		DatasetElementObj dsElement = new DatasetElementObj(ds.getId());
		dsElement.setDs(ds);
		setData(ds);
		dsElement.setLocation(new Point(100,100));
		graph.addCell(dsElement);
		this.dsElementObj = dsElement;
				
		widgeTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		if(widgeTreeItem != null){
			widget = (LfwWidget)widgeTreeItem.getWidget();
			FieldRelations fieldrelations = ds.getFieldRelations();
			int  parenty = 0;
			if(fieldrelations != null){
				FieldRelation[] fieldrelas = fieldrelations.getFieldRelations();
				if(fieldrelas != null){
				for (int i = 0; i < fieldrelas.length; i++) {
					FieldRelation fr = fieldrelas[i];
					Dataset refds = widget.getViewModels().getDataset(fr.getRefDataset());
					RefDatasetElementObj refdsobj = new RefDatasetElementObj();
					refdsobj.setRefFieldRelation(fr);
					refdsobj.setDs(refds);
					dsElement.addRefDataset(refdsobj);
					refdsobj.setDsobj(dsElement);
					int pointy = 0;
					refdsobj.setSize(new Dimension(120, 120));
					if(i == 0)
						pointy = 0;
					else 
						pointy = parenty + 100 + WEBProjConstants.BETWEEN;
					parenty = pointy;
					Point point = new Point(400, pointy);
					refdsobj.setLocation(point);
					graph.addRefDs(refdsobj);
					Connection con = new Connection(dsElement, refdsobj);
					con.connect();
					dealFieldRelation(refdsobj,  fieldrelas[i]);
					}
				}
			}
			if(!(ds instanceof IRefDataset)){
				List<ListenerElementObj> listener = graph.getJsListeners();
				if(listener == null || listener.size()== 0){
					// ����Listenerͼ��
					Map<String, JsListenerConf> listenerMap = ds.getListenerMap();
					for (Iterator<String> itwd = listenerMap.keySet().iterator(); itwd.hasNext();) {
						String listerId = (String) itwd.next();
						JsListenerConf listenerConf = listenerMap.get(listerId);
						//��Ԫ������������listener�������Ѿ�ɾ��
						if(listenerConf.getFrom() != null && listenerConf.getConfType() != null && listenerConf.getConfType().equals(WebElement.CONF_DEL)){
							listenerMap.remove(listerId);
						}
					}
					addListenerCellToEditor(listenerMap, graph);
				}
			}
		}
		else {
			dsElementObj.setPoolds(true);
			setPoolds(true);
		}
	}
	

	//ѭ������FieldRelation
	private void dealFieldRelation(RefDatasetElementObj refdsobj, FieldRelation fieldrelation){
		List<FieldRelation>  childrelationlist = fieldrelation.getChildRelationList();
		if(childrelationlist != null){
			for (int j = 0; j < childrelationlist.size(); j++) {
				Point refpoint = refdsobj.getLocation();
				Dimension refsize = refdsobj.getSize();
				FieldRelation childrefrelation = childrelationlist.get(j);
				String childrefid = childrefrelation.getRefDatasetid();
				Dataset childrefds = widget.getViewModels().getDataset(childrefid);
				RefDatasetElementObj childrefobj = new RefDatasetElementObj();
				childrefobj.setDs(childrefds);
				refdsobj.addChild(childrefobj);
				childrefobj.setSize(new Dimension(100,100));
				childrefobj.setLocation(new Point((refpoint.x + refsize.width + 100),(refpoint.y + 200*j)));
				graph.addRefDs(childrefobj);
				ChildConnection childcon = new ChildConnection(refdsobj, childrefobj);
				childcon.connect();
				List<FieldRelation> chdchachildfrs = childrefrelation.getChildRelationList();
				if(chdchachildfrs != null && chdchachildfrs.size() > 0)
					dealFieldRelation(refdsobj, childrefrelation);
			}
		}
	}
	
	
	public DatasetElementObj getModel(){
		return this.dsElementObj;
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
	private KeyHandler shareKeyHandler = null;
	
	private KeyHandler getShareKeyHandler() {
		if(shareKeyHandler == null){
			shareKeyHandler = new KeyHandler();
			shareKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127,0), 
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
	
	/**
	 * ���ӱ༭���Զ���˵�
	 * @param manager
	 */
	//todo
	protected void editMenuManager(IMenuManager manager) {
		if (null != getCurrentSelection()) {
			StructuredSelection ss = (StructuredSelection) getCurrentSelection();
			Object sel = ss.getFirstElement();
			if (sel instanceof LFWElementPart) {
				LFWElementPart lfwEle = (LFWElementPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof DatasetElementObj) {
					DatasetElementObj dsObj = (DatasetElementObj) model;
					Dataset dataset = dsObj.getDs();
					//if(!(dataset instanceof IRefDataset)){
						if(!isPoolds && dataset.getFrom() == null){
							manager.add(new NewDsFromPoolAction((DatasetElementObj) graph.getCells().get(0)));
							manager.add(new NewDsFormMDAction((DatasetElementObj) graph.getCells().get(0)));
						}
						manager.add(new GeneratorVObyDsAction((DatasetElementObj) graph.getCells().get(0)));
						manager.add(new ImportDsFromDBAction((DatasetElementObj)graph.getCells().get(0)));
				//	}
				}
				else if(model instanceof RefDatasetElementObj){
					RefDatasetElementObj refdsObj = (RefDatasetElementObj) model;
					Dataset refds = refdsObj.getDs();
					if(refds.getFrom() == null){
						manager.add(new GeneratorRefNodeAction((DatasetElementObj) graph.getCells().get(0), refds));
					}
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}
	
	
	protected PaletteRoot getPaletteRoot() {
		if(paleteRoot == null){
			paleteRoot = PaletteFactory.createPaletteRoot();
		}
		return paleteRoot;
	}

	
	public void doSave(IProgressMonitor monitor) {
		// ����Event����
		super.doSave(monitor);
		//TODO �����������
		String msg = graph.getCells().get(0).validate();
		if(msg != null){
			String message = "���ݼ�������Ϣ�д����Ƿ�Ҫ���棺\r\n\r\n\r\n"+msg;
			if(!MessageDialog.openConfirm(getSite().getShell(), "��ʾ", message))
				return;
		}
		save();
	}

	
	public LFWAbstractViewPage createViewPage() {
		return new LFWViewPage();
	}

	
	@SuppressWarnings("unchecked")
	public void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf listener) {
		if(widget != null){
			IEditorInput input = getEditorInput();
			DataSetEitorInput dsEditorInput = (DataSetEitorInput)input;
			ds = (Dataset) dsEditorInput.getCloneElement();
			Dataset addListenerds = widget.getViewModels().getDataset(ds.getId());
			if(addListenerds == null)
				addListenerds = ds;
			if (null != jsEventHandler) {
				JsListenerConf jsListener = null;
				if (addListenerds.getListenerMap().containsKey(jsListenerId)) {
					jsListener = addListenerds.getListenerMap().get(jsListenerId);
					if ((jsEventHandler.getScript() != null && jsEventHandler.getScript().trim().length() > 0) 
							|| jsEventHandler.isOnserver() 
							|| jsEventHandler.getSubmitRule() != null
							|| jsEventHandler.getExtendParamList().size() > 0){
						jsListener.addEventHandler(jsEventHandler);
					}
					else
						jsListener.removeEventHandler(jsEventHandler.getName());
					List<EventHandlerConf> eventList = jsListener.getJsEventHandlerList();
					for (int i = 0, n = eventList.size(); i < n; i++) {
						if (eventList.get(i).getName().equals(jsEventHandler.getName()))
							eventList.get(i).setScript(jsEventHandler.getScript());
					}
				}
				List<ListenerElementObj> listeners = graph.getJsListeners();
				if(listeners != null && listeners.size() > 0){
					ListenerElementObj listenerObj = listeners.get(0);
					listenerObj.getListenerMap().put(jsListener.getId(), jsListener);
					ds.setListenerMap(listenerObj.getListenerMap());
				}
			} else {
				addListenerds.addListener(listener);
			}
//			DatasetElementObj dsobj = (DatasetElementObj) this.graph.getCells().get(0);
			widget.getViewModels().addDataset(ds);
			LFWPersTool.saveWidget(widget);
			setDirty(false);
		}
	}
	

	public void removeJsListener(JsListenerConf jsListener) {
		if(widget != null){
			IEditorInput input = getEditorInput();
			String jsListenerId = jsListener.getId();
			DataSetEitorInput dsEditorInput = (DataSetEitorInput)input;
			Dataset ds = (Dataset) dsEditorInput.getCloneElement();
			if(jsListener.getFrom() != null){
				jsListener.setConfType(WebElement.CONF_DEL);
				widget.getViewModels().getDataset(ds.getId()).addListener(jsListener);
			}
			else
				widget.getViewModels().getDataset(ds.getId()).removeListener(jsListenerId);
			setDirtyTrue();
		}
	}

	
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		if (null != dsElementObj)
			return dsElementObj.getWebElement().getAcceptListeners();
		return null;
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
		return dsElementObj.getDs().getAcceptEventDescs();
	}

	public DatasetElementObj getDsElementObj() {
		return dsElementObj;
	}

	public void setDsElementObj(DatasetElementObj dsElementObj) {
		this.dsElementObj = dsElementObj;
	}

	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}
	
	@Override
	protected TreeItem getSelectedTreeItem(LFWDirtoryTreeItem dirTreeItem,
			LfwBaseEditorInput editorInput) {
		return null;
	}
	
}
