package nc.lfw.editor.pagemeta.relation;

import nc.lfw.editor.widget.WidgetElementObj;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget连接器 触发时机构造向导 第一页
 * 
 * @author guoweic
 *
 */
public class WidgetRelationEmitPage1 extends WizardPage {
	
	private WidgetElementObj sourceWidget;
	
	private WidgetRelationEmitPanel1 mainContainer = null;
	
	protected WidgetRelationEmitPage1(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("触发时机构造向导");
		
		setPageComplete(true);
		
		mainContainer = new WidgetRelationEmitPanel1(parent, SWT.NONE, sourceWidget);
		
		// 增加触发时机名输入框事件监听
//		mainContainer.getNameText().addModifyListener(new MyModifyListener());
		
//		setMessage("构建触发时机");
		
		setControl(mainContainer);
	}

	public void setSourceWidget(WidgetElementObj sourceWidget) {
		this.sourceWidget = sourceWidget;
	}

	public WidgetRelationEmitPanel1 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * 触发时机名输入框改变事件监听器，控制“下一步”和“完成”按钮
	 * 
	 * @author guoweic
	 *
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(false);
			String name = mainContainer.getNameText().getText().trim();
			if (!"".equals(name) && null != mainContainer.getCurrentComp()) {
				mainContainer.setName(name);
				setPageComplete(true);
			}
		}
	}
	 */

}
