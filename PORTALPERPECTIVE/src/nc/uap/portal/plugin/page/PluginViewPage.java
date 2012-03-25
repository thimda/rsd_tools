package nc.uap.portal.plugin.page;

import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.uap.portal.plugin.PluginEditor;
import nc.uap.portal.plugin.PluginElementObj;
import nc.uap.portal.plugin.PluginElementPart;
import nc.uap.portal.plugins.model.Extension;

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
 * 插件 模型视图
 * 
 * @author dingrf
 * 
 */
public class PluginViewPage extends LFWAbstractViewPage {

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
		cellPropTabItem.setText("扩展");
		setCellPropTabItem(cellPropTabItem);
		addCellPropItemControl();
		sl.topControl = voTabFolder;
		comp.layout();
		
		setCellPropertiesView(new PluginPropertiesView(voTabFolder, SWT.NONE));
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
		// 为当前编辑器设置选中对象
		if(PluginEditor.getActiveEditor() != null)
			PluginEditor.getActiveEditor().setCurrentSelection(selection);
		//partName = "模型视图";
		if (part == null || selection == null) {
			return;
		} 
		else if (part instanceof PluginEditor) {
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof PluginElementPart) {
				// (DatasetElementObj) dsobj = (DatasetElementObj)sel.;
				PluginElementPart eEle = (PluginElementPart) sel;
				Object model = eEle.getModel();
				if (model instanceof PluginElementObj) {
					PluginElementObj obj = (PluginElementObj) model;
					List<Extension> et = obj.getExtension();
					((PluginPropertiesView) getCellPropertiesView()).getTv().setInput(et);
					((PluginPropertiesView) getCellPropertiesView()).setPluginElementPart(eEle);
					((PluginPropertiesView) getCellPropertiesView()).getTv().expandAll();
					
					sl.topControl = voTabFolder;
					//partName = "(属性)";
					// 增加控件属性页内容
					setCellPropTabItem(cellPropTabItem);
					addCellPropItemControl();
					// 清空listener属性页内容
					//clearItemControl(getJsListenerItem());
					comp.layout();
					if (!comp.isVisible())
						comp.setVisible(true);
				}
			}
			else if(sel instanceof PluginElementObj){
				PluginElementObj obj = (PluginElementObj) sel;
				List<Extension> et = obj.getExtension();
				((PluginPropertiesView) getCellPropertiesView()).getTv().setInput(et);
				//((PluginPropertiesView) getCellPropertiesView()).setPluginElementPart();
				((PluginPropertiesView) getCellPropertiesView()).getTv().expandAll();
				
				sl.topControl = voTabFolder;
				//partName = "(属性)";
				// 增加控件属性页内容
				setCellPropTabItem(cellPropTabItem);
				addCellPropItemControl();
				// 清空listener属性页内容
				//clearItemControl(getJsListenerItem());
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
