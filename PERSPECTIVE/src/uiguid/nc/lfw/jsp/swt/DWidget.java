package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UIWidget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DWidget extends DesignPanel {
	private LfwWidget widget;
	public DWidget(Composite parent, int style, UIWidget uimeta, LfwWidget lfwWidget) {
		super(parent, style, uimeta);
		this.widget = lfwWidget;
		initUI();
	}
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("»ù±¾");
		al.add(idProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}

	
	protected void removeChild(DLayoutPanel panel) {
		DLayoutPanel parentPanel = (DLayoutPanel) this.getDParent();
		UILayoutPanel uiParentPanel = (UILayoutPanel) parentPanel.getUiElement();
		uiParentPanel.setElement(null);
		parentPanel.initUI();
	}

	
	protected void initialize() {
		UIWidget widget = (UIWidget) getUiElement();
		DWidgetPanel widgetPanel = new DWidgetPanel(this, SWT.NONE, widget.getUimeta());
		
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;

		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		widgetPanel.setLayoutData(gd);
		
		if(!isNest())
			renderNavigate();
	}

	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}
	
	@Override
	protected String getName() {
		return "Æ¬¶Î";
	}
}
