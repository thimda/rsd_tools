/**
 * 
 */
package nc.uap.lfw.editor.application;

import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * Application�༭��Input��
 * @author chouhl
 *
 */
public class ApplicationEditorInput extends LfwBaseEditorInput {

	private Application webElement = new Application();
	
	private Application cloneElement = null;
	
	/**
	 * ��ǰ����
	 */
	private IProject project = null;
	
	/**
	 * ��ǰBCP����ڵ�
	 */
	private LFWBusinessCompnentTreeItem busiCompTreeItem = null;
	
	/**
	 * ��ǰ��BCP����ڵ�
	 */
	private TreeItem currentTreeItem = null;
	
	public ApplicationEditorInput(Application webElement){
		if(webElement != null){
			this.webElement = webElement;
		}
		this.project = LFWAMCPersTool.getCurrentProject();
		this.busiCompTreeItem = LFWAMCPersTool.getCurrentBusiCompTreeItem();
		this.currentTreeItem = LFWAMCPersTool.getCurrentTreeItem();
	}
	
	public String getName() {
		return "Application �༭��";
	}

	public String getToolTipText() {
		return "Application �༭��";
	}

	public Application getWebElement() {
		return webElement;
	}

	public WebElement getCloneElement() {
		if(cloneElement == null){
			cloneElement = (Application)webElement.clone();
		}
		return cloneElement;
	}

	public IProject getProject() {
		return project;
	}

	public LFWBusinessCompnentTreeItem getBusiCompTreeItem() {
		return busiCompTreeItem;
	}

	public TreeItem getCurrentTreeItem() {
		return currentTreeItem;
	}
	
}
