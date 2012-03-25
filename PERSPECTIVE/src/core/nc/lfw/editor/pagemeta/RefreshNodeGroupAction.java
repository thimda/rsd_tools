package nc.lfw.editor.pagemeta;

import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.perspective.project.LFWExplorerTreeBuilder;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 刷新节点组文件夹
 * @author guoweic
 *
 */
public class RefreshNodeGroupAction extends Action {
	
	
	public RefreshNodeGroupAction() {
		setText(WEBProjConstants.REFRESH);
		setToolTipText(WEBProjConstants.REFRESH);
	}

	
	public void run() {
		refreshNodes();
	}
	
	private void refreshNodes() {
		Tree tree = LFWPersTool.getTree();
		IProject project = LFWPersTool.getCurrentProject();
		TreeItem[] tis = tree.getSelection();
		if (tis == null || tis.length == 0)
			return;
		TreeItem ti = tis[0];
		ti.removeAll();
		String[] projPaths = new String[1];
		projPaths[0] = project.getLocation().toString();
		String busiComp = null;
		if(LFWPersTool.isBCPProject(project)){
			LFWBusinessCompnentTreeItem busiCompnent = LFWPersTool.getCurrentBusiCompTreeItem();
			busiComp = busiCompnent.getText().substring(busiCompnent.getText().indexOf(WEBProjConstants.BUSINESSCOMPONENT) + 
					WEBProjConstants.BUSINESSCOMPONENT.length() + 1, busiCompnent.getText().length() -1);
			projPaths[0]  += "/" + busiComp ;
		}
		Map<String, String>[] pageNames = NCConnector.getPageNames(projPaths);
		if (busiComp == null){
			LFWExplorerTreeBuilder.getInstance().initNodeSubTree(ti, project.getLocation().toString() , pageNames[0]);
		}
		else{
			LFWExplorerTreeBuilder.getInstance().initNodeSubTree(ti, project.getLocation().toString() + "/" + busiComp , pageNames[0]);
		}
	}

	
	/**
	 * 刷新节点组（供外部调用）
	 * @param treeView
	 * @param project
	 */
	public static void refreshNodes(Tree tree, IProject project) {
		TreeItem[] tis = tree.getSelection();
		if (tis == null || tis.length == 0)
			return;
		TreeItem ti = tis[0];
		ti.removeAll();
		String[] projPaths = new String[1];
		projPaths[0] = project.getLocation().toString();
		Map<String, String>[] pageNames = NCConnector.getPageNames(projPaths);
		LFWExplorerTreeBuilder.getInstance().initNodeSubTree(ti, project.getLocation().toString(), pageNames[0]);
	}

}
