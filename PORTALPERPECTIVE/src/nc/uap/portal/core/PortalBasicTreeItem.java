package nc.uap.portal.core;

import java.io.File;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Tree;

import org.eclipse.swt.widgets.TreeItem;

/**
 * ���ڵ����
 * 
 * @author dingrf
 *
 */
public class PortalBasicTreeItem extends TreeItem implements IPortalTreeNode{

//	/**˫�����ڵ��editorʱ��EditorInput*/
	private PortalBaseEditorInput editorInput;

	/**
	 * ���캯��
	 * 
	 * @param parentItem	���ڵ�����ΪTreeItem
	 * @param style			��ʽ
	 */
	public PortalBasicTreeItem(TreeItem parentItem, int style) {
		super(parentItem, style);
	}

	/**
	 * ���캯��
	 * 
	 * @param parentItem	���ڵ�����ΪTreeItem
	 * @param style			��ʽ
	 * @param index			�ڸ��ڵ��е�λ������
	 */
	public PortalBasicTreeItem(TreeItem parentItem, int style, int index) {
		super(parentItem, style, index);
	}

	/**
	 * ���캯��
	 * 
	 * @param parentItem	���ڵ�����ΪTree
	 * @param style			��ʽ
	 */
	public PortalBasicTreeItem(Tree parent, int style) {
		super(parent, style);
	}

	public PortalBaseEditorInput getEditorInput() {
		return editorInput;
	}

	public void setEditorInput(PortalBaseEditorInput editorInput) {
		this.editorInput = editorInput;
	}

	/**
	 * ɾ���Լ�
	 */
	public void deleteNode() {
	}

	/**
	 * �õ����ڵ��Ӧ�ļ�
	 * 
	 * @return 
	 */
	public File getFile() {
		return null;
	}

	/**
	 * �õ����ڵ��Ӧ�ļ�����Ŀ��·��
	 * 
	 * @return
	 */
	public String getIPathStr() {
		return null;
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * �����Ҽ��˵�
	 * 
	 * @param manager	�Ҽ��˵�������
	 */
	public void addMenuListener(IMenuManager manager){
	}
	
	/**
	 * ˫������¼�
	 * 
	 */
	public void mouseDoubleClick(){
	} 
	
}
