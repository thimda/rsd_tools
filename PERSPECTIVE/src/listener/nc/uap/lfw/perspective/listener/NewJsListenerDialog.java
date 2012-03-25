package nc.uap.lfw.perspective.listener;

import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.DialogWithTitle;
import nc.uap.lfw.core.event.conf.JsListenerConf;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 新建JsListener对话框
 * @author guoweic
 *
 */
public class NewJsListenerDialog extends DialogWithTitle {

	private Map<String, JsListenerConf> listenerMap;
	
	private Text idText;
	private Combo classCombo;
	
	private String id = "";
//	// 目标对象类型
//	private String targetType = "";
	private String listenerClass = "";
	// 可接受的JsListener类型
	private List<Class<? extends JsListenerConf>> acceptListeners;
	
	private final String SELECT_SENTENCE = "请选择";
	
	public NewJsListenerDialog(Shell parentShell, String title) {
		super(parentShell, title);
	}

	protected void okPressed() {
		id = idText.getText().trim();
		if ("".equals(id)) {
			MessageDialog.openInformation(this.getShell(), "提示", "请输入Listener的ID");
			idText.setFocus();
			return;
		} else if (listenerMap.containsKey(id)) {
			MessageDialog.openInformation(this.getShell(), "提示", "ID重复，请输入其它ID");
			idText.setFocus();
			return;
		} else if ("".equals(classCombo.getText()) || SELECT_SENTENCE.equals(classCombo.getText())) {
			MessageDialog.openInformation(this.getShell(), "提示", "请选择Listener类型");
			classCombo.setFocus();
			return;
		}
		listenerClass = (String) classCombo.getData(classCombo.getText());
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(450, 150);
	}

	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(container, SWT.NONE).setText("ID:");
		idText = new Text(container, SWT.BORDER);
		idText.setLayoutData(createGridData(150, 1));

		new Label(container, SWT.NONE).setText("类型:");
		classCombo = new Combo(container, SWT.READ_ONLY);
		classCombo.setLayoutData(createGridData(135, 1));
		classCombo.add(SELECT_SENTENCE);
		
//		String[] classArray = JsListenerConstant.getJsListenerClassMap().get(targetType);
//		for (int i = 0, n = classArray.length; i < n; i++) {
//			String name = classArray[i].substring(classArray[i].lastIndexOf(".") + 1);
//			classCombo.add(name);
//			classCombo.setData(name, classArray[i]);
//		}
		
		for (int i = 0, n = acceptListeners.size(); i < n; i++) {
			String listenerClassName = acceptListeners.get(i).getName();
			String name = listenerClassName.substring(listenerClassName.lastIndexOf(".") + 1);
			classCombo.add(name);
			classCombo.setData(name, listenerClassName);
		}
		
		classCombo.select(0);
		
		return container;
	}
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public String getId() {
		return id;
	}

	public String getListenerClass() {
		return listenerClass;
	}

//	public void setTargetType(String targetType) {
//		this.targetType = targetType;
//	}

	public void setListenerMap(Map<String, JsListenerConf> listenerMap) {
		this.listenerMap = listenerMap;
	}

	public void setAcceptListeners(List<Class<? extends JsListenerConf>> acceptListeners) {
		this.acceptListeners = acceptListeners;
	}

}
