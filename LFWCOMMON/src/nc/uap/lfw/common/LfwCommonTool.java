package nc.uap.lfw.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import nc.uap.lfw.plugin.common.CommonPlugin;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ExternalPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Perspective公用方法
 * @author zhangxya
 *
 */
@SuppressWarnings("restriction")
public class LfwCommonTool  {
	
	private static Boolean isSupportGDI = null;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String WEBCONTEXT = "module.webContext";
	/**
	 * 获取所有的根路径
	 * @return
	 */
	public static  String[] getAllRootPackage(IProject proj){
		String projName = proj.getName();
		JavaProject javaProj = (JavaProject) JavaCore.create(proj);
		IPackageFragmentRoot[] pfrs = null;;
		try {
			pfrs = javaProj.getAllPackageFragmentRoots();
		} 
		catch (JavaModelException e) {
			CommonPlugin.getPlugin().logError(e);
		}
		List<String> rootPackage = new ArrayList<String>(); 
		if(pfrs != null){
			for (int i = 0; i < pfrs.length; i++) {
				if(pfrs[i] instanceof JarPackageFragmentRoot || pfrs[i] instanceof ExternalPackageFragmentRoot)
					continue;
				else if(pfrs[i] instanceof PackageFragmentRoot){
					PackageFragmentRoot root = (PackageFragmentRoot) pfrs[i];
					String rootPath = root.getPath().toString();
					if(rootPath.indexOf(projName) != -1){
						String absPath = root.getPath().removeFirstSegments(1).makeRelative().toString();
						if(!absPath.startsWith("NC_HOME") && absPath.startsWith("src"))
							rootPackage.add(absPath);
					}
				}
				
			}
		}
		return (String[])rootPackage.toArray(new String[rootPackage.size()]);
	}
	
	
	public static String getModuleProperty(IProject project, String name){
		IFile jfile = project.getFile(".module_prj");
 		File file = new File(jfile.getLocation().toString());
 		InputStream input = null;
 		String returnName = null;
		if (file.exists()) {
			try {
				input = new FileInputStream(file);
				Properties prop = new Properties();
				prop.load(input);
				returnName =  prop.getProperty(name);
			} catch (Exception e) {
				CommonPlugin.getPlugin().logError("读取module_prj文件出错!", e);
			}
			finally{
				try {
					if(input != null)
						input.close();
				} catch (IOException e) {
					//LfwLogger.error(e.getMessage(), e);
					CommonPlugin.getPlugin().logError("读取module_prj文件出错!", e);
				}
			}
		}
		return returnName;
	}
	
	/**
	 * checkout文件
	 */
	 public static void checkOutFile(String path){
			IPath ph = new Path(path);
			IFile ifile =  ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(ph);
			File filea = new File(path);
			//如果文件不可写，checkout,若果未连cc，变为可写
			IWorkbenchPart part = null;
			Shell shell = null;
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (page != null)
				part = page.getActivePart();
			if(part != null)
				shell = part.getSite().getShell();
			IStatus statu = ResourcesPlugin.getWorkspace().validateEdit(new IFile[]{ifile}, shell);
				if(!filea.canWrite() && !statu.isOK()){
				boolean isWritable = MessageDialog.openConfirm(null, "提示", "是否将文件变为可写?");
				if(isWritable){
					try {
						LfwCommonTool.silentSetWriterable(path);
					} catch (Exception e) {
						CommonPlugin.getPlugin().logError(e.getMessage());
						MessageDialog.openInformation(null, "提示", e.getMessage());
					}
				}
			}
		 }
		
	
	

	public static boolean isSupportGDI() {
		if (isSupportGDI == null) {
			try {
				System.loadLibrary("gdiplus");
				isSupportGDI = Boolean.TRUE;
			} catch (Throwable e) {
				isSupportGDI = Boolean.FALSE;
			}
		}
		return isSupportGDI.booleanValue();
	}

		
	/**
	 * 将文件变为可写
	 * @param filename
	 * @throws CoreException
	 */
	 public static void silentSetWriterable(String filename) throws CoreException {
        IFileInfo fileinfo = new FileInfo(filename);
        fileinfo.setAttribute(EFS.ATTRIBUTE_READ_ONLY, false);
        IFileSystem fs = EFS.getLocalFileSystem();
        IFileStore store = fs.fromLocalFile(new File(filename));
        store.putInfo(fileinfo, EFS.SET_ATTRIBUTES, null);
	 }
	
	
	
	public static IViewPart showView(String viewId) {
		IViewPart part = null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (page != null)
				part = page.showView(viewId, null, IWorkbenchPage.VIEW_VISIBLE);
		} 
		catch (PartInitException e) {
			CommonPlugin.getPlugin().logError(e.getMessage(), e);
		}
		return part;
	}
	
	
	

	public static String formatDateString(Date date) {
		if (date == null) {
			return "";
		}
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	public static Date parseStringToDate(String dateStr) {
		if (dateStr == null || dateStr.trim().length() == 0)
			return null;
		Date d = null;
		try {
			d = sdf.parse(dateStr);
		} catch (ParseException e) {
			CommonPlugin.getPlugin().logError(e);
		}
		return d;
	}

	public static String getWrokspaceDirPath() {
		IPath path = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		String strPath = path.toOSString();
		return strPath;
	}

	public static IProject[] getJavaProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		return projects;
	}
	
	/**
	 * 获取所有LFW工程
	 * @return
	 */
	public static IProject[] getLfwProjects(){
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<IProject> lfwProjects = new ArrayList<IProject>();
		for (int i = 0; i < projects.length; i++) {
			try {
				if(projects[i].hasNature(CommonConstants.MODULE_NATURE))
					lfwProjects.add(projects[i]);
			} 
			catch (CoreException e) {
				CommonPlugin.getPlugin().logError(e);
				
			}
		}
		return lfwProjects.toArray(new IProject[lfwProjects.size()]);
	}
	
	public static String getLfwProjectCtx(IProject project) {
		return getModuleProperty(project, WEBCONTEXT);
	}
	
	
    public static String getNCHome()
    {
        return getVariableManager().getValueVariable("FIELD_NC_HOME").getValue();
    }
	
    
    private static IStringVariableManager getVariableManager() {
        return VariablesPlugin.getDefault().getStringVariableManager();
    }
	/**
	 * 是否是bcp工程
	 * @param project
	 * @return
	 */
	public static boolean isBCPProject(IProject project){
		try {
			if(project.hasNature(CommonConstants.BCPMODULE_NATURE))
				return true;
		} 
		catch (CoreException e) {
			CommonPlugin.getPlugin().logError(e);
		}
		return false;
	}
	
	/**
	 * 获取所有打开的LFW工程
	 * @return
	 */
	public static IProject[] getOpenedLfwProjects() {
		IProject[] allProjects = getLfwProjects();
			//getJavaProjects();
		ArrayList<IProject> al = new ArrayList<IProject>();
		int count = allProjects == null ? 0 : allProjects.length;
		for (int i = 0; i < count; i++) {
			if (allProjects[i].isOpen()) {
				al.add(allProjects[i]);
			}
		}
		return al.toArray(new IProject[0]);
	}
	
	/**
	 * 获取UAPWEB工程
	 * @return
	 */
	public static IProject getUapWebProject() {
		IProject[] projects = getAllOpenedJavaProjects();
		for (int i = 0, n = projects.length; i < n; i++) {
			String moduleName = getProjectModuleName(projects[i]);
			if (moduleName != null && moduleName.equals(CommonConstants.UAPWEB_MODULE_NAME_57))
				return projects[i];
			if (moduleName != null && moduleName.equals(CommonConstants.UAPWEB_MODULE_NAME))
				return projects[i];
		}
		return null;
	}
	
	/**
	 * 获取所有打开的工程
	 * @return
	 */
	public static IProject[] getAllOpenedJavaProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			//getJavaProjects();
		ArrayList<IProject> al = new ArrayList<IProject>();
		int count = projects == null ? 0 : projects.length;
		for (int i = 0; i < count; i++) {
			if (projects[i].isOpen()) {
				al.add(projects[i]);
			}
		}
		return al.toArray(new IProject[0]);
	}
	/**
	 * 错误提示
	 * @param errMsg
	 */
	public static void showErrDlg(String errMsg) {
		if (errMsg != null) {
			Shell parent = new Shell(Display.getCurrent());
			MessageDialog.openError(parent, "错误提示", errMsg);
		}
	}
	
	/**
	 * 获取某项目的moduleName
	 * @param project
	 * @return
	 */
	public static String getProjectModuleName(IProject project) {
		String module = null;
		File f = project.getLocation().toFile();
		File moduleFile = new File(f, ".module_prj");
		if (moduleFile.exists()) {
			try {
				InputStream is = new FileInputStream(moduleFile);
				Properties prop = new Properties();
				prop.load(is);
				module = prop.getProperty("module.name");
			} catch (Exception e) {
				CommonPlugin.getPlugin().logError(e);
			}
		}
		return module;
	}

	public static String[] getBCPNames(IProject project){
		IFile manifestFile = project.getFile("/manifest.xml");
		File mfile = new File(manifestFile.getLocation().toString());
		try {
			Document doc = XmlUtility.getDocument(mfile); 
			NodeList nodeList =  doc.getElementsByTagName("BusinessComponet");
			String[] names = new String[nodeList.getLength()]; 
			for (int i = 0 ; i < nodeList.getLength(); i++){
				names[i]=((Element)nodeList.item(i)).getAttribute("name");
			}
			return names;
		} catch (Exception e) {
			CommonPlugin.getPlugin().logError(e);
			return null;
		}
	} 
}
