package nc.uap.lfw.label;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * ��ǩͼ��
 * @author zhangxya
 *
 */
public class LabelElementObjFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 45, 186, 206);
	public LabelElementObjFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<��ǩ>>");
		setBackgroundColor(bgColor);
		LabelElementObj labelobj = (LabelElementObj) ele;
		setTitleText(labelobj.getLabelComp().getId(), labelobj.getLabelComp().getId());
		markError(labelobj.validate());
		Point point = labelobj.getLocation();
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
		return "<<��ǩ>>";
	}
}
	
