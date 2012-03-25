package nc.lfw.editor.pagemeta;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * 新建 页面状态 对话框
 * @author guoweic
 *
 */
public class NewPageStateDialog extends Dialog {
	
	private PageStateElementObj pageStateObj;
	
	private PageStatePanel mainContainer;
	
	private String name;

	protected NewPageStateDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Point getInitialSize() {
		return new Point(300, 150); 
	}
	
	
	public Control createDialogArea(Composite parent) {

		mainContainer = new PageStatePanel(parent, SWT.NONE);
		
		return mainContainer;
	}
	
	protected void okPressed() {
		if (!"".equals(mainContainer.getNameText().getText().trim())) {
			name = mainContainer.getNameText().getText().trim();
			super.okPressed();
			
		} else {
			MessageDialog.openInformation(null, "提示", "名称不能为空");
			mainContainer.getNameText().setFocus();
		}
	}

	public PageStatePanel getMainContainer() {
		return mainContainer;
	}

	public String getName() {
		return name;
	}

	public void setPageStateObj(PageStateElementObj pageStateObj) {
		this.pageStateObj = pageStateObj;
	}

}
