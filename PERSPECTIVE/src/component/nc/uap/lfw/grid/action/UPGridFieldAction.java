package nc.uap.lfw.grid.action;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.GridColumn;
import nc.uap.lfw.core.comp.GridColumnGroup;
import nc.uap.lfw.core.comp.IGridColumn;
import nc.uap.lfw.grid.core.GridEditor;
import nc.uap.lfw.grid.core.GridPropertisView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * ����grid�ֶβ���
 * @author zhangxya
 *
 */
public class UPGridFieldAction extends Action {
	private class AttrMoveupCommand extends Command{
		private ArrayList<IGridColumn> arraylist = null;
		private List<IGridColumn> grouplist = null;
		private int index = -1;
		private int groupindex = -1;
		public AttrMoveupCommand(ArrayList<IGridColumn> arraylist, List<IGridColumn> grouplist,  int index, int groupindex) {
			super("��������");
			this.arraylist = arraylist;
			this.index = index;
			this.groupindex = groupindex;
			this.grouplist = grouplist;
		}
		
		public void execute() {
			redo();
		}
		
		public void redo() {
			TreeViewer treeView = view.getTv();
			if(index > 0){
				IGridColumn field = arraylist.get(index);
				arraylist.remove(field);
				arraylist.add(index -1, field);
				treeView.refresh(arraylist);
			}
			else if(groupindex > 0){
				IGridColumn field = grouplist.get(groupindex);
				grouplist.remove(groupindex);
				grouplist.add(groupindex - 1, field);
				treeView.refresh(grouplist);
			}
		}
		
		public void undo() {
			if(index < arraylist.size() -1){
				TreeViewer treeView = view.getTv();
				IGridColumn field = arraylist.remove(index-1);
				arraylist.add(index, field);
				treeView.refresh(arraylist);
			}
		}
		
	}
	private GridPropertisView view = null;
	public UPGridFieldAction(GridPropertisView view) {
		super("����");
		this.view =view;
	}

	@SuppressWarnings("unchecked")
	
	public void run() {
		TreeViewer treeView = view.getTv();
		treeView.cancelEditing();
		TreeSelection sel =(TreeSelection) treeView.getSelection();
		Object o = sel.getFirstElement();
		if(o instanceof IGridColumn){
			ArrayList<IGridColumn> al =(ArrayList<IGridColumn>)(ArrayList)treeView.getInput();
			List<IGridColumn> groupal = new ArrayList<IGridColumn>();
			int index = al.indexOf(o);
			int groupindex = -1;
			if(index <= 0){
				if(o instanceof GridColumn){
					GridColumn gridc = (GridColumn) o;
					if(gridc.getColmngroup() != null && !gridc.getColmngroup().equals("")){
						String groupcolumn = gridc.getColmngroup();
						for (int i = 0; i < al.size(); i++) {
							if(al.get(i) instanceof GridColumnGroup){
								GridColumnGroup col = (GridColumnGroup) al.get(i);
								groupal = col.getChildColumnList();
								if(col.getId().equals(groupcolumn)){
									groupindex = col.getChildColumnList().indexOf(o);
									break;
								}
							}
						}
					}
				}
			}
			if(index > 0 || groupindex > 0){
				AttrMoveupCommand cmd = new AttrMoveupCommand(al, groupal,  index, groupindex);
				if(GridEditor.getActiveEditor() != null)
					GridEditor.getActiveEditor().executComand(cmd);
			}
		}		
		
	}
}
