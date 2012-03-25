package nc.uap.lfw.perspective.listener;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.figure.ui.IDirectEditable;
import nc.uap.lfw.figure.ui.NullBorder;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;


/**
 * js event Label
 * @author zhangxya
 *
 */
public class JsEventLabel  extends Label implements IDirectEditable {

	private EventHandlerConf jsEvent;

	public JsEventLabel(String text, EventHandlerConf jsEvent) {
		super(text);
		this.jsEvent = jsEvent;
		setLabelAlignment(PositionConstants.LEFT);
		setBorder(new NullBorder());
		// ªÒ»°Õº∆¨
		ImageDescriptor imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "event.png");
		Image image = imageDescriptor.createImage();
		setIcon(image);
		
		markError();
	}

	private void markError() {

	}

	
	public Object getEditableObj() {
		return jsEvent;
	}

}
