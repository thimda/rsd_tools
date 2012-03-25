package nc.uap.lfw.tree.treelevel;

import java.util.ArrayList;

import nc.lfw.editor.common.LFWBasicElementObj;
import nc.uap.lfw.core.comp.RecursiveTreeLevel;
import nc.uap.lfw.core.comp.TreeLevel;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.tree.TreeElementObj;
import nc.uap.lfw.tree.core.TreeLevelElementObj;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

/**
 * �ݹ���ҳ������
 * @author zhangxya
 *
 */
public class TreeLevelRecusivePage extends WizardPage{

	public Combo getTreeType() {
		return treeType;
	}
	public void setTreeType(Combo treeType) {
		this.treeType = treeType;
	}
	public Text getDataset() {
		return dataset;
	}
	public void setDataset(Text dataset) {
		this.dataset = dataset;
	}
	public Combo getRecursiveKeyField() {
		return recursiveKeyField;
	}
	public void setRecursiveKeyField(Combo recursiveKeyField) {
		this.recursiveKeyField = recursiveKeyField;
	}
	public Combo getRecursivePKeyField() {
		return recursivePKeyField;
	}
	public void setRecursivePKeyField(Combo recursivePKeyField) {
		this.recursivePKeyField = recursivePKeyField;
	}
	public Combo getMasterKeyField() {
		return masterKeyField;
	}
	public void setMasterKeyField(Combo masterKeyField) {
		this.masterKeyField = masterKeyField;
	}
	public List getLabelFields() {
		return labelFields;
	}
	public void setLabelFields(List labelFields) {
		this.labelFields = labelFields;
	}
	public Text getLabelDelims() {
		return labelDelims;
	}
	public void setLabelDelims(Text labelDelims) {
		this.labelDelims = labelDelims;
	}
	public Text getId() {
		return id;
	}
	public void setId(Text id) {
		this.id = id;
	}
	
	private String flag;

	protected TreeLevelRecusivePage(String pageName) {
		super(pageName);
		
		// TODO Auto-generated constructor stub
	}

	private TreeLevelElementObj treelevel = null;
	
	public TreeLevelElementObj getTreelevel() {
		return treelevel;
	}
	public void setTreelevel(TreeLevelElementObj treelevel) {
		this.treelevel = treelevel;
	}
	private LFWBasicElementObj treeobj = null;
	public TreeLevelRecusivePage(String pageName, LFWBasicElementObj treeobj,  TreeLevelElementObj treelevel, String flag){
		super(pageName);
		this.treelevel = treelevel;
		this.treeobj = treeobj;
		this.flag = flag;
		
	}
	
	protected IDialogSettings getDialogSettings() {
		// TODO Auto-generated method stub
		return super.getDialogSettings();
	}

	private Combo treeType = null;
	private Text dataset = null;
	private Combo recursiveKeyField = null;
	private Combo recursivePKeyField = null;
	private Combo masterKeyField = null;
	private List labelFields = null;
	private List rightlabelFields = null;
	public List getRightlabelFields() {
		return rightlabelFields;
	}
	public void setRightlabelFields(List rightlabelFields) {
		this.rightlabelFields = rightlabelFields;
	}

	private Text labelDelims = null;
	private Text id = null;
	private Text detailKeyParameter = null;
	
	public Text getDetailKeyParameter() {
		return detailKeyParameter;
	}
	public void setDetailKeyParameter(Text detailKeyParameter) {
		this.detailKeyParameter = detailKeyParameter;
	}
	public IWizardPage getNextPage() {
		return null;
	}
	

	public void createControl(Composite parent) {
		setTitle("����Tree Level�Ի���"); 
		setMessage("����Tree Level�Ի���");
		Composite container = new Composite(parent , SWT.NONE);
		container.setLayout(new GridLayout(1,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group grouId = new Group(container, SWT.NONE);
		grouId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grouId.setLayout(new GridLayout(2,false));
		Label labelId = new Label(grouId, SWT.NONE);
		labelId.setText("TreeLeve ID:");
		id = new Text(grouId, SWT.BORDER);
		id.setLayoutData(new GridData(140,15));
		id.setText("level1");
		
		Label DatasetLabel = new Label(grouId, SWT.NONE);
		DatasetLabel.setText("����Դ:");
		
		
		dataset = new Text(grouId, SWT.BORDER);
		dataset.setLayoutData(new GridData(140,15));
		dataset.setText(treelevel.getDs().getId());
		dataset.setEditable(false);
		
		
		Label recursiveKeyFieldLabel = new Label(grouId, SWT.NONE);
		recursiveKeyFieldLabel.setText("�ݹ��ֶ�:");
		recursiveKeyField = new Combo(grouId, SWT.READ_ONLY);
		recursiveKeyField.setLayoutData(new GridData(120,15));
		Field[] fields = treelevel.getDs().getFieldSet().getFields();
		java.util.List<String> list = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			String fieldId = fields[i].getId();
			if(fieldId != null)
				list.add(fieldId);
		}
		
		recursiveKeyField.setItems((String[])list.toArray(new String[list.size()]));
		recursiveKeyField.select(0);
		
		Label recursivePKeyFieldLabel = new Label(grouId, SWT.NONE);
		recursivePKeyFieldLabel.setText("�ݹ鸸�ֶ�:");
		recursivePKeyField = new Combo(grouId, SWT.READ_ONLY);
		recursivePKeyField.setLayoutData(new GridData(120,15));
		recursivePKeyField.setItems((String[])list.toArray(new String[list.size()]));
		recursivePKeyField.select(0);
		
		Label masterKeyFieldLabel = new Label(grouId, SWT.NONE);
		masterKeyFieldLabel.setText("����:");
		masterKeyField = new Combo(grouId, SWT.READ_ONLY);
		masterKeyField.setLayoutData(new GridData(120,15));
		masterKeyField.setItems((String[])list.toArray(new String[list.size()]));
		masterKeyField.select(0);
		
		Label detailKeyParameterLabel = new Label(grouId, SWT.NONE);
		detailKeyParameterLabel.setText("���:");
		detailKeyParameter = new Text(grouId, SWT.BORDER);
		detailKeyParameter.setLayoutData(new GridData(140,15));
		String primarykey = "";
		for (int i = 0; i < fields.length; i++) {
			Field fi = fields[i];
			if(fi.isPrimaryKey()){
				primarykey = fi.getId();
				break;
			}
		}
		detailKeyParameter.setText(primarykey);
		if(flag != null && flag.equals("Y"))
			detailKeyParameter.setEditable(false);
		
		Label labelFieldsLabel = new Label(grouId, SWT.NONE);
		labelFieldsLabel.setText("��ʾ�ֶ�:");
		
		
		Group labelFieldsGroup = new Group(grouId, SWT.NONE);
		labelFieldsGroup.setLayoutData(new GridData(450,150));
		labelFieldsGroup.setLayout(new GridLayout(3,false));
		labelFields = new List(labelFieldsGroup, SWT.BORDER|SWT.MULTI| SWT.V_SCROLL);
		labelFields.setLayoutData(new GridData(120,125));
		labelFields.setItems((String[])list.toArray(new String[list.size()]));
		
		
		Group buttongroup = new Group(labelFieldsGroup,SWT.NONE|SWT.VERTICAL);
		buttongroup.setLayoutData(new GridData(40,50));
		buttongroup.setLayout(new GridLayout(1,false));
		//���ҵļ�ͷ
		Button rightbutton = new Button(buttongroup, SWT.RIGHT | SWT.ARROW | SWT.FILL);
		//����ļ�ͷ
		Button leftbutton = new Button(buttongroup, SWT.LEFT | SWT.ARROW);
		
		rightlabelFields = new List(labelFieldsGroup, SWT.BORDER|SWT.MULTI| SWT.V_SCROLL);
		rightlabelFields.setLayoutData(new GridData(120,125));
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
		
		
		
		Label labelDelimsLabel = new Label(grouId, SWT.NONE);
		labelDelimsLabel.setText("�ָ���:");
		labelDelims = new Text(grouId, SWT.BORDER);
		labelDelims.setLayoutData(new GridData(140,15));
		//����Ĭ������
		dealDefaultData();
		setControl(container);
	}
	
	private void dealDefaultData(){
		if(treeobj instanceof TreeElementObj){
			TreeElementObj treeelement = (TreeElementObj)treeobj;
			TreeLevel toplevel = treeelement.getTreeComp().getTopLevel();
			if(toplevel != null && toplevel instanceof RecursiveTreeLevel){
				RecursiveTreeLevel rec = (RecursiveTreeLevel)toplevel;
				id.setText(rec.getId());
				dataset.setText(rec.getDataset());
				masterKeyField.setText(rec.getMasterKeyField());
				recursiveKeyField.setText(rec.getRecursiveKeyField());
				recursivePKeyField.setText(rec.getRecursivePKeyField());
				String field = rec.getLabelFields();
				String[] fields = field.split(",");
				rightlabelFields.setItems(fields);
				labelFields.setSelection(fields);
				String labelDel = rec.getLabelDelims();
				if(labelDel == null)
					labelDel = " ";
				labelDelims.setText(labelDel);
			}
		}else if(treeobj instanceof TreeLevelElementObj){
			TreeLevelElementObj treelevelobj = (TreeLevelElementObj)treeobj;
			LFWBasicElementObj parent = treelevelobj.getParentTreeLevel();
			while(parent != null && parent instanceof TreeLevelElementObj){
				TreeLevelElementObj parenttreelvel = (TreeLevelElementObj)parent;
				treelevelobj = parenttreelvel;
				parent = parenttreelvel.getParentTreeLevel();
			}
			TreeElementObj treeElement = (TreeElementObj)parent;
			TreeLevel topLevel = treeElement.getTreeComp().getTopLevel();
			TreeLevel childLevel = topLevel.getChildTreeLevel();
			while(childLevel != null){
				if(childLevel.getDataset().equals(treelevel.getDs().getId())){
					break;
				}
				else{
					childLevel = childLevel.getChildTreeLevel();
				}
			}
			if(childLevel != null && childLevel instanceof RecursiveTreeLevel){
				RecursiveTreeLevel rec = (RecursiveTreeLevel)childLevel;
				id.setText(rec.getId());
				dataset.setText(rec.getDataset());
				masterKeyField.setText(rec.getMasterKeyField());
				recursiveKeyField.setText(rec.getRecursiveKeyField());
				recursivePKeyField.setText(rec.getRecursivePKeyField());
				detailKeyParameter.setText(rec.getDetailKeyParameter());
				String field = rec.getLabelFields();
				String[] fields = field.split(",");
				rightlabelFields.setItems(fields);
				labelFields.setSelection(fields);
				String labelDel = rec.getLabelDelims();
				if(labelDel == null)
					labelDel = " ";
				labelDelims.setText(labelDel);
			}
		
		}
	}

}
