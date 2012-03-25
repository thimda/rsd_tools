package nc.uap.lfw.label.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.label.LabelEditor;
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
 * 增加label命令
 * @author zhangxya
 *
 */
public class NewLabelAction extends Action {

	private class AddGridCommand extends Command{
		public AddGridCommand(){
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
	public NewLabelAction() {
		super(WEBProjConstants.NEW_LABEL, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_LABEL,"新建标签名称","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem label = (LFWWebComponentTreeItem)view.addLabelTreeNode(dirName);
					//打开ds编辑器
					view.openLabelEditor(label);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_LABEL;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "错误提示", "请输入标签的名称!");
				return;
			}
			AddGridCommand cmd = new AddGridCommand();
			if(LabelEditor.getActiveEditor() != null)
				LabelEditor.getActiveEditor().executComand(cmd);
		}
		else return;
		
	}

}
