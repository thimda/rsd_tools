package nc.uap.lfw.perspective.action;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.funnode.WidgetTempDialog;

import org.eclipse.jface.action.Action;

/**
 * 新建结点
 * @author guoweic
 *
 */
public class UseNCTemplateAction extends Action {

	public UseNCTemplateAction() {
		super(WEBProjConstants.USE_NC_TEMPLATE);
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
		//new TemplateLauncher().launch();
		WidgetTempDialog dlg = new WidgetTempDialog(null, WEBProjConstants.USE_NC_TEMPLATE, funnode, filePath);
		dlg.open();
	}

}
