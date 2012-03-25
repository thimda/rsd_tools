package nc.uap.portal.events.action;

import java.util.ArrayList;

import nc.uap.portal.container.om.EventDefinition;
import nc.uap.portal.events.EventsEditor;
import nc.uap.portal.events.EventsElementObj;
import nc.uap.portal.events.page.EventsPropertiesView;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * �¼����Ӳ���
 * 
 * @author dingrf
 */
public class AddEventsPropAction extends Action {
	
	/**
	 * ģ��������ͼ
	 */
	private EventsPropertiesView view = null;
	
	@SuppressWarnings("unused")
	private class AddAttrCommand extends Command {
		private EventDefinition ed = null;
		
		private ArrayList<EventDefinition> arraylist = null;
		
		private EventsElementObj eeobj = null;

		public AddAttrCommand(EventsElementObj eeobj,
				ArrayList<EventDefinition> arraylist, EventDefinition ed) {
			super("����");
			this.eeobj = eeobj;
			this.arraylist = arraylist;
			this.ed = ed;
		}

		private EventsPropertiesView getPropertiesView() {
			return view;
		}

		public void execute() {
			redo();
		}

		public void redo() {
			EventsPropertiesView view = getPropertiesView();
			view.getTv().setInput(arraylist);
			view.getTv().refresh();
			view.getTv().expandAll();

		}
	}
	
	public AddEventsPropAction(EventsPropertiesView view) {
		super("����");
		this.view = view;
	}
	
	private EventsPropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		EventDefinition ed = new EventDefinition();
		insertNullProp(ed);
	}
	
	/**
	 *�������� 
	 */
	@SuppressWarnings("unchecked")
	private void insertNullProp(EventDefinition ed){
		EventsPropertiesView view =getPropertiesView();
		if(view.getEventsElementPart() != null && view.getEventsElementPart().getModel() instanceof EventsElementObj){
			EventsElementObj vo = (EventsElementObj)view.getEventsElementPart().getModel();
			Object object = view.getTv().getInput();
			ArrayList<EventDefinition> arraylist = null;
			if(object instanceof ArrayList){
				 arraylist = (ArrayList<EventDefinition>)object;
			}
			else{
				arraylist = new ArrayList<EventDefinition>();
			}
			arraylist.add(ed);
			AddAttrCommand addcmd = new AddAttrCommand(vo, arraylist, ed);
			if(EventsEditor.getActiveEditor() != null)
				EventsEditor.getActiveEditor().executComand(addcmd);

		}
	}
}

