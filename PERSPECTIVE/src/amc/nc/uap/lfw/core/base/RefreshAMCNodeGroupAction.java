/**
 * 
 */
package nc.uap.lfw.core.base;

import java.util.Map;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeBuilder;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * 刷新节点基类
 * @author chouhl
 *
 */
public class RefreshAMCNodeGroupAction extends NodeAction{

	/**
	 * 节点保存文件相对路径
	 */
	private String path = null;
	/**
	 * 节点类型
	 */
	private String itemType = null;
	/**
	 * 节点保存文件名称
	 */
	private String fileName = null;
	
	public RefreshAMCNodeGroupAction(String path, String itemType, String fileName) {
		super(WEBProjConstants.REFRESH, WEBProjConstants.REFRESH);
		setImageDescriptor(PaletteImage.getRefreshImgDescriptor());
		this.path = path;
		this.itemType = itemType;
		this.fileName = fileName;
	}

	public void run() {
		refreshNodes();
	}
	
	private void refreshNodes() {
		//获取当前工程
		IProject project = LFWAMCPersTool.getCurrentProject();
		//获取选择的节点
		TreeItem parent = LFWAMCPersTool.getCurrentTreeItem();
		//移除所有子节点,重新加载
		parent.removeAll();
		//获取当前工程路径
		String projPath = LFWAMCPersTool.getProjectPath();
		String indexOfStr = null;
		if(ILFWTreeNode.APPLICATION.equals(itemType)){
			indexOfStr = WEBProjConstants.AMC_APPLICATION_PATH.replaceAll("\\\\", "/");
		}else if(ILFWTreeNode.MODEL.equals(itemType)){
			indexOfStr = WEBProjConstants.AMC_MODEL_PATH.replaceAll("\\\\", "/");
		}else if(ILFWTreeNode.WINDOW.equals(itemType)){
			indexOfStr = WEBProjConstants.AMC_WINDOW_PATH.replaceAll("\\\\", "/");
		}else if(ILFWTreeNode.PUBLIC_VIEW.equals(itemType)){
			indexOfStr = WEBProjConstants.AMC_PUBLIC_VIEW_PATH.replaceAll("\\\\", "/");
		}
		int index = -1;
		if(indexOfStr != null){
			index = LFWAMCPersTool.getCurrentFolderPath().lastIndexOf(indexOfStr);
		}
		if(index >= 0){
			path = LFWAMCPersTool.getCurrentFolderPath().substring(index);
		}
		Map<String, String>[] amcNodeNames = LFWAMCConnector.getTreeNodeNames(new String[]{projPath}, itemType, path);
		if(amcNodeNames != null && amcNodeNames.length > 0){
			LFWExplorerTreeBuilder.getInstance().refreshAMCNodeSubTree(parent, projPath, amcNodeNames[0], path, itemType, fileName, project);
		}else{
			MessageDialog.openInformation(null, "刷新", "当前节点无子节点");
		}
	}
	
}
