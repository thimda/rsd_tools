package nc.uap.lfw.funnode;

import java.io.File;
import java.util.List;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.design.itf.TemplateVO;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * widget����NC����ģ��
 * @author zhangxya
 *
 */
public class WidgetTempDialog extends DialogWithTitle {

	private TableViewer tv;
	private String funnode;
	private LfwWidget widget;
	private String projectPath;
	private String filePath;
	public WidgetTempDialog(Shell parentShell, String title, String funnode,  String filePath) {
		super(parentShell, title);
		this.funnode = funnode;
		this.filePath = filePath;
	}
	
	protected Point getInitialSize() {
		return new Point(418,500); 
	}

	
	protected void okPressed() {
//		Integer pagetype = (Integer)pagetypeComp.getData(pagetypeComp.getText());
		widget = LFWPersTool.getCurrentWidget();
//		widget.setExtendAttribute(ExtAttrConstants.BILL_PAGETYPE, pagetype);
		TableItem item = tv.getTable().getSelection()[0];
		TemplateVO temp = (TemplateVO)item.getData();
		String nodekey = temp.getNodekey();
		if(nodekey == null)
			nodekey = "";
//		widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, nodekey);
		widget.setFrom("NC");
		projectPath = LFWPersTool.getProjectPath();
		File file = LFWPersTool.getCurrentWidgetTreeItem().getFile();
		String path = file.getPath() + "/widget.wd";
		LFWPersTool.checkOutFile(path);
		NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
		super.okPressed();
	}
	
	private Combo pagetypeComp = null;
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group templateGroup = new Group(container, SWT.NONE);
		templateGroup.setLayout(new GridLayout(1,false));
		templateGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateGroup.setText("Widget����ģ��");
		
		Group fieldGroup = new Group(templateGroup, SWT.NONE);
		fieldGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fieldGroup.setLayout(new GridLayout(2,false));
		//fieldGroup.setText("���ܽڵ�����");
		
		Label pageTypeLabel = new Label(fieldGroup, SWT.NONE);
		pageTypeLabel.setText("ҳ������:");
		pagetypeComp = new Combo(fieldGroup, SWT.READ_ONLY);
		pagetypeComp.setLayoutData(new GridData(95,15));
//		pagetypeComp.add("����������_��Ƭ����");
//		pagetypeComp.setData("����������_��Ƭ����", ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST);
//		
//		pagetypeComp.add("����������_�б�����");
//		pagetypeComp.setData("����������_�б�����", ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST);
//		
//		pagetypeComp.add("������ͷ��_��Ƭ����");
//		pagetypeComp.setData("������ͷ��_��Ƭ����", ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST);
//		
//		pagetypeComp.add("������ͷ��_�б�����");
//		pagetypeComp.setData("������ͷ��_�б�����", ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST);
//		
//		pagetypeComp.add("��Ƭ������");
//		pagetypeComp.setData("��Ƭ������", ExtAttrConstants.PAGETYPE_CARD_MASCHI);
//		pagetypeComp.add("��Ƭ����ͷ��");
//		pagetypeComp.setData("��Ƭ����ͷ��", ExtAttrConstants.PAGETYPE_CARD_SINGAL);
//		pagetypeComp.add("�б�������");
//		pagetypeComp.setData("�б�������", ExtAttrConstants.PAGETYPE_LIST_MASCHI);
//		pagetypeComp.add("�б���ͷ��");
//		pagetypeComp.setData("�б���ͷ��", ExtAttrConstants.PAGETYPE_LIST_SINGAL);
//		pagetypeComp.select(0);
		
		tv = new TableViewer(templateGroup,SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Table templateTree = tv.getTable();
		templateTree.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateTree.setLinesVisible(true);
		templateTree.setHeaderVisible(true);
		
		String[] colNames = new String[]{"ģ������", "ģ������", "nodekey","ģ������"};
		for (int i = 0; i < colNames.length; i++) {
			TableColumn col = new TableColumn(templateTree, SWT.None, i);
			col.setText(colNames[i]);
			col.setWidth(125);
			col.setAlignment(SWT.LEFT);
		}
		tv.setLabelProvider(new LabelContentProvider());
		tv.setContentProvider(new FuncContentProvider());
		tv.setColumnProperties(colNames);
		tv.setInput(getTemplateVOByfunnode(funnode));
		//ÿ��Cell�ı༭Editor��todo
		CellEditor[] cellEditors = new CellEditor[colNames.length];
		cellEditors[0] = new TextCellEditor(templateTree);;
		cellEditors[1] = new TextCellEditor(templateTree);
		tv.setCellEditors(cellEditors);
		
		setSelectedData(widget);
		return container;
	}
	
	public void setSelectedData(LfwWidget widget){
		String pagetypevalue = "";
		if(widget == null)
			widget = LFWPersTool.getCurrentWidget();
//		if(widget.getExtendAttribute(ExtAttrConstants.BILL_PAGETYPE) == null)
//			return;
//		if(widget.getExtendAttributeValue(ExtAttrConstants.BILL_PAGETYPE) == null)
//			return;
//		Integer pagetype = Integer.valueOf((String) widget.getExtendAttribute(ExtAttrConstants.BILL_PAGETYPE).getValue().toString());
//		if(pagetype == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST)
//			pagetypevalue = "����������_��Ƭ����";
//		else if(pagetype == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST)
//			pagetypevalue = "����������_�б�����";
//		else if(pagetype == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST)
//			pagetypevalue = "������ͷ��_��Ƭ����";
//		else if(pagetype == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST)
//			pagetypevalue = "������ͷ��_�б�����";
//		else if(pagetype == ExtAttrConstants.PAGETYPE_CARD_MASCHI)
//			pagetypevalue = "��Ƭ������";
//		else if(pagetype == ExtAttrConstants.PAGETYPE_CARD_SINGAL)
//			pagetypevalue = "��Ƭ����ͷ��";
//		else if(pagetype == ExtAttrConstants.PAGETYPE_LIST_MASCHI)
//			pagetypevalue = "�б�������";
//		else if(pagetype == ExtAttrConstants.PAGETYPE_LIST_SINGAL)
//			pagetypevalue = "�б���ͷ��";
		
		pagetypeComp.setText(pagetypevalue);
//		String nodekey = (String)widget.getExtendAttributeValue(ExtAttrConstants.BILL_NODEKEY);
//		if(nodekey != null){
//			if(nodekey != null && !nodekey.equals("")){
//				TableItem [] items = tv.getTable().getItems();
//				for (int k = 0; k < items.length; k++) {
//					if(((TemplateVO)items[k].getData()).getNodekey().equals(nodekey)){
//						tv.getTable().setSelection(items[k]);
//						break;
//					}
//				}
//			}else {
//				TableItem [] items = tv.getTable().getItems();
//				for (int k = 0; k < items.length; k++) {
//					if(((TemplateVO)items[k].getData()).getNodekey() == null && (nodekey == null || nodekey.equals(""))){
//						tv.getTable().setSelection(items[k]);
//						break;
//					}
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

	public List<TemplateVO> getTemplateVOs() {
		return NCConnector.getTemplateVOs();
	}
	
	public List<TemplateVO> getTemplateVOByfunnode(String funnode){
		return NCConnector.getTemplateByFuncnode(funnode);
	}
}

