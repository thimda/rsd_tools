package nc.uap.lfw.aciton;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.funnode.FuncRegisterDialog;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 编辑节点
 * @author guoweic
 *
 */
public class EditNCTemplateAction extends Action {

	public EditNCTemplateAction() {
		super(WEBProjConstants.EDIT_NC_TEMPLATE);
	}

	
	public void run() {
		
		FuncRegisterDialog dlg = new FuncRegisterDialog(null,null,WEBProjConstants.EDIT_NC_TEMPLATE,null,null,null,null);
		dlg.open();
		
		LFWExplorerTreeView view = LFWExplorerTreeView
				.getLFWExploerTreeView(null);
		if (view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.EDIT_NC_TEMPLATE, "输入节点名称", "", null);
		if (input.open() == InputDialog.OK) {
			String nodeName = input.getValue();
			if (nodeName != null && nodeName.trim().length() > 0) {
				nodeName = nodeName.trim();
				try {
					//TODO guwoeic：持久化
					view.addDirTreeNode(nodeName);
				} catch (Exception e) {
					String title = WEBProjConstants.EDIT_NC_TEMPLATE;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}

}
