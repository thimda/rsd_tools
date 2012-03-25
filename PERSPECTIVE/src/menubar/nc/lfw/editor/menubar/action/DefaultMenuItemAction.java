package nc.lfw.editor.menubar.action;

import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.common.tools.LfwGlobalEditorInfo;
import nc.lfw.editor.menubar.DefaultItem;
import nc.lfw.editor.menubar.ListenerClassInfo;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.lfw.editor.menubar.page.MenuItemPropertiesView;
import nc.lfw.editor.menubar.wizard.DefaultMenuItemWizard;
import nc.lfw.editor.menubar.wizard.MenuItemParamPanel;
import nc.uap.lfw.editor.common.tools.LFWTool;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 设置默认菜单项 操作
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemAction extends Action {
	
	private MenubarElementObj menubarObj = null;
	
	private MenuElementObj menuObj = null;
	
	public DefaultMenuItemAction(MenubarElementObj menubarObj) {
//		super("设置默认项");
		super("增加默认项");
		this.menubarObj = menubarObj;
	}
	
	public DefaultMenuItemAction(MenuElementObj menuObj) {
//		super("设置默认项");
		super("增加默认项");
		this.menuObj = menuObj;
	}

	
	public void run() {
		Shell shell = new Shell(Display.getCurrent());
		
		DefaultMenuItemWizard wizard = new DefaultMenuItemWizard();
		
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.setPageSize(700, 450);
		if (null != menubarObj) {  // 为Menubar增加默认项
			// 构造Listener类信息的key值
			String key = LFWPersTool.getCurrentProject().getName() + "." + LFWPersTool.getCurrentSimpleFolderPath() + "." + menubarObj.getMenubar().getId();
			wizard.setListenerClassInfoKey(key);
			wizard.setMenubar(menubarObj.getMenubar());
			int result = dialog.open();
			if (result == IDialogConstants.FINISH_ID || result == IDialogConstants.OK_ID) {
				List<DefaultItem> defaultMenuItems = wizard.getSelectedMenuItems();
				String packageName = wizard.getPackageName();
				String sourceFolder = wizard.getSourceFolder();
				String classPrefix = wizard.getClassPrefix();
				Map<DefaultItem, MenuItemParamPanel> paramPanels = wizard.getMenuItemParamPanels();
				
				// 绘制子项
				menubarObj.getFigure().resetDefaultMenuItems(defaultMenuItems, packageName, sourceFolder, classPrefix, paramPanels);
				menubarObj.setCurrentItem(null);
				if(!LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
					saveListenerClassInfo(key, packageName, sourceFolder, classPrefix);
				}
				
				MenubarEditor.getActiveMenubarEditor().getGraph().unSelectAllLabels();
				
				MenubarEditor.getActiveMenubarEditor().setDirtyTrue();
			}
		} else if (null != menuObj) {// 为子菜单增加默认项
			// 构造Listener类信息的key值
			MenubarElementObj parentObj = MenubarEditor.getActiveMenubarEditor().getMenubarObj();
			String key = LFWPersTool.getCurrentProject().getName() + "." + LFWPersTool.getCurrentSimpleFolderPath() + "." + parentObj.getMenubar().getId();
			wizard.setListenerClassInfoKey(key);
			wizard.setMenuItem(menuObj.getMenuItem());
			int result = dialog.open();
			if (result == IDialogConstants.FINISH_ID || result == IDialogConstants.OK_ID) {
				List<DefaultItem> defaultMenuItems = wizard.getSelectedMenuItems();
				String packageName = wizard.getPackageName();
				String sourceFolder = wizard.getSourceFolder();
				String classPrefix = wizard.getClassPrefix();
				Map<DefaultItem, MenuItemParamPanel> paramPanels = wizard.getMenuItemParamPanels();
				
				// 绘制子项
				menuObj.getFigure().resetDefaultMenuItems(defaultMenuItems, packageName, sourceFolder, classPrefix, paramPanels);
				menuObj.setCurrentItem(null);

				saveListenerClassInfo(key, packageName, sourceFolder, classPrefix);
				
				MenubarEditor.getActiveMenubarEditor().getGraph().unSelectAllLabels();
				
				MenubarEditor.getActiveMenubarEditor().setDirtyTrue();
			}
		}
		// 刷新菜单项表格
		Composite view = MenubarEditor.getActiveMenubarEditor().getViewPage().getCellPropertiesView();
		if (null != view)
			((MenuItemPropertiesView) view).getTv().refresh();
		
	}
	
	/**
	 * 保存Listener类信息
	 */
	private void saveListenerClassInfo(String key, String packageName, String sourceFolder, String classPrefix) {
		ListenerClassInfo info = new ListenerClassInfo();
		info.setPackageName(packageName);
		info.setSourceFolder(sourceFolder);
		info.setClassPrefix(classPrefix);
		LfwGlobalEditorInfo.addAttribute(key, info);
	}

}
