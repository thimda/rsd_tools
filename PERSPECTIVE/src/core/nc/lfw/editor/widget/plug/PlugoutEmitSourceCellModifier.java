package nc.lfw.editor.widget.plug;


import nc.lfw.editor.widget.plug.PlugoutEmitSourceDialog.PlugoutEmitSource;

import org.eclipse.jface.viewers.ICellModifier;

/**
 * PlugoutEmitCellModifier
 * 
 * @author dingrf
 *
 */
public class PlugoutEmitSourceCellModifier implements ICellModifier {
	
	/**������*/
	public static final String[] colNames = {"����������"};
	
	
	public PlugoutEmitSourceCellModifier() {
		super();
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
		if(element instanceof PlugoutEmitSource){
			PlugoutEmitSource prop = (PlugoutEmitSource)element;
			if(colNames[0].equals(property)){
				return prop.getSourceType() == null ? "" : prop.getSourceType();
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

