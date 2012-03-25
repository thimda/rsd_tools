package nc.uap.lfw.excel.commands;

import nc.uap.lfw.excel.core.ExcelPropertiesView;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

public class ExcelUnSelectedAllAction extends Action{
	private ExcelPropertiesView view;
	
	public ExcelUnSelectedAllAction(ExcelPropertiesView view) {
		setText("È«²»Ñ¡");
		setHoverImageDescriptor(PaletteImage.getUnSelectedAllImgDescriptor());
		this.view = view;
	}
	
	public void run(){
		CheckboxTreeViewer ctx = view.getCtv();
		ctx.setAllChecked(false);
	}
}
