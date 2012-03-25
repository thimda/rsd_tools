package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DesignPanel extends DLayout {
	private NavigatePanel navigate;
	private PageMeta pagemeta;
	private UIElement cutElement;
	protected int borderLayoutIndex;
	protected int flowhIndex;
	protected int FlowvIndex;
	protected int cardLayoutIndex;
	protected int tabIndex;
	protected int splitterIndex;
	protected int borderIndex;
	protected int menubarGroupIndex;
	protected int panelIndex;
	protected int groupIndex;
	protected int outlookbarIndex;
	
	public DesignPanel(Composite parent, int style, UIElement uimeta, PageMeta pm) {
		this(parent, style, uimeta);
		this.pagemeta = pm;
		initUI();
	}

	public DesignPanel(Composite parent, int style, UIElement uimeta) {
		super(parent, style);
		setUiElement(uimeta);
		//this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_CYAN));
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		this.setLayout(layout);
	}

	protected void initialize() {
		renderLayout();
		renderNavigate();
	}

	protected void renderNavigate() {
		navigate = new NavigatePanel(this, SWT.NULL);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		gd.heightHint = 26;
//		gd.verticalAlignment = GridData.FILL;
//		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 2;

		navigate.setLayoutData(gd);
	}

	public void updateNavigate(DesignBase current) {
		navigate.setDesignElement(current);
		int totleHeight = 0;
		for (Control c : navigate.getChildren()){
			totleHeight = c.getBounds().y + c.getBounds().height;
		}
		GridData gd = (GridData)navigate.getLayoutData();
		gd.heightHint = totleHeight;
		this.layout(true);
	}

//	private void renderToolbar() {
//		final ToolBar tb = new ToolBar(this, SWT.VERTICAL | SWT.FLAT
//				| SWT.RIGHT);
//		ToolItem tiOpen = new ToolItem(tb, SWT.PUSH);
//		tiOpen.setText("打开");
//		// tiOpen.setImage(i1);
//		tiOpen.setToolTipText("打开文件");
//		ToolItem tiSave = new ToolItem(tb, SWT.PUSH);
//		tiSave.setText("保存");
//		// tiSave.setImage(i2);
//		ToolItem tiSeparator1 = new ToolItem(tb, SWT.SEPARATOR);
//		tiSeparator1.setText("分隔符1不会显示");
//		final ToolItem tiHelp = new ToolItem(tb, SWT.DROP_DOWN);// 下拉按钮
//		tiHelp.setText("帮助");
//		// tiHelp.setImage(i3);
//
//		final Menu muhelp = new Menu(this.getShell(), SWT.POP_UP);
//
//		MenuItem miWelcom = new MenuItem(muhelp, SWT.PUSH);
//		miWelcom.setText("欢迎");
//		MenuItem miWizard = new MenuItem(muhelp, SWT.PUSH);
//		miWizard.setText("向导");
//
//		tiHelp.addListener(SWT.Selection, new Listener() {
//
//			
//			public void handleEvent(Event event) {
//				if (event.detail == SWT.ARROW) {
//					Rectangle rect = tiHelp.getBounds();// 取得按钮边框
//					Point pt = new Point(rect.x, rect.y + rect.height);
//					pt = tb.toDisplay(pt);// 转换为屏幕相对位置
//					muhelp.setLocation(pt.x, pt.y);// 设置菜单
//					muhelp.setVisible(true);// 显示菜单
//
//				}
//			}
//
//		});
//
//		GridData gd = new GridData();
//		gd.verticalAlignment = GridData.FILL;
//		gd.grabExcessVerticalSpace = true;
//		gd.widthHint = 60;
//		tb.setLayoutData(gd);
//
//	}

	private void renderLayout() {
		
		DesignPanelPanel panelPanel = new DesignPanelPanel(this, SWT.NONE, getUiElement());
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;

		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		panelPanel.setLayoutData(gd);

	}

	
	protected Composite getComposite() {
		return this;
	}
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		return al.toArray(new IPropertyDescriptor[0]);
	}

	
	protected void removeChild(DLayoutPanel panel) {
		// TODO Auto-generated method stub
		
	}

	public PageMeta getPagemeta() {
		return pagemeta;
	}

	public void setPagemeta(PageMeta pagemeta) {
		this.pagemeta = pagemeta;
	}

	@Override
	protected String getName() {
		return "主面板";
	}

	public UIElement getCutElement() {
		return cutElement;
	}

	public void setCutElement(UIElement cutElement) {
		this.cutElement = cutElement;
	}
}
