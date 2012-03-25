
package nc.uap.lfw.perspective.webcomponent;

import java.io.File;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.action.LFWCopyAction;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.perspective.action.DeleteWebComponentAction;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

/**
 * �ؼ���Item
 * @author zhangxya
 *
 */
public class LFWWebComponentTreeItem  extends LFWBasicTreeItem implements ILFWTreeNode{
	
	/**
	 * �������
	 */
	private String type = "";

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static final String PARAM_EXCEL = "PARAM_EXCEL";
	public static final String PARAM_GRID = "PARAM_GRID";
	public static final String PARAM_FORM = "PARAM_FORM";
	public static final String PARAM_BUTTON = "PARAM_BUTTON";
	public static final String PARAM_CHART = "PARAM_CHART";
	public static final String PARAM_PROGRESSBAR = "PARAM_PROGRESSBAR";
	public static final String PARAM_MENUBAR = "PARAM_MENUBAR";
	public static final String PARAM_TREE = "PARAM_TREE";
	public static final String PARAM_LABEL = "PARAM_LABEL";
	public static final String PARAM_LINK = "PARAM_LINK";
	public static final String PARAM_IMAGE = "PARAM_IMAGE";
	public static final String PARAM_IMAGEBUTTON = "PARAM_IMAGEBUTTON";
	public static final String PARAM_TEXTAREA = "PARAM_TEXTAREA";
	public static final String PARAM_TEXTCOMP = "PARAM_TEXTCOMP";
	public static final String PARAM_IFRAME = "PARAM_IFRAME";
	public static final String PARAM_TOOLBAR = "PARAM_TOOLBAR";
	public static final String PARAM_SELFDEF = "PARAM_SELFDEF";
		
	public static final String TAB_NAME = "[Tabҳǩ]";
	public static final String CARDLAYOUT_NAME = "[Card����]";
	public static final String PANELLAYOUT_NAME = "[Panel����]";
	public static final String OUTLOOKBAR_NAME = "[OutlookBar]";
	
	public LFWWebComponentTreeItem(TreeItem parentItem, String type, WebComponent webcomponent) {
		super(parentItem, SWT.NONE);
		setData(webcomponent);
		if(webcomponent instanceof FormComp){
			FormComp form = (FormComp) webcomponent;
			if(form.getCaption() != null && !"".equals(form.getCaption()))
				setText(type + webcomponent.getId()  + "[" + form.getCaption() + "]");
			else
				setText(type + webcomponent.getId());
		}
		else if(webcomponent instanceof GridComp){
			GridComp grid = (GridComp) webcomponent;
			if(grid.getCaption() != null && !"".equals(grid.getCaption()))
				setText(type + webcomponent.getId()  + "[" + grid.getCaption() + "]");
			else
				setText(type + webcomponent.getId());
		}
		else if(webcomponent instanceof TreeViewComp){
			TreeViewComp tree = (TreeViewComp) webcomponent;
			if(tree.getCaption() != null && !"".equals(tree.getCaption()))
				setText(type + webcomponent.getId()  + "[" + tree.getCaption() + "]");
			else
				setText(type + webcomponent.getId());
		}
		else	
			setText(type + webcomponent.getId());
		setImage(getFileImage());
	}
	protected void checkSubclass () {
	}
	
	protected Image getFileImage() {
		ImageDescriptor imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "component.gif");
		return imageDescriptor.createImage();
	}

		
	public File getFile() {
		return (File) getData();
	}

	public void deleteNode() {
		FileUtilities.deleteFile(getFile());
		dispose();
	}
	
	public void deleteNode(LFWWebComponentTreeItem webComponent) {
		LfwWidget lfwwidget = LFWPersTool.getCurrentWidget();
		if(lfwwidget != null){
			WebComponent web = (WebComponent)webComponent.getData();
			Map<String, WebComponent> webComponentsMap = lfwwidget.getViewComponents().getComponentsMap();
			WebComponent webComp  = webComponentsMap.get(web.getId());
			if(webComp != null){
				webComponentsMap.remove(web.getId());
				lfwwidget.getViewComponents().setCompMap(webComponentsMap);
			}else{
				return;
			}
			LFWPersTool.saveWidget(lfwwidget);
		}else {
			PageMeta pm = LFWPersTool.getCurrentPageMeta();
			LFWPersTool.savePagemeta(pm);
		}
		dispose();
	}
	
	public String getIPathStr() {
		String parentIPath = "";
		TreeItem parent = getParentItem();
		if(parent instanceof ILFWTreeNode){
			parentIPath = ((ILFWTreeNode)parent).getIPathStr();
		}
		return parentIPath+"/"+getFile().getName();
		
	}
	public void addMenuListener(IMenuManager manager){
		//����ds����
		LFWCopyAction copyAction = null;
		WebComponent webComponent = (WebComponent) getData();
		String text = null;
		if(getType().equals(LFWWebComponentTreeItem.PARAM_GRID)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_GRID);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_GRID;
//				DeleteGridAction deleteGridAction = new DeleteGridAction();
//				manager.add(deleteGridAction);
			}
		}else if(getType().equals(LFWWebComponentTreeItem.PARAM_EXCEL)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_EXCEL);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_EXCEL;
//				DeleteTreeAction deleteTreeAction = new DeleteTreeAction();
//				manager.add(deleteTreeAction);
			}
		}else if(getType().equals(LFWWebComponentTreeItem.PARAM_TREE)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_TREE);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_TREE;
//				DeleteTreeAction deleteTreeAction = new DeleteTreeAction();
//				manager.add(deleteTreeAction);
			}
		}else if(getType().equals(LFWWebComponentTreeItem.PARAM_FORM)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_FORM);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_FORM;
//				DeleteFormAction deleteFormAction = new DeleteFormAction();
//				manager.add(deleteFormAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_IFRAME)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_IFRAMECOMP);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_IFRAME;
//				DeleteIFrameAction deleteIframe  = new DeleteIFrameAction();
//				manager.add(deleteIframe);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_TEXTCOMP)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_TEXTCOMP);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_TEXTCOMP;
//				DeleteTextCompAction deleteCompAction = new DeleteTextCompAction();
//				manager.add(deleteCompAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_LABEL)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_LABEL);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_LABEL;
//				DeleteLabelAction deleteLabelAction = new DeleteLabelAction();
//				manager.add(deleteLabelAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_LINK)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_LINKCOMP);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_LINKCOMP;
//				DelLinkCompAction  deleteLinkAction = new DelLinkCompAction();
//				manager.add(deleteLinkAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_BUTTON)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_BUTTON);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_BUTTON;
//				DeleteButtonAction deleteButtonAction = new DeleteButtonAction();
//				manager.add(deleteButtonAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_CHART)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_CHART);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_CHART;
//				DelChartAction delChartAction = new DelChartAction();
//				manager.add(delChartAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_SELFDEF)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_SELFDEFCOMP);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_SELF_DEF_COMP;
//				DelSelfdefCompAction delselfAction = new DelSelfdefCompAction();
//				manager.add(delselfAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_IMAGE)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_IMAGE);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_IMAGE;
//				DeleteImageAction deleteImageAction = new DeleteImageAction();
//				manager.add(deleteImageAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_TOOLBAR)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_TOOLBAR);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_TOOLBAR;
//				DeleteToolbarAction deleteToolbarAction = new DeleteToolbarAction();
//				manager.add(deleteToolbarAction);
			}
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_PROGRESSBAR)){
			copyAction = new LFWCopyAction(WEBProjConstants.COMPONENT_TOOLBAR);
			manager.add(copyAction);
			if(webComponent.getFrom() == null){
				text = WEBProjConstants.DEL_PROGRESSBAR;
//				DelProjessBarAction delProjessBarAction = new DelProjessBarAction();
//				manager.add(delProjessBarAction);
			}
		}
		if(text != null){
			DeleteWebComponentAction deleteAction = new DeleteWebComponentAction(text);
			manager.add(deleteAction);
		}
	} 
	public void mouseDoubleClick(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(getType().equals(LFWWebComponentTreeItem.PARAM_GRID)){
			view.openGridEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_EXCEL)){
			view.openExcelEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_TREE)){
			view.openTreeEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_BUTTON)){
			view.openButtonEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_CHART)){
			view.openChartEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_SELFDEF)){
			view.openSelfdefEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_PROGRESSBAR)){
			view.openProgressBarEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_IMAGE)){
			view.openImageEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_LABEL)){
			view.openLabelEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_LINK)){
			view.openLinkEditor(this);
		}
		else  if(getType().equals(LFWWebComponentTreeItem.PARAM_FORM)){
			view.openFormEditor(this);
		}
		else  if(getType().equals(LFWWebComponentTreeItem.PARAM_TEXTCOMP)){
			view.openTextEditor(this);
		}
		else  if(getType().equals(LFWWebComponentTreeItem.PARAM_IFRAME)){
			view.openIFrameEditor(this);
		}
		else if(getType().equals(LFWWebComponentTreeItem.PARAM_TOOLBAR)){
			view.openToolbarEditor(this);
		}
	} 

}

