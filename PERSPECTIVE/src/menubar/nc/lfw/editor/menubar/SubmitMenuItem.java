package nc.lfw.editor.menubar;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.perspective.listener.FileUtil;

/**
 * @author guoweic
 *
 */
public class SubmitMenuItem extends SaveMenuItem {

	private static final String ACTION_TYPE = "SAVE";
	private static final String packName = "nc.bs.pub.action";
	
	private String billType;
	private String sourceFolder;
	private String packageName;
	
	public void afterAdd() {
		
		saveActionFile(sourceFolder, ACTION_TYPE, billType);
		
//		String packName = "nc.bs.pub.action";
//		String[] fileContents = NCConnector.registerBillAction(billType, actions);
//		if (null != fileContents && fileContents.length > 0) {
//			String fileContent = fileContents[0];
//			String folder = sourceFolder + "/" + packName.replace(".", "/");
//			String fileName = "N_" + billType + "_" + ACTION_TYPE + ".java";
//			String projectPath = LFWPersTool.getProjectPath();
//			String filePath = projectPath + "/" + folder;
//			FileUtil.saveToFile(filePath, fileName, fileContent);
//		}
	}
	
	
	public void saveActionFile(String sourceFolder, String actiontype, String billType){
		String[] actions = new String[1];
		actions[0] = actiontype;
		String[] fileContents = NCConnector.registerBillAction(billType, actions);
		if (null != fileContents && fileContents.length > 0) {
			String fileContent = fileContents[0];
			String folder = sourceFolder + "/" + packName.replace(".", "/");
			String fileName = "N_" + billType + "_" + actiontype + ".java";
			String projectPath = LFWPersTool.getProjectPath();
			String filePath = projectPath + "/" + folder;
			FileUtil.saveToFile(filePath, fileName, fileContent);
		}
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
