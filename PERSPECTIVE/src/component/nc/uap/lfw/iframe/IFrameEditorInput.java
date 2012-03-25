package nc.uap.lfw.iframe;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.IFrameComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * Iframe editor input
 * @author zhangxya
 *
 */
public class IFrameEditorInput extends ElementEditorInput{


	public IFrameEditorInput(IFrameComp iFrame, LfwWidget widget, PageMeta pagemeta){
		super(iFrame, widget, pagemeta);
	}
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "IFrame ±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "IFrame ±à¼­Æ÷";
	}
}
