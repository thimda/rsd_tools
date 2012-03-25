package nc.uap.lfw.chart.core;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.chart.model.Bar3DChartModelEleObj;
import nc.uap.lfw.chart.model.BaseChartModelEleObj;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.palette.PaletteFactory.TemplateCreationTool;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;

public class ChartPaletteFactory {

	public static PaletteRoot createChartPalette() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createBar2DPalette());
		return paletteRoot;
	}
	
	private static PaletteContainer createBar2DPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityBar2D = new CombinedTemplateCreationEntry("��ͼ",
				"����һ����ͼͼ", BaseChartModelEleObj.class,
				new SimpleFactory(BaseChartModelEleObj.class), PaletteImage
						.getBar2DChartDescriptor(), PaletteImage
						.getBar2DChartDescriptor());
		entityBar2D.setToolClass(TemplateCreationTool.class);
		entries.add(entityBar2D);
		
		ToolEntry entityBar3D = new CombinedTemplateCreationEntry("��״ͼ",
				"����һ����״ͼ", Bar3DChartModelEleObj.class,
				new SimpleFactory(Bar3DChartModelEleObj.class), PaletteImage
						.getBarChartDescriptor(), PaletteImage
						.getBarChartDescriptor());
		entityBar3D.setToolClass(TemplateCreationTool.class);
		entries.add(entityBar3D);
		
		ToolEntry enttityBar3 = new CombinedTemplateCreationEntry("����ͼ",
				"����һ������ͼ", BaseChartModelEleObj.class,
				new SimpleFactory(BaseChartModelEleObj.class), PaletteImage.getEntityImgDescriptor(), 
				PaletteImage.getEntityImgDescriptor());
			enttityBar3.setToolClass(TemplateCreationTool.class);
		entries.add(enttityBar3);
		
		drawer.addAll(entries);
		return drawer;

	}
	
	
}
