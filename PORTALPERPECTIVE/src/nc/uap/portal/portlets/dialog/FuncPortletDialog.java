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
public class FuncPortletDialog extends DialogWithTitle {

	/**ID�ı���*/
	private Text idText;

	/**�����ı���*/
	private Text nameText;
	
	/**Ŀ¼�ı���*/
	private Text cateText;
	
	/**ID*/
	private String id;

	/**����*/
	private String name;
	
	/**category*/
	private String cate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public String getCate() {
		return cate;
	}

	public void setName(String name) {
		this.name = name;
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

	public FuncPortletDialog(Shell parentShell, String title) {
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
		cate = cateText.getText();
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
		
//		new Label(container, SWT.NONE).setText("view-jsp:");
//		viewText = new Text(container, SWT.BORDER);
//		viewText.setLayoutData(createGridData(250, 1));
//		
//		new Label(container, SWT.NONE).setText("edit-jsp:");
//		editText = new Text(container, SWT.BORDER);
//		editText.setLayoutData(createGridData(250, 1));
		
		new Label(container, SWT.NONE).setText("�˵���:");
		cateText = new Text(container, SWT.BORDER);
		cateText.setLayoutData(createGridData(250, 1));
		
		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}
}

