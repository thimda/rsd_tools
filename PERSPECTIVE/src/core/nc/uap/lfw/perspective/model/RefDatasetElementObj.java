package nc.uap.lfw.perspective.model;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.grid.GridElementObj;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;



/**
 * 设置FieldRelation时的引用ds
 * @author zhangxya
 *
 */
public class RefDatasetElementObj extends LfwElementObjWithGraph{
	
	private static final long serialVersionUID = 1L;
	private DatasetElementObj dsobj = null;
	private GridElementObj gridobj = null;
	public GridElementObj getGridobj() {
		return gridobj;
	}
	public void setGridobj(GridElementObj gridobj) {
		this.gridobj = gridobj;
	}
	
	private FieldRelation refFieldRelation ;
	public FieldRelation getRefFieldRelation() {
		return refFieldRelation;
	}
	public void setRefFieldRelation(FieldRelation refFieldRelation) {
		this.refFieldRelation = refFieldRelation;
	}
	
	public static final String PROP_FIELDRELATION_PROPS = "add_reffieldrelation_props";
	public void addFieldRelation(FieldRelation fr){
		fireStructureChange(PROP_FIELDRELATION_PROPS, fr);
	}
	
	public static final String PROP_DELETE_FIELDRELATION_PROPS = "delete_reffieldrelation_props";
	public void deleteFieldRelation(FieldRelation fr){
		fireStructureChange(PROP_DELETE_FIELDRELATION_PROPS, fr);
	}
	
	private RefDatasetElementObj parent = null;
	
	public RefDatasetElementObj getParent() {
		return parent;
	}
	public void setParent(RefDatasetElementObj parent) {
		this.parent = parent;
	}
	public DatasetElementObj getDsobj() {
		return dsobj;
	}
	public void setDsobj(DatasetElementObj dsobj) {
		this.dsobj = dsobj;
	}

	public static final String REFDS_CHILD_ADD = "refds_child_add";
	private ArrayList<RefDatasetElementObj> children = new ArrayList<RefDatasetElementObj>();
	
	public ArrayList<RefDatasetElementObj> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<RefDatasetElementObj> children) {
		this.children = children;
	}
	public boolean addChild(RefDatasetElementObj cell) {
		cell.setParent(this);
		boolean b = children.add(cell);
		if (b) {
			fireStructureChange(REFDS_CHILD_ADD, cell);
		}
		return b;
	}
	
	public static final String REFDS_CHILD_REMOVE = "refds_child_remove";
	
	public void removeChild(RefDatasetElementObj cell){
		children.remove(cell);
		fireStructureChange(REFDS_CHILD_REMOVE, cell);
	}
	

	private DatasetElementObj dsEle = null;
	public static final String PROP_CELL_LOCATION = "cell_location";

	public DatasetElementObj getDsEle() {
		return dsEle;
	}

	public void setDsEle(DatasetElementObj dsEle) {
		this.dsEle = dsEle;
	}

	private Dataset ds;

	public Dataset getDs() {
		return ds;
	}

	public void setDs(Dataset ds) {
		this.ds = ds;
	}
	
	private String islazy;
	private String refdatasetid;
	
	public String getIslazy() {
		return islazy;
	}

	public void setIslazy(String islazy) {
		this.islazy = islazy;
	}

	public String getRefdatasetid() {
		return refdatasetid;
	}

	public void setRefdatasetid(String refdatasetid) {
		this.refdatasetid = refdatasetid;
	}

	public static final String PROP_ISLAZY = "element_ISLAZY";
	//public static final String PROP_REFDATASETID = "element_REFDATASETID";
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[1];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_ID, "ID");
		pds[0].setCategory("基本");
//		pds[1] = new ComboBoxPropertyDescriptor(PROP_ISLAZY,"是否缓加载", Constant.ISLAZY);
//		pds[1].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		if(PROP_ID.equals(id))
			setIslazy((String)value);
	}
	public Object getPropertyValue(Object id) {
		if(PROP_ID.equals(id)){
			return ds.getId() == null?"":ds.getId();
		}
		return null;
	}
		
	public WebElement getWebElement() {
		// TODO Auto-generated method stub
		return ds;
	}
	
}
