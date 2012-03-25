package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.lfw.jsp.editor.GuideEditor;
import nc.uap.lfw.jsp.uimeta.UICardPanel;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayout;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UISplitterOne;
import nc.uap.lfw.jsp.uimeta.UIWidget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

public class NavigatePanel extends Canvas {
	private DesignBase element;
	public NavigatePanel(Composite parent, int style) {
		super(parent, style);
		//this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_CYAN));
		initialize();
	}

	private void initialize() {
		RowLayout row = new RowLayout();
		row.marginTop = 0;
		row.marginBottom = 0;
		row.spacing = 0;
		this.setLayout(row);
		recreateBar();
	}
	
	public void setDesignElement(DesignBase ele){
		this.element = ele;
		recreateBar();
	}

	private void recreateBar() {
		Control[] ctrls = this.getChildren();
		if(ctrls != null){
			for (int i = 0; i < ctrls.length; i++) {
				ctrls[i].dispose();
			}
		}
		if(element == null){
			new Label(this, SWT.NONE).setText("Î´Ñ¡ÖÐ×é¼þ");
			return;
		}
		final List<DesignBase> list = new ArrayList<DesignBase>();
		list.add(0, element);
		Composite parent =  element.getDParent();
		while(parent != null && !(parent instanceof DesignPanel)){
			if(!(parent instanceof DesignBase)){
				parent = parent.getParent();
				continue;
			}
			list.add(0, (DesignBase) parent);
			parent = parent.getParent();
		}
		
//		final Canvas canvas = this;
//		this.getDisplay().syncExec(new Runnable(){
//			public void run() {
//				
//			}
//		});
		
		
		
		Iterator<DesignBase> it = list.iterator();
		while(it.hasNext()){
			DesignBase db = it.next();
			Control ctrl = null;
			if(db instanceof DCardLayout){
				DCardLayout layout = (DCardLayout) db;
				
				Button bt = new Button(this, SWT.NONE);
				bt.setText("¿¨Æ¬");
				bt.setSize(100, 33);
				Control[] panels = layout.getChildren();
				String[] strs = new String[panels.length];
				int selIndex = 0;
				StackLayout stackLayout = (StackLayout) layout.getLayout();
				for (int i = 0; i < panels.length; i++) {
					DCardPanel panel = (DCardPanel) panels[i];
					if(stackLayout.topControl.equals(panel))
						selIndex = i;
					strs[i] = ((UICardPanel)panel.getUiElement()).getId();
				}
				Combo comb = new Combo(this, SWT.NONE | SWT.READ_ONLY);
				comb.setItems(strs);
				comb.select(selIndex);
				comb.setData(db);
				
				comb.addSelectionListener(new SelectionListener(){

					public void widgetDefaultSelected(SelectionEvent e) {
						Combo comb = (Combo) e.getSource();
						String id = comb.getItem(comb.getSelectionIndex());
						DCardLayout db = (DCardLayout) comb.getData();
						db.setNowPanel(id);
					}

					public void widgetSelected(SelectionEvent e) {
						Combo comb = (Combo) e.getSource();
						String id = comb.getItem(comb.getSelectionIndex());
						DCardLayout db = (DCardLayout) comb.getData();
						db.setNowPanel(id);
					}
					
				});
				
				
				bt.setData(comb);
				bt.addMouseListener(new MouseListener(){
					public void mouseDoubleClick(MouseEvent e) {
					}

					public void mouseDown(MouseEvent e) {
						Button bt = (Button) e.getSource();
						Combo comb = (Combo) bt.getData();
						DesignBase db = (DesignBase) comb.getData();
						if(db instanceof DTabComp){
							db.setCurrentSelection(db);
							return;
						}
						db.forceFocus();
					}

					public void mouseUp(MouseEvent e) {
					}
				});
				ctrl = comb;
				
			}
			else{
				if(db.getName() == null)
					continue;
				Button bt = new Button(this, SWT.NONE);
				
				bt.setText(db.getName());
				bt.setData(db);
				bt.addMouseListener(new MouseListener(){
					public void mouseDoubleClick(MouseEvent e) {
					}

					public void mouseDown(MouseEvent e) {
						Button bt = (Button) e.getSource();
						DesignBase db = (DesignBase) bt.getData();
						if(db instanceof DTabComp){
							db.setCurrentSelection(db);
							return;
						}
						db.forceFocus();
					}

					public void mouseUp(MouseEvent e) {
					}
				});
				ctrl = bt;
			}
			
			if(db instanceof DLayout){
				Menu popMenu = new Menu(ctrl);
				MenuItem item = new MenuItem(popMenu, SWT.NONE);
				item.setData(ctrl);
				item.setText("¼ôÇÐ");
				item.addSelectionListener(new SelectionListener(){
					public void widgetDefaultSelected(SelectionEvent e) {
						
					}
	
					public void widgetSelected(SelectionEvent e) {
						MenuItem item = (MenuItem) e.getSource();
						Control c = (Control) item.getData();
						DesignBase db = (DesignBase) c.getData();
						DesignPanel top = db.getDesignPanel(db);
						while(top.getDesignPanel(top) != null)
							top = top.getDesignPanel(top);
						UIElement ele = db.getUiElement();
						if(ele instanceof UISplitterOne){
							db = db.getDParent();
							ele = db.getUiElement();
						}
						top.setCutElement(ele);
						UIElement topUm = (UIElement) top.getUiElement();
						if(topUm instanceof UIWidget)
							topUm = ((UIWidget)topUm).getUimeta();
						cutFromLayoutPanel((UILayoutPanel) topUm, ele);
						db.getDParent().initUI();
						GuideEditor editor = (GuideEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
						editor.setDirty();
					}
					
	
					private void cutFromLayoutPanel(UILayoutPanel panel, UIElement ele) {
						UIElement nowEle = panel.getElement();
						if(nowEle != null && nowEle.equals(ele)){
							panel.setElement(null);
						}
						else{
							if(nowEle instanceof UILayout){
								cutFromLayout((UILayout) nowEle, ele);
							}
						}
							
					}
	
					private void cutFromLayout(UILayout layout, UIElement ele) {
						List<UILayoutPanel> lp = layout.getPanelList();
						if(lp != null){
							Iterator<UILayoutPanel> it = lp.iterator();
							while(it.hasNext()){
								UILayoutPanel panel = it.next();
								cutFromLayoutPanel(panel, ele);
							}
						}
					}
					
				});
				
				ctrl.setMenu(popMenu);
			}
			else if(db instanceof DLayoutPanel){
				Menu popMenu = new Menu(ctrl);
				MenuItem pasteItem = new MenuItem(popMenu, SWT.NONE);
				pasteItem.setData(ctrl);
				pasteItem.setText("Õ³Ìù");
				pasteItem.addSelectionListener(new SelectionListener(){
					public void widgetDefaultSelected(SelectionEvent e) {
						
					}

					public void widgetSelected(SelectionEvent e) {
						MenuItem item = (MenuItem) e.getSource();
						Control c = (Control) item.getData();
						DesignBase db = (DesignBase) c.getData();
						UIElement currEle = db.getUiElement();
						DesignPanel top = db.getDesignPanel(db);
						while(top.getDesignPanel(top) != null)
							top = top.getDesignPanel(top);
						UIElement ele = top.getCutElement();
						if(currEle instanceof UILayoutPanel){
							((UILayoutPanel)currEle).setElement(ele);
						}
						else if(currEle instanceof UILayout){
							((UILayout)currEle).addPanel((UILayoutPanel) ele);
						}
						db.initUI();
					}
				});
					
					ctrl.setMenu(popMenu);
			}
			
//			if(it.hasNext()){
//				new Label(this, SWT.NONE).setText("|");
//			}
		}
		this.layout();
	}

}
