package nc.uap.lfw.linkcomp;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.uap.lfw.core.comp.LinkComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * 链接model
 * @author zhangxya
 *
 */
public class LinkCompElementObj extends LFWWebComponentObj{

	private static final long serialVersionUID = 6253081418703115641L;

	public static final String PROP_LINKCOMP_ELEMENT ="linkcomp_element";
	public static final String PROP_I18NNAME = "element_i18nname";
	public static final String PROP_HREF = "element_href";
	public static final String PROP_HASIMAG = "element_hasimg";
	public static final String PROP_IMAGE = "element_image";
	public static final String PROP_TARGET = "element_target";
	
	private LinkComp linkComp;
	
	public LinkComp getLinkComp() {
		return linkComp;
	}
	
	public void setLinkComp(LinkComp linkComp) {
		this.linkComp = linkComp;
		fireStructureChange(PROP_LINKCOMP_ELEMENT, linkComp);
	}


	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[5];
		pds[0] = new TextPropertyDescriptor(PROP_I18NNAME, "多语显示值");
		pds[0].setCategory("高级");
		pds[1] = new TextPropertyDescriptor(PROP_HREF, "链接地址");
		pds[1].setCategory("高级");
		pds[2] = new ComboBoxPropertyDescriptor(PROP_HASIMAG, "是否包含图形", Constant.ISLAZY);
		pds[2].setCategory("高级");
		pds[3] = new TextPropertyDescriptor(PROP_IMAGE, "图形");
		pds[3].setCategory("高级");
		pds[4] = new TextPropertyDescriptor(PROP_TARGET, "目标地址");
		pds[4].setCategory("高级");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_I18NNAME.equals(id))
			linkComp.setI18nName((String)value);
		else if(PROP_HREF.equals(id))
			linkComp.setHref((String)value);
		else if(PROP_HASIMAG.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			linkComp.setHasImg(truevalue);
		}
		else if(PROP_IMAGE.equals(id))
			linkComp.setImage((String)value);
		else if(PROP_TARGET.equals(id))
			linkComp.setTarget((String)value);
		else 
			super.setPropertyValue(id, value);
	}
	
	public Object getPropertyValue(Object id) {
		if(PROP_I18NNAME.equals(id))
			return linkComp.getI18nName() == null?"":linkComp.getI18nName();
		else if(PROP_HREF.equals(id))
			return linkComp.getHref() == null?"":linkComp.getHref();
		else if(PROP_HASIMAG.equals(id))
			return linkComp.isHasImg() == true? new Integer(0):new Integer(1);
		else if(PROP_IMAGE.equals(id))
			return linkComp.getImage() == null?"":linkComp.getImage();
		else if(PROP_TARGET.equals(id))
			return linkComp.getTarget() == null?"":linkComp.getTarget();
		return super.getPropertyValue(id);
	}

	public WebElement getWebElement() {
		return linkComp;
	}

}
