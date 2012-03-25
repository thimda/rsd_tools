package nc.lfw.editor.menubar.wizard;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.menubar.DefaultItem;
import nc.lfw.editor.menubar.dialog.MultiDsSelDialog;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.IRefDataset;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.core.event.conf.MouseListener;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.uif.listener.UifMethodAnnotation;
import nc.uap.lfw.perspective.listener.FileUtil;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

public class MenuItemParamPanel extends Canvas {

//	private static final String MANAGE_CARD = "manageCard";
	
	private String superClazz;
	private List<Scrollable> ctrlList = new ArrayList<Scrollable>();

	private List<Combo> widgetComboList = new ArrayList<Combo>();
	private List<Combo> dsComboList = new ArrayList<Combo>();
	private List<Text> multiDsTextList = new ArrayList<Text>();
	private List<Scrollable> otherCompList = new ArrayList<Scrollable>();
	
	private Text changeCardText;
	private Text changeCardItemText;
	
	private Map<String, String> widgetIdMap = new HashMap<String, String>();
	private Map<String, String> dsIdMap = new HashMap<String, String>();
	private Map<String, String> otherParamMap = new HashMap<String, String>();
	
	private String currentWidgetId = "";
	private List<String> currentDsIds = null;
	
	private String cardId = "";
	private String tabId = "";

	private Map<String, Boolean> booleanParamMap = new HashMap<String, Boolean>();
	
	private String annotationCardName = "";
	private String annotationTabName = "";
	public MenuItemParamPanel(Composite parent, int style, String superClazz) {
		super(parent, style);
		this.superClazz = superClazz;
		this.setLayout(new GridLayout(6, false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		this.setLayoutData(gridData);
		this.initUI();
	}
	
	@SuppressWarnings("unchecked")
	private void initUI() {
		try {
			this.setLayout(new GridLayout(9, false));
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			this.setLayoutData(gridData);
			
			Class c = Class.forName(superClazz);
			Method[] ms = c.getDeclaredMethods();
			for (int i = 0; i < ms.length; i++) {
				Method m = ms[i];
				UifMethodAnnotation annotation = m.getAnnotation(UifMethodAnnotation.class);
				if (annotation != null) {
					String name = annotation.name();
					if (annotation.isWidget()) {
						createWidgetPair(name, annotation.isMust());
					} else if (annotation.isDataset()) {
						if (annotation.isMultiSel()) {
							createMultiDataset(name, annotation.isMust());
						} else {
							createDataset(name, annotation.isMust());
						}
					}
//					else if (annotation.isCardLayout()) {
//						createCardText(name, annotation.isMust());
//						annotationCardName = name;
//					} else if (annotation.isTab()) {
//						createTabText(name, annotation.isMust());
//						annotationTabName = name;
//					} 
					else if (annotation.isBoolean()) {
						createBooleanText(name, annotation.booleanValue(), annotation.isMust());
						booleanParamMap.put(name, annotation.booleanValue());
					} else {  // 其他字段
						createOtherComp(name, annotation);
					}
				}
			}
			initDefaultData();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void createDataset(String name, Boolean isMust) {
		
		createMustLabel(isMust);
		
		new Label(this, SWT.NONE).setText(name);
		
		Combo combo = new Combo(this, SWT.READ_ONLY);
		combo.setLayoutData(createGridData(100, 1));
		combo.setData(name);
		
		dsComboList.add(combo);

		if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
			combo.setToolTipText("");
		}
		
	}
	
	private void createMultiDataset(String name, Boolean isMust) {
		
		createMustLabel(isMust);
		
		new Label(this, SWT.NONE).setText(name);
		
		Text text = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		text.setLayoutData(createGridData(120, 1));
		text.setData(name);
		
		multiDsTextList.add(text);

		text.setBackground(new Color(null, 255, 255, 255));
		text.addMouseListener(new MyMouseListener());

		if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
			text.setToolTipText("");
		}
		
	}
	
	/**
	 * 多选DS输入框单击事件，打开选择对话框
	 * 
	 * @author guoweic
	 *
	 */
	private class MyMouseListener implements org.eclipse.swt.events.MouseListener {
		
		
		public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			
		}
		
		public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
			Text text = (Text) e.getSource();
			MultiDsSelDialog dialog = new MultiDsSelDialog(new Shell(),"数据集选择");
			dialog.setDsIds(currentDsIds);
			if (dialog.open() == Dialog.OK) {
				List<String> selectedDsIds = dialog.getSelectedDsIds();
				String ids = "";
				for (String id : selectedDsIds) {
					ids += id + ",";
				}
				if (ids.length() > 0)
					ids = ids.substring(0, ids.length() - 1);
				text.setText(ids);
				text.setToolTipText(ids);
			}
		}
		
		public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
			
		}
	}
	
//	private void createCardText(String name, boolean isMust) {
//
//		createMustLabel(isMust);
//		
//		new Label(this, SWT.NONE).setText(name);
//		
//		Text text = new Text(this, SWT.BORDER);
//		text.setLayoutData(createGridData(100, 1));
//		text.setData("card");
//		ctrlList.add(text);
//
//		if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
//			text.setToolTipText("");
//		}
//	}
	
//	private void createTabText(String name, boolean isMust) {
//
//		createMustLabel(isMust);
//		
//		new Label(this, SWT.NONE).setText(name);
//		
//		Text text = new Text(this, SWT.BORDER);
//		text.setLayoutData(createGridData(100, 1));
//		text.setData("tab");
//		ctrlList.add(text);
//
//		if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
//			text.setToolTipText("");
//		}
//	}
	
	private void createBooleanText(String name, boolean booleanValue, boolean isMust) {
		
		createMustLabel(isMust);
		
		new Label(this, SWT.NONE).setText(name);
		
		Combo combo = new Combo(this, SWT.READ_ONLY);
		
		combo.setLayoutData(createGridData(20, 1));
		combo.setItems(new String[]{"是", "否"});
		combo.setData(name);
		combo.select(booleanValue ? 0 : 1);
		
		ctrlList.add(combo);

		if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
			combo.setToolTipText("");
		}
		
		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.getSource();
				String name = (String) combo.getData();
				String value = combo.getItem(combo.getSelectionIndex());
				booleanParamMap.put(name, "是".equals(value) ? true : false);
			}
		});
		
	}
	
	private void createOtherComp(String name, UifMethodAnnotation annotation) {
		boolean isMust = annotation.isMust();
		String defaultValue = annotation.defaultValue();
		// 是否是页面状态
		boolean isPageState = annotation.isPageState();
		createMustLabel(isMust);
		
		new Label(this, SWT.NONE).setText(name);

//		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		
		if (!isPageState) {
			Text text = new Text(this, SWT.BORDER);
			text.setLayoutData(createGridData(100, 1));
			text.setData(name);
			
			if (null != defaultValue && !"".equals(defaultValue)) {
				if (-1 != defaultValue.indexOf("#PAGE#")) {
					String pmId = LFWPersTool.getCurrentPageMetaTreeItem().getPageId();
					defaultValue = defaultValue.replaceAll("#PAGE#", pmId);
				}
				if (-1 != defaultValue.indexOf("#CPAGE#")) {
					TreeItem[] items = LFWPersTool.getCurrentPageMetaTreeItem().getItems();
					for (TreeItem treeItem : items) {
						if (treeItem instanceof LFWPageMetaTreeItem) {
							String subPmId = ((LFWPageMetaTreeItem)treeItem).getPageId();
							defaultValue = defaultValue.replaceAll("#CPAGE#", subPmId);
							break;
						}
					}
				}
				text.setText(defaultValue);
			}
			
			if (annotation.isChangeCard()) { // 是"切换卡片"
				changeCardText = text;
			} else if (annotation.isChangeCardItem()) { // 是"切换卡片项"
				changeCardItemText = text;
				// 设置只能输入数字
				text.addVerifyListener(new VerifyListener(){
					public void verifyText(VerifyEvent e) {
						String inStr = e.text;
						if (inStr.length() > 0)  // 按退格键时长度为0
							try {
								Integer.parseInt(inStr);
								e.doit = true;
							} catch (Exception ex) {
								e.doit = false;
							}
							
					}
				});
			}
			
			otherCompList.add(text);
	
			if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
				text.setToolTipText("");
			}
		} else {  // 是页面状态
			Combo combo = new Combo(this, SWT.BORDER);
			combo.setLayoutData(createGridData(100, 1));
			combo.setData(name);
			
			if (!isMust) {
				combo.add("请选择");
				combo.setData("请选择", null);
			}
			
//			PageState[] pageStates = pm.getPageStates().getPageStates();
//			for (PageState pageState : pageStates) {
//				combo.add(pageState.getName());
//				combo.setData(pageState.getName(), pageState.getKey());
//			}
			
//			if (!isMust) {
//				if (pageStates.length > 0)
//					combo.select(1);
//				else
//					combo.select(0);
//			} else if (pageStates.length > 0) {
//				combo.select(0);
//			}
			
			otherCompList.add(combo);

			if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
				combo.setToolTipText("");
			}
		}
		
	}
	
	private void createWidgetPair(String name, boolean isMust) {
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		
		createMustLabel(isMust);
		
		Label label = new Label(this, SWT.NONE);
		label.setText(name);
		
		Combo combo = new Combo(this, SWT.READ_ONLY);
		
		combo.setLayoutData(createGridData(50, 1));
		
		LfwWidget[] widgets = pm.getWidgets();
		String[] ids = new String[widgets.length];
		int selectIndex = 0;
		LfwWidget mainWidget = getMainWidget();
		for (int i = 0; i < widgets.length; i++) {
			ids[i] = widgets[i].getId();
			// 获取默认选中项
			if (mainWidget != null && mainWidget.getId().equals(widgets[i].getId()))
				selectIndex = i;
		}
		combo.setItems(ids);
		combo.setData(name);
		if (ids.length > 0) {
			combo.select(selectIndex);
			currentWidgetId = ids[selectIndex];
		}
		widgetComboList.add(combo);

		if (isMust) {  // 必填时，设置提示内容为空字符串，以判断该项是否为必填项，若为null则为不必填；校验时使用
			combo.setToolTipText("");
		}
		
		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Combo wdCombo = (Combo) e.getSource();
				String widgetId = wdCombo.getText();
				currentWidgetId = widgetId;
				PageMeta pm = LFWPersTool.getCurrentPageMeta();
				List<String> dsIds = new ArrayList<String>();
				
				Dataset[] dsArray = pm.getWidget(widgetId).getViewModels().getDatasets();
				String masterDataset = getMasterDataset(pm.getWidget(widgetId));
				int dsSelectIndex = 0;
				for (int i = 0, j = 0, n = dsArray.length; i < n; i++) {
					Dataset dataset = dsArray[i];
					if (!(dataset instanceof IRefDataset)) {
						dsIds.add(dataset.getId());
						// 获取默认选中项
						if (masterDataset.equals(dataset.getId()))
							dsSelectIndex = j;
						j++;
					}
				}
				Combo[] dsComboArray = getDsCombo();
				for (Combo dsCombo : dsComboArray) {
					dsCombo.setItems(dsIds.toArray(new String[0]));
					if (dsIds.toArray(new String[0]).length > 0)
						dsCombo.select(dsSelectIndex);
				}
				currentDsIds = dsIds;
			}
		});
		
	}
	
	private void createMustLabel(boolean isMust) {
		if (isMust) {
			Label label = new Label(this, SWT.NONE);
			label.setText("*");
			label.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		} else
			new Label(this, SWT.NONE).setText(" ");
	}
	
	/**
	 * 获取当前Pagemeta的主Widget（含有Dataset最多的Widget）
	 * @return
	 */
	private LfwWidget getMainWidget() {
		LfwWidget[] widgets = LFWPersTool.getCurrentPageMeta().getWidgets();
		if (widgets.length > 0) {
			LfwWidget widget = widgets[0];
			for (int i = 1, n = widgets.length; i < n; i++) {
				if (widget.getViewModels().getDatasets().length == 0 
						|| widgets[i].getViewModels().getDatasets().length > widget.getViewModels().getDatasets().length) {
					widget = widgets[i];
				}
			}
			return widget;
		}
		return null;
	}
	
	/**
	 * 获取主Dataset的ID
	 * @param widget
	 * @return
	 */
	private String getMasterDataset(LfwWidget widget) {
		if (null != widget.getViewModels().getDsrelations()) {
			DatasetRelation[] relationList = widget.getViewModels().getDsrelations().getDsRelations();
			if (relationList.length > 0) {
				DatasetRelation relation = relationList[0];
				String masterDataset = relation.getMasterDataset();
				return masterDataset;
			}
		}
		return "";
	}
	
	/**
	 * 根据选中的Widget加载初始化数据
	 */
	private void initDefaultData() {
		PageMeta pm = LFWPersTool.getCurrentPageMeta();
		initDsComboDefaultData(pm);
		initMultiDsDefaultData(pm);
		initOtherDefaultData(pm);
	}
	
	/**
	 * 根据选中的Widget加载Dataset下拉列表初始化数据
	 * @param pm
	 */
	private void initDsComboDefaultData(PageMeta pm) {
		String widgetId = currentWidgetId;
		List<String> dsIds = new ArrayList<String>();
		int dsSelectIndex = 0;
		if (widgetId != null && !"".equals(widgetId)) {
			Dataset[] dsArray = pm.getWidget(widgetId).getViewModels().getDatasets();
			String masterDataset = getMasterDataset(pm.getWidget(widgetId));
			int j = 0;
			for (int i = 0, n = dsArray.length; i < n; i++) {
				Dataset dataset = dsArray[i];
				if (!(dataset instanceof IRefDataset)) {
					dsIds.add(dataset.getId());
					// 获取默认选中项
					if (masterDataset.equals(dataset.getId()))
						dsSelectIndex = j;
					j++;
				}
			}
		}
		else {
			LfwWidget[] widgets = pm.getWidgets();
			int j = 0;
			for (LfwWidget widget : widgets) {
				Dataset[] dsArray = widget.getViewModels().getDatasets();
				String masterDataset = getMasterDataset(widget);
				for (int i = 0, n = dsArray.length; i < n; i++) {
					Dataset dataset = dsArray[i];
					if (!(dataset instanceof IRefDataset)) {
						dsIds.add(dataset.getId());
						// 获取默认选中项
						if (masterDataset.equals(dataset.getId()))
							dsSelectIndex = j;
						j++;
					}
				}
			}
		}
		for (Combo dsCombo : dsComboList) {
			dsCombo.setItems(dsIds.toArray(new String[0]));
			if (dsIds.toArray(new String[0]).length > 0)
				dsCombo.select(dsSelectIndex);
		}
	}
	
	/**
	 * 根据选中的Widget加载多选Dataset输入框初始化数据
	 * @param pm
	 */
	private void initMultiDsDefaultData(PageMeta pm) {
		String widgetId = currentWidgetId;
		List<String> dsIds = new ArrayList<String>();
		if (widgetId != null && !"".equals(widgetId)) {
			Dataset[] dsArray = pm.getWidget(widgetId).getViewModels().getDatasets();
			for (Dataset dataset : dsArray) {
				if (!(dataset instanceof IRefDataset)) {
					dsIds.add(dataset.getId());
				}
			}
		}
		else {
			LfwWidget[] widgets = pm.getWidgets();
			for (LfwWidget widget : widgets) {
				Dataset[] dsArray = widget.getViewModels().getDatasets();
				for (Dataset dataset : dsArray) {
					if (!(dataset instanceof IRefDataset)) {
						dsIds.add(dataset.getId());
					}
				}
			}
		}
		currentDsIds = dsIds;
	}
	
	/**
	 * 根据选中的Widget加载其他输入框初始化数据
	 * @param pm
	 */
	private void initOtherDefaultData(PageMeta pm) {
		if (currentWidgetId != null && !"".equals(currentWidgetId)) {
			if (changeCardText != null) { // "切换卡片"
				// 设置默认值
//				ExtAttribute attr = pm.getWidget(currentWidgetId).getExtendAttribute(ExtAttrConstants.BILL_PAGETYPE);
//				if (null != attr) {
//					int pageType = Integer.parseInt((String) attr.getValue());
//					if (checkPageType(pageType)) {
//						changeCardText.setText(MANAGE_CARD);
//					}
//				}
			}
			if (changeCardItemText != null) { // "切换卡片项"
				// 设置默认值
//				ExtAttribute attr = pm.getWidget(currentWidgetId).getExtendAttribute(ExtAttrConstants.BILL_PAGETYPE);
//				if (null != attr) {
//					int pageType = Integer.parseInt((String) attr.getValue());
//					if (checkPageType(pageType)) {
//						changeCardItemText.setText("0");
//					}
//				}
			}
		}
	}
	
	/**
	 * 校验页面类型
	 * @param pageType
	 * @return
	 */
//	private boolean checkPageType(int pageType) {
////		if (pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST
////				|| pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST) {
////			return true;
////		}
//		return false;
//	}
	
	public JsListenerConf generateListener(String sourceFolder, String packageName, String genClazz, DefaultItem defaultItem, String listenerId) {
		String superClazz = defaultItem.getSuperClazz();
		generateListenerCode();
		JsListenerConf listener = new MouseListener();
		listener.setServerClazz(packageName + "." + genClazz);
		listener.setId(listenerId);
		
		EventHandlerConf event = new EventHandlerConf();
		event.setAsync(true);
		event.setName("onclick");
		event.setOnserver(true);
		
		listener.addEventHandler(event);
		
		LfwParameter param = new LfwParameter();
		param.setName("mouseEvent");
		event.addParam(param);
		
		// 生成提交规则
		EventSubmitRule sr = defaultItem.generateSubmitRule(widgetIdMap, dsIdMap);
		if (null != sr)
			event.setSubmitRule(sr);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(widgetIdMap.size() > 0) {
			for (String widgetParamName : widgetIdMap.keySet()) {
				String widgetId = widgetIdMap.get(widgetParamName);
				paramMap.put(widgetParamName, widgetId);
				if (dsIdMap.size() > 0) {
					for (String dsParamName : dsIdMap.keySet()) {
						String dsId = dsIdMap.get(dsParamName);
						paramMap.put(dsParamName, dsId);
					}
				}
			}
		}
		
		paramMap.put(annotationCardName, cardId);
		paramMap.put(annotationTabName, tabId);
		
		String slaveDs = "";
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		if(widget != null){
			Dataset[] dsArray = widget.getViewModels().getDatasets();
			for (Dataset ds : dsArray) {
				if(!(ds instanceof IRefDataset)){
					if(getMasterDataset(widget) != null){
						if (!ds.getId().equals(getMasterDataset(widget))){
							slaveDs = ds.getId();
							break;
						}
					}
				}
			}
		}
		
		paramMap.put("BodyInfo子数据集", slaveDs);
		
		for (String booleanParamName : booleanParamMap.keySet()) {
			paramMap.put(booleanParamName, booleanParamMap.get(booleanParamName));
		}
		
		for (String otherParamName : otherParamMap.keySet()) {
			paramMap.put(otherParamName, otherParamMap.get(otherParamName));
		}
		
		// 保存Java文件
		String javaData = LFWConnector.generatorClass(listener.getServerClazz(), superClazz, paramMap);
		String folderPath = LFWPersTool.getProjectPath() + "/" +  sourceFolder + "/" + listener.getServerClazz().substring(0, listener.getServerClazz().lastIndexOf(genClazz) - 1).replace('.', '/');
		FileUtil.saveToFile(folderPath, genClazz + ".java", javaData);
		
		return listener;
	}
	
	public Combo[] getWidgetCombo() {
		return widgetComboList.toArray(new Combo[0]);
	}
	
	public Combo[] getDsCombo() {
		return dsComboList.toArray(new Combo[0]);
	}
	
	public Text[] getMultiDsText() {
		return multiDsTextList.toArray(new Text[0]);
	}

	public Scrollable[] getOtherComp() {
		return otherCompList.toArray(new Scrollable[0]);
	}
	
	public Text getCardText() {
		Iterator<Scrollable> it = ctrlList.iterator();
		while(it.hasNext()){
			Scrollable comp = it.next();
			if(comp instanceof Text){
				if(comp.getData().equals("card"))
					return (Text) comp;
			}
		}
		return null;
	}
	
	public Text getTabText() {
		Iterator<Scrollable> it = ctrlList.iterator();
		while(it.hasNext()){
			Scrollable comp = it.next();
			if(comp instanceof Text){
				if(comp.getData().equals("tab"))
					return (Text) comp;
			}
		}
		return null;
	}
	
	private void generateListenerCode() {
		
	}

	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public Map<String, String> getDsIdMap() {
		return dsIdMap;
	}

	public Map<String, String> getWidgetIdMap() {
		return widgetIdMap;
	}

	public Map<String, String> getOtherParamMap() {
		return otherParamMap;
	}
	

}
