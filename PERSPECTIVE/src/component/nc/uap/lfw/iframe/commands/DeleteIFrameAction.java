package nc.uap.lfw.iframe.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * É¾³ýIframeÃüÁî
 * @author zhangxya
 *
 */
public class DeleteIFrameAction  extends Action {
	public DeleteIFrameAction() {
		super("É¾³ý", PaletteImage.getDeleteImgDescriptor());
		setText(WEBProjConstants.DEL_IFRAME);
		setToolTipText(WEBProjConstants.DEL_IFRAME);
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedWebComponentNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_IFRAME;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	

}
