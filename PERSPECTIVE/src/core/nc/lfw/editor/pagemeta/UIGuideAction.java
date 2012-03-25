package nc.lfw.editor.pagemeta;

import java.io.File;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.jsp.editor.GuideEditorInput;
import nc.lfw.jsp.editor.UIMetaUtil;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * 模版向导
 * @author guoweic
 *
 */
public class UIGuideAction extends Action {

	private IViewSite site;
	public UIGuideAction() {
		super(WEBProjConstants.UI_GUIDE);
	}

	
	public void run() {
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		String folderPath = LFWPersTool.getCurrentFolderPath();
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
		String pmPath = null;
		if(pmTreeItem != null)
			pmPath = LFWPersTool.getCurrentPageMetaTreeItem().getFile().getAbsolutePath();
		//if(!f.exists()){
		if(false){
			LFWExplorerTreeView view = LFWExplorerTreeView
					.getLFWExploerTreeView(null);
			if (view == null)
				return;
			Shell shell = new Shell(Display.getCurrent());
			
			UIGuideWizard wizard = new UIGuideWizard();
			
			wizard.setNodePath(folderPath);
			WizardDialog dialog = new WizardDialog(shell, wizard);
			dialog.setPageSize(-1, 200);
			int result = dialog.open();
			if (result == IDialogConstants.FINISH_ID) {
				
			}
		}
		else{
			String filePath = folderPath;
			filePath += "/uimeta.um";
			File f = new File(filePath);
			
			GuideEditorInput editorinput = new GuideEditorInput();
			editorinput.setPmPath(pmPath);
			// 设置项目路径（必须设置）
			editorinput.setFolderPath(folderPath);
			if(!f.exists()){
				createBeforeOpen(editorinput, folderPath, widget);
			}
			openEditor(editorinput);
		}
	}
	
	
	private void createBeforeOpen(GuideEditorInput editorinput, String folderPath, LfwWidget widget) {
		UIMeta meta = null;
		if(widget != null){
			meta = UIMetaUtil.getDefaultUIMeta(LFWPersTool.getCurrentWidget(),false);
			LFWPageMetaTreeItem pmTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
			String pmPath = null;
			if(pmTreeItem != null)
				pmPath = pmTreeItem.getFile().getAbsolutePath();
			NCConnector.saveUIMeta(meta, pmPath, folderPath);
		}
		else{
			meta = UIMetaUtil.getDefaultUIMeta(new PageMeta(), 100, false);
			String pmPath = LFWPersTool.getCurrentPageMetaTreeItem().getFile().getAbsolutePath();
			NCConnector.saveUIMeta(meta, pmPath, folderPath);
		}
	}

	private void openEditor(GuideEditorInput editorinput) {
		
		String editorid = "nc.lfw.jsp.editor.GuideEditor";
		if (editorinput == null || editorid == null)
			return;
		IWorkbenchPage workbenchPage = site.getPage();
		IEditorPart editor = workbenchPage.findEditor(editorinput);
		if (editor != null)
			workbenchPage.bringToTop(editor);
		else {
			try {
				workbenchPage.openEditor(editorinput, editorid);
			} 
			catch (PartInitException e1) {
				e1.printStackTrace();
			}
		}
	}

	
	public IViewSite getSite() {
		return site;
	}

	public void setSite(IViewSite site) {
		this.site = site;
	}

}
