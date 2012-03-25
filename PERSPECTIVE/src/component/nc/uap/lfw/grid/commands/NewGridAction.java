package nc.uap.lfw.grid.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.grid.core.GridEditor;
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
 * 新建grid action
 * @author zhangxya
 *
 */
public class NewGridAction extends Action {

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
	public NewGridAction() {
		super(WEBProjConstants.NEW_GRID, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_GRID,"输入表格名称","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem grid = (LFWWebComponentTreeItem)view.addGridTreeNode(dirName);
					//打开ds编辑器
					view.openGridEditor(grid);
				} catch (Exception e) {
					String title = WEBProjConstants.NEW_GRID;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "错误提示", "请输入表格的名称!");
				return;
			}
			AddGridCommand cmd = new AddGridCommand();
			if(GridEditor.getActiveEditor() != null)
				GridEditor.getActiveEditor().executComand(cmd);
		}
		else return;
		
	}

}
