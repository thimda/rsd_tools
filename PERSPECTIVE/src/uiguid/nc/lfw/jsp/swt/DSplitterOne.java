package nc.lfw.jsp.swt;

import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DSplitterOne extends DLayoutPanel {

	public DSplitterOne(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}

	
	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE);
		bt.setText("ONE");
		return bt;
	}


	@Override
	protected String getName() {
		return "�ָ����1";
	}
}
