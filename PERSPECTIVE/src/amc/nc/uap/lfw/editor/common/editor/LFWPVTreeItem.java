/**
 * 
 */
package nc.uap.lfw.editor.common.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.publicview.RefreshAllPublicViewAction;

/**
 * @author chouhl
 * 2011-11-17
 */
public class LFWPVTreeItem extends LFWBasicTreeItem {
	
	private int imageType = 0;

	public LFWPVTreeItem(Tree parent, int style) {
		super(parent, style);
		setImage(getProjectImage());
	}
	
	public LFWPVTreeItem(TreeItem parentItem, int style, int imageType) {
		super(parentItem, style);
		this.imageType = imageType;
		setImage(getProjectImage());
	}
	
	public LFWPVTreeItem(TreeItem parentItem, int style, int index, int imageType) {
		super(parentItem, style, index);
		this.imageType = imageType;
		setImage(getProjectImage());
	}
	
	protected void checkSubclass () {
	}

	private Image getProjectImage() {
		ImageDescriptor imageDescriptor = null;
		switch(imageType){
			case 0:
				imageDescriptor = MainPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "project.gif");
				break;
			case 1:
				imageDescriptor = MainPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "pages.gif");
				break;
			case 2:
				imageDescriptor = MainPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "widget.gif");
				break;
			default:
				imageDescriptor = MainPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "refnode.gif");
				break;
		}
		return imageDescriptor.createImage();
	}

	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	
	@Override
	public void mouseDoubleClick() {
	}
	
	@Override
	public void addMenuListener(IMenuManager manager) {
		if(("PublicViews").equals(this.getId())){
			RefreshAllPublicViewAction refreshAction = new RefreshAllPublicViewAction(this);
			manager.add(refreshAction);
		}
	}
	
}
