package nc.uap.portal.plugin;

import nc.uap.portal.core.PortalBaseEditorInput;
import nc.uap.portal.plugins.model.PtPlugin;

/**
 * PluginEditorInput
 * 
 * @author dingrf
 * 
 */
public class PluginEditorInput extends PortalBaseEditorInput {

	/**
	 * �������
	 */
	private PtPlugin ptPlugin;
	
	public PtPlugin getPtPlugin() {
		return ptPlugin;
	}

	public void setPtPlugin(PtPlugin ptPlugin) {
		this.ptPlugin = ptPlugin;
	}

	public PluginEditorInput(PtPlugin ptPlugin){
		this.ptPlugin=ptPlugin;
	}
	
	/**
	 * �༭������
	 */
	public String getName() {
		return "����༭��";
	}

	/**
	 * tool tip
	 */
	public String getToolTipText() {
		return "����༭��";
	}

}
