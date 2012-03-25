package nc.uap.portal.managerapps.dialog;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.om.ManagerNode;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 导入功能节点对话框
 * 
 * @author dingrf
 */
public class ImportManagerNodesDialog extends DialogWithTitle {

	private TableViewer tv = null;
	
	/**功能列表*/
	List<ManagerNode> managerNodes = null;
	
	/**page列表*/
	List<PageNode> pageNodes =  null;
	
	public TableViewer getTv() {
		return tv;
	}

	public void setTv(TableViewer tv) {
		this.tv = tv;
	}
	
	public List<ManagerNode> getManagerNodes() {
		return managerNodes;
	}

	public void setManagerNodes(List<ManagerNode> managerNodes) {
		this.managerNodes = managerNodes;
	}

	public List<PageNode> getPageNodes() {
		return pageNodes;
	}

	public void setPageNodes(List<PageNode> pageNodes) {
		this.pageNodes = pageNodes;
	}

	public ImportManagerNodesDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected void okPressed() {
		super.okPressed();
	}


	protected Point getInitialSize() {
		return new Point(400, 500);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		ViewForm vf = new ViewForm(container,SWT.NONE);
		vf.setLayoutData(new GridData(GridData.FILL_BOTH));
		vf.setLayout(new FillLayout());
		
		tv = new TableViewer(vf,SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);

		Table table = tv.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		createColumn(table, ImportManagerNodesCellModifier.colNames[0], 50, SWT.LEFT, 0);
		createColumn(table, ImportManagerNodesCellModifier.colNames[1], 150, SWT.LEFT, 1);
		createColumn(table, ImportManagerNodesCellModifier.colNames[2], 150, SWT.LEFT, 2);
		ImportManagerNodesProvider provider = new ImportManagerNodesProvider();
		tv.setLabelProvider(provider);
		tv.setContentProvider(provider);
		tv.setColumnProperties(ImportManagerNodesCellModifier.colNames);
		CellEditor[] cellEditors = new CellEditor[ImportManagerNodesCellModifier.colNames.length];
		cellEditors[0] = new CheckboxCellEditor(table);
		cellEditors[1] = new TextCellEditor(table);
		cellEditors[2] = new TextCellEditor(table);
		tv.setCellEditors(cellEditors);
		tv.setCellModifier(new ImportManagerNodesCellModifier(this));

		vf.setContent(tv.getControl());
		
		return container;
	}
	
	private TableColumn createColumn(Table table, String colName , int width, int align, int index){
		TableColumn col = new TableColumn(table, SWT.None, index);
		col.setText(colName);
		col.setWidth(width);
		col.setAlignment(align);
		return col;
	}
	
	
	public  void loadNodes(){
		
		Map<String , ManagerNode> managerNodeMap = new HashMap<String ,ManagerNode>();
		if (managerNodes == null){
			managerNodes = new ArrayList<ManagerNode>();
		}
		for(ManagerNode m : managerNodes){
			managerNodeMap.put(m.getId(), m);
		}
		String projectPath = LFWPersTool.getProjectWithBcpPath();
		String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
			
		Map<String, String>[] pageNamesMap = PortalConnector.getPageNames(new String[] {projectPath});
		
		pageNodes = new ArrayList<PageNode>();

		if(pageNamesMap != null){
			File fileFolder = new File(projectPath + "/web/html/nodes");
			File[] children = fileFolder.listFiles();
			if(children != null){
				for (int j = 0; j < children.length; j++) {
					if(children[j].isDirectory()){
						scanDir(pageNodes, children[j], pageNamesMap[0],null,projectModuleName,managerNodeMap);
					}
				}
			}
		}
		tv.setInput(pageNodes);
		
	}
	
	private void scanDir(List<PageNode> pageNodes, File dir, Map<String, String> pageNames,String parentId,String projectModuleName,Map<String,ManagerNode> managerNodeMap){
		String id = null;
		if(judgeIsPMFolder(dir)){
			id = dir.getName();
			PageNode pageNode = new PageNode();
			 
			pageNode.setId(id);
			if (managerNodeMap.containsKey(id))
				pageNode.setCheck(true);
			else
				pageNode.setCheck(false);
			if (parentId == null){
				pageNode.setName(pageNames.get(id));
			}
			else{
				pageNode.setName(pageNames.get(parentId + "." + id));
			}
			String url = "/core/uimeta.um?pageId="+id;
			pageNode.setUrl(url);
			pageNodes.add(pageNode);
		}
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if(fs[i].isDirectory())
				scanDir(pageNodes, fs[i],pageNames,id,projectModuleName,managerNodeMap);
		}
	}
	
	private boolean judgeIsPMFolder(File fold) {
		File[] childChildren = fold.listFiles();
		if(childChildren == null)
			return false;
		for (int i = 0; i < childChildren.length; i++) {
			if(childChildren[i].getName().equals("pagemeta.pm"))
				return true;
		}
		return false;
		
	}
	
}

