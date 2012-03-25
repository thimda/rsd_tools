/**
 * 
 */
package nc.uap.lfw.core.base;

import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.palette.PaletteImage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * 
 * 节点行为抽象基类
 * @author chouhl
 *
 */
public abstract class NodeAction extends Action {

	public NodeAction(){
		super();
	}
	
	public NodeAction(String text) {
		super(text);
		
	}
	
	public NodeAction(String text, String toolTipText) {
		super(text);
		setToolTipText(toolTipText);
	}
	
	public NodeAction(String text, ImageDescriptor image){
		super(text, image);
	}
	
	public NodeAction(String text, int style){
		super(text, style);
	}
	
	public void run(){
		super.run();
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		ImageDescriptor image = null;
		if(getText() != null){
			if(getText().contains(WEBProjConstants.NEW) || getText().contains(WEBProjConstants.ADD)){
				image = PaletteImage.getCreateTreeImgDescriptor();
			}else if(getText().contains(WEBProjConstants.REFRESH)){
				image = PaletteImage.getRefreshImgDescriptor();
			}else if(getText().contains(WEBProjConstants.MODIFY) || getText().contains(WEBProjConstants.EDITOR)){
				image = PaletteImage.getEditorDescriptor();
			}else if(getText().contains(WEBProjConstants.DELETE)){
				image = PaletteImage.getDeleteImgDescriptor();
			}else if(getText().contains(WEBProjConstants.UI_GUIDE)){
				image = PaletteImage.getPortletDescriptor();
			}else if(getText().contains(WEBProjConstants.REFERENCE)){
				image = PaletteImage.getRefnodeDescriptor();
			}else if(getText().contains(WEBProjConstants.REGISTRY)){
				image = PaletteImage.getCreateTreeImgDescriptor();
			}else if(getText().contains(WEBProjConstants.MANAGER)){
				image = PaletteImage.getCreateTreeImgDescriptor();
			}
		}
		return image;
	}
	
}
