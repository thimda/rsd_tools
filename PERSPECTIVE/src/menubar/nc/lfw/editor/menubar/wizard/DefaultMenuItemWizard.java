package nc.lfw.editor.menubar.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.lfw.editor.common.LfwWizard;
import nc.lfw.editor.menubar.DefaultItem;
import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.editor.common.tools.LFWTool;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Text;

/**
 * MenubarĬ����������
 * 
 * @author guoweic
 *
 */
public class DefaultMenuItemWizard extends LfwWizard {

	/**
	 * ��ǰMenubar
	 */
	private MenubarComp menubar = null;
	
	/**
	 * ��ǰMenuItem
	 */
	private MenuItem menuItem = null;
	
	private String listenerClassInfoKey;
	
	/**
	 * ѡ�еĲ˵���
	 */
	private List<DefaultItem> selectedMenuItems = new ArrayList<DefaultItem>();
	
	private DefaultMenuItemPage1 page1;

	private DefaultMenuItemPage2 page2;

	private String packageName = "";
	private String sourceFolder = "";
	private String classPrefix = "";
	private Map<DefaultItem, MenuItemParamPanel> paramPanels = null;
	
	/**
	 * �����ҳ��
	 */
	public void addPages() {
		// ����ҳ���������ҳ������
		page1 = new DefaultMenuItemPage1("page1");
		page1.setMenubar(menubar);
		page1.setMenuItem(menuItem);

		page2 = new DefaultMenuItemPage2("page2");
		
		// ����ҳ��
		addPage(page1);
		addPage(page2);
	}
	
	/**
	 * �жϡ���ɡ���ť��ʱ��Ч������true��Ч��false��Ч
	 */
	public boolean canFinish() {
		if (this.getContainer().getCurrentPage() == page2) {  // ��ʼ���˵�������ҳ��
			page2.getMainContainer().initPanel(page1.getMainContainer().getSelectedMenuItems(), listenerClassInfoKey);
			return page2.checkCanFinished();
		}
		
		// ���óɣ�ֻ�е����һҳʱ����ɡ���ť��Ч
		if (LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion()) && this.getContainer().getCurrentPage() == page1){
			return true;
		}
		if (this.getContainer().getCurrentPage() != page2){
			return false;
		}
		return super.canFinish();
	}
	
	
	public boolean performFinish() {
		// У�������
		boolean checkResult = checkParamPanels();
		if (!checkResult) {
			MessageDialog.openInformation(null, "��ʾ", "�б�����δ����");
			return false;
		}
		
		packageName = page2.getMainContainer().getPackageText().getText().trim();
		sourceFolder = page2.getMainContainer().getSourceFolderCombo().getText();
		classPrefix = page2.getMainContainer().getClassPrefixText().getText().trim();
		paramPanels = page2.getMainContainer().getMenuItemParamPanels();
		for (DefaultItem item : paramPanels.keySet()) {
			MenuItemParamPanel paramPanel = paramPanels.get(item);
			Combo[] widgetComboArray = paramPanel.getWidgetCombo();
			for (Combo combo : widgetComboArray) {
				String sel = combo.getItem(combo.getSelectionIndex());
				if (!"".equals(sel))
					paramPanel.getWidgetIdMap().put((String)combo.getData(), sel);
			}
			Combo[] dsComboArray = paramPanel.getDsCombo();
			for (Combo combo : dsComboArray) {
				String sel = combo.getItem(combo.getSelectionIndex());
				if (!"".equals(sel))
					paramPanel.getDsIdMap().put((String)combo.getData(), sel);
			}
			Text[] multiDsTextArray = paramPanel.getMultiDsText();
			for (Text text : multiDsTextArray) {
				if (!"".equals(text.getText())) {
					String[] idArray = text.getText().split(",");
					String ids = "";
					for (String id : idArray) {
						if (!"".equals(id.trim()))
							ids += id + ",";
					}
					if (ids.length() > 0)
						ids = ids.substring(0, ids.length() - 1);
					paramPanel.getDsIdMap().put((String)text.getData(), ids);
				}
			}
			Text cardText = paramPanel.getCardText();
			if (null != cardText && !"".equals(cardText.getText().trim()))
				paramPanel.setCardId(cardText.getText().trim());
			Text tabText = paramPanel.getTabText();
			if (null != tabText && !"".equals(tabText.getText().trim()))
				paramPanel.setTabId(tabText.getText().trim());
			Scrollable[] otherCompArray = paramPanel.getOtherComp();
			for (Scrollable comp : otherCompArray) {
				if (comp instanceof Text && !"".equals(((Text)comp).getText())) {
					paramPanel.getOtherParamMap().put((String)((Text)comp).getData(), ((Text)comp).getText().trim());
				} else if (comp instanceof Combo) {
					String value = (String) ((Combo)comp).getData(((Combo)comp).getText());
					if (null != value)
						paramPanel.getOtherParamMap().put((String)((Combo)comp).getData(), value);
				}
			}
		}
		return true;
	}
	
	/**
	 * У��˵���������õı�����
	 * @return
	 */
	public boolean checkParamPanels() {
		Map<DefaultItem, MenuItemParamPanel> panels = page2.getMainContainer().getMenuItemParamPanels();
		for (DefaultItem item : panels.keySet()) {
			MenuItemParamPanel paramPanel = panels.get(item);
			Combo[] widgetComboArray = paramPanel.getWidgetCombo();
			for (Combo combo : widgetComboArray) {
				if (null != combo.getToolTipText()) {
					String sel = combo.getItem(combo.getSelectionIndex());
					if ("".equals(sel))
						return false;
				}
			}
			Combo[] dsComboArray = paramPanel.getDsCombo();
			for (Combo combo : dsComboArray) {
				if (null != combo.getToolTipText()) {
					String sel = combo.getItem(combo.getSelectionIndex());
					if ("".equals(sel))
						return false;
				}
			}
			Text[] multiDsTextArray = paramPanel.getMultiDsText();
			for (Text text : multiDsTextArray) {
				if (null != text.getToolTipText()) {
					String ids = text.getText().trim();
					if ("".equals(ids))
						return false;
				}
			}
			Text cardText = paramPanel.getCardText();
			if (null != cardText && null != cardText.getToolTipText()) {
				if ("".equals(cardText.getText().trim()))
					return false;
			}
			Text tabText = paramPanel.getTabText();
			if (null != tabText && null != tabText.getToolTipText()) {
				if ("".equals(tabText.getText().trim()))
					return false;
			}
			Scrollable[] otherCompArray = paramPanel.getOtherComp();
			for (Scrollable comp : otherCompArray) {
				String ids = "";
				if (comp instanceof Text && null != ((Text)comp).getToolTipText()) {
					ids = ((Text)comp).getText().trim();
					if (null == ids || "".equals(ids))
						return false;
				} else if (comp instanceof Combo && null != ((Combo)comp).getToolTipText()) {
					ids = (String) ((Combo)comp).getData(((Combo)comp).getText());
					if (null == ids || "".equals(ids))
						return false;
				}
			}
		}
		return true;
	}

	public void setMenubar(MenubarComp menubar) {
		this.menubar = menubar;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public List<DefaultItem> getSelectedMenuItems() {
		return selectedMenuItems;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public String getClassPrefix() {
		return classPrefix;
	}

	public Map<DefaultItem, MenuItemParamPanel> getMenuItemParamPanels() {
		return paramPanels;
	}

	public void setListenerClassInfoKey(String listenerClassInfoKey) {
		this.listenerClassInfoKey = listenerClassInfoKey;
	}

}
