package nc.uap.portal.portalmodule;

import nc.uap.portal.core.PortalBaseEditorInput;
import nc.uap.portal.deploy.vo.PortalModule;

/**
 * PortalModuleEditorInput
 * 
 * @author dingrf
 * 
 */
public class PortalModuleEditorInput extends PortalBaseEditorInput {

	private PortalModule portalModule;

	private String moduleName;
	
	public PortalModule getPortalModule() {
		return portalModule;
	}

	public void setPortalModule(PortalModule portalModule) {
		this.portalModule = portalModule;
	}

	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public PortalModuleEditorInput(PortalModule portalModule,String ModuleName){
		this.portalModule = portalModule;
		this.moduleName = ModuleName;
	}
	
	public String getName() {
		return "Portal±à¼­Æ÷";
	}

	public String getToolTipText() {
		return "Portal±à¼­Æ÷";
	}

}
