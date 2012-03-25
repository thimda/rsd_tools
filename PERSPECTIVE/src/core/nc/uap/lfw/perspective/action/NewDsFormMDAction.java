package nc.uap.lfw.perspective.action;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.Connection;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.MdDataset;
import nc.uap.lfw.core.data.RefMdDataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.dataset.MDDatsetDialog;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.model.DatasetElementObj;
import nc.uap.lfw.perspective.model.DatasetGraph;
import nc.uap.lfw.perspective.model.RefDatasetElementObj;
import nc.uap.lfw.perspective.views.CellPropertiesView;
import nc.uap.lfw.perspective.views.LFWViewPage;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;


/**
 * ��Ԫ����������ds
 * 
 * @author zhangxya
 *
 */
public class NewDsFormMDAction extends Action{
	
	private DatasetElementObj dsobj = null;
	private LfwWidget widget = null;
	
	private class AddDSCommand extends Command{
		public AddDSCommand(Dataset ds){
			super("��������");
		}
		
		public void execute() {
			redo();
		}

		public void redo() {
		}
		
		public void undo() {
		}
	}
	
	public NewDsFormMDAction(DatasetElementObj dsobj) {
		super("��Ԫ�����������ݼ�",PaletteImage.getCreateDsImgDescriptor());
		this.dsobj = dsobj;
	}

	/**
	 * ���ԭ������Ϣ
	 */
	private void clearOriginalData(){
		Dataset oringinalDs = dsobj.getDs();
		//���ԭ����refnode
		if(widget == null)
			widget = LFWPersTool.getCurrentWidget();
		//���ԭ����FieldRelation��ͼ��
		if(widget != null){
			FieldRelation[] frs = oringinalDs.getFieldRelations().getFieldRelations();
			if(frs != null && frs.length > 0){
				for (int i = 0; i < frs.length; i++) {
					FieldRelation fr = frs[i];
					dsobj.deleteFieldRelation(fr);
				}
				List<RefDatasetElementObj> refdss = dsobj.getCells();
					if(refdss != null && refdss.size() > 0){
					int size = refdss.size();
					DatasetGraph dsgraph = (DatasetGraph) dsobj.getGraph();
					for (int i = 0; i < size; i++) {
						RefDatasetElementObj refds = refdss.get(i);
						if(dsgraph.removeRefDs(refds)){
							//dsobj.removeCell(refdss.get(i));
						}
					}
				}
				List<Connection> conList = dsobj.getGraph().getConnections();
				if(conList != null && conList.size() > 0){
					for (int i = 0; i < conList.size(); i++) {
						Connection con = conList.get(i);
						con.disConnect();
					}
				}
			}
			IRefNode[] orginalRefNodes = widget.getViewModels().getRefNodes();
			if(orginalRefNodes != null && orginalRefNodes.length > 0){
				for (int i = 0; i < orginalRefNodes.length; i++) {
					IRefNode refnode = orginalRefNodes[i];
					if(refnode.getId().startsWith("refNode_" + oringinalDs.getId()))
						widget.getViewModels().removeRefNode(refnode.getId());
				}
			}
			//���ԭ����combo
			ComboData[] comboDatas = widget.getViewModels().getComboDatas();
			if(comboDatas != null && comboDatas.length > 0){
				for (int i = 0; i < comboDatas.length; i++) {
					ComboData com = comboDatas[i];
					if(com.getId().startsWith("comboComp_" + oringinalDs.getId()))
						widget.getViewModels().removeComboData(com.getId());
				}
			}
		}
	}
	
	public void run() {
		Shell shell = new Shell();
		MDDatsetDialog dialog = new MDDatsetDialog(shell, "��Ԫ�����������ݼ�");
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID){
			//��ԭ��ds�е�����fieldRelation������refdsɾ��
			clearOriginalData();
			String originaldsId = dsobj.getDs().getId();
			Dataset refds = dialog.getDataset();
			MdDataset mdds  = (MdDataset)refds;
			mdds.setId(originaldsId);
			dsobj.setDs(mdds);
			Field[] pubfields = (Field[])mdds.getFieldSet().getFields();
			LFWViewPage lfwviewpage = LFWPersTool.getLFWViewPage();
			TreeViewer tv = ((CellPropertiesView) lfwviewpage.getCellPropertiesView()).getTv();
			if(pubfields == null){
				MessageDialog.openError(shell, "������ʾ", "��Ԫ����û���ֶ�!");
				return;
			}
			tv.refresh();
			tv.expandAll();
			boolean flag = MessageDialog.openConfirm(null, "��ʾ", "�Ƿ��������ݼ���������������?");
			if(flag){
				dsobj.setExtendFlag(true);
				//�õ����в���ds
				putAllRefDs(mdds);
				//�õ����е�refNode
				putAllRefNodeDs(mdds);
				//�õ����е�combodata
				putAllComboData(mdds);
			}
			AddDSCommand cmd = new AddDSCommand(refds);
			if(DataSetEditor.getActiveEditor() != null)
			DataSetEditor.getActiveEditor().executComand(cmd);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void putAllRefNodeDs(MdDataset mdds){
		List<RefNode> refnodeList = LFWConnector.getAllNCRefNode(mdds);
		ViewModels viewModels = widget.getViewModels();
		for (int i = 0; i < refnodeList.size(); i++) {
			RefNode refNode = (RefNode) refnodeList.get(i);
			IRefNode refNodeold = viewModels.getRefNode(refNode.getId());
			if(refNodeold != null){
				viewModels.removeRefNode(refNodeold.getId());
			}
			viewModels.addRefNode(refNode);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void putAllComboData(MdDataset mdds){
		List<ComboData> combodataList = LFWConnector.getAllNcComboData(mdds);
		ViewModels viewModels = widget.getViewModels();
		for (int i = 0; i < combodataList.size(); i++) {
			ComboData combo = (ComboData) combodataList.get(i);
			ComboData comboold = viewModels.getComboData(combo.getId());
			if(comboold != null){
				viewModels.removeComboData(combo.getId());
			}
			viewModels.addComboData(combo);
		}
	}
	
	
	//�õ����е�refds
	@SuppressWarnings("unchecked")
	private void putAllRefDs(MdDataset mdds){
		//��ȡ���е�Fieldrelation
		List<FieldRelation> fieldRelations = LFWConnector.getNCFieldRelations(mdds);
		for (int i = 0; i < fieldRelations.size(); i++) {
			FieldRelation fr = (FieldRelation) fieldRelations.get(i);
			mdds.getFieldRelations().addFieldRelation(fr);
		}
		List refdsList = LFWConnector.getNCRefMdDataset(mdds);
		widget = LFWPersTool.getCurrentWidget();
		if(widget != null){
			ViewModels viewModels = widget.getViewModels();
			if(refdsList != null && refdsList.size() > 0){
				for (int i = 0; i < refdsList.size(); i++) {
					RefMdDataset refmdDs = (RefMdDataset) refdsList.get(i);
					RefMdDataset refmdDsold = (RefMdDataset) viewModels.getDataset(refmdDs.getId());
					if(refmdDsold != null)
						viewModels.removeDataset(refmdDs.getId());
					viewModels.addDataset(refmdDs);
				}
				
				int  parenty = 0;
				//�������е�refds
				for (int i = 0; i < refdsList.size(); i++) {
					RefDatasetElementObj refdsobj = new RefDatasetElementObj();
					Dataset ref = (Dataset)refdsList.get(i);
					refdsobj.setDs(ref);
					int pointy = 0;
					refdsobj.setSize(new Dimension(100,100));
					if(i == 0)
						pointy = 0;
					else 
						pointy = parenty + 100 + WEBProjConstants.BETWEEN;
					parenty = pointy;
					Point point = new Point(400, pointy);
					refdsobj.setLocation(point);
					if(dsobj.getGraph() instanceof DatasetGraph){
						DatasetGraph dsgraph = (DatasetGraph) dsobj.getGraph();
						dsgraph.addRefDs(refdsobj);
						dsobj.setGraph(dsgraph);
					}
					dsobj.addRefDataset(refdsobj);
					Connection con = new Connection(dsobj, refdsobj);
					con.connect();
					dsobj.getGraph().addConns(con);
					
				}
			}
			
			//�������е�FieldRelation
			FieldRelation[] frs = mdds.getFieldRelations().getFieldRelations();
			if(frs != null && frs.length > 0){
				List<RefDatasetElementObj> refdss = dsobj.getCells();
				int size = refdss.size();
				List<RefDatasetElementObj> copyrefds = new ArrayList<RefDatasetElementObj>();
				for (int i = 0; i < refdss.size(); i++) {
					copyrefds.add(refdss.get(i));
				}
				if(frs != null){
					for (int j = 0; j < frs.length; j++) {
						FieldRelation frr = frs[j];
						dsobj.addFieldRelation(frr);
						size = refdss.size();
						for (int i = 0; i < size; i++) {
							RefDatasetElementObj refds = (RefDatasetElementObj) refdss.get(i);
							if(frr.getRefDataset().equals(refds.getDs().getId())){
								refds.addFieldRelation(frr);
								refdss.remove(refds);
								break;
							}
						}
					}
				}
			}
			//�õ�ds�༭��
			DataSetEditor dsEditor = DataSetEditor.getActiveEditor();
			//���µ���Listenerλ��
			if(dsEditor != null)
				dsEditor.repaintListenerPositon();
		}
	}
}
