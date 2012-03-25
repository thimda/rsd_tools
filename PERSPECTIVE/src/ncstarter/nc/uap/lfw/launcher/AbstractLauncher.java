package nc.uap.lfw.launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.internal.util.ProjCoreUtility;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;


public abstract class AbstractLauncher implements ILfwLauncher {
	public void launch() {
		try {
			ILaunchConfiguration configuration = createConfiguration(getProject());
			VMRunnerConfiguration runConfig = getRunConfig(configuration);
			LfwPluginVmRunner vmRunner = new LfwPluginVmRunner();
			vmRunner.setLaunchConfig(configuration);
			vmRunner.run(runConfig, null, null);
		} 
		catch (CoreException e) {
			MainPlugin.getDefault().logError(e);
		}
	}
	
	
	
	
	private VMRunnerConfiguration getRunConfig(ILaunchConfiguration configuration) throws CoreException {
		
		String[] classpath = getClasspath(configuration);
		VMRunnerConfiguration runConfig = new VMRunnerConfiguration(getMainClass(), classpath);
		ExecutionArguments execArgs = new ExecutionArguments(getVMArguments(configuration), getProgramArguments(configuration));
		
		File workingDir = verifyWorkingDirectory(configuration);
		String workingDirName = null;
		if (workingDir != null) {
			workingDirName = workingDir.getAbsolutePath();
		}
		
		
		runConfig.setProgramArguments(execArgs.getProgramArgumentsArray());
		runConfig.setEnvironment(null);
		runConfig.setVMArguments(execArgs.getVMArgumentsArray());
		runConfig.setWorkingDirectory(workingDirName);
		runConfig.setVMSpecificAttributesMap(null);

		// Bootpath
		runConfig.setBootClassPath(getBootpath(configuration));
		return runConfig;
	}
	
	private String getVMArguments(ILaunchConfiguration configuration) throws CoreException {
		String arguments = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, ""); //$NON-NLS-1$
		String args = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(arguments);
		int libraryPath = args.indexOf("-Djava.library.path"); //$NON-NLS-1$
		if (libraryPath < 0) {
			// if a library path is already specified, do not override
			String[] javaLibraryPath = getJavaLibraryPath(configuration);
			if (javaLibraryPath != null && javaLibraryPath.length > 0) {
				StringBuffer path = new StringBuffer(args);
				path.append(" -Djava.library.path="); //$NON-NLS-1$
				path.append("\""); //$NON-NLS-1$
				for (int i = 0; i < javaLibraryPath.length; i++) {
					if (i > 0) {
						path.append(File.pathSeparatorChar);
					}
					path.append(javaLibraryPath[i]);
				}
				path.append("\""); //$NON-NLS-1$
				args = path.toString();
			}
		}
		return args;
	}
	
	private String[] getJavaLibraryPath(ILaunchConfiguration configuration) throws CoreException {
		IJavaProject project = getJavaProject(configuration);
		if (project != null) {
			String[] paths = JavaRuntime.computeJavaLibraryPath(project, true);
			if (paths.length > 0) {
				return paths;
			}
		}
		return null;
	}
	
	public String getProgramArguments(ILaunchConfiguration configuration)
			throws CoreException {
		String arguments = configuration.getAttribute(
				IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, ""); //$NON-NLS-1$
		return VariablesPlugin.getDefault().getStringVariableManager()
				.performStringSubstitution(arguments);
	}

	private File verifyWorkingDirectory(ILaunchConfiguration configuration)
			throws CoreException {
		IPath path = getWorkingDirectoryPath(configuration);
		if (path == null) {
			File dir = getDefaultWorkingDirectory(configuration);
			if (dir != null) {
				if (!dir.isDirectory()) {
//					abort(
//							MessageFormat
//									.format(
//											LaunchingMessages.AbstractJavaLaunchConfigurationDelegate_Working_directory_does_not_exist___0__12,
//											new String[] { dir.toString() }),
//							null,
//							IJavaLaunchConfigurationConstants.ERR_WORKING_DIRECTORY_DOES_NOT_EXIST);
				}
				return dir;
			}
		} else {
			if (path.isAbsolute()) {
				File dir = new File(path.toOSString());
				if (dir.isDirectory()) {
					return dir;
				}
				// This may be a workspace relative path returned by a variable.
				// However variable paths start with a slash and thus are
				// thought to
				// be absolute
				IResource res = ResourcesPlugin.getWorkspace().getRoot()
						.findMember(path);
				if (res instanceof IContainer && res.exists()) {
					return res.getLocation().toFile();
				}
//				abort(
//						MessageFormat
//								.format(
//										LaunchingMessages.AbstractJavaLaunchConfigurationDelegate_Working_directory_does_not_exist___0__12,
//										new String[] { path.toString() }),
//						null,
//						IJavaLaunchConfigurationConstants.ERR_WORKING_DIRECTORY_DOES_NOT_EXIST);
			} else {
				IResource res = ResourcesPlugin.getWorkspace().getRoot()
						.findMember(path);
				if (res instanceof IContainer && res.exists()) {
					return res.getLocation().toFile();
				}
//				abort(
//						MessageFormat
//								.format(
//										LaunchingMessages.AbstractJavaLaunchConfigurationDelegate_Working_directory_does_not_exist___0__12,
//										new String[] { path.toString() }),
//						null,
//						IJavaLaunchConfigurationConstants.ERR_WORKING_DIRECTORY_DOES_NOT_EXIST);
			}
		}
		return null;
	}
	
	private File getWorkingDirectory(ILaunchConfiguration configuration)
			throws CoreException {
		return verifyWorkingDirectory(configuration);
	}

	/**
	 * Returns the working directory path specified by the given launch
	 * configuration, or <code>null</code> if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the working directory path specified by the given launch
	 *         configuration, or <code>null</code> if none
	 * @exception CoreException
	 *                if unable to retrieve the attribute
	 */
	private IPath getWorkingDirectoryPath(ILaunchConfiguration configuration)
			throws CoreException {
		String path = configuration.getAttribute(
				IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY,
				(String) null);
		if (path != null) {
			path = VariablesPlugin.getDefault().getStringVariableManager()
					.performStringSubstitution(path);
			return new Path(path);
		}
		return null;
	}
	

	private String[] getBootpath(ILaunchConfiguration configuration)
			throws CoreException {
		String[][] paths = getBootpathExt(configuration);
		String[] pre = paths[0];
		String[] main = paths[1];
		String[] app = paths[2];
		if (pre == null && main == null && app == null) {
			// default
			return null;
		}
		IRuntimeClasspathEntry[] entries = JavaRuntime
				.computeUnresolvedRuntimeClasspath(configuration);
		entries = JavaRuntime.resolveRuntimeClasspath(entries, configuration);
		List bootEntries = new ArrayList(entries.length);
		boolean empty = true;
		boolean allStandard = true;
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getClasspathProperty() != IRuntimeClasspathEntry.USER_CLASSES) {
				String location = entries[i].getLocation();
				if (location != null) {
					empty = false;
					bootEntries.add(location);
					allStandard = allStandard
							&& entries[i].getClasspathProperty() == IRuntimeClasspathEntry.STANDARD_CLASSES;
				}
			}
		}
		if (empty) {
			return new String[0];
		} else if (allStandard) {
			return null;
		} else {
			return (String[]) bootEntries
					.toArray(new String[bootEntries.size()]);
		}
	}
	
	private String[][] getBootpathExt(ILaunchConfiguration configuration)
			throws CoreException {
		String[][] bootpathInfo = new String[3][];
		IRuntimeClasspathEntry[] entries = JavaRuntime
				.computeUnresolvedRuntimeClasspath(configuration);
		List bootEntriesPrepend = new ArrayList();
		int index = 0;
		IRuntimeClasspathEntry jreEntry = null;
		while (jreEntry == null && index < entries.length) {
			IRuntimeClasspathEntry entry = entries[index++];
			if (entry.getClasspathProperty() == IRuntimeClasspathEntry.BOOTSTRAP_CLASSES
					|| entry.getClasspathProperty() == IRuntimeClasspathEntry.STANDARD_CLASSES) {
				if (JavaRuntime.isVMInstallReference(entry)) {
					jreEntry = entry;
				} else {
					bootEntriesPrepend.add(entry);
				}
			}
		}
		IRuntimeClasspathEntry[] bootEntriesPrep = JavaRuntime
				.resolveRuntimeClasspath(
						(IRuntimeClasspathEntry[]) bootEntriesPrepend
								.toArray(new IRuntimeClasspathEntry[bootEntriesPrepend
										.size()]), configuration);
		String[] entriesPrep = null;
		if (bootEntriesPrep.length > 0) {
			entriesPrep = new String[bootEntriesPrep.length];
			for (int i = 0; i < bootEntriesPrep.length; i++) {
				entriesPrep[i] = bootEntriesPrep[i].getLocation();
			}
		}
		if (jreEntry != null) {
			List bootEntriesAppend = new ArrayList();
			for (; index < entries.length; index++) {
				IRuntimeClasspathEntry entry = entries[index];
				if (entry.getClasspathProperty() == IRuntimeClasspathEntry.BOOTSTRAP_CLASSES) {
					bootEntriesAppend.add(entry);
				}
			}
			bootpathInfo[0] = entriesPrep;
			IRuntimeClasspathEntry[] bootEntriesApp = JavaRuntime
					.resolveRuntimeClasspath(
							(IRuntimeClasspathEntry[]) bootEntriesAppend
									.toArray(new IRuntimeClasspathEntry[bootEntriesAppend
											.size()]), configuration);
			if (bootEntriesApp.length > 0) {
				bootpathInfo[2] = new String[bootEntriesApp.length];
				for (int i = 0; i < bootEntriesApp.length; i++) {
					bootpathInfo[2][i] = bootEntriesApp[i].getLocation();
				}
			}
//			IVMInstall install = getVMInstall(configuration);
//			LibraryLocation[] libraryLocations = install.getLibraryLocations();
//			if (libraryLocations != null) {
//				// determine if explicit bootpath should be used
//				// TODO: this test does not tell us if the bootpath entries are
//				// different (could still be
//				// the same, as a non-bootpath entry on the JRE may have been
//				// removed/added)
//				// We really need a way to ask a VM type for its default
//				// bootpath library locations and
//				// compare that to the resolved entries for the "jreEntry" to
//				// see if they
//				// are different (requires explicit bootpath)
//				if (!JRERuntimeClasspathEntryResolver.isSameArchives(
//						libraryLocations, install.getVMInstallType()
//								.getDefaultLibraryLocations(
//										install.getInstallLocation()))) {
//					// resolve bootpath entries in JRE entry
//					IRuntimeClasspathEntry[] bootEntries = null;
//					if (jreEntry.getType() == IRuntimeClasspathEntry.CONTAINER) {
//						IRuntimeClasspathEntry bootEntry = JavaRuntime
//								.newRuntimeContainerClasspathEntry(
//										jreEntry.getPath(),
//										IRuntimeClasspathEntry.BOOTSTRAP_CLASSES,
//										getJavaProject(configuration));
//						bootEntries = JavaRuntime.resolveRuntimeClasspathEntry(
//								bootEntry, configuration);
//					} else {
//						bootEntries = JavaRuntime.resolveRuntimeClasspathEntry(
//								jreEntry, configuration);
//					}
//
//					// non-default JRE libraries - use explicit bootpath only
//					String[] bootpath = new String[bootEntriesPrep.length
//							+ bootEntries.length + bootEntriesApp.length];
//					if (bootEntriesPrep.length > 0) {
//						System.arraycopy(bootpathInfo[0], 0, bootpath, 0,
//								bootEntriesPrep.length);
//					}
//					int dest = bootEntriesPrep.length;
//					for (int i = 0; i < bootEntries.length; i++) {
//						bootpath[dest] = bootEntries[i].getLocation();
//						dest++;
//					}
//					if (bootEntriesApp.length > 0) {
//						System.arraycopy(bootpathInfo[2], 0, bootpath, dest,
//								bootEntriesApp.length);
//					}
//					bootpathInfo[0] = null;
//					bootpathInfo[1] = bootpath;
//					bootpathInfo[2] = null;
//				}
//			}
		} else {
			if (entriesPrep == null) {
				bootpathInfo[1] = new String[0];
			} else {
				bootpathInfo[1] = entriesPrep;
			}
		}
		return bootpathInfo;
	}
	
	private String[] getClasspath(ILaunchConfiguration configuration)
			throws CoreException {
		IRuntimeClasspathEntry[] entries = JavaRuntime
				.computeUnresolvedRuntimeClasspath(configuration);
		entries = JavaRuntime.resolveRuntimeClasspath(entries, configuration);
		List userEntries = new ArrayList(entries.length);
		Set set = new HashSet(entries.length);
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getClasspathProperty() == IRuntimeClasspathEntry.USER_CLASSES) {
				String location = entries[i].getLocation();
				if (location != null) {
					if (!set.contains(location)) {
						userEntries.add(location);
						set.add(location);
					}
				}
			}
		}
		return (String[]) userEntries.toArray(new String[userEntries.size()]);
	}
	
	private IJavaProject getProject() {
		IProject[] projects = LFWPersTool.getOpenedLFWJavaProjects();
		IProject proj = getLfwProj(projects);
		if(proj == null)
			proj = projects[0];
		return JavaCore.create(proj);
	}
	
	private IProject getLfwProj(IProject[] projs) {
		if(projs == null)
			return null;
		for (int i = 0; i < projs.length; i++) {
			IProject proj = projs[i];
			if(proj.getName().toLowerCase().indexOf("uapweb") != -1)
				return proj;
		}
		return null;
	}
	


	private ILaunchConfiguration createConfiguration(IJavaProject javaProject)
	{
		ILaunchConfiguration config = null;
		try
		{
			ILaunchConfigurationType configType = getConfigurationType();
			ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, getLaunchManager().generateUniqueLaunchConfigurationNameFrom(
					javaProject.getElementName()));
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
	
	private ILaunchManager getLaunchManager()
	{
		return DebugPlugin.getDefault().getLaunchManager();
	}
	
	private String getMainClass()
	{
		return "nc.uap.applet.NCStarter";
	}
	

	private ILaunchConfigurationType getConfigurationType()
	{
		return getLaunchManager().getLaunchConfigurationType(WEBProjConstants.NC_LAUNCH_ID);
	}
	
	private void configLaunchConfiguration(ILaunchConfigurationWorkingCopy wc)
	{
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, getMainClass());
		ProjCoreUtility.configLaunchConfiguration(wc);
		String vmargs;
		try {
			vmargs = wc.getAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, "");
			vmargs += " -Dworkbench_clazz=" + getWorkbenchClazz();
			vmargs += getLoginInfo();
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmargs);
		} 
		catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	protected String getLoginInfo() {
		String userInfo = LFWPersTool.getUserMessage();
		String[] userInfos = userInfo.split(":", -1);
		return " -DBC=" + userInfos[0] + " -DU=" + userInfos[1] + " -DP=" + userInfos[2];
		//return " -DBC=develop -DU=1 -DP=1";
	}
	
	
	

	private File getDefaultWorkingDirectory(ILaunchConfiguration configuration) throws CoreException {
		// default working directory is the project if this config has a project
		IJavaProject jp = getJavaProject(configuration);
		if (jp != null) {
			IProject p = jp.getProject();
			return p.getLocation().toFile();
		}
		return null;
	}
	
	private IJavaProject getJavaProject(ILaunchConfiguration configuration)
			throws CoreException {
		String projectName = getJavaProjectName(configuration);
		if (projectName != null) {
			projectName = projectName.trim();
			if (projectName.length() > 0) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(projectName);
				IJavaProject javaProject = JavaCore.create(project);
				if (javaProject != null && javaProject.exists()) {
					return javaProject;
				}
			}
		}
		return null;
	}
	
	private String getJavaProjectName(ILaunchConfiguration configuration)
			throws CoreException {
		return configuration.getAttribute(
				IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
				(String) null);
	}
}
