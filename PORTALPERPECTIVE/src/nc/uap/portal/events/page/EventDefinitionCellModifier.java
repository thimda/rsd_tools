package nc.uap.portal.events.page;


import nc.uap.portal.container.om.EventDefinition;
import nc.uap.portal.events.EventsEditor;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;
/**
 * EventsPropertiesView��CellModifier
 * 
 * @author dingrf
 *
 */

public class EventDefinitionCellModifier implements ICellModifier {
	
	/**�¼�����ģ����ͼ*/
	private EventsPropertiesView view = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private EventDefinition attr = null;
		public CellModifiCommand(String property, Object value, EventDefinition attr) {
			super("�޸�����");
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
	
	/**�ж���*/
	public static final String[] colNames = {"����","ֵ����"};
	
	
	public EventDefinitionCellModifier(EventsPropertiesView view) {
		super();
		this.view = view;
	}
	
	/**
	 * �Ƿ���޸�
	 */
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * ȡֵ 
	 */
	public Object getValue(Object element, String property) {
		if(element instanceof EventDefinition){
			EventDefinition prop = (EventDefinition)element;
			if(colNames[0].equals(property)){
				return prop.getName()==null? "" : prop.getName();
			}else if(colNames[1].equals(property)){
				return prop.getValueType()==null?"":prop.getValueType();
			}
		}
		return "";
	}

	/**
	 * �޸Ĳ���
	 */
	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof EventDefinition){
			EventDefinition prop = (EventDefinition)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			EventsEditor editor = EventsEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	
	private void modifyAttr(EventDefinition prop, String property,Object value){
		 if(colNames[0].equals(property)){
			 prop.setName((String)value);
		 }
		else if(colNames[1].equals(property)){
			 prop.setValueType((String)value);
		}
		view.getTv().update(prop, null);
	}
}

