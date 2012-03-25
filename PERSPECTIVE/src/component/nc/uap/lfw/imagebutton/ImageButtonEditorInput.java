package nc.uap.lfw.imagebutton;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ImageButtonComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class ImageButtonEditorInput extends ElementEditorInput{


	public ImageButtonEditorInput(ImageButtonComp imageButton, LfwWidget widget, PageMeta pagemeta){
		super(imageButton, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Í¼ÐÎ°´Å¥±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "Í¼ÐÎ°´Å¥±à¼­Æ÷";
	}

}
