package nc.lfw.editor.contextmenubar;

import java.util.ArrayList;
import java.util.List;

import nc.lfw.editor.common.Connection;
import nc.lfw.editor.menubar.ele.MenuElementObj;
import nc.uap.lfw.palette.PaletteImage;
import nc.uap.lfw.palette.PaletteFactory.CustomConnectionCreationTool;
import nc.uap.lfw.palette.PaletteFactory.CustomSelectionTool;
import nc.uap.lfw.palette.PaletteFactory.TemplateCreationTool;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;

public class ContextMenuPalette {
	public static PaletteRoot createPaletteRoot() {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.add(createBasePalette(paletteRoot));
		paletteRoot.add(createBusinessComponentPalette());
		paletteRoot.add(createConnectionPalette());
		return paletteRoot;
	}

	private static PaletteContainer createConnectionPalette() {
		PaletteDrawer drawer = new PaletteDrawer("������ϵ������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationTE = createConnection();
		entries.add(relationTE);
		drawer.addAll(entries);
		return drawer;

	}

	private static ToolEntry createNewMenu() {
		ToolEntry entityTE = new CombinedTemplateCreationEntry("�Ҽ��Ӳ˵�",
				"����һ���Ҽ��Ӳ˵�", MenuElementObj.class, new SimpleFactory(
						MenuElementObj.class), PaletteImage
						.getEntityImgDescriptor(), PaletteImage
						.getEntityImgDescriptor());
		entityTE.setToolClass(TemplateCreationTool.class);

		return entityTE;
	}

	/**
	 * ����������ϵ
	 * 
	 * @author guoweic
	 * @return
	 */
	private static ToolEntry createConnection() {
		ToolEntry relationTE = new ConnectionCreationToolEntry("����",
				"����һ���µĹع�ϵ", new CreationFactory() {
					public Object getNewObject() {
						return Connection.class;
					}

					public Object getObjectType() {
						return new Integer(1);
					}
				}, PaletteImage.getRelationImgDescriptor(), PaletteImage
						.getRelationImgDescriptor());
		relationTE.setToolClass(CustomConnectionCreationTool.class);
		return relationTE;
	}

	/**
	 * �����༭����ҵ�����������
	 * 
	 * @return
	 */
	private static PaletteContainer createBusinessComponentPalette() {
		PaletteDrawer drawer = new PaletteDrawer("ҵ�����������");
		List<ToolEntry> entries = new ArrayList<ToolEntry>();
		ToolEntry relationREF = createNewMenu();
		entries.add(relationREF);
		drawer.addAll(entries);
		return drawer;

	}

	private static PaletteContainer createBasePalette(PaletteRoot root) {
		PaletteContainer container = new PaletteGroup("Base Palette");
		SelectionToolEntry entry = new SelectionToolEntry("ѡ��");
		entry.setToolClass(CustomSelectionTool.class);
		container.add(entry);
		return container;
	}
}
