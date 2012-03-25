package nc.lfw.editor.pagemeta;


import nc.lfw.editor.pagemeta.PlugRelationDialog.PlugRelation;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TreeItem;

/**
 * PlugoutDescCellModifier
 * 
 * @author dingrf
 *
 */
public class PlugRelationCellModifier implements ICellModifier {
	
	private PlugRelationDialog dialog = null;
	
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private PlugRelation attr = null;
		public CellModifiCommand(String property, Object value, PlugRelation attr) {
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
	public static final String[] colNames = {"�����ֵ","�����ֵ"};
	
	
	public PlugRelationCellModifier(PlugRelationDialog dialog) {
		super();
		this.dialog = dialog;
	}
	
	/**
	 * �Ƿ���޸�
	 */
	public boolean canModify(Object element, String property) {
		if(colNames[1].equals(property)){
			return true;
		}else
			return false;
	}

	/**
	 * ȡ��ֵ
	 */
	public Object getValue(Object element, String property) {
		if(element instanceof PlugRelation){
			PlugRelation prop = (PlugRelation)element;
			if(colNames[0].equals(property)){
				return prop.getOutValue() == null ? "" : prop.getOutValue();
			}else if(colNames[1].equals(property)){
				return prop.getInValue() == null ? "" : prop.getInValue();
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
		if(object instanceof PlugRelation){
			PlugRelation prop = (PlugRelation)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			PagemetaEditor editor = (PagemetaEditor)PagemetaEditor.getActiveEditor();
			editor.setDirtyTrue();
			
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	
	private void modifyAttr(PlugRelation prop, String property,Object value){
		if (colNames[0].equals(property)) {
			prop.setOutValue((String) value);
		} 
		else if (colNames[1].equals(property)) {
			prop.setInValue((String) value);
		}
		dialog.getTv().update(prop, null);
	}
}

