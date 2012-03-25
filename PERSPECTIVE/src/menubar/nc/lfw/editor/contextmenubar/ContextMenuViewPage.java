package nc.lfw.editor.contextmenubar;

import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.graph.MenuElementPart;
import nc.lfw.editor.menubar.graph.MenubarGraphPart;
import nc.lfw.editor.menubar.page.MenuItemPropertiesView;
import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

public class ContextMenuViewPage extends LFWAbstractViewPage {

	private Composite comp = null;
	private StackLayout sl = new StackLayout();
	private TabFolder voTabFolder = null;
	private TabItem cellPropTabItem = null;
	private MenuItemPropertiesView cellPropertiesView = null;
	
	
	public void createControl(Composite parent) {
		comp = new Composite(parent, SWT.NONE);
		comp.setLayout(sl);
		voTabFolder = new TabFolder(comp, SWT.NONE);

		// �����ؼ�����ҳ
		cellPropTabItem = new TabItem(voTabFolder, SWT.NONE);
		cellPropTabItem.setText("�˵���");
		
		cellPropertiesView = new MenuItemPropertiesView(voTabFolder, SWT.NONE);
		cellPropTabItem.setControl(cellPropertiesView);
		
		voTabFolder.setSelection(cellPropTabItem);
		
		setVoTabFolder(voTabFolder);
		
		sl.topControl = voTabFolder;
		comp.layout();
	}


	
	public Control getControl() {
		return this.comp;
	}

	
	public void setFocus() {
		getControl().setFocus();
	}

	
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// Ϊ��ǰ�༭������ѡ�ж���
		LFWBaseEditor.getActiveEditor().setCurrentSelection(selection);
		if (part instanceof ContextMenuEditor) {
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof MenuElementPart) {
				Object model = ((MenuElementPart)sel).getModel();
				if(model instanceof ContextMenuElementObj){
					ContextMenuElementObj menubarObj = (ContextMenuElementObj) model;
					ContextMenuComp menubar = menubarObj.getMenubar();
					List<MenuItem> items = menubar.getItemList();
					cellPropertiesView.setChildList(items);
					cellPropertiesView.getTv().setInput(items);
//					cellPropertiesView.getTv().expandAll();
					// ���listener����ҳ����
					clearItemControl(getJsListenerItem());
					voTabFolder.setSelection(cellPropTabItem);
					/**
					 * ѡ��ContextMenubar�����Listner�¼�
					 */
					// ȡ��������������ѡ��״̬
					LFWBaseEditor.getActiveEditor().getGraph().unSelectAllLabels();
					// ��ʾ����
					IViewPart[] views = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViews();
					PropertySheet sheet = null;
					for (int i = 0, n = views.length; i < n; i++) {
						if(views[i] instanceof PropertySheet) {
							sheet = (PropertySheet) views[i];
							break;
						}
					}
					if (sheet != null) {
						StructuredSelection select = new StructuredSelection(menubarObj);
						sheet.selectionChanged(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(), select);
					}
					
					// ������ʾListener����
					if(ContextMenuEditor.getActiveMenubarEditor() != null){
						((ContextMenuGrahp)ContextMenuEditor.getActiveMenubarEditor().getGraph())
								.reloadListenerFigure(menubar);
					}
				/******************/
				}
				else if(model instanceof MenuElementObj){
					MenuElementObj menuObj = (MenuElementObj) model;
					List<MenuItem> items = menuObj.getMenuItem().getChildList();
					cellPropertiesView.setChildList(items);
					cellPropertiesView.getTv().setInput(items);
//					cellPropertiesView.getTv().expandAll();
					// ���listener����ҳ����
					clearItemControl(getJsListenerItem());
					voTabFolder.setSelection(cellPropTabItem);
				}
				cellPropertiesView.setLfwElementPart((MenuElementPart) sel);
			} else if (sel instanceof MenubarGraphPart) {
				cellPropertiesView.setChildList(null);
				cellPropertiesView.getTv().setInput(null);
			}
		}
	}


	public MenuItemPropertiesView getCellPropertiesView() {
		return cellPropertiesView;
	}

}
