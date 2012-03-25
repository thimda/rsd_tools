package nc.lfw.editor.pagemeta.relation;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Widget�������� �����Զ�������Ի���
 * 
 * @author guoweic
 *
 */
public class WidgetRelationSelfParamDialog extends Dialog {
	
	private WidgetRelationSelfParamPanel mainContainer;
	
	private String name;
	
	private String desc;

	protected WidgetRelationSelfParamDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Point getInitialSize() {
		return new Point(300, 150); 
	}
	
	
	public Control createDialogArea(Composite parent) {

		mainContainer = new WidgetRelationSelfParamPanel(parent, SWT.NONE);
		
		return mainContainer;
	}
	
	protected void okPressed() {
		name = mainContainer.getNameText().getText().trim();
		desc = mainContainer.getDescText().getText().trim();
		if (!"".equals(name)) {
			super.okPressed();
		} else {
			MessageDialog.openInformation(null, "��ʾ", "���Ʋ���Ϊ��");
			mainContainer.getNameText().setFocus();
		}
	}

	public WidgetRelationSelfParamPanel getMainContainer() {
		return mainContainer;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
}
