package nc.lfw.jsp.swt;

import java.io.Serializable;

import nc.lfw.jsp.editor.GuideEditor;
import nc.uap.lfw.jsp.uimeta.UIElement;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

public class DPanelPanel extends DLayoutPanel {

	public DPanelPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}

	
	protected Control createSign() {
		Button bt = new Button(this, SWT.NONE);
		bt.setText("Panel");
		return bt;
	}

	
	protected Composite getComposite() {
		return this;
	}
	
	protected void updateProperty(String id, Serializable oldValue, Serializable newValue){
		if(getParent() != null)
			((DPanel)getParent()).updateProperty(id, oldValue, newValue);
	}


	@Override
	protected String getName() {
		return null;
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
				MenuItem item = (MenuItem) e.getSource();
				DesignBase db = (DesignBase) item.getData();
				DLayout parent = (DLayout) db.getDParent();
				String panelId = (String) parent.getUiElement().getAttribute("id");
				if(panelId == null || "".equals(panelId)){
					MessageDialog.openError(null, "错误提示", "请设置Panle布局的ID!");
					return;
				}
//				addPanelCompnent(panelId);
			}
			
		});
		comp.setMenu(popMenu);
	}
	
//	private void addPanelCompnent(String id){
//		PanelLayout panelLayout = new PanelLayout();
//		panelLayout.setId(id);
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
//			WebComponent panelComp = widgetTreeItem.getWidget().getViewConinters().getContainer(id);
//			if(panelComp != null && panelComp instanceof PanelLayout){
//				boolean flag = MessageDialog.openConfirm(null, "提示", "此片段下已经存在名字为" + id + "panel布局,是否覆盖?");
//				//覆盖已有的tab
//				if(flag){
//					TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
//					for (int i = 0; i < treeItems.length; i++) {
//						LFWWebComponentTreeItem treeItem = (LFWWebComponentTreeItem) treeItems[i];
//						WebComponent data = (WebComponent) treeItem.getData();
//						if(data instanceof PanelLayout && data.getId().equals(id)){
//							treeItem.dispose();
//							break;
//						}
//					}
//				}
//				else return;
//			}
//			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(lfwSeparaTreeItem, LFWWebComponentTreeItem.PANELLAYOUT_NAME ,panelLayout);
//			newComp.setType(LFWWebComponentTreeItem.PARAM_PANELLAYOUT);
//			LfwWidget widget = widgetTreeItem.getWidget();
//			widget.getViewConinters().addContainer(panelLayout);
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
//				WebComponent panelComp = pmTreeItem.getPm().getViewConinters().getContainer(id);
//				if(panelComp != null && panelComp instanceof PanelLayout){
//					boolean flag = MessageDialog.openConfirm(null, "提示", "此Page下已经存在名字为" + id + "panel布局,是否覆盖?");
//					//覆盖已有的tab
//					if(flag){
//						TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
//						for (int i = 0; i < treeItems.length; i++) {
//							LFWWebComponentTreeItem treeItem = (LFWWebComponentTreeItem) treeItems[i];
//							WebComponent data = (WebComponent) treeItem.getData();
//							if(data instanceof PanelLayout && data.getId().equals(id)){
//								//pmTreeItem.getPm().getViewConinters().addContainer(tabLayout);
//								treeItem.dispose();
//								break;
//							}
//						}
//					}else
//						return;
//				}
//				LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(lfwSeparaTreeItem, LFWWebComponentTreeItem.PANELLAYOUT_NAME ,panelLayout);
//				newComp.setType(LFWWebComponentTreeItem.PARAM_CARDLAYOUT);
//				pm.getViewConinters().addContainer(panelLayout);
//				LFWPersTool.savePagemeta(pm);
//			}
//		}
//	}
		
}
