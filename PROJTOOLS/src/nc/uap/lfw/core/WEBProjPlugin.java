package nc.uap.lfw.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import nc.uap.lfw.common.DefaultUIPlugin;
import nc.uap.lfw.internal.util.ProjCoreUtility;

import org.eclipse.core.resources.IPathVariableChangeEvent;
import org.eclipse.core.resources.IPathVariableChangeListener;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.osgi.framework.BundleContext;

public class WEBProjPlugin extends DefaultUIPlugin {
    public final static String ICONS_PATH = "icons/";
    private static WEBProjPlugin plugin;
    private ResourceBundle resourceBundle;

    /**
     * The constructor.
     */
    public WEBProjPlugin() {
        super();
        plugin = this;

        try {
            resourceBundle = ResourceBundle.getBundle(
                    "nc.uap.lfw.core.WEBProjPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }
    
    public static IPath getNCHomePath()
    {
        return Path.fromOSString(getNCHome());
    }
    
    public static String getNCHome()
    {
        return getVariableManager().getValueVariable("FIELD_NC_HOME").getValue();
    }


    private void updateClassPath() {
        if (MessageDialog.openConfirm(null, "Confirm",
                    "Do you want to update classpath?")) {
            ProjCoreUtility.updateWorkspaceClasspath();
        }
    }

    private IValueVariable getValueVariable(String varname, String description)
        throws CoreException {
        IStringVariableManager vvManager = getVariableManager();
        IValueVariable var = vvManager.getValueVariable(varname);

        if (var == null) {
            var = vvManager.newValueVariable(varname, description);
            vvManager.addVariables(new IValueVariable[] { var });
        }

        return var;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        ResourcesPlugin.getWorkspace().getPathVariableManager().addChangeListener(new IPathVariableChangeListener() {
                public void pathVariableChanged(IPathVariableChangeEvent event) {
                    if (WEBProjConstants.FIELD_NC_HOME.equals(
                                event.getVariableName())) {
                        //updateClassPath();
                    }
                }
            });
        getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    String newString = event.getNewValue().toString();

                    if (event.getProperty().equals(WEBProjConstants.FIELD_NC_HOME)) {
                        try {
                            getValueVariable(WEBProjConstants.FIELD_NC_HOME,
                                "The NC_HOME location").setValue(newString);

                            IPathVariableManager pathVarMgr = ResourcesPlugin.getWorkspace()
                                                                             .getPathVariableManager();
                            pathVarMgr.setValue(WEBProjConstants.FIELD_NC_HOME,
                                new Path(event.getNewValue().toString()));
                        } catch (CoreException e) {
                        	getDefault().logError(e);
                        }

                        updateClassPath();
                    }

                    if (event.getProperty().equals(WEBProjConstants.FIELD_CLINET_IP)) {
                        try {
                            getValueVariable(WEBProjConstants.FIELD_CLINET_IP,
                                "CLINET_IP").setValue(newString);
                            setUrltoProperty();
                        } 
                        catch (CoreException e) {
                        	getDefault().logError(e);
                        }
                    }

                    if (event.getProperty()
                                 .equals(WEBProjConstants.FIELD_CLINET_PORT)) {
                        try {
                            getValueVariable(WEBProjConstants.FIELD_CLINET_PORT,
                                "CLINET_PORT").setValue(newString);
                            setUrltoProperty();
                        } catch (CoreException e) {
                            getDefault().logError(e);
                        }
                    }
                }
            });
        // Platform.getOS()
        getPreferenceStore().setDefault(WEBProjConstants.FIELD_NC_HOME, "c:/nc_home");
        getPreferenceStore()
            .setDefault(WEBProjConstants.FIELD_CLINET_IP, "127.0.0.1");
        getPreferenceStore().setDefault(WEBProjConstants.FIELD_CLINET_PORT, 80);
        setUrltoProperty();
    }

    private void setUrltoProperty() {
        try {
            IValueVariable var = getValueVariable(WEBProjConstants.FIELD_CLINET_IP,
                    "CLINET_IP");
            String ip = var.getValue();
            var = getValueVariable(WEBProjConstants.FIELD_CLINET_PORT, "CLINET_PORT");

            String port = var.getValue();
            System.setProperty("CLIENT_URL_MDE",
                MessageFormat.format("http://{0}:{1}", ip, port));
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public static String[] getExModuleNames() {
        return WEBProjPlugin.getVariableManager()
                        .getValueVariable(WEBProjConstants.FIELD_EX_MODULES)
                        .getValue().split(",");
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     */
    public static WEBProjPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = WEBProjPlugin.getDefault().getResourceBundle();

        try {
            return (bundle != null) ? bundle.getString(key) : key;
        } catch (MissingResourceException e) {
            return key;
        }
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static URL makeImageURL(String prefix, String name) {
        String path = prefix + name;
        URL url = null;

        try {
            url = new URL(WEBProjPlugin.getDefault().getBundle().getEntry("/"), path);
        } catch (MalformedURLException e) {
            return null;
        }

        return url;
    }

    public static ImageDescriptor loadImage(String path, String imgName) {
        return ImageDescriptor.createFromURL(makeImageURL(path, imgName));
    }

    public static IStringVariableManager getVariableManager() {
        return VariablesPlugin.getDefault().getStringVariableManager();
    }

    public static String getExceptJarNames() {
        return getPreferenceStoreString(WEBProjConstants.EXCEPT_JAR_NC_HOME,
            "nc.bs.framework.tool.config.+.jar\ntestbill.+.jar\n.*PROXY.jar");
    }

    public static String[] getModuleNames() {
        return getPreferenceStoreString(WEBProjConstants.MODULES_NC_HOME,
            "uap/uapqe/uapbd").split("/");
    }

    public static String getPreferenceStoreString(String id, String defvalue) {
        String string = getDefault().getPreferenceStore().getString(id);

        return (string.length() > 1) ? string : defvalue;
    }
}
