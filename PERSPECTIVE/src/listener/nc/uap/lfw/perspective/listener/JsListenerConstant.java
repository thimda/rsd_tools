package nc.uap.lfw.perspective.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.event.conf.PageListener;

public class JsListenerConstant {
//	public static final String TARGET_PAGEMETA = "target_pagemeta";
//	public static final String TARGET_DATASET = "target_dataset";

//	//TODO
//	private static final String[] PAGEMETA_LISTENER_CLASS_ARRAY = {PageListener.class.getName()};
//	
//	private static final String[] DATASET_LISTENER_CLASS_ARRAY = {DatasetListener.class.getName()};
//	
//	
//	private static Map<String, String[]> JsListenerClassMap = null;
//	
//	private static void init() {
//		if (JsListenerClassMap == null) {
//			JsListenerClassMap = new HashMap<String, String[]>();
//			//TODO
//			JsListenerClassMap.put(TARGET_PAGEMETA, PAGEMETA_LISTENER_CLASS_ARRAY);
//			
//			JsListenerClassMap.put(TARGET_DATASET, DATASET_LISTENER_CLASS_ARRAY);
//			
//			
//			
//		}
//	}
//
//	public static Map<String, String[]> getJsListenerClassMap() {
//		init();
//		return JsListenerClassMap;
//	}
	
	public static final String TARGET_PAGEMETA = "target_pagemeta";

	//TODO
	private static List<Class<? extends JsListenerConf>> PAGEMETA_LISTENER_CLASS_LIST = new ArrayList<Class<? extends JsListenerConf>>();
	
	private static Map<String, List<Class<? extends JsListenerConf>>> JsListenerClassMap = null;
	
	private static void init() {
		if (JsListenerClassMap == null) {
			JsListenerClassMap = new HashMap<String, List<Class<? extends JsListenerConf>>>();
			//TODO
			PAGEMETA_LISTENER_CLASS_LIST.add(PageListener.class);
			JsListenerClassMap.put(TARGET_PAGEMETA, PAGEMETA_LISTENER_CLASS_LIST);
			
		}
	}

	public static Map<String, List<Class<? extends JsListenerConf>>> getJsListenerClassMap() {
		init();
		return JsListenerClassMap;
	}
	
}
