package nc.lfw.editor.menubar.action;

import java.util.ArrayList;

import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.lfw.editor.menubar.graph.MenuElementPart;
import nc.lfw.editor.menubar.page.MenuItemPropertiesView;
import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

/**
 * Field ��������
 * 
 * @author zhangxya
 * 
 */
public class DownMenuItemPropAction extends Action {
	private class AttrMoveDownCommand extends Command {
		private ArrayList<MenuItem> arraylist = null;
		private int index = -1;

		public AttrMoveDownCommand(ArrayList<MenuItem> arraylist, int index) {
			super("��������");
			this.arraylist = arraylist;
			this.index = index;
		}

		
		public void execute() {
			redo();
		}

		
		public void redo() {
			if (index < arraylist.size() - 1) {
				TableViewer tableView = view.getTv();
				MenuItem item = arraylist.get(index);
				arraylist.remove(index);
				arraylist.add(index + 1, item);
				tableView.refresh(arraylist);
			}
		}

		
		public void undo() {
			if (index > 0) {
				TableViewer tableView = view.getTv();
				MenuItem item = arraylist.get(index + 1);
				arraylist.remove(item);
				arraylist.add(index, item);
				tableView.refresh(arraylist);
			}
		}

	}

	private MenuItemPropertiesView view = null;

	public DownMenuItemPropAction(MenuItemPropertiesView view) {
		super("����");
		this.view = view;
	}

	@SuppressWarnings("unchecked")
	
	public void run() {
		TableViewer tableView = view.getTv();
		tableView.cancelEditing();
		StructuredSelection sel = (StructuredSelection) tableView
				.getSelection();
		Object o = sel.getFirstElement();
		if (o instanceof MenuItem) {
			ArrayList<MenuItem> al = (ArrayList<MenuItem>) (ArrayList) tableView
					.getInput();
			int index = al.indexOf(o);
			if (index < al.size() - 1) {
				AttrMoveDownCommand cmd = new AttrMoveDownCommand(al, index);
				MenubarEditor editor = MenubarEditor.getActiveMenubarEditor();
				if (editor != null) {
					editor.executComand(cmd);
					editor.setDirtyTrue();
					// ˢ��ͼ����ʾ
					StructuredSelection ss = (StructuredSelection) editor.getCurrentSelection();
					Object currentSel = ss.getFirstElement();
					if (currentSel instanceof MenuElementPart) {
						Object model = ((MenuElementPart)currentSel).getModel();
						if (model instanceof MenubarElementObj) {
							((MenubarElementObj) model).getFigure().refreshItems();
						} else if (model instanceof MenuElementObj) {
							((MenuElementObj) model).getFigure().refreshItems();
						}
					}
				}
			}
		}

	}

}
