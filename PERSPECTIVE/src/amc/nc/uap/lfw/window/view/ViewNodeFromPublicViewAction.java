/**
 * 
 */
package nc.uap.lfw.window.view;

import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.widget.WidgetFromPoolDialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 
 * 从PublicView引用View行为类
 * @author chouhl
 *
 */
public class ViewNodeFromPublicViewAction extends NodeAction {

	public ViewNodeFromPublicViewAction() {
		super(WEBProjConstants.NEW_VIEW_FROM_PUBLIC_VIEW, PaletteImage.getCreateDsImgDescriptor());
	}
	
	public void run() {
		createViewNodeFromPublicView();
	}
	
	private void createViewNodeFromPublicView(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null){
			return;
		}
		WidgetFromPoolDialog dialog = new WidgetFromPoolDialog(null, WEBProjConstants.NEW_VIEW_FROM_PUBLIC_VIEW);
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID){
			LfwWidget refWidget = dialog.getSelectedWidget();
			if (refWidget != null ) {
				createView(refWidget.getId());
			}
		}
	}
	
	private void createView(String refId){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		PageMeta window = LFWAMCPersTool.getCurrentPageMeta();
		if(view == null || window == null){
			return;
		}
		try {
			LfwWidget widget = null;
			widget = new LfwWidget();
			widget.setId(refId);
			widget.setRefId("../" + refId);
			widget.setControllerClazz("");
			widget.setSourcePackage("");
			if(window.getWidget(widget.getId()) != null){
				MessageDialog.openInformation(null, WEBProjConstants.NEW_VIEW, "ID为" + widget.getId() + "的View节点已存在!");
				return;
			}
			
			WidgetConfig wconf = new WidgetConfig();
			wconf.setId(widget.getId());
			wconf.setRefId("../" + refId);
			window.addWidgetConf(wconf);
			//保存该view到pagemeta.pm文件
			LFWSaveElementTool.savePagemeta(window);
			// 刷新节点
			RefreshNodeAction.refreshNode(view, LFWAMCPersTool.getTree());
			// 刷新打开的Window编辑器页面
			PagemetaEditor.refreshPagemetaEditor();
		} catch (Exception e) {
			MainPlugin.getDefault().logError("创建View节点失败:" + e.getMessage(), e);
			MessageDialog.openError(null, WEBProjConstants.NEW_VIEW, "创建View节点失败:" + e.getMessage());
		}
	}
	
}
