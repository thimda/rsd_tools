package nc.lfw.editor.menubar.wizard;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.menubar.DefaultItem;
import nc.lfw.editor.menubar.DefaultMenuItemCreator;
import nc.lfw.editor.menubar.page.DefaultMenuItemViewProvider;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.perspective.editor.TableViewContentProvider;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 选择默认菜单项 第一页内容
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemPanel1 extends Canvas {

	/**
	 * 当前Menubar
	 */
	private MenubarComp menubar;
	
	/**
	 * 当前MenuItem
	 */
	private MenuItem menuItem;
	
	/**
	 * 选中的菜单项
	 */
	private List<DefaultItem> selectedMenuItems = new ArrayList<DefaultItem>();
	
	private CheckboxTableViewer ctv;

	public DefaultMenuItemPanel1(Composite parent, int style, MenubarComp menubar, MenuItem menuItem) {
		super(parent, style);
		this.menubar = menubar;
		this.menuItem = menuItem;
		initUI();
	}

	private void initUI() {
		// 总体布局
		GridLayout mainLayout = new GridLayout(4, false);
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// 所有默认菜单项
		List<DefaultItem> defaultMenuItems = DefaultMenuItemCreator.getDefaultMenuItems();
		// 原有菜单项
		List<MenuItem> oldMenuItemList = null;
		if (null != menubar)
			oldMenuItemList = menubar.getMenuList();
		else if (null != menuItem)
			oldMenuItemList = menuItem.getChildList();

		
		ViewForm vf = new ViewForm(this, SWT.NONE);
		vf.setLayout(new GridLayout(1, false));
		vf.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		TableViewer tv = new TableViewer(vf, SWT.CHECK | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		ctv = new CheckboxTableViewer(tv.getTable());
		Table table = tv.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		createColumn(table, layout, 20, SWT.NONE, "选择");
		createColumn(table, layout, 60, SWT.NONE, "显示名");
		
		tv.setContentProvider(new TableViewContentProvider());
		tv.setLabelProvider(new DefaultMenuItemViewProvider());
		
		tv.setInput(defaultMenuItems);
		
		for (DefaultItem defaultItem : defaultMenuItems) {
			if (null != oldMenuItemList) {
				for (MenuItem temp : oldMenuItemList) {
					if (temp.getId().equals(defaultItem.getId())) {
						//TODO
//						selectedMenuItems.add(defaultItem);
						tv.remove(defaultItem);
					}
				}
			}
			
		}
//		ctv.setCheckedElements(selectedMenuItems.toArray());

		vf.setContent(tv.getControl());
		
		this.getData();
		
	}
	
	/**
	 * 获取选中的默认表格项
	 * @return
	 */
	public List<DefaultItem> getSelectedMenuItems() {
		selectedMenuItems.clear();
		Object[] checkedElementArray = ctv.getCheckedElements();
		for (Object item : checkedElementArray) {
			if (item instanceof DefaultItem)
				selectedMenuItems.add((DefaultItem) item);
		}
		return selectedMenuItems;
	}
	
	private void createColumn(Table table, TableLayout layout, int width,
			int align, String text) {
		layout.addColumnData(new ColumnWeightData(width));
		new TableColumn(table, align).setText(text);
	}

	public CheckboxTableViewer getCtv() {
		return ctv;
	}

}
