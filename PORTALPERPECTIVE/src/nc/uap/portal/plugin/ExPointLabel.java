package nc.uap.portal.plugin;

import nc.uap.portal.plugins.model.ExPoint;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

/**
 *扩展点label
 * 
 * @author dingrf
 */
public class ExPointLabel extends Label{

	/**
	 * 扩展点对象
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
