package nc.uap.portal.managerapps.page;

import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.uap.portal.managerapps.ManagerAppsEditor;
import nc.uap.portal.managerapps.ManagerAppsElementObj;
import nc.uap.portal.managerapps.ManagerAppsElementPart;
import nc.uap.portal.om.ManagerNode;

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

/**
 * 功能节点 模型视图
 * 
 * @author dingrf
 * 
 */
public class ManagerNodeViewPage extends LFWAbstractViewPage {

	private Composite comp = null;
	private StackLayout sl = new StackLayout();
	private TabFolder voTabFolder = null;
	private TabItem cellPropTabItem = null;
	
	public void createControl(Composite parent) {
		comp = new Composite(parent, SWT.NONE);
		comp.setLayout(sl);
		voTabFolder = new TabFolder(comp, SWT.NONE);
		
		setVoTabFolder(voTabFolder);

		// 创建控件属性页
		cellPropTabItem = new TabItem(voTabFolder, SWT.NONE);
		cellPropTabItem.setText("功能");
		setCellPropTabItem(cellPropTabItem);
		addCellPropItemControl();
		sl.topControl = voTabFolder;
		comp.layout();
		
		setCellPropertiesView(new ManagerNodePropertiesView(voTabFolder, SWT.NONE));
	}

	public Control getControl() {
		return this.comp;
	}

	
	public void setFocus() {
		getControl().setFocus();
	}
	
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof PropertySheet)
			return;
		if (part == null || selection == null) {
			return;
		} 
		else if (part instanceof ManagerAppsEditor) {
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof ManagerAppsElementPart) {
				ManagerAppsElementPart mEle = (ManagerAppsElementPart) sel;
				Object model = mEle.getModel();
				if (model instanceof ManagerAppsElementObj) {
					ManagerAppsElementObj obj = (ManagerAppsElementObj) model;
					List<ManagerNode> nodes = obj.getManagerNode();
					((ManagerNodePropertiesView) getCellPropertiesView()).getTv().setInput(nodes);
					((ManagerNodePropertiesView) getCellPropertiesView()).setManagerAppsElementPart(mEle);
					((ManagerNodePropertiesView) getCellPropertiesView()).getTv().expandAll();
					
					sl.topControl = voTabFolder;
					setCellPropTabItem(cellPropTabItem);
					addCellPropItemControl();
					comp.layout();
					if (!comp.isVisible())
						comp.setVisible(true);
				}
			}
			else if(sel instanceof ManagerAppsElementObj){
				ManagerAppsElementObj obj = (ManagerAppsElementObj) sel;
				List<ManagerNode> nodes = obj.getManagerNode();
				((ManagerNodePropertiesView) getCellPropertiesView()).getTv().setInput(nodes);
				((ManagerNodePropertiesView) getCellPropertiesView()).getTv().expandAll();
				
				sl.topControl = voTabFolder;
				setCellPropTabItem(cellPropTabItem);
				addCellPropItemControl();
				comp.layout();
				if (!comp.isVisible())
					comp.setVisible(true);
			}
			else {
				comp.setVisible(false);
			}
		}
	}
}
