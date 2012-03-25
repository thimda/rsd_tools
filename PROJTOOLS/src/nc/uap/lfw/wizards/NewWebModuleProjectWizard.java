package nc.uap.lfw.wizards;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.core.WEBCopyPathConstants;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.internal.project.IProjectProvider;
import nc.uap.lfw.internal.util.ProjCoreUtility;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * LFW��Ŀ��
 *
 * @since 1.6
 */

public class NewWebModuleProjectWizard extends Wizard implements IExecutableExtension, INewWizard {

	/** ��ͼƬ*/
	private static final ImageDescriptor DESC_NEWPPRJ_WIZ = WEBProjPlugin.loadImage(WEBProjPlugin.ICONS_PATH, "newpprj_wiz.gif");

	/** ����ҳ��*/
	protected NewWebModuleProjectPage fMainPage;

	/**������·������ҳ��*/
	protected NewModuleWebContextPage fNewModuleWebPage;
	
	/**ѡ���¾ɰ汾ҳ��*/
	protected NewWebModuleEditorPage fNewModuleEditorPage;

	protected IProjectProvider fProjectProvider;

	private IConfigurationElement fConfig;

	private final String targetFolder = "web";
	
	private IProject project;

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public NewWebModuleProjectWizard() {
		setDefaultPageImageDescriptor(DESC_NEWPPRJ_WIZ);
		setWindowTitle(WEBProjPlugin.getResourceString(WEBProjConstants.KEY_NEWPRJ_MAINPAGE_TITLE));
		fProjectProvider = new LFWProjectProvider();
	}
	
	/**
	 * ������ҳ��
	 */
	@Override
	public void addPages() {
		//��ҳ��
		fMainPage = new NewWebModuleProjectPage(WEBProjPlugin.getResourceString(WEBProjConstants.KEY_NEWPRJ_MAINPAGE_TITLE));
		addPage(fMainPage);
		((LFWProjectProvider)fProjectProvider).setFMainPage(fMainPage);
		// nextҳ�棬������·������
		addPathPage();
		// nextҳ�棬ѡ���¾ɰ汾
		fNewModuleEditorPage = new NewWebModuleEditorPage(WEBProjConstants.KEY_NEWPRJ_MAINPAGE_TITLE);
		addPage(fNewModuleEditorPage);
	}

	protected void addPathPage() {
		fNewModuleWebPage = new NewModuleWebContextPage(WEBProjConstants.KEY_NEWPRJ_MAINPAGE_TITLE);
		addPage(fNewModuleWebPage);
	}

	/**
	 * ���ʱִ�ж���
	 */
	@Override
	public boolean performFinish() {
		BasicNewProjectResourceWizard.updatePerspective(fConfig);
		try {
			performBasicOperation();
			perLfwOperation();
		} catch (InvocationTargetException e) {
			WEBProjPlugin.getDefault().logError(e);
		} catch (InterruptedException e) {
			WEBProjPlugin.getDefault().logError(e);
		}
		return true;
	}

	/**
	 * ��Ŀ��������
	 * 
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	protected void performBasicOperation() throws InvocationTargetException,InterruptedException {
		getContainer().run(false, true, new NewProjectCreationOperation(fProjectProvider));
	}

	/**
	 * LFW�������
	 */
	@SuppressWarnings("static-access")
	protected void perLfwOperation() {
		String context = "";
		/* ����Ŀ */
		if (fNewModuleWebPage.getMainRadio().isSelected())
			context = fNewModuleWebPage.getMainPath().getText();
		else
			context = fNewModuleWebPage.getPartPath().getText();
		final String docbase = fMainPage.getProjectHandle().getLocation().toString() + "/web";
		System.setProperty("context", context);
		System.setProperty("editorVersion", fNewModuleEditorPage.getSelectedValue());
		try {
			if (createLFWFolder(docbase) && addLfwWebContextToProperty() && writeModuleToProperties()) {
				Thread.currentThread().sleep(500);
				project.refreshLocal(IProject.DEPTH_INFINITE, null);
			}
		} catch (Exception e) {
			WEBProjPlugin.getDefault().logError(e);
		}
	}

	/**
	 * ��func_module��billtype_module����system.properties�ļ�
	 * 
	 * @return
	 */
	private boolean writeModuleToProperties() {
		//������Ŀʱ��������system.properties
		if (fNewModuleWebPage.getPartRadio().isSelected()){
			return true;
		}
		String filePath = project.getLocation().toString() + "/" + targetFolder
				+ "/WEB-INF/conf/system.properties";
		String initContent = "func_module=" + System.getProperty("context")
				+ "\r\n" + "billtype_module=" + System.getProperty("context")
				+ "\r\n" + "login.provider=" + WEBProjConstants.LOGIN_PROVIDER;
		try {
			FileUtilities.saveFile(filePath, initContent.getBytes());
		} catch (Exception e) {
			WEBProjPlugin.getDefault().logError(
					"����system.properties����" + e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * ����lfw�е��ļ������ع�����
	 * 
	 * @param docbase
	 * @return
	 */
	private boolean createLFWFolder(String docbase) {
		if (project == null)
			project = fMainPage.getProjectHandle();
		setProject(project);
		IFolder toFolder = project.getFolder(targetFolder);
		if (toFolder.exists()) 
			if (!MessageDialog.openQuestion(JavaPlugin.getActiveWorkbenchShell(), "��Ϣ", "Lfw web�ļ����Ѿ����ڣ����ǣ�"))
				return false;
		try {
			// ����;������ lfw����������ļ�
			String sourcePath = ProjCoreUtility.getWebSourcePath();
			String[] copyPaths = WEBCopyPathConstants.getWebCopyPath();
			String fromPath = null;
			String toPath = null;
			for (int i = 0; i < copyPaths.length; i++) {
				fromPath = sourcePath + "/" + copyPaths[i];
				if (copyPaths[i].equals("WEB-INF/web.xml.copy"))
					toPath = project.getLocation().toString() + "/" + targetFolder + "/" + "WEB-INF/web.xml";
				else
					toPath = project.getLocation().toString() + "/" + targetFolder + "/" + copyPaths[i];
				FileUtilities.copyFile(fromPath, toPath);
			}

			// ����webbase��copynodes�������ļ��м��ļ�
			String toDir = project.getLocation().toString() + "/" + targetFolder + "/html/nodes";
			String copyfiles = sourcePath + "/" + "copynodes";
			FileUtilities.copyFileFromDir(toDir, copyfiles);

			//����Ŀ 
			if (fNewModuleWebPage.getMainRadio().isSelected()) {
				// �滻web.xml�ļ�������
				String webtoPath = project.getLocation().toString() + "/" + targetFolder + "/WEB-INF";
				String filePath = webtoPath + "/web.xml";
				String fileContent = FileUtilities.fetchFileContent(filePath,"UTF-8");
				if (fileContent.indexOf("<param-value>/lfw2</param-value>") != -1)
					fileContent = fileContent.replace("<param-value>/lfw2</param-value>","<param-value>/"
									+ fNewModuleWebPage.getMainPath().getText() + "</param-value>");
				else
					fileContent = fileContent.replace("<param-value>/lfw</param-value>", "<param-value>/"
									+ fNewModuleWebPage.getMainPath().getText() + "</param-value>");
				byte[] content = fileContent.getBytes();
				FileUtilities.saveFile(filePath, content);
			}
			//������Ŀ 
			else if (fNewModuleWebPage.getPartRadio().isSelected()) {
				final String mainContext = fNewModuleWebPage.getPartPath()
						.getText();
				final String webPath = fMainPage.getProjectHandle()
						.getLocation().toString()
						+ "/web/WEB-INF/";
				try {
					// ɾ��web.xml 
					File webFile = new File(webPath + "web.xml");
					webFile.delete();

					// ����portal.part
					writePart(webPath, fMainPage.getModuleName(), mainContext);
				} catch (Exception e) {
					WEBProjPlugin.getDefault().logError(e);
				}
			}
		} catch (Exception e) {
			WEBProjPlugin.getDefault().logError("����LFW�ļ�����" + e.getMessage(),e);
			return false;
		}
		return true;
	}

	/**
	 * �򹤳����.module_prj�ļ������module.webContext���ԣ�����ȡ��lfwweb��context
	 * 
	 * @return
	 */
	private boolean addLfwWebContextToProperty() {
//		/* ������Ŀ context Ϊ�� */
//		if (System.getProperty("context").equals(""))
//			return true;
		IFile jfile = project.getFile(".module_prj");
		File file = new File(jfile.getLocation().toString());
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		String tempString = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				String[] v = tempString.split("\\=");
				if (!v[0].equals("module.webContext")) {
					sb.append(tempString + "\n");
				}
				line++;
			}
			reader.close();
			String initContent = sb.toString() + "module.webContext="
					+ System.getProperty("context") + "\n" + "module.editorVersion=" + System.getProperty("editorVersion");
			ByteArrayInputStream stream = new ByteArrayInputStream(initContent
					.getBytes("8859_1"));
			if (jfile.exists())
				jfile.setContents(stream, false, false, null);
			stream.close();
		} catch (Exception e) {
			WEBProjPlugin.getDefault().logError(
					"�޸�.module_prj����" + e.getMessage(), e);
			return false;
		}
		return true;
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		fConfig = config;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	/**
	 * ����*.part
	 * 
	 * @param webPath
	 *            WEB-INF ·��
	 * @param module
	 *            ģ����
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	protected void writePart(String webPath, String module, String mainContext)
			throws UnsupportedEncodingException, Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		buffer.append("\n<web-app>");
		buffer.append("\n	<context-param>");
		buffer.append("\n		<param-name>ctxPath</param-name>");
		buffer.append("\n		<param-value>/" + module + "</param-value>");
		buffer.append("\n	</context-param>\n");
		buffer.append("\n	<context-param>");
		buffer.append("\n		<param-name>modules</param-name>");
		buffer.append("\n		<param-value>" + module + "</param-value>");
		buffer.append("\n	</context-param>");
		buffer.append("\n</web-app>");
		File f = new File(webPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileUtilities.saveFile(webPath + "/" + mainContext + ".part", buffer
				.toString().getBytes("UTF-8"));

	}

}
