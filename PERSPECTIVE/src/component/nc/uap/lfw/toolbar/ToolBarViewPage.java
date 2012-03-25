package nc.uap.lfw.toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LfwCanvasGraphPart;
import nc.uap.lfw.core.comp.ToolBarComp;
import nc.uap.lfw.core.comp.ToolBarItem;
import nc.uap.lfw.parts.LFWElementPart;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbenchPart;

public class ToolBarViewPage extends LFWAbstractViewPage {

	private Composite comp = null;
	private StackLayout sl = new StackLayout();
	private TabFolder voTabFolder = null;
	private TabItem cellPropTabItem = null;
	private ToolBarPropertiesView cellPropertiesView = null;
	
	
	public void createControl(Composite parent) {
		comp = new Composite(parent, SWT.NONE);
		comp.setLayout(sl);
		voTabFolder = new TabFolder(comp, SWT.NONE);

		// �����ؼ�����ҳ
		cellPropTabItem = new TabItem(voTabFolder, SWT.NONE);
		cellPropTabItem.setText("�˵���");
		
		cellPropertiesView = new ToolBarPropertiesView(voTabFolder, SWT.NONE);
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
		if(ToolBarEditor.getActiveEditor() != null)
			ToolBarEditor.getActiveEditor().setCurrentSelection(selection);
		//String partName = "ģ����ͼ";
		if (part instanceof ToolBarEditor) {
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof LFWElementPart) {
				LFWElementPart lfwEle = (LFWElementPart) sel;
				Object model = lfwEle.getModel();
				if(model instanceof ToolBarElementObj){
					ToolBarElementObj toolbarObj = (ToolBarElementObj) model;
					ToolBarComp toolbar = toolbarObj.getToolbarComp();
					toolbarObj.setId(toolbar.getId());
					ToolBarItem[] items = toolbar.getElements();
					List<ToolBarItem> datas = Arrays.asList(items);
					List<ToolBarItem> dataList = new ArrayList<ToolBarItem>();
					for (int i = 0; i < datas.size(); i++) {
						dataList.add(datas.get(i));
					}
					getCellPropertiesView().getTv().setInput(dataList);
					getCellPropertiesView().setLfwElementPart(lfwEle);
					getCellPropertiesView().getTv().expandAll();
					sl.topControl = voTabFolder;
					//partName = "(����)";
					//TODO guoweic ���ӿؼ�����ҳ����
				
					// ���listener����ҳ����
					clearItemControl(getJsListenerItem());
					comp.layout();
					if (!comp.isVisible())
						comp.setVisible(true);
				}
			}else if(sel instanceof LfwCanvasGraphPart){
				addEventPropertiesView(ToolBarEditor.getActiveEditor().getToolbarElement().getToolbarComp().getEventConfs(), ToolBarEditor.getActiveEditor().getWidget().getControllerClazz());
			}
		}
	}


	public ToolBarPropertiesView getCellPropertiesView() {
		return cellPropertiesView;
	}

}
