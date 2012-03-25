package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class DError extends DComponent {
	private Button bt = null; 
	public DError(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}

	
	protected Control getComposite() {
		return bt;
	}

	protected void initialize() {
		bt = new Button(this, SWT.BORDER_SOLID);
		bt.setImage(PaletteImage.createDeleteImage());
		bt.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		bt.setText("³ö´í!");
		bt.setData(this);
		
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
		return UIConstant.DERROR;
	}

}
