package nc.uap.lfw.perspective.listener;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author guoweic
 * 
 */
public class SubmitRuleCellEditor extends DialogCellEditor {

	private Composite composite;
	
	private Table table;
	
	private TableViewer tv;
	
	private JsListenerConf jsListener;

	public SubmitRuleCellEditor(Composite parent, TableViewer tv, JsListenerConf jsListener) {
		this(parent, SWT.NONE);
		this.table = (Table) parent;
		this.tv = tv;
		this.jsListener = jsListener;
		
	}

	public SubmitRuleCellEditor(Composite parent, int style) {
		super(parent, style);
		doSetValue("");
	}

	protected Control createContents(Composite cell) {
		composite = new Composite(cell, getStyle());
		return composite;
	}

	protected Object openDialogBox(Control cellEditorWindow) {
		TableItem item = table.getSelection()[0];
//		if ("ÊÇ".equals(item.getText(2))) {
		IStructuredSelection selection = (IStructuredSelection) tv.getSelection();
		EventHandlerConf jsEventObj = (EventHandlerConf) selection.getFirstElement();
		EventSubmitRule submitRule = jsEventObj.getSubmitRule();
		if (null == submitRule) {
			submitRule = new EventSubmitRule();
		}
			
		SubmitRuleDialog dialog = new SubmitRuleDialog(cellEditorWindow.getShell());
		PageMeta pagemeta = LFWPersTool.getCurrentPageMeta();
		dialog.setPagemeta(pagemeta);
		dialog.setSubmitRule(submitRule);
		
		if (dialog.open() == Dialog.OK) {
			jsEventObj.setSubmitRule(dialog.getMainContainer().getSubmitRule());
			
//			LFWBaseEditor.getActiveEditor().saveJsListener(jsListener.getId(), jsEventObj, jsListener);
			LFWBaseEditor.getActiveEditor().saveListener(jsListener.getId(), jsEventObj, jsListener);
			
			tv.refresh();
			
			LFWBaseEditor.getActiveEditor().setDirtyTrue();
			
			return null;
		}
//		}
		return null;
	}

}
