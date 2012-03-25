package nc.lfw.editor.menubar.action;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.contextmenubar.ContextMenuEditor;
import nc.lfw.editor.contextmenubar.ContextMenuElementObj;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.dialog.NewMenuItemDialog;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 增加菜单项操作
 * 
 * @author guoweic
 *
 */
public class AddMenuItemAction extends Action {
	
	private LfwElementObjWithGraph menuObj;
	
	public AddMenuItemAction(LfwElementObjWithGraph menuObj) {
		super("增加自定义项");
		this.menuObj = menuObj;
	}

	
	public void run() {
		Shell shell = new Shell(Display.getCurrent());
		
		NewMenuItemDialog dialog = new NewMenuItemDialog(shell, "增加自定义项");
		if (menuObj instanceof MenuElementObj)
			dialog.setParentMenuItem(((MenuElementObj)menuObj).getMenuItem());
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			MenuItem newMenuItem = new MenuItem();
			newMenuItem.setId(dialog.getItemId());
			newMenuItem.setText(dialog.getText());
			if(menuObj instanceof ContextMenuElementObj){
				((ContextMenuElementObj)menuObj).getFigure().addItem(newMenuItem);
				ContextMenuEditor.getActiveMenubarEditor().setDirtyTrue();
			}
			else{
				// 增加子项
				if (menuObj instanceof MenubarElementObj)
					((MenubarElementObj)menuObj).getFigure().addItem(newMenuItem);
				else if (menuObj instanceof MenuElementObj)
					((MenuElementObj)menuObj).getFigure().addItem(newMenuItem);
			}
			if(ContextMenuEditor.getActiveMenubarEditor() != null)
				ContextMenuEditor.getActiveMenubarEditor().setDirtyTrue();
			else if(MenubarEditor.getActiveMenubarEditor() != null)
				MenubarEditor.getActiveMenubarEditor().setDirtyTrue();
		}
	}
}
