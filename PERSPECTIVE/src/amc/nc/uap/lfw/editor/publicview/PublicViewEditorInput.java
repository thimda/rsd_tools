/**
 * 
 */
package nc.uap.lfw.editor.publicview;

import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;

/**
 * @author chouhl
 *
 */
public class PublicViewEditorInput extends LfwBaseEditorInput {

	private String folderPath;
	private String pmPath;
	
	private LFWDirtoryTreeItem dirTreeItem;
	
	private LfwWidget widget;
	private LfwWidget cloneElement;
	
	public PublicViewEditorInput(){
		super();
	}
	
	public LfwWidget getWidget() {
		return widget;
	}
	
	public WebElement getCloneElement() {
		if(cloneElement == null)
			cloneElement = (LfwWidget) widget.clone();
		return cloneElement;
	}
	
	public PublicViewEditorInput(LfwWidget widget){
		this.widget = widget;
	}
	
	public String getPmPath() {
		return pmPath;
	}

	public void setPmPath(String pmPath) {
		this.pmPath = pmPath;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getName() {
		return "PublicView Web 设计器";
	}

	public String getToolTipText() {
		return "PublicView Web 设计器";
	}

	public LFWDirtoryTreeItem getDirTreeItem() {
		return dirTreeItem;
	}

	public void setDirTreeItem(LFWDirtoryTreeItem dirTreeItem) {
		this.dirTreeItem = dirTreeItem;
	}

}
