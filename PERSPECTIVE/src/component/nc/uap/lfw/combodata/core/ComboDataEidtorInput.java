package nc.uap.lfw.combodata.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * 下拉数据集input
 * @author zhangxya
 *
 */
public class ComboDataEidtorInput  extends ElementEditorInput{


	public ComboDataEidtorInput(ComboData combo, LfwWidget widget, PageMeta pagemeta){
		super(combo, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "下拉数据编辑器";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "下拉数据编辑器";
	}
}
