package nc.lfw.editor.pagemeta;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * UI ������ ���һҳ
 * 
 * @author guoweic
 *
 */
public class UIGuideLastPage extends WizardPage {

	protected UIGuideLastPage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub

		setTitle("UI ��װ");
		
		Composite main = new Composite(parent, SWT.NONE);
		
		setControl(main);
	}

}
