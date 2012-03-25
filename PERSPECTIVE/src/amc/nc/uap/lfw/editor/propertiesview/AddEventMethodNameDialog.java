/**
 * 
 */
package nc.uap.lfw.editor.propertiesview;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import nc.lfw.editor.common.DialogWithTitle;

/**
 * @author chouhl
 *
 */
public class AddEventMethodNameDialog extends DialogWithTitle {

	/**
	 * ���������
	 */
	private Text nameText;
	/**
	 * �¼�����
	 */
	private String name;
	
	public AddEventMethodNameDialog(String title) {
		super(null, title);
	}
	
	public AddEventMethodNameDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		//����
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("�¼�����:");
		nameText = new Text(container, SWT.NONE);
		nameText.setLayoutData(new GridData(220,20));
		nameText.setText(name != null ? name : "");
		
		return container;
	}
	
	protected void okPressed() {
		if(nameText.getText().trim().length() == 0){
			MessageDialog.openError(null, "������ʾ", "���Ʋ���Ϊ��");
			return;
		}
		setName(nameText.getText().trim());
		super.okPressed();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
