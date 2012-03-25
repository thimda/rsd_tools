package nc.uap.portal.plugin.page;


import nc.uap.portal.plugin.PluginEditor;
import nc.uap.portal.plugins.model.Extension;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

/**
 * PluginPropertiesView用CellModifier
 * 
 * @author dingrf
 *
 */
public class PluginCellModifier implements ICellModifier {
	
	private PluginPropertiesView view = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private Extension attr = null;
		public CellModifiCommand(String property, Object value, Extension attr) {
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
	
	/**列名称*/
	public static final String[] colNames = {"ID","classname","title","isactive"};
	
	
	public PluginCellModifier(PluginPropertiesView view) {
		super();
		this.view = view;
	}
	
	/**
	 * 是否可修改
	 */
	@Override
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * 取列值
	 */
	@Override
	public Object getValue(Object element, String property) {
		if(element instanceof Extension){
			Extension prop = (Extension)element;
			if(colNames[0].equals(property)){
				return prop.getId()==null? "" : prop.getId();
			}
			else if(colNames[1].equals(property)){
				return prop.getClassname()==null?"":prop.getClassname();
			}
			else if(colNames[2].equals(property)){
				return prop.getTitle()==null?"":prop.getTitle();
			}
			else if(colNames[3].equals(property)){
				return prop.getIsactive()==true? new Integer(0):new Integer(1);
			}
		}
		return "";
	}

	/**
	 * 修改列值
	 */
	@Override
	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof Extension){
			Extension prop = (Extension)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			PluginEditor editor = PluginEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	
	private void modifyAttr(Extension prop, String property,Object value){
		 if(colNames[0].equals(property)){
			 prop.setId((String)value);
		 }
		else if(colNames[1].equals(property)){
			 prop.setClassname((String)value);
		}
		else if(colNames[2].equals(property)){
			prop.setTitle((String)value);
		}
		else if(colNames[3].equals(property)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			prop.setIsactive(truevalue);	
		}
		view.getTv().update(prop, null);
	}
}

