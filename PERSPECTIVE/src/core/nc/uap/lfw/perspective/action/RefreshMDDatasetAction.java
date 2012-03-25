package nc.uap.lfw.perspective.action;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.FieldRelations;
import nc.uap.lfw.core.data.MdDataset;
import nc.uap.lfw.core.data.RefMdDataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.editor.DataSetEditor;

import org.eclipse.jface.action.Action;

/**
 * ����refnode
 * @author zhangxya
 *
 */
public class RefreshMDDatasetAction  extends Action {
	
	private MdDataset mdds;
	private LfwWidget widget = null;
	public RefreshMDDatasetAction(MdDataset mdds) {
		super("��������RefNode", PaletteImage.getCreateDsImgDescriptor());
		setText("��������RefNode");
		setToolTipText("��������RefNode");
		this.mdds = mdds;
	}

	
	public void run() {
		List<RefNode> refnodeList = LFWConnector.getAllNCRefNode(mdds);
		widget = LFWPersTool.getCurrentWidget();
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
	
	private void putAllRefDs(MdDataset mdds){
		FieldRelations oldFieldRelations = mdds.getFieldRelations();
		//��ȡ���е�Fieldrelation
		List<FieldRelation> fieldRelations = LFWConnector.getNCFieldRelations(mdds);
		List<String> newfrIds = new ArrayList<String>();
		for (int i = 0; i < fieldRelations.size(); i++) {
			String frId = fieldRelations.get(i).getId();
			newfrIds.add(frId);
		}
		if(oldFieldRelations != null){
			if(oldFieldRelations.getFieldRelations() != null && oldFieldRelations.getFieldRelations().length > 0){
				FieldRelation[] oldFieldRelation = oldFieldRelations.getFieldRelations();
				//����µ�FieldRelation�в������˾ɵ�FieldRelations�е�Id,��ԭ����ɾ��
				for (int j = 0; j < oldFieldRelation.length; j++) {
					String oldfrId = oldFieldRelation[j].getId();
					if(!newfrIds.contains(oldfrId))
						oldFieldRelations.removeFieldRelation(oldfrId);
				}
				for (int i = 0; i < fieldRelations.size(); i++) {
					FieldRelation fr = fieldRelations.get(i);
					String frId = fr.getId();
					//ԭ���Ѵ��ڴ�FieldRelation
					if(oldFieldRelations.getFieldRelation(frId) != null){
						continue;
					}
					//�¼ӵ�FileldRelation,ֱ�Ӽ���
					else{
						oldFieldRelations.addFieldRelation(fr);
					}
				}
			}
		}
		//���ԭ��û��FieldRelation,��FieldRelationֱ�Ӽ���
		else{
			for (int i = 0; i < fieldRelations.size(); i++) {
				FieldRelation fr = (FieldRelation) fieldRelations.get(i);
				mdds.getFieldRelations().addFieldRelation(fr);
			}
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
			}
			//�õ�ds�༭��
			DataSetEditor dsEditor = DataSetEditor.getActiveEditor();
			//���µ���Listenerλ��
			if(dsEditor != null)
				dsEditor.repaintListenerPositon();
		}
	}

}
