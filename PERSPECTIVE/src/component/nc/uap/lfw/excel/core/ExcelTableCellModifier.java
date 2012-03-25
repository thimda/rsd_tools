package nc.uap.lfw.excel.core;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.ObjectComboCellEditor;
import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.comp.ExcelColumn;
import nc.uap.lfw.core.comp.ExcelColumnGroup;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.excel.ExcelElementObj;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

public class ExcelTableCellModifier implements ICellModifier{
	
	/**
	 * {"id","field","text","i18nName","langdir","width","visible","editable","columBgColor"
		,"textAlign","textColor","fixedHeader","editorType","renderType","refNode","imageOnly",
		"sumCol","autoExpand","sortable"};
	 */
	public static final String[] colNames ={"ID","字段","显示值","多语资源","多语所在模块","宽度","是否可见","是否可编辑",
		"列背景色","内容位置","内容颜色","是否固定表头","编辑类型","渲染类型","引用参照","引用下拉框","只显示图片","是否合计列","是否自动扩展",
		"是否排序","所属组","是否可以为空"};

	private ExcelPropertiesView view = null;
	
	private class GridModifiCommand extends Command{
		
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private IExcelColumn attr = null;
		
		public GridModifiCommand(String property, Object value, IExcelColumn attr) {
			super("修改属性");
			this.property = property;
			this.value = value;
			this.attr = attr;
		}
		
		public void execute() {
			oldValue = getValue(attr, property);
			redo();
		}
		
		public void redo() {
			modifyAttr(attr, property, value);
		}
		
		public void undo() {
			modifyAttr(attr, property, oldValue);
		}
	}
	
	
	public ExcelTableCellModifier(ExcelPropertiesView view) {
		super();
		this.view = view;
	}
	
	private  TreeViewer getTreeViewer(){
		return getPropertiesView().getTv();
	}
	
	private ExcelPropertiesView getPropertiesView(){
		return view;
	}
	

	private void modifyAttr(IExcelColumn prop, String property,Object value){
		if(prop instanceof ExcelColumn){
			ExcelColumn propCol = (ExcelColumn) prop;
			if(colNames[0].equals(property)){
				propCol.setId((String)value);
			}else if(colNames[1].equals(property)){
				propCol.setField((String)value);
			}else if(colNames[2].equals(property)){
				propCol.setText((String)value);
			}else if(colNames[3].equals(property)){
				propCol.setI18nName((String)value);
			}
			else if(colNames[4].equals(property)){
				propCol.setLangDir((String)value);
			}
			else if(colNames[5].equals(property)){
				if(value == null || value.equals(""))
					propCol.setWidth(ExcelColumn.DEFAULT_WIDTH);
				else
					propCol.setWidth((Integer.valueOf((String)value)));
			}
			else if(colNames[6].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setVisible(truevalue);
			}else if(colNames[7].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setEditable(truevalue);
			}else if(colNames[8].equals(property)){
				propCol.setColumBgColor((String)value);
			}else if(colNames[9].equals(property)){
				propCol.setTextAlign((String)value);
			}
			else if(colNames[10].equals(property)){
				propCol.setTextColor((String)value);
			}else if(colNames[11].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setFixedHeader(truevalue);
			}else if(colNames[12].equals(property)){
				propCol.setEditorType((String)value);
				if(value != null && !(value.toString().equals(EditorTypeConst.REFERENCE))){
					propCol.setRefNode(null);
				}
				if(value != null && !(value.toString().equals(EditorTypeConst.COMBODATA))){
					propCol.setRefComboData(null);
				}
			}
			else if(colNames[13].equals(property)){
				propCol.setRenderType((String)value);
			}else if(colNames[14].equals(property)){
				propCol.setRefNode((String)value);
			}else if(colNames[15].equals(property)){
				propCol.setRefComboData((String)value);
			}
			else if(colNames[16].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setImageOnly(truevalue);
			}
			else if(colNames[17].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setSumCol(truevalue);
			}else if(colNames[18].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setAutoExpand(truevalue);
			}
			else if(colNames[19].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setSortable(truevalue);
			}
			else if(colNames[20].equals(property)){
				if(value != null && !value.equals("")){
					if(view.getLfwElementPart() != null && view.getLfwElementPart().getModel() instanceof ExcelElementObj){
						ExcelElementObj vo = (ExcelElementObj)view.getLfwElementPart().getModel();
						ExcelComp grid = vo.getExcelComp();
						IExcelColumn column = grid.getColumnById((String)value);
						if(column != null && column instanceof ExcelColumnGroup){
							ExcelColumnGroup columngroup = (ExcelColumnGroup) column;
							if(columngroup.getChildColumnList() == null || (columngroup.getChildColumnList() != null && !columngroup.getChildColumnList().contains(propCol)))
							// if(!columngroup.getChildColumnList().contains(propCol))
								 columngroup.addColumn(propCol);
							 grid.removeColumnByField(propCol.getId());
						}
						if(propCol.getColmngroup() != null && !propCol.equals("")){
							IExcelColumn columnold = grid.getColumnById(propCol.getColmngroup());
							if(columnold != null && columnold instanceof ExcelColumnGroup){
								ExcelColumnGroup columgroupold = (ExcelColumnGroup) columnold;
								columgroupold.removeColumn(propCol.getId());
							}
						}
						
					}
				}
				else{
					ExcelElementObj vo = (ExcelElementObj)view.getLfwElementPart().getModel();
					ExcelComp grid = vo.getExcelComp();
					if(propCol.getColmngroup() != null && !propCol.equals("")){
						IExcelColumn columnold = grid.getColumnById(propCol.getColmngroup());
						if(columnold != null && columnold instanceof ExcelColumnGroup){
							ExcelColumnGroup columgroupold = (ExcelColumnGroup) columnold;
							columgroupold.removeColumn(propCol.getId());
							grid.addColumn(propCol);
						}
					}
				}
				propCol.setColmngroup((String)value);
			}
			else if(colNames[21].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propCol.setNullAble(truevalue);
			}
		}
		else if(prop instanceof ExcelColumnGroup){
			ExcelColumnGroup propGroup = (ExcelColumnGroup)prop;
			if(colNames[0].equals(property)){
				propGroup.setId((String)value);
			}else if(colNames[2].equals(property)){
				propGroup.setText((String)value);
			}
			else if(colNames[3].equals(property)){
				propGroup.setI18nName((String)value);
			}
			else if(colNames[6].equals(property)){
				boolean truevalue = false;
				if((Integer)value == 0)
					truevalue = true;
				propGroup.setVisible(truevalue);
			}
			
			CellEditor editor = view.getCellEditorByColName("所属组");
			String[] valuegroup = view.getColumnGroup();
			if(editor instanceof ObjectComboCellEditor){
				ObjectComboCellEditor objEditor = (ObjectComboCellEditor) editor;
				objEditor.setObjectItems(valuegroup);
			}
		}
		
		getTreeViewer().refresh(prop);
		view.getTv().update(prop, null);
	}
	
	public boolean canModify(Object element, String property) {
		if(element instanceof ExcelColumn)
			return true;
		else if(element instanceof ExcelColumnGroup){
			if(colNames[0].equals(property) || colNames[2].equals(property)
					|| colNames[3].equals(property) || colNames[6].equals(property)){
				return true;
			}
			else
				return false;
		}
		return false;
	}

	public Object getValue(Object element, String property) {
		if(element instanceof ExcelColumn){
			ExcelColumn prop = (ExcelColumn)element;
			if(colNames[0].equals(property)){
				return prop.getId()==null? "" : prop.getId();
			}else if(colNames[1].equals(property)){
				return prop.getField()==null?"":prop.getField();
			}else if(colNames[2].equals(property)){
				return prop.getText() == null?"":prop.getText();
			}else if(colNames[3].equals(property)){
				return prop.getI18nName()== null?"":prop.getI18nName();
			}else if(colNames[4].equals(property)){
				return  prop.getLangDir()==null?"":prop.getLangDir();
			}else if(colNames[5].equals(property)){
				 new Integer(prop.getWidth()).toString();
			}
			
			else if(colNames[6].equals(property)){
				return prop.isVisible() == true? new Integer(0):new Integer(1);
			}
			else if(colNames[7].equals(property)){
				return prop.isEditable() == true? new Integer(0):new Integer(1);
			}
			else if(colNames[8].equals(property)){
				return  prop.getColumBgColor() == null?"":prop.getColumBgColor();
			}
			else if(colNames[9].equals(property)){
				return  prop.getTextAlign() == null?"":prop.getTextAlign();
			}
			else if(colNames[10].equals(property)){
				return  prop.getTextColor() == null?"":prop.getTextColor();
			}
			else if(colNames[11].equals(property)){
				return prop.isFixedHeader() == true? new Integer(0):new Integer(1);
			}
			
			else if(colNames[12].equals(property)){
				return  prop.getEditorType() == null?"":prop.getEditorType();
			}
			else if(colNames[13].equals(property)){
				return prop.getRenderType() == null?"":prop.getRenderType();
			}
			else if(colNames[14].equals(property)){
				return  prop.getRefNode() == null?"":prop.getRefNode();
			}
			else if(colNames[15].equals(property)){
				return  prop.getRefComboData() == null?"":prop.getRefComboData();
			}
			else if(colNames[16].equals(property)){
				return prop.isImageOnly() == true? new Integer(0):new Integer(1);
			}
			
			else if(colNames[17].equals(property)){
				 return prop.isSumCol() == true? new Integer(0):new Integer(1);
			}else if(colNames[18].equals(property)){
				return prop.isAutoExpand() == true? new Integer(0):new Integer(1);
			}
			else if(colNames[19].equals(property)){
				return prop.isSortable() == true? new Integer(0):new Integer(1);
			}
			else if(colNames[20].equals(property)){
				return prop.getColmngroup() == null? "":prop.getColmngroup();
			}
			else if(colNames[21].equals(property)){
				return prop.isNullAble() == true? new Integer(0):new Integer(1);
			}
		}
		else if(element instanceof ExcelColumnGroup){
			ExcelColumnGroup prop = (ExcelColumnGroup)element;
			if(colNames[0].equals(property)){
				return prop.getId()==null? "" : prop.getId();
			}
			else if(colNames[2].equals(property)){
				return prop.getText() == null?"":prop.getText();
			}
			else if(colNames[3].equals(property)){
				return prop.getI18nName()== null?"":prop.getI18nName();
			}
			else if(colNames[6].equals(property)){
				return prop.isVisible() == true? new Integer(0):new Integer(1);
			}
		}
			
		return "";
	}
	
	/**
	 * 查询除了要修改的字段的其余字段的id
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllFieldExceptModi(String id){
		Object object = view.getTv().getInput();
		List<String> list = new ArrayList<String>();
		if(object instanceof List){
			List<IExcelColumn> allColumns = (List<IExcelColumn>)object;
			for (int i = 0; i < allColumns.size(); i++) {
				IExcelColumn col = allColumns.get(i);
				if(col instanceof ExcelColumn){
					ExcelColumn column = (ExcelColumn) allColumns.get(i);
					if(column.getId() != null && !(column.getId().equals(id)))
						list.add(column.getId());
				}
				else if(col instanceof ExcelColumnGroup){
					ExcelColumnGroup column = (ExcelColumnGroup) allColumns.get(i);
					if(column.getId() != null && !(column.getId().equals(id)))
						list.add(column.getId());
					}
				}
			}
		return list;
	}

	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object o = item.getData();
		if(o instanceof IExcelColumn){
			IExcelColumn prop = (IExcelColumn)o;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			if(property.endsWith(colNames[0])){
				List<String> list = getAllFieldExceptModi(old.toString());
				if(list.contains(value)){
					MessageDialog.openError(null, "错误提示", "此表格已经存在ID为" + value+ "的列!");
					return;
				}
			}
			GridModifiCommand cmd = new GridModifiCommand(property, value, prop );
			ExcelEditor editor = ExcelEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	
	}
}


