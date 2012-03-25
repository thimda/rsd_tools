package nc.uap.lfw.md.component;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;


/**
 * ��Ԫ���ݵ�����������е�ds
 * @author zhangxya
 *
 */
public class NewMdDsFormComponent extends Action{
	
	public NewMdDsFormComponent() {
		super(WEBProjConstants.NEW_DS_FORM_COMPONENT);
	}

	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell();
		MDComponentDialog componetDialog = new MDComponentDialog(shell, "����" + LFWPerspectiveNameConst.MD_COMPONENT);
		int result = componetDialog.open();
		if (result == IDialogConstants.OK_ID){
			LfwWidget widget = componetDialog.getWidget();
			LFWPersTool.saveWidget(widget);
			TreeItem treeItem = LFWPersTool.getCurrentTreeItem();
			if(treeItem instanceof LFWWidgetTreeItem){
				LFWWidgetTreeItem widgetTreeItem = (LFWWidgetTreeItem) treeItem;
				widgetTreeItem.setWidget(widget);
				view.dealCurrentWidgetTreeItem(widgetTreeItem);
			}
		}
		else 
			return;

	}
}

