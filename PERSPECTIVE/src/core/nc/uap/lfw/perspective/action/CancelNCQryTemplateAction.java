package nc.uap.lfw.perspective.action;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * ȡ������NC��ѯģ��
 * @author zhangxya
 *
 */
public class CancelNCQryTemplateAction extends Action {

	public CancelNCQryTemplateAction() {
		super(WEBProjConstants.CANCEL_NC_QUERY_TEMPLATE);
	}

	
	public void run() {
		boolean flag = MessageDialog.openConfirm(new Shell(), WEBProjConstants.CANCEL_NC_QUERY_TEMPLATE, "ȷ��Ҫȡ������NC��ѯģ����?");
		if(flag){
			LfwWidget widget = LFWPersTool.getCurrentWidget();
			if(widget != null){
				if(widget.getFrom() != null && widget.getFrom().equals("NCQ")){
					widget.setFrom(null);
//					if(widget.getExtendAttribute(ExtAttrConstants.BILL_NODEKEY) != null){
//						widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, null);
//						String filePath = LFWPersTool.getCurrentFolderPath();
//						String projectPath = LFWPersTool.getProjectPath();
//						NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
//					}
				}
			}
		}
	}
}
