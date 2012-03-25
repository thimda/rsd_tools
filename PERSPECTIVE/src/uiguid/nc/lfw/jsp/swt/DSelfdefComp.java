package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.SelfDefComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UISelfDefComp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DSelfdefComp extends  DComponent {
	private Control ctrl = null; 
	public DSelfdefComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}
	
	
	protected Control getComposite() {
		return ctrl;
	}

	
	protected void initialize() {
		UISelfDefComp ele = (UISelfDefComp) getUiElement();
		SelfDefComp webBt = (SelfDefComp) getWebElementByUIElement(ele);
		
		Button bt = new Button(this, SWT.NONE);
		bt.setData(this);
//		bt.setSize(100, 20);
		bt.setText(webBt.getId());
		bt.setAlignment(SWT.CENTER);
		ctrl = bt;
//		bt.addMouseListener(new MouseListener(){
//
//			public void mouseDoubleClick(MouseEvent e) {
//				
//			}
//
//			public void mouseDown(MouseEvent e) {
//				Button t = (Button) e.getSource();
//				DesignBase db = (DesignBase) t.getData();
//				if(db.isNest()){
////					DWidget dw = (DWidget) getDesignPanel(db);
////					dw.setSelectedSign(true);
////					setCurrentSelection(dw);
//				}
//				else{
//					db.setSelectedSign(true);
//					setCurrentSelection(db);
//				}
//			}
//
//			public void mouseUp(MouseEvent e) {
//			}
//			
//		});
	}


	@Override
	protected String getName() {
		return UIConstant.DSELFDEFCOMP;
	}

}
