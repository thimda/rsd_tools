package nc.lfw.editor.common;

import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.perspective.listener.SubmitRuleDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Control;

/**
 * 提交规则
 * @author dingrf
 *
 */
public class SubmitRulePropertyDescriptor extends AbstractDialogPropertyDescriptor {

	
	private PageMeta pageMeta = null;
	
	private EventSubmitRule  submitRule = null; 
	
	/**
	 * @param id
	 * @param displayName
	 */
	public SubmitRulePropertyDescriptor(Object id, String displayName, PageMeta pageMeta, EventSubmitRule  submitRule) {
		super(id, displayName);
		this.pageMeta = pageMeta;
		this.submitRule = submitRule;
//		if (this.submitRule == null)
//			this.submitRule = new EventSubmitRule();
	}
	
	protected Object openDialogBox(Object obj, Control cellEditorWindow) {
//		SubmitRuleDialog dialog = new SubmitRuleDialog(cellEditorWindow.getShell());
		SubmitRuleDialog dialog = new SubmitRuleDialog(cellEditorWindow.getShell());
		dialog.setPagemeta(pageMeta);
		dialog.setSubmitRule(submitRule);
		
		if (dialog.open() == Dialog.OK) {
//			eventConf.setSubmitRule(dialog.getMainContainer().getSubmitRule());
			LFWBaseEditor.getActiveEditor().setDirtyTrue();
			return null;
		}
		return null;
	}
	
	
}
