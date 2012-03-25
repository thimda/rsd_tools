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
public class PluginDescElementFigure extends LFWBaseRectangleFigure {

	// Ĭ�ϴ�С
	private Dimension dimen;
	// �ܸ߶�
	private int height = 0;

	private static Color bgColor = new Color(null, 239, 255, 150);
	
	public PluginDescElementFigure(PluginDescElementObj pluginObj) {
		super(pluginObj);
		setTypeLabText("<<plugin>>");
		
		setBackgroundColor(bgColor);
		
		pluginObj.setFigure(this);
		
		setTitleText(pluginObj.getPlugin().getId(), pluginObj.getPlugin().getId());

		// ���ô�С��λ��
		Point point = pluginObj.getLocation();
		dimen = pluginObj.getSize();
		this.height += 1 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width, dimen.height < this.height ? this.height : dimen.height));
		
	}
	
	@Override
	protected String getTypeText() {
		return "<<plugin>>";
	}

	
}
