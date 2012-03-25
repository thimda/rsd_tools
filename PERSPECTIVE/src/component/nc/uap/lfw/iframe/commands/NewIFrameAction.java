package nc.uap.lfw.iframe.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.iframe.IFrameEditor;
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
 * �½�Iframe ����
 * @author zhangxya
 *
 */
public class NewIFrameAction  extends Action {

	private class AddIFrameCommand extends Command{
		public AddIFrameCommand(){
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
	public NewIFrameAction() {
		super(WEBProjConstants.NEW_IFRAME, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_IFRAME,"����IFrame����","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem iframe = (LFWWebComponentTreeItem)view.addIFrameTreeNode(dirName);
					//��ds�༭��
					view.openIFrameEditor(iframe);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_IFRAME;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "������ʾ", "������IFrame������!");
				return;
			}
			AddIFrameCommand cmd = new AddIFrameCommand();
			if(IFrameEditor.getActiveEditor() != null)
				IFrameEditor.getActiveEditor().executComand(cmd);
		}
		else return;
	}

}
