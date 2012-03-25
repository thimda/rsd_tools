package nc.uap.lfw.textcomp.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;
import nc.uap.lfw.textcomp.TextCompEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * ����textcomp����
 * @author zhangxya
 *
 */
public class NewTextCompAction  extends Action {

	private class AddTextCompCommand extends Command{
		public AddTextCompCommand(){
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
	public NewTextCompAction() {
		super(WEBProjConstants.NEW_TEXTCOMP, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_TEXTCOMP,"�����ı�������","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem textcomp = (LFWWebComponentTreeItem)view.addTextCompTreeNode(dirName);
					//��ds�༭��
					view.openTextEditor(textcomp);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_TEXTCOMP;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "������ʾ", "�������ı��������!");
				return;
			}
			AddTextCompCommand cmd = new AddTextCompCommand();
			if(TextCompEditor.getActiveEditor() != null)
				TextCompEditor.getActiveEditor().executComand(cmd);
		}
		else return;
	}
	
}