/**
 * 
 */
package nc.uap.lfw.application;

import java.io.File;

import nc.uap.lfw.core.base.LFWAMCTreeItem;
import nc.uap.lfw.core.uimodel.Application;

import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * Application TreeItem¿‡
 * @author chouhl
 *
 */
public class LFWApplicationTreeItem extends LFWAMCTreeItem  {
	
	private Application application = null;

	public LFWApplicationTreeItem(TreeItem parentItem, Object object, String text) {
		super(parentItem, object, text);
	}
	
	public LFWApplicationTreeItem(TreeItem parentItem, File file) {
		super(parentItem, file);
	}

	public LFWApplicationTreeItem(TreeItem parentItem, File file, String text) {
		super(parentItem, file, text);
	}
	
	public void mouseDoubleClick(){
		EditApplicationNodeAction.openApplicationEditor();
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
