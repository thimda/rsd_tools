package nc.uap.lfw.toolbar;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ToolBarComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * toolbar 编辑器input
 * @author zhangxya
 *
 */
public class ToolBarEditorInput extends ElementEditorInput{


	public ToolBarEditorInput(ToolBarComp toolbar, LfwWidget widget, PageMeta pagemeta){
		super(toolbar, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "工具条编辑器";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "工具条编辑器";
	}

}
