package nc.lfw.jsp.swt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.NumberPropertyDescriptor;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIFlowvPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DFlowvPanel extends DLayoutPanel {

	public DFlowvPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}

	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE);
		bt.setText("Flowv1");
		return bt;
	}

	protected Composite getComposite() {
		return this;
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		
		NumberPropertyDescriptor heightProp = new NumberPropertyDescriptor(UIFlowvPanel.HEIGHT, "高度");
		heightProp.setCategory("基本");
		TextPropertyDescriptor anchorProp = new TextPropertyDescriptor(UIFlowvPanel.ANCHOR, "锚点");
		anchorProp.setCategory("基本");
		al.add(heightProp);
		al.add(anchorProp);
	
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	@Override
	public void setPropertyValue(Object id, Object value) {
		if(id.equals("height")){
			if(value != null && value.equals("0"))
				value = null;
		}
		super.setPropertyValue(id, value);
	}

	protected void updateProperty(String id, Serializable oldValue, Serializable newValue){
//		if(getParent() != null){
//			DFlowvLayout dFlowvLayout = (DFlowvLayout) getParent();
//			if(newValue != null && newValue.equals("0"))
//				newValue = "";
//			dFlowvLayout.updateProperty(id, oldValue, newValue);
//			UIFlowvLayout uiflowLayout = (UIFlowvLayout) dFlowvLayout.getUiElement();
//			List<UILayoutPanel> flowvLayoutList = uiflowLayout.getPanelList();
//			int height = 0;
//			if(flowvLayoutList != null && flowvLayoutList.size() > 0){
//				for (int i = 0; i < flowvLayoutList.size(); i++) {
//					UILayoutPanel panel = flowvLayoutList.get(i);
//					if(panel.getAttribute(UIFlowvPanel.HEIGHT) != null && !panel.getAttribute(UIFlowvPanel.HEIGHT).equals("")){
//						height += Integer.valueOf(panel.getAttribute(UIFlowvPanel.HEIGHT).toString());
//					}
//					else {
//						//panel.setAttribute(UIFlowvPanel.HEIGHT, "100");
//						height += 100;
//						}
//					}
//			}
//			int parentheight = dFlowvLayout.getClientArea().height;
//			if(height < parentheight)
//				height = parentheight;
//			dFlowvLayout.setSize(1000 , height);
//		}
//		DFlowvLayout dFlowvLayout = (DFlowvLayout) getParent();
//		if(dFlowvLayout != null){
//			dFlowvLayout.initUI();
//			dFlowvLayout.getChildren()[0].forceFocus();
//			dFlowvLayout.layout(false);
//		}
		
		if(getParent() != null)
			((DFlowvLayout)getParent()).updateProperty(id, oldValue, newValue);
	}


	@Override
	protected String getName() {
		return "纵向面板";
	}
}
