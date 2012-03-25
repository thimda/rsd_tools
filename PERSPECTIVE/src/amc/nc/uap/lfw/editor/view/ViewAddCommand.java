/**
 * 
 */
package nc.uap.lfw.editor.view;

import java.io.File;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.editor.pagemeta.PagemetaGraph;
import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.window.view.CreateNormalViewDialog;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * @author chouhl
 *
 */
public class ViewAddCommand extends Command {

	private WidgetElementObj wdObj;
	private boolean canUndo = true;
	private PagemetaGraph graph;

	private Rectangle rect;

	public ViewAddCommand(WidgetElementObj wdObj, PagemetaGraph graph, Rectangle rect) {
		super();
		this.wdObj = wdObj;
		this.graph = graph;
		this.rect = rect;
		setLabel("create new view");
	}

	public boolean canExecute() {
		return wdObj != null && graph != null && rect != null;
	}
	
	public void execute() {
		CreateNormalViewDialog dialog = new CreateNormalViewDialog(WEBProjConstants.NEW_VIEW);
		if(dialog.open() == IDialogConstants.OK_ID){
			LfwWidget widget = new LfwWidget();
			widget.setId(dialog.getName().trim());
			widget.setRefId(dialog.getName().trim());
			widget.setControllerClazz(dialog.getControllerClazz().trim());
			widget.setSourcePackage(dialog.getSourcePackage());
			wdObj.setWidget(widget);
		}else{
			return;
		}
		int realNum = graph.elementsCount;
		int num = realNum - 2;
		wdObj.setSize(new Dimension(100,100));
		int pointY = num / 2 * 200 - 150;
		Point point = new Point(400, pointY);
		if (num % 2 == 0){
			point.x = 100;
		}
		wdObj.setLocation(point);
		wdObj.setGraph(graph);
		redo();
		// ��ȡPagemetaEditor
		PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
		// ��������ҳ��״̬ͼ����ʾλ��
//		editor.adjustPageStateCell(graph);
		// ��������JsListenerͼ����ʾλ��
		editor.repaintListenerPositon();
		// ���ɲ�����widget.wd�ļ�
		editor.addTempWidget(wdObj.getWidget());
		// �����Widget��pagemeta.pm�ļ���
		PageMeta pagemeta = graph.getPagemeta();
		LfwWidget widgetConf = new LfwWidget();
		widgetConf.setId(wdObj.getWidget().getId());
		widgetConf.setRefId(wdObj.getWidget().getRefId());
		widgetConf.setSourcePackage(wdObj.getWidget().getSourcePackage());
		widgetConf.setControllerClazz(wdObj.getWidget().getControllerClazz());
		if(pagemeta.getWidget(widgetConf.getId()) != null){
			MessageDialog.openInformation(null, WEBProjConstants.NEW_VIEW, "IDΪ" + widgetConf.getId() + "��View�ڵ��Ѵ���!");
			return;
		}
		
		WidgetConfig wconf = new WidgetConfig();
		wconf.setId(widgetConf.getId());
		wconf.setRefId(wdObj.getWidget().getRefId());
		pagemeta.addWidgetConf(wconf);
		pagemeta.addWidget(wdObj.getWidget());
		
		UIMeta meta = new UIMeta();
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String filePath = folderPath + File.separator + widgetConf.getId();
		String fp = filePath.replaceAll("\\\\", "/");
		String id = fp.substring(fp.lastIndexOf("/") + 1) + "_um";
		meta.setAttribute(UIMeta.ID, id);
		meta.setFlowmode(dialog.isFlowlayout());
		
		//����view�ڵ㵽�ļ�
		LFWSaveElementTool.createView(widgetConf, meta);
		//�����view��pagemeta.pm�ļ�
		LFWSaveElementTool.savePagemeta(pagemeta);
		// ˢ�½ڵ�
		RefreshNodeAction.refreshNode(LFWExplorerTreeView.getLFWExploerTreeView(null), LFWAMCPersTool.getTree());
		// ˢ�´򿪵�Window�༭��ҳ��
		PagemetaEditor.refreshPagemetaEditor();
		
//		editor.save();
//		// ˢ�����иýڵ�
//		editor.refreshTreeNode();
	}

	public void redo() {
		graph.addWidgetCell(wdObj);
	}

	public void undo() {
		graph.removeWidgetCell(wdObj);
	}

	public boolean isCanUndo() {
		return canUndo;
	}

	public void setCanUndo(boolean canUndo) {
		this.canUndo = canUndo;
	}

	public boolean canUndo() {
		return isCanUndo();
	}
	
}
