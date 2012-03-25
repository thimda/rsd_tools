package nc.uap.portal.plugin.action;

import java.util.ArrayList;

import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.plugin.PluginEditor;
import nc.uap.portal.plugin.PluginElementObj;
import nc.uap.portal.plugin.page.PluginPropertiesView;
import nc.uap.portal.plugins.model.Extension;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;

/**
 * Exension增加操作
 * 
 * @author dingrf
 */
public class AddExtensionPropAction extends Action {
	
	/**模型属性视图*/
	private PluginPropertiesView view = null;
	
	private class AddAttrCommand extends Command {
		
		private ArrayList<Extension> arraylist = null;
		

		public AddAttrCommand(ArrayList<Extension> arraylist) {
			super("增加");
			this.arraylist = arraylist;
		}

		private PluginPropertiesView getPropertiesView() {
			return view;
		}

		public void execute() {
			redo();
		}

		public void redo() {
			PluginPropertiesView view = getPropertiesView();
			view.getTv().setInput(arraylist);
			view.getTv().refresh();
			view.getTv().expandAll();

		}
	}
	
	public AddExtensionPropAction(PluginPropertiesView view) {
		super(PortalProjConstants.NEW_EXTENSION);
		this.view = view;
	}
	
	private PluginPropertiesView getPropertiesView(){
		return view;
	}
	
	public void run() {
		Extension ed = new Extension();
		ed.setIsactive(true);
		insertNullProp(ed);
	}
	
	/**
	 *新增属性 
	 */
	@SuppressWarnings("unchecked")
	private void insertNullProp(Extension ed){
		PluginPropertiesView view =getPropertiesView();
		if(view.getPluginElementPart() != null && view.getPluginElementPart().getModel() instanceof PluginElementObj){
			Object object = view.getTv().getInput();
			ArrayList<Extension> arraylist = null;
			if(object instanceof ArrayList){
				 arraylist = (ArrayList<Extension>)object;
			}
			else{
				arraylist = new ArrayList<Extension>();
			}
			arraylist.add(ed);

			AddAttrCommand addcmd = new AddAttrCommand(arraylist);
			if(PluginEditor.getActiveEditor() != null)
				PluginEditor.getActiveEditor().executComand(addcmd);
		}
	}
}

