package nc.lfw.editor.common.extend;

import java.util.List;

import nc.uap.lfw.core.base.ExtAttribute;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
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
public class ExtendAttributeDialog extends TitleAreaDialog {

	private List<ExtAttribute> attributeList;
	
	private Text keyText;
	private Text valueText;
	private Text descText;
	
	private String key = "";
	private String value = "";
	private String desc = "";
	
	private String oldKey = "";
	private String oldValue = "";
	private String oldDesc = "";
	
	public ExtendAttributeDialog(Shell parentShell) {
		super(parentShell);
	}

	protected void okPressed() {
		key = keyText.getText().trim();
		value = valueText.getText().trim();
		if ("".equals(key)) {
			MessageDialog.openInformation(this.getShell(), "提示", "请输入Key的值");
			keyText.setFocus();
			return;
		} else if (!checkAttribute(key) && !oldKey.equals(key)) {
			MessageDialog.openInformation(this.getShell(), "提示", "该属性已存在，请输入其它属性名称");
			keyText.setFocus();
			return;
		} else if ("".equals(value)) {
			MessageDialog.openInformation(this.getShell(), "提示", "请输入Value的值");
			valueText.setFocus();
			return;
		}
		desc = descText.getText() == null ? "" : descText.getText().trim();
		
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(450, 250);
	}

	protected Control createDialogArea(Composite parent) {
		setTitle("扩展属性");
		setMessage("请输入扩展属性基本信息", IMessageProvider.NONE);
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("Key:");
		keyText = new Text(container, SWT.BORDER);
		keyText.setLayoutData(createGridData(150, 1));
		keyText.setText(oldKey);

		new Label(container, SWT.NONE).setText("Value:");
		valueText = new Text(container, SWT.BORDER);
		valueText.setLayoutData(createGridData(150, 1));
		valueText.setText(oldValue);
		
		new Label(container, SWT.NONE).setText("描述:");
		descText = new Text(container, SWT.BORDER);
		descText.setLayoutData(createGridData(353, 3));
		descText.setText(oldDesc);
		
		return container;
	}
	
	/**
	 * 编辑时设置初始值
	 * @param key
	 * @param value
	 * @param desc
	 * @param attributeList
	 */
	public void initDialogArea(String key, String value, String desc, List<ExtAttribute> attributeList) {
		this.oldKey = key == null ? "" : key;
		this.oldValue = value == null ? "" : value;
		this.oldDesc = desc == null ? "" : desc;
		setAttributeList(attributeList);
	}
	
	/**
	 * 检查key是否重复
	 * @param name
	 * @return
	 */
	private boolean checkAttribute(String key) {
		for (int i = 0, n = attributeList.size(); i < n; i++) {
			if (key.equals(attributeList.get(i).getKey()))
				return false;
		}
		return true;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public void setAttributeList(List<ExtAttribute> attributeList) {
		this.attributeList = attributeList;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

}
