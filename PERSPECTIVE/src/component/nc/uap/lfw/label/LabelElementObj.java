package nc.uap.lfw.label;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.uap.lfw.core.comp.LabelComp;
import nc.uap.lfw.core.comp.WebElement;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * Label�ؼ�
 * @author zhangxya
 *
 */
public class LabelElementObj extends LFWWebComponentObj{

	public static final String PROP_LABEL_ELEMENT ="label_element";
	public static final String PROP_I18NNAME = "element_i18nname";
	public static final String PROP_LANGDIR = "element_langdir";
	public static final String PROP_COLOR = "element_color";
	public static final String PROP_STYLE = "element_style";
	public static final String PROP_WEIGHT = "element_weight";
	public static final String PROP_SIZE = "element_size";
	public static final String PROP_FAMILY = "element_family";
	public static final String PROP_INNERHTML = "element_innerHTML";
	public static final String PROP_TEXT = "element_Text";
	private static final long serialVersionUID = 6253081418703115641L;
	private LabelComp labelComp;

	public LabelComp getLabelComp() {
		return labelComp;
	}
	
	public void setLabelComp(LabelComp labelComp) {
		this.labelComp = labelComp;
		fireStructureChange(PROP_LABEL_ELEMENT, labelComp);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[9];
		pds[0] = new TextPropertyDescriptor(PROP_I18NNAME, "������ʾֵ");
		pds[0].setCategory("�߼�");
		pds[1] = new TextPropertyDescriptor(PROP_LANGDIR, "����Ŀ¼");
		pds[1].setCategory("�߼�");
		pds[2] = new TextPropertyDescriptor(PROP_FAMILY, "����");
		pds[2].setCategory("�߼�");
		pds[3] = new TextPropertyDescriptor(PROP_STYLE, "������ʽ");
		pds[3].setCategory("�߼�");
		pds[4] = new TextPropertyDescriptor(PROP_WEIGHT, "������");
		pds[4].setCategory("�߼�");
		pds[5] = new TextPropertyDescriptor(PROP_SIZE, "�����С");
		pds[5].setCategory("�߼�");
		pds[6] = new TextPropertyDescriptor(PROP_COLOR, "������ɫ");
		pds[6].setCategory("�߼�");
		pds[7] = new TextPropertyDescriptor(PROP_INNERHTML, "HTML����");
		pds[7].setCategory("�߼�");
		pds[8] = new TextPropertyDescriptor(PROP_TEXT, "��ʾֵ");
		pds[8].setCategory("�߼�");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_I18NNAME.equals(id))
			labelComp.setI18nName((String)value);
		else if(PROP_LANGDIR.equals(id))
			labelComp.setLangDir((String)value);
		else if(PROP_COLOR.equals(id))
			labelComp.setColor((String)value);
//		else if(PROP_STYLE.equals(id))
//			labelComp.setStyle((String)value);
//		else if(PROP_WEIGHT.equals(id))
//			labelComp.setWeight((String)value);
//		else if(PROP_SIZE.equals(id))
//			labelComp.setSize((String)value);
//		else if(PROP_FAMILY.equals(id))
//			labelComp.setFamily((String)value);
		else if(PROP_INNERHTML.equals(id))
			labelComp.setInnerHTML((String)value);
		else if(PROP_TEXT.equals(id))
			labelComp.setText((String)value);
		else
			super.setPropertyValue(id, value);
	}
	
	public Object getPropertyValue(Object id) {
		if(PROP_I18NNAME.equals(id))
			return labelComp.getI18nName() == null?"":labelComp.getI18nName();
		else if(PROP_LANGDIR.equals(id))
			return labelComp.getLangDir() == null?"":labelComp.getLangDir();
		else if(PROP_COLOR.equals(id))
			return labelComp.getColor() == null?"":labelComp.getColor();
//		else if(PROP_STYLE.equals(id))
//			return labelComp.getStyle() == null?"":labelComp.getStyle();
//		else if(PROP_WEIGHT.equals(id))
//			return labelComp.getWeight() == null?"":labelComp.getWeight();
//		else if(PROP_SIZE.equals(id))
//			return labelComp.getSize() == null?"":labelComp.getSize();
//		else if(PROP_FAMILY.equals(id))
//			return labelComp.getFamily() == null?"":labelComp.getFamily();
		else if(PROP_INNERHTML.equals(id))
			return labelComp.getInnerHTML() == null?"":labelComp.getInnerHTML();
		else if(PROP_TEXT.equals(id))
			return labelComp.getText() == null?"":labelComp.getText();
		else return super.getPropertyValue(id);
	}
	
	public WebElement getWebElement() {
		return labelComp;
	}

}
