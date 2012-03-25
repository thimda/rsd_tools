package nc.uap.lfw.label;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.LabelComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * ±êÇ©±à¼­Æ÷Input
 * @author zhangxya
 *
 */
public class LabelEditorInput extends ElementEditorInput{


	public LabelEditorInput(LabelComp label, LfwWidget widget, PageMeta pagemeta){
		super(label, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "±êÇ©±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "±êÇ©±à¼­Æ÷";
	}
}
