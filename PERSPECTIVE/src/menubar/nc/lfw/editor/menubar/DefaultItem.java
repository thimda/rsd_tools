package nc.lfw.editor.menubar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.event.conf.DatasetRule;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.WidgetRule;

/**
 * Ĭ�ϲ˵���
 * 
 * @author guoweic
 *
 */
public abstract class DefaultItem {
	
	private String cmdId = "";
	
	private String id = "";
	
	private String text = "";
	
	private String superClazz = "";
	
//	private String operatorStatusArray = "";
//
//	private String businessStatusArray = "";
	
	private List<DefaultItem> childList = null;

	public String getCmdId() {
		return cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}
	
	/**
	 * ��DefaultItem����ת��ΪMenuItem����
	 */
	public MenuItem generateMenuItem() {
		MenuItem item = new MenuItem();
		item.setText(text);
		item.setId(id);
//		item.setOperatorStatusArray(operatorStatusArray);
//		item.setBusinessStatusArray(businessStatusArray);
		if (null != childList) {
			for (DefaultItem childDefaultItem : childList) {
				// �����Ӳ˵�
				MenuItem childItem = childDefaultItem.generateMenuItem();
				item.addMenuItem(childItem);
			}
		} else {
			item.setChildList(new ArrayList<MenuItem>());
		}
		return item;
	}
	
	/**
	 * ��DefaultItem����ת��ΪMenuItem���󣨽�ΪͼƬ�����������֣�
	 * @param imgIcon
	 * @param imgIconOn
	 * @param imgIconDisable
	 * @return
	 */
	public MenuItem generateImgMenuItem(String imgIcon, String imgIconOn, String imgIconDisable) {
		MenuItem item = new MenuItem();
		item.setId(id);
		item.setTip(text);
		item.setImgIcon(imgIcon);
		item.setImgIconOn(imgIconOn);
		item.setImgIconDisable(imgIconDisable);
//		item.setOperatorStatusArray(operatorStatusArray);
//		item.setBusinessStatusArray(businessStatusArray);
		if (null != childList) {
			for (DefaultItem childDefaultItem : childList) {
				// �����Ӳ˵�
				MenuItem childItem = childDefaultItem.generateMenuItem();
				item.addMenuItem(childItem);
			}
		} else {
			item.setChildList(new ArrayList<MenuItem>());
		}
		return item;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public List<DefaultItem> getChildList() {
		return childList;
	}

	public void setChildList(List<DefaultItem> childList) {
		this.childList = childList;
	}
	
	public void addChild(DefaultItem defaultItem) {
		if (null == childList)
			childList = new ArrayList<DefaultItem>();
		if (!childList.contains(defaultItem))
			childList.add(defaultItem);
	}
	
	/**
	 * �����ύ����
	 * @param widgetIdMap
	 * @param dsIdMap
	 * @return
	 */
	public EventSubmitRule generateSubmitRule(Map<String, String> widgetIdMap, Map<String, String> dsIdMap) {
		if(widgetIdMap.size() > 0) {
			EventSubmitRule sr = new EventSubmitRule();
			for (String widgetParamName : widgetIdMap.keySet()) {
				String widgetId = widgetIdMap.get(widgetParamName);
				WidgetRule wr = new WidgetRule();
				wr.setId(widgetId);
				processWidgetRule(wr);
				if (dsIdMap.size() > 0) {
					for (String dsParamName : dsIdMap.keySet()) {
						DatasetRule dsr = new DatasetRule();
						String dsId = dsIdMap.get(dsParamName);
						dsr.setId(dsId);
						dsr.setType(DatasetRule.TYPE_CURRENT_PAGE);
						processDatasetRule(dsr);
						wr.addDsRule(dsr);
					}
				}
				sr.getWidgetRules().put(wr.getId(), wr);
			}
			return sr;
		}
		return null;
	}
	
	/**
	 * �����ύ����
	 * @param widgetId
	 * @param dsIds
	 * @return
	 */
	public EventSubmitRule generateSubmitRule(String widgetId, String masterDsId, List<String> slaveDsIds, Map<String, String> tabiddsMap) {
		EventSubmitRule sr = new EventSubmitRule();
		WidgetRule wr = new WidgetRule();
		wr.setId(widgetId);
		processWidgetRule(wr);
		DatasetRule mdsr = new DatasetRule();
		mdsr.setId(masterDsId);
		mdsr.setType(DatasetRule.TYPE_CURRENT_PAGE);
		processDatasetRule(mdsr);
		wr.addDsRule(mdsr);
		if (slaveDsIds.size() > 0) {
			for (String slaveDsId : slaveDsIds) {
				DatasetRule dsr = new DatasetRule();
				dsr.setId(slaveDsId);
				dsr.setType(DatasetRule.TYPE_CURRENT_PAGE);
				processDatasetRule(dsr);
				wr.addDsRule(dsr);
			}
		}
		sr.getWidgetRules().put(wr.getId(), wr);
		return sr;
	}
	
	protected void processDatasetRule(DatasetRule dsr) {
	}
	
	protected void processWidgetRule(WidgetRule rule) {
		
	}

	public String getSuperClazz() {
		return superClazz;
	}

	public void setSuperClazz(String superClazz) {
		this.superClazz = superClazz;
	}
//
//	public String getOperatorStatusArray() {
//		return operatorStatusArray;
//	}
//
//	public void setOperatorStatusArray(String operatorStatusArray) {
//		this.operatorStatusArray = operatorStatusArray;
//	}
//
//	public String getBusinessStatusArray() {
//		return businessStatusArray;
//	}
//
//	public void setBusinessStatusArray(String businessStatusArray) {
//		this.businessStatusArray = businessStatusArray;
//	}
	
	/**
	 * ��ť�����Ӻ�ִ�еķ���
	 */
	public abstract void afterAdd();
	
	/**
	 * ��ť��ɾ����ִ�еķ���
	 */
	public abstract void afterDelete();
	
	
}
