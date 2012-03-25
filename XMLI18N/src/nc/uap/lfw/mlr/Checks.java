package nc.uap.lfw.mlr;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * Refactoring У��
 * 
 * @author dingrf
 *
 */
public class Checks{

	/**
	 * ��֤�ļ���д
	 * 
	 * @param filesToModify
	 * @param context
	 * @return
	 */
	public static RefactoringStatus validateModifiesFiles(IFile filesToModify[], Object context){
		RefactoringStatus result = new RefactoringStatus();
		IFile readOnlyFiles[] = getReadOnlyFile(filesToModify);
		if (readOnlyFiles.length > 0){
			IStatus status = ResourcesPlugin.getWorkspace().validateEdit(readOnlyFiles, context);
			if (!status.isOK())
				result.merge(RefactoringStatus.create(status));
		}
		return result;
	}

	/**
	 * ȡ�ļ������е�ֻ���ļ�
	 * 
	 * @param files
	 * @return
	 */
	private static IFile[] getReadOnlyFile(IFile files[]){
		List<IFile> list = new ArrayList<IFile>();
		int count = files != null ? files.length : 0;
		for (int i = 0; i < count; i++)
			if (files[i].isReadOnly())
				list.add(files[i]);

		return (IFile[])list.toArray(new IFile[0]);
	}
}
