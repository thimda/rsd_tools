/**
 * 
 */
package nc.uap.lfw.editor.common.tools;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.comp.ContextMenuComp;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewComponents;
import nc.uap.lfw.core.page.ViewMenus;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.refnode.BaseRefNode;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.jsp.uimeta.UIElement;

/**
 * @author chouhl
 * 2011-11-11
 */
public class ViewEventTool {
	
	/**
	 * ��ȡView�����¼����ϣ�����View�и����ؼ�,UIMeta�¼���
	 * @param viewConf
	 * @return
	 */
	public static EventConf[] getAllEvents(LfwWidget viewConf, UIElement uielement){
		//�¼�List����
		List<EventConf> ecList= new ArrayList<EventConf>();
		if(viewConf != null){
			//Component
			ViewComponents vc = viewConf.getViewComponents();
			//Model
			ViewModels vm = viewConf.getViewModels();
			//�˵�
			ViewMenus vmenu = viewConf.getViewMenus();	
			//View�¼�����
			EventConf[] events = viewConf.getEventConfs();
			if(events != null && events.length > 0){
				for(EventConf event : events){
					ecList.add(event);
				}
			}
			//WebԪ�ؼ���
			WebElement[] wcs = null;
			//�ؼ�Ԫ�ؼ���
			wcs = vc.getComponents();
			//�ؼ�Ԫ���¼�����
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					EventConf[] ecs = wc.getEventConfs();
					if(ecs != null && ecs.length > 0){
						for(EventConf ec : ecs){
							ecList.add(ec);
						}
					}
				}
			}
			//�������ݼ� ����
			wcs = vm.getComboDatas();
			//�������ݼ��¼�����
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					EventConf[] ecs = wc.getEventConfs();
					if(ecs != null && ecs.length > 0){
						for(EventConf ec : ecs){
							ecList.add(ec);
						}
					}
				}
			}
			//���ݼ� ����
			wcs = vm.getDatasets();
			//���ݼ��¼�����
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					EventConf[] ecs = wc.getEventConfs();
					if(ecs != null && ecs.length > 0){
						for(EventConf ec : ecs){
							ecList.add(ec);
						}
					}
				}
			}
			//���ռ���
			IRefNode[] rns = vm.getRefNodes();
			//�����¼�����
			if(rns != null && rns.length > 0){
				for(IRefNode rn : rns){
					if(rn instanceof BaseRefNode){
						EventConf[] ecs = ((BaseRefNode)rn).getEventConfs();
						if(ecs != null && ecs.length > 0){
							for(EventConf ec : ecs){
								ecList.add(ec);
							}
						}
					}
				}
			}
			//�˵�����
			MenubarComp[] mcs = vmenu.getMenuBars();
			//�˵����
			wcs = null;
			if(mcs != null && mcs.length > 0){
				List<WebElement> weList = new ArrayList<WebElement>();
				for(MenubarComp comp : mcs){
					if(comp.getMenuList() != null){
						List<MenuItem> miList = comp.getMenuList();
						for(MenuItem mi : miList){
							weList.addAll(getMenuItems(mi));
						}
					}
				}
				wcs = weList.toArray(new WebElement[0]);
			}
			//�˵����¼�����
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					EventConf[] ecs = wc.getEventConfs();
					if(ecs != null && ecs.length > 0){
						for(EventConf ec : ecs){
							ecList.add(ec);
						}
					}
				}
			}
			//�Ҽ��˵�����
			ContextMenuComp[] cmcs = vmenu.getContextMenus();
			//�Ҽ��˵����
			wcs = null;
			if(cmcs != null && cmcs.length > 0){
				List<WebElement> weList = new ArrayList<WebElement>();
				for(ContextMenuComp comp : cmcs){
					if(comp.getItemList() != null){
						List<MenuItem> miList = comp.getItemList();
						for(MenuItem mi : miList){
							weList.addAll(getMenuItems(mi));
						}
					}
				}
				wcs = weList.toArray(new WebElement[0]);
			}
			//�Ҽ��˵����¼�����
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					EventConf[] ecs = wc.getEventConfs();
					if(ecs != null && ecs.length > 0){
						for(EventConf ec : ecs){
							ecList.add(ec);
						}
					}
				}
			}
		}
		//UIMeta�¼�
		if(uielement != null){
			EventConf[] eventConfs = uielement.getEventConfs();
			if(eventConfs != null && eventConfs.length > 0){
				for(EventConf event : eventConfs){
					ecList.add(event);
				}
			}
		}
		//View���������¼�����
		return ecList.toArray(new EventConf[0]);
	}
	
	/**
	 * �Ƴ�View�����¼�������View�и����ؼ���
	 * @param viewConf
	 * @return
	 */
	public static void removeEvent(LfwWidget viewConf, String eventName, String methodName){
		//Component
		ViewComponents vc = viewConf.getViewComponents();
		//Model
		ViewModels vm = viewConf.getViewModels();
		//�˵�
		ViewMenus vmenu = viewConf.getViewMenus();

		viewConf.removeEventConf(eventName, methodName);
		
		//WebԪ�ؼ���
		WebElement[] wcs = null;
		//�ؼ�Ԫ�ؼ���
		wcs = vc.getComponents();
		//�ؼ�Ԫ���¼�����
		if(wcs != null && wcs.length > 0){
			for(WebElement wc : wcs){
				wc.removeEventConf(eventName, methodName);
			}
		}
		//�������ݼ� ����
		wcs = vm.getComboDatas();
		//�������ݼ��¼�����
		if(wcs != null && wcs.length > 0){
			for(WebElement wc : wcs){
				wc.removeEventConf(eventName, methodName);
			}
		}
		//���ݼ� ����
		wcs = vm.getDatasets();
		//���ݼ��¼�����
		if(wcs != null && wcs.length > 0){
			for(WebElement wc : wcs){
				wc.removeEventConf(eventName, methodName);
			}
		}
		//���ռ���
		IRefNode[] rns = vm.getRefNodes();
		//�����¼�����
		if(rns != null && rns.length > 0){
			for(IRefNode rn : rns){
				if(rn instanceof BaseRefNode){
					((BaseRefNode)rn).removeEventConf(eventName, methodName);
				}
			}
		}
		//�˵�����
		MenubarComp[] mcs = vmenu.getMenuBars();
		//�˵����
		if(mcs != null && mcs.length > 0){
			List<WebElement> weList = new ArrayList<WebElement>();
			for(MenubarComp comp : mcs){
				if(comp.getMenuList() != null){
					List<MenuItem> miList = comp.getMenuList();
					for(MenuItem mi : miList){
						weList.addAll(getMenuItems(mi));
					}
				}
			}
			wcs = weList.toArray(new WebElement[0]);
		}
		//�˵����¼�����
		if(wcs != null && wcs.length > 0){
			for(WebElement wc : wcs){
				wc.removeEventConf(eventName, methodName);
			}
		}
		//�Ҽ��˵�����
		ContextMenuComp[] cmcs = vmenu.getContextMenus();
		//�Ҽ��˵����
		if(cmcs != null && cmcs.length > 0){
			List<WebElement> weList = new ArrayList<WebElement>();
			for(ContextMenuComp comp : cmcs){
				if(comp.getItemList() != null){
					List<MenuItem> miList = comp.getItemList();
					for(MenuItem mi : miList){
						weList.addAll(getMenuItems(mi));
					}
				}
			}
			wcs = weList.toArray(new WebElement[0]);
		}
		//�Ҽ��˵����¼�����
		if(wcs != null && wcs.length > 0){
			for(WebElement wc : wcs){
				wc.removeEventConf(eventName, methodName);
			}
		}
	}
	
	private static List<MenuItem> getMenuItems(MenuItem mi){
		List<MenuItem> miList = new ArrayList<MenuItem>();
		if(mi != null){
			miList.add(mi);
			List<MenuItem> childList = mi.getChildList();
			if(childList != null && childList.size() > 0){
				for(MenuItem item : childList){
					miList.add(item);
					miList.addAll(getMenuItems(item));
				}
			}
		}
		return miList;
	}
	
	public static WebElement getWebElementById(LfwWidget viewConf, String id){
		if(viewConf != null && id != null){
			//Component
			ViewComponents vc = viewConf.getViewComponents();
			//Model
			ViewModels vm = viewConf.getViewModels();
			//�˵�
			ViewMenus vmenu = viewConf.getViewMenus();
			
			//WebԪ�ؼ���
			WebElement[] wcs = null;
			//�ؼ�Ԫ�ؼ���
			wcs = vc.getComponents();
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					if(id.equals(wc.getId())){
						return wc;
					}
				}
			}
			//�������ݼ� ����
			wcs = vm.getComboDatas();
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					if(id.equals(wc.getId())){
						return wc;
					}
				}
			}
			//���ݼ� ����
			wcs = vm.getDatasets();
			if(wcs != null && wcs.length > 0){
				for(WebElement wc : wcs){
					if(id.equals(wc.getId())){
						return wc;
					}
				}
			}
			//���ռ���
			IRefNode[] rns = vm.getRefNodes();
			if(rns != null && rns.length > 0){
				for(IRefNode rn : rns){
					if(id.equals(rn.getId()) && rn instanceof BaseRefNode){
						return (BaseRefNode)rn;
					}
				}
			}
			//�˵�����
			MenubarComp[] mcs = vmenu.getMenuBars();
			if(mcs != null && mcs.length > 0){
				//�˵����
				List<WebElement> weList = new ArrayList<WebElement>();
				for(MenubarComp comp : mcs){
					if(comp.getMenuList() != null){
						List<MenuItem> miList = comp.getMenuList();
						for(MenuItem mi : miList){
							weList.addAll(getMenuItems(mi));
						}
					}
				}
				wcs = weList.toArray(new WebElement[0]);
				for(WebElement we : wcs){
					if(id.equals(we.getId())){
						return we;
					}
				}
			}
			//�Ҽ��˵�����
			ContextMenuComp[] cmcs = vmenu.getContextMenus();
			if(cmcs != null && cmcs.length > 0){
				List<WebElement> weList = new ArrayList<WebElement>();
				for(ContextMenuComp comp : cmcs){
					if(comp.getItemList() != null){
						List<MenuItem> miList = comp.getItemList();
						for(MenuItem mi : miList){
							weList.addAll(getMenuItems(mi));
						}
					}
				}
				wcs = weList.toArray(new WebElement[0]);
				for(WebElement we : wcs){
					if(id.equals(we.getId())){
						return we;
					}
				}
			}
		}
		return null;
	}
	
}
