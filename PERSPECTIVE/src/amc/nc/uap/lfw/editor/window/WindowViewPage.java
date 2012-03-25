/**
 * 
 */
package nc.uap.lfw.editor.window;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.LFWAbstractViewPage;
import nc.lfw.editor.pagemeta.PagemetaEditor;
import nc.lfw.editor.pagemeta.PagemetaElementPart;
import nc.lfw.editor.pagemeta.PagemetaGraphPart;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.lfw.editor.widget.plug.PluginDescElementObj;
import nc.lfw.editor.widget.plug.PluginDescElementPart;
import nc.lfw.editor.widget.plug.PluginDescItemPropertiesView;
import nc.lfw.editor.widget.plug.PlugoutDescElementObj;
import nc.lfw.editor.widget.plug.PlugoutDescElementPart;
import nc.lfw.editor.widget.plug.PlugoutDescItemPropertiesView;
import nc.lfw.editor.widget.plug.PlugoutEmitItemPropertiesView;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.base.ExtAttribute;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.core.page.PluginDescItem;
import nc.uap.lfw.core.page.PlugoutDescItem;
import nc.uap.lfw.core.page.PlugoutEmitItem;
import nc.uap.lfw.core.uimodel.WidgetConfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

/**
 * @author chouhl
 *
 */
public class WindowViewPage extends LFWAbstractViewPage {
	
	private TabFolder plugoutFolder = null;
	private TabFolder pluginFolder = null;
	private TabItem plugoutDescTabItem = null;
	private TabItem plugoutEmitTabItem = null;
	private TabItem pluginDescTabItem = null;
	
	private TabItem attrItem = null;
//	private EventPropertiesView eventPropertieView = null;

	private PlugoutDescItemPropertiesView  plugoutDescItemPropertiesView = null;
	private PlugoutEmitItemPropertiesView  plugoutEmitItemPropertiesView = null;
	private PluginDescItemPropertiesView  pluginDescItemPropertiesView = null;
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setComp(comp);
		comp.setLayout(getSl());
		
		plugoutFolder = new TabFolder(comp, SWT.NONE);
		pluginFolder = new TabFolder(comp, SWT.NONE);
		
		plugoutDescTabItem = new TabItem(plugoutFolder, SWT.NONE);
		plugoutDescTabItem.setText(WEBProjConstants.PLUGOUTDESC);
		
		plugoutEmitTabItem = new TabItem(plugoutFolder, SWT.NONE);
		plugoutEmitTabItem.setText(WEBProjConstants.PLUGOUTEMIT);
		
		pluginDescTabItem = new TabItem(pluginFolder, SWT.NONE);
		pluginDescTabItem.setText(WEBProjConstants.PLUGINDESC);
		
		TabFolder voTabFolder = new TabFolder(comp, SWT.NONE);
		setVoTabFolder(voTabFolder);
		
		attrItem = new TabItem(voTabFolder, SWT.NONE);
		attrItem.setText(WEBProjConstants.ATTRIBUTE);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof PropertySheet){
			return;
		}
		// 为当前编辑器设置选中对象
		PagemetaEditor.getActiveEditor().setCurrentSelection(selection);
		if (part == null || selection == null) {
			return;
		} else if (part instanceof PagemetaEditor) {
			Composite comp = getComp();
			StructuredSelection ss = (StructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof PagemetaElementPart) {
				PagemetaElementPart lfwEle = (PagemetaElementPart) sel;
				Object model = lfwEle.getModel();
				if (model instanceof WidgetElementObj) {
					PageMeta pageMeta = PagemetaEditor.getActivePagemetaEditor().getGraph().getPagemeta();
					WidgetConfig widgetConfig = pageMeta.getWidgetConf(((WidgetElementObj) model).getWidget().getId());
					ExtAttribute attr = widgetConfig.getExtendAttribute(PageMeta.$QUERYTEMPLATE);
					if(attr == null){
						attr = new ExtAttribute();
						attr.setKey(PageMeta.$QUERYTEMPLATE);
						widgetConfig.addAttribute(attr);
					}
					addExtendAttributesView(widgetConfig);
				}
			}
			else if (sel instanceof PagemetaGraphPart) {
				PageMeta pageMetaConf = PagemetaEditor.getActivePagemetaEditor().getGraph().getPagemeta();
				setWebElement(pageMetaConf);
				TabItem[] items = getVoTabFolder().getItems();
				if(items != null && items.length > 0){
					for(TabItem item : items){
						item.setControl(null);
					}
				}
				ExtAttribute attr = pageMetaConf.getExtendAttribute(PageMeta.$QUERYTEMPLATE);
				if(attr == null){
					attr = new ExtAttribute();
					attr.setKey(PageMeta.$QUERYTEMPLATE);
					pageMetaConf.addAttribute(attr);
				}
				addExtendAttributesView(pageMetaConf);
				addEventPropertiesView(pageMetaConf.getEventConfs(), pageMetaConf.getControllerClazz());
			}
			else if (sel instanceof PlugoutDescElementPart) {
				PlugoutDescElementPart eEle = (PlugoutDescElementPart)sel;
				Object model = eEle.getModel();
				PlugoutDescElementObj plugoutObj = (PlugoutDescElementObj)model;
				List<PlugoutDescItem> descItems = plugoutObj.getPlugout().getDescItemList();
				if (descItems == null){
					descItems = new ArrayList<PlugoutDescItem>();
					plugoutObj.getPlugout().setDescItemList(descItems);
				}
				getSl().topControl = plugoutFolder;
				plugoutDescItemPropertiesView = new PlugoutDescItemPropertiesView(plugoutFolder, SWT.NONE, false);
				plugoutDescItemPropertiesView.getTv().setInput(descItems);
				plugoutDescItemPropertiesView.setPlugoutDescElementPart(eEle);
				plugoutDescItemPropertiesView.getTv().expandAll();
				plugoutDescTabItem.setControl(plugoutDescItemPropertiesView);
				
				List<PlugoutEmitItem> emitItems = plugoutObj.getPlugout().getEmitList();
				if (emitItems == null){
					emitItems = new ArrayList<PlugoutEmitItem>();
					plugoutObj.getPlugout().setEmitList(emitItems);
				}
				plugoutEmitItemPropertiesView = new PlugoutEmitItemPropertiesView(plugoutFolder, SWT.NONE, false);
				plugoutEmitItemPropertiesView.getTv().setInput(emitItems);
				plugoutEmitItemPropertiesView.setPlugoutDescElementPart(eEle);
				plugoutEmitItemPropertiesView.getTv().expandAll();
				plugoutEmitTabItem.setControl(plugoutEmitItemPropertiesView);
				
				plugoutFolder.setSelection(0);
				if (comp != null) {
					comp.layout();
					if (!comp.isVisible()){
						comp.setVisible(true);
					}
				}
			} 
			else if (sel instanceof PluginDescElementPart) {
				PluginDescElementPart eEle = (PluginDescElementPart)sel;
				Object model = eEle.getModel();
				PluginDescElementObj pluginObj = (PluginDescElementObj)model;
				List<PluginDescItem> descItems = pluginObj.getPlugin().getDescItemList();
				if (descItems == null){
					descItems = new ArrayList<PluginDescItem>();
					pluginObj.getPlugin().setDescItemList(descItems);
				}
				getSl().topControl = pluginFolder;
				pluginDescItemPropertiesView = new PluginDescItemPropertiesView(pluginFolder, SWT.NONE, false);
				pluginDescItemPropertiesView.getTv().setInput(descItems);
				pluginDescItemPropertiesView.setPluginDescElementPart(eEle);
				pluginDescItemPropertiesView.getTv().expandAll();
				pluginDescTabItem.setControl(pluginDescItemPropertiesView);
				
				pluginFolder.setSelection(0);
				if (comp != null) {
					comp.layout();
					if (!comp.isVisible()){
						comp.setVisible(true);
					}
				}
			}
			else {
				comp.setVisible(false);
			}
		}
	}
	
	@Override
	public void addExtendAttributesView(WebElement webElement) {
		Composite comp = getComp();
		getSl().topControl = getVoTabFolder();
		TabItem[] items = getVoTabFolder().getItems();
		if(items != null && items.length > 0){
			for(TabItem item : items){
				item.setControl(null);
			}
		}
		// 增加扩展属性内容页
		addExtendAttributesItem(webElement);
		// 在扩展属性表格页面中，记录当前编辑器页面
		getExtendAttributesView().setEditor(PagemetaEditor.getActiveEditor());
		if (comp != null) {
			comp.layout();
			if (!comp.isVisible())
				comp.setVisible(true);
		}
	}

}
