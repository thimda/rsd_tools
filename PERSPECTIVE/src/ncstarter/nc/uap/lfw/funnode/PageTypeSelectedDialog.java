package nc.uap.lfw.funnode;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.common.tools.LfwGlobalEditorInfo;
import nc.lfw.editor.menubar.ListenerClassInfo;
import nc.uap.lfw.perspective.action.NcPatternGenerator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
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
 * 页面类型选择对话框
 * @author zhangxya
 *
 */
public class PageTypeSelectedDialog extends Dialog {

	//页面类型
	private Integer pageType;
	
	//包名
	private String rootPackage;
	
	//是否将菜单生成在表体
	private boolean isBody;
	
	//是否支持多状态显示
	private boolean isSupportMultiVisible;
	
	//根路径控件
	private Combo rootPathField = null;
	
	//生成类所在的包控件
	private Text listenerPackage;
	
	//页面类型选择控件
	private Combo pagetypeComp = null;
	
	//是否将菜单生成在表体上按钮
	private Button bodyToolbar;
	
	//是否支持多状态显示
	private Button suportMultiVisibilty;
	

	public PageTypeSelectedDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Point getInitialSize() {
		return new Point(418,200); 
	}

	
	public boolean isBody(){
		return isBody;
	}
	
	public Integer getPageType(){
		return pageType;
	}
	
	public String getRootPackage(){
		return rootPackage;
	}
	
	public boolean isSupportMultiVisible(){
		return isSupportMultiVisible;
	}
	
	protected void okPressed() {
		pageType = (Integer)pagetypeComp.getData(pagetypeComp.getText());
		rootPackage = (String) rootPathField.getText();
		isBody = bodyToolbar.getSelection();
		isSupportMultiVisible = suportMultiVisibilty.getSelection();
		String lisPackage = listenerPackage.getText();
		if(lisPackage == null || lisPackage.trim().equals("")){
			MessageDialog.openError(null, "错误提示", "请输入类所在包名!");
			return;
		}
		ListenerClassInfo info = new ListenerClassInfo();
		info.setPackageName(lisPackage);
		String key = LFWPersTool.getCurrentProject().getName() + "." + LFWPersTool.getCurrentFolderPath() + "." + NcPatternGenerator.GENERATElISTENER;
		LfwGlobalEditorInfo.addAttribute(key, info);
		super.okPressed();
	}

	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label pageTypeLabel = new Label(container, SWT.NONE);
		pageTypeLabel.setText("页面类型选择:");
		pagetypeComp = new Combo(container, SWT.READ_ONLY);
		pagetypeComp.setLayoutData(new GridData(212,15));
//		pagetypeComp.add("管理主子型_卡片优先");
//		pagetypeComp.setData("管理主子型_卡片优先", ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST);
		
//		pagetypeComp.add("管理主子型_列表优先");
//		pagetypeComp.setData("管理主子型_列表优先", ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST);
//		
//		pagetypeComp.add("管理单表头型_卡片优先");
//		pagetypeComp.setData("管理单表头型_卡片优先", ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST);
//		
//		pagetypeComp.add("管理单表头型_列表优先");
//		pagetypeComp.setData("管理单表头型_列表优先", ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST);
//		
//		pagetypeComp.add("卡片主子型");
//		pagetypeComp.setData("卡片主子型", ExtAttrConstants.PAGETYPE_CARD_MASCHI);
//		pagetypeComp.add("卡片单表头型");
//		pagetypeComp.setData("卡片单表头型", ExtAttrConstants.PAGETYPE_CARD_SINGAL);
//		pagetypeComp.add("列表主子型");
//		pagetypeComp.setData("列表主子型", ExtAttrConstants.PAGETYPE_LIST_MASCHI);
//		pagetypeComp.add("列表单表头型");
//		pagetypeComp.setData("列表单表头型", ExtAttrConstants.PAGETYPE_LIST_SINGAL);
//		pagetypeComp.select(0);
		
		Label rootPath = new Label(container, SWT.NONE);
		rootPath.setText("根路径:");
		rootPathField = new Combo(container, SWT.READ_ONLY);
		rootPathField.setLayoutData(new GridData(200,15));
		String[] rootPackages = LFWPersTool.getAllRootPackage();
		if(rootPackages !=  null)
			rootPathField.setItems(rootPackages);
		rootPathField.select(0);
		
		Label listenerpackage = new Label(container, SWT.NONE);
		listenerpackage.setText("生成类包名:");
		listenerPackage = new Text(container, SWT.NONE);
		listenerPackage.setLayoutData(new GridData(222,15));
		String key = LFWPersTool.getCurrentProject().getName() + "." + LFWPersTool.getCurrentFolderPath() + "." + "GenerateListener";
		ListenerClassInfo listener = (ListenerClassInfo) LfwGlobalEditorInfo.getAttr(key);
		String packageName = null;
		if(listener != null){
			packageName  = listener.getPackageName();
		}
		if(packageName != null)
			listenerPackage.setText(packageName);
		
		bodyToolbar = new Button(container, SWT.CHECK);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		bodyToolbar.setText("是否将菜单生成在表体上");
		if(pagetypeComp.getText().equals("管理主子型_卡片优先") || pagetypeComp.getText().equals("管理主子型_列表优先")
				|| pagetypeComp.getText().equals("卡片主子型") || pagetypeComp.getText().equals("列表主子型"))
			bodyToolbar.setVisible(true);
			
		else 
			bodyToolbar.setVisible(false);
		//支持多状态显示
		suportMultiVisibilty = new Button(container, SWT.CHECK);
		suportMultiVisibilty.setText("是否支持多状态显示");
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		suportMultiVisibilty.setLayoutData(gridData);
		return container;
	}

}

