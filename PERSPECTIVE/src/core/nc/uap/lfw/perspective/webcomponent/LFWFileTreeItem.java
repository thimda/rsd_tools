package nc.uap.lfw.perspective.webcomponent;

import java.io.File;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.perspective.project.ILFWTreeNode;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;
/**
 * lfw��Ŀ�����ļ�Item
 * @author zhangxya
 *
 */
public class LFWFileTreeItem  extends TreeItem implements ILFWTreeNode{
	public LFWFileTreeItem(TreeItem parentItem, File file) {
		super(parentItem, SWT.NONE);
		setData(file);
		setText(file.getName());
		setImage(getFileImage());
	}
	protected void checkSubclass () {
	}
	private Image getFileImage() {
		ImageDescriptor imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "file.png");
		return imageDescriptor.createImage();
	}

		
	public File getFile() {
		return (File) getData();
	}

	public void deleteNode() {
		FileUtilities.deleteFile(getFile());
		dispose();

	}
	public String getIPathStr() {
		String parentIPath = "";
		TreeItem parent = getParentItem();
		if(parent instanceof ILFWTreeNode){
			parentIPath = ((ILFWTreeNode)parent).getIPathStr();
		}
		return parentIPath+"/"+getFile().getName();
		
	}

}
