package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget连接器 向导 最后一页
 * 
 * @author guoweic
 *
 */
public class WidgetRelationPage5 extends WizardPage {
	
	private Connector connector = null;
	
	private WidgetRelationPanel5 mainContainer = null;
	
	protected WidgetRelationPage5(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle(LFWPerspectiveNameConst.WIDGET_CN + " 连接向导");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationPanel5(parent, SWT.NONE, connector);
		
		String id = mainContainer.getIdText().getText().trim();
		if (!"".equals(id)) {
			setPageComplete(true);
		}
		
		// 增加参数名输入框事件监听
		mainContainer.getIdText().addModifyListener(new MyModifyListener());

		setMessage("填写连接器ID");
		
		setControl(mainContainer);
	}

	public WidgetRelationPanel5 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * ID输入框改变事件监听器，控制“下一步”和“完成”按钮
	 * 
	 * @author guoweic
	 *
	 */
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(false);
			String id = mainContainer.getIdText().getText().trim();
			if (!"".equals(id)) {
				setPageComplete(true);
			}
		}
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

}
