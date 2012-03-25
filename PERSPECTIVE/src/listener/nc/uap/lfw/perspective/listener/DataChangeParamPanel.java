package nc.uap.lfw.perspective.listener;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.perspective.editor.TableViewContentProvider;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * DatasetChangeEvent�Ĳ���ѡ��Ի�������
 * 
 * @author guoweic
 *
 */
public class DataChangeParamPanel extends Canvas {
	
	private Dataset dataset = null;
	private LfwParameter oldExtendParameter;
	private LfwParameter extendParameter;
	private String selectedFields = "";
	
	private CheckboxTableViewer ctv;
	
	class CheckStateListener implements ICheckStateListener {

		public void checkStateChanged(CheckStateChangedEvent event) {
			refreshSelectedFields();
		}
		
	}
	
	public DataChangeParamPanel(Composite parent, int style, Dataset dataset, LfwParameter extendParameter) {
		super(parent, style);
		this.dataset = dataset;
		this.oldExtendParameter = extendParameter;
		initUI();
	}
	
	private void initUI() {
		this.extendParameter = (LfwParameter) oldExtendParameter.clone();
		// ���岼��
		GridLayout mainLayout = new GridLayout(4, false);
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// ԭ��ѡ������������
		String[] oldFields = oldExtendParameter.getDesc().split(",");
		List<Field> oldFieldList = new ArrayList<Field>();
		// ����������
		Field[] fields = dataset.getFieldSet().getFields();
		List<Field> fieldList = new ArrayList<Field>();
		for (Field field : fields) {
			fieldList.add(field);
		}
		
		ViewForm vf = new ViewForm(this, SWT.NONE);
		vf.setLayout(new GridLayout(1, false));
		vf.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		TableViewer tv = new TableViewer(vf, SWT.CHECK | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		ctv = new CheckboxTableViewer(tv.getTable());
		Table table = tv.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		createColumn(table, layout, 15, SWT.NONE, "ѡ��");
		createColumn(table, layout, 60, SWT.NONE, "��ID");
		createColumn(table, layout, 60, SWT.NONE, "������");
		
		tv.setContentProvider(new TableViewContentProvider());
		tv.setLabelProvider(new DataChangeParamViewProvider());
		
		tv.setInput(fieldList);
		
		for (String fieldId : oldFields) {
			if (null != fields) {
				for (Field field : fields) {
					if (field.getId().equals(fieldId)) {
						oldFieldList.add(field);
					}
				}
			}
			
		}
		ctv.setCheckedElements(oldFieldList.toArray());
		
		ctv.addCheckStateListener(new CheckStateListener());

		vf.setContent(tv.getControl());
		
		this.getData();
		
	}

	public LfwParameter getExtendParameter() {
		extendParameter.setDesc(selectedFields);
		return extendParameter;
	}
	
	/**
	 * ˢ��ѡ�е��в�������
	 * @return
	 */
	private String refreshSelectedFields() {
		selectedFields = "";
		Object[] checkedElementArray = ctv.getCheckedElements();
		for (Object field : checkedElementArray) {
			if (field instanceof Field)
				selectedFields += ((Field)field).getId() + ",";
		}
		if (selectedFields.length() > 0)
			selectedFields = selectedFields.substring(0, selectedFields.length() - 1);
		return selectedFields;
	}
	
	private void createColumn(Table table, TableLayout layout, int width,
			int align, String text) {
		layout.addColumnData(new ColumnWeightData(width));
		new TableColumn(table, align).setText(text);
	}

	public CheckboxTableViewer getCtv() {
		return ctv;
	}

}
