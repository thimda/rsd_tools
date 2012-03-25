package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIBorder;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public abstract class DLayout extends DesignBase {
	public DLayout(Composite parent, int style, UIElement ele) {
		super(parent, style, ele);
		initUI();
	}
	
	public DLayout(Composite parent, int style){
		super(parent, style, null);
	}
	protected Composite getComposite() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor(UIBorder.ID, "ID");
		idProp.setCategory("»ù±¾");
		al.add(idProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}
	public Object getEditableValue() {
		return this;
	}
	
	protected abstract void removeChild(DLayoutPanel panel);
}
