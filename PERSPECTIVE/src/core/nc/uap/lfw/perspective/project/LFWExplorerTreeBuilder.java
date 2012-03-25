package nc.uap.lfw.perspective.project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.lfw.design.view.LFWConnector;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.lfw.editor.pagemeta.LFWPageMetaTreeItem;
import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.aciton.NCConnector;
import nc.uap.lfw.application.LFWApplicationTreeItem;
import nc.uap.lfw.common.LFWUtility;
import nc.uap.lfw.core.WEBProjConstants;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.refnode.IRefNode;
import nc.uap.lfw.core.uimodel.Application;
import nc.uap.lfw.design.itf.BCPManifest;
import nc.uap.lfw.design.itf.BusinessComponent;
import nc.uap.lfw.design.view.LFWAMCConnector;
import nc.uap.lfw.editor.common.editor.LFWPVTreeItem;
import nc.uap.lfw.editor.common.tools.LFWTool;
import nc.uap.lfw.model.LFWModelTreeItem;
import nc.uap.lfw.perspective.ref.RefDatasetData;
import nc.uap.lfw.perspective.webcomponent.LFWBasicTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWBusinessCompnentTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDSTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWDirtoryTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWProjectTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWRefNodeTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWVirtualDirTreeItem;
import nc.uap.lfw.perspective.webcomponent.LFWWidgetTreeItem;
import nc.uap.lfw.publicview.LFWPublicViewTreeItem;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 建立树的builder
 * 
 * @author zhangxya
 * 
 */
public class LFWExplorerTreeBuilder {

	private static LFWExplorerTreeBuilder instance = null;

	public LFWExplorerTreeBuilder() {
		super();
	}

	public static LFWExplorerTreeBuilder getInstance() {
		synchronized (LFWExplorerTreeBuilder.class) {
			if (instance == null)
				instance = new LFWExplorerTreeBuilder();
		}
		return instance;
	}
	/**
	 * 构造树结构
	 * 
	 * @param tree
	 */
	public void buildTree(Tree tree) {
		tree.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {

			}

			public void mouseDown(MouseEvent e) {
				Tree t = (Tree) e.getSource();
				TreeItem[] items = t.getSelection();
				if (items != null && items.length > 0) {
					if (items[0] instanceof LFWProjectTreeItem) {
						LFWProjectTreeItem lfwItem = (LFWProjectTreeItem) items[0];
						IProject project = lfwItem.getProjectModel()
								.getJavaProject();
						LFWPersTool.setCurrentProject(project);
						String projectPath = project.getLocation().toString();
						LFWPersTool.setProjectPath(projectPath);
						LFWPersTool.setProjectWithBcpPath(LFWPersTool
								.getBcpPath(items[0]));
					} else if (items[0] instanceof LFWDirtoryTreeItem) {
						LFWDirtoryTreeItem lfwItem = (LFWDirtoryTreeItem) items[0];
						IProject project = getFileOwnProject(t, lfwItem
								.getFile());
						LFWPersTool.setCurrentProject(project);
						String projectPath = project.getLocation().toString();
						LFWPersTool.setProjectPath(projectPath);
						LFWPersTool.setProjectWithBcpPath(LFWPersTool
								.getBcpPath(items[0]));
					} 
//					else if (items[0] instanceof LFWPageFlowTreeItem) {
//						LFWPageFlowTreeItem lfwItem = (LFWPageFlowTreeItem) items[0];
//						IProject project = getFileOwnProject(t, lfwItem
//								.getFile());
//						LFWPersTool.setCurrentProject(project);
//						String projectPath = project.getLocation().toString();
//						LFWPersTool.setProjectPath(projectPath);
//						LFWPersTool.setProjectWithBcpPath(LFWPersTool
//								.getBcpPath(items[0]));
//					} 
					else if (items[0] instanceof LFWBusinessCompnentTreeItem) {
						LFWBusinessCompnentTreeItem lfwItem = (LFWBusinessCompnentTreeItem) items[0];
						IProject project = getFileOwnProject(t, lfwItem
								.getFile());
						LFWPersTool.setCurrentProject(project);
						LFWPersTool.setProjectWithBcpPath(LFWPersTool
								.getBcpPath(items[0]));
						// String projectPath = project.getLocation().toString()
						// + "/" +
						// lfwItem.getText().substring(lfwItem.getText().indexOf(BUSINESSCOMPONENT)
						// + BUSINESSCOMPONENT.length() + 1);
						// LFWPersTool.setProjectPath(projectPath);
					} else {
						otherMouseDown(t, items[0]);
					}
				}
			}

			public void mouseUp(MouseEvent e) {

			}

		});
		buildLFWProTree(tree);
		buildBcpProjTree(tree);
		buildPublicViewTree(tree);
	}

	protected void otherMouseDown(Tree tree, TreeItem item) {

	}

	/**
	 * 创建PublicView树
	 * @param tree
	 */
	private void buildPublicViewTree(Tree tree){
		Map<String, String> ctxMap = new HashMap<String, String>();
		
		IProject[] projects = getOpenedLFWNotBCPJavaProjects();
		if(projects != null && projects.length > 0){
			for(IProject project : projects){
				ctxMap.put(LFWUtility.getContextFromResource(project), "/" + LFWUtility.getContextFromResource(project));
			}
		}
		
		IProject[] bcpProjects = getOpenedBcpJavaProjects();
		if(bcpProjects != null && bcpProjects.length > 0){
			for(IProject project : bcpProjects){
				ctxMap.put(LFWUtility.getContextFromResource(project), "/" + LFWUtility.getContextFromResource(project));
			}
		}
		
		if(ctxMap.size() > 0){
			LFWPVTreeItem basicTreeItem = new LFWPVTreeItem(tree, SWT.NONE);
			basicTreeItem.setId("PublicViews");
			basicTreeItem.setItemName("PublicViews");
			basicTreeItem.setText("PublicViews");
			basicTreeItem.setData("PublicViews");
			
			Iterator<String> keys = ctxMap.keySet().iterator();
			String key = null;
			while(keys.hasNext()){
				key = keys.next();
				if(key == null)
					continue;
				try{
					Map<String, Map<String, LfwWidget>> allWidget = RefDatasetData.getPoolWidgets(ctxMap.get(key));
					if(allWidget != null){
						Map<String, LfwWidget> widgetMap = allWidget.get(ctxMap.get(key));
						if(widgetMap != null){
							LFWPVTreeItem ctxTreeItem = new LFWPVTreeItem(basicTreeItem, SWT.NONE, 1);
							ctxTreeItem.setId(key);
							ctxTreeItem.setItemName(key);
							ctxTreeItem.setText(key);
							ctxTreeItem.setData(key);
							Iterator<LfwWidget> widgets = widgetMap.values().iterator();
							while (widgets.hasNext()) {
								LfwWidget widget = widgets.next();
								LFWPVTreeItem widgetTreeItem = new LFWPVTreeItem(ctxTreeItem, SWT.NONE, 2);
								widgetTreeItem.setId(widget.getId());
								widgetTreeItem.setItemName(widget.getId());
								widgetTreeItem.setText("[" + WEBProjConstants.PUBLIC_VIEW_SUB + "] " + widget.getId());
								widgetTreeItem.setData(widget);
							}
						}
					}
				}catch(Throwable e){
					MainPlugin.getDefault().logError(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 创建lfw工程的树
	 * 
	 * @param tree
	 */
	private void buildLFWProTree(Tree tree) {
		IProject[] projects = getOpenedLFWNotBCPJavaProjects();
		// 将项目按照名称排序
		for (int i = 0; i < projects.length; i++) {
			for (int j = projects.length - 1; j > i; j--) {
				if (projects[i].getName().compareTo(projects[j].getName()) > 0) {
					IProject temp = projects[i];
					projects[i] = projects[j];
					projects[j] = temp;
				}
			}
		}
		String[] projPaths = new String[projects.length];
		for (int i = 0; i < projPaths.length; i++) {
			projPaths[i] = projects[i].getLocation().toString();
		}

		Map<String, String>[] pageNames = null;
//		PageFlow[][] pageFlows = null;
		Map<String, String>[] appNames = null;
		Map<String, String>[] modelNames = null;
		Map<String, String>[] windowNames = null;
		Map<String, String>[] publicViewNames = null;
		try {
			pageNames = NCConnector.getPageNames(projPaths);
//			pageFlows = NCConnector.getPageFlows(projPaths);
			appNames = LFWAMCConnector.getTreeNodeNames(projPaths,
					ILFWTreeNode.APPLICATION, WEBProjConstants.AMC_APPLICATION_PATH);
			modelNames = LFWAMCConnector.getTreeNodeNames(projPaths,
					ILFWTreeNode.MODEL, WEBProjConstants.AMC_MODEL_PATH);
			windowNames = LFWAMCConnector.getTreeNodeNames(projPaths,
					ILFWTreeNode.WINDOW, WEBProjConstants.AMC_WINDOW_PATH);
			publicViewNames = LFWAMCConnector.getTreeNodeNames(projPaths,
					ILFWTreeNode.PUBLIC_VIEW, WEBProjConstants.AMC_PUBLIC_VIEW_PATH);
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
		int count = projects == null ? 0 : projects.length;
		for (int i = 0; i < count; i++) {
			
			IProject project = projects[i];
			String projPath = projPaths[i];
			
			Map<String, String> pageName = null;
			if (pageNames != null) {
				pageName = pageNames[i];
			}
			
			LFWProjectModel model = new LFWProjectModel(project);
			LFWProjectTreeItem ti = new LFWProjectTreeItem(tree, model);
			buildAppendTree(ti, project);
			Map<String, String> appName = null;
			if (appNames != null) {
				appName = appNames[i];
			}
			Map<String, String> windowName = null;
			if (windowNames != null) {
				windowName = windowNames[i];
			}
			Map<String, String> modelName = null;
			if (modelNames != null) {
				modelName = modelNames[i];
			}
			Map<String, String> publicViewName = null;
			if (publicViewNames != null) {
				publicViewName = publicViewNames[i];
			}
			initCommonProjectTree(project, projPath, pageName, ti, appName, modelName, windowName, publicViewName);
			
		}
		
	}

	protected IProject[] getOpenedLFWNotBCPJavaProjects() {
		return LFWPersTool.getOpenedLFWNotBCPJavaProjects();
	}

	/**
	 * 创建bcp工程的树结构
	 * 
	 * @param tree
	 */

	@SuppressWarnings("unchecked")
	private void buildBcpProjTree(Tree tree) {
		try {
			// 得到所有的bcp工程
			IProject[] projects = getOpenedBcpJavaProjects();
			if (projects == null || projects.length == 0)
				return;
			// 将项目按照名称排序
			for (int i = 0; i < projects.length; i++) {
				for (int j = projects.length - 1; j > i; j--) {
					if (projects[i].getName().compareTo(projects[j].getName()) > 0) {
						IProject temp = projects[i];
						projects[i] = projects[j];
						projects[j] = temp;
					}
				}
			}

			List<String> projectPathList = new ArrayList<String>();
			for (int i = 0; i < projects.length; i++) {
				String projPath = projects[i].getLocation().toString();
				MainPlugin.getDefault().logInfo("从" + projPath + "下查询bcp配置");
				BCPManifest manifest = NCConnector.getBCPManifest(projPath
						+ "/manifest.xml");
				if (manifest == null) {
					MainPlugin.getDefault().logError("BCP解析异常,路径:" + projPath);
					continue;
				}
				List componentsList = manifest.getBusinessComponentList();
				for (int j = 0; j < componentsList.size(); j++) {
					BusinessComponent businessC = (BusinessComponent) componentsList
							.get(j);
					String busiComName = businessC.getName();
					projectPathList.add(projPath + "/" + busiComName);
				}
			}

			if (projectPathList.size() == 0)
				return;

			String[] projPaths = (String[]) projectPathList
					.toArray(new String[0]);
			Map<String, String>[] pageNames = null;
//			PageFlow[][] pageFlows = null;

			pageNames = NCConnector.getPageNames(projPaths);
//			pageFlows = NCConnector.getPageFlows(projPaths);

			Map<String, String>[] appNames = LFWAMCConnector.getTreeNodeNames(
					projPaths, ILFWTreeNode.APPLICATION, WEBProjConstants.AMC_APPLICATION_PATH);
			Map<String, String>[] modelNames = LFWAMCConnector.getTreeNodeNames(
					projPaths, ILFWTreeNode.MODEL, WEBProjConstants.AMC_MODEL_PATH);
			Map<String, String>[] windowNames = LFWAMCConnector.getTreeNodeNames(
					projPaths, ILFWTreeNode.WINDOW, WEBProjConstants.AMC_WINDOW_PATH);
			Map<String, String>[] publicViewNames = LFWAMCConnector
					.getTreeNodeNames(projPaths, ILFWTreeNode.PUBLIC_VIEW, WEBProjConstants.AMC_PUBLIC_VIEW_PATH);
			int temp = 0;
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				LFWProjectModel model = new LFWProjectModel(project);
				LFWProjectTreeItem ti = new LFWProjectTreeItem(tree, model);
				String projPath = project.getLocation().toString();
				MainPlugin.getDefault().logInfo("从" + projPath + "下查询bcp配置");
				BCPManifest manifest = NCConnector.getBCPManifest(projPath
						+ "/manifest.xml");
				if (manifest == null) {
					MainPlugin.getDefault().logError("BCP解析异常,路径:" + projPath);
					continue;
				}
				List componentsList = manifest.getBusinessComponentList();
				for (int j = 0; j < componentsList.size(); j++) {
					BusinessComponent businessC = (BusinessComponent) componentsList
							.get(j);
					String busiComName = businessC.getName();
					Map<String, String> pageName = null;
//					PageFlow[] pageflow = null;
					// 组件的项目
					File file = ti.getFile();
					File busiFile = new File(file, busiComName);
					if (pageNames != null)
						pageName = pageNames[j + temp];
//					if (pageFlows != null)
//						pageflow = pageFlows[j];
					LFWBusinessCompnentTreeItem busiComTreeItem = new LFWBusinessCompnentTreeItem(
							ti, busiFile, WEBProjConstants.BUSINESSCOMPONENT
									+ "[" + busiComName + "]");
					
					String busiCompPath = projPath + "/" + busiComName;
					
//					buildAppendTree(ti, project);
					buildAppendTree(busiComTreeItem, project);
					
					Map<String, String> appName = null;
					if (appNames != null) {
						appName = appNames[j + temp];
					}
					Map<String, String> modelName = null;
					if (modelNames != null) {
						modelName = modelNames[j + temp];
					}
					Map<String, String> windowName = null;
					if (windowNames != null) {
						windowName = windowNames[j + temp];
					}
					Map<String, String> publicViewName = null;
					if (publicViewNames != null) {
						publicViewName = publicViewNames[j + temp];
					}
					
					initCommonProjectTree(project, busiCompPath, pageName,
							busiComTreeItem, appName, modelName, windowName,
							publicViewName);
				}
				temp += componentsList.size();
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}

	private void initCommonProjectTree(IProject project, String projPath,
			Map<String, String> pageName,
			LFWBasicTreeItem busiComTreeItem,
			Map<String, String> appName, Map<String, String> modelName,
			Map<String, String> windowName, Map<String, String> publicViewName) {
		if (!LFWTool.NEW_VERSION.equals(LFWTool
				.getLFWProjectVersion(project))) {
			initProjectTree(busiComTreeItem, projPath, pageName, 
					project);
		}
		if (!LFWTool.OLD_VERSION.equals(LFWTool
				.getLFWProjectVersion(project))) {
			initAMCProjectTree(busiComTreeItem, projPath, pageName, appName,
					modelName, windowName, publicViewName,
					project);
		}
	}
	
	protected IProject[] getOpenedBcpJavaProjects() {
		return LFWPersTool.getOpenedBcpJavaProjects();
	}

	private void initProjectTree(LFWBasicTreeItem projectRoot,
			String projectPath, Map<String, String> pageNames, IProject project) {
		File file = projectRoot.getFile();
		// 处理其他逻辑
		String dirNodeName = WEBProjConstants.NODEGROUP_CN;
		File nodefile = new File(file, WEBProjConstants.BASE_NODEGROUP_PATH);

		// 初始化节点组下的文件
		LFWDirtoryTreeItem nodegroup = new LFWDirtoryTreeItem(projectRoot,
				nodefile, dirNodeName);
		nodegroup.setType(LFWDirtoryTreeItem.PARENT_NODE_FOLDER);
		initNodeSubTree(nodegroup, projectPath, pageNames);

		// 初始化页面流
		File pageflowFile = new File(file, WEBProjConstants.PAGEFLOW);
		LFWDirtoryTreeItem pageflowNode = new LFWDirtoryTreeItem(projectRoot,
				pageflowFile);
		pageflowNode.setType(LFWDirtoryTreeItem.PAGEFLOW_FOLDER);
//		initPageFlowSubTree(pageflowNode, projectPath);

		// 初始化公共数据集
		File dsFile = new File(file, WEBProjConstants.PUBLIC_DATASET);
		LFWDirtoryTreeItem dsnodegroup = new LFWDirtoryTreeItem(projectRoot,
				dsFile);
		dsnodegroup.setType(LFWDirtoryTreeItem.PARENT_PUB_DS_FOLDER);
		initDsSubTree(dsnodegroup, dsFile, projectPath);

		// 初始化参照节点下的文件
		File refNodeFile = new File(file, WEBProjConstants.PUBLIC_REFNODE);
		LFWDirtoryTreeItem refnodegroup = new LFWDirtoryTreeItem(projectRoot,
				refNodeFile);
		refnodegroup.setType(LFWDirtoryTreeItem.PARENT_PUB_REF_FOLDER);
		initRefNodeSubTree(refnodegroup, refNodeFile, projectPath);

		// 公共片段的树加载
		File widgetFile = new File(file, WEBProjConstants.PUBLIC_WIDGET);
		LFWDirtoryTreeItem widgetNodeGroup = new LFWDirtoryTreeItem(
				projectRoot, widgetFile);
		widgetNodeGroup.setType(LFWDirtoryTreeItem.PARENT_PUB_WIDGET_FOLDER);
		initPubWidgetSubTree(widgetNodeGroup, widgetFile, project);
	}

	private void initAMCProjectTree(LFWBasicTreeItem projectRoot,
			String projectPath, Map<String, String> pageNames,
			Map<String, String> appNames, Map<String, String> modelNames,
			Map<String, String> windowNames,
			Map<String, String> publicViewName, IProject project) {
		
		new AMCThread().start();
		
		File file = projectRoot.getFile();
		// 初始化Applications节点
		File applicationFile = new File(file,
				WEBProjConstants.AMC_APPLICATION_PATH);
		LFWDirtoryTreeItem applicationNode = new LFWDirtoryTreeItem(
				projectRoot, applicationFile, WEBProjConstants.APPLICATION);
		applicationNode.setType(LFWDirtoryTreeItem.APPLICATION_FOLDER);
		applicationNode.setLfwVersion(LFWTool.NEW_VERSION);
		refreshAMCNodeSubTree(applicationNode, projectPath, appNames,
				WEBProjConstants.AMC_APPLICATION_PATH,
				LFWDirtoryTreeItem.APPLICATION,
				WEBProjConstants.AMC_APPLICATION_FILENAME, project);

		// 初始化Modules节点
		File modelFile = new File(file, WEBProjConstants.AMC_MODEL_PATH);
		LFWDirtoryTreeItem modelNode = new LFWDirtoryTreeItem(projectRoot,
				modelFile, WEBProjConstants.MODEL);
		modelNode.setType(LFWDirtoryTreeItem.MODEL_FOLDER);
		modelNode.setLfwVersion(LFWTool.NEW_VERSION);
		refreshAMCNodeSubTree(modelNode, projectPath, modelNames,
				WEBProjConstants.AMC_MODEL_PATH, LFWDirtoryTreeItem.MODEL,
				WEBProjConstants.AMC_MODEL_FILENAME, project);

		// 初始化Windows节点
		File windowFile = new File(file, WEBProjConstants.AMC_WINDOW_PATH);
		LFWDirtoryTreeItem windowNode = new LFWDirtoryTreeItem(projectRoot,
				windowFile, WEBProjConstants.WINDOW);
		windowNode.setType(LFWDirtoryTreeItem.WINDOW_FOLDER);
		windowNode.setLfwVersion(LFWTool.NEW_VERSION);
		refreshAMCNodeSubTree(windowNode, projectPath, pageNames,
				WEBProjConstants.AMC_WINDOW_PATH, LFWDirtoryTreeItem.WINDOW,
				WEBProjConstants.AMC_WINDOW_FILENAME, project);
		// initNodeSubTree(windowNode, projectPath, pageNames);

		// 初始化PublicViews节点
		File publicViewFile = new File(file,
				WEBProjConstants.AMC_PUBLIC_VIEW_PATH);
		LFWDirtoryTreeItem publicViewNode = new LFWDirtoryTreeItem(projectRoot,
				publicViewFile, WEBProjConstants.PUBLIC_VIEW);
		publicViewNode.setType(LFWDirtoryTreeItem.PUBLIC_VIEW_FOLDER);
		publicViewNode.setLfwVersion(LFWTool.NEW_VERSION);
//		 refreshAMCNodeSubTree(publicViewNode, projectPath, publicViewName,
//		 WEBProjConstants.AMC_PUBLIC_VIEW_PATH,
//		 LFWDirtoryTreeItem.PUBLIC_VIEW,
//		 WEBProjConstants.AMC_PUBLIC_VIEW_FILENAME, project);
		initPublicViewSubTree(publicViewNode, publicViewFile, project);
	}

	/**
	 * 初始化PublicView
	 * @param parent
	 * @param file
	 * @param project
	 */
	public void initPublicViewSubTree(LFWDirtoryTreeItem parent, File file, IProject project) {
		try {
			String ctx = LFWUtility.getContextFromResource(project);
			Map<String, Map<String, LfwWidget>> allWidget = RefDatasetData.getPoolWidgets("/" + ctx);
			Map<String, LfwWidget> widgetMap = allWidget.get("/" + ctx);
			LFWWidgetTreeItem pubWidgetTreeItem = null;
			if (widgetMap != null) {
				String parentFilePath = file.getPath();
				Iterator<LfwWidget> it = widgetMap.values().iterator();
				while (it.hasNext()) {
					LfwWidget widget = it.next();
					File widgetFile = new File(parentFilePath + File.separator + widget.getId());
					if(widgetFile.exists()){
						pubWidgetTreeItem = new LFWWidgetTreeItem(parent, widgetFile, widget, "[" + WEBProjConstants.PUBLIC_VIEW_SUB + "] " + widgetFile.getName());
						pubWidgetTreeItem.setType(LFWDirtoryTreeItem.POOLWIDGETFOLDER);
						pubWidgetTreeItem.setLfwVersion(parent.getLfwVersion());
					}
				}
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}
	/**
	 * 初始化公共片段下的节点
	 * 
	 * @param parent
	 * @param file
	 * @param projectPath
	 */
	public void initPubWidgetSubTree(LFWDirtoryTreeItem parent, File file,
			IProject project) {
		try {
			String ctx = LFWUtility.getContextFromResource(project);
			Map<String, Map<String, LfwWidget>> allWidget = RefDatasetData
					.getPoolWidgets("/" + ctx);
			Map<String, LfwWidget> widgetMap = allWidget.get("/" + ctx);
			LFWWidgetTreeItem pubWidgetTreeItem = null;
			if (widgetMap != null) {
				String msg = LFWPerspectiveNameConst.WIDGET_CN;
				if (LFWTool.NEW_VERSION.equals(parent.getLfwVersion())) {
					msg = WEBProjConstants.PUBLIC_VIEW_SUB;
				}
				String parentFilePath = file.getPath().replace(
						WEBProjConstants.PUBLIC_WIDGET,
						"web\\pagemeta\\public\\widgetpool");
				Iterator<LfwWidget> it = widgetMap.values().iterator();
				while (it.hasNext()) {
					LfwWidget widget = it.next();
					File widgetFile = new File(parentFilePath + "/"
							+ widget.getId());
					pubWidgetTreeItem = new LFWWidgetTreeItem(parent,
							widgetFile, widget, "[" + msg + "] "
									+ widgetFile.getName());
					pubWidgetTreeItem
							.setType(LFWDirtoryTreeItem.POOLWIDGETFOLDER);
					pubWidgetTreeItem.setLfwVersion(parent.getLfwVersion());
				}
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}

	/**
	 * 初始化公共数据集下的节点
	 * 
	 * @param parent
	 * @param project
	 */
	public void initDsSubTree(LFWDirtoryTreeItem parent, File file,
			String projectPath) {
		Map<File, LFWDirtoryTreeItem> foldermap = new HashMap<File, LFWDirtoryTreeItem>();
		try {
			File rootFile = file;
			Map<String, Dataset> dsMap = LFWConnector.getDataset(projectPath);
			if (dsMap != null) {
				Iterator<Dataset> it = dsMap.values().iterator();
				LFWDirtoryTreeItem orinalparent = parent;
				while (it.hasNext()) {
					Dataset ds = it.next();
					String id = ds.getId();
					if (id.indexOf(".") == -1) {
						new LFWDSTreeItem(parent, ds, ds.getId());
					} else {
						String foldername = id.substring(0, id.indexOf("."));
						String leftfolder = id.substring(id.indexOf(".") + 1);
						while (leftfolder != null) {
							File newfile = new File(file, foldername);
							LFWDirtoryTreeItem treeItem = (LFWDirtoryTreeItem) foldermap
									.get(newfile);
							file = newfile;
							if (treeItem == null) {
								LFWDirtoryTreeItem newTreeItem = new LFWDirtoryTreeItem(
										parent, newfile);
								newTreeItem
										.setType(LFWDirtoryTreeItem.POOLDSFOLDER);
								parent = newTreeItem;
								foldermap.put(newfile, newTreeItem);
								if (leftfolder.indexOf(".") != -1) {
									foldername = leftfolder.substring(0,
											leftfolder.indexOf("."));
									leftfolder = leftfolder
											.substring(leftfolder.indexOf(".") + 1);
								} else {
									new LFWDSTreeItem(parent, ds, leftfolder);
									leftfolder = null;
								}
							} else {
								parent = treeItem;
								if (leftfolder.indexOf(".") != -1) {
									foldername = leftfolder.substring(0,
											leftfolder.indexOf("."));
									leftfolder = leftfolder
											.substring(leftfolder.indexOf(".") + 1);

								} else {
									new LFWDSTreeItem(parent, ds, leftfolder);
									leftfolder = null;
								}
							}
						}
						parent = orinalparent;
					}
					file = rootFile;
				}
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}
//
//	// 初始化pageflow节点
//	public void initPageFlowSubTree(TreeItem parent, String projectPath) {
//		if (pageFlows != null) {
//			for (int i = 0; i < pageFlows.length; i++) {
//				PageFlow pageflow = pageFlows[i];
//				new LFWPageFlowTreeItem(parent, pageflow);
//			}
//		}
//	}

	/**
	 * 刷新某个page下的节点
	 * 
	 * @param nodeItem
	 * @param project
	 */
	public void initSubNodeTree(TreeItem nodeItem, IProject project) {
		String[] projPaths = new String[1];
		projPaths[0] = project.getLocation().toString();
		try {
			Map<String, String> pageNames = NCConnector.getPageNames(projPaths)[0];
			LFWDirtoryTreeItem dir = (LFWDirtoryTreeItem) nodeItem;
			File fileFolder = dir.getFile();
			File[] children = fileFolder.listFiles();
			if (children != null) {
				for (int j = 0; j < children.length; j++) {
					if (children[j].isDirectory()) {
						scanDir(nodeItem, children[j], pageNames);
					}
				}
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}

	/**
	 * 刷新某个Window下的节点
	 * 
	 * @param nodeItem
	 * @param project
	 */
	public void initWindowSubNodeTree(TreeItem nodeItem, IProject project) {
		String[] projPaths = new String[1];
		projPaths[0] = project.getLocation().toString();
		try {
			Map<String, String> pageNames = NCConnector.getPageNames(projPaths)[0];
			LFWDirtoryTreeItem dir = (LFWDirtoryTreeItem) nodeItem;
			File fileFolder = dir.getFile();
			File[] children = fileFolder.listFiles();
			if (children != null) {
				for (int j = 0; j < children.length; j++) {
					if (children[j].isDirectory()) {
						scanWindowDir(nodeItem, children[j], pageNames,
								ILFWTreeNode.VIEW,
								WEBProjConstants.AMC_VIEW_FILENAME, project);
					}
				}
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}

	/**
	 * 刷新pages下的节点
	 * 
	 * @param parent
	 * @param project
	 * @param pageNames
	 */
	public void initNodeSubTree(TreeItem parent, String projectPath,
			Map<String, String> pageNames) {
		if (pageNames != null) {
			File fileFolder = new File(projectPath + "/web/html/nodes");
			File[] children = fileFolder.listFiles();
			if (children != null) {
				for (int j = 0; j < children.length; j++) {
					if (children[j].isDirectory()) {
						scanDir(parent, children[j], pageNames);
					}
				}
			}
		}
	}

	private void scanDir(TreeItem parent, File dir,
			Map<String, String> pageNames) {
		TreeItem item = null;
		if (judgeIsPMFolder(dir)) {
			String id = dir.getName();
			String parentId = null;
			String name = null;
			if (parent instanceof LFWPageMetaTreeItem) {
				LFWPageMetaTreeItem pmParent = (LFWPageMetaTreeItem) parent;
				parentId = pmParent.getPageId();
			}
			if (parentId == null)
				name = pageNames.get(id);
			else
				name = pageNames.get(parentId + "." + id);
			if (name != null && !name.equals(""))
				item = new LFWPageMetaTreeItem(parent, dir, name + "[" + id
						+ "]");
			else
				item = new LFWPageMetaTreeItem(parent, dir, dir.getName());
			if (item != null) {
				((LFWPageMetaTreeItem)item).setId(id);
				LFWDirtoryTreeItem direc = (LFWDirtoryTreeItem) item;
				direc.setType(LFWDirtoryTreeItem.NODE_FOLDER);
			}
		} else {
			if ((parent instanceof LFWPageMetaTreeItem))
				return;
			item = new LFWVirtualDirTreeItem(parent, dir);
		}

		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory())
				scanDir(item, fs[i], pageNames);
		}
	}

	/**
	 * 刷新AMC下的节点
	 * 
	 * @param parent
	 * @param project
	 * @param pageNames
	 * @param path
	 */
	public void refreshAMCNodeSubTree(TreeItem parent, String projectPath,
			Map<String, String> amcNames, String path, String itemType,
			String fileName, IProject project) {
		if (amcNames != null) {
			if (!projectPath.endsWith("/") && !projectPath.endsWith("\\")) {
				projectPath += "/";
			}
			File fileFolder = new File(projectPath + path);
			File[] children = fileFolder.listFiles();
			if (children != null) {
				for (int j = 0; j < children.length; j++) {
					if (children[j].isDirectory()) {
						scanAMCDir(parent, children[j], amcNames, itemType,
								fileName, project);
					}
				}
			}
		}
	}

	private void scanAMCDir(TreeItem parent, File dir,
			Map<String, String> amcNames, String itemType, String fileName,
			IProject project) {
		if (ILFWTreeNode.APPLICATION.equals(itemType)) {
			scanAppDir(parent, dir, amcNames, itemType, fileName, project);
		} else if (ILFWTreeNode.MODEL.equals(itemType)) {
			scanModelDir(parent, dir, amcNames, itemType, fileName, project);
		} else if (ILFWTreeNode.WINDOW.equals(itemType)) {
			scanWindowDir(parent, dir, amcNames, itemType, fileName, project);
		} else if (ILFWTreeNode.PUBLIC_VIEW.equals(itemType)) {
			scanPublicViewDir(parent, dir, amcNames, itemType, fileName,
					project);
		}
	}

	private void scanAppDir(TreeItem parent, File dir,
			Map<String, String> amcNames, String itemType, String fileName,
			IProject project) {
		TreeItem item = null;
		if (judgeIsNeedFolder(dir, fileName)) {
			String id = dir.getName();
			String name = amcNames.get(id);
			if (name != null && name.trim().length() > 0) {
				item = new LFWApplicationTreeItem(parent, dir, name + "[" + id
						+ "]");
			} else {
				item = new LFWApplicationTreeItem(parent, dir, dir.getName());
			}
			Application application = new Application();
			application.setId(id);
			application.setCaption(name);
			((LFWApplicationTreeItem) item).setApplication(application);
			((LFWApplicationTreeItem) item).setId(id);
			LFWDirtoryTreeItem direc = (LFWDirtoryTreeItem) item;
			direc.setType(itemType);
		} else {
			if (parent instanceof LFWApplicationTreeItem) {
				return;
			}
			item = new LFWVirtualDirTreeItem(parent, dir);
			((LFWVirtualDirTreeItem)item).setLfwVersion(LFWTool.NEW_VERSION);
			((LFWVirtualDirTreeItem)item).setType(ILFWTreeNode.APPLICATION_FOLDER);
		}
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				scanAppDir(item, fs[i], amcNames, itemType, fileName, project);
			}
		}
	}

	private void scanWindowDir(TreeItem parent, File dir,
			Map<String, String> pageNames, String itemType, String fileName,
			IProject project) {
		TreeItem item = null;
		if (judgeIsNeedFolder(dir, fileName)) {
			String id = dir.getName();
			String parentId = null;
			String name = null;
			if (parent instanceof LFWPageMetaTreeItem) {
				LFWPageMetaTreeItem pmParent = (LFWPageMetaTreeItem) parent;
				parentId = pmParent.getPageId();
			}
			if (parentId == null) {
				name = pageNames.get(id);
			} else {
				name = pageNames.get(parentId + "." + id);
			}
			if (name != null && name.trim().length() > 0) {
				item = new LFWPageMetaTreeItem(parent, dir, name + "[" + id
						+ "]");
			} else {
				item = new LFWPageMetaTreeItem(parent, dir, dir.getName());
				name = dir.getName();
			}
			LFWDirtoryTreeItem direc = (LFWDirtoryTreeItem) item;
			direc.setId(id);
			direc.setItemName(name);
			direc.setType(itemType);
			direc.setLfwVersion(LFWTool.NEW_VERSION);
		} else {
			if (parent instanceof LFWPageMetaTreeItem) {
				return;
			}
			item = new LFWVirtualDirTreeItem(parent, dir);
			((LFWVirtualDirTreeItem) item).setLfwVersion(LFWTool.NEW_VERSION);
			((LFWVirtualDirTreeItem)item).setType(ILFWTreeNode.WINDOW_FOLDER);
		}
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				scanWindowDir(item, fs[i], pageNames, itemType, fileName,
						project);
			}
		}
	}

	private void scanPublicViewDir(TreeItem parent, File dir,
			Map<String, String> amcNames, String itemType, String fileName,
			IProject project) {
		TreeItem item = null;
		if (judgeIsNeedFolder(dir, fileName)) {
			String id = dir.getName();
			String name = amcNames.get(id);
			if (name != null && name.trim().length() > 0) {
				item = new LFWPublicViewTreeItem(parent, dir, "[View] "
						+ dir.getName());
			} else {
				item = new LFWPublicViewTreeItem(parent, dir, dir.getName());
			}
			if (item != null) {
				LFWDirtoryTreeItem direc = (LFWDirtoryTreeItem) item;
				direc.setType(itemType);
			}
		} else {
			if (parent instanceof LFWPublicViewTreeItem) {
				return;
			}
			item = new LFWVirtualDirTreeItem(parent, dir);
			((LFWVirtualDirTreeItem) item).setLfwVersion(LFWTool.NEW_VERSION);
			((LFWVirtualDirTreeItem)item).setType(ILFWTreeNode.PUBLIC_VIEW_FOLDER);
		}
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				scanPublicViewDir(item, fs[i], amcNames, itemType, fileName,
						project);
			}
		}
	}

	private void scanModelDir(TreeItem parent, File dir,
			Map<String, String> amcNames, String itemType, String fileName,
			IProject project) {
		TreeItem item = null;
		if (judgeIsNeedFolder(dir, fileName)) {
			String id = dir.getName();
			String name = amcNames.get(id);
			if (name != null && name.trim().length() > 0) {
				item = new LFWModelTreeItem(parent, dir, name + "[" + id + "]");
			} else {
				item = new LFWModelTreeItem(parent, dir, dir.getName());
			}
			if (item != null) {
				LFWDirtoryTreeItem direc = (LFWDirtoryTreeItem) item;
				direc.setType(itemType);
			}
		} else {
			if ((parent instanceof LFWModelTreeItem)) {
				return;
			}
			item = new LFWVirtualDirTreeItem(parent, dir);
			((LFWVirtualDirTreeItem) item).setLfwVersion(LFWTool.NEW_VERSION);
			((LFWVirtualDirTreeItem)item).setType(ILFWTreeNode.MODEL_FOLDER);
		}
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				scanModelDir(item, fs[i], amcNames, itemType, fileName, project);
			}
		}
	}

	private boolean judgeIsNeedFolder(File fold, String fileName) {
		File[] childChildren = fold.listFiles();
		if (childChildren == null)
			return false;
		for (int i = 0; i < childChildren.length; i++) {
			if (childChildren[i].getName().equals(fileName))
				return true;
		}
		return false;

	}

	private boolean judgeIsPMFolder(File fold) {
		File[] childChildren = fold.listFiles();
		if (childChildren == null)
			return false;
		for (int i = 0; i < childChildren.length; i++) {
			if (childChildren[i].getName().equals("pagemeta.pm"))
				return true;
		}
		return false;

	}

	public void initRefNodeSubTree(LFWDirtoryTreeItem parent, File file,
			String projectPath) {
		Map<Object, Object> foldermap = new HashMap<Object, Object>();
		LFWDirtoryTreeItem orinalparent = parent;
		File rootFile = file;
		try {
			Map<String, IRefNode> refnodeMap = LFWConnector
					.getRefNode(projectPath);
			if (refnodeMap != null) {
				Iterator<IRefNode> it = refnodeMap.values().iterator();
				while (it.hasNext()) {
					IRefNode refnode = it.next();
					String id = refnode.getId();
					if (id.indexOf(".") == -1)
						new LFWRefNodeTreeItem(parent, refnode, "[参照]");
					else {
						String foldername = id.substring(0, id.indexOf("."));
						String leftfolder = id.substring(id.indexOf(".") + 1);
						while (leftfolder != null) {
							File newfile = new File(file, foldername);
							LFWDirtoryTreeItem treeItem = (LFWDirtoryTreeItem) foldermap
									.get(newfile);
							file = newfile;
							if (treeItem == null) {
								LFWDirtoryTreeItem newTreeItem = new LFWDirtoryTreeItem(
										parent, newfile);
								newTreeItem
										.setType(LFWDirtoryTreeItem.POOLREFNODEFOLDER);
								parent = newTreeItem;
								foldermap.put(newfile, newTreeItem);
								if (leftfolder.indexOf(".") != -1) {
									foldername = leftfolder.substring(0,
											leftfolder.indexOf("."));
									leftfolder = leftfolder
											.substring(leftfolder.indexOf(".") + 1);
								} else {
									new LFWRefNodeTreeItem(parent, refnode,
											"[参照]");
									leftfolder = null;
								}
							} else {
								parent = treeItem;
								if (leftfolder.indexOf(".") != -1) {
									foldername = leftfolder.substring(0,
											leftfolder.indexOf("."));
									leftfolder = leftfolder
											.substring(leftfolder.indexOf(".") + 1);
								} else {
									new LFWRefNodeTreeItem(parent, refnode,
											"[参照]");
									leftfolder = null;
								}
							}
						}
						parent = orinalparent;
					}
					file = rootFile;
				}
			}
		} catch (Throwable e) {
			MainPlugin.getDefault().logError(e);
		}
	}

	/**
	 * 初始化nodes下节点下的子节点
	 * 
	 * @param parent
	 * @param file
	 * @param project
	 */
	public void initNodeSubTree(TreeItem parent, File file, IProject project) {
		File[] childs = file.listFiles();
		int count = childs == null ? 0 : childs.length;
		for (int i = 0; i < count; i++) {
			File child = childs[i];
			if (child.isDirectory()) {
				File[] children = child.listFiles();
				TreeItem tichild = null;
				if (children != null) {
					for (int j = 0; j < children.length; j++) {
						if (children[j].getName().equals("pagemeta.pm")) {
							if (tichild == null)
								tichild = new LFWDirtoryTreeItem(parent,
										children[j]);
							continue;
						}
						if (children[j].isDirectory()) {
							if (tichild == null)
								tichild = new LFWDirtoryTreeItem(parent,
										children[j]);
							initNodeSubTree(tichild, children[j], project);
						}
					}
				}
			}
		}
	}

	public IProject getFileOwnProject(Tree tree, File file) {
		IProject project = null;
		TreeItem[] items = tree.getItems();
		int count = items == null ? 0 : items.length;
		for (int i = 0; i < count; i++) {
			if (items[i] instanceof LFWProjectTreeItem) {
				LFWProjectTreeItem item = (LFWProjectTreeItem) items[i];
				if (containsFile(item, file)) {
					project = item.getProjectModel().getJavaProject();
					break;
				}
			}
		}
		return project;
	}

	private boolean containsFile(TreeItem item, File file) {
		Object o = item.getData();
		if (o != null && o instanceof File
				&& ((File) o).getAbsolutePath().equals(file.getAbsolutePath())) {
			return true;
		} else {
			TreeItem[] items = item.getItems();
			int count = items == null ? 0 : items.length;
			for (int i = 0; i < count; i++) {
				if (containsFile(items[i], file)) {
					return true;
				}
			}
			return false;
		}
	}
	
	class AMCThread extends Thread{
		
		public AMCThread(){}
		
		@Override
		public void run() {
			LFWTool.checkFirefoxEnvironment();
		}
	}

	protected void buildAppendTree(LFWBasicTreeItem projectRoot, IProject project) {
		return;
	}
	
}
