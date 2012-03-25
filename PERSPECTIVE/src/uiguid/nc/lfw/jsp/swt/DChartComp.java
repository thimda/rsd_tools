package nc.lfw.jsp.swt;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DChartComp extends DComponent {
	private Control ctrl = null; 
	public DChartComp(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element, webElement);
		this.setLayout(new FillLayout());
		initUI();
	}
	
	
	protected Control getComposite() {
		return ctrl;
	}

	
	protected void initialize() {
	//	UIChartComp ele = (UIChartComp) getUiElement();
	//	ChartComp webBt = (ChartComp) getWebElementByUIElement(ele);
		Canvas c = new Canvas(this, SWT.NONE);
		c.setData(this);
		c.setBackgroundImage(MainPlugin.loadImage("icons/fusion/", "barchart.png").createImage());
		ctrl = c;
	}


	@Override
	protected String getName() {
		return UIConstant.DCHARTCOMP;
	}

}
