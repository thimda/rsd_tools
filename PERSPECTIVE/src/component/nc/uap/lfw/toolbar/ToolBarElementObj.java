package nc.uap.lfw.toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.uap.lfw.core.comp.ToolBarComp;
import nc.uap.lfw.core.comp.ToolBarItem;
import nc.uap.lfw.core.comp.ToolBarTitle;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ToolBarElementObj extends LFWWebComponentObj{
	
	private static final long serialVersionUID = 6253081418703115641L;
	
	public static final String TITLE_TEXT = "title_text";
	public static final String TITEL_REFIMG1="title_refimg1";
	public static final String TITEL_REFIMG2="title_refimg2";
	public static final String TITLE_I18NNAME="title_i18nname";
	public static final String TITLE_LANGDIR="title_langdir";
	public static final String TITLE_COLOR="title_color";
	public static final String TITLE_ISBOLD = "title_isbold";
	public static final String TITLE_TRANSPARENT= "title_transparent";
	public static final String PROP_ADD_TOOLBARITEM = "add_toolbar_props";
	public static final String PROP_UPDATE_TOOLBARITEM = "update_toolbar_props";
	public static final String PROP_DEL_TOOLBARITEM = "delete_toolbar_props";
	public static final String PROP_TOOLBAR_ELEMENT ="toolbar_element";
	private ToolBarElementObjFigure figure;
	private List<ToolBarItemObj> childrenList = new ArrayList<ToolBarItemObj>();
	private ToolBarComp toolbarComp;
	/**
	 * 当前选中的子项
	 */
	private ToolBarItem currentItem;
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[8];
		pds[0] = new TextPropertyDescriptor(TITLE_TEXT, "显示名");
		pds[0].setCategory("工具条标题");
		pds[1] = new TextPropertyDescriptor(TITEL_REFIMG1, "引用图形");
		pds[1].setCategory("工具条标题");
		pds[2] = new TextPropertyDescriptor(TITEL_REFIMG2, "引用图形（聚焦）");
		pds[2].setCategory("工具条标题");
		pds[3] = new TextPropertyDescriptor(TITLE_I18NNAME, "多语显示值");
		pds[3].setCategory("工具条标题");
		pds[4] = new TextPropertyDescriptor(TITLE_LANGDIR, "多语目录");
		pds[4].setCategory("工具条标题");
		pds[5] = new TextPropertyDescriptor(TITLE_COLOR, "颜色");
		pds[5].setCategory("工具条标题");
		pds[6] = new ComboBoxPropertyDescriptor(TITLE_ISBOLD,"是否加粗", Constant.ISLAZY);
		pds[6].setCategory("工具条标题");
		pds[7] = new ComboBoxPropertyDescriptor(TITLE_TRANSPARENT,"是否透明", Constant.ISLAZY);
		pds[7].setCategory("工具条特性");
		
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(toolbarComp.getTitle() == null){
			ToolBarTitle title = new ToolBarTitle();
			toolbarComp.setTitle(title);
		}
		if(TITLE_TRANSPARENT.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			toolbarComp.setTransparent(truevalue);
		}
		else if(TITLE_TEXT.equals(id))
			toolbarComp.getTitle().setText((String)value);
		else if(TITEL_REFIMG1.equals(id)){
			toolbarComp.getTitle().setRefImg1((String)value);
		}
		else if(TITEL_REFIMG2.equals(id)){
			toolbarComp.getTitle().setRefImg2((String)value);
		}
		else if(TITLE_I18NNAME.equals(id)){
			toolbarComp.getTitle().setI18nName((String)value);		
		}
		else if(TITLE_LANGDIR.equals(id)){
			toolbarComp.getTitle().setLangDir((String)value);
		}
		else if(TITLE_COLOR.equals(id)){
			toolbarComp.getTitle().setColor((String)value);
		}
		else if(TITLE_ISBOLD.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			toolbarComp.getTitle().setBold(truevalue);
		}
	
	}
	
	public Object getPropertyValue(Object id) {
		if(toolbarComp.getTitle() == null){
			ToolBarTitle title = new ToolBarTitle();
			toolbarComp.setTitle(title);
		}
		if(TITLE_TEXT.equals(id))
			return toolbarComp.getTitle().getText() == null?"":toolbarComp.getTitle().getText();
		else if(TITEL_REFIMG1.equals(id))
			return toolbarComp.getTitle().getRefImg1() == null?"":toolbarComp.getTitle().getRefImg1();
		else if(TITEL_REFIMG2.equals(id))
			return toolbarComp.getTitle().getRefImg2() == null?"":toolbarComp.getTitle().getRefImg2();
		else if(TITLE_I18NNAME.equals(id))
			return toolbarComp.getTitle().getI18nName() == null?"":toolbarComp.getTitle().getI18nName();
		else if(TITLE_LANGDIR.equals(id))
			return toolbarComp.getTitle().getLangDir() == null?"":toolbarComp.getTitle().getLangDir();
		else if(TITLE_COLOR.equals(id))
			return toolbarComp.getTitle().getColor() == null?"":toolbarComp.getTitle().getColor();
		else if(TITLE_ISBOLD.equals(id))
			return toolbarComp.getTitle().isBold() == true?new Integer(0): new Integer(1);
		else if(TITLE_TRANSPARENT.equals(id))
			return toolbarComp.isTransparent() == true? new Integer(0):new Integer(1);
		else return super.getPropertyValue(id);
	}
	
	public ToolBarItem getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(ToolBarItem currentItem) {
		this.currentItem = currentItem;
	}

		
	public ToolBarElementObjFigure getFigure() {
		return figure;
	}

	public void setFigure(ToolBarElementObjFigure figure) {
		this.figure = figure;
	}

	public ToolBarComp getToolbarComp() {
		return toolbarComp;
	}
	

	
	public void setToolbarComp(ToolBarComp toolbarComp) {
		this.toolbarComp = toolbarComp;
		fireStructureChange(PROP_TOOLBAR_ELEMENT, toolbarComp);
	}

	public void addChild(ToolBarItemObj obj) {
		childrenList.add(obj);
		// 如果已有该项，则不增加
		if (toolbarComp.getElementById(obj.getToolbarItem().getId()) != null)
			toolbarComp.addElement(obj.getToolbarItem());
	}

	public List<ToolBarItemObj> getChildrenList() {
		return childrenList;
	}

	public boolean removeChild(ToolBarItemObj obj) {
		toolbarComp.deleteElement(obj.getToolbarItem());
		return childrenList.remove(obj);
	}


	
	public void addToolBarItem(ToolBarItem item){
		fireStructureChange(PROP_ADD_TOOLBARITEM, item);
	}
	
		public void deleteToolBarItgem(ToolBarItem item){
		fireStructureChange(PROP_DEL_TOOLBARITEM, item);
	}
	

	public void updateToolBarItgem(ToolBarItem item){
		fireStructureChange(PROP_UPDATE_TOOLBARITEM, item);
	}

	public WebElement getWebElement() {
		return toolbarComp;
	}

}
