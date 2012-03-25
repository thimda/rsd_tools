package nc.lfw.editor.menubar.action;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.menubar.MenuItemLabel;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.lfw.editor.menubar.graph.MenuElementPart;

import org.eclipse.draw2d.Label;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * 删除MenuItem命令
 *
 */
public class DelMenuItemAction extends Action {
	
	private LfwElementObjWithGraph elementObj;
	private Label label;
	private String itemId;
	
	public DelMenuItemAction(Label label, LfwElementObjWithGraph elementObj, String itemId) {
		setText("删除 " + itemId);
		setToolTipText("删除");
		this.elementObj = elementObj;
		this.label = label;
		this.itemId = itemId;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "确认", "确定删除 " + itemId + " 吗？"))
			deleteItem();
	}
	
	private void deleteItem() {
		MenubarEditor editor = MenubarEditor.getActiveMenubarEditor();
		if (label instanceof MenuItemLabel) {
			
			if (elementObj instanceof MenubarElementObj) {
				((MenubarElementObj) elementObj).getFigure().deleteItem((MenuItemLabel)label);
			} else if (elementObj instanceof MenuElementObj) {
				((MenuElementObj) elementObj).getFigure().deleteItem((MenuItemLabel)label);
			}
			// 设置当前选中项为空
			StructuredSelection ss = (StructuredSelection) editor.getCurrentSelection();
			Object currentSel = ss.getFirstElement();
			if (currentSel instanceof MenuElementPart) {
				Object model = ((MenuElementPart)currentSel).getModel();
				if (model instanceof MenubarElementObj) {
					((MenubarElementObj) model).setCurrentItem(null);
					((MenubarElementObj) model).getFigure().setCurrentLabel(null);
				} else if (model instanceof MenuElementObj) {
					((MenuElementObj) model).setCurrentItem(null);
					((MenuElementObj) model).getFigure().setCurrentLabel(null);
				}
			}
			
			// 保存pagemeta
//			editor.savePagemeta(pagemeta);
			editor.setDirtyTrue();
		}
	}
}
