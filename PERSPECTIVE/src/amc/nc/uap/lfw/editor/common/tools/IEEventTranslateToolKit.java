package nc.uap.lfw.editor.common.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nc.uap.lfw.editor.view.EditorEvent;

/**
 * IE�е��¼���Eclipse�����Ĺ�����
 * 
 * @author licza
 * 
 */
public class IEEventTranslateToolKit {
	
	public void dipatcher(EditorEvent event){
		
	}
	
	
	/**
	 * ��event������ת��Ϊ�༭���¼�
	 * 
	 * @param eventContext
	 * @return
	 */
	public static final EditorEvent decode(String eventContext) {
		/**
		 * ȥ������
		 */
		if (eventContext == null || eventContext.isEmpty()
				|| !eventContext.startsWith("event:"))
			return null;
		/**
		 * ����Լ�����ַ�����,,,�ָ��һ��λ�ã�����¼����ͣ��Ժ��ֵ�Լ�ֵ����ʽ���棬�ԡ������ָ����ֵ
		 * tijiao,,,key1:value1
		 */
		String context = eventContext.substring(6);
		if (context.length() <= 0)
			return null;
		String[] ctxs = context.split(",,,");
		EditorEvent event = new EditorEvent(ctxs[0]);
		if (ctxs.length > 1) {
			Map<String, String> source = new HashMap<String, String>();
			for (int i = 1; i < ctxs.length; i++) {
				String spi = ctxs[i];
				int pi = spi.indexOf(":");
				if (pi == -1 || pi == 0)
					continue;
				String key = spi.substring(0, pi);
				String val = spi.substring(pi + 1);
				source.put(key, val);
			}
			event.setSource(source);
		}
		return event;
	}

	/**
	 * ���༭���¼�ת��Ϊ�ַ���
	 * 
	 * @param event
	 * @return
	 */
	public static final String encode(EditorEvent event) {
		if (event == null || event.getEventType() == null)
			return null;
		StringBuffer sb = new StringBuffer("event:");
		sb.append(event.getEventType());
		Map<String, String> source = event.getSource();
		if (source != null && !source.isEmpty()) {
			Set<Entry<String, String>> es = source.entrySet();
			for (Entry<String, String> ent : es) {
				sb.append(",,,");
				sb.append(ent.getKey());
				sb.append(":");
				sb.append(ent.getValue());
			}
		}
		return sb.toString();
	}
}
