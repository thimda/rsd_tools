package nc.lfw.editor.menubar.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * ѡ��Ĭ�ϲ˵��� �ڶ�ҳ
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemPage2 extends WizardPage {
	
	private DefaultMenuItemPanel2 mainContainer;
	
	protected DefaultMenuItemPage2(String pageName) {
		super(pageName);
	}

	
	public void createControl(Composite parent) {
		
		setTitle("Ĭ�ϲ˵��� ������");

		setPageComplete(false);
		
		mainContainer = new DefaultMenuItemPanel2(parent, SWT.NONE);
		
		// ����������¼�����
		mainContainer.getPackageText().addModifyListener(new MyModifyListener());
		mainContainer.getClassPrefixText().addModifyListener(new MyModifyListener());
		
		mainContainer.getSourceFolderCombo().addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(checkCanFinished());
			}
		});
		
		setMessage("����Ĭ�ϲ˵���");
		
		setControl(mainContainer);
	}
	
	/**
	 * �����ı��¼������������ơ���һ����������ɡ���ť
	 * 
	 * @author guoweic
	 *
	 */
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(checkCanFinished());
		}
	}
	
	/**
	 * ���á���ɡ��Ƿ����
	 */
	public boolean checkCanFinished() {
		String packageName = "";
		String sourceFolder = "";
		String classPrefix = "";
		packageName = mainContainer.getPackageText().getText().trim();
		sourceFolder = (String) mainContainer.getSourceFolderCombo().getData(mainContainer.getSourceFolderCombo().getText());
		classPrefix = mainContainer.getClassPrefixText().getText().trim();
		// У��
		if (!"".equals(packageName) && !"".equals(sourceFolder) && !"".equals(classPrefix))
			return true;
		return false;
    }

	public DefaultMenuItemPanel2 getMainContainer() {
		return mainContainer;
	}

}
