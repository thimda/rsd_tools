/*
 * Created on 2005-8-11
 * @author �ι���
 */
package nc.uap.portal.wizards;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.internal.project.IProjectProvider;
import nc.uap.lfw.internal.util.ProjCoreUtility;
import nc.uap.lfw.wizards.NewProjectCreationOperation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public class PortalProjectCreationOperation extends NewProjectCreationOperation
{

	public PortalProjectCreationOperation(IProjectProvider provider)
	{
		super(provider);
	}

	protected IProject createProject() throws CoreException
	{
		IProject project = super.createProject();
		if (!project.hasNature(WEBProjConstants.PORTAL_MODULE_NATURE))
			ProjCoreUtility.addNatureToProject(project, WEBProjConstants.PORTAL_MODULE_NATURE, null);
		return project;
	}
}