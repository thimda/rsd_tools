package nc.uap.lfw.selfdefcomp.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DelSelfdefCompAction  extends Action {
	public DelSelfdefCompAction() {
		super("ɾ��", PaletteImage.getDeleteImgDescriptor());
		setText(WEBProjConstants.DEL_SELF_DEF_COMP);
		setToolTipText(WEBProjConstants.DEL_SELF_DEF_COMP);
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedWebComponentNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_SELF_DEF_COMP;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	

}
