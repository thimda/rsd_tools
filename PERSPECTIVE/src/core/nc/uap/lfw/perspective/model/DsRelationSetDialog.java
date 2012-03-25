package nc.uap.lfw.perspective.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.LFWBasicElementObj;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.ObjectComboCellEditor;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.common.CompIdGenerator;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.FieldRelations;
import nc.uap.lfw.core.data.MatchField;
import nc.uap.lfw.core.data.MdDataset;
import nc.uap.lfw.core.data.WhereField;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.ref.DsFieldContentProvider;
import nc.uap.lfw.perspective.ref.DsFieldRelationContent;
import nc.uap.lfw.perspective.webcomponent.LFWComboTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
/**
 * ����Ds field Relation�ĶԻ���
 * @author zhangxya
 *
 */
public class DsRelationSetDialog extends DialogWithTitle {
	
	private Table tableleft = null;
	private TableViewer tvleft = null;
	private CheckboxTableViewer ctv = null;
	private Text id = null;
	private Table tableright = null;
	private TableViewer tvright = null;
	private Dataset dataset = null;
	private LFWBasicElementObj dsElement = null;
	private LFWBasicElementObj refDsElement = null;
	
	private class ModifyFiedRelation extends Command{
		public ModifyFiedRelation(){
			super("�޸�FieldRelation");
		}
		
		public void execute() {
			redo();
		}
		public void redo() {
			}
		
		public void undo() {
		}
		
	}


	public DsRelationSetDialog(Shell parentShell, String title, LFWBasicElementObj dsElement, LFWBasicElementObj refDsElement) {
		super(parentShell, title);
		this.dsElement = dsElement;
		this.refDsElement = refDsElement;
		// TODO Auto-generated constructor stub
	}
	
	public Dataset getSelectedDataset(){
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	
	protected void okPressed() {
		String fieldRelationid = id.getText();
		if(fieldRelationid == null || fieldRelationid.equals("")){
			MessageDialog.openError(null, "������ʾ", "FieldRelation��ID����Ϊ��!");
			return;
		}
		TableItem[] items = tableleft.getSelection();
		Object[] itemsright = ctv.getCheckedElements();
		if(items != null && items.length > 0 && itemsright.length > 0){
			int size = 0;
			for (int i = 0; i < itemsright.length; i++) {
				DSRelationField rightitem = (DSRelationField)itemsright[i];
				if(rightitem.getIsmatch().equals("Y")){
					size ++;
				}
			}
			if(size != 1){
				MessageDialog.openError(null, "������ʾ", "��������WhereField�ұ���Ψһ!");
				return;
			}
			TableItem item = items[0];
			Field field = (Field)item.getData();
			FieldRelation fieldRelation = new FieldRelation();
			fieldRelation.setId(fieldRelationid);
			if(dsElement instanceof DatasetElementObj && refDsElement instanceof RefDatasetElementObj){
				DatasetElementObj dsElementnew = (DatasetElementObj)dsElement;
				Dataset ds = dsElementnew.getDs();
				if(ds.getFrom() != null){
					MessageDialog.openError(null, "��ʾ", "���Զ�������ݼ�,�����޸�!");
					return;
				}
				RefDatasetElementObj refDsElementnew = (RefDatasetElementObj)refDsElement;
				//todo �����ԭ����fieldRelation
				FieldRelations frralations = dsElementnew.getDs().getFieldRelations();
				FieldRelation[] dsFieldRelations = frralations.getFieldRelations();
				if(dsFieldRelations != null){
					for (int i = 0; i < dsFieldRelations.length; i++) {
						if(dsFieldRelations[i].getRefDataset().equals(refDsElementnew.getDs().getId())){
							//ɾ��FieldRelationͼ��
							try{
								dsElementnew.deleteFieldRelation(dsFieldRelations[i]);
								refDsElementnew.deleteFieldRelation(dsFieldRelations[i]);
								break;
							}
							catch(Exception e){
								MainPlugin.getDefault().logError(e);
							}
						}
					}
					dsElementnew.getDs().setFieldRelations(frralations);
				} 
				//�����ԭ����fieldRelation
				refDsElementnew.setDsobj(dsElementnew);
				fieldRelation.setRefDataset(refDsElementnew.getDs().getId());
				List<MatchField> matchFieldList = new ArrayList<MatchField>();
				//WhereField> whereField = new ArrayList<WhereField>();
				
				boolean match = false;
				for (int i = 0; i < itemsright.length; i++) {
					DSRelationField rightitem = (DSRelationField)itemsright[i];
					if(rightitem.getIscontains().equals("Y") && rightitem.getIsmatch().equals("Y")){
						MatchField matchField = new MatchField();
						matchField.setIscontains("Y");
						matchField.setIsmacth("Y");
						matchField.setReadField(rightitem.getId());
						matchField.setWriteField(field.getId());
						matchFieldList.add(matchField);
					}
					//ѡ�в��Ҳ���matchfield
					if(rightitem.getIscontains().equals("Y") && rightitem.getIsmatch().equals("N")){
						if(rightitem.getMatchfield() != null && !rightitem.getMatchfield().equals("")){
							MatchField matchField = new MatchField();
							matchField.setReadField(rightitem.getId());
							matchField.setWriteField(rightitem.getMatchfield());
							matchFieldList.add(matchField);
						}
						else{
							Field copyField = dsElementnew.getDs().getFieldSet().getField(field.getId() + "_" + rightitem.getId());
							if(copyField == null){
								copyField = new Field();
								copyField.setId(field.getId() + "_" + rightitem.getId());
								copyField.setField(null);
								copyField.setDefaultValue(null);
								copyField.setText(rightitem.getText());
								copyField.setSourceField(field.getId());
								copyField.setI18nName(rightitem.getI18nName());
								copyField.setLangDir(rightitem.getLangDir());
								copyField.setDataType(rightitem.getDataType());
								//��ds������ֶ�
								dsElementnew.getDs().getFieldSet().addField(copyField);
							}
							MatchField matchField = new MatchField();
							matchField.setReadField(rightitem.getId());
							matchField.setWriteField(copyField.getId());
							matchFieldList.add(matchField);
						}
					}
					if(rightitem.getIsmatch().equals("Y")){
						match = true;
						WhereField whereField = new WhereField();
						whereField.setKey(rightitem.getId());
						whereField.setValue(field.getId());
						//whereFieldList.add(whereField);
						fieldRelation.setWhereField(whereField);
					}
				}
				if(!match){
					MessageDialog.openError(Display.getCurrent().getActiveShell(), "����", "���������б���ѡ�������ѡ�������������");
					return;
				}
				MatchField[] matchFieldss = matchFieldList.toArray(new MatchField[matchFieldList.size()]);
				//WhereField[] whereFieldss = whereFieldList.toArray(new WhereField[whereFieldList.size()]);
				fieldRelation.setMatchFields(matchFieldss);
				//fieldRelation.setWhereFields(whereFieldss);
				//���FieldRelationͼ��
				dsElementnew.addFieldRelation(fieldRelation);
				refDsElementnew.addFieldRelation(fieldRelation);
					//Listener�ı�λ��
					
				
	//				break;
//					DatasetElementFigure figure = new DatasetElementFigure(dsElementnew);
//					ListenerElementObj listener = dsElementnew.getGraph().getJsListeners().get(0);
//					int pointX = figure.getBounds().x;
//					int pointY = figure.getBounds().height + figure.getBounds().y + 100;
//					listener.setLocation(new org.eclipse.draw2d.geometry.Point(pointX, pointY));
					
					
					if(dsElementnew.getDs().getFieldRelations() != null){
						dsElementnew.getDs().getFieldRelations().addFieldRelation(fieldRelation);
					}
					else{
						FieldRelations fieldRelations = new FieldRelations();
						fieldRelations.addFieldRelation(fieldRelation);
						dsElementnew.getDs().setFieldRelations(fieldRelations);
					}
					//Listener�ı�λ��
					DataSetEditor dsEditor = DataSetEditor.getActiveEditor();
					dsEditor.repaintListenerPositon();
					//ͨ������ds����������
					generatorComboData(dsElementnew.getDs(), refDsElementnew.getDs(), matchFieldss);
				}
			else if(dsElement instanceof RefDatasetElementObj && refDsElement instanceof RefDatasetElementObj){
				RefDatasetElementObj dsElementnew = (RefDatasetElementObj)dsElement;
				Dataset ds = dsElementnew.getDs();
				if(ds.getFrom() != null){
					MessageDialog.openError(null, "��ʾ", "���Զ�������ݼ�,�����޸�!");
					return;
				}
				RefDatasetElementObj refDsElementnew = (RefDatasetElementObj)refDsElement;
				DatasetElementObj dsobj = dsElementnew.getDsobj();
				FieldRelation childfr = new FieldRelation();
				childfr.setId(fieldRelationid);
				childfr.setRefDatasetid(refDsElementnew.getDs().getId());
				//�ϼ���Fieldrelation
				FieldRelation parentfr = null;
				//**************���ԭ����fieldrelation
				RefDatasetElementObj sourcenew = dsElementnew;
				RefDatasetElementObj targetnew = refDsElementnew;
				if(sourcenew != null){
					RefDatasetElementObj refpp = sourcenew.getParent();
					while(refpp != null){
						sourcenew = refpp;
						refpp = refpp.getParent();
					}
				}
				
				DatasetElementObj parentds = sourcenew.getDsobj();
				FieldRelations frs = parentds.getDs().getFieldRelations();
				if(frs != null){
					FieldRelation[] frss = frs.getFieldRelations();
					for (int i = 0; i < frss.length; i++) {
						FieldRelation fr = frss[i];
						parentfr = dealChildRelation(fr, targetnew);
					}
				}
				parentds.getDs().setFieldRelations(frs);
				//******************
//				FieldRelation[] fieldRelations = dsobj.getDs().getFieldRelations().getFieldRelations();
//				
//				for (int i = 0; i < fieldRelations.length; i++) {
//					FieldRelation fr = fieldRelations[i];
//					if(fr.getRefDatasetid().equals(dsElementnew.getDs().getId())){
//						parentfr = fr;
//						break;
//					}else {
//						fr.getChildRelationList();
//					}
//				}
				List<MatchField> matchFieldList = new ArrayList<MatchField>();
				//List<WhereField> whereFieldList = new ArrayList<WhereField>();
				for (int i = 0; i < itemsright.length; i++) {
					DSRelationField rightitem = (DSRelationField)itemsright[i];
//					if(rightitem.getIscontains().equals("Y") && rightitem.getIsmatch().equals("Y")){
//						MatchField matchField = new MatchField();
//						matchField.setIsmacth("Y");
//						matchField.setReadField(rightitem.getId());
//						matchField.setWriteField(field.getId());
//						matchField.setIscontains("Y");
//					}
//					if(rightitem.getIscontains().equals("N") && rightitem.getIsmatch().equals("Y")){
//						MatchField matchField = new MatchField();
//						matchField.setIsmacth("Y");
//						//matchField.setReadField(rightitem.getId());
//						//matchField.setWriteField(field.getId());
//						matchField.setIscontains("N");
//					}
					//ѡ�в��Ҳ���matchfield
					if(rightitem.getIscontains().equals("Y") && rightitem.getIsmatch().equals("N")){
						if(rightitem.getMatchfield() != null && !rightitem.getMatchfield().equals("")){
							MatchField matchField = new MatchField();
							matchField.setReadField(rightitem.getId());
							matchField.setWriteField(rightitem.getMatchfield());
							matchFieldList.add(matchField);
						}
						else{
							Field copyField = new Field();
							copyField.setId(dsobj.getDs().getId() + "_" + field.getId() +  "_" + rightitem.getId());
							copyField.setField(dsobj.getDs().getId() + "_" + field.getId() + "_" + rightitem.getId());
							//��ds������ֶ�
							parentds.getDs().getFieldSet().addField(copyField);
							MatchField matchField = new MatchField();
							matchField.setReadField(rightitem.getId());
							matchField.setWriteField(copyField.getId());
							matchFieldList.add(matchField);
						}
					}
					if(rightitem.getIsmatch().equals("Y")){
						WhereField whereField = new WhereField();
						whereField.setKey(rightitem.getId());
						whereField.setValue(field.getId());
						childfr.setWhereField(whereField);
					}
				}
				MatchField[] matchFieldss = matchFieldList.toArray(new MatchField[matchFieldList.size()]);
			//	WhereField[] whereFieldss = whereFieldList.toArray(new WhereField[whereFieldList.size()]);
				childfr.setMatchFields(matchFieldss);
			//	childfr.setWhereFields(whereFieldss);
				parentfr.addChildRelation(childfr);
			}
			super.okPressed();
			//ʹ���水ť����
			if(DataSetEditor.getActiveEditor() != null){
				ModifyFiedRelation cmd = new ModifyFiedRelation();
				DataSetEditor.getActiveEditor().executComand(cmd);
			}
		}else{
			MessageDialog.openConfirm(this.getShell(), "��ʾ", "��ѡ������FieldRelation���ֶ�");
			tableleft.setFocus();
			//tableleft.clearAll();
			//tableright.clearAll();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void generatorComboData(Dataset mainDs, Dataset refDs, MatchField[] matchFields){
		if(refDs instanceof MdDataset){
			LfwWidget widget = LFWPersTool.getCurrentWidget();
			MdDataset mdds = (MdDataset) refDs;
			List<ComboData> combodataList = LFWConnector.getAllNcComboData(mdds);
			List<ComboData> generateCombo = new ArrayList<ComboData>();
			for (int i = 0; i < matchFields.length; i++) {
				MatchField matchField = matchFields[i];
				String readField = matchField.getReadField();
				String writeField = matchField.getWriteField();
				String compId = CompIdGenerator.generateComboCompId(mdds.getId(), readField);
				for (int j = 0; j < combodataList.size(); j++) {
					ComboData combo = combodataList.get(j);
					if(combo.getId().equals(compId)){
						String newcomboId = CompIdGenerator.generateComboCompId(mainDs.getId(), writeField);
						combo.setId(newcomboId);
						generateCombo.add(combo);
						widget.getViewModels().addComboData(combo);
					}
				}
			}
		addExtendedCombdatas(generateCombo.toArray(new ComboData[0]));
		 
		}
	}
	
	public void addExtendedCombdatas(ComboData[] comboDatas){
		//�����������ݼ�
		LFWSeparateTreeItem lfwSeparaTreeItem = null;
		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
		TreeItem[] separasTreeItems = widgetTreeItem.getItems();
		for (int i = 0; i < separasTreeItems.length; i++) {
			TreeItem item = separasTreeItems[i];
			if(item instanceof LFWSeparateTreeItem){
				LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
				if(seitem.getText().equals("�������ݼ�")){
					lfwSeparaTreeItem = seitem;
					break;
				}
			}
		}
		if(lfwSeparaTreeItem != null){
			TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < treeItems.length; i++) {
				ComboData combo = (ComboData) treeItems[i].getData();
				list.add(combo.getId());
			}
			for (int i = 0; i < comboDatas.length; i++) {
				if(!list.contains(comboDatas[i].getId())){
					ComboData comboData  = comboDatas[i];
					new LFWComboTreeItem(lfwSeparaTreeItem, comboData, "[��������]");
				}
			}
		}		
	}
	
	private FieldRelation dealChildRelation(FieldRelation fr,RefDatasetElementObj target){
		List<FieldRelation> childrelaiton = fr.getChildRelationList();
		if(childrelaiton != null){
			for (int i = 0; i < childrelaiton.size(); i++) {
				if(childrelaiton.get(i).getRefDataset().equals(target.getDs().getId())){
					fr.getChildRelationList().remove(childrelaiton.get(i));
					return fr;
				}
				else
					return dealChildRelation(childrelaiton.get(i), target);
			}
			return fr;
		}
		else
			return fr;
	}
	
	protected Point getInitialSize() {
		return new Point(900,500); 
	}
	
	protected Control createDialogArea(Composite parent) {
		//parent.setLayout(new FillLayout(SWT.VERTICAL|SWT.Activate));
		Composite container = new Composite(parent , SWT.NONE);
		container.setLayout(new GridLayout(1,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group grouId = new Group(container, SWT.NONE);
		grouId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grouId.setLayout(new GridLayout(2,false));
		Label labelId = new Label(grouId, SWT.NONE);
		labelId.setText("�ֶι�����ϵ ID:");
		id = new Text(grouId, SWT.NONE);
		//id.setText(this.dsElement..getId() + "_" + this.refDsElement.getId() + "_rel");
		
		id.setLayoutData(new GridData(100,15));
		
		Group grouptree = new Group(container, SWT.NONE);
		grouptree.setLayoutData(new GridData(GridData.FILL_BOTH));
		grouptree.setLayout(new GridLayout(2,false));
		//��ߵ�table
		tvleft = new TableViewer(grouptree, SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		tableleft = tvleft.getTable();
		tableleft.setLinesVisible(true);
		tableleft.setHeaderVisible(true);
		TableLayout layoutleft = new TableLayout();
		tableleft.setLayout(layoutleft);
		//ID�ֶ�
		layoutleft.addColumnData(new ColumnWeightData(150));
		new TableColumn(tableleft,SWT.NONE).setText("ID");
		layoutleft.addColumnData(new ColumnWeightData(150));
		//Field�ֶ�
		new TableColumn(tableleft, SWT.NONE).setText("Field");
		tableleft.setLayoutData(new GridData(GridData.FILL_BOTH));
		tvleft.setContentProvider(new DsFieldRelationContent());
		tvleft.setLabelProvider(new DsFieldRelationContent());
		//�ұߵ�table
		tvright = new TableViewer(grouptree, SWT.CHECK|SWT.BORDER|SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		ctv = new CheckboxTableViewer(tvright.getTable());
		tableright = tvright.getTable();
		tableright.setLinesVisible(true);
		tableright.setHeaderVisible(true);
		TableLayout layoutright = new TableLayout();
		tableright.setLayout(layoutright);
		//ID�ֶ�
		layoutright.addColumnData(new ColumnWeightData(150));
		new TableColumn(tableright,SWT.NONE).setText("ID");
		layoutright.addColumnData(new ColumnWeightData(100));
		//Field�ֶ�
		new TableColumn(tableright, SWT.NONE).setText("�ֶ�");
		layoutright.addColumnData(new ColumnWeightData(100));
		new TableColumn(tableright,SWT.NONE).setText("�Ƿ�����ֶ�");
		layoutright.addColumnData(new ColumnWeightData(150));
		new TableColumn(tableright,SWT.NONE).setText("�Ƿ���ʾ");
		//ѡ��ƥ���ֶ�
		layoutright.addColumnData(new ColumnWeightData(150));
		new TableColumn(tableright, SWT.NONE).setText("ƥ���ֶ�");
		tableright.setLayoutData(new GridData(GridData.FILL_BOTH));
		tvright.setContentProvider(new DsFieldContentProvider());
		tvright.setLabelProvider(new DsFieldContentProvider());
		List<Field> leftinput = new ArrayList<Field>();
		Field[] leftfields = null;
		if(dsElement instanceof DatasetElementObj){
			DatasetElementObj dsElementnew = (DatasetElementObj)dsElement;
			leftfields = dsElementnew.getDs().getFieldSet().getFields();
			if(leftfields != null && leftfields.length > 0){
				leftinput = Arrays.asList(leftfields);
				tvleft.setInput(leftinput);
			}
		}else if(dsElement instanceof RefDatasetElementObj){
			RefDatasetElementObj dsElementnew = (RefDatasetElementObj)dsElement;
			if(dsElementnew != null){
				RefDatasetElementObj refpp = dsElementnew.getParent();
				while(refpp != null){
					dsElementnew = refpp;
					refpp = refpp.getParent();
				}
			}
			
			DatasetElementObj parentds = dsElementnew.getDsobj();
			leftfields = parentds.getDs().getFieldSet().getFields();
			FieldRelation[] frs = parentds.getDs().getFieldRelations().getFieldRelations();
			FieldRelation parentfr = null;
			for (int i = 0; i < frs.length; i++) {
				if(frs[i].getRefDatasetid().equals(dsElementnew.getDs().getId())){
					parentfr = frs[i];
					break;
				}
			}
			List<Field> refleftfileds = new ArrayList<Field>();
			MatchField[] matchfields = parentfr.getMatchFields();
			Field[] allfields = dsElementnew.getDs().getFieldSet().getFields();
			for (int i = 0; i < matchfields.length; i++) {
				String readfield = matchfields[i].getReadField();
				if(readfield != null){
					for (int j = 0; j < allfields.length; j++) {
						if(readfield.equals(allfields[j].getId())){
							refleftfileds.add(allfields[j]);
							break;
						}
					}
				}
			}
			tvleft.setInput(refleftfileds);
		}
		List<DSRelationField> rightinput = new ArrayList<DSRelationField>();
		if(refDsElement instanceof RefDatasetElementObj){
			RefDatasetElementObj refDsElementnew = (RefDatasetElementObj)refDsElement;
			Field[] originalfs = refDsElementnew.getDs().getFieldSet().getFields();
			if(originalfs != null){
				for (int i = 0; i < originalfs.length; i++) {
					DSRelationField dsrf = new DSRelationField();
					dsrf.setId(originalfs[i].getId());
					dsrf.setField(originalfs[i].getField());
					dsrf.setDataType(originalfs[i].getDataType());
					dsrf.setI18nName(originalfs[i].getI18nName());
					dsrf.setLangDir(originalfs[i].getLangDir());
					dsrf.setText(originalfs[i].getText());
					dsrf.setIscontains("Y");
					dsrf.setIsmatch("N");
					rightinput.add(dsrf);
				}
			}
		
		}
		tvright.setInput(rightinput);
		String[] names = {"Y","N"};
		CellEditor[] cellEditors = new CellEditor[5];
		cellEditors[0] = new TextCellEditor(tableright);;
		cellEditors[1] = new TextCellEditor(tableright);
		cellEditors[2] = new ComboBoxCellEditor(tableright, names, SWT.NONE);
		cellEditors[3] = new ComboBoxCellEditor(tableright, names, SWT.NONE);
		
		List<String> matchFields = new ArrayList<String>();
		if(leftfields != null){
			for (int i = 0; i < leftfields.length; i++) {
				matchFields.add(leftfields[i].getId());
			}
		}
		cellEditors[4] = new  ObjectComboCellEditor(tableright, matchFields.toArray(new String[0]), SWT.NONE);
		
		tvright.setCellEditors(cellEditors);
		tvright.setCellModifier(new DsRelationCellModifier(tvright));
		tvright.setColumnProperties(new String[]{"ID", "Field","ismatch","isinput","matchField"});
		getSelectedData(dsElement, refDsElement);
		return container;
	}
	
	//���fieldrelationѡ������
	private void getSelectedData(LFWBasicElementObj source, LFWBasicElementObj target){
		if(source instanceof DatasetElementObj){
			DatasetElementObj sourcenew = (DatasetElementObj)source;
			RefDatasetElementObj targetnew = (RefDatasetElementObj)target;
			Dataset sourceds = sourcenew.getDs();
			if(sourceds.getFieldRelations() != null){
				FieldRelation[] fieldRelations = sourceds.getFieldRelations().getFieldRelations();
				if(fieldRelations != null){
					for (int i = 0; i < fieldRelations.length; i++) {
						FieldRelation fieldrelation = fieldRelations[i];
						if(fieldrelation.getRefDataset().equals(targetnew.getDs().getId())){
							String targetNewFieldRelId = targetnew.getRefFieldRelation().getId();
							if(fieldrelation.getId().equals(targetNewFieldRelId)){
								id.setText(fieldrelation.getId());
								MatchField[]  matchFields = fieldrelation.getMatchFields();
								TableItem [] righttableItems = tvright.getTable().getItems();
								List<DSRelationField> readFields = new ArrayList<DSRelationField>();
								for (int j = 0; j < matchFields.length; j++) {
									String match = matchFields[j].getIscontains();
									String iscontains = matchFields[j].getIscontains();
									String readField = matchFields[j].getReadField();
									String writeField = matchFields[j].getWriteField();
									for (int k = 0; k < righttableItems.length; k++) {
										if(((DSRelationField)righttableItems[k].getData()).getId().equals(readField)){
											DSRelationField dsFieldRelation = (DSRelationField) righttableItems[k].getData();
											if(match != null)
												dsFieldRelation.setIsmatch(match);
											if(iscontains != null)
												dsFieldRelation.setIscontains(iscontains);
											dsFieldRelation.setMatchfield(writeField);
											readFields.add(dsFieldRelation);
											tvright.update(dsFieldRelation, null);
											break;
										}
									}
									
								}
								
								
								WhereField whereField = fieldrelation.getWhereField();
								String fieldKey = whereField.getKey();
									for (int k = 0; k < righttableItems.length; k++) {
										if(((DSRelationField)righttableItems[k].getData()).getId().equals(fieldKey)){
											DSRelationField dsFieldRelation = (DSRelationField) righttableItems[k].getData();
											dsFieldRelation.setIsmatch("Y");
											dsFieldRelation.setIscontains("N");
											readFields.add(dsFieldRelation);
											tvright.update(dsFieldRelation, null);
											break;
											}
										}
								//	}
								
								//ѡ���ұ���
								ctv.setCheckedElements(readFields.toArray());
								//���ѡ����
								//for (int j = 0; j < whereFields.length; j++) {
									//WhereField whereField = whereFields[j];
									String fieldValue = whereField.getValue();
									TableItem [] lefttableItems = tvleft.getTable().getItems();
									for (int k = 0; k < lefttableItems.length; k++) {
										if(((Field)lefttableItems[k].getData()).getId().equals(fieldValue)){
											tableleft.setSelection(lefttableItems[k]);
											break;
										}
									}
								//}
	//							for (int j = 0; j < matchFields.length; j++) {
	//								String writeField = matchFields[j].getWriteField();
	//								if(!writeField.endsWith("_mc")){
	//									TableItem [] lefttableItems = tvleft.getTable().getItems();
	//									for (int k = 0; k < lefttableItems.length; k++) {
	//										if(((Field)lefttableItems[k].getData()).getId().equals(writeField)){
	//											tableleft.setSelection(lefttableItems[k]);
	//											break;
	//										}
	//									}
	//								}
	//							
	//							}
								
							}
						}
					}
				}
			}
			
		}else {
			RefDatasetElementObj sourcenew = (RefDatasetElementObj)source;
			RefDatasetElementObj targetnew = (RefDatasetElementObj)target;
			if(sourcenew != null){
				RefDatasetElementObj refpp = sourcenew.getParent();
				while(refpp != null){
					sourcenew = refpp;
					refpp = refpp.getParent();
				}
			}
			
			DatasetElementObj parentds = sourcenew.getDsobj();
			//�õ���fieldrelation
			FieldRelation childRelation = getChildRelation(parentds, targetnew);
			if(childRelation != null){
				//���ݵõ�����fieldrelation�����ݿ���д���
				id.setText(childRelation.getId());
				MatchField[]  matchFields = childRelation.getMatchFields();
				TableItem [] righttableItems = tvright.getTable().getItems();
				List<DSRelationField> readFields = new ArrayList<DSRelationField>();
				for (int j = 0; j < matchFields.length; j++) {
					String match = matchFields[j].getIscontains();
					String iscontains = matchFields[j].getIscontains();
					String readField = matchFields[j].getReadField();
					String writeField = matchFields[j].getWriteField();
					for (int k = 0; k < righttableItems.length; k++) {
						if(((DSRelationField)righttableItems[k].getData()).getField().equals(readField)){
							DSRelationField dsFieldRelation = (DSRelationField) righttableItems[k].getData();
							if(match != null)
								dsFieldRelation.setIsmatch(match);
							if(iscontains != null)
								dsFieldRelation.setIscontains(iscontains);
							dsFieldRelation.setMatchfield(writeField);
							readFields.add(dsFieldRelation);
							tvright.update(dsFieldRelation, null);
							break;
						}
					}
				}
				
				
				WhereField whereField = childRelation.getWhereField();
				
				//for (int j = 0; j < whereFields.length; j++) {
					//WhereField whereField = whereFields[j];
					String fieldValue = whereField.getValue();
					for (int k = 0; k < righttableItems.length; k++) {
						if(((DSRelationField)righttableItems[k].getData()).getId().equals(fieldValue)){
							DSRelationField dsFieldRelation = (DSRelationField) righttableItems[k].getData();
							dsFieldRelation.setIsmatch("Y");
							dsFieldRelation.setIscontains("N");
							readFields.add(dsFieldRelation);
							tvright.update(dsFieldRelation, null);
							break;
							}
						}
				//	}
				
				//ѡ���ұ���
				ctv.setCheckedElements(readFields.toArray());
				//���ѡ����
			//	for (int j = 0; j < whereFields.length; j++) {
			//		WhereField whereField = whereFields[j];
			//		String fieldValue = whereField.getValue();
					TableItem [] lefttableItems = tvleft.getTable().getItems();
					for (int k = 0; k < lefttableItems.length; k++) {
						if(((Field)lefttableItems[k].getData()).getId().equals(fieldValue)){
							tableleft.setSelection(lefttableItems[k]);
							break;
						}
					}
			//	}
				
//				ctv.setCheckedElements(readFields.toArray());
//				for (int j = 0; j < matchFields.length; j++) {
//					String writeField = matchFields[j].getWriteField();
//					if(!writeField.endsWith("_mc")){
//						TableItem [] lefttableItems = tvleft.getTable().getItems();
//						for (int k = 0; k < lefttableItems.length; k++) {
//							if(((Field)lefttableItems[k].getData()).getField().equals(writeField)){
//								tableleft.setSelection(lefttableItems[k]);
//								break;
//							}
//						}
//					}
//				}
			}
			
		}
	}
	
	private FieldRelation getChildRelation(DatasetElementObj source, RefDatasetElementObj target){
		FieldRelations frs = source.getDs().getFieldRelations();
		if(frs != null){
			FieldRelation[] frrs = frs.getFieldRelations();
			for (int i = 0; i < frrs.length; i++) {
				return dealFieldRelation(frrs[i], target);
			}
		}
		return null;
	}
	
	private FieldRelation dealFieldRelation(FieldRelation fieldRelation, RefDatasetElementObj target){
		 List<FieldRelation> childrflist = fieldRelation.getChildRelationList();
		 if(childrflist != null){
			 for (int j = 0; j < childrflist.size(); j++) {
				 if(childrflist.get(j).getRefDataset().equals(target.getDs().getId())){
					return childrflist.get(j);
				 }
				 else{
					 dealFieldRelation(childrflist.get(j), target);
					}
				 }
		 }
			return null;
		}
	}
