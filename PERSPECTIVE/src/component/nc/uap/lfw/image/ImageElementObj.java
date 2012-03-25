package nc.uap.lfw.image;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.uap.lfw.core.comp.ImageComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * 图形编辑控件
 * @author zhangxya
 *
 */
public class ImageElementObj extends LFWWebComponentObj{
	
	private static final long serialVersionUID = 6253081418703115641L;
	
	public static final String PROP_IMAGE_ELEMENT ="image_element";
	public static final String PROP_IMAGE1 = "element_IMAGE1";
	public static final String PROP_IMAGE2 = "element_IMAGE2";
	public static final String PROP_ALT = "element_alt";
	public static final String PROP_IMATGEINACT = "element_imageInact";
	public static final String PROP_FLOATRIGHT = "element_floatRight";
	public static final String PROP_FLOATLEFT= "element_floatLeft";
	
	private ImageComp imageComp;
	
	public ImageComp getImageComp() {
		return imageComp;
	}
	
	public void setImageComp(ImageComp imageComp) {
		this.imageComp = imageComp;
		fireStructureChange(PROP_IMAGE_ELEMENT, imageComp);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[6];
	
		pds[0] = new TextPropertyDescriptor(PROP_IMAGE1, "图片处于未选中效果样式图片的绝对路径");
		pds[0].setCategory("高级");
		pds[1] = new TextPropertyDescriptor(PROP_IMAGE2,"图片处于选中效果的样式图片的绝对路径");
		pds[1].setCategory("高级");
		pds[2] = new TextPropertyDescriptor(PROP_ALT,"图片提示");
		pds[2].setCategory("高级");
		pds[3] = new TextPropertyDescriptor(PROP_IMATGEINACT,"图片处于禁用效果的样式图片的绝对路径");
		pds[3].setCategory("高级");
		pds[4] = new ComboBoxPropertyDescriptor(PROP_FLOATRIGHT,"是否右对齐", Constant.ISLAZY);
		pds[4].setCategory("高级");
		pds[5] = new ComboBoxPropertyDescriptor(PROP_FLOATLEFT,"是否左对齐", Constant.ISLAZY);
		pds[5].setCategory("高级");
	
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_IMAGE1.equals(id))
			imageComp.setImage1((String)value);
		if(PROP_IMAGE2.equals(id))
			imageComp.setImage2((String)value);
		else if(PROP_ALT.equals(id)){
			imageComp.setAlt((String)value);
		}
		else if(PROP_IMATGEINACT.equals(id)){
			imageComp.setImageInact((String)value);		
		}
		else if(PROP_FLOATRIGHT.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			imageComp.setFloatRight(truevalue);
		}
		else if(PROP_FLOATLEFT.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			imageComp.setFloatLeft(truevalue);
		}
	}
	public Object getPropertyValue(Object id) {
		if(PROP_IMAGE1.equals(id))
			return imageComp.getImage1() == null?"":imageComp.getImage1();
		else if(PROP_IMAGE2.equals(id))
			return imageComp.getImage2() == null?"":imageComp.getImage2();
		else if(PROP_ALT.equals(id))
			return imageComp.getAlt() == null?"":imageComp.getAlt();
		else if(PROP_IMATGEINACT.equals(id))
			return imageComp.getImageInact() == null?"":imageComp.getImageInact();
		else if(PROP_FLOATRIGHT.equals(id))
			return imageComp.isFloatRight() == true? new Integer(0):new Integer(1);
		else if(PROP_FLOATLEFT.equals(id))
			return imageComp.isFloatLeft() == true? new Integer(0):new Integer(1);
		else return super.getPropertyValue(id); 
	}
	
	public WebElement getWebElement() {
		return imageComp;
	}
}
