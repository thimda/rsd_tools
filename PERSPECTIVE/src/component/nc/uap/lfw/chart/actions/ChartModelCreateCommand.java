package nc.uap.lfw.chart.actions;

import java.util.List;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.chart.core.ChartCompEleObj;
import nc.uap.lfw.chart.core.ChartGraph;
import nc.uap.lfw.chart.model.Bar2DChartModelEleObj;
import nc.uap.lfw.chart.model.Bar3DChartModelEleObj;
import nc.uap.lfw.chart.model.BaseChartModelEleObj;
import nc.uap.lfw.core.comp.Bar2DChartModel;
import nc.uap.lfw.core.comp.Bar3DChartModel;
import nc.uap.lfw.core.comp.BaseChartModel;
import nc.uap.lfw.core.comp.ChartComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.perspective.model.RefDSFromWidget;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ChartModelCreateCommand extends Command{
	private BaseChartModelEleObj chartmodelobj = null;
	private boolean canUndo = true;
	private ChartGraph graph = null;

	private Rectangle rect = null;

	public ChartModelCreateCommand(BaseChartModelEleObj chartmodelobj, ChartGraph graph, Rectangle rect) {
		super();
		this.chartmodelobj = chartmodelobj;
		this.graph = graph;
		this.rect = rect;
		setLabel("create new chartmodel");
	}

	
	public boolean canExecute() {
		return chartmodelobj != null && graph != null && rect != null;
	}
	

	public void execute() {
		List<LfwElementObjWithGraph> cells = graph.getCells();
		if(cells.size() >= 2){
			MessageDialog.openConfirm(null, "��ʾ", "�Ѿ�����һ��ChartModel��һ��ͼ��ֻ�ܺ�һ��ChartModel��!");
			return;
		}
		//�ӱ�widget����
		Shell shell = new Shell();
		RefDSFromWidget dialog = new RefDSFromWidget(shell,"�������ݼ�", false);
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			Dataset ds = dialog.getSelectedDataset();
			if (ds != null) {
				BaseChartModel barModel = null;
				if(chartmodelobj instanceof Bar2DChartModelEleObj)
					barModel = new Bar2DChartModel();
				else if(chartmodelobj instanceof Bar3DChartModelEleObj)
					barModel = new Bar3DChartModel();
				barModel.setId(ds.getId());
				barModel.setDataset(ds.getId());
				chartmodelobj.setBasebarChartModel(barModel);
				chartmodelobj.setId(ds.getId());
				
				ChartCompEleObj chartobj = (ChartCompEleObj) this.graph.getCells().get(0);
				ChartComp chartComp = chartobj.getChartComp();
				//chartComp.setCharModel(barModel);
			}
		}
		else if (result == IDialogConstants.CANCEL_ID)
			return;
		chartmodelobj.setLocation(new Point(350, 100));
		redo();
	}

	
	public void redo() {
		graph.addCell(chartmodelobj);
	}

	
	public void undo() {
		graph.removeCell(chartmodelobj);
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
