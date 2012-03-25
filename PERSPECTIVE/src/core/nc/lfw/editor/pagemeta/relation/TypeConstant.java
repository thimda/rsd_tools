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
	// ��������ӳ��
	private static Map<String, String> widgetRelationParamType = null;
	// ����ʱ������ӳ��
	private static Map<String, String> widgetRelationEmitType = null;
	// ִ�ж�������ӳ��
	private static Map<String, String> widgetRelationActionType = null;
	
	public static Map<String, String> getWidgetRelationParamType() {
		if (null == widgetRelationParamType) {
			widgetRelationParamType = new HashMap<String, String>();
			widgetRelationParamType.put(PlugoutDescItem.TYPE_FOMULAR, "��ʽ");
			widgetRelationParamType.put(PlugoutDescItem.TYPE_DS_FIELD, "��");
			//TODO
			
		}
		return widgetRelationParamType;
	}

	public static Map<String, String> getWidgetRelationEmitType() {
		if (null == widgetRelationEmitType) {
			widgetRelationEmitType = new HashMap<String, String>();
			widgetRelationEmitType.put(PlugoutEmitItem.TYPE_DS_ROW_SELECT, "��ѡ��");
			//TODO
			
		}
		return widgetRelationEmitType;
	}

	public static Map<String, String> getWidgetRelationActionType() {
		if (null == widgetRelationActionType) {
			widgetRelationActionType = new HashMap<String, String>();
			widgetRelationActionType.put(PluginDescItem.TYPE_IFRAME_REDIRECT, "ҳ���ض���");
			widgetRelationActionType.put(PluginDescItem.TYPE_DS_LOAD, "���ݼ���");
			//TODO
			
		}
		return widgetRelationActionType;
	}
	
}
