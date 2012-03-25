package nc.lfw.editor.pagemeta.relation;

import nc.lfw.editor.widget.WidgetElementObj;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget������ ִ�����ݹ����� ��һҳ
 * 
 * @author guoweic
 *
 */
public class WidgetRelationActionPage1 extends WizardPage {
	
	private WidgetElementObj targetWidget;
	
	private WidgetRelationActionPanel1 mainContainer = null;
	
	protected WidgetRelationActionPage1(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("ִ�����ݹ�����");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationActionPanel1(parent, SWT.NONE, targetWidget);
		
		// ����ִ��������������¼�����
		mainContainer.getNameText().addModifyListener(new MyModifyListener());
		
//		setMessage("ִ������ʱ��");
		
		setControl(mainContainer);
	}

	public WidgetRelationActionPanel1 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * ִ�������������ı��¼������������ơ���һ�����͡���ɡ���ť
	 * 
	 * @author guoweic
	 *
	 */
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

	public void setTargetWidget(WidgetElementObj targetWidget) {
		this.targetWidget = targetWidget;
	}

}
