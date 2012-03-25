/**
 * 
 */
package nc.uap.lfw.model;

import java.io.File;

import nc.uap.lfw.core.base.LFWAMCTreeItem;
import nc.uap.lfw.core.uimodel.conf.Model;

import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * Model TreeItem¿‡
 * @author chouhl
 *
 */
public class LFWModelTreeItem extends LFWAMCTreeItem {
	
	private Model model = null;

	public LFWModelTreeItem(TreeItem parentItem, Object object, String text) {
		super(parentItem, object, text);
	}
	
	public LFWModelTreeItem(TreeItem parentItem, File file) {
		super(parentItem, file);
	}

	public LFWModelTreeItem(TreeItem parentItem, File file, String text) {
		super(parentItem, file, text);
	}

	public void mouseDoubleClick(){}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}
