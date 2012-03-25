package nc.lfw.editor.pagemeta.relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PluginDescItem;
import nc.uap.lfw.perspective.editor.TableViewContentProvider;
import nc.uap.lfw.perspective.editor.TableViewLabelProvider;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
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
import org.eclipse.swt.widgets.TableItem;

/**
 * Widget������ ִ�����ݹ����� �ڶ�ҳ����
 * @author guoweic
 *
 */
public class WidgetRelationActionPanel2 extends Canvas {
	
	/**
	 * �������ñ��
	 */
	private Table paramTable;
	
	private TableViewer tv;
	
	private CheckboxTableViewer ctv;
	
	private List<String[]> paramList;
	
	/**
	 * ֵѡ������
	 */
	private Composite valueContainer;
	
	/**
	 * ��ǰ�������
	 */
	private WebElement currentComp;
	
	/**
	 * ����ѡ��������
	 */
	private Combo typeCombo;
	
	/**
	 * ִ����������
	 */
	private String type = "";
	
	/**
	 * ִ������ֵ
	 */
	private String value = "";
	
	/**
	 * ��������ӳ��
	 */
	private Map<String, String> paramNameMap = new HashMap<String, String>();

	public WidgetRelationActionPanel2(Composite parent, int style) {
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
		valueContainer.setLayout(new GridLayout(4, false));

		new Label(valueContainer, SWT.NONE).setText("����:");
		
		// ��ʼ���������
		initParamTable(valueContainer);
		
		// ����ִ����������ѡ���¼�
		typeCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = typeCombo.getText();
				String data = (String) typeCombo.getData(text);
				//TODO 
				
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
		if (currentComp instanceof LfwWidget) {
			typeCombo.add("ҳ���ض���");
			typeCombo.setData("ҳ���ض���", PluginDescItem.TYPE_IFRAME_REDIRECT);
			
			typeCombo.select(0);
			
			type = PluginDescItem.TYPE_IFRAME_REDIRECT;
		} else if (currentComp instanceof Dataset) {
			typeCombo.add("���ݼ���");
			typeCombo.setData("���ݼ���", PluginDescItem.TYPE_DS_LOAD);
			
			typeCombo.select(0);
			
			type = PluginDescItem.TYPE_DS_LOAD;
		} else {  //TODO
			type = "";
		}
	}
	
	/**
	 * ���¼��ز����������
	 */
	public void reloadParamTableData(Map<String, LfwParameter> parameterMap) {
		tv.setContentProvider(new TableViewContentProvider());
		tv.setLabelProvider(new ParamTableViewProvider());
		
//		paramTable.removeAll();
		
		paramList = new ArrayList<String[]>();
		
		for (String key : parameterMap.keySet()) {
			LfwParameter PluginParameter = parameterMap.get(key);
//			paramNameMap.put(PluginParameter.getName(), "");
			
			// ���ر������
			String[] str = new String[3];
			str[0] = "";
			str[1] = PluginParameter.getName();
			str[2] = "";
			
			paramList.add(str);
			
//			TableItem item = new TableItem(paramTable, SWT.NONE);
//			item.setText(str);
			
		}
		
		tv.setInput(paramList);
		
		
		tv.setColumnProperties(new String[]{"sel", "PluginParam", "actionParam"});
		// ���ñ༭��
		CellEditor[] cellEditor = new CellEditor[3];
		cellEditor[0] = null;
		cellEditor[1] = null;
		cellEditor[2] = new TextCellEditor(tv.getTable());
		
		tv.setCellEditors(cellEditor);
		tv.setCellModifier(new MyCellModifier(tv));
		
	}
	
	/**
	 * ��ʼ���������
	 * @param parent
	 * @return
	 */
	private void initParamTable(Composite parent) {
		
		// �������ñ��
		ViewForm vf = new ViewForm(parent, SWT.NONE);
		vf.setLayout(new GridLayout());
		vf.setLayoutData(new GridData(GridData.FILL_BOTH));
		tv = new TableViewer(vf, SWT.CHECK | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		ctv = new CheckboxTableViewer(tv.getTable());
		paramTable = tv.getTable();
		
		TableLayout tableLayout = new TableLayout();
		paramTable.setLayout(tableLayout);
		paramTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		paramTable.setHeaderVisible(true);
		paramTable.setLinesVisible(true);
		createColumn(paramTable, tableLayout, 30, SWT.NONE, "ѡ��");
		createColumn(paramTable, tableLayout, 150, SWT.NONE, "Plugin����");
		createColumn(paramTable, tableLayout, 150, SWT.NONE, "ʹ�ò���");
		
		vf.setContent(tv.getControl());
		
	}
	
	public Map<String, String> getParamNameMap() {
		Object[] checkedParamArray = ctv.getCheckedElements();
		for (Object item : checkedParamArray) {
			if (item instanceof String[]) {
				paramNameMap.put(((String[]) item)[1], null == ((String[]) item)[2] || "".equals(((String[]) item)[2]) ? ((String[]) item)[1] : ((String[]) item)[2]);
			}
		}
		return paramNameMap;
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

	public void setValue(String value) {
		this.value = value;
	}

	public Combo getTypeCombo() {
		return typeCombo;
	}

	/**
	 * �б༭��
	 * @author guoweic
	 *
	 */
	private class MyCellModifier implements ICellModifier {

		private TableViewer tv;
		
		public MyCellModifier(TableViewer tv) {
			this.tv = tv;
		}
		
		public boolean canModify(Object element, String property) {
			return true;
		}

		/**
		 * ��ȡ��ʾ����
		 */
		public Object getValue(Object element, String property) {
			String[] param = (String[]) element;
			return param[2];
		}

		/**
		 * �޸ĺ�ִ�з���
		 */
		public void modify(Object element, String property, Object value) {
			// �޸ı������
			TableItem item = (TableItem) element;
			item.setText(2, (String) value);
			
			for (String[] param : paramList) {
				if (item.getText(1).equals(param[1]))
					param[2] = (String) value;
			}
			
		}
		
	}
	
	/**
	 * ������ݼ�����
	 * @author guoweic
	 *
	 */
	private class ParamTableViewProvider extends TableViewLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			String[] param = (String[])element;
			if(columnIndex == 0)
				return param[0];
			if(columnIndex == 1) {
				return param[1];
			}
			if(columnIndex == 2)
				return param[2];
			return null;
		}

	}
	
	
}
