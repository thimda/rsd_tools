/**
 * 
 */
package nc.uap.lfw.editor.application;

import nc.lfw.editor.common.LFWBaseRectangleFigure;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * 
 * Applicationͼ����ʾ������
 * @author chouhl
 *
 */
public class ApplicationFigure extends LFWBaseRectangleFigure {

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
	private static Color bgColor = new Color(null, 190, 195, 240);

	public ApplicationFigure(ApplicationObj obj) {
		super(obj);
		
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
		return "<<Application>>";
	}

	protected String getTypeLabText() {
		return "<<Application>>";
	}
	
	public Color getBackgroundColor() {
		return bgColor;
	}
	
}
