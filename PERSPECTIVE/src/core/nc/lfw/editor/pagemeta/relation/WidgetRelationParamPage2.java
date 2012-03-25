package nc.lfw.editor.pagemeta.relation;

import nc.uap.lfw.core.page.PlugoutDescItem;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * Widget连接器 参数构造向导 第二页
 * 
 * @author guoweic
 *
 */
public class WidgetRelationParamPage2 extends WizardPage {
	
	private WidgetRelationParamPanel2 mainContainer = null;
	
	protected WidgetRelationParamPage2(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		
		setTitle("参数构造向导");
		
		setPageComplete(false);
		
		mainContainer = new WidgetRelationParamPanel2(parent, SWT.NONE);
		
		// 增加参数名输入框事件监听
		mainContainer.getFormularText().addModifyListener(new MyModifyListener());
		
		// 增加参数类型选择事件
		mainContainer.getTypeCombo().addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				String text = mainContainer.getTypeCombo().getText();
				String data = (String) mainContainer.getTypeCombo().getData(text);
				if (PlugoutDescItem.TYPE_DS_FIELD.equals(data))
					setPageComplete(true);
				else if (PlugoutDescItem.TYPE_FOMULAR.equals(mainContainer.getType())) {  // 公式类型
					String formular = mainContainer.getFormularText().getText().trim();
					if (!"".equals(formular)) {
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
				else
					setPageComplete(false);
			}
		});
		
//		setMessage("构建参数");
		
		setControl(mainContainer);
	}

	public WidgetRelationParamPanel2 getMainContainer() {
		return mainContainer;
	}
	
	/**
	 * 公式输入框改变事件监听器，控制“完成”按钮
	 * 
	 * @author guoweic
	 *
	 */
	private class MyModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			setPageComplete(false);
			String formular = mainContainer.getFormularText().getText().trim();
			// 公式类型
			if (PlugoutDescItem.TYPE_FOMULAR.equals(mainContainer.getType()) && !"".equals(formular)) {
				mainContainer.setValue(formular);
				setPageComplete(true);
			}
		}
	}

}
