package nc.uap.lfw.perspective.action;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

import org.eclipse.jface.action.Action;

/**
 *  ncģʽ������
 *  1.ѡ��ҳ������
 *  2.����mainwidget
 * @author zhangxya
 *
 */
public class NcPatternGenerator  extends Action {

	//�õ��ڵ�����ĵ���ģ��
//	private TemplateVO[] alltemps;
//	private Integer pageType;
//	private boolean isBody;
////	private boolean isSupportMultiVisible;
//	private String rootPackage;
//	private List<TemplateVO> billtempvos;
//	private List<TemplateVO> qrytempvos;
////	private List<TemplateVO> printtemvos;
//	private UIMetaUtil uimetaUtil;
//	private LfwWidget widget;
//	private NewWidgetAction widgetAction;
//	
//	private MenubarGenerator menubarGenerator;
//	
//	private String billType;
	
	private PageMeta pm;
	
	public static String GENERATElISTENER = "GenerateListener";
	
	public NcPatternGenerator() {
		super(WEBProjConstants.NC_PATTERN_GENERATOR);
	}

	public void run() {
		if(this instanceof AMCNCPatternGenerator){
			pm = LFWAMCPersTool.getDefaultWindow(null);
		}else{
			pm = LFWPersTool.getCurrentPageMeta();
		}
		if(pm == null){
			return;
		}
//		ExtAttribute funnodeAttr = pm.getExtendAttribute(ExtAttrConstants.FUNC_CODE);
//		if(pm != null && funnodeAttr == null){
//			MessageDialog.openError(null, "��ʾ", "δ���ù��ܽڵ�,�޷�����NCģʽ������!");
//			return;
//		}
//		else {
//			String funnode = funnodeAttr.getValue().toString();
//			//�˽ڵ������ģ��
//			billtempvos = getAllBillTemplateVOByfunnode(funnode);
//			if(billtempvos == null || billtempvos.size() == 0){
//				MessageDialog.openError(null, "��ʾ", "�˽ڵ�δ����ģ��,�޷�����NCģʽ������!");
//				return;
//			}
//			if(billtempvos.size() > 1){
//				MessageDialog.openError(null, "��ʾ", "NC��������ģ�����һ��,�޷�����NCģʽ������!");
//				return;
//			}
//			else{
//				//��ѯģ�壬���û�����ò�ѯģ����ߴ�ӡģ�壬������ʾ�Ƿ������
//				if(qrytempvos == null)
//					qrytempvos = getALlQryTemplateVOByfunnode(funnode);
//				boolean qryFlag = true;
//				if(qrytempvos == null || qrytempvos.size() == 0)
//					qryFlag = MessageDialog.openConfirm(null, "��ʾ", "û�����ò�ѯģ��,�Ƿ����?");
//				if(!qryFlag)
//					return;
//				if(printtemvos == null)
//					printtemvos = getALlPrintTemplateVOByfunnode(funnode);
//				boolean printFlag = true;
//				if(printtemvos == null || printtemvos.size() == 0)
//					printFlag = MessageDialog.openConfirm(null, "��ʾ", "û�����ô�ӡģ��,�Ƿ����?");
//				if(!printFlag)
//					return;
//				if(qryFlag && printFlag){
//					//ҳ������ѡ��Ի���
//					PageTypeSelectedDialog pagetypeDialog = new PageTypeSelectedDialog(null);
//					if(pagetypeDialog.open() == pagetypeDialog.OK){
//						//ҳ������
//						pageType = pagetypeDialog.getPageType();
//						//package
//						rootPackage = pagetypeDialog.getRootPackage();
//						//isbody
//						isBody = pagetypeDialog.isBody();
//						//isSupportMultiVisible
////						isSupportMultiVisible = pagetypeDialog.isSupportMultiVisible();
////						if(pageType == FuncNodeVO.PAGETYPE_MANAGE_MASCHI_LISTFIRST || pageType == FuncNodeVO.PAGETYPE_MANAGE_SINGAL_LISTFIRST){
////							pm.setPageState("2");
////						}
//						try{
//							//����widget���ҹ���nc����ģ��
//							generatorWidget(pm);
//							//���ɲ˵�
//							pm = LFWPersTool.getCurrentPageMeta();
//							generatorMenubar(pm,funnode);
//							
//							
//							pm = LFWPersTool.getCurrentPageMeta();
//							//widgetAction.saveWidget(pm.getWidget("main"));
//							//����pm
//							widgetAction.savePagemeta(pm);
//							//����ui
//							generatorUI(pm);
//							//����ڵ�ֻ������һ����ѯģ�壬��Ĭ������qry�ڵ�
//	//							if(qrytempvos == null)
//	//								qrytempvos = getALlQryemplateVOByfunnode(funnode);
//							if(qrytempvos.size() == 1){
//								generatorQryNode(funnode);
//							}
//							
//							// ˢ�½ڵ�
//							LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//							Tree tree = LFWPersTool.getTree();
//							RefreshNodeAction.refreshNode(view, tree);
//						}catch(Exception e){
//							MainPlugin.getDefault().logError(e.getMessage(), e);
//							MessageDialog.openError(null, "����", e.getMessage());
//						}
//						
//					}
//				}
//			}
//		}
	}

	public static final String DEFAULT_PROCESSOR_CLASS_NAME = "nc.uap.lfw.core.processor.EventRequestProcessor";
	
	//Ĭ�����ɲ�ѯ�ڵ�
//	private void generatorQryNode(String funnode){
//		PageMeta pagemeta = new PageMeta();
//		pagemeta.setId("qry");
//		pagemeta.setProcessorClazz(DEFAULT_PROCESSOR_CLASS_NAME);
////		pagemeta.setExtendAttribute(ExtAttrConstants.FUNC_CODE, funnode);
//		if(widgetAction == null)
//			widgetAction = new NewWidgetAction();
//		Tree tree = LFWPersTool.getTree();
////		RefreshNodeGroupAction.refreshNodes(tree, LFWPersTool.getCurrentProject());
////		//���ɲ˵�
////		generatorMenubar(pagemeta);
//		
//		String folderPath = LFWPersTool.getCurrentFolderPath();
//		folderPath += "/qry";
//		//����pm
//		widgetAction.savePagemeta(folderPath, pagemeta);
//		
//		NCConnector.refreshNode();
//		// ˢ�½ڵ�
//		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//		RefreshNodeAction.refreshNode(view, tree);
//		
//		TreeItem ti = LFWPersTool.getCurrentTreeItem();
//		if(ti instanceof LFWApplicationTreeItem){
//			ti = LFWAMCPersTool.getDefaultWindowTreeItem(null);
//		}
//		TreeItem[] items = ti.getItems();
//		LFWPageMetaTreeItem pmItem = null;
//		for (int i = 0; i < items.length; i++) {
//			if(items[i] instanceof LFWPageMetaTreeItem){
//				pmItem = (LFWPageMetaTreeItem) items[i];
//				break;
//			}
//		}
//		pagemeta = pmItem.getPm();
//		if(pagemeta == null)
//			return;
//		generatorQryWidget(pagemeta);
//		//����ui
//		//generatorUI(pagemeta);
//		//����mainwidget
//		
//	}
	
	
	
	//todo
//	private void generatorMenubar(PageMeta pm, String funcnode){
//		if(menubarGenerator == null){
//			String pageName = pm.getId();
//			if(pageName.indexOf("\\") != -1){
//				pageName = pageName.substring(pageName.lastIndexOf("\\") + 1);
//			}
//			String suffix = pageName.substring(0, 1).toUpperCase() + pageName.substring(1);
//			String key = LFWPersTool.getCurrentProject().getName() + "." + LFWPersTool.getCurrentFolderPath() + "." + GENERATElISTENER;
//			ListenerClassInfo listener = (ListenerClassInfo) LfwGlobalEditorInfo.getAttr(key);
//			String packageName  = listener.getPackageName();
//			LfwWidget widget = pm.getWidget("main");
//			String aggVO = getAggvo(widget); 
//			menubarGenerator = new MenubarGenerator(rootPackage, packageName, suffix, pm, aggVO, isBody);
//		}
//		if(billType == null)
//			billType = NCConnector.getBillType(funcnode);
//		menubarGenerator.generateMenubar(pageType, billType);
//	}
	
	/**
	 * ��ȡ��ds��Id
	 * @param widget
	 * @return
	 */
//	private String getMasterDsID(LfwWidget widget) {
//		if(masterId == null){
//			if (null != widget.getViewModels().getDsrelations()) {
//				DatasetRelation[] relationList = widget.getViewModels().getDsrelations().getDsRelations();
//				if (relationList.length > 0) {
//					DatasetRelation relation = relationList[0];
//					String masterDataset = relation.getMasterDataset();
//					masterId =  masterDataset;
//				}
//			}
//			Dataset[] dss = widget.getViewModels().getDatasets();
//			for (int i = 0; i < dss.length; i++) {
//				if(dss[i] instanceof IRefDataset)
//					continue;
//				return dss[i].getId();
//			}
//			masterId =  "";
//		}
//		return masterId;
//	}
	
//	private String masterId;
	
	/**
	 * ��ȡ��aggvo
	 * @param widget
	 * @return
	 */
//	private String getAggvo(LfwWidget widget){
//		String masterId = getMasterDsID(widget);
//		if(masterId != null && !masterId.equals("")){
//			Dataset ds = widget.getViewModels().getDataset(masterId);
//			if(ds != null){
//				String objMeta = ds.getVoMeta();
//				if(objMeta != null){
//					String aggVO = LFWConnector.getAggVO(objMeta);
//					if(aggVO != null && !aggVO.equals(""))
//						return aggVO;
//				}
//			}
//		}
//		return "";
//	}
	
//	private void generatorUI(PageMeta pm){
//		LfwWidget lfwwidget =  pm.getWidget(widget.getId());
//		if(uimetaUtil == null)
//			uimetaUtil = new UIMetaUtil();
//		//����widget��uimeta.um
//		UIMeta widgetMeta = uimetaUtil.getDefaultUIMeta(lfwwidget, isBody);
//		String pmPath = LFWPersTool.getCurrentPageMetaTreeItem().getFile().getAbsolutePath();
//		String folderPath = pmPath + "/" + widget.getId();
//		NCConnector.saveUIMeta(widgetMeta, pmPath, folderPath);
//		//����pm��uimeta.um
//		UIMeta pmMeta = uimetaUtil.getDefaultUIMeta(pm, pageType, isBody);
//		NCConnector.saveUIMeta(pmMeta, pmPath, pmPath);
//	}
	
//	private void generatorQryWidget(PageMeta pm){
//		widget = new LfwWidget();
//		widget.setId("main");
//		widget.setRefId("main");
//		//widget��������ģ��
//		TemplateVO temp = (TemplateVO) qrytempvos.get(0);
//		String nodekey = temp.getNodekey();
//		if(nodekey == null)
//			nodekey = "";
////		widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, nodekey);
//		widget.setFrom("NCQ");
//		if(widgetAction == null)
//			widgetAction = new NewWidgetAction();
//		//����widget
//		String folderPath = LFWPersTool.getCurrentFolderPath();
//		folderPath += "/qry";
//		widgetAction.saveWidget(folderPath, widget);
//		
//		WidgetConfig wconf = new WidgetConfig();
//		wconf.setId(widget.getId());
//		wconf.setRefId(widget.getRefId());
//		// ���ɲ�����widget.wd�ļ�
//		pm.addWidgetConf(wconf);
//		
//		//����pm
//		widgetAction.savePagemeta(folderPath, pm);
//		
//		// ˢ�½ڵ�
//		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//		Tree tree = LFWPersTool.getTree();
//		RefreshNodeAction.refreshNode(view, tree);
//	}
	

	//����widget,widget����nc����ģ��
//	private void generatorWidget(PageMeta pm){
//		widget = new LfwWidget();
//		widget.setId("main");
//		widget.setRefId("main");
//		//widget��������ģ��
////		widget.setExtendAttribute(ExtAttrConstants.BILL_PAGETYPE, pageType);
//		TemplateVO temp = (TemplateVO) billtempvos.get(0);
//		String nodekey = temp.getNodekey();
//		if(nodekey == null)
//			nodekey = "";
////		widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, nodekey);
//		widget.setFrom("NC");
//		if(widgetAction == null)
//			widgetAction = new NewWidgetAction();
//		//����widget
//		widgetAction.saveWidget(widget);
//		
//		WidgetConfig wconf = new WidgetConfig();
//		wconf.setId(widget.getId());
//		wconf.setRefId(widget.getRefId());
//		// ���ɲ�����widget.wd�ļ�
//		pm.addWidgetConf(wconf);
//		pm.addWidget(widget);
//		
//		widgetAction.savePagemeta(pm);
//		
//		// ˢ�½ڵ�
//		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//		Tree tree = LFWPersTool.getTree();
//		RefreshNodeAction.refreshNode(view, tree);
//	}
	
//	private TemplateVO[] getAllTemplateByfunnode(String funnode){
//		if(alltemps == null)
//			alltemps =  NCConnector.getAllTemplateByFuncnode(funnode).toArray(new TemplateVO[0]);
//		return alltemps;
//	}
	
//	private List<TemplateVO>  getAllBillTemplateVOByfunnode(String funnode){
//		List<TemplateVO>  billtemps = new ArrayList<TemplateVO> ();
//		TemplateVO[] alltemps =  getAllTemplateByfunnode(funnode);
//		for (int i = 0; i < alltemps.length; i++) {
//			if(alltemps[i].getTemplatetype().equals(TemplateVO.TEMPLAGE_TYPE_BILL))
//				billtemps.add(alltemps[i]);
//		}
//		return billtemps;
//	}
	
//	private List<TemplateVO> getALlQryTemplateVOByfunnode(String funnode){
//		List<TemplateVO>  qrytemps = new ArrayList<TemplateVO> ();
//		TemplateVO[] alltemps =  getAllTemplateByfunnode(funnode);
//		for (int i = 0; i < alltemps.length; i++) {
//			if(alltemps[i].getTemplatetype().equals(TemplateVO.TEMPLAGE_TYPE_QUERY))
//				qrytemps.add(alltemps[i]);
//		}
//		return qrytemps;
//	}
//	
//	private List<TemplateVO>  getALlPrintTemplateVOByfunnode(String funnode){
//		List<TemplateVO>  printTemsp = new ArrayList<TemplateVO> ();
//		TemplateVO[] alltemps =  getAllTemplateByfunnode(funnode);
//		for (int i = 0; i < alltemps.length; i++) {
//			if(alltemps[i].getTemplatetype().equals(TemplateVO.TEMPLAGE_TYPE_PRINT))
//				printTemsp.add(alltemps[i]);
//		}
//		return printTemsp;
//	}
	
	public PageMeta getPm() {
		return pm;
	}
	public void setPm(PageMeta pm) {
		this.pm = pm;
	}
	
}
