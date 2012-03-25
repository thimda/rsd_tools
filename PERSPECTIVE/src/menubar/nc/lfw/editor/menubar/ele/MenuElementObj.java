package nc.lfw.editor.menubar.ele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.contextmenubar.ContextMenuEditor;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.figure.MenuItemElementFigure;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * 
 * @author guoweic
 * 
 */
public class MenuElementObj extends LfwElementObjWithGraph {
	private static final long serialVersionUID = -6950996276310048550L;
	private MenuItem menuItem;
	private List<MenuItemObj> childrenList = new ArrayList<MenuItemObj>();
	
	// ����Event�����б�
//	List<CmdParameter> refParamList = null;
	
	/**
	 * ���˵�����
	 */
	private LfwElementObjWithGraph parentElementObj;

	private MenuItemElementFigure figure;
	
	private int level = 1;

	public static final String PROP_ITEM_ID = "ITEM_ID";
	public static final String PROP_ITEM_TEXT = "ITEM_TEXT";
	public static final String PROP_ITEM_PARAM = "ITEM_PARAM_";
	
	public static final String MENU_ITEM_ADD = "MENU_ITEM_ADD";

	public static final String PROP_DATASET_ELEMENT = "dataset_element";
	
	public static final String WIDGETID_EN = "widgetid";
	public static final String DSID_EN = "dsid";
	
	/**
	 * ��ǰѡ�е�����
	 */
	private MenuItem currentItem;

	public void addChild(MenuItemObj obj) {
		childrenList.add(obj);
		fireStructureChange(MENU_ITEM_ADD, this);
	}

	public void removeChild(MenuItemObj obj) {
		menuItem.getChildList().remove(obj.getMenuItem());
		childrenList.remove(obj);
		fireStructureChange(MENU_ITEM_ADD, this);
	}

	public List<MenuItemObj> getChildrenList() {
		return childrenList;
	}

	public List<MenuItem> getChildrenItems() {
		if (childrenList == null)
			return null;
		List<MenuItem> list = new ArrayList<MenuItem>();
		Iterator<MenuItemObj> it = childrenList.iterator();
		while (it.hasNext()) {
			MenuItemObj item = it.next();
			list.add(item.getMenuItem());
		}
		return list;
	}
	
	private String[] widgetIdArray;
	
	/**
	 * ��ȡ��ǰPagemeta��WidgetId����
	 * @return
	 */
	private String[] getWidgetIdArray() {
		if (null == widgetIdArray) {
//			Map<String, LfwWidget> widgetMap = LFWPersTool.getCurrentPageMeta().getWidgetMap();
//			if (widgetMap.keySet().size() > 0) {
//				Object[] array = widgetMap.keySet().toArray();
//				widgetIdArray = new String[array.length + 1];
//				widgetIdArray[0] = "";
//				for (int i = 1, n = array.length + 1; i < n; i++) {
//					widgetIdArray[i] = (String) array[i - 1];
//				}
//			} else {
//				widgetIdArray = new String[1];
//				widgetIdArray[0] = "";
//			}
			LfwWidget[] widgets = LFWPersTool.getCurrentPageMeta().getWidgets();
			if (widgets.length > 0) {
				widgetIdArray = new String[widgets.length + 1];
				widgetIdArray[0] = "";
				for (int i = 1, n = widgets.length + 1; i < n; i++) {
					widgetIdArray[i] = widgets[i - 1].getId();
				}
			} else {
				widgetIdArray = new String[1];
				widgetIdArray[0] = "";
			}
		}
		return widgetIdArray;
	}
	
	/**
	 * ��ȡwidget�е�DsId����
	 * @param widgetId
	 * @return
	 */
	private String[] getDsIdArray(String widgetId) {

//		Map<String, LfwWidget> widgetMap = LFWPersTool.getCurrentPageMeta().getWidgetMap();
//		LfwWidget widget = widgetMap.get(widgetId);
//		if (null != widget && widget.getViewModels().getDatasetMap().keySet().size() > 0) {
//			Object[] dsIdArray = widget.getViewModels().getDatasetMap().keySet().toArray();
//			String[] array = new String[dsIdArray.length + 1];
//			array[0] = "";
//			for (int i = 1, n = dsIdArray.length + 1; i < n; i++) {
//				array[i] = (String) dsIdArray[i - 1];
//			}
//			
//			return array;
//		} else {
//			String[] array = new String[1];
//			array[0] = "";
//
//			return array;
//		}
		LfwWidget widget = LFWPersTool.getCurrentPageMeta().getWidget(widgetId);
		if (null != widget && widget.getViewModels().getDatasets().length > 0) {
			Dataset[] dsArray = widget.getViewModels().getDatasets();
			String[] array = new String[dsArray.length + 1];
			array[0] = "";
			for (int i = 1, n = dsArray.length + 1; i < n; i++) {
				array[i] = dsArray[i - 1].getId();
			}
			
			return array;
		} else {
			String[] array = new String[1];
			array[0] = "";

			return array;
		}
	}
	
	/**
	 * ��ȡ�����б��е�WidgetIdֵ
	 * @return
	private String getCurrentWidgetId() {
		for (CmdParameter parameter : refParamList) {
			if (parameter.getName().toLowerCase().indexOf(WIDGETID_EN) != -1) {  // ��WidgetId����
				return parameter.getValue() == null ? "" : parameter.getValue();
			}
		}
		return "";
	}
	 */

	public IPropertyDescriptor[] getPropertyDescriptors() {
		int paramsCount = 0;
		// ��ղ����б�
//		refParamList = null;
//		if (null != currentItem) {
//			Map<String, JsListenerConf> listenerMap = currentItem.getListenerMap();
//			for (String listenerKey : listenerMap.keySet()) {
//				JsListenerConf listener = listenerMap.get(listenerKey);
//				Map<String, EventHandlerConf> eventMap = listener.getEventHandlerMap();
//				for (String eventKey : eventMap.keySet()) {
//					if (eventMap.get(eventKey) instanceof RefEventHandlerConf) {
//						refParamList = ((RefEventHandlerConf)eventMap.get(eventKey)).getCmdParamList();
//						paramsCount += refParamList.size();
//					}
//				}
//			}
//		}
		PropertyDescriptor[] pds = new PropertyDescriptor[3 + paramsCount];
		pds[0] = new TextPropertyDescriptor(PROP_ID, "ID");
		pds[0].setCategory("�˵�");
		pds[1] = new TextPropertyDescriptor(PROP_ITEM_ID, "ID");
		pds[1].setCategory("�˵���");
		pds[2] = new TextPropertyDescriptor(PROP_ITEM_TEXT, "��ʾ��");
		pds[2].setCategory("�˵���");
		//TODO
//		if (null == refParamList)
//			refParamList = new ArrayList<CmdParameter>();
//		if (null != currentItem) {
//			int index = 3;
//			for (Parameter parameter : refParamList) {
//				if (parameter.getName().toLowerCase().indexOf(WIDGETID_EN) != -1) {  // ��WidgetId����
//					pds[index] = new ComboBoxPropertyDescriptor(PROP_ITEM_PARAM + parameter.getName(), parameter.getName(), getWidgetIdArray());
//				} else if (parameter.getName().toLowerCase().indexOf(DSID_EN) != -1) {  // ��DsId����
//					//TODO
//					String currentWidgetId = getCurrentWidgetId();
//					pds[index] = new ComboBoxPropertyDescriptor(PROP_ITEM_PARAM + parameter.getName(), parameter.getName(), getDsIdArray(currentWidgetId));
//				} else {
//					pds[index] = new TextPropertyDescriptor(PROP_ITEM_PARAM + parameter.getName(), parameter.getName());
//				}
//				pds[index].setCategory("�˵������");
//				
//				index++;
//			}
//		}
		return pds;
	}

	public Object getPropertyValue(Object id) {
		MenuItem menuItem = this.getMenuItem();
		if (PROP_ID.equals(id)) {
			return menuItem.getId() == null ? "" : menuItem.getId();
		} else if (PROP_ITEM_ID.equals(id)) {
			if (null != currentItem)
				return currentItem.getId();
		} else if (PROP_ITEM_TEXT.equals(id)) {
			if (null != currentItem)
				return currentItem.getText();
		} else if (((String)id).indexOf(PROP_ITEM_PARAM) != -1) {
//			if (null != currentItem) {
//				String name = ((String)id).substring(((String)id).lastIndexOf('_') + 1);
//				for (CmdParameter parameter : refParamList) {
//					if (name.equals(parameter.getName())) {
//						if (name.toLowerCase().indexOf(WIDGETID_EN) != -1) {  // ��WidgetId����
//							for (int i = 0, n = widgetIdArray.length; i < n; i++) {
//								if (null != parameter.getValue() && widgetIdArray[i].equals(parameter.getValue())) {
//									return i;
//								}
//							}
//							return 0;
//						} else if (name.toLowerCase().indexOf(DSID_EN) != -1) {  // ��DsId����
//							//TODO
//							String currentWidgetId = getCurrentWidgetId();
//							String[] dsIdArray = getDsIdArray(currentWidgetId);
//							for (int i = 0, n = dsIdArray.length; i < n; i++) {
//								if (null != parameter.getValue() && dsIdArray[i].equals(parameter.getValue())) {
//									return i;
//								}
//							}
//							return 0;
//						} else {
//							return parameter.getValue() == null ? "" : parameter.getValue();
//						}
//					}
//				}
//				return "";
//			}
		}
		return "";
	}

	
	public void setPropertyValue(Object id, Object value) {
		MenuItem menuItem = this.getMenuItem();
		if (PROP_ID.equals(id)) {
			menuItem.setId((String) value);
		} else if (PROP_ITEM_ID.equals(id)) {
			if (null != currentItem)
				currentItem.setId((String) value);
		} else if (PROP_ITEM_TEXT.equals(id)) {
			if (null != currentItem)
				currentItem.setText((String) value);
		} else if (((String)id).indexOf(PROP_ITEM_PARAM) != -1) {
//			if (null != currentItem) {
//				String name = ((String)id).substring(((String)id).lastIndexOf('_') + 1);
//				for (CmdParameter parameter : refParamList) {
//					if (name.equals(parameter.getName())) {
//						//TODO
//						if (name.toLowerCase().indexOf(WIDGETID_EN) != -1) {  // ��WidgetId����
//							//TODO
//							parameter.setValue(widgetIdArray[((Integer) value).intValue()]);
//							// ������ʾ��������
//							MenuItemElementFigure.reloadPropertySheet(this);
//						} else if (name.toLowerCase().indexOf(DSID_EN) != -1) {  // ��DsId����
//							//TODO
//							parameter.setValue(getDsIdArray(getCurrentWidgetId())[((Integer) value).intValue()]);
//						} else {
//							parameter.setValue((String) value);
//						}
//					}
//				}
//			}
		}
		MenubarEditor me = MenubarEditor.getActiveMenubarEditor();
		if(me != null){
			me.refreshAllElementObj();
			me.setDirtyTrue();
		}else{
			ContextMenuEditor.getActiveMenubarEditor().refreshAllElementObj();
			ContextMenuEditor.getActiveMenubarEditor().setDirtyTrue();
		}
	}

	public int getElementHeight() {
		int height = this.childrenList.size() * 22;
		if (height < 120)
			return 120;
		return height;
	}

	
	public WebElement getWebElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public MenuItemElementFigure getFigure() {
		return figure;
	}

	public void setFigure(MenuItemElementFigure figure) {
		this.figure = figure;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public LfwElementObjWithGraph getParentElementObj() {
		return parentElementObj;
	}

	public void setParentElementObj(LfwElementObjWithGraph parentElementObj) {
		this.parentElementObj = parentElementObj;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setCurrentItem(MenuItem currentItem) {
		this.currentItem = currentItem;
	}
}
