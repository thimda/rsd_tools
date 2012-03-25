/**
 * 
 */
package nc.uap.lfw.application;

import java.util.List;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.ILFWTreeNode;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * 新建Application节点对话框类
 * @author chouhl
 *
 */
public class CreateApplicationDialog extends DialogWithTitle {

	/**
	 * ID输入框
	 */
	private Text idText;
	/**
	 * 名称输入框
	 */
	private Text nameText;
	/**
	 * Controller类全路径输入框
	 */
	private Text controllerText;
	/**
	 * ApplicationID
	 */
	private String applicationId;
	/**
	 * Application名称
	 */
	private String applicationName;
	/**
	 * Controller类全路径 
	 */
	private String controllerClazz;
	/**
	 * 源文件夹
	 */
	private String sourcePackage;
	/**
	 * 下拉框控件
	 */
	private Combo sourceFolderCombo;
	
	public CreateApplicationDialog(String title) {
		super(null, title);
	}
	
	public CreateApplicationDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		//ID
		Label idLabel = new Label(container, SWT.NONE);
		idLabel.setText("Id:");
		idText = new Text(container, SWT.NONE);
		idText.setLayoutData(new GridData(220,20));
		idText.setText("");
		//名称
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("名称:");
		nameText = new Text(container, SWT.NONE);
		nameText.setLayoutData(new GridData(220,20));
		nameText.setText("");
		nameText.addFocusListener(new FocusListener(){
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			@Override
			public void focusGained(FocusEvent e) {
				if(nameText.getText() == null || nameText.getText().trim().length() == 0){
					nameText.setText(idText.getText());
				}
				nameText.selectAll();
			}
		});
		//源文件夹
		new Label(container, SWT.NONE).setText("源文件夹：");
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
		//Controller类全路径
		Label controllerLabel = new Label(container, SWT.NONE);
		controllerLabel.setText("Controller类:");
		controllerText = new Text(container, SWT.NONE);
		controllerText.setLayoutData(new GridData(220,20));
		controllerText.setText("");
		
		return container;
	}
	
	protected void okPressed() {
		//ID校验
		if(idText.getText().trim().length() == 0){
			MessageDialog.openError(null, "错误提示", "Id不能为空");
			return;
		}
		setApplicationId(idText.getText().trim());
		try{
			LFWTool.idCheck(applicationId);
			LFWTool.createNodeCheck(applicationId, ILFWTreeNode.APPLICATION);
		}catch(Exception e){
			MessageDialog.openError(null, "错误提示", e.getMessage());
			return;
		}
		//Name校验
		if(nameText.getText().trim().length() == 0){
			MessageDialog.openError(null, "错误提示", "名称不能为空");
			return;
		}
		setApplicationName(nameText.getText().trim());
		//源文件夹校验
		if(sourceFolderCombo.getText().trim().length() == 0){
			MessageDialog.openError(null, "错误提示", "请选择源文件夹");
			return;
		}
		setSourcePackage(sourceFolderCombo.getText().trim());
		//Controller类校验
		if(controllerText.getText().trim().length() == 0){
			MessageDialog.openError(null, "错误提示", "Controller类全路径不能为空");
			return;
		}
		controllerText.setText(LFWTool.upperClassName(controllerText.getText().trim()));
		setControllerClazz(controllerText.getText().trim());
		try{
			LFWTool.clazzCheck(controllerClazz);
		}catch(Exception e){
			MessageDialog.openError(null, "错误提示", e.getMessage());
			return;
		}
		try{
			LFWTool.createNodeClassFileCheck(controllerClazz, sourcePackage);
		}catch(Exception e){
			MainPlugin.getDefault().logError(e.getMessage(), e);
			boolean temp = MessageDialog.openConfirm(null, "信息提示", e.getMessage() + "是否继续?");
			if(!temp){
				return;
			}else{
				controllerClazz = LFWTool.getExistWholeClassName(controllerClazz, sourcePackage);
			}
		}
		super.okPressed();
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getSourcePackage() {
		return sourcePackage;
	}

	public void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}

	public String getControllerClazz() {
		return controllerClazz;
	}

	public void setControllerClazz(String controllerClazz) {
		this.controllerClazz = controllerClazz;
	}

}
