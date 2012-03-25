/*
 * Created on 2005-8-20
 * @author ºÎ¹ÚÓî
 */
package nc.uap.lfw.launcher;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.internal.util.ProjCoreUtility;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

/**
 *
 */
public class AbstractClientShortcut implements IShortcut
{
	public void launch(IProject project, String mode) throws CoreException
	{
		IJavaProject javaProject = JavaCore.create(project);
		ILaunchConfiguration config = findLaunchConfiguration(javaProject, mode);
		if (config != null)
		{
			DebugUITools.launch(config, mode);
		}
	}
	
	protected void configLaunchConfiguration(ILaunchConfigurationWorkingCopy wc)
	{
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, getMainClass());
		ProjCoreUtility.configLaunchConfiguration(wc);
		String vmargs;
		try {
			vmargs = wc.getAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, "");
			vmargs += " -Dworkbench_clazz=nc.uap.applet.NCMenuWorkbench";
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmargs);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		
	}

	protected String getMainClass()
	{
		return "nc.uap.applet.NCStarter";
	}

	protected String getName()
	{
		return "_NC_MENU";
	}
	

	protected ILaunchConfigurationType getConfigurationType()
	{
		return getLaunchManager().getLaunchConfigurationType(WEBProjConstants.NC_LAUNCH_ID);
	}

	protected ILaunchManager getLaunchManager()
	{
		return DebugPlugin.getDefault().getLaunchManager();
	}

	protected ILaunchConfiguration findLaunchConfiguration(IJavaProject type, String mode)
	{
		return createConfiguration(type);
	}
	private ILaunchConfiguration createConfiguration(IJavaProject javaProject)
	{
		ILaunchConfiguration config = null;
		try
		{
			ILaunchConfigurationType configType = getConfigurationType();
			ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, getLaunchManager().generateUniqueLaunchConfigurationNameFrom(
					javaProject.getElementName())
					+ getName());
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, javaProject.getJavaProject().getElementName());
			configLaunchConfiguration(wc);
			config = wc;
		}
		catch (CoreException ce)
		{
			ce.printStackTrace();
		}
		return config;
	}

}
