package nc.uap.lfw.perspective.action;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;

public class RefreshLFWExplorerTreeAction  extends Action {

	public RefreshLFWExplorerTreeAction() {
		super(WEBProjConstants.REFRESH,PaletteImage.getRefreshImgDescriptor());
		setToolTipText(WEBProjConstants.REFRESH);
	}

	@Override
	public void run() {
		LFWExplorerTreeView.getLFWExploerTreeView(null).refreshTree();
	}

}
