package nc.uap.portal.theme.dialog;

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
 * �½�����Ի���
 * 
 * @author dingrf
 */
public class ThemeDialog extends DialogWithTitle {

	/**ID�ı���*/
	private Text idText;

	/**�����ı���*/
	private Text titleText;
	
	/**�����ı���*/
	private Text i18nNameText;
	
	/**����ID�ı���*/
	private Text themeIdText;

	/**����ID*/
	private String id;

	/**��������*/
	private String title;
	
	/**�������id*/
	private String i18nName;
	
	/**����ID*/
	private String themeId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getI18nName() {
		return i18nName;
	}

	public void setI18nName(String name) {
		this.i18nName = name;
	}

	public String getThemeId() {
		return themeId;
	}
	
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	
	public Text getIdText() {
		return idText;
	}

	public Text getTitleText() {
		return titleText;
	}

	public Text getI18nNameText() {
		return i18nNameText;
	}

	public Text getThemeIdText() {
		return themeIdText;
	}

	public ThemeDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected void okPressed() {
		if ("".equals(idText.getText())) {
			MessageDialog.openInformation(this.getShell(), "��ʾ", "������ID");
			idText.setFocus();
			return;
		} else if ("".equals(titleText.getText())) {
			MessageDialog.openInformation(this.getShell(), "��ʾ", "������title");
			titleText.setFocus();
			return;
		} else if ("".equals(themeIdText.getText())) {
			MessageDialog.openInformation(this.getShell(), "��ʾ", "������lfw-theme-id");
			themeIdText.setFocus();
			return;
		}
		id = idText.getText();
		title = titleText.getText();
		i18nName =i18nNameText.getText();
		themeId =themeIdText.getText();
		super.okPressed();
	}


	protected Point getInitialSize() {
		return new Point(250, 210);
	}

	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("ID:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(150, 1));

		new Label(container, SWT.NONE).setText("text:");
		titleText = new Text(container, SWT.BORDER);
		titleText.setLayoutData(createGridData(150, 1));

		new Label(container, SWT.NONE).setText("i18nName:");
		i18nNameText = new Text(container, SWT.BORDER);
		i18nNameText.setLayoutData(createGridData(150, 1));
		
		new Label(container, SWT.NONE).setText("lfw-theme-id:");
		themeIdText = new Text(container, SWT.BORDER);
		themeIdText.setLayoutData(createGridData(150, 1));
		
		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}
}

