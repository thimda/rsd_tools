package nc.lfw.editor.widget;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.LfwWidget;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

/**
 * @author guoweic
 *
 */
public class WidgetViewPage extends LFWAbstractViewPage {
	
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setComp(comp);
		comp.setLayout(getSl());
		TabFolder voTabFolder = new TabFolder(comp, SWT.NONE);
		setVoTabFolder(voTabFolder);

		// 创建控件属性页
//		TabItem cellPropTabItem = new TabItem(voTabFolder, SWT.NONE);
//		cellPropTabItem.setText("属性");
//		setCellPropTabItem(cellPropTabItem);
//		addCellPropItemControl();
		
	}
	
	
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof PropertySheet)
			return;
		// 为当前编辑器设置选中对象
		WidgetEditor.getActiveWidgetEditor().setCurrentSelection(selection);
		// clearAttributeView();
		String partName = "模型视图";
		if (part == null || selection == null) {
			return;
		} else if (part instanceof WidgetEditor) {
			Composite comp = getComp();
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof WidgetElementPart) {
				WidgetElementPart lfwEle = (WidgetElementPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof WidgetElementObj) {
					WidgetElementObj widgetObj = (WidgetElementObj) model;
					LfwWidget widget = widgetObj.getWidget();

					getSl().topControl = getVoTabFolder();
					
//					partName = "(属性)";
//					//TODO guoweic 增加控件属性页内容
//					setCellPropertiesView(new CellPropertiesView(getVoTabFolder(), SWT.NONE));
//					addCellPropItemControl();
					
					// 清空listener属性页内容
					clearItemControl(getJsListenerItem());
					comp.layout();
					if (!comp.isVisible())
						comp.setVisible(true);
					
				}
			} else if (sel instanceof WidgetGraphPart) {
				WebElement webElement = WidgetEditor.getActiveWidgetEditor().getGraph().getWidget();
				addExtendAttributesView(webElement);
			} else {
				comp.setVisible(false);
			}
		}
	}

}
