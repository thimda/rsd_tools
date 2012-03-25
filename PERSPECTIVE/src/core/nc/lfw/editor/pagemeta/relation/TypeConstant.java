package nc.lfw.editor.pagemeta.relation;

import java.util.HashMap;
import java.util.Map;

import nc.uap.lfw.core.page.PluginDescItem;
import nc.uap.lfw.core.page.PlugoutDescItem;
import nc.uap.lfw.core.page.PlugoutEmitItem;

/**
 * @author guoweic
 *
 */
public class TypeConstant {
	// 参数类型映射
	private static Map<String, String> widgetRelationParamType = null;
	// 触发时机类型映射
	private static Map<String, String> widgetRelationEmitType = null;
	// 执行动作类型映射
	private static Map<String, String> widgetRelationActionType = null;
	
	public static Map<String, String> getWidgetRelationParamType() {
		if (null == widgetRelationParamType) {
			widgetRelationParamType = new HashMap<String, String>();
			widgetRelationParamType.put(PlugoutDescItem.TYPE_FOMULAR, "公式");
			widgetRelationParamType.put(PlugoutDescItem.TYPE_DS_FIELD, "列");
			//TODO
			
		}
		return widgetRelationParamType;
	}

	public static Map<String, String> getWidgetRelationEmitType() {
		if (null == widgetRelationEmitType) {
			widgetRelationEmitType = new HashMap<String, String>();
			widgetRelationEmitType.put(PlugoutEmitItem.TYPE_DS_ROW_SELECT, "行选择");
			//TODO
			
		}
		return widgetRelationEmitType;
	}

	public static Map<String, String> getWidgetRelationActionType() {
		if (null == widgetRelationActionType) {
			widgetRelationActionType = new HashMap<String, String>();
			widgetRelationActionType.put(PluginDescItem.TYPE_IFRAME_REDIRECT, "页面重定向");
			widgetRelationActionType.put(PluginDescItem.TYPE_DS_LOAD, "数据加载");
			//TODO
			
		}
		return widgetRelationActionType;
	}
	
}
