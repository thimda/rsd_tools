package nc.lfw.editor.pagemeta;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.FileUtilities;

import org.eclipse.jface.wizard.Wizard;

/**
 * UI 创建向导
 * @author guoweic
 *
 */
public class UIGuideWizard extends Wizard {

	private String nodePath = "";
	
	private UIGuideFirstPage firstPage;
	//TODO
	
	private UIGuideLastPage lastPage;
	
	/**
	 * 加入各页面
	 */
	public void addPages() {
		// 创建页面对象并设置页面名称
		firstPage = new UIGuideFirstPage("firstPage");
		//TODO
		
		lastPage = new UIGuideLastPage("lastPage");
		
		// 加入页面
		addPage(firstPage);
		//TODO
		
		addPage(lastPage);
	}
	
	/**
	 * 判断“完成”按钮何时有效。返回true有效，false无效
	 */
	public boolean canFinish() {
		// 设置成，只有到最后一页时“完成”按钮有效
		if (this.getContainer().getCurrentPage() != lastPage)
			return false;
		
		// 复制选中的文件夹中文件
		if (this.getContainer().getCurrentPage() == lastPage && firstPage.getCurrentMode() == 4) {
			String oldPath = firstPage.getTempleteFolderPath();
			// 复制文件
			try {
				FileUtilities.copyFileFromDir(nodePath, oldPath);
			} catch (Exception e) {
				MainPlugin.getDefault().logError(e.getMessage(), e);
			}
//			FileTools.copyFolder(oldPath, nodePath);
		}
		
		return super.canFinish();
	}
	
	
	public boolean performFinish() {
		//TODO 完成后进行的操作
		
		return true;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

}
