package nc.uap.portal.managerapps.action;

import nc.uap.lfw.palette.PaletteImage;
import nc.uap.portal.managerapps.ManagerAppsTreeItem;
import nc.uap.portal.managerapps.ManagerCategoryTreeItem;
import nc.uap.portal.managerapps.dialog.ManagerCategoryDialog;
import nc.uap.portal.om.ManagerApps;
import nc.uap.portal.om.ManagerCategory;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalProjConstants;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 新建功能分类
 * @author dingrf
 *
 */
public class NewManagerCategoryAction extends Action {
	

	public NewManagerCategoryAction() {
		super(PortalProjConstants.NEW_MANAGERCATEGORY, PaletteImage.getCreateGridImgDescriptor());
	}

	private Boolean checkID(TreeItem[] items,String id){
		for (int i=0 ; i< items.length ; i++){
			if(items[i].getData() instanceof ManagerCategory){
				if (((ManagerCategory)items[i].getData()).getId().equals(id)){
					return true;
				}
				else if (checkID(items[i].getItems(),id)){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		if(view == null)
			return;
		ManagerCategoryDialog categoryDialog = new ManagerCategoryDialog(new Shell(), PortalProjConstants.NEW_MANAGERCATEGORY);
		if(categoryDialog.open() == IDialogConstants.OK_ID){
			
			Tree tree = view.getTreeView().getTree();
			TreeItem[] selTIs = tree.getSelection();
			TreeItem selTI = selTIs[0];
			TreeItem[] subItems= selTI.getItems();
			if (checkID(subItems,categoryDialog.getId())){
				MessageDialog.openError(new Shell(), "错误提示", "已经存在ID为"+ categoryDialog.getId() +"的功能分类!");
				return;
			}
			try {
				String managerId="";
				/* 如果从 ManagerApps 上点右键新增 */
				if (selTI instanceof ManagerAppsTreeItem){
					managerId = ((ManagerApps)selTI.getData()).getId();
				}
				/* 如果从 ManagerCategory 上点右键新增 */
				else if (selTI instanceof ManagerCategoryTreeItem){
					managerId = ((ManagerCategoryTreeItem) selTI).getManagerId();
				}
				
				ManagerCategoryTreeItem cti = (ManagerCategoryTreeItem)view.addManagerCategoryTreeNode(categoryDialog.getId(),
						categoryDialog.getText(),categoryDialog.getI18nName(),managerId);
				view.openManagerAppsPageEditor(cti);
				
			} catch (Exception e) {
				String title =PortalProjConstants.NEW_MANAGERCATEGORY;
				String message = e.getMessage();
				MessageDialog.openError(new Shell(), title, message);
			}
		}
		else
			return;
	}

}
