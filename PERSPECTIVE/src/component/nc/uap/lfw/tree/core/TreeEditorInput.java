package nc.uap.lfw.tree.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * Ê÷±à¼­Æ÷input
 * @author zhangxya
 *
 */
public class TreeEditorInput  extends ElementEditorInput{

	public TreeEditorInput(TreeViewComp treeViewComp, LfwWidget widget, PageMeta pagemeta){
		super(treeViewComp, widget, pagemeta);
	}
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Ê÷±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "Ê÷±à¼­Æ÷";
	}
}
