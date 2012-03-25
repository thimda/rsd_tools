package nc.lfw.editor.menubar.dialog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

/**
 * �༭�˵���Ի�������
 * 
 * @author guoweic
 * 
 */
public class MenuItemPanel extends Canvas {

	private MenuItem item;

	private Text idText;

	private Text textText;
	// private Text operateVisibleStatusArrayText;
	//	
	// private Text businessVisibleStatusArrayText;

	// private Text operateStatusArrayText;

	// private Text businessStatusArrayText;

	// public Text getOperateVisibleStatusArrayText() {
	// return operateVisibleStatusArrayText;
	// }
	//
	// public void setOperateVisibleStatusArrayText(Text
	// operateVisibleStatusArrayText) {
	// this.operateVisibleStatusArrayText = operateVisibleStatusArrayText;
	// }
	//
	// public Text getBusinessVisibleStatusArrayText() {
	// return businessVisibleStatusArrayText;
	// }
	//
	// public void setBusinessVisibleStatusArrayText(
	// Text businessVisibleStatusArrayText) {
	// this.businessVisibleStatusArrayText = businessVisibleStatusArrayText;
	// }

	private Text i18nNameText;

	private Text directoryText;

	private Text imgIconText;
	private Text imgIconOnText;
	private Text imgIconDisabledText;

	private Combo modifierCombo;
	private Text hotKeyText;

	/** �ָ��� */
	private Combo sepCombo;

	/** ״̬���� */
	private Combo stateCombo;

	// �ȼ�ǰ׺ӳ��
	private Map<String, String> hotKeyModifierMap = null;

	public MenuItemPanel(Composite parent, int style, MenuItem item) {
		super(parent, style);
		this.item = item;
		initHotKeyModifierMap();
		initUI();
	}

	/**
	 * ��ʼ���ȼ�ǰ׺ӳ��
	 */
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

	private void initUI() {
		this.setLayout(new GridLayout(4, false));
		this.setLayoutData(new GridData(GridData.FILL_BOTH));

		new Label(this, SWT.NONE).setText("Id:");
		idText = new Text(this, SWT.BORDER);
		idText.setLayoutData(createGridData(120, 1));
		idText.setText(item.getId());

		new Label(this, SWT.NONE).setText("��ʾ��:");
		textText = new Text(this, SWT.BORDER);
		textText.setLayoutData(createGridData(120, 1));
		textText.setText(item.getText() == null ? "" : item.getText());

		// new Label(this, SWT.NONE).setText("���ܵ���״̬:");
		// operateStatusArrayText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		// operateStatusArrayText.setBackground(new Color(null, 255, 255, 255));
		// operateStatusArrayText.setLayoutData(createGridData(120, 1));
		// operateStatusArrayText.setText(item.getOperatorStatusArray() == null
		// ? "" : item.getOperatorStatusArray());
		//
		// new Label(this, SWT.NONE).setText("����ҵ��״̬:");
		// businessStatusArrayText = new Text(this, SWT.BORDER);
		// businessStatusArrayText.setLayoutData(createGridData(120, 1));
		// businessStatusArrayText.setText(item.getBusinessStatusArray() == null
		// ? "" : item.getBusinessStatusArray());
		//		
		// //�Ƿ�ɼ�����״̬
		// new Label(this, SWT.NONE).setText("�ɼ�����״̬:");
		// operateVisibleStatusArrayText = new Text(this, SWT.BORDER |
		// SWT.READ_ONLY);
		// operateVisibleStatusArrayText.setBackground(new Color(null, 255, 255,
		// 255));
		// operateVisibleStatusArrayText.setLayoutData(createGridData(120, 1));
		// operateVisibleStatusArrayText.setText(item.getOperatorVisibleStatusArray()
		// == null ? "" : item.getOperatorVisibleStatusArray());
		//
		// //�Ƿ�ɼ�ҵ��״̬
		// new Label(this, SWT.NONE).setText("�ɼ�ҵ��״̬:");
		// businessVisibleStatusArrayText = new Text(this, SWT.BORDER);
		// businessVisibleStatusArrayText.setLayoutData(createGridData(120, 1));
		// businessVisibleStatusArrayText.setText(item.getBusinessVisibleStatusArray()
		// == null ? "" : item.getBusinessVisibleStatusArray());

		new Label(this, SWT.NONE).setText("������Դ:");
		i18nNameText = new Text(this, SWT.BORDER);
		i18nNameText.setLayoutData(createGridData(120, 1));
		i18nNameText.setText(item.getI18nName() == null ? "" : item
				.getI18nName());

		new Label(this, SWT.NONE).setText("��ԴĿ¼:");
		directoryText = new Text(this, SWT.BORDER);
		directoryText.setLayoutData(createGridData(120, 1));
		directoryText.setEnabled(false);

		new Label(this, SWT.NONE).setText("ͼƬ·��:");
		imgIconText = new Text(this, SWT.BORDER);
		imgIconText.setLayoutData(createGridData(355, 3));
		imgIconText.setText(item.getImgIcon() == null ? "" : item.getImgIcon());

		new Label(this, SWT.NONE).setText("����ʱͼƬ·��:");
		imgIconOnText = new Text(this, SWT.BORDER);
		imgIconOnText.setLayoutData(createGridData(355, 3));
		imgIconOnText.setText(item.getImgIconOn() == null ? "" : item
				.getImgIconOn());

		new Label(this, SWT.NONE).setText("����ʱͼƬ·��:");
		imgIconDisabledText = new Text(this, SWT.BORDER);
		imgIconDisabledText.setLayoutData(createGridData(355, 3));
		imgIconDisabledText.setText(item.getImgIconDisable() == null ? ""
				: item.getImgIconDisable());

		new Label(this, SWT.NONE).setText("״̬������:");
		stateCombo = new Combo(this, SWT.BORDER | SWT.DROP_DOWN);
		stateCombo.setLayoutData(createGridData(355, 3));
		// sepCombo.add("",0);
		stateCombo.add("�༭̬�����ж���", 0);
		stateCombo.add("��ʼ̬,��ѡ,��ѡʱ���õ�״̬����", 1);
		stateCombo.add("����ʼ̬,��ѡ̬�����ж���", 2);
		stateCombo.add("����ʼ̬�����ж���", 3);
		stateCombo.add("����ѡ̬,��ѡ̬�����ж���", 4);
		stateCombo.add("����ѡ̬�����ж���", 5);
		if (item.getStateManager().equals(
				"nc.uap.lfw.core.bm.dft.Edit_StateManager"))
			stateCombo.select(0);
		else if (item.getStateManager().equals(
				"nc.uap.lfw.core.bm.dft.Init_Ss_Ms_StateManager"))
			stateCombo.select(1);
		else if (item.getStateManager().equals(
				"nc.uap.lfw.core.bm.dft.Init_Ss_StateManager"))
			stateCombo.select(2);
		else if (item.getStateManager().equals(
				"nc.uap.lfw.core.bm.dft.Init_StateManager"))
			stateCombo.select(3);
		else if (item.getStateManager().equals(
				"nc.uap.lfw.core.bm.dft.Ss_Ms_StateManager"))
			stateCombo.select(4);
		else if (item.getStateManager().equals(
				"nc.uap.lfw.core.bm.dft.Ss_StateManager"))
			stateCombo.select(5);
		else
			stateCombo.setText(item.getStateManager());

		new Label(this, SWT.NONE).setText("�ȼ�ǰ׺:");
		modifierCombo = new Combo(this, SWT.BORDER | SWT.READ_ONLY);
		modifierCombo.setLayoutData(createGridData(120, 1));

		new Label(this, SWT.NONE).setText("�ȼ�:");
		hotKeyText = new Text(this, SWT.BORDER);
		hotKeyText.setLayoutData(createGridData(120, 1));

		new Label(this, SWT.NONE).setText("�ָ���:");
		sepCombo = new Combo(this, SWT.BORDER | SWT.READ_ONLY);
		sepCombo.setLayoutData(createGridData(120, 1));
		sepCombo.add("��", 0);
		sepCombo.add("��", 1);
		sepCombo.select(item.isSep() ? 1 : 0);

		// // sepCombo.select(0);

		if (item.getHotKey() != null)
			hotKeyText.setText(item.getHotKey());

		Set<String> values = hotKeyModifierMap.keySet();
		Iterator<String> it = values.iterator();
		String hotKey = item.getHotKey();
		int index = 0;
		while (it.hasNext()) {
			String value = it.next();
			String text = hotKeyModifierMap.get(value);
			modifierCombo.add(text);
			modifierCombo.setData(text, value);
			if (index == 0)
				modifierCombo.select(0);
			if (hotKey != null && hotKey.indexOf(value) != -1
					&& value.length() != hotKey.length()) {
				int valueIndex = hotKey.lastIndexOf(value);
				if ((valueIndex + value.length()) == hotKey.length()) {
					modifierCombo.select(index);
					hotKeyText.setText(hotKey.substring(0, valueIndex));
				}
			}
			index++;
		}

		// operateStatusArrayText.addMouseListener(new
		// OperateStatusMouseListener());
		//		
		// operateVisibleStatusArrayText.addMouseListener(new
		// OperateVisibleStatusMouseListener());

	}

	// /**
	// * �Ƿ�ɼ�����״̬
	// * @author zhangxya
	// *
	// */
	// private class OperateVisibleStatusMouseListener implements
	// org.eclipse.swt.events.MouseListener {
	//		
	//		
	// public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
	//			
	// }
	//		
	// public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
	// Text text = (Text) e.getSource();
	// OperateStatusSelDialog dialog = new OperateStatusSelDialog(new Shell(),
	// "����״̬ѡ��");
	// dialog.setStates(operateVisibleStatusArrayText.getText().trim());
	// if (dialog.open() == Dialog.OK) {
	// List<String> selectedStates = dialog.getSelectedStates();
	// String states = "";
	// for (String id : selectedStates) {
	// states += id + ",";
	// }
	// if (states.length() > 0)
	// states = states.substring(0, states.length() - 1);
	// text.setText(states);
	// text.setToolTipText(states);
	// }
	// }
	//		
	// public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
	//			
	// }
	// }

	// /**
	// * ���ܵ���״̬ ����򵥻��¼�����ѡ��Ի���
	// *
	// * @author guoweic
	// *
	// */
	// private class OperateStatusMouseListener implements
	// org.eclipse.swt.events.MouseListener {
	//		
	//		
	// public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
	//			
	// }
	//		
	// public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
	// Text text = (Text) e.getSource();
	// OperateStatusSelDialog dialog = new OperateStatusSelDialog(new Shell(),
	// "����״̬ѡ��");
	// dialog.setStates(operateStatusArrayText.getText().trim());
	// if (dialog.open() == Dialog.OK) {
	// List<String> selectedStates = dialog.getSelectedStates();
	// String states = "";
	// for (String id : selectedStates) {
	// states += id + ",";
	// }
	// if (states.length() > 0)
	// states = states.substring(0, states.length() - 1);
	// text.setText(states);
	// text.setToolTipText(states);
	// }
	// }
	//		
	// public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
	//			
	// }
	// }

	private GridData createGridData(int width, int horizontalSpan) {
		GridData gridData = new GridData(width, 15);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	public Text getIdText() {
		return idText;
	}

	public Text getTextText() {
		return textText;
	}

	// public Text getOperateStatusArrayText() {
	// return operateStatusArrayText;
	// }
	//
	// public Text getBusinessStatusArrayText() {
	// return businessStatusArrayText;
	// }

	public Text getI18nNameText() {
		return i18nNameText;
	}

	public Text getDirectoryText() {
		return directoryText;
	}

	public Text getImgIconText() {
		return imgIconText;
	}

	public Text getImgIconOnText() {
		return imgIconOnText;
	}

	public Text getImgIconDisabledText() {
		return imgIconDisabledText;
	}

	public String getHotKey() {
		String hotKey = hotKeyText.getText().trim();
		// String modifier = (String)
		// modifierCombo.getData(modifierCombo.getItem(modifierCombo.getSelectionIndex()));
		// if (modifier != null)
		// hotKey += modifier;
		return hotKey;
	}

	public int getModifier() {
		String modifier = (String) modifierCombo.getData(modifierCombo
				.getItem(modifierCombo.getSelectionIndex()));
		return Integer.valueOf(modifier);
	}

	public String getDisplayHotKey() {
		String hotKey = hotKeyText.getText().trim();
		if (hotKey.equals(""))
			return null;
		String modifier = (String) modifierCombo.getData(modifierCombo
				.getItem(modifierCombo.getSelectionIndex()));
		String displayHotKey = hotKey;
		if (hotKeyModifierMap.containsKey(modifier))
			displayHotKey = hotKeyModifierMap.get(modifier) + "+" + hotKey;
		return displayHotKey;
	}

	public boolean getSep() {
		return sepCombo.getSelectionIndex() == 0 ? false : true;
	}

	public Combo getSepCombo() {
		return sepCombo;
	}

	public Combo getStateCombo() {
		return stateCombo;
	}

	public void setStateCombo(Combo stateCombo) {
		this.stateCombo = stateCombo;
	}

	public String getState() {
		switch (stateCombo.getSelectionIndex()) {
		case 0:
			return "nc.uap.lfw.core.bm.dft.Edit_StateManager";
		case 1:
			return "nc.uap.lfw.core.bm.dft.Init_Ss_Ms_StateManager";
		case 2:
			return "nc.uap.lfw.core.bm.dft.Init_Ss_StateManager";
		case 3:
			return "nc.uap.lfw.core.bm.dft.Init_StateManager";
		case 4:
			return "nc.uap.lfw.core.bm.dft.Ss_Ms_StateManager";
		case 5:
			return "nc.uap.lfw.core.bm.dft.Ss_StateManager";

		default:
			return stateCombo.getText();
		}
	}

}
