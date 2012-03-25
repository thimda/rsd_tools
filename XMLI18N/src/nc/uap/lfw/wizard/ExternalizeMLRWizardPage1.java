package nc.uap.lfw.wizard;

import nc.uap.lfw.tool.Helper;
import nc.uap.lfw.tool.LangResStructScaner;
import nc.uap.lfw.tool.ProjConstants;

import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * WizardPage1
 * 
 * @author dingrf
 *
 */
public class ExternalizeMLRWizardPage1 extends UserInputWizardPage{

	private Text resModuleText;
	private Text resFileNameText;
	private Button customResFileNameCB;
	private Button selResModuleBtn;
	private MLRRefactoring mlrRefactoring;

	public ExternalizeMLRWizardPage1(MLRRefactoring mlrRefactoring){
		super("������Դģ�����Դ�ļ�����");
		this.mlrRefactoring = mlrRefactoring;
	}

	public void createControl(Composite parent){
		Composite container = new Composite(parent, 0);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		Label moduleLbl = new Label(container, 0);
		moduleLbl.setText("������Դ���");
		moduleLbl.setLayoutData(new GridData());
		resModuleText = new Text(container, 2052);
		resModuleText.setLayoutData(new GridData(768));
//		resModuleText.setText(mlrRefactoring.getResModuleName());
		resModuleText.selectAll();
		resModuleText.addVerifyListener(new VerifyListener() {
			//��ĸСд
			public void verifyText(VerifyEvent e){
				if (e.text != null)
					e.text = e.text.toLowerCase();
			}
		});
		resModuleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e){
				doneResModuleTextModify();
			}
			
		});
		selResModuleBtn = new Button(container, 8);
		selResModuleBtn.setText("...");
		selResModuleBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				doneSelResModuleBtnClicked();
			}
		});
		customResFileNameCB = new Button(container, 32);
		customResFileNameCB.setText("�Զ�����Դ�ļ�����");
		customResFileNameCB.setSelection(false);
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		customResFileNameCB.setLayoutData(gd);
		customResFileNameCB.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e)
			{
				resFileNameText.setEditable(customResFileNameCB.getSelection());
				updateResFileNameText();
			}

		});
		Label resFileNameLbl = new Label(container, 0x20000);
		resFileNameLbl.setText("������Դ�ļ�����");
		resFileNameLbl.setLayoutData(new GridData());
		resFileNameText = new Text(container, 2052);
		GridData gdata = new GridData(768);
		gdata.horizontalSpan = 2;
		resFileNameText.setLayoutData(gdata);
//		resFileNameText.setText(mlrRefactoring.getResFileName());
		resFileNameText.selectAll();
		resFileNameText.setEditable(customResFileNameCB.getSelection());
		resFileNameText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e)
			{
				setPageComplete(isComplete());
			}
			
		});
		setControl(container);
		setPageComplete(isComplete());
	}

	/**
	 * ѡ�������Դ���
	 * 
	 */
	protected void doneSelResModuleBtnClicked(){
		String modulesStrs[] = (new LangResStructScaner(mlrRefactoring.getProject())).findLangMoudles(mlrRefactoring.getResouceHomePath(), "simpchn", false);
		String modules[][] = new String[modulesStrs.length][2];
		for (int i = 0; i < modulesStrs.length; i++){
			modules[i][0] = "0";
			modules[i][1] = modulesStrs[i];
		}

		LangModuleSelDlg dlg = new LangModuleSelDlg(getShell(), modules, true);
		int result = dlg.open();
		if (result == 0){
			for (int i = 0; i < modules.length; i++){
				if (!modules[i][0].equals("1"))
					continue;
				resModuleText.setText(modules[i][1]);
				break;
			}
		}
	}

	/**
	 * ���ö�����Դ�ļ���
	 * 
	 */
	private void updateResFileNameText(){
		if (!customResFileNameCB.getSelection()){
			StringBuilder sb = new StringBuilder();
			String resModuleName = resModuleText.getText().trim();
			if (!Helper.isEmptyString(resModuleName))
				sb.append(resModuleName).append("res").append(".properties");
			resFileNameText.setText(sb.toString());
		}
	}

	/**
	 * ������Դ������ı�
	 * 
	 */
	protected void doneResModuleTextModify(){
		updateResFileNameText();
		setPageComplete(isComplete());
	}

	public boolean isComplete(){
		String resModuleName = resModuleText.getText().trim();
		String resFileName = resFileNameText.getText().trim();
		return !Helper.isEmptyString(resModuleName) && !Helper.isEmptyString(resFileName) 
			&& !resModuleName.equalsIgnoreCase(ProjConstants.COMM);
	}

	public void updateRefactoring(){
		String resModuleName = resModuleText.getText().trim();
		String resFileName = resFileNameText.getText().trim();
//		if (!resModuleName.equals(mlrRefactoring.getResModuleName())){
////			mlrRefactoring.setResModuleName(resModuleName);
//		}
//		if (!resFileName.equals(mlrRefactoring.getResFileName())){
//			mlrRefactoring.setResFileName(resFileName);
//		}
	}



}
