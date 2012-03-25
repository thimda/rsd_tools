package nc.uap.lfw.plugin.common;

import nc.uap.lfw.common.DefaultUIPlugin;

import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CommonPlugin extends DefaultUIPlugin{

	// The plug-in ID
	public static final String PLUGIN_ID = "PLUGIN_COMMON";
	
	// The shared instance
	private static CommonPlugin plugin;
	
	/**
	 * The constructor
	 */
	public CommonPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	public static CommonPlugin getPlugin() {
		return plugin;
	}
}
