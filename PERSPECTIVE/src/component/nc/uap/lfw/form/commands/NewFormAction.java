package nc.uap.lfw.form.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.form.core.FormEditor;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 新建表单命令
 * @author zhangxya
 *
 */
public class NewFormAction extends Action {

	private class AddFormCommand extends Command{
		public AddFormCommand(){
			super("增加属性");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			}
		
		public void undo() {
		}
		
	}
	public NewFormAction() {
		super(WEBProjConstants.NEW_FORM, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_FORM,"输入表单名称","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem form = (LFWWebComponentTreeItem)view.addFormTreeNode(dirName);
					//打开ds编辑器
					view.openFormEditor(form);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_FORM;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "错误提示", "请输入表单的名称!");
				return;
			}
			AddFormCommand cmd = new AddFormCommand();
			if(FormEditor.getActiveEditor() != null)
				FormEditor.getActiveEditor().executComand(cmd);
		}
		else return;
	}
	
}