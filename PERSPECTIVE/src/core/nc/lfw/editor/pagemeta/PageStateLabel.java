package nc.lfw.editor.pagemeta;

import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.figure.ui.IDirectEditable;
import nc.uap.lfw.figure.ui.NullBorder;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * 页面状态行
 * 
 * @author guoweic
 *
 */
public class PageStateLabel extends Label implements IDirectEditable {

	public PageStateLabel(String text) {
		super(text);
		setLabelAlignment(PositionConstants.LEFT);
		setBorder(new NullBorder());
		// 获取图片
		ImageDescriptor imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "page_state.gif");
		Image image = imageDescriptor.createImage();
		setIcon(image);
		
		markError();
	}

	private void markError() {

	}

	@Override
	public Object getEditableObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
