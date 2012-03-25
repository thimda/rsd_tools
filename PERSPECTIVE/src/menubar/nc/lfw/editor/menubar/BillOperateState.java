package nc.lfw.editor.menubar;

/**
 * �˵���ť����״̬
 * @author guoweic
 *
 */
public class BillOperateState {

	// ��ʼ����ť����
	public static final String OP_INIT = "0";
	// �Ǳ༭ʱ��ť����
	public static final String OP_SINGLESEL = "1";
	public static final String OP_MULTISEL = "2";
	// �༭ʱ��ť����
	public static final String OP_EDIT = "3";
	// ��������״̬
	public static final String OP_ADD = "4";
	//TODO ����״̬
	
	// ����״̬
	public static final String OP_ALL = "7";
	
	// ��ʾ����
	private String text = "";
	
	// ֵ
	private String value;
	
	public BillOperateState(String text, String value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
