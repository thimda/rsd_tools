package nc.uap.lfw.launcher;

import java.util.Arrays;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

public class LFWLaunchDelegate extends JavaLaunchDelegate
{
	@Override
	public String[] getClasspath(ILaunchConfiguration configuration) throws CoreException
	{
		String nchome = WEBProjPlugin.getVariableManager().getValueVariable(WEBProjConstants.FIELD_NC_HOME).getValue();
		if (nchome.endsWith("\\") || nchome.endsWith("/"))
		{
			nchome = nchome.substring(0, nchome.length() - 1);
		}
		String[] entries = super.getClasspath(configuration);
		for (int i = 0; i < entries.length; i++)
		{
			String entry = entries[i];
			if (entry.startsWith(nchome))
			{
				entries[i] = "." + entry.substring(nchome.length(), entry.length());
			}
		}
		WEBProjPlugin.getDefault().logInfo(Arrays.asList(entries).toString());
		return entries;
	}
}
