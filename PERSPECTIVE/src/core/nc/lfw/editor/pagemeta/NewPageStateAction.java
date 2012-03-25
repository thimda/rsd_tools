package nc.lfw.editor.pagemeta;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * �½� ҳ��״̬
 * @author guoweic
 *
 */
public class NewPageStateAction extends Action {
	
	private PageStateElementObj pageStateObj;
	
	public NewPageStateAction(PageStateElementObj pageStateObj) {
		super("�½� ҳ��״̬");
		this.pageStateObj = pageStateObj;
	}
	
	public void run() {
		Shell shell = new Shell(Display.getCurrent());
		NewPageStateDialog dialog = new NewPageStateDialog(shell);
		dialog.setPageStateObj(pageStateObj);
		if (dialog.open() == Dialog.OK) {
			try {
				// ��ȡEditor
				PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
				// �������ñ�ţ�Key��
//				Map<String, PageState> pageStateMap = new HashMap<String, PageState>();
//				for (int i = 0, n = pageStateArray.length; i < n; i++) {
//					pageStateArray[i].setKey(String.valueOf(i + 1));
//					pageStateMap.put(pageStateArray[i].getKey(), pageStateArray[i]);
//				}
//				pageStates.setStates(pageStateMap);
				editor.setDirtyTrue();
			} catch (Exception e) {
				String title = "�½� ҳ��״̬";
				String message = e.getMessage();
				MessageDialog.openError(shell, title, message);
			}
		}
	}

}
