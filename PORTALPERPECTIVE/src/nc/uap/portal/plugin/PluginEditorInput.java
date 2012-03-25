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
	 * ²å¼þ¶ÔÏó
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
	 * ±à¼­Æ÷Ãû³Æ
	 */
	public String getName() {
		return "²å¼þ±à¼­Æ÷";
	}

	/**
	 * tool tip
	 */
	public String getToolTipText() {
		return "²å¼þ±à¼­Æ÷";
	}

}
