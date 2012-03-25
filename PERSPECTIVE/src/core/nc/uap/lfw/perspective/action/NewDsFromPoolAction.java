package nc.uap.lfw.perspective.action;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.PubDataset;
import nc.uap.lfw.core.data.PubField;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.model.DatasetElementObj;
import nc.uap.lfw.perspective.model.DatasetsFromPoolDialog;
import nc.uap.lfw.perspective.views.CellPropertiesView;
import nc.uap.lfw.perspective.views.LFWViewPage;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;

/**
 * 从公共池中引用ds
 * @author zhangxya
 *
 */
public class NewDsFromPoolAction extends Action{
	
	private DatasetElementObj dsobj = null;
	
	private List<PubField> pubfieldslist = null;
	
	private class AddDSCommand extends Command{
		public AddDSCommand(Dataset ds){
			super("增加属性");
		}
		
		public void execute() {
			redo();
		}

		
		public void redo() {
			}
		
		public void undo() {
		}
		
	}

	
	
	public NewDsFromPoolAction(DatasetElementObj dsobj) {
		super("从公共池中引用数据集",PaletteImage.getCreateDsImgDescriptor());
		this.dsobj = dsobj;
	}
	
	
	public void run() {
		Shell shell = new Shell();
		DatasetsFromPoolDialog dialog = new DatasetsFromPoolDialog(shell, "从公共池中引用数据集");
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID){
			Dataset refds = dialog.getSelectedDataset();
			//PubDataset pubds = (PubDataset)refds;
			if (refds != null ) {
				pubfieldslist = new ArrayList<PubField>();
				PubDataset pubds = new PubDataset();
				pubds.setId(dsobj.getDs().getId());
				pubds.setRefId(refds.getId());
				pubds.setVoMeta(refds.getVoMeta());
				dsobj.setDs(pubds);
				Field[] fields = refds.getFieldSet().getFields();
				for (int i = 0; i < fields.length; i++) {
					PubField pubfield = new PubField();
					pubfield.setId(fields[i].getId());
					pubfield.setField(fields[i].getField());
					pubfield.setLangDir(fields[i].getLangDir());
					pubfield.setI18nName(fields[i].getI18nName());
					pubfield.setText(fields[i].getText());
					pubfield.setNullAble(fields[i].isNullAble());
					pubfield.setDataType(fields[i].getDataType());
					pubfield.setDefaultValue(fields[i].getDefaultValue());
					pubfield.setPrimaryKey(fields[i].isPrimaryKey());
					pubfield.setEditFormular(fields[i].getEditFormular());
					pubfield.setLoadFormular(fields[i].getLoadFormular());
					pubfield.setIdColName(fields[i].getIdColName());
					pubfield.setLock(fields[i].isLock());
					pubfield.setFormularTabCode(fields[i].getFormularTabCode());
					pubfield.setDefEditFormular(fields[i].getDefEditFormular());
					pubfield.setValidateFormula(fields[i].getValidateFormula());
					pubfield.setFormater(fields[i].getFormater());
					pubfield.setMaxValue(fields[i].getMaxValue());
					pubfield.setMinValue(fields[i].getMinValue());
					pubfieldslist.add(pubfield);
					pubds.getFieldSet().addField(pubfield);
				}
				LFWViewPage lfwviewpage = LFWPersTool.getLFWViewPage();
				TreeViewer tv = ((CellPropertiesView) lfwviewpage.getCellPropertiesView()).getTv();
				tv.setInput(pubfieldslist);
				tv.refresh();
				tv.expandAll();
				
				AddDSCommand cmd = new AddDSCommand(refds);
				if(DataSetEditor.getActiveEditor() != null)
					DataSetEditor.getActiveEditor().executComand(cmd);
			}
		}
	}
}
