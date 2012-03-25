package nc.uap.lfw.textcomp;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.core.comp.CheckBoxComp;
import nc.uap.lfw.core.comp.CheckboxGroupComp;
import nc.uap.lfw.core.comp.RadioComp;
import nc.uap.lfw.core.comp.RadioGroupComp;
import nc.uap.lfw.core.comp.ReferenceComp;
import nc.uap.lfw.core.comp.TextAreaComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.comp.text.ComboBoxComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * textcomp 的model
 * @author zhangxya
 *
 */
public class TextCompElementObj extends LFWWebComponentObj{

	public static final String PROP_TEXTCOMP_ELEMENT ="textcomp_element";
	private static final long serialVersionUID = 6253081418703115641L;
	private TextComp textComp;
	private ComboBoxComp comboboxcomp;
	private ReferenceComp referencecomp;
	private TextAreaComp textareaComp;
	
	public static final String PROP_VALUE = "element_VALUE";
	public static final String PROP_TYPE = "element_TYPE";
	public static final String PROP_MAXVALUE = "element_MAXVALUE";
	public static final String PROP_MINVALUE = "element_MINVALUE";
	public static final String PROP_PRECISION= "element_PRECISION";
	public static final String PROP_MAXLENGTH= "element_MAXLENGTH";
	
	//公用属性
	public static final String PROP_TEXT= "element_TEXT";
	public static final String PROP_I18NNAME= "element_I18NNAME";
	public static final String PROP_LANGDIR= "element_LANGDIR";
	public static final String PROP_FOCUE= "element_FOCUS";
	public static final String PROP_TEXTALGIN= "element_TEXTALIGN";
	public static final String PROP_TEXTWIDTH= "element_TEXTWIDTH";
	
	//text
	public static final String PROP_TIP = "element_TIP";
		
	//用户combodata
	public static final String PROP_REFCOMBODATA= "element_REFCOMBODATA";
	public static final String PROP_SELECTONLY= "element_REFCOMSELECTONLY";
	public static final String ALLOWEXTENDVALUE= "element_ALLOWEXTENDVALUE";
	
	//用于参照
	public static final String PROP_REFCODE= "element_REFCODE";
	public static final String REFERENCE_TIP = "element_REF_TIP";
	
	//用于radio
	//public static final String PROP_TEXT= "element_TEXT";
	public static final String PROP_CHECKED= "element_CHECKED";
	public static final String PROP_GROUP= "element_GROUP";

	//checkbox
	public static final String CHECKBOX_I18NNAME = "element_I18NNAME";
	public static final String CHECKBOX_CHECKED = "checkboxelement_CHECKED";
	public static final String CHECKBOX_DATATYPE = "checkbox_datatype";
	
	//textarea
	public static final String TEXTAREA_ROWS = "textarea_rows";
	public static final String TEXTAREA_COLS="textarea_cols";
	public static final String TEXTAREA_TIP = "element_TEXTAREA_TIP";
	
	//checkboxgroupcomp
	public static final String CHECKBOXGROUP_COMBODATAID = "CHECKBOXGROUP_COMBODATAID";
	public static final String CHECKBOXGROUP_VALUE = "CHECKBOXGROUP_VALUE";
	public static final String CHECKBOXGROUP_TABINDEX = "CHECKBOXGROUP_TABINDEX";
	public static final String CHECKBOXGROUP_SEPWIDTH = "CHECKBOXGROUP_SEPWIDTH";
	
	//radiogroupcomp
	public static final String RADIOGROUP_COMBODATAID = "RADIOGROUP_COMBODATAID";
	public static final String RADIOGROUP_VALUE = "RADIOGROUP_VALUE";
	public static final String RADIOGROUP_TABINDEX = "RADIOGROUP_TABINDEX";
	public static final String RADIOGROUP_SEPWIDTH = "RADIOGROUP_SEPWIDTH";
	public static final String RADIOGROUP_INDEX = "RADIOGROUP_INDEX";
	
	//filecomp
	public static final String FILECOMP_FILESIZE = "FILECOMP_FILESIZE";

	public TextAreaComp getTextareaComp() {
		return textareaComp;
	}

	public void setTextareaComp(TextAreaComp textareaComp) {
		this.textareaComp = textareaComp;
	}
	
	public TextComp getTextComp() {
		return textComp;
	}
	
	public void setTextComp(TextComp textComp) {
		this.textComp = textComp;
		fireStructureChange(PROP_TEXTCOMP_ELEMENT, textComp);
	}

	public ReferenceComp getReferencecomp() {
		return referencecomp;
	}

	public void setReferencecomp(ReferenceComp referencecomp) {
		this.referencecomp = referencecomp;
	}

	public CheckBoxComp getCheckboxcomp() {
		return checkboxcomp;
	}

	public void setCheckboxcomp(CheckBoxComp checkboxcomp) {
		this.checkboxcomp = checkboxcomp;
	}

	public RadioComp getRadiocomp() {
		return radiocomp;
	}

	public void setRadiocomp(RadioComp radiocomp) {
		this.radiocomp = radiocomp;
	}
	private CheckBoxComp checkboxcomp;
	private RadioComp radiocomp;
	

	public ComboBoxComp getComboboxcomp() {
		return comboboxcomp;
	}

	public void setComboboxcomp(ComboBoxComp comboboxcomp) {
		this.comboboxcomp = comboboxcomp;
	}
	
	private CheckboxGroupComp checkboxGroupComp;
	public CheckboxGroupComp getCheckboxGroupComp() {
		return checkboxGroupComp;
	}

	public void setCheckboxGroupComp(CheckboxGroupComp checkboxGroupComp) {
		this.checkboxGroupComp = checkboxGroupComp;
	}
	private RadioGroupComp radioGroupComp;



	public RadioGroupComp getRadioGroupComp() {
		return radioGroupComp;
	}

	public void setRadioGroupComp(RadioGroupComp radioGroupComp) {
		this.radioGroupComp = radioGroupComp;
	}

	private String[] getRefCombdata(){
		return LFWPersTool.getRefCombdata();
	}
	
	private String[] getRefnodes(){
		return LFWPersTool.getRefNodes();
}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		int prop = 0;
		//StringText类型
		if(textComp.getEditorType().equals(Constant.TEXTTYPE[0])){
			prop = 2;
		}
		//radiocomp
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[13])){
			prop = 3;
		}
		//IntegerText
		else if (textComp.getEditorType().equals(Constant.TEXTTYPE[1])){
			prop = 4;
		}
		//DECIMALTEXT
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[3])){
			prop = 3;
		}
		//CHECKBOX
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[2])){
			prop = 4;
		}
		//filecomp
		else if(textComp.getEditorType().equals("FileComp")){
			prop = 1;
		}
		//combobox
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[9])){
			prop = 4;
		}
		//reference
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[8])){
			prop = 2;
			
		}
		//textarea
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[10])){
			prop = 3;
		}
		//checkboxgroup
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[12])){
			prop = 4;
		}
		//radiogroup
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[7])){
			prop = 5;
		}
		PropertyDescriptor[] pds = new PropertyDescriptor[7 + prop];
		pds[0] = new ObjectComboPropertyDescriptor(PROP_TYPE,"类型", Constant.TEXTTYPE);
		pds[0].setCategory("基本");
		pds[1] = new TextPropertyDescriptor(PROP_TEXT,"显示值");
		pds[1].setCategory("基本");
		pds[2] = new TextPropertyDescriptor(PROP_I18NNAME,"多语显示值");
		pds[2].setCategory("基本");
		pds[3] = new TextPropertyDescriptor(PROP_LANGDIR,"多语目录");
		pds[3].setCategory("基本");
		
		pds[4] = new ObjectComboPropertyDescriptor(PROP_TEXTALGIN, "标签位置", Constant.LABELPOSITION);
		pds[4].setCategory("基本");
		pds[5] = new TextPropertyDescriptor(PROP_TEXTWIDTH,"标签宽度");
		pds[5].setCategory("基本");
		pds[6] = new ComboBoxPropertyDescriptor(PROP_FOCUE, "是否聚焦", Constant.ISLAZY);
		pds[6].setCategory("基本");	
		
		if(textComp.getEditorType().equals(Constant.TEXTTYPE[0])){
			//prop = 2;
			pds[7] = new TextPropertyDescriptor(PROP_VALUE,"输入值");
			pds[7].setCategory("高级");
			
			pds[8] = new TextPropertyDescriptor(PROP_TIP,"提示");
			pds[8].setCategory("高级");
			
			if(textComp instanceof CheckBoxComp || textComp instanceof TextAreaComp || 	textComp instanceof RadioComp || textComp instanceof ReferenceComp || 
					textComp instanceof ComboBoxComp || textComp instanceof RadioGroupComp || textComp instanceof CheckboxGroupComp){
				String id = textComp.getId();
				textComp = new TextComp();
				textComp.setId(id);
				textComp.setEditorType(textComp.getEditorType());
				setTextComp(textComp);
			}
		}
		//IntegerText
		else if (textComp.getEditorType().equals(Constant.TEXTTYPE[1])){
			pds[7] = new TextPropertyDescriptor(PROP_VALUE,"输入值");
			pds[7].setCategory("高级");
			pds[8] = new TextPropertyDescriptor(PROP_MAXVALUE,"最大值");
			pds[8].setCategory("高级");
			pds[9] = new TextPropertyDescriptor(PROP_MINVALUE, "最小值");
			pds[9].setCategory("高级");
			pds[10] = new TextPropertyDescriptor(PROP_TIP,"提示");
			pds[10].setCategory("高级");
			if(textComp instanceof CheckBoxComp ||  textComp instanceof TextAreaComp || textComp instanceof RadioComp || textComp instanceof ReferenceComp || 
					textComp instanceof ComboBoxComp || textComp instanceof RadioGroupComp || textComp instanceof CheckboxGroupComp){
				String id = textComp.getId();
				textComp = new TextComp();
				textComp.setId(id);
				textComp.setEditorType(textComp.getEditorType());
				setTextComp(textComp);
			}
		}
		//radiocomp
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[13])){
			pds[7] = new TextPropertyDescriptor(PROP_VALUE,"输入值");
			pds[7].setCategory("高级");
			pds[8] = new ComboBoxPropertyDescriptor(PROP_CHECKED, "是否被选中", Constant.ISLAZY);
			pds[8].setCategory("高级");
			pds[9] = new TextPropertyDescriptor(PROP_GROUP, "所属组");
			pds[9].setCategory("高级");
			if(radiocomp == null){
				if(textComp instanceof RadioComp)
					radiocomp = (RadioComp) textComp;
				else{
					radiocomp = new RadioComp();
					radiocomp.setId(textComp.getId());
					radiocomp.setEditorType(textComp.getEditorType());
				}
			}
			else 
				radiocomp.setEditorType(textComp.getEditorType());
			setTextComp(radiocomp);
				
		}
		//checkboxgroup
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[12])){
			pds[7] = new ObjectComboPropertyDescriptor(CHECKBOXGROUP_COMBODATAID, "引用下拉框数据", getRefCombdata());
			pds[7].setCategory("高级");
			
			pds[8] = new TextPropertyDescriptor(CHECKBOXGROUP_VALUE,"输入值");
			pds[8].setCategory("高级");
			
			pds[9] = new TextPropertyDescriptor(CHECKBOXGROUP_TABINDEX,"tabIndex");
			pds[9].setCategory("高级");
			
			pds[10] = new TextPropertyDescriptor(CHECKBOXGROUP_SEPWIDTH,"间距");
			pds[10].setCategory("高级");
			
			if(checkboxGroupComp == null){
				if(textComp instanceof CheckboxGroupComp)
					checkboxGroupComp = (CheckboxGroupComp) textComp;
				else{
					checkboxGroupComp = new CheckboxGroupComp();
					checkboxGroupComp.setId(textComp.getId());
					checkboxGroupComp.setEditorType(textComp.getEditorType());
				}
			}
			else
				checkboxGroupComp.setEditorType(textComp.getEditorType());
			setTextComp(checkboxGroupComp);
		}
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[7])){
			pds[7] = new ObjectComboPropertyDescriptor(RADIOGROUP_COMBODATAID, "引用下拉框数据", getRefCombdata());
			pds[7].setCategory("高级");
			
			pds[8] = new TextPropertyDescriptor(RADIOGROUP_VALUE,"输入值");
			pds[8].setCategory("高级");
			
			pds[9] = new TextPropertyDescriptor(RADIOGROUP_TABINDEX,"tabIndex");
			pds[9].setCategory("高级");
			
			pds[10] = new TextPropertyDescriptor(RADIOGROUP_SEPWIDTH,"间距");
			pds[10].setCategory("高级");
			
			pds[11] = new TextPropertyDescriptor(RADIOGROUP_INDEX,"index");
			pds[11].setCategory("高级");
			
			if(radioGroupComp == null){
				if(textComp instanceof RadioGroupComp)
					radioGroupComp = (RadioGroupComp) textComp;
				else{
					radioGroupComp = new RadioGroupComp();
					radioGroupComp.setId(textComp.getId());
					radioGroupComp.setEditorType(textComp.getEditorType());
				}
			}
			else
				radioGroupComp.setEditorType(textComp.getEditorType());
			setTextComp(radioGroupComp);
		}
		
		//comboboxcomp
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[9])){
			//prop = 1;
			pds[7] = new ObjectComboPropertyDescriptor(PROP_REFCOMBODATA, "引用下拉框数据", getRefCombdata());
			pds[7].setCategory("高级");

			pds[8] = new ComboBoxPropertyDescriptor(PROP_SELECTONLY,"selectOnly", Constant.ISLAZY);
			pds[8].setCategory("高级");
			
			
			pds[9] = new TextPropertyDescriptor(PROP_VALUE,"输入值");
			pds[9].setCategory("高级");
			
			pds[10] = new ComboBoxPropertyDescriptor(ALLOWEXTENDVALUE,"允许存在下拉数据之外的值", Constant.ISLAZY);
			pds[10].setCategory("高级");
			
			
			if(comboboxcomp == null){
				if(textComp instanceof ComboBoxComp)
					comboboxcomp = (ComboBoxComp) textComp;
				if(comboboxcomp == null){
					comboboxcomp = new ComboBoxComp();
					comboboxcomp.setId(textComp.getId());
					comboboxcomp.setEditorType(textComp.getEditorType());
				}
			}
			else
				comboboxcomp.setEditorType(textComp.getEditorType());
			setTextComp(comboboxcomp);
		}
		//reference
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[8])){
			pds[7] = new ObjectComboPropertyDescriptor(PROP_REFCODE, "引用参照ID", getRefnodes());
			pds[7].setCategory("高级");
			pds[8] = new TextPropertyDescriptor(REFERENCE_TIP,"提示");
			pds[8].setCategory("高级");
			if(referencecomp == null){
				if(textComp instanceof ReferenceComp)
					referencecomp = (ReferenceComp) textComp;
				else{
					referencecomp = new ReferenceComp();
					referencecomp.setId(textComp.getId());
					referencecomp.setEditorType(textComp.getEditorType());
				}
			}
			else
				referencecomp.setEditorType(textComp.getEditorType());
			setTextComp(referencecomp);
				
		}
		//DECIMALTEXT
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[3])){
			pds[7] = new TextPropertyDescriptor(PROP_VALUE,"值");
			pds[7].setCategory("高级");
			pds[8] = new TextPropertyDescriptor(PROP_PRECISION, "精度");
			pds[8].setCategory("高级");
			pds[9] = new TextPropertyDescriptor(PROP_TIP,"提示");
			pds[9].setCategory("高级");
			if(textComp instanceof CheckBoxComp || textComp instanceof TextAreaComp || 	textComp instanceof RadioComp || textComp instanceof ReferenceComp || 
					textComp instanceof ComboBoxComp || textComp instanceof RadioGroupComp || textComp instanceof CheckboxGroupComp){
				String id = textComp.getId();
				textComp = new TextComp();
				textComp.setId(id);
				textComp.setEditorType(textComp.getEditorType());
				setTextComp(textComp);
			}
		}
		
		//checkbox
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[2])){
			pds[7] = new TextPropertyDescriptor(PROP_VALUE,"值");
			pds[7].setCategory("高级");
			pds[8] = new TextPropertyDescriptor(CHECKBOX_I18NNAME,"checkbox旁边显示的文字");
			pds[8].setCategory("高级");
			
			pds[9] = new ComboBoxPropertyDescriptor(CHECKBOX_CHECKED, "是否选中", Constant.ISLAZY);
			pds[9].setCategory("高级");
			
			pds[10] = new ObjectComboPropertyDescriptor(CHECKBOX_DATATYPE, "类型", Constant.CHECKBOXTYPE);
			pds[10].setCategory("高级");
				
			if(checkboxcomp == null){
				if(textComp instanceof CheckBoxComp)
					checkboxcomp = (CheckBoxComp) textComp;
				else{
					checkboxcomp = new CheckBoxComp();
					checkboxcomp.setId(textComp.getId());
					checkboxcomp.setEditorType(textComp.getEditorType());
				}
			}
			else
				checkboxcomp.setEditorType(textComp.getEditorType());
			setTextComp(checkboxcomp);
		}
		
		else if(textComp.getEditorType().equals(Constant.TEXTTYPE[10])){
			pds[7] = new TextPropertyDescriptor(TEXTAREA_ROWS, "行宽");
			pds[7].setCategory("高级");
			pds[8] = new TextPropertyDescriptor(TEXTAREA_COLS, "列宽");
			pds[8].setCategory("高级");
			pds[9] = new TextPropertyDescriptor(TEXTAREA_TIP,"提示");
			pds[9].setCategory("高级");
			if(textareaComp == null){
				if(textComp instanceof TextAreaComp)
					textareaComp = (TextAreaComp) textComp;
				else{
					textareaComp = new TextAreaComp();
					textareaComp.setId(textComp.getId());
					textareaComp.setEditorType(textComp.getEditorType());
				}
			}
			else
				textareaComp.setEditorType(textComp.getEditorType());
			setTextComp(textareaComp);
		}
		else if(textComp.getEditorType().equals("FileComp")){
			pds[7] = new TextPropertyDescriptor(FILECOMP_FILESIZE, "上传附件最大值");
			pds[7].setCategory("高级");
			setTextComp(textComp);
		}
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	
	
	public void setPropertyValue(Object id, Object value) {
		if(PROP_VALUE.equals(id)){
			textComp.setValue((String)value);
		}
		else if(PROP_TIP.equals(id))
			textComp.setTip((String)value);
		else if(PROP_TEXT.equals(id)){
			textComp.setText((String)value);
		}
		else if(PROP_I18NNAME.equals(id)){
			textComp.setI18nName((String)value);
		}
		else if(PROP_LANGDIR.equals(id)){
			textComp.setLangDir((String)value);
		}
		else if(PROP_TYPE.equals(id)){
			textComp.setEditorType((String)value);
		}
		
		else if(PROP_FOCUE.equals(id)){
			textComp.setFocus((Integer)value == 0);
		}
		else if(PROP_TEXTALGIN.equals(id)){
			textComp.setTextAlign((String)value);
		}
		else if(PROP_TEXTWIDTH.equals(id)){
			if(value == null || value.equals(""))
				value = "0";
			textComp.setTextWidth(Integer.valueOf((String)value));
		}
		else if(PROP_MAXVALUE.equals(id)){
			textComp.setMaxValue((String)value);
		}
		else if(PROP_MINVALUE.equals(id))
			textComp.setMinValue((String)value);
		else if(PROP_PRECISION.equals(id))
			textComp.setPrecision((String)value);
		else if(PROP_REFCOMBODATA.equals(id)){
			comboboxcomp.setRefComboData((String)value);
		}
		else if(PROP_SELECTONLY.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			comboboxcomp.setSelectOnly(truevalue);
		}
		else if(PROP_CHECKED.equals(id)){
			radiocomp.setChecked((Integer)value == 0);
		}
		else if(PROP_GROUP.equals(id)){
			radiocomp.setGroup((String)value);
		}
		else if(PROP_REFCODE.equals(id)){
			referencecomp.setRefcode((String)value);
		}
		else if(REFERENCE_TIP.equals(id))
			referencecomp.setTip((String)value);
		else if(CHECKBOX_CHECKED.equals(id)){
			checkboxcomp.setChecked((Integer)value == 0);
		}
		else if(CHECKBOX_I18NNAME.equals(id)){
			checkboxcomp.setI18nName((String)value);
		}
		else if(CHECKBOX_DATATYPE.equals(id)){
			checkboxcomp.setDataType((String)value);
		}
		else if(TEXTAREA_ROWS.equals(id)){
			textareaComp.setRows((String)value);
		}
		else if(TEXTAREA_COLS.equals(id)){
			textareaComp.setCols((String)value);
		}
		else if(TEXTAREA_TIP.equals(id))
			textareaComp.setTip((String)value);
		else if(CHECKBOXGROUP_COMBODATAID.equals(id)){
			checkboxGroupComp.setComboDataId((String)value);
		}
		else if(CHECKBOXGROUP_VALUE.equals(id)){
			checkboxGroupComp.setValue((String)value);
		}
		else if(CHECKBOXGROUP_TABINDEX.equals(id)){
			checkboxGroupComp.setTabIndex(Integer.valueOf((String)value));
		}
		else if(CHECKBOXGROUP_SEPWIDTH.equals(id)){
			checkboxGroupComp.setSepWidth(Integer.valueOf((String)value));
		}
		else if(RADIOGROUP_COMBODATAID.equals(id)){
			radioGroupComp.setComboDataId((String)value);
		}
		else if(RADIOGROUP_VALUE.equals(id)){
			radioGroupComp.setValue((String)value);
		}
		else if(RADIOGROUP_TABINDEX.equals(id)){
			radioGroupComp.setTabIndex(Integer.valueOf((String)value));
		}
		else if(RADIOGROUP_SEPWIDTH.equals(id)){
			radioGroupComp.setSepWidth(Integer.valueOf((String)value));
		}
		else if(RADIOGROUP_INDEX.equals(id)){
			radioGroupComp.setIndex(Integer.valueOf((String)value));
		}
		else if(ALLOWEXTENDVALUE.equals(id)){
			comboboxcomp.setAllowExtendValue((Integer)value == 0);
		}
		else if(FILECOMP_FILESIZE.equals(id)){
			textComp.setSizeLimit((String)value);
		}
		
		else
			super.setPropertyValue(id, value);
	}
	public Object getPropertyValue(Object id) {
		if(PROP_VALUE.equals(id))
			return textComp.getValue() == null?"":textComp.getValue();
		else if(PROP_TYPE.equals(id))
			return textComp.getEditorType() == null?"":textComp.getEditorType();
		else if(PROP_FOCUE.equals(id))
			return textComp.isFocus() == true? new Integer(0):new Integer(1);
		else if(PROP_TIP.equals(id))
			return textComp.getTip() == null?"":textComp.getTip();	
		else if(PROP_TEXTALGIN.equals(id))
			return textComp.getTextAlign() == null?"":textComp.getTextAlign();
		else if(PROP_TEXTWIDTH.equals(id))
			return textComp.getTextWidth() == 0?"": String.valueOf(textComp.getTextWidth());
		else if(PROP_MAXVALUE.equals(id))
			return textComp.getMaxValue() == null?"":textComp.getMaxValue();
		else if(PROP_MINVALUE.equals(id))
			return textComp.getMinValue() == null?"":textComp.getMinValue();
		else if(PROP_PRECISION.equals(id))
			return textComp.getPrecision() == null?"":textComp.getPrecision();
		else if(PROP_REFCOMBODATA.equals(id)){
//			if(comboboxcomp == null){
//				comboboxcomp = new ComboBoxComp();
//				comboboxcomp.setId(textComp.getId());
//				setTextComp(comboboxcomp);
//			}
			return comboboxcomp.getRefComboData() == null?"":comboboxcomp.getRefComboData();
		}
		else if(PROP_SELECTONLY.equals(id)){
			return (comboboxcomp.isSelectOnly() == true)? new Integer(0):new Integer(1);	
		}
		else if(PROP_TEXT.equals(id)){
			return textComp.getText() == null?"":textComp.getText();
		}
		else if(PROP_I18NNAME.equals(id)){
			return textComp.getI18nName() == null?"":textComp.getI18nName();
		}
		else if(PROP_LANGDIR.equals(id)){
			return textComp.getLangDir() == null?"":textComp.getLangDir();
		}
		else if(PROP_GROUP.equals(id)){
//			if(radiocomp == null){
//				radiocomp = new RadioComp();
//				radiocomp.setId(textComp.getId());
//				setTextComp(radiocomp);
//			}
			return radiocomp.getGroup() == null?"":radiocomp.getGroup();
		}
		else if(PROP_REFCODE.equals(id)){
//			if(referencecomp == null){
//				referencecomp = new ReferenceComp();
//				referencecomp.setId(textComp.getId());
//				setTextComp(referencecomp);
//			}
			return referencecomp.getRefcode() == null?"":referencecomp.getRefcode();
		}
		else if(REFERENCE_TIP.equals(id))
			return referencecomp.getTip() == null?"":referencecomp.getTip();
		else if(PROP_CHECKED.equals(id)){
//			if(radiocomp == null){
//				radiocomp = new RadioComp();
//				radiocomp.setId(textComp.getId());
//				setTextComp(radiocomp);
//			}
			return radiocomp.isChecked() == true? new Integer(0) : new Integer(1);
		}
		else if(CHECKBOX_CHECKED.equals(id)){
//			if(checkboxcomp == null){
//				checkboxcomp = new CheckBoxComp();
//				checkboxcomp.setId(textComp.getId());
//				setTextComp(checkboxcomp);
//			}
			return checkboxcomp.isChecked() == true? new Integer(0) : new Integer(1);
		}
		else if(CHECKBOX_I18NNAME.equals(id)){
//			if(checkboxcomp == null){
//				checkboxcomp = new CheckBoxComp();
//				checkboxcomp.setId(textComp.getId());
//				setTextComp(checkboxcomp);
//			}
			return checkboxcomp.getI18nName() == null? "": checkboxcomp.getI18nName();
		}
		else if(CHECKBOX_DATATYPE.equals(id)){
//			if(checkboxcomp == null){
//				checkboxcomp = new CheckBoxComp();
//				checkboxcomp.setId(textComp.getId());
//				setTextComp(checkboxcomp);
//			}
			return checkboxcomp.getDataType() == null? "": checkboxcomp.getDataType();
		}
		else if(TEXTAREA_ROWS.equals(id)){
			return textareaComp.getRows() == null?"":textareaComp.getRows();
		}
		else if(TEXTAREA_COLS.equals(id)){
			return textareaComp.getCols() == null?"":textareaComp.getCols();
		}
		else if(TEXTAREA_TIP.equals(id))
			return textareaComp.getTip() == null?"":textareaComp.getTip();
		else if(CHECKBOXGROUP_COMBODATAID.equals(id)){
			return checkboxGroupComp.getComboDataId() == null?"":checkboxGroupComp.getComboDataId();
		}
		else if(CHECKBOXGROUP_VALUE.equals(id)){
			return checkboxGroupComp.getValue() == null?"":checkboxGroupComp.getValue();
		}
		else if(CHECKBOXGROUP_TABINDEX.equals(id)){
			return checkboxGroupComp.getTabIndex() == 0? "0":String.valueOf(checkboxGroupComp.getTabIndex());
		}
		else if(CHECKBOXGROUP_SEPWIDTH.equals(id)){
			return checkboxGroupComp.getSepWidth() == 0? "0":String.valueOf(checkboxGroupComp.getSepWidth());
		}
		
		else if(RADIOGROUP_COMBODATAID.equals(id)){
			return radioGroupComp.getComboDataId() == null?"":radioGroupComp.getComboDataId();
		}
		else if(RADIOGROUP_VALUE.equals(id)){
			return radioGroupComp.getValue() == null?"":radioGroupComp.getValue();
		}
		else if(RADIOGROUP_TABINDEX.equals(id)){
			return radioGroupComp.getTabIndex() == 0? "0":String.valueOf(radioGroupComp.getTabIndex());
		}
		else if(RADIOGROUP_SEPWIDTH.equals(id)){
			return radioGroupComp.getSepWidth() == 0? "0":String.valueOf(radioGroupComp.getSepWidth());
		}
		else if(RADIOGROUP_INDEX.equals(id)){
			return radioGroupComp.getIndex() == 0? "0":String.valueOf(radioGroupComp.getIndex());
		}
		else if(ALLOWEXTENDVALUE.equals(id)){
			return comboboxcomp.isAllowExtendValue() == true? new Integer(0) : new Integer(1);
		}
		else if(FILECOMP_FILESIZE.equals(id)){
			return textComp.getSizeLimit() == null ?"":textComp.getSizeLimit();
		}
		else return super.getPropertyValue(id);
	}
	
	public WebElement getWebElement() {
		// TODO Auto-generated method stub
		return textComp;
	}
}
