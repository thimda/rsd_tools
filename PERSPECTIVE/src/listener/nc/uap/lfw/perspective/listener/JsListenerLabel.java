package nc.uap.lfw.perspective.listener;

import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.figure.ui.IDirectEditable;
import nc.uap.lfw.figure.ui.NullBorder;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author guoweic
 *
 */
public class JsListenerLabel extends Label implements IDirectEditable {

	private JsListenerConf jsListener;

	public JsListenerLabel(String text, JsListenerConf jsListener) {
		super(text);
		this.jsListener = jsListener;
		setLabelAlignment(PositionConstants.LEFT);
		setBorder(new NullBorder());
		// ªÒ»°Õº∆¨
		ImageDescriptor imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "listener.gif");
		Image image = imageDescriptor.createImage();
		setIcon(image);
		
		markError();
	}

	private void markError() {

	}

	
	public Object getEditableObj() {
		return jsListener;
	}

}
