package nc.uap.lfw.dataset;

import java.io.File;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWDSTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * �½��������е�ds
 * @author zhangxya
 *
 */
public class NewPoolDsAction  extends Action {
	private class AddDSCommand extends Command{
		public AddDSCommand(){
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

	public NewPoolDsAction() {
		super(WEBProjConstants.NEW_PUBLIC_DATASET, PaletteImage.getCreateDsImgDescriptor());
	}

	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Tree tree = view.getTreeView().getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0){
			MessageDialog.openError(null, "������ʾ", "��ѡ��һ���ڵ�!");
			return;
		}
			
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode) selTI).getFile();
		if (parentFile.isFile()){
			MessageDialog.openError(null, "������ʾ", "�������ļ����½�Ŀ¼!");
			return;
		}
		String fullName = "";
		if(selTI instanceof LFWDirtoryTreeItem){
			LFWDirtoryTreeItem dir = (LFWDirtoryTreeItem) selTI;
			if(!dir.getType().equals(LFWDirtoryTreeItem.PARENT_PUB_DS_FOLDER)){
				fullName = selTI.getText();
				while (selTI.getParentItem() != null && (selTI instanceof LFWDirtoryTreeItem)) {
					LFWDirtoryTreeItem parent = (LFWDirtoryTreeItem)selTI.getParentItem();
					if ((parent.getText().equals(WEBProjConstants.NODEGROUP_CN) || parent.getText().equals(WEBProjConstants.PUBLIC_DATASET)))
						break;
					fullName = parent.getFile().getName() + "."	+ fullName;
					selTI = parent;
					
				}
			}
		}
		
		Shell shell = new Shell(Display.getCurrent());
		PoolDsDialog input = new PoolDsDialog(shell, WEBProjConstants.NEW_PUBLIC_DATASET, fullName);
		int result = input.open();
		if (result == IDialogConstants.OK_ID){
			String dirName = input.getDsId();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWDSTreeItem ds = (LFWDSTreeItem)view.addPoolDSTreeNode(dirName);
					//��ds�༭��
					view.openPoolDsEdit(ds);
				} catch (Exception e) {
					String title = WEBProjConstants.NEW_PUBLIC_DATASET;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "������ʾ", "���������ݼ�������!");
				return;
			}
			AddDSCommand cmd = new AddDSCommand();
			if(DataSetEditor.getActiveEditor() != null)
				DataSetEditor.getActiveEditor().executComand(cmd);
			}
			else return;
		}
	}
