package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UIPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DPanel extends DLayout {

	public DPanel(Composite parent, int style, UIElement ele) {
		super(parent, style, ele);
	}

	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		
	
		TextPropertyDescriptor idProp = new TextPropertyDescriptor(UIPanel.ID, "ID");
		idProp.setCategory("基本");
		al.add(idProp);
	
//		TextPropertyDescriptor paddingLeftProp = new TextPropertyDescriptor(UIPanel.PADDINGLEFT, "paddingLeft");
//		paddingLeftProp.setCategory("基本");
//		al.add(paddingLeftProp);
//		
//		TextPropertyDescriptor paddingTopProp = new TextPropertyDescriptor(UIPanel.PADDINGTOP, "paddingTop");
//		paddingTopProp.setCategory("基本");
//		al.add(paddingTopProp);
//		
//		TextPropertyDescriptor paddingRightProp = new TextPropertyDescriptor(UIPanel.PADDINGRIGHT, "paddingRight");
//		paddingRightProp.setCategory("基本");
//		al.add(paddingRightProp);
//		
//		TextPropertyDescriptor paddingBottomProp = new TextPropertyDescriptor(UIPanel.PADDINGBOTTOM, "paddingBottom");
//		paddingBottomProp.setCategory("基本");
//		al.add(paddingBottomProp);
//		
//		TextPropertyDescriptor leftProp = new TextPropertyDescriptor(UIPanel.LEFT, "左边距");
//		leftProp.setCategory("基本");
//		al.add(leftProp);
//		
//		TextPropertyDescriptor topProp = new TextPropertyDescriptor(UIPanel.TOP, "距离顶部距离");
//		topProp.setCategory("基本");
//		al.add(topProp);
//		
//		TextPropertyDescriptor widthProp = new TextPropertyDescriptor(UIPanel.WIDTH, "宽度");
//		widthProp.setCategory("基本");
//		al.add(widthProp);
//		
//		TextPropertyDescriptor heightProp = new TextPropertyDescriptor(UIPanel.HEIGTH, "高度");
//		heightProp.setCategory("基本");
//		al.add(heightProp);
//		
//		TextPropertyDescriptor positinProp = new TextPropertyDescriptor(UIPanel.POSITION, "位置");
//		positinProp.setCategory("基本");
//		al.add(positinProp);
		
		TextPropertyDescriptor calssProp = new TextPropertyDescriptor(UIPanel.CLASSNAME, "相关类");
		calssProp.setCategory("基本");
		al.add(calssProp);
		
//		ComboBoxPropertyDescriptor transParent = new ComboBoxPropertyDescriptor(UIPanel.TRANSPARENT, "是否透明", new String[]{"是", "否"});
//		transParent.setCategory("基本");
//		al.add(transParent);
//		
//		ComboBoxPropertyDescriptor displayProp = new ComboBoxPropertyDescriptor(UIPanel.DISPLAY, "display", new String[]{"是", "否"});
//		displayProp.setCategory("基本");
//		al.add(displayProp);
//		
//		ComboBoxPropertyDescriptor visibilityProp = new ComboBoxPropertyDescriptor(UIPanel.VISIBILITY, "是否可见", new String[]{"是", "否"});
//		visibilityProp.setCategory("基本");
//		al.add(visibilityProp);
//		
//		
//		ComboBoxPropertyDescriptor scrollProp = new ComboBoxPropertyDescriptor(UIPanel.SCROLL, "scroll", new String[]{"是", "否"});
//		scrollProp.setCategory("基本");
//		al.add(scrollProp);
		
//		ComboBoxPropertyDescriptor scrollProp = new ComboBoxPropertyDescriptor(UIPanel.SCROLL, "scroll", new String[]{"是", "否"});
//		scrollProp.setCategory("基本");
//		al.add(scrollProp);
		
//		ComboBoxPropertyDescriptor roundRectProp = new ComboBoxPropertyDescriptor(UIPanel.ROUND_RECT, "roundRect", new String[]{"是", "否"});
//		roundRectProp.setCategory("基本");
//		al.add(roundRectProp);
//		
//		
//		ComboBoxPropertyDescriptor withBorderProp = new ComboBoxPropertyDescriptor(UIPanel.WITH_BORDER, "是否有边框", new String[]{"是", "否"});
//		withBorderProp.setCategory("基本");
//		al.add(withBorderProp);
//	
//		TextPropertyDescriptor raiusProp = new TextPropertyDescriptor(UIPanel.RADIUS, "圆角半径");
//		raiusProp.setCategory("基本");
//		al.add(raiusProp);
//		
//		TextPropertyDescriptor borderWidthProp = new TextPropertyDescriptor(UIPanel.BORDER_WIDTH, "边框宽度");
//		borderWidthProp.setCategory("基本");
//		al.add(borderWidthProp);
//		
//		TextPropertyDescriptor borderColorProp = new TextPropertyDescriptor(UIPanel.BORDER_COLOR, "边框颜色");
//		borderColorProp.setCategory("基本");
//		al.add(borderColorProp);
//	
//		TextPropertyDescriptor backgroupColor = new TextPropertyDescriptor(UIPanel.BACKGROUNDCOLOR, "背景色");
//		backgroupColor.setCategory("基本");
//		al.add(backgroupColor);
		
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	protected void initialize() {
		
//		this.setLayout(new GridLayout(1, false));
//		this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
//		UIPanel uiPanel = (UIPanel) getUiElement();
//		List<UILayoutPanel> list = uiPanel.getPanelList();
//		DPanelPanel dPanel = new DPanelPanel(this, SWT.NONE, list.get(0));
//		GridData gd = new GridData();
//		gd.grabExcessVerticalSpace = true;
//		gd.verticalAlignment = GridData.FILL;
//		
//		UIPanelPanel panel = (UIPanelPanel) list.get(0);
////		String width = panel.getWidth();
////		if(width == null || width.equals("100%") || Integer.parseInt(width) == 0 ){
//			gd.grabExcessHorizontalSpace = true;
//			gd.horizontalAlignment = GridData.FILL;
////		}
////		else{
////			gd.widthHint = Integer.parseInt(width);
////		}
////		String heigth = panel.getHeight();
////		if(heigth == null || heigth.equals("100%") || Integer.parseInt(heigth) == 0 ){
//			gd.grabExcessHorizontalSpace = true;
////			gd.horizontalAlignment = GridData.FILL;
////		}
////		else{
////			gd.heightHint = Integer.parseInt(heigth);
////		}
//		dPanel.setLayoutData(gd);
		
		this.setLayout(new FillLayout());
		UIPanel uiPanel = (UIPanel) getUiElement();
		List<UILayoutPanel> list = uiPanel.getPanelList();
		new DPanelPanel(this, SWT.NONE, list.get(0));
	}

	
	protected void removeChild(DLayoutPanel panel) {
		DLayoutPanel parentPanel = (DLayoutPanel) this.getDParent();
		UILayoutPanel uiParentPanel = (UILayoutPanel) parentPanel.getUiElement();
		uiParentPanel.setElement(null);
		parentPanel.initUI();
	}


	@Override
	protected String getName() {
		return UIConstant.DPANEL;
	}

}
