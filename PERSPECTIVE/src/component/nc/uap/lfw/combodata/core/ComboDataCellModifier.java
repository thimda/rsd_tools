package nc.uap.lfw.combodata.core;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.combodata.CombItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 静态下拉框cell Modifier
 * @author zhangxya
 *
 */
public class ComboDataCellModifier  implements ICellModifier {
	private class CellModifiCommand extends Command{
		private String property = "";
		private Object value = null;
		private Object oldValue = null;
		private CombItem attr = null;
		public CellModifiCommand(String property, Object value, CombItem attr) {
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
	
	private ComboDataPropertiesView view = null;
	
	public ComboDataCellModifier(ComboDataPropertiesView view) {
		super();
		this.view = view;
	}
	private  TreeViewer getTreeViewer(){
		return getPropertiesView().getTv();
	}
	private ComboDataPropertiesView getPropertiesView(){
		return view;
	}
	public boolean canModify(Object element, String property) {
		return true;
	}

	public Object getValue(Object element, String property) {
		if(element instanceof CombItem){
			CombItem prop = (CombItem)element;
			if(ComboDataPropertiesView.colNames[0].equals(property)){
				return prop.getText()==null? "" : prop.getText();
			}else if(ComboDataPropertiesView.colNames[1].equals(property)){
				return prop.getValue()==null?"":prop.getValue();
			}else if(ComboDataPropertiesView.colNames[2].equals(property)){
				return prop.getI18nName()==null?"":prop.getI18nName();
			}else if(ComboDataPropertiesView.colNames[3].equals(property)){
				return prop.getImage()==null?"":prop.getImage();
			}
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllFieldExceptModi(String id){
		Object object = view.getTv().getInput();
		List<String> list = new ArrayList<String>();
		if(object instanceof List){
			List<CombItem> allColumns = (List<CombItem>)object;
			for (int i = 0; i < allColumns.size(); i++) {
				CombItem combo = (CombItem) allColumns.get(i);
				if(combo.getValue() != null && !(combo.getValue().equals(id)))
					list.add(combo.getText());
				}
			}
		return list;
	}

	public void modify(Object element, String property, Object value) {
		TreeItem item = (TreeItem)element;
		Object object = item.getData();
		if(object instanceof CombItem){
			CombItem prop = (CombItem)object;
			Object old = getValue(prop, property);
			if(old != null && old.equals(value))
				return;
			if(property.endsWith(ComboDataPropertiesView.colNames[1])){
				List<String> list = getAllFieldExceptModi(old.toString());
				if(list.contains(value)){
					MessageDialog.openError(null, "错误提示", "此下拉框已经存在真实值为" + value+ "的项!");
					return;
				}
			}
			CellModifiCommand cmd = new CellModifiCommand(property, value, prop);
			ComboDataEditor editor = ComboDataEditor.getActiveEditor();
			if(editor != null)
				editor.executComand(cmd);
		}
	}

	private void modifyAttr(CombItem prop, String property,Object value){
		if(ComboDataPropertiesView.colNames[0].equals(property)){
			 prop.setText((String)value);
		}
		else if(ComboDataPropertiesView.colNames[1].equals(property)){
			 prop.setValue((String)value);
		}
		else if(ComboDataPropertiesView.colNames[2].equals(property)){
			 prop.setI18nName((String)value);
		}
		else if(ComboDataPropertiesView.colNames[3].equals(property)){
			prop.setImage((String)value);
		}
		getTreeViewer().refresh(prop);
		view.getTv().update(prop, null);
	}
}

