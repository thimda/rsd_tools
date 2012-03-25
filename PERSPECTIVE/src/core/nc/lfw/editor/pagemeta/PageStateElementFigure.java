package nc.lfw.editor.pagemeta;

import nc.lfw.editor.common.LFWBaseRectangleFigure;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * ҳ��״̬ ͼ��
 * 
 * @author guoweic
 *
 */
public class PageStateElementFigure extends LFWBaseRectangleFigure {

	private PageStateElementObj pageStateObj;
	
	// Ĭ�ϴ�С
	private Dimension dimen;
	// �ܸ߶�
	private int height = 0;

	private static Color bgColor = new Color(null, 57, 242, 235);
	
	public PageStateElementFigure(PageStateElementObj pageStateObj) {
		super(pageStateObj);
		
		this.pageStateObj = pageStateObj;
		
		setTypeLabText("<<ҳ��״̬>>");
		
		setTitleText("", "");
		
		addPageStates();
		
		setBackgroundColor(bgColor);
		
		pageStateObj.setFigure(this);
		
		markError(pageStateObj.validate());
		// ���ô�С��λ��
		Point point = pageStateObj.getLocation();
		dimen = pageStateObj.getSize();
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, dimen.width, dimen.height < this.height ? this.height : dimen.height));
		
	}

	
	protected String getTypeText() {
		return "<<ҳ��״̬>>";
	}
	
	/**
	 * ��ʾ����ҳ��״̬
	 */
	private void addPageStates() {}
	
	/**
	 * ɾ��ҳ��״̬
	 * @param label
	 */
	public void deletePageState(PageStateLabel label) {
		getContentFigure().remove(label);
		this.height -= LINE_HEIGHT;
		resizeHeight();
	}
	
	/**
	 * �������ø߶�
	 */
	private void resizeHeight() {
		setSize(dimen.width, dimen.height < this.height ? this.height : dimen.height);
	}


	public PageStateElementObj getPageStateObj() {
		return pageStateObj;
	}

}
