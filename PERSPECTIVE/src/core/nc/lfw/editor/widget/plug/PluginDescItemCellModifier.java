package nc.lfw.editor.widget.plug;


import nc.lfw.editor.widget.WidgetEditor;
import nc.uap.lfw.core.page.PluginDescItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

/**
 * PluginDescCellModifier
 * 
 * @author dingrf
 *
 */
public class PluginDescItemCellModifier implements ICellModifier {
	
	private PluginDescItemPropertiesView view = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private PluginDescItem attr = null;
		public CellModifiCommand(String property, Object value, PluginDescItem attr) {
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
	
	/**������*/
	public static final String[] colNames = {"ID","�����������"};
	
	
	public PluginDescItemCellModifier(PluginDescItemPropertiesView view) {
		super();
		this.view = view;
	}
	
	/**
	 * �Ƿ���޸�
	 */
	public boolean canModify(Object element, String property) {
		if (view.isCanEdit())
			return true;
		else
			return false;
	}

	/**
	 * ȡ��ֵ
	 */
	public Object getValue(Object element, String property) {
		if(element instanceof PluginDescItem){
			PluginDescItem prop = (PluginDescItem)element;
			if(colNames[0].equals(property)){
				return prop.getId() == null? "" : prop.getId();
//			}else if(colNames[1].equals(property)){
//				return prop.getValue() == null?"":prop.getValue();
			}else if(colNames[1].equals(property)){
				return prop.getClazztype() == null?"":prop.getClazztype();
			}	
		}
		return "";
	}

	/**
	 * �޸���ֵ
	 */
	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof PluginDescItem){
			PluginDescItem prop = (PluginDescItem)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			WidgetEditor editor = (WidgetEditor)WidgetEditor.getActiveEditor();
			editor.setDirtyTrue();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	
	private void modifyAttr(PluginDescItem prop, String property,Object value){
		if (colNames[0].equals(property)) {
			prop.setId((String) value);
		} 
//		else if (colNames[1].equals(property)) {
//			prop.setValue((String) value);
//		}
		else if (colNames[1].equals(property)) {
			prop.setClazztype((String) value);
		}
		view.getTv().update(prop, null);
	}
}

