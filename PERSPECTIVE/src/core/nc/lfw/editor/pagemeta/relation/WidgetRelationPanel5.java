package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.page.Connector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Widget连接器 向导 最后一页内容
 * 
 * @author guoweic
 *
 */
public class WidgetRelationPanel5 extends Canvas {

	private Connector connector = null;
	
	/**
	 * ID输入框
	 */
	private Text idText;

	public WidgetRelationPanel5(Composite parent, int style, Connector connector) {
		super(parent, style);
		this.connector = connector;
		initUI();
	}

	private void initUI() {
		// 总体布局
		GridLayout mainLayout = new GridLayout();
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// ID区域
		Composite nameArea = new Composite(this, SWT.NONE);
		GridLayout idAreaLayout = new GridLayout(4, false);
		nameArea.setLayout(idAreaLayout);
		nameArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(nameArea, SWT.NONE).setText("连接器ID:");
		idText = new Text(nameArea, SWT.BORDER);
		idText.setLayoutData(createGridData(200, 3));
		
		loadPageData();
		
	}
	
	/**
	 * 编辑状态时，加载页面数据
	 */
	private void loadPageData() {
		if (null != connector) {
			String id = connector.getId();
			idText.setText(id);
		}
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public Text getIdText() {
		return idText;
	}

}
