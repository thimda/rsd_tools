package nc.uap.lfw.launcher;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public interface IShortcut {
	public void launch(IProject project, String mode) throws CoreException;
}
