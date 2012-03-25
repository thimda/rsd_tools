/**
 * 
 */
package nc.uap.lfw.perspective.listener;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author chouhl
 *
 */
public class EventSubmitRuleCellEditor extends DialogCellEditor {

	private Composite composite;
	
	private TableViewer tv;

	public EventSubmitRuleCellEditor(Composite parent, TableViewer tv) {
		this(parent, SWT.NONE);
		this.tv = tv;
	}

	public EventSubmitRuleCellEditor(Composite parent, int style) {
		super(parent, style);
		doSetValue("");
	}

	protected Control createContents(Composite cell) {
		composite = new Composite(cell, getStyle());
		return composite;
	}

	protected Object openDialogBox(Control cellEditorWindow) {		
		IStructuredSelection selection = (IStructuredSelection) tv.getSelection();
		EventConf eventConf = (EventConf) selection.getFirstElement();
		EventSubmitRule submitRule = eventConf.getSubmitRule();
		if (null == submitRule) {
			submitRule = new EventSubmitRule();
		}
			
		SubmitRuleDialog dialog = new SubmitRuleDialog(cellEditorWindow.getShell());
		PageMeta pagemeta = LFWPersTool.getCurrentPageMeta();
		dialog.setPagemeta(pagemeta);
		dialog.setSubmitRule(submitRule);
		
		if (dialog.open() == Dialog.OK) {
			eventConf.setSubmitRule(dialog.getMainContainer().getSubmitRule());
			LFWBaseEditor.getActiveEditor().setDirtyTrue();
			return null;
		}
		return null;
	}

}
