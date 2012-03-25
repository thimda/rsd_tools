package nc.uap.portal.plugin.page;

import java.util.ArrayList;
import java.util.List;

import nc.uap.portal.plugins.model.Extension;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

/**
 * PluginPropertiesView”√LabelProvider
 * 
 * @author dingrf
 *
 */
public class PluginProvider extends LabelProvider implements
		ITableLabelProvider, ITreeContentProvider {
	public PluginProvider() {
		super();
	}

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}


	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if(element instanceof Extension){
			return ((Extension)element).getId() == null? "":((Extension)element).getId();
			}
			break;
		case 1:
			if(element instanceof Extension){
			return ((Extension)element).getClassname() == null? "":((Extension)element).getClassname() ;
			}
			break;
		case 2:
			if(element instanceof Extension){
				return ((Extension)element).getTitle() == null? "":((Extension)element).getTitle() ;
			}
			break;
		case 3:
			if(element instanceof Extension){
				return ((Extension)element).getIsactive() == true? "Y":"N";
			}
			break;
		}
		return null;
	}

	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof Extension){
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
