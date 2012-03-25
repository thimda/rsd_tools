package nc.uap.portal.managerapps.dialog;

/**
 * 功能节点
 * 
 * @author dingrf
 *
 */
public class PageNode {

	/**ID值*/
	private String id;
	
	/**显示名称*/
	private String name;
	
	/**page的URL*/
	private String url;
	
	/**是否已选中*/
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
