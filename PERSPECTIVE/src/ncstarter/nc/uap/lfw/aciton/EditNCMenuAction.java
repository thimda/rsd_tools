package nc.uap.lfw.aciton;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.launcher.NCMenuLauncher;

import org.eclipse.jface.action.Action;

/**
 *
 */
public class EditNCMenuAction extends Action {

	public EditNCMenuAction() {
		super(WEBProjConstants.EDIT_NC_MENU);
	}

	
	public void run() {
		new NCMenuLauncher().launch();
	}

}
