package nc.uap.portal.page;

import java.io.File;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.portal.core.PortalBasicTreeItem;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.om.Page;
import nc.uap.portal.page.action.DeletePageAction;
import nc.uap.portal.page.action.EditPageAction;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalPlugin;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Pageҳ���ڵ�
 * 
 * @author dingrf
 *
 */
public class PortalPageTreeItem extends PortalBasicTreeItem{
	
	/**ͼƬ������*/
	private ImageDescriptor imageDescriptor = null;
	
	public PortalPageTreeItem(Tree parent, int style) {
		super(parent, style);
	}

	public PortalPageTreeItem(TreeItem parentItem, File file, String text) {
		super(parentItem, SWT.NONE);
		setData(file);
		setText(text);
		setImage(getDirImage());
	}
	
	public PortalPageTreeItem(TreeItem parentItem, Page page) {
		super(parentItem, SWT.NONE);
		setData(page);
		setText(page.getPagename());
		setImage(getDirImage());
	}
	
	protected Image getDirImage() {
		imageDescriptor = PortalPlugin.loadImage(PortalPlugin.ICONS_PATH, "page.gif");
		return imageDescriptor.createImage();
	}
	
	@Override
	public void deleteNode() {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		String pageName = ((Page)this.getData()).getPagename();
		PortalConnector.deletePage(projectPath, projectModuleName, pageName);
		dispose();
		
	}
	
	@Override
	public File getFile() {
		return null;
	}
	@Override
	public String getIPathStr() {
		return null;
	}
	/**
	 * �����Ҽ��˵�
	 * 
	 * @param manager
	 */
	@Override
	public void addMenuListener(IMenuManager manager){
		DeletePageAction deletePageAction = new DeletePageAction();
		EditPageAction editPageAction = new EditPageAction();
		manager.add(editPageAction);
		manager.add(deletePageAction);
	}
	
	/**
	 * ˫������¼�
	 * 
	 */
	@Override
	public void mouseDoubleClick(){
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		view.openPortalPageEditor(this);
	} 

}
