package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIButton;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DButton extends DComponent {
	private Button bt = null; 
	public DButton(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		//this.setLayout(new FillLayout());
		initUI();
	}
	
	
	protected Control getComposite() {
		return bt;
	}

	
	protected void initialize() {
		UIButton ele = (UIButton) getUiElement();
//		ButtonComp webBt = (ButtonComp) getWebElementByUIElement(ele);
		
		bt = new Button(this, SWT.NONE);
		bt.setData(this);
//		bt.setSize(Integer.valueOf(webBt.getWidth()), Integer.valueOf(webBt.getHeight()));
		bt.setSize(Integer.valueOf(ele.getWidth()), Integer.valueOf(ele.getHeight()));
		
//		bt.setText(webBt.getId());
		bt.setText(ele.getId());
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


	@Override
	protected String getName() {
		return UIConstant.DBUTTON;
	}

}
