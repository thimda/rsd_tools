package nc.uap.portal.managerapps.action;

import java.util.ArrayList;

import nc.uap.portal.managerapps.ManagerAppsEditor;
import nc.uap.portal.managerapps.ManagerAppsElementObj;
import nc.uap.portal.managerapps.page.ManagerNodePropertiesView;
import nc.uap.portal.om.ManagerNode;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 删除功能节点
 * @author dingrf
 *
 */
public class DelManagerNodePropAction extends Action {
	private class DelCellPropCommand extends Command{
		private ManagerNodePropertiesView view = null;
		private ManagerAppsElementObj obj = null;
		private ManagerNode node = null;
		private ArrayList<ManagerNode> arraylist = null;
		
		public DelCellPropCommand(ArrayList<ManagerNode> arraylist, ManagerAppsElementObj obj, ManagerNode node) {
			super("删除");
			this.arraylist = arraylist;
			this.obj = obj;
			this.node = node;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			ManagerNodePropertiesView view =getPropertiesView();
			obj.getManagerNode().remove(node);
			//eeobj.getDs().getFieldSet().removeField(field);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}

		
		public void undo() {
			arraylist.add(node);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}
		
	}
	private ManagerNodePropertiesView view = null;
	public DelManagerNodePropAction(ManagerNodePropertiesView view) {
		setText("删除");
		this.view = view;
	}

	private ManagerNodePropertiesView getPropertiesView() {
		return view;
	}

	
	@SuppressWarnings("unchecked")
	public void run() {
		boolean tip = MessageDialog.openConfirm(null, "提示", "确定要删除数据吗?");
		if(tip){
			TreeViewer tv = getPropertiesView().getTv();
			Tree tree = tv.getTree();
			TreeItem[] tis = tree.getSelection();
			if (tis != null && tis.length > 0) {
				TreeItem ti = tis[0];
				Object o = ti.getData();
				Object model = getPropertiesView().getManagerAppsElementPart().getModel();
				if (o instanceof ManagerNode && model instanceof ManagerAppsElementObj) {
					ManagerNode prop = (ManagerNode) o;
					ManagerAppsElementObj vo = (ManagerAppsElementObj) model;
					Object object = view.getTv().getInput();
					ArrayList<ManagerNode> arraylist = null;
					if(object instanceof ArrayList){
						 arraylist = (ArrayList<ManagerNode>)object;
					}
					arraylist.remove(prop);
					DelCellPropCommand cmd = new DelCellPropCommand(arraylist, vo, prop);
					if(ManagerAppsEditor.getActiveEditor() != null)
						ManagerAppsEditor.getActiveEditor().executComand(cmd);
				}
	
			}
		}
	}
}
