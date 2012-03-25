package nc.uap.portal.core;

import java.util.ArrayList;

import nc.lfw.editor.common.LFWBasicElementObj;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * portlet �����ؼ�
 * @author dingrf
 *
 */
public class PortalElementObjWithGraph extends LFWBasicElementObj {

	private static final long serialVersionUID = -3650115440868732362L;
	
	public static final String PROP_CELL_SIZE = "cell_size";
	
	public static final String PROP_CELL_LOCATION = "cell_location";
	
	/**ͼ����ʾλ��*/
	private PortalBaseGraph graph;
	
	/**λ��*/
	private Point location = new Point(0, 0);
	
	/**ͼ�δ�С*/
	private Dimension size = new Dimension(80, 100);
	
	public PortalBaseGraph getGraph() {
		return graph;
	}
	
	public void setGraph(PortalBaseGraph graph) {
		this.graph = graph;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		Point oldP = this.location;
		this.location = location;
		firePropertyChange(PROP_CELL_LOCATION, oldP, location);
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		Dimension oldD = this.size;
		this.size = size;
		firePropertyChange(PROP_CELL_SIZE, oldD, size);
	}

	/**
	 * ��ȡ��������
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		return al.toArray(new IPropertyDescriptor[0]);
	}

	/**
	 * ��ȡ����ֵ
	 */
	@Override
	public Object getPropertyValue(Object id) {
		return null;
	}
	
	/**
	 * ��������ֵ
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
	}

}
