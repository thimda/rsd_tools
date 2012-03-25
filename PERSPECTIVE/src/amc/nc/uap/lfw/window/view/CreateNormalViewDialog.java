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
	 * ID输入框
	 */
//	private Text idText;
	/**
	 * 名称输入框
	 */
	private Text nameText;
	/**
	 * Controller类全路径输入框
	 */
	private Text controllerText;
	/**
	 * WindowID
	 */
	private String id;
	/**
	 * Window名称
	 */
	private String name;
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
	/**
	 * 流式布局
	 */
	private Button flowlayoutRadio;
	/**
	 * 是否流式布局
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
		//名称
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("名称:");
		nameText = new Text(container, SWT.NONE);
		nameText.setLayoutData(new GridData(220,20));
		nameText.setText("");
		if(isNotRefPublicView){
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
			//流式布局
			new Label(container, SWT.NONE).setText("流式布局：");
			Composite radioContainer = new Composite(container, SWT.NONE);
			radioContainer.setLayout(new GridLayout(2, false));
			radioContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
			flowlayoutRadio = new Button(radioContainer, SWT.RADIO);
			flowlayoutRadio.setSelection(true);
			flowlayoutRadio.setText("是");
			Button flowlayoutRadio1 = new Button(radioContainer, SWT.RADIO);
			flowlayoutRadio1.setSelection(false);
			flowlayoutRadio1.setText("否");
		}
		
		return container;
	}
	
	protected void okPressed() {
//		if(idText.getText() == null || idText.getText().trim().length() == 0){
//			MessageDialog.openError(null, "错误提示", "Id不能为空");
//			return;
//		}
//		setId(idText.getText().trim());
		//Name校验
		if(nameText.getText() == null || nameText.getText().trim().length() == 0){
			MessageDialog.openError(null, "错误提示", "名称不能为空");
			return;
		}
		setName(nameText.getText().trim());
		if(isCreateView){//创建View
			try{
				LFWTool.createNodeCheck(name, ILFWTreeNode.VIEW);
			}catch(Exception e){
				MainPlugin.getDefault().logError(e.getMessage(), e);
				MessageDialog.openError(null, "错误提示", e.getMessage());
				return;
			}
		}else{//创建PublicView
			Map<String, LfwWidget> publicViews = LFWAMCPersTool.getPublicViewsByContext();
			if(publicViews != null && publicViews.get(name) != null){
				MessageDialog.openError(null, WEBProjConstants.NEW_PUBLIC_VIEW, "已经存在ID为" + name + "的" + WEBProjConstants.PUBLIC_VIEW_SUB + "!");
				return;
			}
		}
		if(isNotRefPublicView){
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
			//流式布局
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
