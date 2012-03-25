package nc.lfw.editor.common.extend;

import nc.uap.lfw.core.base.ExtAttribute;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * ɾ����չ���Բ���
 * @author guoweic
 *
 */
public class DelExtendAttributeAction extends Action {

	private ExtendAttributesView view = null;

	public DelExtendAttributeAction(ExtendAttributesView view) {
		setText("ɾ��");
		this.view = view;
	}

	
	public void run() {
		if (MessageDialog.openConfirm(null, "ȷ��", "ȷ��ɾ��ѡ����չ������"))
			delAttribute();
	}
	
	private void delAttribute() {
		IStructuredSelection selection = (IStructuredSelection) view.getTv().getSelection();
		ExtAttribute attr = (ExtAttribute) selection.getFirstElement();
		view.deleteExtendAttribute(attr);
	}

}
