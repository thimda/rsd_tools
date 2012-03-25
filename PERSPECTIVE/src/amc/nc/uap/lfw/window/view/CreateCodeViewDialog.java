/**
 * 
 */
package nc.uap.lfw.window.view;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 *
 */
public class CreateCodeViewDialog extends DialogWithTitle {
	
	/**
	 * ID�����
	 */
//	private Text idText;
	/**
	 * ���������
	 */
	private Text nameText;
	/**
	 * Provider��ȫ·�������
	 */
	private Text providerText;
	
	/**
	 * UI Provider��ȫ·�������
	 */
	private Text uiProviderText;
	
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
	private String providerClazz;
	private String uiProviderClazz;
	private boolean isCreateView;
	private boolean isNotRefPublicView;
	
	public CreateCodeViewDialog(String title) {
		super(null, title);
	}
	
	public CreateCodeViewDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		//����
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("����:");
		nameText = new Text(container, SWT.NONE);
		nameText.setLayoutData(new GridData(220,20));
		nameText.setText("");
		//Controller��ȫ·��
		Label controllerLabel = new Label(container, SWT.NONE);
		controllerLabel.setText("Provider��:");
		providerText = new Text(container, SWT.NONE);
		providerText.setLayoutData(new GridData(220,20));
		providerText.setText("");
		
		//Controller��ȫ·��
		Label uiProviderLabel = new Label(container, SWT.NONE);
		uiProviderLabel.setText("UI Provider��:");
		uiProviderText = new Text(container, SWT.NONE);
		uiProviderText.setLayoutData(new GridData(220,20));
		uiProviderText.setText("");
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
			}
			catch(Exception e){
				MainPlugin.getDefault().logError(e.getMessage(), e);
				MessageDialog.openError(null, "������ʾ", e.getMessage());
				return;
			}
		}
		else{//����PublicView
			Map<String, LfwWidget> publicViews = LFWAMCPersTool.getPublicViewsByContext();
			if(publicViews != null && publicViews.get(name) != null){
				MessageDialog.openError(null, WEBProjConstants.NEW_PUBLIC_VIEW, "�Ѿ�����IDΪ" + name + "��" + WEBProjConstants.PUBLIC_VIEW_SUB + "!");
				return;
			}
		}
		if(isNotRefPublicView){
			//Controller��У��
			if(providerText.getText().trim().length() == 0){
				MessageDialog.openError(null, "������ʾ", "Provider��ȫ·������Ϊ��");
				return;
			}
			providerText.setText(LFWTool.upperClassName(providerText.getText().trim()));
			setProviderClazz(providerText.getText().trim());
			
			//Controller��У��
			if(uiProviderText.getText().trim().length() == 0){
				MessageDialog.openError(null, "������ʾ", "UI Provider��ȫ·������Ϊ��");
				return;
			}
			uiProviderText.setText(LFWTool.upperClassName(uiProviderText.getText().trim()));
			setUiProviderClazz(uiProviderText.getText().trim());
			try{
				LFWTool.clazzCheck(providerClazz);
			}catch(Exception e){
				MessageDialog.openError(null, "������ʾ", e.getMessage());
				return;
			}
		}
		else{
			setProviderClazz("");
			setUiProviderClazz("");
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

	public String getProviderClazz() {
		return providerClazz;
	}

	public void setProviderClazz(String controllerClazz) {
		this.providerClazz = controllerClazz;
	}


	public void setNotRefPublicView(boolean isNotRefPublicView) {
		this.isNotRefPublicView = isNotRefPublicView;
	}

	public void setCreateView(boolean isCreateView) {
		this.isCreateView = isCreateView;
	}

	public String getUiProviderClazz() {
		return uiProviderClazz;
	}

	public void setUiProviderClazz(String uiProviderClazz) {
		this.uiProviderClazz = uiProviderClazz;
	}

}
