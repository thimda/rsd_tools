package nc.lfw.editor.common.extend;

import nc.uap.lfw.core.base.ExtAttribute;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * 删除扩展属性操作
 * @author guoweic
 *
 */
public class DelExtendAttributeAction extends Action {

	private ExtendAttributesView view = null;

	public DelExtendAttributeAction(ExtendAttributesView view) {
		setText("删除");
		this.view = view;
	}

	
	public void run() {
		if (MessageDialog.openConfirm(null, "确认", "确定删除选中扩展属性吗？"))
			delAttribute();
	}
	
	private void delAttribute() {
		IStructuredSelection selection = (IStructuredSelection) view.getTv().getSelection();
		ExtAttribute attr = (ExtAttribute) selection.getFirstElement();
		view.deleteExtendAttribute(attr);
	}

}
