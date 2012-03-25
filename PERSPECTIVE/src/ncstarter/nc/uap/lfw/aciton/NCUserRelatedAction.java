
package nc.uap.lfw.aciton;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.funnode.NCUserRelatedDialog;

import org.eclipse.jface.action.Action;

/**
 * 关联nc开发用
 * @author zhangxya
 *
 */
public class NCUserRelatedAction extends Action {

	public NCUserRelatedAction() {
		super(WEBProjConstants.USERRELATE);
	}

	public void run() {
		NCUserRelatedDialog dialog = new NCUserRelatedDialog(null);
		dialog.open();
	}

}