package nc.uap.lfw.aciton;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.action.Action;

/**
 * ע�ᰴť
 * @author zhangxya
 *
 */
public class RegisterMenuItemAction extends Action {

	public RegisterMenuItemAction() {
		super(WEBProjConstants.REGISTER_MENU_ITEM);
	}

	public void run() {
		registerMenuItem();
	}
	
	public void registerMenuItem() {
		PageMeta pm  = LFWPersTool.getCurrentPageMeta();
		if(pm == null)
			return;
		MenubarComp[] menubarComps = pm.getViewMenus().getMenuBars();
		List<MenuItem> allMenuItems = new ArrayList<MenuItem>();
		for (int i = 0; i < menubarComps.length; i++) {
			MenubarComp menubar = menubarComps[i];
			List<MenuItem> menuItems = menubar.getMenuList();
			allMenuItems.addAll(menuItems);
		}
//		if(pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE) != null){
//			String funnode = (String) pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue();
//			if(funnode != null){
//				NCConnector.registerButtonAction(allMenuItems, funnode);
//			}
//		}
	}
	
}
