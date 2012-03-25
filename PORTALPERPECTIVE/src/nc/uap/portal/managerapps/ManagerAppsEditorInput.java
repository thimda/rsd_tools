package nc.uap.portal.managerapps;

import nc.uap.portal.core.PortalBaseEditorInput;
import nc.uap.portal.om.ManagerCategory;

/**
 * ManagerAppsEditorInput
 * 
 * @author dingrf
 * 
 */
public class ManagerAppsEditorInput extends PortalBaseEditorInput {

	/**ManagerCategory ����*/
	private ManagerCategory managerCategory;
	
	/** ManagerApps id*/
	private String  managerId;

	public ManagerCategory getManagerCategory() {
		return managerCategory;
	}

	public void setManagerCategory(ManagerCategory managerCategory) {
		this.managerCategory = managerCategory;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public ManagerAppsEditorInput(ManagerCategory managerCategory,String managerId){
		this.managerCategory = managerCategory;
		this.managerId = managerId; 
	}
	
	/**
	 * �༭������
	 */
	public String getName() {
		return "���� �༭��";
	}

	/**
	 * tool tip
	 */
	public String getToolTipText() {
		return "���� �༭��";
	}

}
