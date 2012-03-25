package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DMenuGroupItem extends DLayoutPanel {

	public DMenuGroupItem(Composite parent, int style, UIElement element) {
		super(parent, style, element);
	}

	
	protected Control createSign() {
		//UIMenuGroupItem menuItem = (UIMenuGroupItem) getUiElement();
		Button bt = new Button(this, SWT.NONE);
		bt.setText("groupitem");
		return bt;
	}

	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
//		DesignPanel dPanel = getDesignPanel(this);
//		PageMeta pm = dPanel.getPagemeta();
//		PageStates pss = pm.getPageStates();
//		PageState[] states = pss.getPageStates();
//		Integer[] statesIndices = new Integer[states.length + 1];
//		String[] statesStr = new String[states.length + 1];
//		statesStr[0] = " ";
//		statesIndices[0] = 0;
//		for (int i = 0; i < states.length; i++) {
//			statesStr[i + 1] = states[i].getName();
//		}
//		ComboBoxPropertyDescriptor widgetProp = new ComboBoxPropertyDescriptor(UIMenuGroupItem.STATE, "关联页面状态", statesStr);
//		widgetProp.setCategory("基本");
//		al.add(widgetProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	protected String getName() {
		return "菜单组项";
	}

}
