package nc.uap.portal.portlets.action;

import java.util.ArrayList;

import nc.uap.portal.container.om.InitParam;
import nc.uap.portal.portlets.PortletEditor;
import nc.uap.portal.portlets.PortletElementObj;
import nc.uap.portal.portlets.page.InitParamPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * 增加InitParam
 * @author dingrf
 *
 */
public class AddInitParamPropAction extends Action {
	
	private InitParamPropertiesView view = null;
	private class AddAttrCommand extends Command{
		public AddAttrCommand() {
			super("增加");
		}

		public void execute() {
			redo();
		}

		public void redo() {
		}
		
		public void undo() {
		}
		
	}
	public AddInitParamPropAction(InitParamPropertiesView view) {
		super("增加");
		this.view = view;
	}
	private InitParamPropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		InitParam IP = new InitParam();
		IP.addDescription(null);
		insertNullProp(IP);
	}
	
	@SuppressWarnings("unchecked")
	private void insertNullProp(InitParam initParam){
		InitParamPropertiesView view =getPropertiesView();
		if(view.getPortletElementPart() != null && view.getPortletElementPart().getModel() instanceof PortletElementObj){
			Object object = view.getTv().getInput();
			ArrayList<InitParam> arraylist = null;
    		arraylist = (ArrayList<InitParam>)object;
			arraylist.add(initParam);
			view.getTv().setInput(arraylist);
			view.getTv().refresh();
			view.getTv().expandAll();

			AddAttrCommand addcmd = new AddAttrCommand();
			if(PortletEditor.getActiveEditor() != null)
				PortletEditor.getActiveEditor().executComand(addcmd);
		}
	}
}

