/**
 * 
 */
package nc.uap.lfw.palette;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.editor.application.ApplicationObj;
import nc.uap.lfw.editor.window.WindowObj;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * AMC工具箱类
 * @author chouhl
 *
 */
public class AMCPaletteFactory extends PaletteFactory {
	
	/**
	 * 创建Application编辑器工具箱
	 * @author chouhl
	 * @return
	 */
	public static PaletteRoot createApplicationPalette(IProject project, TreeItem currentTreeItem){
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createApplicationBusinessComponentPalette());
		paletteRoot.add(createApplicationWindowListPalette(project, currentTreeItem));
		return paletteRoot;
	}
	
	/**
	 * 基本工具箱——选择
	 * @param root
	 * @return
	 */
	private static PaletteContainer createBasePalette(PaletteRoot root) {
		PaletteContainer container = new PaletteGroup("Base Palette");
		SelectionToolEntry entry = new SelectionToolEntry("选择");
		entry.setToolClass(CustomSelectionTool.class);
		container.add(entry);
		return container;
	}
	
	/**
	 * Application业务组件工具箱——创建Window
	 * @author chouhl
	 * @return
	 */
	private static PaletteContainer createApplicationBusinessComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("业务组件工具箱");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("创建Window",
				"创建一个新的Window", ApplicationObj.class,
				new SimpleFactory(ApplicationObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		entries.add(entityTE);
		drawer.addAll(entries);
		return drawer;
	}
	
	/**
	 * Application Window列表——选择Window
	 * @param project
	 * @param currentTreeItem
	 * @return
	 */
	private static PaletteContainer createApplicationWindowListPalette(IProject project, TreeItem currentTreeItem){
		PaletteDrawer drawer = new PaletteDrawer("Window列表");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry entityTE = new CombinedTemplateCreationEntry("选择Window",
				"选择一个存在的Window", WindowObj.class,
				new SimpleFactory(WindowObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);
		entries.add(entityTE);
		drawer.addAll(entries);
		return drawer;
	}
	
}
