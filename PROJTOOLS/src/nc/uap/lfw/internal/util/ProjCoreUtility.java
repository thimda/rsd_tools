package nc.uap.lfw.internal.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.XmlUtility;
import nc.uap.lfw.core.WEBClassPathContainerID;
import nc.uap.lfw.core.WEBCopyPathConstants;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.internal.project.LFWClasspathContainer;
import nc.uap.lfw.internal.project.WEBProject;
import nc.uap.lfw.model.LFWLibraryLocation;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProjCoreUtility {
	public static IAccessRule[]	Fobidden	= new IAccessRule[] { JavaCore.newAccessRule(new Path("**/*"), IAccessRule.K_NON_ACCESSIBLE) };
	public static IAccessRule[]	Discouraged	= new IAccessRule[] { JavaCore.newAccessRule(new Path("**/*"), IAccessRule.K_DISCOURAGED) };
	public static IAccessRule[]	Accessible	= {};
	private static final String targetFolder = "web";

	public static void createProject(IProject project, IPath location, IProgressMonitor monitor) throws CoreException
	{
		if (!Platform.getLocation().equals(location))
		{
			IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
			desc.setLocation(location);
			project.create(desc, monitor);
		}
		else
			project.create(monitor);
	}

	public static boolean isEditable(IFile file, Shell shell)
	{
		if (!file.isReadOnly())
		{
			return true;
		}
		else
		{
			ResourcesPlugin.getWorkspace().validateEdit(new IFile[] { file }, shell);
			return !file.isReadOnly();
		}
	}

	public static void silentSetWriterable(IResource res) throws CoreException
	{
		ResourceAttributes attrib = res.getResourceAttributes();
		attrib.setReadOnly(false);
		res.setResourceAttributes(attrib);
	}

	public static IClasspathEntry[] getClasspathEntry(IProject project, WEBClassPathContainerID id) throws CoreException
	{
		WEBProject mdeproject = ProjCoreUtility.createMDEProject(project);
		switch (id)
		{
			case Module_Public_Library:
				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getAccessibleModuleFolders()), Accessible);
			case Module_Client_Library:
				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getModuleAccessibleClientFolders()), ClasspathComputer
						.computeStandCP(mdeproject.getModuleDiscouragedClientFolders()), null);
			case Module_Private_Library:
				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getModulePrivateFolders()), Fobidden);
			case Module_Lang_Library:
				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getModulesLanglibFoder()), Accessible);
			case Product_Common_Library:
				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getNCHOME(), mdeproject.getExternalFoder()), Accessible);
			case Framework_Library:
				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getFrameworkFoder()), Accessible);
			case Generated_EJB:
				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getEjbFoder()), Fobidden);
			case Middleware_Library:
				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getMiddlewareFoder()), Discouraged);
			case Ant_Library:
				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getAntFoder()), Accessible);
			default:
				return new IClasspathEntry[0];
		}
	}

	public static IClasspathEntry[] computeClasspathEntry(LFWLibraryLocation[] accessiblelibs, LFWLibraryLocation[] discouragedlibs, LFWLibraryLocation[] fobiddenlibs)
	throws CoreException
	{
	IClasspathEntry[] accessibleEntries = computeClasspathEntry(accessiblelibs, Accessible);
	IClasspathEntry[] discouragedEntries = computeClasspathEntry(discouragedlibs, Discouraged);
	IClasspathEntry[] fobiddenEntries = computeClasspathEntry(fobiddenlibs, Fobidden);
	IClasspathEntry[] allentries = new IClasspathEntry[accessibleEntries.length + discouragedEntries.length + fobiddenEntries.length];
	System.arraycopy(accessibleEntries, 0, allentries, 0, accessibleEntries.length);
	System.arraycopy(discouragedEntries, 0, allentries, accessibleEntries.length, discouragedEntries.length);
	System.arraycopy(fobiddenEntries, 0, allentries, accessibleEntries.length + discouragedEntries.length, fobiddenEntries.length);
	return allentries;
	}
	
	
	 public static IPath getModulesFolderPath()
    {
        return getNcHomeFolderPath().append("modules");
    }

	 public static IPath getNcHomeFolderPath()
    {
          return WEBProjPlugin.getNCHomePath();
    }

	public static IClasspathEntry[] computeClasspathEntry(LFWLibraryLocation[] libs, IAccessRule[] rules) throws CoreException
	{
		ArrayList<IClasspathEntry> list = new ArrayList<IClasspathEntry>();
		if (libs != null)
		{
			for (LFWLibraryLocation lib : libs)
			{
				IClasspathAttribute[] atts = new IClasspathAttribute[0];
				if (lib.getDocLocation() != null)
				{
					atts = new IClasspathAttribute[] { JavaCore
							.newClasspathAttribute(IClasspathAttribute.JAVADOC_LOCATION_ATTRIBUTE_NAME, lib.getDocLocation()) };
				}
				IClasspathEntry entry = JavaCore.newLibraryEntry(lib.getLibPath(), lib.getSrcPath(), null, rules, atts, false);
				list.add(entry);
			}
		}
		return list.toArray(new IClasspathEntry[0]);
	}
	
	
	public static LFWClasspathContainer getLFWClasspathContainer(IPath path, IJavaProject javaProject)
	{
		try
		{
			return (LFWClasspathContainer) JavaCore.getClasspathContainer(path, javaProject);
		}
		catch (JavaModelException e)
		{
			return null;
		}
	}
	
	
	public static void fileCopy(IFile form, IFile to) throws CoreException, IOException
	{
		InputStream in = form.getContents();
		if (to.exists())
		{
			silentSetWriterable(to);
			to.setContents(in, true, false, null);
		}
		else
		{
			to.create(in, true, null);
		}
		in.close();
	}

	public static void addNatureToProject(IProject proj, String natureId, IProgressMonitor monitor) throws CoreException
	{
		IProjectDescription description = proj.getDescription();
		String[] prevNatures = description.getNatureIds();
		String[] newNatures = new String[prevNatures.length + 1];
		System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
		newNatures[prevNatures.length] = natureId;
		description.setNatureIds(newNatures);
		proj.setDescription(description, monitor);
	}

	public static void createFolder(IFolder folder) throws CoreException
	{
		if (!folder.exists())
		{
			IContainer parent = folder.getParent();
			if (parent instanceof IFolder)
			{
				createFolder((IFolder) parent);
			}
			folder.create(true, true, null);
		}
	}

	public static IClasspathEntry createJREEntry()
	{
		return JavaCore.newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"));
	}

	public static IClasspathEntry createSourceEntry(IProject project, String src, String output) throws CoreException
	{
		IFolder folder = project.getFolder(src);
		if (!folder.exists())
			ProjCoreUtility.createFolder(folder);
		folder = project.getFolder(output);
		if (!folder.exists())
			ProjCoreUtility.createFolder(folder);
		IPath path = project.getFullPath().append(src);
		IPath outPath = project.getFullPath().append(output);
		return JavaCore.newSourceEntry(path, new IPath[0], new IPath[0], outPath);
	}

	public static IClasspathEntry createSourceEntry(IProject project, String src) throws CoreException
	{
		IFolder folder = project.getFolder(src);
		if (!folder.exists())
			ProjCoreUtility.createFolder(folder);
		IPath path = project.getFullPath().append(src);
		return JavaCore.newSourceEntry(path, new IPath[0]);
	}
	
	public static IFolder creatFolderLink(IProject project, String folderName, final IPath linkPath) throws CoreException
	{
		IFolder folderHandle = createFolderHandle(project, folderName);
		if (!folderHandle.exists())
		{
			if (linkPath == null)
				folderHandle.create(false, true, null);
			else
				folderHandle.createLink(linkPath, IResource.ALLOW_MISSING_LOCAL, null);
		}
		else
		{
			if (!folderHandle.getRawLocation().equals(linkPath))
			{
				folderHandle.delete(true, null);
				folderHandle = creatFolderLink(project, folderName, linkPath);
			}
		}
		return folderHandle;
	}

	public static IFolder createFolderHandle(IProject project, String folderName)
	{
		IWorkspaceRoot workspaceRoot = project.getWorkspace().getRoot();
		IPath folderPath = project.getFullPath().append(folderName);
		IFolder folderHandle = workspaceRoot.getFolder(folderPath);
		return folderHandle;
	}

//	public static MDEClasspathContainer getMDEClasspathContainer(IPath path, IJavaProject javaProject)
//	{
//		try
//		{
//			return (MDEClasspathContainer) JavaCore.getClasspathContainer(path, javaProject);
//		}
//		catch (JavaModelException e)
//		{
//			return null;
//		}
//	}

	public static boolean isModuleProject(IProject project)
	{
		if (project.isOpen())
		{
			try
			{
				return project.hasNature(WEBProjConstants.MODULE_NATURE);
			}
			catch (CoreException e)
			{
			}
		}
		return false;
	}
	
	public static boolean isPortalProject(IProject project){
		if(project.isOpen()){
			try{
				return (project.hasNature(WEBProjConstants.MODULE_NATURE) && project.hasNature(WEBProjConstants.PORTAL_MODULE_NATURE));
			}catch(CoreException e){
				
			}
		}
		return false;
	}

//	public static void configLaunchConfiguration(ILaunchConfigurationWorkingCopy wc)
//	{
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MDEConstants.SERVER_MAIN_CLASS);
//		String ncHome = toVarRepresentation(MDEConstants.FIELD_NC_HOME);
//		StringBuffer vmargs = new StringBuffer();
//		vmargs.append("-Dnc.exclude.modules=").append(toVarRepresentation(MDEConstants.FIELD_EX_MODULES)).append(" ");
//		vmargs.append("-Dnc.runMode=develop -Dnc.server.location=").append(ncHome).append(" ");
//		vmargs.append("-DEJBConfigDir=").append(ncHome).append("/").append("ejbXMLs").append(" ");
//		vmargs.append("-DExtServiceConfigDir=").append(ncHome).append("/").append("ejbXMLs");
//		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, MDEConstants.MW_CLASSPATH_PROVIDER);
//		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, MDEConstants.NC_SOURCEPATH_PROVIDER);
//		wc.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmargs.toString());
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, toVarRepresentation(MDEConstants.FIELD_NC_HOME));
//	}
//
//	public static void configJStarterConfiguration(ILaunchConfigurationWorkingCopy wc)
//	{
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MDEConstants.JSTARTER_CLASS);
//		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, javaProject.getJavaProject().getElementName());
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, MDEConstants.MW_CLASSPATH_PROVIDER);
//		//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, MDEConstants.NC_SOURCEPATH_PROVIDER);
//		wc.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
//		StringBuffer vmargs = new StringBuffer();
//		vmargs.append("-Dnc.runMode=develop -Dnc.jstart.server=" +
//				toVarRepresentation(MDEConstants.FIELD_CLINET_IP) +
//				" -Dnc.jstart.port=" +
//				toVarRepresentation(MDEConstants.FIELD_CLINET_PORT));
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmargs.toString());
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, toVarRepresentation(MDEConstants.FIELD_NC_HOME));
//	}
	
//	public static void configLFWLaunchConfiguration(ILaunchConfigurationWorkingCopy wc)
//	{
//		IProject project = null;
//		try {
//			project = ResourcesPlugin.getWorkspace().getRoot().getProject(wc.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""));
//			
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, WEBProjConstants.LFW_SERVER_MAIN_CLASS);
//		String ncHome = toVarRepresentation(WEBProjConstants.FIELD_NC_HOME);
//		StringBuffer vmargs = new StringBuffer();
//		vmargs.append("-Dnc.runMode=develop -Dnc.server.location=").append(ncHome).append(" ");
//		vmargs.append("-DEJBConfigDir=").append(ncHome).append("/").append("ejbXMLs").append(" ");
//		vmargs.append("-DExtServiceConfigDir=").append(ncHome).append("/").append("ejbXMLs").append(" ");
//	
//		vmargs.append("-Dweb.context=" + LFWUtility.getContextFromResource(project));
//		
//		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, MDEConstants.MW_CLASSPATH_PROVIDER);
//		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, MDEConstants.NC_SOURCEPATH_PROVIDER);
//		wc.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmargs.toString());
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, toVarRepresentation(WEBProjConstants.FIELD_NC_HOME));
//	}

	public static String getPrefix(String fullName)
	{
		if (fullName == null)
			return null;
		int lastDotIndex = fullName.lastIndexOf('.');
		if (lastDotIndex > 0)
			return fullName.substring(0, lastDotIndex);
		else
			return fullName;
	}

	public static String getExtension(String fullName)
	{
		if (fullName == null)
			return null;
		int lastDotIndex = fullName.lastIndexOf('.');
		if (lastDotIndex > 0 && lastDotIndex != (fullName.length() - 1))
			return fullName.substring(lastDotIndex + 1);
		else
			return null;
	}

	public static void refreshWorkspace(IProgressMonitor monitor)
	{
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects)
		{
			try
			{
				if (project.isOpen() && project.hasNature(WEBProjConstants.MODULE_NATURE))
				{
					project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				}
			}
			catch (CoreException e)
			{
				WEBProjPlugin.getDefault().logError(e);
			}
		}
	}

	public static void updateWorkspaceClasspath()
	{
		Job job = new Job("Update LFW Project Classpath")
		{
			public IStatus run(IProgressMonitor monitor)
			{
				updateWorkspaceClasspath(monitor);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	public static void updateWorkspaceClasspath(IProgressMonitor monitor)
	{
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects)
		{
			try
			{
				if (project.isOpen() && project.hasNature(WEBProjConstants.MODULE_NATURE))
				{
					monitor.beginTask("LFW", 100);
					project.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 80));
					//ClasspathComputer.updateClasspath(project, new SubProgressMonitor(monitor, 20));
					updateLFWCopyFolder(project);
				}
			}
			catch (CoreException e)
			{
				WEBProjPlugin.getDefault().logError(e);
			}
		}
	}
	
	
	
	private static boolean  updateLFWCopyFolder(IProject project){
		
//		IFolder toFolder = project.getFolder(targetFolder);
//		if(toFolder.exists()){
//			if (!MessageDialog.openQuestion(JavaPlugin.getActiveWorkbenchShell(), "信息", "Lfw web文件夹已经存在！覆盖？"))
//				return false;
//		}
		try{
			//两种途径拷贝 lfw工程所需的文件
			String sourcePath = getWebSourcePath();
			//拷贝指定文件
			String[] copyPaths = WEBCopyPathConstants.getWebCopyPath();
			String fromPath = null;
			String toPath = null;
			for (int i = 0; i < copyPaths.length; i++) {
				fromPath = sourcePath + "/" + copyPaths[i];
				toPath = project.getLocation().toString() + "/" + targetFolder + "/" + copyPaths[i];
				FileUtilities.copyFile(fromPath, toPath);
			}
			
			//拷贝webbase下copynodes的所有文件夹及文件
			///**
//			 * 复制目录下的文件（不包括此目录）到指定目录，连同子目录一起复制。
//			 * @param toPath
//			 * @param fromPath
//			 */
			String toDir = project.getLocation().toString() + "/" + targetFolder + "/html/nodes";
			String copyfiles =  sourcePath + "/" + "copynodes";
			FileUtilities.copyFileFromDir(toDir, copyfiles);

			//替换web.xml文件中内容
			String webtoPath = project.getLocation().toString() + "/" + targetFolder + "/WEB-INF";
			String filePath = webtoPath + "/web.xml";
			String fileContent = FileUtilities.fetchFileContent(filePath,"UTF-8");
			if(fileContent.indexOf("<param-value>/lfw2</param-value>") != -1)
	 			fileContent = fileContent.replace("<param-value>/lfw2</param-value>","<param-value>/" +  project.getName() + "</param-value>");
	 		else
	 			fileContent = fileContent.replace("<param-value>/lfw</param-value>","<param-value>/" +  project.getName() + "</param-value>");
			byte[] content = fileContent.getBytes();
			FileUtilities.saveFile(filePath, content);
			
		}
		catch(Exception e){
			WEBProjPlugin.getDefault().logError("拷贝LFW文件出错：" + e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	
	
	/**
 	 * web.xml路径
 	 * @return
 	 * @throws Exception
 	 */
	public static String getWebSourcePath() throws Exception {
		String sourcePath = "";
		IPath path = WEBProjPlugin.getNCHomePath();
		String ncHomePath =  path.toString();
		sourcePath = ncHomePath + "/hotwebs/lfw2/WEB-INF";
		File file = new File(sourcePath);
		if(!file.exists()){
			sourcePath = ncHomePath + "/hotwebs/lfw/WEB-INF";
			file = new File(sourcePath);
			if(!file.exists()){
				File jfile = new File(ncHomePath + "/conf/Catalina/localhost/lfw2.xml");
				if(!jfile.exists())
					jfile = new File(ncHomePath + "/conf/Catalina/localhost/lfw.xml");
				Document doc = XmlUtility.getDocument(jfile);
//		 		BufferedReader reader  = new BufferedReader(new FileReader(jfile));
//		 		Document doc = getDocumentBuilder().parse(new InputSource(reader));
		 		Element ele = (Element) doc.getDocumentElement();
		 		sourcePath= ele.getAttribute("docBase");
			}
		}
		if(sourcePath.endsWith("/WEB-INF"))
			sourcePath = sourcePath.substring(0, sourcePath.length() - "/WEB-INF".length());
		return sourcePath;
	}

	
	public static void configLaunchConfiguration(ILaunchConfigurationWorkingCopy wc)
	{
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, WEBProjConstants.SERVER_MAIN_CLASS);
		String ncHome = toVarRepresentation(WEBProjConstants.FIELD_NC_HOME);
		StringBuffer vmargs = new StringBuffer();
		vmargs.append("-Dnc.exclude.modules=").append(toVarRepresentation(WEBProjConstants.FIELD_EX_MODULES)).append(" ");
		vmargs.append("-Dnc.runMode=develop -Dnc.server.location=").append(ncHome).append(" ");
		vmargs.append("-DEJBConfigDir=").append(ncHome).append("/").append("ejbXMLs").append(" ");
		vmargs.append("-DExtServiceConfigDir=").append(ncHome).append("/").append("ejbXMLs");
		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, MDEConstants.MW_CLASSPATH_PROVIDER);
		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, MDEConstants.NC_SOURCEPATH_PROVIDER);
		wc.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmargs.toString());
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, toVarRepresentation(WEBProjConstants.FIELD_NC_HOME));
	}

	public static void createBuildProperties(IProject project, String moduleName, String moduleConfig)
	{
		IFile file = project.getFile(".module_prj");
		StringWriter swriter = new StringWriter();
		PrintWriter writer = new PrintWriter(swriter);
		writer.println(WEBProjConstants.MODULE_NAME_PROPERTY + "=" + moduleName);
		writer.println(WEBProjConstants.MODULE_CONFIG_PROPERTY + "=" + moduleConfig);
		String initContent = swriter.getBuffer().toString();
		try
		{
			ByteArrayInputStream stream = new ByteArrayInputStream(initContent.getBytes("8859_1"));
			if (file.exists())
			{
				file.setContents(stream, false, false, null);
			}
			else
			{
				file.create(stream, false, null);
			}
			stream.close();
		}
		catch (CoreException e)
		{
		}
		catch (IOException e)
		{
		}
	}

	public static void ceateInitManifest(IProject project, String moduleConfig, String moduleName) throws CoreException
	{
		IFolder folder = project.getFolder("META-INF");
		if (!folder.exists())
			createFolder(folder);
		if (moduleConfig != null)
		{
			IFile file = project.getFile("META-INF/" + moduleConfig);
			StringWriter swriter = new StringWriter();
			PrintWriter writer = new PrintWriter(swriter);
			writer.println("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
			writer.print("<module");
			if (moduleName != null && "module.xml".equals(moduleConfig))
			{
				writer.print(" name=\"" + moduleName + "\"");
			}
			//        writer.print(" priority=\"" + fProjectProvider.getModulePriority() + "\"");
			writer.println(">");
			writer.println("    <public>");
			writer.println("    </public>");
			writer.println("    <private>");
			writer.println("    </private>");
			writer.println("</module>");
			String initContent = swriter.getBuffer().toString();
			try
			{
				ByteArrayInputStream stream = new ByteArrayInputStream(initContent.getBytes("8859_1"));
				if (file.exists())
				{
					file.setContents(stream, false, false, null);
				}
				else
				{
					file.create(stream, false, null);
				}
				stream.close();
			}
			catch (CoreException e)
			{
			}
			catch (IOException e)
			{
			}
		}
	}

	public static WEBProject createMDEProject(IProject project)
	{
		try
		{
			if (!project.hasNature(WEBProjConstants.MODULE_NATURE))
			{
				throw new IllegalArgumentException("无效的参数,不是LFWProject");
			}
			if (!project.hasNature(WEBProjConstants.MDE_MODULE_NATURE))
			{
				throw new IllegalArgumentException("无效的参数,不是MDEProject");
			}
		}
		catch (CoreException e)
		{
			throw new IllegalArgumentException("无效的参数,不是MDEProject");
		}
		return new WEBProject(project);
	}

	
	public static WEBProject createPortalProject(IProject project)
	{
		try
		{
			if (!project.hasNature(WEBProjConstants.MODULE_NATURE))
			{
				throw new IllegalArgumentException("无效的参数,不是LFWProject");
			}
			if (!project.hasNature(WEBProjConstants.MDE_MODULE_NATURE))
			{
				throw new IllegalArgumentException("无效的参数,不是MDEProject");
			}
			if(!project.hasNature(WEBProjConstants.PORTAL_MODULE_NATURE))
			{
				throw new IllegalArgumentException("无效的参数,不是Portal Projcet!");
			}
		}
		catch (CoreException e)
		{
			throw new IllegalArgumentException("无效的参数,不是MDEProject");
		}
		return new WEBProject(project);
	}

	
	public static String toVarRepresentation(String varName)
	{
		return "${" + varName + "}";
	}

	public static IClasspathEntry createContainerClasspathEntry(WEBClassPathContainerID id)
	{
		return JavaCore.newContainerEntry(new Path(WEBProjConstants.MDE_LIBRARY_CONTAINER_ID).append(id.name()), false);
	}

	public static String getProjectModuleName(IProject project)
	{
		return createMDEProject(project).getModuleName();
	}

	public static void silentSetWriterable(String filename) throws CoreException
	{
		IFileSystem fs = EFS.getLocalFileSystem();
		IFileInfo fileinfo = new FileInfo(filename);
		fileinfo.setAttribute(EFS.ATTRIBUTE_READ_ONLY, false);
		IFileStore store = fs.fromLocalFile(new File(filename));
		store.putInfo(fileinfo, EFS.SET_ATTRIBUTES, null);
	}

//	public static IClasspathEntry[] getClasspathEntry(IProject project, MDEClassPathContainerID id) throws CoreException
//	{
//		MDEProject mdeproject = CoreUtility.createMDEProject(project);
//		switch (id)
//		{
//			case Module_Public_Library:
//				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getAccessibleModuleFolders()), Accessible);
//			case Module_Client_Library:
//				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getModuleAccessibleClientFolders()), ClasspathComputer
//						.computeStandCP(mdeproject.getModuleDiscouragedClientFolders()), null);
//			case Module_Private_Library:
//				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getModulePrivateFolders()), Fobidden);
//			case Module_Lang_Library:
//				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getModulesLanglibFoder()), Accessible);
//			case Product_Common_Library:
//				return computeClasspathEntry(ClasspathComputer.computeStandCP(mdeproject.getNCHOME(), mdeproject.getExternalFoder()), Accessible);
//			case Framework_Library:
//				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getFrameworkFoder()), Accessible);
//			case Generated_EJB:
//				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getEjbFoder()), Fobidden);
//			case Middleware_Library:
//				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getMiddlewareFoder()), Discouraged);
//			case Ant_Library:
//				return computeClasspathEntry(ClasspathComputer.computeJarsInPath(mdeproject.getAntFoder()), Accessible);
//			default:
//				return new IClasspathEntry[0];
//		}
//	}
//
//	public static IClasspathEntry[] computeClasspathEntry(LibraryLocation[] libs, IAccessRule[] rules) throws CoreException
//	{
//		ArrayList<IClasspathEntry> list = new ArrayList<IClasspathEntry>();
//		if (libs != null)
//		{
//			for (LibraryLocation lib : libs)
//			{
//				IClasspathAttribute[] atts = new IClasspathAttribute[0];
//				if (lib.getDocLocation() != null)
//				{
//					atts = new IClasspathAttribute[] { JavaCore
//							.newClasspathAttribute(IClasspathAttribute.JAVADOC_LOCATION_ATTRIBUTE_NAME, lib.getDocLocation()) };
//				}
//				IClasspathEntry entry = JavaCore.newLibraryEntry(lib.getLibPath(), lib.getSrcPath(), null, rules, atts, false);
//				list.add(entry);
//			}
//		}
//		return list.toArray(new IClasspathEntry[0]);
//	}
//
//	public static IClasspathEntry[] computeClasspathEntry(LibraryLocation[] accessiblelibs, LibraryLocation[] discouragedlibs, LibraryLocation[] fobiddenlibs)
//			throws CoreException
//	{
//		IClasspathEntry[] accessibleEntries = computeClasspathEntry(accessiblelibs, Accessible);
//		IClasspathEntry[] discouragedEntries = computeClasspathEntry(discouragedlibs, Discouraged);
//		IClasspathEntry[] fobiddenEntries = computeClasspathEntry(fobiddenlibs, Fobidden);
//		IClasspathEntry[] allentries = new IClasspathEntry[accessibleEntries.length + discouragedEntries.length + fobiddenEntries.length];
//		System.arraycopy(accessibleEntries, 0, allentries, 0, accessibleEntries.length);
//		System.arraycopy(discouragedEntries, 0, allentries, accessibleEntries.length, discouragedEntries.length);
//		System.arraycopy(fobiddenEntries, 0, allentries, accessibleEntries.length + discouragedEntries.length, fobiddenEntries.length);
//		return allentries;
//	}

	public static IFolder[] getModuleFolders(IFolder modulefolder) throws CoreException
	{
		final ArrayList<IFolder> list = new ArrayList<IFolder>();
		if (modulefolder.exists())
		{
			modulefolder.accept(new IResourceVisitor()
			{
				public boolean visit(IResource resource) throws CoreException
				{
					if (resource.getType() == IResource.FILE)
					{
						return false;
					}
					if (resource.getType() == IResource.FOLDER)
					{
						IFolder folder = (IFolder) resource;
						if (folder.exists(new Path("META-INF/module.xml")))
						{
							list.add(folder);
							return false;
						}
					}
					return true;
				}
			});
		}
		return list.toArray(new IFolder[0]);
	}
	//add 20080220 for lfw
//	public static void configLFWLaunchConfiguration(ILaunchConfigurationWorkingCopy wc)
//	{
//		IProject project = null;
//		try {
//			project = ResourcesPlugin.getWorkspace().getRoot().getProject(wc.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""));
//			
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MDEConstants.LFW_SERVER_MAIN_CLASS);
//		String ncHome = toVarRepresentation(MDEConstants.FIELD_NC_HOME);
//		StringBuffer vmargs = new StringBuffer();
//		vmargs.append("-Dnc.runMode=develop -Dnc.server.location=").append(ncHome).append(" ");
//		vmargs.append("-DEJBConfigDir=").append(ncHome).append("/").append("ejbXMLs").append(" ");
//		vmargs.append("-DExtServiceConfigDir=").append(ncHome).append("/").append("ejbXMLs").append(" ");
//	
//		vmargs.append("-Dweb.context=" + LfwUtility.getContextFromResource(project));
//		
//		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, MDEConstants.MW_CLASSPATH_PROVIDER);
//		//wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, MDEConstants.NC_SOURCEPATH_PROVIDER);
//		wc.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmargs.toString());
//		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, toVarRepresentation(MDEConstants.FIELD_NC_HOME));
//	}
//	//add end
}
