/**
 * 
 */
package nc.uap.lfw.application;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.editor.application.ApplicationEditor;
import nc.uap.lfw.editor.application.ApplicationEditorInput;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * 
 * �༭Application�ڵ���
 * @author chouhl
 *
 */
public class EditApplicationNodeAction extends NodeAction {

	public EditApplicationNodeAction(){
		super(WEBProjConstants.EDIT_APPLICATION, WEBProjConstants.EDIT_APPLICATION);
	}

	public void run() {
		openApplicationEditor();
	}
	
	public static void openApplicationEditor(){
		Application app = LFWAMCPersTool.getCurrentApplication();
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(app == null || view == null){
			throw new LfwRuntimeException("Application����Ϊ�� �� lfw�������ͼ����Ϊ��!");
		}
		IWorkbenchPage workbenchPage = view.getViewSite().getPage();
		IEditorReference[] editorRefs = workbenchPage.getEditorReferences();
		IEditorPart editor = null;
		for(int i = 0; i < editorRefs.length; i++){
			editor = editorRefs[i].getEditor(true);
			if(editor instanceof ApplicationEditor){
				if(app.getId().equals(((ApplicationEditorInput)editor.getEditorInput()).getWebElement().getId())) {
					break;
				}
			}
			editor = null;
		}
		LFWPersTool.showView(LFWTool.ID_LFW_VIEW_SHEET);
		LFWPersTool.showView(IPageLayout.ID_PROP_SHEET);
		if(editor != null){
			workbenchPage.bringToTop(editor);
		}else{
			try{
				ApplicationEditorInput editorInput = new ApplicationEditorInput(app);
				LFWAMCPersTool.getCurrentApplicationTreeItem().setEditorInput(editorInput);
				String editorid = ApplicationEditor.class.getName();
				workbenchPage.openEditor(editorInput, editorid);
			} catch (PartInitException e) {
				MainPlugin.getDefault().logError("��Application�༭����ʧ��", e);
			}
		}
	}
	
}
