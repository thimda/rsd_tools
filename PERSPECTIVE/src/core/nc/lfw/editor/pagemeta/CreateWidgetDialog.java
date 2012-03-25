package nc.lfw.editor.pagemeta;

import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;

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

public class CreateWidgetDialog extends TitleAreaDialog {

	private PagemetaGraph graph;
	private Text idText;
//	private Text refIdText;
	private String widgetId;
//	private String widgetRefId;
	
	public CreateWidgetDialog(Shell parentShell) {
		super(parentShell);

	}

	protected void okPressed() {
		widgetId = idText.getText().trim();
		String text = LFWPerspectiveNameConst.WIDGET_CN;
		if(graph != null && graph.getCurrentTreeItem() != null){
			text = LFWTool.getViewText(graph.getCurrentTreeItem());
		}
//		widgetRefId = refIdText.getText();
		if (widgetId == null || "".equals(widgetId)) {
			MessageDialog.openInformation(this.getShell(), "提示", "请输入" + text + "的ID");
			idText.setFocus();
			return;
		} 
//		else if (widgetRefId == null || "".equals(widgetRefId)) {
//			MessageDialog.openInformation(this.getShell(), "提示", "请输入Widget的RefId");
//			refIdText.setFocus();
//			return;
//		}
		WidgetConfig[] configs = graph.getPagemeta().getWidgetConfs();
		for (int i = 0, n = configs.length; i < n; i++) {
			if (configs[i].getId().equals(widgetId)) {
				MessageDialog.openWarning(this.getShell(), "提示", "该" + text + "已存在！");
				idText.setFocus();
				return;
			}
//			if (list.get(i).getRefId().equals(widgetRefId)) {
//				MessageDialog.openWarning(this.getShell(), "提示", "RefId已存在！");
//				refIdText.setFocus();
//				return;
//			}
		}
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(350, 250);
	}

	protected Control createDialogArea(Composite parent) {
		String text = LFWPerspectiveNameConst.WIDGET_CN;
		if(graph != null && graph.getCurrentTreeItem() != null){
			text = LFWTool.getViewText(graph.getCurrentTreeItem());
		}
		setTitle(text);
		setMessage("请输入" + text + "基本信息", IMessageProvider.NONE);
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("ID:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(200, 3));
		
//		new Label(container, SWT.NONE).setText("RefId:");
//		refIdText = new Text(container, SWT.BORDER);
//		refIdText.setLayoutData(createGridData(200, 3));
		
		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public String getWidgetId() {
		return widgetId;
	}

//	public String getWidgetRefId() {
//		return widgetRefId;
//	}

	public void setGraph(PagemetaGraph graph) {
		this.graph = graph;
	}

	public Text getIdText() {
		return idText;
	}

//	public Text getRefIdText() {
//		return refIdText;
//	}

}
