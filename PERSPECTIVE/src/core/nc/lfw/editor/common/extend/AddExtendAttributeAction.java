package nc.lfw.editor.common.extend;

import nc.uap.lfw.core.base.ExtAttribute;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;

/**
 * ������չ���Բ���
 * 
 * @author guoweic
 *
 */
public class AddExtendAttributeAction extends Action {
	
	private ExtendAttributesView view = null;
	
	public AddExtendAttributeAction(ExtendAttributesView view) {
		super("����");
		this.view = view;
	}
	
	public void run() {
		createEvents();
	}
	
	public void createEvents() {
		Shell shell = new Shell();
		ExtendAttributeDialog dialog = new ExtendAttributeDialog(shell);
		dialog.setAttributeList(view.getAttributeList());
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			ExtAttribute attribute = new ExtAttribute();
			attribute.setKey(dialog.getKey());
			attribute.setValue(dialog.getValue());
			attribute.setDesc(dialog.getDesc());
			view.saveExtendAttribute(attribute);
		}
	}

}
