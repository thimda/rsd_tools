package nc.lfw.editor.contextmenubar.actions;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWContextMenuTreeItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 新建右键菜单
 * @author zhangxya
 *
 */
public class NewContextMenuAction extends Action{
	
	public NewContextMenuAction() {
		super(WEBProjConstants.NEW_CONTEXT_MENU, PaletteImage.getCreateDsImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_CONTEXT_MENU, "输入" + WEBProjConstants.CONTEXT_MENUBAR + "名称","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					LFWContextMenuTreeItem contextMenubar = (LFWContextMenuTreeItem)view.addContextMenubarTreeNode(dirName);
					LfwWidget widget = LFWPersTool.getCurrentWidget();
					widget.getViewMenus().addContextMenu((ContextMenuComp)contextMenubar.getData());
					LFWPersTool.saveWidget(widget);
					view.openContextMenuEditor(contextMenubar);
					
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_CONTEXT_MENU;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}
}
