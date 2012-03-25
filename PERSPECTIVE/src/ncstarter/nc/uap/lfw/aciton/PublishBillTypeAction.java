package nc.uap.lfw.aciton;

import nc.uap.lfw.launcher.BilltypeLauncher;

import org.eclipse.jface.action.Action;

/**
 *
 */
public class PublishBillTypeAction extends Action {

	public PublishBillTypeAction() {
		super("注册单据类型");
	}

	
	public void run() {
		new BilltypeLauncher().launch();
	}

}
