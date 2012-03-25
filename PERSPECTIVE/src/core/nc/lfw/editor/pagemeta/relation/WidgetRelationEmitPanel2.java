package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.PlugoutEmitItem;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Widget������ ����ʱ�������� �ڶ�ҳ����
 * @author guoweic
 *
 */
public class WidgetRelationEmitPanel2 extends Canvas {

	/**
	 * ��ջ����
	 */
	private StackLayout stackLayout = new StackLayout();
	
	/**
	 * ֵѡ������
	 */
	private Composite valueContainer;
	
	private Composite valueContainer1;
	private Composite valueContainer2;
	
	/**
	 * ��ǰ�������
	 */
	private WebElement currentComp;
	
	/**
	 * ����ѡ��������
	 */
	private Combo typeCombo;
	
	/**
	 * DS��Fieldѡ����
	 */
	private Table fieldTable;
	
	/**
	 * ����ʱ������
	 */
	private String type = "";
	
	/**
	 * ����ʱ��ֵ
	 */
	private String value = "";

	public WidgetRelationEmitPanel2(Composite parent, int style) {
		super(parent, style);
		initUI();
	}

	private void initUI() {
		// ���岼��
		GridLayout mainLayout = new GridLayout();
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// ����ѡ������
		Composite typeArea = new Composite(this, SWT.NONE);
		GridLayout idAreaLayout = new GridLayout(4, false);
		typeArea.setLayout(idAreaLayout);
		typeArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(typeArea, SWT.NONE).setText("����:");
		typeCombo = initTypeCombo(typeArea);
		
		// ֵ��������
		valueContainer = new Composite(this, SWT.NONE);
		valueContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		valueContainer.setLayout(stackLayout);
		
		// ��ʼ��ÿҳ
		valueContainer1 = initValueContainer1(valueContainer);
		valueContainer2 = initValueContainer2(valueContainer);
		
		// Ĭ����ʾ��һҳ
		stackLayout.topControl = valueContainer1;
		valueContainer.layout();
		
		
		// ���Ӳ�������ѡ���¼�
		typeCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = typeCombo.getText();
				String data = (String) typeCombo.getData(text);
				//TODO ��ҳ
//				if (PlugoutEmitItem.TYPE_DS_ROW_SELECT.equals(data)) {
//					stackLayout.topControl = valueContainer2;
//				} else {
//					stackLayout.topControl = valueContainer1;
//				}
//				valueContainer.layout();
				type = data;
			}
		});
		
		
	}
	
	/**
	 * ��ʼ������ѡ��������
	 * @param parent
	 */
	private Combo initTypeCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setLayoutData(createGridData(200, 3));
		combo.setBounds(16, 0, 200, 25);
		return combo;
		
	}
	
	/**
	 * ���¼�������ѡ������ѡ��
	 */
	public void reloadTypeComboData() {
		typeCombo.removeAll();
		if (currentComp instanceof Dataset) {
			typeCombo.add(TypeConstant.getWidgetRelationEmitType().get(PlugoutEmitItem.TYPE_DS_ROW_SELECT));
			typeCombo.setData(TypeConstant.getWidgetRelationEmitType().get(PlugoutEmitItem.TYPE_DS_ROW_SELECT), PlugoutEmitItem.TYPE_DS_ROW_SELECT);
			
			typeCombo.select(0);
			
			type = PlugoutEmitItem.TYPE_DS_ROW_SELECT;
		} else {  //TODO
			type = "";
		}
	}
	
	/**
	 * ���¼���DS����ѡ����
	public void reloadFieldTable() {
		if (currentComp instanceof Dataset) {
			Field[] fieldArray = ((Dataset)currentComp).getFieldSet().getFields();
			for (Field field : fieldArray) {
				// ��������
				String[] str = new String[1];
				str[0] = field.getId();
				TableItem item = new TableItem(fieldTable, SWT.NONE);
				item.setText(str);
			}
			if (fieldArray.length > 0) {
				fieldTable.setSelection(0);
				value = fieldTable.getItem(fieldTable.getSelectionIndex()).getText(0);
			}
		}
	}
	 */
	
	/**
	 * ��ʼ����һҳ
	 * @param parent
	 * @return
	 */
	private Composite initValueContainer1(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		
		
		return container;
	}
	
	/**
	 * ��ʼ���ڶ�ҳ
	 * @param parent
	 * @return
	 */
	private Composite initValueContainer2(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		
//		new Label(container, SWT.NONE).setText("ѡ����:");
//		
//		// ���ݼ�����Ϣ���
//		fieldTable = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
//		
//		TableLayout tableLayout = new TableLayout();
//		fieldTable.setLayout(tableLayout);
//		fieldTable.setLayoutData(new GridData(GridData.FILL_BOTH));
//		fieldTable.setHeaderVisible(true);
//		fieldTable.setLinesVisible(true);
//		createColumn(fieldTable, tableLayout, 200, SWT.NONE, "����");

		return container;
	}
	
	private void createColumn(Table table, TableLayout layout, int width,
			int align, String text) {
		layout.addColumnData(new ColumnWeightData(width));
		new TableColumn(table, align).setText(text);
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public WebElement getCurrentComp() {
		return currentComp;
	}

	public void setCurrentComp(WebElement currentComp) {
		this.currentComp = currentComp;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public Table getFieldTable() {
		return fieldTable;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Combo getTypeCombo() {
		return typeCombo;
	}

}
