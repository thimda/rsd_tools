package nc.uap.lfw.perspective.action;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.funnode.NCQueryTemplateDialog;

import org.eclipse.jface.action.Action;

/**
 * 关联NC查询模板
 * @author zhangxya
 *
 */
public class NCQueryTemplageAction extends Action {

	public NCQueryTemplageAction() {
		super(WEBProjConstants.USE_NC_QUERY_TEMPLAGE);
	}

	private String funnode;
	private String filePath;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFunnode() {
		return funnode;
	}
	public void setFunnode(String funnode) {
		this.funnode = funnode;
	}
	
	public void run() {
		NCQueryTemplateDialog dlg = new NCQueryTemplateDialog(null, funnode, filePath);
		dlg.open();
	}

}
