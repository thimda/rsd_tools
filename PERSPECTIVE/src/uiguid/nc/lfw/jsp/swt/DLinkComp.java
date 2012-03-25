package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILinkComp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DLinkComp extends DComponent {
	private Button bt = null; 
	public DLinkComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		initUI();
	}

	
	protected Control getComposite() {
		return bt;
	}

	
	protected void initialize() {
		UILinkComp ele = (UILinkComp) getUiElement();
//		LinkComp webLink = (LinkComp) getWebElementByUIElement(ele);
		bt = new Button(this, SWT.NONE);
//		bt.setText(webLink.getId());
//		bt.setSize(100, Integer.valueOf(webLink.getHeight()));
		bt.setText(ele.getId());
		bt.setSize(100, Integer.valueOf(ele.getHeight()));
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
		return UIConstant.DLINKCOMP;
	}

}