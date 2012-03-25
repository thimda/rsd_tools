package nc.lfw.editor.menubar;

import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.conf.DatasetRule;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.WidgetRule;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

public class SaveMenuItem extends DefaultItem {
	/**
	 * 生成提交规则
	 * @param widgetIdMap
	 * @param dsIdMap
	 * @return
	 */
	public EventSubmitRule generateSubmitRule(Map<String, String> widgetIdMap, Map<String, String> dsIdMap) {
		String widgetId = widgetIdMap.values().toArray(new String[0])[0];
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		EventSubmitRule sr = new EventSubmitRule();
		LfwWidget widget = pm.getWidget(widgetId);
		WidgetRule wr = new WidgetRule();
		wr.setId(widgetId);
		sr.addWidgetRule(wr);
		Dataset[] dss = widget.getViewModels().getDatasets();
		for (int i = 0; i < dss.length; i++) {
			DatasetRule dsr = new DatasetRule();
			dsr.setId(dss[i].getId());
			dsr.setType(DatasetRule.TYPE_ALL_LINE);
			wr.addDsRule(dsr);
		}
		return sr;
	}

	public void afterAdd() {
		
	}

	public void afterDelete() {
		
	}
}
