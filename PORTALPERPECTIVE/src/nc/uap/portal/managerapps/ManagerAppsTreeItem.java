package nc.uap.portal.managerapps;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.portal.core.PortalBasicTreeItem;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.managerapps.action.DeleteManagerAppsAction;
import nc.uap.portal.managerapps.action.NewManagerCategoryAction;
import nc.uap.portal.om.ManagerApps;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ManagerApps树节点
 * 
 * @author dingrf
 *
 */
public class ManagerAppsTreeItem extends PortalBasicTreeItem{
	
	/**图片描述符*/
	private ImageDescriptor imageDescriptor = null;
	
	public ManagerAppsTreeItem(Tree parent, int style) {
		super(parent, style);
	}
	
	public ManagerAppsTreeItem(TreeItem parentItem, ManagerApps managerApps) {
		super(parentItem, SWT.NONE);
		setData(managerApps);
		setText(managerApps.getId());
		setImage(getDirImage());
	}
	
	protected Image getDirImage() {
		imageDescriptor = MainPlugin.loadImage(MainPlugin.ICONS_PATH, "groups.gif");
		return imageDescriptor.createImage();
	}
	
	/**
	 * 删除节点
	 */
	@Override
	public void deleteNode() {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		String id = ((ManagerApps)this.getData()).getId();
		PortalConnector.deleteManagerApps(projectPath, projectModuleName, id);
		dispose();
		
	}
	
	/**
	 * 增加右键菜单
	 * 
	 * @param manager
	 */
	@Override
	public void addMenuListener(IMenuManager manager){
		NewManagerCategoryAction newManagerCategoryAction = new NewManagerCategoryAction();
		DeleteManagerAppsAction deleteManagerAppsAction = new DeleteManagerAppsAction();
		manager.add(newManagerCategoryAction);
		manager.add(deleteManagerAppsAction);
	}
	
	/**
	 * 双击鼠标事件
	 * 
	 */
	@Override
	public void mouseDoubleClick(){
	} 

}
