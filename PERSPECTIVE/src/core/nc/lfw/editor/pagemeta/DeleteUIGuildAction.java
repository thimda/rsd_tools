package nc.lfw.editor.pagemeta;

import java.io.File;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.jsp.editor.GuideEditor;
import nc.lfw.jsp.editor.GuideEditorInput;
import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;

/**
 * 删除UI设计
 * @author zhangxya
 *
 */
public class DeleteUIGuildAction extends NodeAction {
	
	private IViewSite site;
	
	public DeleteUIGuildAction() {
		super(WEBProjConstants.DEL_UI_GUIDE, WEBProjConstants.DEL_UI_GUIDE);
	}
	
	public void run() {
		boolean flag = MessageDialog.openConfirm(null ,"提示", "确定要删除UI设计吗?");
		if(flag){
			String folderPath = LFWPersTool.getCurrentFolderPath();
			String filePath = folderPath + "/uimeta.um";
			File file = new File(filePath);
			//删除文件
			if(file.exists()){
				FileUtilities.deleteFile(file);
			}
			GuideEditor guideEditor = GuideEditor.getActiveEditor();
			if(guideEditor != null){
				GuideEditorInput guideEditorInput = (GuideEditorInput) guideEditor.getEditorInput();
				closeEditor(guideEditorInput);
			}
				
		}
		
	}
	
	private void closeEditor(GuideEditorInput editorinput) {
		IWorkbenchPage workbenchPage = site.getPage();
		IEditorPart editor = workbenchPage.findEditor(editorinput);
		workbenchPage.closeEditor(editor, false);
	}
	
	public IViewSite getSite() {
		return site;
	}
	public void setSite(IViewSite site) {
		this.site = site;
	}
	
}
