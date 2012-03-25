package nc.uap.lfw.perspective.action;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;

import org.eclipse.jface.action.Action;

/**
 *  nc模式化生成
 *  1.选择页面类型
 *  2.生成mainwidget
 * @author zhangxya
 *
 */
public class NcPatternGenerator  extends Action {

	//得到节点关联的单据模板
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
//			MessageDialog.openError(null, "提示", "未设置功能节点,无法进行NC模式化生成!");
//			return;
//		}
//		else {
//			String funnode = funnodeAttr.getValue().toString();
//			//此节点关联的模板
//			billtempvos = getAllBillTemplateVOByfunnode(funnode);
//			if(billtempvos == null || billtempvos.size() == 0){
//				MessageDialog.openError(null, "提示", "此节点未关联模板,无法进行NC模式化生成!");
//				return;
//			}
//			if(billtempvos.size() > 1){
//				MessageDialog.openError(null, "提示", "NC关联单据模板多于一个,无法进行NC模式化生成!");
//				return;
//			}
//			else{
//				//查询模板，如果没有设置查询模板或者打印模板，给出提示是否继续？
//				if(qrytempvos == null)
//					qrytempvos = getALlQryTemplateVOByfunnode(funnode);
//				boolean qryFlag = true;
//				if(qrytempvos == null || qrytempvos.size() == 0)
//					qryFlag = MessageDialog.openConfirm(null, "提示", "没有设置查询模板,是否继续?");
//				if(!qryFlag)
//					return;
//				if(printtemvos == null)
//					printtemvos = getALlPrintTemplateVOByfunnode(funnode);
//				boolean printFlag = true;
//				if(printtemvos == null || printtemvos.size() == 0)
//					printFlag = MessageDialog.openConfirm(null, "提示", "没有设置打印模板,是否继续?");
//				if(!printFlag)
//					return;
//				if(qryFlag && printFlag){
//					//页面类型选择对话框
//					PageTypeSelectedDialog pagetypeDialog = new PageTypeSelectedDialog(null);
//					if(pagetypeDialog.open() == pagetypeDialog.OK){
//						//页面类型
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
//							//生成widget并且关联nc单据模板
//							generatorWidget(pm);
//							//生成菜单
//							pm = LFWPersTool.getCurrentPageMeta();
//							generatorMenubar(pm,funnode);
//							
//							
//							pm = LFWPersTool.getCurrentPageMeta();
//							//widgetAction.saveWidget(pm.getWidget("main"));
//							//保存pm
//							widgetAction.savePagemeta(pm);
//							//生成ui
//							generatorUI(pm);
//							//如果节点只关联了一个查询模板，则默认生成qry节点
//	//							if(qrytempvos == null)
//	//								qrytempvos = getALlQryemplateVOByfunnode(funnode);
//							if(qrytempvos.size() == 1){
//								generatorQryNode(funnode);
//							}
//							
//							// 刷新节点
//							LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//							Tree tree = LFWPersTool.getTree();
//							RefreshNodeAction.refreshNode(view, tree);
//						}catch(Exception e){
//							MainPlugin.getDefault().logError(e.getMessage(), e);
//							MessageDialog.openError(null, "错误", e.getMessage());
//						}
//						
//					}
//				}
//			}
//		}
	}

	public static final String DEFAULT_PROCESSOR_CLASS_NAME = "nc.uap.lfw.core.processor.EventRequestProcessor";
	
	//默认生成查询节点
//	private void generatorQryNode(String funnode){
//		PageMeta pagemeta = new PageMeta();
//		pagemeta.setId("qry");
//		pagemeta.setProcessorClazz(DEFAULT_PROCESSOR_CLASS_NAME);
////		pagemeta.setExtendAttribute(ExtAttrConstants.FUNC_CODE, funnode);
//		if(widgetAction == null)
//			widgetAction = new NewWidgetAction();
//		Tree tree = LFWPersTool.getTree();
////		RefreshNodeGroupAction.refreshNodes(tree, LFWPersTool.getCurrentProject());
////		//生成菜单
////		generatorMenubar(pagemeta);
//		
//		String folderPath = LFWPersTool.getCurrentFolderPath();
//		folderPath += "/qry";
//		//保存pm
//		widgetAction.savePagemeta(folderPath, pagemeta);
//		
//		NCConnector.refreshNode();
//		// 刷新节点
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
//		//生成ui
//		//generatorUI(pagemeta);
//		//生成mainwidget
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
	 * 获取主ds的Id
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
	 * 获取主aggvo
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
//		//生成widget的uimeta.um
//		UIMeta widgetMeta = uimetaUtil.getDefaultUIMeta(lfwwidget, isBody);
//		String pmPath = LFWPersTool.getCurrentPageMetaTreeItem().getFile().getAbsolutePath();
//		String folderPath = pmPath + "/" + widget.getId();
//		NCConnector.saveUIMeta(widgetMeta, pmPath, folderPath);
//		//生成pm的uimeta.um
//		UIMeta pmMeta = uimetaUtil.getDefaultUIMeta(pm, pageType, isBody);
//		NCConnector.saveUIMeta(pmMeta, pmPath, pmPath);
//	}
	
//	private void generatorQryWidget(PageMeta pm){
//		widget = new LfwWidget();
//		widget.setId("main");
//		widget.setRefId("main");
//		//widget关联单据模板
//		TemplateVO temp = (TemplateVO) qrytempvos.get(0);
//		String nodekey = temp.getNodekey();
//		if(nodekey == null)
//			nodekey = "";
////		widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, nodekey);
//		widget.setFrom("NCQ");
//		if(widgetAction == null)
//			widgetAction = new NewWidgetAction();
//		//保存widget
//		String folderPath = LFWPersTool.getCurrentFolderPath();
//		folderPath += "/qry";
//		widgetAction.saveWidget(folderPath, widget);
//		
//		WidgetConfig wconf = new WidgetConfig();
//		wconf.setId(widget.getId());
//		wconf.setRefId(widget.getRefId());
//		// 生成并保存widget.wd文件
//		pm.addWidgetConf(wconf);
//		
//		//保存pm
//		widgetAction.savePagemeta(folderPath, pm);
//		
//		// 刷新节点
//		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
//		Tree tree = LFWPersTool.getTree();
//		RefreshNodeAction.refreshNode(view, tree);
//	}
	

	//生成widget,widget关联nc单据模板
//	private void generatorWidget(PageMeta pm){
//		widget = new LfwWidget();
//		widget.setId("main");
//		widget.setRefId("main");
//		//widget关联单据模板
////		widget.setExtendAttribute(ExtAttrConstants.BILL_PAGETYPE, pageType);
//		TemplateVO temp = (TemplateVO) billtempvos.get(0);
//		String nodekey = temp.getNodekey();
//		if(nodekey == null)
//			nodekey = "";
////		widget.setExtendAttribute(ExtAttrConstants.BILL_NODEKEY, nodekey);
//		widget.setFrom("NC");
//		if(widgetAction == null)
//			widgetAction = new NewWidgetAction();
//		//保存widget
//		widgetAction.saveWidget(widget);
//		
//		WidgetConfig wconf = new WidgetConfig();
//		wconf.setId(widget.getId());
//		wconf.setRefId(widget.getRefId());
//		// 生成并保存widget.wd文件
//		pm.addWidgetConf(wconf);
//		pm.addWidget(widget);
//		
//		widgetAction.savePagemeta(pm);
//		
//		// 刷新节点
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
