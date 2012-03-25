package nc.lfw.editor.menubar;

import nc.lfw.editor.common.PagemetaElementEditorInput;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * 
 * @author guoweic
 *
 */
public class MenubarEditorInput extends PagemetaElementEditorInput {

	private LfwWidget widget;
	
	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}

	public MenubarEditorInput(WebElement element, PageMeta pagemeta) {
		super(element, pagemeta);
	}

	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "²Ëµ¥±à¼­Æ÷";
	}

	public String getToolTipText() {
		return  "²Ëµ¥±à¼­Æ÷";
	}

}
