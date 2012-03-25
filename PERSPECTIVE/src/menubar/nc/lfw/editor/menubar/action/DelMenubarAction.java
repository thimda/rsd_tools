package nc.lfw.editor.menubar.action;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author guoweic
 *
 */
public class DelMenubarAction extends Action {
	public DelMenubarAction() {
		super("删除", PaletteImage.getDeleteImgDescriptor());
		setText(WEBProjConstants.DEL_MENUBAR);
		setToolTipText(WEBProjConstants.DEL_MENUBAR);
	}
	
	public void run() {
		if (MessageDialog.openConfirm(null, "确认", "确定删除所选" + WEBProjConstants.MENUBAR_CN + "吗？")) {
			delete();
		}
	}
	
	private void delete() {
		try {
			PageMeta pm = LFWPersTool.getCurrentPageMeta();
			TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
			MenubarComp menubarComp = ((MenubarComp)treeItem.getData());
			if(pm.getViewMenus().getMenuBar(menubarComp.getId()) != null){
				pm.getViewMenus().removeMenuBar(menubarComp.getId());
				LFWPersTool.savePagemeta(pm);
			}
			else {
				LfwWidget widget = LFWPersTool.getCurrentWidget();
				if(widget.getViewMenus().getMenuBar(menubarComp.getId()) != null){
					widget.getViewMenus().removeMenuBar(menubarComp.getId());
					LFWPersTool.saveWidget(widget);
				}
			}
			treeItem.dispose();
			
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_MENUBAR;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	
}
