package nc.lfw.editor.menubar;

import java.util.Map;

import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.WidgetRule;

public class AddMenuItem extends DefaultItem {
	/**
	 * 生成提交规则
	 * @param widgetIdMap
	 * @param dsIdMap
	 * @return
	 */
	public EventSubmitRule generateSubmitRule(Map<String, String> widgetIdMap, Map<String, String> dsIdMap) {
		return super.generateSubmitRule(widgetIdMap, dsIdMap);
	}
	
	protected void processWidgetRule(WidgetRule rule) {
		rule.setCardSubmit(true);
	}

	public void afterAdd() {
		
	}

	public void afterDelete() {
		
	}
}
