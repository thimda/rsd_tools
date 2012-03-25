package nc.lfw.editor.menubar;

/**
 * 联查审批单据按钮
 * @author zhangxya
 *
 */
public class QueryPfInfoMenuItem extends SubmitMenuItem {

	private static final String ACTION_TYPE = "QUERYPFINFO";

	private String billType;
	private String sourceFolder;
	private String packageName;
	
	public void afterAdd() {
		//saveActionFile(sourceFolder, ACTION_TYPE, billType);
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
