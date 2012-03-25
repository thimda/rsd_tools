package nc.lfw.editor.menubar.wizard;

import java.util.List;

import nc.lfw.editor.menubar.DefaultItem;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * 选择默认菜单项 第一页
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemPage1 extends WizardPage {

	/**
	 * 当前Menubar
	 */
	private MenubarComp menubar = null;
	
	/**
	 * 当前MenuItem
	 */
	private MenuItem menuItem = null;
	
	private DefaultMenuItemPanel1 mainContainer;
	
	protected DefaultMenuItemPage1(String pageName) {
		super(pageName);
	}

	
	public void createControl(Composite parent) {
		
		setTitle("默认菜单项 设置向导");

		setPageComplete(false);
		
		mainContainer = new DefaultMenuItemPanel1(parent, SWT.NONE, menubar, menuItem);
		
		List<DefaultItem> selectedMenuItems = mainContainer.getSelectedMenuItems();
		// 校验
		if (selectedMenuItems.size() > 0) {
			setPageComplete(true);
		}

		// 增加触发时机表格行变化事件，行数大于0时，可进行下一步（暂时是鼠标移动事件）
		mainContainer.getCtv().addSelectionChangedListener(new MySelectionChangeListener());
		
		setMessage("选择默认菜单项");
		
		setControl(mainContainer);
	}
	
	/**
	 * 表格中选中行改变事件，控制“下一步”、“完成”按钮
	 * 
	 * @author guoweic
	 *
	 */
	private class MySelectionChangeListener implements ISelectionChangedListener {

		
		public void selectionChanged(SelectionChangedEvent event) {
			checkPageComplete();
		}
		
	}
	
	/**
	 * 校验页面输入内容是否完整
	 */
	private void checkPageComplete() {
		setPageComplete(false);
		List<DefaultItem> selectedMenuItems = mainContainer.getSelectedMenuItems();
		// 校验
		if (selectedMenuItems.size() > 0) {
			setPageComplete(true);
		}
	}
	
	/**
	 * 判断“下一页”是否可用
	 */
	public boolean canFlipToNextPage() {
        return super.canFlipToNextPage();
    }

	public DefaultMenuItemPanel1 getMainContainer() {
		return mainContainer;
	}

	public void setMenubar(MenubarComp menubar) {
		this.menubar = menubar;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

}
