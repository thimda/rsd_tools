package nc.lfw.editor.widget.plug;

import java.util.ArrayList;

import nc.lfw.editor.widget.WidgetEditor;
import nc.uap.lfw.core.page.PlugoutDescItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ɾ��PlugoutDescItem
 * @author dingrf
 *
 */
public class DelPlugoutDescItemPropAction extends Action {
	private class DelCellPropCommand extends Command{
		private PlugoutDescItemPropertiesView view = null;
		private PlugoutDescElementObj obj = null;
		private PlugoutDescItem descItem = null;
		private ArrayList<PlugoutDescItem> arraylist = null;
		
		public DelCellPropCommand(ArrayList<PlugoutDescItem> arraylist, PlugoutDescElementObj obj, PlugoutDescItem descItem) {
			super("ɾ��");
			this.arraylist = arraylist;
			this.obj = obj;
			this.descItem = descItem;
		}

		public void execute() {
			redo();
		}
		
		public void redo() {
			PlugoutDescItemPropertiesView view =getPropertiesView();
			obj.getPlugout().getDescItemList().remove(descItem);
			//eeobj.getDs().getFieldSet().removeField(field);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}

		
		public void undo() {
			arraylist.add(descItem);
			//eeobj.getDs().getFieldSet().addField(field);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}
		
	}
	private PlugoutDescItemPropertiesView view = null;
	public DelPlugoutDescItemPropAction(PlugoutDescItemPropertiesView view) {
		setText("ɾ��");
		this.view = view;
	}

	private PlugoutDescItemPropertiesView getPropertiesView() {
		return view;
	}

	
	@SuppressWarnings("unchecked")
	public void run() {
		boolean tip = MessageDialog.openConfirm(null, "��ʾ", "ȷ��Ҫɾ��������?");
		if(tip){
			TreeViewer tv = getPropertiesView().getTv();
			Tree tree = tv.getTree();
			TreeItem[] tis = tree.getSelection();
			if (tis != null && tis.length > 0) {
				TreeItem ti = tis[0];
				Object o = ti.getData();
				Object model = getPropertiesView().getPlugoutDescElementPart().getModel();
				if (o instanceof PlugoutDescItem && model instanceof PlugoutDescElementObj) {
					PlugoutDescItem prop = (PlugoutDescItem) o;
					PlugoutDescElementObj vo = (PlugoutDescElementObj) model;
					Object object = view.getTv().getInput();
					ArrayList<PlugoutDescItem> arraylist = null;
					if(object instanceof ArrayList){
						 arraylist = (ArrayList<PlugoutDescItem>)object;
					}
					arraylist.remove(prop);
					DelCellPropCommand cmd = new DelCellPropCommand(arraylist, vo, prop);
					WidgetEditor editor = (WidgetEditor)WidgetEditor.getActiveEditor();
					editor.setDirtyTrue();
					if(editor != null)
						editor.executComand(cmd);
				}
	
			}
		}
	}
}
