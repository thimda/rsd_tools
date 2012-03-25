/**
 * 
 */
package nc.uap.lfw.editor.propertiesview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.EventUtil;
import nc.uap.lfw.core.event.conf.DatasetRule;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.WidgetRule;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.editor.LFWBrowserEditor;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.editor.TableViewContentProvider;
import nc.uap.lfw.perspective.listener.EventEditorHandler;
import nc.uap.lfw.perspective.listener.EventSubmitRuleCellEditor;
import nc.uap.lfw.perspective.listener.EventTableViewLabelProvider;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ExternalPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author chouhl
 *
 */
@SuppressWarnings("restriction")
public class EventPropertiesView extends Composite {

	private TableViewer tv = null;
	
	private LFWBaseEditor editor = null;
	
	private static String[] IS_SERVER = {"��", "��"};
	
	private EventConf[] eventConfs = null;
	
	private String controllerClazz = null;
	
	private WebElement webElement = null;
	
	private UIElement uiElement = null;
	
	private UIMeta uimeta = null;
	
	public EventPropertiesView(Composite parent, int style, EventConf[] eventConfs, String controllerClazz, WebElement webElement, UIElement uiElement, UIMeta uimeta) {
		super(parent, style);
		this.eventConfs = eventConfs;
		this.controllerClazz = controllerClazz;
		this.webElement = webElement;
		this.uiElement = uiElement;
		this.uimeta = uimeta;
		createPartControl(this);
	}
	
	public void createPartControl(Composite parent){
		if(getEditor() != null && !(editor instanceof LFWBrowserEditor)){
			if(editor.getAcceptEventDescs() == null || editor.getAcceptEventDescs().size() == 0){
				return;
			}
		}else if((getEditor() instanceof LFWBrowserEditor)){
			if(webElement != null){
				if(webElement.getAcceptEventDescs() == null || webElement.getAcceptEventDescs().size() == 0){
					LFWPersTool.hideView(LFWTool.ID_LFW_VIEW_SHEET);
					return;
				}else{
					LFWPersTool.showView(LFWTool.ID_LFW_VIEW_SHEET);
				}
			}else if(uiElement != null){
				if(EventUtil.createAcceptEventDescs(uiElement) == null || EventUtil.createAcceptEventDescs(uiElement).size() == 0){
					LFWPersTool.hideView(LFWTool.ID_LFW_VIEW_SHEET);
					return;
				}else{
					LFWPersTool.showView(LFWTool.ID_LFW_VIEW_SHEET);
				}
			}else{
				LFWPersTool.hideView(LFWTool.ID_LFW_VIEW_SHEET);
				return;
			}
		}else{
			return;
		}
		parent.setLayout(new FillLayout());
		ViewForm vf = new ViewForm(parent, SWT.NONE);
		vf.setLayout(new FillLayout());
		tv = new TableViewer(vf, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tv.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		createColumn(table, layout, 80, SWT.NONE, "�¼�����");
		createColumn(table, layout, 80, SWT.NONE, "�¼���������");
		createColumn(table, layout, 80, SWT.NONE, "����");
		createColumn(table, layout, 70, SWT.CENTER, "�Ƿ��ڷ�����������");
		createColumn(table, layout, 50, SWT.CENTER, "�ύ����");
		createColumn(table, layout, 80, SWT.NONE, "���Ӳ���");
		createColumn(table, layout, 80, SWT.NONE, "������");
//		createColumn(table, layout, 200, SWT.NONE, "JS����");
		
		showPropertiesView();
		// ���ܲ���
		Action addEventPropertiesAction = new OperateEventAction(1, this, controllerClazz);
//		editExtendAttributeAction = new EditExtendAttributeAction(this);
		Action deleteEventPropertiesAction = new OperateEventAction(2, this, controllerClazz);
		// ���ܲ˵�
		MenuManager mm = new MenuManager();
		mm.add(addEventPropertiesAction);
//		mm.add(editExtendAttributeAction);
		mm.add(deleteEventPropertiesAction);
		Menu menu = mm.createContextMenu(table);
		table.setMenu(menu);
		// ���ܹ�����
		ToolBar tb = new ToolBar(vf, SWT.FLAT);
		ToolBarManager tbm = new ToolBarManager(tb);
		tbm.add(addEventPropertiesAction);
//		tbm.add(editExtendAttributeAction);
		tbm.add(deleteEventPropertiesAction);
		tbm.update(true);
		vf.setTopLeft(tb);
		
		vf.setContent(tv.getControl());
		// ˫���¼��к󣬴��¼������༭��
		tv.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				EventConf eventConf = (EventConf) selection.getFirstElement();
				if (!eventConf.isOnserver()) {
					if (editor instanceof LFWBaseEditor) {
						EventEditorHandler eventHandler = (EventEditorHandler)((LFWBaseEditor) editor).getEventHandler();
						eventHandler.createEventEditorItem(eventConf, ((LFWBaseEditor) editor).getTabFolder(), editor);
					}
				}else{//��java�༭����
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						String javaEditor = "org.eclipse.jdt.ui.CompilationUnitEditor";
						if(controllerClazz == null || controllerClazz.trim().length() == 0){
							MessageDialog.openError(null, "������ʾ", "�������÷���������!");
							return;
						}
						//��projectExplorer��ͼ
						page.showView("org.eclipse.ui.navigator.ProjectExplorer");
//						ISelection is = ivp.getViewSite().getSelectionProvider().getSelection();
//						if(is instanceof TreeSelection){
//							TreeSelection ts = (TreeSelection)is;
//							ts.getElementComparer();
//						}
						//ͨ��treeItem�õ�project
						IProject proj = LFWPersTool.getCurrentProject();
						IJavaProject javaProj = JavaCore.create(proj);
						IPackageFragmentRoot[] pfrs = javaProj.getAllPackageFragmentRoots();
						PackageFragmentRoot root = null; 
						String classPath = null;
						if(pfrs != null){
							for (int i = 0; i < pfrs.length; i++) {
								if(pfrs[i] instanceof JarPackageFragmentRoot || pfrs[i] instanceof ExternalPackageFragmentRoot)
									continue;
								else if(pfrs[i] instanceof PackageFragmentRoot){
									root = (PackageFragmentRoot) pfrs[i];
									String absPath = proj.getLocation().toString() + "/"+ root.getPath().removeFirstSegments(1).makeRelative()+ "/";
									String absPath1 = absPath.replaceAll("\\\\", "/");
									absPath += controllerClazz.replaceAll("\\.", "/");
									absPath += ".java";
									File f = new File(absPath);
									if(f.exists()){
										try {
											classPath = f.getCanonicalPath().replaceAll("\\\\", "/");
											classPath = classPath.substring(classPath.indexOf(absPath1) + absPath1.length());
										} catch (IOException e) {
											MainPlugin.getDefault().logError(e);
										}
										break;
									}
								}
							}
						}
						
						if(root == null){
							MessageDialog.openError(null, "������ʾ", "û���ҵ��ļ����ڰ�!");
							return;
						}
						String filePath = root.getPath().toString() + "/" +  controllerClazz.replaceAll("\\.", "/") + ".java";
						if(classPath != null){
							filePath = root.getPath().toString() + "/" + classPath;
						}
						Workspace wp = (Workspace) ResourcesPlugin.getWorkspace();
						IPath ph = new Path(filePath);
						IFile file = (IFile) wp.newResource(ph, IResource.FILE);
						FileEditorInput input = new FileEditorInput(file);
						page.openEditor(input, javaEditor);
					} catch (PartInitException e) {
						MainPlugin.getDefault().logError(e);
					} catch (JavaModelException e) {
						MainPlugin.getDefault().logError(e);
					}
				}
			}
		});
	}
	
	private void createColumn(Table table, TableLayout layout, int width,
			int align, String text) {
		layout.addColumnData(new ColumnWeightData(width));
		new TableColumn(table, align).setText(text);
	}

	/**
	 * ��ʾ����
	 */
	public void showPropertiesView() {
		tv.setContentProvider(new TableViewContentProvider());
		tv.setLabelProvider(new EventTableViewLabelProvider(true));

		if(eventConfs != null){
			List<EventConf> eventConfList = new ArrayList<EventConf>();
			for(EventConf eventConf : eventConfs){
				switch(eventConf.getEventStatus()){
					case EventConf.NORMAL_STATUS:
					case EventConf.ADD_STATUS:
						eventConfList.add(eventConf);
						break;
					default:
						break;
				}
			}
			tv.setInput(eventConfList);
		}

		tv.setColumnProperties(new String[]{"methodName", "name", "params", "isOnServer", "submitRule", "", "controllerClazz"});
		// ���ñ༭��
		CellEditor[] cellEditor = new CellEditor[7];
		cellEditor[0] = null;
		cellEditor[1] = null;
		cellEditor[2] = null;
		cellEditor[3] = new ComboBoxCellEditor(tv.getTable(), IS_SERVER, SWT.READ_ONLY);
		cellEditor[4] = new EventSubmitRuleCellEditor(tv.getTable(), tv);
		cellEditor[5] = null;
		cellEditor[6] = null;
//		cellEditor[4] = new SubmitRuleCellEditor(tv.getTable(), tv, jsListener);
//		cellEditor[5] = new ExtendParameterCellEidtor(tv.getTable(), tv, jsListener);
		
		tv.setCellEditors(cellEditor);
		tv.setCellModifier(new MyCellModifier(tv));
	}
	
	public TableViewer getTv() {
		return tv;
	}

	public void setTv(TableViewer tv) {
		this.tv = tv;
	}
	
	public LFWBaseEditor getEditor() {
		if(editor == null){
			editor = LFWBaseEditor.getActiveEditor();
		}
		return editor;
	}

	public void setEditor(LFWBaseEditor editor) {
		this.editor = editor;
	}

	private class MyCellModifier implements ICellModifier {

		private TableViewer tv;
		
		public MyCellModifier(TableViewer tv) {
			this.tv = tv;
		}
		
		
		public boolean canModify(Object element, String property) {
			return true;
		}

		/**
		 * ��ȡ��ʾ����
		 */
		public Object getValue(Object element, String property) {
			EventConf eventObj = (EventConf) element;
			if (property.equals("isOnServer")) {
				int result = 1;
//				if (null == eventObj.getScript() || "".equals(eventObj.getScript().trim())) {  // ��Script����ʱ�������޸��ύ����
				result = eventObj.isOnserver() ? 0 : 1;
				return result;
//				}
			} else if (property.equals("submitRule")) {
				
			}
			return null;
		}

		/**
		 * �޸ĺ�ִ�з���
		 */
		public void modify(Object element, String property, Object value) {
			TableItem item = (TableItem) element;
			EventConf eventObj = (EventConf) item.getData();
			if (property.equals("isOnServer")) {
//				if (null == eventObj.getScript() || "".equals(eventObj.getScript().trim())) {  // ��Script����ʱ�������޸��ύ����
				eventObj.setOnserver((Integer)value == 0 ? true : false);
//				editor.saveJsListener(jsListener.getId(), eventObj, jsListener);
//				editor.saveListener(jsListener.getId(), eventObj, jsListener);
				
				if (eventObj.isOnserver() && eventObj.getSubmitRule() == null) {  // ��Ϊ�������������ҵ�ǰû��SubmitRule
					// �½�Ĭ�ϵ�SubmitRule
					EventSubmitRule submitRule = createDefaultSubmitRule();
					eventObj.setSubmitRule(submitRule);
				}
				
				tv.refresh();
				editor.setDirtyTrue();
//				}
			} else if (property.equals("submitRule")) {
				
			}
		}
		
		/**
		 * ����Ĭ�ϵ�SubmitRule
		 * @return
		 */
		private EventSubmitRule createDefaultSubmitRule() {
			EventSubmitRule submitRule = new EventSubmitRule();
			LfwWidget widget = LFWPersTool.getCurrentWidget();
			if (widget != null) {
				// ����Widget�ύ����
				WidgetRule widgetRule = new WidgetRule();
				widgetRule.setId(widget.getId());
				// ����Dataset�ύ����
				Dataset[] dsArray = widget.getViewModels().getDatasets();
				for (Dataset dataset : dsArray) {
					DatasetRule dsRule = new DatasetRule();
					dsRule.setId(dataset.getId());
					dsRule.setType(DatasetRule.TYPE_CURRENT_LINE);
					widgetRule.addDsRule(dsRule);
				}
				submitRule.addWidgetRule(widgetRule);
			}
			return submitRule;
		}
	}

	public WebElement getWebElement() {
		return webElement;
	}

	public UIElement getUiElement() {
		return uiElement;
	}

	public UIMeta getUimeta() {
		return uimeta;
	}
	
}
