package nc.lfw.editor.pagemeta;

import nc.lfw.editor.common.Connection;
import nc.uap.lfw.core.page.Connector;
import nc.uap.lfw.core.page.PageMeta;

import org.eclipse.gef.commands.Command;

/**
 * ɾ��Widget���ӹ�ϵ
 * 
 * @author guoweic
 * 
 */
public class WidgetRelationDeleteCommand extends Command {
	private Connection conn = null;

	public WidgetRelationDeleteCommand(Connection relation) {
		super();
		this.conn = relation;
		setLabel("delete relation");
	}

	
	public boolean canExecute() {
		// TODO Auto-generated method stub
		return super.canExecute();
	}

	
	public void execute() {
		redo();
	}

	
	public void redo() {
		conn.disConnect();
		delData();
	}

	
	public void undo() {
		conn.connect();
	}

	private void delData() {
		if (conn instanceof WidgetConnector) {
			PagemetaEditor editor = PagemetaEditor.getActivePagemetaEditor();
			PageMeta pagemeta = editor.getGraph().getPagemeta();
			// ɾ����ص�connector
			Connector connector = ((WidgetConnector) conn).getConnector();
			pagemeta.getConnectorMap().remove(connector.getId());
			// ����pagemeta
//			editor.savePagemeta(pagemeta);
			editor.setDirtyTrue();
		}
	}

}
