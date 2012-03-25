package nc.uap.lfw.perspective.webcomponent;

import java.io.File;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.DeleteUIGuildAction;
import nc.lfw.editor.pagemeta.EditNodeAction;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.editor.pagemeta.NewNodeAction;
import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.lfw.editor.pagemeta.RefreshNodeGroupAction;
import nc.lfw.editor.pagemeta.UIGuideAction;
import nc.lfw.editor.pagemeta.UIGuildImportHtml;
import nc.lfw.virtualdirec.core.NewVirtualDirAction;
import nc.uap.lfw.aciton.CancelPublishNCAction;
import nc.uap.lfw.aciton.EditNCMenuAction;
import nc.uap.lfw.aciton.RegisterMenuItemAction;
import nc.uap.lfw.application.ApplicationNodeAction;
import nc.uap.lfw.application.DeleteApplicationNodeAction;
import nc.uap.lfw.application.EditApplicationNodeAction;
import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.base.CreateVirtualFolderAction;
import nc.uap.lfw.core.base.DeleteUIAction;
import nc.uap.lfw.core.base.DeleteVirtualFolderAction;
import nc.uap.lfw.core.base.RefreshAMCNodeGroupAction;
import nc.uap.lfw.core.common.ExtAttrConstants;
import nc.uap.lfw.core.page.config.RefNodeConf;
import nc.uap.lfw.dataset.NewPoolDsAction;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.model.DeleteModelNodeAction;
import nc.uap.lfw.model.ModelNodeAction;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.action.AMCPublishNCAction;
import nc.uap.lfw.perspective.action.DeleteNodeAction;
import nc.uap.lfw.perspective.action.NcPatternGenerator;
import nc.uap.lfw.perspective.action.NewWidgetAction;
import nc.uap.lfw.perspective.action.PublishNCAction;
import nc.uap.lfw.perspective.commands.RefreshPubDataAction;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeBuilder;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.publicview.PublicViewNodeAction;
import nc.uap.lfw.publicview.RefreshPublicViewNodeAction;
import nc.uap.lfw.refnode.NewPoolRefNodeAction;
import nc.uap.lfw.widget.DelPoolWidgetAction;
import nc.uap.lfw.widget.NewPoolWidgetAction;
import nc.uap.lfw.window.DeleteWindowNodeAction;
import nc.uap.lfw.window.RefreshWindowNodeAction;
import nc.uap.lfw.window.WindowNodeAction;
import nc.uap.lfw.window.WindowUIGuideAction;
import nc.uap.lfw.window.view.ViewNodeAction;
import nc.uap.lfw.window.view.ViewNodeFromPublicViewAction;
import nc.uap.lfw.window.view.ViewUIGuideAction;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

public class LFWDirtoryTreeItem extends LFWBasicTreeItem implements	ILFWTreeNode {

	/**
	 * �ļ�������
	 */
	private String type = "";

	public Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public LFWDirtoryTreeItem(TreeItem parentItem, Object object, String text) {
		super(parentItem, SWT.NONE);
		this.object = object;
		setData(object);
		setText(text);
		setImage(getDirImage());
	}

	public LFWDirtoryTreeItem(TreeItem parentItem, File file) {
		this(parentItem, file, file.getName());
	}

	protected void checkSubclass() {
	}

	private ImageDescriptor imageDescriptor = null;

	protected Image getDirImage() {
		if (object instanceof RefNodeConf) {
			imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH,
					"refnode.gif");
		} else
			imageDescriptor = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH,
					"pages.gif");
		return imageDescriptor.createImage();
	}

	public File getFile() {
		return (File) getData();
	}

	public void deleteNode() {
		FileUtilities.deleteFile(getFile());
		LFWAMCPersTool.refreshCurrentPorject();
		dispose();
	}

	public String getIPathStr() {
		String parentIPath = "";
		TreeItem parent = getParentItem();
		if (parent instanceof ILFWTreeNode) {
			parentIPath = ((ILFWTreeNode) parent).getIPathStr();
		}
		return parentIPath + "/" + getFile().getName();

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void addMenuListener(IMenuManager manager) {
		final LFWExplorerTreeView view = LFWExplorerTreeView
				.getLFWExploerTreeView(null);
		if (type.equals(LFWDirtoryTreeItem.POOLDSFOLDER)
				|| type.equals(LFWDirtoryTreeItem.PARENT_PUB_DS_FOLDER)) {
			NewPoolDsAction newPoolDsAction = new NewPoolDsAction();
			manager.add(newPoolDsAction);
		} else if (type.equals(LFWDirtoryTreeItem.POOLREFNODEFOLDER)
				|| type.equals(LFWDirtoryTreeItem.PARENT_PUB_REF_FOLDER)) {
			NewPoolRefNodeAction newPoolRefNodeAction = new NewPoolRefNodeAction();
			manager.add(newPoolRefNodeAction);
		} else if (type.equals(LFWDirtoryTreeItem.PARENT_PUB_WIDGET_FOLDER)) {
			NewPoolWidgetAction widgetAction = new NewPoolWidgetAction();
			manager.add(widgetAction);
		} else if (type.equals(LFWDirtoryTreeItem.PARENT_NODE_FOLDER)) {
			NewNodeAction newNodeAction = new NewNodeAction();
			NewVirtualDirAction newVirDirAction = new NewVirtualDirAction();
			RefreshNodeGroupAction refreshNodeGroupAction = new RefreshNodeGroupAction();
			manager.add(newNodeAction);
			manager.add(newVirDirAction);
			manager.add(refreshNodeGroupAction);
		} 
//		else if (type.equals(LFWDirtoryTreeItem.PAGEFLOW_FOLDER)) {
//			NewPageFlowAction newPageFlowAction = new NewPageFlowAction();
//			manager.add(newPageFlowAction);
//		} 
		else if (type.equals(LFWDirtoryTreeItem.PARENT_PUB_REF_FOLDER)
				|| type.equals(LFWDirtoryTreeItem.PARENT_PUB_DS_FOLDER)) {
			RefreshPubDataAction pubDataAction = new RefreshPubDataAction();
			manager.add(pubDataAction);
		} else if (type.equals(LFWDirtoryTreeItem.NODE_FOLDER)) {
			final MenuManager uiMenuManager = new MenuManager(
					WEBProjConstants.UI_DESIGN);
			uiMenuManager.setRemoveAllWhenShown(true);

			uiMenuManager.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					UIGuildImportHtml importHtml = new UIGuildImportHtml();
					DeleteUIGuildAction deleteGuild = new DeleteUIGuildAction();
					deleteGuild.setSite(view.getViewSite());
					UIGuideAction uiGuild = new UIGuideAction();
					uiGuild.setSite(view.getViewSite());
					manager.add(uiGuild);
					manager.add(deleteGuild);
					manager.add(importHtml);
				}
			});
			manager.add(uiMenuManager);
			// UI���
			// manager.add(uiGuideAction);
			// �����ӽڵ�
			if (isNotChildNode(this)) {
				MenuManager ncMenuManager = new MenuManager(
						WEBProjConstants.PUBLISH_NODE);
				ncMenuManager.setRemoveAllWhenShown(true);
				final PublishNCAction publishNCAction = new PublishNCAction();
				LFWPageMetaTreeItem pmTreeItem = (LFWPageMetaTreeItem) this;
				String nodeId = pmTreeItem.getPageId();
				publishNCAction.setNodeid(nodeId);

				// ncģʽ������
				final NcPatternGenerator ncPatternGenerator = new NcPatternGenerator();

				ncMenuManager.addMenuListener(new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						manager.add(publishNCAction);

						manager.add(ncPatternGenerator);

						if (LFWPersTool.getVersion().equals(
								ExtAttrConstants.VERSION60)) {
							EditNCMenuAction editNC = new EditNCMenuAction();
							manager.add(editNC);
						}
						CancelPublishNCAction cancelNC = new CancelPublishNCAction();
						manager.add(cancelNC);

						// ע�ᰴť
						RegisterMenuItemAction menuItemAction = new RegisterMenuItemAction();
						manager.add(menuItemAction);
					}
				});
				manager.add(ncMenuManager);
			}
			NewWidgetAction newWidgetAction = new NewWidgetAction();
			EditNodeAction editNodeAction = new EditNodeAction();
			DeleteNodeAction deleteAction = new DeleteNodeAction();
			RefreshNodeAction refreshNodeAction = new RefreshNodeAction();
			manager.add(newWidgetAction);
			manager.add(editNodeAction);
			manager.add(deleteAction);
			manager.add(refreshNodeAction);
		} else if (type.equals(LFWDirtoryTreeItem.PUB_REF_FOLDER)) {

		} else {
			addAMCMenuListener(manager);
		}
	}

	public void addAMCMenuListener(IMenuManager manager) {
		final LFWExplorerTreeView view = LFWExplorerTreeView
				.getLFWExploerTreeView(null);
		if (type.equals(ILFWTreeNode.APPLICATION_FOLDER)) {
			// �½�
			ApplicationNodeAction appNodeAction = new ApplicationNodeAction();
			// �½�����Ŀ¼
			CreateVirtualFolderAction createVirFolderAction = new CreateVirtualFolderAction(type);
			// ɾ������Ŀ¼
			DeleteVirtualFolderAction deleteVirFolderAction = new DeleteVirtualFolderAction();
			// ˢ��
			RefreshAMCNodeGroupAction refreshAMCNodeGroupAction = new RefreshAMCNodeGroupAction(
					WEBProjConstants.AMC_APPLICATION_PATH,
					ILFWTreeNode.APPLICATION,
					WEBProjConstants.AMC_APPLICATION_FILENAME);
			AMCPublishNCAction manageMenuCategory = new AMCPublishNCAction(WEBProjConstants.MANAGE_MENU_CATEGORY);
			// �Ҽ��˵�����
			manager.add(appNodeAction);
			manager.add(createVirFolderAction);
			if(!(this instanceof LFWVirtualDirTreeItem)){
				manager.add(manageMenuCategory);
			}
			if(this instanceof LFWVirtualDirTreeItem){
				manager.add(deleteVirFolderAction);
			}
			manager.add(refreshAMCNodeGroupAction);
		} else if (type.equals(ILFWTreeNode.APPLICATION)) {
			// �༭
			EditApplicationNodeAction editNodeAction = new EditApplicationNodeAction();
			// ɾ��
			DeleteApplicationNodeAction deleteNodeAction = new DeleteApplicationNodeAction();
			// �����ڵ�
			MenuManager ncMenuManager = new MenuManager(WEBProjConstants.PUBLISH_NODE);
			ncMenuManager.setRemoveAllWhenShown(true);
			AMCPublishNCAction registerFunNode = new AMCPublishNCAction(WEBProjConstants.REGISTER_FUNCTION_NODE);
			AMCPublishNCAction registerMenu = new AMCPublishNCAction(WEBProjConstants.REGISTER_MENU);
			AMCPublishNCAction registerFormType = new AMCPublishNCAction(WEBProjConstants.REGISTER_FORM_TYPE);
			// �Ҽ��˵�����
			manager.add(editNodeAction);
			manager.add(deleteNodeAction);
			manager.add(registerFunNode);
			manager.add(registerMenu);
			manager.add(registerFormType);
		} else if (type.equals(ILFWTreeNode.MODEL_FOLDER)) {
			// �½�
			ModelNodeAction modelNodeAction = new ModelNodeAction();
			// ˢ��
			RefreshAMCNodeGroupAction refreshAMCNodeGroupAction = new RefreshAMCNodeGroupAction(
					WEBProjConstants.AMC_MODEL_PATH, ILFWTreeNode.MODEL,
					WEBProjConstants.AMC_MODEL_FILENAME);
			// �Ҽ��˵�����
			manager.add(modelNodeAction);
			manager.add(refreshAMCNodeGroupAction);
		} else if (type.equals(ILFWTreeNode.MODEL)) {
			// ɾ��
			DeleteModelNodeAction deleteNodeAction = new DeleteModelNodeAction();
			// �Ҽ��˵�����
			manager.add(deleteNodeAction);
		} else if (type.equals(ILFWTreeNode.WINDOW_FOLDER)) {
			// �½�
			WindowNodeAction windowNodeAction = new WindowNodeAction();
			// �½�����Ŀ¼
			CreateVirtualFolderAction createVirFolderAction = new CreateVirtualFolderAction(type);
			// ɾ������Ŀ¼
			DeleteVirtualFolderAction deleteVirFolderAction = new DeleteVirtualFolderAction();
			// ˢ��
			RefreshAMCNodeGroupAction refreshAMCNodeGroupAction = new RefreshAMCNodeGroupAction(
					WEBProjConstants.AMC_WINDOW_PATH, ILFWTreeNode.WINDOW,
					WEBProjConstants.AMC_WINDOW_FILENAME);
			// �Ҽ��˵�����
			manager.add(windowNodeAction);
			manager.add(createVirFolderAction);
			if(this instanceof LFWVirtualDirTreeItem){
				manager.add(deleteVirFolderAction);
			}
			manager.add(refreshAMCNodeGroupAction);
		} else if (type.equals(ILFWTreeNode.WINDOW)) {
			// UI���
			final MenuManager uiMenuManager = new MenuManager(WEBProjConstants.UI_DESIGN, PaletteImage.getCreateMenuImgDescriptor(), WEBProjConstants.UI_DESIGN);
			uiMenuManager.setRemoveAllWhenShown(true);
			uiMenuManager.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					DeleteUIAction deleteUI = new DeleteUIAction();
					deleteUI.setSite(view.getViewSite());
//					UIGuideAction uiGuild = new UIGuideAction();
					WindowUIGuideAction uiGuide = new WindowUIGuideAction();
//					uiGuide.setSite(view.getViewSite());
					manager.add(uiGuide);
					manager.add(deleteUI);
				}
			});
			// �½�View
			ViewNodeAction viewNodeAction = new ViewNodeAction();
			// ɾ��Window
			DeleteWindowNodeAction deleteAction = new DeleteWindowNodeAction();
			// EditWindowNodeAction editNodeAction = new EditWindowNodeAction();
			// ˢ��
			RefreshWindowNodeAction refreshNodeAction = new RefreshWindowNodeAction();
			// �༭Window
			EditNodeAction editNodeAction = new EditNodeAction();
			// RefreshNodeAction refreshNodeAction = new RefreshNodeAction();
			// ��publicview����
			ViewNodeFromPublicViewAction viewNodeFromPVAction = new ViewNodeFromPublicViewAction();
			// �Ҽ��˵�����
			manager.add(uiMenuManager);
			manager.add(viewNodeAction);
			manager.add(editNodeAction);
			manager.add(deleteAction);
			manager.add(refreshNodeAction);
			manager.add(viewNodeFromPVAction);
		} else if (type.equals(ILFWTreeNode.PUBLIC_VIEW_FOLDER)) {
			// �½�
			PublicViewNodeAction publicViewAction = new PublicViewNodeAction();
//			RefreshAMCNodeGroupAction refreshAMCNodeGroupAction = new
//			RefreshAMCNodeGroupAction(WEBProjConstants.AMC_PUBLIC_VIEW_PATH,
//			ILFWTreeNode.PUBLIC_VIEW,
//			WEBProjConstants.AMC_PUBLIC_VIEW_FILENAME);
			RefreshPublicViewNodeAction refreshAMCNodeGroupAction = new RefreshPublicViewNodeAction(this, this.getFile(), LFWAMCPersTool.getCurrentProject());
			// �Ҽ��˵�����
			manager.add(publicViewAction);
			manager.add(refreshAMCNodeGroupAction);
		} else if (type.equals(ILFWTreeNode.PUBLIC_VIEW)) {
			// UI���
			final MenuManager uiMenuManager = new MenuManager(
					WEBProjConstants.UI_DESIGN);
			uiMenuManager.setRemoveAllWhenShown(true);
			uiMenuManager.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					DeleteUIGuildAction deleteGuild = new DeleteUIGuildAction();
					deleteGuild.setSite(view.getViewSite());
					UIGuideAction uiGuild = new UIGuideAction();
					uiGuild.setSite(view.getViewSite());
					ViewUIGuideAction viewUIGuide = new ViewUIGuideAction();
					manager.add(viewUIGuide);
//					manager.add(uiGuild);
					manager.add(deleteGuild);
				}
			});
//			editWidgetAction.setTreeItem(this);
			// ɾ��
			DelPoolWidgetAction delWidgetAction = new DelPoolWidgetAction();
			// �Ҽ��˵�����
			manager.add(uiMenuManager);
			manager.add(delWidgetAction);
		}
	}

	/**
	 * �ж��Ƿ����ӽڵ�
	 * 
	 * @param ti
	 * @return
	 */
	private boolean isNotChildNode(TreeItem ti) {
		TreeItem parentTreeItem = ti.getParentItem();
		if (parentTreeItem instanceof LFWDirtoryTreeItem) {
			if (!((LFWDirtoryTreeItem) parentTreeItem).getType().equals(
					LFWDirtoryTreeItem.NODE_FOLDER))
				return true;
			else
				return false;
		} else
			return false;

	}

	public void mouseDoubleClick() {
		LFWExplorerTreeView view = LFWExplorerTreeView
				.getLFWExploerTreeView(null);
		if (view.hasOpened(this)) {
			return;
		}
		IProject project = LFWPersTool.getCurrentProject();
		File file = null;
		if (getParentItem() instanceof LFWBusinessCompnentTreeItem) {
			LFWBusinessCompnentTreeItem projectTreeItem = (LFWBusinessCompnentTreeItem) getParentItem();
			file = projectTreeItem.getFile();
		} else {
			LFWProjectTreeItem projectTreeItem = (LFWProjectTreeItem) getParentItem();
			file = projectTreeItem.getFile();
		}
		if (getType().equals(LFWDirtoryTreeItem.PARENT_PUB_REF_FOLDER)) {
			LFWExplorerTreeBuilder lfwExplorer = LFWExplorerTreeBuilder
					.getInstance();
			File dsRefNodeFile = new File(file, WEBProjConstants.PUBLIC_REFNODE);
			lfwExplorer.initRefNodeSubTree(this, dsRefNodeFile, project
					.getLocation().toString());
		}
		// �������ݼ�
		else if (getType().equals(LFWDirtoryTreeItem.PARENT_PUB_DS_FOLDER)) {
			LFWExplorerTreeBuilder lfwExplorer = LFWExplorerTreeBuilder
					.getInstance();
			File dsFile = new File(file, WEBProjConstants.PUBLIC_DATASET);
			lfwExplorer.initDsSubTree(this, dsFile, project.getLocation()
					.toString());
		}
	}
	
}
