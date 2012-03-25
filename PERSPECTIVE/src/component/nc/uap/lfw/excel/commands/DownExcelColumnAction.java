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

public class DownExcelColumnAction extends Action {
	private ExcelPropertiesView view = null;
	private class AttrMoveDownCommand extends Command{
		private ArrayList<IExcelColumn> arraylist = null;
		private int index = -1;
		private int groupindex = -1;
		private List<IExcelColumn> groupList = null;
		public AttrMoveDownCommand(ArrayList<IExcelColumn> arraylist, List<IExcelColumn> groupList, int index, int groupindex) {
			super("œ¬“∆ Ù–‘");
			this.arraylist = arraylist;
			this.index = index;
			this.groupindex = groupindex;
			this.groupList = groupList;
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			TreeViewer treeView = view.getTv();
			if(index != -1  && index < arraylist.size()-1){
				IExcelColumn field = arraylist.get(index);
				arraylist.remove(index);
				arraylist.add(index+1, field);
				treeView.refresh(arraylist);
			}
			else if(groupindex != -1 && groupindex < groupList.size() -1){
				IExcelColumn field = groupList.get(groupindex);
				groupList.remove(groupindex);
				groupList.add(groupindex + 1, field);
				treeView.refresh(groupList);
			}
		}

		
		public void undo() {
			if(index > 0){
				TreeViewer treeView = view.getTv();
				IExcelColumn field = arraylist.get(index + 1);
				arraylist.remove(field);
				arraylist.add(index, field);
				treeView.refresh(arraylist);
			}
		}
		
	}
	
	public DownExcelColumnAction(ExcelPropertiesView view) {
		super("œ¬“∆");
		this.view = view;
	}

	@SuppressWarnings("unchecked")
	
	public void run() {
		TreeViewer treeView = view.getTv();
		treeView.cancelEditing();
		TreeSelection sel =(TreeSelection) treeView.getSelection();
		Object o = sel.getFirstElement();
		if(o instanceof ExcelColumn){
			ArrayList<IExcelColumn> al =(ArrayList<IExcelColumn>)(ArrayList)treeView.getInput();
			List<IExcelColumn> groupal = new ArrayList<IExcelColumn>();
			int index = al.indexOf(o);
			int groupindex = -1;
			if(index < 0){
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

			if(index < al.size()-1 || groupindex < groupal.size() - 1){
				AttrMoveDownCommand cmd = new AttrMoveDownCommand(al, groupal, index, groupindex);
				if(ExcelEditor.getActiveEditor() != null)
					ExcelEditor.getActiveEditor().executComand(cmd);
			}
		}		
		
	}

}
