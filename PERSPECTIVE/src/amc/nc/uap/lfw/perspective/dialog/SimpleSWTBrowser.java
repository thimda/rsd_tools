/**
 * 
 */
package nc.uap.lfw.perspective.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SimpleSWTBrowser {

	// ����������ı���
	public static final String APP_TITLE = "Simple SWT Browser";
	// ������ҳ��url
	public String HOME_URL = "";
	// ���������ں������ؼ�
	private Shell parent = null;
	private Button backButton = null;// ���˰�ť
	private Button forwardButton = null;// ǰ����ť
	private Button stopButton = null;// ֹͣ��ť
	private Text locationText = null;// ��ʾurl���ı���
	private Button goButton = null;// ת��ť
	private Browser browser = null;// ���������
	private Button homeButton = null;// ��ҳ��ť
	private Label statusText = null;// ��ʾ�����״̬���ı���
	private ProgressBar progressBar = null;// װ��ҳ��ʱ�Ľ�����
	private Button refreshButton = null;// ˢ�°�ť

	public SimpleSWTBrowser(String url){
		this.HOME_URL = url;
		this.parent = new Shell(SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.TITLE);
	}
	
	public void openBrowser(){
		createparent();
		parent.setSize(1024, 768);
		parent.open();
	}
	
	// ��ʼ�������
	private void createBrowser() {
		parent.setSize(1024, 768);
		parent.setLayout(new FillLayout());
		// �������������
		browser = new Browser(parent, SWT.BORDER);
		// �������������
		GridData layout = new GridData();
		layout.horizontalSpan = 7;
		layout.horizontalAlignment = GridData.FILL;
		layout.verticalAlignment = GridData.FILL;
		layout.grabExcessVerticalSpace = true;
		browser.setLayoutData(layout);
		// Ϊ�����ע�����ı��¼�
//		browser.addTitleListener(new org.eclipse.swt.browser.TitleListener() {
//			public void changed(org.eclipse.swt.browser.TitleEvent e) {
//				parent.setText(APP_TITLE + " - " + e.title);
//			}
//		});
		// Ϊ�����ע���ַ�ı��¼�
		browser.addLocationListener(new org.eclipse.swt.browser.LocationListener() {
			public void changing(org.eclipse.swt.browser.LocationEvent e) {
				locationText.setText(e.location);
			}

			public void changed(org.eclipse.swt.browser.LocationEvent e) {
			}
		});
		// Ϊ�����ע��װ����ҳ�¼�
		browser.addProgressListener(new org.eclipse.swt.browser.ProgressListener() {
			// ��װ��ʱ������װ�صĽ��ȣ���������ֹͣ��ť����
			public void changed(org.eclipse.swt.browser.ProgressEvent e) {
				if (!stopButton.isEnabled() && e.total != e.current) {
					stopButton.setEnabled(true);
				}
				progressBar.setMaximum(e.total);
				progressBar.setSelection(e.current);
			}

			// װ����ɺ�����ֹͣ��ť�����˰�ť��ǰ����ť�ͽ�������״̬
			public void completed(
					org.eclipse.swt.browser.ProgressEvent e) {
				stopButton.setEnabled(false);
				backButton.setEnabled(browser.isBackEnabled());
				forwardButton.setEnabled(browser.isForwardEnabled());
				progressBar.setSelection(0);
			}
		});
		// ע�������״̬�ı��¼�
		browser.addStatusTextListener(new org.eclipse.swt.browser.StatusTextListener() {
			public void changed(
					org.eclipse.swt.browser.StatusTextEvent e) {
				statusText.setText(e.text);
			}
		});
		// ��ʼ״̬����ҳ��url
		browser.setUrl(HOME_URL);
	}

	// �������ںʹ��ڵĿؼ�
	private void createparent() {
		//parent = new org.eclipse.swt.widgets.Shell();
		org.eclipse.swt.layout.GridLayout gridLayout1 = new GridLayout();
		org.eclipse.swt.layout.GridData gridData2 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData4 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData5 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData6 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData7 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData8 = new org.eclipse.swt.layout.GridData();
		backButton = new Button(parent, SWT.ARROW | SWT.LEFT);
		forwardButton = new Button(parent, SWT.ARROW | SWT.RIGHT);
		stopButton = new Button(parent, SWT.NONE);
		refreshButton = new Button(parent, SWT.NONE);
		homeButton = new Button(parent, SWT.NONE);
		locationText = new Text(parent, SWT.BORDER);
		goButton = new Button(parent, SWT.NONE);
		createBrowser();
		progressBar = new ProgressBar(parent, SWT.BORDER);
		statusText = new Label(parent, SWT.NONE);
//		parent.setText(APP_TITLE);
		parent.setLayout(gridLayout1);
		gridLayout1.numColumns = 7;
		backButton.setEnabled(false);
		backButton.setToolTipText("Navigate back to the previous page");
		backButton.setLayoutData(gridData6);
		forwardButton.setEnabled(false);
		forwardButton.setToolTipText("Navigate forward to the next page");
		forwardButton.setLayoutData(gridData5);
		stopButton.setText("Stop");
		stopButton.setEnabled(false);
		stopButton.setToolTipText("Stop the loading of the current page");
		goButton.setText("Go!");
		goButton.setLayoutData(gridData8);
		goButton.setToolTipText("Navigate to the selected web address");
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		locationText.setLayoutData(gridData2);
		locationText.setText(HOME_URL);
		locationText.setToolTipText("Enter a web address");
		homeButton.setText("Home");
		homeButton.setToolTipText("Return to home page");
		statusText.setText("Done");
		statusText.setLayoutData(gridData7);
		gridData4.horizontalSpan = 5;
		progressBar.setLayoutData(gridData4);
		progressBar.setEnabled(false);
		progressBar.setSelection(0);
		gridData5.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData5.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData6.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData6.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData7.horizontalSpan = 1;
		gridData7.grabExcessHorizontalSpace = true;
		gridData7.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData7.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		gridData8.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
		gridData8.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		refreshButton.setText("Refresh");
		refreshButton.setToolTipText("Refresh the current page");
		parent.setSize(new org.eclipse.swt.graphics.Point(553, 367));
		// ע����ʾ��ַ���ı����¼�
		locationText
				.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
					public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
						locationText.selectAll();
					}
				});
		locationText.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				// Handle the press of the Enter key in the locationText.
				// This will browse to the entered text.
				if (e.character == SWT.LF || e.character == SWT.CR) {
					e.doit = false;
					browser.setUrl(locationText.getText());
				}
			}
		});
		refreshButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						browser.refresh();// ��������
					}
				});
		locationText
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						browser.setUrl(locationText.getText());// �����������ָ���url
					}
				});
		stopButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						browser.stop();// ֹͣװ����ҳ
					}
				});
		backButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						browser.back();// ����
					}
				});
		forwardButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						browser.forward();// ǰ��
					}
				});
		homeButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						browser.setUrl(HOME_URL);// ������ҳ
					}
				});
		goButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						browser.setUrl(locationText.getText());// ת���ַ����ҳ
					}
				});
	}
	
}
