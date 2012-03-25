package nc.lfw.editor.common;

import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

/**
 * ����ͼ�εĻ���
 * 
 * @author guoweic
 * 
 */
public abstract class LFWBaseRectangleFigure extends RectangleFigure {

	private Label typeLabel = new Label("<<LFW ���>>");
	private PartmentFigure attrsFigure = null;
	private PartmentFigure titleFigure = null;
	private IFigure contentFigure = null;
	// ÿ�и߶�
	public final int LINE_HEIGHT = 17;
	// ����ѡ��ʱ����ɫ
	public final Color SELECTED_COLOR = new Color(null, 255, 0, 0);
	// ����δѡ��ʱ����ɫ
	public final Color UN_SELECTED_COLOR = new Color(null, 0, 0, 0);
	// ��ǰѡ�е�����źš��ۣ�
	private Label currentLabel;
	
	private Color bgColor = Display.getCurrent().getSystemColor(SWT.COLOR_CYAN);
	
	private Color white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	
	private LFWBasicElementObj elementObj;
	
	private String errStr = null;
	
	protected void setTypeLabText(String text) {
		typeLabel.setText(text);
	}

	protected String getTypeLabText() {
		return typeLabel.getText();
	}
	
	public LFWBaseRectangleFigure(LFWBasicElementObj ele) {
		this.elementObj = ele;
		setBackgroundColor(bgColor);
		ToolbarLayout layout = new ToolbarLayout();
		layout.setSpacing(3);
		setLayoutManager(layout);
		titleFigure = getTitleFigure();
		if(titleFigure != null)
			add(titleFigure);
		contentFigure = getContentFigure();
		if(contentFigure != null)
			add(contentFigure);
	}

	
	protected PartmentFigure getAttrsFigure(){
		if(attrsFigure == null){
			attrsFigure = new PartmentFigure();
		}
		return attrsFigure;
	}
	
	
	/**
	 * ������ʾ��������
	 * 
	 * @param element
	 */
	@SuppressWarnings("deprecation")
	public static void reloadPropertySheet(Object element) {
		IViewPart[] views = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViews();
		PropertySheet sheet = null;
		for (int i = 0, n = views.length; i < n; i++) {
			if(views[i] instanceof PropertySheet) {
				sheet = (PropertySheet) views[i];
				break;
			}
		}
		if (sheet != null) {
			StructuredSelection select = new StructuredSelection(element);
			sheet.selectionChanged(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(), select);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected void clearContentFigure() {
		if(contentFigure == null)
			return;
		List<IFigure> cList = contentFigure.getChildren();
		if(cList.size() > 0){
			IFigure[] figures = cList.toArray(new IFigure[0]);
			for (int i = 0; i < figures.length; i++) {
				contentFigure.remove(figures[i]);
			}
		}
	}
	
	public IFigure getContentFigure() {
		if(contentFigure == null){
			contentFigure = new PartmentFigure();
		}
		return contentFigure;
	}
	
	public void addToContent(IFigure figure) {
		getContentFigure().add(figure);
	}
	
	protected void addToContent(IFigure figure, int index) {
		getContentFigure().add(figure, index);
	}
	
	protected void removeFromContent(IFigure figure) {
		getContentFigure().remove(figure);
	}

	public void setTitleText(String text, String id) {
		NameLabel label = new NameLabel(text, id);
		getTitleFigure().add(label);
	}
	

	
	public PartmentFigure getTitleFigure() {
		if (titleFigure == null) {
			titleFigure = new PartmentFigure();
			titleFigure.add(typeLabel);
			titleFigure.setBorder(new AbstractBorder() {
				public Insets getInsets(IFigure ifigure) {
					return new Insets(2, 0, 0, 0);
				}
				public void paint(IFigure figure, Graphics graphics,
						Insets insets) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return titleFigure;
	}
	
	
	protected abstract String getTypeText();
	
	
	public String getErrStr() {
		return errStr;
	}

	public void setErrStr(String errStr) {
		this.errStr = errStr;
	}


	public void markError(String errMsg) {
		errStr = errMsg;
		if(errStr != null){
			typeLabel.setIcon(PaletteImage.createDeleteImage());
		}else{
			typeLabel.setIcon(null);
		}
	}
	
	protected LFWBasicElementObj getElementObj() {
		return this.elementObj;
	}

	protected void fillShape(Graphics g) {
		int corSize = WEBProjConstants.ELEMENT_CORNER_SIZE;
		if (LFWPersTool.isSupportGDI()) {
			g.setAntialias(SWT.ON);
			Rectangle rect = getBounds().getCopy();
			Image img = null;
			GC gc = null;
			Pattern pattern = null;
			try {
				rect.width -= 1;
				rect.height -= 1;
				if(rect.width<=0)
					rect.width = 1;
				if(rect.height<=0)
					rect.height = 1;
				if(g instanceof SWTGraphics){
					pattern = new Pattern(Display.getCurrent(), rect.x, rect.y, rect.x+rect.width, rect.y+rect.height, white, getBackgroundColor());
					g.setBackgroundPattern(pattern);
					g.fillRoundRectangle(rect, corSize, corSize);
				}else{// ScaledGraphics
					pattern = new Pattern(Display.getCurrent(), 0, 0, rect.width, rect.height, white, getBackgroundColor());
					
					img = new Image(Display.getCurrent(), rect.width, rect.height);
					gc = new GC(img);
					gc.setBackgroundPattern(pattern);
					gc.fillRoundRectangle(0,0 , rect.width, rect.height, corSize, corSize);
					g.drawImage(img, rect.x, rect.y);
				}
			} finally {
				if(pattern != null)
					pattern.dispose();
				if (gc != null)
					gc.dispose();
				if (img != null)
					img.dispose();
			}
		} else {
			// setGraphics(g);
			 g.fillRoundRectangle(getBounds(),corSize,corSize);
		}
	}

	
	protected void outlineShape(Graphics graphics) {
		int corSize = WEBProjConstants.ELEMENT_CORNER_SIZE;
		Rectangle r = getBounds();
		int x = r.x + lineWidth / 2;
		int y = r.y + lineWidth / 2;
		int w = r.width - Math.max(1, lineWidth);
		int h = r.height - Math.max(1, lineWidth);
		Rectangle newR = new Rectangle(x, y, w, h);
		graphics.drawRoundRectangle(newR, corSize, corSize);// Rectangle(x, y, w, h);
	}
	
	/**
	 * ѡ������źš��ۣ�
	 * @author guoweic
	 * @param label
	 */
	public void selectLabel(Label label) {
		label.setForegroundColor(SELECTED_COLOR);
		currentLabel = label;
	}

	/**
	 * ȡ��ȫ������źš��ۣ�ѡ��״̬
	 * @author guoweic
	 */
	public void unSelectAllLabels() {
		List list = getContentFigure().getChildren();
		for (int i = 0, n = list.size(); i < n; i++) {
			if (list.get(i) instanceof Label)
				unSelectLabel((Label) list.get(i));
		}
	}
	
	/**
	 * ȡ������źš��ۣ�ѡ��״̬
	 * @author guoweic
	 * @param label
	 */
	private void unSelectLabel(Label label) {
		label.setForegroundColor(UN_SELECTED_COLOR);
		currentLabel = null;
	}
	
	/**
	 * @author guoweic
	 * @return
	 */
	public Label getCurrentLabel() {
		return currentLabel;
	}

	public void setCurrentLabel(Label currentLabel) {
		this.currentLabel = currentLabel;
	}
}
