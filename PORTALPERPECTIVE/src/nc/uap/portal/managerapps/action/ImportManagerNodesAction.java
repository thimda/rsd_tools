package nc.uap.portal.managerapps.action;

import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.managerapps.ManagerCategoryTreeItem;
import nc.uap.portal.managerapps.dialog.ImportManagerNodesDialog;
import nc.uap.portal.managerapps.dialog.PageNode;
import nc.uap.portal.om.ManagerApps;
import nc.uap.portal.om.ManagerCategory;
import nc.uap.portal.om.ManagerNode;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalProjConstants;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 导入功能节点
 * 
 * @author dingrf
 *
 */
public class ImportManagerNodesAction extends Action {
	
	public ImportManagerNodesAction() {
		super(PortalProjConstants.IMPORT_MANAGERNODES, PaletteImage.getCreateGridImgDescriptor());
	}

	public void run() {
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		if(view == null)
			return;
		Tree tree = view.getTreeView().getTree();
		TreeItem[] selTIs = tree.getSelection();
		TreeItem selTI = selTIs[0];
		if (!(selTI instanceof ManagerCategoryTreeItem)){
			MessageDialog.openError(new Shell(), "错误提示", "必须在功能分类下导入功能节点!");
			return;
		}
		
		ImportManagerNodesDialog importManagerNodesDialog = new ImportManagerNodesDialog(new Shell(), PortalProjConstants.IMPORT_MANAGERNODES);
		importManagerNodesDialog.create();
		
		ManagerCategory managerCategory =  (ManagerCategory)selTI.getData();
		List<ManagerNode> managerNodes  = managerCategory.getNode();
		importManagerNodesDialog.setManagerNodes(managerNodes);
		importManagerNodesDialog.loadNodes();
		
		if(importManagerNodesDialog.open() == IDialogConstants.OK_ID){
			
			try {
				managerNodes.clear();
				List<PageNode> pageNodes = importManagerNodesDialog.getPageNodes();
				for (PageNode p : pageNodes){
					if (p.getCheck()){
						ManagerNode managerNode = new ManagerNode();
						managerNode.setId(p.getId());
						managerNode.setText(p.getName());
						managerNode.setUrl(p.getUrl());
						managerNodes.add(managerNode);
					}
				}
				
				String managerId = ((ManagerCategoryTreeItem)selTI).getManagerId(); 
				String projectPath = LFWPersTool.getProjectWithBcpPath();
				String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
				ManagerApps managerApps = PortalConnector.getManagerApps(projectPath, projectModuleName, managerId);
				if (managerApps == null){
					managerApps = new ManagerApps();
					managerApps.setId(managerId);
					managerApps.addPortletDisplayList(managerCategory);
				}
				else{
					updateManagerNode(managerApps.getCategory(),managerCategory);
				}
				PortalConnector.saveManagerAppsToXml(projectPath, projectModuleName, managerApps);
			} catch (Exception e) {
				String title =PortalProjConstants.IMPORT_MANAGERNODES;
				String message = e.getMessage();
				MessageDialog.openError(new Shell(), title, message);
			}
		}
		else
			return;
	}

	/**
	 * 更新 managerApps 中对应的 ManagerNode
	 * 
	 * @param mc
	 * @param managerCategory
	 */
	private boolean updateManagerNode(List<ManagerCategory> managerCategories,
			ManagerCategory managerCategory) {
		for (ManagerCategory mc: managerCategories){
			if (managerCategory.getId().equals(mc.getId())){
				mc.getNode().clear();
				mc.getNode().addAll(managerCategory.getNode());
				return true;
			}
			else if (updateManagerNode(mc.getCategory(),managerCategory)){
					return true;
			}
		}
		return false;
	}

}
