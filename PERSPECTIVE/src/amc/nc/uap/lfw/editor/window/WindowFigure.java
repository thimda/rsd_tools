/**
 * 
 */
package nc.uap.lfw.editor.window;

import nc.lfw.editor.common.LFWBaseRectangleFigure;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * 
 * Windowͼ����ʾ������
 * @author chouhl
 *
 */
public class WindowFigure extends LFWBaseRectangleFigure {
	
	/**
	 * ��С
	 */
	private Dimension dimen;
	/**
	 * �ܸ߶�
	 */
	private int height = 0;
	/**
	 * ͼ����ɫ
	 */
	private static Color bgColor = new Color(null, 57, 242, 235);
	/**
	 * Windowͼ��
	 */
	private WindowObj obj;

	public WindowFigure(WindowObj obj) {
		super(obj);
		this.obj = obj;
		
		setTypeLabText(getTypeLabText());
		setBackgroundColor(getBackgroundColor());
		obj.setFigure(this);
		markError(obj.validate());
		//���ô�С��λ��
		Point point = obj.getLocation();
		dimen = obj.getSize();
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width, dimen.height < this.height ? this.height : dimen.height));
	}

	protected String getTypeText() {
		return "<<" + obj.getWindow().getCaption() + ">>";
	}

	protected String getTypeLabText() {
		return "<<" + obj.getWindow().getCaption() + ">>";
	}

	public Color getBackgroundColor() {
		return bgColor;
	}

}
