package nc.lfw.editor.menubar;

import nc.uap.lfw.core.event.conf.WidgetRule;

public class ListMenuItem extends DefaultItem {
	protected void processWidgetRule(WidgetRule rule) {
		rule.setCardSubmit(true);
	}

	public void afterAdd() {
		
	}

	public void afterDelete() {
		
	}
}
