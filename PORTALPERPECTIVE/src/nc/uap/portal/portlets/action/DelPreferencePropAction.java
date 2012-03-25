package nc.uap.portal.portlets.action;

import java.util.ArrayList;

import nc.uap.portal.container.om.Preference;
import nc.uap.portal.portlets.PortletEditor;
import nc.uap.portal.portlets.PortletElementObj;
import nc.uap.portal.portlets.page.PreferencePropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 删除Preference
 * @author dingrf
 *
 */
public class DelPreferencePropAction extends Action {
	private class DelCellPropCommand extends Command{
		private PreferencePropertiesView view = null;
		private PortletElementObj ptobj = null;
		private Preference preference = null;
		private ArrayList<Preference> arraylist = null;
		
		public DelCellPropCommand(ArrayList<Preference> arraylist, PortletElementObj ptobj, Preference preference) {
			super("删除");
			this.arraylist = arraylist;
			this.ptobj = ptobj;
			this.preference = preference;
		}

		public void execute() {
			redo();
		}

		
		public void redo() {
			PreferencePropertiesView view =getPropertiesView();
			ptobj.getPreferences().remove(preference);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}

		
		public void undo() {
			arraylist.add(preference);
			//eeobj.getDs().getFieldSet().addField(field);
			TreeViewer tv = view.getTv();
			tv.setInput(arraylist);
			tv.cancelEditing();
			tv.refresh();
			tv.expandAll();
		}
		
	}
	private PreferencePropertiesView view = null;
	public DelPreferencePropAction(PreferencePropertiesView view) {
		setText("删除");
		this.view = view;
	}

	private PreferencePropertiesView getPropertiesView() {
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
				Object model = getPropertiesView().getPortletElementPart().getModel();
				if (o instanceof Preference && model instanceof PortletElementObj) {
					Preference prop = (Preference) o;
					PortletElementObj vo = (PortletElementObj) model;
					Object object = view.getTv().getInput();
					ArrayList<Preference> arraylist = null;
					if(object instanceof ArrayList){
						 arraylist = (ArrayList<Preference>)object;
					}
					arraylist.remove(prop);
					DelCellPropCommand cmd = new DelCellPropCommand(arraylist, vo, prop);
					if(PortletEditor.getActiveEditor() != null)
						PortletEditor.getActiveEditor().executComand(cmd);
				}
	
			}
		}
	}
}
