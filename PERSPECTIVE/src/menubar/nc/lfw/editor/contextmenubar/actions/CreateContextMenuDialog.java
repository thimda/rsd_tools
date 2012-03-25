package nc.lfw.editor.contextmenubar.actions;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 创建contextMenu子菜单
 * @author zhangxya
 *
 */
public class CreateContextMenuDialog  extends TitleAreaDialog {

	private Text idText;
	private Combo levelCombo;
	private String subMenuId;
	private int level = 1;
	
	public CreateContextMenuDialog(Shell parentShell) {
		super(parentShell);

	}

	protected void okPressed() {
		subMenuId = idText.getText().trim();
		level = Integer.parseInt((String) levelCombo.getData(levelCombo.getText()));
		if (subMenuId == null || "".equals(subMenuId)) {
			MessageDialog.openInformation(this.getShell(), "提示", "请输入子菜单的ID");
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
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(350, 250);
	}

	protected Control createDialogArea(Composite parent) {
		setTitle("新建子右键菜单");
		setMessage("请输入右键子菜单基本信息", IMessageProvider.NONE);
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("ID:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(200, 3));
		
		new Label(container, SWT.NONE).setText("右键子菜单级别:");
		levelCombo = new Combo(container, SWT.READ_ONLY);
		levelCombo.setLayoutData(createGridData(100, 1));
		levelCombo.add("一级");
		levelCombo.setData("一级", "1");
		levelCombo.add("二级");
		levelCombo.setData("二级", "2");
		levelCombo.add("三级");
		levelCombo.setData("三级", "3");
		levelCombo.select(0);
		
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

	public String getSubMenuId() {
		return subMenuId;
	}

	public int getLevel() {
		return level;
	}

}
