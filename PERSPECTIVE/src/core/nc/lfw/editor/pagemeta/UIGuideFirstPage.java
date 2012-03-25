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
 * UI ������ ��һҳ
 * 
 * @author guoweic
 *
 */
public class UIGuideFirstPage extends WizardPage {

	/**
	 * ��ǰģʽ
	 */
	private int currentMode = 1;
	
	/**
	 * ģ���ļ���ѡ���
	 */
	private Text templeteFolderText;
	
	/**
	 * ѡ���ģ���ļ���·��
	 */
	private String templeteFolderPath = "";
	
	/**
	 * ��ջ����
	 */
	private StackLayout stackLayout = new StackLayout();
	
	/**
	 * ��ѡ���
	 */
	private Combo mainCombo;
	
	/**
	 * ��Ҫ����
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
		
		setTitle("UI ��װ");
		
		Composite main = new Composite(parent, SWT.NONE);
		
		// ���岼��
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginWidth = 0;
		main.setLayout(mainLayout);
		main.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// �ϲ�
		Composite top = new Composite(main, SWT.NONE);
		GridData gridDataTop = new GridData(GridData.FILL_HORIZONTAL);
		top.setLayoutData(gridDataTop);
		mainCombo = initMainCombo(top);
		// ���ñ����µ���Ϣ
		setMessage(mainCombo.getText());
		
		// �²�
		//container = new Composite(main, SWT.NONE);
		container = new Group(main, SWT.NONE);
		GridData gridDataContainer = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gridDataContainer);
		
		stackLayout.marginWidth = 10;
		
		container.setLayout(stackLayout);
		
		// ��ʼ��ÿҳ
		container1 = initContainer1(container);
		container2 = initContainer2(container);
		container3 = initContainer3(container);
		container4 = initContainer4(container);
		
		// Ĭ����ʾ��һҳ
		stackLayout.topControl = container1;
		container.layout();
		
		// ����ѡ���¼�
		mainCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = mainCombo.getText();
				int data = ((Integer) mainCombo.getData(text)).intValue();
				currentMode = data;
				// ��ҳ
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
				// ���ñ����µ���Ϣ
				setMessage(text);
			}
		});
		
		setControl(main);
	}

	/**
	 * ��������ѡ��
	 * @param parent
	 */
	private Combo initMainCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setBounds(16, 11, 200, 25);
		combo.add("NC��׼ģ��");
		combo.add("ģ����");
		combo.add("�Զ���ģ��");
		combo.add("HTMLģ��");
		combo.setData("NC��׼ģ��", 1);
		combo.setData("ģ����", 2);
		combo.setData("�Զ���ģ��", 3);
		combo.setData("HTMLģ��", 4);
		combo.select(0);
		return combo;
		
	}
	
	/**
	 * ��ʼ����һҳ
	 * @param parent
	 * @return
	 */
	private Composite initContainer1(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		
		Button radio1 = new Button(container, SWT.RADIO);
		radio1.setLayoutData(createGridData(100, 1));
		radio1.setText("��Ƭ����");
		radio1.setSelection(true);
		Button radio2 = new Button(container, SWT.RADIO);
		radio2.setLayoutData(createGridData(100, 1));
		radio2.setText("��Ƭ����ͷ");
		Button radio3 = new Button(container, SWT.RADIO);
		radio3.setLayoutData(createGridData(100, 1));
		radio3.setText("��Ƭ������");
		
		Button radio4 = new Button(container, SWT.RADIO);
		radio4.setLayoutData(createGridData(100, 3));
		radio4.setText("�б�����");
		
		Button radio5 = new Button(container, SWT.RADIO);
		radio5.setLayoutData(createGridData(100, 1));
		radio5.setText("��������");
		Button radio6 = new Button(container, SWT.RADIO);
		radio6.setLayoutData(createGridData(100, 2));
		radio6.setText("��������");
		
		// �������
		Label label = new Label(container, SWT.HORIZONTAL);
		label.setLayoutData(createGridData(100, 3));
		
		Button check1 = new Button(container, SWT.CHECK);
		check1.setLayoutData(createGridData(150, 3));
		check1.setText("�Ƿ���е�����");
		
		
		return container;
	}

	/**
	 * ��ʼ���ڶ�ҳ
	 * @param parent
	 * @return
	 */
	private Composite initContainer2(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		Button radio1 = new Button(container, SWT.RADIO);
		radio1.setLayoutData(createGridData(100, 1));
		radio1.setText("��Ƭ����ͷ");
		radio1.setSelection(true);
		Button radio2 = new Button(container, SWT.RADIO);
		radio2.setLayoutData(createGridData(100, 1));
		radio2.setText("�б�");
		Button radio3 = new Button(container, SWT.RADIO);
		radio3.setLayoutData(createGridData(100, 1));
		radio3.setText("����");
		
		Button radio4 = new Button(container, SWT.RADIO);
		radio4.setLayoutData(createGridData(100, 1));
		radio4.setText("List to List");
		Button radio5 = new Button(container, SWT.RADIO);
		radio5.setLayoutData(createGridData(100, 1));
		radio5.setText("��ʽ");
		
		return container;
	}

	/**
	 * ��ʼ������ҳ
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
	 * ��ʼ������ҳ
	 * @param parent
	 * @return
	 */
	private Composite initContainer4(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		new Label(container, SWT.NONE).setText("ѡ��ģ���ļ�:");
		templeteFolderText = new Text(container, SWT.BORDER);
		templeteFolderText.setLayoutData(createGridData(250, 1));
		Button button = new Button(container, SWT.NONE);
		GridData gridDataBtn = new GridData(50, 20);
		gridDataBtn.horizontalSpan = 1;
		button.setLayoutData(gridDataBtn);
		button.setText("ѡ��");
		
		// ����ѡ���¼�
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell(Display.getCurrent());
				// �ļ���ѡ���
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN | SWT.SINGLE);
				// �ļ��е�ȫ·��
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
