package nc.uap.lfw.actions;

import nc.uap.lfw.tool.ProjConstants;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;

/**
 * ������ԴAction����
 * 
 * @author dingrf
 *
 */
public abstract class AbstractMLResRefactorActionDelegate implements IWorkbenchWindowActionDelegate{

	/**eclipse��ǰ��IWorkbenchWindow*/
	private IWorkbenchWindow window = null;
	
	/**ѡ�еĶ���Ŀ���ļ�*/
	protected ISelection selection = null;

	@Override
	public void dispose(){
	}

	@Override
	public void init(IWorkbenchWindow window){
		this.window = window;
	}

	protected Shell getShell(){
		return window.getShell();
	}

	/**
	 * �õ�ѡ�е�xml�ļ�
	 * 
	 * @param selection
	 * @return
	 */
	protected IFile getXmlFile(ISelection selection){
		if (selection instanceof IStructuredSelection){
			IStructuredSelection ss = (IStructuredSelection)selection;
			if (ss.isEmpty() || ss.size() != 1)
				return null;
			Object first = ss.getFirstElement();
			if (first instanceof IFile){
				if(((IFile)first).getName().endsWith(".xml")){
					return (IFile)first;
				}
			}
		} 
		return null;
	}

	//�ݲ�֧��textSelection
//	protected Region getSelectionRegion(){
//		if (selection != null && (selection instanceof TextSelection)){
//			TextSelection textSel = (TextSelection)selection;
//			return new Region(textSel.getOffset(), textSel.getLength());
//		} 
//		else{
//			return null;
//		}
//	}

	/**
	 * ѡ��LFW��Ŀʱ���˵�����
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection){
		this.selection = selection;
		action.setEnabled(true);
//		action.setEnabled(getXmlFile(selection) != null);
		//ѡ��lfw��Ŀʱ������
		action.setEnabled(getLfwProject(selection) != null);
	}

	/**
	 * �Ƿ�Ϊlfw��Ŀ
	 * @return
	 */
	protected IProject getLfwProject(ISelection selection) {
		//TODO ����ֻ��ѡ�ļ����ļ���ʱ�ź���
		if (selection instanceof IStructuredSelection){
			IStructuredSelection ss = (IStructuredSelection)selection;
			if (ss.isEmpty() || ss.size() != 1)
				return null;
			Object first = ss.getFirstElement();
//			if (first instanceof PackageFragmentRoot){
//				((PackageFragmentRoot)first).
//			}
			IProject project = null;
			if (first instanceof JavaProject){
				project = ((JavaProject)first).getProject();
			}
			else if (first instanceof IResource){
				project = ((IResource)first).getProject();
			}
			try {
				if (project != null && project.hasNature(ProjConstants.MODULE_NATURE))
					return project;
			} catch (CoreException e) {
				return null;
			}
		}
		return null; 
	}
}
