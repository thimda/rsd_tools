package nc.uap.lfw.perspective.editor;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * Dataset��input
 * @author zhangxya
 *
 */
public class DataSetEitorInput extends ElementEditorInput{

	public DataSetEitorInput(Dataset ds,LfwWidget widget, PageMeta pagemeta){
		super(ds, widget, pagemeta);
	}
	
	

	public boolean exists() {
		// TODO Auto-generated method stub
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "���ݼ��༭��";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "���ݼ��༭��";
	}
}
