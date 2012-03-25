package nc.lfw.editor.widget.plug;

import nc.lfw.editor.widget.WidgetEditor;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.page.PlugoutDesc;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * É¾³ýPlugoutDesc
 * @author dingrf
 *
 */
public class DelPlugoutDescAction extends Action {
	private class DelPlugoutCommand extends Command{
		
		private PlugoutDescElementObj plugoutObj;
		public DelPlugoutCommand(PlugoutDescElementObj obj) {
			super("É¾³ý");
			this.plugoutObj = obj;
//			this.label = label;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			WidgetElementObj weObj = plugoutObj.getWidgetObj();
			PlugoutDesc plugout = plugoutObj.getPlugout();
			weObj.getWidget().getPlugoutDescs().remove(plugout);
			plugoutObj.getConn().disConnect();
			weObj.removePlugoutCell(plugoutObj);
//			WidgetEditor.getActiveWidgetEditor().getGraph().removePlugoutCell(plugoutObj);
			
//			ExPoint point = label.getExPoint();
//			pluginObj.getExPoint().remove(point);
//			pluginObj.setCurrentExPoint(null);
//			pluginObj.setExtension(null);
//			pluginObj.getFingure().getContentFigure().remove(label);
//			pluginObj.getFingure().setCurrentLabel(null);
//			pluginObj.getFingure().reloadPropertySheet(pluginObj);
//			pluginObj.getFingure().reloadExtensionSheet(pluginObj);
//			pluginObj.getFingure().resizeHeight();
		}

		
		public void undo() {
//			pluginObj.getExPoint().add(label.getExPoint());
//			pluginObj.getFingure().addToContent(label);
//			pluginObj.getFingure().addExPointLabelListener(label);
//			pluginObj.getFingure().selectedLabel(label);
		}
		
	}	
	
	
	private PlugoutDescElementObj plugoutObj;
	
	public DelPlugoutDescAction(PlugoutDescElementObj plugoutObj) {
		setText("É¾³ý ");
		setToolTipText("É¾³ý");
		this.plugoutObj = plugoutObj;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "È·ÈÏ", "È·¶¨É¾³ý Êä³öÃèÊöÂð£¿"))
			deleteItem();
	}
	
	private void deleteItem() {
		DelPlugoutCommand cmd = new DelPlugoutCommand(plugoutObj);
		if(WidgetEditor.getActiveEditor() != null)
			WidgetEditor.getActiveEditor().executComand(cmd);
		WidgetEditor.getActiveWidgetEditor().doSave(null);
	}

}
