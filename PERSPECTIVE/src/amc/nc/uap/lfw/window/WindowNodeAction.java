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
 * �½�Window�ڵ���
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
				//����Window�ڵ㵽�ļ�
				LFWSaveElementTool.createPagemeta(winConf, windowDialog.isFlowlayout());
				//ˢ����
				LFWPageMetaTreeItem treeItem = (LFWPageMetaTreeItem)view.addAMCTreeNode(pmId, pmName, ILFWTreeNode.WINDOW);
				treeItem.setType(ILFWTreeNode.WINDOW);
				//ˢ���ڴ�
//				NCConnector.refreshNode();
			} catch (Exception e) {
				MainPlugin.getDefault().logError(WEBProjConstants.NEW_WINDOW + "�ڵ�ʧ��:" + e.getMessage(), e);
				MessageDialog.openError(null, WEBProjConstants.NEW_WINDOW, WEBProjConstants.NEW_WINDOW + "�ڵ�ʧ��:" + e.getMessage());
			}
		}
	}
	
}
