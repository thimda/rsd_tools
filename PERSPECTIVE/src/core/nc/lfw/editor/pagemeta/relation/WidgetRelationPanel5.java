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
 * Widget������ �� ���һҳ����
 * 
 * @author guoweic
 *
 */
public class WidgetRelationPanel5 extends Canvas {

	private Connector connector = null;
	
	/**
	 * ID�����
	 */
	private Text idText;

	public WidgetRelationPanel5(Composite parent, int style, Connector connector) {
		super(parent, style);
		this.connector = connector;
		initUI();
	}

	private void initUI() {
		// ���岼��
		GridLayout mainLayout = new GridLayout();
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// ID����
		Composite nameArea = new Composite(this, SWT.NONE);
		GridLayout idAreaLayout = new GridLayout(4, false);
		nameArea.setLayout(idAreaLayout);
		nameArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(nameArea, SWT.NONE).setText("������ID:");
		idText = new Text(nameArea, SWT.BORDER);
		idText.setLayoutData(createGridData(200, 3));
		
		loadPageData();
		
	}
	
	/**
	 * �༭״̬ʱ������ҳ������
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
