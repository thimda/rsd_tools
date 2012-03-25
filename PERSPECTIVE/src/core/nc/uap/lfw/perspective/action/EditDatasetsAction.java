package nc.uap.lfw.perspective.action;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * �༭���ݼ���
 * @author zhangxya
 *
 */
public class EditDatasetsAction extends Action {
	public LFWSeparateTreeItem getLfwDatasetsTreeItem() {
		return lfwDatasetsTreeItem;
	}

	public void setLfwDatasetsTreeItem(LFWSeparateTreeItem lfwDatasetsTreeItem) {
		this.lfwDatasetsTreeItem = lfwDatasetsTreeItem;
	}
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

	private LFWSeparateTreeItem lfwDatasetsTreeItem = null;
	public EditDatasetsAction() {
		super(WEBProjConstants.EDIT_DATASET, PaletteImage.getCreateDsImgDescriptor());
	}

		
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		view.openDatasetsEditor(lfwDatasetsTreeItem);
		AddDSCommand cmd = new AddDSCommand();
		if(DataSetEditor.getActiveEditor() != null)
			DataSetEditor.getActiveEditor().executComand(cmd);
	}

}
