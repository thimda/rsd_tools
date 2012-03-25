package nc.lfw.editor.pagemeta.relation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Widget连接器向导 构建自定义参数对话框内容
 * 
 * @author guoweic
 *
 */
public class WidgetRelationSelfParamPanel extends Canvas {
	
	/**
	 * 参数名输入框
	 */
	private Text nameText;

	/**
	 * 参数描述输入框
	 */
	private Text descText;
	
	public WidgetRelationSelfParamPanel(Composite parent, int style) {
		super(parent, style);
		initUI();
	}

	private void initUI() {
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(this, SWT.NONE).setText("参数名:");
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(createGridData(200, 1));

		new Label(this, SWT.NONE).setText("参数描述:");
		descText = new Text(this, SWT.BORDER);
		descText.setLayoutData(createGridData(200, 1));
		
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public Text getNameText() {
		return nameText;
	}

	public Text getDescText() {
		return descText;
	}

}
