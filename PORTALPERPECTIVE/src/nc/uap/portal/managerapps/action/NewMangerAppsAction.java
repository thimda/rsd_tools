
package nc.uap.portal.managerapps.action;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.portal.core.PortalDirtoryTreeItem;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.perspective.PortalTreeBuilder;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 新建功能模块
 * 
 * @author dingrf
 */
public class NewMangerAppsAction extends Action {

	public NewMangerAppsAction() {
		super(PortalProjConstants.NEW_MANAGERAPPS, PaletteImage.getCreateGridImgDescriptor());
	}
	
	@Override
	public void run() {
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, PortalProjConstants.NEW_MANAGERAPPS,"输入功能模块id","", null);
		if(input.open() == IDialogConstants.OK_ID){
			String id = input.getValue();
			Tree tree = view.getTreeView().getTree();
			TreeItem[] selTIs = tree.getSelection();
			TreeItem selTI = selTIs[0];
			if (selTI.getItemCount() ==0){
				PortalTreeBuilder.getInstance().initManagerApps((PortalDirtoryTreeItem)selTI, LFWPersTool.getCurrentProjectModuleName());
			}
			TreeItem[] subItems= selTI.getItems();
			for (int i=0;i<subItems.length;i++){
				if (subItems[i].getText().equals(id)){
					MessageDialog.openError(shell, "错误提示", "已经存在ID为"+ id +"的功能模块!");
					return;
				}
			}
			try {
				view.addManagerAppsTreeNode(id);
			} catch (Exception e) {
				String title =PortalProjConstants.NEW_MANAGERAPPS;
				String message = e.getMessage();
				MessageDialog.openError(new Shell(), title, message);
			}
		}
		else
			return;
	}

}
