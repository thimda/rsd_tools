package nc.uap.lfw.button.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * É¾³ý°´Å¥ÃüÁî
 * @author zhangxya
 *
 */
public class DeleteButtonAction extends NodeAction {
	
	public DeleteButtonAction() {
		setText(WEBProjConstants.DEL_BUTTON);
		setToolTipText(WEBProjConstants.DEL_BUTTON);
	}

	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedWebComponentNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_BUTTON;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}

}
