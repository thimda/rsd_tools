package nc.lfw.editor.menubar.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * 选择默认菜单项 第二页
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemPage2 extends WizardPage {
	
	private DefaultMenuItemPanel2 mainContainer;
	
	protected DefaultMenuItemPage2(String pageName) {
		super(pageName);
	}

	
	public void createControl(Composite parent) {
		
		setTitle("默认菜单项 设置向导");

		setPageComplete(false);
		
		mainContainer = new DefaultMenuItemPanel2(parent, SWT.NONE);
		
		// 增加输入框事件监听
		mainContainer.getPackageText().addModifyListener(new MyModifyListener());
		mainContainer.getClassPrefixText().addModifyListener(new MyModifyListener());
		
		mainContainer.getSourceFolderCombo().addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(checkCanFinished());
			}
		});
		
		setMessage("配置默认菜单项");
		
		setControl(mainContainer);
	}
	
	/**
	 * 输入框改变事件监听器，控制“下一步”、“完成”按钮
	 * 
	 * @author guoweic
	 *
	 */
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(checkCanFinished());
		}
	}
	
	/**
	 * 设置“完成”是否可用
	 */
	public boolean checkCanFinished() {
		String packageName = "";
		String sourceFolder = "";
		String classPrefix = "";
		packageName = mainContainer.getPackageText().getText().trim();
		sourceFolder = (String) mainContainer.getSourceFolderCombo().getData(mainContainer.getSourceFolderCombo().getText());
		classPrefix = mainContainer.getClassPrefixText().getText().trim();
		// 校验
		if (!"".equals(packageName) && !"".equals(sourceFolder) && !"".equals(classPrefix))
			return true;
		return false;
    }

	public DefaultMenuItemPanel2 getMainContainer() {
		return mainContainer;
	}

}
