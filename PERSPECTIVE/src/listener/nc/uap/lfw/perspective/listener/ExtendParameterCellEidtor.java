package nc.uap.lfw.perspective.listener;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.event.conf.DatasetListener;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.perspective.editor.DataSetEditor;
import nc.uap.lfw.perspective.model.DatasetElementObj;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * JsListener的Event列表的参数列编辑器
 * 
 * @author guoweic
 *
 */
public class ExtendParameterCellEidtor extends DialogCellEditor {

	private Composite composite;
	
	private TableViewer tv;
	
	private JsListenerConf jsListener;

	public ExtendParameterCellEidtor(Composite parent, TableViewer tv, JsListenerConf jsListener) {
		this(parent, SWT.NONE);
		this.tv = tv;
		this.jsListener = jsListener;
		
	}

	public ExtendParameterCellEidtor(Composite parent, int style) {
		super(parent, style);
		doSetValue("");
	}

	protected Control createContents(Composite cell) {
		composite = new Composite(cell, getStyle());
		return composite;
	}

	protected Object openDialogBox(Control cellEditorWindow) {
		if (jsListener instanceof DatasetListener) {
			IStructuredSelection selection = (IStructuredSelection) tv.getSelection();
			EventHandlerConf jsEventObj = (EventHandlerConf) selection.getFirstElement();
			
			if (DatasetListener.ON_BEFORE_DATA_CHANGE.equals(jsEventObj.getName())
					|| DatasetListener.ON_AFTER_DATA_CHANGE.equals(jsEventObj.getName())) {  // 数据改变前后事件
			
				LfwParameter param = jsEventObj.getExtendParam(EventHandlerConf.PARAM_DATASET_FIELD_ID);
				
				if (param == null) {
					param = new LfwParameter();
					param.setName(EventHandlerConf.PARAM_DATASET_FIELD_ID);
					jsEventObj.addExtendParam(param);
				}
					
				DataChangeParamDialog dialog = new DataChangeParamDialog(cellEditorWindow.getShell());
				
				DatasetElementObj dsobj = (DatasetElementObj) DataSetEditor.getActiveEditor().getGraph().getCells().get(0);
				
				Dataset ds = dsobj.getDs();
				dialog.setDataset(ds);
				dialog.setExtendParameter(param);
				
				if (dialog.open() == Dialog.OK) {
					param.setDesc(dialog.getMainContainer().getExtendParameter().getDesc());
					
					LFWBaseEditor.getActiveEditor().saveListener(jsListener.getId(), jsEventObj, jsListener);
					
					tv.refresh();
					
					LFWBaseEditor.getActiveEditor().setDirtyTrue();
					
					return null;
				}
				
				
				
			}
		}
		return null;
	}

}
