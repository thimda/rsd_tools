package nc.lfw.editor.pagemeta;


import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * ���������ϣ�
 * @author guoweic
 *
 */
public class PagemetaEditorInput extends LfwBaseEditorInput {

	// ֱ�Ӵ�Widget�༭��ʱ��Ҫ�õ�
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
		return "Pagemeta �༭��";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "Pagemeta �༭��";
	}

	public LFWExplorerTreeView getTreeView() {
		return treeView;
	}
	public void setTreeView(LFWExplorerTreeView treeView) {
		this.treeView = treeView;
	}

}
