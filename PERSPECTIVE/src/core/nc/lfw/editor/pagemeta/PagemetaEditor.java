package nc.lfw.editor.pagemeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.Connection;
import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwBaseEditorInput;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.PagemetaEditorInput;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.widget.EditWidgetAction;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.lfw.editor.widget.plug.PluginDescElementObj;
import nc.lfw.editor.widget.plug.PlugoutDescElementObj;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PluginDesc;
import nc.uap.lfw.core.page.PlugoutDesc;
import nc.uap.lfw.core.uimodel.WidgetConfig;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWSaveElementTool;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.editor.view.DeleteViewObjAction;
import nc.uap.lfw.editor.window.WindowViewPage;
import nc.uap.lfw.palette.ChildConnection;
import nc.uap.lfw.palette.PaletteFactory;
import nc.uap.lfw.perspective.action.DeleteWidgetAction;
import nc.uap.lfw.perspective.listener.FileUtil;
import nc.uap.lfw.perspective.listener.JsListenerConstant;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWVirtualDirTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

public class PagemetaEditor extends LFWBaseEditor {

	private PagemetaGraph graph = new PagemetaGraph();
	
	// ����Widget����������ӵ���������������
	private Map<String, Integer> connectionCountMap = new HashMap<String, Integer>();
	
	// ת�۵㴹ֱ����
	private static final int DISTANCE_V = 20;
	// ת�۵�ˮƽ����
	private static final int DISTANCE_H = 30;
//	private PageMetaConfig pagemetaWithWidgets;
	
	/**
	 * �������Widget
	 */
	private List<LfwWidget> tempWidgets = new ArrayList<LfwWidget>();

	public PagemetaGraph getGraph() {
		return graph;
	}

	public void setGraph(PagemetaGraph graph) {
		this.graph = graph;
	}

	public PagemetaEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new PaletteFactory.CustomSelectionTool());
	}
	
	TableViewer tv = null;

	public TableViewer getTv() {
		return tv;
	}

//	public void executComand(Command cmd) {
//		super.getCommandStack().execute(cmd);
//	}

	
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());

	}
	
	/**
	 * ��ȡ��ǰ��PagemetaEditor�����������и÷�����
	 * @return
	 */
	public static PagemetaEditor getActivePagemetaEditor() {
		PagemetaEditor pmEditor = null;
		IWorkbenchPage page = WEBProjPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		if (page != null) {
			editor = page.getActiveEditor();
		}
		if (editor != null && editor instanceof PagemetaEditor) {
			pmEditor = (PagemetaEditor)editor;
		}else{
			LFWPageMetaTreeItem pmTI = LFWAMCPersTool.getCurrentPageMetaTreeItem();
			String id = null;
			if(pmTI != null){
				id = pmTI.getId();
			}
			List<LFWBaseEditor> list = LFWTool.getAllLfwEditors();
			for(LFWBaseEditor be : list){
				if(be instanceof PagemetaEditor){
					PageMeta pm = ((PagemetaEditor)be).getGraph().getPagemeta();
					if(pm != null && id != null && id.equals(pm.getId())){
						pmEditor = (PagemetaEditor)be;
						break;
					}
				}
			}
		}
		return pmEditor;
	}

	
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	private WidgetElementObj wdElementObj = null;
	
//	private PageStateElementObj pageStateObj = null;

	protected void setInput(IEditorInput input) {
		super.setInput(input);

//		PageMetaConfig pagemetaWithWidgets = LFWPersTool.getCurrentPageMeta();
//		//setPagemetaWithWidgets(pagemetaWithWidgets);
//		
//		PagemetaEditorInput pmEditorInput = (PagemetaEditorInput) input;
//		//LFWNodeTreeItem lfwNodeTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
//		//PageMetaConfig pm = lfwNodeTreeItem.getPm();
//		PageMetaConfig pm = pagemetaWithWidgets;
//		PageMetaConfig pm = LFWPersTool.getCurrentPageMeta();
		PageMeta pm = (PageMeta) ((PagemetaEditorInput) input).getCloneElement();
		
		// ��Widget��ID
//		String mainWidgetId = pm.getMasterWidget();
		// ��ʾ����Widget
		WidgetConfig[] configs = pm.getWidgetConfs();
		// Widget��Map��widgetList�е�widget������Ϣ��ȫ��
//		Map<String, LfwWidget> widgetMap = pm.getWidgetMap();
		int pointX = 100;
//		int pointY = -150;
		int pointY = 0;
		int topHeight = 50;
		Map<String, WidgetElementObj> wdObjMap = new HashMap<String, WidgetElementObj>();
		for (int i = 0, n = configs.length; i < n; i++) {
			WidgetElementObj wdObj = new WidgetElementObj();
			//LfwWidgetConf widget = widgetList.get(i);
//			LfwWidget widget = widgetMap.get(widgetList.get(i).getId());
			LfwWidget widget = pm.getWidget(configs[i].getId());
			
//			if (widgetId.equals(mainWidgetId))
//				wdObj.setMainWidget(true);
			wdObj.setWidget(widget);
			// ������ʾλ��
			wdObj.setSize(new Dimension(100, 100));
			if (i % 2 == 0) {
				pointX = 130;
//				pointY += 250;
				pointY = topHeight;
			} else {
				pointX = 580;
			}
			//����topHeight
			int tempHeight = pointY + getWidgetHeight(wdObj.getWidget()) + 50;
			topHeight = topHeight < tempHeight ? tempHeight : topHeight;    
			Point point = new Point(pointX, pointY);
			wdObj.setLocation(point);
			graph.addWidgetCell(wdObj);
			wdObjMap.put(widget.getId(), wdObj);
			//��ʾplugoutDesc��pluginDesc
			if (widget.getPlugoutDescs() != null){
				for (PlugoutDesc p: widget.getPlugoutDescs()){
					PlugoutDescElementObj plugoutObj = new PlugoutDescElementObj();
					plugoutObj.setPlugout(p);
					plugoutObj.setId(p.getId());
					Point outpoint = new Point();
					int count =wdObj.getPlugoutCells().size();
					outpoint.x = wdObj.getLocation().x + wdObj.getSize().width + 50;
					outpoint.y = wdObj.getLocation().y + count * 40;
					ChildConnection conn = new ChildConnection(wdObj,plugoutObj);
					conn.connect();
					plugoutObj.setConn(conn);
					plugoutObj.setLocation(outpoint);
					plugoutObj.setSize(new Dimension(100, 30));
					plugoutObj.setWidgetObj(wdObj);
					wdObj.addPlugoutCell(plugoutObj);
				}
			}
			if (widget.getPluginDescs() != null){
				for (PluginDesc p: widget.getPluginDescs()){
					PluginDescElementObj pluginObj = new PluginDescElementObj();
					pluginObj.setPlugin(p);
					pluginObj.setId(p.getId());
					Point outpoint = new Point();
					int count =wdObj.getPluginCells().size();
					outpoint.x = wdObj.getLocation().x +  -100 - 50;
					outpoint.y = wdObj.getLocation().y + count * 40;
					ChildConnection conn = new ChildConnection(wdObj,pluginObj);
					conn.connect();
					pluginObj.setConn(conn);
					pluginObj.setLocation(outpoint);
					pluginObj.setSize(new Dimension(100, 30));
					pluginObj.setWidgetObj(wdObj);
					wdObj.addPluginCell(pluginObj);
				}
			}
			
		}
		graph.setPagemeta(pm);
		String pageModel = LFWPersTool.getPageModel();
		if(pageModel != null && !pageModel.equals(""))
			graph.setPagemodel(pageModel);
		// ����Connector������
		Map<String, Connector> connectorMap = pm.getConnectorMap();
		Iterator<String> it = connectorMap.keySet().iterator();
		while (it.hasNext()) {
			Connector connector = connectorMap.get(it.next());
			String source = connector.getSource();
			String target = connector.getTarget();
			String plugoutId = connector.getPlugoutId();
			String pluginId = connector.getPluginId();
			
//			WidgetConnector con = new WidgetConnector(wdObjMap.get(source), wdObjMap.get(target));
//			con.setId(connector.getId());
//			con.setConnector(connector);
//			con.connect();
			
			WidgetElementObj sourceObj = wdObjMap.get(source);
			WidgetElementObj targetObj = wdObjMap.get(target);
			if (sourceObj == null || targetObj == null)
				continue;
			PlugoutDescElementObj plugoutDesc = null;
			PluginDescElementObj pluginDesc = null;
			for (PlugoutDescElementObj plugout : sourceObj.getPlugoutCells()){
				if (plugoutId.equals(plugout.getPlugout().getId())){
					plugoutDesc = plugout;
					break;
				}
			}
			for (PluginDescElementObj plugin : targetObj.getPluginCells()){
				if (pluginId.equals(plugin.getPlugin().getId())){
					pluginDesc = plugin;
					break;
				}
			}
			if (plugoutDesc == null || pluginDesc == null)
					return;
			WidgetConnector con = new WidgetConnector(plugoutDesc, pluginDesc);
			con.setId(connector.getId());
			con.setConnector(connector);
			con.connect();
			
			graph.addConns(con);
			
			Point point = getCenterBendPoint(con);
			con.addBendPoint(0, point);
			
		}
		
//		// ��ʾҳ��״̬
//		if (null == pm.getPageStates())
//			pm.setPageStates(new PageStates());
//		addPageStateCellToEidtor(pm.getPageStates(), graph);

		// ��ʾListener
		addListenerCellToEditor(pm.getListenerMap(), graph);
	}
	
	/**
	 * ������������ʾλ��
	 * @param connections
	 */
	public void adjustConnections(List<Connection> connections) {
		for (int i = 0, n = connections.size(); i < n; i++) {
			WidgetConnector con = (WidgetConnector) connections.get(i);
			adjustConnector(con);
		}
	}

	/**
	 * ������������ʾλ��
	 * @param connections
	 */
	public void adjustConnector(WidgetConnector con) {
		Point point = getCenterBendPoint(con);
		con.addBendPoint(0, point);
	}
	
	/**
	 * ����ת�۵�
	 * @param con
	 * @return
	 */
	public Point getCenterBendPoint(WidgetConnector con) {

		// ������������ֹλ��
		PlugoutDescElementObj sourceObj = (PlugoutDescElementObj) con.getSource();
		PluginDescElementObj targetObj = (PluginDescElementObj) con.getTarget();
		Point startObjPoint = sourceObj.getLocation();
		Point endObjPoint = targetObj.getLocation();
		Dimension size = sourceObj.getSize();
		int objHeight = size.height;
		int objWidth = size.width;
		
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		if (startObjPoint.x < endObjPoint.x) {
			startX = startObjPoint.x + objWidth/2;
			endX = endObjPoint.x + objWidth/2;
		} else {
			startX = endObjPoint.x + objWidth/2;
			endX = startObjPoint.x + objWidth/2;
		}
		if (startObjPoint.y < endObjPoint.y) {
			startY = startObjPoint.y + objHeight/2;
			endY = endObjPoint.y + objHeight/2;
		} else {
			startY = endObjPoint.y + objHeight/2;;
			endY = startObjPoint.y + objHeight/2;;
		}
		
		// ����connectionCountMap��Keyֵ
		String key1 = sourceObj.getPlugout().getId() + "_" + targetObj.getPlugin().getId();
		String key2 = targetObj.getPlugin().getId() + "_" + sourceObj.getPlugout().getId();
		
		// ��ȡ��ǰΪ����Widget֮��ĵڼ���������
		int index = 0;
		if (connectionCountMap.containsKey(key1)) {
			index = connectionCountMap.get(key1);
			connectionCountMap.put(key1, ++index);
		} else if (connectionCountMap.containsKey(key2)) {
			index = connectionCountMap.get(key2);
			connectionCountMap.put(key2, ++index);
		} else {
			connectionCountMap.put(key1, index);
		}
		
		// �������յ�����
		int resultX = (startX + endX) / 2;
		int resultY = (startY + endY) / 2;
		
		if (index != 0) {
			if (startX != endX) {  // ����Widget���Ǵ�ֱλ�ã�ת�۵������ƶ�
				if (index % 2 != 0) {  // ������������
					resultY -= DISTANCE_V * ((index / 2) + 1);
				} else {  // ż����������
					resultY += DISTANCE_V * (index / 2);
				}
			} else {
				if (index % 2 != 0) {  // ������������
					resultX -= DISTANCE_H * ((index / 2) + 1);
				} else {  // ż����������
					resultX += DISTANCE_H * (index / 2);
				}
			}
		}
		
		return new Point(resultX, resultY);
	}
	
	/**
	 * ���»���ҳ��
	 */
	public void repaintGraph() {
		// ɾ������Widgetͼ���Connector����
		List<Connection> conns = graph.getConnections();
//		for (Connection connection : conns) {
		while (conns.size() > 0) 
			graph.removeConn(conns.get(0));
		List<WidgetElementObj> widgetCells = graph.getWidgetCells();
//		for (WidgetElementObj widgetObj : widgetCells) {
		while (widgetCells.size() > 0)
			graph.removeWidgetCell(widgetCells.get(0));
		
		PageMeta pm = graph.getPagemeta();
		
		// ��ʾ����Widget
		WidgetConfig[] configs = pm.getWidgetConfs();
		// Widget��Map��widgetList�е�widget������Ϣ��ȫ��
//		Map<String, LfwWidget> widgetMap = pm.getWidgetMap();
		int pointX = 100;
//		int pointY = -100;
		int pointY = -150;
		Map<String, WidgetElementObj> wdObjMap = new HashMap<String, WidgetElementObj>();
		for (int i = 0, n = configs.length; i < n; i++) {
			WidgetElementObj wdObj = new WidgetElementObj();
//			LfwWidget widget = widgetMap.get(widgetList.get(i).getId());
			LfwWidget widget = pm.getWidget(configs[i].getId());
//			if(widget == null){
//				widget = configs[i];
//			}
			wdObj.setWidget(widget);
			// ������ʾλ��
			wdObj.setSize(new Dimension(100, 100));
			if (i % 2 == 0) {
				pointX = 100;
//				pointY += 250;
				pointY += 200;
			} else {
				pointX = 400;
			}
			Point point = new Point(pointX, pointY);
			wdObj.setLocation(point);
			graph.addWidgetCell(wdObj);
			wdObjMap.put(widget.getId(), wdObj);
		}
		graph.setPagemeta(pm);
		// ����Connector������
		Map<String, Connector> connectorMap = pm.getConnectorMap();
		Iterator<String> it = connectorMap.keySet().iterator();
		while (it.hasNext()) {
			Connector connector = connectorMap.get(it.next());
			String source = connector.getSource();
			String target = connector.getTarget();
			WidgetConnector con = new WidgetConnector(wdObjMap.get(source), wdObjMap.get(target));
			con.setId(connector.getId());
			con.setConnector(connector);
			con.connect();
			graph.addConns(con);
		}
		
		// ��������ҳ��״̬ͼ����ʾλ��
//		adjustPageStateCell(graph);
		
		// ����Listener����λ��
		repaintListenerPositon();
		
	}
	
	/**
	 * ˢ�´򿪵�Pagemeta�༭��
	 */
	@SuppressWarnings("deprecation")
	public static void refreshPagemetaEditor() {
		IWorkbenchPage workbenchPage = LFWExplorerTreeView.getLFWExploerTreeView(null).getViewSite().getPage();
		IEditorPart editor = null;
		PageMeta currentPagemeta = LFWPersTool.getCurrentPageMeta();
		
		IEditorPart[] parts = workbenchPage.getEditors();
		for (int i = 0; i < parts.length; i++) {
			if (parts[i] instanceof PagemetaEditor) {
				PagemetaEditor pmEditor = (PagemetaEditor)parts[i];
				PageMeta pmnew = ((PagemetaEditorInput)pmEditor.getEditorInput()).getPagemeta();
				if (currentPagemeta.getId().equals(pmnew.getId())) {
					editor =  parts[i];
					break;
				}
			}
		}
		if (editor != null){
			((PagemetaEditor) editor).getGraph().setPagemeta(currentPagemeta);
			((PagemetaEditor) editor).repaintGraph();
		}
	}
	
//	/**
//	 * ����ҳ��״̬ͼ��
//	 * @author guoweic
//	 * @param pageStateMap
//	 * @param graph
//	 */
//	public void addPageStateCellToEidtor(PageStates pageStates, PagemetaGraph graph) {
//		pageStateObj = new PageStateElementObj();
//		pageStateObj.setPageStates(pageStates);
//
//		// ���ó�ʼ��С
//		pageStateObj.setSize(new Dimension(100, 100));
//		// ������ʾλ��
//		setPageStateCellPosition(graph);
//		// ��ʾ������
//		graph.addPageStateCell(pageStateObj);
//		
//	}
	
	/**
	 * ����ҳ��״̬ͼ����ʾλ��
	 * @param graph
	public void setPageStateCellPosition(PagemetaGraph graph) {
		// ������ʾλ��
		int pointX = 100;
		//int pointY = ((graph.elementsCount + 1) / 2) * 250 + 150;
		int pointY = ((graph.elementsCount + 1) / 2) * 200 + 50;
		pageStateObj.setSize(new Dimension(100, 100));
		Point point = new Point(pointX, pointY);
		pageStateObj.setLocation(point);
	}
	 */
	
//	/**
//	 * ����ҳ��״̬ͼ����ʾλ��
//	 * @param graph
//	 */
//	public void setPageStateCellPosition(PagemetaGraph graph) {
//		int realNum = graph.elementsCount;
//		int num = realNum + 2;
//		int pointY = num / 2 * 200 - 150;
//		Point point = new Point(400, pointY);
//		if (num % 2 == 0)
//			point.x = 100;
////		pageStateObj.setSize(new Dimension(100, 100));
////		pageStateObj.setLocation(point);
//		
//	}
	
	/**
	 * ���Ӵ�����Widget
	 * @param widget
	 */
	public void addTempWidget(LfwWidget widget) {
		String id = widget.getId();
		for (int i = 0, n = tempWidgets.size(); i < n; i++) {
			if (id.equals(tempWidgets.get(i).getId())) {
				tempWidgets.remove(i);
				break;
			}
		}
		tempWidgets.add(widget);
	}

	/**
	 * ɾ��������Widget
	 * @param widget
	 */
	public void removeTempWidget(LfwWidget widget) {
		String id = widget.getId();
		for (int i = 0, n = tempWidgets.size(); i < n; i++) {
			if (id.equals(tempWidgets.get(i).getId())) {
				tempWidgets.remove(i);
				break;
			}
		}
	}
	
	/**
	 * �����½��ĺ͸Ķ�����Widget���ļ���
	 * @param widget
	 */
	public void saveWidgets() {
		for (int i = 0, n = tempWidgets.size(); i < n; i++) {
			saveWidget(tempWidgets.get(i));
		}
	}
	

	
	/**
	 * ����Widget���ļ���
	 * @param widget
	 */
	private void saveWidget(LfwWidget widget) {
		//PagemetaEditorInput input = (PagemetaEditorInput) getEditorInput();
		// ��ȡ��Ŀ·��
		String projectPath = LFWPersTool.getProjectPath();
		//LFWNodeTreeItem lfwNodeTreeItem = input.getTreeItem();
		LFWPageMetaTreeItem pageMetaTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
		String pagemetaNodePath = pageMetaTreeItem.getFile().getPath();
		// ƴ��widget.wd�ļ�ȫ·��
		String filePath = pagemetaNodePath + "\\" + widget.getId();
		// ����Widget��widget.wd��
		NCConnector.saveWidgettoXml(filePath, "widget.wd", projectPath, widget, null);
	}

	/**
	 * ����Pagemeta���ļ���
	 * @param widget
	 */
	public void savePagemeta(PageMeta pagemeta) {
		// ��ȡ��Ŀ·��
		String projectPath = LFWPersTool.getProjectPath();
		LFWPageMetaTreeItem pageMetaTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
		String pagemetaNodePath = pageMetaTreeItem.getFile().getPath();
		// ����Widget��pagemeta.pm��
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			LFWSaveElementTool.updateWindow(pagemeta);
		}else{
			LFWConnector.savePagemetaToXml(pagemetaNodePath, "pagemeta.pm", projectPath, pagemeta);
		}
	}

	
	public void saveJsListener(String jsListenerId, EventHandlerConf jsEventHandler, JsListenerConf listener) {
		PageMeta pagemeta = graph.getPagemeta();
		if (null != jsEventHandler) {
			if (pagemeta.getListenerMap().containsKey(jsListenerId)) {
				JsListenerConf jsListener = pagemeta.getListenerMap().get(jsListenerId);
				doSaveListenerEvent(jsListener, jsEventHandler);
			}
		} else {
			pagemeta.addListener(listener);
		}
		setDirtyTrue();
	}
	
	
	public void removeJsListener(JsListenerConf jsListener) {
		String jsListenerId = jsListener.getId();
		PageMeta pagemeta = graph.getPagemeta();
		if (pagemeta.getListenerMap().containsKey(jsListenerId)) {
			pagemeta.getListenerMap().remove(jsListenerId);
		}
		setDirtyTrue();
	}
	
	public void saveJsEvent(EventConf eventConf){
		PageMeta pageMeta = graph.getPagemeta();
		if(eventConf != null){
			EventConf[] eventConfs = pageMeta.getEventConfs();
			if(eventConfs != null && eventConfs.length > 0){
				for(EventConf event : eventConfs){
					if(event.getMethodName().equals(eventConf.getMethodName())){
						event.setScript(eventConf.getScript());
					}
				}
			}
		}
		setDirtyTrue();
	}

//	public File getFile() {
//		IEditorInput input = getEditorInput();
//		File file = null;
//		if (input instanceof FileEditorInput) {
//			FileEditorInput fei = (FileEditorInput) input;
//			file = fei.getPath().toFile();
//		}
//		return file;
//	}
	
	public WidgetElementObj getModel() {
		return this.wdElementObj;
	}
	
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getGraphicalViewer().setContents(this.graph);
		getGraphicalViewer().addDropTargetListener(
				new nc.uap.lfw.perspective.editor.DiagramTemplateTransferDropTargetListener(
						getGraphicalViewer()));
		LFWPersTool.showView(IPageLayout.ID_PROP_SHEET);
	}
	
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}

		};
	}
	
	private KeyHandler shareKeyHandler = null;

	private KeyHandler getShareKeyHandler() {
		if (shareKeyHandler == null) {
			shareKeyHandler = new KeyHandler();
			getActionRegistry().registerAction(new DeleteViewObjAction());
			shareKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
							getActionRegistry().getAction(ActionFactory.DELETE.getId()));
		}
		return shareKeyHandler;
	}
	
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScalableFreeformRootEditPart rootEditpart = new ScalableFreeformRootEditPart();
		getGraphicalViewer().setRootEditPart(rootEditpart);
		getGraphicalViewer().setEditPartFactory(new PagemetaEditPartFactory(this));
		getGraphicalViewer().setKeyHandler(getShareKeyHandler());
		getGraphicalViewer().setContextMenu(getMenuManager());
		
	}
	
	/**
	 * ���ӱ༭���Զ���˵�
	 * @param manager
	 */
	protected void editMenuManager(IMenuManager manager) {
		if (null != getCurrentSelection()) {
			StructuredSelection ss = (StructuredSelection) getCurrentSelection();
			Object sel = ss.getFirstElement();
			if (sel instanceof PagemetaElementPart) {
				PagemetaElementPart lfwEle = (PagemetaElementPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof WidgetElementObj) {
					WidgetElementObj widgetObj = (WidgetElementObj) model;
					// ���� �༭Widget�˵���
					EditWidgetAction editWidgetAction = new EditWidgetAction();
					// ����Widget�༭������
//					PagemetaEditorInput input = (PagemetaEditorInput) getEditorInput();
					//LFWNodeTreeItem lfwNodeTreeItem = input.getTreeItem();
					LFWPageMetaTreeItem lfwNodeTreeItem = LFWPersTool.getCurrentPageMetaTreeItem();
					LFWWidgetTreeItem widgetItem = null;
					TreeItem[] childItems = lfwNodeTreeItem.getItems();
					for (int i = 0, n = childItems.length; i < n; i++) {
						if (childItems[i] instanceof LFWWidgetTreeItem) {
							if (widgetObj.getWidget().getId().equals(((LFWWidgetTreeItem)childItems[i]).getWidget().getId())) {
								widgetItem = (LFWWidgetTreeItem)childItems[i];
								break;
							}
						}
					}
					//editWidgetAction.setTreeView(input.getTreeView());
					editWidgetAction.setTreeItem(widgetItem);
					
					//editWidgetAction.setProjectPath(input.getProjectPath());
					//editWidgetAction.setWidget(widgetItem.getWidget());
//					editWidgetAction.setWidget(graph.getPagemeta().getWidget(widgetItem.getWidget().getId()));
					if (null != widgetItem) {
						//TODO ȥ���༭Widget����
//						manager.add(editWidgetAction);
					}
					
					// ���� ɾ��Widget�˵���
					if (null != widgetItem) {
						DeleteWidgetAction delWidgetAction = new DeleteWidgetAction();
						delWidgetAction.setSelectedWidget(widgetItem.getWidget());
						delWidgetAction.setInPagemetaEditor(true);
						manager.add(delWidgetAction);
					}
					
					
//					// ���� �½�Signal�˵���
//					manager.add(new NewSignalAction(widgetObj));
//					// ���� �½�Slot�˵���
//					manager.add(new NewSlotAction(widgetObj));
					// ��ȡ����ѡ�е�����
					Label label = widgetObj.getFigure().getCurrentLabel();
					if (null != label) {
//						if (label instanceof SignalLabel) {
//							LfwSignal signal = (LfwSignal) ((SignalLabel) label).getEditableObj();
//							// ���� �༭ѡ������˵�
////							manager.add(new EditWidgetItemAction(label, widgetObj, signal.getId()));
//							// ���� ɾ��ѡ������˵�
//							manager.add(new DeleteWidgetItemAction(label, widgetObj, signal.getId()));
//						} else if (label instanceof SlotLabel) {
//							LfwSlot slot = (LfwSlot) ((SlotLabel) label).getEditableObj();
//							// ���� �༭ѡ������˵�
////							manager.add(new EditWidgetItemAction(label, widgetObj, slot.getId()));
//							// ���� ɾ��ѡ������˵�
//							manager.add(new DeleteWidgetItemAction(label, widgetObj, slot.getId()));
//						}
					}
				}
//				else if (model instanceof PageStateElementObj) {
//					PageStateElementObj pageStateObj = (PageStateElementObj) model;
//					
////					// ���� �½�ҳ��״̬�˵���
////					manager.add(new NewPageStateAction(pageStateObj));
////					// ��ȡ����ѡ�е�����
////					Label label = pageStateObj.getFigure().getCurrentLabel();
////					if (null != label) {
////						if (label instanceof PageStateLabel) {
////							PageState pageState = (PageState) ((PageStateLabel) label).getEditableObj();
////							// ���� ɾ��ѡ������˵�
////							manager.add(new DeletePageStateAction(label, pageStateObj, pageState.getKey()));
////						}
////					}
//				}
			} 
			else {
				return;
			}
		} else {
			return;
		}
	}
	
	/**
	 * �Ҽ��˵�
	 * @return
	protected MenuManager getMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				manager.removeAll();
				if (null != getCurrentSelection()) {
					StructuredSelection ss = (StructuredSelection) getCurrentSelection();
					Object sel = ss.getFirstElement();
					if (sel instanceof PagemetaElementPart) {
						PagemetaElementPart lfwEle = (PagemetaElementPart) sel;
						Object model = lfwEle.getModel();
						if (model instanceof WidgetElementObj) {
							WidgetElementObj widgetObj = (WidgetElementObj) model;
							// ���� �½�Signal�˵���
							manager.add(new NewSignalAction(widgetObj));
							// ���� �½�Slot�˵���
							manager.add(new NewSlotAction(widgetObj));
							// ��ȡ����ѡ�е�����
							Label label = widgetObj.getFigure().getCurrentLabel();
							if (null != label) {
								if (label instanceof SignalLabel) {
									LfwSignal signal = (LfwSignal) ((SignalLabel) label).getEditableObj();
									// ���� �༭ѡ������˵�
									manager.add(new EditWidgetItemAction(label, widgetObj, signal.getId()));
									// ���� ɾ��ѡ������˵�
									manager.add(new DeleteWidgetItemAction(label, widgetObj, signal.getId()));
								} else if (label instanceof SlotLabel) {
									LfwSlot slot = (LfwSlot) ((SlotLabel) label).getEditableObj();
									// ���� �༭ѡ������˵�
									manager.add(new EditWidgetItemAction(label, widgetObj, slot.getId()));
									// ���� ɾ��ѡ������˵�
									manager.add(new DeleteWidgetItemAction(label, widgetObj, slot.getId()));
								}
							}
						} else if (model instanceof ListenerElementObj) {
							ListenerElementObj listenerObj = (ListenerElementObj) model;
							// ���� �½�Listener�˵���
							NewJsListenerAction jsListenerAction = new NewJsListenerAction(listenerObj);
							// ����Ŀ���������
							jsListenerAction.setTargetType(JsListenerConstant.TARGET_PAGEMETA);
							manager.add(jsListenerAction);
							// ��ȡ����ѡ�е�����
							Label label = listenerObj.getFigure().getCurrentLabel();
							if (null != label) {
								if (label instanceof JsListenerLabel) {
									JsListener jsListener = (JsListener) ((JsListenerLabel) label).getEditableObj();
									// ���� �༭ѡ������˵�
									manager.add(new EditJsListenerAction(label, listenerObj, jsListener.getId()));
									// ���� ɾ��ѡ������˵�
									manager.add(new DeleteJsListenerAction(label, listenerObj, jsListener.getId()));
								}
							}
							
						}
					} else {
						return;
					}
				} else {
					return;
				}
			}
		});
		return menuManager;
	}
	 */

	
//	/**
//	 * ����ҳ��״̬ͼ����ʾλ��
//	 * @param graph
//	 */
//	public void adjustPageStateCell(PagemetaGraph graph) {
////		// ����graph.addCell(jsListenerObj);ʱ�����һ��������ʱ��ȥ
////		graph.elementsCount--;
//		PageStateElementObj pageStateObj = PagemetaEditor.getActivePagemetaEditor().getPageStateObj();
//		graph.removePageStateCell(pageStateObj);
//		setPageStateCellPosition(graph, pageStateObj);
//		// �Ѽ�ȥ���Ǹ����ӻ���
////		graph.elementsCount++;
//		graph.addPageStateCell(pageStateObj);
//	}
	
	/**
	 * ����ҳ��״̬ͼ����ʾλ��
	 * @param graph
	 * @param pageStateObj
	public void setPageStateCellPosition(PagemetaGraph graph, PageStateElementObj pageStateObj) {
		// ������ʾλ��
		int pointX = 100;
		//int pointY = ((graph.elementsCount + 1) / 2) * 250 + 150;
		int pointY = ((graph.elementsCount - 1) / 2) * 200 + 50;
		pageStateObj.setSize(new Dimension(100, 100));
		Point point = new Point(pointX, pointY);
		pageStateObj.setLocation(point);
	}
	 */
	
	/**
	 * ����ҳ��״̬ͼ����ʾλ��
	 * @param graph
	 * @param pageStateObj
	 */
//	public void setPageStateCellPosition(PagemetaGraph graph, PageStateElementObj pageStateObj) {
//		// ������ʾλ��
//		int realNum = graph.elementsCount;
//		int num = realNum;
//		int pointY = num / 2 * 200 - 150;
//		Point point = new Point(400, pointY);
//		if (num % 2 == 0)
//			point.x = 100;
//		pageStateObj.setSize(new Dimension(100, 100));
//		pageStateObj.setLocation(point);
//		
//	}
	
	/**
	 * ˢ�����иýڵ�
	 */
	public void refreshTreeNode() {
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		if(view == null)
			return;
		RefreshNodeAction.refreshNode(view, view.getTreeView().getTree());
	}

	private PaletteRoot paleteRoot = null;

	protected PaletteRoot getPaletteRoot() {
		if (paleteRoot == null) {
			paleteRoot = PaletteFactory.createPagemetaPalette();
		}
		return paleteRoot;
	}
	
	
	public void doSave(IProgressMonitor monitor) {
		// ����Event����
		super.doSave(monitor);
		save();
	}
	
	public void save() {
//		saveEventScript();
		PageMeta pagemeta = graph.getPagemeta();
		savePagemeta(pagemeta);
		saveWidgets();
		String folder = LFWPersTool.getCurrentFolderPath();
		String fileName = "node.properties";
		if(graph.getPagemodel() != null && !graph.getPagemodel().equals("")){
			String text = "model="+graph.getPagemodel();
			FileUtil.saveToFile(folder, fileName, text);
			LFWPersTool.refreshCurrentPorject();
		}
		refreshTreeNode();
	}

	
	public LFWAbstractViewPage createViewPage() {
		TreeItem treeItem = LFWAMCPersTool.getCurrentTreeItem();
		if(treeItem instanceof LFWPageMetaTreeItem){
			LFWPageMetaTreeItem pmTreeItem = (LFWPageMetaTreeItem)treeItem;
			if(LFWTool.OLD_VERSION.equals(pmTreeItem.getLfwVersion())){
				return new PagemetaViewPage();
			}else{
				return new WindowViewPage();
			}
		}else{
			return new PagemetaViewPage();
		}
	}
	
	public List<Class<? extends JsListenerConf>> getEditorAcceptListeners() {
		return JsListenerConstant.getJsListenerClassMap().get(JsListenerConstant.TARGET_PAGEMETA);
	}
//
//	public PageStateElementObj getPageStateObj() {
//		return pageStateObj;
//	}

	public String getPath() {
		return null;
	}

	protected LfwElementObjWithGraph getLeftElement() {
		return null;
	}

	protected LfwElementObjWithGraph getTopElement() {
		List<WidgetElementObj> widgetCells = getGraph().getWidgetCells();
//		if (widgetCells.size() % 2 == 0)  // ż����������PageState����
//			return getGraph().getPageStateCells().get(0);
//		else  // ���������������һ��Widget����
		if(widgetCells != null && widgetCells.size() > 0){
			return widgetCells.get(widgetCells.size() - 1);
		}else{
			return null;
		}
	}
	
	@Override
	public List<JsEventDesc> getAcceptEventDescs() {
		return graph.getPagemeta().getAcceptEventDescs();
	}
	
	private int getWidgetHeight(LfwWidget widget){
		int pluginNum = widget.getPluginDescs() == null ? 0 : widget.getPluginDescs().size();
		int plugoutNum = widget.getPlugoutDescs() == null ? 0 : widget.getPlugoutDescs().size();
		int maxNum = pluginNum > plugoutNum ? pluginNum : plugoutNum;    
		int plugHeight =  30 + (maxNum - 1) * 70;
		return plugHeight > 100 ? plugHeight : 100; 
	}
	
	@Override
	public void setFocus() {
		super.setFocus();
		IEditorInput input = getEditorInput();
		if (input instanceof PagemetaEditorInput) {
			PagemetaEditorInput editorInput = (PagemetaEditorInput)input;
			try{
				TreeItem selectedTI = editorInput.getCurrentTreeItem();
				if(selectedTI == null || selectedTI.isDisposed()){
					selectedTI = getSelectedTreeItem(WEBProjConstants.WINDOW);
				}
				if(selectedTI != null && !selectedTI.isDisposed()){
					LFWAMCPersTool.getTree().setSelection(selectedTI);
					editorInput.setCurrentTreeItem(selectedTI);
				}else{
					MessageDialog.openError(null, "��ʾ", "��ǰ�༭����ʧЧ,��رղ����´�!");
				}
			}catch(Exception e){
				MainPlugin.getDefault().logError(e);
			}
		}
	}
	
	@Override
	protected TreeItem getSelectedTreeItem(LFWDirtoryTreeItem dirTreeItem,
			LfwBaseEditorInput editorInput) {
		if(dirTreeItem != null && editorInput instanceof PagemetaEditorInput){
			PagemetaEditorInput pagemetaEI = (PagemetaEditorInput)editorInput;
			String pageMetaId = pagemetaEI.getPagemeta().getId();
			if(pageMetaId == null){
				return null;
			}
			return getSelectedTreeItem(pageMetaId, dirTreeItem);
		}
		return null;
	}
	
	private TreeItem getSelectedTreeItem(String id, TreeItem parent){
		if(id != null && parent != null){
			TreeItem[] children = parent.getItems();
			if(children != null && children.length > 0){
				for(TreeItem child : children){
					if(child instanceof LFWPageMetaTreeItem){
						if(id.equals(((LFWPageMetaTreeItem) child).getId())){
							return child;
						}
					}else if(child instanceof LFWVirtualDirTreeItem){
						TreeItem item = getSelectedTreeItem(id, child);
						if(item != null){
							return item;
						}
					}
				}
			}
		}
		return null;
	}
	
}
