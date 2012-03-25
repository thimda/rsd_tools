package nc.lfw.jsp.swt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.NumberPropertyDescriptor;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIFlowhPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DFlowhPanel extends DLayoutPanel {
	public DFlowhPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}

	
	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE);
		bt.setText("Flowh1");
		return bt;
	}
	
	protected Composite getComposite() {
		return this;
	}
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		
		NumberPropertyDescriptor textProp = new NumberPropertyDescriptor(UIFlowhPanel.WIDTH, "宽度");
		textProp.setCategory("基本");
		al.add(textProp);
		
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	protected void updateProperty(String id, Serializable oldValue, Serializable newValue){
		if(getParent() != null)
			((DFlowhLayout)getParent()).updateProperty(id, oldValue, newValue);
	}


	@Override
	protected String getName() {
		return "横向面板";
	}

}
