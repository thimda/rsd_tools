package nc.uap.lfw.editor.application.plug;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.uap.lfw.core.page.Connector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author dingrf
 *
 */
public class ConnectorMappingCellEditor extends DialogCellEditor {

	private Composite composite;
	
	private TreeViewer tv;

	public ConnectorMappingCellEditor(Composite parent, org.eclipse.jface.viewers.TreeViewer tv2) {
		this(parent, SWT.NONE);
		this.tv = tv2;
	}

	public ConnectorMappingCellEditor(Composite parent, int style) {
		super(parent, style);
		doSetValue("");
	}

	protected Control createContents(Composite cell) {
		composite = new Composite(cell, getStyle());
		return composite;
	}

	protected Object openDialogBox(Control cellEditorWindow) {		
		IStructuredSelection selection = (IStructuredSelection) tv.getSelection();
		Connector conn = (Connector) selection.getFirstElement();
			
		ConnectorMappingDialog dialog = new ConnectorMappingDialog(cellEditorWindow.getShell(),"πÿœµ”≥…‰", conn);
		if (dialog.isNoError() && dialog.open() == Dialog.OK) {
			LFWBaseEditor.getActiveEditor().setDirtyTrue();
			return null;
		}
		return null;
	}

}
