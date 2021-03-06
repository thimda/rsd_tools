package nc.uap.portal.managerapps.action;


import nc.uap.lfw.palette.PaletteImage;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalProjConstants;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ɾ�� ���ܷ���
 * @author dingrf
 *
 */
public class DelManagerCategoryAction extends Action {

	public DelManagerCategoryAction() {
		super(PortalProjConstants.DEL_MANAGERCATEGORY, PaletteImage.getDeleteImgDescriptor());
	}
	
	@Override
	public void run() {
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectManagerCategoryTreeNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =PortalProjConstants.DEL_MANAGERCATEGORY;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
}
