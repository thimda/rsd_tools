/**
 * 
 */
package nc.uap.lfw.model;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.DeleteAMCNodeAction;

/**
 * 
 * ɾ��Model�ڵ���
 * @author chouhl
 *
 */
public class DeleteModelNodeAction extends DeleteAMCNodeAction {

	public DeleteModelNodeAction(){
		super(WEBProjConstants.DEL_MODEL, WEBProjConstants.DEL_MODEL);
	}
	
}
