package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMeta;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DesignPanelPanel extends DLayoutPanel {
	public DesignPanelPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
	}

	
	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE);
		bt.setText("Widget");
		return bt;
	}
	
	protected Composite getComposite() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		if(!(getComposite() instanceof DWidgetPanel)){
			ComboBoxPropertyDescriptor jqueryProp = new ComboBoxPropertyDescriptor(UIMeta.ISJQUERY, "是否引入JQuery", new String[]{"否", "是"});
			jqueryProp.setCategory("基本");
			al.add(jqueryProp);
			
//			ComboBoxPropertyDescriptor chartProp = new ComboBoxPropertyDescriptor(UIMeta.ISCHART, "是否引入Chart", new String[]{"否", "是"});
//		
//			chartProp.setCategory("基本");
//			al.add(chartProp);
			
			TextPropertyDescriptor includeJSProp = new TextPropertyDescriptor("includejs", "包含js代码");
			includeJSProp.setCategory("基本");
			al.add(includeJSProp);
			
			TextPropertyDescriptor includeCssProp = new TextPropertyDescriptor("includecss", "包含css代码");
			includeCssProp.setCategory("基本");
			al.add(includeCssProp);
			
//			ComboBoxPropertyDescriptor jsQueryProp = new ComboBoxPropertyDescriptor(UIMeta.JSEDITOR, "是否引入JsEditor", new String[]{"否", "是"});
//			jsQueryProp.setCategory("基本");
//			al.add(jsQueryProp);
//			
//			ComboBoxPropertyDescriptor jsExcelProp = new ComboBoxPropertyDescriptor(UIMeta.ISEXCEL, "是否引入Excel", new String[]{"否", "是"});
//			jsExcelProp.setCategory("基本");
//			al.add(jsExcelProp);
		}
		return al.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	protected String getName() {
		return "主面板";
	}
}
