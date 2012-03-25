/**
 * 
 */
package nc.uap.lfw.window.view;

import java.io.File;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 
 * �½�View�ڵ���
 * @author chouhl
 *
 */
public class ViewNodeAction extends NodeAction {
	
	private String refId = null;

	public ViewNodeAction() {
		super(WEBProjConstants.NEW_VIEW);
	}
	
	public ViewNodeAction(String refId) {
		super(WEBProjConstants.NEW_VIEW);
		this.refId = refId;
	}

	public void run() {
		createView();
	}
	
	private void createView(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		PageMeta window = LFWAMCPersTool.getCurrentPageMeta();
		if(view == null || window == null){
			return;
		}
		ViewTypeDialog typeDialog = new ViewTypeDialog(WEBProjConstants.NEW_VIEW);
		if(typeDialog.open() == IDialogConstants.OK_ID){
			if(typeDialog.isNormalView()){
				CreateNormalViewDialog dialog = new CreateNormalViewDialog(WEBProjConstants.NEW_VIEW);
				dialog.setNotRefPublicView(refId == null);
				if(dialog.open() == IDialogConstants.OK_ID){
					try {
						LfwWidget widget = new LfwWidget();
						widget.setId(dialog.getName().trim());
						if(refId == null){
							widget.setRefId(widget.getId());
						}else{
							widget.setRefId("../" + refId);
						}
						widget.setControllerClazz(dialog.getControllerClazz().trim());
						widget.setSourcePackage(dialog.getSourcePackage());
						if(window.getWidget(widget.getId()) != null){
							MessageDialog.openInformation(null, WEBProjConstants.NEW_VIEW, "IDΪ" + widget.getId() + "��View�ڵ��Ѵ���!");
							return;
						}
						WidgetConfig wconf = new WidgetConfig();
						wconf.setId(widget.getId());
						wconf.setRefId(widget.getRefId());
						window.addWidgetConf(wconf);
						if(refId == null){
							UIMeta meta = new UIMeta();
							String folderPath = LFWPersTool.getCurrentFolderPath();
							String filePath = folderPath + File.separator + widget.getId();
							String fp = filePath.replaceAll("\\\\", "/");
							String id = fp.substring(fp.lastIndexOf("/") + 1) + "_um";
							meta.setAttribute(UIMeta.ID, id);
							meta.setFlowmode(true);
							//����view�ڵ㵽�ļ�
							LFWSaveElementTool.createView(widget, meta);
						}
						//�����view��pagemeta.pm�ļ�
						LFWSaveElementTool.savePagemeta(window);
						//���ӽڵ�
						view.addViewTreeNode(widget); 
		//				RefreshNodeAction.refreshNode(view, LFWAMCPersTool.getTree());
						//ˢ�´򿪵�Window�༭��ҳ��
						PagemetaEditor.refreshPagemetaEditor();
					} catch (Exception e) {
						MainPlugin.getDefault().logError("����View�ڵ�ʧ��:" + e.getMessage(), e);
						MessageDialog.openError(null, WEBProjConstants.NEW_VIEW, "����View�ڵ�ʧ��:" + e.getMessage());
					}
				}
			}
			else{
				CreateCodeViewDialog dialog = new CreateCodeViewDialog(WEBProjConstants.NEW_VIEW);
				dialog.setNotRefPublicView(refId == null);
				if(dialog.open() == IDialogConstants.OK_ID){
					try {
						LfwWidget widget = new LfwWidget();
						widget.setId(dialog.getName().trim());
						if(refId == null){
							widget.setRefId(widget.getId());
						}else{
							widget.setRefId("../" + refId);
						}
						widget.setProvider(dialog.getProviderClazz().trim());
						if(window.getWidget(widget.getId()) != null){
							MessageDialog.openInformation(null, WEBProjConstants.NEW_VIEW, "IDΪ" + widget.getId() + "��View�ڵ��Ѵ���!");
							return;
						}
						WidgetConfig wconf = new WidgetConfig();
						wconf.setId(widget.getId());
						wconf.setRefId(widget.getRefId());
						window.addWidgetConf(wconf);
						if(refId == null){
							String folderPath = LFWPersTool.getCurrentFolderPath();
							String filePath = folderPath + File.separator + widget.getId();
							
							String fp = filePath.replaceAll("\\\\", "/");
							String id = fp.substring(fp.lastIndexOf("/") + 1) + "_um";
							
							UIMeta meta = new UIMeta();
							meta.setAttribute(UIMeta.ID, id);
							meta.setFlowmode(true);
							meta.setUiprovider(dialog.getProviderClazz().trim());
							
							//����view�ڵ㵽�ļ�
							LFWSaveElementTool.createView(widget, meta);
						}
						//�����view��pagemeta.pm�ļ�
						LFWSaveElementTool.savePagemeta(window);
						//���ӽڵ�
						view.addViewTreeNode(widget); 
		//				RefreshNodeAction.refreshNode(view, LFWAMCPersTool.getTree());
						//ˢ�´򿪵�Window�༭��ҳ��
						PagemetaEditor.refreshPagemetaEditor();
					} catch (Exception e) {
						MainPlugin.getDefault().logError("����View�ڵ�ʧ��:" + e.getMessage(), e);
						MessageDialog.openError(null, WEBProjConstants.NEW_VIEW, "����View�ڵ�ʧ��:" + e.getMessage());
					}
				}
			}
		}
	}

}
