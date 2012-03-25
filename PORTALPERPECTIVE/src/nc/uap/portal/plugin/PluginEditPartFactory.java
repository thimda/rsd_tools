package nc.uap.portal.plugin;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * PluginEditPartFactory
 * 
 * @author dingrf
 * 
 */
public class PluginEditPartFactory implements EditPartFactory {

	/**
	 * ´´½¨EditPart
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart editPart = null;
		if (model instanceof PluginElementObj){
			editPart = new PluginElementPart();
		}
		else
			throw new RuntimeException("illegal param");
		editPart.setModel(model);
		return editPart;
	}

}
