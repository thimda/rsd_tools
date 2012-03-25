package nc.uap.lfw.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.comp.IExcelColumn;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.excel.core.ExcelEditor;
import nc.uap.lfw.perspective.model.Constant;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * excel model
 * @author zhangxya
 *
 */
public class ExcelElementObj extends LFWWebComponentObj{

	private static final long serialVersionUID = 6253081418703115641L;

	public static final String PROP_EXCEL_ELEMENT ="excel_element";
	public static final String PROP_UPDATE_CELL_PROPS = "update_cell_props";
	public static final String Grid_ADD_CELL_PROPS = "add_grid_props";
	public static final String PROP_EDITABLE = "element_EDITABLE";
	public static final String PROP_DATASET = "element_DATASET";
	public static final String PROP_MULTISELECT = "element_MULTISELECT";
	public static final String PROP_ROWHEIGHT = "element_ROWHEIGHT";
	public static final String PROP_HEADROWHEIGHT = "element_headerRowHeight";
	public static final String PROP_SHOWNMUCOL = "element_showNumCol";
	public static final String PROP_SHOWSUMROW = "element_showSumRow";
	public static final String PROP_PAGESIZE = "element_pageSize";
	public static final String PROP_SIMPLEPAGEBAR = "element_simplePageBar";
	public static final String PROP_SORTALBE= "element_sortable";
	public static final String PROP_PAGENATIONTOP = "element_pagenationTop";
	public static final String PROP_SHOWCOLINFO = "element_showColInfo";
	public static final String PROP_GROUPCOLUMNS = "element_GROUPCOLUMNS";
	public static final String PROP_SHOWHEADER = "element_showHeader";
	public static final String PROP_CAPTION = "element_CAPTION";
	private ExcelComp excelComp;
	private Dataset ds;
	private List<IExcelColumn> props = new ArrayList<IExcelColumn>();
	
	public ExcelComp getExcelComp() {
		return excelComp;
	}
	
	public void setExcelComp(ExcelComp excelComp) {
		this.excelComp = excelComp;
		fireStructureChange(PROP_EXCEL_ELEMENT, excelComp);
	}

	
	public Dataset getDs() {
		return ds;
	}

	public void setDs(Dataset ds) {
		this.ds = ds;
	}
	
	public void addProp(IExcelColumn prop){
		props.add(prop);
		fireStructureChange(Grid_ADD_CELL_PROPS, prop);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[15];
		pds[0] = new ComboBoxPropertyDescriptor(PROP_EDITABLE,"是否可编辑", Constant.ISLAZY);
		pds[0].setCategory("高级");
		pds[1] = new NoEditableTextPropertyDescriptor(PROP_DATASET,"数据集");
		pds[1].setCategory("高级");
		pds[2] = new ComboBoxPropertyDescriptor(PROP_MULTISELECT,"是否多选", Constant.ISLAZY);
		pds[2].setCategory("高级");
		pds[3] = new TextPropertyDescriptor(PROP_ROWHEIGHT, "单行高度");
		pds[3].setCategory("高级");
		pds[4] = new TextPropertyDescriptor(PROP_HEADROWHEIGHT, "表头单行高度");
		pds[4].setCategory("高级");
		pds[5] = new ComboBoxPropertyDescriptor(PROP_SHOWNMUCOL,"是否显示数字列", Constant.ISLAZY);
		pds[5].setCategory("高级");
		pds[6] = new ComboBoxPropertyDescriptor(PROP_SHOWSUMROW,"是否显示合计行", Constant.ISLAZY);
		pds[6].setCategory("高级");
		pds[7] = new TextPropertyDescriptor(PROP_PAGESIZE, "每页数目");
		pds[7].setCategory("高级");
		pds[8] = new ComboBoxPropertyDescriptor(PROP_SIMPLEPAGEBAR,"是否简易分页显示栏", Constant.ISLAZY);
		pds[8].setCategory("高级");
		pds[9] = new ComboBoxPropertyDescriptor(PROP_SORTALBE,"整体是否可以排序", Constant.ISLAZY);
		pds[9].setCategory("高级");
		pds[10] = new ComboBoxPropertyDescriptor(PROP_PAGENATIONTOP,"分页工具条是否在顶端", Constant.ISLAZY);
		pds[10].setCategory("高级");
		pds[11] = new ComboBoxPropertyDescriptor(PROP_SHOWCOLINFO,"是否显示列控制菜单", Constant.ISLAZY);
		pds[11].setCategory("高级");
		pds[12] = new TextPropertyDescriptor(PROP_GROUPCOLUMNS, "给定的顺序分组");
		pds[12].setCategory("高级");
		pds[13] = new ComboBoxPropertyDescriptor(PROP_SHOWHEADER,"是否显示表头", Constant.ISLAZY);
		pds[13].setCategory("高级");
		//caption
		pds[14] = new TextPropertyDescriptor(PROP_CAPTION,"标题");
		pds[14].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_DATASET.equals(id)){
			excelComp.setDataset((String)value);
			fireStructureChange(PROP_EXCEL_ELEMENT,  excelComp);
		}
		else if(PROP_EDITABLE.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setEditable(truevalue);
			//setEditable((String)value);
		}
		else if(PROP_MULTISELECT.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setMultiSelect(truevalue);
		}
		else if(PROP_ROWHEIGHT.equals(id))
			excelComp.setRowHeight((String)value);
		else if(PROP_HEADROWHEIGHT.equals(id))
			excelComp.setHeaderRowHeight((String)value);
		else if(PROP_SHOWNMUCOL.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setShowNumCol(truevalue);
		}
		else if(PROP_SHOWSUMROW.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setShowSumRow(truevalue);
		}
		else if(PROP_PAGESIZE.equals(id))
			excelComp.setPageSize((String)value);
		else if(PROP_SIMPLEPAGEBAR.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setSimplePageBar(truevalue);
		}
		else if(PROP_SORTALBE.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setSortable(truevalue);
		}
		else if(PROP_PAGENATIONTOP.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setPagenationTop(truevalue);
		} else if (PROP_SHOWCOLINFO.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setShowColInfo(truevalue);
		}else if(PROP_GROUPCOLUMNS.equals(id))
			excelComp.setGroupColumns((String)value);
		else if(PROP_SHOWHEADER.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			excelComp.setShowHeader(truevalue);
		}
		else if(PROP_CAPTION.equals(id)){
			String oldValue = excelComp.getCaption();
			if((oldValue == null && value != null)  || (oldValue != null && value != null && !oldValue.equals(value))){
				excelComp.setCaption((String)value);
				ExcelEditor.getActiveEditor().refreshTreeItemText(excelComp);
			}
		}
		else
			super.setPropertyValue(id, value);
	}
	public Object getPropertyValue(Object id) {
		if(PROP_DATASET.equals(id))
			return excelComp.getDataset() == null?"":excelComp.getDataset();
		else if(PROP_EDITABLE.equals(id))
			return (excelComp.isEditable() == true)? new Integer(0):new Integer(1);
		else if(PROP_MULTISELECT.equals(id))
			return (excelComp.isMultiSelect() == true)? new Integer(0):new Integer(1);
		else if(PROP_ROWHEIGHT.equals(id))
			return excelComp.getRowHeight() == null?"":excelComp.getRowHeight();
		else if(PROP_HEADROWHEIGHT.equals(id))
			return excelComp.getHeaderRowHeight() == null?"":excelComp.getHeaderRowHeight();
		else if(PROP_SHOWNMUCOL.equals(id))
			return (excelComp.isShowNumCol() == true)? new Integer(0):new Integer(1);
		else if(PROP_SHOWSUMROW.equals(id))
			return (excelComp.isShowSumRow() == true)? new Integer(0):new Integer(1);
		else if(PROP_PAGESIZE.equals(id))
			return excelComp.getPageSize() == null?"":excelComp.getPageSize();
		else if(PROP_SIMPLEPAGEBAR.equals(id))
			return (excelComp.isSimplePageBar() == true)? new Integer(0):new Integer(1);
		else if(PROP_SORTALBE.equals(id))
			return (excelComp.isSortable() == true)? new Integer(0):new Integer(1);
		else if(PROP_PAGENATIONTOP.equals(id))
			return excelComp.isPagenationTop() == true? new Integer(0):new Integer(1);
		else if(PROP_SHOWCOLINFO.equals(id))
			return excelComp.isShowColInfo() == true? new Integer(0):new Integer(1);
		else if(PROP_GROUPCOLUMNS.equals(id))
			return excelComp.getGroupColumns() == null?"":excelComp.getGroupColumns();
		else if(PROP_SHOWHEADER.equals(id))
			return excelComp.isShowHeader() == true? new Integer(0):new Integer(1);
		else if(PROP_CAPTION.equals(id))
			return excelComp.getCaption() == null?"":excelComp.getCaption();
		else return super.getPropertyValue(id);
	}

	public WebElement getWebElement() {
		return excelComp;
	}

	
;
}
