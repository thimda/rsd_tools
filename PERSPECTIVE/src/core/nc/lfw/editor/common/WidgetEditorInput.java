package nc.lfw.editor.common;

import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

public class WidgetEditorInput extends PagemetaEditorInput {
	private LfwWidget widget;
	private LfwWidget cloneElement;
	public WidgetEditorInput(LfwWidget widget, PageMeta pagemeta) {
		super(pagemeta);
		this.widget = widget;
	}
	public LfwWidget getWidget() {
		return widget;
	}
	
	public WebElement getCloneElement() {
		if(cloneElement == null)
			cloneElement = (LfwWidget) widget.clone();
		return cloneElement;
	}

	public String getName() {
		return "Widget ±à¼­Æ÷";
	}
	
}
