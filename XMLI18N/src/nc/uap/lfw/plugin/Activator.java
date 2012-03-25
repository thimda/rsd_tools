package nc.uap.lfw.plugin;

import nc.uap.lfw.common.DefaultUIPlugin;

import org.osgi.framework.BundleContext;

/**
 * Activator
 * 
 * @author dingrf
 *
 */
public class Activator extends DefaultUIPlugin{
	
  private static Activator plugin;

  public void start(BundleContext context)throws Exception{
    super.start(context);
    plugin = this;
  }

  public void stop(BundleContext context) throws Exception{
    plugin = null;
    super.stop(context);
  }

  public static Activator getDefault(){
    return plugin;
  }
}