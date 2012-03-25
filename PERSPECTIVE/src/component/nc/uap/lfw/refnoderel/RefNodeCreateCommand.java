package nc.uap.lfw.refnoderel;

import java.util.List;

import nc.lfw.editor.common.LFWBasicElementObj;
import nc.lfw.editor.common.LfwBaseGraph;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.refnode.BaseRefNode;
import nc.uap.lfw.perspective.model.RefDSFromWidget;
import nc.uap.lfw.refnode.RefNodeElementObj;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 创建参照
 * @author zhangxya
 *
 */
public class RefNodeCreateCommand extends Command{
	
	private LFWBasicElementObj refnodeobj = null;
	private boolean canUndo = true;
	private LfwBaseGraph graph = null;
	private Rectangle rect = null;

	public RefNodeCreateCommand(LFWBasicElementObj refnodeobj,LfwBaseGraph graph, Rectangle rect) {
		super();
		this.refnodeobj = refnodeobj;
		this.graph = graph;
		this.rect = rect;
		setLabel("create new refnode");
	}

	
	public boolean canExecute() {
		return refnodeobj != null && graph != null && rect != null;
	}

	public void execute() {
		if(graph instanceof RefnoderelGraph && refnodeobj instanceof RefNodeElementObj){
			RefnoderelGraph graphnew = (RefnoderelGraph)graph;
			RefNodeElementObj refobjnew = (RefNodeElementObj)refnodeobj;
			//从本widget查找ds
			Shell shell = new Shell();
			RefnodeFromWidgetDialog dialog = new RefnodeFromWidgetDialog(shell, "引用参照对话框");
			int result = dialog.open();
			if (result == IDialogConstants.OK_ID) {
				if(dialog.getRefnode() instanceof BaseRefNode){
					BaseRefNode refnode = (BaseRefNode)dialog.getRefnode();
					if (refnode != null) {
						refobjnew.setRefnode(refnode);
						refobjnew.setId(refnode.getId());
					}
				}else{
					throw new LfwRuntimeException("类RefNodeCreateCommand转换BaseRefNode失败!");
				}
			}else if(result == IDialogConstants.CANCEL_ID){
				return;
			}
			RefNodeElementObj detailRefNode = graphnew.getDetailRefNode();
			if(detailRefNode != null){
				MessageDialog.openError(null, "错误提示", "一个参照关系只能有一个参照!");
				return;
			}
			else{
				refobjnew.setSize(new Dimension(120,120));
				refobjnew.setLocation(new Point(350, 200));
				redo();
			}
		}
		else{
			if(graph instanceof RefnoderelGraph && refnodeobj instanceof DatasetFieldElementObj){
				RefnoderelGraph graphnew = (RefnoderelGraph)graph;
				DatasetFieldElementObj refobjnew = (DatasetFieldElementObj)refnodeobj;
				//从本widget查找ds
				Shell shell = new Shell();
				RefDSFromWidget dialog = new RefDSFromWidget(shell, "引用数据集", false);
				int result = dialog.open();
				if (result == IDialogConstants.OK_ID) {
					Dataset ds = dialog.getSelectedDataset();
					refobjnew.setDsId(ds.getId());
					DsFieldSelDialog fieldDialog = new DsFieldSelDialog(null, "数据集字段选择", ds);
					int fresult = fieldDialog.open();
					if(fresult== IDialogConstants.OK_ID) {
						Field field = fieldDialog.getField();
						List<DatasetFieldElementObj> mainRefs = graphnew.getMainRefNodeList();
						for (int i = 0; i < mainRefs.size(); i++) {
							DatasetFieldElementObj mainRef = mainRefs.get(i);
							if((mainRef.getDsId().equals(ds.getId()) && (mainRef.getField().getId().equals(field.getId())))){
								MessageDialog.openError(null, "错误提示", "已经存在此字段,请重新选择新字段!");
								return;
							}
						}
						int pointx = 100;
						int pointy = 100;
						if(field != null ){
							int mainsize = graphnew.getMainRefNodeList().size();
							if(mainsize == 0){
								pointy = 100;
							}else {
								DatasetFieldElementObj lastRef = graphnew.getMainRefNodeList().get(mainsize -1);
								pointy = lastRef.getLocation().y + lastRef.getSize().height + WEBProjConstants.FIELD_BETWEEN;
							}
							refobjnew.setField(field);
							refobjnew.setId(field.getId());
							refobjnew.setSize(new Dimension(120,120));
							refobjnew.setLocation(new Point(pointx, pointy));
							redo();
						}
					}
					else 
						return;
				}else if(result == IDialogConstants.CANCEL_ID){
					return;
				}
			}
		}
	}
	public void redo() {
		if(graph instanceof RefnoderelGraph && refnodeobj instanceof RefNodeElementObj){
			RefnoderelGraph graphnew = (RefnoderelGraph)graph;
			RefNodeElementObj refdsobjnew = (RefNodeElementObj)refnodeobj;
			graphnew.setDetailRefNode(refdsobjnew);
		}
		else if(graph instanceof RefnoderelGraph && refnodeobj instanceof DatasetFieldElementObj){
			RefnoderelGraph graphnew = (RefnoderelGraph)graph;
			DatasetFieldElementObj refobjnew = (DatasetFieldElementObj)refnodeobj;
			graphnew.addMainRefNode(refobjnew);
		}
	}

	
	public void undo() {
		if(graph instanceof RefnoderelGraph && refnodeobj instanceof RefNodeElementObj){
			RefnoderelGraph graphnew = (RefnoderelGraph)graph;
			//RefNodeElementObj refnodeobjnew = (RefNodeElementObj)refnodeobj;
			graphnew.setDetailRefNode(null);
		}
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