package nc.uap.lfw.form.core;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.common.EditorTypeConst;
import nc.uap.lfw.core.comp.FormElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

/**
 * form 单元格编辑
 * @author zhangxya
 *
 */
public class FormTableCellModifier implements ICellModifier{
	
//	public static final String[] colNames = {"ID","字段","显示值","多语资源","多语所在模块","是否可编辑",
//	"编辑类型","引用参照","引用下拉框", "关联字段","行宽","列宽","描述","标签颜色","下拉框控件数据区高度","默认值","仅显示图片",
//		"仅选择","是否换行显示","需要隐藏的操作条","需要隐藏的操作条图片", "是否可见", "输入提示","引用ID"};
//	
	
	public static final String[] colNames = {"ID","字段","显示值","多语资源","是否可见","是否可编辑",
		"编辑类型","引用参照","引用下拉框", "关联字段","RowSpan","ColSpan","height","width","输入框内提示","引用ID","下拉框控件数据区高度","仅显示图片",
			"仅选择","是否换行显示","需要隐藏的操作条","需要隐藏的操作条图片", "多语所在模块","描述","标签颜色","是否可以为空","是否绑定下一个"
			,"是否只读","输入框外提示", "精度", "上传文件大小限制"};
	
//	refComboData
	
//	{"id","Field","text","i18nName","langDir","editable",
//		"editorType","refNode","relationFormElement","rowSpan","colSpan","description","labelColor","dataDivHeight","defaultValue","imageOnly",
//			"selectOnly","nextLine","hideBarIndices","hideImageIndices"};
//	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private FormElement attr = null;
		public CellModifiCommand(String property, Object value, FormElement attr) {
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
	
	private FormPropertiesView view = null;
	public FormTableCellModifier(FormPropertiesView view) {
		super();
		this.view = view;
	}
	private  TreeViewer getTreeViewer(){
		return getPropertiesView().getTv();
	}
	private FormPropertiesView getPropertiesView(){
		return view;
	}
	
	
	
	private void modifyAttr(FormElement prop, String property,Object value){

		if(colNames[0].equals(property)){
			prop.setId((String)value);
		}else if(colNames[1].equals(property)){
			prop.setField((String)value);
		}else if(colNames[2].equals(property)){
			 prop.setText((String)value);
		}else if(colNames[3].equals(property)){
			prop.setI18nName((String)value);
		}
		else if(colNames[4].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setVisible(truevalue);
		}
		else if(colNames[5].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setEnabled(truevalue);
		}else if(colNames[6].equals(property)){
			prop.setEditorType((String)value);
			if(value != null && !(value.toString().equals(EditorTypeConst.REFERENCE))){
				prop.setRefNode(null);
			}
			if(value != null && !(value.toString().equals(EditorTypeConst.COMBODATA))){
				prop.setRefComboData(null);
			}
		}else if(colNames[7].equals(property)){
			 prop.setRefNode((String)value);
		}else if(colNames[8].equals(property)){
			 prop.setRefComboData((String)value);
		}
		else if(colNames[9].equals(property)){
			prop.setRelationField((String)value);
		}
		else if(colNames[10].equals(property)){
			if(value == null || value.equals(""))
				prop.setRowSpan(1);
			else
				prop.setRowSpan((Integer.valueOf((String)value)));
		}
		else if(colNames[11].equals(property)){
			if(value == null || value.equals(""))
				prop.setRowSpan(1);
			else
				prop.setColSpan((Integer.valueOf((String)value)));
		}
		
		else if(colNames[12].equals(property)){
			prop.setHeight((String)value);
		}
		else if(colNames[13].equals(property)){
			prop.setWidth((String)value);
		}
		
		else if(colNames[14].equals(property)){
			prop.setTip((String)value);
		}
		else if(colNames[15].equals(property)){
			prop.setBindId((String)value);
		}
		
		else if(colNames[16].equals(property)){
			 prop.setDataDivHeight((String)value);
		}else if(colNames[17].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			 prop.setImageOnly(truevalue);
		}else if(colNames[18].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setSelectOnly(truevalue);
		}else if(colNames[19].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setNextLine(truevalue);
		}
		else if(colNames[20].equals(property)){
			prop.setHideBarIndices((String)value);
		}else if(colNames[21].equals(property)){
			prop.setHideImageIndices((String)value);
		}
		else if(colNames[22].equals(property)){
			prop.setLangDir((String)value);
		}
		
		else if(colNames[23].equals(property)){
			prop.setDescription((String)value);
		}else if(colNames[24].equals(property)){
			 prop.setLabelColor((String)value);
		}
		else if(colNames[25].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setNullAble(truevalue);
		}
		else if(colNames[26].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setAttachNext(truevalue);
		}
		else if(colNames[27].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = false;
			else
				truevalue = true;
			prop.setEditable(truevalue);
		}
		else if(colNames[28].equals(property)){
			prop.setInputAssistant((String)value);
		}
		else if(colNames[29].equals(property)){
			prop.setPrecision((String)value);
		}
		else if(colNames[30].equals(property)){
			prop.setSizeLimit((String)value);
		}
		getTreeViewer().refresh(prop);
		view.getTv().update(prop, null);
	}
	
	
	public boolean canModify(Object element, String property) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public Object getValue(Object element, String property) {
		if(element instanceof FormElement){
			FormElement prop = (FormElement)element;
			if(colNames[0].equals(property)){
				return prop.getId()==null? "" : prop.getId();
			}else if(colNames[1].equals(property)){
				return prop.getField()==null?"":prop.getField();
			}
			else if(colNames[2].equals(property)){
				return   prop.getText() == null?"":prop.getText();
			}
			else if(colNames[3].equals(property)){
				return prop.getI18nName()== null?"":prop.getI18nName();
			}
			else if(colNames[4].equals(property)){
				return prop.isVisible() == true? new Integer(0):new Integer(1);
			}
			else if(colNames[5].equals(property)){
				return prop.isEnabled() == true? new Integer(0):new Integer(1);
			}else if(colNames[6].equals(property)){
				return  prop.getEditorType() == null?"":prop.getEditorType();
			}else if(colNames[7].equals(property)){
				return  prop.getRefNode() == null?"":prop.getRefNode();
			}else if(colNames[8].equals(property)){
				return  prop.getRefComboData() == null?"":prop.getRefComboData();
			}
			else if(colNames[9].equals(property)){
				return prop.getRelationField() == null?"":prop.getRelationField();
			}
			else if(colNames[10].equals(property)){
				return  new Integer(prop.getRowSpan()).toString();
			}
			else if(colNames[11].equals(property)){
				return  new Integer(prop.getColSpan()).toString();
			}
			else if(colNames[12].equals(property)){
				return prop.getHeight() == null?"":prop.getHeight();
			}
			else if(colNames[13].equals(property)){
				return prop.getWidth() == null?"":prop.getWidth();
			}
			else if(colNames[14].equals(property)){
				return prop.getTip() == null? "":prop.getTip();
				
			}
			else if(colNames[15].equals(property)){
				return prop.getBindId() == null? "":prop.getBindId();
			}
			else if(colNames[16].equals(property)){
				return  prop.getDataDivHeight() == null?"":prop.getDataDivHeight();
			}else if(colNames[17].equals(property)){
				 return prop.isImageOnly() == true? new Integer(0):new Integer(1);
			}else if(colNames[18].equals(property)){
				return prop.isSelectOnly() == true? new Integer(0):new Integer(1);
			}else if(colNames[19].equals(property)){
				return prop.isNextLine() == true?new Integer(0):new Integer(1);
			}
			else if(colNames[20].equals(property)){
				return  prop.getHideBarIndices() == null?"":prop.getHideBarIndices();
			}else if(colNames[21].equals(property)){
				return prop.getHideImageIndices() == null?"":prop.getHideImageIndices();
			}
			else if(colNames[22].equals(property)){
				return  prop.getLangDir()==null?"":prop.getLangDir();
			}
			else if(colNames[23].equals(property)){
				return  prop.getDescription() == null?"":prop.getDescription();
			}else if(colNames[24].equals(property)){
				return  prop.getLabelColor() == null?"":prop.getLabelColor();
			}
			else if(colNames[25].equals(property)){
				return  prop.isNullAble() == true?new Integer(0):new Integer(1);
			}
			else if(colNames[26].equals(property)){
				return  prop.isAttachNext() == true?new Integer(0):new Integer(1);
			}
			else if(colNames[27].equals(property)){
				return  prop.isEditable() == true?new Integer(1):new Integer(2);
			}
			else if(colNames[28].equals(property)){
				return prop.getInputAssistant() == null?"":prop.getInputAssistant();
			}
			else if(colNames[29].equals(property)){
				return prop.getPrecision() == null?"":prop.getPrecision();
			}
			else if(colNames[30].equals(property)){
				return prop.getSizeLimit() == null?"":prop.getSizeLimit();
			}
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getAllFieldExceptModi(String id){
		Object object = view.getTv().getInput();
		List<String> list = new ArrayList<String>();
		if(object instanceof List){
			List<FormElement> allElements = (List<FormElement>)object;
			for (int i = 0; i < allElements.size(); i++) {
				FormElement formel = (FormElement) allElements.get(i);
				if(formel.getId() != null && !(formel.getId().equals(id)))
					list.add(formel.getId());
				}
			}
		return list;
	}


	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object o = item.getData();
		if(o instanceof FormElement){
			FormElement prop = (FormElement)o;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			if(property.endsWith(colNames[0])){
				List<String> list = getAllFieldExceptModi(old.toString());
				if(list.contains(value)){
					MessageDialog.openError(null, "错误提示", "此表单已经存在ID为" + value+ "的元素!");
					return;
				}
			}
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			FormEditor editor = FormEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	}
	

}


