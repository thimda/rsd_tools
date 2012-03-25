package nc.lfw.editor.pagemeta;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.FileUtilities;

import org.eclipse.jface.wizard.Wizard;

/**
 * UI ������
 * @author guoweic
 *
 */
public class UIGuideWizard extends Wizard {

	private String nodePath = "";
	
	private UIGuideFirstPage firstPage;
	//TODO
	
	private UIGuideLastPage lastPage;
	
	/**
	 * �����ҳ��
	 */
	public void addPages() {
		// ����ҳ���������ҳ������
		firstPage = new UIGuideFirstPage("firstPage");
		//TODO
		
		lastPage = new UIGuideLastPage("lastPage");
		
		// ����ҳ��
		addPage(firstPage);
		//TODO
		
		addPage(lastPage);
	}
	
	/**
	 * �жϡ���ɡ���ť��ʱ��Ч������true��Ч��false��Ч
	 */
	public boolean canFinish() {
		// ���óɣ�ֻ�е����һҳʱ����ɡ���ť��Ч
		if (this.getContainer().getCurrentPage() != lastPage)
			return false;
		
		// ����ѡ�е��ļ������ļ�
		if (this.getContainer().getCurrentPage() == lastPage && firstPage.getCurrentMode() == 4) {
			String oldPath = firstPage.getTempleteFolderPath();
			// �����ļ�
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
		//TODO ��ɺ���еĲ���
		
		return true;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

}
