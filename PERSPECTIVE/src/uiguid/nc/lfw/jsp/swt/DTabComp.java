package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UITabComp;
import nc.uap.lfw.jsp.uimeta.UITabItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DTabComp extends DLayout {
	private TabFolder tab = null;
	public DTabComp(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}

	protected void initialize() {
		UITabComp tabEle = (UITabComp) getUiElement();
		tab = new TabFolder(this, SWT.NONE);
		tab.setData(this);

//		UITabRightPanelPanel uiPanel = tabEle.getRightPanel();
//		if(tabEle.getRightPanel() == null){
//			uiPanel = new UITabRightPanelPanel();
//			uiPanel.setWidth("60");
//			tabEle.setRightPanel(uiPanel);
//		}
		
//		DTabRightPanelPanel panel = new DTabRightPanelPanel(tab, SWT.NONE, uiPanel);
		int width = 60;
//		if(uiPanel.getWidth() != null){
//			width = Integer.valueOf(uiPanel.getWidth());
//		}
//		panel.setBounds(300, 0, width, 22);

		List<UILayoutPanel> itemList = tabEle.getPanelList();
		Iterator<UILayoutPanel> it = itemList.iterator();
		while(it.hasNext()){
			UILayoutPanel uiLayout = it.next();
			if(uiLayout instanceof UITabItem){
				UITabItem item = (UITabItem) uiLayout;
				new DTabItem(tab, SWT.NONE, item);
			}
		}
		
		tab.addMouseListener(new MouseListener(){

			
			public void mouseDoubleClick(MouseEvent e) {
				
			}

			
			public void mouseDown(MouseEvent e) {
				//System.out.println(e.);
			}

			
			public void mouseUp(MouseEvent e) {
			}
			
		});
	}

	
	protected Composite getComposite() {
		return tab;
	}
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
		List<UILayoutPanel> panelList = ((UITabComp)getUiElement()).getPanelList();
		List<UILayoutPanel> itemPanelList = new ArrayList<UILayoutPanel>();
		for (int i = 0; i < panelList.size(); i++) {
			UILayoutPanel panel = panelList.get(i) ;
			if(panel instanceof UITabItem)
				itemPanelList.add(panel);
		}
		String[] labels = new String[itemPanelList.size()];
		for (int i = 0; i < labels.length; i++) {
			//labels[i] = ((UITabItem)panelList.get(i)).getId();
			labels[i] = String.valueOf(i);
		}
		ObjectComboPropertyDescriptor currProp = new ObjectComboPropertyDescriptor(UITabComp.CURRENT_ITEM, "默认页签", labels);
		currProp.setCategory("基本");
		al.add(currProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}

	
	protected void removeChild(DLayoutPanel panel) {
		UILayoutPanel uiPanel = (UILayoutPanel) panel.getUiElement();
		UITabComp uitab = (UITabComp) getUiElement();
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
		return "页签";
	}

}
