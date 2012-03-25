/**
 * 
 */
package nc.uap.lfw.publicview;

import java.io.File;

import nc.lfw.editor.pagemeta.DeleteUIGuildAction;
import nc.lfw.editor.pagemeta.UIGuideAction;
import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.LFWAMCTreeItem;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.widget.DelPoolWidgetAction;
import nc.uap.lfw.window.view.ViewUIGuideAction;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * PublicView TreeItem��
 * @author chouhl
 *
 */
public class LFWPublicViewTreeItem extends LFWAMCTreeItem {
	
	/**
	 * ViewԪ��
	 */
	private LfwWidget widget = null;
	
	public LFWPublicViewTreeItem(TreeItem parentItem, File file, LfwWidget widget, String text) {
		super(parentItem, file, text);
		this.widget = widget;
	}
	
	public LFWPublicViewTreeItem(TreeItem parentItem, File file, LfwWidget widget) {
		super(parentItem, file, null);
		this.widget = widget;
	}
	
	public LFWPublicViewTreeItem(TreeItem parentItem, File file, String text){
		super(parentItem, file, text);
	}
	
	protected void checkSubclass() {}

	public File getFile() {
		return (File)getData();
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
		return parentIPath + "/" + getFile().getName();
	}
	
	public void addMenuListener(IMenuManager manager){
		final LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		// UI���
		final MenuManager uiMenuManager = new MenuManager(WEBProjConstants.UI_DESIGN);
		uiMenuManager.setRemoveAllWhenShown(true);
		uiMenuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				DeleteUIGuildAction deleteGuild = new DeleteUIGuildAction();
				deleteGuild.setSite(view.getViewSite());
				UIGuideAction uiGuild = new UIGuideAction();
				uiGuild.setSite(view.getViewSite());
				ViewUIGuideAction viewUIGuide = new ViewUIGuideAction();
				manager.add(viewUIGuide);
//				manager.add(uiGuild);
				manager.add(deleteGuild);
			}
		});
		
//		EditWidgetAction editWidgetAction = new EditWidgetAction();
//		RenameWidgetAction renameWidgetAction = new RenameWidgetAction();
//		editWidgetAction.setTreeItem(this);
		// ɾ��
		DelPoolWidgetAction delWidgetAction = new DelPoolWidgetAction();
		// �Ҽ��˵�����
		manager.add(uiMenuManager);
//		manager.add(editWidgetAction);
//		manager.add(renameWidgetAction);
		manager.add(delWidgetAction);
	} 
	
	public void mouseDoubleClick(){
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
	
	public LfwWidget getWidget() {
		return widget;
	}
	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}
	
}
