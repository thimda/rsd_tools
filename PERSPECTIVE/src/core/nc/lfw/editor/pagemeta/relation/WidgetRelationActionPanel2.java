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
 * Widget连接器 执行内容构造向导 第二页内容
 * @author guoweic
 *
 */
public class WidgetRelationActionPanel2 extends Canvas {
	
	/**
	 * 参数设置表格
	 */
	private Table paramTable;
	
	private TableViewer tv;
	
	private CheckboxTableViewer ctv;
	
	private List<String[]> paramList;
	
	/**
	 * 值选择区域
	 */
	private Composite valueContainer;
	
	/**
	 * 当前组件对象
	 */
	private WebElement currentComp;
	
	/**
	 * 类型选择下拉框
	 */
	private Combo typeCombo;
	
	/**
	 * 执行内容类型
	 */
	private String type = "";
	
	/**
	 * 执行内容值
	 */
	private String value = "";
	
	/**
	 * 参数名称映射
	 */
	private Map<String, String> paramNameMap = new HashMap<String, String>();

	public WidgetRelationActionPanel2(Composite parent, int style) {
		super(parent, style);
		initUI();
	}

	private void initUI() {
		// 总体布局
		GridLayout mainLayout = new GridLayout();
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// 类型选择区域
		Composite typeArea = new Composite(this, SWT.NONE);
		GridLayout idAreaLayout = new GridLayout(4, false);
		typeArea.setLayout(idAreaLayout);
		typeArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(typeArea, SWT.NONE).setText("类型:");
		typeCombo = initTypeCombo(typeArea);
		
		// 值输入区域
		valueContainer = new Composite(this, SWT.NONE);
		valueContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		valueContainer.setLayout(new GridLayout(4, false));

		new Label(valueContainer, SWT.NONE).setText("参数:");
		
		// 初始化参数表格
		initParamTable(valueContainer);
		
		// 增加执行内容类型选择事件
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
	 * 初始化类型选择下拉框
	 * @param parent
	 */
	private Combo initTypeCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setLayoutData(createGridData(200, 3));
		combo.setBounds(16, 0, 200, 25);
		return combo;
		
	}
	
	/**
	 * 重新加载类型选择下拉选项
	 */
	public void reloadTypeComboData() {
		typeCombo.removeAll();
		if (currentComp instanceof LfwWidget) {
			typeCombo.add("页面重定向");
			typeCombo.setData("页面重定向", PluginDescItem.TYPE_IFRAME_REDIRECT);
			
			typeCombo.select(0);
			
			type = PluginDescItem.TYPE_IFRAME_REDIRECT;
		} else if (currentComp instanceof Dataset) {
			typeCombo.add("数据加载");
			typeCombo.setData("数据加载", PluginDescItem.TYPE_DS_LOAD);
			
			typeCombo.select(0);
			
			type = PluginDescItem.TYPE_DS_LOAD;
		} else {  //TODO
			type = "";
		}
	}
	
	/**
	 * 重新加载参数表格数据
	 */
	public void reloadParamTableData(Map<String, LfwParameter> parameterMap) {
		tv.setContentProvider(new TableViewContentProvider());
		tv.setLabelProvider(new ParamTableViewProvider());
		
//		paramTable.removeAll();
		
		paramList = new ArrayList<String[]>();
		
		for (String key : parameterMap.keySet()) {
			LfwParameter PluginParameter = parameterMap.get(key);
//			paramNameMap.put(PluginParameter.getName(), "");
			
			// 加载表格内容
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
		// 设置编辑列
		CellEditor[] cellEditor = new CellEditor[3];
		cellEditor[0] = null;
		cellEditor[1] = null;
		cellEditor[2] = new TextCellEditor(tv.getTable());
		
		tv.setCellEditors(cellEditor);
		tv.setCellModifier(new MyCellModifier(tv));
		
	}
	
	/**
	 * 初始化参数表格
	 * @param parent
	 * @return
	 */
	private void initParamTable(Composite parent) {
		
		// 参数设置表格
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
		createColumn(paramTable, tableLayout, 30, SWT.NONE, "选择");
		createColumn(paramTable, tableLayout, 150, SWT.NONE, "Plugin参数");
		createColumn(paramTable, tableLayout, 150, SWT.NONE, "使用参数");
		
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
	 * 列编辑器
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
		 * 获取显示内容
		 */
		public Object getValue(Object element, String property) {
			String[] param = (String[]) element;
			return param[2];
		}

		/**
		 * 修改后执行方法
		 */
		public void modify(Object element, String property, Object value) {
			// 修改表格内容
			TableItem item = (TableItem) element;
			item.setText(2, (String) value);
			
			for (String[] param : paramList) {
				if (item.getText(1).equals(param[1]))
					param[2] = (String) value;
			}
			
		}
		
	}
	
	/**
	 * 表格内容加载器
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
