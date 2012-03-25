package nc.uap.portal.portlets.action;

import java.util.ArrayList;

import nc.uap.portal.container.om.EventDefinitionReference;
import nc.uap.portal.portlets.PortletEditor;
import nc.uap.portal.portlets.PortletElementObj;
import nc.uap.portal.portlets.page.ProcessingEventPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * 增加ProcessingEvent
 * @author dingrf
 *
 */
public class AddProcessingEventPropAction extends Action {
	
	private ProcessingEventPropertiesView view = null;

	private class AddAttrCommand extends Command {

		public AddAttrCommand() {
			super("增加");
		}

		public void execute() {
			redo();
		}

		public void redo() {
		}
	}
	public AddProcessingEventPropAction(ProcessingEventPropertiesView view) {
		super("增加");
		this.view = view;
	}
	private ProcessingEventPropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		EventDefinitionReference event = new EventDefinitionReference();
		insertNullProp(event);
	}
	
	@SuppressWarnings("unchecked")
	private void insertNullProp(EventDefinitionReference event){
		ProcessingEventPropertiesView view =getPropertiesView();
		if(view.getPortletElementPart() != null && view.getPortletElementPart().getModel() instanceof PortletElementObj){
			Object object = view.getTv().getInput();
			ArrayList<EventDefinitionReference> arraylist = null;
			if(object instanceof ArrayList){
				 arraylist = (ArrayList<EventDefinitionReference>)object;
			}
			else{
				arraylist = new ArrayList<EventDefinitionReference>();
			}
			arraylist.add(event);
			view.getTv().setInput(arraylist);
			view.getTv().refresh();
			view.getTv().expandAll();

			AddAttrCommand addcmd = new AddAttrCommand();
			if(PortletEditor.getActiveEditor() != null)
				PortletEditor.getActiveEditor().executComand(addcmd);
		}
	}
}

