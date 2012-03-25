/**
 * 
 */
package nc.uap.lfw.window.view;

import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.ILFWTreeNode;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author chouhl
 *
 */
public class CreateNormalViewDialog extends DialogWithTitle {
	
	/**
	 * ID�����
	 */
//	private Text idText;
	/**
	 * ���������
	 */
	private Text nameText;
	/**
	 * Controller��ȫ·�������
	 */
	private Text controllerText;
	/**
	 * WindowID
	 */
	private String id;
	/**
	 * Window����
	 */
	private String name;
	/**
	 * Controller��ȫ·�� 
	 */
	private String controllerClazz;
	/**
	 * Դ�ļ���
	 */
	private String sourcePackage;
	/**
	 * ������ؼ�
	 */
	private Combo sourceFolderCombo;
	/**
	 * ��ʽ����
	 */
	private Button flowlayoutRadio;
	/**
	 * �Ƿ���ʽ����
	 */
	private boolean isFlowlayout = true;
	
	private boolean isNotRefPublicView = true;
	
	private boolean isCreateView = true;
	
	public CreateNormalViewDialog(String title) {
		super(null, title);
	}
	
	public CreateNormalViewDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		//ID
//		Label idLabel = new Label(container, SWT.NONE);
//		idLabel.setText("Id:");
//		idText = new Text(container, SWT.NONE);
//		idText.setLayoutData(new GridData(220,20));
//		idText.setText("");
		//����
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("����:");
		nameText = new Text(container, SWT.NONE);
		nameText.setLayoutData(new GridData(220,20));
		nameText.setText("");
		if(isNotRefPublicView){
			//Դ�ļ���
			new Label(container, SWT.NONE).setText("Դ�ļ��У�");
			sourceFolderCombo = new Combo(container, SWT.READ_ONLY);
			sourceFolderCombo.setLayoutData(new GridData(150, 1));
			sourceFolderCombo.removeAll();

			List<String> sourceFolderList = LFWTool.getAllRootPackage();
			for (String sourceFolder : sourceFolderList) {
				sourceFolderCombo.add(sourceFolder);
				sourceFolderCombo.setData(sourceFolder, sourceFolder);
			}
			if(sourceFolderCombo.getItemCount() > 0){
				sourceFolderCombo.select(0);
			}
			//Controller��ȫ·��
			Label controllerLabel = new Label(container, SWT.NONE);
			controllerLabel.setText("Controller��:");
			controllerText = new Text(container, SWT.NONE);
			controllerText.setLayoutData(new GridData(220,20));
			controllerText.setText("");
			//��ʽ����
			new Label(container, SWT.NONE).setText("��ʽ���֣�");
			Composite radioContainer = new Composite(container, SWT.NONE);
			radioContainer.setLayout(new GridLayout(2, false));
			radioContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
			flowlayoutRadio = new Button(radioContainer, SWT.RADIO);
			flowlayoutRadio.setSelection(true);
			flowlayoutRadio.setText("��");
			Button flowlayoutRadio1 = new Button(radioContainer, SWT.RADIO);
			flowlayoutRadio1.setSelection(false);
			flowlayoutRadio1.setText("��");
		}
		
		return container;
	}
	
	protected void okPressed() {
//		if(idText.getText() == null || idText.getText().trim().length() == 0){
//			MessageDialog.openError(null, "������ʾ", "Id����Ϊ��");
//			return;
//		}
//		setId(idText.getText().trim());
		//NameУ��
		if(nameText.getText() == null || nameText.getText().trim().length() == 0){
			MessageDialog.openError(null, "������ʾ", "���Ʋ���Ϊ��");
			return;
		}
		setName(nameText.getText().trim());
		if(isCreateView){//����View
			try{
				LFWTool.createNodeCheck(name, ILFWTreeNode.VIEW);
			}catch(Exception e){
				MainPlugin.getDefault().logError(e.getMessage(), e);
				MessageDialog.openError(null, "������ʾ", e.getMessage());
				return;
			}
		}else{//����PublicView
			Map<String, LfwWidget> publicViews = LFWAMCPersTool.getPublicViewsByContext();
			if(publicViews != null && publicViews.get(name) != null){
				MessageDialog.openError(null, WEBProjConstants.NEW_PUBLIC_VIEW, "�Ѿ�����IDΪ" + name + "��" + WEBProjConstants.PUBLIC_VIEW_SUB + "!");
				return;
			}
		}
		if(isNotRefPublicView){
			//Դ�ļ���У��
			if(sourceFolderCombo.getText().trim().length() == 0){
				MessageDialog.openError(null, "������ʾ", "��ѡ��Դ�ļ���");
				return;
			}
			setSourcePackage(sourceFolderCombo.getText().trim());
			//Controller��У��
			if(controllerText.getText().trim().length() == 0){
				MessageDialog.openError(null, "������ʾ", "Controller��ȫ·������Ϊ��");
				return;
			}
			controllerText.setText(LFWTool.upperClassName(controllerText.getText().trim()));
			setControllerClazz(controllerText.getText().trim());
			try{
				LFWTool.clazzCheck(controllerClazz);
			}catch(Exception e){
				MessageDialog.openError(null, "������ʾ", e.getMessage());
				return;
			}
			try{
				LFWTool.createNodeClassFileCheck(controllerClazz, sourcePackage);
			}catch(Exception e){
				MainPlugin.getDefault().logError(e.getMessage(), e);
				boolean temp = MessageDialog.openConfirm(null, "��Ϣ��ʾ", e.getMessage() + "�Ƿ����?");
				if(!temp){
					return;
				}else{
					controllerClazz = LFWTool.getExistWholeClassName(controllerClazz, sourcePackage);
				}
			}
			//��ʽ����
			setFlowlayout(flowlayoutRadio.getSelection());
		}else{
			setSourcePackage("");
			setControllerClazz("");
		}
		super.okPressed();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getControllerClazz() {
		return controllerClazz;
	}

	public void setControllerClazz(String controllerClazz) {
		this.controllerClazz = controllerClazz;
	}

	public String getSourcePackage() {
		return sourcePackage;
	}

	public void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}

	public void setNotRefPublicView(boolean isNotRefPublicView) {
		this.isNotRefPublicView = isNotRefPublicView;
	}

	public void setCreateView(boolean isCreateView) {
		this.isCreateView = isCreateView;
	}

	public boolean isFlowlayout() {
		return isFlowlayout;
	}

	public void setFlowlayout(boolean isFlowlayout) {
		this.isFlowlayout = isFlowlayout;
	}

}
