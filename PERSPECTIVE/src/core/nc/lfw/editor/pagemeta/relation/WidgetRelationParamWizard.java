package nc.lfw.editor.pagemeta.relation;

import java.util.Map;

import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.page.PlugoutDescItem;

import org.eclipse.jface.wizard.Wizard;

/**
 * Widget连接器 参数构造向导
 * @author guoweic
 *
 */
public class WidgetRelationParamWizard extends Wizard {
	
	private WidgetElementObj sourceWidget;
	
	private WidgetRelationParamPage1 page1;
	
	private WidgetRelationParamPage2 page2;
	
	private PlugoutDescItem plugoutParam = null;
	
	/**
	 * 已有参数集合
	 */
	private Map<String, PlugoutDescItem> parameterMap;
	
	/**
	 * 加入各页面
	 */
	public void addPages() {
		
		// 创建页面对象并设置页面名称
		page1 = new WidgetRelationParamPage1("page1");
		page1.setSourceWidget(sourceWidget);
		page1.setParameterMap(parameterMap);

		page2 = new WidgetRelationParamPage2("page2");
		
		// 加入页面
		addPage(page1);

		addPage(page2);
		
	}
	
	/**
	 * 判断“完成”按钮何时有效。返回true有效，false无效
	 */
	public boolean canFinish() {
		// 设置成，只有到最后一页时“完成”按钮有效
		if (this.getContainer().getCurrentPage() != page2) {
			return false;
		} else {
			// 为第二页设置参数
			page2.getMainContainer().setCurrentComp(page1.getMainContainer().getCurrentComp());
			page2.getMainContainer().reloadTypeComboData();
			page2.getMainContainer().reloadFieldTable();
			
			return page2.isPageComplete();
		}
		
	}
	
	
	public boolean performFinish() {
		// 完成后进行的操作
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
