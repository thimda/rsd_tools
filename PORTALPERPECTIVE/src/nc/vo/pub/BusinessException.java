package nc.vo.pub;



/**
 *     ҵ���쳣�ࡣ��Υ��ҵ���߼��Ĳ����׳�����쳣��������EJB�淶
 * �е�Ӧ�ü��쳣��<br>
 *     �����쳣Ӧ�ɲ���Ա�ж���δ������BO�಻Ӧ���������쳣����
 * Ӧֱ�ӽ�����ת�����ͻ��ˡ�<br>
 *     ע�⣺��BO�����׳�ҵ���쳣�ķ�����Ӧ�ڸ�BO�����remote�ӿ���
 * ��Ӧ�����������ϼ��϶Ը��쳣��������
 * 
 * �������ڣ�(2001-2-15 9:18:57)
 * @author��Zhao Jijiang
 */
public class BusinessException extends Exception {
	
	static final long serialVersionUID = -35466L;

    private String hint;
    
	/*
	 *�����룬��ͳһ��д������ҵ�����׳��Ĵ���
	 **/
	//TODO
	private String errorCodeString = ""; 
 
    /**
     * BusinessException ������ע�⡣
     */
    public BusinessException() {
        super();
    }

    /**
     * BusinessException ������ע�⡣
     * @param s java.lang.String
     */
    public BusinessException(String s) {
        super(s);
        setErrorCodeString(IErrorDict.ERR_BUSI_BILL_EXCEPTION);
    }
    
    /**
     * BusinessException ������ע�⡣
     * @param s java.lang.String
     */
    public BusinessException(String s,String errorCode) {
        super(s);
        setErrorCodeString(errorCode);
    }

    /**
     * ��������쳣��ԭ�����ʾ��˵����
     * 
     * �������ڣ�(2001-3-1 9:55:28)
     * @return java.lang.String �����쳣��ԭ��˵����
     */
    public java.lang.String getHint() {
        return hint;
    }

    /**
     * ���ö������쳣��ԭ�����ʾ��˵����
     * 
     * �������ڣ�(2001-3-1 9:55:28)
     * @param newHint java.lang.String �����쳣��ԭ��
     */
    public void setHint(java.lang.String newHint) {
        hint = newHint;
    }

    /**
     * @since  nc3.1
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @since  nc3.1
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

	public String getErrorCodeString() {
		return errorCodeString;
	}

	public void setErrorCodeString(String errorCode) {
		this.errorCodeString = errorCode;
	}

}
