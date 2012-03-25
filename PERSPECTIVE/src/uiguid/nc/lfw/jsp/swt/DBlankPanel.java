package nc.lfw.jsp.swt;

import nc.lfw.jsp.editor.GuideEditor;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

public class DBlankPanel extends DLayoutPanel {

	public DBlankPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
	}

	@Override
	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE);
		bt.setText("blankPanel");
		return bt;
	}

	@Override
	protected String getName() {
		return "blankPanel";
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
//				MenuItem item = (MenuItem) e.getSource();
//				DesignBase db = (DesignBase) item.getData();
//				DLayout parent = (DLayout) db.getDParent();
//				UIShutter shutter = (UIShutter) parent.getUiElement();
//				List<UILayoutPanel> children = shutter.getPanelList();
//				String shutterId = (String) shutter.getAttribute("id");
//				for (int i = 0; i < children.size(); i++) {
//					UIShutterItem shutterItem = (UIShutterItem) children.get(i);
//				}
//				addShutterCompnent(outlookbar);
			}
			
		});
		comp.setMenu(popMenu);
	}
	
	
//	private void addShutterCompnent(OutlookbarComp outlookbar){
//		LFWSeparateTreeItem lfwSeparaTreeItem = null;
//		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
//		if(widgetTreeItem != null){
//			TreeItem[] separasTreeItems = widgetTreeItem.getItems();
//			for (int i = 0; i < separasTreeItems.length; i++) {
//				TreeItem item = separasTreeItems[i];
//				if(item instanceof LFWSeparateTreeItem){
//					LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
//					if(seitem.getText().equals("容器")){
//						lfwSeparaTreeItem = seitem;
//						break;
//					}
//				}
//			}
//			if(lfwSeparaTreeItem == null){
//				LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//				File file = widgetTreeItem.getFile();
//				lfwSeparaTreeItem = view.createFolder(widgetTreeItem, file, "容器");
//			}
//			
//			WebComponent shutterComp = widgetTreeItem.getWidget().getViewConinters().getContainer(outlookbar.getId());
//			if(shutterComp != null && shutterComp instanceof OutlookbarComp){
//				boolean flag = MessageDialog.openConfirm(null, "提示", "此片段下已经存在名字为" + outlookbar.getId() + "card布局,是否覆盖?");
//				//覆盖已有的tab
//				if(flag){
//					TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
//					for (int i = 0; i < treeItems.length; i++) {
//						LFWWebComponentTreeItem treeItem = (LFWWebComponentTreeItem) treeItems[i];
//						WebComponent data = (WebComponent) treeItem.getData();
//						if(data instanceof OutlookbarComp && data.getId().equals(outlookbar.getId())){
//							//widgetTreeItem.getWidget().getViewConinters().addContainer(tabLayout);
//							treeItem.dispose();
//							break;
//						}
//					}
//				}
//				else return;
//			}
//			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(lfwSeparaTreeItem, LFWWebComponentTreeItem.OUTLOOKBAR_NAME ,outlookbar);
//			newComp.setType(LFWWebComponentTreeItem.PARAM_OUTLOOKBAR);
//			LfwWidget widget = widgetTreeItem.getWidget();
//			widget.getViewConinters().addContainer(outlookbar);
//			LFWPersTool.saveWidget(widget);
//		}
//		else {
//			LFWPageMetaTreeItem pmTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
//			PageMeta pm = pmTreeItem.getPm();
//			if(pmTreeItem != null){
//				TreeItem[] separasTreeItems = pmTreeItem.getItems();
//				for (int i = 0; i < separasTreeItems.length; i++) {
//					TreeItem item = separasTreeItems[i];
//					if(item instanceof LFWSeparateTreeItem){
//						LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
//						if(seitem.getText().equals("容器")){
//							lfwSeparaTreeItem = seitem;
//							break;
//						}
//					}
//				}
//				if(lfwSeparaTreeItem == null){
//					LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//					File file = pmTreeItem.getFile();
//					lfwSeparaTreeItem = view.createFolder(pmTreeItem, file, "容器");
//				}
//				
//				WebComponent cardComp = pmTreeItem.getPm().getViewConinters().getContainer(outlookbar.getId());
//				if(cardComp != null && cardComp instanceof CardLayout){
//					boolean flag = MessageDialog.openConfirm(null, "提示", "此Page下已经存在名字为" + outlookbar.getId() + "card布局,是否覆盖?");
//					//覆盖已有的tab
//					if(flag){
//						TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
//						for (int i = 0; i < treeItems.length; i++) {
//							LFWWebComponentTreeItem treeItem = (LFWWebComponentTreeItem) treeItems[i];
//							WebComponent data = (WebComponent) treeItem.getData();
//							if(data.getId().equals(outlookbar.getId())){
//								//pmTreeItem.getPm().getViewConinters().addContainer(tabLayout);
//								treeItem.dispose();
//								break;
//							}
//						}
//					}else
//						return;
//				}
//				LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(lfwSeparaTreeItem, LFWWebComponentTreeItem.OUTLOOKBAR_NAME ,outlookbar);
//				newComp.setType(LFWWebComponentTreeItem.PARAM_OUTLOOKBAR);
//				pm.getViewConinters().addContainer(outlookbar);
//				LFWPersTool.savePagemeta(pm);
//			}
//		}
//	}
}
