package nc.lfw.editor.pagemeta;

import nc.uap.lfw.core.WEBProjConstants;

import org.eclipse.jface.action.Action;

/**
 * 导入美工
 * @author zhangxya
 *
 */
public class UIGuildImportHtml extends Action {

	public UIGuildImportHtml() {
		super(WEBProjConstants.UI_GUILD_IMPORT_HTML);
	}
	
	
	public void run() {
		UIGuildImportHtmlDialog dialog = new UIGuildImportHtmlDialog(null);
		dialog.open();
	}
}
