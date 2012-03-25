package nc.uap.lfw.perspective.listener;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.uap.lfw.core.event.conf.EventHandlerConf;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.EditorPart;

@SuppressWarnings("restriction")
public class ListenerEditorHandler {
	
	// 最外层编辑器
	private EditorPart parentEdtor;
//	// 项目路径
//	private String projectPath = "";
	
	/**
	 * 创建并打开事件方法编辑tab页
	 * @param event
	 * @param listenerName
	 * @param tabFolder
	 * @param editor
	 * @return
	 */
	public EventEditorControl createEventEditorItem(EventHandlerConf event, String listenerName, CTabFolder tabFolder, EditorPart editor) {
		parentEdtor = editor;
		String itemText = listenerName + ":" + event.getName();
		CTabItem[] items = tabFolder.getItems();
		for (int i = 0, n = items.length; i < n; i++) {
			if (items[i].getText().equals(itemText)) {
				tabFolder.setSelection(i);
				return openEventEditorControl(event, listenerName, tabFolder, editor);
			}
		}
		CTabItem item = new CTabItem(tabFolder, SWT.NONE);
		EventEditorControl eventCtrl = openEventEditorControl(event, listenerName, tabFolder, editor);
		item.setText(itemText);
		item.setControl(eventCtrl);
		item.setShowClose(true);
		tabFolder.setSelection(item);
		
		/**
		 * Tab页关闭事件
		 */
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			public void close(CTabFolderEvent event) {
				CTabItem currentItem = (CTabItem) event.item;
				EventEditorControl ctrl = (EventEditorControl) currentItem.getControl();
				if (parentEdtor.isDirty()) {
					// 保存所有事件代码
					((LFWBaseEditor)parentEdtor).saveEventScript();
				}
				((LFWBaseEditor)parentEdtor).removeEventCtrl(ctrl);
//				// 删除临时文件
//				FileUtil.deleteEventFile(ctrl);
			}
		});
		
		/**
		 * Event代码改变事件
		eventCtrl.getEditor().getDocument().addDocumentListener(new IDocumentListener() {
			public void documentAboutToBeChanged(DocumentEvent event) {
				
			}

			public void documentChanged(DocumentEvent event) {
				if (parentEdtor instanceof LFWBaseEditor)
					((LFWBaseEditor)parentEdtor).setDirtyTrue();
			}
		});
		 */
		
		/**
		 * Event代码改变事件
		 */
		eventCtrl.getJsText().addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (parentEdtor instanceof LFWBaseEditor)
					((LFWBaseEditor)parentEdtor).setDirtyTrue();
			}
		});
		
		if (parentEdtor instanceof LFWBaseEditor) {
			((LFWBaseEditor) parentEdtor).addEventCtrl(eventCtrl);
		}
		
		return eventCtrl;
			
	}
	
	/**
	 * 临时文件目录
	private final String TEMP_FILE_FOLDER = "temp/listener/event";
	 */
	
	/**
	 * 打开JS编辑器
	 * @param event
	 * @param listenerName
	 * @param parent
	 * @param editor
	 * @return
	 */
	public EventEditorControl openEventEditorControl(EventHandlerConf event, String listenerName, Composite parent, EditorPart editor) {
//		IEditorInput editorInput = editor.getEditorInput();
//		// 获取项目路径
//		if (editorInput instanceof LfwBaseEditorInput)
//			projectPath = LFWPersTool.getProjectPath();
//		
//		// 创建临时js文件
//		String path = createEventFile(projectPath + "/" + TEMP_FILE_FOLDER, event, listenerName);
//		
//		IFile file = new FileSystemResourceManager((Workspace) ResourcesPlugin.getWorkspace())
//				.fileForLocation(new Path(path));
//		IEditorInput input = new FileEditorInput(file);
//		EditorSite site = new EditorSite(((WorkbenchPage) editor.getEditorSite()
//				.getPage()).getEditorReferences()[0], editor,
//				(WorkbenchPage) editor.getEditorSite().getPage(),
//				((EditorSite) editor.getEditorSite()).getEditorDescriptor());
//		site.setActionBars((SubActionBars) editor.getEditorSite()
//						.getActionBars());
//		EventEditorControl eventCtrl = new EventEditorControl(parent, SWT.NONE,
//				site, input, listenerName, event, path);
		
		EditorSite site = new EditorSite(((WorkbenchPage) editor.getEditorSite()
				.getPage()).getEditorReferences()[0], editor,
				(WorkbenchPage) editor.getEditorSite().getPage(),
				((EditorSite) editor.getEditorSite()).getEditorDescriptor());
		EventEditorControl eventCtrl = new EventEditorControl(parent, SWT.NONE, site, listenerName, event);
		
		// 设置编辑时临时文件的input
//		((LFWBaseEditor)parentEdtor).setFileEditorInput(input);
		return eventCtrl;
	}

	/**
	 * 构造事件处理代码临时JS文件
	 * @param folderPath
	 * @param event
	 * @param listenerName
	 * @return
	public String createEventFile(String folderPath, EventHandlerConf event,
			String listenerName) {
		File folder = new File(folderPath);
		String fileName = listenerName + "_" + event.getName() + "_event_temp.js";
		if (!folder.exists())
			folder.mkdirs();
		File f = null;
		try {
			f = new File(folderPath + "\\" + fileName);
			f.createNewFile();
		} catch (IOException _ex) {
		}
		if (f == null)
			FileUtil.saveToFile(folderPath, fileName, event.getScript());
		else
			FileUtil.saveToFile(f, event.getScript());
		return f.getPath();
	}
	 */
	
	/**
	 * 保存事件代码
	 * @param ctrl
	 */
	public void saveEventScript(EventEditorControl eventCtrl) {
//		String script = eventCtrl.getEditor().getDocument().get();

		String script = eventCtrl.getJsText().getText();
		
//		// 保存到临时文件中
//		FileUtil.saveToFile(eventCtrl.getFilePath(), script);
		
		// 更新XML文件
		eventCtrl.getJsEventHandler().setScript(script);
		if (parentEdtor instanceof LFWBaseEditor)
//			((LFWBaseEditor)parentEdtor).saveJsListener(eventCtrl.getListenerName(), eventCtrl.getJsEventHandler(), null);
			((LFWBaseEditor)parentEdtor).saveListener(eventCtrl.getListenerName(), eventCtrl.getJsEventHandler(), null);
		// 更新Listener事件列表
		((LFWBaseEditor)parentEdtor).getViewPage().getListenerPropertiesView().showPropertiesView();
	}
	
	
}
