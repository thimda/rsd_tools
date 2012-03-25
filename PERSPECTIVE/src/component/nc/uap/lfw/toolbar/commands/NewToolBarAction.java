package nc.uap.lfw.toolbar.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;
import nc.uap.lfw.toolbar.ToolBarEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * �½�toolbar
 * @author zhangxya
 *
 */
public class NewToolBarAction extends Action {

	private class AddFormCommand extends Command{
		public AddFormCommand(){
			super("��������");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			}
		
		public void undo() {
		}
		
	}
	public NewToolBarAction() {
		super(WEBProjConstants.NEW_TOOLBAR, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_TOOLBAR,"���빤����ID","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem toolbar = (LFWWebComponentTreeItem)view.addToolbarTreeNode(dirName);
					//��ds�༭��
					view.openToolbarEditor(toolbar);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_TOOLBAR;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "������ʾ", "�����빤����������!");
				return;
			}
			AddFormCommand cmd = new AddFormCommand();
			if(ToolBarEditor.getActiveEditor() != null)
				ToolBarEditor.getActiveEditor().executComand(cmd);
		}
		else return;
	}
	
}