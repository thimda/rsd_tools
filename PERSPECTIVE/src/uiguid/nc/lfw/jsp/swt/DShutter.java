package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.lfw.widget.shutter.Blind;
import nc.lfw.widget.shutter.ISlatLabelProvider;
import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UIShutter;
import nc.uap.lfw.jsp.uimeta.UIShutterItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DShutter extends DLayout {

	public DShutter(Composite parent, int style, UIElement ele) {
		super(parent, style, ele);
		this.setLayout(new FillLayout());
	}

	@Override
	protected void removeChild(DLayoutPanel panel) {
		UILayoutPanel uiPanel = (UILayoutPanel) panel.getUiElement();
		UIShutter uishutter = (UIShutter) getUiElement();
		uishutter.getPanelList().remove(uiPanel);
		if(uishutter.getPanelList().size() == 0){
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
		return "百叶窗";
	}

	@Override
	protected void initialize() {
		UIShutter uishutter = (UIShutter) getUiElement();
		Blind b = new Blind(this, SWT.BORDER);
		ISlatLabelProvider lp = new ISlatLabelProvider() {
			public String getText(Control slatControl) {
				if(slatControl instanceof DShutterItem){
					DShutterItem item = (DShutterItem) slatControl;
					UIShutterItem uiItem = (UIShutterItem) item.getUiElement();
					return uiItem.getText();
				}
				else{
					return "";
				}
			}
			
		};
		b.setLabelProvider(lp);
		if(uishutter.getPanelList().size() == 0){
//			UIBlankPanel ele = new UIBlankPanel();
//			ele.setAttribute("id", "blankpanel");
//			DBlankPanel panel = new DBlankPanel(b, SWT.NONE, ele);
//			b.addSlat(panel);
		}
		else{
			Iterator<UILayoutPanel> it = uishutter.getPanelList().iterator();
			while(it.hasNext()){
				UILayoutPanel panel = it.next();
				DShutterItem item = new DShutterItem(b, SWT.NONE, panel);
				b.addSlat(item);
			}
		}
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		
		TextPropertyDescriptor idProp = new TextPropertyDescriptor(UIShutter.ID, "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
		TextPropertyDescriptor classNameProp = new TextPropertyDescriptor(UIShutter.CLASSNAME, "实现类");
		classNameProp.setCategory("基本");
		al.add(classNameProp);
		
		UIShutter uishutter = (UIShutter) getUiElement();
		List<UILayoutPanel> itemPanelList = uishutter.getPanelList();
		String[] labels = new String[itemPanelList.size()];
		for (int i = 0; i < labels.length; i++) {
			//labels[i] = ((UIShutterItem)itemPanelList.get(i)).getId();
			labels[i] = String.valueOf(i);
		}
		ObjectComboPropertyDescriptor currProp = new ObjectComboPropertyDescriptor(UIShutter.CURRENT_ITEM, "默认项", labels);
		currProp.setCategory("基本");
		al.add(currProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}

}
