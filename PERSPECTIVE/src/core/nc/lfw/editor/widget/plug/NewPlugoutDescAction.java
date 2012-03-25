package nc.lfw.editor.widget.plug;


import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.widget.WidgetEditor;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.palette.ChildConnection;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 新建plugout
 * @author dingrf
 *
 */
public class NewPlugoutDescAction extends Action {
	
	private class NewPlugoutCommand extends Command{
		private WidgetElementObj widgetObj;
		
		private String id;
		
		public NewPlugoutCommand(WidgetElementObj obj, String id) {
			super(WEBProjConstants.NEW_PLUGOUTDESC);
			this.widgetObj = obj;
			this.id = id;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			PlugoutDescElementObj plugoutObj = new PlugoutDescElementObj();
			plugoutObj.setPlugout(new PlugoutDesc());
			plugoutObj.getPlugout().setId(this.id);
			List<PlugoutDesc> plugoutDescs = widgetObj.getWidget().getPlugoutDescs();
			if (plugoutDescs == null){
				plugoutDescs = new ArrayList<PlugoutDesc>();
				widgetObj.getWidget().setPlugoutDescs(plugoutDescs);
			}
			plugoutDescs.add(plugoutObj.getPlugout());
			Point point = new Point();
			int count =widgetObj.getPlugoutCells().size();
			point.x = widgetObj.getLocation().x + widgetObj.getSize().width + 50;
			point.y = widgetObj.getLocation().y + count * 40;
			ChildConnection conn = new ChildConnection(widgetObj,plugoutObj);
			conn.connect();
			plugoutObj.setConn(conn);
			plugoutObj.setLocation(point);
			plugoutObj.setSize(new Dimension(100, 30));
			plugoutObj.setWidgetObj(widgetObj);
			widgetObj.addPlugoutCell(plugoutObj);
			//Graph中加入plugout
//			((WidgetGraph)widgetObj.getGraph()).addPlugoutCell(plugoutObj);
			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
			wdEditor.setDirtyTrue();
//			widgetObj.addPlugoutCell(plugoutObj);
		}

		public void undo() {
		}
		
	}	
	
	private WidgetElementObj widgetObj;

	public NewPlugoutDescAction(WidgetElementObj obj) {
		super(WEBProjConstants.NEW_PLUGOUTDESC);
		this.widgetObj = obj;
	}

	public void run() {
		Shell shell = new Shell(Display.getCurrent());
		
		PlugoutDescDialog plugoutDescDialog = new PlugoutDescDialog(new Shell(), WEBProjConstants.NEW_PLUGOUTDESC);
		if(plugoutDescDialog.open() == InputDialog.OK){
			String id = plugoutDescDialog.getId(); 
			
			/*判断ID重复*/
			for (PlugoutDescElementObj obj : widgetObj.getPlugoutCells()){
				if (obj.getPlugout().getId().equals(id)){
					MessageDialog.openError(shell, "错误提示", "已经存在id为"+ id +"的输出描述!");
					return;
				}
			}
			
			NewPlugoutCommand cmd = new NewPlugoutCommand(widgetObj, id);
			if(WidgetEditor.getActiveEditor() != null)
				WidgetEditor.getActiveEditor().executComand(cmd);
			
		}
		else return;
	}
}
