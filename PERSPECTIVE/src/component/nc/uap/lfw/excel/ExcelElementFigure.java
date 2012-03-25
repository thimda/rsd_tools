package nc.uap.lfw.excel;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class ExcelElementFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 57, 242, 235);
	public ExcelElementFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<Excel>>");
		setBackgroundColor(bgColor);
		ExcelElementObj excelobj = (ExcelElementObj) ele;
		setTitleText(excelobj.getExcelComp().getId(), excelobj.getExcelComp().getId());
		markError(excelobj.validate());
		Point point = excelobj.getLocation();
		int x, y;
		if(point != null){
			x = point.x;
			y = point.y;
		}else{
			x = 100;
			y = 100;
		}
		setBounds(new Rectangle(x, y, 150, 150));
		
	}

	
	protected String getTypeText() {
		return "<<Excel>>";
	}
	
}
