package nc.uap.lfw.perspective.action;

import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * �½�Ŀ¼
 * @author zhangxya
 *
 */
public class NewDirAction extends Action {

	public NewDirAction() {
		super("�½�Ŀ¼");
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, "�½�Ŀ¼","����Ŀ¼����","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					view.addDirTreeNode(dirName);
				} catch (Exception e) {
					String title ="�½�Ŀ¼";
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}

}
