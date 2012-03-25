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
 * 新建Widget命令
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
		//TODO guoweic: ID和RefId是否相同？？
		//widget.setRefId(wdObj.getRefId());
//		widget.setRefId(wdObj.getId());
		
		wdObj.setWidget(widget);
		redo();
		// 获取PagemetaEditor
		PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
		
		// 重新设置页面状态图标显示位置
//		editor.adjustPageStateCell(graph);
		
		// 重新设置JsListener图标显示位置
		editor.repaintListenerPositon();
		
		// 生成并保存widget.wd文件
//		editor.saveWidget(widget);
		editor.addTempWidget(widget);
		// 保存该Widget到pagemeta.pm文件中
		PageMeta pagemeta = graph.getPagemeta();
//		pagemeta.addWidget(widget);
		
		WidgetConfig wconf = new WidgetConfig();
		wconf.setId(widget.getId());
		wconf.setRefId(widget.getId());
		pagemeta.addWidgetConf(wconf);
		pagemeta.addWidget(widget);
		editor.save();
		// 刷新树中该节点
		editor.refreshTreeNode();
		
	}
	
	/**
	 * 调整页面状态图标显示位置
	 * @param graph
	public void adjustPageStateCell(PagemetaGraph graph) {
//		// 调用graph.addCell(jsListenerObj);时多加了一个数，暂时减去
//		graph.elementsCount--;
		PageStateElementObj pageStateObj = PagemetaEditor.getActivePagemetaEditor().getPageStateObj();
		graph.removePageStateCell(pageStateObj);
		setPageStateCellPosition(graph, pageStateObj);
		// 把减去的那个数加回来
//		graph.elementsCount++;
		graph.addPageStateCell(pageStateObj);
	}
	 */
	
	/**
	 * 设置页面状态图标显示位置
	 * @param graph
	 * @param pageStateObj
	public void setPageStateCellPosition(PagemetaGraph graph, PageStateElementObj pageStateObj) {
		// 设置显示位置
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
