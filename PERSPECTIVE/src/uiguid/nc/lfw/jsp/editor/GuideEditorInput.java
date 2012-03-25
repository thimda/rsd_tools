package nc.lfw.jsp.editor;

import nc.lfw.editor.common.LfwBaseEditorInput;

public class GuideEditorInput extends LfwBaseEditorInput {
	
	private String folderPath;
	private String pmPath;
	
	public GuideEditorInput(){
		super();
	}
	
	public String getPmPath() {
		return pmPath;
	}

	public void setPmPath(String pmPath) {
		this.pmPath = pmPath;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getName() {
		return "UI设计器";
	}

	public String getToolTipText() {
		return "UI 设计器";
	}

}
