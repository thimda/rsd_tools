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
 * 插件 Model定义
 * 
 * @author dingrf
 */
public class PluginElementObj extends PortalElementObjWithGraph{

	private static final long serialVersionUID = 7053401624264036804L;

	/**插件元素*/
	public static final String PROP_PLUGIN_ELEMENT = "plugin_element";
	
	/**扩展点*/
	public static final String PROP_POINT = "point";
	
	/**名称*/
	public static final String PROP_TITLE = "title";
	
	/**类*/
	public static final String PROP_CLASSNAME = "classname";
	
	/**当前图像*/      
	private PluginEleObjFigure fingure;
	
	/**Plugin对象*/      
	private PtPlugin ptPlugin;

	/**扩展点对象列表*/      
	private List<ExPoint>  exPoint;

	/**当前扩展点*/      
	private ExPoint  currentExPoint;

	/**扩展对象列表*/      
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
	 * 属性定义
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[3];
		pds[0] = new TextPropertyDescriptor(PROP_POINT, "扩展点");
		pds[0].setCategory("基本");
		pds[1] = new TextPropertyDescriptor(PROP_TITLE,"名称");
		pds[1].setCategory("基本");
		pds[2] = new TextPropertyDescriptor(PROP_CLASSNAME, "接口");
		pds[2].setCategory("基本");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	
	/**
	 * 属性值设置
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
	 * 取属性值
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
