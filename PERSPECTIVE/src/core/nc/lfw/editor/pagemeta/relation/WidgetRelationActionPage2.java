package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.page.PluginDescItem;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget������ ִ�����ݹ����� �ڶ�ҳ
 * @author guoweic
 *
 */
public class WidgetRelationActionPage2 extends WizardPage {
	
	private WidgetRelationActionPanel2 mainContainer = null;
	
	protected WidgetRelationActionPage2(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("ִ�����ݹ�����");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationActionPanel2(parent, SWT.NONE);
		
		// ����ִ����������ѡ���¼�
		mainContainer.getTypeCombo().addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = mainContainer.getTypeCombo().getText();
				String data = (String) mainContainer.getTypeCombo().getData(text);
				//TODO
				if (PluginDescItem.TYPE_DS_LOAD.equals(data))
					setPageComplete(true);
				else
					setPageComplete(false);
			}
		});
		
//		setMessage("����ִ������");
		
		setControl(mainContainer);
	}

	public WidgetRelationActionPanel2 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * ���á���ɡ���ť״̬
	 */
	public boolean resetPageComplete() {
		if (!"".equals(mainContainer.getType()))
			return true;
		else
			return false;
	}

}
