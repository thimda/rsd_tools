/**
 * 
 */
package nc.uap.lfw.editor.publicview;

import java.util.List;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.core.WEBProjConstants;
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
public class PublicViewBrowserEditor extends LFWBrowserEditor {
	
	public PublicViewBrowserEditor(){
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}
	
	@Override
	public MozillaBrowser createMozillaBrowser(Composite parent) {
		MozillaBrowser mozilla = new MozillaBrowser(parent, MozillaBrowser.PUBLIC_VIEW_URL);
		mozilla.setTreeItem(((PublicViewEditorInput)getEditorInput()).getDirTreeItem());
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
		setView((LfwWidget)((PublicViewEditorInput)getEditorInput()).getWidget().clone());
		setPageMetaId(WEBProjConstants.DEFAULT_WINDOW_ID);
		setWidgetId(((PublicViewEditorInput)getEditorInput()).getWidget().getId());
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
	
	public static PublicViewBrowserEditor getActiveEditor(){
		IEditorPart editor = LFWBrowserEditor.getActiveEditor();
		if(editor != null && editor instanceof PublicViewBrowserEditor){
			return (PublicViewBrowserEditor)editor;
		}else {
			return null;
		}
	}

}
