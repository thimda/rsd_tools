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
 * ManagerApps Model����
 * 
 * @author dingrf
 */
public class ManagerAppsElementObj extends PortalElementObjWithGraph{

	private static final long serialVersionUID = 7053401624264036804L;

	/**ManaerAppsԪ��*/
	public static final String PROP_MANAGERAPPS_ELEMENT = "managerApps_element";
	
	/**����ID*/
	public static final String PROP_ID = "id";
	
	/**��������*/
	public static final String PROP_TEXT = "text";
	
	/**���� i18nName*/
	public static final String PROP_I18NNAME = "i18nName";
	
	/**ManagerApps id*/
	private String managerId;
	
	/**��ǰ����*/      
	private ManagerCategory  currentManagerCategory;

	/**node�����б�*/      
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
	 * ���Զ���
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[3];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_ID, "id");
		pds[0].setCategory("����");
		pds[1] = new TextPropertyDescriptor(PROP_TEXT,"text");
		pds[1].setCategory("����");
		pds[2] = new TextPropertyDescriptor(PROP_I18NNAME, "i18nName");
		pds[2].setCategory("����");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}

	/**
	 * ��������ֵ
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
	 * ȡ����ֵ 
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
