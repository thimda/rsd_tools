package nc.lfw.jsp.swt;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class DIFrame extends DComponent {
	private Text text;
	public DIFrame(Composite parent, int style, UIElement element,
			WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}

	
	protected Control getComposite() {
		return text;
	}

	
	protected void initialize() {
		text = new Text(this, SWT.READ_ONLY|SWT.MULTI);
		text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
		text.setText("×Ó´°¿Ú");
		text.setData(this);
	}


	@Override
	protected String getName() {
		return UIConstant.DIFRAME;
	}
}
