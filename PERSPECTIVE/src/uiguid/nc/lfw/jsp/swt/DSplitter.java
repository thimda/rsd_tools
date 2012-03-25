package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.NumberPropertyDescriptor;
import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UISplitter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DSplitter extends DLayout {
	SashForm form = null;
	public DSplitter(Composite parent, int style, UIElement ele) {
		super(parent, style, ele);
	}

	
	protected void initialize() {
		this.setLayout(new FillLayout());
		UISplitter ele = (UISplitter) getUiElement();
		int orientation = SWT.HORIZONTAL;
		if(ele.getOrientation() == 0)
			orientation = SWT.VERTICAL;
		form = new SashForm(this, orientation | SWT.FLAT);
		form.setLayout(new FillLayout());
		
		Integer boundMode = ele.getBoundMode();
		new DSplitterOne(form, SWT.NONE, ele.getSplitterOne());
		new DSplitterTwo(form, SWT.NONE, ele.getSplitterTwo());
		if(boundMode.equals(0)){
			String divideSizeStr = ele.getDivideSize();
			int divideSize = Integer.parseInt(divideSizeStr);
			form.setWeights(new int[]{divideSize, 100 - divideSize});
		}
		else{
			
		}
	}

	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor(UISplitter.ID, "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
		ComboBoxPropertyDescriptor bmProp = new ComboBoxPropertyDescriptor(UISplitter.BOUNDMODE, "分隔方式", new String[]{"%", "px"});
		bmProp.setCategory("基本");
		al.add(bmProp);
		
		NumberPropertyDescriptor sizeProp = new NumberPropertyDescriptor(UISplitter.DIVIDE_SIZE, "分隔比例");
		sizeProp.setCategory("基本");
		al.add(sizeProp);
		
		ComboBoxPropertyDescriptor inverseProp = new ComboBoxPropertyDescriptor(UISplitter.INVERSE, "计算依据", new String[]{"反向", "正向"});
		inverseProp.setCategory("基本");
		al.add(inverseProp);
		
//		NumberPropertyDescriptor divideProp = new NumberPropertyDescriptor(UISplitter.SPLITERWIDTH, "分隔条宽度");
//		divideProp.setCategory("基本");
//		al.add(divideProp);
		
		ComboBoxPropertyDescriptor oriProp = new ComboBoxPropertyDescriptor(UISplitter.ORIENTATION, "分隔方向", new String[]{"纵向", "横向"});
		oriProp.setCategory("基本");
		al.add(oriProp);
		
		ComboBoxPropertyDescriptor oneProp = new ComboBoxPropertyDescriptor(UISplitter.ONETOUCH, "一键隐藏", new String[]{"否", "是"});
		oneProp.setCategory("基本");
		al.add(oneProp);
		
		ComboBoxPropertyDescriptor direcProp = new ComboBoxPropertyDescriptor(UISplitter.HIDEDIRECTION, "隐藏方向", new String[]{"反向", "正向"});
		direcProp.setCategory("基本");
		al.add(direcProp);
		
//		ComboBoxPropertyDescriptor hideProp = new ComboBoxPropertyDescriptor(UISplitter.HIDEBAR, "隐藏分割条", new String[]{"否", "是"});
//		hideProp.setCategory("基本");
//		al.add(hideProp);
		
		//map.put(DIVIDE_SIZE, "0.5");
		//map.put(ORIENTATION, "h");
		//map.put(BOUNDMODE, "0"); //% 0, px 1
		//map.put(ONETOUCH, "0");
		//map.put(SPLITERWIDTH, "4");
		//map.put(INVERSE, "0");
		//map.put(HIDEBAR, "1");
		//map.put(HIDEDIRECTION, "1"); //1 纵向 0横向
		return al.toArray(new IPropertyDescriptor[0]);
	}


	
	protected void removeChild(DLayoutPanel panel) {
		DLayoutPanel parentPanel = (DLayoutPanel) this.getDParent();
		UILayoutPanel uiParentPanel = (UILayoutPanel) parentPanel.getUiElement();
		uiParentPanel.setElement(null);
		parentPanel.initUI();
	}


	@Override
	protected String getName() {
		return UIConstant.DSPLIT;
	}

}
