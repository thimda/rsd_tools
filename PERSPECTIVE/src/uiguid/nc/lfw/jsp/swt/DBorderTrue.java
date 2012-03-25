package nc.lfw.jsp.swt;

import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DBorderTrue extends DLayoutPanel {

	public DBorderTrue(Composite parent, int style, UIElement element) {
		super(parent, style, element);
	}

	
	protected void initialize(){
//		GridData gd = new GridData(GridData.FILL_BOTH);
//		this.setLayoutData(gd);
		FillLayout layout = new FillLayout();
		//layout.marginHeight = 1;
		//layout.marginWidth = 1;
//		layout.marginTop = 10;
//		layout.marginRight = 10;
		this.setLayout(layout);
		super.initialize();
	}
	
	
	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE);
		bt.setText("BORDER");
		return bt;
	}


	@Override
	protected String getName() {
		return null;
	}

}
