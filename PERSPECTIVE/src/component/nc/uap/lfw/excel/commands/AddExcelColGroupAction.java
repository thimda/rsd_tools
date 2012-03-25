package nc.uap.lfw.excel.commands;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.ExcelColumnGroup;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.excel.ExcelElementObj;
import nc.uap.lfw.excel.core.ExcelEditor;
import nc.uap.lfw.excel.core.ExcelPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

public class AddExcelColGroupAction extends Action {
	private ExcelPropertiesView view = null;
	private class AddAttrCommand extends Command{
	private ExcelColumnGroup field = null;
	private ArrayList<IExcelColumn> arraylist = null;
	private ExcelElementObj Excelobj = null;
	public AddAttrCommand(ExcelElementObj Excelobj, ArrayList<IExcelColumn> arraylist, ExcelColumnGroup field) {
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
	public AddExcelColGroupAction(ExcelPropertiesView view) {
		super("增加组");
		this.view = view;
	}
	private ExcelPropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		ExcelColumnGroup fi = new ExcelColumnGroup();
		insertNullProp(fi);
	}
	
	@SuppressWarnings("unchecked")
	private void insertNullProp(ExcelColumnGroup fi){
		ExcelPropertiesView view = getPropertiesView();
		if(view.getLfwElementPart() != null && view.getLfwElementPart().getModel() instanceof ExcelElementObj){
			ExcelElementObj vo = (ExcelElementObj)view.getLfwElementPart().getModel();
			Object object = view.getTv().getInput();
			ArrayList<IExcelColumn> arraylist = new ArrayList<IExcelColumn>();
			if(object instanceof ArrayList){
				 arraylist = (ArrayList<IExcelColumn>)object;
			}
			arraylist.add(fi);
			AddAttrCommand addcmd = new AddAttrCommand(vo, arraylist, fi);
			if(ExcelEditor.getActiveEditor() != null)
				ExcelEditor.getActiveEditor() .executComand(addcmd);
		}
	}
}

