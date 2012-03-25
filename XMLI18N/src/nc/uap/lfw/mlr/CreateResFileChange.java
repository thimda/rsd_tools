package nc.uap.lfw.mlr;

import nc.uap.lfw.tool.Helper;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.internal.corext.refactoring.nls.changes.CreateTextFileChange;
import org.eclipse.ltk.core.refactoring.Change;

/**
 * ������Դ��FileChange
 * 
 * @author dingrf
 *
 */
public class CreateResFileChange extends CreateTextFileChange{

	public CreateResFileChange(IPath path, String source, String encoding){
		super(path, source, encoding, "properties");
	}

	@Override
	public Change perform(IProgressMonitor pm)throws CoreException, OperationCanceledException{
		IFile file = getOldFile(pm);
		IContainer parent = file.getParent();
		//������Դ�ļ�
		if (!parent.exists())
			Helper.createResource(parent);
		return super.perform(pm);
	}
}
