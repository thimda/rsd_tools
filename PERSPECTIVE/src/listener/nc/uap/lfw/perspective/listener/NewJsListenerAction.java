package nc.uap.lfw.perspective.listener;

import java.util.List;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.uap.lfw.core.event.conf.JsListenerConf;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * �½�JsListener
 * @author guoweic
 *
 */
public class NewJsListenerAction extends Action {
	
	private ListenerElementObj jsListenerObj;
//	// Ŀ���������
//	private String targetType = "";
	// �ɽ��ܵ�JsListener����
	private List<Class<? extends JsListenerConf>> acceptListeners;
	
	public NewJsListenerAction(ListenerElementObj jsListenerObj) {
		super("�½� Listener");
		this.jsListenerObj = jsListenerObj;
	}

	
	public void run() {
		Shell shell = new Shell(Display.getCurrent());
		NewJsListenerDialog dialog = new NewJsListenerDialog(shell, "�½� Listener");
		dialog.setListenerMap(jsListenerObj.getListenerMap());
//		dialog.setTargetType(targetType);
		if(acceptListeners != null)
			dialog.setAcceptListeners(acceptListeners);
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			String id = dialog.getId();
			String listenerClass = dialog.getListenerClass();
			// ����Listener����
			JsListenerConf jsListener;
			try {
				jsListener = (JsListenerConf) Class.forName(listenerClass).newInstance();
				jsListener.setId(id);
				jsListenerObj.getFigure().addListener(jsListener);
				// ����
//				LFWBaseEditor.getActiveEditor().saveJsListener(id, null, jsListener);
				LFWBaseEditor.getActiveEditor().saveListener(id, null, jsListener);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	public void setTargetType(String targetType) {
//		this.targetType = targetType;
//	}

	public void setAcceptListeners(List<Class<? extends JsListenerConf>> acceptListeners) {
		this.acceptListeners = acceptListeners;
	}

}
