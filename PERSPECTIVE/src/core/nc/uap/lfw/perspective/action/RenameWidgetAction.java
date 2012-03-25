package nc.uap.lfw.perspective.action;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.listener.FileUtil;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

/**
 * 修改widget名称
 * @author guoweic
 *
 */
public class RenameWidgetAction extends NodeAction{
	
	public RenameWidgetAction() {
		if(LFWTool.NEW_VERSION.equals(((LFWWidgetTreeItem)LFWPersTool.getCurrentTreeItem()).getLfwVersion())){
			setText(WEBProjConstants.RENAME_VIEW);
			setToolTipText(WEBProjConstants.RENAME_VIEW);
		}else{
			setText(WEBProjConstants.RENAME_WIDGET);
			setToolTipText(WEBProjConstants.RENAME_WIDGET);
		}
	} 
	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		Tree tree = LFWPersTool.getTree();
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		if(view == null)
			return;
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		String oldName = widget.getId();
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, "改名", "输入" + LFWTool.getViewText(null) + "名称", oldName, null);
		if(input.open() == InputDialog.OK){
			String newName = input.getValue();
			if(newName != null && newName.trim().length()>0){
				newName =newName.trim();
				try {
					String folderPath = LFWPersTool.getCurrentFolderPath().substring(0, LFWPersTool.getCurrentFolderPath().lastIndexOf(oldName) - 1);
					FileUtil.renameFile(folderPath, oldName, newName);
					
					// 保存该Widget到pagemeta.pm文件中
					pm.getWidget(oldName).setId(newName);
					WidgetConfig[] configs = pm.getWidgetConfs();
					for (WidgetConfig widgetConf : configs) {
						if (widgetConf.getId().equals(oldName)) {
							widgetConf.setId(newName);
							widgetConf.setRefId(newName);
						}
					}
					savePagemeta(pm, folderPath);
					
					// 刷新节点
					RefreshNodeAction.refreshNode(view, tree);
					
					//TODO 刷新打开的Pagemeta编辑器页面
//					PagemetaEditor.refreshPagemetaEditor();
					
				} catch (Exception e) {
					String title =WEBProjConstants.RENAME_WIDGET;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}

	/**
	 * 保存Pagemeta到文件中
	 * @param widget
	 */
	public void savePagemeta(PageMeta pagemeta, String folderPath) {
		String projectPath = LFWPersTool.getProjectPath();
		// 保存Widget到pagemeta.pm中
		LFWConnector.savePagemetaToXml(folderPath, "pagemeta.pm", projectPath, pagemeta);
	}
}




