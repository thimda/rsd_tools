package nc.uap.lfw.perspective.listener;

import java.io.File;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.event.conf.DatasetRule;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.event.conf.WidgetRule;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.perspective.editor.TableViewContentProvider;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ExternalPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * ��ͼ��Listener��Ԫ��������ͼ
 * 
 * @author guoweic
 * 
 */
public class ListenerPropertiesView extends Composite {

	private LFWBaseEditor editor = null;

	private TableViewer tv = null;
	
	private List<EventHandlerConf> jsEventList;
	
	private JsListenerConf jsListener;
	
	private static String[] IS_SERVER = {"��", "��"};

	/**
	 * �б༭��
	 * @author guoweic
	 *
	 */
	private class MyCellModifier implements ICellModifier {

		private TableViewer tv;
		
		public MyCellModifier(TableViewer tv) {
			this.tv = tv;
		}
		
		
		public boolean canModify(Object element, String property) {
			if(jsListener != null && jsListener.getFrom() != null)
				return false;
			return true;
		}

		/**
		 * ��ȡ��ʾ����
		 */
		public Object getValue(Object element, String property) {
			EventHandlerConf eventObj = (EventHandlerConf) element;
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
			EventHandlerConf eventObj = (EventHandlerConf) item.getData();
			if (property.equals("isOnServer")) {
//				if (null == eventObj.getScript() || "".equals(eventObj.getScript().trim())) {  // ��Script����ʱ�������޸��ύ����
				eventObj.setOnserver((Integer)value == 0 ? true : false);
//				editor.saveJsListener(jsListener.getId(), eventObj, jsListener);
				editor.saveListener(jsListener.getId(), eventObj, jsListener);
				
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
	
	public ListenerPropertiesView(Composite parent, int style, JsListenerConf jsListener) {
		super(parent, style);
		this.jsListener = jsListener;
		createPartControl(this);
	}

	public void createPartControl(Composite parent) {
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
		createColumn(table, layout, 80, SWT.NONE, "����");
		createColumn(table, layout, 70, SWT.CENTER, "�Ƿ��ڷ�����������");
		createColumn(table, layout, 50, SWT.CENTER, "�ύ����");
		createColumn(table, layout, 80, SWT.NONE, "���Ӳ���");
//		createColumn(table, layout, 200, SWT.NONE, "JS����");
		
		showPropertiesView();
		
		vf.setContent(tv.getControl());
		
		// ˫���¼��к󣬴��¼������༭��
		tv.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				EventHandlerConf jsEventObj = (EventHandlerConf) selection.getFirstElement();
				if (!jsEventObj.isOnserver()) {
					if (editor instanceof LFWBaseEditor) {
						ListenerEditorHandler listenerHandler = ((LFWBaseEditor) editor).getListenerHandler();
						listenerHandler.createEventEditorItem(jsEventObj, jsListener.getId(), ((LFWBaseEditor) editor).getTabFolder(), editor);
					}
				}
				
				//��java�༭����
				else if(jsEventObj.isOnserver()){
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						String javaEditor = "org.eclipse.jdt.ui.CompilationUnitEditor";
						String path =  jsListener.getServerClazz();
						if(path == null || "".equals(path)){
							MessageDialog.openError(null, "������ʾ", "�������÷���������!");
							return;
						}
						//��projectExplorer��ͼ
						page.showView("org.eclipse.ui.navigator.ProjectExplorer");
						//ͨ��treeItem�õ�project
						IProject proj = LFWPersTool.getCurrentProject();
						JavaProject javaProj = (JavaProject) JavaCore.create(proj);
						IPackageFragmentRoot[] pfrs;
						pfrs = javaProj.getAllPackageFragmentRoots();
						PackageFragmentRoot root = null; 
						if(pfrs != null){
							for (int i = 0; i < pfrs.length; i++) {
								if(pfrs[i] instanceof JarPackageFragmentRoot || pfrs[i] instanceof ExternalPackageFragmentRoot)
									continue;
								else if(pfrs[i] instanceof PackageFragmentRoot){
									root = (PackageFragmentRoot) pfrs[i];
									String absPath = proj.getLocation().toString() + "/"+ root.getPath().removeFirstSegments(1).makeRelative()+ "/";
									absPath += path.replaceAll("\\.", "/");
									absPath += ".java";
									File f = new File(absPath);
									if(f.exists()){
										root = (PackageFragmentRoot) pfrs[i];
										break;
									}
								}
								
							}
						}
						
						if(root == null){
							MessageDialog.openError(null, "������ʾ", "û���ҵ��ļ����ڰ�!");
							return;
						}
						String filePath = root.getPath().toString() + "/" +  path.replaceAll("\\.", "/") + ".java";
						Workspace wp = (Workspace) ResourcesPlugin.getWorkspace();
						IPath ph = new Path(filePath);
						IFile file = (IFile) wp.newResource(ph, IResource.FILE);
						FileEditorInput input = new FileEditorInput(file);
						page.openEditor(input, javaEditor);
					} catch (PartInitException e) {
						e.printStackTrace();
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}
	
	/**
	 * ��ʾ����
	 */
	public void showPropertiesView() {
		tv.setContentProvider(new TableViewContentProvider());
		tv.setLabelProvider(new ListenerTableViewLabelProvider());
		
		jsEventList = jsListener.getJsEventHandlerList();
		// ��ȡscript����jsEventList��
		Map<String, EventHandlerConf> jsEventMap = jsListener.getEventHandlerMap();
		for (int i = 0, n = jsEventList.size(); i < n; i++) {
			EventHandlerConf jsEvent = jsEventList.get(i);
			if (jsEventMap.containsKey(jsEvent.getName())) {
				//TODO
				EventHandlerConf event = jsEventMap.get(jsEvent.getName());
				String script = event.getScript();
				jsEvent.setScript(null == script || "null".equals(script.trim()) ? "" : script);
				jsEvent.setOnserver(event.isOnserver());
				jsEvent.setSubmitRule(event.getSubmitRule());
				jsEvent.setParamList(event.getParamList());
				jsEvent.setExtendParamList(event.getExtendParamList());
//				jsEventList.add(i, jsEventMap.get(jsEvent.getName()));
//				jsEventList.remove(i + 1);
			}
		}
		tv.setInput(jsEventList);

		tv.setColumnProperties(new String[]{"name", "params", "isOnServer", "submitRule", "script"});
		// ���ñ༭��
		CellEditor[] cellEditor = new CellEditor[5];
		cellEditor[0] = null;
		cellEditor[1] = null;
		cellEditor[2] = new ComboBoxCellEditor(tv.getTable(), IS_SERVER, SWT.READ_ONLY);
		cellEditor[3] = new SubmitRuleCellEditor(tv.getTable(), tv, jsListener);
		cellEditor[4] = new ExtendParameterCellEidtor(tv.getTable(), tv, jsListener);
//		cellEditor[5] = null;
		
		tv.setCellEditors(cellEditor);
		tv.setCellModifier(new MyCellModifier(tv));
		
	}

	private void createColumn(Table table, TableLayout layout, int width,
			int align, String text) {
		layout.addColumnData(new ColumnWeightData(width));
		new TableColumn(table, align).setText(text);
	}

	public TableViewer getTv() {
		return tv;
	}

	public void setTv(TableViewer tv) {
		this.tv = tv;
	}

	public LFWBaseEditor getEditor() {
		return editor;
	}

	public void setEditor(LFWBaseEditor editor) {
		this.editor = editor;
	}

}
