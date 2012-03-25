package nc.uap.portal.managerapps;

import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.portal.core.PortalBasicTreeItem;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.managerapps.action.DelManagerCategoryAction;
import nc.uap.portal.managerapps.action.ImportManagerNodesAction;
import nc.uap.portal.managerapps.action.NewManagerCategoryAction;
import nc.uap.portal.om.ManagerApps;
import nc.uap.portal.om.ManagerCategory;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalPlugin;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * ���ܷ������ڵ�
 * 
 * @author dingrf
 *
 */
public class ManagerCategoryTreeItem extends PortalBasicTreeItem{
	
	/**ͼƬ������*/
	private ImageDescriptor imageDescriptor = null;
	
	/**����ID*/
	private String managerId;
	
	
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public ManagerCategoryTreeItem(Tree parent, int style) {
		super(parent, style);
	}
	
	
	
	public ManagerCategoryTreeItem(TreeItem parentItem, String managerId,ManagerCategory managerCategory) {
		super(parentItem, SWT.NONE);
		setData(managerCategory);
		setText(managerCategory.getText());
		setImage(getDirImage());
		this.managerId = managerId; 
	}
	
	protected Image getDirImage() {
		imageDescriptor = PortalPlugin.loadImage(PortalPlugin.ICONS_PATH, "func.gif");
		return imageDescriptor.createImage();
	}

	@Override
	public void deleteNode() {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		ManagerApps managerApps = PortalConnector.getManagerApps(projectPath, projectModuleName, this.getManagerId());
		ManagerCategory managerCategory =  (ManagerCategory) this.getData();
		deleteManagerCategory(managerApps.getCategory(),managerCategory);
		PortalConnector.saveManagerAppsToXml(projectPath, projectModuleName, managerApps);
		dispose();
	}
	
	private boolean deleteManagerCategory(List<ManagerCategory> managerCategories,
			ManagerCategory managerCategory) {
		for (ManagerCategory mc: managerCategories){
			if (managerCategory.getId().equals(mc.getId())){
				mc.getCategory().remove(managerCategory);
				return true;
			}
			else if (deleteManagerCategory(mc.getCategory(),managerCategory)){
					return true;
			}
		}
		return false;
	}
	
	
	/**
	 * �����Ҽ��˵�
	 * 
	 * @param manager
	 */
	@Override
	public void addMenuListener(IMenuManager manager){
		NewManagerCategoryAction newManagerCategoryAction = new NewManagerCategoryAction();
		DelManagerCategoryAction delManagerCategoryAction = new DelManagerCategoryAction();
		ImportManagerNodesAction importManagerNodesAction = new ImportManagerNodesAction();
		manager.add(newManagerCategoryAction);
		manager.add(delManagerCategoryAction);
		manager.add(importManagerNodesAction);
	}
	
	/**
	 * ˫������¼�
	 * 
	 */
	@Override
	public void mouseDoubleClick(){
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		view.openManagerAppsPageEditor(this);
	} 

}
