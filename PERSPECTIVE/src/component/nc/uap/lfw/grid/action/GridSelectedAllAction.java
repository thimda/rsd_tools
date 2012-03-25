package nc.uap.lfw.grid.action;

import nc.uap.lfw.grid.core.GridPropertisView;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

/**
 * gridѡ��������
 * @author zhangxya
 *
 */
public class GridSelectedAllAction extends Action{
	
	private GridPropertisView view;
	
	public GridSelectedAllAction(GridPropertisView view) {
		super("ȫѡ");
		setHoverImageDescriptor(PaletteImage.getSelectedAllImgDescriptor());
		this.view = view;
	}
	
	public void run(){
		CheckboxTreeViewer ctx = view.getCtv();
		ctx.setAllChecked(true);
	}
}


