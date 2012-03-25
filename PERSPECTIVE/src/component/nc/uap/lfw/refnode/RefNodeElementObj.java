package nc.uap.lfw.refnode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.ObjectComboPropertyDescriptor;
import nc.uap.lfw.core.comp.FormElement;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.IRefDataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.config.RefNodeConf;
import nc.uap.lfw.core.refnode.BaseRefNode;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.core.refnode.NCRefNode;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.core.refnode.SelfDefRefNode;
import nc.uap.lfw.perspective.model.Constant;
import nc.uap.lfw.refnode.core.RefNodeEditor;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * ���սڵ�ģ��
 * @author zhangxya
 *
 */
public class RefNodeElementObj extends LfwElementObjWithGraph{
	private static final long serialVersionUID = 908307595793088095L;
	private String refRelationId;
	

	private String reftype;
	private String originalId;
	private String querySql;
	private BaseRefNode refnode;
	private String type;
	private String filterSql;
	private boolean isFromPool;
	public static final String PROP_UPDATE_CELL_PROPS = "update_cell_props";
	
	public String getRefRelationId() {
		return refRelationId;
	}

	public void setRefRelationId(String refRelationId) {
		this.refRelationId = refRelationId;
	}
	
	public boolean isFromPool() {
		return isFromPool;
	}
	
	public void setFromPool(boolean isFromPool) {
		this.isFromPool = isFromPool;
	}
	
	public String getReftype() {
		return reftype;
	}

	public void setReftype(String reftype) {
		this.reftype = reftype;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
	
	public RefNodeElementObj(){
		super();
	}
	
	public RefNodeElementObj(String originalId){
		super();
		this.originalId = originalId;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BaseRefNode getRefnode() {
		return refnode;
	}

	public static final String PROP_REFNODE_ELEMENT ="refnode_element";
	public void setRefnode(BaseRefNode refnode) {
		this.refnode = refnode;
		fireStructureChange(PROP_REFNODE_ELEMENT,  refnode);
	}
	private NCRefNode ncrefnode;
	public NCRefNode getNcrefnode() {
		return ncrefnode;
	}

	public void setNcrefnode(NCRefNode ncrefnode) {
		this.ncrefnode = ncrefnode;
	}
	
	private BaseRefNode newrefnode;

	public BaseRefNode getNewrefnode() {
		return newrefnode;
	}

	public void setNewrefnode(BaseRefNode newrefnode) {
		this.newrefnode = newrefnode;
	}

	public static final String PROP_PATH = "element_PATH";
	public static final String PROP_TYPE = "element_TYPE";
	public static final String PROP_ID = "element_ID";
	public static final String PROP_WRITEDS = "element_WRITEDS";
	public static final String PROP_READDS = "element_READDS";
	public static final String PROP_NCREFNODE = "element_NCREFNODE";
	public static final String PROP_REFID = "element_REFID";
	public static final String PROP_READFIELDS = "element_READFIELDS";
	public static final String PROP_WRITEFIELDS= "element_WRITEFIELDS";
	public static final String PROP_MULTISEL = "element_MULTISEL";
	public static final String PROP_USERPOWER = "element_USERPOWER";
	public static final String PROP_ORGS = "element_ORGS";
	public static final String PROP_SELLEAFONLY = "element_SELLEAFONLY";
	public static final String PROP_PROCESSOR = "element_PROCESSOR";
	public static final String PROP_PAGEMETA = "element_PAGEMETA";
	public static final String PROP_REFMODEL = "element_REFMODEL";
	public static final String PROP_DATALISTENER = "element_DATALISTENER";
	public static final String PROP_PAGEMODEL = "element_PAGEMODEL";
	public static final String PROP_REFNODEDELEGATOR = "element_REFNODEDELEGATOR";
	public static final String PROP_ISREFRESH = "element_ISREFRESH";
	public static final String PROP_ISDIALOG = "element_ISDIALOG";
	public static final String PROP_ALLOWINPUT = "element_allowInput";
	public static final String PROP_CAPTION = "element_CAPTION";
	
	
	public static String PROP_VISIBLEFIELDCODES = "visibleFieldCodes";
	public static String PROP_VISIBLEFIELDNAMES = "visibleFieldNames";
	public static String PROP_HIDDENFIELDCODES = "hiddenFieldCodes";
	public static String PROP_HIDDENFIELDNAMES = "hiddenFieldNames";
	public static String PROP_REFPKFIELD = "refPkField";
	public static String PROP_REFCODEFIELD = "refCodeField";
	public static String PROP_REFNAMEFIELD = "refNameField";
	public static String PROP_REFDATA = "refData";
	public static String PROP_QEURYSQL = "QuerySql";
	public static String PROP_FILTERSQL = "FilterSql";
	public static String PROP_FILTERFIELDS = "FilterFields";
	public static String PROP_REFTYPE = "RefType";
	public static String PROP_FIELDINTEX = "FieldIndex";
	
	
	//�����,���͡������Ͳ���ʹ��
	public static String PROP_TABLESTRING = "tableString";
	
	public static String PROP_STRPATCH = "strpatch";
	public static String PROP_FIXEDWHEREPART = "FixedWherePart";
	public static String PROP_GROUPPART = "GroupPart";
	public static String PROP_ORDERPART = "OrderPart";
	
	//����/�������ʹ��
	public static String PROP_CHILDFIELD = "childField";
	public static String PROP_FATHERFIELD = "FatherField";
	public static String PROP_ROOTNAME = "RootName";
	
	//�����Ͳ���
	public static String PROP_CLASSREFCODEFIELD = "ClassRefCodeField";
	public static String PROP_CLASSREFNAMEFIELD= "ClassRefNameField";
	public static String PROP_CLASSFIELDCODES = "ClassFieldCodes";
	public static String PROP_CLASSJOINFIELD = "ClassJoinField";
	public static String PROP_DOCJOINFIELD = "DocJoinField";
	
	public static final String PROP_REF_TYPE_NC = "NC����";
	public static final String PROP_REF_TYPE_SELF_DEF = "�Զ������";
	/**
	 * ��ȡ���е�Nc���ձ���
	 * @return
	 */
	private String[] getAllNcRefnode(){
		return LFWConnector.getAllNcRefNode();
	}
	
	private Map<String, Map<String, IRefNode>> allRefNodeMap = null;
	private Map<String, Map<String, IRefNode>> getAllRefNodeMap(){
		if(allRefNodeMap == null){
			String ctx = LFWUtility.getContextFromResource(LFWPersTool.getCurrentProject());
			allRefNodeMap = LFWConnector.getAllPoolRefNodes("/" + ctx);
		}
		return allRefNodeMap;
	}
	/**
	 * ��ȡ�����������е�refnodeId
	 * @return
	 */
	private String[] getPoolRefNodeId(){
		List<String> reflist = new ArrayList<String>();
		Map<String, Map<String, IRefNode>> refnodeMap = getAllRefNodeMap();
		for (Iterator<String> it = refnodeMap.keySet().iterator(); it.hasNext();) {
			String itctx = it.next();
			Map<String, IRefNode> ctxMap = refnodeMap.get(itctx);
			for (Iterator<String> itonly = ctxMap.keySet().iterator(); itonly.hasNext();){
				String itonlyN = itonly.next();
				IRefNode refNode = ctxMap.get(itonlyN);
				if(refNode == null)
					continue;
				reflist.add(refNode.getId());
			}
		}
		
		return (String[]) reflist.toArray(new String[reflist.size()]);
	}
	
	private String[] getAllDatasts(){
		List<String> datasetList = new ArrayList<String>();
		LfwWidget widget = LFWPersTool.getCurrentWidget();
		if(widget != null){
			Dataset[] datas =  widget.getViewModels().getDatasets();
			if(datas != null){
				for (int i = 0; i < datas.length; i++) {
					Dataset ds = datas[i];
					if(!(ds instanceof IRefDataset))
						datasetList.add(ds.getId());
				}
				datasetList.add("");
				return (String[])datasetList.toArray(new String[datasetList.size()]);
			}
			else return null;
		}
		else 
			return null;
	}
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		if(getType() == null){
			if(refnode instanceof NCRefNode){
				setType(PROP_REF_TYPE_NC);
				((NCRefNode)refnode).setReadDs("masterDs");
			}else if(refnode instanceof SelfDefRefNode){
				setType(PROP_REF_TYPE_SELF_DEF);
			}else{ 
				setType(PROP_REF_TYPE_SELF_DEF);
			}
		}
		if(getType().equals(PROP_REF_TYPE_SELF_DEF)){
			//prop = 1;
			if(refnode instanceof SelfDefRefNode){
				newrefnode = (SelfDefRefNode)refnode;
			}
			if(newrefnode == null){
				newrefnode = new SelfDefRefNode();
				newrefnode.setId(refnode.getId());
			}
			setRefnode(newrefnode);
		}else if(getType().equals(PROP_REF_TYPE_NC)){
			//prop = 2;
			if(refnode instanceof NCRefNode){
				ncrefnode = (NCRefNode) refnode;
			}
			if(ncrefnode == null){
				ncrefnode = new NCRefNode();
				ncrefnode.setId(refnode.getId());
				ncrefnode.setReadDs(null);
			}
			setRefnode(ncrefnode);
		}
		
		if(this.isFromPool){
//			PropertyDescriptor pd = new ObjectComboPropertyDescriptor(PROP_TYPE, "��������", Constant.POOLREFTYPE);
//			pd.setCategory("����");
//			al.add(pd);
		}else{
			PropertyDescriptor pd = new ObjectComboPropertyDescriptor(PROP_TYPE, "��������", Constant.REFTYPE);
			pd.setCategory("����");
			al.add(pd);
			
			PropertyDescriptor propCaption = new TextPropertyDescriptor(PROP_CAPTION,"��ʾ����");
			propCaption.setCategory("����");
			al.add(propCaption);
		}
		PropertyDescriptor pds = new NoEditableTextPropertyDescriptor(PROP_ID, "����ID");
		pds.setCategory("����");
		al.add(pds);
		
		PropertyDescriptor pd7 = new TextPropertyDescriptor(PROP_PATH,"ҳ��·��");
		pd7.setCategory("����");
		al.add(pd7);
		
		PropertyDescriptor pisDialog = new ComboBoxPropertyDescriptor(PROP_ISDIALOG,"�Ƿ�Dialog", Constant.ISLAZY);
		pisDialog.setCategory("����");
		al.add(pisDialog);
		
		PropertyDescriptor pisFresh = new ComboBoxPropertyDescriptor(PROP_ISREFRESH,"�Ƿ�ˢ��", Constant.ISLAZY);
		pisFresh.setCategory("����");
		al.add(pisFresh);
		
		if((getType() != null && getType().equals(PROP_REF_TYPE_NC)) || refnode instanceof NCRefNode){
			if(this.isFromPool){
//				PropertyDescriptor pd = new TextPropertyDescriptor(PROP_WRITEDS,"д�����ݼ�");
//				pd.setCategory("����");
//				al.add(pd);
			}else{
				PropertyDescriptor pd2 = new ObjectComboPropertyDescriptor(PROP_REFID,"���ò���ID", getPoolRefNodeId());
				pd2.setCategory("����");
				al.add(pd2);
				
				PropertyDescriptor pd = new ObjectComboPropertyDescriptor(PROP_WRITEDS,"д�����ݼ�", getAllDatasts());
				pd.setCategory("����");
				al.add(pd);
				PropertyDescriptor pd3 = new TextPropertyDescriptor(PROP_READFIELDS,"�����ֶ�");
				pd3.setCategory("����");
				al.add(pd3);
				
				PropertyDescriptor pd4 = new TextPropertyDescriptor(PROP_WRITEFIELDS,"д���ֶ�");
				pd4.setCategory("����");
				al.add(pd4);
			}
			PropertyDescriptor pd1 = new TextPropertyDescriptor(PROP_READDS,"�������ݼ�");
			pd1.setCategory("����");
			al.add(pd1);
			
			PropertyDescriptor pd5 = new ComboBoxPropertyDescriptor(PROP_MULTISEL,"�Ƿ��ѡ", Constant.ISLAZY);
			pd5.setCategory("����");
			al.add(pd5);
			
			PropertyDescriptor pd6 = new ComboBoxPropertyDescriptor(PROP_SELLEAFONLY,"ֻѡ��Ҷ�ӽڵ�", Constant.ISLAZY);
			pd6.setCategory("����");
			al.add(pd6);

			PropertyDescriptor pd8 = new TextPropertyDescriptor(PROP_PAGEMETA,"ҳ��ID");
			pd8.setCategory("����");
			al.add(pd8);
			
			PropertyDescriptor pd9 = new TextPropertyDescriptor(PROP_PAGEMODEL,"ҳ�������");
			pd9.setCategory("����");
			al.add(pd9);
			
			PropertyDescriptor pd10 = new TextPropertyDescriptor(PROP_REFMODEL, "����ģ����");
			pd10.setCategory("����");
			al.add(pd10);
			
			PropertyDescriptor pd11 = new TextPropertyDescriptor(PROP_DATALISTENER, "���ݼ�����");
			pd11.setCategory("����");
			al.add(pd11);
			
			PropertyDescriptor pddelegator = new TextPropertyDescriptor(PROP_REFNODEDELEGATOR, "����ȷ������Deldgator");
			pddelegator.setCategory("����");
			al.add(pddelegator);
			
			PropertyDescriptor pisallowInput = new ComboBoxPropertyDescriptor(PROP_ALLOWINPUT,"�Ƿ���������", Constant.ISLAZY);
			pisallowInput.setCategory("����");
			al.add(pisallowInput);
			
			PropertyDescriptor pd = new ComboBoxPropertyDescriptor(PROP_USERPOWER,"�Ƿ���Ȩ�޿���", Constant.ISLAZY);
			pd.setCategory("�߼�");
			al.add(pd);
			
			pd = new ObjectComboPropertyDescriptor(PROP_NCREFNODE,"NC���ձ���", getAllNcRefnode());
			pd.setCategory("�߼�");
			al.add(pd);
			
			pd = new ComboBoxPropertyDescriptor(PROP_ORGS,"�Ƿ����ҵ��Ԫ", Constant.ISLAZY);
			pd.setCategory("�߼�");
			al.add(pd);
			
			if(refnode instanceof NCRefNode){
				ncrefnode = (NCRefNode)refnode;
			}
			if(ncrefnode == null){
				ncrefnode = new NCRefNode();
				ncrefnode.setId(refnode.getId());
			}
			setRefnode(ncrefnode);
			setType(PROP_REF_TYPE_NC);
		}else if((getType() != null && getType().equals(PROP_REF_TYPE_SELF_DEF)) || refnode instanceof SelfDefRefNode){
			setType(PROP_REF_TYPE_SELF_DEF);
		}
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		WebElement webele = getWebElement();
		if(!canChange(webele)){
			return;
		}
		if(PROP_TYPE.equals(id)){
			setType((String)value);
		}
		else if(PROP_ID.equals(id)){
			refnode.setId((String)value);
			LFWBaseEditor.getActiveEditor().refreshTreeItem(refnode);
		}
		else if(PROP_REFNODEDELEGATOR.equals(id) && refnode instanceof RefNode){
			((RefNode)refnode).setRefnodeDelegator((String)value);
		}
		else if(PROP_PAGEMODEL.equals(id) && refnode instanceof RefNodeConf){
			((RefNodeConf)refnode).setPageModel((String)value);
		}
		else if(PROP_REFMODEL.equals(id) && refnode instanceof RefNodeConf){
			((RefNodeConf)refnode).setRefModel((String)value);
		}
		else if(PROP_DATALISTENER.equals(id) && refnode instanceof RefNodeConf){
			((RefNodeConf)refnode).setDataListener((String)value);
		}
		else if(PROP_WRITEDS.equals(id) && refnode instanceof RefNode){
			((RefNode)refnode).setWriteDs((String)value);
		}
		else if(PROP_READDS.equals(id) && refnode instanceof RefNodeConf){
			((RefNodeConf)refnode).setReadDs((String)value);
		}
		else if(PROP_REFID.equals(id) && refnode instanceof RefNode){
			((RefNode)refnode).setRefId((String)value);
		}
		else if(PROP_CAPTION.equals(id)){
			String oldValue = refnode.getText();
			if((oldValue == null && value != null)  || (oldValue != null && value != null && !oldValue.equals(value))){
				refnode.setText((String)value);
				RefNodeEditor.getActiveEditor().refreshTreeItemText(refnode);
			}
		}
		else if(PROP_READFIELDS.equals(id) && refnode instanceof RefNode){
			((RefNode)refnode).setReadFields((String)value);
		}
		else if(PROP_WRITEFIELDS.equals(id) && refnode instanceof RefNode){
			((RefNode)refnode).setWriteFields((String)value);
		}
		else if(PROP_MULTISEL.equals(id) && refnode instanceof RefNodeConf){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			((RefNodeConf)refnode).setMultiSel(truevalue);
		}
		else if(PROP_ISREFRESH.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			refnode.setRefresh(truevalue);
		}
		else if(PROP_ALLOWINPUT.equals(id) && refnode instanceof RefNodeConf){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			((RefNodeConf)refnode).setAllowInput(truevalue);
		}
		else if(PROP_ISDIALOG.equals(id)){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			refnode.setDialog(truevalue);
		}
		else if(PROP_PAGEMETA.equals(id) && refnode instanceof RefNodeConf){
			((RefNodeConf)refnode).setPagemeta((String)value);
		}
		else if(PROP_USERPOWER.equals(id) && refnode instanceof NCRefNode){
			NCRefNode ncRefNode = (NCRefNode) refnode;
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			ncRefNode.setUsePower(truevalue);
		}
		else if(PROP_ORGS.equals(id) && refnode instanceof NCRefNode){
			NCRefNode ncRefNode = (NCRefNode) refnode;
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			ncRefNode.setOrgs(truevalue);
		}
		else if(PROP_SELLEAFONLY.equals(id) && refnode instanceof RefNodeConf){
			boolean truevalue = false;
			if((Integer)value == 0)
				truevalue = true;
			((RefNodeConf)refnode).setSelLeafOnly(truevalue);
		}
		else if(PROP_NCREFNODE.equals(id) && refnode instanceof NCRefNode){
			NCRefNode ref = (NCRefNode)refnode;
			ref.setRefcode((String)value);
		}
//		else if(PROP_PROCESSOR.equals(id))
//			refnode.setProcessor((String)value);
		else if(PROP_PATH.equals(id)){
			refnode.setPath((String)value);
		}
	}
	
	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getFilterSql() {
		return filterSql;
	}

	public void setFilterSql(String filterSql) {
		this.filterSql = filterSql;
	}

	public Object getPropertyValue(Object id) {
		if(PROP_TYPE.equals(id))
			return getType();
		if(PROP_ID.equals(id))
			return refnode.getId() == null?"":refnode.getId();
		else if(PROP_PAGEMODEL.equals(id) && refnode instanceof RefNodeConf){
			return ((RefNodeConf)refnode).getPageModel() == null?"":((RefNodeConf)refnode).getPageModel();
		}
		else if(PROP_REFNODEDELEGATOR.equals(id) && refnode instanceof RefNode){
			return ((RefNode)refnode).getRefnodeDelegator() == null?"":((RefNode)refnode).getRefnodeDelegator();
		}
		else if(PROP_DATALISTENER.equals(id) && refnode instanceof RefNodeConf)
			return ((RefNodeConf)refnode).getDataListener() == null?"":((RefNodeConf)refnode).getDataListener();
		else if(PROP_WRITEDS.equals(id) && refnode instanceof RefNode)
			return ((RefNode)refnode).getWriteDs() == null?"":((RefNode)refnode).getWriteDs();
		else if(PROP_READDS.equals(id) && refnode instanceof RefNodeConf)
			return ((RefNodeConf)refnode).getReadDs() == null?"":((RefNodeConf)refnode).getReadDs();
		else if(PROP_REFID.equals(id) && refnode instanceof RefNode)
			return ((RefNode)refnode).getRefId() == null?"":((RefNode)refnode).getRefId();
		else if(PROP_CAPTION.equals(id))
			return refnode.getText() == null?"":refnode.getText();
		else if(PROP_REFMODEL.equals(id) && refnode instanceof RefNodeConf)
			return ((RefNodeConf)refnode).getRefModel() == null? "":((RefNodeConf)refnode).getRefModel();
		else if(PROP_READFIELDS.equals(id) && refnode instanceof RefNode)
			return ((RefNode)refnode).getReadFields() == null?"":((RefNode)refnode).getReadFields();
		else if(PROP_WRITEFIELDS.equals(id) && refnode instanceof RefNode)
			return ((RefNode)refnode).getWriteFields() == null?"":((RefNode)refnode).getWriteFields();
		else if(PROP_MULTISEL.equals(id) && refnode instanceof RefNodeConf)
			return ((RefNodeConf)refnode).isMultiSel()? new Integer(0):new Integer(1);
		else if(PROP_ISREFRESH.equals(id))
			return (refnode.isRefresh() == true)? new Integer(0):new Integer(1);
		else if(PROP_ALLOWINPUT.equals(id) && refnode instanceof RefNodeConf)
			return ((RefNodeConf)refnode).isAllowInput()? new Integer(0): new Integer(1);
		else if(PROP_ISDIALOG.equals(id))
			return (refnode.isDialog() == true)? new Integer(0):new Integer(1);
		else if(PROP_SELLEAFONLY.equals(id) && refnode instanceof RefNodeConf)
			return ((RefNodeConf)refnode).isSelLeafOnly()? new Integer(0):new Integer(1);
//		else if(PROP_PROCESSOR.equals(id))
//			return refnode.getProcessor() == null?"":refnode.getProcessor();
		else if(PROP_PATH.equals(id))
			return refnode.getPath() == null?"":refnode.getPath();
		else if(PROP_PAGEMETA.equals(id) && refnode instanceof RefNodeConf)
			return ((RefNodeConf)refnode).getPagemeta() == null?"":((RefNodeConf)refnode).getPagemeta();
		else if(PROP_NCREFNODE.equals(id)){
			NCRefNode ncref = (NCRefNode)refnode;
			return ncref.getRefcode() == null?"":ncref.getRefcode();
		}
		else if(PROP_USERPOWER.equals(id)){
			NCRefNode ncref = (NCRefNode)refnode;
			return ncref.isUsePower()? new Integer(0): new Integer(1);
		}
		else if(PROP_ORGS.equals(id)){
			NCRefNode ncref = (NCRefNode)refnode;
			return ncref.isOrgs()? new Integer(0): new Integer(1);
		}
		else
			return null;
	}
	
	public void firePropUpdate(FormElement prop){
		fireStructureChange(PROP_UPDATE_CELL_PROPS, prop);
	}
	
	public WebElement getWebElement() {
		return refnode;
	}
	
}
