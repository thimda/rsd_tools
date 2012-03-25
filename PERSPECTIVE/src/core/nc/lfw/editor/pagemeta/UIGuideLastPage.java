package nc.lfw.editor.pagemeta;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * UI 创建向导 最后一页
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

		setTitle("UI 组装");
		
		Composite main = new Composite(parent, SWT.NONE);
		
		setControl(main);
	}

}
