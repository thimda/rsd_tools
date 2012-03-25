package nc.uap.lfw.perspective.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.comp.GridComp;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Parameter;
import nc.uap.lfw.core.data.RefDataset;
import nc.uap.lfw.core.data.RefMdDataset;
import nc.uap.lfw.core.data.RefPubDataset;
import nc.uap.lfw.core.event.conf.DatasetRule;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.FormRule;
import nc.uap.lfw.core.event.conf.GridRule;
import nc.uap.lfw.core.event.conf.TreeRule;
import nc.uap.lfw.core.event.conf.WidgetRule;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

/**
 * �ύ����Ի�������
 * 
 * @author guoweic
 *
 */
public class SubmitRulePanel extends Canvas {
	
	private PageMeta pagemeta = null;
	
	private EventSubmitRule submitRule = null;
	
	private EventSubmitRule oldSubmitRule = null;
	
	// �Ƿ��Ǹ�ҳ���ύ����
	private boolean isParentSubmitRule = false;
	
	// ��ҳ���ύ����
	private EventSubmitRule parentSubmitRule;
	
	/**
	 * ��ɾ����Widget�ύ����
	 */
	private Map<String, WidgetRule> tempWidgetRuleMap = new HashMap<String, WidgetRule>();
	
//	private Group centerRightTop;
//	private Group centerRightCenter;
//	private Group centerRightOther;
	private Group topRight;
	
	private StackLayout stackLayout = new StackLayout();
	private Composite centerRight;
	
	private Map<String, Map<String, Composite>> widgetAreaMap = new HashMap<String, Map<String, Composite>>();

	private static final String WD_MAIN_AREA = "main";
	private static final String WD_SELECT_AREA = "select";
	private static final String WD_DS_AREA = "centerRightTop";
	private static final String WD_BINDING_AREA = "centerRightCenter";
	private static final String WD_OTHER_AREA = "centerRightOther";
		
	private Table paramTable;
	private Button addBtn;
	private Button delBtn;
	// ��ҳ���ύ������������
	Composite parentSubmitRuleArea;
	// ��ҳ��ѡ���
	Combo parentPagemetaCombo;
	
	/**
	 * Dataset�ύ�����б�
	 */
	private List<SubmitType> dsSubmitTypeList = new ArrayList<SubmitType>();
	
	/**
	 * Tree�ύ�����б�
	 */
	private List<SubmitType> treeSubmitTypeList = new ArrayList<SubmitType>();
	
	/**
	 * Grid�ύ�����б�
	 */
	private List<SubmitType> gridSubmitTypeList = new ArrayList<SubmitType>();
	
	/**
	 * form�ύ����
	 */
	private List<SubmitType> fromSubmitTypeList = new ArrayList<SubmitType>();
	
	/**
	 * �ύ���Ͷ�������Combo��
	 * @author guoweic
	 *
	 */
	private class SubmitType {
		// ��ʾ����
		private String text = "";
		// ����
		private String type = "";
		
		public SubmitType(String text, String type) {
			this.text = text;
			this.type = type;
		}
		
		public String getText() {
			return text;
		}
		
		public String getType() {
			return type;
		}
	}
	
	public SubmitRulePanel(Composite parent, int style, PageMeta pagemeta, EventSubmitRule submitRule, boolean isParentSubmitRule) {
		super(parent, style);
		this.pagemeta = pagemeta;
		this.oldSubmitRule = submitRule;
		this.isParentSubmitRule = isParentSubmitRule;
		initUI();
	}
	
	/**
	 * ����Dataset�ύ�������
	 */
	private void createDsSubmitTypeList() {
		if (dsSubmitTypeList.size() == 0) {
			dsSubmitTypeList.add(new SubmitType("ѡ����", DatasetRule.TYPE_CURRENT_LINE));
			dsSubmitTypeList.add(new SubmitType("��ǰҳ", DatasetRule.TYPE_CURRENT_PAGE));
			dsSubmitTypeList.add(new SubmitType("��ǰ��", DatasetRule.TYPE_CURRENT_KEY));
			dsSubmitTypeList.add(new SubmitType("������", DatasetRule.TYPE_ALL_LINE));
			dsSubmitTypeList.add(new SubmitType("����ѡ����", DatasetRule.TYPE_ALL_SEL_LINE));
		}
	}
	
	/**
	 * ����Tree�ύ�������
	 */
	private void createTreeSubmitTypeList() {
		if (treeSubmitTypeList.size() == 0) {
//			treeSubmitTypeList.add(new SubmitType("��ǰ�ڵ㡢���ڵ�", TreeRule.TYPE_CURRENT_ROOT));
//			treeSubmitTypeList.add(new SubmitType("��ǰ�ڵ㡢���ڵ㡢���ڵ�", TreeRule.TYPE_CURRENT_PARENT_ROOT));
//			treeSubmitTypeList.add(new SubmitType("��ǰ�ڵ㡢���ڵ㡢���ڵ㡢������", TreeRule.TYPE_CURRENT_PARENT_ROOT_TREE));
//			treeSubmitTypeList.add(new SubmitType("��ǰ�ڵ�", TreeRule.TYPE_CURRENT));
			treeSubmitTypeList.add(new SubmitType("��ǰ�ڵ㡢���и��ڵ�", TreeRule.TYPE_CURRENT_PARENT));
//			treeSubmitTypeList.add(new SubmitType("��ǰ�ڵ㡢��ǰ�ڵ�ĵ�һ���ӽڵ�", TreeRule.TREE_CURRENT_CHILDREN));
			treeSubmitTypeList.add(new SubmitType("��ǰ�ڵ㡢���и��ڵ㡢��ǰ�ڵ�ĵ�һ���ӽڵ�", TreeRule.TREE_CURRENT_PARENT_CHILDREN));
			treeSubmitTypeList.add(new SubmitType("���нڵ�", TreeRule.TREE_ALL));
		}
	}
	
	/**
	 * ����Grid�ύ�������
	 */
	private void createGridSubmitTypeList() {
		if (gridSubmitTypeList.size() == 0) {
			gridSubmitTypeList.add(new SubmitType("��ǰѡ����", GridRule.TYPE_CURRENT_ROW));
			gridSubmitTypeList.add(new SubmitType("��ǰ������", GridRule.TYPE_ALL_ROW));
		}
	}
	
	/**
	 * ����form�ύ����
	 */
	private void createFormSubmitTypeList() {
		if (fromSubmitTypeList.size() == 0) {
			fromSubmitTypeList.add(new SubmitType("������������", FormRule.NO_CHILD));
			fromSubmitTypeList.add(new SubmitType("����������", FormRule.ALL_CHILD));
		}
	}
	
	private void initUI() {
		submitRule = (EventSubmitRule) oldSubmitRule.clone();
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Pagemeta����
		Composite pmArea = new Composite(this, SWT.NULL);
		pmArea.setLayout(new GridLayout(2, false));
		GridData gridDataPmArea = new GridData(GridData.FILL_HORIZONTAL);
		gridDataPmArea.heightHint = 135;
		pmArea.setLayoutData(gridDataPmArea);
		
		// Tab��Card�ύѡ������
		Group topLeft = new Group(pmArea, SWT.NULL);
		topLeft.setLayout(new GridLayout());
		GridData gridDataLeftBottom = new GridData(GridData.FILL_VERTICAL);
		gridDataLeftBottom.widthHint = 150;
		topLeft.setLayoutData(gridDataLeftBottom);
		topLeft.setText(WEBProjConstants.PAGEMETA_CN + "ѡ��");
		
		// �Զ����������
		topRight = new Group(pmArea, SWT.NULL);
		topRight.setLayout(new GridLayout());
		GridData gridDataTopRight = new GridData(GridData.FILL_BOTH);
		topRight.setLayoutData(gridDataTopRight);
		topRight.setText(WEBProjConstants.PAGEMETA_CN + "�Զ������");
		
		
		// Widget����
		Composite wdArea = new Composite(this, SWT.NULL);
		wdArea.setLayout(new GridLayout(2, false));
		wdArea.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Widgetѡ������
		Group centerLeft = new Group(wdArea, SWT.NULL);
		centerLeft.setLayout(new GridLayout());
		GridData gridDataCenterLeft = new GridData(GridData.FILL_VERTICAL);
		gridDataCenterLeft.widthHint = 150;
//		gridDataCenterLeft.verticalIndent = 5;
		centerLeft.setLayoutData(gridDataCenterLeft);
		centerLeft.setText("ѡ��" + LFWPerspectiveNameConst.WIDGET_CN);

		// Widget�ύѡ������
		centerRight = new Composite(wdArea, SWT.NULL);
		centerRight.setLayout(stackLayout);
		centerRight.setLayoutData(new GridData(GridData.FILL_BOTH));
		

		// ��ҳ���ύ����ѡ������
		Composite bottom = new Composite(this, SWT.NULL);
		bottom.setLayout(new GridLayout(2, false));
		GridData gridDataBottom = new GridData(GridData.FILL_HORIZONTAL);
		gridDataBottom.heightHint = 73;
		bottom.setLayoutData(gridDataBottom);
		
		// ��ҳ���ύ����ѡ������
		Group bottomGroup = new Group(bottom, SWT.NULL);
		bottomGroup.setLayout(new GridLayout());
		GridData gridDataBottomGroup = new GridData(GridData.FILL_BOTH);
		bottomGroup.setLayoutData(gridDataBottomGroup);
		bottomGroup.setText("��ҳ���ύ����");
		
		initTopLeftArea(topLeft);
		initTopRight();
		initCenterArea(centerLeft);
		initBottomArea(bottomGroup);
		
	}

	/**
	 * ����Widget��������
	 * @param parent
	 */
	private void initCenterArea(Composite parent) {
		if (null != pagemeta) {
			LfwWidget[] widgets = pagemeta.getWidgets();
			initCenterRightArea(widgets);
			boolean isFirst = false;
			for (LfwWidget widget : widgets) {
				Button radio = new Button(parent, SWT.RADIO);
				radio.setText(widget.getId());
				if (!isFirst) {
					radio.setSelection(true);
					selectWidget(widget.getId());
					isFirst = true;
				}
				radio.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						// ѡ�к��¼�
						String widgetId = ((Button)e.getSource()).getText();
						if (((Button)e.getSource()).getSelection()) {  // ѡ��
							selectWidget(widgetId);
						}
					}
				});
			}
		}
		
	}
	
	/**
	 * Ϊÿ��Widget��������ҳ��
	 * @param widgets
	 */
	private void initCenterRightArea(LfwWidget[] widgets) {
		for (LfwWidget widget : widgets) {
			Map<String, Composite> map = new HashMap<String, Composite>();
			
			// �������
			Composite comp = new Composite(centerRight, SWT.NULL);
			comp.setLayout(new GridLayout());
			comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			map.put(WD_MAIN_AREA, comp);
			
			// Widget�Ƿ�ѡ������
			Composite widgetSelArea = new Composite(comp, SWT.NULL);
			widgetSelArea.setLayout(new GridLayout(2, false));
			GridData gridDataSelArea = new GridData(GridData.FILL_HORIZONTAL);
			gridDataSelArea.heightHint = 25;
			widgetSelArea.setLayoutData(gridDataSelArea);
			map.put(WD_SELECT_AREA, widgetSelArea);
			
			// ���ݼ�����
			Group centerRightTop = new Group(comp, SWT.NULL);
			centerRightTop.setLayout(new GridLayout());
			GridData gridDataCenterRightTop = new GridData(GridData.FILL_HORIZONTAL);
			gridDataCenterRightTop.heightHint = 120;
			centerRightTop.setLayoutData(gridDataCenterRightTop);
			centerRightTop.setText("���ݼ�����");
			map.put(WD_DS_AREA, centerRightTop);
			// ���ݰ��������
			Group centerRightCenter = new Group(comp, SWT.NULL);
			centerRightCenter.setLayout(new GridLayout());
			GridData gridDataCenterRightCenter = new GridData(GridData.FILL_BOTH);
			centerRightCenter.setLayoutData(gridDataCenterRightCenter);
			centerRightCenter.setText("���ݰ��������");
			map.put(WD_BINDING_AREA, centerRightCenter);
			
			// Tab��Card�ύѡ������
			Group centerRightOther = new Group(comp, SWT.NULL);
			centerRightOther.setLayout(new GridLayout(3, false));
			GridData gridDataCenterRightOther = new GridData(GridData.FILL_HORIZONTAL);
			gridDataCenterRightOther.heightHint = 30;
			centerRightOther.setLayoutData(gridDataCenterRightOther);
			centerRightOther.setText("����" + LFWPerspectiveNameConst.WIDGET_CN + "ѡ��");
			map.put(WD_OTHER_AREA, centerRightOther);
			
			widgetAreaMap.put(widget.getId(), map);
		}
		
	}
	
	/**
	 * ��������Pagemetaѡ����������
	 * @param parent
	 */
	private void initTopLeftArea(Composite parent) {
		if (null != pagemeta) {
			// Tab
			Button checkboxTab = new Button(parent, SWT.CHECK);
			checkboxTab.setText("�ύҳǩ");
			if (submitRule.isTabSubmit()) {
				checkboxTab.setSelection(true);
			}
			checkboxTab.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					// ѡ�к��¼�
					String widgetId = ((Button)e.getSource()).getText();
					if (((Button)e.getSource()).getSelection()) {  // ѡ��
						submitRule.setTabSubmit(true);
					} else {  // ��ѡ��
						submitRule.setTabSubmit(false);
					}
				}
			});
			// Card
			Button checkboxCard = new Button(parent, SWT.CHECK);
			checkboxCard.setText("�ύ��Ƭ");
			if (submitRule.isCardSubmit()) {
				checkboxCard.setSelection(true);
			}
			checkboxCard.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					// ѡ�к��¼�
					String widgetId = ((Button)e.getSource()).getText();
					if (((Button)e.getSource()).getSelection()) {  // ѡ��
						submitRule.setCardSubmit(true);
					} else {  // ��ѡ��
						submitRule.setCardSubmit(false);
					}
				}
			});
			// Panel
			Button checkboxPanel = new Button(parent, SWT.CHECK);
			checkboxPanel.setText("�ύPanel");
			if (submitRule.isPanelSubmit()) {
				checkboxPanel.setSelection(true);
			}
			checkboxPanel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					// ѡ�к��¼�
					String widgetId = ((Button)e.getSource()).getText();
					if (((Button)e.getSource()).getSelection()) {  // ѡ��
						submitRule.setPanelSubmit(true);
					} else {  // ��ѡ��
						submitRule.setPanelSubmit(false);
					}
				}
			});
			
		}
		
	}
	
	/**
	 * ���ظ�ҳ���ύ��������
	 * @param parent
	 */
	private void initBottomArea(Composite parent) {
		Composite comp = new Composite(parent, SWT.NULL);
		comp.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gridData);
		
		// ��ȡ��ҳ���ύ����
		parentSubmitRule = submitRule.getParentSubmitRule();
		String parentPagemetaId = null;
		if (parentSubmitRule != null && parentSubmitRule.getPagemeta() != null) {
			parentPagemetaId = parentSubmitRule.getPagemeta();
		}
		
		Button checkboxSel = new Button(comp, SWT.CHECK);
		checkboxSel.setLayoutData(createGridData(100, 1));
		checkboxSel.setText("�ύ��ҳ��");
		checkboxSel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ѡ�к��¼�
				String widgetId = (String) ((Button)e.getSource()).getData();
				if (((Button)e.getSource()).getSelection()) {  // ѡ��
					setAreaEnabled(parentSubmitRuleArea, true);
				} else {  // ��ѡ��
					setAreaEnabled(parentSubmitRuleArea, false);
				}
			}
		});
		
		// ��ҳ���ύ������������
		parentSubmitRuleArea = new Composite(comp, SWT.NULL);
		parentSubmitRuleArea.setLayout(new GridLayout(5, false));
		GridData gridDataPA = new GridData(GridData.FILL_BOTH);
		gridDataPA.horizontalSpan = 1;
		parentSubmitRuleArea.setLayoutData(gridDataPA);
		
		Label label = new Label(parentSubmitRuleArea, SWT.NONE);
		label.setLayoutData(createGridData(50, 1));
		label.setText("��ҳ�棺");
		// ��ҳ��ѡ���
		parentPagemetaCombo = new Combo(parentSubmitRuleArea, SWT.READ_ONLY);
		parentPagemetaCombo.setLayoutData(createGridData(100, 1));
		// ��ȡ����Pagemeta����
		String[] projectPaths = new String[1];
		projectPaths[0] = LFWPersTool.getProjectPath();
		//Map<String, String>[] pageMetaIds = NCConnector.getPageNames(projectPaths);
		
		List<String> pageMetas = getAllPageMeta();
		parentPagemetaCombo.add("ѡ��ҳ��");
		parentPagemetaCombo.setData("ѡ��ҳ��", null);
		parentPagemetaCombo.select(0);
		int itemsCount = 0;
//		for (String key : pageMetaIds[0].keySet()) {
//			itemsCount++;
//			parentPagemetaCombo.add(key);
//			parentPagemetaCombo.setData(key, key);
//			if (parentPagemetaId != null && key.equals(parentPagemetaId))
//				parentPagemetaCombo.select(itemsCount);
//		}
		
		for (int i = 0; i < pageMetas.size(); i++) {
			parentPagemetaCombo.add(pageMetas.get(i));
			parentPagemetaCombo.setData(pageMetas.get(i), pageMetas.get(i));
			if (parentPagemetaId != null && pageMetas.get(i).equals(parentPagemetaId))
				parentPagemetaCombo.select(itemsCount);
		}
		
		// �հ�����
		Composite centerComp = new Composite(parentSubmitRuleArea, SWT.NULL);
		centerComp.setLayoutData(createGridData(30, 1));
		
		// ��ҳ���ύ����ѡ��ť
		Button selPsrBtn = new Button(parentSubmitRuleArea, SWT.BUTTON1);
		parentPagemetaCombo.setLayoutData(createGridData(60, 1));
		selPsrBtn.setText("�����ύ����");
		selPsrBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e) {
				String parentPagemetaId = (String) parentPagemetaCombo.getData(parentPagemetaCombo.getText());
				if (parentPagemetaId != null) {
					if (null == parentSubmitRule) {
						parentSubmitRule = new EventSubmitRule();
					}
					PageMeta pagemeta = LFWPersTool.getPagemetaById(parentPagemetaId);
					if(pagemeta == null){
						MessageDialog.openError(null, "��ʾ", "����" + parentPagemetaId + "ȡ������Ӧ��pagemeta!");
						return;
					}
					else{
						SubmitRuleDialog dialog = new SubmitRuleDialog(new Shell());
						dialog.setPagemeta(pagemeta);
						dialog.setSubmitRule(parentSubmitRule);
						dialog.setParentSubmitRule(true);
						if (dialog.open() == Dialog.OK) {
							parentSubmitRule = dialog.getMainContainer().getSubmitRule();
							parentSubmitRule.setPagemeta(parentPagemetaId);
							getSubmitRule().setParentSubmitRule(parentSubmitRule);
						}
					}
				}
			}
		});
		
		if (null != parentSubmitRule) {
			checkboxSel.setSelection(true);
		} else {
			setAreaEnabled(parentSubmitRuleArea, false);
		}
		
		if (isParentSubmitRule) {
			setAreaEnabled(parent, false);
		}
	}
	
	public  List getAllPageMeta() {
		IProject project = LFWPersTool.getCurrentProject();
		List pageMetas = new ArrayList<String>();
		String projectPath = LFWPersTool.getProjectPath();
		if(LFWPersTool.isBCPProject(project)){
			LFWBusinessCompnentTreeItem busiCompnent = LFWPersTool.getCurrentBusiCompTreeItem();
			projectPath += "/" + busiCompnent.getText().substring(busiCompnent.getText().indexOf(WEBProjConstants.BUSINESSCOMPONENT) + 
					WEBProjConstants.BUSINESSCOMPONENT.length() + 1, busiCompnent.getText().length() -1 );
		}
		File fileFolder = new File(projectPath + "/web/html/nodes");
		File[] children = fileFolder.listFiles();
		if(children != null){
			for (int j = 0; j < children.length; j++) {
				if(children[j].isDirectory()){
					pageMetas = scanDir(children[j], pageMetas);
				}
			}
		}
		return pageMetas;
	}
	
	
	private  String BASE_PATH = "\\web\\html\\nodes\\";
	private  List<String> scanDir(File dir, List pageMetas){
		TreeItem item = null;
		if(judgeIsPMFolder(dir)){
			pageMetas.add(dir.getAbsolutePath().substring(dir.getAbsolutePath().lastIndexOf(BASE_PATH) + 16));
		}
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if(fs[i].isDirectory())
				scanDir(fs[i], pageMetas);
		}
		return pageMetas;
	}


	private  boolean judgeIsPMFolder(File fold) {
		File[] childChildren = fold.listFiles();
		if(childChildren == null)
			return false;
		for (int i = 0; i < childChildren.length; i++) {
			if(childChildren[i].getName().equals("pagemeta.pm"))
				return true;
		}
		return false;
		
	}

	
	
	/**
	 * ����Widget�Ƿ�ѡ����������
	 * @param widgetId
	 * @param parent
	 */
	private void showSelInfo(String widgetId, Composite parent) {
		WidgetRule widgetRule = submitRule.getWidgetRule(widgetId);
		
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		
		Button checkboxSel = new Button(parent, SWT.CHECK);
		checkboxSel.setLayoutData(gridData);
		checkboxSel.setText("�ύ��" + LFWPerspectiveNameConst.WIDGET_CN);
		checkboxSel.setData(widgetId);
		if (null != widgetRule) {
			checkboxSel.setSelection(true);
		} else {
			setWidgetAreaEnabled(widgetAreaMap.get(widgetId), false);
		}
		checkboxSel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ѡ�к��¼�
				String widgetId = (String) ((Button)e.getSource()).getData();
				if (((Button)e.getSource()).getSelection()) {  // ѡ��
					WidgetRule wr = new WidgetRule();
					wr.setId(widgetId);
					submitRule.addWidgetRule(wr);
					// ��֮ǰ��ɾ��������ָ���
					if (tempWidgetRuleMap.containsKey(widgetId)) {
						submitRule.getWidgetRules().put(widgetId, (WidgetRule) tempWidgetRuleMap.get(widgetId).clone());
						tempWidgetRuleMap.remove(widgetId);
					}
					setWidgetAreaEnabled(widgetAreaMap.get(widgetId), true);
					
				} else {  // ��ѡ��
					// ��ʱ���汻ɾ����Widget�ύ����
					tempWidgetRuleMap.put(widgetId, (WidgetRule) submitRule.getWidgetRule(widgetId).clone());
					submitRule.getWidgetRules().remove(widgetId);
					setWidgetAreaEnabled(widgetAreaMap.get(widgetId), false);
					
				}
			}
		});
	}
	
	/**
	 * ��������Widgetѡ����������
	 */
	private void showWidgetOtherInfo(String widgetId, Composite parent) {
		WidgetRule widgetRule = submitRule.getWidgetRule(widgetId);
		
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		
		// Tab
		Button checkboxTab = new Button(parent, SWT.CHECK);
		checkboxTab.setLayoutData(gridData);
		checkboxTab.setText("�ύҳǩ");
		checkboxTab.setData(widgetId);
		if ((null != widgetRule && widgetRule.isTabSubmit()) 
				|| (tempWidgetRuleMap.containsKey(widgetId) && tempWidgetRuleMap.get(widgetId).isTabSubmit())) {
			checkboxTab.setSelection(true);
		}
		checkboxTab.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ѡ�к��¼�
				String widgetId = (String) ((Button)e.getSource()).getData();
				if (((Button)e.getSource()).getSelection()) {  // ѡ��
					submitRule.getWidgetRules().get(widgetId).setTabSubmit(true);
				} else {  // ��ѡ��
					submitRule.getWidgetRules().get(widgetId).setTabSubmit(false);
				}
			}
		});
		// Card
		Button checkboxCard = new Button(parent, SWT.CHECK);
		checkboxCard.setLayoutData(gridData);
		checkboxCard.setText("�ύ��Ƭ");
		checkboxCard.setData(widgetId);
		if ((null != widgetRule && widgetRule.isCardSubmit()) 
			|| (tempWidgetRuleMap.containsKey(widgetId) && tempWidgetRuleMap.get(widgetId).isCardSubmit())) {
			checkboxCard.setSelection(true);
		}
		checkboxCard.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ѡ�к��¼�
				String widgetId = (String) ((Button)e.getSource()).getData();
				if (((Button)e.getSource()).getSelection()) {  // ѡ��
					submitRule.getWidgetRules().get(widgetId).setCardSubmit(true);
				} else {  // ��ѡ��
					submitRule.getWidgetRules().get(widgetId).setCardSubmit(false);
				}
			}
		});
		// Panel
		Button checkboxPanel = new Button(parent, SWT.CHECK);
		checkboxPanel.setLayoutData(gridData);
		checkboxPanel.setText("�ύPanel");
		checkboxPanel.setData(widgetId);
		if ((null != widgetRule && widgetRule.isPanelSubmit()) 
			|| (tempWidgetRuleMap.containsKey(widgetId) && tempWidgetRuleMap.get(widgetId).isPanelSubmit())) {
			checkboxPanel.setSelection(true);
		}
		checkboxPanel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ѡ�к��¼�
				String widgetId = (String) ((Button)e.getSource()).getData();
				if (((Button)e.getSource()).getSelection()) {  // ѡ��
					submitRule.getWidgetRules().get(widgetId).setPanelSubmit(true);
				} else {  // ��ѡ��
					submitRule.getWidgetRules().get(widgetId).setPanelSubmit(false);
				}
			}
		});
		
	}
	
	/**
	 * ������������
	 * @param comp
	 */
	private void clearComposite(Composite comp) {
		for (Control child : comp.getChildren()) {
			child.dispose();
		}
	}
	
	/**
	 * ѡ��Widget�Ĳ������򿪶�ӦWidgetѡ��ҳ�棩
	 * @param widgetId
	 */
	public void selectWidget(String widgetId) {
		Map<String, Composite> map = widgetAreaMap.get(widgetId);
		stackLayout.topControl = map.get(WD_MAIN_AREA);
		centerRight.layout();
		Composite selArea = map.get(WD_SELECT_AREA);
		Composite dsArea = map.get(WD_DS_AREA);
		
		
		Composite bindingArea = map.get(WD_BINDING_AREA);
		Composite otherArea = map.get(WD_OTHER_AREA);
		clearComposite(selArea);
		clearComposite(dsArea);
		clearComposite(bindingArea);
		clearComposite(otherArea);
		showDsInfo(widgetId, dsArea);
		showBindingInfo(widgetId, bindingArea);
		showWidgetOtherInfo(widgetId, otherArea);
		showSelInfo(widgetId, selArea);
		dsArea.layout();
		otherArea.layout();
		bindingArea.layout();
		selArea.layout();
	}
	
	/**
	 * ��ʾDataset��Ϣ
	 */
	private void showDsInfo(String widgetId, Composite parent) {
		LfwWidget widget = pagemeta.getWidget(widgetId);
		
		ScrolledComposite scrolledCompositeDS = new ScrolledComposite(parent, SWT.V_SCROLL);
		scrolledCompositeDS.setLayout(new GridLayout());
		scrolledCompositeDS.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite allDsItemsArea = new Composite(scrolledCompositeDS, SWT.NONE);
		allDsItemsArea.setLayout(new GridLayout());
		int  height = 200;
		int size = widget.getViewModels().getDatasets().length;
		if(size > 5)
			height = 40*size;
		allDsItemsArea.setSize(300, height);
		
		scrolledCompositeDS.setContent(allDsItemsArea);
		
		for (Dataset ds : widget.getViewModels().getDatasets()) {
			if (!(ds instanceof RefDataset) && !(ds instanceof RefMdDataset) && !(ds instanceof RefPubDataset)) {
				Composite container = new Composite(allDsItemsArea, SWT.NONE);
				container.setLayout(new GridLayout(3, false));
				container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			//				//itemsArea = new Composite(scrolledComposite, SWT.NONE);
//				itemsArea.setLayout(new GridLayout());
////				itemsArea.setLayoutData(new GridData(GridData.FILL_BOTH));
//				itemsArea.setSize(645, 900);
//				
//				scrolledCompositeDS.setContent(itemsArea);
				
				
				
				Button dsCheckbox = new Button(container, SWT.CHECK);
				dsCheckbox.setLayoutData(createGridData(150, 1));
				dsCheckbox.setText(ds.getId());
				dsCheckbox.setData(ds.getId(), widgetId);
				dsCheckbox.setToolTipText(ds.getId());
				new Label(container, SWT.NONE).setText("�ύ���ͣ�");
				Combo typeCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
				typeCombo.setLayoutData(createGridData(70, 1));
				
				createDsSubmitTypeList();
				for (SubmitType type : dsSubmitTypeList) {
					typeCombo.add(type.getText());
					typeCombo.setData(type.getText(), type.getType());
				}
				typeCombo.select(0);
				
				if (submitRule.getWidgetRules().containsKey(widgetId)) {
					DatasetRule[] dsRules = submitRule.getWidgetRule(widgetId).getDatasetRules();
					if(dsRules != null){
						for (int i = 0; i < dsRules.length; i++) {
							DatasetRule dsRule = dsRules[i];
							if(dsRule.getId().equals(ds.getId())){
								dsCheckbox.setSelection(true);
								String typeStr = dsRule.getType();
								String[] texts = typeCombo.getItems();
								for (int j = 0, n = texts.length; j < n; j++) {
									if (typeCombo.getData(texts[j]).equals(typeStr))
										typeCombo.select(j);
								}
								break;
							}
						}
					}
				} else if (tempWidgetRuleMap.containsKey(widgetId)){
					DatasetRule[] dsRules = tempWidgetRuleMap.get(widgetId).getDatasetRules();
					if(dsRules != null){
						for (int i = 0; i < dsRules.length; i++) {
							DatasetRule dsRule = dsRules[i];
							if(dsRule.getId().equals(ds.getId())){
								dsCheckbox.setSelection(true);
								String typeStr = dsRule.getType();
								String[] texts = typeCombo.getItems();
								for (int j = 0, n = texts.length; j < n; j++) {
									if (typeCombo.getData(texts[j]).equals(typeStr))
										typeCombo.select(j);
								}
								break;
							}
						}
					}
				}
				
				// ѡ�к��¼�
				dsCheckbox.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Button check = ((Button)e.getSource());
						String dsId = check.getText();
						String widgetId = (String) check.getData(dsId);
						if (check.getSelection()) {
							String type = "";
							for (Control brother : check.getParent().getChildren()) {
								if (brother instanceof Combo) {
									type = (String)((Combo) brother).getData(((Combo) brother).getText());
								}
							}
							DatasetRule dsRule = new DatasetRule();
							dsRule.setId(dsId);
							dsRule.setType(type);
							submitRule.getWidgetRules().get(widgetId).addDsRule(dsRule);
						} else {
							submitRule.getWidgetRules().get(widgetId).removeDsRule(dsId);
						}
					}
				});
				// ѡ�к��¼�
				typeCombo.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Combo combo = ((Combo)e.getSource());
						String type = (String)combo.getData(combo.getText());
						String dsId = "";
						String widgetId = "";
						for (Control brother : combo.getParent().getChildren()) {
							if (brother instanceof Button) {
								dsId = ((Button) brother).getText();
								widgetId = (String)((Button) brother).getData(dsId);
							}
						}
						
						DatasetRule[] dsRules = submitRule.getWidgetRules().get(widgetId).getDatasetRules();
						for (int i = 0; i < dsRules.length; i++) {
							DatasetRule dsRule = dsRules[i];
							if(dsRule.getId().equals(dsId)){
								dsRule.setType(type);
								break;
							}
						}
					}
				});
			}
		}
		allDsItemsArea.layout();
	}
	
	/**
	 * ��ʾ���ݰ������Ϣ
	 */
	private void showBindingInfo(String widgetId, Composite parent) {
		LfwWidget widget = pagemeta.getWidget(widgetId);
		ScrolledComposite scrolledCompositeComp = new ScrolledComposite(parent, SWT.V_SCROLL);
		scrolledCompositeComp.setLayout(new GridLayout());
		scrolledCompositeComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite allComponetArea = new Composite(scrolledCompositeComp, SWT.NONE);
		allComponetArea.setLayout(new GridLayout());
		int  height = 200;
		int size = widget.getViewComponents().getComponents().length;
		if(size > 5)
			height = 40*size;
		allComponetArea.setSize(300, height);
		
		scrolledCompositeComp.setContent(allComponetArea);
		for (WebComponent comp : widget.getViewComponents().getComponents()) {
			if (comp instanceof TreeViewComp) {
				Composite container = new Composite(allComponetArea, SWT.NONE);
				container.setLayout(new GridLayout(3, false));
				container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
				Button treeCheckbox = new Button(container, SWT.CHECK);
				treeCheckbox.setLayoutData(createGridData(150, 1));
				treeCheckbox.setText(comp.getId());
				treeCheckbox.setData(comp.getId(), widgetId);
				treeCheckbox.setToolTipText(comp.getId());
				new Label(container, SWT.NONE).setText("�ύ���ͣ�");
				Combo typeCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
				typeCombo.setLayoutData(createGridData(70, 1));
				
				createTreeSubmitTypeList();
				for (SubmitType type : treeSubmitTypeList) {
					typeCombo.add(type.getText());
					typeCombo.setData(type.getText(), type.getType());
				}
				typeCombo.select(0);
				
				if (submitRule.getWidgetRules().containsKey(widgetId)){
					TreeRule[] treeRules = submitRule.getWidgetRules().get(widgetId).getTreeRules();
					if(treeRules != null){
						for (int i = 0; i < treeRules.length; i++) {
							TreeRule treeRule = treeRules[i];
							if(treeRule.getId().equals(comp.getId())){
								treeCheckbox.setSelection(true);
								String typeStr = treeRule.getType();
								String[] texts = typeCombo.getItems();
								for (int j = 0, n = texts.length; j < n; j++) {
									if (typeCombo.getData(texts[j]).equals(typeStr))
										typeCombo.select(j);
								}
								break;
							}
						}
					}
				} else if (tempWidgetRuleMap.containsKey(widgetId)){
					TreeRule[] treeRules = tempWidgetRuleMap.get(widgetId).getTreeRules();
					if(treeRules != null){
						for (int i = 0; i < treeRules.length; i++) {
							TreeRule treeRule = treeRules[i];
							if(treeRule.getId().equals(comp.getId())){
								treeCheckbox.setSelection(true);
								String typeStr = treeRule.getType();
								String[] texts = typeCombo.getItems();
								for (int j = 0, n = texts.length; j < n; j++) {
									if (typeCombo.getData(texts[j]).equals(typeStr))
										typeCombo.select(j);
								}
								break;
							}
						}
					}
				}
				
				// ѡ�к��¼�
				treeCheckbox.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Button check = ((Button)e.getSource());
						String treeId = check.getText();
						String widgetId = (String) check.getData(treeId);
						if (check.getSelection()) {
							String type = "";
							for (Control brother : check.getParent().getChildren()) {
								if (brother instanceof Combo) {
									type = (String)((Combo) brother).getData(((Combo) brother).getText());
								}
							}
							TreeRule treeRule = new TreeRule();
							treeRule.setId(treeId);
							treeRule.setType(type);
							submitRule.getWidgetRules().get(widgetId).addTreeRule(treeRule);
						} else {
							submitRule.getWidgetRules().get(widgetId).removeTreeRule(treeId);
						}
					}
				});
				// ѡ�к��¼�
				typeCombo.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Combo combo = ((Combo)e.getSource());
						String type = (String)combo.getData(combo.getText());
						String treeId = "";
						String widgetId = "";
						for (Control brother : combo.getParent().getChildren()) {
							if (brother instanceof Button) {
								treeId = ((Button) brother).getText();
								widgetId = (String)((Button) brother).getData(treeId);
							}
						}
						
						TreeRule[] treeRules = submitRule.getWidgetRules().get(widgetId).getTreeRules();
						for (int i = 0; i < treeRules.length; i++) {
							TreeRule treeRule = treeRules[i];
							if(treeRule.getId().equals(treeId)){
								treeRule.setType(type);
								break;
							}
						}
					}
				});
			} else if (comp instanceof GridComp) {
				Composite container = new Composite(allComponetArea, SWT.NONE);
				container.setLayout(new GridLayout(3, false));
				container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
				Button gridCheckbox = new Button(container, SWT.CHECK);
				gridCheckbox.setLayoutData(createGridData(150, 1));
				gridCheckbox.setText(comp.getId());
				gridCheckbox.setData(comp.getId(), widgetId);
				gridCheckbox.setToolTipText(comp.getId());
				new Label(container, SWT.NONE).setText("�ύ���ͣ�");
				Combo typeCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
				typeCombo.setLayoutData(createGridData(70, 1));
				
				createGridSubmitTypeList();
				for (SubmitType type : gridSubmitTypeList) {
					typeCombo.add(type.getText());
					typeCombo.setData(type.getText(), type.getType());
				}
				typeCombo.select(0);
				
				if (submitRule.getWidgetRules().containsKey(widgetId)){
					GridRule[] gridRules = submitRule.getWidgetRules().get(widgetId).getGridRules();
					if(gridRules != null){
						for (int i = 0; i < gridRules.length; i++) {
						GridRule gridRule = gridRules[i];
						if(gridRule.getId().equals(comp.getId())){
							gridCheckbox.setSelection(true);
							String typeStr = gridRule.getType();
							String[] texts = typeCombo.getItems();
							for (int j = 0, n = texts.length; j < n; j++) {
								if (typeCombo.getData(texts[j]).equals(typeStr))
									typeCombo.select(j);
								}
							break;
							}
						}
					}
				} else if (tempWidgetRuleMap.containsKey(widgetId)){
					GridRule[] gridRules = tempWidgetRuleMap.get(widgetId).getGridRules();
					if(gridRules != null){
						for (int i = 0; i < gridRules.length; i++) {
						GridRule gridRule = gridRules[i];
						if(gridRule.getId().equals(comp.getId())){
							gridCheckbox.setSelection(true);
							String typeStr = gridRule.getType();
							String[] texts = typeCombo.getItems();
							for (int j = 0, n = texts.length; j < n; j++) {
								if (typeCombo.getData(texts[j]).equals(typeStr))
									typeCombo.select(j);
								}
							break;
							}
						}
					}
				}
				
					
				// ѡ�к��¼�
				gridCheckbox.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Button check = ((Button)e.getSource());
						String gridId = check.getText();
						String widgetId = (String) check.getData(gridId);
						if (check.getSelection()) {
							String type = "";
							for (Control brother : check.getParent().getChildren()) {
								if (brother instanceof Combo) {
									type = (String)((Combo) brother).getData(((Combo) brother).getText());
								}
							}
							GridRule gridRule = new GridRule();
							gridRule.setId(gridId);
							gridRule.setType(type);
							submitRule.getWidgetRules().get(widgetId).addGridRule(gridRule);
						} else {
							submitRule.getWidgetRules().get(widgetId).removeGridRule(gridId);
						}
					}
				});
				// ѡ�к��¼�
				typeCombo.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Combo combo = ((Combo)e.getSource());
						String type = (String)combo.getData(combo.getText());
						String gridId = "";
						String widgetId = "";
						for (Control brother : combo.getParent().getChildren()) {
							if (brother instanceof Button) {
								gridId = ((Button) brother).getText();
								widgetId = (String)((Button) brother).getData(gridId);
							}
						}
						
						GridRule[] gridRules = submitRule.getWidgetRules().get(widgetId).getGridRules();
						for (int j = 0; j < gridRules.length; j++) {
							GridRule gridRule = gridRules[j];
							if(gridRule.getId().equals(gridId)){
								gridRule.setType(type);
								break;
							}
						}
					}
				});
			}
			else if (comp instanceof FormComp) {
				Composite container = new Composite(allComponetArea, SWT.NONE);
				container.setLayout(new GridLayout(3, false));
				container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
				Button formCheckbox = new Button(container, SWT.CHECK);
				formCheckbox.setLayoutData(createGridData(150, 1));
				formCheckbox.setText(comp.getId());
				formCheckbox.setData(comp.getId(), widgetId);
				formCheckbox.setToolTipText(comp.getId());
				new Label(container, SWT.NONE).setText("�ύ���ͣ�");
				Combo typeCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
				typeCombo.setLayoutData(createGridData(70, 1));
				
				createFormSubmitTypeList();
				for (SubmitType type : fromSubmitTypeList) {
					typeCombo.add(type.getText());
					typeCombo.setData(type.getText(), type.getType());
				}
				typeCombo.select(0);
				
				if (submitRule.getWidgetRules().containsKey(widgetId)){
					FormRule[] formRules = submitRule.getWidgetRules().get(widgetId).getFormRules();
					if(formRules != null){
						for (int i = 0; i < formRules.length; i++) {
						FormRule formRule = formRules[i];
						if(formRule.getId().equals(comp.getId())){
							formCheckbox.setSelection(true);
							String typeStr = formRule.getType();
							String[] texts = typeCombo.getItems();
							for (int j = 0, n = texts.length; j < n; j++) {
								if (typeCombo.getData(texts[j]).equals(typeStr))
									typeCombo.select(j);
								}
							break;
							}
						}
					}
				} else if (tempWidgetRuleMap.containsKey(widgetId)){
					FormRule[] formRules = tempWidgetRuleMap.get(widgetId).getFormRules();
					if(formRules != null){
						for (int i = 0; i < formRules.length; i++) {
							FormRule formRule = formRules[i];
							if(formRule.getId().equals(comp.getId())){
								formCheckbox.setSelection(true);
								String typeStr = formRule.getType();
								String[] texts = typeCombo.getItems();
								for (int j = 0, n = texts.length; j < n; j++) {
									if (typeCombo.getData(texts[j]).equals(typeStr))
										typeCombo.select(j);
									}
								break;
							}
						}
					}
				}
				
					
				// ѡ�к��¼�
				formCheckbox.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Button check = ((Button)e.getSource());
						String formId = check.getText();
						String widgetId = (String) check.getData(formId);
						if (check.getSelection()) {
							String type = "";
							for (Control brother : check.getParent().getChildren()) {
								if (brother instanceof Combo) {
									type = (String)((Combo) brother).getData(((Combo) brother).getText());
								}
							}
							FormRule formRule = new FormRule();
							formRule.setId(formId);
							formRule.setType(type);
							submitRule.getWidgetRules().get(widgetId).addFormRule(formRule);
						} else {
							submitRule.getWidgetRules().get(widgetId).removeFormRule(formId);
						}
					}
				});
				// ѡ�к��¼�
				typeCombo.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						Combo combo = ((Combo)e.getSource());
						String type = (String)combo.getData(combo.getText());
						String formId = "";
						String widgetId = "";
						for (Control brother : combo.getParent().getChildren()) {
							if (brother instanceof Button) {
								formId = ((Button) brother).getText();
								widgetId = (String)((Button) brother).getData(formId);
							}
						}
						
						FormRule[] formRules = submitRule.getWidgetRules().get(widgetId).getFormRules();
						for (int j = 0; j < formRules.length; j++) {
							FormRule formRule = formRules[j];
							if(formRule.getId().equals(formId)){
								formRule.setType(type);
								break;
							}
						}
					}
				});
			}
		}
		allComponetArea.layout();
	}
	
	/**
	 * ��ʼ���ϲ��Ҳ�����
	 */
	private void initTopRight() {
		paramTable = new Table(topRight, SWT.BORDER | SWT.FULL_SELECTION);
		TableLayout tableLayout = new TableLayout();
		paramTable.setLayout(tableLayout);
		paramTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		paramTable.setHeaderVisible(true);
		paramTable.setLinesVisible(true);
		createColumn(paramTable, tableLayout, 100, SWT.NONE, "����");
		createColumn(paramTable, tableLayout, 100, SWT.NONE, "ֵ");
		
		Parameter[] paramArray = submitRule.getParams();
		for (Parameter param : paramArray) {
			// ��������
			String[] str = new String[2];
			str[0] = param.getName();
			str[1] = param.getValue();
			TableItem item = new TableItem(paramTable, SWT.NONE);
			item.setText(str);
		}
		
		// �����ư�ť����
		Composite btnArea = new Composite(topRight, SWT.NONE);
		GridLayout btnAreaLayout = new GridLayout(3, false);
		btnArea.setLayout(btnAreaLayout);
		btnArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		GridData btnGridData = new GridData(50, 21);
		
		addBtn = new Button(btnArea, SWT.NONE);
		addBtn.setLayoutData(btnGridData);
		addBtn.setText("����");
		delBtn = new Button(btnArea, SWT.NONE);
		delBtn.setLayoutData(btnGridData);
		delBtn.setText("ɾ��");
		
		// ���Ӳ����¼�
		addBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				// �򿪹��������Ի���
				SubmitRuleParamDialog dialog = new SubmitRuleParamDialog(new Shell());
				dialog.setSubmitRule(submitRule);
				if (dialog.open() == Dialog.OK) {
					String name = dialog.getName();
					if (!"".equals(name)) {
						String value = dialog.getValue();
						Parameter param = new Parameter();
						param.setName(name);
						param.setValue(value);
						submitRule.addParam(param);
						
						// ��������
						String[] str = new String[2];
						str[0] = name;
						str[1] = value;
						TableItem item = new TableItem(paramTable, SWT.NONE);
						item.setText(str);
					}
				}
			}
		});
		
		// ɾ�������¼�
		delBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if (paramTable.getSelectionIndex() != -1) {
					if (MessageDialog.openConfirm(null, "ȷ��", "ȷ��ɾ����ѡ������")) {
						TableItem item = paramTable.getItem(paramTable.getSelectionIndex());
						if (submitRule.getParam(item.getText(0)) != null)
							submitRule.getParamMap().remove(item.getText(0));
						item.dispose();
					}
				} else {
					MessageDialog.openInformation(null, "��ʾ", "����ѡ��Ҫɾ���ı���");
				}
				
			}
		});
		
	}
	
	/**
	 * ����Widgetѡ������༭״̬
	 * @param areaMap
	 * @param enabled
	 */
	private void setWidgetAreaEnabled(Map<String, Composite> areaMap, boolean enabled) {
		Composite dsArea = areaMap.get(WD_DS_AREA);
		Composite bindingArea = areaMap.get(WD_BINDING_AREA);
		Composite otherArea = areaMap.get(WD_OTHER_AREA);
		setAreaEnabled(dsArea, enabled);
		setAreaEnabled(bindingArea, enabled);
		setAreaEnabled(otherArea, enabled);
	}
	
	/**
	 * ��������༭״̬
	 * @param comp
	 * @param enabled
	 */
	private void setAreaEnabled(Composite comp, boolean enabled) {
		for (Control control : comp.getChildren()) {
			control.setEnabled(enabled);
			if (control instanceof Composite) {
				for (Control child : ((Composite)control).getChildren()) {
					child.setEnabled(enabled);
				}
			}
		}
		comp.setEnabled(enabled);
	}
	
	private void createColumn(Table table, TableLayout layout, int width,
			int align, String text) {
		layout.addColumnData(new ColumnWeightData(width));
		new TableColumn(table, align).setText(text);
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public EventSubmitRule getSubmitRule() {
		return submitRule;
	}


}
