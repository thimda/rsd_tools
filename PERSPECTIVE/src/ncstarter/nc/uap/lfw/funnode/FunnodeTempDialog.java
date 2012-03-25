package nc.uap.lfw.funnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.design.itf.TemplateVO;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 节点已有模板
 * @author zhangxya
 *
 */
public class FunnodeTempDialog extends Dialog {

	private TreeViewer maintreeviewer;
	private TableViewer tv;
//	private String funnode;
	private CheckboxTableViewer ctv ;
	private List<TemplateVO> templatevos = null;
	private TreeViewer treeviewer;
	
	public FunnodeTempDialog(Shell parentShell, TreeViewer treeviewer) {
		super(parentShell);
		this.treeviewer = treeviewer;
	}
	
	protected Point getInitialSize() {
		return new Point(650,550); 
	}
	
	
	@SuppressWarnings("unchecked")
	protected void okPressed() {
		Object[] items = ctv.getCheckedElements();
		List list = Arrays.asList(items);
		List arrayList = new ArrayList();
		List showList = (List) treeviewer.getInput();
		if(showList != null){
			for (int i = 0; i < showList.size(); i++) {
				arrayList.add(showList.get(i));
			}
		}
		arrayList.addAll(list);
		treeviewer.setInput(arrayList);
		super.okPressed();
		
	}
	
	protected Control createDialogArea(Composite parent) {
		templatevos = getTemplateVOs();
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group templateGroup = new Group(container, SWT.NONE);
		templateGroup.setLayout(new GridLayout(2,false));
		templateGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateGroup.setText("节点关联模板");
		
		maintreeviewer = new TreeViewer(templateGroup, SWT.BORDER | SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Tree  maintree = maintreeviewer.getTree();
		maintree.setLayoutData(new GridData(GridData.FILL_BOTH));
//		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		maintreeviewer.setLabelProvider(new TreeContentProvider());
		maintreeviewer.setContentProvider(new MainTreeContentProvider());
		//首先显示四大模板类型
		maintreeviewer.setInput(getTemplateTypes());
		maintree.setHeaderVisible(true);
		
		maintree.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				Tree tree = maintreeviewer.getTree();
				TreeItem[] tis = tree.getSelection();
				if (tis == null || tis.length == 0)
					return;
				TreeItem ti = tis[0];
				Object object = ti.getData();
				if(!(object instanceof Integer)){
					TreeItem parentItem = ti.getParentItem();
					Integer parentObject = (Integer) parentItem.getData();
					List<TemplateVO> listnew = getTemplateByModuelName(parentObject, ti.getData().toString());
					tv.setInput(listnew);
				}
			}
		});
		
	
		Group grouId = new Group(templateGroup, SWT.NONE);
		grouId.setLayoutData(new GridData(GridData.FILL_BOTH));
		grouId.setLayout(new GridLayout(1,false));
		
		Group grouId1 = new Group(grouId, SWT.NONE);
		grouId1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grouId1.setLayout(new GridLayout(2,false));
		
		Label labeltemp = new Label(grouId1, SWT.NONE);
		labeltemp.setText("查找指定模板:");
		Text searchText = new Text(grouId1, SWT.NONE);
		searchText.setLayoutData(new GridData(220,15));
		searchText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				Text t =(Text) e.widget;
				String str = t.getText();
				buildTree(str);
			}
		});
		
		tv = new TableViewer(grouId,SWT.CHECK|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Table templateTree = tv.getTable();
		ctv =  new CheckboxTableViewer(templateTree);;
		templateTree.setLayoutData(new GridData(GridData.FILL_BOTH));
//		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateTree.setLinesVisible(true);
		templateTree.setHeaderVisible(true);
		
		String[] colNames = new String[]{"模板主键", "模板名称", "模板编码"};
		for (int i = 0; i < colNames.length; i++) {
			TableColumn col = new TableColumn(templateTree, SWT.None, i);
			col.setText(colNames[i]);
			col.setWidth(150);
			col.setAlignment(SWT.LEFT);
		}
		//MenuItemPropertiesViewProvider provider = new MenuItemPropertiesViewProvider();
		tv.setLabelProvider(new LabelContentProvider());
		tv.setContentProvider(new FuncContentProvider());
		tv.setColumnProperties(colNames);
		tv.setInput(null);
		//每个Cell的编辑Editor，todo
		CellEditor[] cellEditors = new CellEditor[colNames.length];
		cellEditors[0] = new TextCellEditor(templateTree);;
		cellEditors[1] = new TextCellEditor(templateTree);
		tv.setCellEditors(cellEditors);
		return container;
	}
	
	
	private void buildTree(String filter){
		if(filter == null || filter.equals("")){
			tv.setInput(null);
			return;
		}
		
		List<TemplateVO> tempsList = new ArrayList<TemplateVO>();
		for (int i = 0; i < templatevos.size(); i++) {
			TemplateVO temp = templatevos.get(i);
			if(temp.getTemplatecaption() != null && temp.getTemplatecaption().toLowerCase().indexOf(filter.toLowerCase()) != -1
					|| (temp.getTemplatecode() != null && temp.getTemplatecode().toLowerCase().indexOf(filter.toLowerCase()) != -1))
				tempsList.add(temp);
		}
		tv.setInput(tempsList);
	}
		
		

	public List<TemplateVO> getTemplateByModuelName(Integer parentItem, String modelcode) {
		List<TemplateVO> list = new ArrayList<TemplateVO>();
		if(modelcode.equals("未定义")){
			for (int i = 0; i < templatevos.size(); i++) {
				TemplateVO temp = templatevos.get(i);
				if(temp.getTemplatetype().equals(parentItem) && (temp.getModulecode() == null || temp.getModulecode().equals("")))
					list.add(temp);
			}
		}else{
			for (int i = 0; i < templatevos.size(); i++) {
				TemplateVO temp = templatevos.get(i);
				if(temp.getTemplatetype().equals(parentItem) && temp.getModulecode() != null && temp.getModulecode().equals(modelcode))
					list.add(temp);
			}
		}
		return list;
	}
	
	class TreeContentProvider implements ILabelProvider{

		public Image getImage(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getText(Object element) {
			if(element.equals(TemplateVO.TEMPLAGE_TYPE_BILL))
				return "单据模板";
			else 
				if(element.equals(TemplateVO.TEMPLAGE_TYPE_PRINT))
					return "打印模板";
			else if(element.equals(TemplateVO.TEMPLAGE_TYPE_QUERY))
				return "查询模板";
			else if(element.equals(TemplateVO.TEMPLAGE_TYPE_REPORT))
				return "报表模板";
			return element.toString();
		}

		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

	

	
	}
	
	class MainTreeContentProvider implements ITreeContentProvider{
		public MainTreeContentProvider() {
		}

		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof Integer){
				Integer type = (Integer)parentElement;
				return getModelueNames(type).toArray();
			}
			//if(parentElement instanceof )
			return null;
		}
		
		public boolean hasChildren(Object element) {
			if(element instanceof Integer)
				return true;
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
			// TODO Auto-generated method stub
			return null;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
			
		}


	}

	
	class LabelContentProvider implements ITableLabelProvider{

		public Image getColumnImage(Object element, int columnIndex) {
			// TODO Auto-generated method stub
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
			return template.getTemplatecode();
		default:
			return null;
			}
		}

		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			return null;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
			
		}


	}
	
	public List<Integer> getTemplateTypes(){
		//templatevos = getTemplateVOs();
		List<Integer> typelist = new ArrayList<Integer>();
		typelist.add(TemplateVO.TEMPLAGE_TYPE_BILL);
		typelist.add(TemplateVO.TEMPLAGE_TYPE_QUERY);
		typelist.add(TemplateVO.TEMPLAGE_TYPE_REPORT);
		typelist.add(TemplateVO.TEMPLAGE_TYPE_PRINT);
		return typelist;
	}
	
	public List<String> getModelueNames(Integer type){
		List<String> modulenames = new ArrayList<String>();
		for (int i = 0; i < templatevos.size(); i++) {
			if(templatevos.get(i).getTemplatetype().equals(type)){
				String modelname = templatevos.get(i).getModulecode();
				if(modelname != null && !modelname.equals("") && !modulenames.contains(modelname))
					modulenames.add(modelname);
			}
		}
		modulenames.add("未定义");
		return modulenames;
	}

	
	
	public List<TemplateVO> getTemplateVOs() {
		return NCConnector.getTemplateVOs();
	}
}
