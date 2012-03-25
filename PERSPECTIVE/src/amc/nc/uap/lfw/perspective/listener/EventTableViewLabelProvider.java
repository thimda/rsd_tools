/**
 * 
 */
package nc.uap.lfw.perspective.listener;

import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.perspective.editor.TableViewLabelProvider;

/**
 * @author chouhl
 *
 */
public class EventTableViewLabelProvider extends TableViewLabelProvider {
	
	private boolean isShowMethodName = true;
	
	public EventTableViewLabelProvider(boolean isShowMethodName){
		this.isShowMethodName = isShowMethodName;
	}

	public String getColumnText(Object element, int columnIndex) {
		if(isShowMethodName){
			return getColumnText0(element, columnIndex);
		}else{
			return getColumnText1(element, columnIndex);
		}
	}
	
	private String getColumnText0(Object element, int columnIndex){
		EventConf handler = (EventConf)element;
		if(columnIndex == 0)
			return handler.getMethodName();
		if(columnIndex == 1)
			return handler.getName();
		if(columnIndex == 2) {
			if (handler.getParamList().size() > 0)
				return handler.getParamList().get(0).getName();
			return "";
		}
		if(columnIndex == 3)
			return handler.isOnserver() ? "��" : "��";
		if(columnIndex == 4) {
			EventSubmitRule submitRule = handler.getSubmitRule();
			return null == submitRule ? "��" : "��";
		} 
		if(columnIndex == 5) {
			if (handler.getExtendParamList() != null && handler.getExtendParamList().size() > 0)
				return handler.getExtendParamList().get(0).getDesc();;
			return "";
		}
		if(columnIndex == 6){
			return handler.getControllerClazz() != null ? handler.getControllerClazz():"";
		}
//		if(columnIndex == 5)
//			return handler.getScript();
		return null;
	}
	
	private String getColumnText1(Object element, int columnIndex){
		EventConf handler = (EventConf)element;
		if(columnIndex == 0)
			return handler.getName();
		if(columnIndex == 1) {
			if (handler.getParamList().size() > 0)
				return handler.getParamList().get(0).getName();
			return "";
		}
		if(columnIndex == 2)
			return handler.isOnserver() ? "��" : "��";
		if(columnIndex == 3) {
			EventSubmitRule submitRule = handler.getSubmitRule();
			return null == submitRule ? "��" : "��";
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
