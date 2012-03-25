package nc.uap.portal.portalmodule;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.portal.core.PortalElementObjWithGraph;
import nc.uap.portal.deploy.vo.PortalModule;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * PortalModule Model
 * 
 * @author dingrf
 */
public class PortalModuleElementObj extends PortalElementObjWithGraph{


	private static final long serialVersionUID = 7327652850672170428L;

	/**module name*/
	public static final String PROP_MODULE = "module";
	
	/**depends modules*/
	public static final String PROP_DEPENDS = "depends";
	
	private PortalModule portalModule;

	public PortalModule getPortalModule() {
		return portalModule;
	}

	public void setPortalModule(PortalModule portalModule) {
		this.portalModule = portalModule;
	}

	/**
	 * Properties Description
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[2];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_MODULE, "module");
		pds[0].setCategory("基本");
		pds[1] = new TextPropertyDescriptor(PROP_DEPENDS,"depends");
		pds[1].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_MODULE.equals(id)){
			portalModule.setModule((String)value);
		}
		else if(PROP_DEPENDS.equals(id)){
			portalModule.setDepends((String)value);
		}
	}

	public Object getPropertyValue(Object id) {
		if(PROP_MODULE.equals(id))
			return portalModule.getModule()==null?"":portalModule.getModule();
		else if(PROP_DEPENDS.equals(id))
			return portalModule.getDepends()==null?"":portalModule.getDepends();
		else return super.getPropertyValue(id);
	}
}
