package nc.uap.portal.events;


import java.util.EventObject;
import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.perspective.views.ILFWViewPage;
import nc.uap.portal.container.om.EventDefinition;
import nc.uap.portal.container.om.PortletApplicationDefinition;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.events.page.EventsViewPage;
import nc.uap.portal.perspective.PortalPlugin;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * �¼��༭��
 * 
 * @author dingrf
 * 
 */
public class EventsEditor extends GraphicalEditor {

	/**�¼�Ԫ��*/
	private EventsElementObj contents = new EventsElementObj();
	
	/**����ģ����ͼ*/
	private LFWAbstractViewPage viewPage;

	public LFWAbstractViewPage getViewPage() {
		return viewPage;
	}
	
	public EventsEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
	}

	/**
	 * �ļ��仯֪ͨ����
	 */
	@Override
	public void commandStackChanged(EventObject arg0) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(arg0);
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}
	
	/**
	 * ȡ��ǰ���EventsEditor
	 * @return
	 */
	public static EventsEditor getActiveEditor() {
		IWorkbenchPage page = PortalPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if (page != null) {
			editor = page.getActiveEditor();
		}
		if (editor != null && editor instanceof EventsEditor) {
			return (EventsEditor) editor;
		} else {
			return null;
		}
	}
	
	@Override
	public boolean isDirty() {
		if (super.isDirty())
			return true;
		return getCommandStack().isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		EventsEditorInput pEditorInput = (EventsEditorInput) input;
		PortletApplicationDefinition portletApp = pEditorInput.getPortletApp();
		
		EventsElementObj portletAppEle = new EventsElementObj();
		portletAppEle.setPortletApp(portletApp);
		portletAppEle.setEventDefinitions((List<EventDefinition>)portletApp.getEventDefinitions());
		
		portletAppEle.setLocation(new Point(100, 100));
		contents = portletAppEle;
	}

	public void executComand(Command cmd){
		super.getCommandStack().execute(cmd);
	}
	
	/**
	 *EventsEditor ͼ�γ�ʼ�� 
	 */
	@Override
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setContents(this.contents);
	}
	
	@Override	
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScalableFreeformRootEditPart rootEditpart = new ScalableFreeformRootEditPart();
		getGraphicalViewer().setRootEditPart(rootEditpart);
		getGraphicalViewer().setEditPartFactory(new EventsEditPartFactory());
	}

	/**
	 * ����
	 * 
	 * @param monitor
	 */
	public void doSave(IProgressMonitor monitor) {
		EventsElementObj portletAppEle = (EventsElementObj) contents;
		savePortletApp(portletAppEle.getPortletApp());
	}

	/**
	 * ����portlet-app����
	 * 
	 * @param portletApp
	 */
	@SuppressWarnings("unchecked")
	public void savePortletApp(PortletApplicationDefinition portletApp) {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		PortletApplicationDefinition  pa = PortalConnector.getPortletApp(projectPath,projectModuleName);
		if (pa == null){
			pa = portletApp;
		}
		else{
			pa.setDefaultNamespace(portletApp.getDefaultNamespace());
			pa.getEventDefinitions().clear();
			pa.getEventDefinitions().addAll((List)portletApp.getEventDefinitions());
		}
		PortalConnector.savePortletAppToXml(projectPath, projectModuleName, pa);
		getCommandStack().markSaveLocation();
	}
	
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class cls) {
		if (cls == ILFWViewPage.class) {
			this.viewPage = new EventsViewPage();
			return this.viewPage;
		}
		return super.getAdapter(cls);
	}
}
