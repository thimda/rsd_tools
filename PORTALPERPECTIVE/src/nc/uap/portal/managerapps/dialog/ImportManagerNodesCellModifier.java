package nc.uap.portal.managerapps.dialog;


import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

/**
 * ���빦�ܽڵ��� CellModifier
 * 
 * @author dingrf
 *
 */
public class ImportManagerNodesCellModifier implements ICellModifier {
	
	/**���빦�ܶԻ���*/
	private ImportManagerNodesDialog dialog = null;
	
	/**������*/
	public static final String[] colNames = {"ѡ��","����","ID",};
	
	
	public ImportManagerNodesCellModifier(ImportManagerNodesDialog dialog) {
		super();
		this.dialog = dialog;
	}
	
	
	/**
	 * �Ƿ���޸�
	 * */
	public boolean canModify(Object element, String property) {
		if(colNames[0].equals(property)){
			return true;
		}
		else
			return false;
	}

	/**
	 * ȡ��ֵ
	 * 
	 */
	public Object getValue(Object element, String property) {
		if(element instanceof PageNode){
			PageNode prop = (PageNode)element;
			if(colNames[0].equals(property)){
				return prop.getCheck();
			}
			else if(colNames[1].equals(property)){
				return prop.getName()==null?"":prop.getName();
			}
			else if(colNames[2].equals(property)){
				return prop.getId()==null?"":prop.getId();
			}
		}
		return "";
	}

	/**
	 * �޸���ֵ
	 */
	public void modify(Object element, String property, Object value) {
		TableItem item = (TableItem)element;
		Object object = item.getData();
		if(object instanceof PageNode){
			PageNode prop = (PageNode)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			modifyAttr(prop,property,value);
		}
	}

	private void modifyAttr(PageNode prop, String property,Object value){
		 if(colNames[0].equals(property)){
			prop.setCheck((Boolean)value);
		 }
		dialog.getTv().update(prop, null);
	}
}

