package nc.uap.portal.managerapps.action;

import java.util.ArrayList;


import nc.uap.portal.managerapps.ManagerAppsEditor;
import nc.uap.portal.managerapps.ManagerAppsElementObj;
import nc.uap.portal.managerapps.page.ManagerNodePropertiesView;
import nc.uap.portal.om.ManagerNode;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * Exension增加操作
 * 
 * @author dingrf
 */
public class AddManagerNodePropAction extends Action {
	
	/**模型属性视图*/
	private ManagerNodePropertiesView view = null;
	
	private class AddAttrCommand extends Command {
		
		private ArrayList<ManagerNode> arraylist = null;
		
		public AddAttrCommand(ArrayList<ManagerNode> arraylist) {
			super("增加");
			this.arraylist = arraylist;
		}

		private ManagerNodePropertiesView getPropertiesView() {
			return view;
		}

		public void execute() {
			redo();
		}

		public void redo() {
			ManagerNodePropertiesView view = getPropertiesView();
			view.getTv().setInput(arraylist);
			view.getTv().refresh();
			view.getTv().expandAll();

		}
	}
	
	public AddManagerNodePropAction(ManagerNodePropertiesView view) {
		super("增加");
		this.view = view;
	}
	
	private ManagerNodePropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		ManagerNode node = new ManagerNode();
		insertNullProp(node);
	}
	
	/**
	 *新增属性 
	 */
	@SuppressWarnings("unchecked")
	private void insertNullProp(ManagerNode node){
		ManagerNodePropertiesView view =getPropertiesView();
		if(view.getManagerAppsElementPart() != null && view.getManagerAppsElementPart().getModel() instanceof ManagerAppsElementObj){
			Object object = view.getTv().getInput();
			ArrayList<ManagerNode> arraylist = null;
			arraylist = (ArrayList<ManagerNode>)object;
			arraylist.add(node);

			AddAttrCommand addcmd = new AddAttrCommand(arraylist);
			if(ManagerAppsEditor.getActiveEditor() != null)
				ManagerAppsEditor.getActiveEditor().executComand(addcmd);
		}
	}
}

