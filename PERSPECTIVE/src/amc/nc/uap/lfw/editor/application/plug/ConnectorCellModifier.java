package nc.uap.lfw.editor.application.plug;


import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PluginDesc;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.editor.application.ApplicationEditor;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ConnectorCellModifier
 * 
 * @author dingrf
 *
 */
public class ConnectorCellModifier implements ICellModifier {
	
	private ConnectorPropertiesView view = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private Connector attr = null;
		public CellModifiCommand(String property, Object value, Connector attr) {
			super("修改属性");
			this.property = property;
			this.value = value;
			this.attr = attr;
		}
		
		public void execute() {
			oldValue = getValue(attr, property);
			redo();
		}
		
		public void redo() {
			modifyAttr(attr, property, value);
		}
		
		public void undo() {
			modifyAttr(attr, property, oldValue);
		}
	}
	
	/**列名称*/
	public static final String[] colNames = {"输出Window", "输出View", "plugout", "输入Window", "输入View", "plugin", "关系映射 "};
	
	
	public ConnectorCellModifier(ConnectorPropertiesView view) {
		super();
		this.view = view;
	}
	
	/**
	 * 是否可修改
	 */
	public boolean canModify(Object element, String property) {
		if (view.isCanEdit())
			return true;
		else
			return false;
	}

	/**
	 * 取列值
	 */
	public Object getValue(Object element, String property) {
		if(element instanceof Connector){
			Connector prop = (Connector)element;
			if(colNames[0].equals(property)){
				return prop.getSourceWindow() == null? "" : prop.getSourceWindow();
			}
			else if(colNames[1].equals(property)){
				return prop.getSource() == null?"":prop.getSource();
			}	
			else if(colNames[2].equals(property)){
				return prop.getPlugoutId() == null?"":prop.getPlugoutId();
			}	
			else if(colNames[3].equals(property)){
				return prop.getTargetWindow() == null?"":prop.getTargetWindow();
			}	
			else if(colNames[4].equals(property)){
				return prop.getTarget() == null?"":prop.getTarget();
			}	
			else if(colNames[5].equals(property)){
				return prop.getPluginId() == null?"":prop.getPluginId();
			}	
			else if(colNames[6].equals(property)){
				return "";
			}	
		}
		return "";
	}

	/**
	 * 修改列值
	 */
	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof Connector){
			Connector prop = (Connector)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			ApplicationEditor editor = (ApplicationEditor)ApplicationEditor.getActiveEditor();
			editor.setDirtyTrue();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	
	private void modifyAttr(Connector prop, String property,Object value){
		if (colNames[0].equals(property)) {
			prop.setSourceWindow((String) value);
			getSourceView(prop);
		} 
		else if (colNames[1].equals(property)) {
			prop.setSource((String) value);
			getPlugout(prop);
		}
		else if (colNames[2].equals(property)) {
			prop.setPlugoutId((String) value);
		}
		else if (colNames[3].equals(property)) {
			prop.setTargetWindow((String) value);
			getTargetView(prop);
		}
		else if (colNames[4].equals(property)) {
			prop.setTarget((String) value);
			getPlugin(prop);
		}
		else if (colNames[5].equals(property)) {
			prop.setPluginId((String) value);
		}
		view.getTv().update(prop, null);
	}

	private void getSourceView(Connector prop) {

		String windowId = prop.getSourceWindow();
//		ApplicationEditor editor = ApplicationEditor.getActiveEditor();
//		ApplicationConf app = editor.getGraph().getApplication();
//		WindowConf win = app.getWindowConf(windowId);
		PageMeta win = LFWAMCPersTool.getPageMetaById(windowId);
		if(win == null){
			return;
		}
		String[] views = new String[win.getWidgets().length];
		int i = 0;
		for (LfwWidget widget : win.getWidgets()){
			views[i] = widget.getId();
			i ++;
		}
		view.getSourceCellEditor().setObjectItems(views);
	}
	
	private void getTargetView(Connector prop) {
		String windowId = prop.getTargetWindow();
//		ApplicationEditor editor = ApplicationEditor.getActiveEditor();
//		ApplicationConf app = editor.getGraph().getApplication();
//		WindowConf win = app.getWindowConf(windowId);
		PageMeta win = LFWAMCPersTool.getPageMetaById(windowId);
		String[] views = new String[win.getWidgets().length];
		int i = 0;
		for (LfwWidget widget : win.getWidgets()){
			views[i] = widget.getId();
			i ++;
		}
		view.getTargetCellEditor().setObjectItems(views);
	}
	
	private void getPlugout(Connector prop) {
		String windowId = prop.getSourceWindow();
		String viewId = prop.getSource();
		if (windowId == null || viewId == null){
			view.getPlugoutCellEditor().setObjectItems(new String[]{""});
			return;
		}
			
//		ApplicationEditor editor = ApplicationEditor.getActiveEditor();
//		ApplicationConf app = editor.getGraph().getApplication();
		PageMeta win = LFWAMCPersTool.getPageMetaById(windowId);
		LfwWidget widget = win.getWidget(viewId);
		String[] plugouts = new String[widget.getPlugoutDescs() == null ? 0 : widget.getPlugoutDescs().size()];
		if (plugouts.length > 0){
			int i = 0;
			for (PlugoutDesc plugout : widget.getPlugoutDescs()){
				plugouts[i] = plugout.getId();
				i ++;
			}
		} 
		view.getPlugoutCellEditor().setObjectItems(plugouts);
	}
	
	private void getPlugin(Connector prop) {
		String windowId = prop.getTargetWindow();
		String viewId = prop.getTarget();
		if (windowId == null || viewId == null){
			view.getPluginCellEditor().setObjectItems(new String[]{""});
			return;
		}
		
//		ApplicationEditor editor = ApplicationEditor.getActiveEditor();
//		ApplicationConf app = editor.getGraph().getApplication();
//		WindowConf win = app.getWindowConf(windowId);
		PageMeta win = LFWAMCPersTool.getPageMetaById(windowId);
		LfwWidget widget = win.getWidget(viewId);
		String[] plugins = new String[widget.getPluginDescs() == null ? 0 : widget.getPluginDescs().size()];
		if (plugins.length > 0){
			int i = 0;
			for (PluginDesc plugin : widget.getPluginDescs()){
				plugins[i] = plugin.getId();
				i ++;
			}
		}
		view.getPluginCellEditor().setObjectItems(plugins);
	}
}

