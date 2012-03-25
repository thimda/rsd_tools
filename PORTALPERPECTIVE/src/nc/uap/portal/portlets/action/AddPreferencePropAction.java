package nc.uap.portal.portlets.action;

import java.util.ArrayList;

import nc.uap.portal.container.om.Preference;
import nc.uap.portal.portlets.PortletEditor;
import nc.uap.portal.portlets.PortletElementObj;
import nc.uap.portal.portlets.page.PreferencePropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * 增加Preference
 * @author dingrf
 *
 */
public class AddPreferencePropAction extends Action {
	
	private PreferencePropertiesView view = null;
	
	private class AddAttrCommand extends Command{
	public AddAttrCommand() {
		super("增加");
	}
	
	public void execute() {
		redo();
	}
	public void redo() {
	}
}
	public AddPreferencePropAction(PreferencePropertiesView view) {
		super("增加");
		this.view = view;
	}
	private PreferencePropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		Preference pf = new Preference();
		insertNullProp(pf);
	}
	
	@SuppressWarnings("unchecked")
	private void insertNullProp(Preference preference){
		PreferencePropertiesView view =getPropertiesView();
		if(view.getPortletElementPart() != null && view.getPortletElementPart().getModel() instanceof PortletElementObj){
			Object object = view.getTv().getInput();
			ArrayList<Preference> arraylist = null;
			arraylist = (ArrayList<Preference>)object;
			arraylist.add(preference);
			view.getTv().setInput(arraylist);
			view.getTv().refresh();
			view.getTv().expandAll();
			
			AddAttrCommand addcmd = new AddAttrCommand();
			if(PortletEditor.getActiveEditor() != null)
				PortletEditor.getActiveEditor().executComand(addcmd);
		}
	}
}

