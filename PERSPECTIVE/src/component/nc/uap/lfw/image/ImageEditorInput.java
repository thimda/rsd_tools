package nc.uap.lfw.image;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ImageComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * ͼ�α༭��EditorInput
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
		return "ͼ�α༭��";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "ͼ�α༭��";
	}

}
