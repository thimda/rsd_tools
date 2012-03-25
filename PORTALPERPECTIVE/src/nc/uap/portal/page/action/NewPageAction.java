
package nc.uap.portal.page.action;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.portal.core.PortalDirtoryTreeItem;
import nc.uap.portal.om.Page;
import nc.uap.portal.page.PortalPageTreeItem;
import nc.uap.portal.page.dialog.PageDialog;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.perspective.PortalTreeBuilder;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * �½�page
 * 
 * @author dingrf
 */
public class NewPageAction extends Action {

	public NewPageAction() {
		super(PortalProjConstants.NEW_PAGE, PaletteImage.getCreateGridImgDescriptor());
	}
	
	@Override
	public void run() {
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		PageDialog pageDialog = new PageDialog(new Shell(), PortalProjConstants.NEW_PAGE);
		if(pageDialog.open() == IDialogConstants.OK_ID){
			String id = pageDialog.getId();
			String  title = pageDialog.getTitle(); 
			Tree tree = view.getTreeView().getTree();
			TreeItem[] selTIs = tree.getSelection();
			TreeItem selTI = selTIs[0];
			if (selTI.getItemCount() ==0){
				PortalTreeBuilder.getInstance().initPageNodeTree((PortalDirtoryTreeItem)selTI, LFWPersTool.getCurrentProjectModuleName());
			}
			TreeItem[] subItems= selTI.getItems();
			for (int i=0;i<subItems.length;i++){
				Page p =(Page)subItems[i].getData();
				if (p.getPagename().equals(id)){
					MessageDialog.openError(shell, "������ʾ", "�Ѿ�����idΪ"+ id +"��Page!");
					return;
				}
			}
			try {
				Page page = new Page();
				page.setPagename(id);
				page.setVersion("1");
				page.setTitle(title);
				
				PortalPageTreeItem pl = (PortalPageTreeItem)view.addPageTreeNode(page);
				//�� page�༭��
				view.openPortalPageEditor(pl);
				
			} catch (Exception e) {
				String message = e.getMessage();
				MessageDialog.openError(new Shell(), PortalProjConstants.NEW_PAGE, message);
			}
		}
		else
			return;
	}

}
