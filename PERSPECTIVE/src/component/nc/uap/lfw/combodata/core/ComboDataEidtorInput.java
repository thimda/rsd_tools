package nc.uap.lfw.combodata.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * �������ݼ�input
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
		return "�������ݱ༭��";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "�������ݱ༭��";
	}
}
