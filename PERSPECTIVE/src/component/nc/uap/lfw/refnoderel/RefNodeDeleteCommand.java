package nc.uap.lfw.refnoderel;

import java.util.List;

import nc.lfw.editor.common.Connection;
import nc.lfw.editor.common.LFWBasicElementObj;
import nc.lfw.editor.common.LfwBaseGraph;
import nc.lfw.editor.common.tools.LFWPersTool;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.refnode.RefNodeElementObj;

import org.eclipse.gef.commands.Command;

public class RefNodeDeleteCommand extends Command{
	private LFWBasicElementObj lfwobj = null;
	private boolean canUndo = true;
	private LfwBaseGraph graph = null;
	private List<Connection> sourceConnections = null;
	private List<Connection> targetConnections = null;
	public RefNodeDeleteCommand(LFWBasicElementObj lfwobj,LfwBaseGraph graph) {
		super();
		this.lfwobj = lfwobj;
		this.graph = graph;
		setLabel("delete refds");
	}

	
	public boolean canExecute() {
		return super.canExecute();
	}

	
	public void execute() {
		sourceConnections = lfwobj.getSourceConnections();
		targetConnections = lfwobj.getTargetConnections();
		redo();
	}

	
	public void redo() {
		 if(graph instanceof RefnoderelGraph && lfwobj instanceof RefNodeElementObj){
			 RefnoderelGraph refRelgraph = (RefnoderelGraph)graph;
			 RefNodeElementObj refnodeobj = (RefNodeElementObj)lfwobj;
			 refRelgraph.setDetailRefNode(null);
			 String relId = refnodeobj.getRefRelationId();
			 LfwWidget widget = LFWPersTool.getCurrentWidget();
			 widget.getViewModels().getRefNodeRelations().removeRefNodeRelation(relId);
		}
		removeConnections(sourceConnections);
		removeConnections(targetConnections);
	}
	
	private void removeConnections(List<Connection> conn){
		int count = conn.size();
		for (int i = 0; i < count; i++) {
			Connection relation = conn.get(i);
			relation.disConnect();
		}
	}
	
	private void addConnections(List<Connection> conn){
		int count = conn.size();
		for (int i = 0; i < count; i++) {
			Connection relation = conn.get(i);
			relation.connect();
		}
	}

	
	public void undo() {
		if(graph instanceof RefnoderelGraph && lfwobj instanceof RefNodeElementObj){
			RefnoderelGraph refgraph = (RefnoderelGraph)graph;
			RefNodeElementObj refdsobj = (RefNodeElementObj)lfwobj;
			if(refgraph.addCell(refdsobj)){
				addConnections(sourceConnections);
				addConnections(targetConnections);
			}
		}
	}

	public boolean isCanUndo() {
		return canUndo;
	}

	public void setCanUndo(boolean canUndo) {
		this.canUndo = canUndo;
	}

	
	public boolean canUndo() {
		return isCanUndo();
	}

}
