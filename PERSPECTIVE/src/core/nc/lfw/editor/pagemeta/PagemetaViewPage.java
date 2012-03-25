package nc.lfw.editor.pagemeta;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.widget.WidgetElementObj;
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
public class PagemetaViewPage extends LFWAbstractViewPage {
	
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setComp(comp);
		comp.setLayout(getSl());
		TabFolder voTabFolder = new TabFolder(comp, SWT.NONE);
		setVoTabFolder(voTabFolder);

		// �����ؼ�����ҳ
//		TabItem cellPropTabItem = new TabItem(voTabFolder, SWT.NONE);
//		cellPropTabItem.setText("����");
//		setCellPropTabItem(cellPropTabItem);
//		addCellPropItemControl();
		
	}
	
	
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof PropertySheet)
			return;
		// Ϊ��ǰ�༭������ѡ�ж���
		PagemetaEditor.getActivePagemetaEditor().setCurrentSelection(selection);
		// clearAttributeView();
		String partName = "ģ����ͼ";
		if (part == null || selection == null) {
			return;
		} else if (part instanceof PagemetaEditor) {
			Composite comp = getComp();
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof PagemetaElementPart) {
				PagemetaElementPart lfwEle = (PagemetaElementPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof WidgetElementObj) {
					WidgetElementObj widgetObj = (WidgetElementObj) model;
					LfwWidget widget = widgetObj.getWidget();

					getSl().topControl = getVoTabFolder();
					
//					partName = "(����)";
//					//TODO guoweic ���ӿؼ�����ҳ����
//					setCellPropertiesView(new CellPropertiesView(getVoTabFolder(), SWT.NONE));
//					addCellPropItemControl();
					
					// ���listener����ҳ����
					clearItemControl(getJsListenerItem());
					comp.layout();
					if (!comp.isVisible())
						comp.setVisible(true);
					
				}
			} else if (sel instanceof PagemetaGraphPart) {
				WebElement webElement = PagemetaEditor.getActivePagemetaEditor().getGraph().getPagemeta();
				addExtendAttributesView(webElement);
			} else {
				comp.setVisible(false);
			}
		}
	}
	
//	/**
//	 * @author guoweic
//	 */
//	private void clearAttributeView() {
//		if (getCellPropertiesView() != null) {
//			getCellPropertiesView().getTv().setInput(null);
//		}
//	}
	
}
