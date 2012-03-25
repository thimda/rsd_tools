package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DTabRightPanelPanel extends DLayoutPanel {

	public DTabRightPanelPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}

	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE | SWT.NO_BACKGROUND);
		bt.setText("Tab扩展");
		return bt;
	}
	
	protected Composite getComposite() {
		return this;
	}
	
		
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
	
//		TextPropertyDescriptor widthProp = new TextPropertyDescriptor(UITabRightPanelPanel.WIDTH, "宽度");
//		widthProp.setCategory("基本");
//		al.add(widthProp);
		
		return al.toArray(new IPropertyDescriptor[0]);
	}
	

	@Override
	protected String getName() {
		return "TabPanel";
	}
}