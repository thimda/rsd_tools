package nc.uap.lfw.perspective.webcomponent;

import java.io.File;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.common.action.LFWCopyAction;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.comp.TreeLevel;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.IRefDataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.perspective.action.DeleteDsAction;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

/**
 *Ds 
 * @author zhangxya
 *
 */
@SuppressWarnings("restriction")
public class LFWDSTreeItem  extends LFWBasicTreeItem implements ILFWTreeNode{
	
	private Dataset ds;
	public Dataset getDs() {
		return ds;
	}
	public void setDs(Dataset ds) {
		this.ds = ds;
	}
	public LFWDSTreeItem(TreeItem parentItem, Dataset ds, String name) {
		super(parentItem, SWT.NONE);
		this.ds = ds;
		setData(ds);
		if(ds.getCaption() != null)
			setText(name + "[" + ds.getCaption() + "]");
		else
			setText(name);
		setImage(getFileImage());
	}
	protected void checkSubclass () {
	}
	private Image getFileImage() {
		ImageDescriptor imageDescriptor = null;
		if(ds instanceof IRefDataset)
			imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "refds.gif");
		else
			imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "ds.gif");
		return imageDescriptor.createImage();
	}
	private IFile ifile = null;
	
	public IFile getIFile(){
		if(ifile == null){
			IPath path = new Path(getIPathStr());
			ifile = IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFile(path);

		}
		return ifile;
	}


		
	public File getFile() {
		return (File) getData();
	}

	public void deleteNode(LFWDSTreeItem dsTreeItem) {
		LfwWidget lfwwidget = LFWPersTool.getCurrentWidget();
		if(lfwwidget != null){
			Dataset ds = (Dataset)dsTreeItem.getData();
			DatasetRelations dsRelations = lfwwidget.getViewModels().getDsrelations();
			if(dsRelations != null){
				DatasetRelation[] dsRelationList = dsRelations.getDsRelations();
				if(dsRelationList != null && dsRelationList.length > 0){
					for (int i = 0; i < dsRelationList.length; i++) {
						DatasetRelation dsRelation = dsRelationList[i];
						if((dsRelation.getMasterDataset() != null && dsRelation.getMasterDataset().equals(ds.getId()))
							|| (dsRelation.getDetailDataset() != null && dsRelation.getDetailDataset().equals(ds.getId()))){
							MessageDialog.openError(null, "��ʾ", "�����ݼ���DSRealiton�����������,����ɾ��, DSRealiton��IDΪ" + dsRelation.getId() + "!");
							return;
						}
					}
				}
			}
			
			WebComponent[] webComponents = lfwwidget.getViewComponents().getComponents();
			for (int i = 0; i < webComponents.length; i++) {
				WebComponent component = webComponents[i];
				if(component instanceof FormComp){
					FormComp form = (FormComp) component;
					if(form.getDataset() != null && form.getDataset().equals(ds.getId())){
						MessageDialog.openError(null, "��ʾ", "�����ݼ��ͱ������˰�,����ɾ��, ����IDΪ" + form.getId() + "!");
						return;
					}
				}
				else if(component instanceof GridComp){
					GridComp grid = (GridComp) component;
					if(grid.getDataset() != null && grid.getDataset().equals(ds.getId())){
						MessageDialog.openError(null, "��ʾ", "�����ݼ��ͱ������˰�,����ɾ��, ����IDΪ" + grid.getId() + "!");
						return;
					}
				}
				else if(component instanceof TreeViewComp){
					TreeViewComp tree = (TreeViewComp) component;
					if(tree.getTopLevel() != null){
						TreeLevel topLevel = tree.getTopLevel();
						if(topLevel.getDataset() != null && topLevel.getDataset().equals(ds.getId())){
							MessageDialog.openError(null, "��ʾ", "�����ݼ����������˰�,����ɾ��, ����IDΪ" + tree.getId() + ",TreeLevel" +
									"Ϊ" + topLevel.getId() +  "!");
							return;
						}
						TreeLevel childLevel = topLevel.getChildTreeLevel();
						while(childLevel != null){
							if(childLevel.getDataset() != null && childLevel.getDataset().equals(ds.getId())){
								MessageDialog.openError(null, "��ʾ", "�����ݼ����������˰�,����ɾ��, ����IDΪ" + tree.getId() + ",TreeLevel" +
										"Ϊ" + childLevel.getId() +  "!");
								return;
							}
							childLevel = childLevel.getChildTreeLevel();
						}
					}
				}
			}
			
			ViewModels viewModel = lfwwidget.getViewModels();
			Dataset dsdata = viewModel.getDataset(ds.getId());
			if(dsdata == null)
				return;
			if(dsdata != null)
				viewModel.removeDataset(ds.getId());
			//ɾ��ds������combo
			ComboData[] combodatas = viewModel.getComboDatas();
			if(combodatas != null && combodatas.length > 0){
				for (int i = 0; i < combodatas.length; i++) {
					ComboData comdata = combodatas[i];
					if(comdata.getId().startsWith("comboComp_" + ds.getId()))
						viewModel.removeComboData(comdata.getId());
				}
			}
			//ɾ��ds������refnode
			IRefNode[] refNodes = viewModel.getRefNodes();
			if(refNodes != null && refNodes.length > 0){
				for (int i = 0; i < refNodes.length; i++) {
					IRefNode refNode = refNodes[i];
					if(refNode.getId().startsWith("refNode_" + ds.getId()))
						viewModel.removeRefNode(refNode.getId());
				}
			}
			LFWPersTool.saveWidget(lfwwidget);
			
			DataSetEditor dsEditor = DataSetEditor.getActiveEditor();
			if(dsEditor != null){
				dsEditor.RemoveOriginalCombdatas(ds.getId());
				dsEditor.RemoveOriginalRefNodes(ds.getId());
			}
			else{
				LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getWidgetTreeItem(dsTreeItem);
				RemoveOriginalCombdatas(widgetTreeItem, ds.getId());
				RemoveOriginalRefNodes(widgetTreeItem, ds.getId());
				
			}
			//dsEditor.dispose();
		}
		else{
			String projectPath = LFWPersTool.getProjectPath();
			TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
			String directory = treeItem.getText();
			Dataset ds = (Dataset)dsTreeItem.getData();
			TreeItem parent = (TreeItem)treeItem.getParentItem();
			while (parent != null && parent instanceof LFWDirtoryTreeItem) {
				if (parent.getText().equals("�������ݼ�"))
					break;
				directory = ((LFWDirtoryTreeItem)parent).getFile().getName() + "/" + directory;
				//treeItem = parent;
				parent = (TreeItem)parent.getParentItem();
			}
			String filePath = projectPath + "/web/pagemeta/public/dspool";
			if(directory.lastIndexOf("/") != -1){
				String folder = directory.substring(0, directory.lastIndexOf("/"));
				filePath = projectPath + "/web/pagemeta/public/dspool/" + folder;
			}
			String fileName = treeItem.getText() + ".xml";
			File file = new File(filePath + "/" + fileName);
			FileUtilities.deleteFile(file);
			LFWPersTool.getCurrentProject().getLocation();
			//String rootPath = LFWPersTool.getProjectModuleName(project);
			String rootPath = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
			LFWConnector.removeDsFromPool("/" + rootPath, ds);
		}
		dispose();
	}
	
	
	/**
	 *  ɾ��ԭ����combodata
	 * @param id
	 */
	public void RemoveOriginalCombdatas(LFWWidgetTreeItem widgetTreeItem, String id){
		LFWSeparateTreeItem lfwSeparaTreeItem = null;
		TreeItem[] separasTreeItems = widgetTreeItem.getItems();
		for (int i = 0; i < separasTreeItems.length; i++) {
			TreeItem item = separasTreeItems[i];
			if(item instanceof LFWSeparateTreeItem){
				LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
				if(seitem.getText().equals(WEBProjConstants.COMBODATA)){
					lfwSeparaTreeItem = seitem;
					break;
				}
				
			}
		}
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
	public void RemoveOriginalRefNodes(LFWWidgetTreeItem widgetTreeItem, String id){
		LFWSeparateTreeItem lfwSeparaTreeItem = null;
		TreeItem[] separasTreeItems = widgetTreeItem.getItems();
		for (int i = 0; i < separasTreeItems.length; i++) {
			TreeItem item = separasTreeItems[i];
			if(item instanceof LFWSeparateTreeItem){
				LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
				if(seitem.getText().equals(WEBProjConstants.REFNODE)){
					lfwSeparaTreeItem = seitem;
					break;
				}
				
			}
		}
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
	
	
	
	public String getIPathStr() {
		String parentIPath = "";
		TreeItem parent = getParentItem();
		if(parent instanceof ILFWTreeNode){
			parentIPath = ((ILFWTreeNode)parent).getIPathStr();
		}
		return parentIPath+"/"+getText();
		
	}
	public void deleteNode() {
		
	}
	
	public void addMenuListener(IMenuManager manager){
		Dataset dataset = (Dataset)getData();
		//����ds����
		LFWCopyAction coypAction = new LFWCopyAction(WEBProjConstants.DATASET);
		manager.add(coypAction);
		if(dataset.getFrom() != null)
			return;
		//ɾ��ds����
		DeleteDsAction deleteDsAction = new DeleteDsAction();
		manager.add(deleteDsAction);
	}
	
	public void mouseDoubleClick(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);		
		TreeItem parentDs = getParentItem();
		if(parentDs instanceof LFWSeparateTreeItem)
			view.openDsEdit(this);
		else 
			view.openPoolDsEdit(this);		
	}
}

