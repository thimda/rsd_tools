package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.jsp.editor.GuideEditor;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UITabItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DTabItem extends DLayoutPanel{

	private int style;
	private TabItem item;
	public DTabItem(TabFolder parent, int style, UIElement element) {
		super(parent, style, element);
		this.style = style;
	}

	protected void initialize() {
		TabFolder folder = (TabFolder) getParent();
		TabItem[] items = folder.getItems();
		UITabItem uiItem = (UITabItem) getUiElement();
		
		TabItem currItem = null;
		if(items != null){
			for (int i = 0; i < items.length; i++) {
				TabItem tItem = items[i];
				if(tItem.getData("ID").equals(uiItem.getId()))
					currItem = tItem;
			}
		}
		if(currItem != null){
			item = currItem;
		}
		else{
			item = new TabItem((TabFolder) this.getParent(), style);
			item.setData("ID", uiItem.getId());
			item.setData(this);
		}
		item.setText(uiItem.getText());
		
		super.initialize();
	}
	
	public TabItem getTabItem() {
		return item;
	}

	protected void clearSign() {
		
	}
	
	
	protected void afterProcessChild(Control comp) {
		if(comp instanceof DFlowvLayout){
			comp.setParent(this.getParent());
		}
		item.setControl(comp);
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
//				if(db instanceof DTabItem){
//					DTabComp dtab = (DTabComp) db.getDParent();
//					dtab.removeChild((DTabItem)db);
//				}
//				else{
					DLayout parent = (DLayout) db.getDParent();
					parent.removeChild((DLayoutPanel) db);
//				}
			}
			
		});
		
		MenuItem nextItem = new MenuItem(popMenu, SWT.NONE);
		nextItem.setData(this);
		nextItem.setText("发布到容器");
		nextItem.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				GuideEditor editor = (GuideEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				editor.setDirty();
			}
			
		});
		comp.setMenu(popMenu);
	}	

	protected Control createSign() {
		Button bt = new Button(item.getParent(), SWT.NONE);
		bt.setData(this);
		
		bt.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				
			}
			public void mouseDown(MouseEvent e) {
				Button bt = (Button) e.getSource();
				DesignBase db = (DesignBase) bt.getData();
				if(db.isNest()){
					DWidget dw = (DWidget) getDesignPanel(db);
					dw.setSelectedSign(true);
					setCurrentSelection(dw);
				}
				else{
					//TabFolder tab = (TabFolder) db.getParent();
					//DTabComp dtab = (DTabComp) tab.getData();
					//dtab.setChildSelSign(true);
					//db.setSelectedSign(true);
					setCurrentSelection(db);
				}
			}

			public void mouseUp(MouseEvent e) {
				
			}
			
		});
		return bt;
	}
	
//	protected void afterProcessChild(Control comp) {
//		item.setControl(comp);
//		super.afterProcessChild(comp);
//	}

	
	protected Composite getTrueParent() {
		return this.getParent();
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("基本");
		al.add(idProp);
		
		TextPropertyDescriptor textProp = new TextPropertyDescriptor("text", "显示值");
		textProp.setCategory("基本");
		al.add(textProp);
		
//		DesignPanel dPanel = getDesignPanel(this);
//		if(dPanel instanceof DWidget)
//			dPanel = getDesignPanel(dPanel);
//		PageMeta pm = ((DesignPanel)dPanel).getPagemeta();
//		String[] statesStr = null;
//		if(pm != null){
//			PageStates pss = pm.getPageStates();
//			PageState[] states = pss.getPageStates();
//			statesStr = new String[states.length + 1];
//			statesStr[0] = " ";
//			for (int i = 0; i < states.length; i++) {
//				statesStr[i + 1] = states[i].getName();
//			}
//		}
		
//		ComboBoxPropertyDescriptor pageStateProp = new ComboBoxPropertyDescriptor("state", "对应页面状态", statesStr);
//		pageStateProp.setCategory("基本");
//		al.add(pageStateProp);
		
		TextPropertyDescriptor i18nName = new TextPropertyDescriptor("i18nName", "多语资源");
		i18nName.setCategory("基本");
		al.add(i18nName);
		
		return al.toArray(new IPropertyDescriptor[0]);
	}

	
	protected Composite getComposite() {
		return this;
	}

	@Override
	protected String getName() {
		return "页签项";
	}
}
