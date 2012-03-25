package nc.uap.portal.skin.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.portal.core.PortalConnector;
import nc.uap.portal.om.Skin;
import nc.uap.portal.om.SkinDescription;
import nc.uap.portal.om.Theme;
import nc.uap.portal.perspective.PortalProjConstants;
import nc.uap.portal.perspective.PortalExplorerTreeView;
import nc.uap.portal.skin.dialog.SkinDialog;
import nc.uap.portal.theme.ThemeTypeTreeItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Create a new skin
 * 
 * @author dingrf
 */
public class NewSkinAction extends Action {
	
	public NewSkinAction() {
		super(PortalProjConstants.NEW_SKIN, PaletteImage.getCreateGridImgDescriptor());
	}

	public void run() {
		PortalExplorerTreeView view = PortalExplorerTreeView.getPortalExploerTreeView(null);
		if(view == null)
			return;

		Tree tree = view.getTreeView().getTree();
		TreeItem[] selTIs = tree.getSelection();
		TreeItem selTI = selTIs[0];
		if (!(selTI instanceof ThemeTypeTreeItem)){
			MessageDialog.openError(new Shell(), "������ʾ", "����������������½���ʽ!");
			return;
		}
		
		SkinDialog skinDialog = new SkinDialog(new Shell(), PortalProjConstants.NEW_SKIN);
		if(skinDialog.open() == IDialogConstants.OK_ID){
			
			TreeItem[] subItems= selTI.getItems();
			for (int i=0;i<subItems.length;i++){
				if(subItems[i].getData() instanceof String){
					if (subItems[i].getData().equals(skinDialog.getId())){
						MessageDialog.openError(new Shell(), "������ʾ", "�Ѿ�����IDΪ"+ skinDialog.getId() +"����ʽ!");
						return;
					}
				}
			}
			try {
				Skin skin = new Skin();
				skin.setId(skinDialog.getId());
				skin.setName(skinDialog.getName());
				
				view.addSkinTreeNode(skin);

				String type = ((ThemeTypeTreeItem) selTI).getType();
				String themeId = ((Theme) selTI.getData()).getId();
				
				String projectPath = LFWPersTool.getProjectWithBcpPath();
				String projectModuleName = LFWPersTool.getCurrentProjectModuleName();
				
				SkinDescription skinDescription  = PortalConnector.getSkinDescription(projectPath, projectModuleName, type, themeId);
				if (skinDescription == null){
					skinDescription = new SkinDescription();
				}
				skinDescription.getSkin().add(skin);
				PortalConnector.saveSkinDescription(projectPath, projectModuleName, type, themeId, skinDescription);

				/*����skin�ļ�*/
				if (type.equals(PortalProjConstants.TYPE_LAYOUT)){
					String layoutClassPath = "nc/uap/portal/skin/template/layout/template.ftl";
					String fileName = skin.getId() + ".ftl";
					InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(layoutClassPath);
					String layoutfileText= getFileText(ins);
					PortalConnector.createSkinFile(projectPath, projectModuleName, type, themeId, fileName, layoutfileText);
				}
				else if (type.equals(PortalProjConstants.TYPE_PAGE)){
					String layoutClassPath = "nc/uap/portal/skin/template/page/template.ftl";
					String fileName = skin.getId() + ".ftl";
					InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(layoutClassPath);
					String pagefileText= getFileText(ins);
					PortalConnector.createSkinFile(projectPath, projectModuleName, type, themeId, fileName, pagefileText);
				}
				else if (type.equals(PortalProjConstants.TYPE_PORTLET)){
					String layoutClassPath = "nc/uap/portal/skin/template/portlet/template.ftl";
					String fileName = skin.getId() + ".ftl";
					InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(layoutClassPath);
					String portletfileText= getFileText(ins);
					PortalConnector.createSkinFile(projectPath, projectModuleName, type, themeId, fileName, portletfileText);
					
					layoutClassPath = "nc/uap/portal/skin/template/portlet/template.css";
					fileName = skin.getId() + ".css";
					ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(layoutClassPath);
					portletfileText= getFileText(ins);
					PortalConnector.createSkinFile(projectPath, projectModuleName, type, themeId, fileName, portletfileText);
					
					layoutClassPath = "nc/uap/portal/skin/template/portlet/template.js";
					fileName = skin.getId() + ".js";
					ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(layoutClassPath);
					portletfileText= getFileText(ins);
					PortalConnector.createSkinFile(projectPath, projectModuleName, type, themeId, fileName, portletfileText);
				}
			} catch (Exception e) {
				String title =PortalProjConstants.NEW_SKIN;
				String message = e.getMessage();
				MessageDialog.openError(new Shell(), title, message);
			}
		}
		else
			return;
	}

	/**
	 * InputStream ת���� String
	 * 
	 * @param io
	 * @return
	 */
	private String getFileText(InputStream io) {
		String text = null;
		if (io == null)
			return null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(io,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (String line = br.readLine(); line != null; line = br
					.readLine())
				sb.append(line).append("\r\n");

			text = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
}
