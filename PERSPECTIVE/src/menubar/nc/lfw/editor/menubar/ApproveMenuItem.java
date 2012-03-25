package nc.lfw.editor.menubar;


public class ApproveMenuItem extends SubmitMenuItem {

	private static final String ACTION_TYPE = "APPROVE";

	private String billType;
	private String sourceFolder;
	private String packageName;
	
	public void afterAdd() {
		saveActionFile(sourceFolder, ACTION_TYPE, billType);
//		String[] actions = new String[1];
//		actions[0] = ACTION_TYPE;
//		String[] fileContents = NCConnector.registerBillAction(billType, actions);
//		if (null != fileContents && fileContents.length > 0) {
//			String fileContent = fileContents[0];
//			String folder = sourceFolder + "/" + packageName.replace(".", "/");
//			String fileName = "N_" + billType + "_" + ACTION_TYPE + ".java";
//			String projectPath = LFWPersTool.getProjectPath();
//			String filePath = projectPath + "/" + folder;
//			FileUtil.saveToFile(filePath, fileName, fileContent);
//		}
	}

	public void afterDelete() {
		
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
