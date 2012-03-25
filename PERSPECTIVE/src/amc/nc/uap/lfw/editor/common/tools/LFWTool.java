/**
 * 
 */
package nc.uap.lfw.editor.common.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.core.combodata.ComboData;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.DatasetRelation;
import nc.uap.lfw.core.data.DatasetRelations;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.FieldRelations;
import nc.uap.lfw.core.data.FieldSet;
import nc.uap.lfw.core.data.LfwParameter;
import nc.uap.lfw.core.event.conf.EventConf;
import nc.uap.lfw.core.event.conf.EventSubmitRule;
import nc.uap.lfw.core.event.conf.JsEventDesc;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.refnode.BaseRefNode;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.core.refnode.RefNode;
import nc.uap.lfw.core.refnode.SelfDefRefNode;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.editor.LFWBrowserEditor;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.perspective.project.ILFWTreeNode;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import nc.uap.lfw.perspective.project.LFWPerspectiveNameConst;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ExternalPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * @author chouhl
 *
 */
@SuppressWarnings("restriction")
public class LFWTool {
	
	/**
	 * v61��ʶ
	 */
	public static final String NEW_VERSION = "NEW_VERSION";
	/**
	 * v61ǰ��ʶ
	 */
	public static final String OLD_VERSION = "OLD_VERSION";
	
	/**
	 * ��ȡ���̱�ʶ
	 * @param project
	 * @return
	 */
	public static String getLFWProjectVersion(IProject project){
		String editorVersion = LfwCommonTool.getModuleProperty(project, "module.editorVersion");
		if(editorVersion == null || editorVersion.trim().length() == 0){
			editorVersion = OLD_VERSION;
		}
		return editorVersion;
	}
	
	/**
	 * ��ȡ��ǰ���̱�ʶ
	 * @return
	 */
	public static String getCurrentLFWProjectVersion(){
		return getLFWProjectVersion(LFWAMCPersTool.getCurrentProject());
	}
	
	/**
	 * ��ȡv61�汾����ֵ
	 * @param constantName
	 * @param version
	 * @return
	 */
	public static String getWEBProjConstantValue(String constantName,String version){
		String enValue = null;
		if(constantName != null){
			String valName = null;
			java.lang.reflect.Field f = null;
			if(!LFWTool.OLD_VERSION.equals(version)){
				valName = constantName + "_EN";
				try{
					f = WEBProjConstants.class.getDeclaredField(valName);
				}catch(Exception e){
					f = null;
				}
			}
			if(f == null){
				valName = constantName;
				try{
					f = WEBProjConstants.class.getDeclaredField(valName);
				}catch(Exception e){
					f = null;
				}
			}
			if(f != null){
				try{
					enValue = (String)f.get(valName);
				}catch(Exception e){
					enValue = "";
				}
			}
		}else{
			enValue = "";
		}
		return enValue;
	}
	
	/**
	 * ��ȡView�ڵ���ʾ����
	 * @param treeItem
	 * @return
	 */
	public static String getViewText(TreeItem treeItem){
		if(treeItem == null){
			treeItem = LFWAMCPersTool.getCurrentTreeItem();
		}
		String text = LFWPerspectiveNameConst.WIDGET_CN;
		if(treeItem != null && treeItem instanceof LFWBasicTreeItem){
			LFWBasicTreeItem bTreeItem = (LFWBasicTreeItem)treeItem;
			if(NEW_VERSION.equals(bTreeItem.getLfwVersion())){
				text = WEBProjConstants.VIEW_SUB;
			}
		}
		return text;
	}
	
	/**
	 * ��ȡ��ǰ�������򿪵ı༭��
	 * @param workbenchPage
	 * @return
	 */
	public static IEditorPart[] getEditors(IWorkbenchPage workbenchPage){
		IEditorReference[] ers = workbenchPage.getEditorReferences();
		IEditorPart[] eps = null;
		if(ers != null){
			eps = new IEditorPart[ers.length];
			for(int i=0;i<ers.length;i++){
				eps[i] = ers[i].getEditor(true);
			}
		}
		return eps;
	}
	
	/**
	 * lfw�������ͼID
	 */
	public static final String ID_LFW_EXPLORER_TREE_VIEW = "nc.uap.lfw.perspective.project.LFWExplorerTreeView";
	/**
	 * portal�������ͼID
	 */
	public static final String ID_PORTAL_EXPLORER_TREE_VIEW = "nc.uap.portal.perspective.PortalExplorerTreeView";
	/**
	 * lfwģ����ͼID
	 */
	public static final String ID_LFW_VIEW_SHEET = "nc.uap.lfw.perspective.LFWViewSheet";
	/**
	 * Web�༭����ʼ����ͼ
	 */
	public static void webBrowserInitViews(){
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		IWorkbenchPage wbp = wbw.getActivePage();
		IViewReference[] vrs = wbp.getViewReferences();
		//�Ƿ�����������ͼ
		boolean isPermitHide = false;
		for(IViewReference vr:vrs){
			if(ID_LFW_EXPLORER_TREE_VIEW.equals(vr.getId()) || ID_PORTAL_EXPLORER_TREE_VIEW.equals(vr.getId())){
				isPermitHide = true;
				break;
			}
		}
		if(isPermitHide){
			for(IViewReference vr:vrs){
				if(ID_LFW_EXPLORER_TREE_VIEW.equals(vr.getId()) || ID_PORTAL_EXPLORER_TREE_VIEW.equals(vr.getId())){
					continue;
				}
				try{
				wbp.hideView(vr);
				}catch(Exception e){
					LfwLogger.error(e.getMessage(), e);
				}
			}
		}	
	}
	
	/**
	 * ��ȡ�ɴ����¼��б�
	 * @param descList
	 * @return
	 */
	public static List<EventConf> getAcceptEventList(List<JsEventDesc> descList){
		List<EventConf> list = new ArrayList<EventConf>();
		if(descList != null && descList.size() > 0){
			for(JsEventDesc desc : descList){
				EventConf event = new EventConf();
				event.setName(desc.getName());
				event.setMethodName("");
				event.setJsEventClaszz(desc.getJsEventClazz());
				List<String> params = desc.getParamList();
				if(params != null && params.size() > 0){
					for(String param : params){
						LfwParameter lfwParam = new LfwParameter();
						lfwParam.setName(param);
						lfwParam.setDesc(desc.getEventClazz());
						event.addParam(lfwParam);
					}
				}
				EventSubmitRule esb = new EventSubmitRule();
				event.setSubmitRule(esb);
				list.add(event);
			}
		}
		return list;
	}
	
	/**
	 * ��ȡ��ǰ����Դ�ļ���Ŀ¼
	 * @return
	 */
	public static List<String> getAllRootPackage() {
		IProject proj = LFWPersTool.getCurrentProject();
		IJavaProject javaProj = JavaCore.create(proj);
		IPackageFragmentRoot[] pfrs = null;
		try {
			pfrs = javaProj.getAllPackageFragmentRoots();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		List<String> rootPackage = new ArrayList<String>();
		// ��ǰ��Ŀ��
		String projectName = LFWPersTool.getCurrentProject().getName();
		if (pfrs != null) {
			for (int i = 0; i < pfrs.length; i++) {
				if (pfrs[i] instanceof JarPackageFragmentRoot || pfrs[i] instanceof ExternalPackageFragmentRoot)
					continue;
				else if (pfrs[i] instanceof PackageFragmentRoot) {
					PackageFragmentRoot root = (PackageFragmentRoot) pfrs[i];
					// ��Ŀ¼������Ŀ��
					String name = root.getPath().toString().substring(1).substring(0, root.getPath().toString().substring(1).indexOf("/"));
					if (projectName.equals(name)) {
						String absPath = root.getPath().removeFirstSegments(1).makeRelative().toString();
						if (!absPath.startsWith("NC_HOME"))
							rootPackage.add(absPath + "/");
					}
				}

			}
		}
		return rootPackage;
	}
	
	/**
	 * ��ȡ��ǰ���������л���LFWBaseEditor�༭������
	 * @return
	 */
	public static List<LFWBaseEditor> getAllLfwEditors(){
		List<LFWBaseEditor> list = new ArrayList<LFWBaseEditor>();
		IWorkbenchWindow wbWindow = WEBProjPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage[] wbPages = wbWindow.getPages();
		if(wbPages != null && wbPages.length > 0){
			for(IWorkbenchPage wbPage : wbPages){
				IEditorReference[] editorRefs = wbPage.getEditorReferences();
				if(editorRefs != null && editorRefs.length > 0){
					for(IEditorReference editorRef : editorRefs){
						if(editorRef != null && editorRef.getEditor(true) instanceof LFWBaseEditor){
							list.add((LFWBaseEditor)editorRef.getEditor(true));
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * ��ȡ��ǰ���������л���LFWBrowserEditor�༭������
	 * @return
	 */
	public static List<LFWBrowserEditor> getAllWebEditors(){
		List<LFWBrowserEditor> list = new ArrayList<LFWBrowserEditor>();
		IWorkbenchWindow wbWindow = WEBProjPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage[] wbPages = wbWindow.getPages();
		if(wbPages != null && wbPages.length > 0){
			for(IWorkbenchPage wbPage : wbPages){
				IEditorReference[] editorRefs = wbPage.getEditorReferences();
				if(editorRefs != null && editorRefs.length > 0){
					for(IEditorReference editorRef : editorRefs){
						if(editorRef != null && editorRef.getEditor(true) instanceof LFWBrowserEditor){
							list.add((LFWBrowserEditor)editorRef.getEditor(true));
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * IDУ��
	 * @param id
	 */
	public static void idCheck(String id){
		if(id != null && id.trim().length() > 0){
			if(id.matches(".{0,}[^0-9_A-Za-z\\-].{0,}")){
				throw new LfwRuntimeException("ID��ֻ��������ĸ(a-zA-Z)������(0-9)���»���(_)�ͷָ���(-)");
			}
		}
	}
	
	/**
	 * ClassУ��
	 * @param clazz
	 */
	public static void clazzCheck(String clazz){
		if(clazz != null){
			String[] strs = clazz.split("\\.");
			//У��
			for(int i=0; i<strs.length; i++){
				javaNameCheck(strs[i]);
			}
		}
	}
	
	/**
	 * ����У��
	 * @param name
	 */
	public static void javaNameCheck(String name){
		if(name != null && name.trim().length() > 0){
			if(name.matches(".{0,}[^0-9_A-Za-z].{0,}")){
				throw new LfwRuntimeException("����������ֻ��������ĸ(a-zA-Z)������(0-9)���»���(_)");
			}
			if(name.matches("^[0-9].{0,}")){
				throw new LfwRuntimeException("��������ֻ������ĸ(a-zA-Z)���»���(_)��ͷ");
			}
		}else{
			throw new LfwRuntimeException("�������Ʋ���Ϊ��");
		}
	}
	
	/**
	 * ��д������
	 * @param className
	 * @return
	 */
	public static String upperClassName(String className){
		if(className != null && className.trim().length() > 0){
			int lastPoint = className.lastIndexOf(".");
			if(lastPoint != -1){
				String first = String.valueOf(className.substring(lastPoint + 1).charAt(0)).toUpperCase();
				className = className.substring(0, lastPoint + 1) + first + className.substring(lastPoint + 1).substring(1);
			}else{
				String first = String.valueOf(className.charAt(0)).toUpperCase();
				className = first + className.substring(1);
			}
		}
		return className;
	}
	
	/**
	 * �����ڵ��������
	 * @param nodeId
	 */
	public static void createNodeCheck(String nodeId, String itemType){
		Tree tree = LFWExplorerTreeView.getLFWExploerTreeView(null).getTreeView().getTree();
		TreeItem[] selTIs = tree.getSelection();
		if (selTIs == null || selTIs.length == 0){
			throw new LfwRuntimeException("��ѡ��һ���ڵ�!");
		}
		TreeItem selTI = selTIs[0];
		File parentFile = ((ILFWTreeNode)selTI).getFile();
		if (parentFile.isFile()){
			throw new LfwRuntimeException("�������ļ����½�Ŀ¼!");
		}
		String projPath = LFWAMCPersTool.getProjectPath();
		//key-id,value-caption
		Map<String, String>[] nodeNameMaps = LFWAMCConnector.getTreeNodeNames(
				new String[] { projPath }, itemType, null);
		if (nodeNameMaps != null && nodeNameMaps.length > 0) {
			Map<String, String> nodeNameMap = nodeNameMaps[0];
			if (nodeNameMap != null && nodeNameMap.size() > 0) {
				Iterator<String> ids = nodeNameMap.keySet().iterator();
				String id = null;
				while (ids.hasNext()) {
					id = ids.next();
					if (id.indexOf("/") != -1){
						id = id.substring(id.indexOf("/"));
					}
					if (id.equalsIgnoreCase(nodeId)) {
						throw new LfwRuntimeException("�Ѿ�����IDΪ" + nodeId + "�Ľڵ�!");
					}
				}
			}
		}
		if(ILFWTreeNode.VIEW.equals(itemType)){
			if(selTI instanceof LFWPageMetaTreeItem){
				LfwWidget widget = ((LFWPageMetaTreeItem) selTI).getPm().getWidget(nodeId);
				if(widget != null){
					throw new LfwRuntimeException("�Ѿ�����IDΪ" + nodeId + "�Ľڵ�!");
				}
			}
		}
	}
	/**
	 * �����ڵ�Controller���ļ����
	 * @param classFilePath
	 * @param classFileName
	 */
	public static void createNodeClassFileCheck(String controllerClazz, String sourcePackage){
		int index = controllerClazz.lastIndexOf(".");
		String packageName = null;
		if(index > 0){
			packageName = controllerClazz.substring(0, index);
		}else{
			packageName = "";
		}
		String projectPath = LFWAMCPersTool.getLFWProjectPath();
		String className = controllerClazz.substring(index + 1);
		String classFilePath = projectPath + File.separator + sourcePackage + packageName.replaceAll("\\.", "/");
		String classFileName = className + ".java";
		
		File javaFile = new File(classFilePath + File.separator + classFileName);
		if(javaFile.exists()){
			String path = null;
			try {
				path = javaFile.getCanonicalPath();
			} catch (IOException e) {
				MainPlugin.getDefault().logError(e);
			}
			if(path == null){
				path = javaFile.getAbsolutePath();
			}
			throw new LfwRuntimeException(path + "���ļ��Ѵ���!");
		}
	}
	
	/**
	 * ��ȡʵ�ʵ���ȫ����
	 * @param controllerClazz
	 * @param sourcePackage
	 * @return
	 */
	public static String getRealWholeClassName(String controllerClazz, String sourcePackage){
		int index = controllerClazz.lastIndexOf(".");
		String packageName = null;
		if(index > 0){
			packageName = controllerClazz.substring(0, index);
		}else{
			packageName = "";
		}
		String projectPath = LFWAMCPersTool.getLFWProjectPath();
		String className = controllerClazz.substring(index + 1);
		String classFilePath = projectPath + File.separator + sourcePackage + packageName.replaceAll("\\.", "/");
		String classFileName = className + ".java";
		
		File javaFile = new File(classFilePath + File.separator + classFileName);
		if(javaFile.exists()){
			String existPath = null;
			try {
				existPath = javaFile.getCanonicalPath();
			} catch (IOException e) {
				MainPlugin.getDefault().logError(e);
			}
			if(existPath != null){
				existPath = existPath.replaceAll("\\\\", "/");
				projectPath = (projectPath + File.separator + sourcePackage).replaceAll("\\\\", "/");
				controllerClazz = existPath.substring(existPath.indexOf(projectPath) + projectPath.length()).replaceAll("/", ".");
				controllerClazz = controllerClazz.substring(0, controllerClazz.lastIndexOf("."));
				return controllerClazz;
			}
		}else{
			File javaDir = new File(classFilePath);
			if(javaDir.exists()){
				String existPath = null;
				try {
					existPath = javaDir.getCanonicalPath();
				} catch (IOException e) {
					MainPlugin.getDefault().logError(e);
				}
				if(existPath != null){
					existPath = existPath.replaceAll("\\\\", "/");
					projectPath = (projectPath + File.separator + sourcePackage).replaceAll("\\\\", "/");
					controllerClazz = existPath.substring(existPath.indexOf(projectPath) + projectPath.length()).replaceAll("/", ".");
					controllerClazz = controllerClazz + "." + className;
					return controllerClazz;
				}
			}
		}
		return controllerClazz;
	}
	
	/**
	 * ��ȡ�Ѵ��ڵ���ȫ����
	 * @param controllerClazz
	 * @param sourcePackage
	 * @return
	 */
	public static String getExistWholeClassName(String controllerClazz, String sourcePackage){
		int index = controllerClazz.lastIndexOf(".");
		String packageName = null;
		if(index > 0){
			packageName = controllerClazz.substring(0, index);
		}else{
			packageName = "";
		}
		String projectPath = LFWAMCPersTool.getLFWProjectPath();
		String className = controllerClazz.substring(index + 1);
		String classFilePath = projectPath + File.separator + sourcePackage + packageName.replaceAll("\\.", "/");
		String classFileName = className + ".java";
		
		File javaFile = new File(classFilePath + File.separator + classFileName);
		if(javaFile.exists()){
			String existPath = null;
			try {
				existPath = javaFile.getCanonicalPath();
			} catch (IOException e) {
				MainPlugin.getDefault().logError(e);
			}
			if(existPath != null){
				existPath = existPath.replaceAll("\\\\", "/");
				projectPath = (projectPath + File.separator + sourcePackage).replaceAll("\\\\", "/");
				controllerClazz = existPath.substring(existPath.indexOf(projectPath) + projectPath.length()).replaceAll("/", ".");
				controllerClazz = controllerClazz.substring(0, controllerClazz.lastIndexOf("."));
			}
		}
		return controllerClazz;
	}
	
	private static final int BUFFER = 1024 * 256;
	/**
	 * ����Firefox����
	 */
	public static void checkFirefoxEnvironment(){
		File f = new File("./xulrunner");
		System.setProperty("org.eclipse.swt.browser.XULRunnerPath",f.getAbsolutePath()); 
		if(!f.exists()){
			InputStream input = null;
			OutputStream out = null;
			File zipFile = new File("./xui.zip");
			try {
				input = Thread.currentThread().getContextClassLoader().getResourceAsStream("nc/uap/lfw/xulrunner/xul.zip");
				out = new FileOutputStream(zipFile);
				byte[] b = new byte[BUFFER];
				int len = 0;
				while ((len = input.read(b)) != -1) {
					out.write(b, 0, len);
				}
				unzipFile(zipFile.getAbsolutePath(), new File("."));
				System.out.println(f.exists());
			}catch (Exception e) {
				MainPlugin.getDefault().logError(e.getMessage(), e);
			}finally{
				if (out != null)
					try {
						out.close();
					} catch (Exception e) {
						MainPlugin.getDefault().logError(e.getMessage(), e);
					}
				if (input != null)
					try {
						input.close();
					} catch (Exception e) {
						MainPlugin.getDefault().logError(e.getMessage(), e);
					}
			}
			try {
				//ע��XULRunner
				Runtime.getRuntime().exec(f.getAbsolutePath() + " --register-global");
			} catch (Exception e) {
				MainPlugin.getDefault().logError(e.getMessage(), e);
			}
		}
		MainPlugin.getDefault().logInfo("xulrunner_path:" + f.getAbsolutePath());
	}
	
	/**
	 * ��ѹZip�ļ�
	 * @param src
	 * @param des
	 * @throws Exception
	 */
	public static void unzipFile(String src, File des) throws Exception {
		ZipFile file = null;
		FileOutputStream out = null;
		InputStream input = null;
		try {
			file = new ZipFile(src);
			Enumeration<?> en = file.entries();
			while (en.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) en.nextElement();
				File target = null;
				if (entry.isDirectory()) {// �ļ���
					target = new File(des, entry.getName());
					if (!target.exists()) {
						target.mkdirs();
					}
				} else {// �ļ�
					String path = entry.getName();
					int pos = path.lastIndexOf('/');
					if (pos != -1) {
						path = path.substring(0, pos + 1);
						target = new File(des, path);
						if (!target.exists()) {
							target.mkdirs();
						}
					}
					
					target = new File(des, entry.getName());

					out = new FileOutputStream(target);
					input = file.getInputStream(entry);

					byte[] b = new byte[BUFFER];
					int len = 0;
					while ((len = input.read(b)) != -1) {
						out.write(b, 0, len);
					}
					if (out != null)
						out.close();
					if (input != null)
						input.close();
				}
				if (target != null) {
					target.setLastModified(entry.getTime());
				}
			}
		} finally {
			try {
				if (file != null)
					file.close();
				if (out != null)
					out.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * �ϲ�Widget
	 * @param widget
	 * @param newWidget
	 */
	public static void mergeWidget(LfwWidget widget, LfwWidget newWidget){
		if(widget == null || newWidget == null){
			return;
		}
		ViewModels vm = widget.getViewModels();
		ViewModels newVm = newWidget.getViewModels();
		//���ݼ�
		Dataset[] datasets = newVm.getDatasets();
		for(Dataset newDs : datasets){
			Dataset ds = vm.getDataset(newDs.getId());
			if(ds == null){
				vm.addDataset(newDs);
			}else{
				//Dataset ����
				mergeObject(ds, newDs, ds.getClass(), Dataset.class.getName());
				//Dataset �����ֶ�
				FieldRelations fieldRel = ds.getFieldRelations();
				FieldRelations newFieldRel = newDs.getFieldRelations();
				FieldRelation[] newFrs = newFieldRel.getFieldRelations();
				if(newFrs != null && newFrs.length > 0){
					for(FieldRelation newFr : newFrs){
						FieldRelation fr = fieldRel.getFieldRelation(newFr.getId());
						if(fr == null){
							fieldRel.addFieldRelation(newFr);
						}else{
							mergeObject(fr, newFr, fr.getClass(), FieldRelation.class.getName());
						}
					}
				}
				//Dataset �ֶ�
				FieldSet fieldSet = ds.getFieldSet();
				FieldSet newFieldSet = newDs.getFieldSet();
				Field[] newFds = newFieldSet.getFields();
				if(newFds != null && newFds.length > 0){
					for(Field newFd : newFds){
						Field fd = fieldSet.getField(newFd.getId());
						if(fd == null){
							fieldSet.addField(newFd);
						}else{
							mergeObject(fd, newFd, fd.getClass(), Field.class.getName());
						}
					}
				}
			}
		}
		//���ݼ���ϵ
		DatasetRelations dsRels = vm.getDsrelations();
		DatasetRelations newDsRels = newVm.getDsrelations();
		if(newDsRels != null){
			DatasetRelation[] newDrs = newDsRels.getDsRelations();
			if(newDrs != null && newDrs.length > 0){
				if(dsRels == null){
					dsRels = new DatasetRelations();
				}
				// DatasetRelation ����
				for(DatasetRelation newDr : newDrs){
					DatasetRelation dr = dsRels.getDsRelation(newDr.getMasterDataset(), newDr.getDetailDataset());
					if(dr == null){
						dsRels.addDsRelation(newDr);
					}else{
						mergeObject(dr, newDr, dr.getClass(), DatasetRelation.class.getName());
					}
				}
				vm.setDsrelations(dsRels);
			}
		}
		
		//����
		IRefNode[] newRns = newVm.getRefNodes();
		if(newRns != null && newRns.length > 0){
			for(IRefNode newRn : newRns){
				IRefNode rn = vm.getRefNode(newRn.getId());
				if(rn == null){
					vm.addRefNode(newRn);
				}else{
					if(rn instanceof SelfDefRefNode){
						mergeObject(rn, newRn, rn.getClass(), SelfDefRefNode.class.getName());
					}else if(rn instanceof RefNode){
						mergeObject(rn, newRn, rn.getClass(), RefNode.class.getName());
					}else if(rn instanceof BaseRefNode){
						mergeObject(rn, newRn, rn.getClass(), BaseRefNode.class.getName());
					}
				}
			}
		}
		//�������ݼ�
		ComboData[] newCds = newVm.getComboDatas();
		if(newCds != null && newCds.length > 0){
			for(ComboData newCd : newCds){
				ComboData cd = vm.getComboData(newCd.getId());
				if(cd == null){
					vm.addComboData(newCd);
				}else{
					mergeObject(cd, newCd, cd.getClass(), ComboData.class.getName());
				}
			}
		}
	}
	
	/**
	 * �ϲ�����ֵ
	 * @param obj
	 * @param newObj
	 * @param clazz
	 * @param className
	 */
	private static void mergeObject(Object obj, Object newObj, Class<?> clazz, String className){
		do{
			java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
			if(fields != null && fields.length > 0){
				for(java.lang.reflect.Field field : fields){
					try{
						if(field.get(obj) == null && field.get(newObj) != null){
							field.set(obj, field.get(newObj));
						}else if((Integer)field.get(obj) == -1 && (Integer)field.get(newObj) != -1){
							field.set(obj, field.get(newObj));
						}
					}catch(Exception e){}
				}
			}
			if(clazz.getName().equals(className)){
				break;
			}else if(clazz.getName().equals("java.lang.Object")){
				break;
			}
			clazz = clazz.getSuperclass();
		}while(true);
	}
	
	/**
	 * �ϲ�UIMeta�¼�������UIElement�¼���
	 * @param oriUIMeta
	 * @param elementMap
	 */
	public static void mergeUIMetaEvent(UIMeta oriUIMeta, Map<String, UIElement> elementMap){
		if(oriUIMeta != null && elementMap != null){
			UIElement element = null;
			UIElement oriElement = null;
			Iterator<String> keys = elementMap.keySet().iterator();
			while(keys.hasNext()){
				element = elementMap.get(keys.next());
				if(element != null){
					oriElement = oriUIMeta.findChildById((String)element.getAttribute(UIElement.ID));
					if(oriElement != null){
						EventConf[] events = element.getEventConfs();
						if(events != null){
							oriElement.removeAllEventConf();
							for(EventConf event : events){
								oriElement.addEventConf(event);
							}
						}
					}
				}
			}
		}
	}
	
}
