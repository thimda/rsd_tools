package nc.uap.lfw.funnode;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.lfwtools.perspective.MainPlugin;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 关联NC开发用户对话框
 * @author zhangxya
 *
 */
public class NCUserRelatedDialog  extends Dialog {
	
	private String userName = null;
	private String passWord = null;
	private String accountCode  = null;
	private Combo account = null;
	private Text username = null;
	private Text password = null;
	public static String ACCOUTN = "lfw_account";
	public static String USERNAME = "lfw_username";
	public static String PASSWORD = "lfw_password";
	
	public NCUserRelatedDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected void okPressed() {
		String accountname = account.getText();
		accountCode = (String) account.getData(accountname);
		userName = username.getText();
		passWord = password.getText();
		addLfwWebContextToProperty();
		super.okPressed();
		
	}
	
	/**
 	 * 向工程里的.module_prj文件中添加module.webContext属性，便于取得lfwweb的context
 	 * @return
 	 */
 	private boolean  addLfwWebContextToProperty(){
 		IProject project = LFWPersTool.getCurrentProject();
 		IFile jfile = project.getFile(".module_prj");
 		File file = new File(jfile.getLocation().toString());
 		LFWPersTool.checkOutFile(jfile.getLocation().toString());
 		BufferedReader reader = null;
 		StringBuffer sb = new StringBuffer();
 		String tempString = null;
 		try{
 			reader = new BufferedReader(new FileReader(file));
 			int line = 1;
 			boolean add = true;
 			while ((tempString = reader.readLine()) != null){
 				String[] v = tempString.split("\\=");
 				if(!v[0].equals(ACCOUTN) && !v[0].equals(USERNAME) &&  !v[0].equals(PASSWORD)){
 					sb.append(tempString + "\n");
 				}else {
 					add = false;
 					if(v[0].equals(ACCOUTN)){
 						sb.append(ACCOUTN + "=" + accountCode + "\n" );
 					}else if(v[0].equals(USERNAME)){
 						sb.append(USERNAME + "=" +  userName+ "\n"  );
 					}else if(v[0].equals(PASSWORD)){
 						sb.append(PASSWORD + "=" + passWord);
 					}
 				}
 				line++;
 			}
 			reader.close();
 			String initContent = sb.toString();
 			if(add){
 				initContent +=  ACCOUTN + "=" + accountCode + "\n" + USERNAME + "=" + userName + "\n" + PASSWORD + "=" + passWord;
 			}
 			ByteArrayInputStream stream = new ByteArrayInputStream(initContent.getBytes("8859_1"));
 			if (jfile.exists())
 				jfile.setContents(stream, true, false, null);
 			stream.close();
 		}catch(Exception e){
 			MainPlugin.getDefault().logError("修改.module_prj出错!", e);
 			return false;
 		}
 		return true;
 	}


	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent , SWT.NONE);
		container.setLayout(new GridLayout(1,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		Group grouId = new Group(container, SWT.NONE);
		grouId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grouId.setLayout(new GridLayout(2,false));
		
		Label accountLabel = new Label(grouId, SWT.NONE);
		accountLabel.setText("帐套:");
		account = new Combo(grouId, SWT.READ_ONLY);
		account.setLayoutData(new GridData(120,15));

		String[][] allAccount = LFWConnector.getAllAccount();
		for (int i = 0; i < allAccount.length; i++) {
			account.add(allAccount[i][1]);
			account.setData(allAccount[i][1], allAccount[i][0]);
		}
		
		Label labelId = new Label(grouId, SWT.NONE);
		labelId.setText("用户名:");
		username = new Text(grouId, SWT.BORDER);
		username.setLayoutData(new GridData(140,15));
		
		Label DatasetLabel = new Label(grouId, SWT.NONE);
		DatasetLabel.setText("密码:");
		password = new Text(grouId, SWT.BORDER);
		password.setLayoutData(new GridData(140,15));
		
//		Map<String, String>  userInfo = LFWPersTool.getUserInfoMap();
//		String acc = (String) userInfo.get(ExtAttrConstants.ACCOUNTCODE);
//		String user = (String)userInfo.get(ExtAttrConstants.USERNAME);
//		String pass = (String) userInfo.get(ExtAttrConstants.PASSWORD);
//		if(acc != null){
//			for (int i = 0; i < allAccount.length; i++) {
//				if(allAccount[i][0].equals(acc)){
//					//account.setData(allAccount[i][1], allAccount[i][0]);
//					account.setText(allAccount[i][1]);
//					break;
//				}
//			}
//		}
//		if(user != null)
//			username.setText(user);
//		if(pass != null)
//			password.setText(pass);
		return container;
	}
}
