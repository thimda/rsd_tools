package nc.uap.lfw.textcomp;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * textcomp ±à¼­Æ÷input
 * @author zhangxya
 *
 */

public class TextCompEditorInput extends ElementEditorInput{


	public TextCompEditorInput(TextComp textcomp, LfwWidget widget, PageMeta pagemeta){
		super(textcomp, widget, pagemeta);
	}
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "ÎÄ±¾¿ò±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "ÎÄ±¾¿ò±à¼­Æ÷";
	}
}
