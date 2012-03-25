package nc.lfw.jsp.editor;

import nc.uap.lfw.core.base.ExtAttribute;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.design.itf.FuncNodeVO;
import nc.uap.lfw.jsp.uimeta.UIFlowvLayout;
import nc.uap.lfw.jsp.uimeta.UIFlowvPanel;
import nc.uap.lfw.jsp.uimeta.UIMenubarComp;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UIWidget;

public class UIMetaUtil {
//	private static final String MENU_CARD = "menu_card";
	private static final String MENU_LIST = "menu_list";
	private static final String MAIN_WIDGET = "main";
//	private static final String TAILSPLITER_SIZE = "Tail_DivideSize";
	public static UIMeta getDefaultUIMeta(LfwWidget widget, boolean isBody) {
		UIMeta meta = new UIMeta();
		if(widget.getFrom() != null && widget.getFrom().equals("NC")){
			//获取uimeta的动态生成uimeta的类
			ExtAttribute geneClassAttr = widget.getExtendAttribute(UIMeta.GENERATECLASS);
			if(geneClassAttr != null){
				String geneClass = (String) geneClassAttr.getValue();
				meta.setAttribute(UIMeta.GENERATECLASS, geneClass);
				meta.setAttribute(UIMeta.TABBODY, isBody);
				return meta;
			}
			
//			ExtAttribute attr = widget.getExtendAttribute(ExtAttrConstants.BILL_PAGETYPE);
//			int pageType = Integer.parseInt((String.valueOf(attr.getValue())));
//			//List 主子
//			if(pageType == FuncNodeVO.PAGETYPE_LIST_MASCHI){
//				UISplitter spliter = new UISplitter();
//				spliter.setId("spliter_list");
//				spliter.setOrientation(0);  //vertical
//				spliter.setDivideSize("50"); //%50
//				spliter.setWidgetId(widget.getId());
//				UISplitterOne one = new UISplitterOne();
//				spliter.addPanel(one);
//				
//				UISplitterTwo two = new UISplitterTwo();
//				spliter.addPanel(two);
//				
//				UITabComp[] tabs = (UITabComp[]) widget.getExtendAttribute("NCTABS").getValue();
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_list")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						one.setElement(tab);
//					}
//					else if(t.getId().startsWith("bodyTab_list")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, isBody);
//						two.setElement(tab);
//					}
//					else if(t.getId().startsWith("tailTab_list")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						two.setElement(tab);
//					}
//				}
//				meta.setElement(spliter);
//			}
//			//card主子
//			else if(pageType == ExtAttrConstants.PAGETYPE_CARD_MASCHI){
//				UISplitter spliter = new UISplitter();
//				spliter.setId("spliter_card");
//				spliter.setOrientation(0);  //vertical
//				spliter.setDivideSize("50"); //%50
//				UISplitterOne one = new UISplitterOne();
//				spliter.addPanel(one);
//				spliter.setWidgetId(widget.getId());
//				UISplitterTwo two = new UISplitterTwo();
//				spliter.addPanel(two);
//				
//				UITabComp[] tabs = (UITabComp[]) widget.getExtendAttribute("NCTABS").getValue();
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_card")){
//						UITabComp uitab = new UITabComp();
//						uitab.setId(t.getId());
//						uitab.setWidgetId(widget.getId());
//						generateChild(t, uitab, widget, false);
//						one.setElement(uitab);
//					}
//					else if(t.getId().startsWith("bodyTab_card")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, isBody);
//						two.setElement(tab);
//					}
//					else if(t.getId().startsWith("tailTab_card")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						two.setElement(tab);
//						if(t.getAttribute(TAILSPLITER_SIZE) != null){
//							String size = (String)t.getAttribute(TAILSPLITER_SIZE);
//							spliter.setInverse(1);
//							spliter.setDivideSize(size);
//							spliter.setBoundMode(1);
//						}
//					}
//				}
//				meta.setElement(spliter);
//			}
//			//管理主子卡片优先，管理主子列表优先
//			else if(pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST || pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST){
//				UITabComp[] tabs = (UITabComp[]) widget.getExtendAttribute("NCTABS").getValue();
//				boolean hasTail = hasTail(tabs);
//				UICardLayout card = new UICardLayout();
//				card.setId("manageCard");
//				card.setWidgetId(widget.getId());
//				
//				UICardPanel cardcard = new UICardPanel();
//				cardcard.setId("cardcard");
//				
//				UICardPanel cardlist = new UICardPanel();
//				cardlist.setId("cardlist");
//				
//				if(pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST){
//					card.setCurrentItem("cardcard");
//					card.addPanel(cardcard);
//					card.addPanel(cardlist);
//				}
//				else{
//					card.setCurrentItem("cardlist");
//					card.addPanel(cardlist);
//					card.addPanel(cardcard);
//				}
//				
//				UISplitter cardSplitter = new UISplitter();
//				cardSplitter.setId("spliter_card");
//				cardSplitter.setOrientation(0);  //vertical
//				if(hasTail)
//					cardSplitter.setDivideSize("40"); //%50
//				else
//					cardSplitter.setDivideSize("50");
//				UISplitterOne cardOne = new UISplitterOne();
//				cardSplitter.addPanel(cardOne);
//				cardSplitter.setWidgetId(widget.getId());
//				
//				UISplitterTwo cardTwo = new UISplitterTwo();
//				cardSplitter.addPanel(cardTwo);
//				cardcard.setElement(cardSplitter);
//				
//				
//				UISplitterOne cardTailOne = null;
//				UISplitterTwo cardTailTwo = null;
//				UISplitter cardTailSpliter = new UISplitter();
//				if(hasTail){
//					cardTailSpliter.setId("spliter_card_tail");
//					cardTailSpliter.setOrientation(0);  //vertical
//					cardTailSpliter.setDivideSize("70"); //%50
//					cardTailOne = new UISplitterOne();
//					cardTailSpliter.addPanel(cardTailOne);
//					cardTailSpliter.setWidgetId(widget.getId());
//					
//					cardTailTwo = new UISplitterTwo();
//					cardTailSpliter.addPanel(cardTailTwo);
//					
//					cardTwo.setElement(cardTailSpliter);
//				}
//				
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_card")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						cardOne.setElement(tab);
//						tab.setOneTabHide(t.getOneTabHide());
//					}
//					else if(t.getId().startsWith("bodyTab_card")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, isBody);
//						if(hasTail)
//							cardTailOne.setElement(tab);
//						else
//							cardTwo.setElement(tab);
//						tab.setOneTabHide(t.getOneTabHide());
//					}
//					
//					else if(t.getId().startsWith("tailTab_card")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget,false);
//						if(t.getAttribute(TAILSPLITER_SIZE) != null){
//							String size = (String)t.getAttribute(TAILSPLITER_SIZE);
//							cardTailSpliter.setInverse(1);
//							cardTailSpliter.setDivideSize(size);
//							cardTailSpliter.setBoundMode(1);
//							cardTailTwo.setElement(tab);
//						}
//						tab.setOneTabHide(t.getOneTabHide());
//					}
//				}
//				
//				UISplitter spliter_list = new UISplitter();
//				spliter_list.setId("spliter_list");
//				spliter_list.setOrientation(0);  //vertical
//				spliter_list.setDivideSize("50"); //%50
//				UISplitterOne listOne = new UISplitterOne();
//				spliter_list.addPanel(listOne);
//				spliter_list.setWidgetId(widget.getId());
//				
//				UISplitterTwo listTwo = new UISplitterTwo();
//				spliter_list.addPanel(listTwo);
//				cardlist.setElement(spliter_list);
//				
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_list")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						listOne.setElement(tab);
//						tab.setOneTabHide(t.getOneTabHide());
//					}
//					else if(t.getId().startsWith("bodyTab_list")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						listTwo.setElement(tab);
//						tab.setOneTabHide(t.getOneTabHide());
//					}
////					else if(t.getId().startsWith("tailTab_list")){
////						UITabComp tab = new UITabComp();
////						tab.setId(t.getId());
////						tab.setWidgetId(widget.getId());
////						generateChild(t, tab, widget);
////						listTwo.setElement(tab);
////					}
//				}
//				
//				meta.setElement(card);
//			}
//			
//			else if(pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST || pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST){
//				UITabComp[] tabs = (UITabComp[]) widget.getExtendAttribute("NCTABS").getValue();
//				UICardLayout card = new UICardLayout();
//				card.setId("manageCard");
//				card.setWidgetId(widget.getId());
//				
//				UICardPanel cardcard = new UICardPanel();
//				cardcard.setId("cardcard");
//				
//				UICardPanel cardlist = new UICardPanel();
//				cardlist.setId("cardlist");
//				
//				if(pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST){
//					card.setCurrentItem("cardcard");
//					card.addPanel(cardcard);
//					card.addPanel(cardlist);
//				}
//				else{
//					card.setCurrentItem("cardlist");
//					card.addPanel(cardlist);
//					card.addPanel(cardcard);
//				}
//				
//				boolean onHead = false;
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_card")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget,false);
//						cardcard.setElement(tab);
//						onHead = true;
//					}
//				}
//				
//				if(onHead == false){
//					for (int i = 0; i < tabs.length; i++) {
//						UITabComp t = tabs[i];
//						if(t.getId().startsWith("bodyTab_card")){
//							UITabComp tab = new UITabComp();
//							tab.setId(t.getId());
//							tab.setWidgetId(widget.getId());
//							generateChild(t, tab, widget, false);
//							cardcard.setElement(tab);
//							onHead = true;
//						}
//					}
//				}
//				
//				onHead = false;
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_list")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						cardlist.setElement(tab);
//						onHead = true;
//					}
//				}
//				
//				if(onHead == false){
//					for (int i = 0; i < tabs.length; i++) {
//						UITabComp t = tabs[i];
//						if(t.getId().startsWith("bodyTab_list")){
//							UITabComp tab = new UITabComp();
//							tab.setId(t.getId());
//							tab.setWidgetId(widget.getId());
//							generateChild(t, tab, widget, false);
//							cardlist.setElement(tab);
//							onHead = true;
//						}
//					}
//				}
//				meta.setElement(card);
//			}
//			
//			//card单表头
//			else if(pageType == FuncNodeVO.PAGETYPE_CARD_SINGAL){
//				UIBorderLayout borderLayout = new UIBorderLayout();
//				UIBorderPanel centerPanel = new UIBorderPanel();
//				centerPanel.setPosition("center");
//				borderLayout.addPanel(centerPanel);
//				meta.setElement(borderLayout);
//				
//				UITabComp[] tabs = (UITabComp[]) widget.getExtendAttribute("NCTABS").getValue();
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_card")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						centerPanel.setElement(tab);
//					}
//				}
//			}
//			
//			else if(pageType == FuncNodeVO.PAGETYPE_LIST_SINGAL){
//				UIBorderLayout borderLayout = new UIBorderLayout();
//				UIBorderPanel centerPanel = new UIBorderPanel();
//				centerPanel.setPosition("center");
//				borderLayout.addPanel(centerPanel);
//				meta.setElement(borderLayout);
//				
//				UITabComp[] tabs = (UITabComp[]) widget.getExtendAttribute("NCTABS").getValue();
//				for (int i = 0; i < tabs.length; i++) {
//					UITabComp t = tabs[i];
//					if(t.getId().startsWith("headTab_list")){
//						UITabComp tab = new UITabComp();
//						tab.setId(t.getId());
//						tab.setWidgetId(widget.getId());
//						generateChild(t, tab, widget, false);
//						centerPanel.setElement(tab);
//					}
//				}
//			}
//			else{
//				UIBorderLayout borderLayout = new UIBorderLayout();
//				UIBorderPanel centerPanel = new UIBorderPanel();
//				centerPanel.setPosition("center");
//				borderLayout.addPanel(centerPanel);
//				meta.setElement(borderLayout);
//			}
		}
		else{
//			UIBorderLayout borderLayout = new UIBorderLayout();
//			UIBorderPanel centerPanel = new UIBorderPanel();
//			centerPanel.setPosition("center");
//			borderLayout.addPanel(centerPanel);
//			meta.setElement(borderLayout);
		}
		return meta;
	}
	
//	private static boolean hasTail(UITabComp[] tabs) {
//		for (int i = 0; i < tabs.length; i++) {
//			if(tabs[i].getId().startsWith("tailTab_"))
//				return true;
//		}
//		return false;
//	}
	
//	private static GroupLayout[] getFieldSetByTab(LfwWidget widget, UITabComp tab, UITabItem item) {
//		GroupLayout[] fss = (GroupLayout[]) widget.getExtendAttributeValue("NCFS");
//		if(fss != null){
//			List<GroupLayout> fsList = new ArrayList<GroupLayout>();
//			for (int i = 0; i < fss.length; i++) {
//				String baseTab = (String) fss[i].getExtendAttributeValue("basetab");
//				String baseTabItem = (String) fss[i].getExtendAttributeValue("basetabitem");
//				if(baseTab.equals(tab.getId()) && baseTabItem.equals(item.getId())){
//					fsList.add(fss[i]);
//				}
//			}
//			return fsList.toArray(new GroupLayout[0]);
//		}
//		return null;
//	}
	
//	private static void generateChild(UITabComp tab, UITabComp uitab, LfwWidget widget, boolean isBody){
//		List<UILayoutPanel> list = tab.getPanelList();
//		Iterator<UILayoutPanel> it = list.iterator();
//		ViewComponents viewcomps = widget.getViewComponents();
//		while(it.hasNext()){
//			UILayoutPanel panel = it.next();
//			if(panel instanceof UITabItem){
//				UITabItem item = (UITabItem)panel;
//				
//				UITabItem uiitem = new UITabItem();
//				uiitem.setId(item.getId());
//				uiitem.setText(item.getText());
//				uitab.addPanel(uiitem);
//				
//				UIBorder border = new UIBorder();
//				UIBorderTrue bt = new UIBorderTrue();
//				border.addPanel(bt);
//				
//				uiitem.setElement(border);
//				
//				GroupLayout[] fss = getFieldSetByTab(widget, tab, item);
//				UIFlowvLayout fvl = null;
//				if(fss != null && fss.length > 0){
//					fvl = new UIFlowvLayout();
//					bt.setElement(fvl);
//				}
//				if(fvl != null)
//					fvl.setAutoFill(new Integer(1));
//				WebComponent comp = viewcomps.getComponent(tab.getId() + "_" + item.getId() + "_grid");
//				if(comp == null){
//					comp = viewcomps.getComponent(tab.getId() + "_" + item.getId() + "_form");
//					if(tab.getId().startsWith("tailTab_card")){
//						if(comp instanceof FormComp){
//							FormComp formComp = (FormComp) comp;
//							int size = formComp.getElementList().size();
//							int height = 0;
//							if(size <= 3){
//								height = 40;
//							}
//							else{
//								int rowColumn = size/3;
//								if(size%3 != 0)
//									rowColumn += 1;
//								height = formComp.getRowHeight() * rowColumn + 10;
//							}
//							tab.setAttribute(TAILSPLITER_SIZE, String.valueOf(height));
//						}	
//					}
//				}
//				if(comp != null){
//					UIComponent uigrid = null;
//					if(comp instanceof GridComp)
//						uigrid = new UIGridComp();
//					else
//						uigrid = new UIFormComp();
//					uigrid.setId(comp.getId());
//					uigrid.setWidgetId(widget.getId());
//					if(fvl != null){
//						UIFlowvPanel flp = new UIFlowvPanel();
//						fvl.addPanel(flp);
//						flp.setElement(uigrid);
//					}
//					else
//						bt.setElement(uigrid);
//				}
//				
//				if(fvl != null){
//					for (int i = 0; i < fss.length; i++) {
//						GroupLayout f = fss[i];
//						UIGroup g = new UIGroup();
//						g.setId(f.getId());
//						g.setText(f.getText());
//						
//						g.setMarginTop("5");
//						
//						UIGroupPanel gp = new UIGroupPanel();
//						g.addPanel(gp);
//						
//						WebComponent c = viewcomps.getComponent(tab.getId() + "_" + g.getId() + "_grid");
//						if(c == null)
//							c = viewcomps.getComponent(tab.getId() + "_" + g.getId() + "_form");
//						if(c != null){
//							UIComponent uigrid = null;
//							if(comp instanceof GridComp)
//								uigrid = new UIGridComp();
//							else
//								uigrid = new UIFormComp();
//							uigrid.setId(c.getId());
//							uigrid.setWidgetId(widget.getId());
//							
//							gp.setElement(uigrid);
//						}
//						UIFlowvPanel flp = new UIFlowvPanel();
//						fvl.addPanel(flp);
//						flp.setElement(g);
//					}
//				}
//			}
//		}
//		if(isBody){
//			UITabRightPanelPanel rightPanel = new UITabRightPanelPanel();
//			rightPanel.setWidth("220");
//			uitab.setRightPanel(rightPanel);
//			UIMenubarComp menubar = new UIMenubarComp();
//			menubar.setId("bodyMenubar");
//			rightPanel.setElement(menubar);
//		}
//	}
	
	public static UIMeta getDefaultUIMeta(PageMeta pm, int pageType, boolean isBody) {
		UIMeta meta = new UIMeta();
		//list主子
		if(pageType == FuncNodeVO.PAGETYPE_LIST_MASCHI || pageType == FuncNodeVO.PAGETYPE_LIST_SINGAL){
			//List主子
				UIFlowvLayout flowvLayout = new UIFlowvLayout();
				UIFlowvPanel flowvPanelOne = new UIFlowvPanel();
				flowvPanelOne.setHeight("25");
				flowvLayout.addPanel(flowvPanelOne);
				UIMenubarComp menebarList = new UIMenubarComp();
				menebarList.setId(MENU_LIST);
				flowvPanelOne.setElement(menebarList);
				
				UIFlowvPanel flowvPanelTwo = new UIFlowvPanel();
				flowvLayout.addPanel(flowvPanelTwo);
				
				UIWidget widget = new UIWidget();
				widget.setId(MAIN_WIDGET);
				flowvPanelTwo.setElement(widget);
				meta.setElement(flowvLayout);
		}
		//card主子
//		else if(pageType == ExtAttrConstants.PAGETYPE_CARD_MASCHI || pageType == ExtAttrConstants.PAGETYPE_CARD_SINGAL){
//			UIFlowvLayout flowvLayout = new UIFlowvLayout();
//			UIFlowvPanel flowvPanelOne = new UIFlowvPanel();
//			flowvPanelOne.setHeight("25");
//			flowvLayout.addPanel(flowvPanelOne);
//			UIMenubarComp menebarList = new UIMenubarComp();
//			menebarList.setId(MENU_CARD);
//			flowvPanelOne.setElement(menebarList);
//			UIFlowvPanel flowvPanelTwo = new UIFlowvPanel();
//			flowvLayout.addPanel(flowvPanelTwo);
//			UIWidget widget = new UIWidget();
//			widget.setId(MAIN_WIDGET);
//			flowvPanelTwo.setElement(widget);
//			meta.setElement(flowvLayout);
//		}
//		//管理主子，卡片优先
//		else if(pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_CARDFIRST || 
//				pageType == ExtAttrConstants.PAGETYPE_MANAGE_MASCHI_LISTFIRST || 
//				pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_CARDFIRST ||
//				pageType == ExtAttrConstants.PAGETYPE_MANAGE_SINGAL_LISTFIRST){
//			UIFlowvLayout flowvLayout = new UIFlowvLayout();
//			UIFlowvPanel flowvPanelOne = new UIFlowvPanel();
//			flowvPanelOne.setHeight("25");
//			flowvLayout.addPanel(flowvPanelOne);
//			
//			UIMenuGroup menugroup = new UIMenuGroup();
//			flowvPanelOne.setElement(menugroup);
//				
//			UIMenuGroupItem cardMenuItem = new UIMenuGroupItem();
//			cardMenuItem.setState(1);
//			UIMenubarComp cardMenubar = new UIMenubarComp();
//			cardMenubar.setId(MENU_CARD);
//			cardMenuItem.setElement(cardMenubar);
//			menugroup.addPanel(cardMenuItem);
//			
//			UIMenuGroupItem listMenuItem = new UIMenuGroupItem();
//			listMenuItem.setState(2);
//			UIMenubarComp listMenubar = new UIMenubarComp();
//			listMenubar.setId(MENU_LIST);
//			listMenuItem.setElement(listMenubar);
//			menugroup.addPanel(listMenuItem);
//			
//			UIFlowvPanel flowvPanelTwo = new UIFlowvPanel();
//			flowvLayout.addPanel(flowvPanelTwo);
//			UIWidget widget = new UIWidget();
//			widget.setId(MAIN_WIDGET);
//			flowvPanelTwo.setElement(widget);
//			meta.setElement(flowvLayout);
//		}
//
//		//所有单表头，只需要widget
//		else if(false){
//			UIBorderLayout borderLayout = new UIBorderLayout();
//			UIBorderPanel centerPanel = new UIBorderPanel();
//			centerPanel.setPosition("center");
//			borderLayout.addPanel(centerPanel);
//			UIWidget widget = new UIWidget();
//			widget.setId(MAIN_WIDGET);
//			centerPanel.setElement(widget);
//			meta.setElement(borderLayout);
//		}
		else{
//			UIBorderLayout borderLayout = new UIBorderLayout();
//			UIBorderPanel centerPanel = new UIBorderPanel();
//			centerPanel.setPosition("center");
//			borderLayout.addPanel(centerPanel);
//			meta.setElement(borderLayout);
		}
		return meta;
	}
}
