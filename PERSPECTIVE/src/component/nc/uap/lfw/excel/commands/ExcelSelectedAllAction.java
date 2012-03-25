package nc.uap.lfw.excel.commands;

import nc.uap.lfw.excel.core.ExcelPropertiesView;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

public class ExcelSelectedAllAction extends Action{
	
	private ExcelPropertiesView view;
	
	public ExcelSelectedAllAction(ExcelPropertiesView view) {
		super("ȫѡ");
		setHoverImageDescriptor(PaletteImage.getSelectedAllImgDescriptor());
		this.view = view;
	}
	
	public void run(){
		CheckboxTreeViewer ctx = view.getCtv();
		ctx.setAllChecked(true);
	}
}


