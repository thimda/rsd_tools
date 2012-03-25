package nc.uap.lfw.funnode;

import java.io.File;
import java.util.List;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.design.itf.TemplateVO;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * 关联NC查询模板对话框
 * @author zhangxya
 *
 */
public class NCQueryTemplateDialog  extends Dialog {

	private TableViewer tv;
	private String funnode;
	private LfwWidget widget;
	private String projectPath;
	private String filePath;
	public NCQueryTemplateDialog(Shell parentShell, String funnode,  String filePath) {
		super(parentShell);
		this.funnode = funnode;
		this.filePath = filePath;
	}
	
	protected Point getInitialSize() {
		return new Point(418,500); 
	}

	private List<TemplateVO> queryList = null;
	protected void okPressed() {
		if(widget == null)
			widget = LFWPersTool.getCurrentWidget();
		widget = LFWPersTool.getCurrentWidget();
		TableItem item = tv.getTable().getSelection()[0];
		TemplateVO temp = (TemplateVO)item.getData();
		String nodekey = temp.getNodekey();
		if(nodekey == null)
			nodekey = "";
//		widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, nodekey);
		widget.setFrom("NCQ");
		projectPath = LFWPersTool.getProjectPath();
		File file = LFWPersTool.getCurrentWidgetTreeItem().getFile();
		String path = file.getPath() + "/widget.wd";
		LFWPersTool.checkOutFile(path);
		NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
		super.okPressed();
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group templateGroup = new Group(container, SWT.NONE);
		templateGroup.setLayout(new GridLayout(1,false));
		templateGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateGroup.setText("Widget关联查询模板");
	
		tv = new TableViewer(templateGroup,SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Table templateTree = tv.getTable();
		templateTree.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateTree.setLinesVisible(true);
		templateTree.setHeaderVisible(true);
		
		String[] colNames = new String[]{"模板主键", "模板名称", "nodekey","模板类型"};
		for (int i = 0; i < colNames.length; i++) {
			TableColumn col = new TableColumn(templateTree, SWT.None, i);
			col.setText(colNames[i]);
			col.setWidth(125);
			col.setAlignment(SWT.LEFT);
		}
		tv.setLabelProvider(new LabelContentProvider());
		tv.setContentProvider(new FuncContentProvider());
		tv.setColumnProperties(colNames);
		if(queryList == null)
			queryList = getQueryTemplateVOByfunnode(funnode);
		tv.setInput(queryList);
		//每个Cell的编辑Editor，todo
		CellEditor[] cellEditors = new CellEditor[colNames.length];
		cellEditors[0] = new TextCellEditor(templateTree);;
		cellEditors[1] = new TextCellEditor(templateTree);
		tv.setCellEditors(cellEditors);
		
		setSelectedData(widget);
		return container;
	}
	
	public void setSelectedData(LfwWidget widget){
		if(widget == null)
			widget = LFWPersTool.getCurrentWidget();
//		String nodekey = (String)widget.getExtendAttributeValue(ExtAttrConstants.BILL_NODEKEY);
//		if(nodekey == null)
//			return;
//		if(nodekey != null && !nodekey.equals("")){
//			TableItem [] items = tv.getTable().getItems();
//			if(items == null || items.length == 0)
//				return;
//			for (int k = 0; k < items.length; k++) {
//				if(((TemplateVO)items[k].getData()).getNodekey().equals(nodekey)){
//					tv.getTable().setSelection(items[k]);
//					break;
//				}
//			}
//		}
//		else {
//			TableItem [] items = tv.getTable().getItems();
//			if(items == null || items.length == 0)
//				return;
//			for (int k = 0; k < items.length; k++) {
//				if(((TemplateVO)items[k].getData()).getNodekey() == null && (nodekey == null || nodekey.equals(""))){
//					tv.getTable().setSelection(items[k]);
//					break;
//				}
//			}
//		}
	}
	class LabelContentProvider implements ITableLabelProvider{

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
		TemplateVO template = (TemplateVO)element;
		switch (columnIndex) {
		case 0:
				return template.getPk_template();
		case 1:
			return template.getTemplatecaption();
		case 2:
			return template.getNodekey();
		default:
			return null;
			}
		}

		public void addListener(ILabelProviderListener listener) {
			
		}

		public void dispose() {
			
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			
		}

	
	}
	
	class FuncContentProvider implements ITreeContentProvider{

		//private TemplateVO[] vos = null;
		public FuncContentProvider() {
		}

		public Object[] getChildren(Object parentElement) {
			return null;
		}
		
		public boolean hasChildren(Object element) {
			return false;
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof List)
				return ((List) inputElement).toArray();
			else 
				return new Object[0];
		}

		public void dispose() {
		}

		public Object getParent(Object element) {
			return null;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			
		}


	}

	public List<TemplateVO> getQueryTemplateVOByfunnode(String funnode){
		return NCConnector.getQeuryTemplateByFuncnode(funnode);
	}
}

