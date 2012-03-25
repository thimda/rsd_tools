package nc.uap.lfw.iframe;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.core.comp.IFrameComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * IFrame控件
 * @author zhangxya
 *
 */
public class IFrameElementObj extends LFWWebComponentObj{

	private static final long serialVersionUID = 6253081418703115641L;
	private IFrameComp iframecomp;
	public static final String PROP_IFRAME_ELEMENT ="iframe_element";
	public static final String PROP_SRC = "element_src";
	public static final String PROP_NAME = "element_name";
	public static final String PROP_BORDER = "element_border";
	public static final String PROP_FARMEBORDER = "element_farmeborder";
	public static final String PROP_SCROLLING = "element_scrolling";
	
	public IFrameComp getIframecomp() {
		return iframecomp;
	}
	
	public void setIframecomp(IFrameComp iframecomp) {
		this.iframecomp = iframecomp;
		fireStructureChange(PROP_IFRAME_ELEMENT, iframecomp);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[4];
		pds[0] = new TextPropertyDescriptor(PROP_SRC, "源地址");
		pds[0].setCategory("高级");
		pds[1] = new TextPropertyDescriptor(PROP_NAME, "名称");
		pds[1].setCategory("高级");
		//pds[2] = new ObjectComboPropertyDescriptor(PROP_FARMEBORDER,"边框是否三维显示", Constant.ISFARMEBORDER);
		pds[2] = new TextPropertyDescriptor(PROP_FARMEBORDER, "Frame边框");
		pds[2].setCategory("高级");
		pds[3] = new ObjectComboPropertyDescriptor(PROP_SCROLLING,"滚动条",Constant.SCROLLING);
		pds[3].setCategory("高级");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_SRC.equals(id))
			iframecomp.setSrc((String)value);
		if(PROP_NAME.equals(id))
			iframecomp.setName((String)value);
		else if(PROP_BORDER.equals(id)){
			iframecomp.setBorder((String)value);
		}
		else if(PROP_FARMEBORDER.equals(id)){
			iframecomp.setFrameBorder((String)value);
		}
		else if(PROP_SCROLLING.equals(id)){
			if(value == null)
				return;
			if(value.equals("显示"))
				iframecomp.setScrolling("yes");
			else if(value.equals("不显示"))
				iframecomp.setScrolling("no");
			else iframecomp.setScrolling("auto");
		}
	}
	public Object getPropertyValue(Object id) {
		if(PROP_SRC.equals(id))
			return iframecomp.getSrc() == null?"":iframecomp.getSrc();
		else if(PROP_NAME.equals(id))
			return iframecomp.getName() == null?"":iframecomp.getName();
		else if(PROP_BORDER.equals(id))
			return iframecomp.getBorder() == null?"":iframecomp.getBorder();
		else if(PROP_FARMEBORDER.equals(id))
			return iframecomp.getFrameBorder() == null? Constant.ISFARMEBORDER[0]:iframecomp.getFrameBorder();
		else if(PROP_SCROLLING.equals(id))
			return iframecomp.getScrolling() == null? Constant.SCROLLING[0]:iframecomp.getScrolling();
		else return super.getPropertyValue(id);
	}

	public WebElement getWebElement() {
		return iframecomp;
	}

}
