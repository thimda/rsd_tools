package nc.uap.portal.plugin;

import nc.uap.lfw.perspective.LFWViewSheet;
import nc.uap.portal.core.PortalBaseRectangleFigure;
import nc.uap.portal.core.PortalElementObjWithGraph;
import nc.uap.portal.plugins.model.ExPoint;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

/**
 * �������ͼ��
 * 
 * @author dingrf
 */
public class PluginEleObjFigure extends PortalBaseRectangleFigure{

	/**����ͼ����ɫ*/
	private static Color bgColor = new Color(null, 239, 255, 150);
	
	/**�ܸ߶�*/
	private int height = 150;
	
	public PluginEleObjFigure(PortalElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<���>>");
		setBackgroundColor(bgColor);
		PluginElementObj plugin = (PluginElementObj) ele;
		plugin.setFingure(this);
		Point point = plugin.getLocation();
		int x, y;
		if(point != null){
			x = point.x;
			y = point.y;
		}else{
			x = 100;
			y = 100;
		}
		setBounds(new Rectangle(x, y, 150, 150));
		LoadPointItem(plugin);
	}
	
	protected String getTypeText() {
		return "<<���>>";
	}
	
	/**
	 * ����ExPointLabel�¼�
	 * 
	 * @param label
	 */
	public void addExPointLabelListener(ExPointLabel label) {
		label.addMouseListener(new MouseListener() {
			public void mouseDoubleClicked(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
				ExPointLabel currentLabel = (ExPointLabel) e.getSource();
				selectedLabel(currentLabel);
			}

		});
	}

	/**
	 * �ڵ�ѡ��
	 * 
	 * @param label
	 */
	public void selectedLabel(ExPointLabel label) {
		unSelectAllLabels();
		selectLabel(label);

		ExPoint exPoint = (ExPoint) label.getExPoint();
		((PluginElementObj) getElementObj()).setCurrentExPoint(exPoint);
		((PluginElementObj) getElementObj()).setExtension(exPoint.getExtensionList());
		// ������ʾ��������
		reloadPropertySheet((PluginElementObj) getElementObj());
		reloadExtensionSheet((PluginElementObj) getElementObj());
	}

	/**
	 * ������ʾ��չ����
	 * 
	 * @param element
	 */
	@SuppressWarnings("deprecation")
	public static void reloadExtensionSheet(Object element) {
		IViewPart[] views = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViews();
		LFWViewSheet sheet = null;
		for (int i = 0, n = views.length; i < n; i++) {
			if(views[i] instanceof LFWViewSheet) {
				sheet = (LFWViewSheet) views[i];
				break;
			}
		}
		if (sheet != null) {
			StructuredSelection select = new StructuredSelection(element);
			sheet.selectionChanged(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(), select);
		}
	}
	
	/**
	 * ������չ��
	 * @param obj
	 */
	private void LoadPointItem(PluginElementObj obj) {
		for(ExPoint pt : obj.getExPoint()){
			ExPointLabel label = new ExPointLabel(pt.getTitle(),pt);
			//this.add(label);
			addToContent(label);
			//this.setHeight(this..getHeight() + label.LINE_HEIGHT);
			this.addExPointLabelListener(label);
		}
		resizeHeight();
	}

	/**
	 * �������ø߶�
	 */
	public void resizeHeight() {
		int ItemHeight = this.getContentFigure().getChildren().size()*this.LINE_HEIGHT;
		this.height= ItemHeight;
		if(height<150){
			height=150;
		}
		this.setSize(150,this.height);
	}
	
}
	
