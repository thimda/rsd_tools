package nc.lfw.editor.common;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.PageMeta;

public class PagemetaElementEditorInput extends PagemetaEditorInput {
	private WebElement element;
	private WebElement cloneElement;
	public PagemetaElementEditorInput(WebElement element, PageMeta pagemeta) {
		super(pagemeta);
		this.element = element;
	}
	public WebElement getElement() {
		return element;
	}
	
	public WebElement getCloneElement() {
		if(cloneElement == null)
			cloneElement = (WebElement) element.clone();
		return cloneElement;
	}
	
}
