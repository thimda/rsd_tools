package nc.uap.lfw.perspective.webcomponent;

import java.io.File;

import nc.lfw.editor.pagemeta.NewNodeAction;
import nc.lfw.virtualdirec.core.DelVirtualDirAcrtion;
import nc.lfw.virtualdirec.core.NewVirtualDirAction;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.editor.common.tools.LFWTool;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ÐéÄâÄ¿Â¼TreeItem
 * @author zhangxya
 *
 */
public class LFWVirtualDirTreeItem extends LFWDirtoryTreeItem{
	private ImageDescriptor imageDescriptor = null;
	
	public LFWVirtualDirTreeItem(TreeItem parentItem, File file) {
		super(parentItem, file);
	}
	
	protected Image getDirImage() {
		imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "virtualdir.gif");
		return imageDescriptor.createImage();
	}
	
	public void addMenuListener(IMenuManager manager){
		if(LFWTool.NEW_VERSION.equals(getLfwVersion())){
			addAMCMenuListener(manager);
		}else{
			NewNodeAction newNodeAction = new NewNodeAction();
			NewVirtualDirAction newVirDirAction = new NewVirtualDirAction();
			DelVirtualDirAcrtion delVirDirAction = new DelVirtualDirAcrtion();
			manager.add(newNodeAction);
			manager.add(newVirDirAction);
			manager.add(delVirDirAction);
		}
	} 

	public void mouseDoubleClick(){
	} 
	
}
