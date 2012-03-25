package nc.uap.lfw.combodata.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ɾ���������ݼ�
 * @author zhangxya
 *
 */
public class DeleteComboAction  extends NodeAction {
	
	public DeleteComboAction() {
		setText(WEBProjConstants.DEL_COMBO_DATASET);
		setToolTipText(WEBProjConstants.DEL_COMBO_DATASET);
	}

	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedComboNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_COMBO_DATASET;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}

}