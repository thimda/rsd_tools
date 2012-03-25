package nc.uap.lfw.toolbar.commands;

import java.util.ArrayList;

import nc.uap.lfw.core.comp.ToolBarItem;
import nc.uap.lfw.toolbar.ToolBarEditor;
import nc.uap.lfw.toolbar.ToolBarPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

public class DownToolBarAction extends Action {
	private class AttrMoveDownCommand extends Command{
		private ArrayList<ToolBarItem> arraylist = null;
		private int index = -1;
		public AttrMoveDownCommand(ArrayList<ToolBarItem> arraylist, int index) {
			super("��������");
			this.arraylist = arraylist;
			this.index = index;
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			if(index < arraylist.size()-1){
				TreeViewer treeView = view.getTv();
				ToolBarItem field = arraylist.get(index);
				arraylist.remove(index);
				arraylist.add(index+1, field);
				treeView.refresh(arraylist);
			}
		}

		
		public void undo() {
			if(index > 0){
				TreeViewer treeView = view.getTv();
				ToolBarItem field = arraylist.get(index + 1);
				arraylist.remove(field);
				arraylist.add(index, field);
				treeView.refresh(arraylist);
			}
		}
		
	}
	private ToolBarPropertiesView view = null;
	public DownToolBarAction(ToolBarPropertiesView view) {
		super("����");
		this.view = view;
	}

	@SuppressWarnings("unchecked")
	
	public void run() {
		TreeViewer treeView = view.getTv();
		treeView.cancelEditing();
		TreeSelection sel =(TreeSelection) treeView.getSelection();
		Object o = sel.getFirstElement();
		if(o instanceof ToolBarItem){
			ArrayList<ToolBarItem> al =(ArrayList<ToolBarItem>)(ArrayList)treeView.getInput();
			int index = al.indexOf(o);
			if(index < al.size()-1){
				AttrMoveDownCommand cmd = new AttrMoveDownCommand(al, index);
				if(ToolBarEditor.getActiveEditor() != null)
					ToolBarEditor.getActiveEditor().executComand(cmd);
			}
		}		
		
	}

}
