package nc.uap.portal.plugin.action;

import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.plugin.ExPointLabel;
import nc.uap.portal.plugin.PluginEditor;
import nc.uap.portal.plugin.PluginElementObj;
import nc.uap.portal.plugins.model.ExPoint;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * É¾³ýExPoint
 * @author dingrf
 *
 */
public class DelPointAction extends Action {
	private class DelPointCommand extends Command{
		private PluginElementObj pluginObj;
		private ExPointLabel label;
		
		public DelPointCommand(PluginElementObj obj, ExPointLabel label) {
			super(PortalProjConstants.DEL_POINT);
			this.pluginObj = obj;
			this.label = label;
		}

		public void execute() {
			redo();
		}

		
		@SuppressWarnings("static-access")
		public void redo() {
			ExPoint point = label.getExPoint();
			pluginObj.getExPoint().remove(point);
			pluginObj.setCurrentExPoint(null);
			pluginObj.setExtension(null);
			pluginObj.getFingure().getContentFigure().remove(label);
			pluginObj.getFingure().setCurrentLabel(null);
			pluginObj.getFingure().reloadPropertySheet(pluginObj);
			pluginObj.getFingure().reloadExtensionSheet(pluginObj);
			pluginObj.getFingure().resizeHeight();
		}

		
		public void undo() {
			pluginObj.getExPoint().add(label.getExPoint());
			pluginObj.getFingure().addToContent(label);
			pluginObj.getFingure().addExPointLabelListener(label);
			pluginObj.getFingure().selectedLabel(label);
		}
		
	}	
	
	
	private PluginElementObj pluginObj;
	private ExPointLabel label;
	
	public DelPointAction(ExPointLabel label,PluginElementObj pluginObj) {
		setText("É¾³ý " + label.getText());
		setToolTipText("É¾³ý");
		this.pluginObj = pluginObj;
		this.label = label;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "È·ÈÏ", "È·¶¨É¾³ý " + label.getText() + " Âð£¿"))
			deleteItem();
	}
	
	private void deleteItem() {
		DelPointCommand cmd = new DelPointCommand(pluginObj, label);
		if(PluginEditor.getActiveEditor() != null)
			PluginEditor.getActiveEditor().executComand(cmd);
	}

}
