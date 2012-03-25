package nc.lfw.editor.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LfwBaseGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.lfw.editor.widget.plug.PluginDescElementObj;
import nc.lfw.editor.widget.plug.PlugoutDescElementObj;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * @author guoweic
 *
 */
public class WidgetGraph extends LfwBaseGraph {

	public static final String PROP_CHILD_ADD = "prop_child_add";
	public static final String PROP_CHILD_REMOVE = "prop_child_remove";
	
//	public static final String PROP_PLUGOUT_ADD = "prop_plugout_add";
//	public static final String PROP_PLUGOUT_REMOVE = "prop_plugout_remove";
//	public static final String PROP_PLUGIN_ADD = "prop_plugin_add";
//	public static final String PROP_PLUGIN_REMOVE = "prop_plugin_remove";
//	
	public static final String PROP_WIDGETPLUG_CHANGE = "prop_widgetplug_change";
	

	private LfwWidget widget = null;
	private List<WidgetElementObj> widgetCells = new ArrayList<WidgetElementObj>();

	/*plugoutDesc列表*/
	private List<PlugoutDescElementObj> plugoutCells = new ArrayList<PlugoutDescElementObj>();
	/*pluginDesc列表*/
	private List<PluginDescElementObj> pluginCells = new ArrayList<PluginDescElementObj>();
	
	public WidgetGraph() {
		super();
	}

	public boolean addWidgetCell(WidgetElementObj cell) {
		cell.setGraph(this);
		boolean b = widgetCells.add(cell);
		elementsCount++;
		if (b) {
			fireStructureChange(PROP_CHILD_ADD, cell);
		}
		return b;
	}

	public boolean removeWidgetCell(WidgetElementObj cell) {
		boolean b = widgetCells.remove(cell);
		elementsCount--;
		cell.setGraph(null);
		if (b) {
			fireStructureChange(PROP_CHILD_REMOVE, cell);
		}
		return b;
	}

	public List<WidgetElementObj> getWidgetCells() {
		return widgetCells;
	}

	/**
	 * 取消所有图形的所有子项选中状态
	 */
	public void unSelectAllLabels() {
		super.unSelectAllLabels();
		// Widget图形
		for (int i = 0, n = widgetCells.size(); i < n; i++) {
			WidgetElementObj widgetObj = widgetCells.get(i);
			widgetObj.getFigure().unSelectAllLabels();
		}
	}

	private String id = "";

	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = null;
		if(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWWidgetTreeItem && LFWTool.NEW_VERSION.equals(((LFWWidgetTreeItem)LFWAMCPersTool.getCurrentTreeItem()).getLfwVersion())){
			pds = new PropertyDescriptor[2];
			pds[0] = new NoEditableTextPropertyDescriptor(WEBProjConstants.PROP_ID, WEBProjConstants.PROP_ID);
			pds[0].setCategory(WEBProjConstants.BASIC);
			pds[1] = new TextPropertyDescriptor(WEBProjConstants.PROP_CONTROLLER_CLASS, WEBProjConstants.CONTROLLER_CLASS);
			pds[1].setCategory(WEBProjConstants.BASIC);
		}else{
			pds = new PropertyDescriptor[1];
			pds[0] = new TextPropertyDescriptor(WEBProjConstants.PROP_ID, WEBProjConstants.PROP_ID);
			pds[0].setCategory(WEBProjConstants.BASIC);
		}
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}

	public void setPropertyValue(Object id, Object value) {
		if(!(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWWidgetTreeItem && LFWTool.NEW_VERSION.equals(((LFWWidgetTreeItem)LFWAMCPersTool.getCurrentTreeItem()).getLfwVersion()))){
			if (WEBProjConstants.PROP_ID.equals(id)) {
				setId((String) value);
			}
		}
	}

	public Object getPropertyValue(Object id) {
		if(WEBProjConstants.PROP_ID.equals(id)) {
			return widget.getId() == null ? "" : widget.getId();
		}else if(WEBProjConstants.PROP_CONTROLLER_CLASS.equals(id)){
			return widget.getControllerClazz() == null ? "" : widget.getControllerClazz();
		}
		return null;
	}

	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		//TODO 目前还不能支持改名操作
//		if (!id.equals(widget.getId())) {
//			widget.setId(id);
//			WidgetEditor editor = WidgetEditor.getActiveWidgetEditor();
//			editor.setDirtyTrue();
//		}
	}
	
//	public boolean addPlugoutCell(PlugoutDescElementObj cell) {
//		boolean b = plugoutCells.add(cell);
//		if (b) {
//			fireStructureChange(PROP_PLUGOUT_ADD, cell);
//		}
//		return b;
//	}
//
//	public boolean removePlugoutCell(PlugoutDescElementObj cell) {
//		boolean b = plugoutCells.remove(cell);
//		cell.setWidgetObj(null);
//		if (b) {
//			for (int i = 0 ; i < plugoutCells.size() ; i++){
//				PlugoutDescElementObj plugoutCell = plugoutCells.get(i);
//				Point p = new Point();
//				p.x = plugoutCell.getLocation().x;
//				p.y = plugoutCell.getWidgetObj().getLocation().y + i * 40;
//				plugoutCell.setLocation(p);
//			}
//			fireStructureChange(PROP_PLUGOUT_REMOVE, cell);
//		}
//		return b;
//	}
//
//	public List<PlugoutDescElementObj> getPlugoutCells() {
//		return plugoutCells;
//	}
//	
//	public boolean addPluginCell(PluginDescElementObj cell) {
//		boolean b = pluginCells.add(cell);
//		if (b) {
//			fireStructureChange(PROP_PLUGIN_ADD, cell);
//		}
//		return b;
//	}
//	
//	public boolean removePluginCell(PluginDescElementObj cell) {
//		boolean b = pluginCells.remove(cell);
//		cell.setWidgetObj(null);
//		if (b) {
//			for (int i = 0 ; i < pluginCells.size() ; i++){
//				PluginDescElementObj pluginCell = pluginCells.get(i);
//				Point p = new Point();
//				p.x = pluginCell.getLocation().x;
//				p.y = pluginCell.getWidgetObj().getLocation().y + i * 40;
//				pluginCell.setLocation(p);
//			}
//			fireStructureChange(PROP_PLUGIN_REMOVE, cell);
//		}
//		return b;
//	}
//	
//	public List<PluginDescElementObj> getPluginCells() {
//		return pluginCells;
//	}
	
	public void fireWidgetPlugChange(LfwWidget widget){
		fireStructureChange(PROP_WIDGETPLUG_CHANGE, widget);
	}
}
