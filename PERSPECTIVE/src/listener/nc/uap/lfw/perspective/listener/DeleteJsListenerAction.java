package nc.uap.lfw.perspective.listener;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.event.conf.JsListenerConf;

import org.eclipse.draw2d.Label;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * ɾ��Listener
 * @author guoweic
 *
 */
public class DeleteJsListenerAction extends Action {
	
	private ListenerElementObj listenerObj;
	private Label label;
	private String itemId;
	
	public DeleteJsListenerAction(Label label, ListenerElementObj listenerObj, String itemId) {
		setText("ɾ�� " + itemId);
		setToolTipText("ɾ��");
		this.listenerObj = listenerObj;
		this.label = label;
		this.itemId = itemId;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "ȷ��", "ȷ��ɾ�� " + itemId + " ��"))
			deleteItem();
	}
	
	private void deleteItem() {
		JsListenerConf jsListener = (JsListenerConf) ((JsListenerLabel) label).getEditableObj();
		try {
			// ɾ��
			if(jsListener.getFrom() == null)
				listenerObj.getFigure().removeListener((JsListenerLabel) label);
			else{
				ImageDescriptor imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "delete.gif");
				label.setIcon(imageDescriptor.createImage());
			}
			LFWBaseEditor.getActiveEditor().removeJsListener(jsListener);
			listenerObj.getFigure().setCurrentLabel(null);
			LFWBaseEditor.getActiveEditor().setDirtyTrue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
