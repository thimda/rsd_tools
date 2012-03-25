package nc.lfw.editor.widget;

import nc.lfw.editor.common.WidgetEditorInput;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.NodeAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * 编辑Widget操作
 * 
 * @author guoweic
 *
 */
public class EditWidgetAction extends NodeAction {
	
	private LFWWidgetTreeItem treeItem;
	
	public void setTreeItem(LFWWidgetTreeItem treeItem) {
		this.treeItem = treeItem;
	}
	
	public EditWidgetAction() {
		if(LFWTool.NEW_VERSION.equals(((LFWBasicTreeItem)LFWPersTool.getCurrentTreeItem()).getLfwVersion())){
			setText(WEBProjConstants.EDIT_VIEW);
			setToolTipText(WEBProjConstants.EDIT_VIEW);
		}else{
			setText(WEBProjConstants.EDIT_WIDGET);
			setToolTipText(WEBProjConstants.EDIT_WIDGET);
		}
	}

	
	public void run() {
		openEditor();
	}
	
	private void openEditor() {
		WidgetEditorInput editorinput = new WidgetEditorInput(treeItem.getWidget(), LFWPersTool.getCurrentPageMeta());
		// 设置项目路径（必须设置）
		String editorid = "nc.lfw.editor.widget.WidgetEditor";
		if (editorinput == null || editorid == null)
			return;
		
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		IWorkbenchPage workbenchPage = view.getViewSite().getPage();
		IEditorPart editor = null;
		LfwWidget widget = treeItem.getWidget();
		if (null == widget)
			widget = LFWPersTool.getCurrentWidget();
		String currentPath = LFWPersTool.getCurrentFolderPath();
		String pagemetaId = currentPath.substring(0, currentPath.lastIndexOf("\\")).substring(currentPath.substring(0, currentPath.lastIndexOf("\\")).lastIndexOf("\\") + 1);
		String editorInputId = view.getEditoridMap().get("widget_" + pagemetaId + "_" + widget.getId());
		if (editorInputId != null) {
			IEditorReference[] ers = workbenchPage.getEditorReferences();
			IEditorPart editorPart = null;
			for (int i = 0; i < ers.length; i++) {
				editorPart = ers[i].getEditor(true);
				if(editorPart instanceof WidgetEditor){
					WidgetEditor wbEditor = (WidgetEditor)editorPart;
					LfwWidget widgetNew = ((WidgetEditorInput)wbEditor.getEditorInput()).getWidget();
					if (widget.getId().equals(widgetNew.getId())) {
						editor =  editorPart;
						TreeItem ti = ((WidgetEditorInput)wbEditor.getEditorInput()).getCurrentTreeItem();
						if(ti == null || ti.isDisposed()){
							workbenchPage.closeEditor(editor, false);
							editor = null;
						}
						break;
					}
				}
			}
			LFWPersTool.getLFWViewSheet();
			if (editor != null)
				workbenchPage.bringToTop(editor);
			else {
				try {
					workbenchPage.openEditor(editorinput, editorid);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
			}
		}else {
			view.getEditoridMap().put("widget_" + pagemetaId + "_" + widget.getId(), "widget_" + pagemetaId + "_" + widget.getId());
			try {
				LFWPersTool.getLFWViewSheet();
				workbenchPage.openEditor(editorinput, editorid);
			} catch (PartInitException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
