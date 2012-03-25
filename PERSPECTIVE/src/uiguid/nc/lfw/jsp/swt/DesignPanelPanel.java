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
			ComboBoxPropertyDescriptor jqueryProp = new ComboBoxPropertyDescriptor(UIMeta.ISJQUERY, "�Ƿ�����JQuery", new String[]{"��", "��"});
			jqueryProp.setCategory("����");
			al.add(jqueryProp);
			
//			ComboBoxPropertyDescriptor chartProp = new ComboBoxPropertyDescriptor(UIMeta.ISCHART, "�Ƿ�����Chart", new String[]{"��", "��"});
//		
//			chartProp.setCategory("����");
//			al.add(chartProp);
			
			TextPropertyDescriptor includeJSProp = new TextPropertyDescriptor("includejs", "����js����");
			includeJSProp.setCategory("����");
			al.add(includeJSProp);
			
			TextPropertyDescriptor includeCssProp = new TextPropertyDescriptor("includecss", "����css����");
			includeCssProp.setCategory("����");
			al.add(includeCssProp);
			
//			ComboBoxPropertyDescriptor jsQueryProp = new ComboBoxPropertyDescriptor(UIMeta.JSEDITOR, "�Ƿ�����JsEditor", new String[]{"��", "��"});
//			jsQueryProp.setCategory("����");
//			al.add(jsQueryProp);
//			
//			ComboBoxPropertyDescriptor jsExcelProp = new ComboBoxPropertyDescriptor(UIMeta.ISEXCEL, "�Ƿ�����Excel", new String[]{"��", "��"});
//			jsExcelProp.setCategory("����");
//			al.add(jsExcelProp);
		}
		return al.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	protected String getName() {
		return "�����";
	}
}
