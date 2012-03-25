package nc.lfw.editor.pagemeta;

import nc.lfw.editor.common.LFWBaseRectangleFigure;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * 页面状态 图像
 * 
 * @author guoweic
 *
 */
public class PageStateElementFigure extends LFWBaseRectangleFigure {

	private PageStateElementObj pageStateObj;
	
	// 默认大小
	private Dimension dimen;
	// 总高度
	private int height = 0;

	private static Color bgColor = new Color(null, 57, 242, 235);
	
	public PageStateElementFigure(PageStateElementObj pageStateObj) {
		super(pageStateObj);
		
		this.pageStateObj = pageStateObj;
		
		setTypeLabText("<<页面状态>>");
		
		setTitleText("", "");
		
		addPageStates();
		
		setBackgroundColor(bgColor);
		
		pageStateObj.setFigure(this);
		
		markError(pageStateObj.validate());
		// 设置大小和位置
		Point point = pageStateObj.getLocation();
		dimen = pageStateObj.getSize();
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width, dimen.height < this.height ? this.height : dimen.height));
		
	}

	
	protected String getTypeText() {
		return "<<页面状态>>";
	}
	
	/**
	 * 显示所有页面状态
	 */
	private void addPageStates() {}
	
	/**
	 * 删除页面状态
	 * @param label
	 */
	public void deletePageState(PageStateLabel label) {
		getContentFigure().remove(label);
		this.height -= LINE_HEIGHT;
		resizeHeight();
	}
	
	/**
	 * 重新设置高度
	 */
	private void resizeHeight() {
		setSize(dimen.width, dimen.height < this.height ? this.height : dimen.height);
	}


	public PageStateElementObj getPageStateObj() {
		return pageStateObj;
	}

}
