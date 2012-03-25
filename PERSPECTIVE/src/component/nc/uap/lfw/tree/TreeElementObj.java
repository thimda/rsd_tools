package nc.uap.lfw.tree;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LFWWebComponentObj;
import nc.uap.lfw.core.comp.TreeViewComp;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.perspective.model.Constant;
import nc.uap.lfw.tree.core.TreeEditor;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * �������model
 * @author zhangxya
 *
 */
public class TreeElementObj extends LFWWebComponentObj{

	private static final long serialVersionUID = 6253081418703115641L;
	
	public static final String PROP_DRAGENABLE= "element_DRAGENABLE";
	public static final String PROP_WITHCHECKBOX= "element_WITHCHECKBOX";
	public static final String PROP_WITHROOT= "element_WITHROOT";
	public static final String PROP_ROOTOPEN= "element_ROOTOPEN";
	public static final String PROP_TEXT= "element_TEXT";
	public static final String PROP_LANGDIR= "element_LANGDIR";
	public static final String PROP_I18NNAME= "element_I18NNAME";	
	public static final String PROP_TREE_ELEMENT ="tree_element";
	public static final String PROP_CAPTION = "element_CAPTION";
	public static final String PROP_CHECKBOXMODEL = "element_CHECKBOXMODEL";
	
	private TreeViewComp treeComp;
	private Dataset ds;
	private boolean truevalue = false;
	
	public TreeViewComp getTreeComp() {
		return treeComp;
	}
	
	public void setTreeComp(TreeViewComp treeComp) {
		this.treeComp = treeComp;
		fireStructureChange(PROP_TREE_ELEMENT, treeComp);
	}

	public Dataset getDs() {
		return ds;
	}

	public void setDs(Dataset ds) {
		this.ds = ds;
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		al.addAll(Arrays.asList(super.getPropertyDescriptors()));
		PropertyDescriptor[] pds = new PropertyDescriptor[9];
		pds[0] =  new ComboBoxPropertyDescriptor(PROP_WITHCHECKBOX,"�Ƿ��ж�ѡ��", Constant.ISLAZY);
		pds[0].setCategory("�߼�");
		pds[1] =  new ComboBoxPropertyDescriptor(PROP_ROOTOPEN,"�Ƿ�򿪸��ڵ�", Constant.ISLAZY);
		pds[1].setCategory("�߼�");
		pds[2] = new ComboBoxPropertyDescriptor(PROP_DRAGENABLE,"�Ƿ����ק", Constant.ISLAZY);
		pds[2].setCategory("�߼�");
		pds[3] = new ComboBoxPropertyDescriptor(PROP_WITHROOT,"�Ƿ���ʾ���ڵ�", Constant.ISLAZY);
		pds[3].setCategory("�߼�");
		pds[4] = new TextPropertyDescriptor(PROP_TEXT, "���ڵ�Ĭ����ʾֵ");
		pds[4].setCategory("�߼�");
		pds[5] = new TextPropertyDescriptor(PROP_I18NNAME, "������ʾֵ");
		pds[5].setCategory("�߼�");
		pds[6] = new TextPropertyDescriptor(PROP_LANGDIR, "����Ŀ¼");
		pds[6].setCategory("�߼�");
		//caption
		pds[8] = new TextPropertyDescriptor(PROP_CAPTION,"����");
		pds[8].setCategory("����");
		pds[7] = new ComboBoxPropertyDescriptor(PROP_CHECKBOXMODEL,"��ѡ����", Constant.CHECKBOXMODEL);
		pds[7].setCategory("�߼�");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if(PROP_ROOTOPEN.equals(id)){
			if((Integer)value == 0)
				truevalue = true;
			else
				truevalue = false;
			treeComp.setRootOpen(truevalue);
		}
		else if(PROP_DRAGENABLE.equals(id)){
			if((Integer)value == 0)
				truevalue = true;
			else
				truevalue = false;
			treeComp.setDragEnable(truevalue);
		}
		else if(PROP_WITHCHECKBOX.equals(id)){
			if((Integer)value == 0)
				truevalue = true;
			else
				truevalue = false;
			treeComp.setWithCheckBox(truevalue);
		}
		else if(PROP_WITHROOT.equals(id)){
			if((Integer)value == 0)
				truevalue = true;
			else
				truevalue = false;
			treeComp.setWithRoot(truevalue);
		}
		else if(PROP_CHECKBOXMODEL.equals(id)){			
			treeComp.setCheckBoxModel((Integer)value);
		}
		else if(PROP_CAPTION.equals(id)){
			String oldValue = treeComp.getCaption();
			if((oldValue == null && value != null)  || (oldValue != null && value != null && !oldValue.equals(value))){
				treeComp.setCaption((String)value);
				TreeEditor.getActiveEditor().refreshTreeItemText(treeComp);
			}
		}
		else if(PROP_TEXT.equals(id))
			treeComp.setText((String)value);
		else if(PROP_I18NNAME.equals(id))
			treeComp.setI18nName((String)value);
		else if(PROP_LANGDIR.equals(id))
			treeComp.setLangDir((String)value);
	}
	
	public Object getPropertyValue(Object id) {
		if(PROP_ROOTOPEN.equals(id))
			return (treeComp.isRootOpen() == true)? new Integer(0):new Integer(1);
		else if(PROP_DRAGENABLE.equals(id))
			return (treeComp.isDragEnable() == true)? new Integer(0):new Integer(1);
		else if(PROP_WITHCHECKBOX.equals(id))
			return (treeComp.isWithCheckBox() == true)? new Integer(0):new Integer(1);
		else if(PROP_WITHROOT.equals(id))
			return (treeComp.isWithRoot() == true)? new Integer(0):new Integer(1);
	    else if(PROP_CHECKBOXMODEL.equals(id))
				return treeComp.getCheckBoxModel();
		else if(PROP_TEXT.equals(id))
			return treeComp.getText() == null?"":treeComp.getText();
		else if(PROP_I18NNAME.equals(id))
			return treeComp.getI18nName() == null?"":treeComp.getI18nName();
		else if(PROP_LANGDIR.equals(id))
			return treeComp.getLangDir()== null?"":treeComp.getLangDir();
		else if(PROP_CAPTION.equals(id))
			return treeComp.getCaption() == null?"":treeComp.getCaption();
		return super.getPropertyValue(id);
	}
	
	public WebElement getWebElement() {
		return treeComp;
	}
}
