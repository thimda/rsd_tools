package nc.uap.portal.managerapps.page;

import nc.uap.portal.managerapps.ManagerAppsEditor;
import nc.uap.portal.om.ManagerNode;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ManagerNodePropertiesView 用 CellModifier
 * 
 * @author dingrf
 *
 */
public class ManagerNodeCellModifier implements ICellModifier {
	
	private ManagerNodePropertiesView view = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private ManagerNode attr = null;
		public CellModifiCommand(String property, Object value, ManagerNode attr) {
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
	public static final String[] colNames = {"id","text","i18nName","url"};
	
	
	public ManagerNodeCellModifier(ManagerNodePropertiesView view) {
		super();
		this.view = view;
	}
	
	/**
	 * 是否可修改
	 */
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * 取列值
	 */
	public Object getValue(Object element, String property) {
		if(element instanceof ManagerNode){
			ManagerNode prop = (ManagerNode)element;
			if(colNames[0].equals(property)){
				return prop.getId()==null? "" : prop.getId();
			}
			else if(colNames[1].equals(property)){
				return prop.getText()==null?"":prop.getText();
			}
			else if(colNames[2].equals(property)){
				return prop.getI18nName()==null?"":prop.getI18nName();
			}
			else if(colNames[3].equals(property)){
				return prop.getUrl()==null?"":prop.getUrl();
			}
		}
		return "";
	}

	/**
	 * 修改列值
	 */
	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof ManagerNode){
			ManagerNode prop = (ManagerNode)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			ManagerAppsEditor editor = ManagerAppsEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	private void modifyAttr(ManagerNode prop, String property,Object value){
		 if(colNames[0].equals(property)){
			 prop.setId((String)value);
		 }
		else if(colNames[1].equals(property)){
			 prop.setText((String)value);
		}
		else if(colNames[2].equals(property)){
			prop.setI18nName((String)value);
		}
		else if(colNames[3].equals(property)){
			prop.setUrl((String)value);
		}
		view.getTv().update(prop, null);
	}
}

