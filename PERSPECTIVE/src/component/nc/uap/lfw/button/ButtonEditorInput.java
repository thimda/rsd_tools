package nc.uap.lfw.button;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * button editor input
 * @author zhangxya
 *
 */
public class ButtonEditorInput extends ElementEditorInput{


	public ButtonEditorInput(ButtonComp button, LfwWidget widget, PageMeta pagemeta){
		super(button, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "°´Å¥±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "°´Å¥±à¼­Æ÷";
	}

}
