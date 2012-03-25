package nc.uap.lfw.editor.window;

import org.eclipse.core.resources.IProject;

import nc.lfw.editor.common.PagemetaEditorInput;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

public class WindowEditorInput extends PagemetaEditorInput {
	
	/**
	 * 当前工程
	 */
	private IProject project = null;

	public WindowEditorInput(PageMeta pagemeta) {
		super(pagemeta);
		this.project = LFWAMCPersTool.getCurrentProject();
	}

	public String getName() {
		return "Window 编辑器";
	}
	
	public String getToolTipText() {
		return "Window 编辑器";
	}
	
	public IProject getProject() {
		return project;
	}
	
}
