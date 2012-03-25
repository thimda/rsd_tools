package nc.uap.lfw.perspective.webcomponent;

import java.io.File;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.aciton.NCUserRelatedAction;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWProjectModel;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;

public class LFWProjectTreeItem extends LFWBasicTreeItem implements ILFWTreeNode {
//	private static final String ProjectModelKey = "projectModel";
	private LFWProjectModel pm = null;
	private String moduleName = null;
	public LFWProjectTreeItem(Tree parent, LFWProjectModel pm) {
		super(parent, SWT.NONE);
		this.pm = pm;
		init();
	}
	
	protected void checkSubclass () {
	}
	private void init() {
		File f = new File(pm.getJavaProject().getLocation().toFile(), pm.getProjectName());
		if (!f.exists())
			f.mkdirs();
		setText(pm.getProjectName()+" ["+getModuleName()+"]");
		setData(pm);
//		setData(ProjectModelKey, pm);
		setImage(getProjectImage());
	}

	private Image getProjectImage() {
		ImageDescriptor imageDescriptor = MainPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "project.gif");
		return imageDescriptor.createImage();
	}

	public LFWProjectModel getProjectModel() {
		return (LFWProjectModel) getData();
	}

	public File getFile() {
		return pm.getMDRoot();
	}

	public void deleteNode() {
		MessageDialog.openInformation(null, "提示", "不能删除该" + WEBProjConstants.PAGEMETA_CN + "："+getText());
	}
	public String getIPathStr() {
		String IPath = getProjectModel().getProjectName()+"/METADATA";
		return IPath;
	}
	public String getModuleName(){
		if(moduleName == null){
			moduleName = LFWPersTool.getProjectModuleName(pm.getJavaProject());
		}
		return moduleName;
	}
	
	public void addMenuListener(IMenuManager manager){
		NCUserRelatedAction ncUserRelateAction = new NCUserRelatedAction();
		manager.add(ncUserRelateAction);
	} 
	
}
