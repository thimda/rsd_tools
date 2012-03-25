package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.jsp.uimeta.UICardLayout;
import nc.uap.lfw.jsp.uimeta.UICardPanel;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayout;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DCardLayout extends DLayout {
	public DCardLayout(Composite parent, int style, UIElement element) {
		super(parent, style, element);
	}

	
	protected void initialize() {
		StackLayout stackLayout = new StackLayout();
		this.setLayout(stackLayout);
		UICardLayout ele = (UICardLayout) getUiElement();
		List<UILayoutPanel> list = ele.getPanelList();
		Iterator<UILayoutPanel> it = list.iterator();
		while(it.hasNext()){
			UICardPanel panel = (UICardPanel) it.next();
			new DCardPanel(this, SWT.NONE, panel);
		}
		stackLayout.topControl = this.getChildren()[0];
		//Button bt1 = new Button(this, SWT.NONE);
		//bt1.setText("aaa");
		
		//layout.topControl = bt1;
		this.layout();
	}

	public void setNowPanel(String id) {
		Control[] ctrls = this.getChildren();
		for (int i = 0; i < ctrls.length; i++) {
			DCardPanel panel = (DCardPanel) ctrls[i];
			UICardPanel ele = (UICardPanel) panel.getUiElement();
			if(ele.getId().equals(id)){
				((StackLayout)getLayout()).topControl = panel;
				this.layout();
				return;
			}
		}
	}

	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
		List<UILayoutPanel> panelList = ((UILayout)getUiElement()).getPanelList();
		String[] labels = new String[panelList.size()];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = ((UICardPanel)panelList.get(i)).getId();
		}
		ObjectComboPropertyDescriptor currProp = new ObjectComboPropertyDescriptor(UICardLayout.CURRENT_ITEM, "默认卡片", labels);
		currProp.setCategory("基本");
		al.add(currProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}

	
	protected void removeChild(DLayoutPanel panel) {
		UILayoutPanel uiPanel = (UILayoutPanel) panel.getUiElement();
		UICardLayout uitab = (UICardLayout) getUiElement();
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


	@Override
	protected String getName() {
		return UIConstant.DCARD;
	}

}
