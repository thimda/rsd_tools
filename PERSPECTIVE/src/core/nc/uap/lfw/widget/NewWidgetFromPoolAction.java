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
 * �ӹ�����������Wdiget
 * @author zhangxya
 *
 */
public class NewWidgetFromPoolAction extends Action {
	
	private class AddDSCommand extends Command{
		public AddDSCommand(Dataset ds){
			super("��������");
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
				//����pm
				savePagemeta(pm);
				//����uimeta.um�ļ�
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
					MainPlugin.getDefault().logError("��" + sourcePath + "��" + uimetaFilePath + "�����ļ������쳣!", e);
				}
				//refWidget.get
				// ˢ�½ڵ�
				RefreshNodeAction.refreshNode(view, tree);
				// ˢ�´򿪵�Pagemeta�༭��ҳ��
				PagemetaEditor.refreshPagemetaEditor();
			}
		}
		else
			return;
	}
	
	public static void savePagemeta(PageMeta pagemeta) {
		// ����Widget��pagemeta.pm��
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String currentPath = folderPath.substring(0, folderPath.lastIndexOf("\\"));
		LFWConnector.savePagemetaToXml(currentPath, "pagemeta.pm", currentPath, pagemeta);
	}
}
