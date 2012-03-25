package nc.uap.lfw.funnode;

import java.util.List;

import nc.uap.lfw.design.itf.TemplateVO;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

/**
 * 用于模板的内容标签器
 * @author zhangxya
 *
 */
public class TemplatePropertiewViewProvider extends LabelProvider implements
		ITableLabelProvider, ITreeContentProvider {
	public TemplatePropertiewViewProvider() {
		super();
	}

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		TemplateVO item = (TemplateVO) element;
		switch (columnIndex) {
			case 0:
				return item.getPk_template();
		case 1:
			return item.getTemplatecaption();
		case 2:
			return item.getNodekey();
		case 3:
			if(item.getTemplatetype() == null)
				return "";
			if(item.getTemplatetype()!= null && item.getTemplatetype().equals(TemplateVO.TEMPLAGE_TYPE_BILL))
				return "单据模板";
			else 
				if(item.getTemplatetype() != null && item.getTemplatetype().equals(TemplateVO.TEMPLAGE_TYPE_PRINT))
					return "打印模板";
			else if(item.getTemplatetype() != null && item.getTemplatetype().equals(TemplateVO.TEMPLAGE_TYPE_QUERY))
				return "查询模板";
			else if(item.getTemplatetype() != null && item.getTemplatetype().equals(TemplateVO.TEMPLAGE_TYPE_REPORT))
				return "报表模板";
		default:
			return null;
		}

	}


	public Object[] getChildren(Object parentElement) {
		return null;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
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
