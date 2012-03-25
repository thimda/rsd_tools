package nc.lfw.editor.contextmenubar.actions;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

public class DelContextMenubarAction  extends Action {
	public DelContextMenubarAction() {
		super("删除", PaletteImage.getDeleteImgDescriptor());
		setText(WEBProjConstants.DEL_CONTEXT_MENU);
		setToolTipText(WEBProjConstants.DEL_CONTEXT_MENU);
	}
	
	public void run() {
		if (MessageDialog.openConfirm(null, "确认", "确定删除所选" + WEBProjConstants.CONTEXT_MENUBAR + "吗？")) {
			delete();
		}
	}
	
	private void delete() {
		try {
			TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
			ContextMenuComp menubarComp = ((ContextMenuComp)treeItem.getData());
			LfwWidget widget = LFWPersTool.getCurrentWidget();
			if(widget.getViewMenus().getContextMenu(menubarComp.getId()) != null){
				widget.getViewMenus().removeContextMenu(menubarComp.getId());
				LFWPersTool.saveWidget(widget);
			}
			treeItem.dispose();
			
		} catch (Exception e) {
			Shell shell = new Shell(Display.getCurrent());
			String title =WEBProjConstants.DEL_CONTEXT_MENU;
			String message = e.getMessage();
			MessageDialog.openError(shell, title, message);
		}
	}
	
}
