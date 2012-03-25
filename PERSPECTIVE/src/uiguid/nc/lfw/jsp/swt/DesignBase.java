package nc.lfw.jsp.swt;

import java.io.Serializable;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.jsp.editor.GuideEditor;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIComponent;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMenubarComp;
import nc.uap.lfw.jsp.uimeta.UITabItem;
import nc.uap.lfw.jsp.uimeta.UIWidget;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertySheet;

public abstract class DesignBase extends Canvas implements IPropertySource {
	private boolean selectedSign = false;
	private UIElement uiElement;
	public DesignBase(Composite parent, int style, UIElement element) {
		super(parent, style);
		this.uiElement = element;
	}
	
	protected void initUI() {
		Control[] ctrls = getChildren();
		for (int i = 0; i < ctrls.length; i++) {
			try{
				ctrls[i].dispose();
			}
			catch(Exception e){
				
			}
		}
		initialize();
		addControlListener();
		this.layout(false);
	}

	protected void addControlListener() {
		getComposite().addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
				//有些区别应在各个子中实现，方便问题，写在这里
				Object t = e.getSource();
				DesignBase db = null;
				if(t instanceof DesignBase)
					db = (DesignBase) t;
				else{
					if(t instanceof Control){
						db = (DesignBase) ((Control)t).getData();
					}
					else{
						db = (DesignBase) ((Composite)t).getData();
					}
				}
//				if(db instanceof DTabComp){
//					if(((DTabComp)db).isChildSelSign())
//						return;
////				}
				if(db.isNest()){
					DWidget dw = (DWidget) getDesignPanel(db);
					setCurrentSelection(dw);
				}
				else{
					setCurrentSelection(db);
				}
			}

			public void focusLost(FocusEvent e) {
				Object t = e.getSource();
				DesignBase db = null;
				if(t instanceof DesignBase)
					db = (DesignBase) t;
				else{
					if(t instanceof Control){
						db = (DesignBase) ((Control)t).getData();
					}
					else{
						db = (DesignBase) ((Composite)t).getData();
					}
				}
				db.setSelectedSign(false);
			}
			
		});
	}

	protected abstract void initialize();
	protected abstract Control getComposite();
	
	public UIElement getUiElement() {
		return uiElement;
	}
	public void setUiElement(UIElement uiElement) {
		this.uiElement = uiElement;
	}
	
	public void setCurrentSelection(DesignBase current) {
		current.setSelectedSign(true);
//		if(current instanceof DesignPanel)
//			return;
		DesignPanel panel = getDesignPanel(current);
		panel.updateNavigate(current);
	}
	
	public DesignPanel getDesignPanel(DesignBase current) {
		Composite parent = current.getParent();
		while(parent != null){
			if(parent instanceof DesignPanel){
				DesignPanel panel = (DesignPanel) parent;
				return panel;
			}
			parent = parent.getParent();
		}
		return null;
	}
	
	public DesignBase getDParent(){
		Composite parent = this.getParent();
		while(parent != null){
			if(parent instanceof DesignBase){
				DesignBase panel = (DesignBase) parent;
				return panel;
			}
			//TODO 时间关系，子类重载
			else if(parent instanceof TabFolder){
				TabFolder tab = (TabFolder) parent;
				if(this instanceof DTabItem)
					return (DesignBase) tab.getData();
				TabItem[] items = tab.getItems();
				for (int i = 0; i < items.length; i++) {
					DesignBase p = (DesignBase) items[i].getData();
					UITabItem uiitem = (UITabItem) p.getUiElement();
					if(uiitem != null && uiitem.getElement() != null){
						if(uiitem.getElement().equals(this.getUiElement()))
							return p;
					}
				}
			}
			parent = parent.getParent();
		}
		return null;
	}
	
	public void setSelectedSign(boolean select) {
		this.selectedSign = select;
		if(!select)
			return;
//		getDesignPanel(p)
//		getDesignPanel(this).update();
		PropertySheet sheet = null;
		IViewPart[] views = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViews();
		for (int i = 0; i < views.length; i++) {
			if(views[i] instanceof PropertySheet){
				sheet = (PropertySheet) views[i];
			}
		}
		if(sheet != null){
			StructuredSelection selection = new StructuredSelection(this);
			sheet.selectionChanged(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(), selection);
		}
		this.layout(false);
	}

	public boolean isSelectedSign() {
		return selectedSign;
	}
	
	public WebElement getWebElementByUIElement(UIElement ele){
		DesignPanel design = getDesignPanel(this);
		if(design instanceof DWidget){
			LfwWidget widget = (LfwWidget) ((DWidget)design).getWidget();
			if(ele instanceof UIMenubarComp){
				if(ele.getAttribute("id").equals("bodyMenubar")){
					if(widget != null){
						WebElement bodyMenubar =  widget.getViewMenus().getMenuBar(((UIMenubarComp) ele).getId());
						if(bodyMenubar != null)
							return bodyMenubar;
						else{
							PageMeta pm = LFWPersTool.getCurrentPageMeta();
							return pm.getViewMenus().getMenuBar(((UIMenubarComp) ele).getId());
						}
					}
				}
				if(widget != null){
					return widget.getViewMenus().getMenuBar(((UIMenubarComp) ele).getId());
				}else{
					PageMeta pm = LFWPersTool.getCurrentPageMeta();
					return pm.getViewMenus().getMenuBar(((UIMenubarComp) ele).getId());
				}
			}
			else
				return widget.getViewComponents().getComponent(((UIComponent)ele).getId());
		}
		else{
			PageMeta pm = (PageMeta) ((DesignPanel)design).getPagemeta();
			if(ele instanceof UIMenubarComp){
				return pm.getViewMenus().getMenuBar(((UIMenubarComp) ele).getId());
			}
			else{
				LfwWidget widget = null;
				if(ele instanceof UIWidget){
					widget =  pm.getWidget(((UIWidget) ele).getId());
					return widget;
				}
				else
					widget = pm.getWidget(((UIComponent)ele).getWidgetId());
				return widget.getViewComponents().getComponent(((UIComponent)ele).getId());
			}
		}
	}
	public Object getPropertyValue(Object id) {
		Object obj = getUiElement().getAttribute((String) id);
		if(obj == null)
			return "";
		return obj;
	}
	public boolean isPropertySet(Object id) {
		return getUiElement().getAttribute((String) id) != null;
	}
	public void resetPropertyValue(Object id) {
	}
	public void setPropertyValue(Object id, Object value) {
		getUiElement().setAttribute((String) id, (Serializable) value);
		GuideEditor editor = (GuideEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		editor.setDirty();
		updateProperty((String)id, getUiElement().getAttribute((String) id), (Serializable)value);
	}
	
	protected void checkWidget () {
		//super.checkWidget();
	}
	
	
	public boolean isNest() {
		DesignPanel panel = null;
		if(this instanceof DWidget)
			panel = (DesignPanel) this;
		else
			panel = getDesignPanel(this);
		if(panel instanceof DWidget){
			return getDesignPanel(panel) != null;
		}
		return false;
	}
	
	protected void updateProperty(String id, Serializable oldValue, Serializable newValue){
		initUI();
		this.forceFocus();
		this.layout(false);
	}
	
	protected abstract String getName();

}
