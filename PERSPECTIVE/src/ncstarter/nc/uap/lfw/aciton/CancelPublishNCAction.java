package nc.uap.lfw.aciton;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * ȡ��NC����
 * @author zhangxya
 *
 */
public class CancelPublishNCAction extends Action {

	public CancelPublishNCAction() {
		super(WEBProjConstants.CANCEL_PUBLISH_NC);
	}

	
	public void run() {
		boolean flag = MessageDialog.openConfirm(new Shell(), "ȡ��NC������ʾ", "ȷ��Ҫȡ��NC������?");
		if(flag){
			PageMeta pm = LFWPersTool.getCurrentPageMeta();
			if(pm == null)
				return;
//			if(pm.getExtendAttributeValue(ExtAttrConstants.FUNC_CODE) != null){
//				String funnode = (String) pm.getExtendAttributeValue(ExtAttrConstants.FUNC_CODE);
//				if(funnode != null){
//					NCConnector.delSysTemplateRelated(funnode);
//				}
//				pm.setExtendAttribute(ExtAttrConstants.FUNC_CODE, null);
//				String filePath = LFWPersTool.getCurrentFolderPath();
//				String projectPath = LFWPersTool.getProjectPath();
//				LFWConnector.savePagemetaToXml(filePath, "pagemeta.pm", projectPath, pm);
//			}
		}
	}
}
