package nc.lfw.editor.pagemeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LfwBaseGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.editor.common.tools.LFWAMCPersTool;
import nc.uap.lfw.editor.common.tools.LFWTool;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class PagemetaGraph extends LfwBaseGraph {

	private static final long serialVersionUID = 1L;
	
	public static final String PROP_CHILD_ADD = "prop_child_add";
	public static final String PROP_CHILD_REMOVE = "prop_child_remove";

	public static final String PROP_WIDGETPLUG_CHANGE = "prop_widgetplug_change";
	
	public static final String PROP_CONNECT_CHANGE = "prop_widgetplug_change";
	
	private PageMeta pagemeta = null;

	private List<WidgetElementObj> widgetCells = new ArrayList<WidgetElementObj>();
	
//	private List<PageStateElementObj> pageStateCells = new ArrayList<PageStateElementObj>();
	
	public PagemetaGraph() {
		super();
	}

	public boolean addWidgetCell(WidgetElementObj cell) {
		cell.setGraph(this);
		boolean b = widgetCells.add(cell);
		elementsCount++;
		if (b) {
			fireStructureChange(PROP_CHILD_ADD, cell);
		}
		return b;
	}

	public boolean removeWidgetCell(WidgetElementObj cell) {
		boolean b = widgetCells.remove(cell);
		elementsCount--;
		cell.setGraph(null);
		if (b) {
			fireStructureChange(PROP_CHILD_REMOVE, cell);
		}
		return b;
	}

	public List<WidgetElementObj> getWidgetCells() {
		return widgetCells;
	}

	public void addConnector(Connector conn){
		pagemeta.getConnectorMap().put(conn.getId(), conn);
		fireStructureChange(PROP_CONNECT_CHANGE, conn);
	}
	
	public void removeConnector(Connector conn){
		pagemeta.getConnectorMap().remove(conn.getId());
		fireStructureChange(PROP_CONNECT_CHANGE, conn);
	}
	
	/**
	 * 取消所有图形的所有子项选中状态
	 */
	public void unSelectAllLabels() {
		super.unSelectAllLabels();
		// Widget图形
		for (int i = 0, n = widgetCells.size(); i < n; i++) {
			WidgetElementObj widgetObj = widgetCells.get(i);
			widgetObj.getFigure().unSelectAllLabels();
		}
//		// 页面状态图形
//		for (int i = 0, n = pageStateCells.size(); i < n; i++) {
//			PageStateElementObj pageStateObj = pageStateCells.get(i);
//			pageStateObj.getFigure().unSelectAllLabels();
//		}
	}

	private String mainWidgetId = "";
	private String caption = "";
	private String i18nName = "";
	private String sourcePackage = "";
	private String controllerClazz = "";
	private String processor = "";
	private String pagemodel = "";

	public String getPagemodel() {
		return pagemodel;
	}

	public void setPagemodel(String pagemodel) {
		this.pagemodel = pagemodel;
		String pageModel = LFWPersTool.getPageModel();
		if (!pagemodel.equals(pageModel)) {
			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
			editor.setDirtyTrue();
		}
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = null;
		if(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWPageMetaTreeItem && LFWTool.NEW_VERSION.equals(((LFWPageMetaTreeItem)LFWAMCPersTool.getCurrentTreeItem()).getLfwVersion())){
			pds = new PropertyDescriptor[5];
			pds[0] = new NoEditableTextPropertyDescriptor(WEBProjConstants.PROP_ID, WEBProjConstants.ID);
			pds[0].setCategory(WEBProjConstants.BASIC);
			pds[1] = new TextPropertyDescriptor(WEBProjConstants.PROP_CAPTION, WEBProjConstants.CAPTION);
			pds[1].setCategory(WEBProjConstants.BASIC);
			pds[2] = new TextPropertyDescriptor(WEBProjConstants.PROP_I18NNAME, WEBProjConstants.I18NNAME);
			pds[2].setCategory(WEBProjConstants.BASIC);
			pds[3] = new ComboBoxPropertyDescriptor(WEBProjConstants.PROP_SOURCE_PACKAGE, WEBProjConstants.SOURCE_PACKAGE, getSourcePackages());
			pds[3].setCategory(WEBProjConstants.BASIC);
			pds[4] = new TextPropertyDescriptor(WEBProjConstants.PROP_CONTROLLER_CLASS, WEBProjConstants.CONTROLLER_CLASS);
			pds[4].setCategory(WEBProjConstants.BASIC);
		}else{
			pds = new PropertyDescriptor[4];
			pds[0] = new TextPropertyDescriptor(WEBProjConstants.PROP_CAPTION, WEBProjConstants.CAPTION);
			pds[0].setCategory(WEBProjConstants.BASIC);
			pds[1] = new TextPropertyDescriptor(WEBProjConstants.PROP_I18NNAME, WEBProjConstants.I18NNAME);
			pds[1].setCategory(WEBProjConstants.BASIC);
			pds[2] = new TextPropertyDescriptor(WEBProjConstants.PROP_PROCESS_CLASS, WEBProjConstants.PROCESS_CLASS);
			pds[2].setCategory(WEBProjConstants.BASIC);
			pds[3] = new TextPropertyDescriptor(WEBProjConstants.PROP_PAGEMODEL, WEBProjConstants.PAGEMODEL);
			pds[3].setCategory(WEBProjConstants.BASIC);
		}
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}

	public void setPropertyValue(Object id, Object value) {
		if(LFWAMCPersTool.getCurrentTreeItem() instanceof LFWPageMetaTreeItem && LFWTool.NEW_VERSION.equals(((LFWPageMetaTreeItem)LFWAMCPersTool.getCurrentTreeItem()).getLfwVersion())){
			if(WEBProjConstants.PROP_CAPTION.equals(id)) {
				setCaption((String)value);
			}else if (WEBProjConstants.PROP_I18NNAME.equals(id)) {
				setI18nName((String)value);
			}else if(WEBProjConstants.PROP_SOURCE_PACKAGE.equals(id)){
				if(value instanceof Integer){
					String[] sourcePackages = getSourcePackages();
					if(sourcePackages.length > (Integer)value){
						setSourcePackage(sourcePackages[((Integer)value).intValue()]);
					}
				}
			}else if(WEBProjConstants.PROP_CONTROLLER_CLASS.equals(id)){
				setControllerClazz((String)value);
			}
		}else{
			if (WEBProjConstants.PROP_CAPTION.equals(id)) {
				setCaption((String) value);
			} else if (WEBProjConstants.PROP_I18NNAME.equals(id)) {
				setI18nName((String) value);
			} else if (WEBProjConstants.PROP_PROCESS_CLASS.equals(id)) {
				setProcessor((String) value);
			}else if(WEBProjConstants.PROP_PAGEMODEL.equals(id))
				setPagemodel((String)value);
		}
	}

	public Object getPropertyValue(Object id) {
		if(WEBProjConstants.PROP_ID.equals(id)){
			return pagemeta.getId() == null? "" : pagemeta.getId();
		}else if(WEBProjConstants.PROP_CAPTION.equals(id)) {
			return pagemeta.getCaption() == null ? "" : pagemeta.getCaption();
		}else if (WEBProjConstants.PROP_I18NNAME.equals(id)) {
			return pagemeta.getI18nName() == null ? "" : pagemeta.getI18nName();
		}else if(WEBProjConstants.PROP_SOURCE_PACKAGE.equals(id)){
			if(pagemeta.getSourcePackage() == null){
				return 0;
			}
			String[] sourcePackages = getSourcePackages();
			for(int i=0; i < sourcePackages.length; i++){
				if(pagemeta.getSourcePackage().equals(sourcePackages[i])){
					return i;
				}
			}
//			pagemeta.setSourcePackage(sourcePackages[0]);
			return 0;
		}else if (WEBProjConstants.PROP_CONTROLLER_CLASS.equals(id)) {
			return pagemeta.getControllerClazz() == null ? "" : pagemeta.getControllerClazz();
		}else if (WEBProjConstants.PROP_PROCESS_CLASS.equals(id)) {
			return pagemeta.getProcessorClazz() == null ? "" : pagemeta.getProcessorClazz();
		}else if(WEBProjConstants.PROP_PAGEMODEL.equals(id)){
			return getPagemodel();
		}else{
			return null;
		}
	}
	
	private String[] getSourcePackages(){
		return LFWTool.getAllRootPackage().toArray(new String[0]);
	}
	
	public String getMainWidgetId() {
		return mainWidgetId;
	}

//	public void setMainWidgetId(String mainWidgetId) {
//		this.mainWidgetId = mainWidgetId;
//		if (!mainWidgetId.equals(pagemeta.getMasterWidget())) {
//			pagemeta.setMasterWidget(mainWidgetId);
//			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
//			editor.setDirtyTrue();
//		}
//	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		if (!caption.equals(pagemeta.getCaption())) {
			pagemeta.setCaption(caption);
			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
			editor.setDirtyTrue();
		}
	}

	public String getI18nName() {
		return i18nName;
	}

	public void setI18nName(String name) {
		i18nName = name;
		if (!name.equals(pagemeta.getI18nName())) {
			pagemeta.setI18nName(name);
			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
			editor.setDirtyTrue();
		}
	}
	
	public String getSourcePackage() {
		return sourcePackage;
	}

	public void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
		if(!sourcePackage.equals(pagemeta.getSourcePackage())){
			pagemeta.setSourcePackage(sourcePackage);
			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
			editor.setDirtyTrue();
		}
	}

	public String getControllerClazz() {
		return controllerClazz;
	}

	public void setControllerClazz(String controllerClazz) {
		this.controllerClazz = controllerClazz;
		if(!controllerClazz.equals(pagemeta.getControllerClazz())){
			pagemeta.setControllerClazz(controllerClazz);
			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
			editor.setDirtyTrue();
		}
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
		if (!processor.equals(pagemeta.getProcessorClazz())) {
			pagemeta.setProcessorClazz(processor);
			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
			editor.setDirtyTrue();
		}
	}

	public PageMeta getPagemeta() {
		return pagemeta;
	}

	public void setPagemeta(PageMeta pagemeta) {
		this.pagemeta = pagemeta;
	}

//	public boolean addPageStateCell(PageStateElementObj cell) {
//		cell.setGraph(this);
//		boolean b = pageStateCells.add(cell);
//		//elementsCount++;
//		// 占用一行，2个图标位置
//		elementsCount += 2;
//		if (b) {
//			fireStructureChange(PROP_CHILD_ADD, cell);
//		}
//		return b;
//	}
//
//	public boolean removePageStateCell(PageStateElementObj cell) {
//		boolean b = pageStateCells.remove(cell);
//		//elementsCount--;
//		// 占用一行，2个图标位置
//		elementsCount -= 2;
//		cell.setGraph(null);
//		if (b) {
//			fireStructureChange(PROP_CHILD_REMOVE, cell);
//		}
//		return b;
//	}
//
//	public List<PageStateElementObj> getPageStateCells() {
//		return pageStateCells;
//	}
	
	public void fireWidgetPlugChange(LfwWidget widget){
		fireStructureChange(PROP_WIDGETPLUG_CHANGE, widget);
	}
}
