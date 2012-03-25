package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.page.PlugoutDescItem;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget������ ���������� �ڶ�ҳ
 * 
 * @author guoweic
 *
 */
public class WidgetRelationParamPage2 extends WizardPage {
	
	private WidgetRelationParamPanel2 mainContainer = null;
	
	protected WidgetRelationParamPage2(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("����������");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationParamPanel2(parent, SWT.NONE);
		
		// ���Ӳ�����������¼�����
		mainContainer.getFormularText().addModifyListener(new MyModifyListener());
		
		// ���Ӳ�������ѡ���¼�
		mainContainer.getTypeCombo().addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = mainContainer.getTypeCombo().getText();
				String data = (String) mainContainer.getTypeCombo().getData(text);
				if (PlugoutDescItem.TYPE_DS_FIELD.equals(data))
					setPageComplete(true);
				else if (PlugoutDescItem.TYPE_FOMULAR.equals(mainContainer.getType())) {  // ��ʽ����
					String formular = mainContainer.getFormularText().getText().trim();
					if (!"".equals(formular)) {
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
				else
					setPageComplete(false);
			}
		});
		
//		setMessage("��������");
		
		setControl(mainContainer);
	}

	public WidgetRelationParamPanel2 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * ��ʽ�����ı��¼������������ơ���ɡ���ť
	 * 
	 * @author guoweic
	 *
	 */
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(false);
			String formular = mainContainer.getFormularText().getText().trim();
			// ��ʽ����
			if (PlugoutDescItem.TYPE_FOMULAR.equals(mainContainer.getType()) && !"".equals(formular)) {
				mainContainer.setValue(formular);
				setPageComplete(true);
			}
		}
	}

}
