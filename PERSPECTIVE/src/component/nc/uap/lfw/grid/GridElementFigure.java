package nc.uap.lfw.grid;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * grid图形i
 * @author zhangxya
 *
 */
public class GridElementFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 57, 242, 235);
	public GridElementFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<表格>>");
		setBackgroundColor(bgColor);
		GridElementObj gridobj = (GridElementObj) ele;
		setTitleText(gridobj.getGridComp().getId(), gridobj.getGridComp().getId());
		markError(gridobj.validate());
		Point point = gridobj.getLocation();
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
		return "<<表格>>";
	}
	
}
