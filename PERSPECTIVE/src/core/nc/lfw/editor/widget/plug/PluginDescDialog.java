package nc.lfw.editor.widget.plug;


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

/**
 * �½�plugindesc�Ի���
 * 
 * @author dingrf
 */
public class PluginDescDialog extends DialogWithTitle {

	/**ID�ı���*/
	private Text idText;

	/**ID*/
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Text getIdText() {
		return idText;
	}

	public void setIdText(Text idText) {
		this.idText = idText;
	}

	public PluginDescDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected void okPressed() {
		if ("".equals(idText.getText())) {
			MessageDialog.openInformation(this.getShell(), "��ʾ", "������ID");
			idText.setFocus();
			return;
		} 
		id = idText.getText();
		super.okPressed();
	}


	protected Point getInitialSize() {
		return new Point(250, 150);
	}

	@Override
	protected boolean isResizable(){
		return true;
	}
	
	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("id:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(150, 1));

		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}
}

