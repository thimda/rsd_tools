package nc.lfw.jsp.swt;

import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class DGroup extends DLayout {

	public DGroup(Composite parent, int style, UIElement ele) {
		super(parent, style, ele);
	}

	@Override
	protected void removeChild(DLayoutPanel panel) {
		DLayoutPanel parentPanel = (DLayoutPanel) this.getDParent();
		UILayoutPanel uiParentPanel = (UILayoutPanel) parentPanel.getUiElement();
		uiParentPanel.setElement(null);
		parentPanel.initUI();
	}

	@Override
	protected String getName() {
		return "·Ö×é";
	}

	@Override
	protected void initialize() {
		this.setLayout(new FillLayout());
//		this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
//		UIGroup uigroup = (UIGroup) getUiElement();
//		List<UILayoutPanel> list = uigroup.getPanelList();
//		new DGroupPanel(this, SWT.NONE, list.get(0));
	}

}
