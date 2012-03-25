package nc.uap.lfw.dataset;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.model.DatasetElementObj;
import nc.uap.lfw.perspective.views.CellPropertiesView;
import nc.uap.lfw.perspective.views.LFWViewPage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;

/**
 * ��������action
 * @author zhangxya
 *
 */
public class ImportDsFromDBAction extends Action {

	private DatasetElementObj dsobj;
	public ImportDsFromDBAction(DatasetElementObj dsobj) {
		super("��������",PaletteImage.getCreateDsImgDescriptor());
		this.dsobj = dsobj;
	}

	public void run() {
		DataSetEditor editor = DataSetEditor.getActiveEditor();
		if (editor != null) {
			Dataset ds = dsobj.getDs();
			if (ds != null) {
				LFWViewPage lfwviewpage = LFWPersTool.getLFWViewPage();
				CellPropertiesView view = ((CellPropertiesView) lfwviewpage.getCellPropertiesView());
				WizardDialog wd = new WizardDialog(editor.getSite().getShell(), new ImportDsAttrWizard(ds, view)) {
						protected void nextPressed() {
						IWizardPage page = getCurrentPage().getNextPage();
						if (page instanceof SelectTableColumnPage) {
							((SelectTableColumnPage)page).initTableListData();
						}
						super.nextPressed();
					}

				};
				String title = "Ϊ" + ds.getId() + "�����ֶζԻ���";
				wd.setTitle(title);
				wd.setPageSize(-1, 300);
				wd.open();
			} else {
				MessageDialog.openConfirm(editor.getSite().getShell(), "��ʾ", "��ѡ��һ�����ݼ�!");
			}

		}
	}

}
