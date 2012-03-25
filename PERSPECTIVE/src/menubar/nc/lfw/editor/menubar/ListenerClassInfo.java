package nc.lfw.editor.menubar;

import java.io.Serializable;

/**
 * Menubar的Listener类信息
 * 
 * @author guoweic
 *
 */
public class ListenerClassInfo implements Serializable{

	private static final long serialVersionUID = 6451982213378360969L;
	
	private String packageName = "";
	private String sourceFolder = "";
	private String classPrefix = "";
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getSourceFolder() {
		return sourceFolder;
	}
	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	public String getClassPrefix() {
		return classPrefix;
	}
	public void setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
	}

}
