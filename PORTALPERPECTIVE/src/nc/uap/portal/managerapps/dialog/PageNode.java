package nc.uap.portal.managerapps.dialog;

/**
 * ���ܽڵ�
 * 
 * @author dingrf
 *
 */
public class PageNode {

	/**IDֵ*/
	private String id;
	
	/**��ʾ����*/
	private String name;
	
	/**page��URL*/
	private String url;
	
	/**�Ƿ���ѡ��*/
	private boolean check;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean getCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
