/**
 * 
 */
package nc.uap.lfw.window.view.context;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * ��Model�������ݼ���Ϊ��
 * @author chouhl
 *
 */
public class ContextNodeAction extends NodeAction {
	
	public ContextNodeAction() {
		super(WEBProjConstants.MAPPING);
	}

	public void run() {
		addDatasetToView();
	}
	
	private void addDatasetToView(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if (view == null){
			return;
		}
		ContextMDDatasetDialog dialog = new ContextMDDatasetDialog("��Model�������ݼ�");
		if (dialog.open() == IDialogConstants.OK_ID) {
			//����ڵ���Ϣ���ļ�
			LfwWidget widget = dialog.getWidget();
			LFWAMCPersTool.saveWidget(widget);
			//���½ڵ���ʾ��Ϣ
			TreeItem treeItem = LFWAMCPersTool.getCurrentTreeItem().getParentItem();
			if(treeItem instanceof LFWWidgetTreeItem){
				LFWWidgetTreeItem widgetTreeItem = (LFWWidgetTreeItem) treeItem;
				widgetTreeItem.setWidget(widget);
				view.dealCurrentWidgetTreeItem(widgetTreeItem);
			}
		}
	}

}
