package nc.lfw.editor.pagemeta.relation;

import nc.lfw.editor.widget.WidgetElementObj;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget������ ����ʱ�������� ��һҳ
 * 
 * @author guoweic
 *
 */
public class WidgetRelationEmitPage1 extends WizardPage {
	
	private WidgetElementObj sourceWidget;
	
	private WidgetRelationEmitPanel1 mainContainer = null;
	
	protected WidgetRelationEmitPage1(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("����ʱ��������");
		
		setPageComplete(true);
		
		mainContainer = new WidgetRelationEmitPanel1(parent, SWT.NONE, sourceWidget);
		
		// ���Ӵ���ʱ����������¼�����
//		mainContainer.getNameText().addModifyListener(new MyModifyListener());
		
//		setMessage("��������ʱ��");
		
		setControl(mainContainer);
	}

	public void setSourceWidget(WidgetElementObj sourceWidget) {
		this.sourceWidget = sourceWidget;
	}

	public WidgetRelationEmitPanel1 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * ����ʱ���������ı��¼������������ơ���һ�����͡���ɡ���ť
	 * 
	 * @author guoweic
	 *
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(false);
			String name = mainContainer.getNameText().getText().trim();
			if (!"".equals(name) && null != mainContainer.getCurrentComp()) {
				mainContainer.setName(name);
				setPageComplete(true);
			}
		}
	}
	 */

}
