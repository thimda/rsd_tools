package nc.lfw.editor.pagemeta.relation;

import java.util.Map;

import nc.lfw.editor.widget.WidgetElementObj;
import nc.uap.lfw.core.page.PlugoutDescItem;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget连接器 参数构造向导 第一页
 * 
 * @author guoweic
 *
 */
public class WidgetRelationParamPage1 extends WizardPage {
	
	private WidgetElementObj sourceWidget;

	/**
	 * 已有参数集合
	 */
	private Map<String, PlugoutDescItem> parameterMap;
	
	private WidgetRelationParamPanel1 mainContainer = null;
	
	protected WidgetRelationParamPage1(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("参数构造向导");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationParamPanel1(parent, SWT.NONE, sourceWidget);
		
		// 增加参数名输入框事件监听
		mainContainer.getNameText().addModifyListener(new MyModifyListener());
		
//		setMessage("构建参数");
		
		setControl(mainContainer);
	}

	public void setSourceWidget(WidgetElementObj sourceWidget) {
		this.sourceWidget = sourceWidget;
	}

	public WidgetRelationParamPanel1 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * 参数名输入框改变事件监听器，控制“下一步”和“完成”按钮
	 * 
	 * @author guoweic
	 *
	 */
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(false);
			String name = mainContainer.getNameText().getText().trim();
			if (!"".equals(name) && !parameterMap.containsKey(name) && null != mainContainer.getCurrentComp()) {
				mainContainer.setName(name);
				setPageComplete(true);
			}
		}
	}

	public void setParameterMap(Map<String, PlugoutDescItem> parameterMap) {
		this.parameterMap = parameterMap;
	}


}
