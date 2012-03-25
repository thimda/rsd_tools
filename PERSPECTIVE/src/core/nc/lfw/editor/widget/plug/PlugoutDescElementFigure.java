package nc.lfw.editor.widget.plug;

import nc.lfw.editor.common.LFWBaseRectangleFigure;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * PlugoutDescͼ��
 * @author dingrf
 *
 */
public class PlugoutDescElementFigure extends LFWBaseRectangleFigure {

	// Ĭ�ϴ�С
	private Dimension dimen;
	// �ܸ߶�
	private int height = 0;

	private static Color bgColor = new Color(null, 239, 255, 150);
	
	public PlugoutDescElementFigure(PlugoutDescElementObj plugoutObj) {
		super(plugoutObj);
		setTypeLabText("<<plugout>>");
		
		setBackgroundColor(bgColor);
		
		plugoutObj.setFigure(this);
		
		setTitleText(plugoutObj.getPlugout().getId(), plugoutObj.getPlugout().getId());

		// ���ô�С��λ��
		Point point = plugoutObj.getLocation();
		dimen = plugoutObj.getSize();
		this.height += 1 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width, dimen.height < this.height ? this.height : dimen.height));
		
	}
	
	@Override
	protected String getTypeText() {
		return "<<plugout>>";
	}

	
}
