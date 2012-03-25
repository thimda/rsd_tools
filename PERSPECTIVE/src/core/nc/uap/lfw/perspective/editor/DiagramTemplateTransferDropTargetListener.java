/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nc.uap.lfw.perspective.editor;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;

/**
 */
public class DiagramTemplateTransferDropTargetListener extends
		TemplateTransferDropTargetListener {
	
	public DiagramTemplateTransferDropTargetListener(EditPartViewer editPartViewer) {
		super(editPartViewer);
	}
	 @SuppressWarnings("unchecked")
	protected CreationFactory getFactory(Object template) {
    	if(template instanceof Class)
    		return new SimpleFactory((Class)template);
    	else
    		return null;
    }
}
  

