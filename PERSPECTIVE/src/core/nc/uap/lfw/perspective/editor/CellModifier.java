package nc.uap.lfw.perspective.editor;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.common.StringDataTypeConst;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.MDField;
import nc.uap.lfw.core.data.PubField;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.views.CellPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ds Field Modifier
 * @author zhangxya
 *
 */
public class CellModifier implements ICellModifier {
	
	private CellPropertiesView view = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private Field attr = null;
		public CellModifiCommand(String property, Object value, Field attr) {
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
	
	public static final String[] colNames = {"ID","与VO对应字段(属性)","列名","多语资源","数据类型",
		"是否可以为空","缺省值","是否主键","编辑公式"
		,"验证公式","是否锁定","字段校验类型","多语目录","最大值","最小值","精度"};
	
	
	public CellModifier(CellPropertiesView view) {
		super();
		this.view = view;
	}
	
	
	public boolean canModify(Object element, String property) {
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		if(widget != null && widget.getFrom() != null)
			return false;
		if(element instanceof PubField){
			if(property.equals(colNames[0]) || property.equals(colNames[1]) || property.equals(colNames[4]))
			return false;
		}
		else if(element instanceof MDField){
			if(property.equals(colNames[0]) || property.equals(colNames[1]) || property.equals(colNames[4]))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllFieldExceptModi(String id){
		Object object = view.getTv().getInput();
		List<String> list = new ArrayList<String>();
		if(object instanceof List){
			List<Field> allFields = (List)object;
			for (int i = 0; i < allFields.size(); i++) {
				Field field = (Field) allFields.get(i);
				if(field.getId() != null && !(field.getId().equals(id)))
					list.add(field.getId());
				}
			}
		return list;
	}

	public Object getValue(Object element, String property) {
		if(element instanceof Field){
			Field prop = (Field)element;
			if(colNames[0].equals(property)){
				return prop.getId()==null? "" : prop.getId();
			}else if(colNames[1].equals(property)){
				return prop.getField()==null?"":prop.getField();
			}
			else if(colNames[2].equals(property)){
				String label = prop.getText() == null?"":prop.getText();
				return label;
			}else if(colNames[3].equals(property)){
				String i18nname = prop.getI18nName() == null?"":prop.getI18nName();
				return i18nname;
			}
			else if(colNames[4].equals(property)){
				String datatype = prop.getDataType() == null?"":prop.getDataType();
				return datatype;
			}else if(colNames[5].equals(property)){
				return prop.isNullAble() == true? new Integer(0):new Integer(1);
			}else if(colNames[6].equals(property)){
				String defaultvalue = prop.getDefaultValue() == null?"":prop.getDefaultValue().toString();
				return defaultvalue;
			}else if(colNames[7].equals(property)){
				return prop.isPrimaryKey() == true? new Integer(0):new Integer(1);
			}else if(colNames[8].equals(property)){
				String editfamular = prop.getEditFormular() == null?"":prop.getEditFormular();
				return editfamular;
			}else if(colNames[9].equals(property)){
				String validatefamular = prop.getValidateFormula() == null?"":prop.getValidateFormula();
				return validatefamular;
			}else if(colNames[10].equals(property)){
				return prop.isLock() == true? new Integer(0):new Integer(1);
			}else if(colNames[11].equals(property)){
				String validatetype = prop.getFormater() == null?"":prop.getFormater();
				return validatetype;
			}else if(colNames[12].equals(property)){
				String langdir = prop.getLangDir() == null?"":prop.getLangDir();
				return langdir;
			}
			else if(colNames[13].equals(property)){
				return prop.getMaxValue() == null?"":prop.getMaxValue();
			}
			else if(colNames[14].equals(property)){
				return prop.getMinValue() == null?"":prop.getMinValue();
			}
			else if(colNames[15].equals(property)){
				return prop.getPrecision() == null?"":prop.getPrecision();
			}
		}
		return "";
	}

	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof Field){
			Field prop = (Field)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			if(property.endsWith(colNames[0])){
				List<String> list = getAllFieldExceptModi(old.toString());
				if(list.contains(value)){
					MessageDialog.openError(null, "错误提示", "此数据源中已经存在ID为" + value+ "的Field!");
					return;
				}
			}
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			DataSetEditor editor = DataSetEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	
	private void modifyAttr(Field prop, String property,Object value){
		 if(colNames[0].equals(property)){
			 prop.setId((String)value);
			 prop.setText((String)value);
		 }
		else if(colNames[1].equals(property)){
			 prop.setField((String)value);
		}else if(colNames[2].equals(property)){
			prop.setText((String)value);
		}
		else if(colNames[3].equals(property)){
			prop.setI18nName((String)value);
		}else if(colNames[4].equals(property)){
			prop.setDataType((String)value);
		}else if(colNames[5].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setNullAble(truevalue);	
		}else if(colNames[6].equals(property)){
			prop.setDefaultValue((String)value);
		}else if(colNames[7].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setPrimaryKey(truevalue);	
		}else if(colNames[8].equals(property)){
			prop.setEditFormular((String)value);
		}else if(colNames[9].equals(property)){
			prop.setValidateFormula((String)value);
		}else if(colNames[10].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setLock(truevalue);	
		}else if(colNames[11].equals(property)){
			prop.setFormater((String)value);
		}else if(colNames[12].equals(property)){
			prop.setLangDir((String)value);
		}
		else if(colNames[13].equals(property)){
			prop.setMaxValue((String)value);
		}
		else if(colNames[14].equals(property)){
			prop.setMinValue((String)value);
		}
		else if(colNames[15].equals(property)){
			if(prop.getDataType().equals(StringDataTypeConst.Decimal) || prop.getDataType().equals(StringDataTypeConst.UFDOUBLE)
					|| prop.getDataType().equals(StringDataTypeConst.FLOATE) || 
					
					prop.getDataType().equals(StringDataTypeConst.INTEGER) || prop.getDataType().equals(StringDataTypeConst.INT)
					|| prop.getDataType().equals(StringDataTypeConst.DOUBLE) || prop.getDataType().equals(StringDataTypeConst.dOUBLE)
					||  prop.getDataType().equals(StringDataTypeConst.fLOATE) ||   prop.getDataType().equals(StringDataTypeConst.BIGDECIMAL)
					|| prop.getDataType().equals(StringDataTypeConst.LONG) ||  prop.getDataType().equals(StringDataTypeConst.lONG) )
				prop.setPrecision((String)value);
		}
		view.getTv().update(prop, null);
	}
}

