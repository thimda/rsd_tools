package nc.uap.lfw.refnoderel;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DelRefNodeRelAction extends NodeAction {
	
	public DelRefNodeRelAction() {
		setText(WEBProjConstants.DEL_REFNODE_REF);
		setToolTipText(WEBProjConstants.DEL_REFNODE_REF);
	}

	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedRefNodeRel();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title = WEBProjConstants.DEL_REFNODE_REF;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}

}
