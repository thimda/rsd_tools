package nc.uap.lfw.perspective.webcomponent;

import java.io.File;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.perspective.project.ILFWTreeNode;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 工程中组件TreeItem
 * @author zhangxya
 *
 */
public class LFWBusinessCompnentTreeItem extends LFWBasicTreeItem implements ILFWTreeNode {

	public Object object;
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public LFWBusinessCompnentTreeItem(TreeItem parentItem, Object object, String text){
		super(parentItem, SWT.NONE);
		this.object = object;
		setData(object);
		setText(text);
		setImage(getBusiComImage());
	}
	
	private Image getBusiComImage() {
		ImageDescriptor imageDescriptor = MainPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "businesscomp.gif");
		return imageDescriptor.createImage();
	}

	public LFWBusinessCompnentTreeItem(TreeItem parentItem, File file) {
		this(parentItem, file, file.getName());
	}

	protected void checkSubclass() {
	}

	public File getFile() {
		return (File) getData();
	}

	public void deleteNode() {
		//LFWPersTool.deleteFile(getFile());
		//dispose();
	}

	public String getIPathStr() {
		String parentIPath = "";
		TreeItem parent = getParentItem();
		if (parent instanceof ILFWTreeNode) {
			parentIPath = ((ILFWTreeNode) parent).getIPathStr();
		}
		return parentIPath + "/" + getFile().getName();

	}

}
