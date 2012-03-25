package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIBorder;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DBorder extends DLayout {

	public DBorder(Composite parent, int style, UIElement ele) {
		super(parent, style, ele);
	}

	
	protected void initialize() {
		FillLayout layout = new FillLayout();
		this.setLayout(layout);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		UIBorder uiBorder = (UIBorder) getUiElement();
		List<UILayoutPanel> list = uiBorder.getPanelList();
		new DBorderTrue(this, SWT.NONE, list.get(0));
	}

	
	protected void removeChild(DLayoutPanel panel) {
		DLayoutPanel parentPanel = (DLayoutPanel) this.getDParent();
		UILayoutPanel uiParentPanel = (UILayoutPanel) parentPanel.getUiElement();
		uiParentPanel.setElement(null);
		parentPanel.initUI();
	}
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor(UIBorder.ID, "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
		TextPropertyDescriptor widgetProp = new TextPropertyDescriptor(UIBorder.WIDGET_ID, "WidgetId");
		widgetProp.setCategory("基本");
		al.add(widgetProp);

		TextPropertyDescriptor widthProp = new TextPropertyDescriptor(UIBorder.WIDTH, "width");
		widthProp.setCategory("基本");
		al.add(widthProp);
		
		TextPropertyDescriptor leftWidthProp = new TextPropertyDescriptor(UIBorder.LEFTWIDTH, "leftWidth");
		leftWidthProp.setCategory("基本");
		al.add(leftWidthProp);
		
		TextPropertyDescriptor rightWidthProp = new TextPropertyDescriptor(UIBorder.RIGHTWIDTH, "rightWidth");
		rightWidthProp.setCategory("基本");
		al.add(rightWidthProp);
		
		TextPropertyDescriptor topWidthProp = new TextPropertyDescriptor(UIBorder.TOPWIDTH, "topWidth");
		topWidthProp.setCategory("基本");
		al.add(topWidthProp);
		
		TextPropertyDescriptor bottomWidthProp = new TextPropertyDescriptor(UIBorder.BOTTOMWIDTH, "bottomWidth");
		bottomWidthProp.setCategory("基本");
		al.add(bottomWidthProp);
		
		
		TextPropertyDescriptor colorProp = new TextPropertyDescriptor(UIBorder.COLOR, "color");
		colorProp.setCategory("基本");
		al.add(colorProp);
		
		TextPropertyDescriptor leftcolorProp = new TextPropertyDescriptor(UIBorder.LEFTCOLOR, "leftcolor");
		leftcolorProp.setCategory("基本");
		al.add(leftcolorProp);
		
		TextPropertyDescriptor rightcolorProp = new TextPropertyDescriptor(UIBorder.RIGHTCOLOR, "rightcolor");
		rightcolorProp.setCategory("基本");
		al.add(rightcolorProp);
		
		TextPropertyDescriptor topcolorProp = new TextPropertyDescriptor(UIBorder.TOPCOLOR, "topcolor");
		topcolorProp.setCategory("基本");
		al.add(topcolorProp);
		
		TextPropertyDescriptor bottomcolorProp = new TextPropertyDescriptor(UIBorder.BOTTOMCOLOR, "bottomcolor");
		bottomcolorProp.setCategory("基本");
		al.add(bottomcolorProp);
		
		ComboBoxPropertyDescriptor showleftProp = new ComboBoxPropertyDescriptor(UIBorder.SHOWLEFT, "showLeft", new String[]{"是", "否"});
		showleftProp.setCategory("基本");
		al.add(showleftProp);
		
		ComboBoxPropertyDescriptor showRightProp = new ComboBoxPropertyDescriptor(UIBorder.SHOWRIGHT, "showRight", new String[]{"是", "否"});
		showRightProp.setCategory("基本");
		al.add(showRightProp);
		
		ComboBoxPropertyDescriptor showTopProp = new ComboBoxPropertyDescriptor(UIBorder.SHOWTOP, "showTop", new String[]{"是", "否"});
		showTopProp.setCategory("基本");
		al.add(showTopProp);
		
		ComboBoxPropertyDescriptor showBottomProp = new ComboBoxPropertyDescriptor(UIBorder.SHOWBOTTOM, "showBottom", new String[]{"是", "否"});
		showBottomProp.setCategory("基本");
		al.add(showBottomProp);
		
		TextPropertyDescriptor classNameProp = new TextPropertyDescriptor(UIBorder.CLASSNAME, "className");
		classNameProp.setCategory("基本");
		al.add(classNameProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}


	@Override
	protected String getName() {
		return UIConstant.DBORDER;
	}

}
