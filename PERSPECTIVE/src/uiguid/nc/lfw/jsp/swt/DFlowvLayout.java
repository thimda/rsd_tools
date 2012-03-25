package nc.lfw.jsp.swt;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.uap.lfw.jsp.uimeta.UIConstant;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIFlowvLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowvPanel;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DFlowvLayout extends DLayout {

	public DFlowvLayout(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		ScrolledComposite scrolledComposite = new ScrolledComposite(getParent(), SWT.V_SCROLL|SWT.H_SCROLL);
		scrolledComposite.setMinWidth(200);
		scrolledComposite.setMinHeight(300);
		scrolledComposite.setExpandHorizontal(true);
		this.setParent(scrolledComposite);
		scrolledComposite.setContent(this);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);
		this.layout();
//		setDFlowvLayout(this);
	}

	
//	public void setDFlowvLayout(DFlowvLayout layout){
//		layout = layout;
//	}
	
	public DFlowvLayout getDFlowLayout(){
		return this;
	}
	
	protected Composite getComposite() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor(UIFlowvLayout.ID, "ID");
		idProp.setCategory("基本");
		TextPropertyDescriptor widgetProp = new TextPropertyDescriptor(UIFlowvLayout.WIDGET_ID, "Widget");
		widgetProp.setCategory("基本");
//		ComboBoxPropertyDescriptor oneProp = new ComboBoxPropertyDescriptor(UIFlowvLayout.AUTO_FILL, "是否自动填充", new String[]{"是","否"});
//		oneProp.setCategory("基本");
		al.add(idProp);
		al.add(widgetProp);
//		al.add(oneProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	protected void initialize() {
//		UIFlowvLayout flowvLayout = (UIFlowvLayout) getUiElement();
		//if(flowvLayout.getPanelList().size() > 6){
		UIFlowvLayout ele = (UIFlowvLayout) getUiElement();
		List<UILayoutPanel> list = ele.getPanelList();
		Iterator<UILayoutPanel> it = list.iterator();
		while(it.hasNext()){
			UIFlowvPanel pele = (UIFlowvPanel) it.next();
			DFlowvPanel flowvPanel = new DFlowvPanel(this, SWT.NONE, pele);
			GridData gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalAlignment = GridData.FILL;
			
			String height = pele.getHeight();
			height = height == null || height.equals("") || height.equals("0") ? null : height;
			if(height == null || Integer.parseInt(height) == 0){
				gd.grabExcessVerticalSpace = true;
				gd.verticalAlignment = GridData.FILL;
			}
			else{
				gd.heightHint = Integer.parseInt(height);
			}
			flowvPanel.setLayoutData(gd);
		}
		
		Display.getCurrent().asyncExec(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				while(getParent().getClientArea() == null){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//modify
				UIFlowvLayout flowvLayout = (UIFlowvLayout) getUiElement();
				int size = flowvLayout.getPanelList().size();
				int height = 100*size;
				int parentheight =getParent().getClientArea().height;
				int width = 1000;
				boolean isTrue = false;
				List<UILayoutPanel> flowvLayoutList = flowvLayout.getPanelList();
				if(flowvLayoutList != null && flowvLayoutList.size() > 0){
					for (int i = 0; i < flowvLayoutList.size(); i++) {
						UILayoutPanel panel = flowvLayoutList.get(i);
						if(panel.getAttribute(UIFlowvPanel.HEIGHT) != null && !"".equals(panel.getAttribute(UIFlowvPanel.HEIGHT))){
							isTrue = true;
							break;
						}
					}
				}
				
				if(isTrue){
					height = 0;
					for (int i = 0; i < flowvLayoutList.size(); i++) {
						UILayoutPanel panel = flowvLayoutList.get(i);
						if(panel.getAttribute(UIFlowvPanel.HEIGHT) != null){
							height += Integer.valueOf(panel.getAttribute(UIFlowvPanel.HEIGHT).toString());
						}
						else {
							height += 100;
						}
					}
				}
				if(height < parentheight)
					height = parentheight;
				getDFlowLayout().setSize(width, height);
				}
			
		});
	}

	
	protected void removeChild(DLayoutPanel panel) {
		UILayoutPanel uiPanel = (UILayoutPanel) panel.getUiElement();
		UIFlowvLayout uitab = (UIFlowvLayout) getUiElement();
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
		return UIConstant.DFLOWV;
	}

}
