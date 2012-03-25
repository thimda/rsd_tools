package nc.uap.lfw.perspective.action;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.common.CompIdGenerator;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.MatchField;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.listener.FileUtil;
import nc.uap.lfw.perspective.model.DatasetElementObj;
import nc.uap.lfw.perspective.webcomponent.LFWRefNodeTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;
import nc.uap.lfw.refnode.core.RefNodesFromPoolDialog;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 * ����FieldRelation ���ɲ���
 * @author zhangxya
 *
 */
public class GeneratorRefNodeAction extends Action {
	
	private DatasetElementObj dsobj;
	private Dataset refds;
	public GeneratorRefNodeAction(DatasetElementObj dsobj, Dataset refds) {
		super("���ɲ���", PaletteImage.getCreateDsImgDescriptor());
		this.dsobj = dsobj;
		this.refds = refds;
	}
	
	public void run() {
		
		RefNodesFromPoolDialog pubDialog = new RefNodesFromPoolDialog(null, "ѡ�񹫹�����");
		LFWSeparateTreeItem lfwSeparaTreeItem = DataSetEditor.getActiveEditor().getWebSeparateTreeItem(WEBProjConstants.REFNODE);
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		ViewModels viewModels = widget.getViewModels();
		
		if(pubDialog.open() == IDialogConstants.OK_ID){
			Dataset ds = dsobj.getDs();
			if(pubDialog.getSelectedRefNode() instanceof RefNode){
				RefNode pubRefNode = (RefNode)pubDialog.getSelectedRefNode();
				FieldRelation fieldRelation = null;
				FieldRelation[] frs = ds.getFieldRelations().getFieldRelations();
				for (int i = 0; i < frs.length; i++) {
					FieldRelation fr = frs[i];
					if(fr.getRefDatasetid().equals(refds.getId())){
						fieldRelation = fr;
						break;
					}
				}
				String refNodeId = CompIdGenerator.generateRefCompId(ds.getId(), fieldRelation.getWhereField().getValue());
				if(viewModels.getRefNode(refNodeId) != null){
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "��ʾ", "����:" + refNodeId + "�Ѿ�����");
					return;
				}
				
				RefNode refnode = new RefNode();
				refnode.setId(refNodeId);
				refnode.setRefId(pubRefNode.getId());
				
				MatchField[] matchs = fieldRelation.getMatchFields();
				String readFields = "";
				String writeFields = "";
				int size = matchs.length;
				for (int j = 0; j < size; j++) {
					MatchField match = matchs[j];
					String readField = match.getReadField();
					String writeField = match.getWriteField();
					if(j != size - 1){
						readFields += readField + ",";
						writeFields += writeField;
					}
					else{
						readFields += readField;
						writeFields += writeField;
					}
				}
				refnode.setWriteFields(writeFields);
				refnode.setReadFields(readFields);
//				refnode.setReadDs("masterDs");
				refnode.setWriteDs(ds.getId());
				viewModels.addRefNode(refnode);
				new LFWRefNodeTreeItem(lfwSeparaTreeItem, refnode, WEBProjConstants.COMPONENT_REFNODE);
				LFWPersTool.saveWidget(widget);
			}else{
				throw new LfwRuntimeException("��GeneratorRefNodeActionת��RefNodeʧ��!");
			}
		}
		else if(pubDialog.isCreateNewRef()){
			Dataset ds = dsobj.getDs();
			RefTypeSelDialog dialog = new RefTypeSelDialog(null, "����������", refds);
			if(dialog.open() == IDialogConstants.OK_ID){
				String refType = dialog.getRefType();
				//��������
				createClassForRefNode(dialog);
				
				FieldRelation fieldRelation = null;
				FieldRelation[] frs = ds.getFieldRelations().getFieldRelations();
				for (int i = 0; i < frs.length; i++) {
					FieldRelation fr = frs[i];
					if(fr.getRefDatasetid().equals(refds.getId())){
						fieldRelation = fr;
						break;
					}
				}
				if(fieldRelation != null){
					String refKey = fieldRelation.getWhereField().getValue();
					String refNodeId = CompIdGenerator.generateRefCompId(ds.getId(), refKey);
					
					RefNode refnode = new RefNode();
					refnode.setId(refNodeId);
					boolean toPublic = dialog.isToPublic();
					if(toPublic){
						String refId = generatePublicRefNode(dialog);
						if(refId == null || refId.equals("")){
							MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "��ʾ", "���������Ѿ����ڣ���ֱ��ѡ�񹫹�����");
							return;
						}
						refnode.setRefId(refId);
					}
					else{
						refnode.setRefModel(dialog.getModelClass());
						refnode.setPagemeta("reference");
						refnode.setReadDs("masterDs");
						
						if(refType.equals(RefTypeSelDialog.GRIDTYPE))
							refnode.setPath("reference/refgrid.jsp");
						else if(refType.equals(RefTypeSelDialog.TREETYPE))
							refnode.setPath("reference/reftree.jsp");
					}
					if(viewModels.getRefNode(refNodeId) != null)
						return;
				
					MatchField[] matchs = fieldRelation.getMatchFields();
					String readFields = "";
					String writeFields = "";
					int size = matchs.length;
					for (int j = 0; j < size; j++) {
						MatchField match = matchs[j];
						String readField = match.getReadField();
						String writeField = match.getWriteField();
						if(j != size - 1){
							readFields += readField + ",";
							writeFields += writeField;
						}
						else{
							readFields += readField;
							writeFields += writeField;
						}
					}
					refnode.setWriteFields(writeFields);
					refnode.setReadFields(readFields);
					refnode.setWriteDs(ds.getId());
					viewModels.addRefNode(refnode);
					new LFWRefNodeTreeItem(lfwSeparaTreeItem, refnode, WEBProjConstants.COMPONENT_REFNODE);
					LFWPersTool.saveWidget(widget);
				}
			}
		}
	}

	private String generatePublicRefNode(RefTypeSelDialog dialog) {
		RefNode refnode = new RefNode();
		refnode.setId(dialog.getPubRefId());
		refnode.setPagemeta("reference");
		//�ж��������
		if(false){
			boolean result = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "ȷ��", "���������Ѿ����ڣ��Ƿ�Ҫ����?");
			if(result == false)
				return null;
		}
		refnode.setRefModel(dialog.getModelClass());
		refnode.setReadDs("masterDs");
		refnode.setPageModel("nc.uap.lfw.reference.model.DefaultReferencePageModel");
		refnode.setDataListener("nc.uap.lfw.reference.processor.ReferenceDatasetListener");
		String refType = dialog.getRefType();
		if(refType.equals(RefTypeSelDialog.GRIDTYPE))
			refnode.setPath("reference/refgrid.jsp");
		else if(refType.equals(RefTypeSelDialog.TREETYPE))
			refnode.setPath("reference/reftree.jsp");
		
		String projectPath = LFWPersTool.getProjectPath();
		String pubRefId = dialog.getPubRefId();
		String dir = pubRefId.substring(0, pubRefId.lastIndexOf(".")).replaceAll("\\.", "/");
		String file = pubRefId.substring(pubRefId.lastIndexOf(".") + 1);
		String filePath = projectPath + "/web/pagemeta/public/refnodes/" + dir;
		
		String fileName = file + ".xml";
		
		String rootPath = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
		LFWConnector.saveRefNodetoXml("/" + rootPath, filePath, fileName, refnode);
		//refreshTreeItem(refnode);
		return pubRefId;
	}

	private void createClassForRefNode(RefTypeSelDialog dialog) {
		String refType = dialog.getRefType();
		String modelClass = dialog.getModelClass();
		String tableName = dialog.getTableName();
		String refCode = dialog.getRefCode();
		String refPk = dialog.getRefPk();
		String refName = dialog.getRefName();
		String visibleFields = dialog.getVisibleFields();
		String pfield = dialog.getPFieldValue();
		String childfield = dialog.getChildFieldValue();
		String sourceFolder = dialog.getRootPath();
		
		// ����Java�ļ�
		String javaData = LFWConnector.generateRefNodeClass(refType, modelClass, tableName, refPk, refCode, refName, visibleFields, pfield, childfield);
		String packageName = modelClass.substring(0, modelClass.lastIndexOf("."));
		String className = modelClass.substring(modelClass.lastIndexOf(".") + 1);
		String folderPath = LFWPersTool.getProjectPath() + "/" +  sourceFolder + "/" + packageName.replace('.', '/');
		FileUtil.saveToFile(folderPath, className + ".java", javaData);
	}
}