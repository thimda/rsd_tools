package nc.uap.lfw.perspective.action;

import java.io.File;
import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.listener.FileUtil;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

/**
 * ɾ��Widget����
 * @author guoweic
 *
 */
public class DeleteWidgetAction extends NodeAction {
	
	private LfwWidget selectedWidget = null;
	
	private LfwWidget widget = null;
	
	private boolean inPagemetaEditor = false;
	
	public DeleteWidgetAction() {
		if(LFWTool.NEW_VERSION.equals(((LFWBasicTreeItem)LFWPersTool.getCurrentTreeItem()).getLfwVersion())){
			setText(WEBProjConstants.DEL_VIEW);
			setToolTipText(WEBProjConstants.DEL_VIEW);
		}else{
			setText(WEBProjConstants.DEL_WIDGET);
			setToolTipText(WEBProjConstants.DEL_WIDGET);
		}
	}

	
	public void run() {
		String msg = LFWPerspectiveNameConst.WIDGET_CN;
		if(LFWTool.NEW_VERSION.equals(((LFWBasicTreeItem)LFWPersTool.getCurrentTreeItem()).getLfwVersion())){
			msg = WEBProjConstants.VIEW_SUB;
		}
		if (MessageDialog.openConfirm(null, "ȷ��", "ȷ��ɾ����ѡ" + msg + "��")) {
			widget = LFWPersTool.getCurrentWidget();
			if (null == widget) {
				widget = selectedWidget;
			}
			delete();
		}
	}
	
	private void delete() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		Tree tree = LFWPersTool.getTree();
		PageMeta pm = null;
		if (inPagemetaEditor)
			pm = PagemetaEditor.getActivePagemetaEditor().getGraph().getPagemeta();
		else
			pm = LFWPersTool.getCurrentPageMeta();
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());

		try {
			// ��ȡID
			String widgetId = widget.getId();
			
			// ɾ��widget.wd�ļ������ļ�������������
			deleteWidget();
			
			// ɾ��Pagemeta�еĸ�Widget
//			pm.getWidgetMap().remove(widgetId);
			pm.removeWidget(widgetId);
			WidgetConfig[] configs = pm.getWidgetConfs();
			
			PagemetaEditor editor = null;
			
			for (WidgetConfig widgetConf : configs) {
				if (widgetId.equals(widgetConf.getId())) {
					pm.removeWidgetConf(widgetConf);
					break;
				}
			}
			// ɾ��Pagemeta�е����Connector
			Map<String, Connector> connectorMap = pm.getConnectorMap();

			String[] ids = connectorMap.keySet().toArray(new String[0]);
			for (int i = 0, n = ids.length; i < n; i++) {
				String id = ids[i];
				Connector connector = connectorMap.get(id);
				if (widgetId.equals(connector.getSource()) || widgetId.equals(connector.getTarget())) {
					connectorMap.remove(id);
				}
			}
			
			// ���浽pagemeta.pm�ļ���
			savePagemeta(pm);
			
			if (inPagemetaEditor) {
				editor = PagemetaEditor.getActivePagemetaEditor();
				editor.repaintGraph();
			} else {
				// ˢ�´򿪵�Pagemeta�༭��ҳ��
				PagemetaEditor.refreshPagemetaEditor();
			}
			
			// ˢ�½ڵ�
			RefreshNodeAction.refreshNode(view, tree);
			
		} catch (Exception e) {
			String title = WEBProjConstants.DEL_WIDGET;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}

	}

	/**
	 * ����Pagemeta���ļ���
	 * @param widget
	 */
	private void savePagemeta(PageMeta pagemeta) {
		LFWDirtoryTreeItem dirItem = LFWPersTool.getDirectoryTreeItem(LFWPersTool.getCurrentPageMetaTreeItem());
		String folderPath = "";
		if(dirItem != null) {
			File file = dirItem.getFile();
			folderPath = file.getPath();
		}
		
		String projectPath = LFWPersTool.getProjectPath();
		// ����Widget��pagemeta.pm��
		LFWConnector.savePagemetaToXml(folderPath, "pagemeta.pm", projectPath, pagemeta);
	}
	
	/**
	 * ɾ��widget.wd�ļ������ļ�������������
	 */
	private void deleteWidget() {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		if (null == LFWPersTool.getCurrentWidget())
			folderPath += "\\" + widget.getId();
		FileUtil.deleteFile(folderPath);
	}

	public void setSelectedWidget(LfwWidget selectedWidget) {
		this.selectedWidget = selectedWidget;
	}

	public void setInPagemetaEditor(boolean inPagemetaEditor) {
		this.inPagemetaEditor = inPagemetaEditor;
	}
	

}
