package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIImageComp;

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

public class DImageComp extends DComponent {

	private Button bt = null; 
	public DImageComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}

	
	protected Control getComposite() {
		return bt;
	}


	
	protected void initialize() {
		UIImageComp ele = (UIImageComp) getUiElement();
		bt = new Button(this, SWT.BORDER_SOLID);
		bt.setData(this);
		
		bt.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		bt.setText("Í¼Æ¬<" + ele.getId() + ">");
		
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

	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("»ù±¾");
		al.add(idProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	
	@Override
	protected String getName() {
		return UIConstant.DIMAGE;
	}

}
