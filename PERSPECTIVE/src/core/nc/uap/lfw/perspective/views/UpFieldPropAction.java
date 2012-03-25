package nc.uap.lfw.perspective.views;

import java.util.ArrayList;

import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.perspective.editor.DataSetEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Field 上移命令
 * @author zhangxya
 *
 */
public class UpFieldPropAction extends Action {
	private class AttrMoveupCommand extends Command{
		private ArrayList<Field> arraylist = null;
		private int index = -1;
		public AttrMoveupCommand(ArrayList<Field> arraylist, int index) {
			super("上移属性");
			this.arraylist = arraylist;
			this.index = index;
		}
		
		public void execute() {
			redo();
		}
		
		public void redo() {
			if(index > 0){
				TreeViewer treeView = view.getTv();
				Field field = arraylist.get(index);
				arraylist.remove(field);
				arraylist.add(index -1, field);
				treeView.refresh(arraylist);
			}
		}
		
		public void undo() {
			if(index < arraylist.size() -1){
				TreeViewer treeView = view.getTv();
				Field field = arraylist.remove(index-1);
				arraylist.add(index, field);
				treeView.refresh(arraylist);
			}
		}
		
	}
	private CellPropertiesView view = null;
	public UpFieldPropAction(CellPropertiesView view) {
		super("上移");
		this.view =view;
	}

	@SuppressWarnings("unchecked")
	
	public void run() {
		TreeViewer treeView = view.getTv();
		treeView.cancelEditing();
		TreeSelection sel =(TreeSelection) treeView.getSelection();
		Object o = sel.getFirstElement();
		if(o instanceof Field){
			ArrayList<Field> al =(ArrayList<Field>)(ArrayList)treeView.getInput();
			int index = al.indexOf(o);
			if(index > 0){
				AttrMoveupCommand cmd = new AttrMoveupCommand(al, index);
				if(DataSetEditor.getActiveEditor() != null)
					DataSetEditor.getActiveEditor().executComand(cmd);
			}
		}		
		
	}
}
