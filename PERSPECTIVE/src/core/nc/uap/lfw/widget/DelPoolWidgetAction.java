package nc.uap.lfw.widget;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.listener.FileUtil;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 删除公共widget
 * @author zhangxya
 *
 */
public class DelPoolWidgetAction extends NodeAction {

	private LfwWidget widget = null;
	
	public DelPoolWidgetAction() {
		if(LFWTool.NEW_VERSION.equals(((LFWBasicTreeItem)LFWPersTool.getCurrentTreeItem()).getLfwVersion())){
			setText(WEBProjConstants.DEL_PUBLIC_VIEW);
			setToolTipText(WEBProjConstants.DEL_PUBLIC_VIEW);
		}else{
			setText(WEBProjConstants.DEL_PUBLIC_WIDGET);
			setToolTipText(WEBProjConstants.DEL_PUBLIC_WIDGET);
		}
	}

	
	public void run() {
		String msg = LFWPerspectiveNameConst.WIDGET_CN;
		if(LFWTool.NEW_VERSION.equals(((LFWWidgetTreeItem)LFWPersTool.getCurrentTreeItem()).getLfwVersion())){
			msg = "PublicView";
		}
		if (MessageDialog.openConfirm(null, "确认", "确定删除所选" + msg + "吗？")) {
			widget = LFWPersTool.getCurrentWidget();
			deleteWidget();
		}
	}
	
	
	private void deleteWidget() {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		//String widgetPoolPath = folderPath + "\\web\\pagemeta\\public\\widgetpool\\" + widget.getId();
		FileUtil.deleteFile(folderPath);
		
		String rootPath = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
		LFWConnector.removeWidgetFromPool("/" + rootPath, widget);
		
		//删除此树节点
		TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
		treeItem.dispose();
	}
	
}
