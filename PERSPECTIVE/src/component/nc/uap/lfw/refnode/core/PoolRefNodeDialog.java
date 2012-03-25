package nc.uap.lfw.refnode.core;

import nc.lfw.editor.common.DialogWithTitle;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 新建公共参照ID
 * @author zhangxya
 *
 */

public class PoolRefNodeDialog extends DialogWithTitle {

	private Text refNodeNameText;
	private String refnodeId;
	public String getRefnodeId() {
		return refnodeId;
	}

	public void setRefnodeId(String refnodeId) {
		this.refnodeId = refnodeId;
	}

	private String defaultRefNodeId;
	


	public PoolRefNodeDialog(Shell parentShell, String title, String defaultRefNodeId) {
		super(parentShell, title);
		this.defaultRefNodeId = defaultRefNodeId;
	}
	
	protected Point getInitialSize() {
		return new Point(400, 150); 
	}
	
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label pooldsIdLabel = new Label(container, SWT.NONE);
		pooldsIdLabel.setText("公共参照ID:");
		refNodeNameText = new Text(container,SWT.NONE);
		refNodeNameText.setLayoutData(new GridData(225,20));
		refNodeNameText.setText(defaultRefNodeId);
		return container;
	}
	
	protected void okPressed() {
		refnodeId = refNodeNameText.getText();
		if(refnodeId == null || refnodeId.equals("")){
			MessageDialog.openError(null, "错误提示", "公共参照ID不能为空!");
			return;
		}
		super.okPressed();
	}
}
