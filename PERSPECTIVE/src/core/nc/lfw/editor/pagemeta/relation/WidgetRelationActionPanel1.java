package nc.lfw.editor.pagemeta.relation;

import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;

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
 * Widget连接器 执行内容构造向导 第一页内容
 * 
 * @author guoweic
 *
 */
public class WidgetRelationActionPanel1 extends Canvas {


	private WidgetElementObj targetWidget;
	
	/**
	 * 当前组件对象
	 */
	private WebElement currentComp;
	
	/**
	 * 参数名输入框
	 */
	private Text nameText;
	
	/**
	 * 目标选择区域
	 */
	private Composite targetContainer;

	private Group widgetGroup;
	private Group dsGroup;
	private Group compGroup;
	
	/**
	 * 参数名
	 */
	private String name = "";

	public WidgetRelationActionPanel1(Composite parent, int style, WidgetElementObj targetWidget) {
		super(parent, style);
		this.targetWidget = targetWidget;
		initUI();
	}

	private void initUI() {
		// 总体布局
		GridLayout mainLayout = new GridLayout();
		this.setLayout(mainLayout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// 参数名区域
		Composite nameArea = new Composite(this, SWT.NONE);
		GridLayout idAreaLayout = new GridLayout(4, false);
		nameArea.setLayout(idAreaLayout);
		nameArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(nameArea, SWT.NONE).setText("名称:");
		nameText = new Text(nameArea, SWT.BORDER);
		nameText.setLayoutData(createGridData(200, 3));
		
		// 选择目标区域
//		new Label(this, SWT.NONE).setText("目标:");
		targetContainer = initSourceContainer(this);
		
		
	}

	/**
	 * 加载目标选择区域
	 * @param parent
	 */
	private Composite initSourceContainer(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout gridLayout = new GridLayout(3, false);
		
		widgetGroup = new Group(container, SWT.NONE);
		widgetGroup.setLayout(gridLayout);
		widgetGroup.setLayoutData(gridData);
		widgetGroup.setText(LFWPerspectiveNameConst.WIDGET_CN);
		
		dsGroup = new Group(container, SWT.NONE);
		dsGroup.setLayout(gridLayout);
		dsGroup.setLayoutData(gridData);
		dsGroup.setText("数据集");
		

		compGroup = new Group(container, SWT.NONE);
		compGroup.setLayout(gridLayout);
		compGroup.setLayoutData(gridData);
		compGroup.setText("组件");
		
		
		LfwWidget widget = LFWPersTool.getCurrentPageMeta().getWidget(targetWidget.getWidget().getId());

		boolean isFirst = true;
		
		Button radioWd = new Button(widgetGroup, SWT.RADIO);
		radioWd.setLayoutData(createGridData(150, 1));
		radioWd.setText(widget.getId());
		radioWd.setData(widget.getId(), widget);
		if (isFirst) {
			radioWd.setSelection(true);
			currentComp = widget;
			isFirst = false;
		}
		
		radioWd.addSelectionListener(new SelectionAdapter(){
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
					
					Control[] compGroupChildren = compGroup.getChildren();
					for (Control control : compGroupChildren) {
						if (control instanceof Button) {
							((Button) control).setSelection(false);
						}
					}
					
				}
			}
		});
		
//		Map<String, Dataset> dsMap = widget.getViewModels().getDatasetMap();
//		for (String dsId : dsMap.keySet()) {
//			Button radio = new Button(dsGroup, SWT.RADIO);
//			radio.setLayoutData(createGridData(150, 1));
//			radio.setText(dsId);
//			radio.setData(dsId, dsMap.get(dsId));
//			if (isFirst) {
//				radio.setSelection(true);
//				currentComp = dsMap.get(dsId);
//				isFirst = false;
//			}
//			
//			radio.addSelectionListener(new SelectionAdapter(){
//				public void widgetSelected(SelectionEvent e) {
//					Button radio = (Button) e.getSource();
//					if (radio.getSelection()) {
//						currentComp = (WebElement) radio.getData(radio.getText());
//
//						Control[] wdGroupChildren = widgetGroup.getChildren();
//						for (Control control : wdGroupChildren) {
//							if (control instanceof Button) {
//								((Button) control).setSelection(false);
//							}
//						}
//						
//						Control[] compGroupChildren = compGroup.getChildren();
//						for (Control control : compGroupChildren) {
//							if (control instanceof Button) {
//								((Button) control).setSelection(false);
//							}
//						}
//						
//					}
//				}
//			});
		Dataset[] dsArray = widget.getViewModels().getDatasets();
		for (Dataset dataset : dsArray) {
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

						Control[] wdGroupChildren = widgetGroup.getChildren();
						for (Control control : wdGroupChildren) {
							if (control instanceof Button) {
								((Button) control).setSelection(false);
							}
						}
						
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

						Control[] wdGroupChildren = widgetGroup.getChildren();
						for (Control control : wdGroupChildren) {
							if (control instanceof Button) {
								((Button) control).setSelection(false);
							}
						}
						
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
