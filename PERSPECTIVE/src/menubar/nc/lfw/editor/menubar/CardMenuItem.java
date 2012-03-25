package nc.lfw.editor.menubar;

import nc.uap.lfw.core.event.conf.WidgetRule;

public class CardMenuItem extends DefaultItem {
	protected void processWidgetRule(WidgetRule rule) {
		rule.setCardSubmit(true);
	}

	public void afterAdd() {
		
	}

	public void afterDelete() {
		
	}
}
