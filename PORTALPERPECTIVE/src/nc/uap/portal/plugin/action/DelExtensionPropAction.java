package nc.uap.portal.plugin.action;

import java.util.ArrayList;

import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.plugin.PluginEditor;
import nc.uap.portal.plugin.PluginElementObj;
import nc.uap.portal.plugin.page.PluginPropertiesView;
import nc.uap.portal.plugins.model.Extension;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 删除命令
 * @author dingrf
 *
 */
public class DelExtensionPropAction extends Action {
	private class DelCellPropCommand extends Command{
		private PluginPropertiesView view = null;
		private PluginElementObj obj = null;
		private Extension ed = null;
		private ArrayList<Extension> arraylist = null;
		
		public DelCellPropCommand(ArrayList<Extension> arraylist, PluginElementObj obj, Extension ed) {
			super("删除");
			this.arraylist = arraylist;
			this.obj = obj;
			this.ed = ed;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			PluginPropertiesView view =getPropertiesView();
			obj.getExtension().remove(ed);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}

		
		public void undo() {
			arraylist.add(ed);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}
		
	}
	private PluginPropertiesView view = null;
	public DelExtensionPropAction(PluginPropertiesView view) {
		setText(PortalProjConstants.DEL_EXTENSION);
		this.view = view;
	}

	private PluginPropertiesView getPropertiesView() {
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
				Object model = getPropertiesView().getPluginElementPart().getModel();
				if (o instanceof Extension && model instanceof PluginElementObj) {
					Extension prop = (Extension) o;
					PluginElementObj vo = (PluginElementObj) model;
					Object object = view.getTv().getInput();
					ArrayList<Extension> arraylist = null;
					if(object instanceof ArrayList){
						 arraylist = (ArrayList<Extension>)object;
					}
					arraylist.remove(prop);
					DelCellPropCommand cmd = new DelCellPropCommand(arraylist, vo, prop);
					if(PluginEditor.getActiveEditor() != null)
						PluginEditor.getActiveEditor().executComand(cmd);
				}
	
			}
		}
	}
}
