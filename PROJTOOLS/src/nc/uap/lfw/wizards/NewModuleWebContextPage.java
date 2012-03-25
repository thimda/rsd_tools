package nc.uap.lfw.wizards;

import java.util.regex.Pattern;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;

import org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class NewModuleWebContextPage extends WizardPage implements
		IPropertyChangeListener, IDialogFieldListener {

	//����Ŀ��ѡ��ť
	private SelectionButtonDialogField fMainRadio;
	
	//������Ŀ��ѡ��ť
	private SelectionButtonDialogField fPartRadio;
	
	//����Ŀ������·��
	private StringDialogField mainPath;
	
	//������Ŀ������·��
	private StringDialogField partPath;

	public StringDialogField getMainPath() {
		return mainPath;
	}

	public void setMainPath(StringDialogField mainPath) {
		this.mainPath = mainPath;
	}

	public StringDialogField getPartPath() {
		return partPath;
	}

	public SelectionButtonDialogField getMainRadio() {
		return fMainRadio;
	}

	public SelectionButtonDialogField getPartRadio() {
		return fPartRadio;
	}

	protected NewModuleWebContextPage(String pageName) {
		super(pageName);
		setTitle(WEBProjPlugin
				.getResourceString(WEBProjConstants.KEY_NEWPRJ_MAINPAGE_TITLE));
		setDescription(WEBProjPlugin
				.getResourceString(WEBProjConstants.KEY_NEWPRJ_MAINPAGE_DESC));
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(initGridLayout(new GridLayout(1, false), true));
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		PartGroup(composite);
		setControl(composite);
	}

	public Group PartGroup(Composite composite) {
		return PartGroup(composite, true, null);
	}
	/**
	 * ����Ŀ�������Ŀ��
	 * 
	 * @param composite
	 * @return
	 */
	public Group PartGroup(Composite composite, boolean mainProject, String name) {
		final int numColumns = 2;
		final Group group = new Group(composite, SWT.NONE);
		group
				.setLayout(initGridLayout(new GridLayout(numColumns, false),
						true));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fMainRadio = new SelectionButtonDialogField(SWT.RADIO);
		fMainRadio.setLabelText("����Ŀ"); 
		fMainRadio.setDialogFieldListener(this);
		mainPath = new StringDialogField();
		mainPath.setLabelText("������·��:");
		mainPath.setDialogFieldListener(this);
		fMainRadio.attachDialogField(mainPath);

		fPartRadio = new SelectionButtonDialogField(SWT.RADIO);
		fPartRadio.setLabelText("������Ŀ"); 
		fPartRadio.setDialogFieldListener(this);
		partPath = new StringDialogField();
		partPath.setLabelText("����Ŀ������·��:");
		if(name != null)
			partPath.setText(name);
		partPath.setDialogFieldListener(this);
		fPartRadio.attachDialogField(partPath);
		fMainRadio.setSelection(mainProject);
		fPartRadio.setSelection(!mainProject);
		fMainRadio.doFillIntoGrid(group, numColumns);
		mainPath.doFillIntoGrid(group, numColumns);
		fPartRadio.doFillIntoGrid(group, numColumns);
		partPath.doFillIntoGrid(group, numColumns);
		LayoutUtil.setHorizontalGrabbing(mainPath.getTextControl(null));
		LayoutUtil.setHorizontalGrabbing(partPath.getTextControl(null));
		return group;
	}

	protected GridLayout initGridLayout(GridLayout layout, boolean margins) {
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		if (margins) {
			layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
			layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		} else {
			layout.marginWidth = 0;
			layout.marginHeight = 0;
		}
		return layout;
	}

	/**
	 * У�� ����ĿʱmainPath����Ϊ�գ�������������ţ�
	 * ������ĿʱpartPaht����Ϊ�գ�������������š�
	 */
	private void validate() {
		if (fMainRadio.isSelected() && mainPath != null) {
			if (mainPath.getText().equals("")) {
				setPageComplete(false);
				return;
			}
			Pattern pattern = Pattern.compile("[!@~#$%^&*-+]+");
			if (pattern.matcher(mainPath.getText().trim()).find()) {
				setPageComplete(false);
				return;
			}
		}
		if (fPartRadio.isSelected() && partPath != null) {
			if (partPath.getText().equals("")) {
				setPageComplete(false);
				return;
			}
			Pattern pattern = Pattern.compile("[!@~#$%^&*-+]+");
			if (pattern.matcher(partPath.getText().trim()).find()) {
				setPageComplete(false);
				return;
			}
		}
		setPageComplete(true);
	}

	public void propertyChange(PropertyChangeEvent event) {
		validate();
	}

	public void dialogFieldChanged(DialogField field) {
		validate();
	}
}