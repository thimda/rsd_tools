package nc.uap.portal.portlets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.portal.container.om.Description;
import nc.uap.portal.container.om.DisplayName;
import nc.uap.portal.container.om.EventDefinitionReference;
import nc.uap.portal.container.om.InitParam;
import nc.uap.portal.container.om.PortletDefinition;
import nc.uap.portal.container.om.Preference;
import nc.uap.portal.container.om.Preferences;
import nc.uap.portal.container.om.Supports;
import nc.uap.portal.core.PortalElementObjWithGraph;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * portletͼ�οؼ�����
 * @author dingrf
 *
 */
public class PortletElementObj extends PortalElementObjWithGraph{

	private static final long serialVersionUID = 7053401624264036804L;

	public static final String PROP_PORTLET_ELEMENT ="portlet_element";
	
	/**portlet ID*/
	private static final String PROP_ID = "id";
	
	/**����*/
	private static final String PROP_DESCRIPTION = "description";
	
	/**��ʾ����*/
	private static final String PROP_DISPLAYNAME = "displayName";
	
	/**��*/
	private static final String PROP_PORTLETCLASS = "portletClass";
	
	private PortletDefinition portlet;
	private List<InitParam> initParams;
	private List<Supports>  supports;
	private Preferences  portletPreferences; 
	private List<Preference>  preferences;
	private String version="0";
	private String categoryId = null;
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	private List<EventDefinitionReference>  supportedProcessingEvents;
	private List<EventDefinitionReference>  supportedPublishingEvents;


	public List<Preference> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<Preference> preferences) {
		this.preferences = preferences;
	}

	public List<InitParam> getInitParams() {
		return initParams;
	}

	public void setInitParams(List<InitParam> initParams) {
		this.initParams = initParams;
	}

	public List<Supports> getSupports() {
		return supports;
	}
	
	public void setSupports(List<Supports> supports) {
		this.supports = supports;
	}

	public Preferences getPortletPreferences() {
		return portletPreferences;
	}

	public void setPortletPreferences(Preferences portletPreferences) {
		this.portletPreferences = portletPreferences;
	}

	public List<EventDefinitionReference> getSupportedProcessingEvents() {
		return supportedProcessingEvents;
	}

	public void setSupportedProcessingEvents(
			List<EventDefinitionReference> supportedProcessingEvents) {
		this.supportedProcessingEvents = supportedProcessingEvents;
	}

	public List<EventDefinitionReference> getSupportedPublishingEvents() {
		return supportedPublishingEvents;
	}

	public void setSupportedPublishingEvents(
			List<EventDefinitionReference> supportedPublishingEvents) {
		this.supportedPublishingEvents = supportedPublishingEvents;
	}


	/**
	 * ����
	 * 
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[4];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_ID, "id");
		pds[0].setCategory("����");
		pds[1] = new TextPropertyDescriptor(PROP_DISPLAYNAME, "��ʾ����");
		pds[1].setCategory("����");
		pds[2] = new TextPropertyDescriptor(PROP_PORTLETCLASS,"��");
		pds[2].setCategory("����");
		pds[3] = new TextPropertyDescriptor(PROP_DESCRIPTION, "����");
		pds[3].setCategory("����");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_DESCRIPTION.equals(id)){
			if (portlet.getDescriptions().isEmpty()){
				Description desc = new Description();
				desc.setDescription((String)value);
				portlet.getDescription().add(desc);
			}
			else{
				portlet.getDescriptions().get(0).setDescription((String)value);
			}
		}
		else if(PROP_DISPLAYNAME.equals(id)){
			if (portlet.getDisplayNames().isEmpty()){
				DisplayName dispayName = new DisplayName();
				dispayName.setDisplayName((String)value);
				portlet.getDisplayName().add(dispayName);
			}
			else{
				portlet.getDisplayNames().get(0).setDisplayName((String)value);
			}
		}
		else if(PROP_PORTLETCLASS.equals(id)){
			portlet.setPortletClass((String)value);
		}
	}
	public Object getPropertyValue(Object id) {
		if(PROP_ID.equals(id))
			return portlet.getPortletName()==null?"":portlet.getPortletName();
		else if(PROP_DESCRIPTION.equals(id))
			return portlet.getDescriptions().isEmpty()?"":
				(portlet.getDescriptions().get(0).getDescription()==null?"":portlet.getDescriptions().get(0).getDescription());
		else if(PROP_DISPLAYNAME.equals(id))
			return portlet.getDisplayNames().isEmpty()?"":portlet.getDisplayNames().get(0).getDisplayName();
		else if(PROP_PORTLETCLASS.equals(id))
			return portlet.getPortletClass()==null?"":portlet.getPortletClass();
		else return super.getPropertyValue(id);
	}
	
	
	public PortletDefinition getPortlet() {
		return portlet;
	}

	public void setPortlet(PortletDefinition portlet) {
		this.portlet = portlet;
		fireStructureChange(PROP_PORTLET_ELEMENT, portlet);
	}

}
