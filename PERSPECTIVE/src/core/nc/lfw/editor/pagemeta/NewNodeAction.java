package nc.lfw.editor.pagemeta;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.event.conf.EventHandlerConf;
import nc.uap.lfw.core.event.conf.PageListener;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * �½����
 * @author guoweic
 *
 */
public class NewNodeAction extends Action {

	public static final String DEFAULT_PROCESSOR_CLASS_NAME = "nc.uap.lfw.core.processor.EventRequestProcessor";
	
	public NewNodeAction() {
		super(WEBProjConstants.NEW_PAGEMETA);
	}
	
	public void run() {
		LFWExplorerTreeView view = LFWExplorerTreeView
				.getLFWExploerTreeView(null);
		if (view == null)
			return;
		Shell shell = new Shell(Display.getCurrent());
		CreatePageMetaDialog pageMetaDlg = new CreatePageMetaDialog(shell, WEBProjConstants.NEW_PAGEMETA);
		
		//InputDialog input = new InputDialog(shell, "�½�" + LFWExplorerTreeView.PAGEMETA_CN, "����" + LFWExplorerTreeView.PAGEMETA_CN + "����", "", null);
		if (pageMetaDlg.open() == IDialogConstants.OK_ID) {
			String pmName = pageMetaDlg.getName();
			String pmId = pageMetaDlg.getId();
			if (pmName != null && pmName.trim().length() > 0 && pmId != null && pmId.length() > 0) {
				pmName = pmName.trim();
				pmId = pmId.trim();
				try {
					
					LFWPageMetaTreeItem treeItem = (LFWPageMetaTreeItem) view.addPageMetaTreeNode(pmId, pmName);
					treeItem.setType(LFWNodeTreeItem.NODE_FOLDER);

					// �½�Node��pagemeta.pm
					PageMeta pagemeta = new PageMeta();
					pagemeta.setId(pmId);
					pagemeta.setCaption(pmName);
					pagemeta.setProcessorClazz(DEFAULT_PROCESSOR_CLASS_NAME);
//					PageStates pageStates = new PageStates();
//					PageState pageStateCard = new PageState();
//					pageStateCard.setKey("1");
//					pageStateCard.setName("��Ƭ��ʾ");
//					PageState pageStateList = new PageState();
//					pageStateList.setKey("2");
//					pageStateList.setName("�б���ʾ");
//					pageStates.addPageState(pageStateCard);
//					pageStates.addPageState(pageStateList);
//					pagemeta.setPageStates(pageStates);
//					pagemeta.setPageState("1");
					
					// ����Ĭ���¼�
					PageListener listener = new PageListener();
					listener.setId(pmId + "_defaultListener");
					//���nc������һ��ҳ��ִ���¼�
					listener.setServerClazz("nc.uap.lfw.ncadapter.deft.NCDefaultPageServerListener");
					
					// ҳ��ر�ǰ�¼�
					EventHandlerConf onClosingEvent = PageListener.getOnClosingEvent();
					onClosingEvent.setAsync(false);
					onClosingEvent.setOnserver(false);
					StringBuffer script = new StringBuffer();
					script.append("pageUI.showCloseConfirm();\n");
					onClosingEvent.setScript(script.toString());
					listener.addEventHandler(onClosingEvent);
					
					// ҳ��ر��¼�
					EventHandlerConf onClosedEvent = PageListener.getOnClosedEvent();
					onClosedEvent.setAsync(false);
					onClosedEvent.setOnserver(true);
					listener.addEventHandler(onClosedEvent);
					
					pagemeta.addListener(listener);
					
					savePagemeta(pagemeta);
					
//					// ˢ��
//					if (RefreshNodeGroupAction.class.getName().equals(actionName))
//						RefreshNodeGroupAction.refreshNodes(tree, LFWPersTool.getCurrentProject());
//					else if (RefreshNodeAction.class.getName().equals(actionName))
//						RefreshNodeAction.refreshNode(view, tree);
					NCConnector.refreshNode();
				} catch (Exception e) {
					MainPlugin.getDefault().logError(e.getMessage(), e);
					String title = WEBProjConstants.NEW_PAGEMETA;
					String message = e.getMessage();
					MessageDialog.openError(shell, title, message);
				}
			}
		}
	}
	
	/**
	 * �½�Node��pagemeta.pm
	 * @param pagemeta
	 */
	private void savePagemeta(PageMeta pagemeta) {
		String folderPath = LFWPersTool.getCurrentFolderPath();
		String projectPath = LFWPersTool.getProjectPath();
		String filePath = folderPath + "/" + pagemeta.getId();
		// ����pagemeta.pm
		LFWConnector.savePagemetaToXml(filePath, "pagemeta.pm", projectPath, pagemeta);
	}

}
