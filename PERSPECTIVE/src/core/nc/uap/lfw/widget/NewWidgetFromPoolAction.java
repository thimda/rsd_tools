package nc.uap.lfw.widget;

import java.io.IOException;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

/**
 * 从公共池中引入Wdiget
 * @author zhangxya
 *
 */
public class NewWidgetFromPoolAction extends Action {
	
	private class AddDSCommand extends Command{
		public AddDSCommand(Dataset ds){
			super("增加属性");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			}
		
		public void undo() {
		}
		
	}

	
	
	public NewWidgetFromPoolAction() {
		super(WEBProjConstants.NEW_WIDGET_FROM_POOL, PaletteImage.getCreateDsImgDescriptor());
	}
	
	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		Tree tree = LFWPersTool.getTree();
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		Shell shell = new Shell();
		WidgetFromPoolDialog dialog = new WidgetFromPoolDialog(shell, WEBProjConstants.NEW_WIDGET_FROM_POOL);
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID){
			LfwWidget refWidget = dialog.getSelectedWidget();
			if (refWidget != null ) {
				LfwWidget oriWidget = LFWPersTool.getCurrentWidget();
				String widgetId = oriWidget.getId();
				//pm.getWidgetConfList().remove(oriWidget);
				pm.removeWidget(widgetId);
				WidgetConfig[] widgetList = pm.getWidgetConfs();
				for (WidgetConfig widgetConf : widgetList) {
					if (widgetId.equals(widgetConf.getId())) {
						pm.removeWidgetConf(widgetConf);
						break;
					}
				}
				WidgetConfig pubWidgetConf = new WidgetConfig();
				pubWidgetConf.setId(widgetId);
				pubWidgetConf.setRefId(".." +  refWidget.getId());
				pm.addWidgetConf(pubWidgetConf);
				//保存pm
				savePagemeta(pm);
				//拷贝uimeta.um文件
				String folderPath = LFWPersTool.getCurrentFolderPath();
				//
				String uimetaFilePath = folderPath;
				uimetaFilePath += "/uimeta.um";
				String projectPath = LFWPersTool.getCurrentProject().getLocation().toString();
				String sourcePath = projectPath;
				sourcePath += "/web/pagemeta/public/widgetpool";
				sourcePath += "/" + refWidget.getId() + "/uimeta.um";
				try {
					FileUtilities.copyFile(sourcePath, uimetaFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					MainPlugin.getDefault().logError("从" + sourcePath + "到" + uimetaFilePath + "拷贝文件出现异常!", e);
				}
				//refWidget.get
				// 刷新节点
				RefreshNodeAction.refreshNode(view, tree);
				// 刷新打开的Pagemeta编辑器页面
				PagemetaEditor.refreshPagemetaEditor();
			}
		}
		else
			return;
	}
	
	public static void savePagemeta(PageMeta pagemeta) {
		// 保存Widget到pagemeta.pm中
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String currentPath = folderPath.substring(0, folderPath.lastIndexOf("\\"));
		LFWConnector.savePagemetaToXml(currentPath, "pagemeta.pm", currentPath, pagemeta);
	}
}
