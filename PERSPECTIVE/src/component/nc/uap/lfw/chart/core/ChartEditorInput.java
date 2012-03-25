package nc.uap.lfw.chart.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ChartComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class ChartEditorInput extends ElementEditorInput{

	public ChartEditorInput(ChartComp charComp, LfwWidget widget, PageMeta pagemeta){
		super(charComp, widget, pagemeta);
	}
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Í¼±í±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "Í¼±í±à¼­Æ÷";
	}
}
