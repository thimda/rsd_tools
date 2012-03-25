package nc.uap.portal.portlets.dialog;


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
 * �½�jspportlet�Ի���
 * 
 * @author dingrf
 */
public class JspPortletDialog extends DialogWithTitle {

	/**ID�ı���*/
	private Text idText;

	/**�����ı���*/
	private Text nameText;
	
	/**view-jsp�ı���*/
	private Text viewText;
	
	/**edit-jsp�ı���*/
	private Text editText;
	
	/**help-jsp�ı���*/
	private Text helpText;
	
	/**ID*/
	private String id;

	/**����*/
	private String name;
	
	/**view-jsp*/
	private String view;
	
	/**edit-jsp*/
	private String edit;
	
	/**help-jsp*/
	private String help;
	
	
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

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public Text getIdText() {
		return idText;
	}

	public Text getNameText() {
		return nameText;
	}

	public void setNameText(Text nameText) {
		this.nameText = nameText;
	}

	public void setIdText(Text idText) {
		this.idText = idText;
	}

	public Text getViewText() {
		return viewText;
	}

	public void setViewText(Text viewText) {
		this.viewText = viewText;
	}

	public Text getEditText() {
		return editText;
	}

	public void setEditText(Text editText) {
		this.editText = editText;
	}

	public Text getHelpText() {
		return helpText;
	}

	public void setHelpText(Text helpText) {
		this.helpText = helpText;
	}

	public JspPortletDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected void okPressed() {
		if ("".equals(idText.getText())) {
			MessageDialog.openInformation(this.getShell(), "��ʾ", "������ID");
			idText.setFocus();
			return;
		} else if ("".equals(nameText.getText())) {
			MessageDialog.openInformation(this.getShell(), "��ʾ", "������name");
			nameText.setFocus();
			return;
		} 
		id = idText.getText();
		name = nameText.getText();
		view = viewText.getText();
		edit = editText.getText();
		help = helpText.getText();
		super.okPressed();
	}


	protected Point getInitialSize() {
		return new Point(350, 230);
	}

	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("id:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(250, 1));

		new Label(container, SWT.NONE).setText("name:");
		nameText = new Text(container, SWT.BORDER);
		nameText.setLayoutData(createGridData(250, 1));
		
		new Label(container, SWT.NONE).setText("view-jsp:");
		viewText = new Text(container, SWT.BORDER);
		viewText.setLayoutData(createGridData(250, 1));
		
		new Label(container, SWT.NONE).setText("edit-jsp:");
		editText = new Text(container, SWT.BORDER);
		editText.setLayoutData(createGridData(250, 1));
		
		new Label(container, SWT.NONE).setText("help-jsp:");
		helpText = new Text(container, SWT.BORDER);
		helpText.setLayoutData(createGridData(250, 1));
		
		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}
}

