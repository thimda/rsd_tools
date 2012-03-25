package nc.uap.lfw.selfdefcomp.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.SelfDefComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class SelfDefCompEditorInput extends ElementEditorInput{


	public SelfDefCompEditorInput(SelfDefComp seldefComp, LfwWidget widget, PageMeta pagemeta){
		super(seldefComp, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "自定义编辑器";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "自定义编辑器";
	}

}
