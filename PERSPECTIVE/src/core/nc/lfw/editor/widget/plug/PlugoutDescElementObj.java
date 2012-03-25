package nc.lfw.editor.widget.plug;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.lfw.editor.widget.WidgetEditor;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.palette.ChildConnection;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * PlugoutDesc元素对象
 * @author dingrf
 *
 */
public class PlugoutDescElementObj extends LfwElementObjWithGraph {

	private static final long serialVersionUID = -5675125816072493807L;
	
	private WidgetElementObj widgetObj;
	// PlugoutDesc对象
	private PlugoutDesc plugout;
	// 当前图像
	private PlugoutDescElementFigure figure;
	//连接线
	private ChildConnection conn;

	public static final String PROP_PLUGOUT_ID = "prop_plugout_id";
	
	public static final String PROP_PLUGOUT_TYPE = "prop_plugout_type";
	

	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[2];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_PLUGOUT_ID, "ID");
		pds[0].setCategory("基本");
		pds[1] = new ObjectComboPropertyDescriptor(PROP_PLUGOUT_TYPE, "类型", Constant.PLUGOUTTYPE);
		pds[1].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}

	public void setPropertyValue(Object id, Object value) {
//		if (PROP_PLUGOUT_ID.equals(id)) {
//			plugout.setId((String)value);
//			getFigure().getTitleFigure().getChildren().remove(1);
//			getFigure().setTitleText((String)value, (String)value);
//			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
//			wdEditor.setDirtyTrue();
//		} 
//		else 
		if (PROP_PLUGOUT_TYPE.equals(id)){
			plugout.setPlugoutType((String) value);
			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
			wdEditor.setDirtyTrue();
		}
	}

	public Object getPropertyValue(Object id) {
		if (PROP_PLUGOUT_ID.equals(id)) {
			return plugout.getId() == null ? "" : plugout.getId();
		}
		else if (PROP_PLUGOUT_TYPE.equals(id)){
			return plugout.getPlugoutType() == null ? "" : plugout.getPlugoutType();
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

	public PlugoutDesc getPlugout() {
		if (plugout == null)
			plugout = new PlugoutDesc(); 
		return plugout;
	}

	public void setPlugout(PlugoutDesc plugout) {
		this.plugout = plugout;
	}

	public PlugoutDescElementFigure getFigure() {
		return figure;
	}

	public void setFigure(PlugoutDescElementFigure figure) {
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
