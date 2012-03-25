package nc.uap.lfw.perspective.action;

import java.util.ArrayList;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * ��������ѡ��
 * @author zhangxya
 *
 */
public class RefTypeSelDialog extends DialogWithTitle{

	public static String TREETYPE = "tree";
	public static String GRIDTYPE="grid";
	
	private Combo refTypeCom;
	
	
	private Text tableText;
	private Text modelText;
	private Text pubRefText;
	private Combo refPkText;
	private Combo refCodeText;
	private Combo refNameText;
	private Combo rootPathField;
	private Combo toPublicField;
	
	private Canvas cavas;
	private Combo pfield;
	private Combo childfield;
	
	private String modelClass;
	private String tableName;
	private String refPk;
	private String refCode;
	private String refName;
	private String labelField = "";
	private List labelFields = null;
	private List rightlabelFields = null;
	private Dataset ds;
	private boolean toPublic;
	private String refType;
	private String pFieldValue;
	private String childFieldValue;
	private String rootPath;
	private String pubRefId;
	
	public String getRefType() {
		return refType;
	}
	
	public String getModelClass(){
		return modelClass;
	}
	
	public String getRefPk(){
		return refPk;
	}
	
	public String getRefCode(){
		return refCode;
	}
	
	public String getRefName(){
		return refName;
	}
	
	public String getVisibleFields(){
		return labelField;
	}
	
	public RefTypeSelDialog(Shell parentShell, String title, Dataset ds) {
		super(parentShell, title);
		this.ds = ds;
	}
	
	protected void okPressed() {
		tableName = tableText.getText();
		if(tableName == null || tableName.equals("")){
			MessageDialog.openError(null, "������ʾ", "�����ò��ձ���!");
			return;
		}
		//tableName
		modelClass = modelText.getText();
		if(modelClass == null || modelClass.equals("") || modelClass.indexOf(".") == -1){
			MessageDialog.openError(null, "������ʾ", "�������ʽ����ȷ!");
			return;
		}
		//refcode
		refPk = refPkText.getText();
		if(refPk == null || refPk.equals("")){
			MessageDialog.openError(null, "������ʾ", "�����ò�������!");
			return;
		}
		//refcode
		refCode = refCodeText.getText();
		if(refCode == null || refCode.equals("")){
			MessageDialog.openError(null, "������ʾ", "�����ò��ձ���!");
			return;
		}
		//refname
		refName = refNameText.getText();
		if(refName == null || refName.equals("")){
			MessageDialog.openError(null, "������ʾ", "�����ò�������!");
			return;
		}
		//visibleFields
		String[] labelFields = rightlabelFields.getItems();
		for (int i = 0; i < labelFields.length; i++) {
			if(i != labelFields.length - 1)
				labelField += labelFields[i] + ",";
			else
				labelField += labelFields[i];
		}
		if(labelField == null || labelField.equals("")){
			MessageDialog.openError(null, "������ʾ", "�����ÿɼ��ֶ�!");
			return;
		}
		//����
		refType = (String) refTypeCom.getData(refTypeCom.getText());
		if(refType.equals(TREETYPE)){
			pFieldValue = pfield.getText();
			if(pFieldValue == null || pFieldValue.equals("")){
				MessageDialog.openError(null, "������ʾ", "�����������ֶ�!");
				return;
			}
			
			childFieldValue = childfield.getText();
			if(childFieldValue == null || childFieldValue.equals("")){
				MessageDialog.openError(null, "������ʾ", "�����������ֶ�!");
				return;
			}
		}
		
		rootPath = rootPathField.getText();
		
		toPublic = toPublicField.getData(toPublicField.getText()).equals("��");
		if(toPublic){
			pubRefId = pubRefText.getText();
			if(pubRefId == null || pubRefId.indexOf(".") == -1){
				MessageDialog.openError(null, "������ʾ", "����ȷ�Ĺ�������·��!");
				return;
			}
		}
		
		super.okPressed();
	}

	protected Control createDialogArea(Composite parent) {
		//setTitle("���ɴ���Ի���"); 
		Composite container = new Composite(parent , SWT.NONE);
		container.setLayout(new GridLayout(1,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group grouId = new Group(container, SWT.NONE);
		grouId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grouId.setLayout(new GridLayout(2,false));
		
		Label modellabel = new Label(grouId, SWT.NONE);
		modellabel.setText("��������:");
		modelText =  new Text(grouId, SWT.NONE);
		modelText.setLayoutData(new GridData(318,15));
		
		Label tablelabel = new Label(grouId, SWT.NONE);
		tablelabel.setText("���ձ���:");
		tableText =  new Text(grouId, SWT.NONE);
		tableText.setLayoutData(new GridData(318,15));
		
		
		Label rootPath = new Label(grouId, SWT.NONE);
		rootPath.setText("����Ŀ¼:");
		rootPathField = new Combo(grouId, SWT.READ_ONLY);
		rootPathField.setLayoutData(new GridData(115,20));
		String[] rootPackages = LFWPersTool.getAllRootPackage();
		if(rootPackages !=  null)
			rootPathField.setItems(rootPackages);
		rootPathField.select(0);
		
		
		Label refpklabel = new Label(grouId, SWT.NONE);
		refpklabel.setText("����������:");
		refPkText = new Combo(grouId, SWT.NONE);
		refPkText.setLayoutData(new GridData(300,15));
		
		Field[] fields = ds.getFieldSet().getFields();
		if(fields != null){
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				refPkText.add(field.getId());
			}
		}
		
		
		Label refcodeLabel = new Label(grouId, SWT.NONE);
		refcodeLabel.setText("���ձ�����");
		refCodeText = new Combo(grouId, SWT.NONE);
		refCodeText.setLayoutData(new GridData(300,15));
		if(fields != null){
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				refCodeText.add(field.getId());
			}
		}
		Label refNameLabel = new Label(grouId, SWT.NONE);
		refNameLabel.setText("����������");
		refNameText = new Combo(grouId, SWT.NONE);
		refNameText.setLayoutData(new GridData(300,15));
		
		if(fields != null){
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				refNameText.add(field.getId());
			}
		}
		Label visibleFieldsLabel = new Label(grouId, SWT.NONE);
		visibleFieldsLabel.setText("�ɼ��ֶα���");
		
		createVisibleFields(grouId);
			
		Label label = new Label(grouId, SWT.NONE);
		label.setText("����ѡ��:");
		refTypeCom = new Combo(grouId, SWT.READ_ONLY);
		refTypeCom.setLayoutData(new GridData(300,20));
		refTypeCom.add("���Ͳ���");
		refTypeCom.add("���Ͳ���");
		refTypeCom.setData("���Ͳ���", GRIDTYPE);
		refTypeCom.setData("���Ͳ���", TREETYPE);
		refTypeCom.select(0);
		
		
//		cavas = new Canvas(grouId, SWT.NONE);
//		cavas.setVisible(false);
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.horizontalSpan = 2;
//		cavas.setLayoutData(gd);
//		cavas.setLayout(new GridLayout(2,false));
		final Label ppkfield = new Label(grouId, SWT.NONE);
		ppkfield.setText("�����ֶ�:       ");
		pfield = new Combo(grouId, SWT.NONE);
		pfield.setLayoutData(new GridData(300,15));
		pfield.setEnabled(false);
		Field[] pfields = ds.getFieldSet().getFields();
		if(pfields != null){
			for (int i = 0; i < pfields.length; i++) {
				Field field = pfields[i];
				pfield.add(field.getId());
			}
		}
		
		final Label childpkfield = new Label(grouId, SWT.NONE);
		childpkfield.setText("�����ֶ�:");
		childfield = new Combo(grouId, SWT.NONE);
		childfield.setLayoutData(new GridData(300,15));
		childfield.setEnabled(false);
		Field[] childfields = ds.getFieldSet().getFields();
		if(childfields != null){
			for (int i = 0; i < childfields.length; i++) {
				Field field = childfields[i];
				childfield.add(field.getId());
			}
		}
		
		refTypeCom.addSelectionListener(new SelectionListener(){
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
			public void widgetSelected(SelectionEvent e) {
				Combo comb = (Combo) e.getSource();
				int index = comb.getSelectionIndex();
				if(index == 1){
					childpkfield.setEnabled(true);
					childfield.setEnabled(true);
					ppkfield.setEnabled(true);
					pfield.setEnabled(true);
//					cavas.setVisible(true);
				}
				else{
					childpkfield.setEnabled(false);
					childfield.setEnabled(false);
					ppkfield.setEnabled(false);
					pfield.setEnabled(false);
				}
			}
			
		});
		Label publicLabel = new Label(grouId, SWT.NONE);
		publicLabel.setText("�Ƿ񷢲���������:");
		toPublicField = new Combo(grouId, SWT.READ_ONLY);
		toPublicField.setLayoutData(new GridData(300,20));
		toPublicField.add("��");
		toPublicField.add("��");
		toPublicField.setData("��", "��");
		toPublicField.setData("��", "��");
		toPublicField.select(0);
		
		
		final Label pubField = new Label(grouId, SWT.NONE);
		pubField.setText("��������ȫ·��:");
		pubRefText =  new Text(grouId, SWT.NONE);
		pubRefText.setLayoutData(new GridData(318,15));
		
		toPublicField.addSelectionListener(new SelectionListener(){
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
			public void widgetSelected(SelectionEvent e) {
				Combo comb = (Combo) e.getSource();
				int index = comb.getSelectionIndex();
				if(index == 1){
					pubField.setEnabled(true);
					pubRefText.setEnabled(true);
				}
				else{
					pubField.setEnabled(false);
					pubRefText.setEnabled(false);
				}
			}
			
		});
		return container;
	}
	
	private void createVisibleFields(Group grouId){
		Group labelFieldsGroup = new Group(grouId, SWT.NONE);
		labelFieldsGroup.setLayoutData(new GridData(330,150));
		labelFieldsGroup.setLayout(new GridLayout(3,false));
		labelFields = new List(labelFieldsGroup, SWT.BORDER|SWT.MULTI| SWT.V_SCROLL);
		labelFields.setLayoutData(new GridData(100,125));
		java.util.List <String> list = new ArrayList<String>();
		Field[] fields = ds.getFieldSet().getFields();
		if(fields != null){
			int size = fields.length;
			for (int i = 0; i < size; i++) {
				list.add(fields[i].getId());
			}
		}
		labelFields.setItems((String[])list.toArray(new String[list.size()]));
			
		Group buttongroup = new Group(labelFieldsGroup,SWT.NONE|SWT.VERTICAL);
		buttongroup.setLayoutData(new GridData(40,50));
		buttongroup.setLayout(new GridLayout(1,false));
		//���ҵļ�ͷ
		Button rightbutton = new Button(buttongroup, SWT.RIGHT | SWT.ARROW | SWT.FILL);
		//����ļ�ͷ
		Button leftbutton = new Button(buttongroup, SWT.LEFT | SWT.ARROW);
		
		rightlabelFields = new List(labelFieldsGroup, SWT.BORDER|SWT.MULTI| SWT.V_SCROLL);
		rightlabelFields.setLayoutData(new GridData(100,125));
		rightbutton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				String[] leftlables = labelFields.getSelection();
				for (int i = 0; i < leftlables.length; i++) {
					rightlabelFields.add(leftlables[i]);
					labelFields.remove(leftlables[i]);
				}
				
			}
		});
		
		leftbutton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				String[] rightlabels = rightlabelFields.getSelection();
				for (int i = 0; i < rightlabels.length; i++) {
					labelFields.add(rightlabels[i]);
					rightlabelFields.remove(rightlabels[i]);
				}
			}
		});
	}

	public boolean isToPublic() {
		return toPublic;
	}

	public void setToPublic(boolean toPublic) {
		this.toPublic = toPublic;
	}

	public String getPFieldValue() {
		return pFieldValue;
	}

	public void setPFieldValue(String fieldValue) {
		pFieldValue = fieldValue;
	}

	public String getChildFieldValue() {
		return childFieldValue;
	}

	public void setChildFieldValue(String childFieldValue) {
		this.childFieldValue = childFieldValue;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public void setModelClass(String modelClass) {
		this.modelClass = modelClass;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPubRefId() {
		return pubRefId;
	}
	
}
