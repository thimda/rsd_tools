package nc.uap.lfw.aciton;

import nc.uap.lfw.launcher.BilltypeLauncher;

import org.eclipse.jface.action.Action;

/**
 *
 */
public class PublishBillTypeAction extends Action {

	public PublishBillTypeAction() {
		super("ע�ᵥ������");
	}

	
	public void run() {
		new BilltypeLauncher().launch();
	}

}
