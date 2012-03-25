package nc.lfw.editor.common;

import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class PagemetaEditorInput extends LfwBaseEditorInput {

	private PageMeta pagemeta;
	
	private PageMeta cloneElement;
	
	private LFWPageMetaTreeItem pmTreeItem;
	
	public PagemetaEditorInput(PageMeta pagemeta){
		super();
		this.pagemeta = (PageMeta) pagemeta;
	}
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Pagemeta ±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "Pagemeta ±à¼­Æ÷";
	}


	public PageMeta getPagemeta() {
		return pagemeta;
	}
	
	public WebElement getCloneElement() {
		if(cloneElement == null)
			cloneElement = (PageMeta) pagemeta.clone();
		return cloneElement;
	}
	
	public LFWPageMetaTreeItem getPmTreeItem() {
		return pmTreeItem;
	}
	public void setPmTreeItem(LFWPageMetaTreeItem pmTreeItem) {
		this.pmTreeItem = pmTreeItem;
	}

}
