package nc.uap.lfw.tree;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * ���ؼ�ͼ��
 * @author zhangxya
 *
 */
public class TreeElementObjFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 111,200,116);
	public TreeElementObjFigure(LfwElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<��>>");
		setBackgroundColor(bgColor);
		TreeElementObj treeobj = (TreeElementObj) ele;
		setTitleText(treeobj.getTreeComp().getId(), treeobj.getTreeComp().getId());
		
		markError(treeobj.validate());
		Point point = treeobj.getLocation();
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
		return "<<��>>";
	}
	
}