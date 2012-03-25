package nc.lfw.jsp.swt;

import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

public class DGroupPanel extends DLayoutPanel {
	private Group grp;
	public DGroupPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}

//	@Override
//	protected Control createSign() {
//		UIGroup uig = (UIGroup) ((DGroup)this.getParent()).getUiElement();
//		Group g = new Group(this, SWT.NONE);
//		g.setLayout(new FillLayout());
//		g.setText(uig.getText());
//		
//		Button bt = new Button(g, SWT.NONE);
//		bt.setText("·Ö×éÄÚÈÝ");
//		return bt;
//	}

	@Override
	protected String getName() {
		return null;
	}

	@Override
	protected Composite getTrueParent() {
		return grp;
	}

	@Override
	protected Control createSign() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	protected void initchildren() {
//		UIGroup uig = (UIGroup) ((DGroup)this.getParent()).getUiElement();
//		grp = new Group(this, SWT.NONE);
//		grp.setLayout(new FillLayout());
//		grp.setText(uig.getText());
//		super.initchildren();
//	}


}
