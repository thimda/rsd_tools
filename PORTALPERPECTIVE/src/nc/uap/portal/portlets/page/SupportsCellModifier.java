package nc.uap.portal.portlets.page;


import java.util.List;

import nc.uap.portal.container.om.Supports;
import nc.uap.portal.portlets.PortletEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

/**
 * SupportsCellModifier
 * 
 * @author dingrf
 *
 */
public class SupportsCellModifier implements ICellModifier {
	
	private SupportsPropertiesView view = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private Supports attr = null;
		public CellModifiCommand(String property, Object value, Supports attr) {
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
	public static final String[] colNames = {"mime类型","Portal模式","window状态"};
	
	
	public SupportsCellModifier(SupportsPropertiesView view) {
		super();
		this.view = view;
	}
	
	
	public boolean canModify(Object element, String property) {
		return true;
	}

	public Object getValue(Object element, String property) {
		if(element instanceof Supports){
			Supports prop = (Supports)element;
			if(colNames[0].equals(property)){
				return prop.getMimeType()==null? "" : prop.getMimeType();
			}else if(colNames[1].equals(property)){
				List<String> listValue = prop.getPortletModes();
				String values="";
				for(int i=0;i<listValue.size();i++){
					if(i != listValue.size() -1 )
						values += listValue.get(i) + ",";
					else 
						values += listValue.get(i);
				}
				return values;
			}else if(colNames[2].equals(property)){
				List<String> listValue = prop.getWindowStates();
				String values="";
				for(int i=0;i<listValue.size();i++){
					if(i != listValue.size() -1 )
						values += listValue.get(i) + ",";
					else 
						values += listValue.get(i);
				}
				return values;
			}
		}
		return "";
	}

	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof Supports){
			Supports prop = (Supports)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			PortletEditor editor = PortletEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	
	private void modifyAttr(Supports prop, String property,Object value){
		 if(colNames[0].equals(property)){
			 prop.setMimeType((String)value);
		 }
		else if(colNames[1].equals(property)){
			prop.getPortletModes().clear();
			String[] values = ((String)value).split(",");
			for (String v :values){
				prop.getPortletModes().add(v);
			}
		}
		else if(colNames[2].equals(property)){
			prop.getWindowStates().clear();
			String[] values = ((String)value).split(",");
			for (String v :values){
				prop.getWindowStates().add(v);
			}
		}
		view.getTv().update(prop, null);
	}
}

