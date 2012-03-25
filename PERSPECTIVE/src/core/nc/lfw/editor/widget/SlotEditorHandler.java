package nc.lfw.editor.widget;

import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.uap.lfw.perspective.listener.EventEditorControl;

import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.part.EditorPart;

/**
 * �۱༭������
 * @author guoweic
 *
 */
public class SlotEditorHandler {
	/**
	 * �����༭��
	 */
	private EditorPart parentEdtor;
	
	/**
	 * ��������Slot�༭tabҳ
	 * @param listenerName
	 * @param tabFolder
	 * @param editor
	 * @return
	 */
	public EventEditorControl createSlotEditorItem(Label label, WidgetElementObj widgetObj, String itemId) {
		parentEdtor = PagemetaEditor.getActivePagemetaEditor();
		CTabFolder tabFolder = ((PagemetaEditor) parentEdtor).getTabFolder();
		String itemText = widgetObj.getWidget().getId() + ":" + itemId;
		int opendIndex = checkTabItemOpen(tabFolder, itemText);
		if (-1 != opendIndex) {
			tabFolder.setSelection(opendIndex);
			return null;
		}
		CTabItem item = new CTabItem(tabFolder, SWT.NONE);
//		EventEditorControl eventCtrl = openEventEditorControl(listenerName, tabFolder, editor);
		item.setText(itemText);
//		item.setControl(eventCtrl);
		item.setShowClose(true);
		tabFolder.setSelection(item);
		
//		/**
//		 * Tabҳ�ر��¼�
//		 */
//		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
//			public void close(CTabFolderEvent event) {
//				CTabItem currentItem = (CTabItem) event.item;
//				EventEditorControl ctrl = (EventEditorControl) currentItem.getControl();
//				if (parentEdtor instanceof DataSetEditor) {
//					if (parentEdtor.isDirty()) {
//						// ���������¼�����
//						((DataSetEditor)parentEdtor).saveEventScript();
//					}
//					((DataSetEditor)parentEdtor).removeEventCtrl(ctrl);
//				} else if (parentEdtor instanceof PagemetaEditor) {
//					//TODO guoweic
//					
//				}
//				// ɾ����ʱ�ļ�
//				FileUtil.deleteEventFile(ctrl);
//			}
//		});
//		
//		/**
//		 * Event����ı��¼�
//		 */
//		eventCtrl.getEditor().getDocument().addDocumentListener(new IDocumentListener() {
//			public void documentAboutToBeChanged(DocumentEvent event) {
//				
//			}
//
//			public void documentChanged(DocumentEvent event) {
//				if (parentEdtor instanceof DataSetEditor)
//					((DataSetEditor)parentEdtor).setDirtyTrue();
//			}
//		});
//		
//		if (parentEdtor instanceof DataSetEditor) {
//			((DataSetEditor) parentEdtor).addEventCtrl(eventCtrl);
//		}
//		
//		return eventCtrl;
		return null;
			
	}
	
	/**
	 * ����Tabҳ�Ƿ��Ѵ�
	 * @param tabFolder
	 * @param itemText
	 * @return
	 */
	private int checkTabItemOpen(CTabFolder tabFolder, String itemText) {
		CTabItem[] items = tabFolder.getItems();
		for (int i = 0, n = items.length; i < n; i++) {
			if (itemText.equals(items[i].getText()))
				return i;
		}
		return -1;
	}
	
	/**
	 * ��ʱ�ļ�Ŀ¼
	 */
	private final String TEMP_FILE_FOLDER = "tempslot";
	
//	/**
//	 * ��slot�༭��
//	 * @param event
//	 * @param listenerName
//	 * @param parent
//	 * @param editor
//	 * @return
//	 */
//	public EventEditorControl openEventEditorControl(String listenerName, Composite parent, EditorPart editor) {
//		// ������ʱjs�ļ�
//		String path = createEventFile(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "\\ttt\\" + TEMP_FILE_FOLDER, event, listenerName);
//
//		IFile file = new FileSystemResourceManager((Workspace) ResourcesPlugin.getWorkspace())
//				.fileForLocation(new Path(path));
//		
////		IFile f = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
//		
//		IEditorInput input = new FileEditorInput(file);
//		EditorSite site = new EditorSite(((WorkbenchPage) editor.getEditorSite()
//				.getPage()).getEditorReferences()[0], editor,
//				(WorkbenchPage) editor.getEditorSite().getPage(),
//				((EditorSite) editor.getEditorSite()).getEditorDescriptor());
//		site.setActionBars((SubActionBars) editor.getEditorSite()
//						.getActionBars());
//		EventEditorControl eventCtrl = new EventEditorControl(parent, SWT.NONE,
//				site, input);
//		return eventCtrl;
//	}
//
//	/**
//	 * �����¼����������ʱJS�ļ�
//	 * @param folderPath
//	 * @param event
//	 * @param listenerName
//	 * @return
//	 */
//	public String createEventFile(String folderPath, JsEventHandler event,
//			String listenerName) {
//		File folder = new File(folderPath);
//		String fileName = listenerName + "_" + event.getName() + "_temp.js";
//		if (!folder.exists())
//			folder.mkdirs();
//		File f = null;
//		try {
//			f = new File(folderPath + "\\" + fileName);
//			f.createNewFile();
//		} catch (IOException _ex) {
//		}
//		if (f == null)
//			FileUtil.saveToFile(folderPath, fileName, event.getScript());
//		else
//			FileUtil.saveToFile(f, event.getScript());
//		return f.getPath();
//	}
//	
//	/**
//	 * �����¼�����
//	 * @param ctrl
//	 */
//	public void saveEventScript(EventEditorControl eventCtrl) {
//		FileEditorInput input = (FileEditorInput)eventCtrl.getEditorInput();
//		String script = eventCtrl.getEditor().getDocument().get();
//		FileUtil.saveToFile(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + input.getFile().getFullPath().toString(), script);
//		//TODO guoweic: ����XML�ļ�
//		
//		//TODO guoweic: ����Listener�¼��б�
//		
//		
//	}
}
