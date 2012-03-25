package nc.uap.lfw.core;

/**
 * ��Ҫ������WEB-INF�µ��ļ�Ŀ¼
 * @author zhangxya
 *
 */

public class WEBCopyPathConstants {
	public static final String	FILE_CP1 = "WEB-INF/tld/c-rt.tld";
	public static final String	FILE_CP2 = "WEB-INF/tld/fn.tld";
	public static final String	FILE_CP3 = "WEB-INF/tld/lfw.tld";
	public static final String	FILE_CP4 = "WEB-INF/tld/lfwtool.tld";
	public static final String	FILE_CP5 = "WEB-INF/tld/multilang.tld";
	public static final String	FILE_CP8 = "WEB-INF/web.xml.copy";
	public static final String	FILE_CP11 = "index.jsp";
	/**
	 * ��ȡ��Ҫ�������ļ�Ŀ¼
	 */
	private static String[] copyPaths = {FILE_CP1,FILE_CP2,FILE_CP3,FILE_CP4,FILE_CP5,FILE_CP8,FILE_CP11};
	
	public static String[] getWebCopyPath() {
		
		return copyPaths;
	}

}
