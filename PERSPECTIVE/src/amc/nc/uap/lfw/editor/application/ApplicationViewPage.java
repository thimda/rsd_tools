/**
 * 
 */
package nc.uap.lfw.editor.application;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.editor.application.plug.ConnectorPropertiesView;
import nc.uap.lfw.editor.window.WindowObj;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

/**
 * 
 * Application������ͼ������
 * @author chouhl
 *
 */
public class ApplicationViewPage extends LFWAbstractViewPage {
	
//	private EventPropertiesView eventPropertieView = null;

	private TabItem connectorTabItem = null;
	
	private ConnectorPropertiesView connectorPropertiesView = null;
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setComp(comp);
		comp.setLayout(getSl());
		
		TabFolder voTabFolder = new TabFolder(comp, SWT.NONE);
		setVoTabFolder(voTabFolder);
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection){
		if (part instanceof PropertySheet){
			return;
		}
		// Ϊ��ǰ�༭������ѡ�ж���
		ApplicationEditor.getActiveEditor().setCurrentSelection(selection);
		if (part == null || selection == null) {
			return;
		} else if (part instanceof ApplicationEditor) {
			Composite comp = getComp();
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof ApplicationPart) {
				ApplicationPart lfwEle = (ApplicationPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof ApplicationObj || model instanceof WindowObj) {
					getSl().topControl = getVoTabFolder();
					// ���listener����ҳ����
					clearItemControl(getJsListenerItem());
					comp.layout();
					if (!comp.isVisible()){
						comp.setVisible(true);
					}
				}
				//����plugҳ
				Application appConf = ApplicationEditor.getActiveEditor().getGraph().getApplication();
				List<Connector> connectorList = appConf.getConnectorList();
				if (connectorList == null){
					connectorList = new ArrayList<Connector>();
					appConf.setConnectorList(connectorList);
				}
				if (connectorTabItem == null){
					connectorTabItem = new TabItem(getVoTabFolder(), SWT.NONE);
					connectorTabItem.setText("Plug");
				}
				connectorPropertiesView = new ConnectorPropertiesView(getVoTabFolder(), SWT.NONE, true, lfwEle);
				connectorPropertiesView.getTv().setInput(connectorList);
//				connectorPropertiesView.setApplicationPart(lfwEle);
				connectorPropertiesView.getTv().expandAll();
				connectorTabItem.setControl(connectorPropertiesView);
			} 
			else if (sel instanceof ApplicationGraphPart) {
				Application appConf = ApplicationEditor.getActiveEditor().getGraph().getApplication();
				setWebElement(appConf);
				addEventPropertiesView(appConf.getEventConfs(), appConf.getControllerClazz());
			} 
			else {
				comp.setVisible(false);
			}
		}
	}
	
//	public void addEventPropertiesView(EventConf[] eventConfs, String controllerClazz){
//		Composite comp = getComp();
//		getSl().topControl = getVoTabFolder();
//		// �����¼�����ҳ
//		TabItem jsListenerItem = getJsListenerItem();
//		if (jsListenerItem == null) {
//			jsListenerItem = new TabItem(getVoTabFolder(), SWT.NONE);
//			jsListenerItem.setText("�¼�");
//			setJsListenerItem(jsListenerItem);
//		}
//		eventPropertieView = new EventPropertiesView(getVoTabFolder(), SWT.NONE, eventConfs, controllerClazz, getWebElement());
//		jsListenerItem.setControl(eventPropertieView);
//		getVoTabFolder().setSelection(jsListenerItem);
//		// ��տؼ�����ҳ����
////		clearItemControl(cellPropTabItem);
//		// ��listener���Ա��ҳ���У���¼��ǰ�༭��ҳ��
//		eventPropertieView.setEditor((LFWBaseEditor) LFWBaseEditor.getActiveEditor());
//		if (comp != null) {
//			comp.layout();
//			if (!comp.isVisible()){
//				comp.setVisible(true);
//			}
//		}
//	}
//
//	public EventPropertiesView getEventPropertieView() {
//		return eventPropertieView;
//	}
//
//	public void setEventPropertieView(EventPropertiesView eventPropertieView) {
//		this.eventPropertieView = eventPropertieView;
//	}
	
}
