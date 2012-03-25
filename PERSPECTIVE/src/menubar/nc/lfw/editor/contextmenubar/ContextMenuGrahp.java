package nc.lfw.editor.contextmenubar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LfwBaseGraph;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.perspective.listener.JsEventLabel;
import nc.uap.lfw.perspective.listener.JsListenerLabel;
import nc.uap.lfw.perspective.listener.ListenerElementFigure;
import nc.uap.lfw.perspective.listener.ListenerElementObj;

import org.eclipse.draw2d.IFigure;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ContextMenuGrahp extends LfwBaseGraph {

	private static final long serialVersionUID = 8325848396222128312L;
	private List<MenuElementObj> childrenList = new ArrayList<MenuElementObj>();

	public ContextMenuGrahp() {
		super();
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor[] pds = new PropertyDescriptor[1];
		pds[0] = new TextPropertyDescriptor(PROP_ID, "ID");
		for (int i = 0; i < pds.length; i++) {
			pds[i].setCategory("����");
		}
		return pds;
	}

	public Object getPropertyValue(Object id) {
		if (PROP_ID.equals(id)) {
			ContextMenuElementObj menubarObj = (ContextMenuElementObj) this.getCells()
					.get(0);
			return menubarObj.getMenubar().getId();
		}
		return null;
	}

	public void addMenu(MenuElementObj cell) {
		cell.setGraph(this);
		childrenList.add(cell);
		fireStructureChange(PROP_CHILD_ADD, cell);
	}

	public boolean removeMenu(MenuElementObj cell) {
		boolean b = childrenList.remove(cell);
		cell.setGraph(null);
		if (b) {
			fireStructureChange(PROP_CHILD_REMOVE, cell);
		}
		return b;
	}

	public List<MenuElementObj> getAllChildren() {
		List<MenuElementObj> eleList = new ArrayList<MenuElementObj>();
		Iterator<MenuElementObj> it = childrenList.iterator();
		while (it.hasNext()) {
			MenuElementObj ele = it.next();
			eleList.add(ele);
		}
		return eleList;
	}

	public void setPropertyValue(Object id, Object value) {

	}

	public List<MenuElementObj> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<MenuElementObj> childrenList) {
		this.childrenList = childrenList;
	}

	/**
	 * ȡ������ͼ�ε���������ѡ��״̬
	 */
	public void unSelectAllLabels() {
		super.unSelectAllLabels();
		// ���˵�
		List<LfwElementObjWithGraph> menuCells = getCells();
		for (int i = 0, n = menuCells.size(); i < n; i++) {
			if (menuCells.get(i) instanceof ContextMenuElementObj) {
				ContextMenuElementObj menubarObj = (ContextMenuElementObj) menuCells.get(i);
				menubarObj.getFigure().unSelectAllLabels();
			}
		}
		// �Ӳ˵���
		for (int i = 0, n = childrenList.size(); i < n; i++) {
			MenuElementObj menuObj = childrenList.get(i);
			menuObj.getFigure().unSelectAllLabels();
		}
	}
	
	/**
	 * ������ʾListener����
	 */
	public void reloadListenerFigure(WebElement component) {
		if(component instanceof MenuItem){
			MenuItem menuItem = (MenuItem) component;
			ContextMenuEditor.getActiveMenubarEditor().setCurrentListenerMenuItem(menuItem);
			ListenerElementObj listenerObj = ContextMenuEditor.getActiveMenubarEditor().getJsListenerObj();
			ListenerElementFigure figure = listenerObj.getFigure();
			if( figure.getTitleFigure().getChildren().size() > 1){
				IFigure title = (IFigure) figure.getTitleFigure().getChildren().get(1);
				figure.getTitleFigure().remove(title);
			}
			if (null != menuItem) {
				figure.setTitleText(menuItem.getText(), menuItem.getText());
				refreshListenerItems(figure, menuItem, listenerObj);
			}
		}
		else if(component instanceof ContextMenuComp){
			
			ContextMenuEditor.getActiveMenubarEditor().setCurrentListenerMenuItem(null);
			
			ContextMenuComp menubar = (ContextMenuComp) component;
			ListenerElementObj listenerObj = ContextMenuEditor.getActiveMenubarEditor().getJsListenerObj();
			ListenerElementFigure figure = listenerObj.getFigure();
			if(figure.getTitleFigure().getChildren().size() > 1){
				IFigure title = (IFigure) figure.getTitleFigure().getChildren().get(1);
				figure.getTitleFigure().remove(title);
			}
			refreshListenerItems(figure, menubar, listenerObj);
		}
	}
	
	/**
	 * ˢ��Listener�����������ʾ
	 * @param figure
	 * @param menuItem
	 */
	private void refreshListenerItems(ListenerElementFigure figure, WebComponent menuItem, ListenerElementObj listenerObj) {
		// �Ƴ�ȫ��ԭ����
		listenerObj.getListenerMap().clear();
		while (figure.getContentFigure().getChildren().size() > 0) {
			Object child = figure.getContentFigure().getChildren().get(0);
			if (child instanceof JsListenerLabel) {
				figure.getContentFigure().remove((JsListenerLabel) child);
				figure.setHeight(figure.getHeight() - figure.LINE_HEIGHT);
			}
			else if(child instanceof JsEventLabel){
				figure.getContentFigure().remove((JsEventLabel) child);
				figure.setHeight(figure.getHeight() - figure.LINE_HEIGHT);
			}
		}
		// ��ʾ��������
		addItems(figure, menuItem, listenerObj);
	}

	/**
	 * ��ʾ��������
	 * @param figure
	 * @param menuItem
	 */
	@SuppressWarnings("unchecked")
	private void addItems(ListenerElementFigure figure, WebComponent menuItem, ListenerElementObj listenerObj) {
		Map<String, JsListenerConf> map = menuItem.getListenerMap();
		for (String key : map.keySet()) {
			JsListenerConf listener = map.get(key);
			listenerObj.getListenerMap().put(listener.getId(), listener);
			JsListenerLabel label = new JsListenerLabel("[" + listener.getClass().getSimpleName() + "]" + listener.getId(), listener);
			figure.addToContent(label);
			figure.setHeight(figure.getHeight() + figure.LINE_HEIGHT);
			figure.addListenerLabelListener(label);
		}
	}

}
