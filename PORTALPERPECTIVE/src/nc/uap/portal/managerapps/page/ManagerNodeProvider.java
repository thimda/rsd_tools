package nc.uap.portal.managerapps.page;

import java.util.ArrayList;
import java.util.List;


import nc.uap.portal.om.ManagerNode;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

public class ManagerNodeProvider extends LabelProvider implements
		ITableLabelProvider, ITreeContentProvider {
	public ManagerNodeProvider() {
		super();
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * 取列的显示值
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if(element instanceof ManagerNode){
			return ((ManagerNode)element).getId() == null? "":((ManagerNode)element).getId();
			}
			break;
		case 1:
			if(element instanceof ManagerNode){
			return ((ManagerNode)element).getText() == null? "":((ManagerNode)element).getText() ;
			}
			break;
		case 2:
			if(element instanceof ManagerNode){
				return ((ManagerNode)element).getI18nName() == null? "":((ManagerNode)element).getI18nName() ;
			}
			break;
		case 3:
			if(element instanceof ManagerNode){
				return ((ManagerNode)element).getUrl() == null? "":((ManagerNode)element).getUrl();
			}
			break;
		}
		return null;
	}

	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof ManagerNode){
			return new Object[0];
		}
		else return new Object[0];
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if(element instanceof ArrayList)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof List)
			return ((List) inputElement).toArray();
		else 
			return new Object[0];
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}
