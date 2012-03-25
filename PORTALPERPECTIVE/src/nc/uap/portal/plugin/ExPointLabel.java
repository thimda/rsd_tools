package nc.uap.portal.plugin;

import nc.uap.portal.plugins.model.ExPoint;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

/**
 *��չ��label
 * 
 * @author dingrf
 */
public class ExPointLabel extends Label{

	/**
	 * ��չ�����
	 */
	private ExPoint exPoint;

	public ExPointLabel(String text, ExPoint exPoint) {
		super(text);
		this.exPoint = exPoint;
		setLabelAlignment(PositionConstants.LEFT);
		markError();
	}

	private void markError() {

	}

	
	public ExPoint getExPoint() {
		return exPoint;
	}

}
