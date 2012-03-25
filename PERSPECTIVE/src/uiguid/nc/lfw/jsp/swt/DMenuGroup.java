package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayout;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UIMenuGroup;
import nc.uap.lfw.jsp.uimeta.UIMenuGroupItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DMenuGroup extends DLayout {

	public DMenuGroup(Composite parent, int style, UIElement ele) {
		super(parent, style, ele);
	}

	
	protected void initialize() {
		this.setLayout(new FillLayout());
		this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		UILayout layout = (UILayout) getUiElement();
		List<UILayoutPanel> list = layout.getPanelList();
		Iterator<UILayoutPanel> it = list.iterator();
		while(it.hasNext()){
			UIMenuGroupItem uiItem = (UIMenuGroupItem) it.next();
			new DMenuGroupItem(this, SWT.NONE, uiItem);
			
		}
	}

	
	protected void removeChild(DLayoutPanel panel) {
		UILayoutPanel uiPanel = (UILayoutPanel) panel.getUiElement();
		UIMenuGroup uitab = (UIMenuGroup) getUiElement();
		uitab.getPanelList().remove(uiPanel);
		if(uitab.getPanelList().size() == 0){
			DLayoutPanel parentPanel = (DLayoutPanel) this.getDParent();
			UILayoutPanel uiParentPanel = (UILayoutPanel) parentPanel.getUiElement();
			uiParentPanel.setElement(null);
			parentPanel.initUI();
		}
		else
			this.initUI();
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		
	
		TextPropertyDescriptor idProp = new TextPropertyDescriptor(UIMenuGroup.ID, "ID");
		idProp.setCategory("»ù±¾");
		al.add(idProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	protected String getName() {
		return UIConstant.DMENUGROUP;
	}

}
