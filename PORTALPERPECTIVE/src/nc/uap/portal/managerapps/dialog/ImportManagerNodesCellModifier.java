package nc.uap.portal.managerapps.dialog;


import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

/**
 * 导入功能节点用 CellModifier
 * 
 * @author dingrf
 *
 */
public class ImportManagerNodesCellModifier implements ICellModifier {
	
	/**导入功能对话框*/
	private ImportManagerNodesDialog dialog = null;
	
	/**列数组*/
	public static final String[] colNames = {"选择","名称","ID",};
	
	
	public ImportManagerNodesCellModifier(ImportManagerNodesDialog dialog) {
		super();
		this.dialog = dialog;
	}
	
	
	/**
	 * 是否可修改
	 * */
	public boolean canModify(Object element, String property) {
		if(colNames[0].equals(property)){
			return true;
		}
		else
			return false;
	}

	/**
	 * 取列值
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
	 * 修改列值
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

