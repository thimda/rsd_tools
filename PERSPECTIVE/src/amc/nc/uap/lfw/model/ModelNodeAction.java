/**
 * 
 */
package nc.uap.lfw.model;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.LFWAMCTreeItem;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.uimodel.conf.Model;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 
 * �½�Model�ڵ���
 * @author chouhl
 *
 */
public class ModelNodeAction extends NodeAction {

	public ModelNodeAction(){
		super(WEBProjConstants.NEW_MODEL);
	}

	public void run(){
		createModel();
	}
	
	private void createModel(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if (view == null){
			return;
		}
		Model model = null;
		SelectDataModelDialog sdmd = new SelectDataModelDialog(WEBProjConstants.NEW_MODEL);
		if(sdmd.open() == IDialogConstants.OK_ID){
			int radioValue = sdmd.getRadioValue();
			switch(radioValue){
				case 1:
					ModelMDDatsetDialog mdd = new ModelMDDatsetDialog("Ԫ����");
					if(mdd.open() == IDialogConstants.OK_ID){
						CreateModelDialog cmd = new CreateModelDialog(WEBProjConstants.NEW_MODEL);
						cmd.setRefId(mdd.getModelRefId());
						cmd.setModelName(mdd.getModelName());
						if(cmd.open() == IDialogConstants.OK_ID){
							model = new Model();
							model.setId(cmd.getModelId());
							model.setCaption(cmd.getModelName());
							model.setRefId(cmd.getRefId());
							model.setTs(mdd.getTs());
							model.setVersion(mdd.getVersion());
						}
					}
					break;
				case 2:
				default:
					MessageDialog.openInformation(null, "��Ϣ", "������δ�ṩ��");
					break;
			}
		}
		if(model != null){
			try {
				//����ڵ���Ϣ���ļ�
				LFWSaveElementTool.createModel(model);
				//�½�Model�ڵ�
				LFWModelTreeItem treeItem = (LFWModelTreeItem) view.addAMCTreeNode(model.getId(), model.getCaption(), ILFWTreeNode.MODEL);
				treeItem.setType(LFWAMCTreeItem.MODEL);
				//ˢ�»���
//				NCConnector.refreshNode();
			} catch (Exception e) {
				MainPlugin.getDefault().logError(WEBProjConstants.NEW_MODEL + "�ڵ�ʧ��:" + e.getMessage(), e);
				MessageDialog.openError(null, WEBProjConstants.NEW_MODEL, WEBProjConstants.NEW_MODEL + "�ڵ�ʧ��:" + e.getMessage());
			}
		}
	}
	
}
