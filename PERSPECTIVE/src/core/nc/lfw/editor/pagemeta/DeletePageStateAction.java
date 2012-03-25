package nc.lfw.editor.pagemeta;

import org.eclipse.draw2d.Label;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * ɾ�� ҳ��״̬
 * @author guoweic
 *
 */
public class DeletePageStateAction extends Action {
	
	private PageStateElementObj pageStateObj;
	private Label label;
	private String itemId;
	
	public DeletePageStateAction(Label label, PageStateElementObj pageStateObj, String itemId) {
		setText("ɾ�� " + itemId);
		setToolTipText("ɾ��");
		this.pageStateObj = pageStateObj;
		this.label = label;
		this.itemId = itemId;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "ȷ��", "ȷ��ɾ�� " + itemId + " ��"))
			deleteItem();
	}
		
	private void deleteItem() {
		PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
		if (label instanceof PageStateLabel) {
			// ����pagemeta
//			editor.savePagemeta(pagemeta);
			editor.setDirtyTrue();
			pageStateObj.getFigure().deletePageState((PageStateLabel) label);
		}
	}

}
