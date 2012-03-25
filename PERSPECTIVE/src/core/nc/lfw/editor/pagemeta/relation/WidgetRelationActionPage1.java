package nc.lfw.editor.pagemeta.relation;

import nc.lfw.editor.widget.WidgetElementObj;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget连接器 执行内容构造向导 第一页
 * 
 * @author guoweic
 *
 */
public class WidgetRelationActionPage1 extends WizardPage {
	
	private WidgetElementObj targetWidget;
	
	private WidgetRelationActionPanel1 mainContainer = null;
	
	protected WidgetRelationActionPage1(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("执行内容构造向导");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationActionPanel1(parent, SWT.NONE, targetWidget);
		
		// 增加执行内容名输入框事件监听
		mainContainer.getNameText().addModifyListener(new MyModifyListener());
		
//		setMessage("执行内容时机");
		
		setControl(mainContainer);
	}

	public WidgetRelationActionPanel1 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * 执行内容名输入框改变事件监听器，控制“下一步”和“完成”按钮
	 * 
	 * @author guoweic
	 *
	 */
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

	public void setTargetWidget(WidgetElementObj targetWidget) {
		this.targetWidget = targetWidget;
	}

}
