package nc.uap.lfw.button;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nc.lfw.editor.common.DialogWithTitle;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class HotKeySetDialog extends DialogWithTitle{

	private Combo modifierCombo;
	private String result;
	
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public HotKeySetDialog(Shell parentShell, String title) {
		super(parentShell, title);
		initHotKeyModifierMap();
	}
	
	
	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}
	
	private Text hotKeyText;
	
	private void initHotKeyModifierMap() {
		if (hotKeyModifierMap == null) {
			hotKeyModifierMap = new HashMap<String, String>();
			hotKeyModifierMap.put("1", "SHIFT");
			hotKeyModifierMap.put("2", "CTRL");
			hotKeyModifierMap.put("8", "ALT");
			hotKeyModifierMap.put("3", "CTRL+SHIFT");
			hotKeyModifierMap.put("10", "CTRL+ALT");
			hotKeyModifierMap.put("9", "ALT+SHIFT");
			hotKeyModifierMap.put("11", "CTRL+SHIFT+ALT");
		}
	}
	
	
	protected void okPressed() {
		String preHotKey = (String) modifierCombo.getText();
		String hotKey = hotKeyText.getText();
		if(hotKey == null || hotKey.equals("")){
			MessageDialog.openError(null, "提示", "请输入快捷键!");
			return;
		}
		result = preHotKey + "+" +  hotKey;
		super.okPressed();
	}
	
	

	// 热键前缀映射
	private Map<String, String> hotKeyModifierMap = null;
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent , SWT.NONE);
		container.setLayout(new GridLayout(2,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label prehotkeyLabel = new Label(container, SWT.NONE);
		prehotkeyLabel.setText("热键前缀:");
		prehotkeyLabel.setLayoutData(new GridData(60,20));
		//prehotkeyLabel.setLayoutData(createGridData(120, 1));
		modifierCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		modifierCombo.setLayoutData(new GridData(120,20));
		
		Label hotkeyLabel = new Label(container, SWT.NONE);
		hotkeyLabel.setText("热键:");
		hotkeyLabel.setLayoutData(new GridData(60,20));
		//hotkeyLabel.setLayoutData(createGridData(120, 1));
		hotKeyText = new Text(container, SWT.BORDER);
		hotKeyText.setLayoutData(new GridData(140,20));
		//hotKeyText.setLayoutData(createGridData(120, 1));
		
		Set<String> values = hotKeyModifierMap.keySet();
		Iterator<String> it = values.iterator();
		int index = 0;
		while (it.hasNext()) {
			String value = it.next();
			String text = hotKeyModifierMap.get(value);
			modifierCombo.add(text);
			modifierCombo.setData(text, value);
			if (index == 0)
				modifierCombo.select(0);
		}
		return container;
	}

}
