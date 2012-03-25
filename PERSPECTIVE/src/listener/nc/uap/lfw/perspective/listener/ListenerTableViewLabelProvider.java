package nc.uap.lfw.perspective.listener;

import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.perspective.editor.TableViewLabelProvider;

public class ListenerTableViewLabelProvider extends TableViewLabelProvider {
	
//	private Image image = new Image(null, WEBProjPlugin.ICONS_PATH + "rule.gif");
	
	public String getColumnText(Object element, int columnIndex) {
		EventHandlerConf handler = (EventHandlerConf)element;
		if(columnIndex == 0)
			return handler.getName();
		if(columnIndex == 1) {
			if (handler.getParamList().size() > 0)
				return handler.getParamList().get(0).getName();
			return "";
		}
		if(columnIndex == 2)
			return handler.isOnserver() ? "ÊÇ" : "·ñ";
		if(columnIndex == 3) {
			EventSubmitRule submitRule = handler.getSubmitRule();
			return null == submitRule ? "ÎŞ" : "ÓĞ";
		} 
		if(columnIndex == 4) {
			if (handler.getExtendParamList().size() > 0)
				return handler.getExtendParamList().get(0).getDesc();;
			return "";
		}
//		if(columnIndex == 5)
//			return handler.getScript();
		return null;
	}
	
}
