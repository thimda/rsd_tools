package nc.uap.lfw.refnode;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWRefNodeTreeItem;
import nc.uap.lfw.refnode.core.PoolRefNodeDialog;
import nc.uap.lfw.refnode.core.RefNodeEditor;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ���ӹ������е�refnode�ڵ�
 * @author zhangxya
 *
 */
public class NewPoolRefNodeAction extends Action {

	private class AddRefNodeCommand extends Command{
		public AddRefNodeCommand(){
			super("��������");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
		}
		
		public void undo() {
		}
		
	}
	public NewPoolRefNodeAction() {
		super(WEBProjConstants.NEW_PUBLIC_REFNODE, PaletteImage.getCreateTreeImgDescriptor());

	}

	/**
	 * ���к���
	 */
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Tree tree = view.getTreeView().getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0){
			MessageDialog.openError(null, "������ʾ", "��ѡ��һ���ڵ�!");
			return;
		}
		TreeItem selTI = selTIs[0];
		String fullName = "";
		if(selTI instanceof LFWDirtoryTreeItem){
			LFWDirtoryTreeItem dir = (LFWDirtoryTreeItem) selTI;
			if(!dir.getType().equals(LFWDirtoryTreeItem.PARENT_PUB_REF_FOLDER)){
				fullName = selTI.getText();
				while (selTI.getParentItem() != null && (selTI instanceof LFWDirtoryTreeItem)) {
					LFWDirtoryTreeItem parent = (LFWDirtoryTreeItem)selTI.getParentItem();
					if ((parent.getText().equals(WEBProjConstants.NODEGROUP_CN) || parent.getText().equals(WEBProjConstants.PUBLIC_REFNODE)))
						break;
					fullName = parent.getFile().getName() + "."	+ fullName;
					selTI = parent;
					
				}
			}
		}
		
		Shell shell = new Shell(Display.getCurrent());
		PoolRefNodeDialog input = new PoolRefNodeDialog(shell, WEBProjConstants.NEW_PUBLIC_REFNODE, fullName);
		int result = input.open();
		if (result == IDialogConstants.OK_ID){
			String dirName = input.getRefnodeId();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWRefNodeTreeItem refnode = (LFWRefNodeTreeItem)view.addPoolRefNode(dirName);
					view.openPoolRefNodeEdit(refnode);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_PUBLIC_REFNODE;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "������ʾ", "��������յ�ID!");
				return;
			}
			AddRefNodeCommand cmd = new AddRefNodeCommand();
			if(RefNodeEditor.getActiveEditor() != null)
				RefNodeEditor.getActiveEditor().executComand(cmd);
			}
			else return;
		
		
//		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//		if(view == null)
//			return;
//		Shell shell = new Shell(Display.getCurrent());
//		InputDialog input = new InputDialog(shell, "�½���������","���빫����������","", null);
//		if(input.open() == InputDialog.OK){
//			String dirName = input.getValue();
//			if(dirName != null && dirName.trim().length()>0){
//				dirName =dirName.trim();
//				try {
//					LFWRefNodeTreeItem refnode = (LFWRefNodeTreeItem)view.addPoolRefNode(dirName);
//					view.openPoolRefNodeEdit(refnode);
//				} catch (Exception e) {
//					String title ="�½�����";
//					String message = e.getMessage();
//					MessageDialog.openError(shell, title, message);
//				}
//			}
//			else {
//				MessageDialog.openError(shell, "������ʾ", "��������յ�ID!");
//				return;
//			}
//			AddRefNodeCommand cmd = new AddRefNodeCommand();
//			if(RefNodeEditor.getActiveEditor() != null)
//				RefNodeEditor.getActiveEditor().executComand(cmd);
//		}
//		else return;
		
	}
}
