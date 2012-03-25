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
 * 从Model以用数据集行为类
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
		ContextMDDatasetDialog dialog = new ContextMDDatasetDialog("从Model引用数据集");
		if (dialog.open() == IDialogConstants.OK_ID) {
			//保存节点信息到文件
			LfwWidget widget = dialog.getWidget();
			LFWAMCPersTool.saveWidget(widget);
			//更新节点显示信息
			TreeItem treeItem = LFWAMCPersTool.getCurrentTreeItem().getParentItem();
			if(treeItem instanceof LFWWidgetTreeItem){
				LFWWidgetTreeItem widgetTreeItem = (LFWWidgetTreeItem) treeItem;
				widgetTreeItem.setWidget(widget);
				view.dealCurrentWidgetTreeItem(widgetTreeItem);
			}
		}
	}

}
