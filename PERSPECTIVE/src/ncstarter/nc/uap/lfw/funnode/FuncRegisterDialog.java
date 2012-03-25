package nc.uap.lfw.funnode;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.DialogWithTitle;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.common.ExtAttrConstants;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.design.itf.FuncNodeVO;
import nc.uap.lfw.design.itf.TemplateVO;
import nc.uap.lfw.design.itf.TypeNodeVO;
import nc.uap.lfw.launcher.BilltypeLauncher;
import nc.uap.lfw.launcher.TemplateLauncher;
import nc.uap.lfw.launcher.WorkflowLauncher;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 发布到NC Dialog
 */
public class FuncRegisterDialog  extends DialogWithTitle {

	private TreeViewer treeViewer;
	private PageMeta pm;
	private Text funcodeText = null;
	private Text funnameText = null;
//	private Text dispText;
	private Text funUrlText = null;
	private Text funDesText = null;
	private Combo orgtypeComp = null;
	private Combo functionNodeComp = null;
	private Button editBillTypeBt = null;
	private Button editworkflowBt = null;
	private Button deletewfBt = null;
	private TypeNodeVO[] orgtypevos = null;
	private FuncNodeVO[] funvosExfun = null;
	private FuncNodeVO[] funvosOnlyfun = null;
	private String projectPath;
	private String folderPath;
	private String nodeid;
	private String userMessage;
	private String version;
	private TreeViewer tv;
	private FuncNodeVO[] funvos = null;
//	private TemplateVO[] temps = null;
//	private String orgtype = "";
	
	private boolean isSubmit = false;
	
	public PageMeta getPm() {
		return pm;
	}

	public void setPm(PageMeta pm) {
		this.pm = pm;
	}

	public FuncRegisterDialog(String nodeid, Shell parentShell, String title, PageMeta pm, String folderPath, String projectPath, String userMessage) {
		super(parentShell, title);
		this.nodeid = nodeid;
		this.pm = pm;
		this.projectPath = projectPath;
		this.folderPath = folderPath;
		this.userMessage = userMessage;
	}
	
	protected Point getInitialSize() {
		return new Point(800, 600); 
	}

	
	protected Control createDialogArea(Composite parent) {
		version = getVersion();
		if(funvos == null)
			funvos = getFuncVOs();
		if(version != null && version.equals(ExtAttrConstants.VERSION60)){
			if(funvosExfun == null)
				funvosExfun = getFuncVOsExceptFun();
			if(funvosOnlyfun == null)
				funvosOnlyfun = getFuncVOsOnlyFun();
		}
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		treeViewer = new TreeViewer(container,SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		//tree.setLinesVisible(true);
		//tree.setHeaderVisible(true);
		treeViewer.setContentProvider(new FuncContentProvider());
		treeViewer.setLabelProvider(new LabelContentProvider());
		treeViewer.setInput(getMainFunvos());
//		if(version != null && version.equals(ExtAttrConstants.VERSION5X)){
//				if(pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE) == null 
//						||  pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue() == null){
//				treeViewer.addSelectionChangedListener(new MySelectionChangeListener(){
//					
//				});
//			}
//		}
//			
		Canvas canvas = new Canvas(container, SWT.NONE);
		canvas.setLayout(new GridLayout(1, false));
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Group fieldGroup = new Group(canvas, SWT.NONE);
		fieldGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fieldGroup.setLayout(new GridLayout(4,false));
		fieldGroup.setText("功能节点属性");
		
		Label funcodeLabel = new Label(fieldGroup, SWT.NONE);
		funcodeLabel.setText("功能节点编码:");
		funcodeText = new Text(fieldGroup, SWT.NONE);
		funcodeText.setLayoutData(new GridData(120,15));
		funcodeText.setText("");
		
		Label funnameLabel = new Label(fieldGroup, SWT.NONE);
		funnameLabel.setText("功能节点名称:");
		funnameText = new Text(fieldGroup, SWT.NONE);
		funnameText.setLayoutData(new GridData(120,15));
		funnameText.setText("");
		
//		if(version != null && version.equals(ExtAttrConstants.VERSION5X)){
//			Label disPlayLabel = new Label(fieldGroup, SWT.NONE);
//			disPlayLabel.setText("显示编码");
//			dispText = new Text(fieldGroup, SWT.NONE);
//			dispText.setLayoutData(new GridData(120,15));
//		}
		
		Label orgtypeLabel = new Label(fieldGroup, SWT.NONE);
		orgtypeLabel.setText("组织类型:");
		orgtypeComp = new Combo(fieldGroup, SWT.READ_ONLY);
		orgtypeComp.setLayoutData(new GridData(95,15));
		if(orgtypevos == null)
			orgtypevos = getAllMainOrgType();
		for (int i = 0; i < orgtypevos.length; i++) {
			String id = orgtypevos[i].getPk_typenode();
			String name = orgtypevos[i].getTypename();
			if(name != null){
				orgtypeComp.add(name);
				orgtypeComp.setData(name, id);
			}
		}
		orgtypeComp.select(0);
		
		Label funtionNodeLabel = new Label(fieldGroup, SWT.NONE);
		funtionNodeLabel.setText("功能节点类型:");
		functionNodeComp = new Combo(fieldGroup, SWT.READ_ONLY);
		functionNodeComp.setLayoutData(new GridData(95,15));
		String[] functionitems = {"业务类型","管理类型","系统类型"};
		functionNodeComp.setItems(functionitems);
		functionNodeComp.select(0);
		
		Label funDesLabel = new Label(fieldGroup, SWT.NONE);
		funDesLabel.setText("功能描述:");
		funDesText = new Text(fieldGroup, SWT.NONE);
		funDesText.setLayoutData(new GridData(120,15));
		funDesText.setText("");
		
		Label funURLLabel = new Label(fieldGroup, SWT.NONE);
		funURLLabel.setText("功能类名:");
		funUrlText = new Text(fieldGroup, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.heightHint = 15;
		gridData.widthHint = 330;
		funUrlText.setLayoutData(gridData);
		funUrlText.setText("uimeta.um?pageId=" + nodeid + "&model=nc.uap.lfw.ncadapter.LicensePageModel.LicensePageModel");
	
		Group templateGroup = new Group(canvas, SWT.NONE);
		templateGroup.setLayout(new GridLayout(1,false));
		templateGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateGroup.setText("已关联模板");
		
		canvas = new Canvas(templateGroup, SWT.NONE);
		canvas.setLayout(new GridLayout(5, false));
		canvas.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button addNewBt = new Button(canvas, SWT.NONE);
		addNewBt.setText("新建NC模板");
		addNewBt.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				new TemplateLauncher().launch();
			}

			public void mouseUp(MouseEvent e) {
			}
			
		});
				
		tv = new TreeViewer(templateGroup,SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Tree templateTree = tv.getTree();
		templateTree.setLayoutData(new GridData(GridData.FILL_BOTH));
//		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		templateTree.setLinesVisible(true);
		templateTree.setHeaderVisible(true);
		
		String[] colNames = new String[]{"模板主键", "模板名称", "nodekey","模板类型"};
		for (int i = 0; i < colNames.length; i++) {
			TreeColumn col = new TreeColumn(templateTree, SWT.None, i);
			col.setText(colNames[i]);
			col.setWidth(120);
			col.setAlignment(SWT.LEFT);
		}
		TemplatePropertiewViewProvider provider = new TemplatePropertiewViewProvider();
		tv.setLabelProvider(provider);
		tv.setContentProvider(provider);
		tv.setColumnProperties(colNames);
		//每个Cell的编辑Editor，todo
		CellEditor[] cellEditors = new CellEditor[colNames.length];
		cellEditors[0] = new TextCellEditor(templateTree);;
		cellEditors[1] = new TextCellEditor(templateTree);
		cellEditors[2] = new TextCellEditor(templateTree);
		tv.setCellEditors(cellEditors);
		tv.setCellModifier(new LableCellModifier(tv));
		

		Button editNowBt = new Button(canvas, SWT.NONE);
		editNowBt.setText("关联所有模板");
		editNowBt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
			FunnodeTempDialog dlg = new FunnodeTempDialog(null, tv);
			dlg.open();
			}
		});
	
		editBillTypeBt = new Button(canvas, SWT.NONE);
		editBillTypeBt.setText("编辑单据类型");
		editBillTypeBt.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				new BilltypeLauncher().launch();
			}

			public void mouseUp(MouseEvent e) {
			}
			
		});
		
		editworkflowBt = new Button(canvas, SWT.NONE);
		editworkflowBt.setText("编辑测试流程");
		editworkflowBt.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				new WorkflowLauncher().launch();
			}

			public void mouseUp(MouseEvent e) {
			}
			
		});
		
		deletewfBt =  new Button(canvas, SWT.NONE);
		deletewfBt.setText("删除关联模板");
		deletewfBt.addSelectionListener(new SelectionAdapter(){
			@SuppressWarnings("unchecked")
			public void widgetSelected(SelectionEvent e){
			boolean tip = MessageDialog.openConfirm(null, "提示", "确定要删除关联模板吗?");
				if(tip){
					TreeItem treeItem = tv.getTree().getSelection()[0];
					TemplateVO temp = (TemplateVO) treeItem.getData();
					List<TemplateVO> list = (List<TemplateVO>) tv.getInput();
					List<TemplateVO>  newList = new ArrayList<TemplateVO> ();
					for (int i = 0; i < list.size(); i++) {
						if(!newList.contains(list.get(i)))
							newList.add(list.get(i));
					}
					newList.remove(temp);
					tv.setInput(newList);
					tv.cancelEditing();
					tv.refresh();
					tv.expandAll();
					
				}
			}
		});
	
		
//		setSelectedData(pm);
		return container;
	}
	

	class MySelectionChangeListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			nodeIdChanged();
		}
		
	}
	
	private void nodeIdChanged(){
		Tree tree = treeViewer.getTree();
		TreeItem treeItem = (TreeItem) tree.getSelection()[0];
		Object object = treeItem.getData();
		if(object instanceof FuncNodeVO){
			FuncNodeVO func = (FuncNodeVO) object;
			String parentId = func.getFuncCode();
			FuncNodeVO[] funVOs = (FuncNodeVO[]) getChildren(func);
			List<String> funcodes = new ArrayList<String>();
			for (int i = 0; i < funVOs.length; i++) {
				FuncNodeVO fun = funVOs[i];
				funcodes.add(fun.getFuncCode());
			}
			int add = 1;
			String newFuncode = parentId + "0" + String.valueOf(add);
			boolean flag = false;
			while(!flag){
				if(funcodes.contains(newFuncode)){
					add++;
					String addString = String.valueOf(add);
					if(addString.length() == 1)
						newFuncode = parentId + "0" +  String.valueOf(add);
					else
						newFuncode = parentId + String.valueOf(add);
					
				}
				else
					flag = true;
			}
			funcodeText.setText(newFuncode);
		}
	}

	private void processApply(){
		TreeItem treeItem = treeViewer.getTree().getSelection()[0];
		FuncNodeVO parentNode = (FuncNodeVO) treeItem.getData();
		if(parentNode.getNodeType() == FuncNodeVO.MODULE_FUNC_NODE){
			if(parentNode.getFuncCode() != null && parentNode.getFuncCode().length() == 2){
				MessageDialog.openError(null, "提示", "请选择父节点!");
				return;
			}
		}
		TreeItem[] items = tv.getTree().getItems();
		List<TemplateVO> list = new ArrayList<TemplateVO>();
		for (int i = 0; i < items.length; i++) {
			TemplateVO temp = (TemplateVO)items[i].getData();
			temp.setOperatorinfo(userMessage);
			if(!list.contains(temp))
				list.add(temp);
		}
		
		for (int i = 0; i < list.size(); i++) {
			TemplateVO tempa = (TemplateVO) list.get(i);
			String tempaNode = tempa.getNodekey();
			if(tempaNode == null)
				tempaNode = "";
			for (int j = 0; j < list.size(); j++) {
				TemplateVO tempb = (TemplateVO) list.get(j);
				String tempbNode = tempb.getNodekey();
				if(tempbNode == null)
					tempbNode = "";
				if(i != j && tempaNode.equals(tempbNode) && (tempa.getTemplatetype() == tempb.getTemplatetype())){
					MessageDialog.openError(null, "错误提示" , "关联模板的NodeKey重复，请重新设置!");
					return;
				}
			}
		}
		
		
		TemplateVO[] tempvos = (TemplateVO[])list.toArray(new TemplateVO[0]);
		String funnode = funcodeText.getText();
		if(funnode == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能编码");
			funcodeText.setFocus();
			return;
		}
		//检查是否是子节点
//		String parentCode = parentNode.getFuncCode();
		version = getVersion();
//		String dispCode = null;
		//检查是否是子节点
//		if(version != null && version.equals(ExtAttrConstants.VERSION5X)){
//			//为包含
//			if(!funnode.startsWith(parentCode)){
//				MessageDialog.openConfirm(this.getShell(), "提示", "子节点必须继承父节点编码!");
//				funcodeText.setFocus();
//				return;
//			}
//			if(funnode.length() - parentCode.length() != 2){
//				MessageDialog.openConfirm(this.getShell(), "提示", "子节点只能比父节点多2位!");
//				funcodeText.setFocus();
//				return;
//			}
//			dispCode = dispText.getText();
//			if(dispCode == null || dispCode.equals("")){
//				MessageDialog.openConfirm(this.getShell(), "提示", "请设置显示编码");
//				dispText.setFocus();
//				return;
//			}
//		}
		
		FuncNodeVO funnodeVO = null;
		//此PageMeta没有发布到功能节点
//		if(pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE) == null 
//				||  pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue() == null){
//			Object[] children = getChildren(parentNode);
//			for (int i = 0; i < children.length; i++) {
//				if(children[i] instanceof FuncNodeVO){
//					FuncNodeVO fun = (FuncNodeVO) children[i];
//					if(fun.getFuncCode().equals(funnode)){
//						MessageDialog.openError(this.getShell(), "提示", "已经存在此编码的节点!");
//						funcodeText.setFocus();
//						return;
//					}
//				}
//			}
//			funnodeVO = new FuncNodeVO();
//		}
//		else{
//			String originalFunNode = (String) pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue();
//			funvos = getFuncVOs();
//			if(version.equals(ExtAttrConstants.VERSION60)){
//				funvosOnlyfun = getFuncVOsOnlyFun();
//				for (int i = 0; i < funvosOnlyfun.length; i++) {
//					if(funvosOnlyfun[i].getFuncCode().equals(originalFunNode)){
//						funnodeVO = funvosOnlyfun[i];
//						break;
//					}
//				}
//			}
//			else{
//				for (int i = 0; i < funvos.length; i++) {
//					if(funvos[i].getFuncCode().equals(originalFunNode)){
//						funnodeVO = funvos[i];
//						break;
//					}
//				}
//			}
//			String pk_funnode = funnodeVO.getPk_func();
//			Object[] children = getChildren(parentNode);
//			for (int i = 0; i < children.length; i++) {
//				if(children[i] instanceof FuncNodeVO){
//					FuncNodeVO fun = (FuncNodeVO) children[i];
//					if(!fun.getPk_func().equals(pk_funnode) && fun.getFuncCode().equals(funnode)){
//						MessageDialog.openError(this.getShell(), "提示", "已经存在此编码的节点!");
//						funcodeText.setFocus();
//						return;
//					}
//				}
//			}
//		}
		
		String funname = funnameText.getText();
		if(funname == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能名称");
			funnameText.setFocus();
		}
		
		String funurl = funUrlText.getText();
		if(funurl == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能类名");
			funUrlText.setFocus();
			return;
		}
//		String orgtype = (String)orgtypeComp.getData(orgtypeComp.getText());
//		String functype = functionNodeComp.getText();
//		int functypen = 0;
//		if(functype.equals("业务类型"))
//			functypen = 0;
//		if(functype.equals("管理类型"))
//			functypen = 1;
//		if(functype.equals("系统类型"))
//			functypen = 2;
//		String funcdes = funDesText.getText();
		
//		funnodeVO.setFuncCode(funnode);
//		funnodeVO.setFuncName(funname);
//		funnodeVO.setFunurl(funurl);
//		funnodeVO.setOrgTypeCode(orgtype);
//		funnodeVO.setFunType(functypen);
//		funnodeVO.setFunDesc(funcdes);
		int parentnode = parentNode.getNodeType();
		version = getVersion();
//		if(version != null && version.equals(ExtAttrConstants.VERSION5X)){
//			funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//			funnodeVO.setPk_parent(parentNode.getPk_func());
//			funnodeVO.setSubSystemid(parentNode.getSubSystemid());
//			if(dispCode != null)
//				funnodeVO.setDisplayCode(dispCode);
//		}
//		else{
			if(parentnode == FuncNodeVO.MODULE_FUNC_NODE){
//				funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//				funnodeVO.setPk_parent(null);
			}
			else{
//				funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//				funnodeVO.setPk_parent(parentNode.getPk_func());
			}
//		}
		
//		pm.setExtendAttribute(ExtAttrConstants.FUNC_CODE, funnode);
		// 获取项目路径
		// 保存Widget到pagemeta.pm中
		String filePath = folderPath;
		String pmPath = filePath + "/pagemeta.pm";
		LFWPersTool.checkOutFile(pmPath);
		LFWConnector.savePagemetaToXml(filePath, "pagemeta.pm", projectPath, pm);
		//FuncNodeVO funnodeVO = null;
		NCConnector.updateSysTemplate(funnodeVO, tempvos);
		//应用后，使我的编辑测试流程，编辑单据类型可用
		editBillTypeBt.setEnabled(true);
		editworkflowBt.setEnabled(true);
		setSubmit(true);
	}
	protected void createButtonsForButtonBar(Composite parent) {
		Button apply = createButton(parent,50 ,"Apply", false);
		apply.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				processApply();
			}
		});
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		
	}
	
	
	
	protected void okPressed() {
		TreeItem treeItem = treeViewer.getTree().getSelection()[0];
		FuncNodeVO parentNode = (FuncNodeVO) treeItem.getData();
		if(parentNode.getNodeType() == FuncNodeVO.MODULE_FUNC_NODE){
			if(parentNode.getFuncCode() != null && parentNode.getFuncCode().length() == 2){
				MessageDialog.openError(null, "提示", "请选择父节点!");
				return;
			}
		}
		TreeItem[] items = tv.getTree().getItems();
		List<TemplateVO> list = new ArrayList<TemplateVO>();
		for (int i = 0; i < items.length; i++) {
			TemplateVO temp = (TemplateVO)items[i].getData();
			temp.setOperatorinfo(userMessage);
			if(!list.contains(temp))
				list.add(temp);
		}
		
		for (int i = 0; i < list.size(); i++) {
			TemplateVO tempa = (TemplateVO) list.get(i);
			String tempaNode = tempa.getNodekey();
			if(tempaNode == null)
				tempaNode = "";
			for (int j = 0; j < list.size(); j++) {
				TemplateVO tempb = (TemplateVO) list.get(j);
				String tempbNode = tempb.getNodekey();
				if(tempbNode == null)
					tempbNode = "";
				if(i != j && tempaNode.equals(tempbNode) && (tempa.getTemplatetype() == tempb.getTemplatetype())){
					MessageDialog.openError(null, "错误提示" , "关联模板的NodeKey重复，请重新设置!");
					return;
				}
			}
		}
		
		
//		TemplateVO[] tempvos = (TemplateVO[])list.toArray(new TemplateVO[0]);
		String funnode = funcodeText.getText();
		if(funnode == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能编码");
			funcodeText.setFocus();
			return;
		}

		version = getVersion();
//		String dispCode = null;
		//检查是否是子节点
//		if(version != null && version.equals(ExtAttrConstants.VERSION5X)){
//			String parentCode = parentNode.getFuncCode();
//			//为包含
//			if(!funnode.startsWith(parentCode)){
//				MessageDialog.openConfirm(this.getShell(), "提示", "子节点必须继承父节点编码!");
//				funcodeText.setFocus();
//				return;
//			}
//			if(funnode.length() - parentCode.length() != 2){
//				MessageDialog.openConfirm(this.getShell(), "提示", "子节点只能比父节点多2位!");
//				funcodeText.setFocus();
//				return;
//			}
//			dispCode = dispText.getText();
//			if(dispCode == null || dispCode.equals("")){
//				MessageDialog.openConfirm(this.getShell(), "提示", "请设置显示编码");
//				dispText.setFocus();
//				return;
//			}
//		}
//		FuncNodeVO funnodeVO = null;
		//此PageMeta没有发布到功能节点
//		if(pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE) == null 
//				||  pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue() == null){
//			Object[] children = getChildren(parentNode);
//			for (int i = 0; i < children.length; i++) {
//				if(children[i] instanceof FuncNodeVO){
//					FuncNodeVO fun = (FuncNodeVO) children[i];
//					if(fun.getFuncCode().equals(funnode)){
//						MessageDialog.openError(this.getShell(), "提示", "已经存在此编码的节点!");
//						funcodeText.setFocus();
//						return;
//					}
//				}
//			}
//			funnodeVO = new FuncNodeVO();
//		}
//		else{
//			String originalFunNode = (String) pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue();
//			funvos = getFuncVOs();
//			if(version.equals(ExtAttrConstants.VERSION60)){
//				funvosOnlyfun = getFuncVOsOnlyFun();
//				for (int i = 0; i < funvosOnlyfun.length; i++) {
//					if(funvosOnlyfun[i].getFuncCode().equals(originalFunNode)){
//						funnodeVO = funvosOnlyfun[i];
//						break;
//					}
//				}
//			}
//			else{
//				for (int i = 0; i < funvos.length; i++) {
//					if(funvos[i].getFuncCode().equals(originalFunNode)){
//						funnodeVO = funvos[i];
//						break;
//					}
//				}
//			}
//			if(funnodeVO != null){
//				String pk_funnode = funnodeVO.getPk_func();
//				Object[] children = getChildren(parentNode);
//				for (int i = 0; i < children.length; i++) {
//					if(children[i] instanceof FuncNodeVO){
//						FuncNodeVO fun = (FuncNodeVO) children[i];
//						if(!fun.getPk_func().equals(pk_funnode) && fun.getFuncCode().equals(funnode)){
//							MessageDialog.openError(this.getShell(), "提示", "已经存在此编码的节点!");
//							funcodeText.setFocus();
//							return;
//						}
//					}
//				}
//			}
//			else 
//				funnodeVO = new FuncNodeVO();
//		}
		
		String funname = funnameText.getText();
		if(funname == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能名称");
			funnameText.setFocus();
		}
		
		String funurl = funUrlText.getText();
		if(funurl == null){
			MessageDialog.openConfirm(this.getShell(), "提示", "请设置功能类名");
			funUrlText.setFocus();
			return;
		}
//		String orgtype = (String)orgtypeComp.getData(orgtypeComp.getText());
//		String functype = functionNodeComp.getText();
//		int functypen = 0;
//		if(functype.equals("业务类型"))
//			functypen = 0;
//		if(functype.equals("管理类型"))
//			functypen = 1;
//		if(functype.equals("系统类型"))
//			functypen = 2;
//		String funcdes = funDesText.getText();
		
//		funnodeVO.setFuncCode(funnode);
//		funnodeVO.setFuncName(funname);
//		funnodeVO.setFunurl(funurl);
//		funnodeVO.setOrgTypeCode(orgtype);
//		funnodeVO.setFunType(functypen);
//		funnodeVO.setFunDesc(funcdes);
//		int parentnode = parentNode.getNodeType();
		version = getVersion();
//		if(version != null && version.equals(ExtAttrConstants.VERSION5X)){
//			funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//			funnodeVO.setPk_parent(parentNode.getPk_func());
//			funnodeVO.setSubSystemid(parentNode.getSubSystemid());
//			funnodeVO.setDisplayCode(dispCode);
//		}
//		else{
//			if(parentnode == FuncNodeVO.MODULE_FUNC_NODE){
//				funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//				funnodeVO.setPk_parent(null);
//			}
//			else{
//				funnodeVO.setOwnmodule(parentNode.getOwnmodule());
//				funnodeVO.setPk_parent(parentNode.getPk_func());
//			}
//		}
		
//		pm.setExtendAttribute(ExtAttrConstants.FUNC_CODE, funnode);
		// 获取项目路径
		String filePath = folderPath;
		String pmPath = filePath + "/pagemeta.pm";
		LFWPersTool.checkOutFile(pmPath);
		// 保存Widget到pagemeta.pm中
		LFWConnector.savePagemetaToXml(filePath, "pagemeta.pm", projectPath, pm);
		//FuncNodeVO funnodeVO = null;
//		NCConnector.updateSysTemplate(funnodeVO, tempvos);
		super.okPressed();
		
	}
	
	public Object[] getChildren(Object parentElement) {
		List<FuncNodeVO> list = new ArrayList<FuncNodeVO>();
		FuncNodeVO vo = (FuncNodeVO) parentElement;
		String pk = vo.getPk_func();
		if(version.equals(ExtAttrConstants.VERSION60)){
			for (int i = 0; i < funvosExfun.length; i++) {
				if(pk.equals(funvosExfun[i].getPk_parent()))
					list.add(funvosExfun[i]);
			}
		}
		else{
			for (int i = 0; i < funvos.length; i++) {
				if(pk.equals(funvos[i].getPk_parent()))
					list.add(funvos[i]);
			}
		}
		return list.toArray(list.toArray(new FuncNodeVO[0]));
	}
	
//	private void setSelectedData(PageMeta pm){
////		if(pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE) == null ||  pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue() == null){
////			editBillTypeBt.setEnabled(false);
////			editworkflowBt.setEnabled(false);
////			return;
////		}
////		String funnode = pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE).getValue().toString();
//		String funname = "";
//		String funurl = "";
//		String fundes = "";
//		String pk_parent = "";
//		String functionnode = "";
//		String descode = "";
//		if(funnode != null){
//			funcodeText.setText(funnode);
//			//获取节点对应模板
//			if(temps == null)
//				temps = getALlTemplateVOByfunnode(funnode);
//			List<TemplateVO> list = Arrays.asList(temps);
//			tv.setInput(list);
//			if(version.equals(ExtAttrConstants.VERSION60)){
//				for (int i = 0; i < funvosOnlyfun.length; i++) {
//					if(funvosOnlyfun[i].getFuncCode().equals(funnode)){
//						//父节点pk
//						pk_parent = funvosOnlyfun[i].getPk_parent();
//						funname = funvosOnlyfun[i].getFuncName();
//						funurl = funvosOnlyfun[i].getFunurl();
//						fundes = funvosOnlyfun[i].getFunDesc();
//						if(orgtype == null || orgtype.equals(""))
//							orgtype = getOrgTypeByPK(funvosOnlyfun[i].getOrgTypeCode()).getTypename();
//						int funnodetype = funvosOnlyfun[i].getFunType();
//						if(funnodetype == FuncNodeVO.FUNC_TYPE_BUSINESS)
//							functionnode = "业务类型";
//						else if(funnodetype == FuncNodeVO.FUNC_TYPE_ADMIN)
//							functionnode = "管理类型";
//						else if(funnodetype == FuncNodeVO.FUNC_TYPE_SYSTEM)
//							functionnode = "系统类型";
//						else 
//							functionnode = "";
//						break;
//					}
//				}
//			}
//			else{
//				for (int i = 0; i < funvos.length; i++) {
//					if(funvos[i].getFuncCode().equals(funnode)){
//						//父节点pk
//						pk_parent = funvos[i].getPk_parent();
//						funname = funvos[i].getFuncName();
//						funurl = funvos[i].getFunurl();
//						descode = funvos[i].getDisplayCode();
//						fundes = funvos[i].getFunDesc();
//						if(orgtype == null || orgtype.equals(""))
//							orgtype = getOrgTypeByPK(funvos[i].getOrgTypeCode()).getTypename();
//						int funnodetype = funvos[i].getFunType();
//						if(funnodetype == FuncNodeVO.FUNC_TYPE_BUSINESS)
//							functionnode = "业务类型";
//						else if(funnodetype == FuncNodeVO.FUNC_TYPE_ADMIN)
//							functionnode = "管理类型";
//						else if(funnodetype == FuncNodeVO.FUNC_TYPE_SYSTEM)
//							functionnode = "系统类型";
//						else 
//							functionnode = "";
//						break;
//					}
//				}
//				if(descode != null)
//					dispText.setText(descode);
//			}
//			if(funname == null)
//				funname = "";
//			funnameText.setText(funname);
//			if(funurl == null)
//				funurl = "";
//			funUrlText.setText(funurl);
//			if(fundes == null)
//				fundes = "";
//			funDesText.setText(fundes);
//			orgtypeComp.setText(orgtype);
//			functionNodeComp.setText(functionnode);
//			TreeItem [] items = treeViewer.getTree().getItems();
//			boolean isSelected = false;
//			for (int k = 0; k < items.length; k++) {
//				if(isSelected)
//					return;
//				if(((FuncNodeVO)items[k].getData()).getPk_func().equals(pk_parent)){
//					treeViewer.getTree().setSelection(items[k]);
//					isSelected = true;
//					break;
//				}
//				else{
//					treeViewer.expandToLevel(items[k].getData(), 1);
//					TreeItem[] childitems = items[k].getItems();
//					for (int i = 0; i < childitems.length; i++) {
//						if(((FuncNodeVO)childitems[i].getData()).getPk_func().equals(pk_parent)){
//							treeViewer.getTree().setSelection(childitems[i]);
//							isSelected = true;
//							break;
//						}else{
//							TreeItem selected = getSeletedTreeItem(childitems[i], pk_parent);
//							if(selected != null){
//								treeViewer.getTree().setSelection(selected);
//								isSelected = true;
//								break;
//							}
//						}
//					}
//					//treeViewer.collapseToLevel(items[k].getData(), -1);
//				}
//			}
//			
//		}
//	}
//	private TreeItem getSeletedTreeItem(TreeItem treeItem, String pk_parent){
//		treeViewer.expandToLevel(treeItem.getData(), 1);
//		TreeItem[] child = treeItem.getItems();
//		for (int j = 0; j < child.length; j++) {
//			if(((FuncNodeVO)child[j].getData()).getPk_func().equals(pk_parent)){
//				return child[j];
//			}
//			else {
//				return getSeletedTreeItem(child[j], pk_parent);
//			}
//		}
//		TreeItem parentTreeItem = treeItem.getParentItem();
//		while(parentTreeItem != null && parentTreeItem.getData() != null){
//			treeViewer.collapseToLevel(parentTreeItem.getData(), -1);
//			parentTreeItem = parentTreeItem.getParentItem();
//		}
//		return null;
//	}
	
	class LableCellModifier implements ICellModifier{
		private TreeViewer tv;

		public LableCellModifier(TreeViewer tv){
			this.tv = tv;
		}
		public boolean canModify(Object element, String property) {
			if(property.equals("nodekey"))
				return true;
			else return false;
		}

		public Object getValue(Object element, String property) {
			TemplateVO item = (TemplateVO)element;
			if(property.equals("nodekey"))
				return item.getNodekey() == null?"" : item.getNodekey();
			return null;
		}

		public void modify(Object element, String property, Object value) {
			TreeItem treeItem = (TreeItem)element;
			TemplateVO item = (TemplateVO)treeItem.getData();
			if(property.equals("nodekey"))
				item.setNodekey((String)value);
			tv.update(item, null);
		}

		
		
	}
	
	class LabelContentProvider extends LabelProvider{

		public Image getImage(Object element) {
			return super.getImage(element);
		}

		public String getText(Object element) {
			FuncNodeVO vo = (FuncNodeVO) element;
			return "[" + vo.getFuncCode() + "]" + vo.getFuncName();
		}
		
	}
	
	class FuncContentProvider implements ITreeContentProvider{

		public FuncContentProvider() {
		}

		public Object[] getChildren(Object parentElement) {
			List<FuncNodeVO> list = new ArrayList<FuncNodeVO>();
			FuncNodeVO vo = (FuncNodeVO) parentElement;
			String pk = vo.getPk_func();
			if(version.equals(ExtAttrConstants.VERSION60)){
				for (int i = 0; i < funvosExfun.length; i++) {
					if(pk.equals(funvosExfun[i].getPk_parent()))
						list.add(funvosExfun[i]);
				}
			}
			else{
				for (int i = 0; i < funvos.length; i++) {
					if(pk.equals(funvos[i].getPk_parent()))
						list.add(funvos[i]);
				}
			}
			return list.toArray(list.toArray(new FuncNodeVO[0]));
		}

		public Object getParent(Object element) {
			FuncNodeVO vo = (FuncNodeVO) element;
			String pk_parent = vo.getPk_parent();
			if(pk_parent == null)
				return null;
			for (int i = 0; i < funvosExfun.length; i++) {
				if(pk_parent.equals(funvosExfun[i].getPk_func()))
					return funvosExfun[i];
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			if(version.equals(ExtAttrConstants.VERSION60)){
				FuncNodeVO vo = (FuncNodeVO) element;
				String pk = vo.getPk_func();
				for (int i = 0; i < funvosExfun.length; i++) {
					if(pk.equals(funvosExfun[i].getPk_parent())){
						return true;
					}
				}
				return false;
			}
			else{
				FuncNodeVO vo = (FuncNodeVO) element;
				String pk = vo.getPk_func();
				for (int i = 0; i < funvos.length; i++) {
					if(pk.equals(funvos[i].getPk_parent())){
						return true;
					}
				}
				return false;
			}
		}

		public Object[] getElements(Object inputElement) {
			if(inputElement != null){
				return (Object[]) inputElement;
			}
			return new Object[0];
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	public TemplateVO[] getALlTemplateVOByfunnode(String funnode){
		return NCConnector.getAllTemplateByFuncnode(funnode).toArray(new TemplateVO[0]);
	}
	
	private  TypeNodeVO[] getAllMainOrgType(){
		return NCConnector.getAllMainOrgType();
	}
//	private  TypeNodeVO getOrgTypeByPK(String pk){
//		return NCConnector.getOrgTypeByPK(pk);
//	}
	
	public FuncNodeVO[] getMainFunvos(){
		if(version != null && version.equals(ExtAttrConstants.VERSION60)){
			if(funvosExfun != null){
				List<FuncNodeVO> list = new ArrayList<FuncNodeVO>();
				for (int i = 0; i < funvosExfun.length; i++) {
					FuncNodeVO vo = funvosExfun[i];
					if(vo.getPk_parent() == null)
						list.add(vo);
				}
				return list.toArray(new FuncNodeVO[0]);
			}
			return null;
		}
		else{
			if(funvos != null){
				List<FuncNodeVO> list = new ArrayList<FuncNodeVO>();
				for (int i = 0; i < funvos.length; i++) {
					FuncNodeVO vo = funvos[i];
					if(vo.getPk_parent() == null)
						list.add(vo);
				}
				return list.toArray(new FuncNodeVO[0]);
			}
			return null;
		}
		
	}
	
	
	private String getVersion(){
		if(version == null)
			version = NCConnector.getVersion();
		return version;
	}
	
	/**
	 * 节点类型 - 非可执行功能节点
	 */
	public FuncNodeVO[] getFuncVOsExceptFun(){
		List<FuncNodeVO> funList = new ArrayList<FuncNodeVO>();
		for (int i = 0; i < funvos.length; i++) {
			FuncNodeVO fun = funvos[i];
			if(fun.getFunType() == null || (fun.getFunType() != null && fun.getFunType() != FuncNodeVO.EXECUTABLE_FUNC_NODE))
				funList.add(fun);
		}
		return (FuncNodeVO[])funList.toArray(new FuncNodeVO[funList.size()]);
	}
	
	/**
	 * 节点类型 - 可执行功能节点
	 */
	public FuncNodeVO[] getFuncVOsOnlyFun(){
		List<FuncNodeVO> funList = new ArrayList<FuncNodeVO>();
		for (int i = 0; i < funvos.length; i++) {
			FuncNodeVO fun = funvos[i];
			if(fun.getFunType()  != null && fun.getFunType() == FuncNodeVO.EXECUTABLE_FUNC_NODE)
				funList.add(fun);
		}
		return (FuncNodeVO[])funList.toArray(new FuncNodeVO[funList.size()]);
	}
	
	public FuncNodeVO[] getFuncVOs() {
		return NCConnector.getFuncRegisterVOs().toArray(new FuncNodeVO[0]);
	}

	public boolean isSubmit() {
		return isSubmit;
	}

	public void setSubmit(boolean isSubmit) {
		this.isSubmit = isSubmit;
	}
	
}

