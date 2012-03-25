package nc.lfw.editor.pagemeta;

import org.eclipse.draw2d.Label;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * É¾³ý Ò³Ãæ×´Ì¬
 * @author guoweic
 *
 */
public class DeletePageStateAction extends Action {
	
	private PageStateElementObj pageStateObj;
	private Label label;
	private String itemId;
	
	public DeletePageStateAction(Label label, PageStateElementObj pageStateObj, String itemId) {
		setText("É¾³ý " + itemId);
		setToolTipText("É¾³ý");
		this.pageStateObj = pageStateObj;
		this.label = label;
		this.itemId = itemId;
	}
	
	
	public void run() {
		if (MessageDialog.openConfirm(null, "È·ÈÏ", "È·¶¨É¾³ý " + itemId + " Âð£¿"))
			deleteItem();
	}
		
	private void deleteItem() {
		PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
		if (label instanceof PageStateLabel) {
			// ±£´æpagemeta
//			editor.savePagemeta(pagemeta);
			editor.setDirtyTrue();
			pageStateObj.getFigure().deletePageState((PageStateLabel) label);
		}
	}

}
