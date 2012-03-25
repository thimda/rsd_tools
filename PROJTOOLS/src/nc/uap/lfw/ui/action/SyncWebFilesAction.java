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
 * ͬ��Web�ļ�Action
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
						//ͬ���ļ�
						syncFiles(prePaths, proj, partList);
						monitor.worked(1);
					}
					// �ϲ�web.xml
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
	 * �ϲ�web.xml
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
							WEBProjPlugin.getDefault().logInfo("��ʼ�ϲ�part�ļ�: " + fs[j].getAbsolutePath());
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
	 * ͬ���ļ�����
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
				//���û���ҵ�webContext����ͬ����
				if (ctx == null || ctx.equals("")){
					WEBProjPlugin.getDefault().logError("û���ҵ�web������,��Ŀ��"+projPath);
					continue;
				}
				String toDir = LfwCommonTool.getNCHome() + "/hotwebs/" + ctx;
				WEBProjPlugin.getDefault().logInfo("��ʼͬ��Ŀ¼:" + path + ",Ŀ��:" + toDir + ",Դ:" + projPath + "/" + paths[i] + "/web");
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
