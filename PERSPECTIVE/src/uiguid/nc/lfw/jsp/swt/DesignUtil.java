package nc.lfw.jsp.swt;

import nc.uap.lfw.core.page.PageMeta;

public class DesignUtil {
	private static PageMeta pageMeta;
	public static void setPageMeta(PageMeta pm){
		pageMeta = pm;
	}
	public static PageMeta getPageMeta(){
		return pageMeta;
	}
}
