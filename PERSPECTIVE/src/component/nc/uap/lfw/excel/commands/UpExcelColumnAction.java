package nc.uap.lfw.excel.commands;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.ExcelColumn;
import nc.uap.lfw.core.comp.ExcelColumnGroup;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.excel.core.ExcelEditor;
import nc.uap.lfw.excel.core.ExcelPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

public class UpExcelColumnAction extends Action {
	
	private ExcelPropertiesView view = null;
	
	private class AttrMoveupCommand extends Command{
		private ArrayList<IExcelColumn> arraylist = null;
		private List<IExcelColumn> grouplist = null;
		private int index = -1;
		private int groupindex = -1;
		public AttrMoveupCommand(ArrayList<IExcelColumn> arraylist, List<IExcelColumn> grouplist,  int index, int groupindex) {
			super("ÉÏÒÆÊôÐÔ");
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
				IExcelColumn field = arraylist.get(index);
				arraylist.remove(field);
				arraylist.add(index -1, field);
				treeView.refresh(arraylist);
			}
			else if(groupindex > 0){
				IExcelColumn field = grouplist.get(groupindex);
				grouplist.remove(groupindex);
				grouplist.add(groupindex - 1, field);
				treeView.refresh(grouplist);
			}
		}
		
		public void undo() {
			if(index < arraylist.size() -1){
				TreeViewer treeView = view.getTv();
				IExcelColumn field = arraylist.remove(index-1);
				arraylist.add(index, field);
				treeView.refresh(arraylist);
			}
		}
		
	}

	public UpExcelColumnAction(ExcelPropertiesView view) {
		super("ÉÏÒÆ");
		this.view =view;
	}

	@SuppressWarnings("unchecked")
	
	public void run() {
		TreeViewer treeView = view.getTv();
		treeView.cancelEditing();
		TreeSelection sel =(TreeSelection) treeView.getSelection();
		Object o = sel.getFirstElement();
		if(o instanceof IExcelColumn){
			ArrayList<IExcelColumn> al =(ArrayList<IExcelColumn>)(ArrayList)treeView.getInput();
			List<IExcelColumn> groupal = new ArrayList<IExcelColumn>();
			int index = al.indexOf(o);
			int groupindex = -1;
			if(index <= 0){
				if(o instanceof ExcelColumn){
					ExcelColumn Excelc = (ExcelColumn) o;
					if(Excelc.getColmngroup() != null && !Excelc.getColmngroup().equals("")){
						String groupcolumn = Excelc.getColmngroup();
						for (int i = 0; i < al.size(); i++) {
							if(al.get(i) instanceof ExcelColumnGroup){
								ExcelColumnGroup col = (ExcelColumnGroup) al.get(i);
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
				if(ExcelEditor.getActiveEditor() != null)
					ExcelEditor.getActiveEditor().executComand(cmd);
			}
		}		
		
	}
}
