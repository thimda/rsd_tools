package nc.uap.lfw.chart.model;

import java.util.ArrayList;
import java.util.Arrays;

import nc.lfw.editor.common.LfwElementObjWithGraph;
import nc.lfw.editor.common.NoEditableTextPropertyDescriptor;
import nc.uap.lfw.core.comp.BaseChartModel;
import nc.uap.lfw.core.comp.WebElement;
import nc.uap.lfw.core.data.Dataset;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class BaseChartModelEleObj extends LfwElementObjWithGraph{

	private static final long serialVersionUID = -8058986053939782552L;
	private BaseChartModel basebarChartModel = null;
	public static final String PROP_CHARTMODEL_ELEMENT ="chartmodel_element";
	public static final String PROP_DATASET = "element_DATASET";
	private Dataset ds;
	
	public BaseChartModel getBasebarChartModel() {
		return basebarChartModel;
	}

	public void setBasebarChartModel(BaseChartModel basebarChartModel) {
		this.basebarChartModel = basebarChartModel;
		fireStructureChange(PROP_CHARTMODEL_ELEMENT, basebarChartModel);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> al = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor[] pds = new PropertyDescriptor[2];
		pds[0] = new NoEditableTextPropertyDescriptor(PROP_ID, "ID");
		pds[0].setCategory("����");
		pds[1] = new NoEditableTextPropertyDescriptor(PROP_DATASET,"���ݼ�");
		pds[1].setCategory("�߼�");
		al.addAll(Arrays.asList(pds));
		return al.toArray(new IPropertyDescriptor[0]);
	}
	

	public Dataset getDs() {
		return ds;
	}

	public void setDs(Dataset ds) {
		this.ds = ds;
	}

	

	@Override
	public WebElement getWebElement() {
		// TODO Auto-generated method stub
		return basebarChartModel;
	}

	public Object getPropertyValue(Object id) {
		if(PROP_ID.equals(id))
			return basebarChartModel.getId() == null?"":basebarChartModel.getId();
		else if(PROP_DATASET.equals(id))
			return basebarChartModel.getDataset() == null?"":basebarChartModel.getDataset();
		else 
			return null;
		
	}

	public void setPropertyValue(Object id, Object value) {
		if(PROP_ID.equals(id))
			basebarChartModel.setId((String)value);
		if(PROP_DATASET.equals(id)){
			basebarChartModel.setDataset((String)value);
		}
	}

}
