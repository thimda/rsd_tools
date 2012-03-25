package nc.uap.lfw.refnoderel;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 创建参照关联关系
 * @author zhangxya
 *
 */
public class NewRefNodeRelAction extends Action {

	private class AddGridCommand extends Command{
		public AddGridCommand(){
			super("增加属性");
		}
		
		public void execute(){
			redo();
		}

		public void redo(){
		}
		
		public void undo(){
		}
	}
	
	public NewRefNodeRelAction() {
		super(WEBProjConstants.NEW_REFNODE_REF, PaletteImage.getCreateGridImgDescriptor());
	}

	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_REFNODE_REF,"输入参照关系ID","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWRefNodeRelTreeItem refnodeRel = (LFWRefNodeRelTreeItem)view.addRefNodeRelation(dirName);
					//打开ds编辑器
					view.openRefNodeRelEditor(refnodeRel);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_REFNODE_REF;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "错误提示", "请输入按钮的名称!");
				return;
			}
			AddGridCommand cmd = new AddGridCommand();
			if(RefnoderelEditor.getActiveEditor() != null)
				RefnoderelEditor.getActiveEditor().executComand(cmd);
		}
		else return;
	}

}
