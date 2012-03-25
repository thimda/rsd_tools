package nc.uap.lfw.button;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * button按钮图形
 * @author zhangxya
 *
 */
public class ButtonElementObjFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 002,174,141);
	public ButtonElementObjFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<按钮>>");
		setBackgroundColor(bgColor);
		ButtonElementObj buttonobj = (ButtonElementObj) ele;
		setTitleText(buttonobj.getButtonComp().getId(), buttonobj.getButtonComp().getId());
		markError(buttonobj.validate());
		Point point = buttonobj.getLocation();
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
		return "<<按钮>>";
	}
}
	
