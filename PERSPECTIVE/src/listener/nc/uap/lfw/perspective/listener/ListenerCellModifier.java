package nc.uap.lfw.perspective.listener;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;

public class ListenerCellModifier implements ICellModifier {
	
	public static final String[] colNames = {"事件名称", "参数", "代码"};

	private TableViewer tableViewer;
	
	public ListenerCellModifier(TableViewer tableView) {
		this.tableViewer = tableView;
	}

	public boolean canModify(Object element, String property) {
		return true;
	}

	public Object getValue(Object element, String property) {
		return null;
	}

	public void modify(Object element, String property, Object value) {
		
	}
	
	
	
}
