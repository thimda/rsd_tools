package nc.uap.lfw.editor.view;

import java.util.HashMap;
import java.util.Map;

/**
 * IE��UI������¼���װ
 * 
 * @author licza
 * 
 */
public class EditorEvent {
	/**
	 * �¼���Դ
	 */
	private Map<String, String> source = null;
	/**
	 * �¼�����
	 */
	private String eventType;

	public Map<String, String> getSource() {
		if (source == null)
			source = new HashMap<String, String>();
		return source;
	}

	public void setSource(Map<String, String> source) {
		this.source = source;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public EditorEvent() {
		super();
	}

	public EditorEvent(Map<String, String> source, String eventType) {
		super();
		this.source = source;
		this.eventType = eventType;
	}

	public EditorEvent(String eventType) {
		super();
		this.eventType = eventType;
	}
	
}
