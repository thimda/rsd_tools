/**
 * 
 */
package nc.uap.lfw.editor.window;

import java.util.List;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.browser.MozillaBrowser;
import nc.uap.lfw.editor.common.editor.LFWBrowserEditor;
import nc.uap.lfw.editor.common.tools.LFWTool;
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
public class WindowBrowserEditor extends LFWBrowserEditor{
	
	public WindowBrowserEditor(){
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}
	
	@Override
	public void setFocus() {
		super.setFocus();
		LFWPersTool.hideView(LFWTool.ID_LFW_VIEW_SHEET);
	}
	
	public MozillaBrowser createMozillaBrowser(Composite parent){
		MozillaBrowser mozilla = new MozillaBrowser(parent, MozillaBrowser.WINDOW_URL);
		mozilla.setTreeItem(((WindowEditorInput)getEditorInput()).getPmTreeItem());
		mozilla.setEditor(this);
		return mozilla;
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		LFWAMCConnector.savePageMetaAndUIMetaFromSessionCache(getSessionId(), getPageMetaId(), getNodePath());
		getMozilla().changeSaveStatus();
//		getMozilla().execute("saveForEclipse();");
	}
	
	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		setPageMetaId(((WindowEditorInput)getEditorInput()).getPagemeta().getId());
	}

	public static WindowBrowserEditor getActiveEditor(){
		IEditorPart editor = LFWBrowserEditor.getActiveEditor();
		if(editor != null && editor instanceof WindowBrowserEditor){
			return (WindowBrowserEditor)editor;
		}else {
			return null;
		}
	}

	@Override
	protected void editMenuManager(IMenuManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LfwElementObjWithGraph getLeftElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LfwElementObjWithGraph getTopElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeJsListener(JsListenerConf jsListenerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveJsListener(String jsListenerId,
			EventHandlerConf jsEventHandler, JsListenerConf jsListener) {
		// TODO Auto-generated method stub
		
	}

}
