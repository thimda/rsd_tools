/**
 * 
 */
package nc.uap.lfw.window.view;

import nc.lfw.editor.common.DialogWithTitle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 *
 */
public class ViewTypeDialog extends DialogWithTitle {
	
	
	/**
	 * 流式布局
	 */
	private Button normalView;
	/**
	 * 是否流式布局
	 */
	private boolean isNormalView = true;
	
	
	public ViewTypeDialog(String title) {
		super(null, title);
	}
	
	public ViewTypeDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		//流式布局
		new Label(container, SWT.NONE).setText("View类型：");
		Composite radioContainer = new Composite(container, SWT.NONE);
		radioContainer.setLayout(new GridLayout(2, false));
		radioContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		normalView = new Button(radioContainer, SWT.RADIO);
		normalView.setSelection(true);
		normalView.setText("普通VIEW创建");
		Button codeView = new Button(radioContainer, SWT.RADIO);
		codeView.setSelection(false);
		codeView.setText("代码VIEW创建");
		return container;
	}
	
	protected void okPressed() {
		//流式布局
		setViewType(normalView.getSelection());
		super.okPressed();
	}

	public boolean isNormalView() {
		return isNormalView;
	}

	public void setViewType(boolean isFlowlayout) {
		this.isNormalView = isFlowlayout;
	}

}
