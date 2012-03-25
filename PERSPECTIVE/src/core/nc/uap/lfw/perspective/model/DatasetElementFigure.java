package nc.uap.lfw.perspective.model;
import java.util.HashMap;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.MatchField;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;


/**
 * Dataset Figure
 * @author zhangxya
 *
 */
public class DatasetElementFigure extends LFWBaseRectangleFigure{
	
	private static Color bgColor = new Color(null, 233,176,66);
	private HashMap<String, DsFieldLabel> hmAttrToPropLabel = new HashMap<String, DsFieldLabel>();
	private LfwElementObjWithGraph ele;
	private int height = 0;
	private DatasetElementObj datasetobj = null;
	
	public DatasetElementFigure(LfwElementObjWithGraph ele){
		super(ele);
		this.ele = ele;
		setTypeLabText("数据集");
		setBackgroundColor(bgColor);
		datasetobj = (DatasetElementObj) ele;
		setTitleText(datasetobj.getDs().getId(), datasetobj.getDs().getId());
		markError(datasetobj.validate());
		Point point = datasetobj.getLocation();
		int x, y;
		if(point != null){
			x = point.x;
			y = point.y;
		}else{
			x = 100;
			y = 100;
		}
		add(getAttrsFigure());
		FieldRelation[] frs = datasetobj.getDs().getFieldRelations().getFieldRelations();
		if(frs != null && (!(datasetobj.isDatasets()))){
			for (int i = 0; i < frs.length; i++) {
				FieldRelation fr = frs[i];
				addAttribute(fr);
			}
		}
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(x, y, 150, this.height > 150? this.height: 150));
	}
	
	private void resizeHeight() {
		setSize(180, this.height > 150 ? this.height : 150);
		//Dimension dimension = new Dimension(180, this.height > 150 ? this.height : 150);
		//ele.setSize(dimension);
	}
	
	public void addAttribute(FieldRelation fr){
		MatchField[] matches = fr.getMatchFields();
		for (int i = 0; i < matches.length; i++) {
			MatchField match = matches[i];
			String isMatch = match.getIsmacth();
			String writeField = match.getWriteField();
			DsFieldLabel lab = new DsFieldLabel(writeField, isMatch);
			hmAttrToPropLabel.put(writeField, lab);
			getAttrsFigure().add(lab);
			this.height += LINE_HEIGHT;
			resizeHeight();
		}
		
	}
	
	
	/**
	 * 清空所有属性
	 */
	public void deleteAllAttris(){
		getAttrsFigure().removeAll();
	}
	
	public void deleteAttribute(FieldRelation fr){
		MatchField[] matches = fr.getMatchFields();
		for (int i = 0; i < matches.length; i++) {
			MatchField match = matches[i];
			String writeField = match.getWriteField();
			DsFieldLabel lab = hmAttrToPropLabel.get(writeField);
			if(lab != null){
				hmAttrToPropLabel.remove(lab);
				getAttrsFigure().remove(lab);
				this.height -= LINE_HEIGHT;
				resizeHeight();
			}
		}
	}
	
		
	public void updateAttribute(Field attri){
		DatasetElementObj datasetobj = (DatasetElementObj) ele;
		Dataset ds = datasetobj.getDs();
		Field[] fields = ds.getFieldSet().getFields();
		for (int i = 0; i < fields.length; i++) {
//			if(fields[i].getExtendAttribute(PK_FIELD).equals(attri.getExtendAttribute(PK_FIELD)))
//					ds.getFieldSet().addField(attri);
		}
//		ds.getFieldSet().removeField(oldValue);
//		ds.getFieldSet().addField(attri);
		//ds.getFieldSet().
		//DsFieldLabel figure = hmAttrToPropLabel.get(attri);
		//figure.updateFigure(attri);
	}
	
	protected String getTypeText() {
		return "数据集";
	}
	
}
