package nc.uap.lfw.perspective.model;

import java.util.HashMap;

import nc.lfw.editor.common.LFWBaseRectangleFigure;
import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.uap.lfw.core.data.FieldRelation;
import nc.uap.lfw.core.data.MatchField;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * 引用DsFigure
 * 
 * @author zhangxya
 * 
 */
public class RefDatasetElementFigure extends LFWBaseRectangleFigure {

	private int height = 0;
	private static Color bgColor = new Color(null, 239, 255, 150);
	private HashMap<String, DsFieldLabel> hmAttrToPropLabel = new HashMap<String, DsFieldLabel>();
	private LfwElementObjWithGraph ele;
	public RefDatasetElementFigure(LfwElementObjWithGraph ele) {
		super(ele);
		this.ele  = ele;
		setTypeLabText("引用数据集");
		RefDatasetElementObj datasetobj = (RefDatasetElementObj) ele;
		if (datasetobj.getDs() != null){
			String dsId = datasetobj.getDs().getId() == null ? "" : datasetobj.getDs().getId();
			setTitleText(dsId, dsId);
		}
		setBackgroundColor(bgColor);
		markError(datasetobj.validate());

		Point point = datasetobj.getLocation();
		add(getAttrsFigure());
		FieldRelation fr = datasetobj.getRefFieldRelation();
		if(fr != null){
			addAttribute(fr);
		}
		this.height += 3 * LINE_HEIGHT;
		setBounds(new Rectangle(point.x, point.y, 120, this.height > 150? this.height: 120));
	}
	
	public void addAttribute(FieldRelation fr){
		MatchField[] matches = fr.getMatchFields();
		for (int i = 0; i < matches.length; i++) {
			MatchField match = matches[i];
			String isMatch = match.getIsmacth();
			String readField = match.getReadField();
			DsFieldLabel lab = new DsFieldLabel(readField, isMatch);
			if(hmAttrToPropLabel != null && hmAttrToPropLabel.get(readField) == null){
				hmAttrToPropLabel.put(readField, lab);
				getAttrsFigure().add(lab);
				this.height += LINE_HEIGHT;
				resizeHeight();
			}
		}
	}
	
	private void resizeHeight() {
		setSize(120, this.height > 120 ? this.height : 120);
		Dimension dimension = new Dimension(120, this.height > 120 ? this.height : 120);
		ele.setSize(dimension);
	}
	
	public void deleteAttribute(FieldRelation fr){
		MatchField[] matches = fr.getMatchFields();
		for (int i = 0; i < matches.length; i++) {
			MatchField match = matches[i];
			String readField = match.getReadField();
			DsFieldLabel lab = hmAttrToPropLabel.get(readField);
			hmAttrToPropLabel.remove(readField);
			getAttrsFigure().remove(lab);
			this.height -= LINE_HEIGHT;
			resizeHeight();
		}
	}
	
	
	protected String getTypeText() {
		return "引用数据集";
	}

}
