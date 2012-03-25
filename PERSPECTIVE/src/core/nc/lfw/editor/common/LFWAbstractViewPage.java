package nc.lfw.editor.common;

import nc.lfw.editor.common.extend.ExtendAttributesView;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.JsListenerConf;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.editor.propertiesview.EventPropertiesView;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.listener.ListenerPropertiesView;
import nc.uap.lfw.perspective.views.ILFWViewPage;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.Page;

/**
 * ViewPage����
 * @author guoweic
 *
 */
public abstract class LFWAbstractViewPage extends Page implements ILFWViewPage {

	private Composite comp = null;
	private StackLayout sl = new StackLayout();
	private TabFolder voTabFolder = null;
	private TabItem cellPropTabItem = null;
	private Composite cellPropertiesView = null;
	public Composite getComp() {
		return comp;
	}

	public void setComp(Composite comp) {
		this.comp = comp;
	}

	public TabFolder getVoTabFolder() {
		return voTabFolder;
	}

	/**
	 * ���õ�ǰģ����ͼ���ֶ���
	 * @param voTabFolder
	 */
	public void setVoTabFolder(TabFolder voTabFolder) {
		this.voTabFolder = voTabFolder;
	}
	
	public Composite getCellPropertiesView() {
		return cellPropertiesView;
	}

	public void setCellPropertiesView(Composite cellPropertiesView) {
		this.cellPropertiesView = cellPropertiesView;
	}

	public StackLayout getSl() {
		return sl;
	}

	public void setSl(StackLayout sl) {
		this.sl = sl;
	}

	public TabItem getCellPropTabItem() {
		return cellPropTabItem;
	}

	public void setCellPropTabItem(TabItem cellPropTabItem) {
		this.cellPropTabItem = cellPropTabItem;
	}
	
	/**
	 * ���TabItem����
	 * @author guoweic
	 * @param item
	 */
	protected void clearItemControl(TabItem item) {
		if (item != null)
			item.setControl(null);
	}

	
	public Control getControl() {
		return this.comp;
	}

	
	public void setFocus() {
		getControl().setFocus();
	}
	
	/**
	 * ���ӿؼ�����ҳ����
	 * @author guoweic
	 */
	protected void addCellPropItemControl() {
		Control control = null;
		try { 
			control = cellPropTabItem.getControl();
		} catch (Exception e) {
			return;
		} finally {
			if (control == null) {
				if(cellPropertiesView != null)
					cellPropTabItem.setControl(cellPropertiesView);
			}
			voTabFolder.setSelection(cellPropTabItem);
		}
	}
	
	/**
	 * @author guoweic
	 */
	private TabItem jsListenerItem = null;
	
	private ListenerPropertiesView listenerPropertiesView = null;

	public ListenerPropertiesView getListenerPropertiesView() {
		return listenerPropertiesView;
	}
	
	/**
	 * �����¼�����ҳ
	 * @author guoweic
	 */
	protected void addJsListenerItem(JsListenerConf jsListener) {
		if (jsListenerItem == null) {
			jsListenerItem = new TabItem(voTabFolder, SWT.NONE);
			jsListenerItem.setText("�¼�");
		}
		listenerPropertiesView = new ListenerPropertiesView(voTabFolder, SWT.NONE, jsListener);
		jsListenerItem.setControl(listenerPropertiesView);
			voTabFolder.setSelection(jsListenerItem);
	}
	
	/**
	 * �����¼�����ҳ
	 */
	public void addListenerPropertiesView(JsListenerConf jsListener) {
		Composite comp = getComp();
		getSl().topControl = getVoTabFolder();
		// �����¼�����ҳ
		addJsListenerItem(jsListener);
		// ��տؼ�����ҳ����
//		clearItemControl(cellPropTabItem);
		// ��listener���Ա��ҳ���У���¼��ǰ�༭��ҳ��
		getListenerPropertiesView().setEditor((LFWBaseEditor) LFWBaseEditor.getActiveEditor());
		if (comp != null) {
			comp.layout();
			if (!comp.isVisible())
				comp.setVisible(true);
		}
	}
	
	private EventPropertiesView eventPropertiesView = null;
	
	private WebElement webElement = null;
	
	private UIElement uiElement = null;
	
	private UIMeta uimeta = null;
	/**
	 * v61�������¼�����ҳ
	 * @param eventConfs
	 * @param controllerClazz
	 */
	public void addEventPropertiesView(EventConf[] eventConfs, String controllerClazz){
		if(LFWTool.NEW_VERSION.equals(LFWTool.getCurrentLFWProjectVersion())){
			Composite comp = getComp();
			getSl().topControl = getVoTabFolder();
			// �����¼�����ҳ
			TabItem jsListenerItem = getJsListenerItem();
			if (jsListenerItem == null) {
				jsListenerItem = new TabItem(getVoTabFolder(), SWT.NONE);
				jsListenerItem.setText(WEBProjConstants.EVENT);
				setJsListenerItem(jsListenerItem);
			}
			eventPropertiesView = new EventPropertiesView(getVoTabFolder(), SWT.NONE, eventConfs, controllerClazz, webElement, uiElement, uimeta);
			eventPropertiesView.setEditor(LFWBaseEditor.getActiveEditor());
			jsListenerItem.setControl(eventPropertiesView);
			getVoTabFolder().setSelection(jsListenerItem);
			// ��տؼ�����ҳ����
//			clearItemControl(cellPropTabItem);
			// ��listener���Ա��ҳ���У���¼��ǰ�༭��ҳ��
			if (comp != null) {
				comp.layout();
				if (!comp.isVisible()){
					comp.setVisible(true);
				}
			}
		}
	}
	
	public WebElement getWebElement() {
		return webElement;
	}

	public void setWebElement(WebElement webElement) {
		this.webElement = webElement;
	}

	public UIElement getUiElement() {
		return uiElement;
	}

	public void setUiElement(UIElement uiElement) {
		this.uiElement = uiElement;
	}

	public UIMeta getUimeta() {
		return uimeta;
	}

	public void setUimeta(UIMeta uimeta) {
		this.uimeta = uimeta;
	}

	/**
	 * @author guoweic
	 */
	private TabItem extendAttributesItem = null;
	
	private ExtendAttributesView extendAttributesView = null;
	
	/**
	 * ������չ��������ҳ
	 * @author guoweic
	 */
	protected void addExtendAttributesItem(WebElement webElement) {
		if (extendAttributesItem == null) {
			extendAttributesItem = new TabItem(voTabFolder, SWT.NONE);
			extendAttributesItem.setText("��չ����");
		}
		extendAttributesView = new ExtendAttributesView(voTabFolder, SWT.NONE, webElement);
		extendAttributesItem.setControl(extendAttributesView);
		voTabFolder.setSelection(extendAttributesItem);
	}
	
	/**
	 * ������չ��������ҳ
	 */
	public void addExtendAttributesView(WebElement webElement) {
		Composite comp = getComp();
		getSl().topControl = getVoTabFolder();
		// ������չ��������ҳ
		addExtendAttributesItem(webElement);
		// ��տؼ�����ҳ����
//		clearItemControl(cellPropTabItem);
		// ����¼�ҳ����
		clearItemControl(jsListenerItem);
		// ����չ���Ա��ҳ���У���¼��ǰ�༭��ҳ��
		getExtendAttributesView().setEditor((LFWBaseEditor) LFWBaseEditor.getActiveEditor());
		if (comp != null) {
			comp.layout();
			if (!comp.isVisible())
				comp.setVisible(true);
		}
	}
	
	public abstract void createControl(Composite parent);

	public abstract void selectionChanged(IWorkbenchPart part, ISelection selection);

	public TabItem getJsListenerItem() {
		return jsListenerItem;
	}
	
	public void setJsListenerItem(TabItem jsListenerItem){
		this.jsListenerItem = jsListenerItem;
	}

	public ExtendAttributesView getExtendAttributesView() {
		return extendAttributesView;
	}

	public EventPropertiesView getEventPropertiesView() {
		return eventPropertiesView;
	}

	public void setEventPropertiesView(EventPropertiesView eventPropertiesView) {
		this.eventPropertiesView = eventPropertiesView;
	}

}
