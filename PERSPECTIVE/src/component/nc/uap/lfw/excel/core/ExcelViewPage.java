package nc.uap.lfw.excel.core;

import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LfwCanvasGraphPart;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.excel.ExcelElementObj;
import nc.uap.lfw.form.core.FormPropertiesView;
import nc.uap.lfw.parts.LFWElementPart;
import nc.uap.lfw.perspective.views.CellPropertiesView;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

public class ExcelViewPage extends LFWAbstractViewPage {

	private Composite comp = null;
	private StackLayout sl = new StackLayout();
	private TabFolder voTabFolder = null;
	private TabItem cellPropTabItem = null;
	
	private ExcelPropertiesView ExcelPropertiesView = null;
	public ExcelPropertiesView getExcelPropertiesView() {
		return ExcelPropertiesView;
	}

	public void setExcelPropertiesView(ExcelPropertiesView ExcelPropertiesView) {
		this.ExcelPropertiesView = ExcelPropertiesView;
	}

	private FormPropertiesView formPropertiesView = null;
	
	public FormPropertiesView getFormPropertiesView() {
		return formPropertiesView;
	}

	public void setFormPropertiesView(FormPropertiesView formPropertiesView) {
		this.formPropertiesView = formPropertiesView;
	}

	public void createControl(Composite parent) {

		comp = new Composite(parent, SWT.NONE);
		comp.setLayout(sl);
		voTabFolder = new TabFolder(comp, SWT.NONE);
		
		setVoTabFolder(voTabFolder);

		// �����ؼ�����ҳ
		cellPropTabItem = new TabItem(voTabFolder, SWT.NONE);
		cellPropTabItem.setText("����");
		setCellPropTabItem(cellPropTabItem);
		addCellPropItemControl();
		sl.topControl = voTabFolder;
		comp.layout();
		
		setCellPropertiesView(new CellPropertiesView(voTabFolder, SWT.NONE));
	}

	public Control getControl() {
		return this.comp;
	}

	
	public void setFocus() {
		getControl().setFocus();
	}

	/**
	 * ����ExcelEditor������ҳǩ����
	 */
	private void addExcelPropItemControl() {
		Control control = null;
		try { 
			control = cellPropTabItem.getControl();
		} catch (Exception e) {
			return;
		} finally {
				if(control instanceof CellPropertiesView){
				cellPropTabItem.setControl(null);
				
			}
			ExcelPropertiesView = new ExcelPropertiesView(voTabFolder, SWT.NONE);
			cellPropTabItem.setControl(ExcelPropertiesView);
			voTabFolder.setSelection(cellPropTabItem);
		}
	}
	
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof PropertySheet)
			return;
		// Ϊ��ǰ�༭������ѡ�ж���
		if(ExcelEditor.getActiveEditor() != null)
			ExcelEditor.getActiveEditor().setCurrentSelection(selection);
		//partName = "ģ����ͼ";
		if (part == null || selection == null) {
			return;
		} 
		else if(part instanceof ExcelEditor){
			dealExcelEditor(selection);
		}
	}
	

	private void dealExcelEditor(ISelection selection){
		StructuredSelection ss = (StructuredSelection) selection;
		Object sel = ss.getFirstElement();
		if (sel instanceof LFWElementPart) {
			// (DatasetElementObj) dsobj = (DatasetElementObj)sel.;
			LFWElementPart lfwEle = (LFWElementPart) sel;
			Object model = lfwEle.getModel();
			if (model instanceof ExcelElementObj) {
				addExcelPropItemControl();
				ExcelElementObj Excelobj = (ExcelElementObj) model;
				ExcelComp Excelcomp = Excelobj.getExcelComp();
				Excelobj.setId(Excelcomp.getId());
				List<IExcelColumn> datas = Excelcomp.getColumnList();
				getExcelPropertiesView().getTv().setInput(datas);
				getExcelPropertiesView().setLfwElementPart(lfwEle);
				getExcelPropertiesView().getTv().expandAll();
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
			addEventPropertiesView(ExcelEditor.getActiveEditor().getExcelElement().getExcelComp().getEventConfs(), ExcelEditor.getActiveEditor().getWidget().getControllerClazz());
		}else {
			comp.setVisible(false);
		}
	}
}
