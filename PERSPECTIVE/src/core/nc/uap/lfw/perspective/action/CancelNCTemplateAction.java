package nc.uap.lfw.perspective.action;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 取消关联nc单据模板
 * @author zhangxya
 *
 */
public class CancelNCTemplateAction extends Action {

	public CancelNCTemplateAction() {
		super(WEBProjConstants.CANCEL_NC_TEMPLATE);
	}

	
	public void run() {
		boolean flag = MessageDialog.openConfirm(new Shell(), WEBProjConstants.CANCEL_NC_TEMPLATE, "确定要取消关联NC单据模板吗?");
		if(flag){
			LfwWidget widget = LFWPersTool.getCurrentWidget();
			if(widget != null){
				if(widget.getFrom() != null && widget.getFrom().equals("NC")){
					widget.setFrom(null);
//					if(widget.getExtendAttribute(ExtAttrConstants.BILL_NODEKEY) != null){
//						widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, null);
//						if(widget.getExtendAttribute(ExtAttrConstants.BILL_PAGETYPE) != null)
//							widget.setExtendAttribute(ExtAttrConstants.BILL_PAGETYPE, null);
//						String filePath = LFWPersTool.getCurrentFolderPath();
//						String projectPath = LFWPersTool.getProjectPath();
//						NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
//					}
				}
			}
		}
	}

}
