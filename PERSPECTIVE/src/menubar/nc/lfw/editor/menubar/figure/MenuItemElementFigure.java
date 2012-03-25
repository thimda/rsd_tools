package nc.lfw.editor.menubar.figure;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.contextmenubar.ContextMenuEditor;
import nc.lfw.editor.contextmenubar.ContextMenuGrahp;
import nc.lfw.editor.menubar.DefaultItem;
import nc.lfw.editor.menubar.MenuItemLabel;
import nc.lfw.editor.menubar.MenubarConnector;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.MenubarGraph;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.wizard.MenuItemParamPanel;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class MenuItemElementFigure extends LFWBaseRectangleFigure {

	private MenuElementObj menuObj;
	
	// Ĭ�ϴ�С
	private Dimension dimen;
	// �ܸ߶�
	private int height = 0;

	private static Color bgColor = new Color(null, 239, 255, 150);
	
	public MenuItemElementFigure(LfwElementObjWithGraph ele) {
		super(ele);
		menuObj = (MenuElementObj) ele;
		setTypeLabText("<<�Ӳ˵�>>");
		setBackgroundColor(bgColor);
		menuObj.setFigure(this);
		if (null != menuObj.getMenuItem().getId())
			setTitleText(menuObj.getMenuItem().getId(), menuObj.getMenuItem().getId());
		else
			setTitleText("", "");
		addItems();
		markError(menuObj.validate());
		// ���ô�С��λ��
		Point point = menuObj.getLocation();
		dimen = menuObj.getSize();
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width, dimen.height < this.height ? this.height : dimen.height));
		
	}
	
	/**
	 * ��ʾ��������
	 */
	private void addItems() {
		MenuItem menuItem = menuObj.getMenuItem();
		List<MenuItem> itemList = menuItem.getChildList();
		if (itemList != null) {
			Iterator<MenuItem> it = itemList.iterator();
			while (it.hasNext()) {
				MenuItem item = it.next();
				MenuItemLabel label = new MenuItemLabel(item);
				addToContent(label);
				this.height += LINE_HEIGHT;
				addItemLabelListener(label);
			}
		}
	}
	
	/**
	 * ��������Label���¼�
	 * @param label
	 */
	private void addItemLabelListener(MenuItemLabel label) {
		label.addMouseListener(new MouseListener.Stub() {
			public void mouseDoubleClicked(MouseEvent e) {
				
			}
			public void mouseReleased(MouseEvent e) {
				
			}
			public void mousePressed(MouseEvent e) {
				MenuItemLabel currentLabel = (MenuItemLabel) e.getSource();
				// ȡ��������������ѡ��״̬
				LFWBaseEditor.getActiveEditor().getGraph().unSelectAllLabels();
				// ѡ�и�����
				selectLabel(currentLabel);
				
				// ��ʾ����
				MenuItem currentItem = (MenuItem) ((MenuItemLabel) currentLabel).getEditableObj();
				menuObj.setCurrentItem(currentItem);
				//�°���ʾ�¼�
				LFWBaseEditor.getActiveEditor().getViewPage().setWebElement(currentItem);
				LFWBaseEditor.getActiveEditor().getViewPage().addEventPropertiesView(currentItem.getEventConfs(), LFWAMCPersTool.getCurrentWidget().getControllerClazz());
				
				// ������ʾ��������
				reloadPropertySheet(menuObj);
				
				//�˵��������е�menuItem
				if(MenubarEditor.getActiveMenubarEditor() != null){
					// ������ʾListener����
					((MenubarGraph)MenubarEditor.getActiveMenubarEditor().getGraph())
							.reloadListenerFigure((MenuItem)currentLabel.getEditableObj());
				}
				//�Ҽ��˵��е�menuitem
				else if(ContextMenuEditor.getActiveMenubarEditor() != null){
					((ContextMenuGrahp)ContextMenuEditor.getActiveMenubarEditor().getGraph())
					.reloadListenerFigure((MenuItem)currentLabel.getEditableObj());
				}
				
			}
		});
	}
	
	/**
	 * ��������
	 * @param signal
	 */
	public void addItem(MenuItem menuItem) {
		MenuItem currentMenuItem = menuObj.getMenuItem();
		int index = 0;
		if (null != currentMenuItem.getChildList())
			index = currentMenuItem.getChildList().size();
		currentMenuItem.addMenuItem(menuItem);
		MenuItemLabel label = new MenuItemLabel(menuItem);
		addToContent(label, index);
		addItemLabelListener(label);
		this.height += LINE_HEIGHT;
		resizeHeight();
	}
	
	/**
	 * ɾ������
	 * @param label
	 */
	public void deleteItem(MenuItemLabel label) {
		MenuItem item = (MenuItem) label.getEditableObj();
		MenuItem currentMenuItem = menuObj.getMenuItem();
		if (currentMenuItem.getChildList().contains(item)) {
			currentMenuItem.getChildList().remove(item);
			// ɾ���������
			MenubarEditor editor = MenubarEditor.getActiveMenubarEditor();
			MenubarConnector connector = editor.getConnector(item.getId());
			if (null != connector) {
				editor.removeConnector(item.getId());
				connector.disConnect();
			}
		}
		
		getContentFigure().remove(label);
		this.height -= LINE_HEIGHT;
		resizeHeight();
	}
	
	/**
	 * ˢ��ͼ��
	 */
	public void refresh() {
		IFigure title = (IFigure) getTitleFigure().getChildren().get(1);
		getTitleFigure().remove(title);
		setTitleText(menuObj.getMenuItem().getId(), menuObj.getMenuItem().getId());
		refreshItems();
	}
	
	/**
	 * ˢ�������������ʾ
	 */
	public void refreshItems() {
		// �Ƴ�ȫ��ԭ����
		while (getContentFigure().getChildren().size() > 0) {
			Object child = getContentFigure().getChildren().get(0);
			if (child instanceof MenuItemLabel) {
				getContentFigure().remove((MenuItemLabel) child);
				this.height -= LINE_HEIGHT;
			}
		}
		// ��ʾ��������
		addItems();
	}
	
	/**
	 * ���»���Ĭ������
	 * 
	 * @param defaultMenuItems
	 * @param packageName
	 * @param sourceFolder
	 * @param classPrefix
	 * @param paramPanels
	 */
	public void resetDefaultMenuItems(List<DefaultItem> defaultMenuItems, String packageName, 
			String sourceFolder, String classPrefix, Map<DefaultItem, MenuItemParamPanel> paramPanels) {
//		//TODO ԭ���ȫ����ɾ��
//		while (getContentFigure().getChildren().size() > 0) {
//			Object child = getContentFigure().getChildren().get(0);
//			if (child instanceof MenuItemLabel) {
//				deleteItem((MenuItemLabel) child);
//				// ɾ��Command
//				removeCommands((MenuItem)((MenuItemLabel) child).getEditableObj());
//			}
//		}
		createNewMenuItems(null, paramPanels, sourceFolder, packageName, classPrefix);
	}
	
//	private void removeCommands(MenuItem item) {
//		PageMeta pagemeta = LFWPersTool.getCurrentPageMeta();
//		Map<String, JsListenerConf> listenerMap = item.getListenerMap();
//		for (String listenerKey : listenerMap.keySet()) {
//			Map<String, EventHandlerConf> eventMap = listenerMap.get(listenerKey).getEventHandlerMap();
//			for (String eventKey : eventMap.keySet()) {
//				if (eventMap.get(eventKey) instanceof RefEventHandlerConf) {
//					String refId = ((RefEventHandlerConf)eventMap.get(eventKey)).getRefId();
//					pagemeta.removeCommand(refId);
//				}
//			}
//		}
//	}
	
	/**
	 * �����µ�MenuItem
	 * @param item
	 * @param defaultMenuItems
	private void createNewMenuItems(MenuItem item, List<DefaultItem> defaultMenuItems) {
		for (DefaultItem defaultItem : defaultMenuItems) {
			MenuItem menuItem = defaultItem.generateMenuItem();
			// ����ID
			String id = "";
			if (null != item) {
				id = item.getId() + menuItem.getId();
				item.addMenuItem(menuItem);
			} else {
				id = menuObj.getMenuItem().getId() + menuItem.getId();
			}
			menuItem.setId(id);
			
			//TODO
//			DefaultMenuItemCreator.generateListener(menuItem, defaultItem);
			
			Map<String, JsListenerConf> listenerMap = menuItem.getListenerMap();
			for (String listenerKey : listenerMap.keySet()) {
				Map<String, EventHandlerConf> eventMap = listenerMap.get(listenerKey).getEventHandlerMap();
				for (String eventKey : eventMap.keySet()) {
					if (eventMap.get(eventKey) instanceof RefEventHandlerConf) {
						String refId = ((RefEventHandlerConf)eventMap.get(eventKey)).getRefId();
						String commandId = menuItem.getId() + "_" + refId.substring(refId.lastIndexOf('.') + 1);
						//TODO �½�Command
						RefCommand command = new RefCommand();
						command.setId(commandId);
						command.setRefId(refId);
						LFWPersTool.getCurrentPageMeta().addCommand(command);
						//TODO �޸�menuItem��Listener��event��refIdΪ�µ�Command��ID
						((RefEventHandlerConf)eventMap.get(eventKey)).setRefId(commandId);
					}
				}
			}
			
			addItem(menuItem);
		}
	}
	 */
	
	/**
	 * �����µ�MenuItem
	 * @param item
	 * @param paramPanels
	 * @param packageName
	 * @param classPrefix
	 */
	private void createNewMenuItems(MenuItem item, Map<DefaultItem, MenuItemParamPanel> paramPanels, String sourceFolder, String packageName, String classPrefix) {
		for (DefaultItem defaultItem : paramPanels.keySet()) {
			MenuItem menuItem = defaultItem.generateMenuItem();
			// ����ID
			String id = "";
			if (null != item) {
//				id = item.getId() + menuItem.getId();
				id = menuItem.getId();
				item.addMenuItem(menuItem);
			} else {
//				id = menuObj.getMenuItem().getId() + menuItem.getId();
				id = menuItem.getId();
			}
//			menuItem.setId(id);
			
			String listenerId = "menu_item_" + id + "_listener";
			String genClazz = classPrefix + defaultItem.getSuperClazz().substring(defaultItem.getSuperClazz().lastIndexOf('.') + 1);
			
			JsListenerConf listener = paramPanels.get(defaultItem).generateListener(sourceFolder, packageName, genClazz, defaultItem, listenerId);
			menuItem.addListener(listener);
			
			addItem(menuItem);
		}
	}
	
	/**
	 * �������ø߶�
	 */
	private void resizeHeight() {
		setSize(dimen.width, dimen.height < this.height ? this.height : dimen.height);
	}

	
	protected String getTypeText() {
		return null;
	}
	
}
