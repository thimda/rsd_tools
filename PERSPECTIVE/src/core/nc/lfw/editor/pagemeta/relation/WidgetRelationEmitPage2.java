package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.page.PlugoutEmitItem;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget连接器 触发时机构造向导 第二页
 * @author guoweic
 *
 */
public class WidgetRelationEmitPage2 extends WizardPage {
	
	private WidgetRelationEmitPanel2 mainContainer = null;
	
	protected WidgetRelationEmitPage2(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("触发时机构造向导");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationEmitPanel2(parent, SWT.NONE);
		
		// 增加触发时机类型选择事件
		mainContainer.getTypeCombo().addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = mainContainer.getTypeCombo().getText();
				String data = (String) mainContainer.getTypeCombo().getData(text);
				//TODO
				if (PlugoutEmitItem.TYPE_DS_ROW_SELECT.equals(data))
					setPageComplete(true);
				else
					setPageComplete(false);
			}
		});
		
//		setMessage("构建触发时机");
		
		setControl(mainContainer);
	}

	public WidgetRelationEmitPanel2 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * 重置“完成”按钮状态
	 */
	public boolean resetPageComplete() {
		if (!"".equals(mainContainer.getType()))
			return true;
		else
			return false;
	}

}
