package nc.uap.lfw.grid.action;

import nc.uap.lfw.grid.core.GridPropertisView;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

/**
 * gridȡ��ѡ��������
 * @author zhangxya
 *
 */
public class GridUnSelectedAllAction extends Action{
	private GridPropertisView view;
	
	public GridUnSelectedAllAction(GridPropertisView view) {
		setText("ȫ��ѡ");
		setHoverImageDescriptor(PaletteImage.getUnSelectedAllImgDescriptor());
		this.view = view;
	}
	
	public void run(){
		CheckboxTreeViewer ctx = view.getCtv();
		ctx.setAllChecked(false);
	}
}
