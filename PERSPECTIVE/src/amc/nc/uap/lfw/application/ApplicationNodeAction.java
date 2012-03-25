/**
 * 
 */
package nc.uap.lfw.application;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * ����Application�ڵ���
 * @author chouhl
 *
 */
public class ApplicationNodeAction extends NodeAction {

	public ApplicationNodeAction(){
		super(WEBProjConstants.NEW_APPLICATION);
	}

	public void run(){ 
		createApplicationNode();
	}
	
	/**
	 * ����Application�ڵ�
	 */
	private void createApplicationNode(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null){
			return;
		}
		CreateApplicationDialog dialog = new CreateApplicationDialog(WEBProjConstants.NEW_APPLICATION);
		if(dialog.open() == IDialogConstants.OK_ID){
			String appName = dialog.getApplicationName();
			String appId = dialog.getApplicationId();
			Application app = LFWSaveElementTool.createNewApplicationConf(appId, appName, dialog.getControllerClazz(), dialog.getSourcePackage());
			try{
				//����ڵ���Ϣ���ļ�
				LFWSaveElementTool.createApplication(app);
				//ˢ����
				LFWApplicationTreeItem treeItem = (LFWApplicationTreeItem)view.addAMCTreeNode(appId, appName, ILFWTreeNode.APPLICATION);
				treeItem.setType(ILFWTreeNode.APPLICATION);
				treeItem.setApplication(app);
				//ˢ�»���
//				NCConnector.refreshNode();
			}catch(Exception e){
				MainPlugin.getDefault().logError(WEBProjConstants.NEW_APPLICATION + "�ڵ�ʧ��:" + e.getMessage(), e);
				MessageDialog.openError(new Shell(Display.getCurrent()), WEBProjConstants.NEW_APPLICATION, WEBProjConstants.NEW_APPLICATION + "�ڵ�ʧ��:" + e.getMessage());
			}
		}
	}
	
}
