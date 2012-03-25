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
 * 新增Application节点类
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
	 * 创建Application节点
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
				//保存节点信息到文件
				LFWSaveElementTool.createApplication(app);
				//刷新树
				LFWApplicationTreeItem treeItem = (LFWApplicationTreeItem)view.addAMCTreeNode(appId, appName, ILFWTreeNode.APPLICATION);
				treeItem.setType(ILFWTreeNode.APPLICATION);
				treeItem.setApplication(app);
				//刷新缓存
//				NCConnector.refreshNode();
			}catch(Exception e){
				MainPlugin.getDefault().logError(WEBProjConstants.NEW_APPLICATION + "节点失败:" + e.getMessage(), e);
				MessageDialog.openError(new Shell(Display.getCurrent()), WEBProjConstants.NEW_APPLICATION, WEBProjConstants.NEW_APPLICATION + "节点失败:" + e.getMessage());
			}
		}
	}
	
}
