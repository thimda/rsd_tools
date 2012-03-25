package nc.lfw.editor.menubar.dialog;

import nc.lfw.editor.common.DialogWithTitle;
import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * �༭�˵���Ի���
 * 
 * @author guoweic
 *
 */
public class EditMenuItemDialog extends DialogWithTitle {
	
	private MenuItem item;
	
	private MenuItemPanel mainContainer;
	
	private String id;
	
	private String text;
	
//	private String operatorStatusArray;
//
//	private String businessStatusArray;
//	
//	private String operatorVisibleStatusArray;
//
//	public String getOperatorVisibleStatusArray() {
//		return operatorVisibleStatusArray;
//	}
//
//	public void setOperatorVisibleStatusArray(String operatorVisibleStatusArray) {
//		this.operatorVisibleStatusArray = operatorVisibleStatusArray;
//	}
//
//	public String getBusinessVisibleStatusArray() {
//		return businessVisibleStatusArray;
//	}
//
//	public void setBusinessVisibleStatusArray(String businessVisibleStatusArray) {
//		this.businessVisibleStatusArray = businessVisibleStatusArray;
//	}
//
//	private String businessVisibleStatusArray;
	
	private String i18nName;
	
	private String imgIcon;
	private String imgIconOn;
	private String imgIconDisabled;
	
	private int modifier;
	//�ȼ�
	private String hotKey = null;
	//�ȼ���ʾ����
	private String displayHotKey = null;
	
	private boolean isSep = false;
	
	private boolean canChangeSep = true;
	
	private String stateManager = "";
	
	public EditMenuItemDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}
	
	protected Point getInitialSize() {
		return new Point(520, 320);
	}
	
	
	public Control createDialogArea(Composite parent) {

		mainContainer = new MenuItemPanel(parent, SWT.NONE, item);
		
		mainContainer.getSepCombo().setEnabled(canChangeSep);		
		
		return mainContainer;
	}
	
	protected void okPressed() {
		if (!"".equals(mainContainer.getIdText().getText().trim())) {
			id = mainContainer.getIdText().getText().trim();
			
			text = mainContainer.getTextText().getText().trim();
//			operatorStatusArray = mainContainer.getOperateStatusArrayText().getText().trim();
//			businessStatusArray = mainContainer.getBusinessStatusArrayText().getText().trim();
//			//�ɼ�״̬
//			operatorVisibleStatusArray = mainContainer.getOperateVisibleStatusArrayText().getText().trim();
//			businessVisibleStatusArray = mainContainer.getBusinessVisibleStatusArrayText().getText().trim();
			i18nName = mainContainer.getI18nNameText().getText().trim();
			imgIcon = mainContainer.getImgIconText().getText().trim();
			imgIconOn = mainContainer.getImgIconOnText().getText().trim();
			imgIconDisabled = mainContainer.getImgIconDisabledText().getText().trim();
			
			hotKey = mainContainer.getHotKey();
			displayHotKey = mainContainer.getDisplayHotKey();
			modifier = mainContainer.getModifier();
			isSep = mainContainer.getSep();
			stateManager = mainContainer.getState();			
			super.okPressed();
			
		} else {
			MessageDialog.openInformation(null, "��ʾ", "Id����Ϊ��");
			mainContainer.getIdText().setFocus();
		}
	}
	
	public void initDialogArea() {
		
	}

	public MenuItemPanel getMainContainer() {
		return mainContainer;
	}

	public String getId() {
		return id;
	}

	public int getModifier(){
		return modifier;
	}
	
	public String getText() {
		return text;
	}

//	public String getOperatorStatusArray() {
//		return operatorStatusArray;
//	}
//
//	public String getBusinessStatusArray() {
//		return businessStatusArray;
//	}

	public String getI18nName() {
		return i18nName;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}

	public String getImgIcon() {
		return imgIcon;
	}

	public String getImgIconOn() {
		return imgIconOn;
	}

	public String getImgIconDisabled() {
		return imgIconDisabled;
	}

	public String getHotKey() {
		return hotKey;
	}

	public String getDisplayHotKey() {
		return displayHotKey;
	}

	public boolean isSep() {
		return isSep;
	}

	public boolean isCanChangeSep() {
		return canChangeSep;
	}

	public void setCanChangeSep(boolean canChangeSep) {
		this.canChangeSep = canChangeSep;
	}

	public String getStateManager() {
		return stateManager;
	}

	public void setStateManager(String stateManager) {
		this.stateManager = stateManager;
	}
	

}
