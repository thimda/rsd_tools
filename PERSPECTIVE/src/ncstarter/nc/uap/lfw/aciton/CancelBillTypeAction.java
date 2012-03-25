package nc.uap.lfw.aciton;

import nc.uap.lfw.launcher.IShortcut;
import nc.uap.lfw.launcher.NCLauncher;
import nc.uap.lfw.launcher.NCMenuClientShortcut;

import org.eclipse.jface.action.Action;

/**
 *
 */
public class CancelBillTypeAction extends Action {

	public CancelBillTypeAction() {
		super("取消单据类型");
	}

	
	public void run() {
		NCLauncher laucher = new NCLauncher(){

			
			protected IShortcut getClientShortcut() {
				return new NCMenuClientShortcut();
			}
			
		};
		laucher.launch();
	}

}
