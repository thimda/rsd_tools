package nc.uap.portal.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.uap.portal.core.PortalElementObjWithGraph;
import nc.uap.portal.plugins.model.ExPoint;
import nc.uap.portal.plugins.model.Extension;
import nc.uap.portal.plugins.model.PtPlugin;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * ��� Model����
 * 
 * @author dingrf
 */
public class PluginElementObj extends PortalElementObjWithGraph{

	private static final long serialVersionUID = 7053401624264036804L;

	/**���Ԫ��*/
	public static final String PROP_PLUGIN_ELEMENT = "plugin_element";
	
	/**��չ��*/
	public static final String PROP_POINT = "point";
	
	/**����*/
	public static final String PROP_TITLE = "title";
	
	/**��*/
	public static final String PROP_CLASSNAME = "classname";
	
	/**��ǰͼ��*/      
	private PluginEleObjFigure fingure;
	
	/**Plugin����*/      
	private PtPlugin ptPlugin;

	/**��չ������б�*/      
	private List<ExPoint>  exPoint;

	/**��ǰ��չ��*/      
	private ExPoint  currentExPoint;

	/**��չ�����б�*/      
	private List<Extension>  extension;

	public PluginEleObjFigure getFingure() {
		return fingure;
	}

	public void setFingure(PluginEleObjFigure fingure) {
		this.fingure = fingure;
	}

	public PtPlugin getPtPlugin() {
		return ptPlugin;
	}

	public void setPtPlugin(PtPlugin ptPlugin) {
		this.ptPlugin = ptPlugin;
		fireStructureChange(PROP_PLUGIN_ELEMENT, ptPlugin);
	}

	public List<ExPoint> getExPoint() {
		return exPoint;
	}

	public void setExPoint(List<ExPoint> exPoint) {
		this.exPoint = exPoint;
	}

	public ExPoint getCurrentExPoint() {
		return currentExPoint;
	}

	public void setCurrentExPoint(ExPoint currentExPoint) {
		this.currentExPoint = currentExPoint;
	}

	public List<Extension> getExtension() {
		return extension;
	}

	public void setExtension(List<Extension> extension) {
		this.extension = extension;
	}

	/**
	 * ���Զ���
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[3];
		pds[0] = new TextPropertyDescriptor(PROP_POINT, "��չ��");
		pds[0].setCategory("����");
		pds[1] = new TextPropertyDescriptor(PROP_TITLE,"����");
		pds[1].setCategory("����");
		pds[2] = new TextPropertyDescriptor(PROP_CLASSNAME, "�ӿ�");
		pds[2].setCategory("����");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	/**
	 * ����ֵ����
	 */
	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
		if (currentExPoint==null)
			return;
		if(PROP_POINT.equals(id)){
			currentExPoint.setPoint((String)value);
		}
		else if(PROP_TITLE.equals(id)){
			currentExPoint.setTitle((String)value);
			fingure.getCurrentLabel().setText((String)value);
		}
		else if(PROP_CLASSNAME.equals(id)){
			currentExPoint.setClassname((String)value);
		}
	}

	/**
	 * ȡ����ֵ
	 */
	public Object getPropertyValue(Object id) {
		if (currentExPoint!=null){
			if(PROP_POINT.equals(id))
				return currentExPoint.getPoint()==null?"":currentExPoint.getPoint();
			else if(PROP_TITLE.equals(id))
				return currentExPoint.getTitle()==null?"":currentExPoint.getTitle();
			else if(PROP_CLASSNAME.equals(id))
				return currentExPoint.getClassname()==null?"":currentExPoint.getClassname();
			else return super.getPropertyValue(id);
		}
		else return super.getPropertyValue(id);
	}
}
