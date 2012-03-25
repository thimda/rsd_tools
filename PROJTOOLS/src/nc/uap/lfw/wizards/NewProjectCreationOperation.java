/*
 * Created on 2005-8-11
 * @author ºÎ¹ÚÓî
 */
package nc.uap.lfw.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.LFWVersion;
import nc.uap.lfw.core.WEBClassPathContainerID;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.internal.project.IProjectProvider;
import nc.uap.lfw.internal.util.ProjCoreUtility;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class NewProjectCreationOperation extends WorkspaceModifyOperation
{
	IProjectProvider projectProvider;

	public NewProjectCreationOperation(IProjectProvider provider)
	{
		this.projectProvider = provider;
		
	}

	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException
	{
		monitor.beginTask("Create Project...", 5);
		monitor.subTask("Create Project");
		IProject project = createProject();
		monitor.worked(1);
		if(LFWVersion.VERSION != null && LFWVersion.VERSION.equals(LFWVersion.VERSION_5X)){
			monitor.subTask("Create NC Home Link");
			ProjCoreUtility.creatFolderLink(project, "NC_HOME", new Path(WEBProjConstants.FIELD_NC_HOME));//project.getWorkspace().getPathVariableManager().getValue(MDEPlugin.FIELD_NC_HOME));
			monitor.worked(1);
		}
		monitor.subTask("Config project classpath...");
		computeInitClasspath(project);
		monitor.worked(1);
		monitor.subTask("Create build.properties");
		ProjCoreUtility.createBuildProperties(project, projectProvider.getModuleName(), projectProvider.getModuleConfig());
		monitor.worked(1);
		monitor.subTask("Create module configuation file");
		ProjCoreUtility.ceateInitManifest(project, projectProvider.getModuleConfig(), projectProvider.getModuleName());
		monitor.worked(1);
	}

	protected IProject createProject() throws CoreException
	{
		IProject project = projectProvider.getProject();
		if (!project.exists())
		{
			ProjCoreUtility.createProject(project, projectProvider.getLocationPath(), null);
			project.open(null);
		}
		if (!project.hasNature(JavaCore.NATURE_ID))
		{
			ProjCoreUtility.addNatureToProject(project, JavaCore.NATURE_ID, null);
		}
		if (!project.hasNature(WEBProjConstants.MODULE_NATURE))
			ProjCoreUtility.addNatureToProject(project, WEBProjConstants.MODULE_NATURE, null);
		
		if (!project.hasNature(WEBProjConstants.MDE_MODULE_NATURE))
			ProjCoreUtility.addNatureToProject(project, WEBProjConstants.MDE_MODULE_NATURE, null);

		return project;
	}

	private void computeInitClasspath(IProject project) throws CoreException
	{
		List<IClasspathEntry> list = new ArrayList<IClasspathEntry>();
		list.add(ProjCoreUtility.createSourceEntry(project, projectProvider.getPublicSrc(), projectProvider.getPublicOut()));
		list.add(ProjCoreUtility.createSourceEntry(project, projectProvider.getPrivateSrc(), projectProvider.getPrivateOut()));
		list.add(ProjCoreUtility.createSourceEntry(project, projectProvider.getClientSrc(), projectProvider.getClientOut()));
		//list.add(CoreUtility.createSourceEntry(project, fProjectProvider.getGenSrc(), fProjectProvider.getGenOut()));
		list.add(ProjCoreUtility.createSourceEntry(project, projectProvider.getResources(), projectProvider.getResourcesOut()));
		list.add(ProjCoreUtility.createSourceEntry(project, projectProvider.getTestSrc(), projectProvider.getTestOut()));
		//
		list.add(ProjCoreUtility.createJREEntry());
		for (WEBClassPathContainerID id : WEBClassPathContainerID.values())
		{
			list.add(ProjCoreUtility.createContainerClasspathEntry(id));	
		}
		//
		IJavaProject javaProject = JavaCore.create(project);
		javaProject.setRawClasspath(list.toArray(new IClasspathEntry[0]), null);
	}

	public IProjectProvider getProjectProvider() {
		return projectProvider;
	}
}