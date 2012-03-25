package nc.uap.portal.managerapps;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * ManagerAppsEditPartFactory
 * 
 * @author dingrf
 * 
 */
public class ManagerAppsEditPartFactory implements EditPartFactory {

	/**
	 * ´´½¨EditPart
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart editPart = null;
		if (model instanceof ManagerAppsElementObj){
			editPart = new ManagerAppsElementPart();
		}
		else
			throw new RuntimeException("illegal param");
		editPart.setModel(model);
		return editPart;
	}

}
