package nc.uap.lfw.combodata;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * ����������
 * @author zhangxya
 *
 */
public class ComboDataElementFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 239, 255, 150);
	public ComboDataElementFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<��������>>");
		setBackgroundColor(bgColor);
		ComboDataElementObj comboobj = (ComboDataElementObj) ele;
		setTitleText(comboobj.getCombodata().getId(),comboobj.getCombodata().getId());
		
		markError(comboobj.validate());
		Point point = comboobj.getLocation();
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
		return "<<��������>>";
	}
	
}