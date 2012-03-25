/**
 * 
 */
package nc.uap.lfw.window;

import nc.lfw.editor.pagemeta.RefreshNodeAction;
import nc.uap.lfw.palette.PaletteImage;

/**
 * 
 * 刷新Window节点类
 * @author chouhl
 *
 */
public class RefreshWindowNodeAction extends RefreshNodeAction {
	
	public RefreshWindowNodeAction(){
		super();
		setImageDescriptor(PaletteImage.getRefreshImgDescriptor());
	}

}
