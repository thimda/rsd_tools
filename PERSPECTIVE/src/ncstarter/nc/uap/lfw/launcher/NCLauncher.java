package nc.uap.lfw.launcher;

import nc.lfw.editor.common.tools.LFWPersTool;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public abstract class NCLauncher {
	public void launch() {
		IProject[] projects = LFWPersTool.getOpenedLFWJavaProjects();
		IShortcut launcher = getClientShortcut();
		IProject proj = getLfwProj(projects);
		try {
			launcher.launch(proj, "debug");
		} 
		catch (CoreException e) {
			e.printStackTrace();
		}
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
	
	protected abstract IShortcut getClientShortcut();
	
	public void setProperty(String key, String value){
		System.setProperty(key, value);
	}
}
