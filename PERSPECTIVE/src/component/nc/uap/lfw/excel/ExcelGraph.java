package nc.uap.lfw.excel;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.Connection;
import nc.lfw.editor.common.LfwBaseGraph;
import nc.uap.lfw.perspective.model.RefDatasetElementObj;

public class ExcelGraph extends LfwBaseGraph {
	private static final long serialVersionUID = -2154903434383093547L;
	private RefDatasetElementObj dsobj= new RefDatasetElementObj();
	

	public RefDatasetElementObj getDsobj() {
		return dsobj;
	}


	public void setDsobj(RefDatasetElementObj dsobj) {
		this.dsobj = dsobj;
	}


	private List<Connection> connections = new ArrayList<Connection>();
	
	public ExcelGraph() {
		super();
	}
	

	public boolean addConns(Connection cell) {
		cell.setGrahp(this);
		boolean b = connections.add(cell);
		if (b) {
			fireStructureChange(PROP_CHILD_ADD, cell);
		}
		return b;
	}



	
	public Object getPropertyValue(Object id) {
		return null;
	}

	public void setPropertyValue(Object id, Object value) {
		
	}

}

