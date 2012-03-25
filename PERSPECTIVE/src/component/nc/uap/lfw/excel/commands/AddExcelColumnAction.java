package nc.uap.lfw.excel.commands;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.ExcelColumn;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.excel.ExcelElementObj;
import nc.uap.lfw.excel.core.ExcelEditor;
import nc.uap.lfw.excel.core.ExcelPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

public class AddExcelColumnAction extends Action {
	private ExcelPropertiesView view = null;
	private class AddAttrCommand extends Command{
	private ExcelColumn field = null;
	private ArrayList<ExcelColumn> arraylist = null;
	private ExcelElementObj Excelobj = null;
	public AddAttrCommand(ExcelElementObj Excelobj, ArrayList<ExcelColumn> arraylist, ExcelColumn field) {
		super("增加");
		this.Excelobj = Excelobj;
		this.arraylist = arraylist;
		this.field = field;
	}
	private ExcelPropertiesView getPropertiesView(){
		return view;
	}
	
	public void execute() {
		redo();
	}
	

	public void redo() {
		ExcelPropertiesView view =getPropertiesView(); 
		Excelobj.addProp(field);
		List<IExcelColumn> Excelcolumns = Excelobj.getExcelComp().getColumnList();
		if(Excelcolumns == null){
			Excelcolumns = new ArrayList<IExcelColumn>();
		}
		//Excelcolumns.add(field);
		Excelobj.getExcelComp().setColumnList(Excelcolumns);
		view.getTv().setInput(arraylist);
		view.getTv().refresh();
		view.getTv().expandAll();
		
	}
}
	public AddExcelColumnAction(ExcelPropertiesView view) {
		super("增加");
		this.view = view;
	}
	private ExcelPropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		ExcelColumn fi = new ExcelColumn();
		insertNullProp(fi);
	}
	
	@SuppressWarnings("unchecked")
	private void insertNullProp(ExcelColumn fi){
		ExcelPropertiesView view =getPropertiesView();
		if(view.getLfwElementPart() != null && view.getLfwElementPart().getModel() instanceof ExcelElementObj){
			ExcelElementObj vo = (ExcelElementObj)view.getLfwElementPart().getModel();
			Object object = view.getTv().getInput();
			ArrayList<ExcelColumn> arraylist = new ArrayList<ExcelColumn>();
			if(object instanceof ArrayList){
				 arraylist = (ArrayList<ExcelColumn>)object;
			}
			arraylist.add(fi);
			AddAttrCommand addcmd = new AddAttrCommand(vo, arraylist, fi);
			if(ExcelEditor.getActiveEditor() != null)
				ExcelEditor.getActiveEditor() .executComand(addcmd);
		}
	}
}

