package nc.uap.portal.managerapps;


import java.util.EventObject;
import java.util.List;
 
import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.perspective.views.ILFWViewPage;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.managerapps.page.ManagerNodeViewPage;
import nc.uap.portal.om.ManagerApps;
import nc.uap.portal.om.ManagerCategory;
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
 * 功能节点编辑器
 * 
 * @author dingrf
 * 
 */
public class ManagerAppsEditor extends GraphicalEditor {

	/**ManagerApps model对象*/
	private ManagerAppsElementObj contents = new ManagerAppsElementObj();
	
	/**功能属性模型视图*/
	private LFWAbstractViewPage viewPage;

	public LFWAbstractViewPage getViewPage() {
		return viewPage;
	}
	
	public ManagerAppsEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
	}

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
	 * 取当前活动的ManagerAppsEditor
	 * @return
	 */
	public static ManagerAppsEditor getActiveEditor() {
		IWorkbenchPage page = PortalPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if (page != null) {
			editor = page.getActiveEditor();
		}
		if (editor != null && editor instanceof ManagerAppsEditor) {
			return (ManagerAppsEditor) editor;
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
		ManagerAppsEditorInput pEditorInput = (ManagerAppsEditorInput) input;
		ManagerCategory managerCategory= pEditorInput.getManagerCategory();
		
		ManagerAppsElementObj managerAppsEle = new ManagerAppsElementObj();
		managerAppsEle.setCurrentManagerCategory(managerCategory);
		managerAppsEle.setManagerNode(managerCategory.getNode());
		managerAppsEle.setManagerId(pEditorInput.getManagerId());
		
		managerAppsEle.setLocation(new Point(100, 100));
		contents = managerAppsEle;
	}
	
	public void executComand(Command cmd){
		super.getCommandStack().execute(cmd);
	}
	
	/**
	 *ManagerAppsEditor 图形初始化 
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
		getGraphicalViewer().setEditPartFactory(new ManagerAppsEditPartFactory());
	}

	/**
	 * 保存
	 * 
	 * @param monitor
	 */
	public void doSave(IProgressMonitor monitor) {
		ManagerAppsElementObj managerAppsEle = (ManagerAppsElementObj) contents;
		saveManagerApps(managerAppsEle.getCurrentManagerCategory(),managerAppsEle.getManagerId());
	}

	/**
	 * 保存 ManagerApps
	 */
	public void saveManagerApps(ManagerCategory managerCategory,String managerId) {
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		ManagerApps managerApps = PortalConnector.getManagerApps(projectPath, projectModuleName, managerId);
		if (managerApps == null){
			managerApps = new ManagerApps();
			managerApps.setId(managerId);
			managerApps.addPortletDisplayList(managerCategory);
		}
		else{
			updateManagerNode(managerApps.getCategory(),managerCategory);
		}
		PortalConnector.saveManagerAppsToXml(projectPath, projectModuleName, managerApps);
		getCommandStack().markSaveLocation();
	}

	/**
	 * 更新 managerApps 中对应的 ManagerNode
	 * 
	 * @param mc
	 * @param managerCategory
	 */
	private boolean updateManagerNode(List<ManagerCategory> managerCategories,
			ManagerCategory managerCategory) {
		for (ManagerCategory mc: managerCategories){
			if (managerCategory.getId().equals(mc.getId())){
				mc.getNode().clear();
				mc.getNode().addAll(managerCategory.getNode());
				return true;
			}
			else if (updateManagerNode(mc.getCategory(),managerCategory)){
					return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class cls) {
		if (cls == ILFWViewPage.class) {
			this.viewPage = new ManagerNodeViewPage();
			return this.viewPage;
		}
		return super.getAdapter(cls);
	}
	
}
