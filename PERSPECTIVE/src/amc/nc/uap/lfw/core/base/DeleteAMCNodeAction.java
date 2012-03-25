/**
 * 
 */
package nc.uap.lfw.core.base;

import nc.lfw.lfwtools.perspective.MainPlugin;
import nc.uap.lfw.perspective.project.LFWExplorerTreeView;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 
 * ɾ���ڵ�������
 * @author chouhl
 *
 */
public abstract class DeleteAMCNodeAction extends NodeAction {
	
	public DeleteAMCNodeAction(String text, String toolTipText){
		super(text, toolTipText);
	}
	
	public void run(){
		LFWExplorerTreeView view = LFWExplorerTreeView.getLFWExploerTreeView(null);
		try{
			if(view != null){
				view.deleteSelectedTreeNode();
			}
		}catch(Exception e){
			MainPlugin.getDefault().logError("ɾ���ڵ�ʧ��", e);
			MessageDialog.openError(null, getText(), "ɾ���ڵ�ʧ��");
		}
	}
	
}
