package nc.uap.portal.managerapps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.portal.core.PortalElementObjWithGraph;
import nc.uap.portal.om.ManagerCategory;
import nc.uap.portal.om.ManagerNode;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * ManagerApps Model定义
 * 
 * @author dingrf
 */
public class ManagerAppsElementObj extends PortalElementObjWithGraph{

	private static final long serialVersionUID = 7053401624264036804L;

	/**ManaerApps元素*/
	public static final String PROP_MANAGERAPPS_ELEMENT = "managerApps_element";
	
	/**分类ID*/
	public static final String PROP_ID = "id";
	
	/**分类名称*/
	public static final String PROP_TEXT = "text";
	
	/**分类 i18nName*/
	public static final String PROP_I18NNAME = "i18nName";
	
	/**ManagerApps id*/
	private String managerId;
	
	/**当前分类*/      
	private ManagerCategory  currentManagerCategory;

	/**node对象列表*/      
	private List<ManagerNode>  managerNode;

	public ManagerCategory getCurrentManagerCategory() {
		return currentManagerCategory;
	}

	public void setCurrentManagerCategory(ManagerCategory currentManagerCategory) {
		this.currentManagerCategory = currentManagerCategory;
	}

	public List<ManagerNode> getManagerNode() {
		return managerNode;
	}

	public void setManagerNode(List<ManagerNode> managerNode) {
		this.managerNode = managerNode;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	/**
	 * 属性定义
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[3];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_ID, "id");
		pds[0].setCategory("基本");
		pds[1] = new TextPropertyDescriptor(PROP_TEXT,"text");
		pds[1].setCategory("基本");
		pds[2] = new TextPropertyDescriptor(PROP_I18NNAME, "i18nName");
		pds[2].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}

	/**
	 * 设置属性值
	 */
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if (currentManagerCategory==null)
			return;
		if(PROP_ID.equals(id)){
			currentManagerCategory.setId((String)value);
		}
		else if(PROP_TEXT.equals(id)){
			currentManagerCategory.setText((String)value);
		}
		else if(PROP_I18NNAME.equals(id)){
			currentManagerCategory.setI18nName((String)value);
		}
	}

	/**
	 * 取属性值 
	 */
	public Object getPropertyValue(Object id) {
		if (currentManagerCategory!=null){
			if(PROP_ID.equals(id))
				return currentManagerCategory.getId()==null?"":currentManagerCategory.getId();
			else if(PROP_TEXT.equals(id))
				return currentManagerCategory.getText()==null?"":currentManagerCategory.getText();
			else if(PROP_I18NNAME.equals(id))
				return currentManagerCategory.getI18nName()==null?"":currentManagerCategory.getI18nName();
			else return super.getPropertyValue(id);
		}
		else return super.getPropertyValue(id);
	}
}
