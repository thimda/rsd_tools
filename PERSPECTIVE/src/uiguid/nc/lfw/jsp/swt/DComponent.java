package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.jsp.editor.GuideEditor;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public abstract class DComponent extends DesignBase {
	private WebElement webElement;
	public DComponent(Composite parent, int style, UIElement element, WebElement webElement) {
		super(parent, style, element);
		this.webElement = webElement;
	}
	public WebElement getWebElement() {
		return webElement;
	}
	public void setWebElement(WebElement webElement) {
		this.webElement = webElement;
	}
	public Object getEditableValue() {
		return null;
	}
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
		TextPropertyDescriptor widgetProp = new TextPropertyDescriptor("widgetId", "所属片段");
		widgetProp.setCategory("基本");
		al.add(widgetProp);
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	protected void initUI() {
		super.initUI();
		Control comp = getComposite();
		Menu popMenu = new Menu(comp);
		
		MenuItem item = new MenuItem(popMenu, SWT.NONE);
		item.setData(this);
		item.setText("删除");
		item.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				GuideEditor editor = (GuideEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				editor.setDirty();
				MenuItem item = (MenuItem) e.getSource();
				DesignBase db = (DesignBase) item.getData();
				if(db.isNest()){
					db = db.getDesignPanel(db);
				}
				DLayoutPanel parent = (DLayoutPanel) db.getDParent();
			
				UILayoutPanel pele = (UILayoutPanel) (parent).getUiElement();
				pele.setElement(null);
				parent.initUI();
			}
			
		});
		comp.setMenu(popMenu);
		//popMenu.setVisible(true);
	}
	
}
