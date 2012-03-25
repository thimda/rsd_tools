package nc.uap.lfw.widget;

import java.io.File;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class NewPoolWidgetAction extends Action{
	
	public NewPoolWidgetAction() {
		super(WEBProjConstants.NEW_PUBLIC_WIDGET);
	}

	public void run() {
		Tree tree = LFWPersTool.getTree();
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_PUBLIC_WIDGET , "����" + LFWPerspectiveNameConst.WIDGET_CN + "����","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					List<String> allPoolWidgetIds = LFWPersTool.getAllPoolWidgetId();
					if(allPoolWidgetIds.contains(dirName))
						throw new Exception("�Ѿ�����IDΪ" + dirName + "�Ĺ���" + LFWPerspectiveNameConst.WIDGET_CN + "!");
					LfwWidget widget = new LfwWidget();
					widget.setId(dirName);
					widget.setRefId(dirName);
					// ���ɲ�����widget.wd�ļ�
					widget.setExtendAttribute(LfwWidget.POOLWIDGET, LfwWidget.POOLWIDGET);
					saveWidget(widget);
					//widgetTreeItem
					String folderPath = LFWPersTool.getCurrentFolderPath();
					String widgetPoolPath = "\\web\\pagemeta\\public\\widgetpool";
					String widgetPath = folderPath.replace("\\����iView", widgetPoolPath);
					File widgetfile = new File(widgetPath + "/"+ widget.getId());
					TreeItem parentTreeItem = tree.getSelection()[0];
					LFWWidgetTreeItem widgetTreeItem = new LFWWidgetTreeItem(parentTreeItem, widgetfile, widget, "[" + LFWPerspectiveNameConst.WIDGET_CN + "] " + widgetfile.getName());
					view.detalWidgetTreeItem(widgetTreeItem, widgetfile, widget);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_PUBLIC_WIDGET;
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
		String widgetPoolPath = "\\web\\pagemeta\\public\\widgetpool";
		String widgetPath = folderPath.replace("\\����iView", widgetPoolPath);
		saveWidget(widgetPath, widget);
	}
	
	/**
	 * ����Widget���ļ���
	 * @param widget
	 */
	public void saveWidget(String folderPath, LfwWidget widget) {
		String projectPath = LFWPersTool.getProjectPath();
		String rootPath = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
		// ƴ��widget.wd�ļ�ȫ·��
		String filePath = folderPath + "\\" + widget.getId();
		// ����Widget��widget.wd��
		NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, "/" + rootPath);
	}
}
	