package nc.uap.lfw.refnode.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.refnode.BaseRefNode;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
/**
 * ≤Œ’’editorInput
 * @author zhangxya
 *
 */

public class RefNodeEditorInput  extends ElementEditorInput{

	private boolean isFromPool;
	
	public boolean isFromPool() {
		return isFromPool;
	}

	public void setFromPool(boolean isFromPool) {
		this.isFromPool = isFromPool;
	}

	public RefNodeEditorInput(BaseRefNode refnode, LfwWidget widget, PageMeta pagemeta){
		super(refnode, widget, pagemeta);
	}
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "≤Œ’’±‡º≠∆˜";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "≤Œ’’±‡º≠∆˜";
	}

}
