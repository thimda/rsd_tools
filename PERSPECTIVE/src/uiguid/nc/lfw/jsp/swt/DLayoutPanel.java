package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.jsp.dialog.CardLayoutDialog;
import nc.lfw.jsp.dialog.FlowhLayoutDialog;
import nc.lfw.jsp.dialog.FlowvLayoutDialog;
import nc.lfw.jsp.dialog.MenubarGroupDialog;
import nc.lfw.jsp.dialog.TabLayoutDialog;
import nc.lfw.jsp.dialog.WidgetDialog;
import nc.lfw.jsp.editor.GuideEditor;
import nc.lfw.jsp.editor.GuidePaletteView;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIBorder;
import nc.uap.lfw.jsp.uimeta.UIBorderTrue;
import nc.uap.lfw.jsp.uimeta.UIButton;
import nc.uap.lfw.jsp.uimeta.UICardLayout;
import nc.uap.lfw.jsp.uimeta.UICardPanel;
import nc.uap.lfw.jsp.uimeta.UIChartComp;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIExcelComp;
import nc.uap.lfw.jsp.uimeta.UIFlowhLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowhPanel;
import nc.uap.lfw.jsp.uimeta.UIFlowvLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowvPanel;
import nc.uap.lfw.jsp.uimeta.UIFormComp;
import nc.uap.lfw.jsp.uimeta.UIFormGroupComp;
import nc.uap.lfw.jsp.uimeta.UIGridComp;
import nc.uap.lfw.jsp.uimeta.UIIFrame;
import nc.uap.lfw.jsp.uimeta.UIImageComp;
import nc.uap.lfw.jsp.uimeta.UILabelComp;
import nc.uap.lfw.jsp.uimeta.UILayout;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UILinkComp;
import nc.uap.lfw.jsp.uimeta.UIMenuGroup;
import nc.uap.lfw.jsp.uimeta.UIMenuGroupItem;
import nc.uap.lfw.jsp.uimeta.UIMenubarComp;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UIPanel;
import nc.uap.lfw.jsp.uimeta.UIPanelPanel;
import nc.uap.lfw.jsp.uimeta.UISelfDefComp;
import nc.uap.lfw.jsp.uimeta.UIShutter;
import nc.uap.lfw.jsp.uimeta.UIShutterItem;
import nc.uap.lfw.jsp.uimeta.UISplitter;
import nc.uap.lfw.jsp.uimeta.UISplitterOne;
import nc.uap.lfw.jsp.uimeta.UISplitterTwo;
import nc.uap.lfw.jsp.uimeta.UITabComp;
import nc.uap.lfw.jsp.uimeta.UITabItem;
import nc.uap.lfw.jsp.uimeta.UITextField;
import nc.uap.lfw.jsp.uimeta.UIToolBar;
import nc.uap.lfw.jsp.uimeta.UITreeComp;
import nc.uap.lfw.jsp.uimeta.UIWidget;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class DLayoutPanel extends DesignBase {

	public DLayoutPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		initUI();
	}
	protected void initchildren(){
		UILayoutPanel panel = (UILayoutPanel) getUiElement();
		UIElement child = panel.getElement();
		Control control = null;
		if(child instanceof UIWidget)
			control = initWidget(child);
		else if(child instanceof UILayout)
			control = initLayoutChild(child);
		else
			control = initComponentChild(child);
		
		afterProcessChild(control);
	}

	private Control initWidget(UIElement child) {
		//UIWidget widget = (UIWidget) child;
		DWidget wd = new DWidget(getTrueParent(), SWT.NONE, (UIWidget) child, (LfwWidget) getWebElementByUIElement(child));
		PageMeta pm = getDesignPanel(this).getPagemeta();
		wd.setPagemeta(pm);
		return wd;
	}

	private Control initComponentChild(UIElement child) {
		DComponent comp = null;
		WebElement webElement = getWebElementByUIElement(child);
		if(webElement == null && !(child instanceof UIFormGroupComp)){
			comp = new DError(getTrueParent(), SWT.NONE, child, webElement);
			return comp;
		}
		if(child instanceof UIGridComp){
			comp = new DGridComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		if(child instanceof UIExcelComp){
			comp = new DExcelComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UITreeComp){
			comp = new DTreeViewComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UIMenubarComp){
			comp = new DMenubarComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UIFormComp){
			comp = new DFormComp(getTrueParent(), SWT.NONE, child, webElement);
		}
//		else if(child instanceof UIFormGroupComp){
//			comp = new DFormGroupComp(getTrueParent(), SWT.NONE, child, webElement);
//		}
		else if(child instanceof UIButton){
			comp = new DButton(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UIChartComp){
			comp = new DChartComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UILinkComp){
			comp = new DLinkComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UIToolBar){
			comp = new DToolBar(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UILabelComp){
			comp = new DLabelComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UIImageComp){
			comp = new DImageComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UITextField){
			comp = new DTextField(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UIIFrame){
			comp = new DIFrame(getTrueParent(), SWT.NONE, child, webElement);
		}
		else if(child instanceof UISelfDefComp){
			comp = new DSelfdefComp(getTrueParent(), SWT.NONE, child, webElement);
		}
		return comp;
	}

	private Control initLayoutChild(UIElement child) {
		DLayout layout = null;
		if(child instanceof UISplitter){
			layout = new DSplitter(getTrueParent(), SWT.NONE, child);
		}
		else if(child instanceof UIFlowvLayout){
			layout = new DFlowvLayout(getTrueParent(), SWT.NONE, child);
		}
		else if(child instanceof UIPanel){
			layout = new DPanel(getTrueParent(), SWT.NONE, child);
		}
		else if(child instanceof UIFlowhLayout){
			layout = new DFlowhLayout(getTrueParent(), SWT.NONE, child);
		}
		else if(child instanceof UICardLayout){
			layout = new DCardLayout(getTrueParent(), SWT.NONE, child);
		}
		else if(child instanceof UITabComp){
			layout = new DTabComp(getTrueParent(), SWT.NONE, child);
		}
		else if(child instanceof UIMenuGroup){
			layout = new DMenuGroup(getTrueParent(), SWT.NONE, child);
		}
		
		else if(child instanceof UIBorder){
			layout = new DBorder(getTrueParent(), SWT.NONE, child);
		}
		
//		else if(child instanceof UIGroup){
//			layout = new DGroup(getTrueParent(), SWT.NONE, child);
//		}
		
		else if(child instanceof UIShutter){
			layout = new DShutter(getTrueParent(), SWT.NONE, child);
		}

		return layout;
	}

	protected void afterProcessChild(Control comp) {
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
		comp.setMenu(popMenu);
	}

	protected Composite getTrueParent() {
		return this;
	}
	
	protected void initialize(){
		this.setLayout(new FillLayout());
		UILayoutPanel panel = (UILayoutPanel) getUiElement();
		UIElement child = panel.getElement();
		if(child == null){
			Control ctrl = createSign();
			afterProcessChild(ctrl);
			addCommonListener(ctrl);
		}
		else{
			//clearSign();
			initchildren();
		}
		this.layout();
	}

	protected void addCommonListener(Control ctrl) {
		ctrl.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				Button bt = (Button) e.getSource();
				bt.getParent().forceFocus();
			}

			public void focusLost(FocusEvent e) {
				
			}
		});
		
		DropTarget target = new DropTarget(ctrl, DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_DEFAULT);
		target.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		target.addDropListener(new DropTargetListener(){

			public void dragEnter(DropTargetEvent event) {
				//System.out.println("enter");
			}

			public void dragLeave(DropTargetEvent event) {
				//System.out.println("enter");
			}

			public void dragOperationChanged(DropTargetEvent event) {
				//System.out.println("enter");
			}

			public void dragOver(DropTargetEvent event) {
				//System.out.println("over");
			}

			public void drop(DropTargetEvent event) {
				String data = (String) event.data;
				DropTarget target = (DropTarget) event.getSource();
				Control bt = (Control) target.getControl();
				DesignBase db = null;
				
				if(bt.getData() instanceof DTabItem)
					db = (DesignBase) bt.getData();
				else{
					Composite cp = bt.getParent();
					if(cp instanceof DesignBase)
						db = (DesignBase) cp;
					else
						db = (DesignBase) cp.getParent();
				}
				DesignPanel dp = db.getDesignPanel(db);
				//嵌套不允许往子里加
				if(dp.getDesignPanel(dp) != null)
					return;
				
				GuideEditor editor = (GuideEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				editor.setDirty();
				
				LFWPageMetaTreeItem pmTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
				PageMeta pm = null;
				if(pmTreeItem != null)
					pm = (PageMeta) LFWPersTool.getCurrentPageMeta();
				LfwWidget widget = null;
				if(dp instanceof DWidget){
					widget = ((DWidget)dp).getWidget();
				}
				UILayoutPanel panel = (UILayoutPanel) db.getUiElement();
				UIElement ele = null;
				if(data.equals(GuidePaletteView.DCARD)){
					CardLayoutDialog dlg = new CardLayoutDialog(Display.getCurrent().getShells()[0]);
					if(dlg.open() == Dialog.OK){
						ele = createCardLayout(dlg, widget, dp);
					}
					else
						return;
				}
				else if(data.equals(GuidePaletteView.DFLOWH)){
					FlowhLayoutDialog dlg = new FlowhLayoutDialog(Display.getCurrent().getShells()[0]);
					if(dlg.open() == Dialog.OK){
						ele = createFlowhLayout(dlg, dp);
					}
					else
						return;
				}
				else if(data.equals(GuidePaletteView.DFLOWV)){
					FlowvLayoutDialog dlg = new FlowvLayoutDialog(Display.getCurrent().getShells()[0]);
					if(dlg.open() == Dialog.OK){
						ele = createFlowvLayout(dlg, dp);
					}
					else
						return;
				}
				else if(data.equals(GuidePaletteView.DSPLIT)){
					ele = createSplitterLayout(dp);
				}
				else if(data.equals(GuidePaletteView.DBORDER)){
					ele = createBorder(dp);
				}
//				else if(data.equals(GuidePaletteView.DGROUP)){
//					ele = createGroup(dp);
//				}
				else if(data.equals(GuidePaletteView.DPANEL)){
					ele = createPanel(dp);
				}
				else if(data.equals(GuidePaletteView.DSHUTTER)){
					FlowhLayoutDialog dlg = new FlowhLayoutDialog(Display.getCurrent().getShells()[0]);
					if(dlg.open() == Dialog.OK){
						ele = createShutter(dlg, widget, dp);
					}
					else
						return;
				}
				else if(data.equals(GuidePaletteView.DTAB)){
					TabLayoutDialog dlg = new TabLayoutDialog(Display.getCurrent().getShells()[0]);
					if(dlg.open() == Dialog.OK){
						ele = createTabLayout(dlg, widget, dp);
					}
					else
						return;
				}
				else if(data.equals(GuidePaletteView.DMENUGROUP)){
					MenubarGroupDialog dlg = new MenubarGroupDialog(Display.getCurrent().getShells()[0]);
					if(dlg.open() == Dialog.OK){
						ele = createMenubarGroupLayout(dlg, dp);
					}
					else
						return;
				}
				else{
					if(data.equals(GuidePaletteView.DGRID)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_GRID);
						if(dlg.open() == Dialog.OK){
							ele = createGridComp(dlg);
						}
						else
							return;
					}
					else if(data.equals(GuidePaletteView.DTREE)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_TREE);
						if(dlg.open() == Dialog.OK){
							ele = createTreeComp(dlg);
						}
						else
							return;
					}
					else if(data.equals(GuidePaletteView.DExcel)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_EXCELCOMP);
						if(dlg.open() == Dialog.OK){
							ele = createExcelComp(dlg);
						}
						else
							return;
					}
					else if(data.equals(GuidePaletteView.DCHARTCOMP)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_CHARTCOMP);
						if(dlg.open() == Dialog.OK){
							ele = createChartComp(dlg);
						}
						else
							return;
					}
					else if(data.equals(GuidePaletteView.DSELFDEFCOMP)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_SELFDEFCOMP);
						if(dlg.open() == Dialog.OK){
							ele = createSelfdefComp(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DFORM)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_FORM);
						if(dlg.open() == Dialog.OK){
							ele = createFormComp(dlg);
						}
						else
							return;
					}
					//*************************** add by limingf 
//					else if(data.equals("自定义表单")){
//						FormSelectDialog dlg = new FormSelectDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.SELF_TYPE_FORM);
//						if(dlg.open() == Dialog.OK){							
//							FormStyleDialog dialog = new FormStyleDialog(Display.getCurrent().getShells()[0],widget,dlg);
//							if(dialog.open() == Dialog.OK){
//								 
//								   dialog.doSave();          //
//								 
//								 /*  Control[] ctrls = db.getChildren();
//								   for (int i = 0; i < ctrls.length; i++) {
//										try{
//											ctrls[i].dispose();
//										}
//										catch(Exception e){
//											
//										}
//								   }
//									
//									String filePath =  LFWPersTool.getCurrentFolderPath() ;
//									String fileName = dlg.getMainFormId()+FormConstant.POSTFIX;
//									FormCompDesign design = new FormCompDesign();
//									StyleFormComp styleForm = design.getFormCompFromXml(filePath, fileName);
//								
//									WebComponent[]  comps = widget.getViewComponents().getComponents();
//									List<FormComp> formCompList = new ArrayList<FormComp>();
//									int compsLength = comps.length;
//									for(int i = 0; i<compsLength; i++){
//										WebComponent temp = comps[i];
//										if(temp instanceof FormComp){
//											formCompList.add((FormComp)temp);
//										}
//									}
//									
//									FormStylePanel main =  new FormStylePanel(db,SWT.NONE, styleForm,formCompList.toArray(new FormComp[0]));
//									//删除
//									Menu popMenu = new Menu(main);
//									MenuItem item = new MenuItem(popMenu, SWT.NONE);
//									item.setData(main);
//									item.setText("删除");
//									item.addSelectionListener(new SelectionListener(){
//										public void widgetDefaultSelected(SelectionEvent e) {}
//										public void widgetSelected(SelectionEvent e) {
//											GuideEditor editor = (GuideEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//											editor.setDirty();
//											MenuItem item = (MenuItem) e.getSource();
//											FormStylePanel db = (FormStylePanel) item.getData();										
//											DLayoutPanel parent = (DLayoutPanel) db.getParent();									
//											UILayoutPanel pele = (UILayoutPanel) (parent).getUiElement();
//											pele.setElement(null);
//											parent.initUI();
//										}									
//									});
//									main.setMenu(popMenu);
//									db.layout();
//									//return;
//									 * 
//*/									
//								   ele = createFormGroupComp(dlg);
//							   }
//						  
//						}
//						else
//							return;
//					}
					//***************************
					else if(data.equals(GuidePaletteView.DLINKCOMP)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_LINKCOMP);
						if(dlg.open() == Dialog.OK){
							ele = createLinkComp(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DWIDGET)){
						
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_WIDGET);
						if(dlg.open() == Dialog.OK){
							UIMeta uimeta = (UIMeta) dp.getUiElement();
							ele = createWidget(dlg, uimeta);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DTEXT)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_TEXT);
						if(dlg.open() == Dialog.OK){
							ele = createTextField(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DBUTTON)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_BUTTON);
						if(dlg.open() == Dialog.OK){
							ele = createButton(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DTOOLBAR)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_TOOLBAR);
						if(dlg.open() == Dialog.OK){
							ele = createToolBar(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DLABEL)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_LABEL);
						if(dlg.open() == Dialog.OK){
							ele = createLabelComp(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DIMAGE)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_IMAGE);
						if(dlg.open() == Dialog.OK){
							ele = createImage(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DIFRAME)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_IFRAME);
						if(dlg.open() == Dialog.OK){
							ele = createIFrame(dlg);
						}
						else
							return;
					}
					
					else if(data.equals(GuidePaletteView.DMENUBAR)){
						WidgetDialog dlg = new WidgetDialog(Display.getCurrent().getShells()[0], pm, widget, WidgetDialog.TYPE_MENUBAR);
						if(dlg.open() == Dialog.OK){
							ele = createMenubar(dlg);
						}
						else
							return;
					}
					
					
				}
				panel.setElement(ele);
				db.initUI();
			}

			private UIElement createShutter(FlowhLayoutDialog dlg, LfwWidget widget, DesignBase dp) {
				UIShutter layout = new UIShutter();
				layout.setId("shutter1");
				if(widget != null)
					layout.setWidgetId(widget.getId());
				int pageCount = dlg.getPageCount();
				for (int i = 0; i < pageCount; i++) {
					UIShutterItem panel = new UIShutterItem();
					panel.setText("item" + (i+1));
					panel.setId("item" + (i+1));
					layout.addPanel(panel);
				}
				
				DesignPanel designPanel = (DesignPanel) dp;
				int bIndex = ++designPanel.outlookbarIndex;
				layout.setId("outlookbar" + String.valueOf(bIndex));
				layout.setCurrentItem("0");
				return layout;
			}
		

			private UIElement createIFrame(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIIFrame widget = new UIIFrame();
				widget.setId(ids[0]);
				widget.setWidgetId(ids[1]);
				return widget;
			}

			private UIElement createBorder(DesignBase db) {
				DesignPanel designPanel = (DesignPanel) db;
				int bIndex = ++designPanel.borderIndex;
				UIBorder layout = new UIBorder();
				layout.setWidth("1");
				layout.setId("border" + String.valueOf(bIndex));
				UIBorderTrue borderTrue = new UIBorderTrue();
				LfwWidget widget = LFWPersTool.getCurrentWidget();
				if(widget != null)
					layout.setWidgetId(widget.getId());
				layout.addPanel(borderTrue);
				return layout;
			}
			
//			private UIElement createGroup(DesignBase dp) {
//				UIGroup group = new UIGroup();
//				group.setText("新分组");
//				UIGroupPanel gp = new UIGroupPanel();
//				group.addPanel(gp);
//				DesignPanel designPanel = (DesignPanel) dp;
//				int bIndex = ++designPanel.groupIndex;
//				group.setId("group" + String.valueOf(bIndex));
//				return group;
//			}
			
			private UIElement createPanel(DesignBase dp) {
				UIPanel layout = new UIPanel();
				UIPanelPanel panel = new UIPanelPanel();
				layout.addPanel(panel);
				DesignPanel designPanel = (DesignPanel) dp;
				int bIndex = ++designPanel.panelIndex;
				layout.setId("panel" + String.valueOf(bIndex));
				return layout;
			}
			
			
			private UIElement createMenubar(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIMenubarComp menubar = new UIMenubarComp();
				menubar.setId(ids[0]);
				if(ids[1] != null && !ids[1].equals(""))
					menubar.setWidgetId(ids[1]);
				return menubar;
			}

			private UIElement createMenubarGroupLayout(MenubarGroupDialog dlg, DesignBase dp) {
				UIMenuGroup layout = new UIMenuGroup();
				int pageCount = dlg.getPageCount();
				for (int i = 0; i < pageCount; i++) {
					UIMenuGroupItem p = new UIMenuGroupItem();
					layout.addPanel(p);
				}
				DesignPanel designPanel = (DesignPanel) dp;
				int bIndex = ++designPanel.menubarGroupIndex;
				layout.setId("menugroup" + String.valueOf(bIndex));
				return layout;
			}

			private UIElement createTreeComp(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UITreeComp grid = new UITreeComp();
				grid.setId(ids[0]);
				grid.setWidgetId(ids[1]);
				return grid;
			}
			
			private UIElement createExcelComp(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIExcelComp excel = new UIExcelComp();
				excel.setId(ids[0]);
				excel.setWidgetId(ids[1]);
				return excel;
			}
			
			
			private UIElement createChartComp(WidgetDialog dlg){
				String[] ids = dlg.getSelectId();
				UIChartComp chart = new UIChartComp();
				chart.setId(ids[0]);
				chart.setWidgetId(ids[1]);
				return chart;
			}
			
			private UIElement createSelfdefComp(WidgetDialog dlg){
				String[] ids = dlg.getSelectId();
				UISelfDefComp self = new UISelfDefComp();
				self.setId(ids[0]);
				self.setWidgetId(ids[1]);
				return self;
			}

			private UIElement createFormComp(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIFormComp form = new UIFormComp();
				form.setId(ids[0]);
				form.setWidgetId(ids[1]);
				return form;
			}
			
//			private UIElement createFormGroupComp(FormSelectDialog dlg){
//				UIFormGroupComp formGroup = new UIFormGroupComp();
//				formGroup.setId(dlg.getMainFormId());
//				formGroup.setWidgetId(dlg.getWidget().getId());
//				List<String> formList = dlg.getFormList();
//				StringBuffer forms = new StringBuffer();
//				for(String formId:formList){
//					forms.append(formId).append(",");
//				}
//				formGroup.setForms(forms.toString());
//				return formGroup;
//			}
			
			private UIElement createLinkComp(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UILinkComp linkcomp = new UILinkComp();
				linkcomp.setId(ids[0]);
				linkcomp.setWidgetId(ids[1]);
				return linkcomp;
			}

			private UIElement createButton(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIButton widget = new UIButton();
				widget.setId(ids[0]);
				widget.setWidgetId(ids[1]);
				return widget;
			}
			
			private UIElement createToolBar(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIToolBar toolbar = new UIToolBar();
				toolbar.setId(ids[0]);
				toolbar.setWidgetId(ids[1]);
				return toolbar;
			}
			
			
			private UIElement createLabelComp(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UILabelComp widget = new UILabelComp();
				widget.setId(ids[0]);
				widget.setWidgetId(ids[1]);
				return widget;
			}
			
			private UIElement createImage(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIImageComp widget = new UIImageComp();
				widget.setId(ids[0]);
				widget.setWidgetId(ids[1]);
				return widget;
			}

			private UIElement createTextField(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UITextField text = new UITextField();
				text.setId(ids[0]);
				text.setWidgetId(ids[1]);
				text.setType(ids[2]);
				return text;
			}

			private UIElement createWidget(WidgetDialog dlg, UIMeta uimeta) {
				String[] ids = dlg.getSelectId();
				UIWidget widget = new UIWidget();
				widget.setId(ids[0]);
				
				UIMeta wmeta = NCConnector.getUIMeta(uimeta.getFolderPath() + "/" + widget.getId(), null, null);
				widget.setUimeta(wmeta);
				return widget;
			}

			private UIElement createGridComp(WidgetDialog dlg) {
				String[] ids = dlg.getSelectId();
				UIGridComp grid = new UIGridComp();
				grid.setId(ids[0]);
				grid.setWidgetId(ids[1]);
				return grid;
			}

			private UIElement createTabLayout(TabLayoutDialog dlg, LfwWidget widget, DesignBase dp) {
				UITabComp tab = new UITabComp();
				DesignPanel designPanel = (DesignPanel) dp;
				int cardIndex = ++designPanel.tabIndex;
				tab.setId("tab" + String.valueOf( cardIndex));
				if(widget != null)
					tab.setWidgetId(widget.getId());
				int pageCount = dlg.getPageCount();
				for (int i = 0; i < pageCount; i++) {
					UITabItem p = new UITabItem();
					p.setId("item" + (i + 1));
					p.setText("页签" + (i + 1));
					tab.addPanel(p);
				}
				
				
				tab.setCurrentItem("0");
				return tab;
			}

			private UIElement createSplitterLayout(DesignBase dp) {
				UISplitter splitter = new UISplitter();
				DesignPanel designPanel = (DesignPanel) dp;
				int splitterIndex = ++designPanel.splitterIndex;
				splitter.setId("spliter" + String.valueOf(splitterIndex));
				splitter.setOrientation(new Integer(1));
				
				UISplitterOne one = new UISplitterOne();
				UISplitterTwo two = new UISplitterTwo();
				splitter.addPanel(one);
				splitter.addPanel(two);
				return splitter;
			}

			private UIElement createCardLayout(CardLayoutDialog dlg, LfwWidget widget, DesignBase dp) {
				UICardLayout layout = new UICardLayout();
				layout.setId("card1");
				if(widget != null)
					layout.setWidgetId(widget.getId());
				int pageCount = dlg.getPageCount();
				for (int i = 0; i < pageCount; i++) {
					UICardPanel p = new UICardPanel();
					p.setId("cardpanel" + (i + 1));
					layout.addPanel(p);
				}
				
				DesignPanel designPanel = (DesignPanel) dp;
				int cardIndex = ++designPanel.cardLayoutIndex;
				layout.setId("cardLayout" + String.valueOf( cardIndex));
				layout.setCurrentItem("cardpanel1");
				
				return layout;
			}
			
			private UIElement createFlowhLayout(FlowhLayoutDialog dlg, DesignBase dp) {
				UIFlowhLayout layout = new UIFlowhLayout();
				int pageCount = dlg.getPageCount();
				for (int i = 0; i < pageCount; i++) {
					UIFlowhPanel p = new UIFlowhPanel();
					layout.addPanel(p);
				}
				DesignPanel designPanel = (DesignPanel) dp;
				int flowhIndex = ++designPanel.flowhIndex;
				layout.setId("flowh" + String.valueOf(flowhIndex));
				return layout;
			}
			private UIElement createFlowvLayout(FlowvLayoutDialog dlg, DesignBase dp) {
				UIFlowvLayout layout = new UIFlowvLayout();
				int pageCount = dlg.getPageCount();
				for (int i = 0; i < pageCount; i++) {
					UIFlowvPanel p = new UIFlowvPanel();
					layout.addPanel(p);
				}
				DesignPanel designPanel = (DesignPanel) dp;
				int flowvIndex = ++designPanel.FlowvIndex;
				layout.setId("flowv" + String.valueOf(flowvIndex));
				return layout;
			}

			public void dropAccept(DropTargetEvent event) {
				
			}
			
		});
	}

	protected abstract Control createSign();
	protected void clearSign(){
		Control[] ctrls = this.getChildren();
		if(ctrls != null){
			for (int i = 0; i < ctrls.length; i++) {
				ctrls[i].dispose();
			}
		}
	}

	
	protected Composite getComposite() {
		return this;
	}

	public Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		return al.toArray(new IPropertyDescriptor[0]);
	}
}
