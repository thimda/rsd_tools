package nc.uap.portal.events;

import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.core.PortalBaseEditorInput;

/**
 * EventsEditorInput
 * 
 * @author dingrf
 * 
 */
public class EventsEditorInput extends PortalBaseEditorInput {

	/**portlet-app���� */
	private PortletApplicationDefinition portletApp;
	
	public PortletApplicationDefinition getPortletApp() {
		return portletApp;
	}
	
	public void setPortletApp(PortletApplicationDefinition portletApp) {
		this.portletApp = portletApp;
	}
	
	public EventsEditorInput(PortletApplicationDefinition portletApp){
		this.portletApp = portletApp;
	}
	
	public String getName() {
		return "�¼��༭��";
	}

	public String getToolTipText() {
		return "�¼��༭��";
	}

}
