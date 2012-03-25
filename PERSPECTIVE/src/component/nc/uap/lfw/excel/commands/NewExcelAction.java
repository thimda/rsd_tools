package nc.uap.lfw.excel.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.excel.core.ExcelEditor;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NewExcelAction extends Action {

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
	public NewExcelAction() {
		super(WEBProjConstants.NEW_EXCEL, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_EXCEL,"输入Excel名称","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem excel = (LFWWebComponentTreeItem)view.addExcelTreeNode(dirName);
					//打开excel编辑器
					view.openExcelEditor(excel);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_EXCEL;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "错误提示", "请输入Excel的名称!");
				return;
			}
			AddGridCommand cmd = new AddGridCommand();
			if(ExcelEditor.getActiveEditor() != null)
				ExcelEditor.getActiveEditor().executComand(cmd);
		}
		else return;
		
	}

}
