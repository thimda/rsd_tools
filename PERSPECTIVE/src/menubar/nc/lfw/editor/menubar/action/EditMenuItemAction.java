package nc.lfw.editor.menubar.action;

import nc.lfw.editor.menubar.dialog.EditMenuItemDialog;
import nc.lfw.editor.menubar.page.MenuItemPropertiesView;
import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

/**
 * �༭�˵������
 * 
 * @author guoweic
 *
 */
public class EditMenuItemAction extends Action {
	
	private MenuItemPropertiesView view = null;
	
	public EditMenuItemAction(MenuItemPropertiesView view) {
		super("�༭");
		this.view = view;
	}
	
	public void run() {
		createEvents();
	}
	
	public void createEvents() {
		IStructuredSelection selection = (IStructuredSelection) view.getTv().getSelection();
		MenuItem item = (MenuItem) selection.getFirstElement();
		if(item == null)
			return;
		Shell shell = new Shell();
		EditMenuItemDialog dialog = new EditMenuItemDialog(shell, "�༭�˵�");
		dialog.setItem(item);
		
		//������Ӳ˵��������޸ķָ�������
		if(view.isMenuElement()){
			dialog.setCanChangeSep(true);
		}
		
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			item.setId(dialog.getId());
			item.setText(dialog.getText());
//			item.setOperatorStatusArray(dialog.getOperatorStatusArray());
//			item.setBusinessStatusArray(dialog.getBusinessStatusArray());
//			item.setOperatorVisibleStatusArray(dialog.getOperatorVisibleStatusArray());
//			item.setBusinessVisibleStatusArray(dialog.getBusinessVisibleStatusArray());
			item.setI18nName(dialog.getI18nName());
			item.setImgIcon(dialog.getImgIcon());
			item.setImgIconOn(dialog.getImgIconOn());
			item.setImgIconDisable(dialog.getImgIconDisabled());
			item.setHotKey(dialog.getHotKey());
			item.setDisplayHotKey(dialog.getDisplayHotKey());
			item.setModifiers(dialog.getModifier());
			item.setSep(dialog.isSep());
			item.setStateManager(dialog.getStateManager());
			view.saveChildItem(item);
		}
	}

}
