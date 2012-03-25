package nc.uap.lfw.selfdefcomp.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;
import nc.uap.lfw.selfdefcomp.core.SelfDefCompEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NewSelfdefCompAction  extends Action {

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
	public NewSelfdefCompAction() {
		super(WEBProjConstants.NEW_SELF_DEF_COMP, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_SELF_DEF_COMP,"输入自定义控件ID","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem selfdefComponent = (LFWWebComponentTreeItem)view.addSelfdefTreeNode(dirName);
					//打开ds编辑器
					view.openSelfdefEditor(selfdefComponent);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_SELF_DEF_COMP;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "错误提示", "请输入按钮的名称!");
				return;
			}
			AddGridCommand cmd = new AddGridCommand();
			if(SelfDefCompEditor.getActiveEditor() != null)
				SelfDefCompEditor.getActiveEditor().executComand(cmd);
		}
		else return;
		
	}

}
