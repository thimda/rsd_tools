package nc.uap.portal.skin;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.portal.core.PortalBaseEditorInput;
import nc.uap.portal.core.PortalBasicTreeItem;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.om.Skin;
import nc.uap.portal.om.SkinDescription;
import nc.uap.portal.om.Theme;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.perspective.PortalPlugin;
import nc.uap.portal.skin.action.DelSkinAction;
import nc.uap.portal.skin.action.EditSkinAction;
import nc.uap.portal.theme.ThemeTypeTreeItem;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Skin TreeItem
 * 
 * @author dingrf
 *
 */
public class SkinTreeItem extends PortalBasicTreeItem{
	
	private ImageDescriptor imageDescriptor = null;
	
	public SkinTreeItem(TreeItem parentItem, Skin skin) {
		super(parentItem, SWT.NONE);
		setData(skin);
		setText(skin.getName()+"["+skin.getId()+"]");
		setImage(getDirImage());
	}
	
	private PortalBaseEditorInput editorInput;

	public PortalBaseEditorInput getEditorInput() {
		return editorInput;
	}

	public void setEditorInput(PortalBaseEditorInput editorInput) {
		this.editorInput = editorInput;
	}
	
	protected Image getDirImage() {
		imageDescriptor = PortalPlugin.loadImage(PortalPlugin.ICONS_PATH, "skin.gif");
		return imageDescriptor.createImage();
	}

	@Override
	public void deleteNode() {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();

		ThemeTypeTreeItem themeTypeTreeItem = (ThemeTypeTreeItem)this.getParentItem(); 
		String type = themeTypeTreeItem.getType();
		String themeId = ((Theme)themeTypeTreeItem.getData()).getId();
		
		
		SkinDescription skinDescription  = PortalConnector.getSkinDescription(projectPath, projectModuleName, type, themeId);
		
		Skin skin = (Skin) this.getData();
		
		if (skinDescription != null){
			for (Skin s: skinDescription.getSkin()){
				if(s.getId().equals(skin.getId())){
					skinDescription.getSkin().remove(s);
					break;
				}
			}
		}
		PortalConnector.saveSkinDescription(projectPath, projectModuleName, type, themeId, skinDescription);
		
		PortalConnector.deleteSkinFile(projectPath, projectModuleName, type, themeId, skin.getId()+".ftl");
		if (type.equals(PortalProjConstants.TYPE_PORTLET)){
			PortalConnector.deleteSkinFile(projectPath, projectModuleName, type, themeId, skin.getId()+".css");
			PortalConnector.deleteSkinFile(projectPath, projectModuleName, type, themeId, skin.getId()+".js");
		}
		dispose();
		
	}
	/**
	 * 增加右键菜单
	 * 
	 * @param manager
	 */
	public void addMenuListener(IMenuManager manager){
		EditSkinAction editSkinAction = new EditSkinAction();
		DelSkinAction delSkinAction = new DelSkinAction();
		manager.add(editSkinAction);
		manager.add(delSkinAction);
	}
	
	/**
	 * 双击鼠标事件
	 * 
	 */
	public void mouseDoubleClick(){
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		view.openSkinEditor(this);
	} 
}
