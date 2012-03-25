package nc.uap.portal.managerapps;

import nc.uap.portal.om.ManagerCategory;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

/**
 *功能分类 label
 * 
 * @author dingrf
 */
public class ManagerCategoryLabel extends Label{

	/**功能分类对象*/
	private ManagerCategory managerCategory;

	public ManagerCategoryLabel(String text, ManagerCategory managerCategory) {
		super(text);
		this.managerCategory = managerCategory;
		setLabelAlignment(PositionConstants.LEFT);

		markError();
	}

	public ManagerCategory getManagerCategory() {
		return managerCategory;
	}

	private void markError() {

	}

}
