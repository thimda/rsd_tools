package nc.uap.lfw.actions;

import nc.uap.lfw.tool.ProjConstants;
import nc.uap.lfw.wizard.ExternalizeMLRWizard;
import nc.uap.lfw.wizard.MLRRefactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.custom.BusyIndicator;

/**
 * XML多语资源外部化
 * 
 * @author dingrf
 * 
 */
public class ExternalizeMLRActionDelegate extends AbstractMLResRefactorActionDelegate {

	@Override
	public void run(IAction action) {
		final IProject project = getLfwProject(selection);
		BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
			public void run() {
				if (project != null) {
					try {
						MLRRefactoring refactoring = MLRRefactoring.create(project);
						if (refactoring != null) {
							//打开XML多语资源向导
							RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
									new ExternalizeMLRWizard(refactoring));
							op.run(getShell(),ProjConstants.MORE_LANGUAGE_RESOURCES);
						}
					} catch (Exception e) {
						MessageDialog.openError(getShell(), "err",
								(new StringBuilder()).append(e.getClass())
										.append(":").append(e.getMessage())
										.toString());
					}
				}
			}
		});
	}
}
