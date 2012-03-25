package nc.lfw.editor.widget.plug;


import nc.lfw.editor.widget.plug.PlugoutDescSourceDialog.PlugoutDescSource;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellModifier;

/**
 * PlugoutDescCellModifier
 * 
 * @author dingrf
 *
 */
public class PlugoutDescSourceCellModifier implements ICellModifier {
	
//	private PlugoutDescSourceDialog dialog = null;
	
//	private class CellModifiCommand extends Command{
//		private String property = "";
//		private Object value = null;
//		private Object oldValue = null;
//		private PlugRelation attr = null;
//		public CellModifiCommand(String property, Object value, PlugRelation attr) {
//			super("�޸�����");
//			this.property = property;
//			this.value = value;
//			this.attr = attr;
//		}
//		
//		public void execute() {
//			oldValue = getValue(attr, property);
//			redo();
//		}
//		
//		public void redo() {
//		}
//		
//		public void undo() {
//		}
//	}
	
	/**������*/
	public static final String[] colNames = {"��Դ����"};
	
	
	public PlugoutDescSourceCellModifier(PlugoutDescSourceDialog dialog) {
		super();
//		this.dialog = dialog;
	}
	
	/**
	 * �Ƿ���޸�
	 */
	public boolean canModify(Object element, String property) {
		return false;
	}

	/**
	 * ȡ��ֵ
	 */
	public Object getValue(Object element, String property) {
		if(element instanceof PlugoutDescSource){
			PlugoutDescSource prop = (PlugoutDescSource)element;
			if(colNames[0].equals(property)){
				return prop.getSourceName() == null ? "" : prop.getSourceName();
			}
//			else if(colNames[1].equals(property)){
//				return prop.getSourceId() == null ? "" : prop.getSourceId();
//			}
		}
		return "";
	}

	@Override
	public void modify(Object element, String property, Object value) {
	}
}

