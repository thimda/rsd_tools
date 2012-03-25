package nc.lfw.editor.contextmenubar.actions;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.contextmenubar.ContextMenuEditor;
import nc.lfw.editor.contextmenubar.ContextMenuElementObj;
import nc.lfw.editor.menubar.MenuItemLabel;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.lfw.editor.menubar.graph.MenuElementPart;

import org.eclipse.draw2d.Label;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * ɾ��context��menuItem
 * @author zhangxya
 *
 */
public class DelContextMenuItemAction extends Action {
	
	private LfwElementObjWithGraph elementObj;
	private Label label;
	private String itemId;
	
	public DelContextMenuItemAction(Label label, LfwElementObjWithGraph elementObj, String itemId) {
		setText("ɾ�� " + itemId);
		setToolTipText("ɾ��");
		this.elementObj = elementObj;
		this.label = label;
		this.itemId = itemId;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "ȷ��", "ȷ��ɾ�� " + itemId + " ��"))
			deleteItem();
	}
	
	private void deleteItem() {
		ContextMenuEditor editor = ContextMenuEditor.getActiveMenubarEditor();
		if (label instanceof MenuItemLabel) {
			
			if (elementObj instanceof ContextMenuElementObj) {
				((ContextMenuElementObj) elementObj).getFigure().deleteItem((MenuItemLabel)label);
			} else if (elementObj instanceof MenuElementObj) {
				((MenuElementObj) elementObj).getFigure().deleteItem((MenuItemLabel)label);
			}
			// ���õ�ǰѡ����Ϊ��
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
			
			// ����pagemeta
//			editor.savePagemeta(pagemeta);
			editor.setDirtyTrue();
		}
	}
}