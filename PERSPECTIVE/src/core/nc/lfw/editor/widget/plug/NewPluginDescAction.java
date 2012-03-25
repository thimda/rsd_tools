package nc.lfw.editor.widget.plug;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.widget.WidgetEditor;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.page.PluginDesc;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
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
 * 新建plugin
 * @author dingrf
 *
 */
public class NewPluginDescAction extends Action {
	
	private class NewPluginCommand extends Command{
		private WidgetElementObj widgetObj;
		
		private String id;
		public NewPluginCommand(WidgetElementObj obj, String id) {
			super(WEBProjConstants.NEW_PLUGINDESC);
			this.widgetObj = obj;
			this.id = id;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			PluginDescElementObj pluginObj = new PluginDescElementObj();
			pluginObj.setPlugin(new PluginDesc());
			pluginObj.getPlugin().setId(this.id);
//			pluginObj.getPlugin().setSubmitRule(new EventSubmitRule());
			List<PluginDesc> pluginDescs = widgetObj.getWidget().getPluginDescs();
			if (pluginDescs == null){
				pluginDescs = new ArrayList<PluginDesc>();
				widgetObj.getWidget().setPluginDescs(pluginDescs);
			}
			pluginDescs.add(pluginObj.getPlugin());
			Point point = new Point();
			int count =widgetObj.getPluginCells().size();
			point.x = widgetObj.getLocation().x -100 - 50;
			point.y = widgetObj.getLocation().y + count * 40;
			pluginObj.setLocation(point);
			ChildConnection conn = new ChildConnection(widgetObj,pluginObj);
			conn.connect();
			pluginObj.setConn(conn);
			pluginObj.setSize(new Dimension(100, 30));
			pluginObj.setWidgetObj(widgetObj);
			widgetObj.addPluginCell(pluginObj);
			//Graph中加入plugin
//			((WidgetGraph)widgetObj.getGraph()).addPluginCell(pluginObj);
			
//			widgetObj.addPluginCell(pluginObj);
		}

		public void undo() {
		}
		
	}	
	
	private WidgetElementObj widgetObj;
	
	private String controllerClazz = null;
	
	public NewPluginDescAction(WidgetElementObj obj) {
		super(WEBProjConstants.NEW_PLUGINDESC);
		this.widgetObj = obj;
		this.controllerClazz = widgetObj.getWidget().getControllerClazz(); 
	}

	public void run() {
		Shell shell = new Shell(Display.getCurrent());
		
		PluginDescDialog pluginDescDialog = new PluginDescDialog(new Shell(), WEBProjConstants.NEW_PLUGINDESC);
		if(pluginDescDialog.open() == InputDialog.OK){
			String id = pluginDescDialog.getId(); 
			
			/*判断ID重复*/
			for (PluginDescElementObj obj : widgetObj.getPluginCells()){
				if (obj.getPlugin().getId().equals(id)){
					MessageDialog.openError(shell, "错误提示", "已经存在id为"+ id +"的输入描述!");
					return;
				}
			}
			
			
			NewPluginCommand cmd = new NewPluginCommand(widgetObj, id);
			if(WidgetEditor.getActiveEditor() != null)
				WidgetEditor.getActiveEditor().executComand(cmd);
			
			int index = controllerClazz.lastIndexOf(".");
			String packageName = null;
			if(index > 0){
				packageName = controllerClazz.substring(0, index);
			}else{
				packageName = "";
			}
			String projectPath = LFWAMCPersTool.getLFWProjectPath();
			
			String className = controllerClazz.substring(index + 1);
			String classFilePath = projectPath + File.separator + widgetObj.getWidget().getSourcePackage() + packageName.replaceAll("\\.", "/");
			String classFileName = className + ".java";
			EventConf eventConf = new EventConf();
			eventConf.setMethodName("plugin" + id);
			eventConf.setName("");
			LfwParameter param = new LfwParameter();
			param.setDesc("java.util.Map");
			param.setName("keys");
			eventConf.addParam(param);
			eventConf.setEventStatus(EventConf.ADD_STATUS);
			eventConf.setClassFileName(classFileName);
			eventConf.setClassFilePath(classFilePath);
			widgetObj.getWidget().addEventConf(eventConf);
			
			WidgetEditor.getActiveWidgetEditor().setDirtyTrue();
			
//			LFWAMCConnector.operateViewEventMethod(actionType, packageName, className, classFilePath, classFileName, filePath, WEBProjConstants.AMC_VIEW_FILENAME, projectPath, widgetObj.getWidget(), eventConf);
			
//			JsEventDesc desc = new JsEventDesc(eventConf.getMethodName(), "keys");
//			desc.setEventClazz("Map");			
//			eventConf
//			LFWAMCConnector.operateViewEventMethod(actionType, packageName, className, classFilePath, classFileName, filePath, WEBProjConstants.AMC_VIEW_FILENAME, projectPath, widgetObj.getWidget(), eventConf);
				
				
				
//				PortletDefinition p = new PortletDefinition();
//				p.setPortletName(id);
//				DisplayName displayName = new DisplayName();
//				displayName.setDisplayName(name);
//				((List<DisplayName>)p.getDisplayNames()).add(displayName);
//				p.getPortletInfo().setTitle(name);
//				Supports supports = new Supports();
//				supports.setMimeType("text/html");
//				((List<Supports>)p.getSupports()).add(supports);
//				
//				PortletTreeItem pl = (PortletTreeItem)view.addPortletTreeNode(p);
//
//				//保存portlet
//				String categoryId = null;
//				if (selTI instanceof CategoryTreeItem){
//					categoryId = ((PortletDisplayCategory)selTI.getData()).getId();
//				}
//				PortalConnector.savePortletToXml(projectPath, projectModuleName, p, categoryId);
//				
//				//打开ds编辑器
//				view.openPortletEditor(pl);
//				} catch (Exception e) {
//				String title =PortalProjConstants.NEW_PORTLET;
//				String message = e.getMessage();
//				MessageDialog.openError(shell, title, message);
		}
		else return;
	}
}
