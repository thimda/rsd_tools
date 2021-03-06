/*
 * Created on 2005-8-17
 * @author �ι���
 */
package nc.uap.lfw.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nc.uap.lfw.core.WEBClassPathContainerID;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.internal.project.LFWClasspathContainer;
import nc.uap.lfw.model.LFWLibraryLocation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaModelStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;

/*
 * @author �ι���
 */
public class ClasspathComputer
{
	public static void updateClasspath(IProject project, IProgressMonitor monitor) throws CoreException
	{
		if (project != null && project.hasNature(WEBProjConstants.MODULE_NATURE))
		{
			monitor.subTask("Update classpath");
			IJavaProject javaProject = JavaCore.create(project);
			IClasspathEntry[] entries = getClasspath(project, false);
			javaProject.setRawClasspath(entries, monitor);
		}
	}

	public static LFWLibraryLocation[] computeStandCP(IFolder... baseFolders) throws CoreException
	{
		ArrayList<LFWLibraryLocation> llist = new ArrayList<LFWLibraryLocation>();
		for (IFolder folder : baseFolders)
		{
			IFolder classes = folder.getFolder(ClasspathConstants.CLASSES);
			IFolder resources = folder.getFolder(ClasspathConstants.RESOURCES);
			if (classes.exists())
			{
				llist.add(new LFWLibraryLocation(classes));
			}
			if (resources.exists())
			{
				//llist.addAll(Arrays.asList(computeJarsInPath(resources)));
				llist.add(new LFWLibraryLocation(resources));
			}
			//
			IFolder varclass = folder.getFolder(ClasspathConstants.VAR_CLASSES);
			if (varclass.exists())
			{
				llist.add(new LFWLibraryLocation(varclass));
			}
			//
			IFolder extclass = folder.getFolder(ClasspathConstants.EXTENSION_CLASSES);
			if (extclass.exists())
			{
				llist.add(new LFWLibraryLocation(extclass));
			}
			llist.addAll(Arrays.asList(computeJarsInPath(folder.getFolder(ClasspathConstants.LIB))));
		}
		return llist.toArray(new LFWLibraryLocation[0]);
	}

	private static IClasspathEntry[] getClasspath(IProject project, boolean clear) throws CoreException
	{
		IJavaProject javaproject = JavaCore.create(project);
		ArrayList<IClasspathEntry> result = new ArrayList<IClasspathEntry>();
		addSourceAndLibraries(javaproject, clear, result);
		result.add(ProjCoreUtility.createJREEntry());
		for (WEBClassPathContainerID id : WEBClassPathContainerID.values())
		{
			LFWClasspathContainer container = ProjCoreUtility.getLFWClasspathContainer(id.getPath(), javaproject);
			container.setClasspathEntries(ProjCoreUtility.getClasspathEntry(project, id));
			result.add(ProjCoreUtility.createContainerClasspathEntry(id));
		}
		IClasspathEntry[] entries = result.toArray(new IClasspathEntry[result.size()]);
		IJavaModelStatus validation = JavaConventions.validateClasspath(javaproject, entries, javaproject.getOutputLocation());
		if (!validation.isOK())
		{
			throw new CoreException(validation);
		}
		return (IClasspathEntry[]) result.toArray(new IClasspathEntry[result.size()]);
	}

	private static void addSourceAndLibraries(IJavaProject project, boolean clear, ArrayList<IClasspathEntry> result) throws CoreException
	{
		HashSet<IPath> paths = new HashSet<IPath>();
		IClasspathEntry resEntry = null;
		if (!clear)
		{
			IClasspathEntry[] entries = project.getRawClasspath();
			for (int i = 0; i < entries.length; i++)
			{
				IClasspathEntry entry = entries[i];
				int entryType = entry.getEntryKind();
				if (entryType == IClasspathEntry.CPE_SOURCE || entryType == IClasspathEntry.CPE_PROJECT || entryType == IClasspathEntry.CPE_LIBRARY)
				{
					if (entry.getPath().toPortableString().endsWith("resources"))
					{
						resEntry = entry;
						continue;
					}
					// avoid duplicate entry
					if (paths.add(entry.getPath()))
					{
						result.add(entry);
					}
				}
				else if (entryType == IClasspathEntry.CPE_CONTAINER)
				{
					entry = null;
				}
			}
		}
		if (resEntry == null)
			resEntry = ProjCoreUtility.createSourceEntry(project.getProject(), "resources", "out/resources");
		result.add(resEntry);
	}

	public static LFWLibraryLocation[] computeJarsInPath(IFolder... folders) throws CoreException
	{
		final ArrayList<LFWLibraryLocation> list = new ArrayList<LFWLibraryLocation>();
		final Pattern pattern = Pattern.compile("(?<!_(doc|src))\\.(jar|zip)$", Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
		final String[] exps = WEBProjPlugin.getExceptJarNames().split("\r\n");
		for (IFolder folder : folders)
		{
			if (folder.exists())
			{
				folder.accept(new IResourceProxyVisitor()
				{
					public boolean visit(IResourceProxy proxy) throws CoreException
					{
						if (proxy.getType() == IResource.FILE)
						{
							IFile file = (IFile) proxy.requestResource();
							String filename = file.getName();
							Matcher matcher = pattern.matcher(filename);
							if (matcher.find())
							{
								for (String exp : exps)
								{
									Pattern p = Pattern.compile(exp, Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
									Matcher m = p.matcher(filename);
									if (m.find())
									{
										return false;
									}
								}
								list.add(new LFWLibraryLocation(file));
							}
							return false;
						}
						return true;
					}
				}, 0);
			}
		}
		LFWLibraryLocation[] rets = list.toArray(new LFWLibraryLocation[0]);
		Arrays.sort(rets, new Comparator<LFWLibraryLocation>()
		{
			public int compare(LFWLibraryLocation o1, LFWLibraryLocation o2)
			{
				return o1.getLibResource().getName().compareToIgnoreCase(o2.getLibResource().getName());
			}
		});
		return rets;
	}
}
