package nc.lfw.editor.pagemeta.relation;

import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.IRefDataset;
import nc.uap.lfw.core.page.LfwWidget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Widget������ ���������� ��һҳ����
 * @author guoweic
 *
 */
public class WidgetRelationParamPanel1 extends Canvas {


	private WidgetElementObj sourceWidget;
	
	/**
	 * ��ǰ�������
	 */
	private WebElement currentComp;
	
	/**
	 * �����������
	 */
	private Text nameText;
	
	/**
	 * ��Դѡ������
	 */
	private Composite sourceContainer;
	
	private Group dsGroup;
	private Group compGroup;
	
	/**
	 * ������
	 */
	private String name = "";

	public WidgetRelationParamPanel1(Composite parent, int style, WidgetElementObj sourceWidget) {
		super(parent, style);
		this.sourceWidget = sourceWidget;
		initUI();
	}

	private void initUI() {
		// ���岼��
		GridLayout mainLayout = new GridLayout();
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// ����������
		Composite nameArea = new Composite(this, SWT.NONE);
		GridLayout idAreaLayout = new GridLayout(4, false);
		nameArea.setLayout(idAreaLayout);
		nameArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(nameArea, SWT.NONE).setText("������:");
		nameText = new Text(nameArea, SWT.BORDER);
		nameText.setLayoutData(createGridData(200, 3));
		
		// ѡ����Դ����
//		new Label(this, SWT.NONE).setText("��Դ:");
		sourceContainer = initSourceContainer(this);
		
		
	}

	/**
	 * ������Դѡ������
	 * @param parent
	 */
	private Composite initSourceContainer(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout gridLayout = new GridLayout(3, false);
		
		dsGroup = new Group(container, SWT.NONE);
		dsGroup.setLayout(gridLayout);
		dsGroup.setLayoutData(gridData);
		dsGroup.setText("���ݼ�");
		

		compGroup = new Group(container, SWT.NONE);
		compGroup.setLayout(gridLayout);
		compGroup.setLayoutData(gridData);
		compGroup.setText("���");
		
		
		//TODO
		LfwWidget widget = LFWPersTool.getCurrentPageMeta().getWidget(sourceWidget.getWidget().getId());

		boolean isFirst = true;
//		Map<String, Dataset> dsMap = widget.getViewModels().getDatasetMap();
//		for (String dsId : dsMap.keySet()) {
//			Dataset dataset = dsMap.get(dsId);
//			if (!(dataset instanceof IRefDataset)) {
//				Button radio = new Button(dsGroup, SWT.RADIO);
//				radio.setLayoutData(createGridData(150, 1));
//				radio.setText(dsId);
//				radio.setData(dsId, dataset);
//				if (isFirst) {
//					radio.setSelection(true);
//					currentComp = dataset;
//					isFirst = false;
//				}
		Dataset[] dsArray = widget.getViewModels().getDatasets();
		for (Dataset dataset : dsArray) {
			if (!(dataset instanceof IRefDataset)) {
				Button radio = new Button(dsGroup, SWT.RADIO);
				radio.setLayoutData(createGridData(150, 1));
				radio.setText(dataset.getId());
				radio.setData(dataset.getId(), dataset);
				if (isFirst) {
					radio.setSelection(true);
					currentComp = dataset;
					isFirst = false;
				}
				
				radio.addSelectionListener(new SelectionAdapter(){
					public void widgetSelected(SelectionEvent e) {
						Button radio = (Button) e.getSource();
						if (radio.getSelection()) {
							currentComp = (WebElement) radio.getData(radio.getText());
							
							Control[] compGroupChildren = compGroup.getChildren();
							for (Control control : compGroupChildren) {
								if (control instanceof Button) {
									((Button) control).setSelection(false);
								}
							}
							
						}
					}
				});
			}
			
		}
		
		Map<String, WebComponent> compMap = widget.getViewComponents().getComponentsMap();
		for (String compId : compMap.keySet()) {
			Button radio = new Button(compGroup, SWT.RADIO);
			radio.setLayoutData(createGridData(150, 1));
			radio.setText(compId);
			radio.setData(compId, compMap.get(compId));
			if (isFirst) {
				radio.setSelection(true);
				currentComp = compMap.get(compId);
				isFirst = false;
			}
			
			radio.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					Button radio = (Button) e.getSource();
					if (radio.getSelection()) {
						currentComp = (WebElement) radio.getData(radio.getText());
						
						Control[] dsGroupChildren = dsGroup.getChildren();
						for (Control control : dsGroupChildren) {
							if (control instanceof Button) {
								((Button) control).setSelection(false);
							}
						}
						
					}
				}
			});
		}
		
		return container;
		
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public WebElement getCurrentComp() {
		return currentComp;
	}

	public void setCurrentComp(WebElement currentComp) {
		this.currentComp = currentComp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Text getNameText() {
		return nameText;
	}

}
