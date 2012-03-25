package nc.uap.lfw.image;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ImageComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * Í¼ÐÎ±à¼­Æ÷EditorInput
 * @author zhangxya
 *
 */
public class ImageEditorInput  extends ElementEditorInput{


	public ImageEditorInput(ImageComp image, LfwWidget widget, PageMeta pagemeta){
		super(image, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Í¼ÐÎ±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "Í¼ÐÎ±à¼­Æ÷";
	}

}
