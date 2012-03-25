package nc.uap.lfw.editor.application.plug;

import java.util.ArrayList;

import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.editor.application.ApplicationEditor;
import nc.uap.lfw.editor.application.ApplicationObj;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 删除Connector
 * @author dingrf
 *
 */
public class DelConnectorPropAction extends Action {
	private class DelCellPropCommand extends Command{
		private ConnectorPropertiesView view = null;
		private ApplicationObj obj = null;
		private Connector conn = null;
		private ArrayList<Connector> arraylist = null;
		
		public DelCellPropCommand(ArrayList<Connector> arraylist, ApplicationObj obj, Connector conn) {
			super("删除");
			this.arraylist = arraylist;
			this.obj = obj;
			this.conn = conn;
		}

		public void execute() {
			redo();
		}
		
		public void redo() {
			ConnectorPropertiesView view = getPropertiesView();
			obj.getApp().getConnectorList().remove(conn);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}

		
		public void undo() {
			arraylist.add(conn);
			//eeobj.getDs().getFieldSet().addField(field);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}
		
	}
	private ConnectorPropertiesView view = null;
	public DelConnectorPropAction(ConnectorPropertiesView view) {
		setText("删除");
		this.view = view;
	}

	private ConnectorPropertiesView getPropertiesView() {
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
				Object model = getPropertiesView().getApplicationPart().getModel();
				if (o instanceof Connector && model instanceof ApplicationObj) {
					Connector prop = (Connector) o;
					ApplicationObj vo = (ApplicationObj) model;
					Object object = view.getTv().getInput();
					ArrayList<Connector> arraylist = null;
					if(object instanceof ArrayList){
						 arraylist = (ArrayList<Connector>)object;
					}
					arraylist.remove(prop);
					DelCellPropCommand cmd = new DelCellPropCommand(arraylist, vo, prop);
					if(ApplicationEditor.getActiveEditor() != null)
						ApplicationEditor.getActiveEditor().executComand(cmd);
				}
	
			}
		}
	}
}
