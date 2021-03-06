package nc.uap.lfw.chart.actions;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DelChartAction  extends Action {
	public DelChartAction() {
		super("ɾ��", PaletteImage.getDeleteImgDescriptor());
		setText(WEBProjConstants.DEL_CHART);
		setToolTipText(WEBProjConstants.DEL_CHART);
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try {
			if(view != null)
				view.deleteSelectedWebComponentNode();
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_CHART;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	

}
