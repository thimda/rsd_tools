package nc.uap.lfw.palette;

import nc.uap.lfw.core.WEBProjPlugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * PaletteµÄImage
 * @author zhangxya
 *
 */
public class PaletteImage {

	public static ImageDescriptor getRelationImgDescriptor() {
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "relation.png");
	}
	public static ImageDescriptor getEntityImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "entity.png");
	}
	public static ImageDescriptor getStartImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "start.gif");
	}
	public static ImageDescriptor getDependImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "depend.png");
	}
	
	public static ImageDescriptor getDeleteImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "delete.gif");
	}
	
	public static ImageDescriptor getCreateTreeImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "create.gif");
	}
	
	public static ImageDescriptor getCreateDsImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "create.gif");
	}
	
	public static ImageDescriptor getSelectedAllImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "checked.gif");
	}
	
	public static ImageDescriptor getUnCheckedImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "un_checked.gif");
	}
	
	public static ImageDescriptor getUnSelectedAllImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "cancel.gif");
	}
	
	public static ImageDescriptor getDialogImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "dialog.gif");
	}
	
	public static ImageDescriptor getCreateMenuImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "menubar.gif");
	}
	
	public static Image createDeleteImage(){
		ImageDescriptor errorDes = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "err.gif");
		return errorDes.createImage();
	}
	
	public static ImageDescriptor getCreateGridImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "create.gif");
	}
	
	public static ImageDescriptor getRefreshImgDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "refresh.gif");
	}
	
	public static ImageDescriptor getBar2DChartDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "create.gif");
	}
	
	public static ImageDescriptor getBar3DChartDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "dialog.gif");
	}
	
	public static ImageDescriptor getBarChartDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "chart.gif");
	}
		
	public static ImageDescriptor getEditorDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "editor.gif");
	}
	
	public static ImageDescriptor getPortletDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "portlet.gif");
	}
	
	public static ImageDescriptor getRefnodeDescriptor(){
		return WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "refnode.gif");
	}
	
}
