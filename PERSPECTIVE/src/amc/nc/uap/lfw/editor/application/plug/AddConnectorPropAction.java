package nc.uap.lfw.editor.application.plug;

import java.util.ArrayList;

import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.editor.application.ApplicationEditor;
import nc.uap.lfw.editor.application.ApplicationObj;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * 增加Connector
 * @author dingrf
 *
 */
public class AddConnectorPropAction extends Action {
	
	private ConnectorPropertiesView view = null;
	
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
	public AddConnectorPropAction(ConnectorPropertiesView view) {
		super("增加");
		this.view = view;
	}
	private ConnectorPropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		Connector conn = new Connector();
		insertNullProp(conn);
	}
	
	@SuppressWarnings("unchecked")
	private void insertNullProp(Connector conn){
		ConnectorPropertiesView view =getPropertiesView();
		if(view.getApplicationPart() != null && view.getApplicationPart().getModel() instanceof ApplicationObj){
			Object object = view.getTv().getInput();
			ArrayList<Connector> arraylist = null;
			arraylist = (ArrayList<Connector>)object;
			arraylist.add(conn);
			view.getTv().setInput(arraylist);
			view.getTv().refresh();
			view.getTv().expandAll();
	
			AddAttrCommand addcmd = new AddAttrCommand();
			if(ApplicationEditor.getActiveEditor() != null)
				ApplicationEditor.getActiveEditor().executComand(addcmd);
			}
	}
}

