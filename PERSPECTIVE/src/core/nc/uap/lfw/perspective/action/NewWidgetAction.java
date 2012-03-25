package nc.uap.lfw.perspective.action;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.application.LFWApplicationTreeItem;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

/**
 * 新建widget
 * @author guoweic
 *
 */
public class NewWidgetAction extends Action{
	
	public NewWidgetAction() {
		super(WEBProjConstants.NEW_WIDGET);
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		Tree tree = LFWPersTool.getTree();
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		if(view == null || pm == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_WIDGET ,"输入" + LFWPerspectiveNameConst.WIDGET_CN + "名称","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LfwWidget widget = new LfwWidget();
					widget.setId(dirName);
					//TODO guoweic: ID和RefId是否相同？？
					//widget.setRefId(dirName);
					widget.setRefId(dirName);
					
					// 生成并保存widget.wd文件
					saveWidget(widget);
					
					// 保存该Widget到pagemeta.pm文件中
//					pm.addWidget(widget);
					
					WidgetConfig wconf = new WidgetConfig();
					wconf.setId(widget.getId());
					wconf.setRefId(widget.getRefId());
					pm.addWidgetConf(wconf);
					pm.addWidget(widget);
					savePagemeta(pm);
					
					// 刷新节点
					RefreshNodeAction.refreshNode(view, tree);
					
					// 刷新打开的Pagemeta编辑器页面
					PagemetaEditor.refreshPagemetaEditor();
					
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_WIDGET;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}
	
	/**
	 * 保存Widget到文件中
	 * @param widget
	 */
	public void saveWidget(LfwWidget widget) {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		saveWidget(folderPath, widget);
	}
	
	/**
	 * 保存Widget到文件中
	 * @param widget
	 */
	public void saveWidget(String folderPath, LfwWidget widget) {
		String projectPath = LFWPersTool.getProjectPath();
		// 拼接widget.wd文件全路径
		String filePath = folderPath + "\\" + widget.getId();
		// 保存Widget到widget.wd中
		NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
	}

	/**
	 * 保存Pagemeta到文件中
	 * @param widget
	 */
	public void savePagemeta(PageMeta pagemeta) {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		if(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWApplicationTreeItem){
			LFWPageMetaTreeItem pmTreeItem = LFWAMCPersTool.getDefaultWindowTreeItem(null);
			if(pmTreeItem != null){
				folderPath = LFWAMCPersTool.getCurrentFolderPath(pmTreeItem);
			}
		}
		savePagemeta(folderPath, pagemeta);
	}

	/**
	 * 保存Pagemeta到文件中
	 * @param widget
	 */
	public void savePagemeta(String folderPath, PageMeta pagemeta) {
		String projectPath = LFWPersTool.getProjectPath();
		// 保存Widget到pagemeta.pm中
		LFWConnector.savePagemetaToXml(folderPath, "pagemeta.pm", projectPath, pagemeta);
	}
}




