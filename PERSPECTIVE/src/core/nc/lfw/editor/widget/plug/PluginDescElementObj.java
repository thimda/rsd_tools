package nc.lfw.editor.widget.plug;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.lfw.editor.common.SubmitRulePropertyDescriptor;
import nc.lfw.editor.widget.WidgetEditor;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.PluginDesc;
import nc.uap.lfw.palette.ChildConnection;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * PluginDesc元素对象
 * @author dingrf
 *
 */
public class PluginDescElementObj extends LfwElementObjWithGraph {

	private static final long serialVersionUID = -5675125816072493807L;
	
	private WidgetElementObj widgetObj;
	// PluginDesc对象
	private PluginDesc plugin;
	//连接线
	private ChildConnection conn;
	// 当前图像
	private PluginDescElementFigure figure;

	public static final String PROP_PLUGIN_ID = "prop_plugin_id";
	
	public static final String PROP_PLUGIN_TYPE = "prop_plugin_type";
	
//	public static final String PROP_PLUGIN_SUBMITRULE = "prop_plugin_submitrule";

	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[2];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_PLUGIN_ID, "ID");
		pds[0].setCategory("基本");
		pds[1] = new ObjectComboPropertyDescriptor(PROP_PLUGIN_TYPE, "类型", Constant.PLUGINTYPE);
		pds[1].setCategory("基本");
//		pds[2] = new SubmitRulePropertyDescriptor(PROP_PLUGIN_SUBMITRULE, "提交规则", widgetObj.getWidget().getPagemeta(), plugin.getSubmitRule());
//		pds[2].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}

	public void setPropertyValue(Object id, Object value) {
		if (PROP_PLUGIN_ID.equals(id)) {
			plugin.setId((String)value);
			getFigure().getTitleFigure().getChildren().remove(1);
			getFigure().setTitleText((String)value, (String)value);
			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
			wdEditor.setDirtyTrue();
		} 
		else if (PROP_PLUGIN_TYPE.equals(id)){
			plugin.setPluginType((String) value);
			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
			wdEditor.setDirtyTrue();
		}
	}

	public Object getPropertyValue(Object id) {
		if (PROP_PLUGIN_ID.equals(id)) {
			return plugin.getId() == null ? "" : plugin.getId();
		}
		else if (PROP_PLUGIN_TYPE.equals(id)){
			return plugin.getPluginType() == null ? "" : plugin.getPluginType();
		}
		return null;
	}

//	public void setElementAttribute(Element ele) {
//		ele.setAttribute("id", getId());
//	}
//
//	public Object getEditableValue() {
//		return this;
//	}
//
//	public Element createElement(Document doc) {
//
//		return null;
//	}

	public PluginDesc getPlugin() {
		if (plugin == null)
			plugin = new PluginDesc(); 
		return plugin;
	}

	public void setPlugin(PluginDesc plugin) {
		this.plugin = plugin;
	}

	public PluginDescElementFigure getFigure() {
		return figure;
	}

	public void setFigure(PluginDescElementFigure figure) {
		this.figure = figure;
	}

	
	public WebElement getWebElement() {
		return null;
	}

	public WidgetElementObj getWidgetObj() {
		return widgetObj;
	}

	public void setWidgetObj(WidgetElementObj widgetObj) {
		this.widgetObj = widgetObj;
	}
	public ChildConnection getConn() {
		return conn;
	}

	public void setConn(ChildConnection conn) {
		this.conn = conn;
	}

	
//	public String getRefId() {
//		return refId;
//	}
//
//	public void setRefId(String refId) {
//		this.refId = refId;
//	}

}
