package nc.uap.lfw.perspective.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.page.PageMeta;
import nc.uap.lfw.funnode.FuncRegisterDialog;
import nc.uap.lfw.funnode.NCUserRelatedDialog;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 发布到NC
 * @author zhangxya
 *
 */
public class PublishNCAction extends Action {

	private String nodeid;

	private String userMessage = null;
	
	public PublishNCAction() {
		super(WEBProjConstants.PUBLISH_NC);
	}
	
	protected boolean setAccountToProperty(){
 		IProject project = LFWPersTool.getCurrentProject();
 		IFile jfile = project.getFile(".module_prj");
 		File file = new File(jfile.getLocation().toString());
 		BufferedReader reader = null;
 		StringBuffer sb = new StringBuffer();
 		String tempString = null;
 		try{
 			reader = new BufferedReader(new FileReader(file));
 			int line = 1;
 			while ((tempString = reader.readLine()) != null){
 				sb.append(tempString + "\n");
 				line++;
 			}
 			reader.close();
 			String content = sb.toString();
 			if(content.indexOf("account") == -1 || content.indexOf("username") == -1 || content.indexOf("password") == -1){
	 			boolean flag = MessageDialog.openConfirm(null, "提示", "未设置关联帐套、用户名或密码,现在要设置吗?");
	 			if(flag){
	 				NCUserRelatedDialog dialog = new NCUserRelatedDialog(null);
	 				dialog.open();
				}
			}else{
				String accountOpn = content.substring(content.indexOf("account=") + 8, content.indexOf("username")).trim();
				String userName = content.substring(content.indexOf("username=") + 9, content.indexOf("password=")).trim();
				String passWord = content.substring(content.indexOf("password=") + 9);
				userMessage = accountOpn  + ":" + userName + ":" + passWord;
			}
 		}catch(Exception e){
 			//.logException(e, "修改.module_prj出错：", e.getMessage());
 			return false;
 		}
 		return true;
 	}
	
	public void run() {
		boolean flag = setAccountToProperty();
		if(flag){
			PageMeta pm = LFWPersTool.getCurrentPageMeta();
			if(pm == null)
				return;
			String folderPath = LFWPersTool.getCurrentFolderPath();
			String projectPath = LFWPersTool.getProjectPath();
			FuncRegisterDialog dlg = new FuncRegisterDialog(nodeid, null, WEBProjConstants.PUBLISH_NC, pm, folderPath, projectPath, userMessage);
			dlg.open();
		}
	}
	
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

}
