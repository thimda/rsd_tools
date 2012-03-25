package nc.lfw.editor.menubar;

import nc.uap.lfw.core.event.conf.WidgetRule;


public class EditMenuItem extends DefaultItem {

	public void afterAdd() {
		
	}

	public void afterDelete() {
		
	}
	
	protected void processWidgetRule(WidgetRule rule) {
		rule.setCardSubmit(true);
	}
	
}
