package nc.lfw.editor.pagemeta;

import nc.lfw.editor.common.DialogWithTitle;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreatePageMetaDialog extends DialogWithTitle {

	private Text idText;
	private Text nameText;
	private String id;
	private String name;
	
	protected Point getInitialSize() {
		return new Point(250, 150); 
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreatePageMetaDialog(Shell parentShell, String title) {
		super(parentShell, title);
		// TODO Auto-generated constructor stub
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label idLabel = new Label(container, SWT.NONE);
		idLabel.setText("Id:");
		idText = new Text(container, SWT.NONE);
		idText.setLayoutData(new GridData(220,20));
		idText.setText("");
		
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("名称:");
		nameText = new Text(container, SWT.NONE);
		nameText.setLayoutData(new GridData(220,20));
		nameText.setText("");
		return container;
	}
	
	protected void okPressed() {
		String id = idText.getText();
		if(id == null || id.equals("")){
			MessageDialog.openError(null, "错误提示", "Id不能为空");
			return;
		}
		setId(id);
		String name = nameText.getText();{
			if(name == null || name.equals("")){
				MessageDialog.openError(null, "错误提示", "名称不能为空");
				return;
			}
		}
		setName(name);
		super.okPressed();
	}
}
