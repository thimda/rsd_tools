package nc.lfw.editor.pagemeta;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ˢ�½ڵ��ļ���
 * @author guoweic
 *
 */
public class RefreshNodeAction extends Action {

	public RefreshNodeAction() {
		setText(WEBProjConstants.REFRESH);
		setToolTipText(WEBProjConstants.REFRESH);
	}

	public void run() {
		refreshNode();
	}
	
	private void refreshNode() {
		Tree tree = LFWPersTool.getTree();
		TreeItem[] tis = tree.getSelection();
		if (tis == null || tis.length == 0)
			return;
		TreeItem ti = tis[0];
		ti.removeAll();
		LFWExplorerTreeView.getLFWExploerTreeView(null).refreshDirtoryTreeItem();
	}

	/**
	 * ˢ�½ڵ㣨���ⲿ���ã�
	 * @param explorerTreeView
	 * @param treeView
	 */
	public static void refreshNode(LFWExplorerTreeView explorerTreeView, Tree tree) {
		TreeItem[] tis = tree.getSelection();
		if (tis == null || tis.length == 0)
			return;
		TreeItem ti = tis[0];
		ti.removeAll();
		explorerTreeView.refreshDirtoryTreeItem();
	}

}