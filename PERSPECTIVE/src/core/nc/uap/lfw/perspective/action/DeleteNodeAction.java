package nc.uap.lfw.perspective.action;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * É¾³ý½Úµã
 * @author zhangxya
 *
 */
public class DeleteNodeAction extends Action {
	public DeleteNodeAction() {
		//super("É¾³ý",ImageFactory.getDeleteImageDescriptor());
		setText(WEBProjConstants.DEL_PAGEMETA);
		setToolTipText(WEBProjConstants.DEL_PAGEMETA);
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedTreeNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_PAGEMETA;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	

}
