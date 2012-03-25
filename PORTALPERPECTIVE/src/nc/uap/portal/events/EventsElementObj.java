package nc.uap.portal.events;

import java.util.ArrayList;
import java.util.List;

import nc.uap.portal.container.om.EventDefinition;
import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.core.PortalElementObjWithGraph;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * �¼�ͼ�οؼ�����
 * 
 * @author dingrf
 */
public class EventsElementObj extends PortalElementObjWithGraph{

	private static final long serialVersionUID = 7053401624264036804L;

	/**
	 * �¼�Ԫ��
	 */
	public static final String PROP_PORTLETAPP_ELEMENT = "portletapp_element";
	
	
	/**
	 * Portlet-app����
	 */      
	private PortletApplicationDefinition portletApp;

	/**
	 * �¼������б�
	 */      
	private List<EventDefinition>  eventDefinitions;

	public List<EventDefinition> getEventDefinitions() {
		return eventDefinitions;
	}

	public void setEventDefinitions(List<EventDefinition> eventDefinitions) {
		this.eventDefinitions = eventDefinitions;
	}
	
	/**
	 * ���Զ���
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
	}

	public Object getPropertyValue(Object id) {
		return super.getPropertyValue(id);
	}

	public PortletApplicationDefinition getPortletApp() {
		return portletApp;
	}

	public void setPortletApp(PortletApplicationDefinition portletApp) {
		this.portletApp = portletApp;
		fireStructureChange(PROP_PORTLETAPP_ELEMENT, portletApp);
	}

}
