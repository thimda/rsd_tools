/**
 * 
 */
package nc.uap.lfw.core.base;

import java.io.File;

import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * AMC TreeItem³éÏó»ùÀà
 * @author chouhl
 *
 */
public class LFWAMCTreeItem extends LFWDirtoryTreeItem{

	public LFWAMCTreeItem(TreeItem parentItem, Object object, String text) {
		super(parentItem, object, text);
		setLfwVersion(LFWTool.NEW_VERSION);
	}
	
	public LFWAMCTreeItem(TreeItem parentItem, File file) {
		super(parentItem, file);
		setLfwVersion(LFWTool.NEW_VERSION);
	}

	public LFWAMCTreeItem(TreeItem parentItem, File file, String text) {
		super(parentItem, file, text);
		setLfwVersion(LFWTool.NEW_VERSION);
	}
	
	protected Image getDirImage() {
		String imageName = "page.gif";
		if(ILFWTreeNode.APPLICATION_FOLDER.equals(getType())){
			imageName = "pages.gif";
		}else if(ILFWTreeNode.MODEL_FOLDER.equals(getType())){
			imageName = "pages.gif";
		}else if(ILFWTreeNode.WINDOW_FOLDER.equals(getType())){
			imageName = "pages.gif";
		}else if(ILFWTreeNode.PUBLIC_VIEW_FOLDER.equals(getType())){
			imageName = "pages.gif";
		}else if(ILFWTreeNode.VIEW.equals(getType())){
			imageName = "widget.gif";
		}
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, imageName).createImage();
	}
	
	public void mouseDoubleClick(){
	}
}
