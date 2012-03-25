package nc.lfw.jsp.swt;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.jsp.editor.GuideEditor;
import nc.uap.lfw.jsp.uimeta.UICardPanel;
import nc.uap.lfw.jsp.uimeta.UIElement;

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
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DCardPanel extends DLayoutPanel {
	public DCardPanel(Composite parent, int style, UIElement element) {
		super(parent, style, element);
		this.setLayout(new FillLayout());
	}


	
	protected Control createSign() {
		UICardPanel ele = (UICardPanel) getUiElement();
		Button bt = new Button(this, SWT.NONE);
		bt.setText(ele.getId());
		return bt;
	}
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor idProp = new TextPropertyDescriptor("id", "ID");
		idProp.setCategory("����");
		al.add(idProp);
		
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	protected void afterProcessChild(Control comp) {
		Menu popMenu = new Menu(comp);
		MenuItem item = new MenuItem(popMenu, SWT.NONE);
		item.setData(this);
		item.setText("ɾ��");
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
		nextItem.setText("����������");
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
//				String tabId = (String) parent.getUiElement().getAttribute("id");
//				addCardCompnent(tabId);
			}
			
		});
		comp.setMenu(popMenu);
	}

//	private void addCardCompnent(String id){
//		CardLayout cardLayout = new CardLayout();
//		cardLayout.setId(id);
//		LFWSeparateTreeItem lfwSeparaTreeItem = null;
//		LFWWidgetTreeItem widgetTreeItem = LFWPersTool.getCurrentWidgetTreeItem();
//		if(widgetTreeItem != null){
//			TreeItem[] separasTreeItems = widgetTreeItem.getItems();
//			for (int i = 0; i < separasTreeItems.length; i++) {
//				TreeItem item = separasTreeItems[i];
//				if(item instanceof LFWSeparateTreeItem){
//					LFWSeparateTreeItem seitem = (LFWSeparateTreeItem) item;
//					if(seitem.getText().equals("����")){
//						lfwSeparaTreeItem = seitem;
//						break;
//					}
//				}
//			}
//			if(lfwSeparaTreeItem == null){
//				LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//				File file = widgetTreeItem.getFile();
//				lfwSeparaTreeItem = view.createFolder(widgetTreeItem, file, "����");
//			}
//			
//			WebComponent cardComp = widgetTreeItem.getWidget().getViewConinters().getContainer(id);
//			if(cardComp != null && cardComp instanceof CardLayout){
//				boolean flag = MessageDialog.openConfirm(null, "��ʾ", "��Ƭ�����Ѿ���������Ϊ" + id + "card����,�Ƿ񸲸�?");
//				//�������е�tab
//				if(flag){
//					TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
//					for (int i = 0; i < treeItems.length; i++) {
//						LFWWebComponentTreeItem treeItem = (LFWWebComponentTreeItem) treeItems[i];
//						WebComponent data = (WebComponent) treeItem.getData();
//						if(data instanceof CardLayout && data.getId().equals(id)){
//							//widgetTreeItem.getWidget().getViewConinters().addContainer(tabLayout);
//							treeItem.dispose();
//							break;
//						}
//					}
//				}
//				else return;
//			}
//			LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(lfwSeparaTreeItem, LFWWebComponentTreeItem.CARDLAYOUT_NAME ,cardLayout);
//			newComp.setType(LFWWebComponentTreeItem.PARAM_CARDLAYOUT);
//			LfwWidget widget = widgetTreeItem.getWidget();
//			widget.getViewConinters().addContainer(cardLayout);
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
//						if(seitem.getText().equals("����")){
//							lfwSeparaTreeItem = seitem;
//							break;
//						}
//					}
//				}
//				if(lfwSeparaTreeItem == null){
//					LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//					File file = pmTreeItem.getFile();
//					lfwSeparaTreeItem = view.createFolder(pmTreeItem, file, "����");
//				}
//				
//				WebComponent cardComp = pmTreeItem.getPm().getViewConinters().getContainer(id);
//				if(cardComp != null && cardComp instanceof CardLayout){
//					boolean flag = MessageDialog.openConfirm(null, "��ʾ", "��Page���Ѿ���������Ϊ" + id + "card����,�Ƿ񸲸�?");
//					//�������е�tab
//					if(flag){
//						TreeItem[] treeItems = lfwSeparaTreeItem.getItems();
//						for (int i = 0; i < treeItems.length; i++) {
//							LFWWebComponentTreeItem treeItem = (LFWWebComponentTreeItem) treeItems[i];
//							WebComponent data = (WebComponent) treeItem.getData();
//							if(data.getId().equals(id)){
//								//pmTreeItem.getPm().getViewConinters().addContainer(tabLayout);
//								treeItem.dispose();
//								break;
//							}
//						}
//					}else
//						return;
//				}
//				LFWWebComponentTreeItem newComp = new LFWWebComponentTreeItem(lfwSeparaTreeItem, LFWWebComponentTreeItem.CARDLAYOUT_NAME ,cardLayout);
//				newComp.setType(LFWWebComponentTreeItem.PARAM_CARDLAYOUT);
//				pm.getViewConinters().addContainer(cardLayout);
//				LFWPersTool.savePagemeta(pm);
//			}
//		}
//	}
		


	@Override
	protected String getName() {
		return "��Ƭ���";
	}

}
