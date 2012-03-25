/**
 * 
 */
package nc.uap.lfw.editor.window;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * 
 * WindowÕº–Œ¿‡
 * @author chouhl
 *
 */
public class WindowObj extends LfwElementObjWithGraph {

	private static final long serialVersionUID = 514645377817718158L;

	private PageMeta window = null;
	
	private WindowFigure figure = null;
	
	public WebElement getWebElement() {
		return null;
	}
	
	@Override
	public Object getPropertyValue(Object id) {
		return null;
	}
	
	@Override
	public void setPropertyValue(Object id, Object value){}
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return super.getPropertyDescriptors();
	}

	public PageMeta getWindow() {
		return window;
	}

	public void setWindow(PageMeta window) {
		this.window = window;
	}

	public WindowFigure getFigure() {
		return figure;
	}

	public void setFigure(WindowFigure figure) {
		this.figure = figure;
	}
	
}
