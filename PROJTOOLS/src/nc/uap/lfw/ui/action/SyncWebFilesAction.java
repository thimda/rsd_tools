package nc.uap.lfw.ui.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.common.FileUtilities;
import nc.uap.lfw.common.LfwCommonTool;
import nc.uap.lfw.core.WEBProjPlugin;
import nc.uap.lfw.internal.util.PartMergeUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 同步Web文件Action
 *
 */
public class SyncWebFilesAction implements IObjectActionDelegate {
	private ISelection	fSelection;
	public void run(IAction action){
		if (fSelection instanceof IStructuredSelection){
			
			Job job = new Job("Sync Web Files"){
				public IStatus run(IProgressMonitor monitor){
					//IProject proj = (IProject) ((IStructuredSelection) fSelection).getFirstElement();
					IProject[] projs = LfwCommonTool.getOpenedLfwProjects();
					monitor.beginTask("Sync Web Files", projs.length);
					List<IProject> partList = new ArrayList<IProject>();
					for (int i = 0; i < projs.length; i++) {
						IProject proj = projs[i];
						String[] prePaths = null;
						if(LfwCommonTool.isBCPProject(proj)) {
							prePaths = LfwCommonTool.getBCPNames(proj);
						}
						else
							prePaths = new String[]{""};
						//同步文件
						syncFiles(prePaths, proj, partList);
						monitor.worked(1);
					}
					// 合并web.xml
					for (int i=0 ; i<partList.size() ; i++){
						IProject proj = partList.get(i); 
						String[] prePaths = null;
						if(LfwCommonTool.isBCPProject(proj)) {
							prePaths = LfwCommonTool.getBCPNames(proj);
						}
						else
							prePaths = new String[]{""};
						mergeWebXml(prePaths, proj);
					}
					monitor.done();
					return Status.OK_STATUS;
				}
			};
			job.schedule();
		}
	}

	/**
	 * 合并web.xml
	 * 
	 * @param prePaths
	 * @param proj
	 */
	private void mergeWebXml(String[] paths, IProject proj) {
		String projPath = proj.getLocation().toString();
		for (int i = 0; i < paths.length; i++) {
			try {
				String path = paths[i];
				if(!path.equals(""))
					path = "/" + path;
				File dir = new File(projPath + path + "/web/WEB-INF/");
				if(dir.exists()){
					File[] fs = dir.listFiles();
					for (int j = 0; j < fs.length; j++) {
						if(fs[j].getName().endsWith(".part")){
							WEBProjPlugin.getDefault().logInfo("开始合并part文件: " + fs[j].getAbsolutePath());
							PartMergeUtil.mergeWebXml(proj, fs[j]);
							break;
						}
					}
				}
			} 
			catch (Exception e) {
				WEBProjPlugin.getDefault().logError(e);
			}
		}
	}

	/**
	 * 同步文件方法
	 * @param proj 
	 */
	private void syncFiles(String[] paths, IProject proj, List<IProject> partList) {
		String projPath = proj.getLocation().toString();
		for (int i = 0; i < paths.length; i++) {
			try {
				String path = paths[i];
				if(!path.equals(""))
					path = "/" + path;
				String ctx = getCtxPath(projPath, path);
				if(ctx == null){
					ctx = LfwCommonTool.getLfwProjectCtx(proj);
				}
				else
					partList.add(proj);
				//如果没有找到webContext，不同步。
				if (ctx == null || ctx.equals("")){
					WEBProjPlugin.getDefault().logError("没有找到web上下文,项目："+projPath);
					continue;
				}
				String toDir = LfwCommonTool.getNCHome() + "/hotwebs/" + ctx;
				WEBProjPlugin.getDefault().logInfo("开始同步目录:" + path + ",目标:" + toDir + ",源:" + projPath + "/" + paths[i] + "/web");
				FileUtilities.copyFileFromDir(toDir, projPath + "/" + paths[i] + "/web");
			} 
			catch (Exception e) {
				WEBProjPlugin.getDefault().logError(e);
			}
		}
	}

	private String getCtxPath(String projPath, String path) {
		File dir = new File(projPath + path + "/web/WEB-INF/");
		if(dir.exists()){
			File[] fs = dir.listFiles();
			for (int j = 0; j < fs.length; j++) {
				if(fs[j].getName().endsWith(".part")){
					return fs[j].getName().substring(0,fs[j].getName().length()-5);
				}
			}
		}
		return null;
	}

	public void selectionChanged(IAction action, ISelection selection){
		this.fSelection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart){
		
	}
}
