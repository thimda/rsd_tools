package nc.uap.lfw.wizard;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.mlr.MLResSubstitution;
import nc.uap.lfw.plugin.common.CommonPlugin;
import nc.uap.lfw.tool.Helper;
import nc.uap.lfw.tool.ImageFactory;
import nc.uap.lfw.tool.ProjConstants;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.javaeditor.JavaSourceViewer;
import org.eclipse.jdt.internal.ui.refactoring.nls.MultiStateCellEditor;
import org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.*;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * XML外部化多语资源
 * 
 * @author dingrf
 *
 */
public class ExternalizeMLRWizardPage2 extends UserInputWizardPage{
	
	private class MyCellModifier implements ICellModifier{

		public boolean canModify(Object element, String property){
//			return (element instanceof MLResSubstitution)  && (((MLResSubstitution)element).getState()!=3)  && (ExternalizeMLRWizardPage2.colNames[2].equals(property) || ExternalizeMLRWizardPage2.colNames[4].equals(property) || ExternalizeMLRWizardPage2.colNames[5].equals(property));
			return (element instanceof MLResSubstitution)  
				&& (((MLResSubstitution)element).getState()!=3 && ((MLResSubstitution)element).getState()!=4 && ((MLResSubstitution)element).getState()!=5) 
				&& (ExternalizeMLRWizardPage2.colNames[2].equals(property));
		}

		public Object getValue(Object element, String property){
			if (element instanceof MLResSubstitution){
				MLResSubstitution ele = (MLResSubstitution)element;
				if (ExternalizeMLRWizardPage2.colNames[0].equals(property))
					return new Integer(ele.getState());
//				if (ExternalizeMLRWizardPage2.colNames[1].equals(property))
//					return ele.getOldKey().equals("")?"新增":"修改";
				if (ExternalizeMLRWizardPage2.colNames[2].equals(property))
					return Helper.unwindEscapeChars(ele.getValue());
				if (ExternalizeMLRWizardPage2.colNames[3].equals(property))
					if (ele.getState() == 0)
						return Helper.unwindEscapeChars(ele.getKey());
					else
						return "";
				if (ExternalizeMLRWizardPage2.colNames[4].equals(property))
					return ele.getFilePath();
//				if (ExternalizeMLRWizardPage2.colNames[4].equals(property))
//					return Helper.unwindEscapeChars(ele.getEnglishValue());
//				if (ExternalizeMLRWizardPage2.colNames[5].equals(property))
//					return Helper.unwindEscapeChars(ele.getTwValue());
			}
			return "";
		}

		public void modify(Object element, String property, Object value){
			if (element instanceof TableItem){
				Object o = ((TableItem)element).getData();
				if (o instanceof MLResSubstitution)				{
					MLResSubstitution sub = (MLResSubstitution)o;
//					if (ExternalizeMLRWizardPage2.colNames[0].equals(property)){
//						sub.setState(((Integer)value).intValue());
//						mlrRefactoring.updateKey();
//						tableViewer.refresh();
//					} 
					if (ExternalizeMLRWizardPage2.colNames[2].equals(property)){
						String v = Helper.windEscapeChars((String)value);
						String old = sub.getValue();
						sub.setValue(v);
						if (!old.equals(v)){
							MLResSubstitution substitutions[] = mlrRefactoring.getSubstitutions(); 
							for (int i = 0 ; i< substitutions.length ; i++){
								if (substitutions[i].getRealKey().equals(sub.getRealKey()))
									substitutions[i].setValue(v);
							}
//							mlrRefactoring.updateSubstitution(sub);
//							mlrRefactoring.updateKey();
							tableViewer.refresh();
						}
					} 
					else if (ExternalizeMLRWizardPage2.colNames[3].equals(property)){
						sub.setKey((String)value);
						tableViewer.refresh(o);
					} 
//					else if (ExternalizeMLRWizardPage2.colNames[4].equals(property)){
//						String v = Helper.windEscapeChars((String)value);
//						sub.setEnglishValue(v);
//						tableViewer.refresh();
//					} 
//					else if (ExternalizeMLRWizardPage2.colNames[5].equals(property)){
//						String v = Helper.windEscapeChars((String)value);
//						sub.setTwValue(v);
//						tableViewer.refresh();
//					}
				}
			}
		}
	}

	private class MyLabelProvider extends LabelProvider implements ITableLabelProvider{

		public Image getColumnImage(Object element, int columnIndex){
			if (element instanceof MLResSubstitution){
				MLResSubstitution sub = (MLResSubstitution)element;
				if (columnIndex == 0)
					return ImageFactory.getImageBySubstitutionState(sub);
			}
			return null;
		}

		public String getColumnText(Object element, int columnIndex){
			String columnText = "";
			if (element instanceof MLResSubstitution){
				MLResSubstitution substitution = (MLResSubstitution)element;
//				if (ExternalizeMLRWizardPage2.colNames[1].equals(property))
//				return ele.getOldKey().equals("")?"新增":"修改";
				
				if (columnIndex == 1)
					columnText = substitution.getState()==0?"新增":substitution.getState()==1?"修改":  
						substitution.getState()==2?"删除":substitution.getState()==3?"公共":substitution.getState()==4?"错误":
							substitution.getState()==5?"警告":"修复";
				if (columnIndex == 2)
					columnText = Helper.unwindEscapeChars(substitution.getValue());
				else if (columnIndex == 3)
					columnText = substitution.getRealKey();
				else if (columnIndex == 4)
					columnText = substitution.getPageNode().getFile().getFullPath().toString();
//				else if (columnIndex == 4)
//					columnText = Helper.unwindEscapeChars(substitution.getEnglishValue());
//				else if (columnIndex == 5)
//					columnText = Helper.unwindEscapeChars(substitution.getTwValue());
			}
			return columnText;
		}
	}
	
	/**
	 *树的ContentProvider 
	 */
	class TreeContentProvider implements ITreeContentProvider{

		public TreeContentProvider() {
		}

		public Object[] getChildren(Object parentElement) {
			PageNode p = (PageNode) parentElement;
			List<PageNode> list = p.getChildrens();
			
			return list.toArray(list.toArray(new PageNode[0]));
		}

		public Object getParent(Object element) {
			PageNode p = (PageNode) element;
			if(p.getParent() != null)
				return p.getParent();
			return null;
		}

		public boolean hasChildren(Object element) {
			PageNode p = (PageNode) element;
			if (p.getChildrens() == null)
				return false;
			else
				return true;
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

	/**
	 *树的LabelProvider 
	 */
	class LabelContentProvider extends LabelProvider{

		public Image getImage(Object element) {
			return super.getImage(element);
		}

		public String getText(Object element) {
			PageNode p = (PageNode) element;
			return p.getName();
//			return "[" + vo.getFuncCode() + "]" + vo.getFuncName();
		}
		
	}

	/**
	 *树的changed事件 
	 * 
	 */
	class MySelectionChangeListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			nodeIdChanged();
		}
		
	}
	
	private void nodeIdChanged(){
		Tree tree = treeViewer.getTree();
		TreeItem treeItem = (TreeItem) tree.getSelection()[0];
		Object object = treeItem.getData();
		if(object instanceof PageNode){
			if (((PageNode)object).isFile()){
				mlrRefactoring.setCurrentPageNode((PageNode)object);
				prefixText.setText(mlrRefactoring.getPrefix());
				tableViewer.refresh();
				refreshSoruseView();
			} 
		}
	}
	

//	public static final String colNames[] = {"","状态", "多语资源", "资源ID", "英语", "繁体"};
	public static final String colNames[] = {"","状态", "多语资源", "资源ID", "文件"};
	/**左边树节点*/
	private TreeViewer treeViewer;
	/**page列表*/
	List<PageNode> pageNodes =  null;

	private TableViewer tableViewer;
	private MLRRefactoring mlrRefactoring;
	private SourceViewer fSourceViewer;
	private Text prefixText;
	private Button selCommBtn;

	public ExternalizeMLRWizardPage2(MLRRefactoring mlrRefactoring){
		super("LFW外部化多语资源");
		this.mlrRefactoring = mlrRefactoring;
		tableViewer = null;
		fSourceViewer = null;
		prefixText = null;
		this.mlrRefactoring.setPage2(this);
	}

	public void createControl(Composite parent){
		Composite container = new Composite(parent, 0);
//		container.setLayout(new FillLayout(512));
		container.setLayout(new GridLayout(2,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
//		container.setBackground(new org.eclipse.swt.graphics.Color(null,125,25,41));
//		Composite comp = new Composite(container, SWT.NONE);
//		comp.setBackground(new org.eclipse.swt.graphics.Color(null,125,25,41));
//		comp.setLayout(new FillLayout());
		//生成左边树
		createTreeViewer(container);
		
		SashForm form = new SashForm(container, 512);
		form.setLayoutData(new GridData(GridData.FILL_BOTH));
//		form.setBackground(new org.eclipse.swt.graphics.Color(null,125,125,125));
//		form.setLayout(new FillLayout());
		createTableViewer(form);
		createSourceViewer(form);
		setControl(container);
	}

	/**
	 *左边树 
	 * @param comp
	 */
	private void createTreeViewer(Composite container){
		treeViewer = new TreeViewer(container,SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		//tree.setLinesVisible(true);
		//tree.setHeaderVisible(true);
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new LabelContentProvider());
		treeViewer.setInput(loadNodes());
		treeViewer.addSelectionChangedListener(new MySelectionChangeListener(){
			
		});
	}
	
	private void createTableViewer(Composite parent){
		Composite comp = new Composite(parent, 0);
		GridLayout gl = new GridLayout(2, false);
		comp.setLayout(gl);
		Label prefixLbl = new Label(comp, SWT.NONE);
		prefixLbl.setText("key值前缀：");
		prefixText = new Text(comp, 2052);
		prefixText.setLayoutData(new GridData(768));
		if (mlrRefactoring.getPrefix() != null)
			prefixText.setText(mlrRefactoring.getPrefix());
		prefixText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e){
				donePrefixTextModify(e);
			}
		});
		prefixText.setEditable(false);
		
		Composite btnComp = new Composite(comp, SWT.NONE);
		GridData compGD = new GridData(768);
		compGD.horizontalSpan = 2;
		compGD.verticalSpan = 1;
		btnComp.setLayoutData(compGD);
		btnComp.setLayout(new GridLayout(3, false));
		selCommBtn = new Button(btnComp, 8);
		selCommBtn.setText("从公共资源选择");
		selCommBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				doneSelCommBtnClicked();
			}
		});
		
		
//		tableViewer = new TableViewer(comp, 0x18b02);
		tableViewer = new TableViewer(comp, SWT.SINGLE|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		GridData gd = new GridData(1808);
		gd.horizontalSpan = 2;
		table.setLayoutData(gd);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		ColumnLayoutData columnLayoutData[] = new ColumnLayoutData[colNames.length];
		columnLayoutData[0] = new ColumnPixelData(18, false, true);
		columnLayoutData[1] = new ColumnWeightData(20, true);
		columnLayoutData[2] = new ColumnWeightData(40, true);
		columnLayoutData[3] = new ColumnWeightData(40, true);
		columnLayoutData[4] = new ColumnWeightData(80, true);
//		columnLayoutData[5] = new ColumnWeightData(40, true);
		for (int i = 0; i < colNames.length; i++){
			TableColumn tc = new TableColumn(table, 0, i);
			tc.setText(colNames[i]);
			layout.addColumnData(columnLayoutData[i]);
			tc.setResizable(columnLayoutData[i].resizable);
		}

		CellEditor editors[] = new CellEditor[colNames.length];
		editors[0] = new MultiStateCellEditor(table, 3, 1);
		editors[1] = new TextCellEditor(table);
		editors[2] = new TextCellEditor(table);
		editors[3] = new TextCellEditor(table);
		editors[4] = new TextCellEditor(table);
//		editors[5] = new TextCellEditor(table);
		tableViewer.setCellEditors(editors);
		tableViewer.setColumnProperties(colNames);
		tableViewer.setCellModifier(new MyCellModifier());
		tableViewer.setLabelProvider(new MyLabelProvider());
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement){
				return mlrRefactoring.getSubstitutions();
			}
			public void dispose(){}
			public void inputChanged(Viewer viewer1, Object obj, Object obj1){}
		});
		//TODO
		tableViewer.setInput(new Object());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event){
				ExternalizeMLRWizardPage2.this.selectionChanged(event);
			}
		});
		tableViewer.addDoubleClickListener(new IDoubleClickListener(){

			@Override
			public void doubleClick(DoubleClickEvent event) {
				//修复警告
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				MLResSubstitution first = (MLResSubstitution)selection.getFirstElement();
				if (first.getState() == 5){
					if (!MessageDialog.openConfirm(new Shell(), "确认", "确定要修复警告吗？"))
						return;
					String s = first.getSelectKey();
					String cx = mlrRefactoring.getContents().get(first.getFilePath());
					String k ="";
					if (!first.getKey().equals(""))
						k = first.getKeyWithPrefix();
					else
					k = mlrRefactoring.getKey(cx, first.getPageNode());
					String s2 = s.replaceFirst(" ", " " +  ProjConstants.I18NNAME + "=\"" + k + "\" " + ProjConstants.LANG_DIR + "=\"" + first.getRealLangModule() +"\" ");
					cx = cx.replace(s, s2);
					mlrRefactoring.getContents().put(first.getFilePath(), cx);
					first.setState(6);
					if (first.getKey().equals(""))
						first.setKey(k.replace(first.getPrefix() , ""));
//					first.setValue("");
//					first.setLangModule(mlrRefactoring.getResModuleName());
					tableViewer.refresh();
					mlrRefactoring.updateSubPostion(null);
					refreshSoruseView();
				}
				else if (first.getState() == 6){
					if (!MessageDialog.openConfirm(new Shell(), "确认", "确定要取消修复吗？"))
						return;
					String s1 = first.getOldKey();
					String s2 = first.getSelectKey();
					first.setSelectKey(s1);
					String cx = mlrRefactoring.getContents().get(first.getFilePath());
//					String k = mlrRefactoring.getKey(cx, first.getPageNode());
//					String s2 = s.replaceFirst(" ", " " +  ProjConstants.I18NNAME + "=\"" + k + "\" " + ProjConstants.LANG_DIR + "=\"" + mlrRefactoring.getResModuleName() +"\" ");
					cx = cx.replace(s2, s1);
					mlrRefactoring.getContents().put(first.getFilePath(), cx);
					first.setState(5);
//					first.setKey(k.replace(first.getPrefix() , ""));
//					first.setLangModule(mlrRefactoring.getResModuleName());
					tableViewer.refresh();
					mlrRefactoring.updateSubPostion(null);
					refreshSoruseView();
				}
			} 
			
		});
//		tableViewer.addFilter(new ViewerFilter(){
//
//			@Override
//			public boolean select(Viewer viewer, Object parentElement,
//					Object element) {
//				MLResSubstitution s = (MLResSubstitution)element;
//				if (mlrRefactoring.getCurrentPageNode() ==null)
//					return false;
//				else
//					return s.getFilePath().equals(mlrRefactoring.getCurrentPageNode().getPath());
//			}
//			
//		});
	}

	protected void donePrefixTextModify(ModifyEvent e){
		mlrRefactoring.setPrefix(prefixText.getText().trim());
//		mlrRefactoring.updateKey();
		tableViewer.refresh();
		refreshSoruseView();
		
	}

	private void selectionChanged(SelectionChangedEvent event)
	{
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		updateSourceView(selection);
	}

	private void createSourceViewer(Composite parent){
		Composite c = new Composite(parent, 0);
		c.setLayoutData(new GridData(1808));
		GridLayout gl = new GridLayout();
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		c.setLayout(gl);
		Label l = new Label(c, 0);
		l.setText("文件源码：");
		l.setLayoutData(new GridData());
		JavaTextTools tools = JavaPlugin.getDefault().getJavaTextTools();
		int styles = 0x10b02;
		org.eclipse.jface.preference.IPreferenceStore store = JavaPlugin.getDefault().getCombinedPreferenceStore();
		fSourceViewer = new JavaSourceViewer(c, null, null, false, styles, store);
		JavaSourceViewerConfiguration config = new JavaSourceViewerConfiguration(tools.getColorManager(), store, null, null);
		fSourceViewer.configure(config);
		fSourceViewer.getControl().setFont(JFaceResources.getFont("org.eclipse.jdt.ui.editors.textfont"));
		try{
//			String contents = mlrRefactoring.getCu().getBuffer().getContents();
			if (mlrRefactoring.getCurrentPageNode() != null){
				String contents = mlrRefactoring.getContents().get(mlrRefactoring.getCurrentPageNode().getPath());
				org.eclipse.jface.text.IDocument document = new Document(contents);
				tools.setupJavaDocumentPartitioner(document);
				fSourceViewer.setDocument(document);
			}
			fSourceViewer.setEditable(false);
			GridData gd = new GridData(1808);
			gd.heightHint = convertHeightInCharsToPixels(10);
			gd.widthHint = convertWidthInCharsToPixels(40);
			fSourceViewer.getControl().setLayoutData(gd);
		}
		catch (Exception e)
		{
			MessageDialog.openError(getShell(), "Error", (new StringBuilder()).append(e.getClass()).append(": ").append(e.getMessage()).toString());
		}
	}


	/**
	 * 加载树节点
	 * @return
	 */
	public  PageNode[] loadNodes(){
		pageNodes = new ArrayList<PageNode>();
		List<PageNode> topNodes = new ArrayList<PageNode>();
		String[] names = LfwCommonTool.getBCPNames(mlrRefactoring.getProject());
		try {
			if (names == null){
				IFolder fileFolder = mlrRefactoring.getProject().getFolder("/web/html/nodes");
				for (int i = 0 ; i<fileFolder.members().length ; i++){
					IResource resorce = fileFolder.members()[i];
					if (resorce instanceof IFolder){
						IFolder f =(IFolder)resorce;
						//加载目录
						PageNode pageNode = new PageNode(f,mlrRefactoring.getProject().getName().toLowerCase() + "_nodes");
						topNodes.add(pageNode);
						pageNodes.add(pageNode);
						scanDir(pageNodes, f,pageNode);
					}
				}
			}
			else{
				for (int count = 0 ; count < names.length ; count ++){
					IFolder fileFolder = mlrRefactoring.getProject().getFolder("/"+names[count]+"/web/html/nodes");
					for (int i = 0 ; i<fileFolder.members().length ; i++){
						IResource resorce = fileFolder.members()[i];
						if (resorce instanceof IFolder){
							IFolder f =(IFolder)resorce;
							PageNode pageNode = new PageNode(f,names[count].toLowerCase()+"_nodes");
							pageNode.setBcpName(names[count].toLowerCase());
							topNodes.add(pageNode);
							pageNodes.add(pageNode);
							scanDir(pageNodes, f,pageNode);
						}
					}
				}
			}
		} catch (CoreException e) {
			CommonPlugin.getPlugin().logError(e.getMessage(), e);
		} 
		
		mlrRefactoring.createRawSubstitution(pageNodes);
		return topNodes.toArray(new PageNode[0]); 
	}

	/**
	 * 扫描文件夹
	 * @param pageNodes
	 * @param dir
	 */
	private void scanDir(List<PageNode> pageNodes, IFolder dir,PageNode parentNode){
		if(judgeIsPMFolder(dir)){
			//加载目录下的 um,pm,wd文件
			scanFiles(dir,pageNodes,parentNode);
		}
		else{
			try {
				for (int i = 0 ; i<dir.members().length ; i++){
					IResource resorce = dir.members()[i];
					if (resorce instanceof IFolder){
						IFolder f = (IFolder)resorce;
						//加载目录
						PageNode pageNode = new PageNode(f,parentNode.getResModuleName());
						pageNode.setParent(parentNode);
						pageNode.setRoot(parentNode.getRoot());
						pageNode.setBcpName(parentNode.getBcpName());
						parentNode.getChildrens().add(pageNode);
						pageNodes.add(pageNode);
						scanDir(pageNodes, (IFolder)resorce,pageNode);
					}
				}
			} catch (CoreException e) {
				CommonPlugin.getPlugin().logError(e.getMessage(), e);
			} 
		}
	}

	/**
	 * 加载目录下的 um,pm,wd文件
	 * 
	 * @param dir
	 * @param pageNodes
	 * @param parent
	 */
	private void scanFiles(IFolder dir,List<PageNode> pageNodes,PageNode parent) {
		try {
			for (int i = 0 ; i<dir.members().length ; i++){
				IResource resorce = dir.members()[i];
				if (resorce instanceof IFolder){
					IFolder folder = (IFolder)resorce;
					PageNode pageNode = new PageNode(folder,parent.getResModuleName());
					pageNode.setParent(parent);
					pageNode.setRoot(parent.getRoot());
					pageNode.setBcpName(parent.getBcpName());
					parent.getChildrens().add(pageNode);
					pageNodes.add(pageNode);
					scanFiles(folder,pageNodes,pageNode);
				}
				else{
					IFile file = (IFile)resorce;
					String fileName = file.getName();
					if (fileName.endsWith(".um") || fileName.endsWith(".pm") || fileName.endsWith(".wd")){
						PageNode pageNode = new PageNode(file,parent.getResModuleName());
						pageNode.setParent(parent);
						pageNode.setRoot(parent.getRoot());
						pageNode.setBcpName(parent.getBcpName());
						parent.getChildrens().add(pageNode);
						pageNodes.add(pageNode);
					}
				}
			}
		} catch (CoreException e) {
			CommonPlugin.getPlugin().logError(e.getMessage(), e);
		}
		
		
	}

	/**
	 * 判断是否含pm文件
	 * @param fold
	 * @return
	 */
	private boolean judgeIsPMFolder(IFolder dir) {
		try {
			for (int i = 0 ; i<dir.members().length ; i++){
				IResource resorce = dir.members()[i];
				if (resorce instanceof IFile){
					if (((IFile)resorce).getName().equals("pagemeta.pm")){
						return true;
					}
				}
			}
		} catch (CoreException e) {
			CommonPlugin.getPlugin().logError(e.getMessage(), e);
			return false;
		}
		return false;
	}
	
	public void refreshSoruseView(){
		if (mlrRefactoring.getCurrentPageNode() != null){
			org.eclipse.jface.text.IDocument document = new Document(mlrRefactoring.getContents().get(mlrRefactoring.getCurrentPageNode().getPath()));
			JavaTextTools tools = JavaPlugin.getDefault().getJavaTextTools();
			tools.setupJavaDocumentPartitioner(document);
			fSourceViewer.setDocument(document);
		}
	}
	
	/**
	 * 更新源码区选中范围
	 * 
	 * @param selection
	 */
	private void updateSourceView(IStructuredSelection selection){
		MLResSubstitution first = (MLResSubstitution)selection.getFirstElement();
		if (first != null){
			//TODO
			if ((mlrRefactoring.getCurrentPageNode() == null) || (mlrRefactoring.getCurrentPageNode() != null && mlrRefactoring.getCurrentPageNode() != first.getPageNode())){
				mlrRefactoring.setCurrentPageNode(first.getPageNode());
				prefixText.setText(mlrRefactoring.getPrefix());
				refreshSoruseView();
			}
			
			Region region = first.getElement().getPosition();
			fSourceViewer.setSelectedRange(region.getOffset(), region.getLength());
			fSourceViewer.revealRange(region.getOffset(), region.getLength());
			
			//公共资源是否可用
			if (first.getState() ==0 || first.getState() ==6 )
				selCommBtn.setEnabled(true);
			else
				selCommBtn.setEnabled(false);
		}
	}

	public void updateTableViewer(){
		tableViewer.refresh();
	}

	/**
	 * 从公共资源选择
	 */
	protected void doneSelCommBtnClicked(){
		
		CommSelDlg dlg = new CommSelDlg(getShell(), mlrRefactoring);
		int result = dlg.open();
		if (result == 0){
			MLResSubstitution commRes =  dlg.getSelectMLRes();
			if (commRes == null)
				return;
			IStructuredSelection selection = (IStructuredSelection)tableViewer.getSelection();
			Object selectedElement = selection.getFirstElement();
			if (selectedElement == null)
				return;
			MLResSubstitution m =  (MLResSubstitution)selectedElement;
			updateMLResSubstitution(m,commRes);
			updateTableViewer();
			refreshSoruseView();
		}
		
	}
	
	private void updateMLResSubstitution(MLResSubstitution substitution,MLResSubstitution commRes){
		String oldLangModule = substitution.getLangModule();
		String oldKey = substitution.getRealKey();
		substitution.setCommKey(commRes.getExtKey());
//		substitution.setPrefix("");
		substitution.setValue(commRes.getValue());
		substitution.setEnglishValue(commRes.getEnglishValue());
		substitution.setTwValue(commRes.getTwValue());
		substitution.setState(3);
		substitution.setLangModule(ProjConstants.COMM);
		
		String contents = mlrRefactoring.getContents().get(mlrRefactoring.getCurrentPageNode().getPath());
		int mlrPos =  contents.indexOf(ProjConstants.I18NNAME + "=\"" + oldKey + "\"");
		for (int i = 0 ; i< mlrRefactoring.getSubstitutions().length ; i ++){
			if (contents.indexOf(ProjConstants.I18NNAME) != mlrPos){
				contents = contents.replaceFirst(ProjConstants.I18NNAME, "%i18n%");
				contents = contents.replaceFirst(ProjConstants.LANG_DIR, "%dir%");
				mlrPos =  contents.indexOf(ProjConstants.I18NNAME + "=\"" + oldKey + "\"");
			}
			else{
				contents = contents.replaceFirst(ProjConstants.I18NNAME+ "=\""+ oldKey + "\"", ProjConstants.I18NNAME+ "=\""+ substitution.getRealKey() + "\"");
				contents = contents.replaceFirst(ProjConstants.LANG_DIR+ "=\""+ oldLangModule + "\"", ProjConstants.LANG_DIR+ "=\""+ substitution.getLangModule() + "\"");
				break;
			}
		}
		contents = contents.replaceAll("%dir%", ProjConstants.LANG_DIR);
		contents = contents.replaceAll("%i18n%", ProjConstants.I18NNAME);
		mlrRefactoring.getContents().put(mlrRefactoring.getCurrentPageNode().getPath(), contents);
		mlrRefactoring.updateSubPostion(null);
		mlrRefactoring.setSourceFileChanged(true);
//		String contents = mlrRefactoring.getContents().replace(ProjConstants.LANG_DIR+ "=\"" + oldLangModule + "\"", ProjConstants.LANG_DIR+ "=\"" + "common"+ "\"");
//		mlrRefactoring.setContents(contents);
//		String newKey = substitution.getKeyWithPrefix();
//		substitution.getElement().setFPosition(contents.indexOf(newKey), newKey.length());
		
	}

	public List<PageNode> getPageNodes() {
		return pageNodes;
	}

//	public void setPageNodes(List<PageNode> pageNodes) {
//		this.pageNodes = pageNodes;
//	}
	
	
	
}
