package nc.lfw.editor.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LfwBaseGraph;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.editor.pagemeta.PagemetaGraph;
import nc.lfw.editor.widget.plug.PluginDescElementObj;
import nc.lfw.editor.widget.plug.PlugoutDescElementObj;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.perspective.model.Constant;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * WidgetԪ�ض���
 * @author guoweic
 *
 */
public class WidgetElementObj extends LfwElementObjWithGraph {

	private static final long serialVersionUID = -8484960457664556121L;
	
	
	/*widget����*/
	private LfwWidget widget = null;
	/*��ǰͼ��*/
	private WidgetElementFigure figure;
	/*plugoutDesc�б�*/
	private List<PlugoutDescElementObj> plugoutCells = new ArrayList<PlugoutDescElementObj>();
	/*pluginDesc�б�*/
	private List<PluginDescElementObj> pluginCells = new ArrayList<PluginDescElementObj>();

	// �Ƿ�����Widget
//	private boolean isMainWidget = false;
//	private String refId = "";

	public static final String PROP_CHILD_ADD = "prop_child_add";
	public static final String PROP_CHILD_REMOVE = "prop_child_remove";

	public static final String PROP_ISDIALOG = "element_ISDIALOG";
//	public static final String PROP_ISMAIN = "element_ISMAIN";
	public static final String PROP_WIDGET_ID = "WIDGET_ID";
//	public static final String PROP_HEIGHT = "WIDGET_height";
//	public static final String PROP_WIDTH = "WIDGET_width";
	public static final String PROP_I18NNAME="WIDGET_i18nname";

	/**
	 * ��ȡԴWidget����δʹ���ź�
	 * @param connectorMap
	 * @param sourceWidget
	 * @return
	public static List<String> getUnUsedSignal(Map<String, Connector> connectorMap, WidgetElementObj sourceWidget) {
//		List<String> signalList = new ArrayList<String>();
//		List<String> resultList = new ArrayList<String>();
//		// ԴWidget�����ź�
//		Set<String> signalSet = sourceWidget.getWidget().getSignalMap().keySet();
//		for (String signalId : signalSet) {
//			signalList.add(signalId);
//			resultList.add(signalId);
//		}
//		// ��������������
//		Iterator it = connectorMap.keySet().iterator();
//		while (it.hasNext()) {
//			Connector connector = connectorMap.get(it.next());
//			if (connector.getSource().equals(sourceWidget.getWidget().getId())) {  // ����ͬԴWidget������������
//				for (int i = 0, n = signalList.size(); i < n; i++) {
//					// ������ź��ѱ�ʹ�ã�����б���ɾ��
//					if (connector.getSignal().equals(signalList.get(i)))
//						resultList.remove(connector.getSignal());
//				}
//			}
//		}
//		return resultList;
		return null;
	}
	 */
	
//	public boolean addPlugoutCell(PlugoutDescElementObj cell) {
//		cell.setWidgetObj(this);
//		boolean b = plugoutCells.add(cell);
//		if (b) {
//			fireStructureChange(PROP_PLUGOUT_ADD, cell);
//		}
//		return b;
//	}
//
//	public boolean removePlugoutCell(PlugoutDescElementObj cell) {
//		boolean b = plugoutCells.remove(cell);
//		cell.setWidgetObj(null);
//		if (b) {
//			fireStructureChange(PROP_PLUGOUT_REMOVE, cell);
//		}
//		return b;
//	}
//
//	public List<PlugoutDescElementObj> getPlugoutCells() {
//		return plugoutCells;
//	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = null;
//		pds[2] = new TextPropertyDescriptor(PROP_WIDTH, "���");
//		pds[2].setCategory(LFWPerspectiveNameConst.WIDGET_CN);
//		pds[3] = new TextPropertyDescriptor(PROP_HEIGHT, "�߶�");
//		pds[3].setCategory(LFWPerspectiveNameConst.WIDGET_CN);
		if(!LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			pds = new PropertyDescriptor[3];
			pds[0] = new TextPropertyDescriptor(PROP_WIDGET_ID, "ID");
			pds[0].setCategory(LFWPerspectiveNameConst.WIDGET_CN);
			pds[1] = new TextPropertyDescriptor(PROP_I18NNAME, "����");
			pds[1].setCategory(LFWPerspectiveNameConst.WIDGET_CN);
			pds[2] = new ComboBoxPropertyDescriptor(PROP_ISDIALOG, "�Ƿ��ǶԻ���", Constant.ISDIALOG);
			pds[2].setCategory(LFWPerspectiveNameConst.WIDGET_CN);
			al.addAll(Arrays.asList(pds));
			return al.toArray(new IPropertyDescriptor[0]);
		}else{
			return super.getPropertyDescriptors();
		}
	}

	public void setPropertyValue(Object id, Object value) {
		if (PROP_WIDGET_ID.equals(id)) {
			//TODO
			setId((String)value);
		} else if (PROP_ISDIALOG.equals(id)) {
			if ((boolean) value.equals(0))
				widget.setDialog(true);
			else
				widget.setDialog(false);
			PagemetaEditor pmEditor = PagemetaEditor.getActivePagemetaEditor();
			if (pmEditor != null) {
				pmEditor.addTempWidget(widget);
				pmEditor.setDirtyTrue();
			} else {
				WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
				wdEditor.setDirtyTrue();
			}
			
		}
//		else if(PROP_WIDTH.equals(id)){
//			widget.setWidth((String)value);
//			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
//			wdEditor.setDirtyTrue();
//		}
//		else if(PROP_HEIGHT.equals(id)){
//			widget.setHeight((String)value);
//			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
//			wdEditor.setDirtyTrue();
//		}
		else if(PROP_I18NNAME.equals(id)){
			widget.setI18nName((String)value);
			WidgetEditor wdEditor = WidgetEditor.getActiveWidgetEditor();
			wdEditor.setDirtyTrue();
		}
//		else if (PROP_ISMAIN.equals(id)) {
//			if ((boolean) value.equals("0"))
//				setMainWidget(true);
//			else
//				setMainWidget(false);
//		}
	}

	public Object getPropertyValue(Object id) {
		if (PROP_WIDGET_ID.equals(id)) {
			return widget.getId();
		} else if (PROP_ISDIALOG.equals(id)) {
			return widget.isDialog() == true ? new Integer(0) : new Integer(1);
		}
//		else if(PROP_WIDTH.equals(id))
//			return widget.getWidth() == null? "":widget.getWidth();
//		else if(PROP_HEIGHT.equals(id))
//			return widget.getHeight() == null?"":widget.getHeight();
		else if(PROP_I18NNAME.equals(id))
			return widget.getI18nName() == null?"":widget.getI18nName();
//		else if (PROP_ISMAIN.equals(id)) {
//			return isMainWidget() == true ? new Integer(0) : new Integer(1);
//		}
		return null;
	}

	public void setElementAttribute(Element ele) {
		ele.setAttribute("id", getId());
	}

	public Object getEditableValue() {
		return this;
	}

	public Element createElement(Document doc) {

		return null;
	}

	public LfwWidget getWidget() {
		return widget;
	}

	public void setWidget(LfwWidget widget) {
		this.widget = widget;
	}


//	public boolean isMainWidget() {
//		return isMainWidget;
//	}
//
//	public void setMainWidget(boolean isMainWidget) {
//		this.isMainWidget = isMainWidget;
//	}

	public WidgetElementFigure getFigure() {
		return figure;
	}

	public void setFigure(WidgetElementFigure figure) {
		this.figure = figure;
	}

	
	public WebElement getWebElement() {
		return widget;
	}

	public List<PlugoutDescElementObj> getPlugoutCells() {
		return plugoutCells;
	}

	public void addPlugoutCell(PlugoutDescElementObj cell) {
		plugoutCells.add(cell);
		firePlugChange();
	}

	public void removePlugoutCell(PlugoutDescElementObj cell) {
		plugoutCells.remove(cell);
		for (int i = 0 ; i < plugoutCells.size() ; i++){
			PlugoutDescElementObj plugoutCell = plugoutCells.get(i);
			Point p = new Point();
			p.x = plugoutCell.getLocation().x;
			p.y = plugoutCell.getWidgetObj().getLocation().y + i * 40;
			plugoutCell.setLocation(p);
		}
		firePlugChange();
	}

	public List<PluginDescElementObj> getPluginCells() {
		return pluginCells;
	}
	
	public void addPluginCell(PluginDescElementObj cell) {
		pluginCells.add(cell);
		firePlugChange();
	}
	
	public void removePluginCell(PluginDescElementObj cell) {
		pluginCells.remove(cell);
		for (int i = 0 ; i < pluginCells.size() ; i++){
			PluginDescElementObj pluginCell = pluginCells.get(i);
			Point p = new Point();
			p.x = pluginCell.getLocation().x;
			p.y = pluginCell.getWidgetObj().getLocation().y + i * 40;
			pluginCell.setLocation(p);
		}
		firePlugChange();
	}
	
	private void firePlugChange(){
		LfwBaseGraph graph = getGraph();
		if (graph instanceof WidgetGraph)
			((WidgetGraph)graph).fireWidgetPlugChange(widget);
		else if (graph instanceof PagemetaGraph)
			((PagemetaGraph)graph).fireWidgetPlugChange(widget);
	}
	
//	public String getRefId() {
//		return refId;
//	}
//
//	public void setRefId(String refId) {
//		this.refId = refId;
//	}

}
