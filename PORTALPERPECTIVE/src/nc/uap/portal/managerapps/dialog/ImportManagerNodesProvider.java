package nc.uap.portal.managerapps.dialog;

import java.util.ArrayList;
import java.util.List;

import nc.uap.portal.perspective.PortalPlugin;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

/**
 * ���빦�ܽڵ��� LabelProvider
 * 
 * @author dingrf
 *
 */
public class ImportManagerNodesProvider extends LabelProvider implements
		ITableLabelProvider, ITreeContentProvider {
	public ImportManagerNodesProvider() {
		super();
	}

	/**
	 * ȡ��һ�ж�ӦͼƬ
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if(element instanceof PageNode){
			return ((PageNode)element).getCheck() == true ? PortalPlugin.loadImage(PortalPlugin.ICONS_PATH, "checked.gif").createImage() : PortalPlugin.loadImage(PortalPlugin.ICONS_PATH, "unchecked.gif").createImage();
			}
			break;
		}
		return null;
	}


	/**
	 * ȡ�е���ʾֵ 
	 */
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 1:
			if(element instanceof PageNode){
			return ((PageNode)element).getName() == null? "":((PageNode)element).getName() ;
			}
			break;
		case 2:
			if(element instanceof PageNode){
				return ((PageNode)element).getId() == null? "":((PageNode)element).getId() ;
			}
			break;
		}
		return null;
	}
	

	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof PageNode){
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
