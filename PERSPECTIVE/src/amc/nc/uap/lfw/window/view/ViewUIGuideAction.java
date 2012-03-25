/**
 * 
 */
package nc.uap.lfw.window.view;

import nc.lfw.editor.common.WidgetEditorInput;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.exception.LfwPluginException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.view.ViewBrowserEditor;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * @author chouhl
 *
 */
public class ViewUIGuideAction extends NodeAction {

	public ViewUIGuideAction(){
		super(WEBProjConstants.UI_GUIDE, WEBProjConstants.UI_GUIDE);
	}
	
	@Override
	public void run() {
		openEditor();
	}
	
	public static void openEditor() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		PageMeta pagemeta = LFWPersTool.getCurrentPageMeta();
		if(widget == null || view == null){
			throw new LfwPluginException("当前Widget对象为空或lfw浏览器视图对象为空!");
		}
		WidgetEditorInput editorInput = new WidgetEditorInput(widget, pagemeta);
		editorInput.setPmTreeItem(LFWAMCPersTool.getCurrentPageMetaTreeItem());
		IWorkbenchPage workbenchPage = view.getViewSite().getPage();
		IEditorReference[] ers = workbenchPage.getEditorReferences();
		IEditorPart editorPart = null;
		for (int i = 0; i < ers.length; i++) {
			editorPart = ers[i].getEditor(true);
			if(editorPart instanceof ViewBrowserEditor){
				ViewBrowserEditor wbEditor = (ViewBrowserEditor)editorPart;
				LfwWidget widgetOpened = ((WidgetEditorInput)wbEditor.getEditorInput()).getWidget();
				if (widget.getId().equals(widgetOpened.getId())) {
					PageMeta pageMeta = widget.getPagemeta();
					PageMeta pageMetaOpened = widgetOpened.getPagemeta();
					if(pageMeta != null && pageMetaOpened != null && pageMeta.getId().equals(pageMetaOpened.getId())){
						break;
					}
				}
			}
			editorPart = null;
		}
		if (editorPart != null)
			workbenchPage.bringToTop(editorPart);
		else {
			try {
				String editorid = "nc.uap.lfw.editor.view.ViewBrowserEditor";
				workbenchPage.openEditor(editorInput, editorid);
			} catch (PartInitException e) {
				MainPlugin.getDefault().logError(e.getMessage(), e);
			}
		}
	}
	
}
