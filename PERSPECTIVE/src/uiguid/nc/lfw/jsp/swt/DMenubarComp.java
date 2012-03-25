package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMenubarComp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DMenubarComp extends DComponent {
	private Button bt = null; 
	public DMenubarComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}

	
	protected void initialize() {
		UIMenubarComp uimenubar = (UIMenubarComp) getUiElement();
		bt = new Button(this, SWT.BORDER_SOLID);
		bt.setData(this);
		
		bt.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		bt.setText("菜单条<" + uimenubar.getId() + ">");
		
		bt.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				
			}

			public void mouseDown(MouseEvent e) {
				Button t = (Button) e.getSource();
				DesignBase db = (DesignBase) t.getData();
				if(db.isNest()){
//					DWidget dw = (DWidget) getDesignPanel(db);
//					dw.setSelectedSign(true);
//					setCurrentSelection(dw);
				}
				else{
					db.setSelectedSign(true);
					setCurrentSelection(db);
				}
			}

			public void mouseUp(MouseEvent e) {
			}
			
		});
		
//		tb = new ToolBar(this, SWT.HORIZONTAL | SWT.FLAT | SWT.RIGHT);
//		tb.setData(this);
//		
//		
//		
//		tb.addMouseListener(new MouseListener(){
//
//			public void mouseDoubleClick(MouseEvent e) {
//				
//			}
//
//			public void mouseDown(MouseEvent e) {
//				System.out.println(e);
//			}
//
//			public void mouseUp(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
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

	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	
	protected Control getComposite() {
		return bt;
	}


	@Override
	protected String getName() {
		return UIConstant.DMENUBAR;
	}

}
