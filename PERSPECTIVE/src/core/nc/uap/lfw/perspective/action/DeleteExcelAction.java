package nc.uap.lfw.perspective.action;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * É¾³ýgridÃüÁî
 * @author zhangxya
 *
 */
public class DeleteExcelAction  extends Action {
	public DeleteExcelAction() {
		super("É¾³ý", PaletteImage.getDeleteImgDescriptor());
		setText(WEBProjConstants.DEL_EXCEL);
		setToolTipText(WEBProjConstants.DEL_EXCEL);
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedWebComponentNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_EXCEL;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	

}
