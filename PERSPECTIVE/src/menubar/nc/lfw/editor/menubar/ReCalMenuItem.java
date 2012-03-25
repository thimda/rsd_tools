package nc.lfw.editor.menubar;

import nc.uap.lfw.core.event.conf.WidgetRule;

public class ReCalMenuItem extends DefaultItem {

	@Override
	public void afterAdd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDelete() {
		// TODO Auto-generated method stub

	}

	protected void processWidgetRule(WidgetRule rule) {
		rule.setCardSubmit(true);
	}
	
}
