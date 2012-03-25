package nc.lfw.editor.widget.plug;

import java.io.File;

import nc.lfw.editor.widget.WidgetEditor;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.page.PluginDesc;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * É¾³ýPluginDesc
 * @author dingrf
 *
 */
public class DelPluginDescAction extends Action {
	private class DelPluginCommand extends Command{
		
		private PluginDescElementObj pluginObj;
		public DelPluginCommand(PluginDescElementObj obj) {
			super("É¾³ý");
			this.pluginObj = obj;
//			this.label = label;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			WidgetElementObj weObj = pluginObj.getWidgetObj();
			PluginDesc plugin = pluginObj.getPlugin();
			weObj.getWidget().getPluginDescs().remove(plugin);
			pluginObj.getConn().disConnect();
			weObj.removePluginCell(pluginObj);
//			WidgetEditor.getActiveWidgetEditor().getGraph().removePluginCell(pluginObj);
			
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
	
	
	private PluginDescElementObj pluginObj;
	
	private String controllerClazz = null;
	
	private int actionType = 2;
	
	public DelPluginDescAction(PluginDescElementObj pluginObj) {
		setText("É¾³ý ");
		setToolTipText("É¾³ý");
		this.pluginObj = pluginObj;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "È·ÈÏ", "È·¶¨É¾³ý Êä³öÃèÊöÂð£¿"))
			deleteItem();
	}
	
	private void deleteItem() {
		String id = pluginObj.getPlugin().getId();
		WidgetElementObj widgetObj =  pluginObj.getWidgetObj();
		DelPluginCommand cmd = new DelPluginCommand(pluginObj);
		if(WidgetEditor.getActiveEditor() != null)
			WidgetEditor.getActiveEditor().executComand(cmd);
		
//		int index = controllerClazz.lastIndexOf(".");
//		String packageName = null;
//		if(index > 0){
//			packageName = controllerClazz.substring(0, index);
//		}else{
//			packageName = "";
//		}
//		String folderPath = LFWAMCPersTool.getCurrentFolderPath();
//		String projectPath = LFWAMCPersTool.getLFWProjectPath();
//		String filePath = folderPath;
//		
////		String className = controllerClazz.substring(index + 1);
////		String classFilePath = projectPath + File.separator + widgetObj.getWidget().getSourcePackage() + packageName.replaceAll("\\.", "/");
////		String classFileName = className + ".java";
//		EventConf eventConf = new EventConf();
//		eventConf.setMethodName("plugin" + id);
//		eventConf.setName("");
//		LfwParameter param = new LfwParameter();
//		param.setDesc("java.util.Map");
//		param.setName("keys");
//		eventConf.addParam(param);
//		eventConf.setEventStatus(EventConf.DEL_STATUS);
		
		for (EventConf event : widgetObj.getWidget().getEventConfs()){
			if (event.getMethodName().equals("plugin" + id)){
				event.setEventStatus(EventConf.DEL_STATUS);
				break;
			}
		}
//		LFWAMCConnector.operateViewEventMethod(actionType, packageName, className, classFilePath, classFileName, filePath, WEBProjConstants.AMC_VIEW_FILENAME, projectPath, widgetObj.getWidget(), eventConf);
		
		WidgetEditor.getActiveWidgetEditor().doSave(null);
	}

}
