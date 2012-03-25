package nc.lfw.editor.pagemeta;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * UI 创建向导 第一页
 * 
 * @author guoweic
 *
 */
public class UIGuideFirstPage extends WizardPage {

	/**
	 * 当前模式
	 */
	private int currentMode = 1;
	
	/**
	 * 模版文件夹选择框
	 */
	private Text templeteFolderText;
	
	/**
	 * 选择的模版文件夹路径
	 */
	private String templeteFolderPath = "";
	
	/**
	 * 堆栈布局
	 */
	private StackLayout stackLayout = new StackLayout();
	
	/**
	 * 主选择框
	 */
	private Combo mainCombo;
	
	/**
	 * 主要区域
	 */
	private Composite container;
	
	private Composite container1;
	private Composite container2;
	private Composite container3;
	private Composite container4;
	
	protected UIGuideFirstPage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("UI 组装");
		
		Composite main = new Composite(parent, SWT.NONE);
		
		// 总体布局
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginWidth = 0;
		main.setLayout(mainLayout);
		main.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// 上部
		Composite top = new Composite(main, SWT.NONE);
		GridData gridDataTop = new GridData(GridData.FILL_HORIZONTAL);
		top.setLayoutData(gridDataTop);
		mainCombo = initMainCombo(top);
		// 设置标题下的信息
		setMessage(mainCombo.getText());
		
		// 下部
		//container = new Composite(main, SWT.NONE);
		container = new Group(main, SWT.NONE);
		GridData gridDataContainer = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gridDataContainer);
		
		stackLayout.marginWidth = 10;
		
		container.setLayout(stackLayout);
		
		// 初始化每页
		container1 = initContainer1(container);
		container2 = initContainer2(container);
		container3 = initContainer3(container);
		container4 = initContainer4(container);
		
		// 默认显示第一页
		stackLayout.topControl = container1;
		container.layout();
		
		// 增加选择事件
		mainCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = mainCombo.getText();
				int data = ((Integer) mainCombo.getData(text)).intValue();
				currentMode = data;
				// 换页
				if (1 == data) {
					stackLayout.topControl = container1;
					container.layout();
				} else if (2 == data) {
					stackLayout.topControl = container2;
					container.layout();
				} else if (3 == data) {
					stackLayout.topControl = container3;
					container.layout();
				} else if (4 == data) {
					stackLayout.topControl = container4;
					container.layout();
				}
				// 设置标题下的信息
				setMessage(text);
			}
		});
		
		setControl(main);
	}

	/**
	 * 加载下拉选项
	 * @param parent
	 */
	private Combo initMainCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setBounds(16, 11, 200, 25);
		combo.add("NC标准模版");
		combo.add("模版向导");
		combo.add("自定义模版");
		combo.add("HTML模版");
		combo.setData("NC标准模版", 1);
		combo.setData("模版向导", 2);
		combo.setData("自定义模版", 3);
		combo.setData("HTML模版", 4);
		combo.select(0);
		return combo;
		
	}
	
	/**
	 * 初始化第一页
	 * @param parent
	 * @return
	 */
	private Composite initContainer1(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		
		Button radio1 = new Button(container, SWT.RADIO);
		radio1.setLayoutData(createGridData(100, 1));
		radio1.setText("卡片主子");
		radio1.setSelection(true);
		Button radio2 = new Button(container, SWT.RADIO);
		radio2.setLayoutData(createGridData(100, 1));
		radio2.setText("卡片单表头");
		Button radio3 = new Button(container, SWT.RADIO);
		radio3.setLayoutData(createGridData(100, 1));
		radio3.setText("卡片单表体");
		
		Button radio4 = new Button(container, SWT.RADIO);
		radio4.setLayoutData(createGridData(100, 3));
		radio4.setText("列表主子");
		
		Button radio5 = new Button(container, SWT.RADIO);
		radio5.setLayoutData(createGridData(100, 1));
		radio5.setText("管理主子");
		Button radio6 = new Button(container, SWT.RADIO);
		radio6.setLayoutData(createGridData(100, 2));
		radio6.setText("管理单表体");
		
		// 横向分栏
		Label label = new Label(container, SWT.HORIZONTAL);
		label.setLayoutData(createGridData(100, 3));
		
		Button check1 = new Button(container, SWT.CHECK);
		check1.setLayoutData(createGridData(150, 3));
		check1.setText("是否带有导航树");
		
		
		return container;
	}

	/**
	 * 初始化第二页
	 * @param parent
	 * @return
	 */
	private Composite initContainer2(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		Button radio1 = new Button(container, SWT.RADIO);
		radio1.setLayoutData(createGridData(100, 1));
		radio1.setText("卡片单表头");
		radio1.setSelection(true);
		Button radio2 = new Button(container, SWT.RADIO);
		radio2.setLayoutData(createGridData(100, 1));
		radio2.setText("列表");
		Button radio3 = new Button(container, SWT.RADIO);
		radio3.setLayoutData(createGridData(100, 1));
		radio3.setText("管理");
		
		Button radio4 = new Button(container, SWT.RADIO);
		radio4.setLayoutData(createGridData(100, 1));
		radio4.setText("List to List");
		Button radio5 = new Button(container, SWT.RADIO);
		radio5.setLayoutData(createGridData(100, 1));
		radio5.setText("向导式");
		
		return container;
	}

	/**
	 * 初始化第三页
	 * @param parent
	 * @return
	 */
	private Composite initContainer3(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		//TODO
		
		return container;
	}

	/**
	 * 初始化第四页
	 * @param parent
	 * @return
	 */
	private Composite initContainer4(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		new Label(container, SWT.NONE).setText("选择模版文件:");
		templeteFolderText = new Text(container, SWT.BORDER);
		templeteFolderText.setLayoutData(createGridData(250, 1));
		Button button = new Button(container, SWT.NONE);
		GridData gridDataBtn = new GridData(50, 20);
		gridDataBtn.horizontalSpan = 1;
		button.setLayoutData(gridDataBtn);
		button.setText("选择");
		
		// 增加选择事件
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell(Display.getCurrent());
				// 文件夹选择框
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN | SWT.SINGLE);
				// 文件夹的全路径
				templeteFolderPath = dialog.open();
				templeteFolderText.setText(templeteFolderPath);
			}
		});
		
		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public int getCurrentMode() {
		return currentMode;
	}

	public String getTempleteFolderPath() {
		return templeteFolderPath;
	}
	
}
