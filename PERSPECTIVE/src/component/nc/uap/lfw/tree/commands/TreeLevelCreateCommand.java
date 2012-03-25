package nc.uap.lfw.tree.commands;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.perspective.model.RefDSFromWidget;
import nc.uap.lfw.tree.TreeGraph;
import nc.uap.lfw.tree.core.TreeLevelElementObj;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;

/**
 * TreeLevel 创建命令
 * @author zhangxya
 *
 */
public class TreeLevelCreateCommand extends Command{
	private TreeLevelElementObj treelevelobj = null;
	private boolean canUndo = true;
	private TreeGraph graph = null;

	private Rectangle rect = null;

	public TreeLevelCreateCommand(TreeLevelElementObj treelevelobj,TreeGraph graph, Rectangle rect) {
		super();
		this.treelevelobj = treelevelobj;
		this.graph = graph;
		this.rect = rect;
		setLabel("create new refds");
	}

	
	public boolean canExecute() {
		return treelevelobj != null && graph != null && rect != null;
	}

	public void execute() {
		int size = graph.getAllCellsExceptListener().size();
		//从本widget查找ds
		Shell shell = new Shell();
		RefDSFromWidget dialog = new RefDSFromWidget(shell,"引用数据集", false);
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			Dataset ds = dialog.getSelectedDataset();
			if (ds != null) {
				treelevelobj.setId(ds.getId());
				treelevelobj.setDs(ds);
				//treelevelobj.setId(ds.getId());
			}
		}
		else if (result == IDialogConstants.CANCEL_ID)
			return;
		
		int x,y;
		if(size == 1){
			x = 350;
			y = 100;
		}else{
			TreeLevelElementObj lastrefds = (TreeLevelElementObj)graph.getAllCellsExceptListener().get(size - 1);
			x = lastrefds.getLocation().x + lastrefds.getSize().width + WEBProjConstants.BETWEEN;
			y = lastrefds.getLocation().y;
		}
		treelevelobj.setLocation(new Point(x, y));
		redo();
	}

	
	public void redo() {
		graph.addCell(treelevelobj);
	}

	
	public void undo() {
		graph.removeCell(treelevelobj);
	}

	public boolean isCanUndo() {
		return canUndo;
	}

	public void setCanUndo(boolean canUndo) {
		this.canUndo = canUndo;
	}

	
	public boolean canUndo() {
		return isCanUndo();
	}

}
