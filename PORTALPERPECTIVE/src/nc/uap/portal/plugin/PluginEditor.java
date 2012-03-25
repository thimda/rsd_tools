package nc.uap.portal.plugin;


import java.util.EventObject;
import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.perspective.views.ILFWViewPage;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.perspective.PortalPlugin;
import nc.uap.portal.plugin.action.DelPointAction;
import nc.uap.portal.plugin.action.NewPointAction;
import nc.uap.portal.plugin.page.PluginViewPage;
import nc.uap.portal.plugins.model.ExPoint;
import nc.uap.portal.plugins.model.Extension;
import nc.uap.portal.plugins.model.PtPlugin;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * 插件编辑器
 * 
 * @author dingrf
 * 
 */
public class PluginEditor extends GraphicalEditor {

	/**
	 * 插件 model对象
	 */
	private PluginElementObj contents = new PluginElementObj();
	
	private LFWAbstractViewPage viewPage;

	private ISelection currentSelection;

	public LFWAbstractViewPage getViewPage() {
		return viewPage;
	}
	
	public void setCurrentSelection(ISelection currentSelection) {
		this.currentSelection = currentSelection;
	}

	public ISelection getCurrentSelection() {
		return currentSelection;
	}
	
	public PluginEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
	}

	public void commandStackChanged(EventObject arg0) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(arg0);
	}
	
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}
	
	public static PluginEditor getActiveEditor() {
		IWorkbenchPage page = PortalPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if (page != null) {
			editor = page.getActiveEditor();
		}
		if (editor != null && editor instanceof PluginEditor) {
			return (PluginEditor) editor;
		} else {
			return null;
		}
	}
	
	public boolean isDirty() {
		if (super.isDirty())
			return true;
		return getCommandStack().isDirty();
	}

	/**
	 * 不允许另存为
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		PluginEditorInput pEditorInput = (PluginEditorInput) input;
		PtPlugin ptPlugin = pEditorInput.getPtPlugin();
		
		PluginElementObj pluginEle = new PluginElementObj();
		pluginEle.setPtPlugin(ptPlugin);
		pluginEle.setExPoint((List<ExPoint>)ptPlugin.getExtensionPointList());
		
		pluginEle.setLocation(new Point(100, 100));
		contents = pluginEle;
	}
	
	public void executComand(Command cmd){
		super.getCommandStack().execute(cmd);
	}
	
	/**
	 *PluginEditor 图形初始化 
	 */
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setContents(this.contents);
	}
	
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}

		};
	}

	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScalableFreeformRootEditPart rootEditpart = new ScalableFreeformRootEditPart();
		getGraphicalViewer().setRootEditPart(rootEditpart);
		getGraphicalViewer().setEditPartFactory(new PluginEditPartFactory());
		getGraphicalViewer().setContextMenu(getMenuManager());
	}

	/**
	 * 保存
	 * 
	 * @param monitor
	 */
	public void doSave(IProgressMonitor monitor) {
		PluginElementObj pluginEle = (PluginElementObj) contents;
		savePlugin(pluginEle.getPtPlugin());
	}

	/**
	 * 保存 Plugin
	 */
	public void savePlugin(PtPlugin plugin) {
		if(pluginValdator(plugin)==false)
			return;
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
		PortalConnector.savePtPluginToXml(projectPath,projectModuleName, plugin);
		getCommandStack().markSaveLocation();
	}

	/**
	 * 保存校验
	 * @param plugin
	 * @return 
	 */
	private boolean pluginValdator(PtPlugin plugin) {
		String errorMsg = null;
		for (ExPoint point : plugin.getExtensionPointList()){
			check(point,errorMsg);
		}
		if (errorMsg !=null){
			Shell shell = new Shell(Display.getCurrent());
			MessageDialog.openError(shell, "保存失败", errorMsg);
			return false;
		}
		else{
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void check(ExPoint point,String errorMsg){
		String classname =null;
		for ( Extension ex : point.getExtensionList()){
			try{
				classname=point.getClassname();
				Class itfClazz = Class.forName(classname);
				classname=ex.getClassname();
				Class cfgClazz = Class.forName(classname);
				if (!itfClazz.isAssignableFrom(cfgClazz)) {
					errorMsg="扩展点 "+point.getTitle()+" 验证失败，扩展 "+ex.getTitle()+" 没有实现扩展点接口!";
					break;
				}
			}catch(ClassNotFoundException e){
				e.printStackTrace();
				errorMsg="没有找到类："+classname;
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class cls) {
		if (cls == ILFWViewPage.class) {
			this.viewPage = new PluginViewPage();
			return this.viewPage;
		}
		return super.getAdapter(cls);
	}

	/**
	 * 增加编辑器自定义菜单
	 * @param manager
	 */
	private MenuManager getMenuManager(){
		MenuManager menuManager = new MenuManager();
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				PluginElementObj obj = (PluginElementObj) contents;
				manager.removeAll();
				manager.add(new NewPointAction(obj));
				if (obj.getFingure().getCurrentLabel()!=null){
					manager.add(new DelPointAction((ExPointLabel)obj.getFingure().getCurrentLabel(),obj));
				}
			}
		});
		return menuManager;
		
	}
}
