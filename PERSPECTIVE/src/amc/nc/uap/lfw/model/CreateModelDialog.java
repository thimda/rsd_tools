/**
 * 
 */
package nc.uap.lfw.model;

import nc.lfw.editor.common.DialogWithTitle;
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
 * �½�Model�ڵ�Ի�����
 * @author chouhl
 *
 */
public class CreateModelDialog extends DialogWithTitle {
	
	/**
	 * ID�����
	 */
	private Text idText;
	/**
	 * ���������
	 */
	private Text nameText;
	/**
	 * ����Ԫ����ID�����
	 */
	private Text refIdText;
	/**
	 * ModelID
	 */
	private String modelId;
	/**
	 * Model����
	 */
	private String modelName;
	/**
	 * ����Ԫ����ID
	 */
	private String refId;
	
	public CreateModelDialog(String title) {
		super(null, title);
	}
	
	public CreateModelDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		//ID
		Label idLabel = new Label(container, SWT.NONE);
		idLabel.setText("Id:");
		idText = new Text(container, SWT.NONE);
		idText.setLayoutData(new GridData(220,20));
		idText.setText("");
		//����
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("����:");
		nameText = new Text(container, SWT.NONE);
		nameText.setLayoutData(new GridData(220,20));
		nameText.setText(modelName);
		nameText.setEditable(false);
		//����ID
		Label refIdLabel = new Label(container, SWT.NONE);
		refIdLabel.setText("����ID:");
		refIdText = new Text(container, SWT.NONE);
		refIdText.setLayoutData(new GridData(220,20));
		refIdText.setText(refId);
		refIdText.setEditable(false);
		return container;
	}
	
	protected void okPressed() {
		//IDУ��
		if(idText.getText() == null || idText.getText().trim().length() == 0){
			MessageDialog.openError(null, "������ʾ", "Id����Ϊ��");
			return;
		}
		setModelId(idText.getText().trim());
		try{
			LFWTool.idCheck(modelId);
			LFWTool.createNodeCheck(modelId, ILFWTreeNode.MODEL);
		}catch(Exception e){
			MessageDialog.openError(null, "������ʾ", e.getMessage());
			return;
		}
		//NameУ��
		if(nameText.getText() == null || nameText.getText().trim().length() == 0){
			MessageDialog.openError(null, "������ʾ", "���Ʋ���Ϊ��,��ѡ��Ԫ����.");
			return;
		}
		setModelName(nameText.getText().trim());
		//����IDУ��
		if(refIdText.getText() == null || refIdText.getText().trim().length() == 0){
			MessageDialog.openError(null, "������ʾ", "����Id����Ϊ��,��ѡ��Ԫ����.");
			return;
		}
		setRefId(refIdText.getText().trim());
		super.okPressed();
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}
