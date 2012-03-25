package nc.uap.lfw.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.form.core.FormEditor;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * form ��model
 * @author zhangxya
 *
 */
public class FormElementObj extends LFWWebComponentObj{
	private static final long serialVersionUID = 6253081418703115641L;
	
	public static final String PROP_UPDATE_CELL_PROPS = "update_cell_props";
	public static final String PROP_FORM_ELEMENT ="form_element";
	public static final String PROP_DATASET = "element_DATASET";
	public static final String PROP_WITHFORM = "element_withForm";
	public static final String PROP_ROWHEIGHT = "element_rowHeight";
	public static final String PROP_BGCOLOR = "element_backgroundColor";
	public static final String PROP_RENDERHIDDENELE = "element_renderHiddenEle";
//	public static final String PROP_POSITION = "element_POSTTION";
	public static final String PROP_COLUMNCOUNT = "element_COLUMNCOUNT";
	public static final String PROP_RENDERTYPE= "element_RENDERTYPE";
	public static final String Form_ADD_CELL_PROPS = "add_form_props";
	public static final String PROP_ELEWIDTH="element_elewidth";
	public static final String PROP_LABELMINWIDTH="element_labelMinWidth";
	public static final String PROP_CAPTION = "element_CAPTION";
	private List<FormElement> props = new ArrayList<FormElement>();
	private FormComp formComp;
	private Dataset ds;
	private String[] renderType = {"�̶�����", "��ʽ����", "�̶���ʾ����"};
	
	public FormComp getFormComp() {
		return formComp;
	}
	
	public void setFormComp(FormComp formComp) {
		this.formComp = formComp;
		fireStructureChange(PROP_FORM_ELEMENT,  formComp);
	}

	public Dataset getDs() {
		return ds;
	}

	public void setDs(Dataset ds) {
		this.ds = ds;
	}
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor a1 =  new NoEditableTextPropertyDescriptor(PROP_DATASET,"����Դ");
		a1.setCategory("�߼�");
		al.add(a1);
//		PropertyDescriptor a2 = new TextPropertyDescriptor(PROP_POSITION, "λ��");
//		a2.setCategory("�߼�");
//		al.add(a2);
		if(formComp.getRenderType() == 1 || formComp.getRenderType() == 3){
			PropertyDescriptor a3 = new TextPropertyDescriptor(PROP_COLUMNCOUNT, "����");
			a3.setCategory("�߼�");
			al.add(a3);
		}
		PropertyDescriptor a4 = new ObjectComboPropertyDescriptor(PROP_RENDERTYPE, "��Ⱦ����", renderType);
		a4.setCategory("�߼�");
		al.add(a4);
		PropertyDescriptor a5 = new TextPropertyDescriptor(PROP_ROWHEIGHT, "ÿ�и߶�");
		a5.setCategory("�߼�");
		al.add(a5);
		PropertyDescriptor a6 = new ComboBoxPropertyDescriptor(PROP_WITHFORM,"�Ƿ���HTML��", Constant.ISLAZY);
		a6.setCategory("�߼�");
		al.add(a6);
		PropertyDescriptor a7 = new TextPropertyDescriptor(PROP_BGCOLOR, "������");
		a7.setCategory("�߼�");
		al.add(a7);
		PropertyDescriptor a8 = new ComboBoxPropertyDescriptor(PROP_RENDERHIDDENELE,"�Ƿ���Ⱦ���صı�Ԫ��", Constant.ISLAZY);
		a8.setCategory("�߼�");
		al.add(a8);
		PropertyDescriptor a9 = new TextPropertyDescriptor(PROP_ELEWIDTH,"�п�");
		a9.setCategory("�߼�");
		al.add(a9);
		
		PropertyDescriptor a10 = new TextPropertyDescriptor(PROP_LABELMINWIDTH,"��С��ǩ���");
		a10.setCategory("�߼�");
		al.add(a10);
		
		TextPropertyDescriptor captionDes = new TextPropertyDescriptor(PROP_CAPTION,"����");
		captionDes.setCategory("����");
		al.add(captionDes);
			
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	
	public void addProp(FormElement prop){
		props.add(prop);
		fireStructureChange(Form_ADD_CELL_PROPS, prop);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_DATASET.equals(id)){
			formComp.setDataset((String)value);
			fireStructureChange(PROP_FORM_ELEMENT,  formComp);
		}
//		else if(PROP_POSITION.equals(id))
//			formComp.setPosition((String)value);
		else if(PROP_COLUMNCOUNT.equals(id))
			formComp.setColumnCount(Integer.valueOf((String)value));
		else if(PROP_RENDERTYPE.equals(id)){
			if(value.equals(renderType[0]))
				formComp.setRenderType(1);
			else if(value.equals(renderType[1]))
				formComp.setRenderType(2);
			else 
				if(value.equals(renderType[2]))
					formComp.setRenderType(3);
		}
		else if(PROP_CAPTION.equals(id)){
			String oldValue = formComp.getCaption();
			if((oldValue == null && value != null)  || (oldValue != null && value != null && !oldValue.equals(value))){
				formComp.setCaption((String)value);
				FormEditor.getActiveEditor().refreshTreeItemText(formComp);
			}
		}
		else if(PROP_ROWHEIGHT.equals(id))
			formComp.setRowHeight(Integer.valueOf((String)value));
		else if(PROP_WITHFORM.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			formComp.setWithForm(truevalue);
		}
		else if(PROP_BGCOLOR.equals(id))
			formComp.setBackgroundColor((String)value);
		else if(PROP_RENDERHIDDENELE.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			formComp.setRenderHiddenEle(truevalue);
		}
		else if(PROP_ELEWIDTH.equals(id)){
			Integer width = Integer.valueOf((String)value);
			formComp.setEleWidth(width);
		}
		else if(PROP_LABELMINWIDTH.equals(id)){
			formComp.setLabelMinWidth((Integer.valueOf((String)value)));
		}
	}
	public Object getPropertyValue(Object id) {
		if(PROP_ID.equals(id))
			return formComp.getId() == null?"":formComp.getId();
		else if(PROP_DATASET.equals(id))
				return formComp.getDataset()==null?"":formComp.getDataset();
//		else if(PROP_POSITION.equals(id))
//			return formComp.getPosition() == null?"":formComp.getPosition();
		else if(PROP_COLUMNCOUNT.equals(id))
			return formComp.getColumnCount() == null?"":formComp.getColumnCount().toString();
		else if(PROP_RENDERTYPE.equals(id)){
			Integer newvalue = new Integer(formComp.getRenderType());
			if(newvalue == 1)
				return renderType[0];
			else if(newvalue == 2)
				return renderType[1];
			else if(newvalue == 3)
				return renderType[2];
			return newvalue.toString();
		}
		else if(PROP_CAPTION.equals(id))
			return formComp.getCaption() == null?"":formComp.getCaption();
//		else if(PROP_HEIGHT.equals(id))
//				return formComp.getHeight()==null?"":formComp.getHeight();
		else if(PROP_ROWHEIGHT.equals(id))
			return   String.valueOf(formComp.getRowHeight());
		else if(PROP_WITHFORM.equals(id))
			return formComp.isWithForm() == true?new Integer(0): new Integer(1);
		else if(PROP_BGCOLOR.equals(id))
			return formComp.getBackgroundColor() == null?"":formComp.getBackgroundColor();
		else if(PROP_RENDERHIDDENELE.equals(id))
			return formComp.isRenderHiddenEle() == true?new Integer(0): new Integer(1);
		else if(PROP_ELEWIDTH.equals(id))
			return String.valueOf(formComp.getEleWidth());
		else if(PROP_LABELMINWIDTH.equals(id)){
			return String.valueOf(formComp.getLabelMinWidth());
		}
		else return super.getPropertyValue(id);
	}
	

	public void firePropUpdate(FormElement prop){
		fireStructureChange(PROP_UPDATE_CELL_PROPS, prop);
	}
	
	public WebElement getWebElement() {
		return formComp;
	}
	
;
}
