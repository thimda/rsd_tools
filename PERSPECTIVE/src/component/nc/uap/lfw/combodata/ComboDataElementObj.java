package nc.uap.lfw.combodata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.lfw.combodata.core.ComboDataEditor;
import nc.uap.lfw.core.combodata.CombItem;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.combodata.DynamicComboData;
import nc.uap.lfw.core.combodata.DynamicComboDataConf;
import nc.uap.lfw.core.combodata.MDComboDataConf;
import nc.uap.lfw.core.combodata.StaticComboData;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * 下拉框元素
 * @author zhangxya
 *
 */
public class ComboDataElementObj extends LfwElementObjWithGraph{

	public static final String PROP_ADD_COMBO_CELL_PROPS = "add_combo_cell_props";
	public static final String PROP_UPDATE_CELL_PROPS = "update_cell_props";
	public static final String PROP_COMBODATA_ELEMENT ="combodata_element";
	
	private ComboData combodata;
	private DynamicComboDataConf dynaCombo;
	private StaticComboData staticCombo;
	private MDComboDataConf mdCombo;
	private String orginalId;
	private List<CombItem> props = new ArrayList<CombItem>();

	public String getOrginalId() {
		return orginalId;
	}

	public void setOrginalId(String orginalId) {
		this.orginalId = orginalId;
	}

	private static final long serialVersionUID = 6253081418703115641L;

	private String isdynamic;

	public String getIsdynamic() {
		return isdynamic;
	}

	public void setIsdynamic(String isdynamic) {
		this.isdynamic = isdynamic;
	}

	public ComboData getCombodata() {
		return combodata;
	}

	
	public void setCombodata(ComboData combodata) {
		this.combodata = combodata;
		fireStructureChange(PROP_COMBODATA_ELEMENT,  combodata);
	}

	public ComboDataElementObj(String orginalId){
		super();
		this.orginalId = orginalId;
	}

	public static final String PROP_ID = "element_ID";
	public static final String PROP_CLASSNAME = "element_CLASSNAME";
	public static final String PROP_REFCLASSNAME = "element_REFCLASSNAME";
	public static final String PROP_ISDYNAMIC = "element_ISDYNAMIC";
	public static final String PROP_CAPTION = "element_CAPTION";
		
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		int prop = 0;
		String isDyna = getIsdynamic();
		if(combodata instanceof DynamicComboData || isDyna != null && isDyna.equals("是"))
			prop = 1;
		PropertyDescriptor[] pds = new PropertyDescriptor[prop + 3];
		pds[0] = new ComboBoxPropertyDescriptor(PROP_ISDYNAMIC,"是否动态", Constant.ISLAZY);
		pds[0].setCategory("基本");
		pds[1] = new NoEditableTextPropertyDescriptor(PROP_ID, "ID");
		pds[1].setCategory("基本");
		pds[2] = new TextPropertyDescriptor(PROP_CAPTION,"标题");
		pds[2].setCategory("基本");
		if(combodata instanceof DynamicComboDataConf || isDyna != null && isDyna.equals("是")){
			pds[3] = new TextPropertyDescriptor(PROP_CLASSNAME,"实现类");
			pds[3].setCategory("基本");
			if(combodata instanceof DynamicComboDataConf){
				dynaCombo = (DynamicComboDataConf) combodata;
			}else{
				if(dynaCombo == null){
					dynaCombo = new DynamicComboDataConf();
					dynaCombo.setId(combodata.getId());
					setCombodata(dynaCombo);
				}
			}
		}
		else if(combodata instanceof  MDComboDataConf){
			pds[3] = new TextPropertyDescriptor(PROP_REFCLASSNAME,"引用类型ID");
			pds[3].setCategory("基本");
			if(combodata instanceof MDComboDataConf){
				mdCombo = (MDComboDataConf) combodata;
			}else{
				if(mdCombo == null){
					mdCombo = new MDComboDataConf();
					mdCombo.setId(combodata.getId());
					setCombodata(mdCombo);
				}
			}
		}
		
		if(isDyna != null && isDyna.equals("否")){
			if(staticCombo == null){
				staticCombo = new StaticComboData();
				staticCombo.setId(combodata.getId());
				setCombodata(staticCombo);
			}
		}
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		WebElement webele = getWebElement();
		if(!canChange(webele)){
			return;
		}
		if(PROP_ID.equals(id)){
			combodata.setId((String)value);
			LFWBaseEditor.getActiveEditor().refreshTreeItem(combodata);
		}
		else if(PROP_ISDYNAMIC.equals(id)){
			if((Integer)value == 0)
				setIsdynamic("是");
			else 
				setIsdynamic("否");
		}
		else if(PROP_CLASSNAME.equals(id)){
			dynaCombo.setClassName((String)value);
		}
		else if(PROP_REFCLASSNAME.equals(id)){
			mdCombo.setFullclassName((String)id);
		}
		else if(PROP_CAPTION.equals(id)){
			String oldValue = combodata.getCaption();
			if((oldValue == null && value != null)  || (oldValue != null && value != null && !oldValue.equals(value))){
				combodata.setCaption((String)value);
				ComboDataEditor.getActiveEditor().refreshTreeItemText(combodata);
			}
		}
	}
	
	public Object getPropertyValue(Object id) {
		if(PROP_ID.equals(id))
			return combodata.getId() == null?"":combodata.getId();
		else if(PROP_ISDYNAMIC.equals(id)){
			if(combodata instanceof DynamicComboDataConf)
				return new Integer(0);
			else if(getIsdynamic() == null)
				return new Integer(1);
			else 
				return getIsdynamic() == "是"? new Integer(0):new Integer(1);
		}
		else if(PROP_CLASSNAME.equals(id)){
			if(combodata instanceof DynamicComboDataConf){
				dynaCombo = (DynamicComboDataConf) combodata; 
			}
			return dynaCombo.getClassName() == null? "":dynaCombo.getClassName();
		}
		else if(PROP_REFCLASSNAME.equals(id)){
			return mdCombo.getFullclassName() == null?"":mdCombo.getFullclassName();
		}
		else if(PROP_CAPTION.equals(id))
			return combodata.getCaption() == null?"":combodata.getCaption();
		else return null;
	}
	
	
	public void addProp(CombItem prop){
		props.add(prop);
		fireStructureChange(PROP_ADD_COMBO_CELL_PROPS, prop);
	}

	public Object getEditableValue() {
		return this;
	}

	public void firePropUpdate(FormElement prop){
		fireStructureChange(PROP_UPDATE_CELL_PROPS, prop);
	}
	
	public WebElement getWebElement() {
		return combodata;
	}
	
;
}
