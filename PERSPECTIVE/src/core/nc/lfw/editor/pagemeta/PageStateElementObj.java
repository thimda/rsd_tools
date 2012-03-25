package nc.lfw.editor.pagemeta;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.core.comp.WebElement;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 页面状态元素对象
 * 
 * @author guoweic
 *
 */
public class PageStateElementObj extends LfwElementObjWithGraph {

	private static final long serialVersionUID = -8484960457664556121L;
	
	// 当前图像
	private PageStateElementFigure figure;
	
	public static final String PROP_CHILD_ADD = "prop_child_add";
	public static final String PROP_CHILD_REMOVE = "prop_child_remove";

	public static final String PROP_CURRENT_PAGESTATE_VALUE = "CURRENT_PAGESTATE_VALUE";
	public static final String PROP_PAGESTATE_VALUE = "PAGESTATE_VALUE";
	public static final String PROP_PAGESTATE_NAME = "PAGESTATE_NAME";
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor[] pds = new PropertyDescriptor[3];
		pds[0] = new TextPropertyDescriptor(PROP_CURRENT_PAGESTATE_VALUE, "状态值");
		pds[0].setCategory("当前状态");
		pds[1] = new TextPropertyDescriptor(PROP_PAGESTATE_VALUE, "状态值");
		pds[1].setCategory("选中状态");
		pds[2] = new TextPropertyDescriptor(PROP_PAGESTATE_NAME, "名称");
		pds[2].setCategory("选中状态");
		return pds;
	}

	public void setPropertyValue(Object id, Object value) {
		if (PROP_CURRENT_PAGESTATE_VALUE.equals(id)) {
		} 
//		else if (PROP_PAGESTATE_VALUE.equals(id)) {  // 自动生成的值不允许自己修改
//			if (null != currentItem)
//				currentItem.setKey((String) value);
//		} 
		else if (PROP_PAGESTATE_NAME.equals(id)) {
		}
		PagemetaEditor.getActivePagemetaEditor().setDirtyTrue();
	}

	public Object getPropertyValue(Object id) {
		if (PROP_CURRENT_PAGESTATE_VALUE.equals(id)) {
		} else if (PROP_PAGESTATE_VALUE.equals(id)) {
		} else if (PROP_PAGESTATE_NAME.equals(id)) {
		}
		return "";
	}

	public void setElementAttribute(Element ele) {
		ele.setAttribute("id", getId());
	}

	public Object getEditableValue() {
		return this;
	}

	public Element createElement(Document doc) {

		return null;
	}

	public PageStateElementFigure getFigure() {
		return figure;
	}

	public void setFigure(PageStateElementFigure figure) {
		this.figure = figure;
	}

	
	public WebElement getWebElement() {
		return null;
	}

}
