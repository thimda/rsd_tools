package nc.uap.lfw.imagebutton.commands;

import nc.uap.lfw.image.ImageEditor;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NewImageButtonAction extends Action {

	private class AddGridCommand extends Command{
		public AddGridCommand(){
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
	public NewImageButtonAction() {
		super("�½�ͼƬ��ť", PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, "�½�ͼƬ��ť","�½�ͼƬ��ťID","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem image = (LFWWebComponentTreeItem)view.addImageButtonTreeNode(dirName);
					//��image�༭��
					view.openImageButtonEditor(image);
				} catch (Exception e) {
					String title ="�½�ͼƬ��ť";
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "������ʾ", "������ͼƬ��ť������!");
				return;
			}
			AddGridCommand cmd = new AddGridCommand();
			if(ImageEditor.getActiveEditor() != null)
				ImageEditor.getActiveEditor().executComand(cmd);
		}
		else return;
		
	}

}
