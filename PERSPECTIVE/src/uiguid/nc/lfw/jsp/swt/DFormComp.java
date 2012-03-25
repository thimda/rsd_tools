package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class DFormComp extends DComponent {
	private FormPanel form;
	public DFormComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		initUI();
	}

	
	protected void initialize() {
		//UIFormComp form = (UIFormComp) getUiElement();
		setLayout(new FillLayout());
		FormComp webEle = (FormComp) getWebElement();
		form = new FormPanel(this, SWT.NONE, webEle);
		form.setData(this);
		
		form.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				
			}

			public void mouseDown(MouseEvent e) {
				Composite t = (Composite) e.getSource();
				DesignBase db = null;
				if(t instanceof DesignBase)
					db = (DesignBase) t;
				else
					db = (DesignBase) t.getData();
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

	
	protected Composite getComposite() {
		return form;
	}


	@Override
	protected String getName() {
		return UIConstant.DFORM;
	}

}
