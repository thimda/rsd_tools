package nc.uap.lfw.linkcomp;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.LinkComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class LinkCompEditroInput extends ElementEditorInput{

	
	public LinkCompEditroInput(LinkComp linkcomp, LfwWidget widget, PageMeta pagemeta) {
		super(linkcomp, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Á´½Ó±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "Á´½Ó±à¼­Æ÷";
	}
}
