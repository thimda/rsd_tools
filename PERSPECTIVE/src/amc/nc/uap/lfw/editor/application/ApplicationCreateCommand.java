/**
 * 
 */
package nc.uap.lfw.editor.application;

import java.util.List;

import nc.lfw.editor.pagemeta.LFWNodeTreeItem;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.editor.window.WindowObj;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.window.CreateWindowDialog;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * Application业务组件工具箱——创建Window命令处理类
 * @author chouhl
 *
 */
public class ApplicationCreateCommand extends Command {

	private ApplicationObj obj;
	
	private ApplicationGraph graph;
	
	private Rectangle rect;
	
	private String sourcePackage;

	public ApplicationCreateCommand(ApplicationObj obj, ApplicationGraph graph, Rectangle rect) {
		super();
		this.obj = obj;
		this.graph = graph;
		this.rect = rect;
		setLabel(WEBProjConstants.NEW_WINDOW);
	}

	public boolean canExecute() {
		return obj != null && graph != null && rect != null;
	}

	public void execute() {
		ApplicationObj applicationObj = (ApplicationObj) obj;
		CreateWindowDialog dialog = new CreateWindowDialog(WEBProjConstants.NEW_WINDOW);
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			applicationObj.setWindowId(dialog.getId());
			applicationObj.setWindowName(dialog.getName());
			applicationObj.setControllerClazz(dialog.getControllerClazz());
			sourcePackage = dialog.getSourcePackage();
		}else{
			return;
		}
		boolean isNotExist = true;
		List<TreeItem> treeItems = LFWAMCPersTool.getAllWindowTreeItems(graph.getProject(), graph.getCurrentTreeItem());
		for(TreeItem treeItem : treeItems){
			if(treeItem instanceof LFWBasicTreeItem){
				if(obj.getWindowId() != null && obj.getWindowId().equals(((LFWBasicTreeItem)treeItem).getId())){
					isNotExist = false;
					break;
				}
			}
		}
		if(isNotExist){
			LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
			if (view == null){
				return;
			}
			PageMeta pagemeta = LFWSaveElementTool.createNewWindowConf(obj.getWindowId(), obj.getWindowName(), obj.getControllerClazz(), sourcePackage);
			try {
				//保存Window节点到文件
				LFWSaveElementTool.createPagemeta(pagemeta, dialog.isFlowlayout());
				//刷新树
				LFWPageMetaTreeItem treeItem = (LFWPageMetaTreeItem) view.addWindowTreeNode(obj.getWindowId(), obj.getWindowName(), graph.getProject(), graph.getCurrentTreeItem());
				treeItem.setType(LFWNodeTreeItem.WINDOW);
				
				ApplicationEditor editor = ApplicationEditor.getActiveEditor();
				WindowObj windowObj = new WindowObj();
				windowObj.setWindow(pagemeta);
				editor.repaintWindowObj(windowObj);
				redo(windowObj);
				editor.setDirtyTrue();
			} catch (Exception e) {
				MainPlugin.getDefault().logError("Application业务组件工具箱——创建Window失败", e);
				MessageDialog.openError(null, WEBProjConstants.NEW_PAGEMETA, "创建Window失败");
			}
		}else{
			MessageDialog.openInformation(null, WEBProjConstants.NEW_PAGEMETA, "已存在ID为：" + obj.getWindowId() + " 的Window节点");
		}
	}
	
	public void redo(WindowObj obj) {
		graph.addWindowCell(obj);
		graph.getApplication().addWindow(obj.getWindow());
	}

}
