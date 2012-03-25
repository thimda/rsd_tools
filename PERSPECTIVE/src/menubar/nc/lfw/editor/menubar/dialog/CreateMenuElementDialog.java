package nc.lfw.editor.menubar.dialog;

import nc.lfw.editor.common.DialogWithTitle;

import org.eclipse.jface.dialogs.MessageDialog;
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
 * @author guoweic
 *
 */
public class CreateMenuElementDialog extends DialogWithTitle {

	private Text idText;
	private Combo levelCombo;
	private String subMenuId;
	private int level = 1;
	
	public CreateMenuElementDialog(Shell parentShell, String title) {
		super(parentShell, title);

	}

	protected void okPressed() {
		subMenuId = idText.getText().trim();
		level = Integer.parseInt((String) levelCombo.getData(levelCombo.getText()));
		if (subMenuId == null || "".equals(subMenuId)) {
			MessageDialog.openInformation(this.getShell(), "��ʾ", "�������Ӳ˵���ID");
			idText.setFocus();
			return;
		}
		//TODO Ψһ��У��
//		List<LfwWidgetConf> list = graph.getPagemeta().getWidgetConfList();
//		for (int i = 0, n = list.size(); i < n; i++) {
//			if (list.get(i).getId().equals(subMenuId)) {
//				MessageDialog.openWarning(this.getShell(), "��ʾ", "���Ӳ˵��Ѵ��ڣ�");
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
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("ID:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(200, 3));
		
		new Label(container, SWT.NONE).setText("�Ӳ˵�����:");
		levelCombo = new Combo(container, SWT.READ_ONLY);
		levelCombo.setLayoutData(createGridData(100, 1));
		levelCombo.add("һ��");
		levelCombo.setData("һ��", "1");
		levelCombo.add("����");
		levelCombo.setData("����", "2");
		levelCombo.add("����");
		levelCombo.setData("����", "3");
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
