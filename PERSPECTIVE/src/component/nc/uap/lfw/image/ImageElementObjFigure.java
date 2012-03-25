package nc.uap.lfw.image;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * Í¼ÐÎ¿Ø¼þµÄÍ¼ÐÎ
 * @author zhangxya
 *
 */
public class ImageElementObjFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 207, 132, 86);
	public ImageElementObjFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<Í¼Æ¬>>");
		setBackgroundColor(bgColor);
		ImageElementObj imageobj = (ImageElementObj) ele;
		setTitleText(imageobj.getImageComp().getId(), imageobj.getImageComp().getId());
		markError(imageobj.validate());
		Point point = imageobj.getLocation();
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
		return "<<Í¼Æ¬>>";
	}
}
	
