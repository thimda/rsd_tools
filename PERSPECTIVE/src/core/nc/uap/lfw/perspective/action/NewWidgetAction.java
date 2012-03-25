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
 * �½�widget
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
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_WIDGET ,"����" + LFWPerspectiveNameConst.WIDGET_CN + "����","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LfwWidget widget = new LfwWidget();
					widget.setId(dirName);
					//TODO guoweic: ID��RefId�Ƿ���ͬ����
					//widget.setRefId(dirName);
					widget.setRefId(dirName);
					
					// ���ɲ�����widget.wd�ļ�
					saveWidget(widget);
					
					// �����Widget��pagemeta.pm�ļ���
//					pm.addWidget(widget);
					
					WidgetConfig wconf = new WidgetConfig();
					wconf.setId(widget.getId());
					wconf.setRefId(widget.getRefId());
					pm.addWidgetConf(wconf);
					pm.addWidget(widget);
					savePagemeta(pm);
					
					// ˢ�½ڵ�
					RefreshNodeAction.refreshNode(view, tree);
					
					// ˢ�´򿪵�Pagemeta�༭��ҳ��
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
	 * ����Widget���ļ���
	 * @param widget
	 */
	public void saveWidget(LfwWidget widget) {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		saveWidget(folderPath, widget);
	}
	
	/**
	 * ����Widget���ļ���
	 * @param widget
	 */
	public void saveWidget(String folderPath, LfwWidget widget) {
		String projectPath = LFWPersTool.getProjectPath();
		// ƴ��widget.wd�ļ�ȫ·��
		String filePath = folderPath + "\\" + widget.getId();
		// ����Widget��widget.wd��
		NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
	}

	/**
	 * ����Pagemeta���ļ���
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
	 * ����Pagemeta���ļ���
	 * @param widget
	 */
	public void savePagemeta(String folderPath, PageMeta pagemeta) {
		String projectPath = LFWPersTool.getProjectPath();
		// ����Widget��pagemeta.pm��
		LFWConnector.savePagemetaToXml(folderPath, "pagemeta.pm", projectPath, pagemeta);
	}
}




