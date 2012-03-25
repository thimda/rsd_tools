/**
 * 
 */
package nc.uap.lfw.core.base;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWVirtualDirTreeItem;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * @author chouhl
 * 2011-12-7
 */
public class CreateVirtualFolderAction extends NodeAction {

	private String itemType = null;
	
	public CreateVirtualFolderAction(String itemType){
		super(WEBProjConstants.NEW_VIRTUALDIR);
		this.itemType = itemType;
	}
	
	@Override
	public void run() {
		createVirtualFolder();
	}
	
	private void createVirtualFolder(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if (view == null){
			return;
		}
		InputDialog input = new InputDialog(null, WEBProjConstants.NEW_VIRTUALDIR, "����" + WEBProjConstants.VIRTUALDIR + "����", "", null);
		if (input.open() == InputDialog.OK) {
			String vitualDirName = input.getValue();
			if (vitualDirName != null && vitualDirName.trim().length() > 0) {
				vitualDirName = vitualDirName.trim();
				try {
					LFWVirtualDirTreeItem folderTreeItem = (LFWVirtualDirTreeItem)view.addVirtualTreeNode(vitualDirName);
					folderTreeItem.setType(itemType);
					folderTreeItem.setLfwVersion(LFWTool.NEW_VERSION);
				} catch (Exception e) {
					String title = WEBProjConstants.NEW_VIRTUALDIR;
					String message = e.getMessage();
					MessageDialog.openError(null, title, message);
				}
			}
		}
	}
	
}
