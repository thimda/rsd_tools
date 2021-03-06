package nc.uap.lfw.perspective.webcomponent;

import java.io.File;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.DeleteUIGuildAction;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.editor.pagemeta.UIGuideAction;
import nc.lfw.editor.pagemeta.UIGuildImportHtml;
import nc.lfw.editor.widget.EditWidgetAction;
import nc.uap.lfw.aciton.EditNCTemplateAction;
import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.base.DeleteUIAction;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.md.component.NewMdDsFormComponent;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.action.CancelNCQryTemplateAction;
import nc.uap.lfw.perspective.action.CancelNCTemplateAction;
import nc.uap.lfw.perspective.action.DeleteWidgetAction;
import nc.uap.lfw.perspective.action.NCQueryTemplageAction;
import nc.uap.lfw.perspective.action.RenameWidgetAction;
import nc.uap.lfw.perspective.action.UseNCTemplateAction;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.publicview.EditorPublicViewAction;
import nc.uap.lfw.publicview.PublicViewUIGuideAction;
import nc.uap.lfw.widget.DelPoolWidgetAction;
import nc.uap.lfw.widget.NewWidgetFromPoolAction;
import nc.uap.lfw.window.view.ViewUIGuideAction;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

/**
 * widget 树组件
 * @author zhangxya
 *
 */
public class LFWWidgetTreeItem extends LFWDirtoryTreeItem {
	private LfwWidget widget = null;
	public LfwWidget getWidget() {
		return widget;
	}
	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}
	
	public LFWWidgetTreeItem(TreeItem parentItem, File file, LfwWidget widget,  String text) {
		super(parentItem, file, text);
		this.widget = widget;
//		setData(file);
//		setText(text);
//		setImage(getDirImage());
	}
	
	public LFWWidgetTreeItem(TreeItem parentItem, File file, LfwWidget widget) {
		super(parentItem, file, null);
		this.widget = widget;
		setData(file);
		setText(file.getName());
		setImage(getDirImage());
	}
	protected void checkSubclass () {
	}
	protected Image getDirImage() {
		ImageDescriptor imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "widget.gif");
		return imageDescriptor.createImage();
	}

	public File getFile() {
		return (File) getData();
	}

	public void deleteNode() {
		FileUtilities.deleteFile(getFile());
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
		final LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		final MenuManager uiMenuManager = new MenuManager(WEBProjConstants.UI_DESIGN, PaletteImage.getCreateMenuImgDescriptor(), WEBProjConstants.UI_DESIGN);
		uiMenuManager.setRemoveAllWhenShown(true);
		
		// 获取当前Pagemeta
		LFWPageMetaTreeItem pmTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
		
		if (pmTreeItem == null){// publicview
			uiMenuManager.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					if(getLfwVersion().equals(LFWTool.NEW_VERSION)){
						PublicViewUIGuideAction viewUIGuide = new PublicViewUIGuideAction();
						DeleteUIAction deleteUI = new DeleteUIAction();
						deleteUI.setSite(view.getViewSite());
						manager.add(viewUIGuide);
						manager.add(deleteUI);
					}else{
						UIGuildImportHtml importHtml = new UIGuildImportHtml();
						UIGuideAction uiGuild = new UIGuideAction();
						uiGuild.setSite(view.getViewSite());
						DeleteUIGuildAction deleteGuild = new DeleteUIGuildAction();
						deleteGuild.setSite(view.getViewSite());
						manager.add(uiGuild);
						manager.add(deleteGuild);
						manager.add(importHtml);
					}
				}
			});
			manager.add(uiMenuManager);
			EditorPublicViewAction editPVAction = new EditorPublicViewAction();
			editPVAction.setTreeItem(this);
			DelPoolWidgetAction delWidgetAction = new DelPoolWidgetAction();
			manager.add(editPVAction);
			manager.add(delWidgetAction);
			return;
		}
		// view
		if(isNotRefView()){
			uiMenuManager.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					if(getLfwVersion().equals(LFWTool.NEW_VERSION)){
						ViewUIGuideAction viewUIGuide = new ViewUIGuideAction();
						DeleteUIAction deleteViewUI = new DeleteUIAction();
						deleteViewUI.setSite(view.getViewSite());
						manager.add(viewUIGuide);
						manager.add(deleteViewUI);
					}else{
						UIGuideAction uiGuild = new UIGuideAction();
						uiGuild.setSite(view.getViewSite());
						DeleteUIGuildAction deleteGuild = new DeleteUIGuildAction();
						deleteGuild.setSite(view.getViewSite());
						UIGuildImportHtml importHtml = new UIGuildImportHtml();
						manager.add(uiGuild);
						manager.add(deleteGuild);
						manager.add(importHtml);
					}
				}
			});
			manager.add(uiMenuManager);
		}

		MenuManager ncMenuManager = new MenuManager(WEBProjConstants.NC_TEMPLATE);
		ncMenuManager.setRemoveAllWhenShown(true);
		final UseNCTemplateAction publishNCAction = new UseNCTemplateAction();
		LFWWidgetTreeItem widgettree = this;
		LFWDirtoryTreeItem parentTreeItem = (LFWDirtoryTreeItem)widgettree.getParentItem();
		File file = ((LFWDirtoryTreeItem) parentTreeItem).getFile();
		// 文件夹路径
		String folderPath = file.getPath();
		PageMeta pm = pmTreeItem.getPm();
		String funnode = (String)pm.getExtendAttributeValue(WEBProjConstants.FUNC_CODE);
		publishNCAction.setFunnode(funnode);
		String filePath = folderPath + "/" + widgettree.getWidget().getId();
		publishNCAction.setFilePath(filePath);
		
		final NCQueryTemplageAction ncQueryTemplateAction = new NCQueryTemplageAction();
		ncQueryTemplateAction.setFunnode(funnode);
		ncQueryTemplateAction.setFilePath(filePath);
		
		ncMenuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				//关联NC模板
				manager.add(publishNCAction);
				manager.add(ncQueryTemplateAction);
				EditNCTemplateAction editNCAction = new EditNCTemplateAction();
				manager.add(editNCAction);
				//取消关联nc单据模板
				CancelNCTemplateAction cancelNC = new CancelNCTemplateAction();
				manager.add(cancelNC);
				//取消关联nc查询模板
				CancelNCQryTemplateAction cancelNCQry = new CancelNCQryTemplateAction();
				manager.add(cancelNCQry);
			}
		});
		if(!LFWTool.NEW_VERSION.equals(getLfwVersion())){
			manager.add(ncMenuManager);
		}
		// 设置Widget编辑器参数
		EditWidgetAction editWidgetAction = new EditWidgetAction();
		DeleteWidgetAction deleteWidgetAction = new DeleteWidgetAction();
		RenameWidgetAction renameWidgetAction = new RenameWidgetAction();
		editWidgetAction.setTreeItem(this);
		
		if(LFWTool.NEW_VERSION.equals(getLfwVersion())){
			if(isNotRefView()){
				manager.add(editWidgetAction);
				manager.add(renameWidgetAction);
			}
			manager.add(deleteWidgetAction);
		}else{
			manager.add(editWidgetAction);
			manager.add(deleteWidgetAction);
			manager.add(renameWidgetAction);
		}
		
		//组件引入action
		if(!LFWTool.NEW_VERSION.equals(getLfwVersion())){
			NewMdDsFormComponent mdCompnentAction = new NewMdDsFormComponent();
			manager.add(mdCompnentAction);
		}
		//公共widget
		if(LFWTool.NEW_VERSION.equals(getLfwVersion())){
//			ViewNodeFromPublicViewAction viewNodeFromPublicViewAction = new ViewNodeFromPublicViewAction();
//			manager.add(viewNodeFromPublicViewAction);
		}else{
			NewWidgetFromPoolAction widgetFromPoolAction = new NewWidgetFromPoolAction();
			manager.add(widgetFromPoolAction);
		}
	} 
	
	public void mouseDoubleClick(){
		if(getLfwVersion().equals(LFWTool.NEW_VERSION)){
			if(ILFWTreeNode.POOLWIDGETFOLDER.equals(this.getType())){
				EditorPublicViewAction editPVAction = new EditorPublicViewAction();
				editPVAction.setTreeItem(this);
				editPVAction.run();
				if(getItemCount() == 0){
					this.removeAll();
					LFWExplorerTreeView.getLFWExploerTreeView(null).detalWidgetTreeItem(this, this.getFile(), widget);
				}
			}else if(ILFWTreeNode.POOLREFNODEFOLDER.equals(this.getType())){
				String refId = this.getWidget().getRefId();
				if(refId != null && refId.startsWith("..")){
					refId = refId.substring(3);
					List<TreeItem> tiList = LFWAMCPersTool.getAllChildren(LFWAMCPersTool.getCurrentProject(), this, ILFWTreeNode.PUBLIC_VIEW_FOLDER);
					if(tiList != null && tiList.size() > 0){
						for(TreeItem ti : tiList){
							if(ti instanceof LFWWidgetTreeItem){
								if(refId.equals(((LFWWidgetTreeItem) ti).getWidget().getId())){
									EditorPublicViewAction editPVAction = new EditorPublicViewAction();
									editPVAction.setTreeItem((LFWWidgetTreeItem) ti);
									editPVAction.run();
									LFWAMCPersTool.getTree().select(ti);
									if(ti.getItemCount() == 0){
										((LFWWidgetTreeItem) ti).removeAll();
										LFWExplorerTreeView.getLFWExploerTreeView(null).detalWidgetTreeItem(((LFWWidgetTreeItem) ti), ((LFWWidgetTreeItem) ti).getFile(), ((LFWWidgetTreeItem) ti).getWidget());
									}
								}
							}
						}
					}
				}
			}else{
				if(isNotRefView()){
					EditWidgetAction editWidgetAction = new EditWidgetAction();
					editWidgetAction.setTreeItem(this);
					editWidgetAction.run();
				}
			}
		}else{
			LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
			if(view.hasOpened(this)){
				return;
			}
			if(LFWDirtoryTreeItem.POOLWIDGETFOLDER.equals(getType())){
				view.refreshPubWidgetTreeItem(this);
			}else{
				view.refreshWidgetTreeItem(this);
			}
		}
	}
	
	private boolean isNotRefView(){
		boolean isNotRefView = true;
		if(widget.getRefId() == null || widget.getRefId().startsWith("..")){
			return false;
		}
//		String ctx = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
//		Map<String, Map<String, LfwWidget>> input = RefDatasetData.getPoolWidgets("/" + ctx);
//		Iterator<String> keys = input.keySet().iterator();
//		if(keys != null){
//			while(keys.hasNext()){
//				LfwWidget pv = input.get(keys.next()).get(widget.getRefId().replaceFirst("../", ""));
//				if(pv != null){//当前View引用了PublicView
//					isNotRefView = false;
//					break;
//				}
//			}
//		}
		return isNotRefView;
	}
	
}