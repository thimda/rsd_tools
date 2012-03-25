package nc.uap.lfw.button;

import nc.lfw.editor.common.AbstractDialogPropertyDescriptor;

import org.eclipse.swt.widgets.Control;

public class HotKeySetPropertyDescriptor extends AbstractDialogPropertyDescriptor{

	public HotKeySetPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object openDialogBox(Object value, Control cellEditorWindow) {
		HotKeySetDialog dialog = new HotKeySetDialog(null, "»»º¸…Ë÷√");
		if(dialog.open() == HotKeySetDialog.OK)
			return dialog.getResult();
		return null;	
	}

}
