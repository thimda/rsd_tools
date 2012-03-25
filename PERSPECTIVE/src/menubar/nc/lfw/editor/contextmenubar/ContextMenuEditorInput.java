package nc.lfw.editor.contextmenubar;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * ÓÒ¼ü²Ëµ¥Edior input
 * @author zhangxya
 *
 */
public class ContextMenuEditorInput extends ElementEditorInput{


	public ContextMenuEditorInput(ContextMenuComp context, LfwWidget widget, PageMeta pagemeta){
		super(context, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "ÓÒ¼ü²Ëµ¥±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "ÓÒ¼ü²Ëµ¥±à¼­Æ÷";
	}
}
