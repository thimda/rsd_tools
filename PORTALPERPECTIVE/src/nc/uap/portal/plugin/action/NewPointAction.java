package nc.uap.portal.plugin.action;

import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.plugin.ExPointLabel;
import nc.uap.portal.plugin.PluginEditor;
import nc.uap.portal.plugin.PluginElementObj;
import nc.uap.portal.plugins.model.ExPoint;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * 新建扩展点
 * @author dingrf
 *
 */
public class NewPointAction extends Action {
	
	private class NewPointCommand extends Command{
		private PluginElementObj pluginObj;
		private ExPointLabel label;
		private ExPoint point;
		
		public NewPointCommand(PluginElementObj obj,ExPoint point) {
			super(PortalProjConstants.NEW_POINT);
			this.pluginObj = obj;
//			this.label = label;
			this.point = point;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			pluginObj.getExPoint().add(point);
			label = new ExPointLabel(point.getTitle(), point);
			pluginObj.getFingure().addToContent(label);
			pluginObj.getFingure().addExPointLabelListener(label);
			pluginObj.getFingure().selectedLabel(label);
			pluginObj.getFingure().resizeHeight();
		}

		
		public void undo() {
		}
		
	}	
	
	private PluginElementObj pluginObj;

	public NewPointAction(PluginElementObj obj) {
		super(PortalProjConstants.NEW_POINT);
		this.pluginObj = obj;
	}

	
	public void run() {
		ExPoint point = new ExPoint();
		point.setTitle(PortalProjConstants.NEW_POINT);

		NewPointCommand cmd = new NewPointCommand(pluginObj, point);
		if(PluginEditor.getActiveEditor() != null)
			PluginEditor.getActiveEditor().executComand(cmd);
	}
}
