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
 * ѡ��Ĭ�ϲ˵��� ��һҳ
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemPage1 extends WizardPage {

	/**
	 * ��ǰMenubar
	 */
	private MenubarComp menubar = null;
	
	/**
	 * ��ǰMenuItem
	 */
	private MenuItem menuItem = null;
	
	private DefaultMenuItemPanel1 mainContainer;
	
	protected DefaultMenuItemPage1(String pageName) {
		super(pageName);
	}

	
	public void createControl(Composite parent) {
		
		setTitle("Ĭ�ϲ˵��� ������");

		setPageComplete(false);
		
		mainContainer = new DefaultMenuItemPanel1(parent, SWT.NONE, menubar, menuItem);
		
		List<DefaultItem> selectedMenuItems = mainContainer.getSelectedMenuItems();
		// У��
		if (selectedMenuItems.size() > 0) {
			setPageComplete(true);
		}

		// ���Ӵ���ʱ������б仯�¼�����������0ʱ���ɽ�����һ������ʱ������ƶ��¼���
		mainContainer.getCtv().addSelectionChangedListener(new MySelectionChangeListener());
		
		setMessage("ѡ��Ĭ�ϲ˵���");
		
		setControl(mainContainer);
	}
	
	/**
	 * �����ѡ���иı��¼������ơ���һ����������ɡ���ť
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
	 * У��ҳ�����������Ƿ�����
	 */
	private void checkPageComplete() {
		setPageComplete(false);
		List<DefaultItem> selectedMenuItems = mainContainer.getSelectedMenuItems();
		// У��
		if (selectedMenuItems.size() > 0) {
			setPageComplete(true);
		}
	}
	
	/**
	 * �жϡ���һҳ���Ƿ����
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
