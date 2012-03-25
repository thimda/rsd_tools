package nc.uap.lfw.chart.actions;

import nc.uap.lfw.chart.core.ChartEditor;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWebComponentTreeItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NewChartAction  extends Action {

	private class AddChartCommand extends Command{
		public AddChartCommand(){
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
	public NewChartAction() {
		super(WEBProjConstants.NEW_CHART, PaletteImage.getCreateGridImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_CHART,"输入图表ID","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWWebComponentTreeItem chart = (LFWWebComponentTreeItem)view.addChartTreeNode(dirName);
					//打开ds编辑器
					view.openChartEditor(chart);
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_CHART;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
			else {
				MessageDialog.openError(shell, "错误提示", "请输入图表的ID!");
				return;
			}
			AddChartCommand cmd = new AddChartCommand();
			if(ChartEditor.getActiveEditor() != null)
				ChartEditor.getActiveEditor().executComand(cmd);
		}
		else return;
		
	}

}
