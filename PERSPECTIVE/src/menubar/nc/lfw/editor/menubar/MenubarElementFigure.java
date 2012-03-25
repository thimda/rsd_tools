package nc.lfw.editor.menubar;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.lfw.editor.menubar.wizard.MenuItemParamPanel;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * 
 * @author guoweic
 * 
 */
public class MenubarElementFigure extends LFWBaseRectangleFigure {

	private MenubarElementObj menubarObj;

	// 默认大小
	private Dimension dimen;
	// 总高度
	private int height = 0;

	private static Color bgColor = new Color(null, 239, 255, 150);

	public MenubarElementFigure(LfwElementObjWithGraph ele) {
		super(ele);
		menubarObj = (MenubarElementObj) ele;
		setTypeLabText("<<菜单>>");
		setBackgroundColor(bgColor);
		menubarObj.setFigure(this);
		setTitleText(menubarObj.getMenubar().getId(), menubarObj.getMenubar().getId());
		addItems();
		markError(menubarObj.validate());
		// 设置大小和位置
		Point point = menubarObj.getLocation();
		dimen = menubarObj.getSize();
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width,
				dimen.height < this.height ? this.height : dimen.height));

	}

	/**
	 * 显示所有子项
	 */
	private void addItems() {
		MenubarComp menubar = menubarObj.getMenubar();
		List<MenuItem> itemList = menubar.getMenuList();
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
	 * 增加子项Label的事件
	 * 
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
				// 取消所有其它子项选中状态
				LFWBaseEditor.getActiveEditor().getGraph().unSelectAllLabels();
				// 选中该子项
				selectLabel(currentLabel);

				// 显示属性
				MenuItem currentItem = (MenuItem) ((MenuItemLabel) currentLabel).getEditableObj();
				menubarObj.setCurrentItem(currentItem);
				//新版显示事件
				LFWBaseEditor.getActiveEditor().getViewPage().setWebElement(currentItem);
				LFWBaseEditor.getActiveEditor().getViewPage().addEventPropertiesView(currentItem.getEventConfs(), LFWAMCPersTool.getCurrentWidget().getControllerClazz());
				
				// 重新显示属性内容
				reloadPropertySheet(menubarObj);
				
				// 重新显示Listener内容
				((MenubarGraph)MenubarEditor.getActiveMenubarEditor().getGraph())
						.reloadListenerFigure((MenuItem)currentLabel.getEditableObj());
			}
		});
	}

	/**
	 * 增加子项
	 * 
	 * @param signal
	 */
	public void addItem(MenuItem menuItem) {
		MenubarComp menubar = MenubarEditor.getActiveMenubarEditor().getMenubarTemp();
		int index = 0;
		if (null != menubar.getMenuList())
			index = menubar.getMenuList().size();
		menubar.addMenuItem(menuItem);
		
		MenuItemLabel label = new MenuItemLabel(menuItem);
		addToContent(label, index);
		addItemLabelListener(label);
		this.height += LINE_HEIGHT;
		resizeHeight();
	}

	/**
	 * 删除子项
	 * 
	 * @param label
	 */
	public void deleteItem(MenuItemLabel label) {
		MenuItem item = (MenuItem) label.getEditableObj();
		MenubarComp menubar = MenubarEditor.getActiveMenubarEditor().getMenubarTemp();
		if (menubar.getMenuList().contains(item)) {
			menubar.getMenuList().remove(item);
			// 删除相关连接
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
	 * 刷新图像
	 */
	public void refresh() {
		IFigure title = (IFigure) getTitleFigure().getChildren().get(1);
		getTitleFigure().remove(title);
		setTitleText(menubarObj.getMenubar().getId(), menubarObj.getMenubar().getId());
		refreshItems();
	}
	
	/**
	 * 刷新所有子项的显示
	 */
	public void refreshItems() {
		// 移除全部原有项
		while (getContentFigure().getChildren().size() > 0) {
			Object child = getContentFigure().getChildren().get(0);
			if (child instanceof MenuItemLabel) {
				getContentFigure().remove((MenuItemLabel) child);
				this.height -= LINE_HEIGHT;
			}
		}
		// 显示所有子项
		addItems();
	}
	
	/**
	 * 重新绘制默认子项
	 * 
	 * @param defaultMenuItems
	 * @param packageName
	 * @param sourceFolder
	 * @param classPrefix
	 * @param paramPanels
	 */
	public void resetDefaultMenuItems(List<DefaultItem> defaultMenuItems, String packageName
			, String sourceFolder, String classPrefix, Map<DefaultItem, MenuItemParamPanel> paramPanels) {
//		//TODO 原有项将全部被删除
//		while (getContentFigure().getChildren().size() > 0) {
//			Object child = getContentFigure().getChildren().get(0);
//			if (child instanceof MenuItemLabel) {
//				deleteItem((MenuItemLabel) child);
//				// 删除Command
//				removeCommands((MenuItem)((MenuItemLabel) child).getEditableObj());
//			}
//		}
		createNewMenuItems(null, paramPanels, sourceFolder, packageName, classPrefix);
	}
	
	/**
	 * 创建新的MenuItem
	 * @param item
	 * @param defaultMenuItems
	private void createNewMenuItems(MenuItem item, List<DefaultItem> defaultMenuItems) {
		for (DefaultItem defaultItem : defaultMenuItems) {
//			if (defaultItem.getChildList() == null || defaultItem.getChildList().size() == 0) {  // 没有子菜单
				MenuItem menuItem = defaultItem.generateMenuItem();
				// 设置ID
				String id = "";
				if (null != item) {
					id = item.getId() + menuItem.getId();
					item.addMenuItem(menuItem);
				} else {
					id = menubarObj.getMenubar().getId() + menuItem.getId();
				}
				menuItem.setId(id);
				
				//TODO
//				DefaultMenuItemCreator.generateListener(menuItem, defaultItem);
				
				Map<String, JsListenerConf> listenerMap = menuItem.getListenerMap();
				for (String listenerKey : listenerMap.keySet()) {
					Map<String, EventHandlerConf> eventMap = listenerMap.get(listenerKey).getEventHandlerMap();
					for (String eventKey : eventMap.keySet()) {
						if (eventMap.get(eventKey) instanceof RefEventHandlerConf) {
							String refId = ((RefEventHandlerConf)eventMap.get(eventKey)).getRefId();
							String commandId = menuItem.getId() + "_" + refId.substring(refId.lastIndexOf('.') + 1);
							//TODO 新建Command
							RefCommand command = new RefCommand();
							command.setId(commandId);
							command.setRefId(refId);
							LFWPersTool.getCurrentPageMeta().addCommand(command);
							//TODO 修改menuItem中Listener的event的refId为新的Command的ID
							((RefEventHandlerConf)eventMap.get(eventKey)).setRefId(commandId);
						}
					}
				}
				
				addItem(menuItem);
//			} 
//			else {  // 有子菜单
//				// 创建子菜单
//				MenuItem menuItem = defaultItem.generateMenuItem();
//				createNewMenuItems(menuItem, defaultItem.getChildList());
//			}
		}
	}
	 */
	
	/**
	 * 创建新的MenuItem
	 * @param item
	 * @param paramPanels
	 * @param packageName
	 * @param classPrefix
	 */
	private void createNewMenuItems(MenuItem item, Map<DefaultItem, MenuItemParamPanel> paramPanels, String sourceFolder, String packageName, String classPrefix) {
		for (DefaultItem defaultItem : paramPanels.keySet()) {
			MenuItem menuItem = defaultItem.generateMenuItem();
			// 设置ID
			String id = "";
			if (null != item) {
//				id = item.getId() + menuItem.getId();
				id = menuItem.getId();
				item.addMenuItem(menuItem);
			} else {
//				id = menubarObj.getMenubar().getId() + menuItem.getId();
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
	 * 重新设置高度
	 */
	private void resizeHeight() {
		setSize(dimen.width, dimen.height < this.height ? this.height
				: dimen.height);
	}

	
	protected String getTypeText() {
		return null;
	}

}
