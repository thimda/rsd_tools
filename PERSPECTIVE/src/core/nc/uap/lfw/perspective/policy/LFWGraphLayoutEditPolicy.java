package nc.uap.lfw.perspective.policy;

import nc.lfw.editor.common.LfwBaseGraph;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.contextmenubar.ContextMenuElementObj;
import nc.lfw.editor.contextmenubar.ContextMenuGrahp;
import nc.lfw.editor.contextmenubar.actions.ContextMenuCreateCommand;
import nc.lfw.editor.contextmenubar.actions.CreateContextMenuCommand;
import nc.lfw.editor.menubar.MenubarGraph;
import nc.lfw.editor.menubar.command.CreateItemCommand;
import nc.lfw.editor.menubar.command.CreateMenuCommand;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenuItemObj;
import nc.lfw.editor.pagemeta.PagemetaGraph;
import nc.lfw.editor.pagemeta.WidgetCreateCommand;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.editor.view.ViewAddCommand;
import nc.uap.lfw.grid.GridGraph;
import nc.uap.lfw.grid.commands.GridLevelCreateCommand;
import nc.uap.lfw.grid.core.GridLevelElementObj;
import nc.uap.lfw.perspective.commands.RefDatasetCreateCommand;
import nc.uap.lfw.perspective.model.RefDatasetElementObj;
import nc.uap.lfw.refnode.RefNodeElementObj;
import nc.uap.lfw.refnoderel.DatasetFieldElementObj;
import nc.uap.lfw.refnoderel.RefNodeCreateCommand;
import nc.uap.lfw.tree.TreeGraph;
import nc.uap.lfw.tree.commands.TreeLevelCreateCommand;
import nc.uap.lfw.tree.core.TreeLevelElementObj;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

/**
 * �ײ㻭��edit Policy
 * 
 * @author zhangxya
 * 
 */
public class LFWGraphLayoutEditPolicy extends XYLayoutEditPolicy {

	
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart editPart, Object constraint) {
		return super.createChangeConstraintCommand(request, editPart,
				constraint);
	}

	
	protected Command createChangeConstraintCommand(EditPart arg0, Object arg1) {
		return null;
	}

	protected Command getCreateCommand(CreateRequest request) {
		if(!(getHost().getModel() instanceof LfwBaseGraph)){
			return null;
		}
		Object obj = request.getNewObject();
		if(TreeLevelElementObj.class.isInstance(obj)){//�½�dataset
			TreeLevelElementObj treeLevelObj = (TreeLevelElementObj)obj;
			TreeGraph treeGraph = (TreeGraph)getHost().getModel();
			return new TreeLevelCreateCommand(treeLevelObj, treeGraph, (Rectangle)getConstraintFor(request));
		}else if(ContextMenuElementObj.class.isInstance(obj)){
			ContextMenuElementObj contextMenuObj = (ContextMenuElementObj)obj;
			LfwBaseGraph baseGraph = (LfwBaseGraph)getHost().getModel();
			return new ContextMenuCreateCommand(contextMenuObj, baseGraph, (Rectangle)getConstraintFor(request));
		}else if(RefNodeElementObj.class.isInstance(obj) || DatasetFieldElementObj.class.isInstance(obj)){
			LfwElementObjWithGraph elementObj = (LfwElementObjWithGraph)obj;
			LfwBaseGraph baseGraph = (LfwBaseGraph)getHost().getModel();
			return new RefNodeCreateCommand(elementObj, baseGraph, (Rectangle) getConstraintFor(request));
		}else if (RefDatasetElementObj.class.isInstance(obj)) { // �½�RefDatasetͼ��
			RefDatasetElementObj elementObj = (RefDatasetElementObj)obj;
			LfwBaseGraph graph = (LfwBaseGraph) getHost().getModel();
			return new RefDatasetCreateCommand(elementObj, graph, (Rectangle) getConstraintFor(request));
		}else if (WidgetElementObj.class.isInstance(obj)) { // �½�Widgetͼ��
			WidgetElementObj elementObj = (WidgetElementObj)obj;
			PagemetaGraph graph = (PagemetaGraph)getHost().getModel();
			if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
				return new ViewAddCommand(elementObj, graph, (Rectangle) getConstraintFor(request));		
			}else{
				return new WidgetCreateCommand(elementObj, graph, (Rectangle) getConstraintFor(request));
			}
		}else if (MenuElementObj.class.isInstance(obj)) {
			if(getHost().getModel() instanceof ContextMenuGrahp){
				ContextMenuGrahp graph = (ContextMenuGrahp) getHost().getModel();
				MenuElementObj menuObj = (MenuElementObj)obj;
				return new CreateContextMenuCommand(menuObj, graph, (Rectangle) getConstraintFor(request));
			}
			else{
				MenubarGraph graph = (MenubarGraph) getHost().getModel();
				MenuElementObj menuObj = (MenuElementObj) obj;
				return new CreateMenuCommand(menuObj, graph, (Rectangle) getConstraintFor(request));
			}
		}else if (MenuItemObj.class.isInstance(obj)) {
			MenubarGraph graph = (MenubarGraph) getHost().getModel();
			MenuItemObj menuObj = (MenuItemObj) obj;
			return new CreateItemCommand(menuObj, graph, (Rectangle) getConstraintFor(request));
		}else if(GridLevelElementObj.class.isInstance(obj)){
			GridLevelElementObj gridLevelObj = (GridLevelElementObj)obj;
			GridGraph gridGraph = (GridGraph)getHost().getModel();
			return new GridLevelCreateCommand(gridLevelObj, gridGraph, (Rectangle)getConstraintFor(request));
		}
//		else if (PfStartElementObj.class.isInstance(obj)) { // �½�PfStartͼ��
//			PageFlowGraph graph = (PageFlowGraph) getHost().getModel();
//			PfStartElementObj startObj = (PfStartElementObj) obj;
//			startObj.setId("start");
//			return new PfStartCreateCommand(startObj, graph, request.getLocation(),
//					(Rectangle) getConstraintFor(request));
//		}
//		else if (PfPageElementObj.class.isInstance(obj)) { // �½�PfPageͼ��
//			PageFlowGraph graph = (PageFlowGraph) getHost().getModel();
//			PfPageElementObj pageObj = (PfPageElementObj) obj;
//			Shell shell = new Shell();
//			CreatePfPageDialog dialog = new CreatePfPageDialog(shell);
//			dialog.setGraph(graph);
//			
//			int result = dialog.open();
//			if (result == IDialogConstants.OK_ID) {
//				pageObj.setId(dialog.getName());
//				pageObj.setViewId(dialog.getViewId());
//				return new PfPageCreateCommand(pageObj, graph, request.getLocation(),
//						(Rectangle) getConstraintFor(request));
//			}
//		}
//		else if (PfDecisionElementObj.class.isInstance(obj)) { // �½�PfDecisionͼ��
//			PageFlowGraph graph = (PageFlowGraph) getHost().getModel();
//			PfDecisionElementObj decisionObj = (PfDecisionElementObj) obj;
//			Shell shell = new Shell();
//			CreatePfDecisionDialog dialog = new CreatePfDecisionDialog(shell);
//			dialog.setGraph(graph);
//			
//			int result = dialog.open();
//			if (result == IDialogConstants.OK_ID) {
//				decisionObj.setId(dialog.getName());
//				decisionObj.setExpression(dialog.getExpression());
//				return new PfDecisionCreateCommand(decisionObj, graph, request.getLocation(),
//						(Rectangle) getConstraintFor(request));
//			}
//		}
		return null;
	}

}
