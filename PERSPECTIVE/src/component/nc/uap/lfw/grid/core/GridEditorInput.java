package nc.uap.lfw.grid.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * gridEditor input
 * @author zhangxya
 *
 */
public class GridEditorInput extends ElementEditorInput{

	
	public GridEditorInput(GridComp gridcomp, LfwWidget widget, PageMeta pagemeta) {
		super(gridcomp, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "表格编辑器";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "表格编辑器";
	}

}
