package nc.uap.lfw.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

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
	
	/**���ڵ�*/
	private PageNode parent;
	
	/**�ӽڵ�List*/
	
	private List<PageNode> childrens;
	
	/**·��*/
	private String path;
	
	/**�Ƿ�Ϊ�ļ�*/
	private boolean isFile;
	
	/**��*/
	private String root;
	
	/** keyֵǰ׺*/
	private String prefix;
	
	/**IFile*/
	private IFile file;
	
	/**�ļ������ı�*/
	private boolean changed = false;
	
	/** ��Դ�ļ�ģ������*/
	private String resModuleName;
	
	/** ��Դ�ļ�ģ������*/
	private String bcpName;
	
	
	
	public PageNode(IFolder f,String resModule){
		this.setId(f.getName());
		this.setName(f.getName());
		this.setPath(f.getLocation().toString());
		this.setRoot(f.getName());
		this.setResModuleName(resModule);
	}
	
	public PageNode(IFile f,String resModule){
		this.setId(f.getName());
		this.setName(f.getName());
		this.setFile(true);
		this.setPath(f.getLocation().toString());
		this.setFile(f);
		this.setResModuleName(resModule);
	}
	
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public PageNode getParent() {
		return parent;
	}

	public void setParent(PageNode parent) {
		this.parent = parent;
	}

	public List<PageNode> getChildrens() {
		if (childrens==null){
			childrens = new ArrayList<PageNode>();
		}
		return childrens;
	}

	public void setChildrens(List<PageNode> childrens) {
		this.childrens = childrens;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
		this.prefix = root + "-";
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public IFile getFile() {
		return file;
	}

	public void setFile(IFile file) {
		this.file = file;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String getResModuleName() {
		return resModuleName;
	}

	public void setResModuleName(String resModuleName) {
		this.resModuleName = resModuleName;
	}

	public String getBcpName() {
		return bcpName;
	}

	public void setBcpName(String bcpName) {
		this.bcpName = bcpName;
	}

}
