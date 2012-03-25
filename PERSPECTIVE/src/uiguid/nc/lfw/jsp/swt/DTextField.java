package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UITextField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DTextField extends DComponent {
	
	private Button bt = null; 
	public DTextField(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		initUI();
	}

	
	protected Control getComposite() {
		return bt;
	}


	
	protected void initialize() {
		UITextField ele = (UITextField) getUiElement();
		TextComp textcomp = (TextComp) getWebElementByUIElement(ele);
		bt = new Button(this, SWT.NONE);
		bt.setData(this);
		//bt.setSize(Integer.valueOf(textcomp.getWidth()), Integer.valueOf(textcomp.getHeight()));
		bt.setSize(100, 30);
		bt.setText(textcomp.getId());
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
	}


//	
//	protected void initialize() {
//		RowLayout layout = new RowLayout(SWT.FILL);
//		this.setLayout(layout);
////		UITextField ele = (UITextField) getUiElement();
////		TextComp webText = (TextComp) getWebElement();
//		Text bt = new Text(this, SWT.BORDER);
//		RowData rd = new RowData();
//		rd.height = 22;
//		bt.setLayoutData(rd);
//		//bt.setText(webText.getWidth(), webText.getHeight());
//	}


	@Override
	protected String getName() {
		return UIConstant.DTEXT;
	}

}
