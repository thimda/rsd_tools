package nc.uap.lfw.mlr;

/**
 * ������Դ
 * 
 * @author dingrf
 * 
 */
public class MLRes {

	/**��Դ�ļ���*/
	private String resFileName = null;
	
	/**��ԴID*/
	private String resID = null;
	
	/**��Դֵ*/
	private String value = null;

	public MLRes(String resFileName, String resID, String value) {
		this.resFileName = resFileName;
		this.resID = resID;
		this.value = value;
	}

	public String getResFileName() {
		return this.resFileName;
	}

	public String getResID() {
		return this.resID;
	}

	public String getValue() {
		return this.value;
	}
}