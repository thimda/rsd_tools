package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.page.PlugoutDescItem;

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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Widget连接器 参数构造向导 第二页内容
 * @author guoweic
 *
 */
public class WidgetRelationParamPanel2 extends Canvas {

	/**
	 * 堆栈布局
	 */
	private StackLayout stackLayout = new StackLayout();
	
	/**
	 * 值选择区域
	 */
	private Composite valueContainer;
	
	private Composite valueContainer1;
	private Composite valueContainer2;
	
	/**
	 * 当前组件对象
	 */
	private WebElement currentComp;
	
	/**
	 * 类型选择下拉框
	 */
	private Combo typeCombo;
	
	/**
	 * DS的Field选择表格
	 */
	private Table fieldTable;
	
	/**
	 * 公式输入框
	 */
	private Text formularText;
	
	/**
	 * 描述输入框
	 */
	private Text descText;
	
	/**
	 * 参数类型
	 */
	private String type = "";
	
	/**
	 * 参数值
	 */
	private String value = "";

	public WidgetRelationParamPanel2(Composite parent, int style) {
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
		valueContainer.setLayout(stackLayout);
		
		// 初始化每页
		valueContainer1 = initValueContainer1(valueContainer);
		valueContainer2 = initValueContainer2(valueContainer);
		
		// 默认显示第一页
		stackLayout.topControl = valueContainer1;
		valueContainer.layout();
		
		// 参数描述区域
		Composite descArea = new Composite(this, SWT.NONE);
		GridLayout descAreaLayout = new GridLayout(4, false);
		descArea.setLayout(descAreaLayout);
		descArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(descArea, SWT.NONE).setText("描述:");
		descText = new Text(descArea, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData descGridData = new GridData(450, 25);
		descGridData.horizontalSpan = 3;
		descText.setLayoutData(descGridData);
		
		
		// 增加参数类型选择事件
		typeCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = typeCombo.getText();
				String data = (String) typeCombo.getData(text);
				// 换页
				if (PlugoutDescItem.TYPE_DS_FIELD.equals(data)) {
					stackLayout.topControl = valueContainer2;
				} else if (PlugoutDescItem.TYPE_FOMULAR.equals(data)) {
					stackLayout.topControl = valueContainer1;
				} else {
					//TODO
					
				}
				valueContainer.layout();
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
		if (currentComp instanceof Dataset) {
			typeCombo.add(TypeConstant.getWidgetRelationParamType().get(PlugoutDescItem.TYPE_FOMULAR));
			typeCombo.setData(TypeConstant.getWidgetRelationParamType().get(PlugoutDescItem.TYPE_FOMULAR), PlugoutDescItem.TYPE_FOMULAR);
			
			typeCombo.add(TypeConstant.getWidgetRelationParamType().get(PlugoutDescItem.TYPE_DS_FIELD));
			typeCombo.setData(TypeConstant.getWidgetRelationParamType().get(PlugoutDescItem.TYPE_DS_FIELD), PlugoutDescItem.TYPE_DS_FIELD);

			if (type.equals(PlugoutDescItem.TYPE_FOMULAR))
				typeCombo.select(0);
			else if (type.equals(PlugoutDescItem.TYPE_DS_FIELD))
				typeCombo.select(1);
			else {
				typeCombo.select(0);
				type = PlugoutDescItem.TYPE_FOMULAR;
			}
		} else {  //TODO
			typeCombo.add(TypeConstant.getWidgetRelationParamType().get(PlugoutDescItem.TYPE_FOMULAR));
			typeCombo.setData(TypeConstant.getWidgetRelationParamType().get(PlugoutDescItem.TYPE_FOMULAR), PlugoutDescItem.TYPE_FOMULAR);
			
			if (type.equals(PlugoutDescItem.TYPE_FOMULAR))
				typeCombo.select(0);
			else {
				typeCombo.select(0);
				type = PlugoutDescItem.TYPE_FOMULAR;
			}
		}
	}
	
	/**
	 * 重新加载DS的列选择表格
	 */
	public void reloadFieldTable() {
		if (currentComp instanceof Dataset) {
			Field[] fieldArray = ((Dataset)currentComp).getFieldSet().getFields();
			for (Field field : fieldArray) {
				// 加载内容
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
	
	/**
	 * 初始化第一页
	 * @param parent
	 * @return
	 */
	private Composite initValueContainer1(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		
		new Label(container, SWT.NONE).setText("公式:");
		formularText = new Text(container, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 3;
		
		formularText.setLayoutData(gridData);
		
		return container;
	}
	
	/**
	 * 初始化第二页
	 * @param parent
	 * @return
	 */
	private Composite initValueContainer2(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		
		new Label(container, SWT.NONE).setText("选择列:");
		
		// 数据集列信息表格
		fieldTable = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		
		TableLayout tableLayout = new TableLayout();
		fieldTable.setLayout(tableLayout);
		fieldTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		fieldTable.setHeaderVisible(true);
		fieldTable.setLinesVisible(true);
		createColumn(fieldTable, tableLayout, 200, SWT.NONE, "列名");

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
		String text = typeCombo.getText();
		String data = (String) typeCombo.getData(text);
		if (PlugoutDescItem.TYPE_DS_FIELD.equals(data)) {
			value = fieldTable.getItem(fieldTable.getSelectionIndex()).getText(0);
		} else if (PlugoutDescItem.TYPE_FOMULAR.equals(data)) {
			value = formularText.getText().trim();
		} else {
			//TODO
			
		}
		return value;
	}

	public Table getFieldTable() {
		return fieldTable;
	}

	public Text getFormularText() {
		return formularText;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Combo getTypeCombo() {
		return typeCombo;
	}

	public Text getDescText() {
		return descText;
	}

}
