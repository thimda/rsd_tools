package nc.lfw.editor.menubar.ele;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.MenubarElementFigure;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.WebElement;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * 
 * @author guoweic
 * 
 */
public class MenubarElementObj extends LfwElementObjWithGraph {

	private static final long serialVersionUID = -1888132777640590427L;
	
	public static final String PROP_ITEM_ID = "ITEM_ID";
	public static final String PROP_ITEM_TEXT = "ITEM_TEXT";
	public static final String PROP_ITEM_STATEMANAGER = "ITEM_STATEMANAGER";
	public static final String PROP_ITEM_MENU = "ITEM_MENU";
//	public static final String PROP_ITEM_PARAM = "ITEM_PARAM_";
	
	private MenubarComp menubar;
	private List<MenuItemObj> childrenList = new ArrayList<MenuItemObj>();
	
	// ����Event�����б�
//	List<CmdParameter> refParamList = null;
	
	/**
	 * ��ǰѡ�е�����
	 */
	private MenuItem currentItem;
	
	private MenubarElementFigure figure;

	public MenubarComp getMenubar() {
		return menubar;
	}

	public static final String PROP_DATASET_ELEMENT = "dataset_element";
	
	public static final String WIDGETID_EN = "widgetid";
	public static final String DSID_EN = "dsid";

	public void setMenubar(MenubarComp menubar) {
		this.menubar = menubar;
		fireStructureChange(PROP_DATASET_ELEMENT, menubar);
	}
	
//	private String[] widgetIdArray;
	
//	/**
//	 * ��ȡ��ǰPagemeta��WidgetId����
//	 * @return
//	 */
//	private String[] getWidgetIdArray() {
//		if (null == widgetIdArray) {
////			Map<String, LfwWidget> widgetMap = LFWPersTool.getCurrentPageMeta().getWidgetMap();
////			if (widgetMap.keySet().size() > 0) {
////				Object[] array = widgetMap.keySet().toArray();
////				widgetIdArray = new String[array.length + 1];
////				widgetIdArray[0] = "";
////				for (int i = 1, n = array.length + 1; i < n; i++) {
////					widgetIdArray[i] = (String) array[i - 1];
////				}
////			} else {
////				widgetIdArray = new String[1];
////				widgetIdArray[0] = "";
////			}
//			LfwWidget[] widgets = LFWPersTool.getCurrentPageMeta().getWidgets();
//			if (widgets.length > 0) {
//				widgetIdArray = new String[widgets.length + 1];
//				widgetIdArray[0] = "";
//				for (int i = 1, n = widgets.length + 1; i < n; i++) {
//					widgetIdArray[i] = widgets[i - 1].getId();
//				}
//			} else {
//				widgetIdArray = new String[1];
//				widgetIdArray[0] = "";
//			}
//		}
//		return widgetIdArray;
//	}
//	
//	/**
//	 * ��ȡwidget�е�DsId����
//	 * @param widgetId
//	 * @return
//	 */
//	private String[] getDsIdArray(String widgetId) {
//
////		Map<String, LfwWidget> widgetMap = LFWPersTool.getCurrentPageMeta().getWidgetMap();
////		LfwWidget widget = widgetMap.get(widgetId);
////		if (null != widget && widget.getViewModels().getDatasetMap().keySet().size() > 0) {
////			Object[] dsIdArray = widget.getViewModels().getDatasetMap().keySet().toArray();
////			String[] array = new String[dsIdArray.length + 1];
////			array[0] = "";
////			for (int i = 1, n = dsIdArray.length + 1; i < n; i++) {
////				array[i] = (String) dsIdArray[i - 1];
////			}
////			
////			return array;
////		} else {
////			String[] array = new String[1];
////			array[0] = "";
////
////			return array;
////		}
//		
//		LfwWidget widget = LFWPersTool.getCurrentPageMeta().getWidget(widgetId);
//		if (null != widget && widget.getViewModels().getDatasets().length > 0) {
//			Dataset[] dsArray = widget.getViewModels().getDatasets();
//			String[] array = new String[dsArray.length + 1];
//			array[0] = "";
//			for (int i = 1, n = dsArray.length + 1; i < n; i++) {
//				array[i] = dsArray[i - 1].getId();
//			}
//			
//			return array;
//		} else {
//			String[] array = new String[1];
//			array[0] = "";
//
//			return array;
//		}
//		
//	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		int paramsCount = 0;
		PropertyDescriptor[] pds = new PropertyDescriptor[5 + paramsCount];
		pds[0] = new TextPropertyDescriptor(PROP_ID, "ID");
		pds[0].setCategory("�˵�");
		pds[1] = new TextPropertyDescriptor(PROP_ITEM_MENU, "�Ҽ��˵�");
		pds[1].setCategory("�˵�");
		pds[2] = new TextPropertyDescriptor(PROP_ITEM_ID, "ID");
		pds[2].setCategory("�˵���");
		pds[3] = new TextPropertyDescriptor(PROP_ITEM_TEXT, "��ʾ��");
		pds[3].setCategory("�˵���");
		pds[4] = new TextPropertyDescriptor(PROP_ITEM_STATEMANAGER, "״̬������");
		pds[4].setCategory("�˵���");
		
		return pds;
	}

	public Object getPropertyValue(Object id) {
		MenubarComp menubar = this.getMenubar();
		if (PROP_ID.equals(id)) {
			return menubar.getId() == null ? "" : menubar.getId();
		} 
		else if (PROP_ITEM_MENU.equals(id)) {
			return menubar.getContextMenu() == null ? "" : menubar.getContextMenu();
		} 
		else if (PROP_ITEM_ID.equals(id)) {
			if (null != currentItem)
				return currentItem.getId();
		} 
		else if (PROP_ITEM_TEXT.equals(id)) {
			if (null != currentItem)
				return currentItem.getText();
		}
		else if (PROP_ITEM_STATEMANAGER.equals(id)) {
			if (null != currentItem)
				return currentItem.getStateManager();
		}
		return "";
	}

	
	public void setPropertyValue(Object id, Object value) {
		MenubarComp menubar = this.getMenubar();
		if (PROP_ID.equals(id)) {
			menubar.setId((String) value);
		} else if (PROP_ITEM_MENU.equals(id)) {
			menubar.setContextMenu((String) value);
		} else if (PROP_ITEM_ID.equals(id)) {
			if (null != currentItem)
				currentItem.setId((String) value);
		} else if (PROP_ITEM_TEXT.equals(id)) {
			if (null != currentItem)
				currentItem.setText((String) value);
		} 
		else if (PROP_ITEM_STATEMANAGER.equals(id)) {
			if (null != currentItem)
				currentItem.setStateManager((String) value);
		} 
		MenubarEditor.getActiveMenubarEditor().refreshAllElementObj();
		MenubarEditor.getActiveMenubarEditor().setDirtyTrue();
	}

	public Object getEditableValue() {
		return this;
	}

	public void addChild(MenuItemObj obj) {
		// ����и��Ӳ˵�����ֻ�ı���Ӳ˵����Ӳ˵�����
		for (int i = 0, n = childrenList.size(); i < n; i++) {
			if (obj.getMenuItem().getId().equals(childrenList.get(i).getMenuItem().getId())) {
				childrenList.get(i).setChild(obj.getChild());
				return;
			}
		}
		childrenList.add(obj);
		// ������и��������
		if (!menubar.getMenuList().contains(obj.getMenuItem()))
			menubar.addMenuItem(obj.getMenuItem());
	}

	public List<MenuItemObj> getChildrenList() {
		return childrenList;
	}

	public boolean removeChild(MenuItemObj obj) {
		menubar.getMenuList().remove(obj.getMenuItem());
		return childrenList.remove(obj);
	}

	
	public WebElement getWebElement() {
		return menubar;
	}

	public MenubarElementFigure getFigure() {
		return figure;
	}

	public void setFigure(MenubarElementFigure figure) {
		this.figure = figure;
	}

	public void setCurrentItem(MenuItem currentItem) {
		this.currentItem = currentItem;
	}
}
