package nc.uap.portal.page;

import nc.uap.portal.core.PortalBaseEditorInput;
import nc.uap.portal.om.Page;

/**
 * PortalPageEditorInput
 * 
 * @author dingrf
 *
 */
public class PortalPageEditorInput extends PortalBaseEditorInput {
	
	/**page树节点*/
	private PortalPageTreeItem pageTreeItem;

	/**page页面*/
	private Page page;
	
	public PortalPageEditorInput(PortalPageTreeItem item){
		this.pageTreeItem = item;
		this.page = (Page)item.getData();
	}
	
	public Page getPage() {
		return page;
	}

	public PortalPageTreeItem getpageTreeItem() {
		return pageTreeItem;
	}

	public void setpageTreeItem(PortalPageTreeItem pageTreeItem) {
		this.pageTreeItem = pageTreeItem;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getName() {
		return "Portal页面设计器";
	}

	public String getToolTipText() {
		return "Portal页面设计器";
	}

}
