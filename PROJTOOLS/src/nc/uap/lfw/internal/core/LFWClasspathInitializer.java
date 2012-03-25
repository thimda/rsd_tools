package nc.uap.lfw.internal.core;

import nc.uap.lfw.core.WEBClassPathContainerID;
import nc.uap.lfw.internal.project.LFWClasspathContainer;
import nc.uap.lfw.internal.util.ProjCoreUtility;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class LFWClasspathInitializer extends ClasspathContainerInitializer
{
	public void initialize(IPath containerPath, IJavaProject javaProject) throws CoreException
	{
		if (javaProject != null)
		{
			String libname = containerPath.segment(1);
			WEBClassPathContainerID  id = null;
			try
			{
				id = WEBClassPathContainerID.valueOf(libname);
				LFWClasspathContainer container = new LFWClasspathContainer(id, ProjCoreUtility.getClasspathEntry(javaProject.getProject(), id));
				JavaCore.setClasspathContainer(container.getPath(), new IJavaProject[] { javaProject }, new IClasspathContainer[] { container }, null);
			}
			catch (IllegalArgumentException e)
			{
			}
		}
	}

	public Object getComparisonID(IPath containerPath, IJavaProject project)
	{
		return containerPath;
	}
}