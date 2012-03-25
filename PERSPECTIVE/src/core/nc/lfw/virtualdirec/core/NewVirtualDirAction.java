package nc.lfw.virtualdirec.core;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * �½�����Ŀ¼
 * @author zhangxya
 *
 */
public class NewVirtualDirAction extends Action {

	public NewVirtualDirAction() {
		super(WEBProjConstants.NEW_VIRTUALDIR);
	}
	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView
				.getLFWExploerTreeView(null);
		if (view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_VIRTUALDIR, "����" + WEBProjConstants.VIRTUALDIR + "����", "", null);
		if (input.open() == InputDialog.OK) {
			String vitualDirName = input.getValue();
			if (vitualDirName != null && vitualDirName.trim().length() > 0) {
				vitualDirName = vitualDirName.trim();
				try {
			        view.addVirtualTreeNode(vitualDirName);
			 	} catch (Exception e) {
					String title = WEBProjConstants.NEW_VIRTUALDIR;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}

}
