package nc.lfw.editor.menubar.dialog;

import nc.lfw.editor.common.DialogWithTitle;
import nc.uap.lfw.core.comp.MenuItem;

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
 * @author guoweic
 *
 */
public class NewMenuItemDialog extends DialogWithTitle {

	private Text idText;
	private String itemId;

	private Text textText;
	private String text;
	
	private MenuItem parentMenuItem;
	
	
	public NewMenuItemDialog(Shell parentShell, String title) {
		super(parentShell, title);

	}

	protected void okPressed() {
		itemId = idText.getText().trim();
		if (itemId == null || "".equals(itemId)) {
			MessageDialog.openInformation(this.getShell(), "提示", "请输入菜单项的ID");
			idText.setFocus();
			return;
		}
		//TODO 唯一性校验
//		List<LfwWidgetConf> list = graph.getPagemeta().getWidgetConfList();
//		for (int i = 0, n = list.size(); i < n; i++) {
//			if (list.get(i).getId().equals(subMenuId)) {
//				MessageDialog.openWarning(this.getShell(), "提示", "该子菜单已存在！");
//				idText.setFocus();
//				return;
//			}
//		}
		text = textText.getText().trim();
		if (text == null || "".equals(text)) {
			MessageDialog.openInformation(this.getShell(), "提示", "请输入菜单项的显示名");
			textText.setFocus();
			return;
		}
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(350,180);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("ID:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(200, 3));

		new Label(container, SWT.NONE).setText("显示名:");
		textText = new Text(container, SWT.BORDER);
		textText.setLayoutData(createGridData(200, 3));
		
		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public Text getIdText() {
		return idText;
	}

	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}

	public MenuItem getParentMenuItem() {
		return parentMenuItem;
	}

	
	public String getItemId() {
		return itemId;
	}

	public String getText() {
		return text;
	}

}
