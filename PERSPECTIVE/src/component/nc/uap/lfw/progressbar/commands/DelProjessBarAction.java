package nc.uap.lfw.progressbar.commands;

import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DelProjessBarAction extends Action {
	public DelProjessBarAction() {
		super("ɾ��", PaletteImage.getDeleteImgDescriptor());
		setText("ɾ��������");
		setToolTipText("ɾ��������");
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedWebComponentNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title ="ɾ��������";
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	

}
