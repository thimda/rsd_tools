package nc.lfw.editor.menubar;

import nc.uap.lfw.core.event.conf.WidgetRule;

public class LineMenuItem extends DefaultItem {

	protected void processWidgetRule(WidgetRule rule) {
		rule.setTabSubmit(true);
	}

	public void afterAdd() {
		
	}

	public void afterDelete() {
		
	}
	
}
