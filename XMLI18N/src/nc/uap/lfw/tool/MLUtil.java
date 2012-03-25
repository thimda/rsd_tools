package nc.uap.lfw.tool;

/**
 * ���﹤����
 * 
 * @author dingrf
 *
 */
public class MLUtil{

	/**
	 * GBK����תΪUnicode
	 * 
	 * @param src
	 * @return
	 */
	public static String gb2Unicode(String src){
		src = spaceToNull(src);
		if (src == null)
			return null;
		char c[] = src.toCharArray();
		int n = c.length;
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
			b[i] = (byte)c[i];

		return new String(b);
	}

	/**
	 * ȥ���ַ����еĿո�
	 * 
	 * @param str
	 * @return
	 */
	protected static String spaceToNull(String str){
		if (str == null || str.length() == 0)
			return null;
		else
			return str.trim();
	}

	/**
	 * Unicode����תΪGBK
	 * 
	 * @param src
	 * @return
	 */
	public static String uniCode2Gb(String src){
		byte b[] = src.getBytes();
		int n = b.length;
		char c[] = new char[n];
		for (int i = 0; i < n; i++)
			c[i] = (char)((short)b[i] & 0xff);

		return new String(c);
	}
}
