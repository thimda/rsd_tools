package nc.lfw.editor.pagemeta;

import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uimodel.WidgetConfig;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;

/**
 * �½�Widget����
 * @author guoweic
 *
 */
public class WidgetCreateCommand extends Command{
	private WidgetElementObj wdObj;
	private boolean canUndo = true;
	private PagemetaGraph graph;

	private Rectangle rect;

	public WidgetCreateCommand(WidgetElementObj wdObj, PagemetaGraph graph, Rectangle rect) {
		super();
		this.wdObj = wdObj;
		this.graph = graph;
		this.rect = rect;
		setLabel("create new widget");
	}

	
	public boolean canExecute() {
		return wdObj != null && graph != null && rect != null;
	}

	
	public void execute() {
		Shell shell = new Shell();
		CreateWidgetDialog dialog = new CreateWidgetDialog(shell);
		dialog.setGraph(graph);
		if (dialog.open() == IDialogConstants.OK_ID) {
			wdObj.setId(dialog.getWidgetId());
		}else{
			return;
		}
		int realNum = graph.elementsCount;
//		int realNum = graph.getWidgetCells().size();
		//int num = realNum + 1;
		//int num = realNum;
		int num = realNum - 2;
		wdObj.setSize(new Dimension(100,100));
		//int pointY = num / 2 * 250;
		//int pointY = num / 2 * 250 - 100;
		int pointY = num / 2 * 200 - 150;
		Point point = new Point(400, pointY);
		if (num % 2 == 0)
			point.x = 100;
		wdObj.setLocation(point);
		wdObj.setGraph(graph);
		LfwWidget widget = new LfwWidget();
		widget.setId(wdObj.getId());
		//TODO guoweic: ID��RefId�Ƿ���ͬ����
		//widget.setRefId(wdObj.getRefId());
//		widget.setRefId(wdObj.getId());
		
		wdObj.setWidget(widget);
		redo();
		// ��ȡPagemetaEditor
		PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
		
		// ��������ҳ��״̬ͼ����ʾλ��
//		editor.adjustPageStateCell(graph);
		
		// ��������JsListenerͼ����ʾλ��
		editor.repaintListenerPositon();
		
		// ���ɲ�����widget.wd�ļ�
//		editor.saveWidget(widget);
		editor.addTempWidget(widget);
		// �����Widget��pagemeta.pm�ļ���
		PageMeta pagemeta = graph.getPagemeta();
//		pagemeta.addWidget(widget);
		
		WidgetConfig wconf = new WidgetConfig();
		wconf.setId(widget.getId());
		wconf.setRefId(widget.getId());
		pagemeta.addWidgetConf(wconf);
		pagemeta.addWidget(widget);
		editor.save();
		// ˢ�����иýڵ�
		editor.refreshTreeNode();
		
	}
	
	/**
	 * ����ҳ��״̬ͼ����ʾλ��
	 * @param graph
	public void adjustPageStateCell(PagemetaGraph graph) {
//		// ����graph.addCell(jsListenerObj);ʱ�����һ��������ʱ��ȥ
//		graph.elementsCount--;
		PageStateElementObj pageStateObj = PagemetaEditor.getActivePagemetaEditor().getPageStateObj();
		graph.removePageStateCell(pageStateObj);
		setPageStateCellPosition(graph, pageStateObj);
		// �Ѽ�ȥ���Ǹ����ӻ���
//		graph.elementsCount++;
		graph.addPageStateCell(pageStateObj);
	}
	 */
	
	/**
	 * ����ҳ��״̬ͼ����ʾλ��
	 * @param graph
	 * @param pageStateObj
	public void setPageStateCellPosition(PagemetaGraph graph, PageStateElementObj pageStateObj) {
		// ������ʾλ��
		int pointX = 100;
		//int pointY = ((graph.elementsCount + 1) / 2) * 250 + 150;
		int pointY = ((graph.elementsCount - 1) / 2) * 200 + 100;
		pageStateObj.setSize(new Dimension(100, 100));
		Point point = new Point(pointX, pointY);
		pageStateObj.setLocation(point);
	}
	 */

	
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
