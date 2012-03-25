package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIToolBar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DToolBar extends DComponent {
	
	private Button bt = null; 
	public DToolBar(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}

	
	protected Control getComposite() {
		return bt;
	}

	protected void initialize() {
		UIToolBar ele = (UIToolBar) getUiElement();
		bt = new Button(this, SWT.BORDER_SOLID);
		bt.setData(this);
		
//		bt.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		bt.setText("¹¤¾ßÌõ<" + ele.getId() + ">");
		
		bt.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				
			}

			public void mouseDown(MouseEvent e) {
				Button t = (Button) e.getSource();
				DesignBase db = (DesignBase) t.getData();
				if(db.isNest()){
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
		return UIConstant.DTOOLBAR;
	}

}
