package nc.lfw.editor.menubar.page;

import java.util.List;

import nc.lfw.editor.contextmenubar.ContextMenuEditor;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.action.DownMenuItemPropAction;
import nc.lfw.editor.menubar.action.EditMenuItemAction;
import nc.lfw.editor.menubar.action.UpMenuItemPropAction;
import nc.lfw.editor.menubar.graph.MenuElementPart;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.perspective.editor.TableViewContentProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.part.EditorPart;

/**
 * ��ͼ�µ�Ԫ��������ͼ
 * 
 * @author guoweic
 *
 */
public class MenuItemPropertiesView extends Composite {
	
	private EditorPart editor = null;

	private TableViewer tv = null;
	
	private List<MenuItem> childList = null; 

	private MenuElementPart lfwElementPart = null;
	
	private MenuItemPropertiesView oThis;
	
	private boolean isMenuElement = false;
	
	
	public MenuElementPart getLfwElementPart() {
		return lfwElementPart;
	}

	public void setLfwElementPart(MenuElementPart lfwElementPart) {
		this.lfwElementPart = lfwElementPart;
	}

	public MenuItemPropertiesView(Composite parent, int style) {
		super(parent, style);
		this.oThis = this;
		createPartControl(this);
	}

	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		ViewForm vf = new ViewForm(parent, SWT.NONE);
		vf.setLayout(new FillLayout());
		tv = new TableViewer(vf, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tv.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		createColumn(table, layout, 70, SWT.NONE, "ID");
		createColumn(table, layout, 80, SWT.NONE, "��ʾ��");
//		createColumn(table, layout, 100, SWT.NONE, "���ܵ���״̬");
//		createColumn(table, layout, 100, SWT.NONE, "����ҵ��״̬");
//		createColumn(table, layout, 100, SWT.NONE, "�ɼ�����״̬");
//		createColumn(table, layout, 100, SWT.NONE, "�ɼ�ҵ��״̬");
		createColumn(table, layout, 100, SWT.NONE, "������Դ");
		createColumn(table, layout, 100, SWT.NONE, "��ԴĿ¼");
		createColumn(table, layout, 110, SWT.NONE, "ͼƬ");
		createColumn(table, layout, 110, SWT.NONE, "����ʱͼƬ");
		createColumn(table, layout, 110, SWT.NONE, "����ʱͼƬ");
		createColumn(table, layout, 110, SWT.NONE, "���η�");
		createColumn(table, layout, 80, SWT.NONE, "��ݼ�");
		createColumn(table, layout, 100, SWT.NONE, "��ݼ���ʾֵ");
		createColumn(table, layout, 80, SWT.NONE, "�ָ���");
		
		showPropertiesView();
		
		// ���ܲ���
//		Action addItem = new AddMenuItemAction(null);
//		Action delItem = new DelMenuItemAction(this);
		Action editItem = new EditMenuItemAction(this);
		Action upItem = new UpMenuItemPropAction(this);
		Action downItem = new DownMenuItemPropAction(this);
		// ���ܲ˵�
		MenuManager mm = new MenuManager();
//		mm.add(addItem);
		Menu menu = mm.createContextMenu(table);
		table.setMenu(menu);
		// ���ܹ�����
		ToolBar tb = new ToolBar(vf, SWT.FLAT);
		ToolBarManager tbm = new ToolBarManager(tb);
//		tbm.add(addItem);
//		tbm.add(delItem);
		tbm.add(editItem);
		tbm.add(upItem);
		tbm.add(downItem);
		
		tbm.update(true);
		vf.setTopLeft(tb);
		vf.setContent(tv.getControl());
		
		
		// ˫���¼��к󣬴򿪲����༭��
		tv.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				Action editItem = new EditMenuItemAction(oThis);
				editItem.run();
			}
		});
		
	}
	
	/**
	 * ��ʾ����
	 */
	public void showPropertiesView() {
		tv.setContentProvider(new TableViewContentProvider());
		tv.setLabelProvider(new MenuItemPropertiesViewProvider());
		
		tv.setInput(childList);
	}

	private void createColumn(Table table, TableLayout layout, int width,
			int align, String text) {
		layout.addColumnData(new ColumnWeightData(width));
		new TableColumn(table, align).setText(text);
	}

	/**
	 * �������
	 * @param item
	 */
	public void saveChildItem(MenuItem item) {
		// ���²����б�
		for (int i = 0, n = childList.size(); i < n; i++) {
			if (item.getId().equals(childList.get(i).getId())) {
				childList.remove(i);
				childList.add(i, item);
				break;
			}
		}
		// ˢ�±��
		tv.refresh();
		if(MenubarEditor.getActiveMenubarEditor() != null){
			MenubarEditor.getActiveMenubarEditor().setDirtyTrue();
			// ˢ��ͼ����ʾ
			MenubarEditor.getActiveMenubarEditor().refreshAllElementObj();
		}
		else if(ContextMenuEditor.getActiveMenubarEditor() != null){
			ContextMenuEditor.getActiveMenubarEditor().setDirtyTrue();
			ContextMenuEditor.getActiveMenubarEditor().refreshAllElementObj();
		}
		
	
	}
	
	/**
	 * ɾ������
	 * @param item
	public void deleteParam(MenuItem item) {
		// ���²����б�
		for (int i = 0, n = childList.size(); i < n; i++) {
			if (item.getId().equals(childList.get(i).getId())) {
				childList.remove(i);
				break;
			}
		}
		((CommandEditor)editor).setDirtyTrue();
		// ˢ�±��
		tv.setInput(childList);
		// ���±༭��ͷ����ʾ����
		refreshEditorControlHeadText();
	}
	 */
	
	public TableViewer getTv() {
		return tv;
	}

	public void setTv(TableViewer tv) {
		this.tv = tv;
	}

	public EditorPart getEditor() {
		return editor;
	}

	public void setEditor(EditorPart editor) {
		this.editor = editor;
	}

	public List<MenuItem> getChildList() {
		return childList;
	}

	public void setChildList(List<MenuItem> childList) {
		this.childList = childList;
	}

	public boolean isMenuElement() {
		return isMenuElement;
	}

	public void setMenuElement(boolean isMenuElement) {
		this.isMenuElement = isMenuElement;
	}

}
