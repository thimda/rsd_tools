package nc.uap.lfw.perspective.listener;

import java.util.Map;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.event.conf.JsListenerConf;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ListenerElementObj extends LfwElementObjWithGraph {

	private static final long serialVersionUID = -3959908675867965556L;
	// 当前图像
	private ListenerElementFigure figure;
	// 事件集合
	private Map<String, JsListenerConf> listenerMap;
	// 当前编辑器
	private LFWBaseEditor editor;
	
	// 当前选中的Listener
	private JsListenerConf currentListener = null;

	public static final String PROP_CHILD_ADD = "prop_child_add";
	public static final String PROP_CHILD_REMOVE = "prop_child_remove";
	public static final String PROP_CELL_SIZE = "cell_size";
	public static final String PROP_CELL_LOCATION = "cell_location";

	public static final String PROP_LISTENER_ID = "PROP_LISTENER_ID";
	public static final String PROP_LISTENER_SERVER_CLASS = "PROP_LISTENER_SERVER_CLASS";
	
	public static final String warningMessage = "非自定义Listener,属性不可修改!";
	public boolean canChange(JsListenerConf listener) {
		if(listener != null && listener.getFrom() != null){
			MessageDialog.openWarning(null, "警告信息", warningMessage);
			return false;
		}
		return true;
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor[] pds = new PropertyDescriptor[2];
		pds[0] = new TextPropertyDescriptor(PROP_LISTENER_ID, "ID");
		pds[0].setCategory("JS 事件");
		pds[1] = new TextPropertyDescriptor(PROP_LISTENER_SERVER_CLASS, "服务器类");
		pds[1].setCategory("JS 事件");
		try{
			LFWBaseEditor.getActiveEditor().getViewPage().addListenerPropertiesView(currentListener);
		}catch(Exception e){
			//LfwLogger.error(e.getMessage(), e);
		}
		return pds;
	}

	public void setPropertyValue(Object id, Object value) {
		if(!canChange(currentListener))
			return;
		if (PROP_LISTENER_ID.equals(id)) {
			listenerMap.remove(currentListener.getId());
			currentListener.setId((String) value);
			listenerMap.put(currentListener.getId(), currentListener);
			figure.refreshListeners();
		} else if (PROP_LISTENER_SERVER_CLASS.equals(id)) {
			currentListener.setServerClazz((String) value);
		}
		LFWBaseEditor.getActiveEditor().setDirtyTrue();
	}

	public Object getPropertyValue(Object id) {
		if (null != currentListener) {
			if (PROP_LISTENER_ID.equals(id)) {
				return currentListener.getId() == null ? "" : currentListener.getId();
			} else if (PROP_LISTENER_SERVER_CLASS.equals(id)) {
				return currentListener.getServerClazz() == null ? "" : currentListener.getServerClazz();
			}
		}
		return null;
	}

//	public void setElementAttribute(Element ele) {
//		ele.setAttribute("id", getId());
//	}

	public Object getEditableValue() {
		return this;
	}

	public Element createElement(Document doc) {
//		Element ele = doc.createElement(DATASET_TAG);
//		setElementAttribute(ele);
//		ArrayList<RefDatasetElementObj> al = new ArrayList<RefDatasetElementObj>();
//		List<RefDatasetElementObj> cells = getCells();
//		Element cellListEle = doc.createElement("celllist");
//		ele.appendChild(cellListEle);
//		for (int i = 0; i < cells.size(); i++) {
//			RefDatasetElementObj cell = cells.get(i);
//			al.add(cell);
//		}
//		for (int i = 0; i < al.size(); i++) {
//			cellListEle.appendChild(al.get(i).createElement(doc, this));
//		}
//		return ele;
		return null;
	}

	public ListenerElementFigure getFigure() {
		return figure;
	}

	public void setFigure(ListenerElementFigure figure) {
		this.figure = figure;
	}

	public Map getListenerMap() {
		return listenerMap;
	}

	public void setListenerMap(Map<String, JsListenerConf> listenerMap) {
		this.listenerMap = listenerMap;
	}

	public LFWBaseEditor getEditor() {
		return editor;
	}

	public void setEditor(LFWBaseEditor editor) {
		this.editor = editor;
	}

	
	public WebElement getWebElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public JsListenerConf getCurrentListener() {
		return currentListener;
	}

	/**
	 * 设置当前选中的Listener
	 * @param currentListener
	 */
	public void setCurrentListener(JsListenerConf currentListener) {
		this.currentListener = currentListener;
	}


}
