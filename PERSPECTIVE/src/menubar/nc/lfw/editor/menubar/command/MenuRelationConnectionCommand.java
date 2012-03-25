package nc.lfw.editor.menubar.command;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.Connection;
import nc.lfw.editor.common.LFWBaseEditor;
import nc.lfw.editor.common.LFWBasicElementObj;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.contextmenubar.ContextMenuEditor;
import nc.lfw.editor.contextmenubar.ContextMenuElementObj;
import nc.lfw.editor.menubar.MenubarConnector;
import nc.lfw.editor.menubar.MenubarEditor;
import nc.lfw.editor.menubar.MenubarGraph;
import nc.lfw.editor.menubar.dialog.MenubarRelationDialog;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.lfw.editor.menubar.ele.MenuItemObj;
import nc.lfw.editor.menubar.ele.MenubarElementObj;
import nc.uap.lfw.core.comp.MenuItem;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

public class MenuRelationConnectionCommand extends Command {
	protected LFWBasicElementObj source;
	protected LFWBasicElementObj target;
	private MenubarConnector conn = null;
	private Constructor constructor = null;
	private Class conCls = null;

	public MenuRelationConnectionCommand(LFWBasicElementObj refdsobj, Class conCls) {
		super();
		this.source = refdsobj;
		this.conCls = conCls;
		try {
			constructor = conCls.getConstructor(new Class[] { LFWBasicElementObj.class, LFWBasicElementObj.class });
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLabel("create new cell");
	}

	public boolean canExecute() {
		if(true)
			return true;
		// ö��ֻ�ܺ�ע�ͽ�������
		if ((source instanceof MenuElementObj && target instanceof MenuElementObj)
				&& Connection.class.equals(conCls)) {
			return true;
		}
		else if ((source instanceof MenubarGraph && target instanceof MenuElementObj)
				&& Connection.class.equals(conCls)) {
			return true;
		}
		return false;
	}

	void setSource(LFWBasicElementObj source) {
		this.source = source;
	}

	public void setTarget(LFWBasicElementObj target) {
		this.target = target;
	}

	public String getLabel() {
		return "Create Connection";
	}

	
	public void redo() {
		//TODO
		if(target instanceof MenuElementObj){
			MenuElementObj targetObj = (MenuElementObj) target;
			List<MenuItem> children = targetObj.getMenuItem().getChildList();
			if(children == null || children.size() == 0){
				MessageDialog.openInformation(null, "��ʾ", "�Ӳ˵���δ��Ӳ˵���");
				return;
			}
		}
		if (source instanceof MenubarElementObj && target instanceof MenuElementObj) {
			if (1 != ((MenuElementObj)target).getLevel()) {
				MessageDialog.openInformation(null, "��ʾ", "���˵�ֻ�����ӵ�һ���Ӳ˵�");
				return;
			} else {
				createRelation();
			}
		}else if(source instanceof ContextMenuElementObj && target instanceof MenuElementObj){
			if (1 != ((MenuElementObj)target).getLevel()) {
				MessageDialog.openInformation(null, "��ʾ", "���˵�ֻ�����ӵ�һ���Ӳ˵�");
				return;
			} else {
				createRelation();
			}
		}else if (source instanceof MenuElementObj && target instanceof MenuElementObj) {
			if (((MenuElementObj)target).getLevel() - 1 != ((MenuElementObj)source).getLevel()) {
				MessageDialog.openInformation(null, "��ʾ", "�˵�ֻ������һ���Ӳ˵�����");
				return;
			} else {
				createRelation();
			}
		}else {
			MessageDialog.openInformation(null, "��ʾ", "");
		}
	}
	
	/**
	 * ��������
	 */
	private void createRelation() {
		MenubarRelationDialog dialog = new MenubarRelationDialog(null,"�˵�������ϵ");
		LfwElementObjWithGraph sourceObj = (LfwElementObjWithGraph) source;
		MenuElementObj targetObj = (MenuElementObj) target;
		dialog.setSource(sourceObj);
		dialog.setTarget(targetObj);
		int result = dialog.open();
		if (result == IDialogConstants.OK_ID) {
			// �޸Ĺ�����ϵ��Ĵ�����
			
			// �µĴ�����
			MenuItem newSourceItem = dialog.getSourceItem();
			if (newSourceItem != targetObj.getMenuItem()) {  // ��������ı�
				// ��������Pagemeta��MenubarComp����
				MenuItem targetMenuItem = targetObj.getMenuItem();
				//List<MenuItem> newChildList = targetObj.getMenuItem().getChildList();
				newSourceItem.addMenuItem(targetMenuItem);
				//newSourceItem.setChildList(newChildList);
				if (null != targetObj.getMenuItem().getText() && !"".equals(targetObj.getMenuItem().getText()))  // Ŀ�겻���½����Ӳ˵�
					targetObj.getMenuItem().setChildList(new ArrayList<MenuItem>());
				else {  // Ŀ�����½����Ӳ˵�
					targetObj.setMenuItem(newSourceItem);
				}

				// �������ø��˵��ı�������
				if (sourceObj instanceof MenubarElementObj) {
					for (MenuItemObj childObj : ((MenubarElementObj)sourceObj).getChildrenList()) {
						if (childObj.getMenuItem() == targetObj.getMenuItem())
							childObj.setMenuItem(newSourceItem);
					}
				} else if (sourceObj instanceof MenuElementObj) {
					for (MenuItemObj childObj : ((MenuElementObj)sourceObj).getChildrenList()) {
						if (childObj.getMenuItem() == targetObj.getMenuItem())
							childObj.setMenuItem(newSourceItem);
					}
				}
				// ���������Ӳ˵��Ĵ�����
				targetObj.setMenuItem(newSourceItem);

				// ��ȡMenubarEditor
				LFWBaseEditor editor = LFWBaseEditor.getActiveEditor();
				editor.setDirtyTrue();
			}
			
			conn.connect();
			
			((MenuElementObj) target).getFigure().refresh();
			
			LFWBaseEditor editor = LFWBaseEditor.getActiveEditor();
			if(editor instanceof MenubarEditor){
				MenubarEditor.getActiveMenubarEditor().addConnector(conn);
			}else if(editor instanceof ContextMenuEditor){
				ContextMenuEditor.getActiveMenubarEditor().addConnector(conn);
			}
		}
	}

	
	public void undo() {
		conn.disConnect();
	}

	public void execute() {
		try {
			Connection conn = (Connection) constructor.newInstance(new LFWBasicElementObj[] { source, target });
			this.conn = new MenubarConnector(conn.getSource(), conn.getTarget());
			redo();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
