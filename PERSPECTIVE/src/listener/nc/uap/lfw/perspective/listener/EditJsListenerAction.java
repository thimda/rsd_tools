package nc.uap.lfw.perspective.listener;

import org.eclipse.draw2d.Label;
import org.eclipse.jface.action.Action;

/**
 * �༭JsListener
 * @author guoweic
 *
 */
public class EditJsListenerAction extends Action {
	
	private ListenerElementObj listenerObj;
	private Label label;
	private String itemId;
	
	public EditJsListenerAction(Label label, ListenerElementObj listenerObj, String itemId) {
		setText("�༭ " + itemId);
		setToolTipText("�༭");
		this.listenerObj = listenerObj;
		this.label = label;
		this.itemId = itemId;
	}
	
	
	public void run() {
		editItem();
	}
	
	private void editItem() {
		//TODO ����
//		LfwWidgetConf widget = widgetObj.getWidget();
//		// ����pagemeta
//		PagemetaEditor.getActivePagemetaEditor().savePagemeta(pagemeta);
	}

}
