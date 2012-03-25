/**
 * 
 */
package nc.uap.lfw.window;

import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 
 * 新建Window节点类
 * @author chouhl
 *
 */
public class WindowNodeAction extends NodeAction {

	public WindowNodeAction() {
		super(WEBProjConstants.NEW_WINDOW);
	}

	public void run() {
		createWindow();
	}
	
	private void createWindow(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if (view == null){
			return;
		}
		CreateWindowDialog windowDialog = new CreateWindowDialog(WEBProjConstants.NEW_WINDOW);
		if (windowDialog.open() == IDialogConstants.OK_ID) {
			String pmId = windowDialog.getId();
			String pmName = windowDialog.getName();
			PageMeta winConf = LFWSaveElementTool.createNewWindowConf(pmId, pmName, windowDialog.getControllerClazz(), windowDialog.getSourcePackage());
			try {
				//保存Window节点到文件
				LFWSaveElementTool.createPagemeta(winConf, windowDialog.isFlowlayout());
				//刷新树
				LFWPageMetaTreeItem treeItem = (LFWPageMetaTreeItem)view.addAMCTreeNode(pmId, pmName, ILFWTreeNode.WINDOW);
				treeItem.setType(ILFWTreeNode.WINDOW);
				//刷新内存
//				NCConnector.refreshNode();
			} catch (Exception e) {
				MainPlugin.getDefault().logError(WEBProjConstants.NEW_WINDOW + "节点失败:" + e.getMessage(), e);
				MessageDialog.openError(null, WEBProjConstants.NEW_WINDOW, WEBProjConstants.NEW_WINDOW + "节点失败:" + e.getMessage());
			}
		}
	}
	
}
