package nc.uap.lfw.excel.commands;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.ExcelColumnGroup;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.excel.ExcelElementObj;
import nc.uap.lfw.excel.core.ExcelEditor;
import nc.uap.lfw.excel.core.ExcelPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;

public class DeleteExcelFieldAction extends Action {
	private class DelCellPropCommand extends Command{
		private ExcelElementObj Excelobj = null;
		private List<IExcelColumn> fields = null;
		private ArrayList<IExcelColumn> arraylist = null;
		public DelCellPropCommand(ArrayList<IExcelColumn> arraylist, ExcelElementObj Excelobj, List<IExcelColumn> fields) {
			super("删除属性");
			this.arraylist = arraylist;
			this.Excelobj = Excelobj;
			this.fields = fields;
		}
		
		public void execute() {
			redo();
		}
		
		public void redo() {
			if(fields != null){
				ExcelComp Excel = Excelobj.getExcelComp();
				for (int i = 0; i < fields.size(); i++) {
					if(fields.get(i) instanceof ExcelComp){
						Excel.getColumnList().remove(fields.get(i));
					}
					else if(fields.get(i) instanceof ExcelColumnGroup){
						ExcelColumnGroup columngroup = (ExcelColumnGroup) fields.get(i);
						if(columngroup.getChildColumnList() != null){
							MessageDialog.openError(null, "提示", "此列中含有子列,不能删除!");
							return;
						}
						Excel.getColumnList().remove(fields.get(i));
					}
				}
			}
			TreeViewer tv = getPropertiesView().getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}

		
		public void undo() {
			if(fields != null){
				for (int i = 0; i < fields.size(); i++) {
					arraylist.remove(fields.get(i));
					Excelobj.getExcelComp().getColumnList().add(fields.get(i));
				}
			}
			TreeViewer tv = getPropertiesView().getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}
		
	}
	private ExcelPropertiesView view = null;
	public DeleteExcelFieldAction(ExcelPropertiesView view) {
		setText("删除");
		this.view = view;
	}

	private ExcelPropertiesView getPropertiesView() {
		return view;
	}

	
	@SuppressWarnings("unchecked")
	public void run() {
		boolean tip = MessageDialog.openConfirm(null, "提示", "确定要删除数据吗?");
		if(tip){
			Object model = getPropertiesView().getLfwElementPart().getModel();
			CheckboxTreeViewer ctx = getPropertiesView().getCtv();
			Object[] objects = ctx.getCheckedElements();
			if(objects != null && model instanceof ExcelElementObj){
				ExcelElementObj vo = (ExcelElementObj) model;
				List allRemoveList = new ArrayList<IExcelColumn>();
				Object object = view.getTv().getInput();
				ArrayList<IExcelColumn> arraylist = null;
				if(object instanceof ArrayList){
					 arraylist = (ArrayList<IExcelColumn>)object;
				}
				for (int i = 0; i < objects.length; i++) {
					if (objects[i] instanceof IExcelColumn) {
						IExcelColumn prop = (IExcelColumn) objects[i];
						arraylist.remove(prop);
						allRemoveList.add(prop);
					}
				}
				DelCellPropCommand cmd = new DelCellPropCommand(arraylist, vo, allRemoveList);
				if(ExcelEditor.getActiveEditor() != null)
					ExcelEditor.getActiveEditor().executComand(cmd);
			}
		}
	}
}
