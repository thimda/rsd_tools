/**
 * 
 */
package nc.uap.lfw.application;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.DeleteAMCNodeAction;

/**
 * 
 * ɾ��Application�ڵ���
 * @author chouhl
 *
 */
public class DeleteApplicationNodeAction extends DeleteAMCNodeAction {

	public DeleteApplicationNodeAction(){
		super(WEBProjConstants.DEL_APPLICATION, WEBProjConstants.DEL_APPLICATION);
	}
	
}
