package nc.uap.lfw.excel.core;

import nc.lfw.editor.common.ElementEditorInput;
import nc.uap.lfw.core.comp.ExcelComp;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class ExcelEditorInput extends ElementEditorInput{

	
	public ExcelEditorInput(ExcelComp excelcomp, LfwWidget widget, PageMeta pagemeta) {
		super(excelcomp, widget, pagemeta);
	}
	
	
	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "Excel±à¼­Æ÷";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return  "Excel±à¼­Æ÷";
	}

}
