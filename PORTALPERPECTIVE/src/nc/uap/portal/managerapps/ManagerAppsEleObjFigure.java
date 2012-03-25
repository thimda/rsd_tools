package nc.uap.portal.managerapps;

import nc.uap.portal.core.PortalBaseRectangleFigure;
import nc.uap.portal.core.PortalElementObjWithGraph;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * ���ܷ���ͼ��
 * 
 * @author dingrf
 */
public class ManagerAppsEleObjFigure extends PortalBaseRectangleFigure{

	/**
	 * ͼ����ɫ
	 */
	private static Color bgColor = new Color(null, 173, 141, 82);
	
	public ManagerAppsEleObjFigure(PortalElementObjWithGraph ele){
		super(ele);
		setTypeLabText("<<���ܷ���>>");
		setBackgroundColor(bgColor);
		ManagerAppsElementObj managerApps = (ManagerAppsElementObj) ele;
		setTitleText(managerApps.getCurrentManagerCategory().getText(), managerApps.getCurrentManagerCategory().getText());
		Point point = managerApps.getLocation();
		int x, y;
		if(point != null){
			x = point.x;
			y = point.y;
		}else{
			x = 100;
			y = 100;
		}
		setBounds(new Rectangle(x, y, 150, 150));
	}
	
	protected String getTypeText() {
		return "<<���ܷ���>>";
	}
	
}
	
