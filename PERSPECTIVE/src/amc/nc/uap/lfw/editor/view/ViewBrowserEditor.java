/**
 * 
 */
package nc.uap.lfw.editor.view;

import java.util.List;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.WidgetEditorInput;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.browser.MozillaBrowser;
import nc.uap.lfw.editor.common.editor.LFWBrowserEditor;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.palette.PaletteFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;

/**
 * @author chouhl
 *
 */
public class ViewBrowserEditor extends LFWBrowserEditor {
	
	public ViewBrowserEditor(){
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}
	
	public MozillaBrowser createMozillaBrowser(Composite parent){
		MozillaBrowser mozilla = new MozillaBrowser(parent, MozillaBrowser.VIEW_URL);
		mozilla.setTreeItem(((WidgetEditorInput)getEditorInput()).getPmTreeItem());
		mozilla.setEditor(this);
		return mozilla;
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		LFWAMCConnector.updateViewSessionCache(getView(), getUimeta(), getElementMap());
		LFWAMCConnector.saveWidgetAndUIMetaFromSessionCache(getSessionId(), getPageMetaId(), getWidgetId(), getNodePath());
		getMozilla().changeSaveStatus();
//		getMozilla().execute("saveForEclipse();");
	}
	
	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		setUimeta(LFWAMCPersTool.getCurrentUIMeta());
		setView((LfwWidget)((WidgetEditorInput)getEditorInput()).getWidget().clone());
		setPageMetaId(((WidgetEditorInput)getEditorInput()).getPagemeta().getId());
		setWidgetId(((WidgetEditorInput)getEditorInput()).getWidget().getId());
	}

	public static ViewBrowserEditor getActiveEditor(){
		IEditorPart editor = LFWBrowserEditor.getActiveEditor();
		if(editor instanceof ViewBrowserEditor){
			return (ViewBrowserEditor)editor;
		}else {
			return null;
		}
	}
	
	@Override
	protected void editMenuManager(IMenuManager manager) {
		
	}

	@Override
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		return null;
	}

	@Override
	protected LfwElementObjWithGraph getLeftElement() {
		return null;
	}

	@Override
	protected LfwElementObjWithGraph getTopElement() {
		return null;
	}

	@Override
	public void removeJsListener(JsListenerConf jsListenerId) {
		
	}

	@Override
	public void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf jsListener) {
		
	}

}



