package nc.lfw.editor.menubar.action;

import java.util.ArrayList;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWMenubarCompTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWSeparateTreeItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * �½�Menubar����
 * 
 * @author guoweic
 *
 */
public class NewMenubarAction extends Action{
	
	private TreeViewer treeView;
	
	private boolean fromWidget;
	
	public boolean isFromWidget() {
		return fromWidget;
	}


	public void setFromWidget(boolean fromWidget) {
		this.fromWidget = fromWidget;
	}


	public NewMenubarAction() {
		super(WEBProjConstants.NEW_MENUBAR,PaletteImage.getCreateDsImgDescriptor());
	}

	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		InputDialog input = new InputDialog(shell, WEBProjConstants.NEW_MENUBAR, "����" + WEBProjConstants.MENUBAR_CN + "����","", null);
		if(input.open() == InputDialog.OK){
			String dirName = input.getValue();
			if(dirName != null && dirName.trim().length()>0){
				dirName =dirName.trim();
				try {
					MenubarComp menubar = new MenubarComp();
					menubar.setId(dirName);
					menubar.setMenuList(new ArrayList<MenuItem>());
					LFWMenubarCompTreeItem newItem = null;
					if(!fromWidget){
						PageMeta pm = LFWPersTool.getCurrentPageMeta();
						// �����Menubar��pagemeta.pm�ļ���
						pm.getViewMenus().addMenuBar(menubar);
						LFWPersTool.savePagemeta(pm);
						// ˢ�²˵�������
						newItem = view.refreshMenusManagerItem(treeView, LFWPersTool.getProjectPath(), pm, dirName);
					}
					//����widget
					else{
						LfwWidget widget = LFWPersTool.getCurrentWidget();
						// �����Menubar��widget.um�ļ���
						widget.getViewMenus().addMenuBar(menubar);
						LFWPersTool.saveWidget(widget);
						Tree tree = treeView.getTree();
						TreeItem[] tis = tree.getSelection();
						if (tis == null || tis.length == 0)
							return;
						LFWSeparateTreeItem menusItem = (LFWSeparateTreeItem) tis[0];
						newItem = new LFWMenubarCompTreeItem(
								menusItem, 
								LFWTool.getWEBProjConstantValue("COMPONENT_MENUBAR", menusItem.getLfwVersion()),
								menubar);
						newItem.setFromWidget(true);
					}
					
					// ��Menubar�༭��
					view.openMenubarEditor(newItem);
					
				} catch (Exception e) {
					String title =WEBProjConstants.NEW_MENUBAR;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}

	public void setTreeView(TreeViewer treeView) {
		this.treeView = treeView;
	}

}
