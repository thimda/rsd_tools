package nc.uap.lfw.refnoderel;


import nc.lfw.editor.common.WidgetEditorInput;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.refnode.RefNodeRelation;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class RefnoderelEditorInput extends WidgetEditorInput{

	private RefNodeRelation refnodeRel;
	public RefNodeRelation getRefnodeRel() {
		return refnodeRel;
	}

	public void setRefnodeRel(RefNodeRelation refnodeRel) {
		this.refnodeRel = refnodeRel;
	}

	public RefnoderelEditorInput(RefNodeRelation refnodeRel, LfwWidget widget, PageMeta pagemeta) {
		super(widget, pagemeta);
		this.refnodeRel = refnodeRel;
	}

	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "参照关系编辑器";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "参照关系编辑器";
	}
}
