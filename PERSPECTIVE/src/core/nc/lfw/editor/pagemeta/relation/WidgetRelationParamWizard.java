package nc.lfw.editor.pagemeta.relation;

import java.util.Map;

import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.page.PlugoutDescItem;

import org.eclipse.jface.wizard.Wizard;

/**
 * Widget������ ����������
 * @author guoweic
 *
 */
public class WidgetRelationParamWizard extends Wizard {
	
	private WidgetElementObj sourceWidget;
	
	private WidgetRelationParamPage1 page1;
	
	private WidgetRelationParamPage2 page2;
	
	private PlugoutDescItem plugoutParam = null;
	
	/**
	 * ���в�������
	 */
	private Map<String, PlugoutDescItem> parameterMap;
	
	/**
	 * �����ҳ��
	 */
	public void addPages() {
		
		// ����ҳ���������ҳ������
		page1 = new WidgetRelationParamPage1("page1");
		page1.setSourceWidget(sourceWidget);
		page1.setParameterMap(parameterMap);

		page2 = new WidgetRelationParamPage2("page2");
		
		// ����ҳ��
		addPage(page1);

		addPage(page2);
		
	}
	
	/**
	 * �жϡ���ɡ���ť��ʱ��Ч������true��Ч��false��Ч
	 */
	public boolean canFinish() {
		// ���óɣ�ֻ�е����һҳʱ����ɡ���ť��Ч
		if (this.getContainer().getCurrentPage() != page2) {
			return false;
		} else {
			// Ϊ�ڶ�ҳ���ò���
			page2.getMainContainer().setCurrentComp(page1.getMainContainer().getCurrentComp());
			page2.getMainContainer().reloadTypeComboData();
			page2.getMainContainer().reloadFieldTable();
			
			return page2.isPageComplete();
		}
		
	}
	
	
	public boolean performFinish() {
		// ��ɺ���еĲ���
		plugoutParam = new PlugoutDescItem();
		plugoutParam.setName(page1.getMainContainer().getName());
		plugoutParam.setType(page2.getMainContainer().getType());
		plugoutParam.setSource(page1.getMainContainer().getCurrentComp().getId());
		plugoutParam.setValue(page2.getMainContainer().getValue());
		plugoutParam.setDesc(page2.getMainContainer().getDescText().getText().trim());
		
		return true;
	}

	public void setSourceWidget(WidgetElementObj sourceWidget) {
		this.sourceWidget = sourceWidget;
	}

	public PlugoutDescItem getPlugoutParam() {
		return plugoutParam;
	}

	public void setParameterMap(Map<String, PlugoutDescItem> parameterMap) {
		this.parameterMap = parameterMap;
	}

}
