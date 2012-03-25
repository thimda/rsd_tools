/**
 * 
 */
package nc.uap.lfw.publicview;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.exception.LfwPluginException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.publicview.PublicViewBrowserEditor;
import nc.uap.lfw.editor.publicview.PublicViewEditorInput;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * @author chouhl
 *
 */
public class PublicViewUIGuideAction extends NodeAction  {

	public PublicViewUIGuideAction(){
		super(WEBProjConstants.UI_GUIDE, WEBProjConstants.UI_GUIDE);
	}
	
	@Override
	public void run() {
		openEditor();
	}
	
	public static void openEditor() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		LfwWidget widget = LFWAMCPersTool.getCurrentWidget();
		if(widget == null || view == null){
			throw new LfwPluginException("��ǰWidget����Ϊ�ջ�lfw�������ͼ����Ϊ��!");
		}
		PublicViewEditorInput editorinput = new PublicViewEditorInput(widget);
		editorinput.setDirTreeItem((LFWDirtoryTreeItem)LFWAMCPersTool.getCurrentTreeItem().getParentItem());
		IWorkbenchPage workbenchPage = view.getViewSite().getPage();
		IEditorReference[] ers = workbenchPage.getEditorReferences();
		IEditorPart editorPart = null;
		for (int i = 0; i < ers.length; i++) {
			editorPart = ers[i].getEditor(true);
			if(editorPart instanceof PublicViewBrowserEditor){
				PublicViewBrowserEditor vbEditor = (PublicViewBrowserEditor)editorPart;
				String openedId = ((PublicViewEditorInput)vbEditor.getEditorInput()).getWidget().getId();
				if (openedId.equals(widget.getId())) {
					break;
				}
			}
			editorPart = null;
		}
		if (editorPart != null){
			workbenchPage.bringToTop(editorPart);
		}else {
			try {
				String editorid = "nc.uap.lfw.editor.publicview.PublicViewBrowserEditor";
				workbenchPage.openEditor(editorinput, editorid);
			} catch (PartInitException e) {
				MainPlugin.getDefault().logError(e.getMessage(), e);
			}
		}
	}
	
}
