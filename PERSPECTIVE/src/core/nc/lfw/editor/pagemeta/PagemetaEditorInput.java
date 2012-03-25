package nc.lfw.editor.pagemeta;


import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * 该类已作废！
 * @author guoweic
 *
 */
public class PagemetaEditorInput extends LfwBaseEditorInput {

	// 直接打开Widget编辑器时需要用到
	private LFWExplorerTreeView treeView;
	public PagemetaEditorInput(){
	}
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Pagemeta 编辑器";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "Pagemeta 编辑器";
	}

	public LFWExplorerTreeView getTreeView() {
		return treeView;
	}
	public void setTreeView(LFWExplorerTreeView treeView) {
		this.treeView = treeView;
	}

}
