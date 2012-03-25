package nc.lfw.editor.widget;

import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * ¸ÃÀàÒÑ×÷·Ï£¡
 * 
 * @author guoweic
 *
 */
public class WidgetEditorInput extends LfwBaseEditorInput {

	private LFWWidgetTreeItem treeItem = null;

	public LFWWidgetTreeItem getTreeItem() {
		return treeItem;
	}

	public void setTreeItem(LFWWidgetTreeItem treeItem) {
		this.treeItem = treeItem;
	}

	public WidgetEditorInput(LFWWidgetTreeItem treeItem){
		this.treeItem = treeItem;
	}
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Widget ±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "Widget ±à¼­Æ÷";
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
