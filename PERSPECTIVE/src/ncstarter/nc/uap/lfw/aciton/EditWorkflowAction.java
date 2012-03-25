package nc.uap.lfw.aciton;

import nc.uap.lfw.launcher.WorkflowLauncher;

import org.eclipse.jface.action.Action;

/**
 *
 */
public class EditWorkflowAction extends Action {

	public EditWorkflowAction() {
		super("±à¼­Á÷³Ì");
	}

	
	public void run() {
		new WorkflowLauncher().launch();
	}

}
