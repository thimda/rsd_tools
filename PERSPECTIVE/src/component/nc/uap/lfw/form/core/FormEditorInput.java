package nc.uap.lfw.form.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * formEditor input
 * @author zhangxya
 *
 */
public class FormEditorInput extends ElementEditorInput{


	public FormEditorInput(FormComp formcomp, LfwWidget widget, PageMeta pagemeta){
		super(formcomp, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "表单编辑器";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "表单编辑器";
	}
}