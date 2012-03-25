package nc.uap.lfw.wizard;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
/**
 * 
 * ����wizard
 * @author dingrf
 *
 */
public class ExternalizeMLRWizard extends RefactoringWizard{

//	private ExternalizeMLRWizardPage1 page1;
	private ExternalizeMLRWizardPage2 page2;

	public ExternalizeMLRWizard(Refactoring refactoring){
		super(refactoring, 3);
//		page1 = null;
		page2 = null;
	}

	@Override
	protected void addUserInputPages(){
		MLRRefactoring refactoring = (MLRRefactoring)getRefactoring();
//		page1 = new ExternalizeMLRWizardPage1(refactoring);
//		page1.setMessage("����XML��Դģ�����Դ�ļ�����");
		page2 = new ExternalizeMLRWizardPage2(refactoring);
		page2.setMessage("LFW�ⲿ��������Դ");
//		addPage(page1);
		addPage(page2);
	}

	@Override
	public boolean canFinish(){
		if (page2.equals(getContainer().getCurrentPage())){
//			page1.updateRefactoring();
			page2.updateTableViewer();
		}
		return super.canFinish();
	}

	@Override
	public boolean performFinish(){
		return super.performFinish();
	}
}
